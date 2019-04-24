package com.neptune.manager.mapper.sys;

import com.neptune.manager.domain.bean.sys.SysPermission;
import org.apache.ibatis.annotations.Mapper;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}