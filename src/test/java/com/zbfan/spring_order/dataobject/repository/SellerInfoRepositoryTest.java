package com.zbfan.spring_order.dataobject.repository;

import com.zbfan.spring_order.dataobject.SellerInfo;
import com.zbfan.spring_order.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        String id = KeyUtil.genUniqueKey();
        sellerInfo.setSellerId(id);
        sellerInfo.setUsername("zb1439");
        sellerInfo.setPassword("123456");
        sellerInfo.setOpenid("oTgZpwctdOo-569GR8E8VHGTqaDE");
        SellerInfo res = repository.save(sellerInfo);
        Assert.assertEquals(id, res.getSellerId());
    }

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = repository.findByOpenid("oTgZpwctdOo-569GR8E8VHGTqaDE");
        Assert.assertNotNull(sellerInfo);
    }
}