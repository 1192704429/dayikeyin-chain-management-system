package com.dake.catering.module.user_info.entity;

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
 * @description: 系统用户实体类
 */
@Data
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 登录账号 */
    private String username;
    /** 登录密码 */
    private String password;
    /** 真实姓名 */
    private String realName;
    /** 用户角色 */
    private String roleType;
    /** 手机号 */
    private String phone;
    /** 用户头像 */
    private String avatar;
    /** 性别 */
    private Integer sex;
    /** 年龄 */
    private Integer age;
    /** 身份证号 */
    private String idCard;
    /** 电子邮箱 */
    private String email;
    /** 入职时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime entryTime;
    /** 职位名称 */
    private String position;
    /** 薪资 */
    private BigDecimal salary;
    /** 账号状态 */
    private Integer status;
    /** 所属门店 */
    private Long storeId;
    /** 紧急联系人 */
    private String emergencyContact;
    /** 紧急联系电话 */
    private String emergencyPhone;
    /** 居住地址 */
    private String address;
    /** 银行卡号 */
    private String bankCard;
    /** 开户银行 */
    private String bankName;
    /** 学历 */
    private Integer education;
    /** 政治面貌 */
    private Integer politicalStatus;
    /** 婚姻状况 */
    private Integer maritalStatus;
    /** 健康状况 */
    private String healthStatus;
    /** 备注信息 */
    private String remark;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 门店名称 */
    @TableField(exist = false)
    private String storeName;


}
