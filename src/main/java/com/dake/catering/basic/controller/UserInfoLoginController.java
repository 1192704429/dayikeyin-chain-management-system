package com.dake.catering.basic.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.dake.catering.common.Result;
import com.dake.catering.module.sys_store.entity.SysStore;
import com.dake.catering.module.sys_store.service.SysStoreService;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.module.user_info.service.UserInfoService;
import com.dake.catering.util.JwtUtil;
import com.dake.catering.vo.LoginVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @author wuming
 * @description: 系统用户登录接口
 */
@RestController
@RequestMapping("/user_info/login")
public class UserInfoLoginController {

    private static final String REDIS_TOKEN_PREFIX = "chain_catering:login:token:";
    private static final String REDIS_USER_PREFIX = "chain_catering:login:user:";

    @Value("${check-captcha}")
    private boolean checkCaptcha;

    @Value("${spring.application.name}")
    private String secret;

    @Value("${login.cache-ttl-hours:24}")
    private long loginCacheTtlHours;

    @Autowired
    private JwtUtil<LoginUser> jwtUtil;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysStoreService sysStoreService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录接口
     *
     * @param loginVO 登录信息
     */
    @PostMapping
    public Result login(@RequestBody LoginVO loginVO) {
        if (StrUtil.isEmpty(loginVO.getUsername())) {
            return Result.error("登录用户名不能为空");
        }
        if (StrUtil.isEmpty(loginVO.getPassword())) {
            return Result.error("登录密码不能为空");
        }
        // 是否开启验证码校验（结构优化：逻辑不变）
        Result captchaCheck = verifyCaptchaIfEnabled(loginVO);
        if (captchaCheck != null) {
            return captchaCheck;
        }
        // 查询用户信息
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUsername, loginVO.getUsername());
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        // 校验用户信息
        if (userInfo == null) {
            return Result.error("用户不存在");
        }
        // 校验账号状态：必须为启用状态 1
        if (userInfo.getStatus() == null || userInfo.getStatus() != 1) {
            return Result.error("账号已被禁用，请联系管理员");
        }
        // 校验门店状态：必须为启用状态 1
        if (userInfo.getStoreId() != null) {
            SysStore sysStore = sysStoreService.getById(userInfo.getStoreId());
            if (sysStore == null || sysStore.getStatus() == null || sysStore.getStatus() != 1) {
                return Result.error("所属门店已停用，无法登录");
            }
        }
        // 校验密码
        if (!loginVO.getPassword().equals(sha256(userInfo.getPassword()))) {
            return Result.error("密码错误");
        }
        // 组装登录用户信息
        LoginUser loginUser = new LoginUser();
        loginUser.setId(userInfo.getId());
        loginUser.setName(userInfo.getRealName());
        loginUser.setAvatar(StrUtil.isNotEmpty(userInfo.getAvatar()) ? userInfo.getAvatar() : "https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2022-05-20/1ef4592a-ff86-424a-845a-260a58a550a4_logo.jpg");
        loginUser.setIntroduction("I am a super administrator");
        loginUser.setRole(String.valueOf(userInfo.getRoleType()));
        String token = jwtUtil.createToken(loginUser);
        // 登录成功后：使用 Redis 缓存用户信息（按 token 与 userId 双索引）
        Duration ttl = Duration.ofHours(loginCacheTtlHours);
        stringRedisTemplate.opsForValue().set(redisTokenKey(token), String.valueOf(userInfo.getId()), ttl);
        stringRedisTemplate.opsForValue().set(redisUserKey(userInfo.getId()), JSON.toJSONString(loginUser), ttl);
        return Result.success(token);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    public Result info(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StrUtil.isEmpty(authorization)) {
            return Result.error(401, "尚未登录~");
        }
        // 以 Redis 是否存在 token 作为登录态依据（TTL 控制过期）
        String userIdStr = stringRedisTemplate.opsForValue().get(redisTokenKey(authorization));
        if (!StringUtils.hasLength(userIdStr)) {
            return Result.error(401, "登录状态已过期~");
        }
        boolean verifiedToken = jwtUtil.verifyToken(authorization);
        if (!verifiedToken) {
            return Result.error(401, "登录状态已过期~");
        }
        Long userId = Long.parseLong(userIdStr);

        // 优先从 Redis 获取用户信息
        String cachedUserJson = stringRedisTemplate.opsForValue().get(redisUserKey(userId));
        if (StringUtils.hasLength(cachedUserJson)) {
            return Result.success(JSON.parseObject(cachedUserJson, LoginUser.class));
        }

