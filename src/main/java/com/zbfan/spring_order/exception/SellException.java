package com.zbfan.spring_order.exception;

import com.zbfan.spring_order.enums.ResultEnum;
import lombok.Getter;

@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());

        code = resultEnum.getCode();
    }

    public SellException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
