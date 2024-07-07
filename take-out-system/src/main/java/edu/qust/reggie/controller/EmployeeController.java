package edu.qust.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.qust.reggie.common.R;
import edu.qust.reggie.entity.Employee;
import edu.qust.reggie.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @version 1.0
 * @description 员工模块 - 控制层
 */

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * 员工登录
     * @param employee Employee对象（前端传）
     * @param session HttpSession对象
     * @return R<Employee>
     */
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpSession session) {
        Employee result = employeeService.login(employee, session);

        return R.success(result);
    }

    /**
     * 员工退出登录
     * @param request HttpServletRequest对象
     * @return R<String>
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee Employee对象（前端传）
     * @return R<String>
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        // 给一个初始的默认密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 补全日志相关信息 (已修正为 MP 自动补全公共字段)
        // LocalDateTime now = LocalDateTime.now();
        // long empId = (long) request.getSession().getAttribute("employee");
        // employee.setCreateTime(now);
        // employee.setCreateUser(empId);
        // employee.setUpdateTime(now);
        // employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("新增员工成功");
    }

    /**
     * 员工信息分页查询
     * @param page 页码
     * @param pageSize 每一页显示的数量
     * @param name 查询的员工姓名（非必要）
     * @return MybatisPlus封装的 Page 分页构造器
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("接收到分页查询请求");

        // 创建分页构造器
        Page<Employee> pageInfo = new Page<>(page, pageSize);
        // 创建条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        // 执行查询
        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /** 修改员工信息 - 修改状态/编辑信息 */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        // log.info("接收到修改员工信息请求");
        // 补全日志相关字段 (已修正为 MP 自动补全公共字段)
        // employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        // employee.setUpdateTime(LocalDateTime.now());

        employeeService.updateById(employee);
        return R.success("修改信息成功");
    }

    /**
     * 根据 Id 查询员工信息
     * @param id 员工 id
     * @return R
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {

        Employee employee = employeeService.getById(id);
        return employee != null ? R.success(employee): R.error("查询员工信息失败");
    }


}
