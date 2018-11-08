package com.hw.service.rp;

import com.hw.cache.BaseInfoCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* Created：panda.HuangWei
* Date：2016-12-14
*/
@Service("inboundRpService")
public class InboundRpService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	private int saveCnt = 100;


	public List<PageData> list(Page page)throws Exception{
		String customerId = page.getPd().getString("CUSTOMER_ID");
		String locatorcode=page.getPd().getString("LOCATOR_CODE");
		String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
		page.getPd().put("PUT_LOCATOR",locatorid);
		String searchType = page.getPd().getString(Const.SEARCH_TYPE);
		List<PageData> list;
		if (WmsEnum.RpSearchType.NEW.eq(searchType)) {
			list = (List<PageData>) dao.findForList("InboundRpMapper.datalistPage", page);
		} else {
			list = (List<PageData>) dao.findForList("InboundRpMapper.data2listPage", page);
		}
		return list;
	}


	public List<PageData> listDynamic(Page page)throws Exception{
		String customerId = page.getPd().getString("CUSTOMER_ID");
		String inboundSql = BaseInfoCache.getInstance().getInboundSql(customerId);
		page.getPd().put("COLUM",inboundSql);
		String locatorcode=page.getPd().getString("LOCATOR_CODE");
		String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
		page.getPd().put("PUT_LOCATOR",locatorid);
		return (List<PageData>)dao.findForList("InboundRpMapper.datalistPage", page);
	}

	public List<String> getListHead() {
		List<String> titles = new ArrayList<String>();
		titles.add("入库单编号");	//2
		titles.add("客户");	//3
		titles.add("预计入库日期");	//4
		titles.add("实际入库日期");	//5
		titles.add("订单号");	//6
		titles.add("箱.外箱号");
		titles.add("箱.内箱号");
		titles.add("料号");	//7
		titles.add("期望EA");
		titles.add("收货EA");
		titles.add("分配EA");
		titles.add("码放EA");
		titles.add("入库类型");
		titles.add("总毛重");

		titles.add("总净重");
		titles.add("总体积");
		titles.add("总价");
		titles.add("总期望EA");
		titles.add("优先级");
		titles.add("费用录入完成");
		titles.add("备注");

		titles.add("收货状态");
		titles.add("分配状态");
		titles.add("码放状态");

		titles.add("入库单.Carrier ETA");
		titles.add("入库单.Carrier ATA");

		titles.add("入库单.Date03");
		titles.add("入库单.Date04");
		titles.add("入库单.Date05");
		titles.add("入库单.Date06");
		titles.add("入库单.Date07");
		titles.add("入库单.Date08");
		titles.add("入库单.Date09");
		titles.add("入库单.Date10");

		titles.add("GW");
		titles.add("Total Invoice Value");
		titles.add("改單次數");

		titles.add("入库单.Number04");
		titles.add("入库单.Number05");
		titles.add("入库单.Number06");
		titles.add("入库单.Number07");
		titles.add("入库单.Number08");
		titles.add("入库单.Number09");
		titles.add("入库单.Number10");

		titles.add("THI RefNo");
		titles.add("Manufacturer");
		titles.add("Shipper");
		titles.add("MAWB/MBL");
		titles.add("HAWB/HBL");
		titles.add("# of package");

		titles.add("Declaration #");
		titles.add("Currency");
		titles.add("Flight No/Vsl+Voy");
		titles.add("Truck Manifest#");
		titles.add("TransportMode");
		titles.add("包裝單位");
		titles.add("發貨港口");
		titles.add("FLAG");
		titles.add("收貨方式");
		titles.add("CashAdvance");
		titles.add("PickArea");
		titles.add("是否報關");
		titles.add("OT");

		titles.add("客户RefNo");
		titles.add("入库日期");
		titles.add("DATE CODE 转化");
		titles.add("入仓时间");

		titles.add("明细.Date04");
		titles.add("明细.Date05");
		titles.add("明细.Date06");
		titles.add("明细.Date07");
		titles.add("明细.Date08");
		titles.add("明细.Date09");
		titles.add("明细.Date10");

		titles.add("SeparateQty");

		titles.add("明细.Number02");
		titles.add("明细.Number03");
		titles.add("明细.Number04");
		titles.add("明细.Number05");
		titles.add("明细.Number06");
		titles.add("明细.Number07");
		titles.add("明细.Number08");
		titles.add("明细.Number09");
		titles.add("明细.Number10");

		titles.add("CaseNumber(内箱号)");
		titles.add("InvNo");

		titles.add("明细.TEXT03");
		titles.add("明细.Remark1");
		titles.add("明细.Remark2");
		titles.add("明细.Remark3");
		titles.add("明细.TEXT07");
		titles.add("明细.TEXT08");
		titles.add("明细.TEXT09");
		titles.add("明细.外箱号");
		titles.add("明细.TEXT11");
		titles.add("明细.TEXT12");

		titles.add("Scan Code(料号)");
		titles.add("明细.TEXT14");
		titles.add("明细.COO");
		titles.add("明细.LotCode");
		titles.add("明细.TEXT17");
		titles.add("明细.DC");
		titles.add("明细.BIN");
		titles.add("明细.TEXT20");
		titles.add("库位");
		return titles;
	}

	public List<PageData> listForExcel(Page page) throws Exception {
		String locatorcode=page.getPd().getString("LOCATOR_CODE");
		String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
		page.getPd().put("PUT_LOCATOR",locatorid);
		String searchType = page.getPd().getString(Const.SEARCH_TYPE);
		List<PageData> list;
		if (WmsEnum.RpSearchType.NEW.eq(searchType)) {
			list = (List<PageData>) dao.findForList("InboundRpMapper.datalistForExcel", page);
		} else {
			list = (List<PageData>) dao.findForList("InboundRpMapper.data2listForExcel", page);
		}

		List<PageData> varList = new ArrayList<PageData>(list.size()*2);
		for (PageData data : list) {
			PageData vpd = new PageData();
			int seq = 1;
			vpd.put(StringUtil.genParamStr(seq++), data.getString("INSTOCK_NO"));
			vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getCustomerCode(data.getString("CUSTOMER_ID")));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("PRE_INSTOCK_DATE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("REAL_INSTOCK_DATE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("ORDER_NO"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_BIG_BOX_NO"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_BOX_NO"));
			vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getProductCode(data.getString("PRODUCT_ID")));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("EA_EA"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("EA_ACTUAL"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("ASSIGNED_EA"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("PUT_EA"));

			String instock_type = data.getString("INSTOCK_TYPE");
			instock_type = "70".equals(instock_type)?"一般":"退货";
			vpd.put(StringUtil.genParamStr(seq++),instock_type);
			vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_WEIGHT"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_SUTTLE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_VOLUME"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_PRICE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_EA"));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.PriorityLevel.getName(data.getString("PRIORITY_LEVEL")));
			String cost_state = data.getString("COST_STATE");
			cost_state = "1".equals(cost_state)?"是":"否";
			vpd.put(StringUtil.genParamStr(seq++),cost_state);
			vpd.put(StringUtil.genParamStr(seq++), data.getString("MEMO"));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.InstockState.getName(data.getString("INSTOCK_TYPE")));

			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.AssignedState.getName(data.getString("ASSIGNED_STATE")));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.PutState.getName(data.getString("PUT_STATE")));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("CARRIER_ETA"));
			/*vpd.put(StringUtil.genParamStr(seq++), data.getString("TIME_ONE"));*/
			vpd.put(StringUtil.genParamStr(seq++), data.getString("CARRIER_ATA"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE03"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE04"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE05"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE06"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE07"));

			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE08"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE09"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE10"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("GW"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("TOTAL_INVOICE_VALUE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("C1_NUM_THR"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("NUMBER04"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("NUMBER05"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("NUMBER06"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("NUMBER07"));

			vpd.put(StringUtil.genParamStr(seq++), data.getString("NUMBER08"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("NUMBER09"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("NUMBER10"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("INBOUND_REFERENCE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("MANUFACTURER"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("SHIPPER"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("MAWB_MBL"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("HAWB_HBL"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("OF_PACKAGE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("DECLARATION"));

			vpd.put(StringUtil.genParamStr(seq++), data.getString("CURRENCY"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("FLIGHT_NO"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("TRUCK_MANIFEST"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("TRANSPORTMODE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("C1_TXT_TWELVE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("C1_TXT_THIRT"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("C1_TXT_FOURT"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("C1_TXT_FIFT"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("CASHADVANCE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("PICKAREA"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("C1_TXT_EIGHT"));

			vpd.put(StringUtil.genParamStr(seq++), data.getString("OT"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("INVOICENO"));
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

    /**
	 * 计算入库报表数据
	 */
	public void saveRpInStock(){
		try {
			dao.delete("InboundRpMapper.deleteRpInStock",null);
			dao.save("InboundRpMapper.insertRpInStock",null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//  insertRpInStock
	public List<PageData> selectRpStockForInsert() {
		try {
			return (List<PageData>)dao.findForList("InboundRpMapper.selectRpInStockForInsert", null);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	public void saveRpInStock(List<PageData> list)  {
		if (list == null || list.isEmpty()) {
			return;
		}
		deleteRpInStock();
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

    //@Transactional
	private void save(List<PageData> list)  {
		try {
			dao.save("InboundRpMapper.insertRpInStock", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private PageData newPd(PageData src) {
		PageData pd = new PageData(src,true);
		addColum(pd, "INSTOCK_NO");
		addColum(pd, "CUSTOMER_ID");
		addColum(pd, "PRE_INSTOCK_DATE");
		addColum(pd, "REAL_INSTOCK_DATE");
		addColum(pd, "ORDER_NO");
		addColum(pd, "PRODUCT_ID");
		addColum(pd, "EA_EA");
		addColum(pd, "EA_ACTUAL");
		addColum(pd, "ASSIGNED_EA");
		addColum(pd, "PUT_EA");
		addColum(pd, "INSTOCK_TYPE");
		addColum(pd, "TOTAL_WEIGHT");
		addColum(pd, "TOTAL_SUTTLE");
		addColum(pd, "TOTAL_VOLUME");
		addColum(pd, "TOTAL_PRICE");
		addColum(pd, "TOTAL_EA");
		addColum(pd, "PRIORITY_LEVEL");
		addColum(pd, "COST_STATE");
		addColum(pd, "MEMO");
		addColum(pd, "INSTOCK_STATE");
		addColum(pd, "ASSIGNED_STATE");
		addColum(pd, "PUT_STATE");
		addColum(pd, "CARRIER_ETA");
		addColum(pd, "CARRIER_ATA");
		addColum(pd, "DATE03");
		addColum(pd, "DATE04");
		addColum(pd, "DATE05");
		addColum(pd, "DATE06");
		addColum(pd, "DATE07");
		addColum(pd, "DATE08");
		addColum(pd, "DATE09");
		addColum(pd, "DATE10");
		addColum(pd, "GW");
		addColum(pd, "TOTAL_INVOICE_VALUE");
		addColum(pd, "C1_NUM_THR");
		addColum(pd, "NUMBER04");
		addColum(pd, "NUMBER05");
		addColum(pd, "NUMBER06");
		addColum(pd, "NUMBER07");
		addColum(pd, "NUMBER08");
		addColum(pd, "NUMBER09");
		addColum(pd, "NUMBER10");
		addColum(pd, "INBOUND_REFERENCE");
		addColum(pd, "MANUFACTURER");
		addColum(pd, "SHIPPER");
		addColum(pd, "MAWB_MBL");
		addColum(pd, "HAWB_HBL");
		addColum(pd, "OF_PACKAGE");
		addColum(pd, "DECLARATION");
		addColum(pd, "CURRENCY");
		addColum(pd, "FLIGHT_NO");
		addColum(pd, "TRUCK_MANIFEST");
		addColum(pd, "TRANSPORTMODE");
		addColum(pd, "C1_TXT_TWELVE");
		addColum(pd, "C1_TXT_THIRT");
		addColum(pd, "C1_TXT_FOURT");
		addColum(pd, "C1_TXT_FIFT");
		addColum(pd, "CASHADVANCE");
		addColum(pd, "PICKAREA");
		addColum(pd, "C1_TXT_EIGHT");
		addColum(pd, "OT");
		addColum(pd, "INVOICENO");
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
		return pd;
	}

	private void addColum(PageData pd,String colum) {
		String str = pd.getString(colum);
		if (StringUtil.isBlank(str)) {
			pd.put(colum,Const.EMPTY_CH);
		}
	}


	/**
	 * 清除入库报表数据
	 */
	public void deleteRpInStock() {
		try {
			dao.delete("InboundRpMapper.truncateRpInStock",null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

