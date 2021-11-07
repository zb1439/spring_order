package com.zbfan.spring_order.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0, "success"),
    INTERNAL_ERROR(-1, ""),
    PRODUCT_NOT_EXIST(10, "Item does not exist!"),
    PRODUCT_STOCK_ERROR(11, "Item quantity larger than stock!"),
    ORDER_NOT_EXIST(12, "Order does not exist!"),
    ORDER_DETAIL_NOT_EXIST(13, "Order details do not exist!"),
    ORDER_STATUS_ERROR(14, "Order status incorrect!"),
    ORDER_UPDATE_FAIL(15, "Order update failed!"),
    ORDER_NO_DETAIL(16, "No order details in order!"),
    ORDER_PAY_STATUS_ERROR(18, "Order payment status error!"),
    PARAM_ERROR(19, "incorrect parameters"),
    CART_EMPTY(20, "cart cannot be empty"),
    ORDER_OWNER_ERROR(21, "order belongs to another user"),
    WECHAT_MP_ERROR(22, "error related to wechat mp"),
    WECHAT_NOTIFY_VERIFY_BALANCE_ERROR(23, "inconsistent balance at wechat payment notification"),
    PRODUCT_STATUS_ERROR(24, "product status incorrect"),
    LOGIN_FAILURE(25, "login information error"),
    SECKILL_FAILURE(101, "sorry, order requests are overloaded now"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
