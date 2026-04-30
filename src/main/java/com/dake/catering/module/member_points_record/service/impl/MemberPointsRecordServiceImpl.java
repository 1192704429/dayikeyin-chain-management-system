package com.dake.catering.module.member_points_record.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.member_points_record.entity.MemberPointsRecord;
import com.dake.catering.module.member_points_record.mapper.MemberPointsRecordMapper;
import com.dake.catering.module.member_points_record.service.MemberPointsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 会员积分记录Service实现类
 */
@Service
public class MemberPointsRecordServiceImpl extends ServiceImpl<MemberPointsRecordMapper, MemberPointsRecord> implements MemberPointsRecordService {

    @Autowired
    private MemberPointsRecordMapper memberPointsRecordMapper;

    @Override
    public IPage<MemberPointsRecord> pageQuery(Integer current, Integer size, MemberPointsRecord entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<MemberPointsRecord> records = memberPointsRecordMapper.pageQuery(entity, offset, limit);
        Long total = memberPointsRecordMapper.countQuery(entity);

        Page<MemberPointsRecord> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<MemberPointsRecord> listQuery(MemberPointsRecord entity) {
        return memberPointsRecordMapper.listQuery(entity);
    }

    @Override
    public MemberPointsRecord getById(Long id) {
        return memberPointsRecordMapper.getById(id);
    }

    /**
     * 下拉数据：user_info
     */
    @Override
    public List<SelectVO> listUserInfo() {
        return memberPointsRecordMapper.listUserInfo();
    }

    /**
     * 下拉数据：member_info
     */
    @Override
    public List<SelectVO> listMemberInfo() {
        return memberPointsRecordMapper.listMemberInfo();
    }

    /**
     * 下拉数据：sys_store
     */
    @Override
    public List<SelectVO> listSysStore() {
        return memberPointsRecordMapper.listSysStore();
    }

    /**
     * 下拉数据：order_main
     */
    @Override
    public List<SelectVO> listOrderMain() {
        return memberPointsRecordMapper.listOrderMain();
    }

}
