package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.entity.Category;
import edu.qust.reggie.entity.Dish;
import edu.qust.reggie.entity.Setmeal;
import edu.qust.reggie.mapper.CategoryMapper;
import edu.qust.reggie.service.ICategoryService;
import edu.qust.reggie.service.IDishService;
import edu.qust.reggie.service.ISetmealService;
import edu.qust.reggie.service.ex.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @version 1.0
 * @description 分类模块 - 业务层实现类
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Autowired
    private IDishService dishService;
    @Autowired
    private ISetmealService setmealService;

    @Override
    public void remove(long id) {
        // 1.判断当前分类是否关联了菜品，如果有管理则抛出业务异常
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishLambdaQueryWrapper);
        if (dishCount > 0) {
            throw new CustomException("该分类已经关联了菜品，不能删除");
        }
        // 2.判断当前分类是否关联了套餐，如果有管理则抛出业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int setmealCount = setmealService.count(setmealLambdaQueryWrapper);
        if (setmealCount > 0) {
            throw new CustomException("该分类已经关联了套餐，不能删除");
        }

        // 3.没有关联则删除该分类
        super.removeById(id);
    }
}
