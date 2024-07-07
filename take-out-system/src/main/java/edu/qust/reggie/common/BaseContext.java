package edu.qust.reggie.common;

/**
 * 
 * @version 1.0
 * @description 基于 ThreadLocal 封装的工具类，用于保存和获取当前登录用户的 id
 */
public class BaseContext {
    static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 将当前登录用户的 id 保存到 ThreadLocal
     * @param id 用户 id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
    /**
     * 从 ThreadLocal 中获取当前登录用户的 id
     */
    public static long getCurrentId() {
        return threadLocal.get();
    }


}
