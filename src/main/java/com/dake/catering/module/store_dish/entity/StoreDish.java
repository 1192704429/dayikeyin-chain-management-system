package com.dake.catering.module.store_dish.entity;

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
 * @description: 门店菜品关联实体类
 */
@Data
@TableName("store_dish")
public class StoreDish implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 门店ID */
    private Long storeId;
    /** 菜品ID */
    private Long dishId;
    /** 上架状态 */
    private Integer saleStatus;
    /** 门店售价 */
    private BigDecimal salePrice;
    /** 库存数量 */
    private Integer stockCount;
    /** 库存预警值 */
    private Integer warningCount;
    /** 门店内排序值 */
    private Integer sort;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 门店名称 */
    @TableField(exist = false)
    private String storeName;
    /** 菜品名称 */
    @TableField(exist = false)
    private String dishName;


}
