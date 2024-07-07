package edu.qust.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.qust.reggie.entity.Category;

/**
 * 
 * @version 1.0
 * @description 分类模块 - 业务层接口
 */

public interface ICategoryService extends IService<Category> {

    /**
     * 根据 id 删除分类信息，如果有菜品或者套餐关联了该分类就不做处理
     * @param id 分类 id
     */
    void remove(long id);
}
