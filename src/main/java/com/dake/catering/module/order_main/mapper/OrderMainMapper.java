package com.dake.catering.module.order_main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.order_main.entity.OrderMain;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 订单主Mapper接口
 */
@Mapper
public interface OrderMainMapper extends BaseMapper<OrderMain> {

    /**
     * 分页查询
     */
    List<OrderMain> pageQuery(@Param("entity") OrderMain entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") OrderMain entity);

    /**
     * 列表查询（不分页）
     */
    List<OrderMain> listQuery(@Param("entity") OrderMain entity);

    /**
     * 根据ID查询
     */
    OrderMain getById(@Param("id") Long id);

    /**
     * 下拉选项查询
     */
    /**
     * 下拉数据：user_info
     */
    @Select("select real_name as label, id as value from user_info")
    List<SelectVO> listUserInfo();

    /**
     * 下拉数据：sys_store
     */
    @Select("select store_name as label, id as value from sys_store")
    List<SelectVO> listSysStore();

    /**
     * 下拉数据：member_info
     */
    @Select("select real_name as label, id as value from member_info")
    List<SelectVO> listMemberInfo();

}
