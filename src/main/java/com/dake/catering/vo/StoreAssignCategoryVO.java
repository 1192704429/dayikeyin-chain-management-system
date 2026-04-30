package com.dake.catering.vo;

import lombok.Data;

import java.util.List;

/**
 * 开发者：wanxiubin
 * 文件作用：用于门店分配菜单功能中，承载单个分类及其下菜品的分配勾选信息。
 */
@Data
public class StoreAssignCategoryVO {

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 当前分类是否已分配给门店（true 表示已分配/已勾选）
     */
    private Boolean selected;

    /**
     * 分类下的菜品分配列表
     */
    private List<StoreAssignDishVO> dishList;

}

