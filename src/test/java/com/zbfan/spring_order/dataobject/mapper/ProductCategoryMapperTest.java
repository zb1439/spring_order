package com.zbfan.spring_order.dataobject.mapper;

import com.zbfan.spring_order.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("category_name", "mybatis");
        map.put("category_type", 101);
        int res = mapper.insertByMap(map);
        Assert.assertEquals(1, res);
    }

    @Test
    public void insertByObject() {
        ProductCategory category = new ProductCategory();
        category.setCategoryType(102);
        category.setCategoryName("mybatis");
        int res = mapper.insertByObject(category);
        Assert.assertEquals(1, res);
    }

    @Test
    public void findByCategoryType() {
        ProductCategory category = mapper.findByCategoryType(102);
        Assert.assertNotNull(category);
    }

    @Test
    public void findByCategoryName() {
        List<ProductCategory> categoryList = mapper.findByCategoryName("mybatis");
        Assert.assertNotEquals(0, categoryList.size());
    }

    @Test
    public void updateByCategoryType() {
        int res = mapper.updateByCategoryType(102, "mybatis2");
        Assert.assertEquals(1, res);
    }

    @Test
    public void updateByObject() {
        ProductCategory category = new ProductCategory();
        category.setCategoryType(102);
        category.setCategoryName("mybatis2");
        int res = mapper.updateByObject(category);
        Assert.assertEquals(1, res);
    }

    @Test
    public void deleteByCategoryType() {
        int res = mapper.deleteByCategory(102);
        Assert.assertEquals(1, res);
    }

    @Test
    public void selectByCategoryType() {
        ProductCategory category = mapper.selectByCategoryType(102);
        Assert.assertNotNull(category);
    }
}