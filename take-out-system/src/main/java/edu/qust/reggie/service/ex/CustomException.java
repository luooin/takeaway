package edu.qust.reggie.service.ex;

/**
 * 
 * @version 1.0
 * @description 删除分类信息时有菜品或者套餐关联了该分类抛出的异常
 */
public class CustomException extends ServiceException{
    public CustomException() {
        super();
    }

    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    protected CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
