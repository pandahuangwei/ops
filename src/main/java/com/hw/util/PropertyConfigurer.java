package com.hw.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Properties;

/**
 * Created by Panda.HuangWei on 2016/10/24.
 */
public class PropertyConfigurer implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(PropertyConfigurer.class);
    Object configOjbect;

    String location;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(location, "property location can't be null");
        Assert.notNull(configOjbect, "configOjbect location can't be null");

        Properties allProperties = PropertiesLoaderUtils
                .loadAllProperties(location);

        @SuppressWarnings("unchecked")
        Map<String, String> configObjectProperties = BeanUtils
                .describe(configOjbect);

        for (Map.Entry<String, String> entry : configObjectProperties.entrySet()) {
            String propertyName = entry.getKey();
            Object value = allProperties.get(propertyName);
            if (value != null) {
                BeanUtils.setProperty(configOjbect, propertyName, value);
            }
        }

        logger.debug("configOjbect is:{}", ReflectionToStringBuilder.toString(
                configOjbect, ToStringStyle.MULTI_LINE_STYLE));

    }

    public void setConfigOjbect(Object configOjbect) {
        this.configOjbect = configOjbect;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
