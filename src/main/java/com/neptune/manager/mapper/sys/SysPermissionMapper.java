package com.neptune.manager.mapper.sys;

import java.util.List;
import java.util.Set;

/**
 * The interface Sys permission mapper.
 */
public interface SysPermissionMapper {

    /**
     * Select by role id set.
     *
     * @param list the list
     * @return the set
     */
    Set<String> selectByRoleId(List<Integer> list);
}