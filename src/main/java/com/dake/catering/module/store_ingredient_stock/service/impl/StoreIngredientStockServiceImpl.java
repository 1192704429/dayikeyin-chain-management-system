package com.dake.catering.module.store_ingredient_stock.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.store_ingredient_stock.entity.StoreIngredientStock;
import com.dake.catering.module.store_ingredient_stock.mapper.StoreIngredientStockMapper;
import com.dake.catering.module.store_ingredient_stock.service.StoreIngredientStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuming
 * @description: 门店原材料库存Service实现类
 */
@Service
public class StoreIngredientStockServiceImpl extends ServiceImpl<StoreIngredientStockMapper, StoreIngredientStock> implements StoreIngredientStockService {

    @Autowired
    private StoreIngredientStockMapper storeIngredientStockMapper;

    @Override
    public IPage<StoreIngredientStock> pageQuery(Integer current, Integer size, StoreIngredientStock entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<StoreIngredientStock> records = storeIngredientStockMapper.pageQuery(entity, offset, limit);
        Long total = storeIngredientStockMapper.countQuery(entity);

        Page<StoreIngredientStock> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<StoreIngredientStock> listQuery(StoreIngredientStock entity) {
        return storeIngredientStockMapper.listQuery(entity);
    }

    @Override
    public StoreIngredientStock getById(Long id) {
        return storeIngredientStockMapper.getById(id);
    }

    @Override
    public StoreIngredientStock getByStoreIdAndIngredientId(Long storeId, Long ingredientId) {
        return storeIngredientStockMapper.getByStoreIdAndIngredientId(storeId, ingredientId);
    }

    /**
     * 调整库存（增加或减少）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean adjustStock(Long storeId, Long ingredientId, BigDecimal quantity) {
        StoreIngredientStock stock = getByStoreIdAndIngredientId(storeId, ingredientId);
        if (stock == null) {
            // 如果不存在，创建新记录
            stock = new StoreIngredientStock();
            stock.setStoreId(storeId);
            stock.setIngredientId(ingredientId);
            stock.setStockQuantity(BigDecimal.ZERO);
            stock.setWarningQuantity(BigDecimal.ZERO);
            stock.setCreateTime(LocalDateTime.now());
        }
        
        // 调整库存数量
        BigDecimal currentStock = stock.getStockQuantity() != null ? stock.getStockQuantity() : BigDecimal.ZERO;
        BigDecimal newStock = currentStock.add(quantity);
        // 确保库存不为负数
        if (newStock.compareTo(BigDecimal.ZERO) < 0) {
            newStock = BigDecimal.ZERO;
        }
        stock.setStockQuantity(newStock);
        stock.setUpdateTime(LocalDateTime.now());
        
        if (stock.getId() == null) {
            return save(stock);
        } else {
            return updateById(stock);
        }
    }

}
