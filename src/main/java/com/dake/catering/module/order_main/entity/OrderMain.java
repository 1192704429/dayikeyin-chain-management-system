package com.dake.catering.module.order_main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.dake.catering.module.order_item.entity.OrderItem;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author wuming
 * @description: 订单主实体类
 */
@Data
@TableName("order_main")
public class OrderMain implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 订单编号 */
    private String orderNo;
    /** 所属门店 */
    private Long storeId;
    /** 下单店员 */
    private Long clerkId;
    /** 消费会员 */
    private Long memberId;
    /** 桌号 */
    private String tableNo;
    /** 订单状态 */
    private Integer orderStatus;
    /** 订单金额 */
    private BigDecimal orderAmount;
    /** 优惠金额 */
    private BigDecimal discountAmount;
    /** 抵扣积分 */
    private Integer pointsDeduct;
    /** 积分抵扣金额 */
    private BigDecimal pointsDeductAmount;
    /** 实付金额 */
    private BigDecimal actualPay;
    /** 支付方式 */
    private Integer payType;
    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;
    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completeTime;
    /** 取消时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime cancelTime;
    /** 取消原因 */
    private String cancelReason;
    /** 订单备注 */
    private String remark;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 门店名称 */
    @TableField(exist = false)
    private String storeName;
    /** 店员名称 */
    @TableField(exist = false)
    private String realName;
    /** 会员名称 */
    @TableField(exist = false)
    private String realName2;

    // 范围查询字段
    /** 支付时间 开始 */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTimeStart;
    /** 支付时间 结束 */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTimeEnd;

    /** 订单明细列表 */
    @TableField(exist = false)
    private List<OrderItem> orderItemList;

}
