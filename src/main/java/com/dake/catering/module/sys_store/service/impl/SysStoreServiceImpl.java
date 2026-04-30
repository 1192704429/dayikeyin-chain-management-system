package com.dake.catering.module.sys_store.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.sys_store.entity.SysStore;
import com.dake.catering.module.sys_store.mapper.SysStoreMapper;
import com.dake.catering.module.sys_store.service.SysStoreService;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.module.user_info.service.UserInfoService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 门店信息Service实现类
 */
@Service
public class SysStoreServiceImpl extends ServiceImpl<SysStoreMapper, SysStore> implements SysStoreService {

    @Autowired
    private SysStoreMapper sysStoreMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public IPage<SysStore> pageQuery(Integer current, Integer size, SysStore entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<SysStore> records = sysStoreMapper.pageQuery(entity, offset, limit);
        Long total = sysStoreMapper.countQuery(entity);

        Page<SysStore> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<SysStore> listQuery(SysStore entity) {
        return sysStoreMapper.listQuery(entity);
    }

    @Override
    public SysStore getById(Long id) {
        return sysStoreMapper.getById(id);
    }

    /**
     * 下拉数据：user_info
     */
    @Override
    public List<SelectVO> listUserInfo() {
        return sysStoreMapper.listUserInfo();
    }

    @Override
    public SysStore getCurrentStore(HttpServletRequest request) {
        Long storeId = getCurrentUserStoreIdIfNotAdmin(request);
        if (storeId == null) {
            return null;
        }
        return sysStoreMapper.getById(storeId);
    }

    @Override
    public boolean updateCurrentStore(SysStore sysStore, HttpServletRequest request) {
        if (sysStore == null) {
            return false;
        }
        Long storeId = getCurrentUserStoreIdIfNotAdmin(request);
        if (storeId == null) {
            // 管理员或未找到门店，不允许通过该接口更新
            return false;
        }
        // 强制只更新当前门店
        sysStore.setId(storeId);
        return this.updateById(sysStore);
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
        if (userInfo.getRoleType() != null && "admin".equalsIgnoreCase(String.valueOf(userInfo.getRoleType()))) {
            return null;
        }
        return userInfo.getStoreId();
    }

    private String redisTokenKey(String token) {
        return "chain_catering:login:token:" + token;
    }

}
