package com.zbfan.spring_order.enums;

import lombok.Getter;

@Getter
public enum ProductStatusEnum implements CodeEnum {

    ONLINE(0, "online"),
    OFFLINE(1, "offline")
    ;

    private Integer code;

    private String msg;

    ProductStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
