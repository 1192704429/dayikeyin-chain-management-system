package com.dake.catering.module.dish_info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.dish_info.entity.DishInfo;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 菜品信息Service接口
 */
public interface DishInfoService extends IService<DishInfo> {

    /**
     * 分页查询
     */
    IPage<DishInfo> pageQuery(Integer current, Integer size, DishInfo entity);

    /**
     * 列表查询（不分页）
     */
    List<DishInfo> listQuery(DishInfo entity);

    /**
     * 根据ID查询
     */
    DishInfo getById(Long id);

    /**
     * 下拉数据：dish_category
     */
    List<SelectVO> listDishCategory();

}
