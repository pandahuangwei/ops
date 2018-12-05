/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.controller;

import com.hw.cache.SelectCache;
import com.hw.entity.Page;
import com.hw.job.RpCalcJob;
import com.hw.util.Const;
import com.hw.util.PageData;
import com.hw.util.StringUtil;
import com.hw.util.UuidUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:23
 */
public class BaseController {
    //protected Logger logger = Logger.getLogger(this.getClass());
    protected org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final long serialVersionUID = 6357869213649815390L;

    /**
     * 得到PageData
     */
    public PageData getPageData(){
        return new PageData(this.getRequest());
    }

    /**
     * 得到ModelAndView
     */
    public ModelAndView getModelAndView(){
        return new ModelAndView();
    }

    /**
     * 得到request对象
     */
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 得到32位的uuid
     * @return id
     */
    public String get32UUID(){
        return UuidUtil.get32UUID();
    }

    /**
     * 得到分页列表的信息
     */
    public Page getPage(){

        return new Page();
    }

    public static void logBefore(Logger logger, String interfaceName){
        logger.info("");
        logger.info("start");
        logger.info(interfaceName);
    }

    public static void logAfter(Logger logger){
        logger.info("end");
        logger.info("");
    }

    /**
     * 获取当前登陆的用户名
     * @return 用户名
     */
    protected String getCurUserName() {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        return session.getAttribute(Const.SESSION_USERNAME).toString();//获取当前登录者loginname
    }

    /**
     * 获取当前登陆用户的的session
     * @return session
     */
    protected Session getCurSession() {
        Subject currentUser = SecurityUtils.getSubject();
        return currentUser.getSession();
    }

    protected void addCustomerId(PageData pd) {
        String customerId = pd.getString("CUSTOMER_ID");
        if (StringUtil.isBlank(customerId)) {
            customerId = SelectCache.getInstance().getAllCustomer().get(0).getId();
            pd.put("CUSTOMER_ID",customerId);
        }
    }

    protected boolean isSearch(String searchFlag) {
        return StringUtil.isBlank(searchFlag)||Const.SEARCH_FLAG_NO.equals(searchFlag)||Const.SEARCH_FLAG_YSE.equals(searchFlag);
    }
}
