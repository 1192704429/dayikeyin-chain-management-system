package com.dake.catering.module.member_points_record.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dake.catering.module.member_points_record.entity.MemberPointsRecord;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 会员积分记录Service接口
 */
public interface MemberPointsRecordService extends IService<MemberPointsRecord> {

    /**
     * 分页查询
     */
    IPage<MemberPointsRecord> pageQuery(Integer current, Integer size, MemberPointsRecord entity);

    /**
     * 列表查询（不分页）
     */
    List<MemberPointsRecord> listQuery(MemberPointsRecord entity);

    /**
     * 根据ID查询
     */
    MemberPointsRecord getById(Long id);

    /**
     * 下拉数据：user_info
     */
    List<SelectVO> listUserInfo();

    /**
     * 下拉数据：member_info
     */
    List<SelectVO> listMemberInfo();

    /**
     * 下拉数据：sys_store
     */
    List<SelectVO> listSysStore();

    /**
     * 下拉数据：order_main
     */
    List<SelectVO> listOrderMain();

}
