package com.zbfan.spring_order.utils;


public class MathUtil {
    private static final double MONEY_RANGE = 0.01;

    public static boolean equals(double a, double b) {
        if (Math.abs(a - b) < MONEY_RANGE) {
            return true;
        }
        return false;
    }
}
