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
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Service("stockReportService")
public class StockReportService {
    private static Logger logger = LoggerFactory.getLogger(StockReportService.class);

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
     *列表
    */
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockReportMapper.datalistPage", page);
    }

    public List<PageData> listStockNow(Page page) throws Exception {
        if (!isSearch(page.getPd())) {
            return new ArrayList<>();
        }

        page.getPd().put("CREATE_EMP", SessionUtil.getCurUserName());
    /*	String locatorcode=page.getPd().getString("LOCATOR_CODE");
        String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
		page.getPd().put("PUT_LOCATOR",locatorid);*/
        //PageData rs = findStockOne(page);
        if (isCalc()) {
            List<PageData> calcStockList = findCalcStockList(page);
            List<PageData> list = calcStock(calcStockList, page.getPd());
            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }
            saveStockNow(list, page);
        }
        //导出
        if (Const.OPT_EVEN_1.equals(page.getPd().getString("EXP"))) {
            return findStockNowlistName(page);
        } else {
            return findStockNowList(page);
        }
    }


    private List<PageData> calcStock(List<PageData> srcList, PageData searchPd) {
        if (srcList == null || srcList.isEmpty()) {
            return new ArrayList<>();
        }
        Set<String> boxs;
        Set<String> locators;
        Map<String, PageData> map = new HashMap<>();
        for (PageData p : srcList) {
            String key = p.getString("CUSTOMER_ID") + Const.SPLIT_LINE + p.getString("PRODUCT_ID");
            PageData pd = map.get(key);
            if (pd == null || pd.getString("CUSTOMER_ID") == null) {
                pd = new PageData();
            }
            pd.put("CUSTOMER_ID", p.getString("CUSTOMER_ID"));
            pd.put("PRODUCT_ID", p.getString("PRODUCT_ID"));
            //pd.put("COMB_ID", p.getString("PRODUCT_ID")+Const.SPLIT_LINE+p.getString("CUSTOMER_ID"));

            pd.put("COMB_ID", StringUtil.genCombIdKeyUserUnderLine(p.getString("PRODUCT_ID"), p.getString("CUSTOMER_ID"), searchPd.getStringNot4EmpyCh("PUT_LOCATOR")
                    , searchPd.getStringNot4EmpyCh("BOX_NO"), searchPd.getStringNot4EmpyCh("LOT_NO"), searchPd.getStringNot4EmpyCh("COO")
                    , searchPd.getStringNot4EmpyCh("DATE_CODE"), searchPd.getStringNot4EmpyCh("INSTOCK_NO")));

            pd.put("CREATE_EMP", SessionUtil.getCurUserName());
            pd.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间

            boxs = pd.getSet("BOXS");
            boxs.add(p.getString("BOX_NO"));
            String boxstr = VaryUtils.genString(boxs);
            boxstr = StringUtil.isNotEmpty(boxstr) && boxstr.length() >= 3000 ? boxstr.substring(0, 2999) : boxstr;
            pd.put("BOXS", boxstr);

            locators = pd.getSet("LOCATORS");
            String locator = BaseInfoCache.getInstance().getLocatorCode(p.getString("PUT_LOCATOR"));
            locators.add(locator);
            String loacatorstr = VaryUtils.genString(locators);
            loacatorstr = StringUtil.isNotEmpty(loacatorstr) && loacatorstr.length() >= 3000 ? loacatorstr.substring(0, 2999) : loacatorstr;
            pd.put("LOCATORS", loacatorstr);

            //已分配未冻结的 算分配的
            if (WmsEnum.AssignedState.ALL.getItemKey().equals(p.getString("ASSIGNED_STOCK_STATE"))
                    && WmsEnum.FreezeState.NONE.getItemKey().equals(p.getString("FREEZE_STATE"))) {
                int ASSIGNED_EA = pd.getInteger("ASSIGNED_EA") + p.getInteger("ASSIGNED_STOCK_NUM");
                pd.put("ASSIGNED_EA", ASSIGNED_EA);
            } else {
                pd.put("ASSIGNED_EA", pd.getInteger("ASSIGNED_EA") + 0);
            }


            //冻结的
            if (WmsEnum.FreezeState.ALL.getItemKey().equals(p.getString("FREEZE_STATE"))) {
                int ass_ea = 0;
                if (WmsEnum.AssignedState.ALL.getItemKey().equals(p.getString("ASSIGNED_STOCK_STATE"))) {
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


    private PageData findStockOne(Page page) throws Exception {
        return (PageData) dao.findForObject("StockReportMapper.findStockNowOne", page);
    }

    private List<PageData> findStockNowList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockReportMapper.findStockNowlistPage", page);
    }

    private List<PageData> findStockNowlistName(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockReportMapper.findStockNowlistName", page);
    }


    private void saveStockNow(List<PageData> list, Page page) throws Exception {
        dao.delete("StockReportMapper.deleteStockNow", page);
        dao.batchSave("StockReportMapper.saveStockNow", list);
    }


    public List<PageData> findCalcStockList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockReportMapper.findCalcStockList", page);
    }

    public List<PageData> findStockDtlListForShow(Page page) throws Exception {
        PageData pd = page.getPd();
        String ids = pd.getString("IDS");
        String[] arr = StringUtils.split(ids, Const.UNDER_LINE);
        pd.put("PRODUCT_ID", arr[0]);
        pd.put("CUSTOMER_ID", arr[1]);
        pd.put("PUT_LOCATOR", StringUtil.replaceRpId(arr[2]));
        pd.put("BOX_NO", StringUtil.replaceRpId(arr[3]));
        pd.put("LOT_NO", StringUtil.replaceRpId(arr[4]));
        pd.put("COO", StringUtil.replaceRpId(arr[5]));
        pd.put("DATE_CODE", StringUtil.replaceRpId(arr[6]));
        pd.put("INSTOCK_NO", StringUtil.replaceRpId(arr[7]));
        List<PageData> forList = (List<PageData>) dao.findForList("StockReportMapper.findStockDtlListForShowlistPage", page);
        if (forList == null || forList.isEmpty()) {
            return new ArrayList<>();
        }
        for (PageData p : forList) {
            //已分配未冻结的 算分配的
            if (WmsEnum.AssignedState.ALL.getItemKey().equals(p.getString("ASSIGNED_STOCK_STATE"))
                    && WmsEnum.FreezeState.NONE.getItemKey().equals(p.getString("FREEZE_STATE"))) {
                p.put("ASSIGNED_EA", p.getInteger("ASSIGNED_STOCK_NUM"));
            } else {
                p.put("ASSIGNED_EA", 0);
            }


            //冻结的
            if (WmsEnum.FreezeState.ALL.getItemKey().equals(p.getString("FREEZE_STATE"))) {
                int ass_ea = 0;
                if (WmsEnum.AssignedState.ALL.getItemKey().equals(p.getString("ASSIGNED_STOCK_STATE"))) {
                    ass_ea = p.getInteger("ASSIGNED_STOCK_NUM");
                } else {
                    ass_ea = p.getInteger("EA_NUM");
                }
                p.put("FREEZE_EA", ass_ea);
            } else {
                p.put("FREEZE_EA", 0);
            }
            //总数
            int TOTAL_EA =  p.getInteger("EA_NUM");
            p.put("TOTAL_EA", TOTAL_EA);
            //可用数
            int USE_EA = p.getInteger("TOTAL_EA") - p.getInteger("ASSIGNED_EA") - p.getInteger("FREEZE_EA");
            p.put("USE_EA", USE_EA);
        }
        return forList;
    }

    /**
     * 定时计算报表使用
     */
    public void saveCalcRpStockDtl() {
        logger.info("===Begin to Load stock dtl rp...");
        try {
            dao.delete("StockReportMapper.deleteRpStockDtl", null);
            dao.save("StockReportMapper.insertRpStockDtl", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    public List<PageData> selectRpStockForInsert() {
        try {
            return (List<PageData>) dao.findForList("StockReportMapper.selectRpStockForInsert", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void saveRpStockDtl(List<PageData> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        deleteRpStockDtl();
        List<PageData> lst = new ArrayList<>(150);
        int i = 0;
        for (PageData p : list) {
            i++;
            lst.add(newPd(p));
            if (i == Const.SAVE_SIZE) {
                i = 0;
                save(lst);
                lst.clear();
            }
        }
        if (!lst.isEmpty()) {
            save(lst);
        }
    }

    private void save(List<PageData> list) {
        try {
            dao.save("StockReportMapper.insertRpStockDtl", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PageData newPd(PageData src) {
        PageData pd = new PageData(src, true);
        addColum(pd, "INSTOCK_NO");
        addColum(pd, "REAL_INSTOCK_DATE");
        addColum(pd, "CUSTOMER_ID");
        addColum(pd, "PRODUCT_ID");
        addColum(pd, "BIG_BOX_NO");
        addColum(pd, "BOX_NO");
        addColum(pd, "TIME_TWO");
        addColum(pd, "DATE_CODE");
        addColum(pd, "LOT_NO");
        addColum(pd, "BIN_CODE");
        addColum(pd, "COO");
        addColum(pd, "REMARK1");
        addColum(pd, "REMARK2");
        addColum(pd, "TXT_SIX");
        addColum(pd, "INVNO");
        addColum(pd, "PUT_LOCATOR");
        addColum(pd, "RECEIV_QTY");
        addColum(pd, "IN_EA");
        addColum(pd, "EA_ACTUAL");
        addColum(pd, "FREEZE_EA");
        addColum(pd, "USE_EA");
        addColum(pd, "ASSIGNED_STATE");
        addColum(pd, "PUT_STATUS");
        addColum(pd, "FREEZE_STATE");
        addColum(pd, "ASSIGNED_STOCK_STATE");
        addColum(pd, "ORDER_NO");
        addColum(pd, "INB_REF_A");
        addColum(pd, "INVNO_A");
        addColum(pd, "SCAN_CODE");
        addColum(pd, "SEPARATE_QTY");
        addColum(pd, "TXT_THR");
        addColum(pd, "TXT_SEV");
        addColum(pd, "TXT_EIG");
        addColum(pd, "TXT_NIG");
        addColum(pd, "TXT_ELEVEN");
        addColum(pd, "TXT_TWELVE");
        addColum(pd, "TXT_FOURT");
        addColum(pd, "TXT_SEVENT");
        addColum(pd, "TXT_TWENT");
        addColum(pd, "MANUFACTUER");
        addColum(pd, "SHIPPER");
        addColum(pd, "EA_NUM");
        return pd;
    }

    private void addColum(PageData pd, String colum) {
        String str = pd.getString(colum);
        if (StringUtil.isBlank(str)) {
            pd.put(colum, Const.EMPTY_CH);
        }
    }

    /**
     * 清除库存明细报表数据
     */
    public void deleteRpStockDtl() {
        try {
            dao.delete("StockReportMapper.truncateRpStockDtl", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<PageData> listStockNowDtl(Page page) throws Exception {
        String locatorcode = page.getPd().getString("LOCATOR_CODE");
        String locatorid = BaseInfoCache.getInstance().getLocatorId(locatorcode);
        page.getPd().put("PUT_LOCATOR", locatorid);
        String searchType = page.getPd().getString(Const.SEARCH_TYPE);
        List<PageData> list;
        if (WmsEnum.RpSearchType.NEW.eq(searchType)) {
            list = (List<PageData>) dao.findForList("StockReportMapper.dataDtllistPage", page);
        } else {
            list = (List<PageData>) dao.findForList("StockReportMapper.dataDtl2listPage", page);
        }

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        for (PageData p : list) {
            setValue(p);
        }
        return list;
    }

    private List<PageData> listStockNowDtlNow(Page page) throws Exception {
        String locatorcode = page.getPd().getString("LOCATOR_CODE");
        String locatorid = BaseInfoCache.getInstance().getLocatorId(locatorcode);
        page.getPd().put("PUT_LOCATOR", locatorid);
        List<PageData> list = (List<PageData>) dao.findForList("StockReportMapper.dataDtllistPage", page);

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        for (PageData p : list) {
            setValue(p);
        }
        return list;
    }

    //FREEZE_EA USE_EA  ASSIGNED_STOCK_NUM
    private void setValue(PageData p) {
        String bigBoxNo = p.getString("BIG_BOX_NO");
        if (StringUtil.isBlank(bigBoxNo)) {
            p.put("BIG_BOX_NO", p.getString("BOX_NO"));
        }

        if (WmsEnum.FreezeState.ALL.eq(p.getString("FREEZE_STATE"))) {
            p.put("FREEZE_EA", p.getInteger("EA_NUM"));
            p.put("USE_EA", 0);
        } else {
            p.put("FREEZE_EA", 0);
            p.put("USE_EA", p.getInteger("EA_NUM") - p.getInteger("ASSIGNED_STOCK_NUM"));
        }
        p.put("IN_EA", p.getInteger("EA_NUM"));
        p.put("REAL_INSTOCK_DATE", DateUtil.formatDay(p.getString("REAL_INSTOCK_DATE")));
    }

    public List<PageData> listStockDtlForExl(Page page) throws Exception {
        List<PageData> datas = listdataDtllistForExl(page);
        List<PageData> varList = new ArrayList<>(datas.size() * 2);
        for (PageData pd : datas) {
            PageData vpd = new PageData();
            int seq = 1;
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("INSTOCK_NO"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("REAL_INSTOCK_DATE"));
            vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getCustomerCode(pd.getString("CUSTOMER_ID")));
            vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getProductCode(pd.getString("PRODUCT_ID")));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("BIG_BOX_NO"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("BOX_NO"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TIME_TWO"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("DATE_CODE"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("LOT_NO"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("BIN_CODE"));

            vpd.put(StringUtil.genParamStr(seq++), pd.getString("COO"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("REMARK1"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("REMARK2"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_SIX"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("INVNO"));
            vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getLocatorCode(pd.getString("PUT_LOCATOR")));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("RECEIV_QTY"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("IN_EA"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("EA_ACTUAL"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("FREEZE_EA"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("USE_EA"));

            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.AssignedState.getName(pd.getString("ASSIGNED_STATE")));    //6
            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.PutState.getName(pd.getString("PUT_STATUS")));
            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.FreezeState.getName(pd.getString("FREEZE_STATE")));
            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.AssignedState.getName(pd.getString("ASSIGNED_STOCK_STATE")));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("ORDER_NO"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("INB_REF_A"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("INVNO_A"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("SCAN_CODE"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("SEPARATE_QTY"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_THR"));

            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_SEV"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_EIG"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_NIG"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_ELEVEN"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_TWELVE"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_FOURT"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_SEVENT"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("TXT_TWENT"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("MANUFACTUER"));
            vpd.put(StringUtil.genParamStr(seq++), pd.getString("SHIPPER"));
            varList.add(vpd);
        }
        return varList;
    }

    public List<PageData> listdataDtllistForExl(Page page) throws Exception {
        String locatorcode = page.getPd().getString("LOCATOR_CODE");
        String locatorid = BaseInfoCache.getInstance().getLocatorId(locatorcode);
        page.getPd().put("PUT_LOCATOR", locatorid);
        String searchType = page.getPd().getString(Const.SEARCH_TYPE);
        List<PageData> list;
        if (WmsEnum.RpSearchType.NEW.eq(searchType)) {
            list = (List<PageData>) dao.findForList("StockReportMapper.dataDtllistForExcel", page);
        } else {
            list = (List<PageData>) dao.findForList("StockReportMapper.dataDtl2listForExcel", page);
        }

        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        for (PageData p : list) {
            setValue(p);
        }
        return list;
    }

    public List<String> getStockDtlHead() {
        List<String> titles = new ArrayList<>(64);
        titles.add("入库单号");
        titles.add("实际入库日期");
        titles.add("客户");
        titles.add("料号");
        titles.add("入库单明細.外箱号");
        titles.add("入库单明細.CaseNumber(内箱号)");
        titles.add("入库单明細.DATE CODE 转化");
        titles.add("入库单明細.DC");
        titles.add("入库单明細.LotCode");
        titles.add("入库单明細.BIN");
        titles.add("入库单明細.COO");
        titles.add("入库单明細.Remark1");
        titles.add("入库单明細.Remark2");
        titles.add("入库单明細.Remark3");
        titles.add("入库单明細.InvNo");
        titles.add("库位");
        titles.add("原始收货EA");
        titles.add("在库EA");
        titles.add("分配EA");
        titles.add("冻结EA");
        titles.add("可用EA");
        titles.add("分配状态");
        titles.add("码放状态");
        titles.add("冻结状态");
        titles.add("分配库存状态");
        titles.add("订单号(入库)");
        titles.add("入库单.THI RefNo");
        titles.add("入库单.客户RefNo");
        titles.add("入库单明細.Scan Code(料号)");
        titles.add("入库单明細.SeparateQty");
        titles.add("入库单明細.TEXT03");
        titles.add("入库单明細.TEXT07");
        titles.add("入库单明細.TEXT08");
        titles.add("入库单明細.TEXT09");
        titles.add("入库单明細.TEXT11");
        titles.add("入库单明細.TEXT12");
        titles.add("入库单明細.TEXT14");
        titles.add("入库单明細.TEXT17");
        titles.add("入库单明細.TEXT20");
        titles.add("入库单.Manufacturer");
        titles.add("入库单.Shipper");
        return titles;
    }

    @Deprecated
    public List<PageData> listdataDtllistForExl_bak(Page page) throws Exception {
        String locatorcode = page.getPd().getString("LOCATOR_CODE");
        String locatorid = BaseInfoCache.getInstance().getLocatorId(locatorcode);
        page.getPd().put("PUT_LOCATOR", locatorid);
        return (List<PageData>) dao.findForList("StockReportMapper.dataDtllistForExl", page);
    }

    private List<PageData> map2List(Map<String, PageData> map) {
        List<PageData> lst = new ArrayList<>();
        for (Map.Entry<String, PageData> entry : map.entrySet()) {
            lst.add(entry.getValue());
        }
        return lst;
    }

    private boolean isSearch(PageData pd) {
        if (Const.OPT_EVEN_1.equals(pd.getString("SEARCH_FLAG"))) {
            return true;
        }
        if (Const.OPT_EVEN_1.equals(pd.getString("EXP"))) {
            return true;
        }

		/*if (!pd.getStringNot4EmpyCh("REAL_INSTOCK_DATE_BEGIN").equals(pd.getString("REAL_INSTOCK_DATE_BEGIN_S"))) {
            return true;
		}
		if (!pd.getStringNot4EmpyCh("REAL_INSTOCK_DATE_END").equals(pd.getString("REAL_INSTOCK_DATE_END_S"))) {
			return true;
		}*/
        if (!pd.getStringNot4EmpyCh("CUSTOMER_ID").equals(pd.getString("CUSTOMER_ID_S"))) {
            return true;
        }
        if (!pd.getStringNot4EmpyCh("PRODUCT_ID").equals(pd.getString("PRODUCT_ID_S"))) {
            return true;
        }

        return false;
    }

    private boolean isCalc() {
        return true;
    }

    private boolean isCalc(PageData rs) {
        if (rs == null) {
            return true;
        }
        //如果当前时间大于上次计算时间的10分钟，则重新计算
        if (DateUtil.compareMinutesOfDate(new Date(), rs.getUtilDate("CREATE_TM"), 10)) {
            return true;
        }
        return false;
    }

}

