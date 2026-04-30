package com.dake.catering.module.member_info.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuming
 * @description: 会员信息实体类
 */
@Data
@TableName("member_info")
public class MemberInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 手机号 */
    private String phone;
    /** 真实姓名 */
    private String realName;
    /** 性别 */
    private Integer sex;
    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /** 注册门店 */
    private Long registerStoreId;
    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;
    /** 总积分 */
    private Integer totalPoints;
    /** 累计消费金额 */
    private BigDecimal consumeAmount;
    /** 消费次数 */
    private Integer consumeCount;
    /** 最后消费时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastConsumeTime;
    /** 偏好口味 */
    private Integer preferredFlavor;
    /** 是否VIP */
    private Integer isVip;
    /** 会员备注 */
    private String remark;
    /** 会员状态 */
    private Integer status;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 门店名称 */
    @TableField(exist = false)
    private String storeName;


}
