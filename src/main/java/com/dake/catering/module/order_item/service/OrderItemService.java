package com.dake.catering.module.order_item.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.order_item.entity.OrderItem;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 订单明细Service接口
 */
public interface OrderItemService extends IService<OrderItem> {

    /**
     * 分页查询
     */
    IPage<OrderItem> pageQuery(Integer current, Integer size, OrderItem entity);

    /**
     * 列表查询（不分页）
     */
    List<OrderItem> listQuery(OrderItem entity);

    /**
     * 根据ID查询
     */
    OrderItem getById(Long id);

    /**
     * 下拉数据：dish_info
     */
    List<SelectVO> listDishInfo();

    /**
     * 下拉数据：order_main
     */
    List<SelectVO> listOrderMain();

}
