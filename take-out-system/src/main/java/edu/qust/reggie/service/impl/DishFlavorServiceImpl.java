package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.entity.DishFlavor;
import edu.qust.reggie.mapper.DishFlavorMapper;
import edu.qust.reggie.service.IDishFlavorService;
import org.springframework.stereotype.Service;

/**
 * 
 * @version 1.0
 * @description 菜品风味模块 - 业务层实现类
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements IDishFlavorService {
}
