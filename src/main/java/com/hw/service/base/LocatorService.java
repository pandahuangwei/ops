package com.hw.service.base;

import java.util.*;

import javax.annotation.Resource;

import com.hw.cache.SelectCache;
import com.hw.entity.base.Select;
import com.hw.util.*;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;

/**
* Created：panda.HuangWei
* Date：2016-10-22
*/
@Service("locatorService")
public class LocatorService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("LocatorMapper.save", pd);
	}

	/*
	* 新增
	*/
	public void saveBatch(List<PageData> list)throws Exception{
		dao.batchSave("LocatorMapper.saveBatch", list);
	}

	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("LocatorMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("LocatorMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("LocatorMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LocatorMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LocatorMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("LocatorMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByCode(PageData pd)throws Exception{
		//return (PageData)dao.findForObject("LocatorMapper.findByCode", pd);
		List<PageData>  list = (List<PageData>)dao.findForList("LocatorMapper.findByCode", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public PageData findStockHadLocator(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("LocatorMapper.findStockHadLocator", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public void auditList(List<PageData> list,List<PageData> auditRs,List<PageData> savepdList) {
		Map<String, Select> locatorMap = new HashMap<>();
		Set<String> hadSet = new HashSet<>();
		getLocatorMap(locatorMap,hadSet);
		//map(code,实体)
		Map<String, Select> storageMap = SelectCache.getInstance().getAllStorageMap();
		for(PageData p:list) {
			String LOCATOR_CODE =  p.getString("var0");
			String STORAGE_CODE =  p.getString("var1");
			if(StringUtil.isEmpty(LOCATOR_CODE)||StringUtil.isEmpty(STORAGE_CODE)) {
				continue;
			}
			LOCATOR_CODE = LOCATOR_CODE.trim();
			STORAGE_CODE = STORAGE_CODE.trim();
			PageData rs;
			boolean conFlag = false;
			if(hadSet.contains(LOCATOR_CODE)) {
				rs = new PageData();
				rs.put("LOCATOR_CODE",LOCATOR_CODE);
				rs.put("STORAGE_CODE",STORAGE_CODE);
				rs.put("MEMO","库位编码重复.");
				auditRs.add(rs);
				conFlag = true;
			}
			hadSet.add(LOCATOR_CODE);
			if(!storageMap.containsKey(STORAGE_CODE)) {
				rs = new PageData();
				rs.put("LOCATOR_CODE",LOCATOR_CODE);
				rs.put("STORAGE_CODE",STORAGE_CODE);
				rs.put("MEMO","库区编码不存在.");
				auditRs.add(rs);
				conFlag = true;
			}

			if(conFlag) {
				continue;
			}

			PageData savePd = new PageData();
			//库位编码	库区编码	排	层	列
			savePd.put("LOCATOR_ID", UuidUtil.get32UUID());
			savePd.put("LOCATOR_CODE",LOCATOR_CODE);
			savePd.put("STORAGE_CODE",storageMap.get(STORAGE_CODE).getId());
			savePd.put("ROW_UNIT",p.getString("var2"));
			savePd.put("FLOOR_UNIT",p.getString("var3"));
			savePd.put("QUEUE_UNIT",p.getString("var4"));
			//库位属性	库位使用	库位类型	库位处理	周转周期
			savePd.put("LOCATOR_STATE",p.getString("var5"));
			savePd.put("LOCATOR_USE",p.getString("var6"));
			savePd.put("LOCATOR_TYPE",p.getString("var7"));
			savePd.put("LOCATOR_UNIT",p.getString("var8"));
			savePd.put("TURNOVER_CYCLE",p.getString("var9"));
			// 库位优先级	体积限制CBM	重量限制	数量限制	托盘限制
			String level = p.getString("var10");
			if(StringUtil.isEmpty(level)) {
				level = "1";
			}
			//97 98 99 103 104
			if (level.equals("1")) {
				level = "97";
			} else if (level.equals("2")){
				level = "98";
			}if (level.equals("3")) {
				level = "99";
			}if (level.equals("4")) {
				level = "103";
			} else {
				level = "104";
			}
			savePd.put("LOCATOR_PRIORITY_LEVEL",level);
			savePd.put("VOLUME_LIMIT",p.getString("var11"));
			savePd.put("WEIGHT_LIMIT",p.getString("var12"));
			savePd.put("QUANTITY_LIMIT",p.getString("var13"));
			savePd.put("PALLET_LIMIT",p.getString("var14"));
			// 产品混合	批号混合	长CM	宽CM	高CM	层数
			savePd.put("PRODUCT_MIX",p.getString("var15"));
			savePd.put("BATCH_MIX",p.getString("var16"));
			savePd.put("LONG_UNIT",p.getString("var17"));
			savePd.put("WIDE_UNIT",p.getString("var18"));
			savePd.put("HIGH_UNIT",p.getString("var19"));
			savePd.put("PLIES_UNIT",p.getString("var20"));

			savePd.put("MEMO", "用户导入");
			savePd.put("CREATE_EMP", SessionUtil.getCurUserName());	//创建人
			savePd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
			savePd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
			savePd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
			savePd.put("DEL_FLG", Const.DEL_NO);	//修改时间

			savepdList.add(savePd);
		}
	}

	private void getLocatorMap(Map<String, Select> map,Set<String> locatorCodeSet) {
		Map<String, Select> locatorMap = SelectCache.getInstance().getAllLocatorMap();
		for(Map.Entry<String, Select> entry : locatorMap.entrySet()) {
			locatorCodeSet.add(entry.getKey());
		}
		map.putAll(locatorMap);
	}
}

