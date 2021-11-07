package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.dataobject.OrderDetail;
import com.zbfan.spring_order.dto.OrderDTO;
import com.zbfan.spring_order.enums.OrderStatusEnum;
import com.zbfan.spring_order.enums.PayStatusEnum;
import com.zbfan.spring_order.enums.ResultEnum;
import com.zbfan.spring_order.exception.SellException;
import org.aspectj.weaver.ast.Or;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl service;

    private String TEST_BUYER_OPENID = "zb1439";

    private OrderDTO mock(String orderId, List<String> productIds, List<Integer> quantities) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("zbfan");
        orderDTO.setBuyerPhone("5102804419");
        orderDTO.setBuyerAddress("2024 Vine St");
        orderDTO.setBuyerOpenid(TEST_BUYER_OPENID);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (int i = 0; i < quantities.size(); i += 1) {
            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setDetailId("123458");  // does not matter, will be replaced
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(productIds.get(i));
            orderDetail.setProductPrice(new BigDecimal(10));
            orderDetail.setProductQuantity(quantities.get(i));
            orderDetail.setProductName("test product");
            orderDetail.setProductIcon("xxx.jpg");
            orderDetailList.add(orderDetail);
        }

        orderDTO.setOrderId(orderId);
        orderDTO.setOrderDetailList(orderDetailList);
        orderDTO.setOrderAmount(new BigDecimal(39.9));
//        orderDTO.setOrderStatus(OrderStatusEnum.NEW.getCode());
//        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        return orderDTO;
    }

    @Test
    public void createOrder() {
        OrderDTO orderDTO = mock("1234568", Arrays.asList("123456", "123457"), Arrays.asList(2, 2));

        try {
            OrderDTO dtoRes = service.createOrder(orderDTO);
            Assert.assertNotNull(dtoRes);
        } catch (SellException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    @Transactional
    public void createOrderStockFailure() {
        OrderDTO orderDTO = mock("1234569", Arrays.asList("123456", "123457"),
                Arrays.asList(Integer.MAX_VALUE, Integer.MAX_VALUE));
        try {
            service.createOrder(orderDTO);
            Assert.fail();
        } catch (SellException e) {
            Assert.assertEquals(ResultEnum.PRODUCT_STOCK_ERROR.getMsg(), e.getMessage());
        }
    }

    @Test
    @Transactional
    public void createOrderProductNotExist() {
        OrderDTO orderDTO = mock("1234569", Arrays.asList("111", "222"),
                Arrays.asList(Integer.MAX_VALUE, Integer.MAX_VALUE));
        try {
            service.createOrder(orderDTO);
            Assert.fail();
        } catch (SellException e) {
            Assert.assertEquals(ResultEnum.PRODUCT_NOT_EXIST.getMsg(), e.getMessage());
        }
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = service.findOne("1234568");
        Assert.assertNotNull(orderDTO);
        Assert.assertEquals("1234568", orderDTO.getOrderId());
        Assert.assertNotEquals(0, orderDTO.getOrderDetailList().size());
    }

    @Test
    public void findList() {
        Page<OrderDTO> res = service.findList("zb1439", PageRequest.of(0, 10));
        Assert.assertNotEquals(0, res.getTotalElements());
    }

    @Test
    @Transactional
    public void cancel() {
        OrderDTO orderDTO = service.findOne("1234568");
        OrderDTO res = service.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), res.getOrderStatus());
    }

    @Test
    @Transactional
    public void finish() {
        OrderDTO orderDTO = service.findOne("1234568");
        OrderDTO res = service.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(), res.getOrderStatus());
    }

    @Test
    @Transactional
    public void paid() {
        OrderDTO orderDTO = service.findOne("1234568");
        OrderDTO res = service.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), res.getPayStatus());
    }

    @Test
    public void findListAll() {
        Page<OrderDTO> res = service.findList(PageRequest.of(0, 10));
        Assert.assertNotEquals(0, res.getTotalElements());
    }
}