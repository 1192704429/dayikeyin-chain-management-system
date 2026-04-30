package com.dake.catering.basic.mapper;

import com.dake.catering.vo.EchartsDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EchartsMapper {

    /**
     * 订单状态分布统计
     */
    List<EchartsDataVO> getOrderStatusAnalysis(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("orderStatus") Integer orderStatus);

    /**
     * 支付方式分布统计
     */
    List<EchartsDataVO> getPayTypeAnalysis(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("payType") Integer payType);

    /**
     * 每日订单金额趋势
     */
    List<EchartsDataVO> getDailyOrderAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 系统用户角色分布
     */
    List<EchartsDataVO> getUserRoleAnalysis(@Param("status") Integer status);

    /**
     * 门店菜品销量统计
     */
    List<EchartsDataVO> getStoreDishSaleAnalysis(@Param("storeId") Long storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 门店订单状态分布
     */
    List<EchartsDataVO> getStoreOrderStatusAnalysis(@Param("storeId") Long storeId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 当前门店每日收入统计（按日期汇总订单实付金额）
     */
    List<EchartsDataVO> getStoreDishStockAnalysis(@Param("storeId") Long storeId,
                                                  @Param("startTime") String startTime,
                                                  @Param("endTime") String endTime);

    /**
     * 门店会员消费统计
     */
    List<EchartsDataVO> getStoreMemberConsumeAnalysis(@Param("registerStoreId") Long registerStoreId, @Param("isVip") Integer isVip);

    /**
     * 所有门店营业额统计（管理员查看）
     */
    List<EchartsDataVO> getAllStoresRevenueAnalysis(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 所有门店订单分布状态统计（管理员查看）
     */
    List<EchartsDataVO> getAllStoresOrderStatusAnalysis(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
