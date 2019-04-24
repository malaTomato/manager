package com.neptune.manager.mapper.sys;

import com.neptune.manager.domain.bean.sys.SysRole;

import java.util.List;

/**
 * The interface Sys role mapper.
 */
public interface SysRoleMapper {
    /**
     * Select by uid list.
     *
     * @param uid the uid
     * @return the list
     */
    List<SysRole> selectByUid(Integer uid);
}