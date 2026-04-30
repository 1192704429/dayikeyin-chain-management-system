package com.dake.catering.module.order_main.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dake.catering.module.order_item.entity.OrderItem;
import com.dake.catering.module.order_item.mapper.OrderItemMapper;
import com.dake.catering.module.order_main.entity.OrderMain;
import com.dake.catering.module.order_main.mapper.OrderMainMapper;
import com.dake.catering.module.order_main.service.OrderMainService;
import com.dake.catering.module.store_dish.entity.StoreDish;
import com.dake.catering.module.store_dish.mapper.StoreDishMapper;
import com.dake.catering.module.member_info.entity.MemberInfo;
import com.dake.catering.module.member_info.service.MemberInfoService;
import com.dake.catering.module.member_points_record.entity.MemberPointsRecord;
import com.dake.catering.module.member_points_record.service.MemberPointsRecordService;
import com.dake.catering.vo.SelectVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wuming
 * @description: 订单主Service实现类
 */
@Service
public class OrderMainServiceImpl extends ServiceImpl<OrderMainMapper, OrderMain> implements OrderMainService {

    @Autowired
    private OrderMainMapper orderMainMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private StoreDishMapper storeDishMapper;

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private MemberPointsRecordService memberPointsRecordService;

    @Override
    public IPage<OrderMain> pageQuery(Integer current, Integer size, OrderMain entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<OrderMain> records = orderMainMapper.pageQuery(entity, offset, limit);
        Long total = orderMainMapper.countQuery(entity);

        // 一次性查询当前页所有订单的明细，并按订单ID分组，避免 N+1 查询
        if (!records.isEmpty()) {
            List<Long> orderIds = records.stream()
                    .map(OrderMain::getId)
                    .collect(Collectors.toList());
            List<OrderItem> orderItems = orderItemMapper.listByOrderIds(orderIds);
            Map<Long, List<OrderItem>> itemMap = orderItems.stream()
                    .collect(Collectors.groupingBy(OrderItem::getOrderId));
            records.forEach(order -> order.setOrderItemList(itemMap.get(order.getId())));
        }

        Page<OrderMain> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<OrderMain> listQuery(OrderMain entity) {
        return orderMainMapper.listQuery(entity);
    }

    @Override
    public OrderMain getById(Long id) {
        OrderMain order = orderMainMapper.getById(id);
        if (order == null) {
            return null;
        }
        List<OrderItem> items = orderItemMapper.listByOrderIds(java.util.Collections.singletonList(id));
        order.setOrderItemList(items);
        return order;
    }

    /**
     * 下拉数据：user_info
     */
    @Override
    public List<SelectVO> listUserInfo() {
        return orderMainMapper.listUserInfo();
    }

    /**
     * 下拉数据：sys_store
     */
    @Override
    public List<SelectVO> listSysStore() {
        return orderMainMapper.listSysStore();
    }

    /**
     * 下拉数据：member_info
     */
    @Override
    public List<SelectVO> listMemberInfo() {
        return orderMainMapper.listMemberInfo();
    }

    /**
     * 门店收银：会员结账下单
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMain checkout(OrderMain orderMain) {
        if (orderMain == null || orderMain.getOrderItemList() == null || orderMain.getOrderItemList().isEmpty()) {
            throw new IllegalArgumentException("checkout, orderItemList is empty");
        }
        List<OrderItem> items = orderMain.getOrderItemList();
        LocalDateTime now = LocalDateTime.now();
        Long storeId = orderMain.getStoreId();
        if (storeId == null) {
          throw new IllegalArgumentException("checkout, storeId is null");
        }

        // 库存校验（基于门店库存 store_dish）
        for (OrderItem item : items) {
            if (item == null || item.getDishId() == null) continue;
            Integer qty = item.getQuantity() != null ? item.getQuantity() : 0;
            if (qty <= 0) {
                throw new IllegalArgumentException("菜品【" + (item.getDishName() != null ? item.getDishName() : "") + "】数量非法");
            }
            QueryWrapper<StoreDish> wrapper = new QueryWrapper<>();
            wrapper.lambda()
                .eq(StoreDish::getStoreId, storeId)
                .eq(StoreDish::getDishId, item.getDishId());
            StoreDish storeDish = storeDishMapper.selectOne(wrapper);
            int stock = storeDish != null && storeDish.getStockCount() != null ? storeDish.getStockCount() : 0;
            if (stock < qty) {
                throw new IllegalArgumentException("菜品【" + (item.getDishName() != null ? item.getDishName() : "") + "】库存不足");
            }
        }

        // 计算订单金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (OrderItem item : items) {
            if (item == null) continue;
            BigDecimal unitPrice = item.getUnitPrice() != null ? item.getUnitPrice() : BigDecimal.ZERO;
            int qty = item.getQuantity() != null ? item.getQuantity() : 0;
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(qty));
            item.setTotalPrice(lineTotal);
            totalAmount = totalAmount.add(lineTotal);
        }

        // 填充订单主金额字段：优惠、积分暂为 0，由后续营销模块扩展
        orderMain.setOrderAmount(totalAmount);
        if (orderMain.getDiscountAmount() == null) {
            orderMain.setDiscountAmount(BigDecimal.ZERO);
        }
        if (orderMain.getPointsDeductAmount() == null) {
            orderMain.setPointsDeductAmount(BigDecimal.ZERO);
        }
        if (orderMain.getPointsDeduct() == null) {
            orderMain.setPointsDeduct(0);
        }
        BigDecimal actualPay = totalAmount
                .subtract(orderMain.getDiscountAmount())
                .subtract(orderMain.getPointsDeductAmount());
        orderMain.setActualPay(actualPay.max(BigDecimal.ZERO));

        // 生成订单号（简单规则，可根据需要调整）
        if (orderMain.getOrderNo() == null || orderMain.getOrderNo().isEmpty()) {
            orderMain.setOrderNo("O" + System.currentTimeMillis());
        }
        // 设置订单状态：1=待付款
        if (orderMain.getOrderStatus() == null) {
            orderMain.setOrderStatus(1);
        }
        orderMain.setCreateTime(now);
        // 未付款订单不设置支付时间与完成时间

        // 先保存订单主
        this.save(orderMain);

        // 再保存订单明细
        Long orderId = orderMain.getId();
        for (OrderItem item : items) {
            if (item == null) continue;
            item.setOrderId(orderId);
            item.setCreateTime(now);
            orderItemMapper.insert(item);

            // 扣减库存
            QueryWrapper<StoreDish> wrapper = new QueryWrapper<>();
            wrapper.lambda()
                .eq(StoreDish::getStoreId, storeId)
                .eq(StoreDish::getDishId, item.getDishId());
            StoreDish storeDish = storeDishMapper.selectOne(wrapper);
            if (storeDish != null && storeDish.getStockCount() != null) {
                int stock = storeDish.getStockCount();
                int qty = item.getQuantity() != null ? item.getQuantity() : 0;
                storeDish.setStockCount(Math.max(stock - qty, 0));
                storeDishMapper.updateById(storeDish);
            }
        }
        return orderMain;
    }

    @Override
    public List<OrderMain> listUnpaidByStoreId(Long storeId) {
        OrderMain condition = new OrderMain();
        condition.setStoreId(storeId);
        condition.setOrderStatus(1);
        return orderMainMapper.listQuery(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMain pay(Long orderId, Integer payType, Long storeId, Long memberId,
                         Integer pointsToDeduct, Long operatorId, String tableNo) {
        if (orderId == null) {
            throw new IllegalArgumentException("pay, orderId is null");
        }
        OrderMain order = orderMainMapper.getById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("pay, order not found");
        }
        if (storeId != null && order.getStoreId() != null && !storeId.equals(order.getStoreId())) {
            throw new IllegalArgumentException("pay, order not belong to current store");
        }
        // 仅允许对“待付款”订单支付，状态为 1 表示待付款，2 表示已付款
        if (order.getOrderStatus() != null && order.getOrderStatus() == 2) {
            return order;
        }
        LocalDateTime now = LocalDateTime.now();
        order.setPayType(payType);
        if (memberId != null) {
            order.setMemberId(memberId);
        }
        if (tableNo != null && !tableNo.trim().isEmpty()) {
            order.setTableNo(tableNo.trim());
        }

        // 会员积分抵扣 & 累计消费处理
        if (memberId != null) {
            MemberInfo member = memberInfoService.getById(memberId);
            if (member != null) {
                int currentPoints = member.getTotalPoints() != null ? member.getTotalPoints() : 0;
                int usePoints = pointsToDeduct != null ? Math.max(pointsToDeduct, 0) : 0;
                if (usePoints > currentPoints) {
                    usePoints = currentPoints;
                }
                // 100 积分抵扣 1 元
                BigDecimal pointsAmount = new BigDecimal(usePoints).divide(new BigDecimal(100), 2, java.math.RoundingMode.DOWN);

                // 计算实付金额
                BigDecimal orderAmount = order.getOrderAmount() != null ? order.getOrderAmount() : BigDecimal.ZERO;
                BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
                order.setPointsDeduct(usePoints);
                order.setPointsDeductAmount(pointsAmount);
                BigDecimal actualPay = orderAmount.subtract(discount).subtract(pointsAmount);
                order.setActualPay(actualPay.max(BigDecimal.ZERO));

                // 累计消费金额和积分：1 元 = 10 积分
                BigDecimal consume = actualPay.max(BigDecimal.ZERO);
                BigDecimal oldConsume = member.getConsumeAmount() != null ? member.getConsumeAmount() : BigDecimal.ZERO;
                member.setConsumeAmount(oldConsume.add(consume));
                int oldCount = member.getConsumeCount() != null ? member.getConsumeCount() : 0;
                member.setConsumeCount(oldCount + 1);
                member.setLastConsumeTime(now);

                int earnPoints = consume.setScale(0, java.math.RoundingMode.DOWN).multiply(new BigDecimal(10)).intValue();
                int newTotalPoints = currentPoints - usePoints + earnPoints;
                member.setTotalPoints(newTotalPoints);
                memberInfoService.updateById(member);

                // 积分记录：抵扣记录（负数）
                if (usePoints > 0) {
                    MemberPointsRecord deductRecord = new MemberPointsRecord();
                    deductRecord.setMemberId(memberId);
                    deductRecord.setStoreId(storeId);
                    deductRecord.setOrderId(orderId);
                    deductRecord.setPointsType(-1);
                    deductRecord.setPointsNum(usePoints);
                    deductRecord.setBizType(2); // 2-订单抵扣
                    deductRecord.setOperatorId(operatorId);
                    deductRecord.setRemark("订单抵扣积分");
                    deductRecord.setCreateTime(now);
                    memberPointsRecordService.save(deductRecord);
                }
                // 积分记录：获得记录（正数）
                if (earnPoints > 0) {
                    MemberPointsRecord earnRecord = new MemberPointsRecord();
                    earnRecord.setMemberId(memberId);
                    earnRecord.setStoreId(storeId);
                    earnRecord.setOrderId(orderId);
                    earnRecord.setPointsType(1);
                    earnRecord.setPointsNum(earnPoints);
                    earnRecord.setBizType(1); // 1-订单消费获得
                    earnRecord.setOperatorId(operatorId);
                    earnRecord.setRemark("订单消费获得积分");
                    earnRecord.setCreateTime(now);
                    memberPointsRecordService.save(earnRecord);
                }
            }
        }

        if (order.getActualPay() == null) {
            BigDecimal orderAmount = order.getOrderAmount() != null ? order.getOrderAmount() : BigDecimal.ZERO;
            BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
            BigDecimal pointsAmount = order.getPointsDeductAmount() != null ? order.getPointsDeductAmount() : BigDecimal.ZERO;
            BigDecimal actualPay = orderAmount.subtract(discount).subtract(pointsAmount);
            order.setActualPay(actualPay.max(BigDecimal.ZERO));
        }
        order.setPayTime(now);
        order.setCompleteTime(now);
        // 设置为已付款状态：2
        order.setOrderStatus(2);
        orderMainMapper.updateById(order);
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMain cancel(Long orderId, Long storeId, String cancelReason) {
        if (orderId == null) {
            throw new IllegalArgumentException("cancel, orderId is null");
        }
        OrderMain order = orderMainMapper.getById(orderId);
        if (order == null) {
            throw new IllegalArgumentException("cancel, order not found");
        }
        if (storeId != null && order.getStoreId() != null && !storeId.equals(order.getStoreId())) {
            throw new IllegalArgumentException("cancel, order not belong to current store");
        }
        // 仅允许取消待付款订单（状态 1）
        if (order.getOrderStatus() == null || order.getOrderStatus() != 1) {
            throw new IllegalArgumentException("仅待付款订单可取消");
        }
        LocalDateTime now = LocalDateTime.now();
        order.setOrderStatus(4);
        order.setCancelTime(now);
        order.setCancelReason(cancelReason);
        orderMainMapper.updateById(order);

        // 恢复库存：根据订单明细逐项加回
        List<OrderItem> items = orderItemMapper.listByOrderIds(java.util.Collections.singletonList(orderId));
        if (items != null && !items.isEmpty()) {
            for (OrderItem item : items) {
                if (item == null || item.getDishId() == null) {
                    continue;
                }
                QueryWrapper<StoreDish> wrapper = new QueryWrapper<>();
                wrapper.lambda()
                    .eq(StoreDish::getStoreId, order.getStoreId())
                    .eq(StoreDish::getDishId, item.getDishId());
                StoreDish storeDish = storeDishMapper.selectOne(wrapper);
                if (storeDish != null) {
                    int stock = storeDish.getStockCount() != null ? storeDish.getStockCount() : 0;
                    int qty = item.getQuantity() != null ? item.getQuantity() : 0;
                    storeDish.setStockCount(stock + qty);
                    storeDishMapper.updateById(storeDish);
                }
            }
        }
        return order;
    }

}
