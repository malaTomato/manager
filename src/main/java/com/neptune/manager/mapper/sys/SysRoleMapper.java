package com.neptune.manager.mapper.sys;

import com.neptune.manager.domain.bean.sys.SysRole;
import org.apache.ibatis.annotations.Mapper;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}