/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import org.slf4j.LoggerFactory;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:48
 */
public class Logger{
    private org.slf4j.Logger logger;


    /**
     * 构造方法，初始化Log4j的日志对象
     */
    private Logger(org.slf4j.Logger log4jLogger) {
        logger = log4jLogger;
    }

    /**
     * 获取构造器，根据类初始化Logger对象
     *
     * @param classObject
     *            Class对象
     * @return Logger对象
     */
    public static Logger getLogger(Class classObject) {
        return new Logger(LoggerFactory.getLogger(classObject));
    }

    public void info(String str) {
        logger.info(str);
    }

    public void info(String str, Throwable e) {
        logger.info(str, e);
    }

   public void debug(String object) {
        logger.debug(object);
    }

    public void debug(String object, Throwable e) {
        logger.debug(object, e);
    }





    public void warn(String object) {
        logger.warn(object);
    }

    public void warn(String object, Throwable e) {
        logger.warn(object, e);
    }

    public void error(String object) {
        logger.error(object);
    }

    public void error(String object, Throwable e) {
        logger.error(object, e);
    }


    public String getName() {
        return logger.getName();
    }

    public org.slf4j.Logger getLog4jLogger() {
        return logger;
    }

    public boolean equals(Logger newLogger) {
        return logger.equals(newLogger.getLog4jLogger());
    }
}
