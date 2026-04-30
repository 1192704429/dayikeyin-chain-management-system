package com.dake.catering.module.store_dish.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.dish_category.entity.DishCategory;
import com.dake.catering.module.dish_category.mapper.DishCategoryMapper;
import com.dake.catering.module.dish_info.entity.DishInfo;
import com.dake.catering.module.dish_info.mapper.DishInfoMapper;
import com.dake.catering.module.store_dish.entity.StoreDish;
import com.dake.catering.module.store_dish.mapper.StoreDishMapper;
import com.dake.catering.module.store_dish.service.StoreDishService;
import com.dake.catering.module.store_dish_category.entity.StoreDishCategory;
import com.dake.catering.module.store_dish_category.mapper.StoreDishCategoryMapper;
import com.dake.catering.vo.SelectVO;
import com.dake.catering.vo.StoreAssignCategoryVO;
import com.dake.catering.vo.StoreAssignDishVO;
import com.dake.catering.vo.StoreDishAssignRequest;
import com.dake.catering.vo.StoreDishInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wuming
 * @description: 门店菜品关联Service实现类
 */
@Service
@Slf4j
public class StoreDishServiceImpl extends ServiceImpl<StoreDishMapper, StoreDish> implements StoreDishService {

    @Autowired
    private StoreDishMapper storeDishMapper;

    /**
     * 菜品分类 Mapper，用于查询状态正常的分类数据
     */
    @Autowired
    private DishCategoryMapper dishCategoryMapper;

    /**
     * 菜品信息 Mapper，用于查询状态正常的菜品数据
     */
    @Autowired
    private DishInfoMapper dishInfoMapper;

    /**
     * 门店-分类关联 Mapper，用于查询和维护门店与分类的分配关系
     */
    @Autowired
    private StoreDishCategoryMapper storeDishCategoryMapper;

