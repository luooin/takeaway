package edu.qust.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * @version 1.0
 * @description 菜品表 - 实体类
 */
@Data
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 菜品名称 */
    private String name;

    /** 菜品分类id */
    private Long categoryId;

    /** 菜品价格 */
    private BigDecimal price;

    /** 商品码 */
    private String code;

    /** 图片 */
    private String image;

    /** 描述信息 */
    private String description;

    /** 0 停售 1 起售 */
    private Integer status;

    /** 顺序  */
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
