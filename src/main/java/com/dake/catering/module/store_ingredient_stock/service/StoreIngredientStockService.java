package com.dake.catering.module.store_ingredient_stock.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.store_ingredient_stock.entity.StoreIngredientStock;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wuming
 * @description: 门店原材料库存Service接口
 */
public interface StoreIngredientStockService extends IService<StoreIngredientStock> {

    /**
     * 分页查询
     */
    IPage<StoreIngredientStock> pageQuery(Integer current, Integer size, StoreIngredientStock entity);

    /**
     * 列表查询（不分页）
     */
    List<StoreIngredientStock> listQuery(StoreIngredientStock entity);

    /**
     * 根据ID查询
     */
    StoreIngredientStock getById(Long id);

    /**
     * 根据门店ID和原材料ID查询
     */
    StoreIngredientStock getByStoreIdAndIngredientId(Long storeId, Long ingredientId);

    /**
     * 调整库存（增加或减少）
     */
    boolean adjustStock(Long storeId, Long ingredientId, BigDecimal quantity);

}
