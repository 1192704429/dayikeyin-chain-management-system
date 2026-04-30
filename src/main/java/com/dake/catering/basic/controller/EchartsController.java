package com.dake.catering.basic.controller;

import com.dake.catering.basic.service.EchartsService;
import com.dake.catering.vo.EchartsDataVO;
import com.dake.catering.common.Result;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.module.user_info.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 图表可视化接口
 */
@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private EchartsService echartsService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 订单状态分布统计
     */
    @PostMapping("/getOrderStatusAnalysis")
    public Result getOrderStatusAnalysis(@RequestBody Map<String, Object> params) {
        // 从 RequestBody 中提取参数
        String startTime = Optional.ofNullable(params.get("startTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        String endTime = Optional.ofNullable(params.get("endTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        Integer orderStatus = Optional.ofNullable(params.get("orderStatus"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .map(Integer::valueOf)
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getOrderStatusAnalysis(startTime, endTime, orderStatus);
        return Result.success(data);
    }

    /**
     * 支付方式分布统计
     */
    @PostMapping("/getPayTypeAnalysis")
    public Result getPayTypeAnalysis(@RequestBody Map<String, Object> params) {
        // 从 RequestBody 中提取参数
        String startTime = Optional.ofNullable(params.get("startTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        String endTime = Optional.ofNullable(params.get("endTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        Integer payType = Optional.ofNullable(params.get("payType"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .map(Integer::valueOf)
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getPayTypeAnalysis(startTime, endTime, payType);
        return Result.success(data);
    }

    /**
     * 每日订单金额趋势
     */
    @PostMapping("/getDailyOrderAmount")
    public Result getDailyOrderAmount(@RequestBody Map<String, Object> params) {
        // 从 RequestBody 中提取参数
        String startTime = Optional.ofNullable(params.get("startTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        String endTime = Optional.ofNullable(params.get("endTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getDailyOrderAmount(startTime, endTime);
        return Result.success(data);
    }

    /**
     * 系统用户角色分布
     */
    @PostMapping("/getUserRoleAnalysis")
    public Result getUserRoleAnalysis(@RequestBody Map<String, Object> params) {
        // 从 RequestBody 中提取参数
        Integer status = Optional.ofNullable(params.get("status"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .map(Integer::valueOf)
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getUserRoleAnalysis(status);
        return Result.success(data);
    }

    /**
     * 门店菜品销量统计
     */
    @PostMapping("/getStoreDishSaleAnalysis")
    public Result getStoreDishSaleAnalysis(@RequestBody Map<String, Object> params,
                                           HttpServletRequest request) {
        // 当前门店ID（非管理员从登录用户中获取）
        Long storeId = getCurrentUserStoreIdIfNotAdmin(request);
        String startTime = Optional.ofNullable(params.get("startTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        String endTime = Optional.ofNullable(params.get("endTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getStoreDishSaleAnalysis(storeId, startTime, endTime);
        return Result.success(data);
    }

    /**
     * 门店订单状态分布
     */
    @PostMapping("/getStoreOrderStatusAnalysis")
    public Result getStoreOrderStatusAnalysis(@RequestBody Map<String, Object> params,
                                              HttpServletRequest request) {
        // 当前门店ID（非管理员从登录用户中获取）
        Long storeId = getCurrentUserStoreIdIfNotAdmin(request);
        String startTime = Optional.ofNullable(params.get("startTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        String endTime = Optional.ofNullable(params.get("endTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getStoreOrderStatusAnalysis(storeId, startTime, endTime);
        return Result.success(data);
    }

    /**
     * 当前门店每日收入统计（按日期汇总订单实付金额）
     */
    @PostMapping("/getStoreDishStockAnalysis")
    public Result getStoreDishStockAnalysis(@RequestBody Map<String, Object> params,
                                            HttpServletRequest request) {
        // 当前门店ID（非管理员从登录用户中获取）
        Long storeId = getCurrentUserStoreIdIfNotAdmin(request);
        // 从 RequestBody 中提取时间范围参数
        String startTime = Optional.ofNullable(params.get("startTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        String endTime = Optional.ofNullable(params.get("endTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getStoreDishStockAnalysis(storeId, startTime, endTime);
        return Result.success(data);
    }

    /**
     * 门店会员消费统计
     */
    @PostMapping("/getStoreMemberConsumeAnalysis")
    public Result getStoreMemberConsumeAnalysis(@RequestBody Map<String, Object> params,
                                                HttpServletRequest request) {
        // 当前门店ID（非管理员从登录用户中获取），作为会员注册门店过滤
        Long registerStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        Integer isVip = Optional.ofNullable(params.get("isVip"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .map(Integer::valueOf)
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getStoreMemberConsumeAnalysis(registerStoreId, isVip);
        return Result.success(data);
    }

    /**
     * 所有门店营业额统计（管理员查看）
     */
    @PostMapping("/getAllStoresRevenueAnalysis")
    public Result getAllStoresRevenueAnalysis(@RequestBody Map<String, Object> params) {
        String startTime = Optional.ofNullable(params.get("startTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        String endTime = Optional.ofNullable(params.get("endTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getAllStoresRevenueAnalysis(startTime, endTime);
        return Result.success(data);
    }

    /**
     * 所有门店订单分布状态统计（管理员查看）
     */
    @PostMapping("/getAllStoresOrderStatusAnalysis")
    public Result getAllStoresOrderStatusAnalysis(@RequestBody Map<String, Object> params) {
        String startTime = Optional.ofNullable(params.get("startTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        String endTime = Optional.ofNullable(params.get("endTime"))
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .orElse(null);
        List<EchartsDataVO> data = echartsService.getAllStoresOrderStatusAnalysis(startTime, endTime);
        return Result.success(data);
    }

    /**
     * 如果当前登录用户不是管理员，则返回其所属门店ID；否则返回 null
     */
    private Long getCurrentUserStoreIdIfNotAdmin(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasLength(token)) {
            return null;
        }
        String userIdStr = stringRedisTemplate.opsForValue().get(redisTokenKey(token));
        if (!StringUtils.hasLength(userIdStr)) {
            return null;
        }
        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return null;
        }
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            return null;
        }
        if (userInfo.getRoleType() != null && "admin".equalsIgnoreCase(String.valueOf(userInfo.getRoleType()))) {
            return null;
        }
        return userInfo.getStoreId();
    }

    private String redisTokenKey(String token) {
        return "chain_catering:login:token:" + token;
    }

}
