package com.hw.controller.app;

import com.hw.controller.BaseController;
import com.hw.service.app.InStockAppService;
import com.hw.service.app.OutStockAppService;
import com.hw.util.AppUtil;
import com.hw.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/3/12.
 */
@Controller
@RequestMapping(value = "/appOutStock")
public class OutStockAppController extends BaseController {
    @Resource(name = "outStockAppService")
    private OutStockAppService outStockAppService;

    /**
     * 根据用户名获取会员信息
     */
    @RequestMapping(value = "/getOutStock")
    @ResponseBody
    public Object getOutStock() {
        logBefore(logger, "根据用户名获取会员信息");
        Map<String, Object> map = new HashMap<String, Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String result = "00";
        try {
            if (outStockAppService.isParam(pd)) {    //检查参数
                Map<PageData, List<PageData>> map1 = new HashMap<>();
                outStockAppService.getOutStock(pd, map1);
                result = (map.isEmpty()) ? "02" : "01";
                map.put("", map1);
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

