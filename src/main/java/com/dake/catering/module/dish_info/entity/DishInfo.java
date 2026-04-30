package com.dake.catering.module.dish_info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;

/**
 * @author wuming
 * @description: 菜品信息实体类
 */
@Data
@TableName("dish_info")
public class DishInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 菜品名称 */
    private String dishName;
    /** 所属分类 */
    private Long categoryId;
    /** 菜品价格 */
    private BigDecimal price;
    /** 成本价格 */
    private BigDecimal costPrice;
    /** 优惠价格 */
    private BigDecimal discountPrice;
    /** 菜品图片 */
    private String dishImg;
    /** 食材配料 */
    private String material;
    /** 口味 */
    private Integer flavor;
    /** 制作时长(分钟) */
    private Integer cookingTime;
    /** 热量(千卡) */
    private BigDecimal calorie;
    /** 是否辣 */
    private Integer isSpicy;
    /** 是否素食 */
    private Integer isVegetarian;
    /** 菜品描述 */
    private String description;
    /** 菜品状态 */
    private Integer status;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 分类名称 */
    @TableField(exist = false)
    private String categoryName;


}
