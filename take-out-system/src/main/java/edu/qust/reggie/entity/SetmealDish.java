package edu.qust.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * @version 1.0
 * @description 套餐与菜品关系的实体类
 */
@Data
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 套餐id */
    private Long setmealId;

    /** 菜品id */
    private Long dishId;

     /** 菜品名称 （冗余字段） */
    private String name;

    /** 菜品原价 */
    private BigDecimal price;

    /** 份数 */
    private Integer copies;

    /** 排序 */
    private Integer sort;

    /** 是否删除 */
    private Integer isDeleted;

    /** 日志相关公共字段（创建人、创建时间、修改人、修改时间） 设置 MP 的公共字段自动填充 */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
