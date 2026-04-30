package com.dake.catering.module.order_main.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.order_main.entity.OrderMain;
import com.dake.catering.module.order_main.service.OrderMainService;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.module.user_info.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import com.dake.catering.vo.SelectVO;

/**
 * @author wuming
 * @description: 订单主Controller
 */
@RestController
@RequestMapping("/order_main")
public class OrderMainController {

    @Autowired
    private OrderMainService orderMainService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询订单主列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) OrderMain entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new OrderMain();
        }
        // 非管理员按照当前登录用户所属门店做数据权限过滤
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setStoreId(currentStoreId);
        }
        return Result.success(orderMainService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有订单主列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) OrderMain entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new OrderMain();
        }
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setStoreId(currentStoreId);
        }
        List<OrderMain> list = orderMainService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询订单主
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        OrderMain orderMain = orderMainService.getById(id);
        if (orderMain == null) {
            return Result.error("订单主不存在");
        }
        return Result.success(orderMain);
    }

    /**
     * 新增订单主
     */
    @PostMapping
    public Result save(@RequestBody OrderMain orderMain,
                       HttpServletRequest request) {
        // 非管理员强制写入当前登录用户所属门店
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            orderMain.setStoreId(currentStoreId);
        }
        orderMain.setCreateTime(LocalDateTime.now());
        boolean success = orderMainService.save(orderMain);
        if (success) {
            return Result.success("新增成功", orderMain);
        }
        return Result.error("新增失败");
    }

    /**
     * 门店收银：会员结账下单（生成未付款订单）
     * 前端只需传入会员ID（可选）、备注以及订单明细列表（dishId、dishName、unitPrice、quantity 等），
     * 门店ID、店员ID、金额等由后端自动计算和补全。
     */
    @PostMapping("/checkout")
    public Result checkout(@RequestBody OrderMain orderMain,
                           HttpServletRequest request) {
        if (orderMain == null || orderMain.getOrderItemList() == null || orderMain.getOrderItemList().isEmpty()) {
            return Result.error("订单明细不能为空");
        }
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId == null) {
            return Result.error("当前门店不存在，无法下单");
        }
        orderMain.setStoreId(currentStoreId);
        // 当前登录用户作为下单店员
        Long clerkId = getCurrentUserId(request);
        if (clerkId != null) {
            orderMain.setClerkId(clerkId);
        }
        try {
            OrderMain saved = orderMainService.checkout(orderMain);
            return Result.success("下单成功", saved);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 门店收银：查询当前门店“未付款”订单列表
     */
    @GetMapping("/unpaidByCurrentStore")
    public Result listUnpaidByCurrentStore(HttpServletRequest request) {
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId == null) {
            return Result.success(new ArrayList<>());
        }
        List<OrderMain> list = orderMainService.listUnpaidByStoreId(currentStoreId);
        return Result.success(list);
    }

    /**
     * 门店收银：对指定订单进行支付结算
     */
    @PostMapping("/pay")
    public Result pay(@RequestBody OrderMain param, HttpServletRequest request) {
        if (param == null || param.getId() == null) {
            return Result.error("订单ID不能为空");
        }
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        Long operatorId = getCurrentUserId(request);
        try {
            OrderMain paid = orderMainService.pay(
                param.getId(),
                param.getPayType(),
                currentStoreId,
                param.getMemberId(),
                param.getPointsDeduct(),
                operatorId,
                param.getTableNo()
            );
            return Result.success("支付成功", paid);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 门店收银：取消订单（仅待付款订单）
     */
    @PostMapping("/cancel")
    public Result cancel(@RequestBody OrderMain param, HttpServletRequest request) {
        if (param == null || param.getId() == null) {
            return Result.error("订单ID不能为空");
        }
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        try {
            OrderMain canceled = orderMainService.cancel(param.getId(), currentStoreId, param.getCancelReason());
            return Result.success("取消成功", canceled);
        } catch (IllegalArgumentException e) {
            return Result.error(e.getMessage());
        }
    }

    /**
     * 更新订单主
     */
    @PutMapping
    public Result update(@RequestBody OrderMain orderMain,
                         HttpServletRequest request) {
        // 非管理员强制写入当前登录用户所属门店，避免跨门店改单
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            orderMain.setStoreId(currentStoreId);
        }
        boolean success = orderMainService.updateById(orderMain);
        if (success) {
            return Result.success("更新成功", orderMain);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除订单主
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = orderMainService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除订单主
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = orderMainService.removeByIds(ids);
        if (success) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 下拉数据接口：user_info
     */
    @GetMapping("/select/listUserInfo")
    public Result listUserInfo() {
        List<SelectVO> list = orderMainService.listUserInfo();
        return Result.success(list);
    }

    /**
     * 下拉数据接口：sys_store
     */
    @GetMapping("/select/listSysStore")
    public Result listSysStore() {
        List<SelectVO> list = orderMainService.listSysStore();
        return Result.success(list);
    }

    /**
     * 下拉数据接口：member_info
     */
    @GetMapping("/select/listMemberInfo")
    public Result listMemberInfo() {
        List<SelectVO> list = orderMainService.listMemberInfo();
        return Result.success(list);
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
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
        return userId;
    }

    /**
     * 如果当前登录用户不是管理员，则返回其所属门店ID；否则返回 null
     */
    private Long getCurrentUserStoreIdIfNotAdmin(HttpServletRequest request) {
        Long userId = getCurrentUserId(request);
        if (userId == null) {
            return null;
        }
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            return null;
        }
        // 超级管理员不过滤
        if (userInfo.getRoleType() != null && "admin".equalsIgnoreCase(String.valueOf(userInfo.getRoleType()))) {
            return null;
        }
        return userInfo.getStoreId();
    }

    private String redisTokenKey(String token) {
        return "chain_catering:login:token:" + token;
    }

}
