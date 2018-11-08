package com.hw.job;

import com.hw.service.JobAppService;
import com.hw.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author panda.HuangWei.
 * @since 2017-06-23 00:10.
 */
public class JobApp implements BeanFactoryAware {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private BeanFactory beanFactory;
    private static long runTimeCount;

    public void clearAnd2History() {
        long startTm = System.nanoTime();
        runTimeCount++;
        logger.info("The AutoAuditCheck which was called by timer for {} time started...",runTimeCount);

        JobAppService service = (JobAppService)beanFactory.getBean("jobAppService");
        service.deleteRpBreakBox();
        service.deleteRpStockNow();
        service.saveReceivOptHistory();
        logger.info("The AutoAuditCheck which was called by timer for {} time was ended,{}",runTimeCount, StringUtil.getSpentTm(startTm));
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
