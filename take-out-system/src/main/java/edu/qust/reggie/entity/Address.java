package edu.qust.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * @version 1.0
 * @description 收货地址管理（地址簿） - 实体类
 */
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /** 用户id */
    private Long userId;

    /** 收货人 */
    private String consignee;

    /** 手机号 */
    private String phone;

    /** 性别 0 女 1 男 */
    private String sex;

    /** 省级区划编号 */
    private String provinceCode;

    /** 省级名称 */
    private String provinceName;

    /** 市级区划编号 */
    private String cityCode;

    /** 市级名称 */
    private String cityName;

    /** 区级区划编号*/
    private String districtCode;

    /** 区级名称 */
    private String districtName;

    /** 详细地址 */
    private String detail;

    /** 标签 */
    private String label;

    /** 是否默认 0 否 1是 */
    private Integer isDefault;

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
