package com.hw.service.app;

import com.hw.dao.DaoSupport;
import com.hw.util.PageData;
import com.hw.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/3/12.
 */
@Service("outStockAppService")
public class OutStockAppService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void getOutStock(PageData pd, Map<PageData, List<PageData>> map) throws Exception {
        List<PageData> list = list(pd);
        map.putAll(groupByNo(list));
    }



    private  Map<PageData, List<PageData>> groupByNo(List<PageData> list) {
        Map<String,List<PageData>> map = new HashMap<>();
        Map<PageData, List<PageData>> map1=new HashMap<>();
        String outStockNo;
        for (PageData p : list) {
            PageData da1= new PageData();
            outStockNo = p.getString("OUTSTOCK_NO");
            da1.put("osNo",outStockNo);

            String sDt = p.getString("PRE_OUT_DATE");
            da1.put("osDt",sDt);

            List<PageData> list1 = map.get(outStockNo);
            List<PageData> list2=map1.get(da1);
            if (list1 == null || list1.isEmpty()) {
                list1 = new ArrayList<>();
                list2= new ArrayList<>();
            }
            list1.add(p);
            map.put(outStockNo,list1);

            PageData pd2=new PageData();
            String proCode = p.getString("PRODUCT_CODE");
            pd2.put("item",proCode);
            String ea = p.getString("EA_EA");
            pd2.put("ea",ea);
            String depot = p.getString("DEPOT_STATE");
            pd2.put("deState",depot);
            list2.add(pd2);
            map1.put(da1,list2);
        }
        return map1;

    }

    /*
    *列表
	*/
    public List<PageData> list(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OutStockAppMapper.findById", pd);
    }

    public boolean isParam(PageData pd) {
        String outstock = pd.getString("osNo");
        if (StringUtil.isNotEmpty(outstock)) {
            pd.put("OUTSTOCK_NO", outstock);
        }
        else
        {
            return false;
        }
        return true;
    }


}
