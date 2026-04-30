package com.dake.catering.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author
 * @description: 根据门店ID查询门店菜品及分类信息 VO
 */
@Data
public class StoreDishInfoVO {

    /** 门店ID */
    private Long storeId;

    /** 门店名称 */
    private String storeName;

    /** 菜品ID */
    private Long dishId;

    /** 菜品名称 */
    private String dishName;

    /** 门店售价 */
    private BigDecimal salePrice;

    /** 上架状态 */
    private Integer saleStatus;

    /** 库存数量 */
    private Integer stockCount;

    /** 菜品图片 */
    private String dishImg;

    /** 口味 */
    private Integer flavor;

    /** 是否辣 */
    private Integer isSpicy;

    /** 是否素食 */
    private Integer isVegetarian;

    /** 分类ID */
    private Long categoryId;

    /** 分类名称 */
    private String categoryName;

}