        // 缓存未命中：查库并回写缓存
        UserInfo userInfo = userInfoService.getById(userId);
        if (userInfo == null) {
            // 数据不存在时清理 token 缓存，避免脏登录态
            stringRedisTemplate.delete(redisTokenKey(authorization));
            return Result.error(401, "登录状态已过期~");
        }
        LoginUser loginUserNew = buildLoginUser(userInfo);
        Duration ttl = Duration.ofHours(loginCacheTtlHours);
        stringRedisTemplate.opsForValue().set(redisUserKey(userId), JSON.toJSONString(loginUserNew), ttl);
        return Result.success(loginUserNew);
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StrUtil.isNotEmpty(token)) {
            // 删除 token 缓存，同时尽量删除关联的用户缓存
            String userIdStr = stringRedisTemplate.opsForValue().get(redisTokenKey(token));
            if (StringUtils.hasLength(userIdStr)) {
                stringRedisTemplate.delete(redisUserKey(Long.parseLong(userIdStr)));
            }
            stringRedisTemplate.delete(redisTokenKey(token));
            jwtUtil.expireTokenImmediately(token);
        }
        return Result.success("退出登录成功");
    }

    /**
     * 根据用户编号获取用户信息
     *
     * @param id 用户编号
     * @return 用户信息
     */
    @GetMapping("/getUserInfoById")
    public Result getUserInfoById(@RequestParam("id") Integer id) {
        return Result.success(userInfoService.getById(id));
    }

    /**
     * 修改个人信息
     *
     * @param user 用户实体
     * @return 结果
     */
    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserInfo user) {
        userInfoService.updateById(user);
        // 用户信息变更：删除用户缓存，避免返回旧数据
        if (user != null && user.getId() != null) {
            stringRedisTemplate.delete(redisUserKey(user.getId()));
        }
        return Result.success("修改成功");
    }

    /**
     * 修改密码
     *
     * @param updatePasswordDTO 修改密码参数
     * @return 结果
     */
    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
        UserInfo user = userInfoService.getById(updatePasswordDTO.getId());
        if (user == null) {
            return Result.error("修改失败，用户信息不存在~");
        }
        if (!sha256(user.getPassword()).equals(updatePasswordDTO.getOldPassword())) {
            return Result.error("修改失败，旧密码不一致~");
        }
        if (user.getPassword().equals(updatePasswordDTO.getNewPassword())) {
            return Result.error("修改失败，新密码与旧密码一致~");
        }
        UserInfo updateUser = new UserInfo();
        updateUser.setId(updatePasswordDTO.getId());
        updateUser.setPassword(updatePasswordDTO.getNewPassword());
        userInfoService.updateById(updateUser);
        // 改密后：删除用户缓存（下次重新加载）
        if (updatePasswordDTO.getId() != null) {
            stringRedisTemplate.delete(redisUserKey(updatePasswordDTO.getId()));
        }
        return Result.success("修改成功~");
    }

    /**
     * SHA-256 加密方法
     *
     * @param input 原始字符串
     * @return 加密后的字符串
     */
    private String sha256(String input) {
        try {
            String newInput = secret + input;
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(newInput.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    static class UpdatePasswordDTO {

        private String oldPassword;

        private String newPassword;

        private Long id;
    }

    /**
     * 登录用户信息
     */
    @Data
    static public class LoginUser {

        private Long id;

        private String name;

        private String avatar;

        private String introduction;

        private String role;
    }

    private String redisTokenKey(String token) {
        return REDIS_TOKEN_PREFIX + token;
    }

    private String redisUserKey(Long userId) {
        return REDIS_USER_PREFIX + userId;
    }

    private LoginUser buildLoginUser(UserInfo userInfo) {
        LoginUser loginUser = new LoginUser();
        loginUser.setId(userInfo.getId());
        loginUser.setName(userInfo.getRealName());
        loginUser.setAvatar(StrUtil.isNotEmpty(userInfo.getAvatar()) ? userInfo.getAvatar() : "https://code-graden-server-dev.oss-cn-beijing.aliyuncs.com/2022-05-20/1ef4592a-ff86-424a-845a-260a58a550a4_logo.jpg");
        loginUser.setIntroduction("I am a super administrator");
        loginUser.setRole(String.valueOf(userInfo.getRoleType()));
        return loginUser;
    }

    /**
     * 校验验证码（仅在开启时执行）
     * 返回：null 表示通过；非 null 表示校验失败的 Result（与原逻辑保持一致）
     */
    private Result verifyCaptchaIfEnabled(LoginVO loginVO) {
        if (!checkCaptcha) {
            return null;
        }

        String captcha = loginVO.getCaptcha();
        if (!StringUtils.hasLength(captcha)) {
            return Result.error("验证码不能为空");
        }

        String captchaKey = loginVO.getCaptchaKey();
        Map<String, Object> captchaInfo = CaptchaController.CAPTCHA_CACHE_MAP.get(captchaKey);
        if (captchaInfo == null) {
            return Result.error("验证码不存在");
        }

        String code = (String) captchaInfo.get("code");
        long timestamp = (long) captchaInfo.get("timestamp");

        // 校验验证码是否过期（过期后删除缓存）
        if (System.currentTimeMillis() - timestamp > CaptchaController.EXPIRATION_TIME) {
            CaptchaController.CAPTCHA_CACHE_MAP.remove(captchaKey);
            return Result.error("验证码已过期");
        }

        // 校验验证码是否正确
        if (!captcha.equalsIgnoreCase(code)) {
            return Result.error("验证码错误");
        }

        // 校验通过删除验证码缓存
        CaptchaController.CAPTCHA_CACHE_MAP.remove(captchaKey);
        return null;
    }

}
