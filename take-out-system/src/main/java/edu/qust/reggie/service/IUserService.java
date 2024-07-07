package edu.qust.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.qust.reggie.entity.User;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 
 * @version 1.0
 * @description 用户信息模块（移动端） - 业务层接口
 */

public interface IUserService extends IService<User> {

    /**
     * 发送短信验证码
     * @param user 当前登录的 user （内部封装了手机号）
     * @param session HttpSession 对象
     * @throws Exception e
     */

    void sendMsg(User user, HttpSession session) throws Exception;

    /**
     * 移动端用户登录功能
     * @param map map
     * @param session HttpSession 对象
     * @return User 对象
     */
    User login(Map<String, Object> map, HttpSession session);
}
