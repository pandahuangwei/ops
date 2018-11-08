package com.hw.service.rp;

import com.hw.cache.BaseInfoCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.job.RpCalcJob;
import com.hw.util.*;
import org.slf4j.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
* Created：panda.HuangWei
* Date：2016-12-14
*/
@Service("breakboxRpService")
public class BreakboxRpService {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(BreakboxRpService.class);

	@Resource(name = "daoSupport")
	private DaoSupport dao;


	public List<PageData> list(Page page)throws Exception{

		if ("1".equals(page.getPd().getString("SEARCH_FLAG"))) {
			if(!calc(page))
			{
				return new ArrayList<>();
			}
		}

		return (List<PageData>)dao.findForList("BreakboxRpMapper.datalistPage", page);
	}

	public boolean calc(Page page)throws Exception{
		/*String locatorcode=page.getPd().getString("LOCATOR_CODE");
		String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
		page.getPd().put("PUT_LOCATOR",locatorid);*/
		//1根据查询条件查找符合条件的记录
		List<PageData> boxList = (List<PageData>)dao.findForList("BreakboxRpMapper.findBoxListSelf", page);
		if (boxList == null || boxList.isEmpty()) {
			return false;
		}
		//根据上面1得到的结果查询 原始箱，与 拆箱记录
		 List<PageData> boxParentList = findParentList(getIdList(boxList, "OLD_BOX_ID"),page);
		 List<PageData> boxSonList = findSonList(getIdList(boxList, "BOX_ID"),page);
		//List<PageData> boxParentList = findParentList(getIdList(boxList, "OLD_BOX_ID"));
		//List<PageData> boxSonList = findSonList(getIdList(boxList, "BOX_ID"));
		//将要计算的记录 = 合并箱记录，箱的父亲，箱的儿子记录
		List<PageData> list = mergeList(boxList, boxParentList, boxSonList);

		Map<String,PageData> srcBox = new HashMap<>();
		Map<String,List<PageData>> breakBox = new HashMap<>();
		if(boxList.isEmpty()){
			return false;
		}
		goupBox(list,srcBox,breakBox);


		Set<String> dtlId = getDtlId(breakBox);
		if (dtlId == null || dtlId.isEmpty()) {
			return false;
		}

        //获取根据拆箱的记录获取到分配到出库明细的记录
		List<PageData> outstockList = (List<PageData>)dao.findForList("BreakboxRpMapper.findOutstockListByBoxId", new ArrayList<>(dtlId));;

		Map<String,List<PageData>> outstock = new HashMap<>();
		goupOutstock(outstockList,outstock);
		List<PageData> calcRs = calc(srcBox, breakBox, new HashMap<>(), outstock);
		if (calcRs == null || calcRs.isEmpty()) {
			return false;
		}
		dao.delete("BreakboxRpMapper.deleteBreakBox",page.getPd());
		List<PageData> saveList = new ArrayList<>(128);
		int totalCnt = calcRs.size();
		logger.info("total break dtl,size : {}",totalCnt);
		for (int i = 0,lastIndex =totalCnt-1 ;i<totalCnt;i++) {
			saveList.add(calcRs.get(i));
			if (saveList.size() == 100 || i == lastIndex) {
				dao.save("BreakboxRpMapper.saveBreakBox",saveList);
				saveList.clear();
			}
		}
		return true;
	}

	/**
	 * 将箱记录，箱的父亲，箱的儿子记录合并
	 * @param boxList 箱记录
	 * @param boxParentList 箱的父亲记录
	 * @param boxSonList 箱的儿子记录
     * @return 合并之后的记录
     */
	private List<PageData> mergeList(List<PageData> boxList,List<PageData> boxParentList,List<PageData> boxSonList) {
		List<PageData> list = new ArrayList<>(boxList.size()*2);
		Set<String> boxIdSet = new HashSet<>(boxList.size()*2);
		add2List(list,boxIdSet,boxList);
		add2List(list,boxIdSet,boxParentList);
		add2List(list,boxIdSet,boxSonList);
		return list;
	}

