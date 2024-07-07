package edu.qust.reggie.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.qust.reggie.entity.Category;
import org.springframework.stereotype.Repository;

/**
 * 
 * @version 1.0
 * @description 分类模块 - 持久层
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
}
