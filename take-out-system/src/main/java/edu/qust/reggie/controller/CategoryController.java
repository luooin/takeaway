package edu.qust.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.qust.reggie.common.R;
import edu.qust.reggie.entity.Category;
import edu.qust.reggie.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 
 * @version 1.0
 * @description 分类模块 - 控制层
 */

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;

    /**
     * 新增一条分类信息（菜品分类/套餐分类根据 type 值决定）
     * @param category 前端传过来的 category
     * @return R
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);

        return R.success("新增分类成功");
    }

    /**
     * 分类信息 - 分页展示
     * @param page 页数
     * @param pageSize 页面显示数量
     * @return R
     */
    @GetMapping("/page")
    public R<Page<Category>> page(int page, int pageSize) {
        // 创建分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        // 创建条件过滤器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.orderByDesc(Category::getSort);

        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 删除分类信息（对于绑定了菜品或者套餐的分类禁止删除）
     * @param ids 删除的分类的 id
     * @return R
     */
    @DeleteMapping
    public R<String> delete(long ids) {
        categoryService.remove(ids);

        return R.success("删除分类成功");
    }

    /**
     * 通过 id 修改分类信息
     * @param category 前端传过来的 category
     * @return R
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);

        return R.success("修改分类成功");
    }

    /**
     * 根据条件查询分类信息（添加菜品/套餐页面选择分类时使用）
     * @param category type = 1：菜品分类 type = 2：套餐分类
     * @return 分类信息集合
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        // 根据 type 查询分类信息
        // 添加条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        // 添加排序规则
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);
    }


}
