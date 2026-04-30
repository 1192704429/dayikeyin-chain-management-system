package com.dake.catering.module.member_points_record.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.member_points_record.entity.MemberPointsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 会员积分记录Mapper接口
 */
@Mapper
public interface MemberPointsRecordMapper extends BaseMapper<MemberPointsRecord> {

    /**
     * 分页查询
     */
    List<MemberPointsRecord> pageQuery(@Param("entity") MemberPointsRecord entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") MemberPointsRecord entity);

    /**
     * 列表查询（不分页）
     */
    List<MemberPointsRecord> listQuery(@Param("entity") MemberPointsRecord entity);

    /**
     * 根据ID查询
     */
    MemberPointsRecord getById(@Param("id") Long id);

    /**
     * 下拉选项查询
     */
    /**
     * 下拉数据：user_info
     */
    @Select("select real_name as label, id as value from user_info")
    List<SelectVO> listUserInfo();

    /**
     * 下拉数据：member_info
     */
    @Select("select real_name as label, id as value from member_info")
    List<SelectVO> listMemberInfo();

    /**
     * 下拉数据：sys_store
     */
    @Select("select store_name as label, id as value from sys_store")
    List<SelectVO> listSysStore();

    /**
     * 下拉数据：order_main
     */
    @Select("select order_no as label, id as value from order_main")
    List<SelectVO> listOrderMain();

}
