package com.neptune.manager.service.sys;

import com.neptune.manager.common.Result;
import com.neptune.manager.common.exception.ServiceException;
import com.neptune.manager.common.uitl.CommonIdGenerator;
import com.neptune.manager.domain.bean.sys.SysRole;
import com.neptune.manager.domain.bean.sys.SysUser;
import com.neptune.manager.mapper.sys.SysPermissionMapper;
import com.neptune.manager.mapper.sys.SysRoleMapper;
import com.neptune.manager.mapper.sys.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.Selector;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    public Result<Boolean> addSysUser(SysUser sysUser){

        SysUser oldUser = sysUserMapper.selectByUsername(sysUser.getUsername());
        if(oldUser != null){
            throw new ServiceException(3,"用户名已经被占用");
        }

        String salt = UUID.randomUUID().toString().replace("-", "");
        String password = salt + sysUser.getPassword();
        sysUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        sysUser.setSalt(salt);
        sysUser.setStatus(1);
        if(1 != sysUserMapper.insert(sysUser)){
            throw new ServiceException(2,"数据更新错误");
        }
        return new Result<>(true);
    }


    public static void main(String[] args) {

        FileInputStream fis = null; // Path of Input text file
        try {
            fis = new FileInputStream("D:\\file-read.txt");



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ReadableByteChannel rbc = fis.getChannel();


        try {
            Selector selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
