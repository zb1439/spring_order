package com.zbfan.spring_order.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnum {
    WAIT(0, "waiting for payment"),
    SUCCESS(1, "payment success")
    ;

    private final Integer code;

    private final String msg;

    PayStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
