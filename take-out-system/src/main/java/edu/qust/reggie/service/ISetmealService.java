package edu.qust.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.qust.reggie.dto.DishDto;
import edu.qust.reggie.dto.SetmealDto;
import edu.qust.reggie.entity.Setmeal;

import java.util.List;

/**
 * 
 * @version 1.0
 * @description 套餐模块 - 业务层接口
 */
public interface ISetmealService extends IService<Setmeal> {

    /**
     * 新增套餐（需要同时保存套餐和套餐与菜品的关系）
     * @param setmealDto 套餐 + 套餐内菜品 DTO 对象
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 根据 id 删除套餐信息
     * @param ids id数组
     */
    void deleteByIds(Long[] ids);

    /**
     * 根据 id 更新套餐状态信息
     * @param status 要更新的状态
     * @param ids id数组
     */
    void updateStatusByIds(Integer status, Long[] ids);

    /**
     * 套餐管理页面分页查询
     * @param page 当前页码
     * @param pageSize 每一页显示的最大记录数
     * @param name 模糊查询（可选）
     * @return Page<SetmealDto>
     */
    Page<SetmealDto> page(Integer page, Integer pageSize, String name);

    /**
     * 根据分类 id 查询套餐信息（前台使用）
     * @param categoryId 分类 id
     * @return 菜品信息
     */
    List<Setmeal> list(Long categoryId);
}
