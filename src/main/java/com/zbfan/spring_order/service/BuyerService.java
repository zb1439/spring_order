package com.zbfan.spring_order.service;

import com.zbfan.spring_order.dto.OrderDTO;

public interface BuyerService {

    OrderDTO findOneOrder(String openid, String orderId);

    OrderDTO cancelOrder(String openid, String orderId);
}
