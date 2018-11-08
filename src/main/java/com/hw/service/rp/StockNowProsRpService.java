package com.hw.service.rp;

import com.hw.cache.BaseInfoCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Panda.HuangWei.
 * @since 2017-05-28 21:30.
 * To change this template use File | Settings | File Templates.
 */
@Service("stockNowProsRpService")
public class StockNowProsRpService {
    private static Logger logger = LoggerFactory.getLogger(StockNowProsRpService.class);
    private int saveCnt = 50;
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public void saveCalc(List<PageData> list) throws Exception {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<PageData> lst = new ArrayList<>(64);
        int i = 0;
        for (PageData p : list) {
            i++;
            lst.add(p);
            if (i == saveCnt) {
                i = 0;
                save(lst);
                lst.clear();
            }
        }
        if (!lst.isEmpty()) {
            save(lst);
        }
    }

    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockNowProsMapper.datalistPage", page);
    }


    public List<PageData> calcStock(Page page) throws Exception {
        // List<PageData> srcList = findDtlList(page);
        List<PageData> srcList = findDtl(page);

        if (srcList == null || srcList.isEmpty()) {
            return new ArrayList<>();
        }
        Map<String, PageData> map = new HashMap<>();
        for (PageData p : srcList) {
            String customerId = p.getString("CUSTOMER_ID");
            String productId = p.getString("PRODUCT_ID");
            String lotNo = p.getStringNot4EmpyCh("LOT_NO");
            String dateCode = p.getStringNot4EmpyCh("DATE_CODE");
            String coo = p.getStringNot4EmpyCh("COO");
            String binCode = p.getStringNot4EmpyCh("BIN_CODE");
            String remark1 = p.getStringNot4EmpyCh("REMARK1");
            String remark2 = p.getStringNot4EmpyCh("REMARK2");
            String remark3 = p.getStringNot4EmpyCh("REMARK3");
            String timeTwo = p.getStringNot4EmpyCh("TIME_TWO");

            String key = StringUtil.genCombIdKey(customerId, productId, lotNo, dateCode, coo, binCode, remark1, remark2, remark3, timeTwo);
            PageData pd = map.get(key);
            if (pd == null || pd.getString("CUSTOMER_ID") == null) {
                pd = new PageData();
                pd.put("COMB_ID", key);
                pd.put("CUSTOMER_ID", p.getString("CUSTOMER_ID"));
                pd.put("PRODUCT_ID", p.getString("PRODUCT_ID"));
                pd.put("LOT_NO", lotNo);
                pd.put("DATE_CODE", dateCode);
                pd.put("COO", coo);
                pd.put("BIN_CODE", binCode);
                pd.put("REMARK1", remark1);
                pd.put("REMARK2", remark2);
                pd.put("REMARK3", remark3);
                pd.put("TIME_TWO", timeTwo);
                pd.put("ASSIGNED_EA", 0);
                pd.put("FREEZE_EA", 0);
                pd.put("TOTAL_EA", 0);
                pd.put("USE_EA", 0);
                pd.put("CREATE_EMP", SessionUtil.getCurUserName());
            }

            //已分配未冻结的 算分配的
            if (WmsEnum.AssignedState.ALL.equals(p.getString("ASSIGNED_STOCK_STATE"))
                    && WmsEnum.FreezeState.NONE.getItemKey().equals(p.getString("FREEZE_STATE"))) {
                int ASSIGNED_EA = pd.getInteger("ASSIGNED_EA") + p.getInteger("ASSIGNED_STOCK_NUM");
                pd.put("ASSIGNED_EA", ASSIGNED_EA);
            } else {
                pd.put("ASSIGNED_EA", pd.getInteger("ASSIGNED_EA") + 0);
            }


            //冻结的
            if (WmsEnum.FreezeState.ALL.getItemKey().equals(p.getString("FREEZE_STATE"))) {
                int ass_ea = 0;
                if (WmsEnum.AssignedState.ALL.equals(p.getString("ASSIGNED_STOCK_STATE"))) {
                    ass_ea = p.getInteger("ASSIGNED_STOCK_NUM");
                } else {
                    ass_ea = p.getInteger("EA_NUM");
                }

                int FREEZE_EA = pd.getInteger("FREEZE_EA") + ass_ea;
                pd.put("FREEZE_EA", FREEZE_EA);
            } else {
                pd.put("FREEZE_EA", pd.getInteger("FREEZE_EA") + 0);
            }
            //总数
            int TOTAL_EA = pd.getInteger("TOTAL_EA") + p.getInteger("EA_NUM");
            pd.put("TOTAL_EA", TOTAL_EA);
            //可用数
            int USE_EA = pd.getInteger("TOTAL_EA") - pd.getInteger("ASSIGNED_EA") - pd.getInteger("FREEZE_EA");
            pd.put("USE_EA", USE_EA);
            map.put(key, pd);
        }
        return map2List(map);
    }

    public void deleteRp(Page page) throws Exception {
        dao.delete("StockNowProsMapper.deleteRpStockpros", page);
    }


    public List<PageData> listLookDtl(Page page) throws Exception {
        addParams(page);
        List<PageData> srcList = findLookDtl(page);
        if (srcList == null || srcList.isEmpty()) {
            return new ArrayList<>();
        }
        List<PageData> list = new ArrayList<>(srcList.size());
        for (PageData p : srcList) {
            PageData pd = new PageData();
            pd.put("BIG_BOX_NO", p.getString("BIG_BOX_NO"));
            pd.put("BOX_NO", p.getString("BOX_NO"));
            pd.put("INSTOCK_NO", p.getString("INSTOCK_NO"));
            pd.put("PUT_LOCATOR", p.getString("PUT_LOCATOR"));
            pd.put("SEPARATE_QTY", p.getString("SEPARATE_QTY"));
            pd.put("ASSIGNED_STOCK_STATE", p.getString("ASSIGNED_STOCK_STATE"));
            pd.put("FREEZE_STATE", p.getString("FREEZE_STATE"));
            pd.put("ASSIGNED_STATE", p.getString("ASSIGNED_STATE"));
            pd.put("PUT_STATUS", p.getString("PUT_STATUS"));

            pd.put("CUSTOMER_ID", p.getString("CUSTOMER_ID"));
            pd.put("PRODUCT_ID", p.getString("PRODUCT_ID"));
            pd.put("LOT_NO", p.getStringNot4EmpyCh("LOT_NO"));
            pd.put("DATE_CODE", p.getStringNot4EmpyCh("DATE_CODE"));
            pd.put("COO", p.getStringNot4EmpyCh("COO"));
            pd.put("BIN_CODE", p.getStringNot4EmpyCh("BIN_CODE"));
            pd.put("REMARK1", p.getStringNot4EmpyCh("REMARK1"));
            pd.put("REMARK2", p.getStringNot4EmpyCh("REMARK2"));
            pd.put("REMARK3", p.getStringNot4EmpyCh("REMARK3"));
            pd.put("TIME_TWO", p.getStringNot4EmpyCh("TIME_TWO"));
            pd.put("TXT_TWO", p.getStringNot4EmpyCh("TXT_TWO"));
            pd.put("ASSIGNED_EA", 0);
            pd.put("FREEZE_EA", 0);
            pd.put("TOTAL_EA", 0);
            pd.put("USE_EA", 0);

            //已分配未冻结的 算分配的
            if (WmsEnum.AssignedState.ALL.equals(p.getString("ASSIGNED_STOCK_STATE"))
                    && WmsEnum.FreezeState.NONE.getItemKey().equals(p.getString("FREEZE_STATE"))) {
                int ASSIGNED_EA = pd.getInteger("ASSIGNED_EA") + p.getInteger("ASSIGNED_STOCK_NUM");
                pd.put("ASSIGNED_EA", ASSIGNED_EA);
            } else {
                pd.put("ASSIGNED_EA", pd.getInteger("ASSIGNED_EA") + 0);
            }


            //冻结的
            if (WmsEnum.FreezeState.ALL.getItemKey().equals(p.getString("FREEZE_STATE"))) {
                int ass_ea = 0;
                if (WmsEnum.AssignedState.ALL.equals(p.getString("ASSIGNED_STOCK_STATE"))) {
                    ass_ea = p.getInteger("ASSIGNED_STOCK_NUM");
                } else {
                    ass_ea = p.getInteger("EA_NUM");
                }

                int FREEZE_EA = pd.getInteger("FREEZE_EA") + ass_ea;
                pd.put("FREEZE_EA", FREEZE_EA);
            } else {
                pd.put("FREEZE_EA", pd.getInteger("FREEZE_EA") + 0);
            }
            //总数
            int TOTAL_EA = pd.getInteger("TOTAL_EA") + p.getInteger("EA_NUM");
            pd.put("TOTAL_EA", TOTAL_EA);
            //可用数
            int USE_EA = pd.getInteger("TOTAL_EA") - pd.getInteger("ASSIGNED_EA") - pd.getInteger("FREEZE_EA");
            pd.put("USE_EA", USE_EA);
            list.add(pd);
        }
        return list;
    }

    /**
     * 到处excel的表头
     *
     * @return excel标题
     */
    public List<String> getRpHead() {
        List<String> titles = new ArrayList<>();
        titles.add("客户");
        titles.add("料号");
        titles.add("DATE CODE 转化");
        titles.add("DateCode");
        titles.add("LotCode");
        titles.add("BIN");
        titles.add("COO");
        titles.add("Remark1");
        titles.add("Remark2");
        titles.add("Remark3");
        titles.add("库存EA数");
        titles.add("分配EA数");
        titles.add("冻结EA数");
        titles.add("可用EA数");
        return titles;
    }

    public List<PageData> getRpDtl(Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("StockNowProsMapper.findForExcel", page);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<PageData> varList = new ArrayList<>(list.size());
        int i = 1;
        for (PageData p : list) {
            i = 1;
            PageData vpd = new PageData();
            vpd.put(StringUtil.genParamStr(i++), BaseInfoCache.getInstance().getCustomerCode(p.getString("CUSTOMER_ID")));
            vpd.put(StringUtil.genParamStr(i++), BaseInfoCache.getInstance().getProductCode(p.getString("PRODUCT_ID")));
            vpd.put(StringUtil.genParamStr(i++), p.getString("TIME_TWO"));//DATE CODE 转化
            vpd.put(StringUtil.genParamStr(i++), p.getString("DATE_CODE"));
            vpd.put(StringUtil.genParamStr(i++), p.getString("LOT_NO"));
            vpd.put(StringUtil.genParamStr(i++), p.getString("BIN_CODE"));
            vpd.put(StringUtil.genParamStr(i++), p.getString("COO"));
            vpd.put(StringUtil.genParamStr(i++), p.getString("REMARK1"));
            vpd.put(StringUtil.genParamStr(i++), p.getString("REMARK2"));
            vpd.put(StringUtil.genParamStr(i++), p.getString("REMARK3"));
            vpd.put(StringUtil.genParamStr(i++), p.getInteger("TOTAL_EA"));
            vpd.put(StringUtil.genParamStr(i++), p.getInteger("ASSIGNED_EA"));
            vpd.put(StringUtil.genParamStr(i++), p.getInteger("FREEZE_EA"));
            vpd.put(StringUtil.genParamStr(i++), p.getInteger("USE_EA"));
            varList.add(vpd);
        }
        return varList;
    }

    private List<PageData> map2List(Map<String, PageData> map) {
        List<PageData> lst = new ArrayList<>();
        for (Map.Entry<String, PageData> entry : map.entrySet()) {
            lst.add(entry.getValue());
        }
        return lst;
    }

    public void deleteRpStockNowPros() {
        try {
            dao.delete("StockNowProsMapper.truncateRpStockPros",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save(List<PageData> list) throws Exception {
        dao.save("StockNowProsMapper.insertRpStockpros", list);
    }

    private List<PageData> findDtl(Page page) throws Exception {
        List<PageData> boxDtlList = findBoxDtlList(page);
        if (boxDtlList == null || boxDtlList.isEmpty()) {
            return new ArrayList<>();
        }
        List<PageData> prosDtlList = findProsDtlList(page);
        List<PageData> list = new ArrayList<>(boxDtlList.size());
        //如果没有以DateCode转化字段查询的话，则以箱号记录为准，否则以明细记录为准
        if (StringUtil.isEmpty(page.getPd().getString("TIME_TWO"))) {
            Map<String, PageData> map = groupByBoxId(prosDtlList);
            for (PageData p : boxDtlList) {
                PageData pd = map.get(p.getString(Const.BOX_ID));
                if (pd == null || StringUtil.isEmpty(pd.getString("TIME_TWO"))) {
                    p.put("TIME_TWO", Const.EMPTY_CH);
                } else {
                    p.put("TIME_TWO", pd.getString("TIME_TWO"));
                }
                list.add(p);
            }
        } else {
            Map<String, PageData> map = groupByBoxId(boxDtlList);
            for (PageData p : prosDtlList) {
                PageData boxPd = map.get(p.getString(Const.BOX_ID));
                if (boxPd == null) {
                    continue;
                }
                boxPd.put("TIME_TWO", p.getString("TIME_TWO"));
                list.add(boxPd);
            }
        }
        return list;
    }


    private List<PageData> findLookDtl(Page page) throws Exception {
        List<PageData> boxDtlList = findBoxLookDtlList(page);
        if (boxDtlList == null || boxDtlList.isEmpty()) {
            return new ArrayList<>();
        }
        List<PageData> prosDtlList = findProsLookDtlList(page);
        List<PageData> list = new ArrayList<>(boxDtlList.size());
        //如果没有以DateCode转化字段查询的话，则以箱号记录为准，否则以明细记录为准
        if (StringUtil.isEmpty(page.getPd().getString("TIME_TWO"))) {
            Map<String, PageData> map = groupByBoxId(prosDtlList);
            for (PageData p : boxDtlList) {
                PageData pd = map.get(p.getString(Const.BOX_ID));
                if (pd == null || StringUtil.isEmpty(pd.getString("TIME_TWO"))) {
                    p.put("TIME_TWO", Const.EMPTY_CH);
                    p.put("TXT_TWO", Const.EMPTY_CH);
                } else {
                    p.put("TIME_TWO", pd.getString("TIME_TWO"));
                    p.put("TXT_TWO", pd.getString("TIME_TWO"));
                    p.put("INSTOCK_NO", pd.getString("INSTOCK_NO"));
                }
                list.add(p);
            }
        } else {
            Map<String, PageData> map = groupByBoxId(boxDtlList);
            for (PageData p : prosDtlList) {
                PageData boxPd = map.get(p.getString(Const.BOX_ID));
                if (boxPd == null) {
                    continue;
                }
                boxPd.put("TIME_TWO", p.getString("TIME_TWO"));
                p.put("TXT_TWO", p.getString("TIME_TWO"));
                p.put("INSTOCK_NO", p.getString("INSTOCK_NO"));
                list.add(boxPd);
            }
        }
        return list;
    }

    private Map<String, PageData> groupByBoxId(List<PageData> list) {
        Map<String, PageData> map = new HashMap<>(list.size());
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            map.put(p.getString(Const.BOX_ID), p);
        }
        return map;
    }

    /**
     * 拆分并将参数加入查询条件
     *
     * @param page 查询实体
     */
    private void addParams(Page page) {
        PageData pd = page.getPd();
        String combIds = pd.getString("COMB_ID");
        String[] arrs = StringUtils.split(combIds, Const.SPLIT_LINE);
        pd.put("CUSTOMER_ID", arrs[0]);
        pd.put("PRODUCT_ID", arrs[1]);
        pd.put("LOT_NO", StringUtil.replaceRpId(arrs[2]));
        pd.put("DATE_CODE", StringUtil.replaceRpId(arrs[3]));
        pd.put("COO", StringUtil.replaceRpId(arrs[4]));
        pd.put("BIN_CODE", StringUtil.replaceRpId(arrs[5]));
        pd.put("REMARK1", StringUtil.replaceRpId(arrs[6]));
        pd.put("REMARK2", StringUtil.replaceRpId(arrs[7]));
        pd.put("REMARK3", StringUtil.replaceRpId(arrs[8]));
        pd.put("TIME_TWO", StringUtil.replaceRpId(arrs[9]));
        page.setPd(pd);
    }

    private List<PageData> findDtlList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockNowProsMapper.findDtlList", page);
    }

    private List<PageData> findBoxDtlList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockNowProsMapper.findBoxDtlList", page);
    }


    private List<PageData> findProsDtlList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockNowProsMapper.findProsDtlList", page);
    }

    private List<PageData> findBoxLookDtlList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockNowProsMapper.findBoxLookDtlList", page);
    }

    private List<PageData> findProsLookDtlList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockNowProsMapper.findProsLookDtlList", page);
    }


}
