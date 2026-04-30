package com.dake.catering.module.sys_store.entity;

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
 * @description: 门店信息实体类
 */
@Data
@TableName("sys_store")
public class SysStore implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 门店名称 */
    private String storeName;
    /** 门店编号 */
    private String storeNo;
    /** 门店地址 */
    private String address;
    /** 联系电话 */
    private String contactPhone;
    /** 营业状态 */
    private Integer businessStatus;
    /** 营业时间 */
    private String businessHours;
    /** 门店封面 */
    private String coverImg;
    /** 门店环境图片 */
    private String storeImg;
    /** 门店描述 */
    private String description;
    /** 门店店长 */
    private Long managerId;
    /** 门店面积 */
    private BigDecimal area;
    /** 员工数量 */
    private Integer employeeCount;
    /** 注册资金 */
    private BigDecimal registerCapital;
    /** 营业执照 */
    private String businessLicense;
    /** 税务登记号 */
    private String taxRegisterNo;
    /** 停车位数量 */
    private Integer parkingSpace;
    /** 门店状态 */
    private Integer status;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 真实姓名 */
    @TableField(exist = false)
    private String realName;


}
