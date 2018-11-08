package com.hw.util;

import org.springframework.beans.factory.InitializingBean;
/**
 * 配置属性(获取server.properties中配置的属性)
 * Created by Panda.HuangWei on 2016/10/24.
 */
public class PropertyConfig implements InitializingBean{
    public static PropertyConfig instance = new PropertyConfig();
    /**
     * 下拉框使用缓存
     */
    private boolean useCache = false;
    /**
     * 启动时候是否计算报表数据
     */
    private boolean startCalcRp = false;

    /**
     * 必须在下面的时间范围内才重新加载报表数据
     */
    private String rpCalcBeginTm;
    private String rpCalcEndTm;

    public boolean isUseCache() {
        return useCache;
    }

    public void setUseCache(boolean useCache) {
        this.useCache = useCache;
    }

    public boolean isStartCalcRp() {
        return startCalcRp;
    }

    public String getRpCalcBeginTm() {
        return rpCalcBeginTm;
    }

    public void setRpCalcBeginTm(String rpCalcBeginTm) {
        this.rpCalcBeginTm = rpCalcBeginTm;
    }

    public String getRpCalcEndTm() {
        return rpCalcEndTm;
    }

    public void setRpCalcEndTm(String rpCalcEndTm) {
        this.rpCalcEndTm = rpCalcEndTm;
    }

    public void setStartCalcRp(boolean startCalcRp) {
        this.startCalcRp = startCalcRp;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
    }

}
