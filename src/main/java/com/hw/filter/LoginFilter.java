/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hw.controller.BaseController;
import com.hw.util.FileUtil;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 15:12
 */
public class LoginFilter extends BaseController implements Filter {

    /**
     * 初始化
     */
    public void init(FilterConfig fc) throws ServletException {
        //FileUtil.createDir("d:/FH/topic/");
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        chain.doFilter(req, res); // 调用下一过滤器
    }
}
