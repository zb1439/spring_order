package com.zbfan.spring_order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zbfan.spring_order.dataobject.OrderDetail;
import com.zbfan.spring_order.dto.OrderDTO;
import com.zbfan.spring_order.enums.ResultEnum;
import com.zbfan.spring_order.exception.SellException;
import com.zbfan.spring_order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
            orderDetails = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e) {
            log.error("[Converter]: converting to List of OrderDetail failed, string={}", orderForm.getItems());
            e.printStackTrace();
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), e.getMessage());
        }
        orderDTO.setOrderDetailList(orderDetails);
        return orderDTO;
    }
}
