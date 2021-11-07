package com.zbfan.spring_order.service.impl;

import com.zbfan.spring_order.dto.OrderDTO;
import com.zbfan.spring_order.enums.ResultEnum;
import com.zbfan.spring_order.exception.SellException;
import com.zbfan.spring_order.service.BuyerService;
import com.zbfan.spring_order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;


    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            log.error("[Order query]: Order with orderId={} has a different openid={}, expected={}",
                    orderId, orderDTO.getBuyerOpenid(), openid);
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO findOneOrder(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null) {
            log.error("[Order cancellation]: No order found");
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }
}
