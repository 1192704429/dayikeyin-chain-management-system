package com.dake.catering.module.store_dish_category.entity;

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
 * @description: 门店菜品分类关联实体类
 */
@Data
@TableName("store_dish_category")
public class StoreDishCategory implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 门店ID */
    private Long storeId;
    /** 分类ID */
    private Long categoryId;
    /** 门店内排序值 */
    private Integer sort;
    /** 分配状态 */
    private Integer status;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /** 门店名称 */
    @TableField(exist = false)
    private String storeName;
    /** 分类名称 */
    @TableField(exist = false)
    private String categoryName;


}
