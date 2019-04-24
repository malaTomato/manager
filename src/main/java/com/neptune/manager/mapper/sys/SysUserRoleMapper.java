package com.neptune.manager.mapper.sys;

import com.neptune.manager.domain.bean.sys.SysUserRole;
import org.apache.ibatis.annotations.Mapper;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}