package com.dake.catering.module.dish_info.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.dish_info.entity.DishInfo;
import com.dake.catering.module.dish_info.service.DishInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.dake.catering.vo.SelectVO;

/**
 * @author wuming
 * @description: 菜品信息Controller
 */
@RestController
@RequestMapping("/dish_info")
public class DishInfoController {

    @Autowired
    private DishInfoService dishInfoService;

    /**
     * 分页查询菜品信息列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) DishInfo entity) {
        if (entity == null) {
            entity = new DishInfo();
        }
        return Result.success(dishInfoService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有菜品信息列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) DishInfo entity) {
        if (entity == null) {
            entity = new DishInfo();
        }
        List<DishInfo> list = dishInfoService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询菜品信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        DishInfo dishInfo = dishInfoService.getById(id);
        if (dishInfo == null) {
            return Result.error("菜品信息不存在");
        }
        return Result.success(dishInfo);
    }

    /**
     * 新增菜品信息
     */
    @PostMapping
    public Result save(@RequestBody DishInfo dishInfo) {
        dishInfo.setCreateTime(LocalDateTime.now());
        boolean success = dishInfoService.save(dishInfo);
        if (success) {
            return Result.success("新增成功", dishInfo);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新菜品信息
     */
    @PutMapping
    public Result update(@RequestBody DishInfo dishInfo) {
        boolean success = dishInfoService.updateById(dishInfo);
        if (success) {
            return Result.success("更新成功", dishInfo);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除菜品信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = dishInfoService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除菜品信息
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = dishInfoService.removeByIds(ids);
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
        List<SelectVO> list = dishInfoService.listDishCategory();
        return Result.success(list);
    }

}
