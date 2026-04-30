package com.dake.catering.module.order_main.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.order_main.entity.OrderMain;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 订单主Service接口
 */
public interface OrderMainService extends IService<OrderMain> {

    /**
     * 分页查询
     */
    IPage<OrderMain> pageQuery(Integer current, Integer size, OrderMain entity);

    /**
     * 列表查询（不分页）
     */
    List<OrderMain> listQuery(OrderMain entity);

    /**
     * 根据ID查询
     */
    OrderMain getById(Long id);

    /**
     * 下拉数据：user_info
     */
    List<SelectVO> listUserInfo();

    /**
     * 下拉数据：sys_store
     */
    List<SelectVO> listSysStore();

    /**
     * 下拉数据：member_info
     */
    List<SelectVO> listMemberInfo();

    /**
     * 门店收银：当前门店会员结账下单（包含订单主信息与明细）
     */
    OrderMain checkout(OrderMain orderMain);

    /**
     * 查询指定门店下所有“未付款”订单列表
     */
    List<OrderMain> listUnpaidByStoreId(Long storeId);

    /**
     * 门店收银：对指定订单进行支付结算
     */
    OrderMain pay(Long orderId,
                  Integer payType,
                  Long storeId,
                  Long memberId,
                  Integer pointsToDeduct,
                  Long operatorId,
                  String tableNo);

    /**
     * 门店收银：取消订单（仅待付款状态允许取消）
     */
    OrderMain cancel(Long orderId, Long storeId, String cancelReason);

}
