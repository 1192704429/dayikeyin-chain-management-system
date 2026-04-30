package com.dake.catering.module.dish_category.entity;

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
 * @description: 菜品分类实体类
 */
@Data
@TableName("dish_category")
public class DishCategory implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 分类名称 */
    private String categoryName;
    /** 分类编号 */
    private String categoryNo;
    /** 排序值 */
    private Integer sort;
    /** 分类状态 */
    private Integer status;
    /** 分类图片 */
    private String categoryImg;
    /** 分类描述 */
    private String description;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
