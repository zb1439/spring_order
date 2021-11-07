package com.zbfan.spring_order.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum {
    NEW(0, "new order"),
    FINISH(1, "completed order"),
    CANCEL(2, "cancelled order")
    ;

    private Integer code;

    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