	private void add2List(List<PageData> list,Set<String> boxIdSet,List<PageData> srcList) {
		if (srcList == null || srcList.isEmpty()) {
			return;
		}
		for (PageData p : srcList) {
			String boxId = p.getString("BOX_ID");
			if (boxIdSet.contains(boxId)) {
				continue;
			}
			boxIdSet.add(boxId);
			list.add(p);
		}
	}

	private List<PageData> findParentList(Set<String> oldBoxIdSet,Page page) throws Exception{
		if (oldBoxIdSet == null || oldBoxIdSet.isEmpty()) {
			return null;
		}
		page.getPd().put("IDS",new ArrayList<>(oldBoxIdSet));
		return (List<PageData>)dao.findForList("BreakboxRpMapper.findBoxListParentPage", page);
	}

	private List<PageData> findSonList(Set<String> boxIdSet,Page page) throws Exception{
		if (boxIdSet == null || boxIdSet.isEmpty()) {
			return null;
		}
		page.getPd().put("IDS",new ArrayList<>(boxIdSet));
		return (List<PageData>)dao.findForList("BreakboxRpMapper.findBoxListSonPage", page);
	}

	private List<PageData> findParentList(Set<String> oldBoxIdSet) throws Exception{
		if (oldBoxIdSet == null || oldBoxIdSet.isEmpty()) {
			return null;
		}
		return (List<PageData>)dao.findForList("BreakboxRpMapper.findBoxListParent", new ArrayList<>(oldBoxIdSet));
	}

	private List<PageData> findSonList(Set<String> boxIdSet) throws Exception{
		if (boxIdSet == null || boxIdSet.isEmpty()) {
			return null;
		}
		return (List<PageData>)dao.findForList("BreakboxRpMapper.findBoxListSon", new ArrayList<>(boxIdSet));
	}

	private Set<String> getIdList(List<PageData> boxList, String columName) {
		Set<String> set = new HashSet<>();
		for (PageData p : boxList) {
			String v = p.getString(columName);
			if (StringUtil.isEmpty(v)) {
				continue;
			}
			set.add(v);
		}
		return set;
	}

	private Set<String> getDtlId(Map<String,List<PageData>> map) {
		Set<String> set = new HashSet<>(32);
		for (Map.Entry<String,List<PageData>> entry : map.entrySet()) {
			for (PageData p : entry.getValue()) {
				set.add(p.getString("BOX_ID"));
			}
		}
		return set;
	}

	public boolean calc_old(Page page)throws Exception{
		String locatorcode=page.getPd().getString("LOCATOR_CODE");
		String locatorid=BaseInfoCache.getInstance().getLocatorId(locatorcode);
		page.getPd().put("PUT_LOCATOR",locatorid);
		List<PageData> boxList = (List<PageData>)dao.findForList("BreakboxRpMapper.findBoxList", page);
		String customer = page.getPd().getString("CUSTOMER_ID");
		List<PageData> instockList;
		if ("c1".equalsIgnoreCase(customer)) {
			instockList = (List<PageData>)dao.findForList("BreakboxRpMapper.findInstockListReotec", page);
		} else {
			instockList = (List<PageData>)dao.findForList("BreakboxRpMapper.findInstockList", page);
		}

		List<PageData> outstockList;
		if ("c1".equalsIgnoreCase(customer)) {
			outstockList = (List<PageData>)dao.findForList("BreakboxRpMapper.findOutstockListreotec", page);
		} else {
			outstockList = (List<PageData>)dao.findForList("BreakboxRpMapper.findOutstockList", page);
		}

		Map<String,PageData> srcBox = new HashMap<>();
		Map<String,List<PageData>> breakBox = new HashMap<>();
		if(boxList.isEmpty())
		{
			return false;
		}
		goupBox(boxList,srcBox,breakBox);

		Map<String,PageData> instock = new HashMap<>();
		goupInstock(instockList,instock);

		Map<String,List<PageData>> outstock = new HashMap<>();
		goupOutstock(outstockList,outstock);
		List<PageData> calcRs = calc(srcBox, breakBox, instock, outstock);
        if (calcRs == null || calcRs.isEmpty()) {
			return false;
		}
		dao.delete("BreakboxRpMapper.deleteBreakBox",page.getPd());
		List<PageData> saveList = new ArrayList<>(128);
		int totalCnt = calcRs.size();
		logger.info("total break dtl,size : {}",totalCnt);
		for (int i = 0,lastIndex =totalCnt-1 ;i<totalCnt;i++) {
			saveList.add(calcRs.get(i));
			if (saveList.size() == 100 || i == lastIndex) {
				dao.save("BreakboxRpMapper.saveBreakBox",saveList);
				saveList.clear();
			}
		}
		return true;
	}

