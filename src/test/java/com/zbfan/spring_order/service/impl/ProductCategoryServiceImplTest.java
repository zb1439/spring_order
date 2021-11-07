package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl categoryService;

    @Test
    @Transactional
    public void findOne() {
        ProductCategory res = categoryService.findOne(1);
        Assert.assertEquals(new Integer(2), res.getCategoryType());
    }

    @Test
    public void findAll() {
        List<ProductCategory> res = categoryService.findAll();
        Assert.assertNotEquals(0, res.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> res = categoryService.findByCategoryTypeIn(Arrays.asList(0, 2, 3));
        Assert.assertNotEquals(0, res.size());
    }

    @Test
    public void save() {
        ProductCategory newCat = categoryService.save(new ProductCategory("new stuff"));
        assertNotNull(newCat);
    }
}