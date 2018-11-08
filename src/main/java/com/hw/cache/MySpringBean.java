package com.hw.cache;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by Panda.HuangWei on 2016/10/22.
 */
public abstract class MySpringBean implements InitializingBean, DisposableBean {
    protected BeanFactory beanFactory;
    private String beanName;

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void destroy() {

    }
}
