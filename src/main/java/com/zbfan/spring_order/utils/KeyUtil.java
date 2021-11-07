package com.zbfan.spring_order.utils;

import java.util.Random;

public class KeyUtil {

    /**
     *
     * @return Time+RandomNumber
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        return System.currentTimeMillis() + String.valueOf(random.nextInt(900000) + 100000);
    }
}
