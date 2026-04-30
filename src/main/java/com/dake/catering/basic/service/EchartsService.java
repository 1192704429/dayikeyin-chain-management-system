package com.dake.catering.basic.service;

import com.dake.catering.vo.EchartsDataVO;

import java.util.List;

public interface EchartsService {

    /**
     * 订单状态分布统计
     */
    List<EchartsDataVO> getOrderStatusAnalysis(String startTime, String endTime, Integer orderStatus);

    /**
     * 支付方式分布统计
     */
    List<EchartsDataVO> getPayTypeAnalysis(String startTime, String endTime, Integer payType);

    /**
     * 每日订单金额趋势
     */
    List<EchartsDataVO> getDailyOrderAmount(String startTime, String endTime);

    /**
     * 系统用户角色分布
     */
    List<EchartsDataVO> getUserRoleAnalysis(Integer status);

    /**
     * 门店菜品销量统计
     */
    List<EchartsDataVO> getStoreDishSaleAnalysis(Long storeId, String startTime, String endTime);

    /**
     * 门店订单状态分布
     */
    List<EchartsDataVO> getStoreOrderStatusAnalysis(Long storeId, String startTime, String endTime);

    /**
     * 当前门店每日收入统计（按日期汇总订单实付金额）
     */
    List<EchartsDataVO> getStoreDishStockAnalysis(Long storeId, String startTime, String endTime);

    /**
     * 门店会员消费统计
     */
    List<EchartsDataVO> getStoreMemberConsumeAnalysis(Long registerStoreId, Integer isVip);

    /**
     * 所有门店营业额统计（管理员查看）
     */
    List<EchartsDataVO> getAllStoresRevenueAnalysis(String startTime, String endTime);

    /**
     * 所有门店订单分布状态统计（管理员查看）
     */
    List<EchartsDataVO> getAllStoresOrderStatusAnalysis(String startTime, String endTime);

}
