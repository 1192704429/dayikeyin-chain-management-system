package com.dake.catering.module.user_info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.user_info.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 系统用户Mapper接口
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 分页查询
     */
    List<UserInfo> pageQuery(@Param("entity") UserInfo entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") UserInfo entity);

    /**
     * 列表查询（不分页）
     */
    List<UserInfo> listQuery(@Param("entity") UserInfo entity);

    /**
     * 根据ID查询
     */
    UserInfo getById(@Param("id") Long id);

    /**
     * 下拉选项查询
     */
    /**
     * 下拉数据：sys_store
     */
    @Select("select store_name as label, id as value from sys_store")
    List<SelectVO> listSysStore();

}
