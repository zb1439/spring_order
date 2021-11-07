package com.zbfan.spring_order.service;

import com.zbfan.spring_order.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface PushMessageService {

    void orderStatus(OrderDTO orderDTO);
}
