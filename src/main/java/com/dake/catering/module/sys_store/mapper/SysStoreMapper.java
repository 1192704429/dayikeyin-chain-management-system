package com.dake.catering.module.sys_store.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.sys_store.entity.SysStore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 门店信息Mapper接口
 */
@Mapper
public interface SysStoreMapper extends BaseMapper<SysStore> {

    /**
     * 分页查询
     */
    List<SysStore> pageQuery(@Param("entity") SysStore entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") SysStore entity);

    /**
     * 列表查询（不分页）
     */
    List<SysStore> listQuery(@Param("entity") SysStore entity);

    /**
     * 根据ID查询
     */
    SysStore getById(@Param("id") Long id);

    /**
     * 下拉选项查询
     */
    /**
     * 下拉数据：user_info
     */
    @Select("select real_name as label, id as value from user_info")
    List<SelectVO> listUserInfo();

}
