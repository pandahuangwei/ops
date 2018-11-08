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
@Service("prodRightService")
public class ProdRightService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;


	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProdRightMapper.datalistPage", page);
	}

	//冻结，前台参数(STOCKDTLMGRIN_IDS)，入库单明细ID列表
	public void saveLocatorTf(PageData pd) throws Exception {
		List<String> list = pd.getList("STOCKDTLMGRIN_IDS");
		if(list == null || list.isEmpty()) {
			return;
		}

		pd.put("IDS",list);
		pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("PRODRIGHT_TM", Tools.date2Str(new Date()));	//修改时间

		Page page = new Page();
		page.setPd(pd);
		modifyCustomer(page);
	}
	private void modifyCustomer(Page page) throws Exception  {
		dao.update("ProdRightMapper.editBoxCustomer",page);
	}
	
}

