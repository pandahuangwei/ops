package com.hw.service.base;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;

/**
* Created：panda.HuangWei
* Date：2016-10-23
*/
@Service("productService")
public class ProductService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ProductMapper.save", pd);

		BaseInfoCache.getInstance().initReload();
		SelectCache.getInstance().initReload();
	}

	public void save(List<PageData> list)throws Exception{
		for(PageData p: list) {
			dao.save("ProductMapper.save", p);
		}
		BaseInfoCache.getInstance().initReload();
		SelectCache.getInstance().initReload();
	}

	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ProductMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ProductMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProductMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProductMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProductMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProductMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByCode(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("ProductMapper.findByCode", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public PageData findByCn(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("ProductMapper.findByCn", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public void auditList(List<PageData> list,List<PageData> auditRs,List<PageData> savepdList) {
       //
		Set<String> prodCodeSet = SelectCache.getInstance().getProdCodeSet(null);
		List<PageData> allCustomerPd = SelectCache.getInstance().getAllCustomerPd();
		List<PageData> allStockPd = SelectCache.getInstance().getAllStockPd();
		List<PageData> packList = BaseInfoCache.getInstance().getPackList();
		List<PageData> pickList = BaseInfoCache.getInstance().getPickList();
		List<PageData> stockasignruleList = BaseInfoCache.getInstance().getStockasignruleList();
		List<PageData> storageasignruleList = BaseInfoCache.getInstance().getStorageasignruleList();
		List<PageData> stockturnList = BaseInfoCache.getInstance().getStockturnList();

		Set<String> hadSet = new HashSet<>();
		hadSet.addAll(prodCodeSet);
		for(PageData p:list) {
			String CUSTOMER_CODE =  p.getString("var0");
			String STOCK_CODE =  p.getString("var1");
			String PROD_CODE =  p.getString("var2");

			if(StringUtil.isEmpty(CUSTOMER_CODE)||StringUtil.isEmpty(PROD_CODE)) {
				continue;
			}

			CUSTOMER_CODE = CUSTOMER_CODE.trim();
			PROD_CODE = PROD_CODE.trim();

			PageData rs;
			boolean conFlag = false;
			if(hadSet.contains(PROD_CODE)) {
				rs = new PageData();
				rs.put("CUSTOMER_CODE",CUSTOMER_CODE);
				rs.put("PROD_CODE",PROD_CODE);
				rs.put("MEMO","产品编码重复.");
				auditRs.add(rs);
				conFlag = true;
			}
			hadSet.add(PROD_CODE);

			String cutomerId = getCutomerId(allCustomerPd, CUSTOMER_CODE);
			if(StringUtil.isEmpty(cutomerId)) {
				rs = new PageData();
				rs.put("CUSTOMER_CODE",CUSTOMER_CODE);
				rs.put("PROD_CODE",PROD_CODE);
				rs.put("MEMO","客户编码不存在.");
				auditRs.add(rs);
				conFlag = true;
			}

			if(conFlag) {
				continue;
			}
			//CUSTOMER_ID	STOCK_ID	PRODUCT_CODE	PRODUCT_CN	PRODUCT_EN
			// RULE_PACK	RULE_PICK	RULE_STORAGE	RULE_STORAGE_ASIGN	RULE_STOCK_ASIGN
			// PERIOD_TYPE	STOCKTURN_ID	LONG_UNIT	WIDE_UNIT	HIGH_UNIT	UNIT_PRICE
			// TOTAL_WEIGHT	TOTAL_SUTTLE	VOLUME_UN	MEMO

			PageData savePd = new PageData();
			//库位编码	库区编码	排	层	列
			savePd.put("PRODUCT_ID", UuidUtil.get32UUID());
			savePd.put("CUSTOMER_ID",cutomerId);
			savePd.put("STORAGE_CODE",getStockId(allStockPd,p.getString("STOCK_CODE")));
			savePd.put("PRODUCT_CODE",PROD_CODE);
			savePd.put("PRODUCT_CN",p.getString("var3"));
			savePd.put("PRODUCT_EN",p.getString("var4"));

			savePd.put("RULE_PACK",getId(packList,p.getString("var5"),"PACKRULE_ID"));
			savePd.put("RULE_PICK",getId(pickList,p.getString("var6"),"PICKRULE_ID"));
			savePd.put("RULE_STORAGE",p.getStringNot4Zero("var7"));
			savePd.put("RULE_STORAGE_ASIGN",getId(storageasignruleList,p.getString("var8"),"STORAGEASIGNRULE_ID"));
			savePd.put("RULE_STOCK_ASIGN",getId(stockasignruleList,p.getString("var9"),"STOCKASIGNRULE_ID"));

			String PERIOD_TYPE = p.getString("var10");
			PERIOD_TYPE = StringUtil.isEmpty(PERIOD_TYPE)?"49":PERIOD_TYPE;

			savePd.put("PERIOD_TYPE",PERIOD_TYPE);
			savePd.put("STOCKTURN_ID",getId(stockturnList,p.getString("var11"),"STOCKTURN_ID"));
			savePd.put("LONG_UNIT",p.getStringNot4Zero("var12"));
			savePd.put("HIGH_UNIT",p.getStringNot4Zero("var13"));
			savePd.put("UNIT_PRICE",p.getStringNot4Zero("var14"));

			savePd.put("TOTAL_WEIGHT",p.getStringNot4Zero("var15"));
			savePd.put("TOTAL_SUTTLE",p.getStringNot4Zero("var16"));
			savePd.put("VOLUME_UN",p.getStringNot4Zero("var17"));
			savePd.put("MEMO",p.getString("var18"));

			savePd.put("CREATE_EMP", SessionUtil.getCurUserName());	//创建人
			savePd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
			savePd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
			savePd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
			savePd.put("DEL_FLG", Const.DEL_NO);	//修改时间

			savePd.put("DANGER_FLG", Const.ZERO);
			savePd.put("MANAGER_FLG",Const.ZERO);
			savePd.put("COMBI_FLG",Const.ZERO);
			savePd.put("ACTIVE_FLG",Const.ZERO);
			savePd.put("USE_VALIDITY",Const.ZERO);
			savePd.put("INBOUND_DAY",Const.ZERO);
			savePd.put("OUTBOUND_DAY",Const.ZERO);

			savePd.put("TOTAL_WEIGHT_UNIT",Const.ZERO);
			savePd.put("TOTAL_SUTTLE_UNIT",Const.ZERO);
			savePd.put("VOLUME_UNIT",Const.ZERO);
			//冻结拒收代码，对应tb_select id
			savePd.put("FREEZE_REJECT_CODE","42");
			savepdList.add(savePd);
		}

	}



	private String getStockId(List<PageData> allStockPd,String stockCode) {
		if(StringUtil.isEmpty(stockCode)) {
			return Const.EMPTY_CH;
		}
		for(PageData p : allStockPd ) {
			String id = p.getString("STOCK_ID");
			if(stockCode.trim().equals(id)) {
				return id;
			}
		}
		return Const.EMPTY_CH;
	}

  private String getCutomerId(List<PageData> allCustomerPd,String CustomerCode) {
	  for(PageData p : allCustomerPd ) {
		  String code = p.getString("CUSTOMER_CODE");
		  if(CustomerCode.equals(code)) {
			return p.getString("CUSTOMER_ID");
		  }
	  }
	  return Const.EMPTY_CH;
  }

	private String gePackRule(List<PageData> packList,String str) {
		if(StringUtil.isEmpty(str)) {
			return Const.EMPTY_CH;
		}
		for(PageData p : packList ) {
			String id = p.getString("PACKRULE_ID");
			if(id.equals(str.trim())) {
				return id;
			}
		}
		return Const.EMPTY_CH;
	}

	private String getId(List<PageData> packList,String str,String cloun) {
		if(StringUtil.isEmpty(str)) {
			return Const.EMPTY_CH;
		}

		for(PageData p : packList ) {
			String id = p.getString(cloun);
			if(StringUtil.isNoBlank(id) && id.equals(StringUtil.trimLRSpace(str))) {
				return id;
			}
		}
		return Const.EMPTY_CH;
	}

}

