package com.hw.service.instock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.hw.cache.BaseInfoCache;
import com.hw.util.*;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;

/**
* Created：panda.HuangWei
* Date：2016-12-14
*/
@Service("boxService")
public class BoxService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("BoxMapper.save", pd);
	}

	public void saveAudit(PageData pd)throws Exception{
		pd.put("RECEIPT_STATE", WmsEnum.InstockState.NONE.getItemKey());
		pd.put("ASSIGNED_STATE",WmsEnum.AssignedState.NONE.getItemKey());
		pd.put("PUT_STATUS",WmsEnum.PutState.NONE.getItemKey());
		pd.put("ASSIGNED_STOCK_STATE",WmsEnum.AssignedState.NONE.getItemKey());
		pd.put("PICK_STATE",WmsEnum.PickState.NONE.getItemKey());
		pd.put("CARGO_STATE",WmsEnum.CargoState.NONE.getItemKey());
		pd.put("DEPOT_TYPE",WmsEnum.DepotState.NONE.getItemKey());
		pd.put("FREEZE_STATE", WmsEnum.FreezeState.NONE.getItemKey());

		pd.put("CREATE_EMP", SessionUtil.getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "2");	//删除标志



		dao.save("BoxMapper.save", pd);
	}


	public void saveAuditPass(PageData pd)throws Exception{

		PageData pageData = findById(pd);
		pageData.put("AUDIT_EMP", SessionUtil.getCurUserName());	//创建人
		pageData.put("AUDIT_TM", Tools.date2Str(new Date()));	//创建时间
		pageData.put("AUDIT_FLG", WmsEnum.AuditFlg.APPLY.getItemKey());	//修改时间

	}


	/*STOCKMGRIN_ID, ROW_NO, CUSTOMER_ID, PRODUCT_ID, PRODUCT_TYPE, PACK_RULE, PACK_UNIT, OT_EA, OT_RATIO, PALLET_EA,
	PALLET_RATIO, BOX_EA, BOX_RATIO, INPACK_EA, INPACK_RATIO, EA_EA, EA_RATIO, LONG_UT, WIDE_UT, HIGH_UT, UNIT_VOLUME,
	TOTAL_VOLUME, TOTAL_WEIGHT, TOTAL_SUTTLE, TOTAL_PRICE, UNIT_PRICE, CURRENCY_TYPE, STORAGE_ID, LOCATOR_ID, CAR_TYPE,
	CAR_PLATE, SEAL_NO, TP_HAULIER, FREEZE_CODE, MGR_SERIAL, MEMO, IMP_FLG, RECEIPT_STATE, DISTR_STATE, PUT_STATUS,
	CREATE_EMP, CREATE_TM, MODIFY_EMP, MODIFY_TM, DEL_FLG, STOCKDTLMGRIN_ID,BOX_NO,EA_ACTUAL,RECEI_FLG*/

	private PageData buildDtl(PageData p) throws Exception{
		PageData searchPd = new PageData();
		searchPd.put("INSTOCK_NO", p.getString("INSTOCK_NO"));
		searchPd.put("PRODUCT_ID", p.getString("PRODUCT_ID"));

		PageData pd =(PageData)dao.findForObject("StockMgrInMapper.findDtlPdByNoAndProd", searchPd);


		pd.put("STOCKDTLMGRIN_ID", p.getString("BOX_ID"));
		pd.put("CUSTOMER_ID", p.getString("CUSTOMER_ID"));

		pd.put("STORAGE_ID", p.getString("PUT_STORAGE"));
		pd.put("LOCATOR_ID", p.getString("PUT_LOCATOR"));

		pd.put("FREEZE_CODE", p.getString("FREEZE_CODE"));
		pd.put("RECEIPT_STATE", WmsEnum.InstockState.ALL.getItemKey());
		pd.put("DISTR_STATE", p.getString("ASSIGNED_STATE"));
		pd.put("PUT_STATUS", p.getString("PUT_STATUS"));
		pd.put("BOX_NO", p.getString("BOX_NO"));

		pd.put("EA_EA", p.getInteger("EA_NUM"));
		pd.put("EA_ACTUAL", p.getInteger("EA_NUM"));

		pd.put("CREATE_EMP", SessionUtil.getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除标志
		pd.put("RECEI_FLG", "1");	//删除标志
		return pd;
	}

	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("BoxMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("BoxMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		List<PageData> forList = (List<PageData>) dao.findForList("BoxMapper.datalistPage", page);
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
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BoxMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BoxMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BoxMapper.deleteAll", ArrayDATA_IDS);
	}

	public List<String> getExcelTitle() {
		List<String> titles = new ArrayList<String>(32);
		titles.add("箱号");
		titles.add("外箱号");
		titles.add("客户");
		titles.add("产品");
		titles.add("LotCode");
		titles.add("DateCode");
		titles.add("BIN");
		titles.add("COO");
		titles.add("SeperateQty");
		titles.add("Remark1");
		titles.add("Remark2");
		titles.add("Remark3");
		titles.add("进货单编号");
		titles.add("码放库区");
		titles.add("码放库位");
		titles.add("原始收货EA数");
		titles.add("EA数");
		titles.add("收货状态");
		titles.add("分配状态");
		titles.add("码放状态");
		titles.add("分配库存状态");
		titles.add("拣货状态");
		titles.add("配载状态");
		titles.add("发货状态");
		titles.add("冻结状态");
		return titles;
	}

	public List<PageData> getExcelContent(Page page) throws Exception{
		List<PageData> list = (List<PageData>) dao.findForList("BoxMapper.datalistForExcel", page);
		if (list == null || list.isEmpty()) {
			return new ArrayList<>();
		}

		List<PageData> varList = new ArrayList<PageData>(list.size()*2);
		int seq = 0;
		for (PageData data: list ) {
			PageData vpd = new PageData();
			seq = 1;
			vpd.put(StringUtil.genParamStr(seq++), data.getString("BIG_BOX_NO"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("BOX_NO"));
			vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getCustomerCode(data.getString("CUSTOMER_ID")));
			vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getProductCode(data.getString("PRODUCT_ID")));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("LOT_NO"));

			vpd.put(StringUtil.genParamStr(seq++), data.getString("DATE_CODE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("BIN_CODE"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("COO"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("SEPARATE_QTY"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("REMARK1"));

			vpd.put(StringUtil.genParamStr(seq++), data.getString("REMARK2"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("REMARK3"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("INSTOCK_NO"));
			vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getStorageCode(data.getString("PUT_STORAGE")));//
			vpd.put(StringUtil.genParamStr(seq++), BaseInfoCache.getInstance().getLocatorCode(data.getString("PUT_LOCATOR")));

			vpd.put(StringUtil.genParamStr(seq++), data.getString("RECEIV_QTY"));
			vpd.put(StringUtil.genParamStr(seq++), data.getString("EA_NUM"));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.InstockState.getName(data.getString("RECEIPT_STATE")));//
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.AssignedState.getName(data.getString("ASSIGNED_STATE")));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.PutState.getName(data.getString("PUT_STATUS")));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.AssignedState.getName(data.getString("ASSIGNED_STOCK_STATE")));

			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.PickState.getName(data.getString("PICK_STATE")));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.CargoState.getName(data.getString("CARGO_STATE")));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.DepotState.getName(data.getString("DEPOT_TYPE")));
			vpd.put(StringUtil.genParamStr(seq++), WmsEnum.FreezeState.getName(data.getString("FREEZE_STATE")));

			varList.add(vpd);
		}
		return varList;
	}
}

