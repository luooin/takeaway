package edu.qust.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.qust.reggie.common.R;
import edu.qust.reggie.dto.SetmealDto;
import edu.qust.reggie.entity.Setmeal;
import edu.qust.reggie.service.ISetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 *
 * @version 1.0
 * @description 套餐模块 - 控制层
 */

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private ISetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDto 套餐 + 套餐内菜品 DTO 对象
     * @return R
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {
       setmealService.saveWithDish(setmealDto);
       return R.success("新增套餐成功");
    }

    /**
     * 套餐管理页面分页查询
     * @param page 当前页码
     * @param pageSize 每一页显示的最大记录数
     * @param name 模糊查询（可选）
     * @return R
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(Integer page, Integer pageSize, String name) {
        Page<SetmealDto> setmealDtoPage = setmealService.page(page, pageSize, name);

        return R.success(setmealDtoPage);
    }

    /**
     * 删除套餐信息 (单项删除/批量删除)
     * @param ids 要删除的套餐 id
     * @return R
     */
    @DeleteMapping
    public R<String> delete(Long[] ids) {
        setmealService.deleteByIds(ids);

        return R.success("删除套餐信息成功");
    }

    /**
     * 修改套餐状态信息（单项修改/批量修改）
     * @param status 要修改的状态 0 停售 1 起售
     * @param ids 要修改的套餐的 id
     * @return R
     */
    @PostMapping("/status/{status}")
    public R<String> updateStatus(@PathVariable Integer status, Long[] ids) {
        setmealService.updateStatusByIds(status, ids);

        return R.success("更新菜品状态信息成功");
    }

    /**
     * 根据分类 id 查询套餐信息（前台使用）
     * @param categoryId 分类 id
     * @return 菜品信息
     */
    @GetMapping("list")
    public R<List<Setmeal>> list(Long categoryId ) {
        List<Setmeal> setmealList = setmealService.list(categoryId);

        return R.success(setmealList);
    }

    /**
     * 修改套餐状态信息（单项修改/批量修改）
     */
    @GetMapping("/{id}")
    public R<Setmeal> getStatus(@PathVariable String id) {
        Setmeal setmeal = setmealService.getById(id);
        return R.success(setmeal);
    }
}
