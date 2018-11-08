package com.hw.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @author Panda.HuangWei.
 * @Create 2016.11.20 12:07.
 * @Version 1.0 .
 */
public class SessionUtil {
    /**
     * 获取当前登陆的用户名
     *
     * @return 用户名
     */
    public static String getCurUserName() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return session.getAttribute(Const.SESSION_USERNAME).toString();//获取当前登录者loginname
    }

    /**
     * 获取当前登陆用户的的session
     *
     * @return session
     */
    public static Session getCurSession() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.getSession();
    }
}
