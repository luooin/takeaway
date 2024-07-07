package edu.qust.reggie.dto;

import edu.qust.reggie.entity.Dish;
import edu.qust.reggie.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @version 1.0
 * @description 用于新增菜品的 DTO 对象
 */

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
