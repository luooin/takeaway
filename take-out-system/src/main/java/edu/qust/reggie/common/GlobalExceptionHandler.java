package edu.qust.reggie.common;

import edu.qust.reggie.service.ex.CustomException;
import edu.qust.reggie.service.ex.LoginException;
import edu.qust.reggie.service.ex.SmsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 
 * @version 1.0
 * @description 全局异常处理器，用于处理业务方法抛出的异常
 */

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    /** 处理重复数据添加数据库的异常 */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {
        log.info(exception.getMessage());
        if (exception.getMessage().contains("Duplicate entry")) {
            String[] s = exception.getMessage().split(" ");
            return R.error(s[2] + "被占用");
        }
        return R.error("未知错误");
    }

    /** 处理删除分类信息时有菜品或者套餐关联了该分类抛出的异常 */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception) {
        log.info(exception.getMessage());

        return R.error(exception.getMessage());
    }

    /** 处理登录相关的异常 */
    @ExceptionHandler(LoginException.class)
    public R<String> exceptionHandler(LoginException exception) {
        log.info(exception.getMessage());

        return R.error(exception.getMessage());
    }

    /** 处理短信服务相关的异常 */
    @ExceptionHandler(SmsException.class)
    public R<String> exceptionHandler(SmsException exception) {
        log.info(exception.getMessage());

        return R.error(exception.getMessage());
    }
}
