package edu.qust.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.qust.reggie.dto.DishDto;
import edu.qust.reggie.dto.SetmealDto;
import edu.qust.reggie.entity.Dish;

import java.util.List;


/**
 * 
 * @version 1.0
 * @description 菜品模块 - 业务层接口
 */

public interface IDishService extends IService<Dish> {

    /**
     * 新增菜品，同时插入菜品对应的口味信息（操纵两种表）
     * 需要启动事务
     * @param dishDto 菜品 DTO 对象
     */
    void saveWithFlavor(DishDto dishDto);

    /**
     * 根据菜品 id 查询菜品信息
     * @param id 菜品 id
     * @return 菜品信息（菜品信息 + 风味信息）
     */
    DishDto getByIdWithFlavor(Long id);

    /**
     * 修改菜品信息 同时修改菜品对应的口味信息（操纵两种表）
     * @param dishDto 菜品 DTO 对象
     */
    void updateWithFlavor(DishDto dishDto);


    /**
     * 根据 id 删除菜品信息
     * @param ids id数组
     */
    void deleteByIds(Long[] ids);

    /**
     * 根据 id 更新菜品状态信息
     * @param status 要更新的状态
     * @param ids id数组
     */
    void updateStatusByIds(Integer status, Long[] ids);

    /**
     * 菜品管理页面分页查询
     * @param page 当前页码
     * @param pageSize 每一页显示的最大记录数
     * @param name 模糊查询（可选）
     * @return Page<SetmealDto>
     */
    Page<DishDto> page(Integer page, Integer pageSize, String name);

    /**
     * 根据分类 id 查询菜品信息（前台后台通用）
     * @param categoryId 分类 id
     * @return 菜品信息
     */
    List<DishDto> list(Long categoryId);
}
