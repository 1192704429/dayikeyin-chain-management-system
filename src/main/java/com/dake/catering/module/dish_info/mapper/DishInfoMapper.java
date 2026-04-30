package com.dake.catering.module.dish_info.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.dish_info.entity.DishInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 菜品信息Mapper接口
 */
@Mapper
public interface DishInfoMapper extends BaseMapper<DishInfo> {

    /**
     * 分页查询
     */
    List<DishInfo> pageQuery(@Param("entity") DishInfo entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") DishInfo entity);

    /**
     * 列表查询（不分页）
     */
    List<DishInfo> listQuery(@Param("entity") DishInfo entity);

    /**
     * 根据ID查询
     */
    DishInfo getById(@Param("id") Long id);

    /**
     * 下拉选项查询
     */
    /**
     * 下拉数据：dish_category
     */
    @Select("select category_name as label, id as value from dish_category")
    List<SelectVO> listDishCategory();

}
