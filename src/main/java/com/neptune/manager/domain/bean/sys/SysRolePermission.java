package com.neptune.manager.domain.bean.sys;

import lombok.Data;

@Data
public class SysRolePermission {
    private Integer id;

    private Integer permissionId;

    private Integer roleId;
}