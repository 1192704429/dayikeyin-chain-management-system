package com.dake.catering.module.member_info.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dake.catering.module.member_info.entity.MemberInfo;
import com.dake.catering.module.member_info.mapper.MemberInfoMapper;
import com.dake.catering.module.member_info.service.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 会员信息Service实现类
 */
@Service
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoMapper, MemberInfo> implements MemberInfoService {

    @Autowired
    private MemberInfoMapper memberInfoMapper;

    @Override
    public IPage<MemberInfo> pageQuery(Integer current, Integer size, MemberInfo entity) {
        Long offset = (long) (current - 1) * size;
        Long limit = (long) size;
        List<MemberInfo> records = memberInfoMapper.pageQuery(entity, offset, limit);
        Long total = memberInfoMapper.countQuery(entity);

        Page<MemberInfo> page = new Page<>(current, size);
        page.setRecords(records);
        page.setTotal(total);
        return page;
    }

    @Override
    public List<MemberInfo> listQuery(MemberInfo entity) {
        return memberInfoMapper.listQuery(entity);
    }

    @Override
    public MemberInfo getById(Long id) {
        return memberInfoMapper.getById(id);
    }

    /**
     * 下拉数据：sys_store
     */
    @Override
    public List<SelectVO> listSysStore() {
        return memberInfoMapper.listSysStore();
    }

}
