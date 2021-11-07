package com.zbfan.spring_order.dataobject.repository;

import com.zbfan.spring_order.dataobject.OrderMaster;
import com.zbfan.spring_order.enums.OrderStatusEnum;
import com.zbfan.spring_order.enums.PayStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private String OPENID = "zb1439";


    private OrderMaster mock() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("zbfan");
        orderMaster.setBuyerPhone("5102804419");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setBuyerAddress("2024 Vine St");
        orderMaster.setOrderAmount(new BigDecimal(23.1));
        return orderMaster;
    }

    @Test
    public void testSave() {
        OrderMaster result = repository.save(mock());
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        Page<OrderMaster> res = repository.findByBuyerOpenid("zb1439", PageRequest.of(1, 2));
        Assert.assertNotEquals(0, res.getTotalElements());
    }
}