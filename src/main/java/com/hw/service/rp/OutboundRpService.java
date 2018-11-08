package com.hw.service.rp;

import com.hw.cache.BaseInfoCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.Const;
import com.hw.util.PageData;
import com.hw.util.StringUtil;
import com.hw.util.WmsEnum;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Service("outboundRpService")
public class OutboundRpService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    private int saveCnt = 100;

    public List<PageData> list(Page page) throws Exception {
       /* String locatorcode=page.getPd().getString("LOCATOR_CODE");
        String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
        page.getPd().put("PUT_LOCATOR",locatorid);*/
        String searchType = page.getPd().getString(Const.SEARCH_TYPE);
        List<PageData> list;
        if (WmsEnum.RpSearchType.NEW.eq(searchType)) {
            list = (List<PageData>) dao.findForList("OutboundRpMapper.datalistPage", page);
        } else {
            list = (List<PageData>) dao.findForList("OutboundRpMapper.data2listPage", page);
        }
        calcEa(list);
        return list;
    }

    public List<PageData> listForExcel(Page page) throws Exception {
        String locatorcode=page.getPd().getString("LOCATOR_CODE");
        String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
        page.getPd().put("PUT_LOCATOR",locatorid);
        String searchType = page.getPd().getString(Const.SEARCH_TYPE);
        List<PageData> list;
        if (WmsEnum.RpSearchType.NEW.eq(searchType)) {
            list = (List<PageData>) dao.findForList("OutboundRpMapper.datalistForExcel", page);
        } else {
            list = (List<PageData>) dao.findForList("OutboundRpMapper.data2listForExcel", page);
        }
        calcEa(list);
        List<PageData> varList = new ArrayList<PageData>(list.size()*2);
        int seq = 0;
        for (PageData data: list ) {
            PageData vpd = new PageData();
            seq = 1;
            vpd.put(StringUtil.genParamStr(seq++), data.getString("OUTSTOCK_NO"));
            vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getCustomerCode(data.getString("CUSTOMER_ID")));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("PRE_OUT_DATE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("REAL_OUT_DATE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("ORDER_NO"));
            vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getProductCode(data.getString("PRODUCT_ID")));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_BIG_BOX_NO"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_BOX_NO"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_DATE_CODE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_BIN_CODE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_COO"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_LOT_NO"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_REMARK1"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_REMARK2"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_REMARK3"));

           // vpd.put(StringUtil.genParamStr(seq++), data.getString("EA_EA"));
           // vpd.put(StringUtil.genParamStr(seq++), data.getString("ASSIGNED_EA"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("RECEIV_QTY"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("USE_EA"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("FREEZE_EA"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("ASSIGNED_EA"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("PICK_EA"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("CARGO_EA"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("SEND_EA"));

            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.OutStockType.getName(data.getString("OUTSTOCK_TYPE")));
            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.OutStockWay.getName(data.getString("OUTSTOCK_WAY")));

            vpd.put(StringUtil.genParamStr(seq++),WmsEnum.PriorityLevel.getName(data.getString("PRIORITY_LEVEL")));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_WEIGHT"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_SUTTLE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_VOLUME"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_PRICE"));//总价
            String cost_state = data.getString("COST_STATE");
            cost_state = "1".equals(cost_state)?"是":"否";
            vpd.put(StringUtil.genParamStr(seq++),cost_state);
            vpd.put(StringUtil.genParamStr(seq++), data.getString("MEMO"));
            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.AssignedState.getName(data.getString("ASSIGNED_STATE")));
            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.PickState.getName(data.getString("PICK_STATE")));
            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.CargoState.getName(data.getString("CARGO_STATE")));
            vpd.put(StringUtil.genParamStr(seq++), WmsEnum.DepotState.getName(data.getString("DEPOT_STATE")));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("C1"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C2"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C3"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C4"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C5"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C6"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C7"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C8"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C9"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C10"));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("C11"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C12"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C13"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C14"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C15"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C16"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C17"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C18"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C19"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C20"));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("C21"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C22"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C23"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C24"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C25"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C26"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C27"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C28"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C29"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C30"));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("C31"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C32"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C33"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C34"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C35"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C36"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C37"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C38"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C39"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("C40"));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_ONE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_TWO"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_THR"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_FOU"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_FIV"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_SIX"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_SEV"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_EIG"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_NIG"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_TEN"));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_ONE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_TWO"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_THR"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_FOU"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_FIV"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_SIX"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_SEV"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_EIG"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_NIG"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("NUM_TEN"));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_ONE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_TWO"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_THR"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_FOU"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_FIV"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_SIX"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_SEV"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_EIG"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_NIG"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_TEN"));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_ELEVEN"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_TWELVE"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_THIRT"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_FOURT"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_FIFT"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_SIXT"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_SEVENT"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_EIGHT"));

            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_NINET"));
            vpd.put(StringUtil.genParamStr(seq++), data.getString("TXT_TWENT"));

            vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getLocatorCode(data.getString("PUT_LOCATOR")));

            varList.add(vpd);
        }
        return varList;
    }

    public void saveRpOutStock() {
        try {
            dao.delete("OutboundRpMapper.deleteRpOutStock",null);
            dao.save("OutboundRpMapper.insertRpOutStock",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PageData> selectRpStockForInsert() {
        try {
            return (List<PageData>)dao.findForList("OutboundRpMapper.selectRpStockForInsert", null);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void saveRpOutStock(List<PageData> list)  {
        if (list == null || list.isEmpty()) {
            return;
        }
        deleteRpOutStock();
        List<PageData> lst = new ArrayList<>(150);
        int i = 0;
        for (PageData p : list) {
            i++;
            lst.add(newPd(p));
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

    private void calcEa(List<PageData> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (PageData p : list) {
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
    }

   // @Transactional
    private void save(List<PageData> list)  {
        try {
            dao.save("OutboundRpMapper.insertRpStock", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PageData newPd(PageData src) {
        PageData pd = new PageData(src,true);
        addColum(pd, "OUTSTOCK_NO");
        addColum(pd, "CUSTOMER_ID");
        addColum(pd, "PRE_OUT_DATE");
        addColum(pd, "REAL_OUT_DATE");
        addColum(pd, "ORDER_NO");
        addColum(pd, "PRODUCT_ID");
        addColum(pd, "EA_EA");
        addColum(pd, "ASSIGNED_EA");
        addColum(pd, "RECEIV_QTY");
        addColum(pd, "ASSIGNED_STOCK_STATE");
        addColum(pd, "ASSIGNED_STOCK_NUM");
        addColum(pd, "FREEZE_STATE");
        addColum(pd, "SEND_EA");
        addColum(pd, "CARGO_EA");
        addColum(pd, "PICK_EA");
        addColum(pd, "OUTSTOCK_TYPE");
        addColum(pd, "OUTSTOCK_WAY");
        addColum(pd, "PRIORITY_LEVEL");
        addColum(pd, "TOTAL_WEIGHT");
        addColum(pd, "TOTAL_SUTTLE");
        addColum(pd, "TOTAL_VOLUME");
        addColum(pd, "TOTAL_PRICE");
        addColum(pd, "COST_STATE");
        addColum(pd, "MEMO");
        addColum(pd, "ASSIGNED_STATE");
        addColum(pd, "PICK_STATE");
        addColum(pd, "CARGO_STATE");
        addColum(pd, "DEPOT_STATE");
        addColum(pd, "C1");
        addColum(pd, "C2");
        addColum(pd, "C3");
        addColum(pd, "C4");
        addColum(pd, "C5");
        addColum(pd, "C6");
        addColum(pd, "C7");
        addColum(pd, "C8");
        addColum(pd, "C9");
        addColum(pd, "C10");
        addColum(pd, "C11");
        addColum(pd, "C12");
        addColum(pd, "C13");
        addColum(pd, "C14");
        addColum(pd, "C15");
        addColum(pd, "C16");
        addColum(pd, "C17");
        addColum(pd, "C18");
        addColum(pd, "C19");
        addColum(pd, "C20");
        addColum(pd, "C21");
        addColum(pd, "C22");
        addColum(pd, "C23");
        addColum(pd, "C24");
        addColum(pd, "C25");
        addColum(pd, "C26");
        addColum(pd, "C27");
        addColum(pd, "C28");
        addColum(pd, "C29");
        addColum(pd, "C30");
        addColum(pd, "C31");
        addColum(pd, "C32");
        addColum(pd, "C33");
        addColum(pd, "C34");
        addColum(pd, "C35");
        addColum(pd, "C36");
        addColum(pd, "C37");
        addColum(pd, "C38");
        addColum(pd, "C39");
        addColum(pd, "C40");
        addColum(pd, "TIME_ONE");
        addColum(pd, "TIME_TWO");
        addColum(pd, "TIME_THR");
        addColum(pd, "TIME_FOU");
        addColum(pd, "TIME_FIV");
        addColum(pd, "TIME_SIX");
        addColum(pd, "TIME_SEV");
        addColum(pd, "TIME_EIG");
        addColum(pd, "TIME_NIG");
        addColum(pd, "TIME_TEN");
        addColum(pd, "NUM_ONE");
        addColum(pd, "NUM_TWO");
        addColum(pd, "NUM_THR");
        addColum(pd, "NUM_FOU");
        addColum(pd, "NUM_FIV");
        addColum(pd, "NUM_SIX");
        addColum(pd, "NUM_SEV");
        addColum(pd, "NUM_EIG");
        addColum(pd, "NUM_NIG");
        addColum(pd, "NUM_TEN");
        addColum(pd, "TXT_ONE");
        addColum(pd, "TXT_TWO");
        addColum(pd, "TXT_THR");
        addColum(pd, "TXT_FOU");
        addColum(pd, "TXT_FIV");
        addColum(pd, "TXT_SIX");
        addColum(pd, "TXT_SEV");
        addColum(pd, "TXT_EIG");
        addColum(pd, "TXT_NIG");
        addColum(pd, "TXT_TEN");
        addColum(pd, "TXT_ELEVEN");
        addColum(pd, "TXT_TWELVE");
        addColum(pd, "TXT_THIRT");
        addColum(pd, "TXT_FOURT");
        addColum(pd, "TXT_FIFT");
        addColum(pd, "TXT_SIXT");
        addColum(pd, "TXT_SEVENT");
        addColum(pd, "TXT_EIGHT");
        addColum(pd, "TXT_NINET");
        addColum(pd, "TXT_TWENT");
        addColum(pd, "PUT_LOCATOR");
        addColum(pd, "BOX_LOT_NO");
        addColum(pd, "BOX_DATE_CODE");
        addColum(pd, "BOX_SEPARATE_QTY");
        addColum(pd, "BOX_COO");
        addColum(pd, "BOX_BIN_CODE");
        addColum(pd, "BOX_BIG_BOX_NO");
        addColum(pd, "BOX_BOX_NO");
        addColum(pd, "BOX_REMARK1");
        addColum(pd, "BOX_REMARK2");
        addColum(pd, "BOX_REMARK3");

        return pd;
    }

    private void addColum(PageData pd,String colum) {
        String str = pd.getString(colum);
        if (StringUtil.isBlank(str)) {
            pd.put(colum,Const.EMPTY_CH);
        }
    }

    /**
     * 清除出库报表数据
     */
    public void deleteRpOutStock() {
        try {
            dao.delete("OutboundRpMapper.truncateRpOutStock",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PageData> listDync(Page page) throws Exception {
        String customerId = page.getPd().getString("CUSTOMER_ID");
        String inboundSql = BaseInfoCache.getInstance().getOutboundSql(customerId);
        page.getPd().put("COLUM",inboundSql);
        String locatorcode=page.getPd().getString("LOCATOR_CODE");
        String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
        page.getPd().put("PUT_LOCATOR",locatorid);
        return (List<PageData>) dao.findForList("OutboundRpMapper.datalistPage", page);
    }

    public List<String> getListHead() {
        List<String> titles = new ArrayList<String>();
        titles.add("发货单号");
        titles.add("客户");
        titles.add("预计发货日期");
        titles.add("实际发货日期");
        titles.add("订单号");
        titles.add("料号");

        titles.add("箱.外箱号");
        titles.add("箱.箱号");
        titles.add("箱.DateCode");
        titles.add("箱.BIN");
        titles.add("箱.COO");
        titles.add("箱.Lotcode");
        titles.add("箱.Remark1");
        titles.add("箱.Remark2");
        titles.add("箱.Remark3");

        titles.add("原始收货EA数");
        titles.add("可用EA数");
        titles.add("冻结EA数");
        titles.add("分配EA数");
        titles.add("拣货EA数");
        titles.add("配载EA数");
        titles.add("发货EA数");
        titles.add("出库类型");
        titles.add("发货类型");
        titles.add("优先级");
        titles.add("毛重");
        titles.add("净重");
        titles.add("体积");
        titles.add("总价");
        titles.add("费用录入已完成");
        titles.add("备注");
        titles.add("分配状态");
        titles.add("拣货状态");

        titles.add("配载状态");
        titles.add("发货状态");
        titles.add("出库单.ETD");
        titles.add("出库单.ATD");
        titles.add("出库单.Date03");
        titles.add("出库单.Date04");
        titles.add("出库单.Date05");
        titles.add("出库单.Date06");
        titles.add("出库单.Date07");
        titles.add("出库单.Date08");
        titles.add("出库单.Date09");
        titles.add("出库单.Date10");

        titles.add("出库单.Total Invoice Value");
        titles.add("出库单.打帶箱數");
        titles.add("出库单.出庫重量");
        titles.add("出库单.出貨箱數");
        titles.add("出库单.拆箱箱數");
        titles.add("出库单.改單次數");
        titles.add("出库单.標籤");
        titles.add("出库单.Number08");
        titles.add("出库单.Number09");
        titles.add("出库单.Number10");

        titles.add("出库单.Consignee Name");
        titles.add("出库单.MAWB/MBL");
        titles.add("出库单.HAWB/HBL");
        titles.add("出库单.Truck Manifest#");
        titles.add("出库单.Currency");
        titles.add("出库单.CustomerRefNo");
        titles.add("出库单.Declaration #");
        titles.add("出库单.目的港口");
        titles.add("出库单.船名航次/航班");
        titles.add("出库单.SI Reference");

        titles.add("出库单.TruckType");
        titles.add("出库单.InvoiceNo");
        titles.add("出库单.出貨模式");
        titles.add("出库单.包裝單位");
        titles.add("出库单.運輸公司");
        titles.add("出库单.是否報關");
        titles.add("出库单.TransportMode");
        titles.add("出库单.Delivery Area");
        titles.add("出库单.OT");
        titles.add("出库单.FLAG");

        titles.add("明細.入库日期");
        titles.add("明細.DATE CODE 转化");
        titles.add("明細.实际入仓时间");
        titles.add("明細.Date04");
        titles.add("明細.Date05");
        titles.add("明細.Date06");
        titles.add("明細.Date07");
        titles.add("明細.Date08");
        titles.add("明細.Date09");
        titles.add("明細.Date10");

        titles.add("明細.Separate QTY");
        titles.add("明細.Number02");
        titles.add("明細.Number03");
        titles.add("明細.Number04");
        titles.add("明細.Number05");
        titles.add("明細.Number06");
        titles.add("明細.Number07");
        titles.add("明細.Number08");
        titles.add("明細.Number09");
        titles.add("明細.Number10");

        titles.add("明細.CaseNumber(内箱号)");
        titles.add("明細.InvNo");
        titles.add("明細.Field23");
        titles.add("明細.Remark1");
        titles.add("明細.Remark2");
        titles.add("明細.Remark3");
        titles.add("明細.TEXT07");
        titles.add("明細.TEXT08");
        titles.add("明細.TEXT09");
        titles.add("明細.外箱号");

        titles.add("明細.TEXT11");
        titles.add("明細.TEXT12");
        titles.add("明細.Scan Code");
        titles.add("明細.TEXT14");
        titles.add("明細.COO");
        titles.add("明細.LotCode");
        titles.add("明細.Field37");
        titles.add("明細.DC");
        titles.add("明細.BIN");
        titles.add("明細.TEXT20");
        titles.add("库位");
        return titles;
    }

}

