package com.hw.service.instock;

import java.util.*;

import javax.annotation.Resource;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;

/**
* Created：panda.HuangWei
* Date：2016-10-27
*/
@Service("stockmgrinService")
public class StockMgrInService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		PageData pdtl = buildOrderProperty(pd);
		pdtl.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_ORDER.getItemValue()));
		dao.save("StockMgrInMapper.save", pd);
		dao.save("StockMgrInMapper.saveOrderDtl", pdtl);
	}


	//收货确认
	public void saveConfirm(PageData pd)throws Exception{
		pd.put("REAL_INSTOCK_DATE", Tools.date2Str(new Date()));
		dao.update("StockMgrInMapper.saveConfirm",pd);
	}


	private PageData buildOrderProperty(PageData pd) {
		List<String> columList = WmsEnum.OrderProperty.columList;
		//属性明细的数据
		PageData pdtl = new PageData();
		for (String colum : columList) {
			String value = pd.getString(colum);
			if(StringUtil.isEmpty(value)) {
				pdtl.put(colum, Const.EMPTY_CH);
			} else {
				pdtl.put(colum, value);
			}
		}

		//设值ID，入库单ID，入库单属性类型
		String pid = StringUtil.isEmpty(pd.getString("P_ID")) ? UuidUtil.get32UUID():pd.getString("P_ID");
		pdtl.put("P_ID", pid);
		pdtl.put("STOCKMGRIN_ID",pd.getString("STOCKMGRIN_ID"));
		pdtl.put("STOCKDTLMGRIN_ID",pd.getString("STOCKDTLMGRIN_ID"));
		//pdtl.put("ORDERBATCH_TYPE",SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_ORDER.getItemValue()));
		return pdtl;
	}

	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("StockMgrInMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		PageData pdtl = buildOrderProperty(pd);
		dao.update("StockMgrInMapper.edit", pd);
		dao.update("StockMgrInMapper.editOrderDtl", pdtl);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StockMgrInMapper.datalistPage", page);
	}

	/**
	 * 更新入库单的收货状态
	 * @param pd 带STOCKMGRIN_ID的实体
	 * @throws Exception 异常处理
	 */
	private void setState(PageData pd) throws Exception {
		//更新收货状态
		String instockState = getInstockState(pd);
		pd.put("INSTOCK_STATE",instockState);
		dao.save("StockMgrInMapper.editStockState", pd);
	}

	//根据条件 判断收货状态  editStockState
	public String getInstockState(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList("StockMgrInMapper.findInstockState", pd);

		int total = list.size();
		int zeroCnt = 0;
		int hadRecCnt = 0;
		//根据实际EA与期望EA比较
		for(PageData rs :list) {
			int EA_ACTUAL = rs == null ?0 :rs.getInteger("EA_ACTUAL");
			int EA_EA = rs == null ?0 :rs.getInteger("EA_EA");

			if (EA_ACTUAL == 0) {
				zeroCnt +=1;
			}

			if(EA_ACTUAL >= EA_EA) {
				hadRecCnt +=1;
			}

		}

		if(total == zeroCnt) {
			return  WmsEnum.InstockState.NONE.getItemKey();
		}

		if (hadRecCnt == total) {
			return WmsEnum.InstockState.ALL.getItemKey();
		}
		return WmsEnum.InstockState.PART.getItemKey();
	}

	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StockMgrInMapper.listAll", pd);
	}

	/**
	 * 根据明细ID 查找到可以退货的纪录
	 * @param ids 退货明细Id列表
	 * @return 结果集
	 * @throws Exception 处理异常
     */
	public List<PageData> findDtlReceive(String[] ids)throws Exception{
		return (List<PageData>)dao.findForList("StockMgrInMapper.findDtlReceive", ids);
	}



	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		pd.put("ORDERBATCH_TYPE",SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_ORDER.getItemValue()));
		return (PageData)dao.findForObject("StockMgrInMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StockMgrInMapper.deleteAll", ArrayDATA_IDS);
		dao.delete("StockMgrInMapper.deleteAllDtl", ArrayDATA_IDS);
	}

	/*
	* 删除
	*/
	public void deleteDtl(PageData pd)throws Exception{
		dao.delete("StockMgrInMapper.deleteDtl", pd);
		setState(pd);
	}

	/*
	* 批量删除
	*/
	public void deleteDtlAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StockMgrInMapper.deleteDtlAll", ArrayDATA_IDS);
	}

	public void saveDtlOnly(PageData pd)throws Exception{
		PageData pdtl = buildOrderProperty(pd);
		pdtl.put("ORDERBATCH_TYPE",SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_BATCH.getItemValue()));
		dao.save("StockMgrInMapper.saveProdDtl", pd);
		dao.save("StockMgrInMapper.saveOrderDtl", pdtl);
	}
	/*
	* 新增产品明细<br>
	*  考虑产品重复，还有新增的逻辑
	*/
	public void saveDtl(PageData pd)throws Exception{
		Set<String> hadSet = getHadList(pd.getString("HAD_PRODS"));
		String curProdId = pd.getString("PRODUCT_ID");
		//如果新增的产品已经存在的话，则需要做update
		if (hadSet.contains(curProdId)) {
			PageData hadPd = findDtlByPidAndProdId(pd);
			setUpdatePd(pd,hadPd);
			editDtl(pd);
		} else {
			saveDtlOnly(pd);
		}
	}

	//该逻辑暂时放弃
	private void setUpdatePd(PageData newPd,PageData hadPd) {
		//设置明细ID
		newPd.put("STOCKDTLMGRIN_ID",hadPd.getString("STOCKDTLMGRIN_ID"));

	}


	private Set<String> getHadList(String strs) {
		if (StringUtil.isEmpty(strs)) {
			return new HashSet<>();
		}

		String[] ids = StringUtils.split(strs,Const.SPLIT_COMMA);
		return new HashSet<>(Arrays.asList(ids));
	}

	/*
	* 通过入库单Id，跟产品ID获取已经存在的一笔明细
	*/
	public PageData findDtlByPidAndProdId(PageData pd){
		try {
			List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findDtlByPidAndProd", pd);
			if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
  * 修改
  */
	public void editDtl(PageData pd)throws Exception{
		PageData pdtl = buildOrderProperty(pd);
		dao.update("StockMgrInMapper.editDtl", pd);
		dao.update("StockMgrInMapper.editOrderDtl", pdtl);
	}

	/*
	* 通过id获取产品明细数据
	*/
	public List<PageData> findDtl(PageData pd)throws Exception{
		pd.put("ORDERBATCH_TYPE",SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_BATCH.getItemValue()));
		List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findDtl", pd);
		//list.sort((e1,e2) -> Integer.parseInt(e1.getString("ROW_NO")) -Integer.parseInt(e2.getString("ROW_NO")) );
		list.sort((e1,e2) ->{
			if(e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO") ){
				return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
			} else {
				return e1.getInteger("ROW_NO") -e2.getInteger("ROW_NO");
			}
		});
		return list;

	}


	/*
	* 通过id获取产品明细数据,
	* 用于js修改页面的显示
	*/
	public List<PageData> findDtlAssignlistPage(Page page)throws Exception{
		List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findDtlAssignlistPage", page);
		if(list == null || list.isEmpty()) {
			return new ArrayList<>();
		}
		for(PageData p : list) {
			setEmptyValue(p,"PRODUCT_TYPE_CN");
			setEmptyValue(p,"SCAN_CODE");
			setEmptyValue(p,"BOX_NO");
			setEmptyValue(p,"EA_ACTUAL");
			setEmptyValue(p,"STORAGE_NAME");
			setEmptyValue(p,"LOCATORE_CODE");
		}
		//e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM")
		//list.sort((e1,e2) -> ((e1.getInteger("ROW_NO") -e2.getInteger("ROW_NO"))) );
		list.sort((e1,e2) ->{
			if(e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO") ){
				return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
			} else {
				return e1.getInteger("ROW_NO") -e2.getInteger("ROW_NO");
			}
		});
		return list;
	}

	/*
	* 通过id获取产品明细数据,
	* 用于js修改页面的显示
	*/
	public List<PageData> findDtlName(Page page)throws Exception{

		String customerId = page.getPd().getString("CUSTOMER_ID");
		String inStockNo = Const.EMPTY_CH;
 		if (StringUtil.isEmpty(customerId)) {
			Pair<String, String> pair = findCustomerIdByStockInId(page.getPd());
			customerId = pair.getLeft();
			inStockNo = pair.getRight();
		}

		String sql = BaseInfoCache.getInstance().getInStockBatchSql(customerId);
		page.getPd().put("DYNC_E",sql);

		page.getPd().put("ORDERBATCH_TYPE",SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_BATCH.getItemValue()));
		List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findDtlNamelistPage", page);
	    if(list == null || list.isEmpty()) {
			return new ArrayList<>();
		}
		for(PageData p : list) {
	    	p.put("INSTOCK_NO",inStockNo);
	    	p.put("BIG_BOX_NO",BaseInfoCache.getInstance().getBigBoxNo(p.getString("STOCKDTLMGRIN_ID")));
			setEmptyValue(p,"PRODUCT_TYPE_CN");
			setEmptyValue(p,"SCAN_CODE");
			setEmptyValue(p,"BOX_NO");
			setEmptyValue(p,"EA_ACTUAL");
			setEmptyValue(p,"STORAGE_NAME");
		}
		//e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM")
		//list.sort((e1,e2) -> ((e1.getInteger("ROW_NO") -e2.getInteger("ROW_NO"))) );
		/*list.sort((e1,e2) ->{
			 if(e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO") ){
				 return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
			 } else {
				return e1.getInteger("ROW_NO") -e2.getInteger("ROW_NO");
			 }
		});*/
		return list;
	}

	public List<PageData> findDtlPutStatelistPage(Page page)throws Exception{
		List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findDtlPutStatelistPage", page);
		if(list == null || list.isEmpty()) {
			return new ArrayList<>();
		}
		for(PageData p : list) {
			setEmptyValue(p,"SCAN_CODE");
			setEmptyValue(p,"BOX_NO");
			setEmptyValue(p,"EA_ACTUAL");
			p.put("BIG_BOX_NO", BaseInfoCache.getInstance().getBigBoxNo(p.getString("STOCKDTLMGRIN_ID")));
		}
		list.sort((e1,e2) ->{
			if(e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO") ){
				return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
			} else {
				return e1.getInteger("ROW_NO") -e2.getInteger("ROW_NO");
			}
		});
		return list;
	}



	/*
	* 通过id获取产品明细数据,
	* 用于js修改页面的显示
	*/
	public List<PageData> findDtlName_bak(PageData pd)throws Exception{
		pd.put("ORDERBATCH_TYPE",SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_BATCH.getItemValue()));
		List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findDtlNamelistPage", pd);
		if(list == null || list.isEmpty()) {
			return new ArrayList<>();
		}
		for(PageData p : list) {
			setEmptyValue(p,"PRODUCT_TYPE_CN");
			setEmptyValue(p,"SCAN_CODE");
			setEmptyValue(p,"BOX_NO");
			setEmptyValue(p,"EA_ACTUAL");
			setEmptyValue(p,"STORAGE_NAME");
		}
		//e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM")
		//list.sort((e1,e2) -> ((e1.getInteger("ROW_NO") -e2.getInteger("ROW_NO"))) );
		list.sort((e1,e2) ->{
			if(e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO") ){
				return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
			} else {
				return e1.getInteger("ROW_NO") -e2.getInteger("ROW_NO");
			}
		});
		return list;
	}

	/**
	 * 根据入库单得到已收货明细
	 * @return
	 * @throws Exception
     */
	public List<PageData> findCancelDtlName(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StockMgrInMapper.findCancelDtlNamelistPage", page);
	}



	private void setEmptyValue(PageData pd,String colum) {
		if(StringUtil.isEmpty(pd.getString(colum))) {
			pd.put(colum,Const.EMPTY_CH);
		}
	}


	public PageData findDtlById(PageData pd)throws Exception{
		pd.put("ORDERBATCH_TYPE",WmsEnum.OrderPropertyType.IN_BATCH.getItemKey());
		return (PageData)dao.findForObject("StockMgrInMapper.findDtl", pd);

	}

	public PageData findByInStockNo(String inStockNo)throws Exception{
		PageData pd = new PageData();
		pd.put("INSTOCK_NO",inStockNo);
		List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findByInStockNoForCheck", pd);
		if(list !=null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public PageData findDtlPdByPidAndProd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StockMgrInMapper.findDtlPdByPidAndProd",pd);
	}

	public PageData findByProdCheck(PageData pd) {
		try {

			PageData idPd = (PageData) dao.findForObject("StockMgrInMapper.findByProdCheck", pd);
			if (idPd == null || StringUtil.isEmpty(idPd.getString("STOCKMGRIN_ID"))) {
				return null;
			}
			idPd.put("PRODUCT_CODE",BaseInfoCache.getInstance().getProductCode(pd.getString("PRODUCT_ID")));
			return idPd;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public PageData findForReceiveCheck(PageData pd) {
		try {
			//A.STOCKMGRIN_ID, A.INSTOCK_NO,B.PRODUCT_ID, B.CUSTOMER_ID, C.PRODUCT_CODE,(B.EA_EA-B.EA_ACTUAL) RECEI_EA
			PageData idPd = (PageData)dao.findForObject("StockMgrInMapper.findStockIdByInStockNo", pd);
			if (idPd == null || StringUtil.isEmpty(idPd.getString("STOCKMGRIN_ID"))) {
				return null;
			}
			String stockInId = idPd.getString("STOCKMGRIN_ID");
			pd.put("STOCKMGRIN_ID",stockInId);
			pd.put("ORDERBATCH_TYPE",WmsEnum.OrderPropertyID.IN_BATCH.getItemKey());
			PageData dtlPd = (PageData) dao.findForObject("StockMgrInMapper.findForReceiveCheck", pd);
			if (dtlPd == null || StringUtil.isEmpty(dtlPd.getString("STOCKDTLMGRIN_ID"))) {
				return null;
			} else {
				dtlPd.put("PRODUCT_CODE",BaseInfoCache.getInstance().getProductCode(pd.getString("PRODUCT_ID")));
				pd.put("STOCKDTLMGRIN_ID",dtlPd.getString("STOCKDTLMGRIN_ID"));
				PageData propertyPd = (PageData) dao.findForObject("StockMgrInMapper.findInStockProperty", pd);
				if (propertyPd != null) {
					//TXT_TWO,TXT_FOU,TXT_FIV,TXT_SIX
					dtlPd.put("INV_NO",propertyPd.getString("TXT_TWO"));
					dtlPd.put("REMARK1",propertyPd.getString("TXT_FOU"));
					dtlPd.put("REMARK2",propertyPd.getString("TXT_FIV"));
					dtlPd.put("REMARK3",propertyPd.getString("TXT_SIX"));
					dtlPd.put("PACKAGE_QTY",propertyPd.getString("NUM_ONE"));
					dtlPd.put("COO",propertyPd.getString("COO"));
					dtlPd.put("LOT_CODE",propertyPd.getString("LOT_CODE"));
					dtlPd.put("DATE_CODE",propertyPd.getString("DATE_CODE"));
					dtlPd.put("BIN_CODE",propertyPd.getString("BIN_CODE"));
				} else {
					dtlPd.put("INV_NO",Const.EMPTY_CH);
					dtlPd.put("REMARK1",Const.EMPTY_CH);
					dtlPd.put("REMARK2",Const.EMPTY_CH);
					dtlPd.put("REMARK3",Const.EMPTY_CH);
					dtlPd.put("PACKAGE_QTY",Const.ZERO_CH);
				}
				return dtlPd;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public List<PageData> findDtlByInStockNo(String inStockNo) {
		PageData pd = new PageData();
		pd.put("INSTOCK_NO",inStockNo);
		try {
			List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findByInStockNoForCheck2", pd);
			if(list ==null || list.isEmpty()) {
                return new ArrayList<>();
            } else {
                return list;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	/**
	 * 判断入库单是否存在
	 * @param inStockNo 入库单
	 * @return  true/false
     */
	public boolean isExists(String inStockNo){
		try {
			PageData data = findByInStockNo(inStockNo);
			if(data !=null) {
                return true;
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public int findRowNo(PageData pd) throws Exception {
		PageData p = (PageData) dao.findForObject("StockMgrInMapper.findRowNo", pd);
		return p == null ? 0 :p.getInteger("ROW_NO");
	}

	private Pair<String,String> findCustomerIdByStockInId(PageData pd) throws Exception{
		PageData p = (PageData) dao.findForObject("StockMgrInMapper.findCustomerIdByStockMgrInId", pd);
		return p == null ? null :Pair.of(p.getString("CUSTOMER_ID"),p.getString("INSTOCK_NO"));
	}
}

