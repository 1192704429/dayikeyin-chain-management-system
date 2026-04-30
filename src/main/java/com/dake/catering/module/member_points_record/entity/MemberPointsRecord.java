package com.dake.catering.module.member_points_record.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wuming
 * @description: 会员积分记录实体类
 */
@Data
@TableName("member_points_record")
public class MemberPointsRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 会员ID */
    private Long memberId;
    /** 门店ID */
    private Long storeId;
    /** 关联订单 */
    private Long orderId;
    /** 积分类型 */
    private Integer pointsType;
    /** 积分数量 */
    private Integer pointsNum;
    /** 业务类型 */
    private Integer bizType;
    /** 操作人 */
    private Long operatorId;
    /** 备注 */
    private String remark;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 真实姓名 */
    @TableField(exist = false)
    private String realName;
    /** 门店名称 */
    @TableField(exist = false)
    private String storeName;
    /** 订单编号 */
    @TableField(exist = false)
    private String orderNo;
    /** 真实姓名 */
    @TableField(exist = false)
    private String realName2;


}
