package com.hw.job;

import com.hw.cache.ReloadableSpringBean;
import com.hw.service.rp.InboundRpService;
import com.hw.service.rp.OutboundRpService;
import com.hw.service.rp.StockNowProsRpService;
import com.hw.service.rp.StockReportService;
import com.hw.util.DateUtil;
import com.hw.util.PageData;
import com.hw.util.PropertyConfig;
import com.hw.util.StringUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Panda.HuangWei.
 * @since 2017-05-13 10:13.
 * To change this template use File | Settings | File Templates.
 */
public class RpCalcJob extends ReloadableSpringBean {
    private static Logger logger = LoggerFactory.getLogger(RpCalcJob.class);
    private long reloadCnt = 0;
    private Pair<Date, Date> rpReloadTmRange;

    @Resource(name = "stockReportService")
    private StockReportService stockReportService;
    @Resource(name = "inboundRpService")
    private InboundRpService inboundRpService;
    @Resource(name = "outboundRpService")
    private OutboundRpService outboundRpService;
    @Resource(name = "stockNowProsRpService")
    private StockNowProsRpService stockNowProsRpService;

    @Override
    public void reload() {
        reloadCnt++;
        getRpCalcTmRange();
        if(!DateUtil.isInRange(new Date(), rpReloadTmRange.getLeft(), rpReloadTmRange.getRight())) {
            clearData();
        }
        if (!isLoadRpData()) {
            return;
        }

        logger.info("=====Begin to load rp data...");
        //计算库存明细报表
        long startTmAll = System.nanoTime();
        long startTm = System.nanoTime();
        List<PageData> list = stockReportService.selectRpStockForInsert();
        //stockReportService.deleteRpStockDtl();
        stockReportService.saveRpStockDtl(list);
        logger.info("==加载库存明细数据== end,{}", StringUtil.getSpentTm(startTm));
        //=================================

        startTm = System.nanoTime();
        list = inboundRpService.selectRpStockForInsert();
        //inboundRpService.deleteRpInStock();
        inboundRpService.saveRpInStock(list);
        logger.info("==加载入库明细数据== end,{}", StringUtil.getSpentTm(startTm));
        //=================================

        startTm = System.nanoTime();
        list = outboundRpService.selectRpStockForInsert();
       // outboundRpService.deleteRpOutStock();
        outboundRpService.saveRpOutStock(list);
        logger.info("==加载出库明细数据== end,{}", StringUtil.getSpentTm(startTm));
        //=================================


        logger.info("=====End to load rp data, {}",StringUtil.getSpentTm(startTmAll));
    }

    /**
     * 判断是否重新加载报表数据.<br>
     * 1、看启动参数是否需要加载.<br>
     * 2、当前时间是否落在配置的时间范围内(0800~2200)
     *
     * @return boolean
     */
    private boolean isLoadRpData() {
        if (!PropertyConfig.instance.isStartCalcRp() && reloadCnt == 1) {
            logger.info("==The param (startCalcRp) is false and now is the first time start, not load rp data...");
            return false;
        }
        //return DateUtil.isInRange(new Date(), rpReloadTmRange.getLeft(), rpReloadTmRange.getRight());
        return true;
    }

    private void getRpCalcTmRange() {
        Date now = new Date();
        if (rpReloadTmRange != null && DateUtil.isSameDay(now, rpReloadTmRange.getLeft())) {
            return;
        }
        Date beginTm = DateUtil.genTm(now, PropertyConfig.instance.getRpCalcBeginTm());
        Date endTm = DateUtil.genTm(now, PropertyConfig.instance.getRpCalcEndTm());
        rpReloadTmRange = Pair.of(beginTm, endTm);
    }

    private void clearData() {
        logger.info("==Begin to clear rp data...");
        stockReportService.deleteRpStockDtl();
        inboundRpService.deleteRpInStock();
        outboundRpService.deleteRpOutStock();
        stockNowProsRpService.deleteRpStockNowPros();


        logger.info("==End to clear rp data!");
    }

    private static RpCalcJob instance;

    private RpCalcJob() {
        RpCalcJob.instance = this;
    }

    public static RpCalcJob getInstance() {
        return instance;
    }
}
