package edu.qust.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @version 1.0
 * @description 通用返回结果 - 后端向前端返回的数据都会封装成该对象
 */
@Data
public class R<T> {
    /** 编码：1成功，0和其它数字为失败 */
    private Integer code;
    /** 错误信息 */
    private String msg;
    /** 数据 */
    private T data;
    /** 动态数据 */
    private Map<String, Object> map = new HashMap<>();

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<T>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}