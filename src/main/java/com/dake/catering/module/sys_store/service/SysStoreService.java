package com.dake.catering.module.sys_store.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.sys_store.entity.SysStore;
import com.dake.catering.vo.SelectVO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * @author wuming
 * @description: 门店信息Service接口
 */
public interface SysStoreService extends IService<SysStore> {

    /**
     * 分页查询
     */
    IPage<SysStore> pageQuery(Integer current, Integer size, SysStore entity);

    /**
     * 列表查询（不分页）
     */
    List<SysStore> listQuery(SysStore entity);

    /**
     * 根据ID查询
     */
    SysStore getById(Long id);

    /**
     * 下拉数据：user_info
     */
    List<SelectVO> listUserInfo();

    /**
     * 根据当前登录用户所属门店ID查询门店信息（管理员返回 null）
     */
    SysStore getCurrentStore(HttpServletRequest request);

    /**
     * 根据当前登录用户所属门店ID更新门店信息（管理员不允许调用，返回 false）
     */
    boolean updateCurrentStore(SysStore sysStore, HttpServletRequest request);

}
