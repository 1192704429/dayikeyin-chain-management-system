package com.dake.catering.module.ingredient.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author wuming
 * @description: 原材料实体类
 */
@Data
@TableName("ingredient")
public class Ingredient implements Serializable {

    private static final long serialVersionUID=1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 原材料名称 */
    private String ingredientName;
    /** 单位（斤/个/包/升等） */
    private String unit;
    /** 描述 */
    private String description;
    /** 状态[0:禁用,1:启用] */
    private Integer status;
    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
