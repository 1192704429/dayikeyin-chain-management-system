package com.dake.catering.module.dish_category.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.dake.catering.common.Result;
import com.dake.catering.module.dish_category.entity.DishCategory;
import com.dake.catering.module.dish_category.service.DishCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author wuming
 * @description: 菜品分类Controller
 */
@RestController
@RequestMapping("/dish_category")
public class DishCategoryController {

    @Autowired
    private DishCategoryService dishCategoryService;

    /**
     * 分页查询菜品分类列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) DishCategory entity) {
        if (entity == null) {
            entity = new DishCategory();
        }
        return Result.success(dishCategoryService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有菜品分类列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) DishCategory entity) {
        if (entity == null) {
            entity = new DishCategory();
        }
        List<DishCategory> list = dishCategoryService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询菜品分类
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        DishCategory dishCategory = dishCategoryService.getById(id);
        if (dishCategory == null) {
            return Result.error("菜品分类不存在");
        }
        return Result.success(dishCategory);
    }

    /**
     * 新增菜品分类
     */
    @PostMapping
    public Result save(@RequestBody DishCategory dishCategory) {
        // 分类编号在新增时自动生成（仅当前端未传时）
        if (StrUtil.isBlank(dishCategory.getCategoryNo())) {
            dishCategory.setCategoryNo(generateCategoryNo());
        }
        dishCategory.setCreateTime(LocalDateTime.now());
        boolean success = dishCategoryService.save(dishCategory);
        if (success) {
            return Result.success("新增成功", dishCategory);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新菜品分类
     */
    @PutMapping
    public Result update(@RequestBody DishCategory dishCategory) {
        boolean success = dishCategoryService.updateById(dishCategory);
        if (success) {
            return Result.success("更新成功", dishCategory);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除菜品分类
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = dishCategoryService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除菜品分类
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = dishCategoryService.removeByIds(ids);
        if (success) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 生成菜品分类编号
     * 规则示例：CAT + yyyymmddHHMMss + 3位随机数，如 CAT20260204153000987
     */
    private String generateCategoryNo() {
        String timePart = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String randomPart = RandomUtil.randomNumbers(3);
        return "CAT" + timePart + randomPart;
    }

}
