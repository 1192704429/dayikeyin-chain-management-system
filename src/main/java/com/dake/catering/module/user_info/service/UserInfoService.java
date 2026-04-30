package com.dake.catering.module.user_info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.user_info.entity.UserInfo;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 系统用户Service接口
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 分页查询
     */
    IPage<UserInfo> pageQuery(Integer current, Integer size, UserInfo entity);

    /**
     * 列表查询（不分页）
     */
    List<UserInfo> listQuery(UserInfo entity);

    /**
     * 根据ID查询
     */
    UserInfo getById(Long id);

    /**
     * 下拉数据：sys_store
     */
    List<SelectVO> listSysStore();

}
