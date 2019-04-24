package com.neptune.manager.mapper.sys;

import com.neptune.manager.domain.bean.sys.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 通过用户名查询
     * @param username username
     * @return SysUser
     */

    SysUser selectByUsername(String username);

}