package com.dake.catering.module.store_dish_category.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.store_dish_category.entity.StoreDishCategory;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 门店菜品分类关联Service接口
 */
public interface StoreDishCategoryService extends IService<StoreDishCategory> {

    /**
     * 分页查询
     */
    IPage<StoreDishCategory> pageQuery(Integer current, Integer size, StoreDishCategory entity);

    /**
     * 列表查询（不分页）
     */
    List<StoreDishCategory> listQuery(StoreDishCategory entity);

    /**
     * 根据ID查询
     */
    StoreDishCategory getById(Long id);

    /**
     * 下拉数据：dish_category
     */
    List<SelectVO> listDishCategory();

    /**
     * 下拉数据：sys_store
     */
    List<SelectVO> listSysStore();

}