	private List<PageData> calc(Map<String,PageData> srcBoxMap,Map<String,List<PageData>> breakBoxMap,Map<String,PageData> instockMap,Map<String,List<PageData>> outstockMap) {
		List<PageData> list = new ArrayList<>();

        PageData pd;
		int id = 0;
		for(Map.Entry<String,PageData> box : srcBoxMap.entrySet()) {
			pd = getPageData();
			PageData value = box.getValue();

			pd.put("BOX_ID",value.getString("BOX_ID"));
			pd.put("BOX_NO",value.getString("BOX_NO"));
			pd.put("PRODUCT_CODE",BaseInfoCache.getInstance().getProductCode(value.getString("PRODUCT_ID")));
			pd.put("CUSTOMER_ID",value.getString("CUSTOMER_ID"));
			pd.put("INSTOCK_NO",value.getString("INSTOCK_NO"));
			/*pd.put("OUTSTOCK_NO",value.getString("OUTSTOCK_NO"));*/
			pd.put("EA_NUM",value.getString("EA_NUM"));
			pd.put("SEPARATE_QTY",value.getString("SEPARATE_QTY"));
			pd.put("COO",value.getString("COO"));
			pd.put("DATE_CODE",value.getString("DATE_CODE"));
			pd.put("LOT_NO",value.getString("LOT_NO"));
			pd.put("BIN_CODE",value.getString("BIN_CODE"));
			String remark1 = StringUtil.isEmpty(value.getString("REMARK1"))?Const.EMPTY_CH:value.getString("REMARK1");
			String remark2 = StringUtil.isEmpty(value.getString("REMARK2"))?Const.EMPTY_CH:value.getString("REMARK2");
			String remark3 = StringUtil.isEmpty(value.getString("REMARK3"))?Const.EMPTY_CH:value.getString("REMARK3");
			pd.put("REMARK1",remark1);
			pd.put("REMARK2",remark2);
			pd.put("REMARK3",remark3);

			pd.put("LOCATOR_CODE",BaseInfoCache.getInstance().getLocatorCode(value.getString("PUT_LOCATOR")));
			pd.put("P_FLG",0);
			pd.put("P_ID",id);
			pd.put("CREATE_EMP", SessionUtil.getCurUserName());

            //入库
			PageData instock = instockMap.get(box.getKey());
			if (instock!= null ) {
				pd.put("INVOICE_NO",value.getString("INVOICE_NO"));
				pd.put("HAWB_HBL",value.getString("HAWB_HBL"));
			}

			//判断是否有拆箱
			List<PageData> breakList = breakBoxMap.get(box.getKey());
			//拆箱
			if (breakList != null) {
				int eaNum = 0;
				for (PageData p : breakList) {
					String key = p.getString("BOX_ID")+Const.SPLIT_LINE+p.getString("PRODUCT_ID");
					List<PageData> pageDatas = outstockMap.get(key);
					PageData outPd= getPageData();
					int countEa = 0;
					if (pageDatas != null) {
						for (PageData out :pageDatas) {
							outPd = getPageData();
							outPd.put("P_FLG",1);
							outPd.put("OUTSTOCK_NO",out.getString("OUTSTOCK_NO"));
							outPd.put("SI_REFERENCE",out.getString("SI_REFERENCE"));
							outPd.put("INVOICE_NO1",out.getString("INVOICE_NO1"));

							outPd.put("ASSIGNED_STOCK_NUM",out.getString("ASSIGNED_EA"));
							outPd.put("SEND_EA",out.getString("SEND_EA"));
							outPd.put("SEND_BOX",out.getString("BOX_NO"));
							outPd.put("P_ID",id);
							outPd.put("SI_REFERENCE",out.getString("SI_REFERENCE"));
							outPd.put("CREATE_EMP", SessionUtil.getCurUserName());

							outPd.put("DATE_CODE",out.getString("DATE_CODE"));
							outPd.put("LOT_NO",out.getString("LOT_NO"));
							outPd.put("BIN_CODE",out.getString("BIN_CODE"));
							outPd.put("COO",out.getString("COO"));
							outPd.put("REMARK1",out.getString("REMARK1"));
							outPd.put("REMARK2",out.getString("REMARK2"));
							outPd.put("REMARK3",out.getString("REMARK3"));


							countEa += out.getInteger("SEND_EA");

							String depot_state = out.getString("DEPOT_STATE");
							if (StringUtil.isNotEmpty(depot_state) && WmsEnum.DepotState.ALL.getItemKey().equals(depot_state)) {
								outPd.put("DEPOT_STATE","已发货");
							} else {
								outPd.put("DEPOT_STATE","未发货");
							}
							if (StringUtil.isNotEmpty(outPd.getString("OUTSTOCK_NO"))) {
								list.add(outPd);
							}
						}
						if (countEa != 0 ) {
							int srcEa = pd.getInteger("EA_NUM");
							int assignEa = outPd.getInteger("SEND_EA");
							if (srcEa == assignEa) {
								pd.put("DEPOT_STATE","已发货");
							} else {
								if (srcEa > assignEa && assignEa != 0) {
									pd.put("DEPOT_STATE","部分发货");
								} else if (assignEa == 0){
									pd.put("DEPOT_STATE","未发货");
								}else if (assignEa == 0){
									pd.put("DEPOT_STATE","部分发货");
								}
							}

						} else {
							pd.put("DEPOT_STATE","未发货");
						}

						list.add(pd);

						id += 1;
					}
					eaNum += p.getInteger("EA_NUM");
				}
				pd.put("EA_NUM",pd.getInteger("EA_NUM")+eaNum);
			} else {
				//不拆箱
				List<PageData> pageDatas = outstockMap.get(box.getKey());
				PageData outPd= new PageData();;
				int countEa = 0;
				if (pageDatas != null) {
					for (PageData out :pageDatas) {
						outPd = getPageData();
						outPd.put("P_FLG",1);
						outPd.put("OUTSTOCK_NO",out.getString("OUTSTOCK_NO"));
						outPd.put("SI_REFERENCE",out.getString("SI_REFERENCE"));
						outPd.put("INVOICE_NO1",out.getString("INVOICE_NO1"));
						outPd.put("ASSIGNED_STOCK_NUM",out.getString("ASSIGNED_EA"));
						outPd.put("SEND_EA",out.getString("SEND_EA"));
						outPd.put("SEND_BOX",out.getString("BOX_NO"));
						outPd.put("SI_REFERENCE",out.getString("SI_REFERENCE"));
						outPd.put("CREATE_EMP", SessionUtil.getCurUserName());

						outPd.put("DATE_CODE",out.getString("DATE_CODE"));
						outPd.put("LOT_NO",out.getString("LOT_NO"));
						outPd.put("BIN_CODE",out.getString("BIN_CODE"));
						outPd.put("COO",out.getString("COO"));
						outPd.put("REMARK1",out.getString("REMARK1"));
						outPd.put("REMARK2",out.getString("REMARK2"));
						outPd.put("REMARK3",out.getString("REMARK3"));

						outPd.put("P_ID",id);
						countEa = out.getInteger("SEND_EA");

						String depot_state = out.getString("DEPOT_STATE");
						if (StringUtil.isNotEmpty(depot_state) && WmsEnum.DepotState.ALL.getItemKey().equals(depot_state)) {
							outPd.put("DEPOT_STATE","已发货");
						} else {
							outPd.put("DEPOT_STATE","未发货");
						}
						if (StringUtil.isNotEmpty(outPd.getString("OUTSTOCK_NO"))) {
							list.add(outPd);
						}
					}

					if (countEa != 0 ) {
						int srcEa = pd.getInteger("EA_NUM");
						int assignEa = outPd.getInteger("SEND_EA");
						if (srcEa > assignEa && assignEa != 0) {
							pd.put("DEPOT_STATE","部分发货");
						} else if (assignEa == 0){
							pd.put("DEPOT_STATE","未发货");
						}else if (assignEa == 0){
							pd.put("DEPOT_STATE","部分发货");
						}
					} else {
						pd.put("DEPOT_STATE","未发货");
					}

					list.add(pd);

					id += 1;
				}


			}
		}
		return list;
	}


