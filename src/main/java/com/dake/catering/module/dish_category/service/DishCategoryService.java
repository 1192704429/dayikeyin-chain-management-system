package com.dake.catering.module.dish_category.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.dish_category.entity.DishCategory;

import java.util.List;

/**
 * @author wuming
 * @description: 菜品分类Service接口
 */
public interface DishCategoryService extends IService<DishCategory> {

    /**
     * 分页查询
     */
    IPage<DishCategory> pageQuery(Integer current, Integer size, DishCategory entity);

    /**
     * 列表查询（不分页）
     */
    List<DishCategory> listQuery(DishCategory entity);

    /**
     * 根据ID查询
     */
    DishCategory getById(Long id);

}
