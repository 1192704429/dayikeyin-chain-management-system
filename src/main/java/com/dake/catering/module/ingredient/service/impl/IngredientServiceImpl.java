package com.dake.catering.module.ingredient.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.ingredient.entity.Ingredient;
import com.dake.catering.module.ingredient.mapper.IngredientMapper;
import com.dake.catering.module.ingredient.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 原材料Service实现类
 */
@Service
public class IngredientServiceImpl extends ServiceImpl<IngredientMapper, Ingredient> implements IngredientService {

    @Autowired
    private IngredientMapper ingredientMapper;

    @Override
    public IPage<Ingredient> pageQuery(Integer current, Integer size, Ingredient entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<Ingredient> records = ingredientMapper.pageQuery(entity, offset, limit);
        Long total = ingredientMapper.countQuery(entity);

        Page<Ingredient> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<Ingredient> listQuery(Ingredient entity) {
        return ingredientMapper.listQuery(entity);
    }

    @Override
    public Ingredient getById(Long id) {
        return ingredientMapper.getById(id);
    }

    /**
     * 下拉数据：ingredient
     */
    @Override
    public List<SelectVO> listIngredient() {
        return ingredientMapper.listIngredient();
    }

}
