/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;
import java.util.UUID;
/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:48
 */
public class UuidUtil {
    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }


    public static void main(String[] args) {
        System.out.println(get32UUID());
        System.out.println(System.currentTimeMillis());

    }
}
