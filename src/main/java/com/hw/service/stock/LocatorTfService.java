package com.hw.service.stock;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;
import com.hw.util.SessionUtil;
import com.hw.util.Tools;
import com.hw.util.WmsEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
* Created：panda.HuangWei
* Date：2016-12-14
*/
@Service("locatorTfService")
public class LocatorTfService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
            *列表
            */
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("LocatorTfMapper.datalistPage", page);
	}


	public void saveLocatorTf(PageData pd) throws Exception {
		List<String> list = pd.getList("STOCKDTLMGRIN_IDS");
		if(list == null || list.isEmpty()) {
			return;
		}

		pd.put("IDS",list);
		pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("TRANSFER_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("FREEZE_STATE", WmsEnum.FreezeState.ALL.getItemKey());	//冻结状态
		pd.put("PUT_STORAGE", pd.getString("STORAGE_ID"));	//修改人
		pd.put("PUT_LOCATOR", pd.getString("LOCATOR_ID"));	//修改人
		Page page = new Page();
		page.setPd(pd);
		modifyLocator(page);
	}

	private void modifyLocator(Page page) throws Exception  {
		dao.update("LocatorTfMapper.editBoxLocator",page);
		dao.update("LocatorTfMapper.editInLocator",page);

	}

}

