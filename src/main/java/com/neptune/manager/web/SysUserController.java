package com.neptune.manager.web;

import com.alibaba.fastjson.JSONObject;
import com.neptune.manager.domain.bean.sys.SysUser;
import com.neptune.manager.service.sys.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiongwu
 **/
@Controller
public class SysUserController {

    private static final Logger LOG = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping({"/","/index"})
    public String index(){


        return"/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map){
        LOG.info("-->>登录<<--");

        System.out.println("HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        return "/login";
    }


    @RequestMapping("/userInfoAdd")
    @RequiresPermissions("userInfo:add")
    public String userInfoAdd(@Valid @RequestBody SysUser sysUser){
        LOG.info("-->>userInfoAdd<<--");
        LOG.info(JSONObject.toJSONString(SecurityUtils.getSubject().getPrincipals()));

        sysUserService.addSysUser(sysUser).get();

        return "/userInfoAdd";
    }


    @RequestMapping("/userInfoDel")
    @RequiresPermissions("userInfo:del")
    public String userInfoDel(){
        LOG.info("-->>userInfoDel<<--");
//
//        System.out.println("HomeController.login()");
//        // 登录失败从request中获取shiro处理的异常信息。
//        // shiroLoginFailure:就是shiro异常类的全类名.
//        String exception = (String) request.getAttribute("shiroLoginFailure");
//        System.out.println("exception=" + exception);
//        String msg = "";

        return "/userInfoDel";
    }


    @RequestMapping("/userInfoView")
    public String userInfoView(){
        LOG.info("-->>userInfoAdd<<--");
        LOG.info(JSONObject.toJSONString(SecurityUtils.getSubject().getPrincipals()));
//
//        System.out.println("HomeController.login()");
//        // 登录失败从request中获取shiro处理的异常信息。
//        // shiroLoginFailure:就是shiro异常类的全类名.
//        String exception = (String) request.getAttribute("shiroLoginFailure");
//        System.out.println("exception=" + exception);
//        String msg = "";

        return "/userInfoView";
    }

    public static void main(String[] args) {
        Map<String, SysUser> map = new HashMap<>();
        SysUser sysUser = new SysUser();
        sysUser.setPassword("123");
        map.put("11",sysUser);
        System.out.println(JSONObject.toJSONString(map));
        sysUser.setPassword("34556");
        System.out.println(JSONObject.toJSONString(map));
        BigDecimal bigDecimal = new BigDecimal("0.09");
        System.out.println(bigDecimal);
    }
}
