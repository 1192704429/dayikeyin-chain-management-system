package com.dake.catering.module.user_info.controller;

import com.dake.catering.common.Result;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.module.user_info.service.UserInfoService;
import com.dake.catering.vo.SelectVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wuming
 * @description: 系统用户Controller
 */
@RestController
@RequestMapping("/user_info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询系统用户列表
     */
    @PostMapping("/page")
    public Result page(@RequestParam(defaultValue = "1") Integer current,
                       @RequestParam(defaultValue = "10") Integer size,
                       @RequestBody(required = false) UserInfo entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new UserInfo();
        }
        // 店长只能查看本店用户
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setStoreId(currentStoreId);
        }
        return Result.success(userInfoService.pageQuery(current, size, entity));
    }

    /**
     * 查询所有系统用户列表
     */
    @PostMapping("/list")
    public Result list(@RequestBody(required = false) UserInfo entity,
                       HttpServletRequest request) {
        if (entity == null) {
            entity = new UserInfo();
        }
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            entity.setStoreId(currentStoreId);
        }
        List<UserInfo> list = userInfoService.listQuery(entity);
        return Result.success(list);
    }

    /**
     * 根据ID查询系统用户
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        UserInfo userInfo = userInfoService.getById(id);
        if (userInfo == null) {
            return Result.error("系统用户不存在");
        }
        return Result.success(userInfo);
    }

    /**
     * 新增系统用户
     */
    @PostMapping
    public Result save(@RequestBody UserInfo userInfo,
                       HttpServletRequest request) {
        // 校验登录账号是否重复
        if (userInfo.getUsername() != null && userInfo.getUsername().trim().length() > 0) {
            long count = userInfoService.lambdaQuery()
                    .eq(UserInfo::getUsername, userInfo.getUsername().trim())
                    .count();
            if (count > 0) {
                return Result.error("登录账号已存在，请更换后再保存");
            }
        }
        // 非管理员强制写入当前门店，避免越权创建其他门店账号
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            userInfo.setStoreId(currentStoreId);
            // 店长只能创建店员角色，不能创建管理员或店长
            String roleType = userInfo.getRoleType();
            if (roleType != null && !"clerk".equalsIgnoreCase(roleType)) {
                return Result.error("店长只能添加店员角色");
            }
            // 强制设置为店员角色
            userInfo.setRoleType("clerk");
        }
        userInfo.setCreateTime(LocalDateTime.now());
        boolean success = userInfoService.save(userInfo);
        if (success) {
            return Result.success("新增成功", userInfo);
        }
        return Result.error("新增失败");
    }

    /**
     * 更新系统用户
     */
    @PutMapping
    public Result update(@RequestBody UserInfo userInfo,
                         HttpServletRequest request) {
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            // 店长只能修改本店用户
            if (userInfo.getId() == null) {
                return Result.error("缺少用户ID，无法更新");
            }
            UserInfo dbUser = userInfoService.getById(userInfo.getId());
            if (dbUser == null) {
                return Result.error("系统用户不存在");
            }
            if (dbUser.getStoreId() == null || !currentStoreId.equals(dbUser.getStoreId())) {
                return Result.error("无权修改其他门店的用户信息");
            }
            // 店长不能修改用户角色为管理员或店长，只能保持为店员
            String roleType = userInfo.getRoleType();
            if (roleType != null && !"clerk".equalsIgnoreCase(roleType)) {
                return Result.error("店长只能将用户角色设置为店员");
            }
            // 防止越权修改门店和角色
            userInfo.setStoreId(currentStoreId);
            userInfo.setRoleType("clerk");
        }
        // 校验登录账号是否重复（排除自己）
        if (userInfo.getUsername() != null && userInfo.getUsername().trim().length() > 0 && userInfo.getId() != null) {
            long count = userInfoService.lambdaQuery()
                    .eq(UserInfo::getUsername, userInfo.getUsername().trim())
                    .ne(UserInfo::getId, userInfo.getId())
                    .count();
            if (count > 0) {
                return Result.error("登录账号已存在，请更换后再保存");
            }
        }
        boolean success = userInfoService.updateById(userInfo);
        if (success) {
            return Result.success("更新成功", userInfo);
        }
        return Result.error("更新失败");
    }

    /**
     * 根据ID删除系统用户
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id,
                         HttpServletRequest request) {
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null) {
            UserInfo dbUser = userInfoService.getById(id);
            if (dbUser == null) {
                return Result.error("系统用户不存在");
            }
            if (dbUser.getStoreId() == null || !currentStoreId.equals(dbUser.getStoreId())) {
                return Result.error("无权删除其他门店的用户");
            }
        }
        boolean success = userInfoService.removeById(id);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }

    /**
     * 批量删除系统用户
     */
    @DeleteMapping("/batch")
    public Result deleteBatch(@RequestBody List<Long> ids,
                              HttpServletRequest request) {
        Long currentStoreId = getCurrentUserStoreIdIfNotAdmin(request);
        if (currentStoreId != null && ids != null) {
            for (Long id : ids) {
                UserInfo dbUser = userInfoService.getById(id);
                if (dbUser == null) {
                    return Result.error("部分用户不存在，批量删除失败");
                }
                if (dbUser.getStoreId() == null || !currentStoreId.equals(dbUser.getStoreId())) {
                    return Result.error("无权删除其他门店的用户");
                }
            }
        }
        boolean success = userInfoService.removeByIds(ids);
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
        List<SelectVO> list = userInfoService.listSysStore();
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
        if (!StringUtils.hasLength(token)) {
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
        UserInfo currentUser = userInfoService.getById(userId);
        if (currentUser == null) {
            return null;
        }
        if (currentUser.getRoleType() != null && "admin".equalsIgnoreCase(String.valueOf(currentUser.getRoleType()))) {
            return null;
        }
        return currentUser.getStoreId();
    }

    private String redisTokenKey(String token) {
        return "chain_catering:login:token:" + token;
    }

}
