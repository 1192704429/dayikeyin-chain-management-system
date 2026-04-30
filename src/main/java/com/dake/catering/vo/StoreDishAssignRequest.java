package com.dake.catering.vo;

import lombok.Data;

import java.util.List;

/**
 * 开发者：wanxiubin
 * 文件作用：门店分配菜单及菜品保存请求体，承载前端弹框勾选后的提交数据。
 */
@Data
public class StoreDishAssignRequest {

    /**
     * 门店ID
     */
    private Long storeId;

    /**
     * 选中的分类ID集合
     */
    private List<Long> categoryIdList;

    /**
     * 选中的菜品ID集合
     */
    private List<Long> dishIdList;

}

