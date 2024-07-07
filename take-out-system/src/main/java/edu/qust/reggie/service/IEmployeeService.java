package edu.qust.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.qust.reggie.entity.Employee;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @version 1.0
 * @description 员工模块 - 业务层接口
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 员工登录功能
     * @param employee Employee对象
     * @param session HttpSession对象
     * @return Employee
     */
    Employee login(Employee employee, HttpSession session);
}
