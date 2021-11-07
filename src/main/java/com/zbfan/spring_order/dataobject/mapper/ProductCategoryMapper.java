package com.zbfan.spring_order.dataobject.mapper;

import com.zbfan.spring_order.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ProductCategoryMapper {

    @Insert("insert into product_category(category_name, category_type) " +
            "values (#{category_name, jdbcType=VARCHAR}, #{category_type, jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Insert("insert into product_category(category_name, category_type) " +
            "values (#{categoryName, jdbcType=VARCHAR}, #{categoryType, jdbcType=INTEGER})")
    int insertByObject(ProductCategory category);

    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName")
    })
    ProductCategory findByCategoryType(Integer categoryType);

    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_type", property = "categoryType"),
            @Result(column = "category_id", property = "categoryId"),
            @Result(column = "category_name", property = "categoryName")
    })
    List<ProductCategory> findByCategoryName(String categoryName);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByCategoryType(@Param("categoryType") Integer categoryType,
                             @Param("categoryName")  String categoryName);

    @Update("update product_category set category_name = #{categoryName} where category_type = #{categoryType}")
    int updateByObject(ProductCategory category);

    @Delete("delete from product_category where category_type = #{categoryType}")
    int deleteByCategory(Integer categoryType);

    // This is an interface driven by xml
    ProductCategory selectByCategoryType(Integer categoryType);
}
