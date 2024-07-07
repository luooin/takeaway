package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.entity.Employee;
import edu.qust.reggie.mapper.EmployeeMapper;
import edu.qust.reggie.service.IEmployeeService;
import edu.qust.reggie.service.ex.LoginException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpSession;

/**
 * 
 * @version 1.0
 * @description 员工模块 - 业务层实现类
 */

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements IEmployeeService {

    @Override
    public Employee login(Employee employee, HttpSession session) {
        // 1. 对前端传过来的 Employee 对象的密码进行 MD5 加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据用户名查询数据库
        String username = employee.getUsername();
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, username);
        Employee result = this.getOne(queryWrapper);

        // 3.用户不存在/密码错误，直接返回结果
        if (result == null) {
            throw new LoginException("用户不存在");
        }
        if (!result.getPassword().equals(password)) {
            throw new LoginException("密码错误");
        }

        // 4.查看员工状态，如果为禁用状态（0） ，返回结果
        if (result.getStatus() == 0) {
            throw new LoginException("\"账号已禁用");
        }

        // 5.信息匹配，登录成功，将用户的 id 存放到 session中，并返回结果
        session.setAttribute("employee", result.getId());

        return result;
    }
}
