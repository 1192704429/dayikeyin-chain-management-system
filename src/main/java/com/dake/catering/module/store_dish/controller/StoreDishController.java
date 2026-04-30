package com.dake.catering.module.store_dish.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.store_dish.entity.StoreDish;
import com.dake.catering.module.store_dish.service.StoreDishService;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.module.user_info.service.UserInfoService;
import com.dake.catering.vo.SelectVO;
import com.dake.catering.vo.StoreAssignCategoryVO;
import com.dake.catering.vo.StoreDishAssignRequest;
import com.dake.catering.vo.StoreDishInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuming
 * @description: 门店菜品关联Controller
 */
@RestController
@RequestMapping("/store_dish")
public class StoreDishController {

    @Autowired
    private StoreDishService storeDishService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询门店菜品关联列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) StoreDish entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new StoreDish();
        }
        // 非管理员按当前登录门店过滤门店菜品
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setStoreId(currentStoreId);
        }
        return Result.success(storeDishService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有门店菜品关联列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) StoreDish entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new StoreDish();
        }
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setStoreId(currentStoreId);
        }
        List<StoreDish> list = storeDishService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询门店菜品关联
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        StoreDish storeDish = storeDishService.getById(id);
        if (storeDish == null) {
            return Result.error("门店菜品关联不存在");
        }
        return Result.success(storeDish);
    }

    /**
     * 新增门店菜品关联
     */
    @PostMapping
    public Result save(@RequestBody StoreDish storeDish,
                       HttpServletRequest request) {
        // 非管理员强制写入当前门店，避免越权分配
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            storeDish.setStoreId(currentStoreId);
        }
        storeDish.setCreateTime(LocalDateTime.now());
        boolean success = storeDishService.save(storeDish);
        if (success) {
            return Result.success("新增成功", storeDish);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新门店菜品关联
     */
    @PutMapping
    public Result update(@RequestBody StoreDish storeDish,
                         HttpServletRequest request) {
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            storeDish.setStoreId(currentStoreId);
        }
        boolean success = storeDishService.updateById(storeDish);
        if (success) {
            return Result.success("更新成功", storeDish);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除门店菜品关联
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = storeDishService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除门店菜品关联
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = storeDishService.removeByIds(ids);
        if (success) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 下拉数据接口：dish_info
     */
    @GetMapping("/select/listDishInfo")
    public Result listDishInfo() {
        List<SelectVO> list = storeDishService.listDishInfo();
        return Result.success(list);
    }

    /**
     * 下拉数据接口：sys_store
     */
    @GetMapping("/select/listSysStore")
    public Result listSysStore() {
        List<SelectVO> list = storeDishService.listSysStore();
        return Result.success(list);
    }

    /**
     * 根据门店ID查询该门店的菜品信息和菜品分类信息
     */
    @GetMapping("/listByStoreId")
    public Result listByStoreId(@RequestParam("storeId") Long storeId) {
        List<StoreDishInfoVO> list = storeDishService.listStoreDishWithCategoryByStoreId(storeId);
        return Result.success(list);
    }

    /**
     * 查询当前登录门店的菜品及分类信息（仅返回管理员已分配给该门店的数据）
     */
    @GetMapping("/listByCurrentStore")
    public Result listByCurrentStore(HttpServletRequest request) {
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId == null) {
            return Result.error("store_dish,listByCurrentStore, current store not found");
        }
        List<StoreDishInfoVO> list = storeDishService.listStoreDishWithCategoryByStoreId(currentStoreId);
        return Result.success(list);
    }

    /**
     * 当前门店修改某个菜品的门店售价、上架状态和库存数量
     * 说明：
     * 1. 门店ID默认取当前登录用户所属门店，防止越权修改其他门店数据；
     * 2. 根据 storeId + dishId 精确更新对应记录的部分字段。
     */
    @PostMapping("/updateForCurrentStore")
    public Result updateForCurrentStore(@RequestBody StoreDish storeDish,
                                        HttpServletRequest request) {
        if (storeDish == null || storeDish.getDishId() == null) {
            return Result.error("菜品ID不能为空");
        }
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId == null) {
            return Result.error("当前门店不存在，无法更新门店菜品信息");
        }
        boolean success = storeDishService.updateStoreDishFields(
                currentStoreId,
                storeDish.getDishId(),
                storeDish.getSalePrice(),
                storeDish.getSaleStatus(),
                storeDish.getStockCount()
        );
        if (success) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败，记录可能不存在");
    }

    /**
     * 根据门店ID，加载门店可分配的菜单（分类及菜品）数据
     * 仅返回状态正常的分类及菜品，并标记当前已分配勾选状态
     */
    @GetMapping("/assign/load")
    public Result loadStoreDishAssign(@RequestParam("storeId") Long storeId) {
        List<StoreAssignCategoryVO> list = storeDishService.listAssignDataByStoreId(storeId);
        return Result.success(list);
    }

    /**
     * 保存门店分配菜单及菜品结果
     * 前端应传入门店ID、选中的分类ID集合、选中的菜品ID集合
     */
    @PostMapping("/assign/save")
    public Result saveStoreDishAssign(@RequestBody StoreDishAssignRequest request) {
        storeDishService.saveStoreDishAssign(request);
        return Result.success("保存成功");
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
