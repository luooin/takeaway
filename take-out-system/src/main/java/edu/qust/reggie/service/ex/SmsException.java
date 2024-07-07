package edu.qust.reggie.service.ex;

/**
 * 
 * @version 1.0
 * @description 短信服务相关的异常
 */

public class SmsException extends ServiceException{
    public SmsException() {
        super();
    }

    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }

    public SmsException(Throwable cause) {
        super(cause);
    }

    protected SmsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
