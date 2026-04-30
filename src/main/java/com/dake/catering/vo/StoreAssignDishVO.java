package com.dake.catering.vo;

import lombok.Data;

/**
 * 开发者：wanxiubin
 * 文件作用：用于门店分配菜单功能中，承载某个分类下单个菜品的分配勾选信息。
 */
@Data
public class StoreAssignDishVO {

    /**
     * 菜品ID
     */
    private Long dishId;

    /**
     * 菜品名称
     */
    private String dishName;

    /**
     * 菜品图片地址
     */
    private String dishImg;

    /**
     * 该菜品是否已分配给当前门店（true 表示已分配/已勾选）
     */
    private Boolean selected;

}

