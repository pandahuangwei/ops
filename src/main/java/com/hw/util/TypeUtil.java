package com.hw.util;

import java.math.BigDecimal;

/**
 * Created by Panda.HuangWei on 2016/11/4.
 */
public class TypeUtil {
    /**
     * 转成 Integer
     */
    public static Integer intValue(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        BigDecimal a = new BigDecimal(1222);
        System.out.println(a.toString());
    }

}
