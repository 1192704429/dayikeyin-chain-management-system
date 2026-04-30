package com.dake.catering.module.sys_store.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.sys_store.entity.SysStore;
import com.dake.catering.module.sys_store.service.SysStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import com.dake.catering.vo.SelectVO;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author wuming
 * @description: 门店信息Controller
 */
@RestController
@RequestMapping("/sys_store")
public class SysStoreController {

    @Autowired
    private SysStoreService sysStoreService;

    /**
     * 分页查询门店信息列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) SysStore entity) {
        if (entity == null) {
            entity = new SysStore();
        }
        return Result.success(sysStoreService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有门店信息列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) SysStore entity) {
        if (entity == null) {
            entity = new SysStore();
        }
        List<SysStore> list = sysStoreService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询门店信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        SysStore sysStore = sysStoreService.getById(id);
        if (sysStore == null) {
            return Result.error("门店信息不存在");
        }
        return Result.success(sysStore);
    }

    /**
     * 查询当前登录门店的门店信息
     */
    @GetMapping("/current")
    public Result getCurrentStore(HttpServletRequest request) {
        SysStore sysStore = sysStoreService.getCurrentStore(request);
        if (sysStore == null) {
            return Result.error("当前门店不存在或当前账号非门店账号");
        }
        return Result.success(sysStore);
    }

    /**
     * 新增门店信息
     */
    @PostMapping
    public Result save(@RequestBody SysStore sysStore) {
        sysStore.setCreateTime(LocalDateTime.now());
        boolean success = sysStoreService.save(sysStore);
        if (success) {
            return Result.success("新增成功", sysStore);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新门店信息
     */
    @PutMapping
    public Result update(@RequestBody SysStore sysStore) {
        boolean success = sysStoreService.updateById(sysStore);
        if (success) {
            return Result.success("更新成功", sysStore);
        }
        return Result.error("更新失败");
    }

    /**
     * 更新当前登录门店的门店信息（仅门店账号可用）
     */
    @PutMapping("/current")
    public Result updateCurrent(@RequestBody SysStore sysStore, HttpServletRequest request) {
        boolean success = sysStoreService.updateCurrentStore(sysStore, request);
        if (success) {
            return Result.success("更新成功", sysStore);
        }
        return Result.error("更新失败，当前门店不存在或当前账号非门店账号");
    }

    /**
     * 根据ID删除门店信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = sysStoreService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除门店信息
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = sysStoreService.removeByIds(ids);
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
        List<SelectVO> list = sysStoreService.listUserInfo();
        return Result.success(list);
    }

}
