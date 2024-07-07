package edu.qust.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.qust.reggie.entity.Employee;
import org.springframework.stereotype.Repository;

/**
 * 
 * @version 1.0
 * @description 员工模块 - 持久层
 */
@Repository
public interface EmployeeMapper extends BaseMapper<Employee> {
}
