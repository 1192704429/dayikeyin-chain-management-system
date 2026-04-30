package com.dake.catering.module.dish_category.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.dish_category.entity.DishCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wuming
 * @description: 菜品分类Mapper接口
 */
@Mapper
public interface DishCategoryMapper extends BaseMapper<DishCategory> {

    /**
     * 分页查询
     */
    List<DishCategory> pageQuery(@Param("entity") DishCategory entity,
                             @Param("offset") Long offset,
                             @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") DishCategory entity);

    /**
     * 列表查询（不分页）
     */
    List<DishCategory> listQuery(@Param("entity") DishCategory entity);

    /**
     * 根据ID查询
     */
    DishCategory getById(@Param("id") Long id);

}