	//将箱的纪录拆分为原始箱与拆分出来的箱
   private void goupBox(List<PageData> boxList,Map<String,PageData> srcBox,Map<String,List<PageData>> breakBox) {
		for (PageData p : boxList) {
			String OLD_BOX_ID = p.getString("OLD_BOX_ID");
			String key;
			if (StringUtil.isEmpty(OLD_BOX_ID)) {
				key = p.getString("BOX_ID")+ Const.SPLIT_LINE+p.getString("PRODUCT_ID");
				srcBox.put(key,p);
			} else {
				key = p.getString("OLD_BOX_ID")+ Const.SPLIT_LINE+p.getString("PRODUCT_ID");
				List<PageData> lst = breakBox.get(key);
				if (lst == null) {
					lst = new ArrayList<>();
				}
				lst.add(p);
				breakBox.put(key,lst);
			}
		}
   }

	private void goupInstock(List<PageData> instckList,Map<String,PageData> instock) {
		for (PageData p : instckList) {
			String BOX_ID = p.getString("STOCKDTLMGRIN_ID");
			String PRODUCT_ID= p.getString("PRODUCT_ID");
			String key = BOX_ID+ Const.SPLIT_LINE+PRODUCT_ID;
			instock.put(key,p);
		}
	}

	private void goupOutstock(List<PageData> outstckList,Map<String,List<PageData>> outstock) {
		for (PageData p : outstckList) {
			String BOX_ID = p.getString("STOCKDTLMGROUT_ID");
			String PRODUCT_ID= p.getString("PRODUCT_ID");
			String key = BOX_ID+ Const.SPLIT_LINE+PRODUCT_ID;
			List<PageData> list = outstock.get(key);
			if (list == null) {
				list = new ArrayList<>();
			}
			list.add(p);
			outstock.put(key,list);
		}
	}

