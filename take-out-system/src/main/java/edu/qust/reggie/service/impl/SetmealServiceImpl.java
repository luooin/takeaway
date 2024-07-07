package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.dto.SetmealDto;
import edu.qust.reggie.entity.*;
import edu.qust.reggie.mapper.SetmealMapper;
import edu.qust.reggie.service.ICategoryService;
import edu.qust.reggie.service.ISetmealDishService;
import edu.qust.reggie.service.ISetmealService;
import edu.qust.reggie.service.ex.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @version 1.0
 * @description 套餐模块 - 业务层实现类
 */

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {

    @Autowired
    private ISetmealDishService setmealDishService;

    @Autowired
    private ICategoryService categoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "setmealCache", allEntries = true)
    public void saveWithDish(SetmealDto setmealDto) {
        // 新增套餐信息
        this.save(setmealDto);
        // 在套餐与菜品关系表中添加数据
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        // 补全套餐 id 字段
        for (SetmealDish setmealDish: setmealDishes) {
            setmealDish.setSetmealId(setmealDto.getId());
        }
        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "setmealCache", allEntries = true)
    public void deleteByIds(Long[] ids) {
        // 判断是否有套餐处于在售状态，如果有则抛出一个异常
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Setmeal::getId, Arrays.asList(ids));
        lambdaQueryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(lambdaQueryWrapper);
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        // 删除套餐表
        this.removeByIds(Arrays.asList(ids));
        // 删除 套餐与菜品关系表中的信息
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId, Arrays.asList(ids));
        setmealDishService.remove(queryWrapper1);

    }

    @Override
    @CacheEvict(value = "setmealCache", allEntries = true)
    public void updateStatusByIds(Integer status, Long[] ids) {
        for (long id: ids) {
            Setmeal setmeal = this.getById(id);
            setmeal.setStatus(status);
            this.updateById(setmeal);
        }
    }

    @Override
    public Page<SetmealDto> page(Integer page, Integer pageSize, String name) {
        // 创建分页构造器
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        // 创建条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(name != null, Setmeal::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        this.page(pageInfo, queryWrapper);

        // 解决页面显示分类信息
        Page<SetmealDto> setmealDtoPage = new Page<>();
        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, setmealDtoPage, "records");
        // 分页的记录信息需要换成 DishDto 对象，这样可以显示分类名称
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> dishDtoList = new ArrayList<>();

        for (Setmeal setmeal: records) {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);
            Category category = categoryService.getById(setmeal.getCategoryId());
            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }
            dishDtoList.add(setmealDto);
        }
        setmealDtoPage.setRecords(dishDtoList);

        return setmealDtoPage;
    }

    @Override
    @Cacheable(value = "setmealCache", key = "#categoryId")
    public List<Setmeal> list(Long categoryId) {
        // 创建条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        // 设置过滤条件
        queryWrapper.eq(Setmeal::getCategoryId, categoryId);
        // 非停售状态才能显示
        queryWrapper.eq(Setmeal::getStatus, 1);
        // 设置排序条件
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = this.list(queryWrapper);


        return list;
    }
}