    @Override
    public IPage<StoreDish> pageQuery(Integer current, Integer size, StoreDish entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<StoreDish> records = storeDishMapper.pageQuery(entity, offset, limit);
        Long total = storeDishMapper.countQuery(entity);

        Page<StoreDish> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<StoreDish> listQuery(StoreDish entity) {
        return storeDishMapper.listQuery(entity);
    }

    @Override
    public StoreDish getById(Long id) {
        return storeDishMapper.getById(id);
    }

    /**
     * 下拉数据：dish_info
     */
    @Override
    public List<SelectVO> listDishInfo() {
        return storeDishMapper.listDishInfo();
    }

    /**
     * 下拉数据：sys_store
     */
    @Override
    public List<SelectVO> listSysStore() {
        return storeDishMapper.listSysStore();
    }

    /**
     * 根据门店ID查询门店菜品及分类信息
     */
    @Override
    public List<StoreDishInfoVO> listStoreDishWithCategoryByStoreId(Long storeId) {
        return storeDishMapper.listStoreDishWithCategoryByStoreId(storeId);
    }

    /**
     * 根据门店ID和菜品ID，更新门店菜品的部分字段（售价 / 上架状态 / 库存数量）
     */
    @Override
    public boolean updateStoreDishFields(Long storeId,
                                         Long dishId,
                                         java.math.BigDecimal salePrice,
                                         Integer saleStatus,
                                         Integer stockCount) {
        if (storeId == null || dishId == null) {
            log.warn("updateStoreDishFields, storeId or dishId is null, storeId={}, dishId={}", storeId, dishId);
            return false;
        }
        int affected = storeDishMapper.updateFieldsByStoreIdAndDishId(storeId, dishId, salePrice, saleStatus, stockCount);
        return affected > 0;
    }

    /**
     * 根据门店ID，查询可分配的分类及菜品信息以及当前勾选情况
     * 说明：
     * 1. 仅查询状态正常（启用状态）的分类和菜品；
     * 2. 已经分配给门店的分类、菜品会设置 selected = true，便于前端默认勾选；
     * 3. 避免空指针，所有集合、VO 均做非空保护。
     *
     * @param storeId 门店ID
     * @return 包含分类及分类下菜品勾选信息的列表
     */
    @Override
    public List<StoreAssignCategoryVO> listAssignDataByStoreId(Long storeId) {
        // 判空保护：如果门店ID为空，直接返回空列表，避免无意义查询
        if (storeId == null) {
            log.warn("listAssignDataByStoreId, storeId is null");
            return new ArrayList<>();
        }

        // 1. 查询状态正常的菜品分类
        DishCategory categoryCondition = new DishCategory();
        categoryCondition.setStatus(1);
        List<DishCategory> categoryList = dishCategoryMapper.listQuery(categoryCondition);
        if (categoryList == null || categoryList.isEmpty()) {
            log.info("listAssignDataByStoreId, no enabled category found, storeId={}", storeId);
            return new ArrayList<>();
        }

        // 2. 查询状态正常的菜品信息
        DishInfo dishCondition = new DishInfo();
        dishCondition.setStatus(1);
        List<DishInfo> dishInfoList = dishInfoMapper.listQuery(dishCondition);
        if (dishInfoList == null) {
            // 为避免后续判空，统一转为空列表
            dishInfoList = new ArrayList<>();
        }

        // 将菜品根据分类ID做分组，便于后续快速组装分类-菜品结构
        Map<Long, List<DishInfo>> dishGroupByCategoryMap = new HashMap<>(16);
        for (DishInfo dishInfo : dishInfoList) {
            if (dishInfo == null || dishInfo.getCategoryId() == null) {
                continue;
            }
            List<DishInfo> groupList = dishGroupByCategoryMap.computeIfAbsent(dishInfo.getCategoryId(), k -> new ArrayList<>());
            groupList.add(dishInfo);
        }

        // 3. 查询当前门店已经分配的分类关系（仅取生效状态）
        StoreDishCategory storeDishCategoryCondition = new StoreDishCategory();
        storeDishCategoryCondition.setStoreId(storeId);
        storeDishCategoryCondition.setStatus(1);
        List<StoreDishCategory> storeDishCategoryList = storeDishCategoryMapper.listQuery(storeDishCategoryCondition);
        Set<Long> selectedCategoryIdSet = new HashSet<>();
        if (storeDishCategoryList != null) {
            for (StoreDishCategory item : storeDishCategoryList) {
                if (item != null && item.getCategoryId() != null) {
                    selectedCategoryIdSet.add(item.getCategoryId());
                }
            }
        }

        // 4. 查询当前门店已经分配的菜品关系
        StoreDish storeDishCondition = new StoreDish();
        storeDishCondition.setStoreId(storeId);
        List<StoreDish> storeDishList = storeDishMapper.listQuery(storeDishCondition);
        Set<Long> selectedDishIdSet = new HashSet<>();
        if (storeDishList != null) {
            for (StoreDish item : storeDishList) {
                if (item != null && item.getDishId() != null) {
                    selectedDishIdSet.add(item.getDishId());
                }
            }
        }

        // 5. 组装返回的分类及菜品勾选结构
        List<StoreAssignCategoryVO> resultList = new ArrayList<>();
        for (DishCategory category : categoryList) {
            if (category == null || category.getId() == null) {
                continue;
            }

            StoreAssignCategoryVO categoryVO = new StoreAssignCategoryVO();
            categoryVO.setCategoryId(category.getId());
            categoryVO.setCategoryName(category.getCategoryName());
            // 如果当前分类已存在于门店-分类关联中，则认为已勾选
            categoryVO.setSelected(selectedCategoryIdSet.contains(category.getId()));

            // 根据分类ID获取对应的菜品列表
            List<DishInfo> groupDishList = dishGroupByCategoryMap.get(category.getId());
            List<StoreAssignDishVO> assignDishVOList = new ArrayList<>();
            if (groupDishList != null) {
                for (DishInfo dishInfo : groupDishList) {
                    if (dishInfo == null || dishInfo.getId() == null) {
                        continue;
                    }
                    StoreAssignDishVO dishVO = new StoreAssignDishVO();
                    dishVO.setDishId(dishInfo.getId());
                    dishVO.setDishName(dishInfo.getDishName());
                    dishVO.setDishImg(dishInfo.getDishImg());
                    dishVO.setSelected(selectedDishIdSet.contains(dishInfo.getId()));
                    assignDishVOList.add(dishVO);
                }
            }
            categoryVO.setDishList(assignDishVOList);

            resultList.add(categoryVO);
        }

        log.info("listAssignDataByStoreId, storeId={}, categorySize={}, dishSize={}", storeId, resultList.size(), dishInfoList.size());
        return resultList;
    }

    /**
     * 保存门店分配菜单及菜品结果
     * 说明：
     * 1. 先清理该门店原有的门店-分类、门店-菜品关联数据；
     * 2. 再根据前端传来的分类ID集合、菜品ID集合批量插入新的关联数据；
     * 3. 全过程开启事务，任意一步失败都会回滚，保证数据一致性。
     *
     * @param request 保存请求体
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveStoreDishAssign(StoreDishAssignRequest request) {
        if (request == null || request.getStoreId() == null) {
            log.warn("saveStoreDishAssign, request or storeId is null");
            return;
        }

        Long storeId = request.getStoreId();
        List<Long> categoryIdList = request.getCategoryIdList();
        List<Long> dishIdList = request.getDishIdList();

        // 1. 先删除该门店已有的门店-分类关联
        QueryWrapper<StoreDishCategory> categoryWrapper = new QueryWrapper<>();
        categoryWrapper.lambda().eq(StoreDishCategory::getStoreId, storeId);
        storeDishCategoryMapper.delete(categoryWrapper);

        // 2. 删除该门店已有的门店-菜品关联
        QueryWrapper<StoreDish> storeDishWrapper = new QueryWrapper<>();
        storeDishWrapper.lambda().eq(StoreDish::getStoreId, storeId);
        storeDishMapper.delete(storeDishWrapper);

        LocalDateTime now = LocalDateTime.now();

        // 预先查询本次要分配的菜品价格，避免在循环中多次访问数据库
        Map<Long, BigDecimal> dishPriceMap = new HashMap<>(16);
        if (dishIdList != null && !dishIdList.isEmpty()) {
            List<DishInfo> dishInfoList = dishInfoMapper.selectBatchIds(dishIdList);
            if (dishInfoList != null) {
                for (DishInfo dishInfo : dishInfoList) {
                    if (dishInfo == null || dishInfo.getId() == null) {
                        continue;
                    }
                    dishPriceMap.put(dishInfo.getId(), dishInfo.getPrice());
                }
            }
        }

        // 3. 重新插入门店-分类关联
        if (categoryIdList != null && !categoryIdList.isEmpty()) {
            for (Long categoryId : categoryIdList) {
                if (categoryId == null) {
                    continue;
                }
                StoreDishCategory storeDishCategory = new StoreDishCategory();
                storeDishCategory.setStoreId(storeId);
                storeDishCategory.setCategoryId(categoryId);
                // 默认分配状态为生效
                storeDishCategory.setStatus(1);
                // 排序值、创建时间如需后续优化，可在此加入更多逻辑
                storeDishCategory.setSort(0);
                storeDishCategory.setCreateTime(now);
                storeDishCategoryMapper.insert(storeDishCategory);
            }
        }

        // 4. 重新插入门店-菜品关联
        if (dishIdList != null && !dishIdList.isEmpty()) {
            for (Long dishId : dishIdList) {
                if (dishId == null) {
                    continue;
                }
                StoreDish storeDish = new StoreDish();
                storeDish.setStoreId(storeId);
                storeDish.setDishId(dishId);
                // 默认上架状态为启用（可根据业务需要调整）
                storeDish.setSaleStatus(1);
                // 如果存在对应菜品，则将菜品价格填充到门店售价字段中，否则使用 0 作为安全默认值
                BigDecimal salePrice = dishPriceMap.get(dishId);
                if (salePrice == null) {
                    salePrice = BigDecimal.ZERO;
                }
                storeDish.setSalePrice(salePrice);
                // 其他数值型字段设置为安全默认值，避免空指针
                storeDish.setStockCount(0);
                storeDish.setWarningCount(0);
                storeDish.setSort(0);
                storeDish.setCreateTime(now);
                storeDishMapper.insert(storeDish);
            }
        }

        int categoryCount = categoryIdList == null ? 0 : categoryIdList.size();
        int dishCount = dishIdList == null ? 0 : dishIdList.size();
        log.info("saveStoreDishAssign, storeId={}, categoryCount={}, dishCount={}", storeId, categoryCount, dishCount);
    }

}
