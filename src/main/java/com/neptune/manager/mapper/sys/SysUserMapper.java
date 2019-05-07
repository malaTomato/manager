package com.neptune.manager.mapper.sys;

import com.neptune.manager.domain.bean.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    int insert(SysUser record);

    /**
     * 通过用户名查询
     * @param username username
     * @return SysUser
     */

    SysUser selectByUsername(String username);

}