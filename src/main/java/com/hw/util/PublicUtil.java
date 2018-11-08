/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;
import java.net.InetAddress;
import java.net.UnknownHostException;
/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:55
 */
public class PublicUtil {
    public static void main(String[] args) {
        System.out.println("本机的ip=" + PublicUtil.getIp());
    }

    public static String getPorjectPath(){
        String nowpath = "";
        nowpath=System.getProperty("user.dir")+"/";

        return nowpath;
    }

    /**
     * 获取本机ip
     * @return
     */
    public static String getIp(){
        String ip = "";
        try {
            InetAddress inet = InetAddress.getLocalHost();
            ip = inet.getHostAddress();
            //System.out.println("本机的ip=" + ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return ip;
    }
}
