package com.neptune.manager.config;

import com.alibaba.fastjson.JSONObject;
import com.neptune.manager.domain.bean.sys.SysRole;
import com.neptune.manager.domain.bean.sys.SysUser;
import com.neptune.manager.service.sys.SysUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录认证实现
 *
 * @author xiongwu
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    private static final Logger LOG = LoggerFactory.getLogger(MyShiroRealm.class);

    /**
     * 获取认证
     * @param authenticationToken  authenticationToken
     * @return AuthenticationInfo
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOG.info("--认证 getAuthenticationInfo()");

        //获取用户的输入的账号
        String username = (String) authenticationToken.getPrincipal();
        LOG.debug(JSONObject.toJSONString(authenticationToken.getCredentials()));
        LOG.debug("username:{}",username);
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser sysUser = sysUserService.findByUsername(username);
        if(sysUser == null){
            return null;
        }
        return new SimpleAuthenticationInfo(
                //用户名
                sysUser,
                //密码
                sysUser.getPassword(),
                ByteSource.Util.bytes(sysUser.getSalt()),
                //realm name
                getName()
        );
    }


    /**
     * 获取授权
     * @param principalCollection principalCollection
     * @return  AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        LOG.info("--权限配置 doGetAuthorizationInfo()");
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //拿取到认证后的用户
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
        //获取用户对应角色
        List<SysRole> roleList = sysUserService.findRoleByUid(sysUser.getId());
        if(CollectionUtils.isEmpty(roleList)){
            return null;
        }
        simpleAuthorizationInfo.addRoles(roleList.stream().map(SysRole::getRole).collect(Collectors.toList()));
        //获取用户对应权限
        Set<String> permissionByRoleList = sysUserService.findPermissionByRoleList(roleList);
        if(CollectionUtils.isEmpty(permissionByRoleList)){
            return null;
        }
        simpleAuthorizationInfo.addStringPermissions(permissionByRoleList);
        return simpleAuthorizationInfo;
    }
}
