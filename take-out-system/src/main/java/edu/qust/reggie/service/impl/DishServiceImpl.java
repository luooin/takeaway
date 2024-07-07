package edu.qust.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.qust.reggie.dto.DishDto;
import edu.qust.reggie.entity.Category;
import edu.qust.reggie.entity.Dish;
import edu.qust.reggie.entity.DishFlavor;
import edu.qust.reggie.mapper.DishMapper;
import edu.qust.reggie.service.ICategoryService;
import edu.qust.reggie.service.IDishFlavorService;
import edu.qust.reggie.service.IDishService;
import edu.qust.reggie.service.ex.CustomException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @version 1.0
 * @description 菜品模块 - 业务层实现类
 */

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {

    @Autowired
    private IDishFlavorService dishFlavorService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWithFlavor(DishDto dishDto) {
        // 保存菜品基本信息到 dish 表
        super.save(dishDto);
        // 保存菜品风味信息到 dish_flavor 表
        // 由于 list 集合中缺少 dishId 信息，手动补充一下
        Long dishId = dishDto.getId();
        for (DishFlavor dishFlavor: dishDto.getFlavors()) {
            dishFlavor.setDishId(dishId);
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());

        // 清理 redis 之前的缓存信息，避免缓存中数据与数据库数据不一致
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        // 查询菜品信息
        Dish dish = this.getById(id);
        // 拷贝到 dishDto 对象中
        BeanUtils.copyProperties(dish, dishDto);
        // 查询风味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        // 添加口味信息
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateWithFlavor(DishDto dishDto) {
        // 修改菜品信息
        this.updateById(dishDto);
        // 将之前的口味信息删除
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        // 添加新的口味信息
        // 补全 dish_id 字段
        Long dishId = dishDto.getId();
        for (DishFlavor dishFlavor: dishDto.getFlavors()) {
            dishFlavor.setDishId(dishId);
        }
        dishFlavorService.saveBatch(dishDto.getFlavors());

        // 清理 redis 之前的缓存信息，避免缓存中数据与数据库数据不一致
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Long[] ids) {
        // 判断是否有菜品处于在售状态，如果有则抛出一个异常
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, Arrays.asList(ids));
        queryWrapper.eq(Dish::getStatus, 1);
        int count = this.count(queryWrapper);
        if (count > 0) {
            throw new CustomException("菜品正在售卖中，不能删除");
        }
        // 删除菜品表
        this.removeByIds(Arrays.asList(ids));
        // 删除菜品口味表的信息
        LambdaQueryWrapper<DishFlavor> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(DishFlavor::getDishId, Arrays.asList(ids));
        dishFlavorService.remove(queryWrapper1);

        // 清理 redis 之前的缓存信息，避免缓存中数据与数据库数据不一致
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);


    }

    @Override
    public void updateStatusByIds(Integer status, Long[] ids) {
        for (long id: ids) {
            Dish dish = this.getById(id);
            dish.setStatus(status);
            this.updateById(dish);
        }

        // 清理 redis 之前的缓存信息，避免缓存中数据与数据库数据不一致
        Set keys = redisTemplate.keys("dish_*");
        redisTemplate.delete(keys);

    }

    @Override
    public Page<DishDto> page(Integer page, Integer pageSize, String name) {
        // 创建分页构造器
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        // 创建条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        // 添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        this.page(pageInfo, queryWrapper);

        // 解决页面显示分类信息
        Page<DishDto> dishDtoPage = new Page<>();
        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");
        // 分页的记录信息需要换成 DishDto 对象，这样可以显示分类名称
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> dishDtoList = new ArrayList<>();

        for (Dish dish: records) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            Category category = categoryService.getById(dish.getCategoryId());
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            dishDtoList.add(dishDto);
        }
        dishDtoPage.setRecords(dishDtoList);

        return dishDtoPage;
    }

    @Override
    public List<DishDto> list(Long categoryId) {
        List<DishDto> dishDtoList;
        // 动态构造 redis 的 key
        String key = "dish_" + categoryId + "_1";
        // 先从 redis 中查询缓存数据，如果找到则直接返回
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);

        if (dishDtoList != null) {
            return dishDtoList;
        }

        // 缓存中不存在数据的话就查询数据库，然后将菜品数据缓存到 redis
        // 创建条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 设置过滤条件
        queryWrapper.eq(Dish::getCategoryId, categoryId);
        // 非停售状态才能显示
        queryWrapper.eq(Dish::getStatus, 1);
        // 设置排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = this.list(queryWrapper);

        dishDtoList = new ArrayList<>();

        // 补全口味信息
        for (Dish dish: list) {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            Long dishId = dish.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId, dishId);
            // 根据菜品 id 查询菜品口味信息
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            dishDtoList.add(dishDto);
        }
        // 将菜品数据缓存到 redis
        redisTemplate.opsForValue().set(key, dishDtoList, 60, TimeUnit.MINUTES);


        return dishDtoList;
    }
}
