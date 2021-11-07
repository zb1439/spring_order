package com.zbfan.spring_order.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zbfan.spring_order.dataobject.OrderDetail;
import com.zbfan.spring_order.enums.OrderStatusEnum;
import com.zbfan.spring_order.enums.PayStatusEnum;
import com.zbfan.spring_order.utils.EnumUtil;
import com.zbfan.spring_order.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private List<OrderDetail> orderDetailList;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using= Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using= Date2LongSerializer.class)
    private Date updateTime;

    @JsonIgnore
    public OrderStatusEnum orderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum payStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }
}
