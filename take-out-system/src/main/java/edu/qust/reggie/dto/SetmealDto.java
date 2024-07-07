package edu.qust.reggie.dto;


import edu.qust.reggie.entity.Setmeal;
import edu.qust.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;
/**
 * 
 * @version 1.0
 * @description 用于新增套餐的 DTO 对象
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
