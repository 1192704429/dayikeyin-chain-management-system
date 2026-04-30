package com.dake.catering.module.member_info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.member_info.entity.MemberInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 会员信息Mapper接口
 */
@Mapper
public interface MemberInfoMapper extends BaseMapper<MemberInfo> {

    /**
     * 分页查询
     */
    List<MemberInfo> pageQuery(@Param("entity") MemberInfo entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") MemberInfo entity);

    /**
     * 列表查询（不分页）
     */
    List<MemberInfo> listQuery(@Param("entity") MemberInfo entity);

    /**
     * 根据ID查询
     */
    MemberInfo getById(@Param("id") Long id);

    /**
     * 下拉选项查询
     */
    /**
     * 下拉数据：sys_store
     */
    @Select("select store_name as label, id as value from sys_store")
    List<SelectVO> listSysStore();

}
