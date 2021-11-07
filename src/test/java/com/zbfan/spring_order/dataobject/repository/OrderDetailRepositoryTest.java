package com.zbfan.spring_order.dataobject.repository;

import com.zbfan.spring_order.dataobject.OrderDetail;
import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    private List<OrderDetail> mock() {
        List<OrderDetail> res = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("123456");
        orderDetail.setProductPrice(new BigDecimal(10));
        orderDetail.setProductQuantity(1);
        orderDetail.setProductName("test product");
        orderDetail.setProductIcon("xxx.jpg");
        res.add(orderDetail);

        orderDetail = new OrderDetail();
        orderDetail.setDetailId("123457");
        orderDetail.setOrderId("123456");
        orderDetail.setProductId("123456");
        orderDetail.setProductPrice(new BigDecimal(10));
        orderDetail.setProductQuantity(1);
        orderDetail.setProductName("test product2");
        orderDetail.setProductIcon("xxx.jpg");
        res.add(orderDetail);

        return res;
    }

    @Test
    public void testSave() {
        List<OrderDetail> details = mock();
        for (OrderDetail detail : details) {
            OrderDetail res = repository.save(detail);
            Assert.assertNotNull(res);
        }
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> res = repository.findByOrderId("123456");
        Assert.assertNotEquals(0, res.size());
    }

}