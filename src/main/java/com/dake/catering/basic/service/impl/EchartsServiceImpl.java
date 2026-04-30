package com.dake.catering.basic.service.impl;

import com.dake.catering.basic.mapper.EchartsMapper;
import com.dake.catering.basic.service.EchartsService;
import com.dake.catering.vo.EchartsDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EchartsServiceImpl implements EchartsService {

    @Autowired
    private EchartsMapper echartsMapper;

    /**
     * 订单状态分布统计
     */
    @Override
    public List<EchartsDataVO> getOrderStatusAnalysis(String startTime, String endTime, Integer orderStatus) {
        return echartsMapper.getOrderStatusAnalysis(startTime, endTime, orderStatus);
    }

    /**
     * 支付方式分布统计
     */
    @Override
    public List<EchartsDataVO> getPayTypeAnalysis(String startTime, String endTime, Integer payType) {
        return echartsMapper.getPayTypeAnalysis(startTime, endTime, payType);
    }

    /**
     * 每日订单金额趋势
     */
    @Override
    public List<EchartsDataVO> getDailyOrderAmount(String startTime, String endTime) {
        return echartsMapper.getDailyOrderAmount(startTime, endTime);
    }

    /**
     * 系统用户角色分布
     */
    @Override
    public List<EchartsDataVO> getUserRoleAnalysis(Integer status) {
        return echartsMapper.getUserRoleAnalysis(status);
    }

    /**
     * 门店菜品销量统计
     */
    @Override
    public List<EchartsDataVO> getStoreDishSaleAnalysis(Long storeId, String startTime, String endTime) {
        return echartsMapper.getStoreDishSaleAnalysis(storeId, startTime, endTime);
    }

    /**
     * 门店订单状态分布
     */
    @Override
    public List<EchartsDataVO> getStoreOrderStatusAnalysis(Long storeId, String startTime, String endTime) {
        return echartsMapper.getStoreOrderStatusAnalysis(storeId, startTime, endTime);
    }

    /**
     * 当前门店每日收入统计（按日期汇总订单实付金额）
     */
    @Override
    public List<EchartsDataVO> getStoreDishStockAnalysis(Long storeId, String startTime, String endTime) {
        return echartsMapper.getStoreDishStockAnalysis(storeId, startTime, endTime);
    }

    /**
     * 门店会员消费统计
     */
    @Override
    public List<EchartsDataVO> getStoreMemberConsumeAnalysis(Long registerStoreId, Integer isVip) {
        return echartsMapper.getStoreMemberConsumeAnalysis(registerStoreId, isVip);
    }

    /**
     * 所有门店营业额统计（管理员查看）
     */
    @Override
    public List<EchartsDataVO> getAllStoresRevenueAnalysis(String startTime, String endTime) {
        return echartsMapper.getAllStoresRevenueAnalysis(startTime, endTime);
    }

    /**
     * 所有门店订单分布状态统计（管理员查看）
     */
    @Override
    public List<EchartsDataVO> getAllStoresOrderStatusAnalysis(String startTime, String endTime) {
        return echartsMapper.getAllStoresOrderStatusAnalysis(startTime, endTime);
    }

}
