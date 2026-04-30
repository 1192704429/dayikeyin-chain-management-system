package com.dake.catering.module.store_ingredient_stock.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.store_ingredient_stock.entity.StoreIngredientStock;
import com.dake.catering.module.store_ingredient_stock.service.StoreIngredientStockService;
import com.dake.catering.module.user_info.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author wuming
 * @description: 门店原材料库存Controller
 */
@RestController
@RequestMapping("/store_ingredient_stock")
public class StoreIngredientStockController {

    @Autowired
    private StoreIngredientStockService storeIngredientStockService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询门店原材料库存列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) StoreIngredientStock entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new StoreIngredientStock();
        }
        // 非管理员按当前登录门店过滤
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setStoreId(currentStoreId);
        }
        return Result.success(storeIngredientStockService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有门店原材料库存列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) StoreIngredientStock entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new StoreIngredientStock();
        }
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setStoreId(currentStoreId);
        }
        List<StoreIngredientStock> list = storeIngredientStockService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询门店原材料库存
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        StoreIngredientStock stock = storeIngredientStockService.getById(id);
        if (stock == null) {
            return Result.error("门店原材料库存不存在");
        }
        return Result.success(stock);
    }

    /**
     * 新增或更新门店原材料库存
     */
    @PostMapping
    public Result save(@RequestBody StoreIngredientStock stock,
                       HttpServletRequest request) {
        // 非管理员强制写入当前门店
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            stock.setStoreId(currentStoreId);
        }
        if (stock.getId() == null) {
            stock.setCreateTime(LocalDateTime.now());
        }
        stock.setUpdateTime(LocalDateTime.now());
        boolean success = storeIngredientStockService.saveOrUpdate(stock);
        if (success) {
            return Result.success(stock.getId() == null ? "新增成功" : "更新成功", stock);
        }
        return Result.error(stock.getId() == null ? "新增失败" : "更新失败");
    }

    /**
     * 更新门店原材料库存
     */
    @PutMapping
    public Result update(@RequestBody StoreIngredientStock stock,
                         HttpServletRequest request) {
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            stock.setStoreId(currentStoreId);
        }
        stock.setUpdateTime(LocalDateTime.now());
        boolean success = storeIngredientStockService.updateById(stock);
        if (success) {
            return Result.success("更新成功", stock);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除门店原材料库存
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = storeIngredientStockService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除门店原材料库存
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = storeIngredientStockService.removeByIds(ids);
        if (success) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 调整库存（增加或减少）
     * 参数：storeId, ingredientId, quantity（正数为增加，负数为减少）
     */
    @PostMapping("/adjust")
    public Result adjustStock(@RequestBody Map<String, Object> params,
                               HttpServletRequest request) {
        Long storeId = params.get("storeId") != null ? Long.valueOf(params.get("storeId").toString()) : null;
        Long ingredientId = params.get("ingredientId") != null ? Long.valueOf(params.get("ingredientId").toString()) : null;
        BigDecimal quantity = params.get("quantity") != null ? new BigDecimal(params.get("quantity").toString()) : null;

        if (ingredientId == null) {
            return Result.error("原材料ID不能为空");
        }
        if (quantity == null) {
            return Result.error("调整数量不能为空");
        }

        // 非管理员使用当前门店
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            storeId = currentStoreId;
        }
        if (storeId == null) {
            return Result.error("门店ID不能为空");
        }

        boolean success = storeIngredientStockService.adjustStock(storeId, ingredientId, quantity);
        if (success) {
            return Result.success("库存调整成功");
        }
        return Result.error("库存调整失败");
    }

    /**
     * 如果当前登录用户不是管理员，则返回其所属门店ID；否则返回 null
     */
    private Long getCurrentUserStoreIdIfNotAdmin(HttpServletRequest request) {
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
        com.dake.catering.module.user_info.entity.UserInfo userInfo = userInfoService.getById(userId);
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
