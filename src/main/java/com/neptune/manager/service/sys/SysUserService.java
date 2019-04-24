package com.neptune.manager.service.sys;

import com.neptune.manager.domain.bean.sys.SysPermission;
import com.neptune.manager.domain.bean.sys.SysRole;
import com.neptune.manager.domain.bean.sys.SysUser;
import com.neptune.manager.mapper.sys.SysPermissionMapper;
import com.neptune.manager.mapper.sys.SysRoleMapper;
import com.neptune.manager.mapper.sys.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author xiongwu
 **/
@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysPermissionMapper sysPermissionMapper;


    public SysUser findByUsername(String username){
        if(StringUtils.isEmpty(username)){
            return null;
        }
        return sysUserMapper.selectByUsername(username);
    }

    public List<SysRole> findRoleByUid(Integer uid){
        return sysRoleMapper.selectByUid(uid);
    }

    public Set<String> findPermissionByRoleList(List<SysRole> list){
        return sysPermissionMapper.selectByRoleId(list.parallelStream().map(SysRole::getId).collect(Collectors.toList()));
    }

}
