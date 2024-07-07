package edu.qust.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @version 1.0
 * @description 菜品口味表 - 实体类
 */
@Data
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 菜品id */
    private Long dishId;

    /** 口味名称 */
    private String name;

    /** 口味数据list */
    private String value;

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
