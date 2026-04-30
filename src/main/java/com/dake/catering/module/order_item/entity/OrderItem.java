package com.dake.catering.module.order_item.entity;

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
 * @description: 订单明细实体类
 */
@Data
@TableName("order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 订单ID */
    private Long orderId;
    /** 菜品ID */
    private Long dishId;
    /** 菜品名称 */
    private String dishName;
    /** 菜品口味 */
    private Integer flavor;
    /** 单价 */
    private BigDecimal unitPrice;
    /** 数量 */
    private Integer quantity;
    /** 小计金额 */
    private BigDecimal totalPrice;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 订单编号 */
    @TableField(exist = false)
    private String orderNo;
    /** 菜品名称 */
    @TableField(exist = false)
    private String dishName2;
    /** 菜品图片 */
    @TableField(exist = false)
    private String dishImg;


}
