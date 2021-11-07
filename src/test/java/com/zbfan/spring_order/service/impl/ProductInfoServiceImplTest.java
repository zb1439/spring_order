package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.dataobject.ProductInfo;
import com.zbfan.spring_order.enums.ProductStatusEnum;
import com.zbfan.spring_order.enums.ResultEnum;
import com.zbfan.spring_order.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl service;

    private static String TEST_PRODUCT_ID = "111111";

    @Test
    public void findOne() {
        ProductInfo info = service.findOne("123456");
        Assert.assertEquals("123456", info.getProductId());
    }

    @Test
    public void findOnlineProducts() {
        List<ProductInfo> onlineProducts = service.findOnlineProducts();
        Assert.assertNotEquals(0, onlineProducts.size());
    }

    @Test
    public void findAll() {
        PageRequest req = PageRequest.of(0, 2);
        Page<ProductInfo> allProductsPage = service.findAll(req);
        assertNotEquals(0, allProductsPage.getTotalElements());
    }

    @Test
    public void save() {
        ProductInfo info = new ProductInfo();
        info.setProductId("123457");
        info.setProductName("test product2");
        info.setProductPrice(new BigDecimal(10));
        info.setProductStock(100);
        info.setProductDescription("something also tasty");
        info.setProductStatus(ProductStatusEnum.OFFLINE.getCode());
        info.setCategoryType(2);
        ProductInfo res = service.save(info);
        Assert.assertNotNull(res);
    }

    @Test
    public void onSale() {
        duplicateRow("123456", ProductStatusEnum.OFFLINE.getCode());
        ProductInfo productInfo = service.onSale(TEST_PRODUCT_ID);
        Assert.assertEquals(ProductStatusEnum.ONLINE.getCode(), productInfo.getProductStatus());
    }

    @Test
    public void offSale() {
        duplicateRow("123456", ProductStatusEnum.ONLINE.getCode());
        ProductInfo productInfo = service.offSale(TEST_PRODUCT_ID);
        Assert.assertEquals(ProductStatusEnum.OFFLINE.getCode(), productInfo.getProductStatus());
    }

    private ProductInfo duplicateRow(String productId, Integer productStatusCode) {
        ProductInfo productInfo = service.findOne(productId);
        productInfo.setProductId(TEST_PRODUCT_ID);
        productInfo.setProductStatus(productStatusCode);
        return service.save(productInfo);
    }
}