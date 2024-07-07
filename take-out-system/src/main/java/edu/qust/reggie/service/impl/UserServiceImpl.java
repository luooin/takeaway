package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.entity.User;
import edu.qust.reggie.mapper.UserMapper;
import edu.qust.reggie.service.IUserService;
import edu.qust.reggie.service.ex.LoginException;
import edu.qust.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @version 1.0
 * @description 户信息模块（移动端） - 业务层实现类
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void sendMsg(User user, HttpSession session) throws Exception {
        // 获取手机号
        String phone = user.getPhone();

        if (StringUtils.isNotEmpty(phone)) {
            // 调用工具类随机生成验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code：{}",code);

            // 调用腾讯云提供的 API 进行发送验证码
            // SmsUtils.sendMessage(phone, code);

            // 将验证码缓存到 Redis 并设置有效期为 5 分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

        }

    }

    @Override
    public User login(Map<String, Object> map, HttpSession session) {
        // 获取前端传过来手机号和验证码
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        // 从 redis 中获取缓存的验证码
        String codeFromSession = redisTemplate.opsForValue().get(phone);

        // 进行验证码的校验
        if (codeFromSession != null && codeFromSession.equals(code)) {
            // 校验通过
            // 判断用户是否为新用户，如果是新用户就自动注册
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = this.getOne(queryWrapper);
            if (user == null) {
                user = new User();
                user.setPhone(phone);
                // 可以不设置，数据库设置了默认值
                user.setStatus(1);
                this.save(user);
            }
            // 将用户信息存放到 session 中
            session.setAttribute("user", user.getId());

            // 登录成功后，删除在 redis 中缓存的验证码
            redisTemplate.delete(phone);

            return user;
        }
        throw new LoginException("验证码输入错误，请重新输入");
    }
}
