package com.neptune.manager.common.exception;

import com.alibaba.fastjson.JSONObject;
import com.neptune.manager.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author xiongwu
 */
@RestControllerAdvice
public class GlobalExceptionHandle {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result errorHandler(Exception e) {
        Result result = new Result();
        if (e instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) e;
            result.setCode(serviceException.getCode());
            result.setMsg(serviceException.getMsg());
            LOGGER.error("错误消息{}", JSONObject.toJSONString(result));
            return result;
        } else {
            LOGGER.error("系统异常",e);
            result.setCode(111001);
            result.setMsg("系统异常");
            return result;
        }
    }
}
