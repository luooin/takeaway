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
 * @description 套餐表 - 实体类
 */

@Data
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 分类id */
    private Long categoryId;

    /** 套餐名称 */
    private String name;

    /** 套餐价格 */
    private BigDecimal price;

    /** 套餐价格状态 0:停用 1:启用 */
    private Integer status;

    /** 编码 */
    private String code;

    /** 描述信息 */
    private String description;

    /** 图片 */
    private String image;

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