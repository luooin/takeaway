package edu.qust.reggie.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.qust.reggie.common.R;
import edu.qust.reggie.dto.DishDto;
import edu.qust.reggie.dto.SetmealDto;
import edu.qust.reggie.service.IDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 
 * @version 1.0
 * @description 菜品管理模块 - 控制层
 */

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private IDishService dishService;



    /**
     * 新增菜品
     * @param dishDto 新增菜品 dto 对象
     * @return R
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * 菜品信息分页查询
     * @param page 当前页码
     * @param pageSize 每一页显示的最大记录数
     * @param name 模糊查询（可选）
     * @return R
     */
    @GetMapping("/page")
    public R<Page<DishDto>> page(Integer page, Integer pageSize, String name) {
        Page<DishDto> result = dishService.page(page, pageSize, name);

        return R.success(result);
    }

    /**
     * 根据 id 查询菜品信息（在修改菜品页面用于原数据的回显）
     * @param id 菜品 id
     * @return R 用于页面回显
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }

    /**
     * 修改菜品信息
     * @param dishDto 菜品 dto 对象
     * @return R
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);

        return R.success("修改菜品成功");
    }

    /**
     * 删除菜品信息 (单项删除/批量删除)
     * @param ids 要删除的菜品 id
     * @return R
     */
    @DeleteMapping
    public R<String> delete(Long[] ids) {
        dishService.deleteByIds(ids);

        return R.success("删除菜品信息成功");
    }

    /**
     * 修改菜品状态信息（单项修改/批量修改）
     * @param status 要修改的状态 0 停售 1 起售
     * @param ids 要修改的菜品的 id
     * @return R
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, Long[] ids) {
        dishService.updateStatusByIds(status, ids);

        return R.success("修改菜品状态成功");
    }

    // 该版本前台不兼容（缺少菜品口味信息） 2022/11/13 修改
    // @GetMapping("list")
    // public R<List<Dish>> list(Long categoryId ) {
    //     // 创建条件构造器
    //     LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
    //     // 设置过滤条件
    //     queryWrapper.eq(Dish::getCategoryId, categoryId);
    //     // 非停售状态才能显示
    //     queryWrapper.eq(Dish::getStatus, 1);
    //     // 设置排序条件
    //     queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
    //
    //     List<Dish> list = dishService.list(queryWrapper);
    //     return R.success(list);
    // }
    /**
     * 根据分类 id 查询菜品信息
     * @param categoryId 分类 id
     * @return R
     */
    @GetMapping("list")
    public R<List<DishDto>> list(Long categoryId) {
        List<DishDto> dishDtoList = dishService.list(categoryId);

        return R.success(dishDtoList);
    }


}
