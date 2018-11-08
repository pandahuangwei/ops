package com.hw.controller;

/**
 * @author Panda.HuangWei.
 * @version 1.0 .
 * @since 2017.02.13 18:52.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个用户 相同url 同时提交 相同数据 验证
 * @author Administrator
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SameUrlData {
}
