package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.entity.SetmealDish;
import edu.qust.reggie.mapper.SetmealDishMapper;
import edu.qust.reggie.service.ISetmealDishService;
import org.springframework.stereotype.Service;

/**
 * 
 * @version 1.0
 * @description 套餐内的菜品模块 - 业务层实现类
 */

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements ISetmealDishService {
}
