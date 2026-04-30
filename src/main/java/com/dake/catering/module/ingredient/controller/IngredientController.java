package com.dake.catering.module.ingredient.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.ingredient.entity.Ingredient;
import com.dake.catering.module.ingredient.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import com.dake.catering.vo.SelectVO;

/**
 * @author wuming
 * @description: 原材料Controller
 */
@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    /**
     * 分页查询原材料列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) Ingredient entity) {
        if (entity == null) {
            entity = new Ingredient();
        }
        return Result.success(ingredientService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有原材料列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) Ingredient entity) {
        if (entity == null) {
            entity = new Ingredient();
        }
        List<Ingredient> list = ingredientService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询原材料
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        Ingredient ingredient = ingredientService.getById(id);
        if (ingredient == null) {
            return Result.error("原材料不存在");
        }
        return Result.success(ingredient);
    }

    /**
     * 新增原材料
     */
    @PostMapping
    public Result save(@RequestBody Ingredient ingredient) {
        ingredient.setCreateTime(LocalDateTime.now());
        boolean success = ingredientService.save(ingredient);
        if (success) {
            return Result.success("新增成功", ingredient);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新原材料
     */
    @PutMapping
    public Result update(@RequestBody Ingredient ingredient) {
        boolean success = ingredientService.updateById(ingredient);
        if (success) {
            return Result.success("更新成功", ingredient);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除原材料
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = ingredientService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除原材料
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = ingredientService.removeByIds(ids);
        if (success) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 下拉数据接口：ingredient
     */
    @GetMapping("/select/listIngredient")
    public Result listIngredient() {
        List<SelectVO> list = ingredientService.listIngredient();
        return Result.success(list);
    }

}
