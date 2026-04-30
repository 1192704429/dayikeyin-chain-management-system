package com.dake.catering.module.member_info.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.member_info.entity.MemberInfo;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 会员信息Service接口
 */
public interface MemberInfoService extends IService<MemberInfo> {

    /**
     * 分页查询
     */
    IPage<MemberInfo> pageQuery(Integer current, Integer size, MemberInfo entity);

    /**
     * 列表查询（不分页）
     */
    List<MemberInfo> listQuery(MemberInfo entity);

    /**
     * 根据ID查询
     */
    MemberInfo getById(Long id);

    /**
     * 下拉数据：sys_store
     */
    List<SelectVO> listSysStore();

}
