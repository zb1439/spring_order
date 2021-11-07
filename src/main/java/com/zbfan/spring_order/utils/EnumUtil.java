package com.zbfan.spring_order.utils;

import com.zbfan.spring_order.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> codeEnum) {
        for (T each : codeEnum.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
