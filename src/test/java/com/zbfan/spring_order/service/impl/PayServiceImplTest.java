package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.dto.OrderDTO;
import com.zbfan.spring_order.service.OrderService;
import com.zbfan.spring_order.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1633041668420982890");
        payService.create(orderDTO);
    }

    @Test
    public void refund() {
        OrderDTO orderDTO = orderService.findOne("1633038569943311668");
        payService.refund(orderDTO);
    }
}