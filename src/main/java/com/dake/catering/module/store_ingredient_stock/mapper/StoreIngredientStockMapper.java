package com.dake.catering.module.store_ingredient_stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.store_ingredient_stock.entity.StoreIngredientStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuming
 * @description: 门店原材料库存Mapper接口
 */
@Mapper
public interface StoreIngredientStockMapper extends BaseMapper<StoreIngredientStock> {

    /**
     * 分页查询
     */
    List<StoreIngredientStock> pageQuery(@Param("entity") StoreIngredientStock entity,
                                         @Param("offset") Long offset,
                                         @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") StoreIngredientStock entity);

    /**
     * 列表查询（不分页）
     */
    List<StoreIngredientStock> listQuery(@Param("entity") StoreIngredientStock entity);

    /**
     * 根据ID查询
     */
    StoreIngredientStock getById(@Param("id") Long id);

    /**
     * 根据门店ID和原材料ID查询
     */
    StoreIngredientStock getByStoreIdAndIngredientId(@Param("storeId") Long storeId,
                                                      @Param("ingredientId") Long ingredientId);

}
