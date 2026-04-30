package com.dake.catering.module.ingredient.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.ingredient.entity.Ingredient;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 原材料Service接口
 */
public interface IngredientService extends IService<Ingredient> {

    /**
     * 分页查询
     */
    IPage<Ingredient> pageQuery(Integer current, Integer size, Ingredient entity);

    /**
     * 列表查询（不分页）
     */
    List<Ingredient> listQuery(Ingredient entity);

    /**
     * 根据ID查询
     */
    Ingredient getById(Long id);

    /**
     * 下拉数据：ingredient
     */
    List<SelectVO> listIngredient();

}
