package com.hw.controller.app;

import com.hw.cache.BaseInfoCache;
import com.hw.controller.BaseController;
import com.hw.service.app.InStockAppService;
import com.hw.service.system.AppuserService;
import com.hw.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2017/3/11.
 */
@Controller
@RequestMapping(value = "/appInStock")
public class InStockAppController extends BaseController {

    @Resource(name = "inStockAppService")
    private InStockAppService inStockAppService;

    /**
     * 根据用户名获取会员信息
     */
    @RequestMapping(value = "/getInStock")
    @ResponseBody
    public Object getInStock() {
        logBefore(logger, "根据用户名获取会员信息");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String result = "00";
        try {
            if (inStockAppService.isParam(pd)) {    //检查参数
                Map<PageData, List<PageData>> map1=new HashMap<>();
                inStockAppService.getInStock(pd, map1);
                result = (map.isEmpty()) ? "02" : "01";
                map.put("",map1);
            } else {
                result = "03";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            map.put("result", result);
            logAfter(logger);
        }

        return AppUtil.returnObject(new PageData(), map);
    }

    @RequestMapping(value = "/getInStockNo")
    @ResponseBody
    public Object getInStockNo() {
        logBefore(logger, "根据用户名获取会员信息");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String result = "00";
        try {
            if (inStockAppService.isCheckParam(pd)) {    //检查参数
                PageData date =  inStockAppService.getDate(pd);
                String allFlag = pd.getString("allFlag");

                if ( allFlag.equals("0") && date != null) {
                    String queryDt = date.getString("QUERY_DATE");

                    pd.put("QUERY_DATE", queryDt);
                }
                List<PageData> lists = inStockAppService.getList(pd);
                Date dat=new Date();
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String da = sdf.format(dat);
                pd.put("QUERY_DATE", da);
                if (date != null) {
                    inStockAppService.updateDate(pd);
                } else
                    inStockAppService.insertDate(pd);
                result = (map.isEmpty()) ? "02" : "01";
                map.put("", lists);
            } else {
                result = "03";
            }

        } catch (Exception e) {
            logger.error(e.toString(), e);
        } finally {
            map.put("result", result);
            logAfter(logger);
        }

        return AppUtil.returnObject(new PageData(), map);
    }

}
