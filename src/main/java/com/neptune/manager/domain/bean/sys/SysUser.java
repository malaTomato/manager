package com.neptune.manager.domain.bean.sys;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
@Data
public class SysUser {
    private Integer id;
    @NotNull(message = "用户名为空")
    private String username;
    @NotNull(message = "密码为空")
    private String password;
    @NotNull(message = "昵称为空")
    private String nickname;

    private String salt;

    private Integer status;

    private Date createTime;

    private Date updateTime;
}