package com.dake.catering.module.store_dish_category.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.store_dish_category.entity.StoreDishCategory;
import com.dake.catering.module.store_dish_category.mapper.StoreDishCategoryMapper;
import com.dake.catering.module.store_dish_category.service.StoreDishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 门店菜品分类关联Service实现类
 */
@Service
public class StoreDishCategoryServiceImpl extends ServiceImpl<StoreDishCategoryMapper, StoreDishCategory> implements StoreDishCategoryService {

    @Autowired
    private StoreDishCategoryMapper storeDishCategoryMapper;

    @Override
    public IPage<StoreDishCategory> pageQuery(Integer current, Integer size, StoreDishCategory entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<StoreDishCategory> records = storeDishCategoryMapper.pageQuery(entity, offset, limit);
        Long total = storeDishCategoryMapper.countQuery(entity);

        Page<StoreDishCategory> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<StoreDishCategory> listQuery(StoreDishCategory entity) {
        return storeDishCategoryMapper.listQuery(entity);
    }

    @Override
    public StoreDishCategory getById(Long id) {
        return storeDishCategoryMapper.getById(id);
    }

    /**
     * 下拉数据：dish_category
     */
    @Override
    public List<SelectVO> listDishCategory() {
        return storeDishCategoryMapper.listDishCategory();
    }

    /**
     * 下拉数据：sys_store
     */
    @Override
    public List<SelectVO> listSysStore() {
        return storeDishCategoryMapper.listSysStore();
    }

}
