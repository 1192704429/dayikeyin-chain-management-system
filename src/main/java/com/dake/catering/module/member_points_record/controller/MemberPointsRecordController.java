package com.dake.catering.module.member_points_record.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.member_points_record.entity.MemberPointsRecord;
import com.dake.catering.module.member_points_record.service.MemberPointsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.dake.catering.vo.SelectVO;

/**
 * @author wuming
 * @description: 会员积分记录Controller
 */
@RestController
@RequestMapping("/member_points_record")
public class MemberPointsRecordController {

    @Autowired
    private MemberPointsRecordService memberPointsRecordService;

    /**
     * 分页查询会员积分记录列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) MemberPointsRecord entity) {
        if (entity == null) {
            entity = new MemberPointsRecord();
        }
        return Result.success(memberPointsRecordService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有会员积分记录列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) MemberPointsRecord entity) {
        if (entity == null) {
            entity = new MemberPointsRecord();
        }
        List<MemberPointsRecord> list = memberPointsRecordService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询会员积分记录
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        MemberPointsRecord memberPointsRecord = memberPointsRecordService.getById(id);
        if (memberPointsRecord == null) {
            return Result.error("会员积分记录不存在");
        }
        return Result.success(memberPointsRecord);
    }

    /**
     * 新增会员积分记录
     */
    @PostMapping
    public Result save(@RequestBody MemberPointsRecord memberPointsRecord) {
        memberPointsRecord.setCreateTime(LocalDateTime.now());
        boolean success = memberPointsRecordService.save(memberPointsRecord);
        if (success) {
            return Result.success("新增成功", memberPointsRecord);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新会员积分记录
     */
    @PutMapping
    public Result update(@RequestBody MemberPointsRecord memberPointsRecord) {
        boolean success = memberPointsRecordService.updateById(memberPointsRecord);
        if (success) {
            return Result.success("更新成功", memberPointsRecord);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除会员积分记录
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = memberPointsRecordService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除会员积分记录
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = memberPointsRecordService.removeByIds(ids);
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
        List<SelectVO> list = memberPointsRecordService.listUserInfo();
        return Result.success(list);
    }

    /**
     * 下拉数据接口：member_info
     */
    @GetMapping("/select/listMemberInfo")
    public Result listMemberInfo() {
        List<SelectVO> list = memberPointsRecordService.listMemberInfo();
        return Result.success(list);
    }

    /**
     * 下拉数据接口：sys_store
     */
    @GetMapping("/select/listSysStore")
    public Result listSysStore() {
        List<SelectVO> list = memberPointsRecordService.listSysStore();
        return Result.success(list);
    }

    /**
     * 下拉数据接口：order_main
     */
    @GetMapping("/select/listOrderMain")
    public Result listOrderMain() {
        List<SelectVO> list = memberPointsRecordService.listOrderMain();
        return Result.success(list);
    }

}
