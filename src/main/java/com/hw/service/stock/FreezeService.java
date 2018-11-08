package com.hw.service.stock;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;
import com.hw.util.SessionUtil;
import com.hw.util.Tools;
import com.hw.util.WmsEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* Created：panda.HuangWei
* Date：2016-12-14
*/
@Service("freezeService")
public class FreezeService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/*
        *列表
        */
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("FreezeMapper.datalistPage", page);
	}

	//冻结，前台参数(STOCKDTLMGRIN_IDS)，入库单明细ID列表
	public void saveFreeze(PageData pd) throws Exception {
		List<String> list = pd.getList("STOCKDTLMGRIN_IDS");
		if(list == null || list.isEmpty()) {
			return;
		}
		pd.put("IDS",list);
		Page page = new Page();
		page.setPd(pd);

		List<PageData> forFreeze = findOutListForFreeze(page);

		List<String> allFreeze = new ArrayList<>();
		List<String> partFreeze = new ArrayList<>();
		for(PageData p : forFreeze) {
			int EA_EA = p.getInteger("EA_NUM");
			int ASSIGNED_EA = p.getInteger("ASSIGNED_STOCK_NUM");

			if (EA_EA == ASSIGNED_EA || ASSIGNED_EA == 0) {
				allFreeze.add(p.getString("BOX_ID"));
			} else {
				partFreeze.add(p.getString("BOX_ID"));
			}
		}

		pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("FREEZE_TM", Tools.date2Str(new Date()));	//修改时间


		if(!allFreeze.isEmpty()) {
			pd.put("FREEZE_STATE", WmsEnum.FreezeState.ALL.getItemKey());	//冻结状态
			pd.put("IDS",allFreeze);
			page = new Page();
			page.setPd(pd);
			freezeState(page);
		}

		if(!partFreeze.isEmpty()) {
			pd.put("FREEZE_STATE", WmsEnum.FreezeState.PART.getItemKey());	//冻结状态
			pd.put("IDS",partFreeze);
			page = new Page();
			page.setPd(pd);
			freezeState(page);
		}
	}

	//释放库存，前台参数(STOCKDTLMGRIN_IDS)，入库单明细ID列表
	public void saveFree(PageData pd) throws Exception {
		List<String> list = pd.getList("STOCKDTLMGRIN_IDS");
		if(list == null || list.isEmpty()) {
			return;
		}

		pd.put("IDS",list);
		pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("FREEZE_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("FREEZE_STATE", WmsEnum.FreezeState.NONE.getItemKey());	//冻结状态

		Page page = new Page();
		page.setPd(pd);
		freezeState(page);
	}


	private List<PageData> findOutListForFreeze(Page page) throws Exception {
		return (List<PageData>) dao.findForList("FreezeMapper.findOutListForFreeze", page);
	}

	private void freezeState(Page page) throws Exception {
		dao.update("FreezeMapper.editBoxFreezeState", page);
	}



	
}

