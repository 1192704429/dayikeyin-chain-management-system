package com.dake.catering.module.dish_info.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.dish_info.entity.DishInfo;
import com.dake.catering.module.dish_info.mapper.DishInfoMapper;
import com.dake.catering.module.dish_info.service.DishInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 菜品信息Service实现类
 */
@Service
public class DishInfoServiceImpl extends ServiceImpl<DishInfoMapper, DishInfo> implements DishInfoService {

    @Autowired
    private DishInfoMapper dishInfoMapper;

    @Override
    public IPage<DishInfo> pageQuery(Integer current, Integer size, DishInfo entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<DishInfo> records = dishInfoMapper.pageQuery(entity, offset, limit);
        Long total = dishInfoMapper.countQuery(entity);

        Page<DishInfo> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<DishInfo> listQuery(DishInfo entity) {
        return dishInfoMapper.listQuery(entity);
    }

    @Override
    public DishInfo getById(Long id) {
        return dishInfoMapper.getById(id);
    }

    /**
     * 下拉数据：dish_category
     */
    @Override
    public List<SelectVO> listDishCategory() {
        return dishInfoMapper.listDishCategory();
    }

}
