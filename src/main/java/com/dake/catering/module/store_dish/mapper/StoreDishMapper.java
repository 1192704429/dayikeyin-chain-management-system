package com.dake.catering.module.store_dish.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.store_dish.entity.StoreDish;
import com.dake.catering.vo.SelectVO;
import com.dake.catering.vo.StoreDishInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wuming
 * @description: 门店菜品关联Mapper接口
 */
@Mapper
public interface StoreDishMapper extends BaseMapper<StoreDish> {

    /**
     * 分页查询
     */
    List<StoreDish> pageQuery(@Param("entity") StoreDish entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") StoreDish entity);

    /**
     * 列表查询（不分页）
     */
    List<StoreDish> listQuery(@Param("entity") StoreDish entity);

    /**
     * 根据ID查询
     */
    StoreDish getById(@Param("id") Long id);

    /**
     * 下拉选项查询
     */
    /**
     * 下拉数据：dish_info
     */
    @Select("select dish_name as label, id as value from dish_info")
    List<SelectVO> listDishInfo();

    /**
     * 下拉数据：sys_store
     */
    @Select("select store_name as label, id as value from sys_store")
    List<SelectVO> listSysStore();

    /**
     * 根据门店ID查询门店菜品及分类信息
     */
    List<StoreDishInfoVO> listStoreDishWithCategoryByStoreId(@Param("storeId") Long storeId);

    /**
     * 根据门店ID和菜品ID，更新门店菜品的部分字段（如售价、上架状态、库存数量）
     */
    int updateFieldsByStoreIdAndDishId(@Param("storeId") Long storeId,
                                       @Param("dishId") Long dishId,
                                       @Param("salePrice") java.math.BigDecimal salePrice,
                                       @Param("saleStatus") Integer saleStatus,
                                       @Param("stockCount") Integer stockCount);

}
