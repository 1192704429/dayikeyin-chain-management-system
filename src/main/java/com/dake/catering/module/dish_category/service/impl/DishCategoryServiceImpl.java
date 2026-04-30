package com.dake.catering.module.dish_category.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.dish_category.entity.DishCategory;
import com.dake.catering.module.dish_category.mapper.DishCategoryMapper;
import com.dake.catering.module.dish_category.service.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuming
 * @description: 菜品分类Service实现类
 */
@Service
public class DishCategoryServiceImpl extends ServiceImpl<DishCategoryMapper, DishCategory> implements DishCategoryService {

    @Autowired
    private DishCategoryMapper dishCategoryMapper;

    @Override
    public IPage<DishCategory> pageQuery(Integer current, Integer size, DishCategory entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<DishCategory> records = dishCategoryMapper.pageQuery(entity, offset, limit);
        Long total = dishCategoryMapper.countQuery(entity);

        Page<DishCategory> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<DishCategory> listQuery(DishCategory entity) {
        return dishCategoryMapper.listQuery(entity);
    }

    @Override
    public DishCategory getById(Long id) {
        return dishCategoryMapper.getById(id);
    }

}
