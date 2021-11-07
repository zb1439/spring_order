package com.zbfan.spring_order.dataobject.repository;

import com.zbfan.spring_order.dataobject.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void testSave() {
        ProductInfo info = new ProductInfo();
        info.setProductId("123456");
        info.setProductName("test product");
        info.setProductPrice(new BigDecimal(10));
        info.setProductStock(100);
        info.setProductDescription("something tasty");
        info.setProductStatus(0);
        info.setCategoryType(2);
        repository.save(info);
    }

    @Test
    public void findByProductStatus() {
        List<ProductInfo> res = repository.findByProductStatus(0);
        assertNotEquals(0, res.size());
    }

}