	private PageData getPageData() {
		PageData pd = new PageData();
		pd.put("P_ID",Const.ZERO);
		pd.put("P_FLG",Const.ZERO);
		pd.put("BOX_ID",Const.EMPTY_CH);
		pd.put("BOX_NO",Const.EMPTY_CH);
		pd.put("PRODUCT_CODE",Const.EMPTY_CH);

		pd.put("CUSTOMER_ID",Const.EMPTY_CH);
		pd.put("INSTOCK_NO",Const.EMPTY_CH);
		pd.put("OUTSTOCK_NO",Const.EMPTY_CH);
		pd.put("EA_NUM",Const.EMPTY_CH);
		pd.put("SEPARATE_QTY",Const.EMPTY_CH);

		pd.put("COO",Const.EMPTY_CH);
		pd.put("LOCATOR_CODE",Const.EMPTY_CH);
		pd.put("INVOICE_NO",Const.EMPTY_CH);
		pd.put("INVOICE_NO1",Const.EMPTY_CH);
		pd.put("HAWB_HBL",Const.EMPTY_CH);

		pd.put("ASSIGNED_STOCK_NUM",Const.EMPTY_CH);
		pd.put("SEND_EA",Const.EMPTY_CH);
		pd.put("SEND_BOX",Const.EMPTY_CH);
		pd.put("DEPOT_STATE",Const.EMPTY_CH);
		pd.put("SI_REFERENCE",Const.EMPTY_CH);
		pd.put("CREATE_EMP",SessionUtil.getCurUserName());

		pd.put("COO",Const.EMPTY_CH);
		pd.put("DATE_CODE",Const.EMPTY_CH);
		pd.put("LOT_NO",Const.EMPTY_CH);
		pd.put("BIN_CODE",Const.EMPTY_CH);
		pd.put("REMARK1",Const.EMPTY_CH);
		pd.put("REMARK2",Const.EMPTY_CH);
		pd.put("REMARK3",Const.EMPTY_CH);
		return pd;
	}

