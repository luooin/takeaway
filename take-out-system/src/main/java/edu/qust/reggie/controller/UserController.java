package edu.qust.reggie.controller;

import edu.qust.reggie.common.R;
import edu.qust.reggie.entity.User;
import edu.qust.reggie.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 
 * @version 1.0
 * @description 用户信息模块（移动端） - 控制层
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;



    /**
     * 发送短信验证码
     * @param user 当前登录的 user （内部封装了手机号）
     * @param session HttpSession 对象
     * @return R
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        // 调用业务层方法发送验证码
        try {
            userService.sendMsg(user, session);
        } catch (Exception e) {
            return R.error("手机验证码发送失败");
        }

        return R.success("手机验证码发送成功");

    }

    /**
     * 移动端用户登录
     * @param map map
     * @param session HttpSession 对象
     * @return R
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map<String, Object> map, HttpSession session) {
        User user = userService.login(map, session);

        return R.success(user);
    }

    /**
     * 移动端用户登录
     * @param session HttpSession 对象
     * @return R
     */
    @PostMapping("/loginout")
    public R<?> loginout(HttpSession session) {
        session.removeAttribute("user");
        return R.success(" loginout success");
    }

}
