package com.dake.catering.module.store_dish.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.store_dish.entity.StoreDish;
import com.dake.catering.vo.SelectVO;
import com.dake.catering.vo.StoreDishInfoVO;
import com.dake.catering.vo.StoreAssignCategoryVO;
import com.dake.catering.vo.StoreDishAssignRequest;

import java.util.List;

/**
 * @author wuming
 * @description: 门店菜品关联Service接口
 */
public interface StoreDishService extends IService<StoreDish> {

    /**
     * 分页查询
     */
    IPage<StoreDish> pageQuery(Integer current, Integer size, StoreDish entity);

    /**
     * 列表查询（不分页）
     */
    List<StoreDish> listQuery(StoreDish entity);

    /**
     * 根据ID查询
     */
    StoreDish getById(Long id);

    /**
     * 下拉数据：dish_info
     */
    List<SelectVO> listDishInfo();

    /**
     * 下拉数据：sys_store
     */
    List<SelectVO> listSysStore();

    /**
     * 根据门店ID查询门店菜品及分类信息
     */
    List<StoreDishInfoVO> listStoreDishWithCategoryByStoreId(Long storeId);

    /**
     * 根据门店ID，查询可分配的分类及菜品信息以及当前勾选情况
     */
    List<StoreAssignCategoryVO> listAssignDataByStoreId(Long storeId);

    /**
     * 保存门店分配菜单及菜品结果
     */
    void saveStoreDishAssign(StoreDishAssignRequest request);

    /**
     * 根据当前门店和菜品ID，更新门店菜品的部分字段（售价 / 上架状态 / 库存数量）
     *
     * @param storeId    门店ID
     * @param dishId     菜品ID
     * @param salePrice  门店售价
     * @param saleStatus 上下架状态
     * @param stockCount 库存数量
     * @return 是否更新成功
     */
    boolean updateStoreDishFields(Long storeId, Long dishId,
                                  java.math.BigDecimal salePrice,
                                  Integer saleStatus,
                                  Integer stockCount);

}
