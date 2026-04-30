package com.dake.catering.module.member_info.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.member_info.entity.MemberInfo;
import com.dake.catering.module.member_info.service.MemberInfoService;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.module.user_info.service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import com.dake.catering.vo.SelectVO;

/**
 * @author wuming
 * @description: 会员信息Controller
 */
@RestController
@RequestMapping("/member_info")
public class MemberInfoController {

    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询会员信息列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) MemberInfo entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new MemberInfo();
        }
        // 非管理员按照当前登录用户所属门店做数据权限过滤
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setRegisterStoreId(currentStoreId);
        }
        return Result.success(memberInfoService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有会员信息列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) MemberInfo entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new MemberInfo();
        }
        // 非管理员按照当前登录用户所属门店做数据权限过滤
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setRegisterStoreId(currentStoreId);
        }
        List<MemberInfo> list = memberInfoService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询会员信息
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        MemberInfo memberInfo = memberInfoService.getById(id);
        if (memberInfo == null) {
            return Result.error("会员信息不存在");
        }
        return Result.success(memberInfo);
    }

    /**
     * 新增会员信息
     */
    @PostMapping
    public Result save(@RequestBody MemberInfo memberInfo,
                       HttpServletRequest request) {
        // 非管理员强制写入当前登录用户所属门店，避免越权注册到其他门店
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            memberInfo.setRegisterStoreId(currentStoreId);
        }
        // 自动填充注册时间（仅在未传入时）
        if (memberInfo.getRegisterTime() == null) {
            memberInfo.setRegisterTime(LocalDateTime.now());
        }
        memberInfo.setCreateTime(LocalDateTime.now());
        boolean success = memberInfoService.save(memberInfo);
        if (success) {
            return Result.success("新增成功", memberInfo);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新会员信息
     */
    @PutMapping
    public Result update(@RequestBody MemberInfo memberInfo,
                         HttpServletRequest request) {
        // 非管理员强制写入当前登录用户所属门店，避免修改到其他门店会员
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            memberInfo.setRegisterStoreId(currentStoreId);
        }
        boolean success = memberInfoService.updateById(memberInfo);
        if (success) {
            return Result.success("更新成功", memberInfo);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除会员信息
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        boolean success = memberInfoService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除会员信息
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids) {
        boolean success = memberInfoService.removeByIds(ids);
        if (success) {
            return Result.success("批量删除成功");
        }
        return Result.error("批量删除失败");
    }

    /**
     * 下拉数据接口：sys_store
     */
    @GetMapping("/select/listSysStore")
    public Result listSysStore() {
        List<SelectVO> list = memberInfoService.listSysStore();
        return Result.success(list);
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
        // 通过 Redis 中的登录态获取当前用户ID
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
        // 超级管理员不过滤
        if (userInfo.getRoleType() != null && "admin".equalsIgnoreCase(String.valueOf(userInfo.getRoleType()))) {
          return null;
        }
        return userInfo.getStoreId();
    }

    /**
     * 构造与登录模块一致的 Redis token key
     */
    private String redisTokenKey(String token) {
        return "chain_catering:login:token:" + token;
    }

}
