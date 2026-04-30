package com.dake.catering.module.order_item.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.order_item.entity.OrderItem;
import com.dake.catering.module.order_item.mapper.OrderItemMapper;
import com.dake.catering.module.order_item.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 订单明细Service实现类
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public IPage<OrderItem> pageQuery(Integer current, Integer size, OrderItem entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<OrderItem> records = orderItemMapper.pageQuery(entity, offset, limit);
        Long total = orderItemMapper.countQuery(entity);

        Page<OrderItem> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<OrderItem> listQuery(OrderItem entity) {
        return orderItemMapper.listQuery(entity);
    }

    @Override
    public OrderItem getById(Long id) {
        return orderItemMapper.getById(id);
    }

    /**
     * 下拉数据：dish_info
     */
    @Override
    public List<SelectVO> listDishInfo() {
        return orderItemMapper.listDishInfo();
    }

    /**
     * 下拉数据：order_main
     */
    @Override
    public List<SelectVO> listOrderMain() {
        return orderItemMapper.listOrderMain();
    }

}
