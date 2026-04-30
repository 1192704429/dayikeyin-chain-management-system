package com.dake.catering.module.user_info.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.module.user_info.mapper.UserInfoMapper;
import com.dake.catering.module.user_info.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 系统用户Service实现类
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public IPage<UserInfo> pageQuery(Integer current, Integer size, UserInfo entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<UserInfo> records = userInfoMapper.pageQuery(entity, offset, limit);
        Long total = userInfoMapper.countQuery(entity);

        Page<UserInfo> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<UserInfo> listQuery(UserInfo entity) {
        return userInfoMapper.listQuery(entity);
    }

    @Override
    public UserInfo getById(Long id) {
        return userInfoMapper.getById(id);
    }

    /**
     * 下拉数据：sys_store
     */
    @Override
    public List<SelectVO> listSysStore() {
        return userInfoMapper.listSysStore();
    }

}
