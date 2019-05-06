package com.neptune.manager.domain.bean.sys;

import lombok.Data;

import java.util.Date;
@Data
public class SysRole {
    private Integer id;

    private String role;

    private String description;

    private Byte status;

    private Date createTime;

    private Date updateTime;
}