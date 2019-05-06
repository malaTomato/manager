package com.neptune.manager.domain.bean.sys;

import lombok.Data;

import java.util.Date;

@Data
public class SysPermission {
    private Integer id;

    private String name;

    private Byte resourceType;

    private String url;

    private String permission;

    private Integer parentid;

    private String parentids;

    private Byte status;

    private Date createTime;

    private Date updateTime;

}