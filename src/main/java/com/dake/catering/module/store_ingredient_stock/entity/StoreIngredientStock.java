package com.dake.catering.module.store_ingredient_stock.entity;

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
 * @description: 门店原材料库存实体类
 */
@Data
@TableName("store_ingredient_stock")
public class StoreIngredientStock implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 门店ID */
    private Long storeId;
    /** 原材料ID */
    private Long ingredientId;
    /** 库存数量 */
    private BigDecimal stockQuantity;
    /** 预警数量 */
    private BigDecimal warningQuantity;
    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 门店名称 */
    @TableField(exist = false)
    private String storeName;
    /** 原材料名称 */
    @TableField(exist = false)
    private String ingredientName;
    /** 单位 */
    @TableField(exist = false)
    private String unit;

}
