package com.zbfan.spring_order.dataobject.repository;

import com.zbfan.spring_order.dataobject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;

    @Test
    @Transactional
    public void findOneTest() {
        ProductCategory cat = repository.getById(1);
        System.out.println(cat.toString());
    }

    @Test
    @Transactional
    public void testSave() {
        ProductCategory cat = new ProductCategory();
        cat.setCategoryName("another");
        cat.setCategoryType(3);
        ProductCategory result = repository.save(cat);
        Assert.assertNotNull(result);
    }

    @Test
    @Transactional
    public void testBatchFind() {
        List<Integer> queryInts = Arrays.asList(2, 3, 4);
        List<ProductCategory> result = repository.findByCategoryTypeIn(queryInts);
        Assert.assertNotEquals(0, result.size());
    }
}