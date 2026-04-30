package com.dake.catering.module.store_dish_category.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.store_dish_category.entity.StoreDishCategory;
import com.dake.catering.module.store_dish_category.service.StoreDishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.dake.catering.vo.SelectVO;

/**
 * @author wuming
 * @description: 门店菜品分类关联Controller
 */
@RestController
@RequestMapping("/store_dish_category")
public class StoreDishCategoryController {

    @Autowired
    private StoreDishCategoryService storeDishCategoryService;

    /**
     * 分页查询门店菜品分类关联列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) StoreDishCategory entity) {
        if (entity == null) {
            entity = new StoreDishCategory();
        }
        return Result.success(storeDishCategoryService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有门店菜品分类关联列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) StoreDishCategory entity) {
        if (entity == null) {
            entity = new StoreDishCategory();
        }
        List<StoreDishCategory> list = storeDishCategoryService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询门店菜品分类关联
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        StoreDishCategory storeDishCategory = storeDishCategoryService.getById(id);
        if (storeDishCategory == null) {
            return Result.error("门店菜品分类关联不存在");
        }
        return Result.success(storeDishCategory);
    }

    /**
     * 新增门店菜品分类关联
     */
    @PostMapping
    public Result save(@RequestBody StoreDishCategory storeDishCategory) {
        storeDishCategory.setCreateTime(LocalDateTime.now());
        boolean success = storeDishCategoryService.save(storeDishCategory);
        if (success) {
            return Result.success("新增成功", storeDishCategory);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新门店菜品分类关联
     */
    @PutMapping
    public Result update(@RequestBody StoreDishCategory storeDishCategory) {
        boolean success = storeDishCategoryService.updateById(storeDishCategory);
        if (success) {
            return Result.success("更新成功", storeDishCategory);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除门店菜品分类关联
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = storeDishCategoryService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除门店菜品分类关联
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = storeDishCategoryService.removeByIds(ids);
        if (success) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 下拉数据接口：dish_category
     */
    @GetMapping("/select/listDishCategory")
    public Result listDishCategory() {
        List<SelectVO> list = storeDishCategoryService.listDishCategory();
        return Result.success(list);
    }

    /**
     * 下拉数据接口：sys_store
     */
    @GetMapping("/select/listSysStore")
    public Result listSysStore() {
        List<SelectVO> list = storeDishCategoryService.listSysStore();
        return Result.success(list);
    }

}
