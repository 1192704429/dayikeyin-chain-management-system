package com.dake.catering.module.store_dish_category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.store_dish_category.entity.StoreDishCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 门店菜品分类关联Mapper接口
 */
@Mapper
public interface StoreDishCategoryMapper extends BaseMapper<StoreDishCategory> {

    /**
     * 分页查询
     */
    List<StoreDishCategory> pageQuery(@Param("entity") StoreDishCategory entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") StoreDishCategory entity);

    /**
     * 列表查询（不分页）
     */
    List<StoreDishCategory> listQuery(@Param("entity") StoreDishCategory entity);

    /**
     * 根据ID查询
     */
    StoreDishCategory getById(@Param("id") Long id);

    /**
     * 下拉选项查询
     */
    /**
     * 下拉数据：dish_category
     */
    @Select("select category_name as label, id as value from dish_category")
    List<SelectVO> listDishCategory();

    /**
     * 下拉数据：sys_store
     */
    @Select("select store_name as label, id as value from sys_store")
    List<SelectVO> listSysStore();

}