	public List<PageData> listForExcel(Page page) throws Exception{
		List<PageData> list = (List<PageData>) dao.findForList("BreakboxRpMapper.datalistForExcel", page);
		if (list == null || list.isEmpty()) {
			return new ArrayList<>();
		}
		List<PageData> rs = new ArrayList<>(list.size());
		for (PageData p : list) {
			int seq = 1;
			PageData vpd = new PageData();
			vpd.put(StringUtil.genParamStr(seq++), p.getString("P_ID"));	//1
			if (Const.DEL_NO.equals(p.getString("P_FLG"))) {
				//vpd.put(StringUtil.genParamStr(seq++), p.getString("INVOICE_NO"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("INSTOCK_NO"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("PRODUCT_CODE"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("BOX_NO"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("LOCATOR_CODE"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("SEPARATE_QTY"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("DATE_CODE"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("LOT_NO"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("BIN_CODE"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("COO"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("REMARK1"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("REMARK2"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("REMARK3"));
				//vpd.put(StringUtil.genParamStr(seq++), p.getString("HAWB_HBL"));
				vpd.put(StringUtil.genParamStr(seq++), p.getString("EA_NUM"));
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);//DEPOT_STATE
			} else {
				//vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++),Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++),Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				//vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), Const.EMPTY_CH);
				vpd.put(StringUtil.genParamStr(seq++), p.getString("DEPOT_STATE"));//DEPOT_STATE
			}
			vpd.put(StringUtil.genParamStr(seq++), p.getString("OUTSTOCK_NO"));
			//vpd.put(StringUtil.genParamStr(seq++), p.getString("INVOICE_NO1"));
			//vpd.put(StringUtil.genParamStr(seq++), p.getString("SI_REFERENCE"));
			vpd.put(StringUtil.genParamStr(seq++), p.getString("SEND_BOX"));
			vpd.put(StringUtil.genParamStr(seq++), p.getString("ASSIGNED_STOCK_NUM"));
			vpd.put(StringUtil.genParamStr(seq++), p.getString("SEND_EA"));
			rs.add(vpd);
		}
		return rs;
	}

	public List<String> getHeadList() {
		List<String> titles = new ArrayList<String>();
		titles.add("组");
		//titles.add("Reotec Reference no.");
		titles.add("入库单号");
		titles.add("产品编码");
		titles.add("Case Number");
		titles.add("库位");
		titles.add("SeparateQty");
		titles.add("DateCode");
		titles.add("LotCode");
		titles.add("BIN");
		titles.add("COO");
		titles.add("Remark1");
		titles.add("Remark2");
		titles.add("Remark3");
		//titles.add("HAWB/HBL");
		titles.add("收货数量(EA)");
		titles.add("发货状态");
		titles.add("發貨單號");
	/*	titles.add("Reotec Reference no.1");
		titles.add("SI Ref.");*/
		titles.add("发货箱号");
		titles.add("分配库存EA");
		titles.add("发货EA");
		return titles;
	}
}

