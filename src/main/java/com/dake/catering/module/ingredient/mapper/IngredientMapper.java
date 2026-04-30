package com.dake.catering.module.ingredient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dake.catering.module.ingredient.entity.Ingredient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.dake.catering.vo.SelectVO;

import java.util.List;

/**
 * @author wuming
 * @description: 原材料Mapper接口
 */
@Mapper
public interface IngredientMapper extends BaseMapper<Ingredient> {

    /**
     * 分页查询
     */
    List<Ingredient> pageQuery(@Param("entity") Ingredient entity,
                               @Param("offset") Long offset,
                               @Param("limit") Long limit);

    /**
     * 查询总数
     */
    Long countQuery(@Param("entity") Ingredient entity);

    /**
     * 列表查询（不分页）
     */
    List<Ingredient> listQuery(@Param("entity") Ingredient entity);

    /**
     * 根据ID查询
     */
    Ingredient getById(@Param("id") Long id);

    /**
     * 下拉数据：ingredient
     */
    @Select("select ingredient_name as label, id as value from ingredient where status = 1")
    List<SelectVO> listIngredient();

}
