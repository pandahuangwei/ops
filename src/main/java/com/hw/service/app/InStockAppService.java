package com.hw.service.app;

import com.hw.cache.BaseInfoCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.AppUtil;
import com.hw.util.Const;
import com.hw.util.PageData;
import com.hw.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by admin on 2017/3/11.
 */
@Service("inStockAppService")
public class InStockAppService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    public void getInStock(PageData pd,  Map<PageData, List<PageData>> map) throws Exception {
        List<PageData> list = list(pd);
        map.putAll(groupByNo(list));
    }

    private  Map<PageData, List<PageData>> groupByNo(List<PageData> list) {
        Map<String,List<PageData>> map = new HashMap<>();
        Map<PageData, List<PageData>> map1=new HashMap<>();
        String inStockNo;
        for (PageData p : list) {
            PageData da1= new PageData();
            inStockNo = p.getString("INSTOCK_NO");
            da1.put("sNo",inStockNo);

            String sDt = p.getString("PRE_INSTOCK_DATE");
            da1.put("sDt",sDt);

            List<PageData> list1 = map.get(inStockNo);
            List<PageData> list2=map1.get(da1);
            if (list1 == null || list1.isEmpty()) {
                list1 = new ArrayList<>();
                list2= new ArrayList<>();
            }
            list1.add(p);
            map.put(inStockNo,list1);

            PageData pd2=new PageData();
            String proCode = p.getString("PRODUCT_CODE");
            pd2.put("item",proCode);
            String boxNo = p.getString("BOX_NO");
            pd2.put("box_no",boxNo);
            String ea = p.getString("EA_EA");
            pd2.put("ea",ea);
            String eaActual = p.getString("EA_ACTUAL");
            pd2.put("eaActual",eaActual);
            list2.add(pd2);
            map1.put(da1,list2);
        }
        return map1;

    }

    /*
    *列表
	*/
    public List<PageData> list(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("InStockAppMapper.findById", pd);
    }

    public boolean isParam(PageData pd) {
        String value = pd.getString("customer");
        String instock = pd.getString("sNo");
        String time = pd.getString("sDt");

        if (StringUtil.isEmpty(value)
                || (StringUtil.isEmpty(instock) && StringUtil.isEmpty(time))) {
            return false;
        }
        String customerId = "c1"; //BaseInfoCache.getInstance().getCustomerId(value);

        if (StringUtil.isEmpty(customerId)) {
            return false;
        }

        pd.put("CUSTOMER_ID", customerId);

        if (StringUtil.isNotEmpty(instock)) {
            pd.put("INSTOCK_NO", instock);
        }
        if (StringUtil.isNotEmpty(time)) {
            pd.put("INSTOCK_DT", time);
        }
        return true;
    }

    public boolean isCheckParam(PageData pd) {
        String value = pd.getString("customer");
        String allFlag = pd.getString("allFlag");

        if (StringUtil.isEmpty(value)||StringUtil.isEmpty(allFlag)) {
            return false;
        }
        String customerId = "c1"; //BaseInfoCache.getInstance().getCustomerId(value);
        if (StringUtil.isEmpty(customerId)) {
            return false;
        }
        pd.put("CUSTOMER_ID", customerId);

        if (StringUtil.isNotEmpty(allFlag)&&allFlag=="0") {
            pd.put("AllFLAG", allFlag);
        }
        return true;
    }
    public PageData getDate(PageData pd) throws Exception {
        return (PageData) dao.findForObject("InStockAppMapper.findDateById", pd);
    }
    public List<PageData> getList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("InStockAppMapper.findInstockNoById", pd);
    }
    public  void updateDate(PageData pd) throws Exception {
          dao.update("InStockAppMapper.updateLastDate", pd);
    }
    public  void insertDate(PageData pd) throws Exception {
        dao.update("InStockAppMapper.insertLastDate", pd);
    }
}
