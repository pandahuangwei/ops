package com.hw.service.property;

import java.util.List;

import javax.annotation.Resource;

import com.hw.util.Const;
import com.hw.util.StringUtil;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-10-24
*/
@Service("packruleService")
public class PackRuleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		setDefalut(pd);
		dao.save("PackRuleMapper.save", pd);
	}

	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		setDefalut(pd);
		dao.update("PackRuleMapper.edit", pd);
	}

	private void setDefalut(PageData pd) {
		pd.put("EA_IN",getDefault(pd.getString("EA_IN")));
		pd.put("INPACK_IN",getDefault(pd.getString("INPACK_IN")));
		pd.put("BOX_IN",getDefault(pd.getString("BOX_IN")));
		pd.put("PALLET_IN",getDefault(pd.getString("PALLET_IN")));
		pd.put("OTHER_IN",getDefault(pd.getString("OTHER_IN")));

		pd.put("EA_OUT",getDefault(pd.getString("EA_OUT")));
		pd.put("INPACK_OUT",getDefault(pd.getString("INPACK_OUT")));
		pd.put("BOX_OUT",getDefault(pd.getString("BOX_OUT")));
		pd.put("PALLET_OUT",getDefault(pd.getString("PALLET_OUT")));
		pd.put("OTHER_OUT",getDefault(pd.getString("OTHER_OUT")));

		setValue("OTHER_NUM",pd.getString("OTHER_IN"),pd);
		setValue("PALLET_NUM",pd.getString("PALLET_IN"),pd);
		setValue("BOX_NUM",pd.getString("BOX_IN"),pd);
		setValue("INPACK_NUM",pd.getString("INPACK_IN"),pd);

		setActive("OTHER_NUM","OTHER_IN","OTHER_OUT",pd);
		setActive("PALLET_NUM","PALLET_IN","PALLET_OUT",pd);
		setActive("BOX_NUM","BOX_IN","BOX_OUT",pd);
		setActive("INPACK_NUM","INPACK_IN","INPACK_OUT",pd);
	}

	private void setActive(String colum1,String columIn,String columOut,PageData pd) {
		int colum1Value = StringUtil.parseInt(pd.getString(colum1));
		if(colum1Value == 0) {
			pd.put(columIn,Const.ZERO_CH);
			pd.put(columOut,Const.ZERO_CH);
		}
	}

	private void setValue(String colum1 ,String flag,PageData pd) {
		if(flag.equals(Const.ZERO_CH)) {
			pd.put(colum1,Const.ZERO_CH);
		}
	}

	private String getDefault(String str) {
		return StringUtil.isEmpty(str)?Const.ZERO_CH:str;
	}

	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("PackRuleMapper.delete", pd);
	}
	

	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PackRuleMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PackRuleMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PackRuleMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PackRuleMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByCode(PageData pd)throws Exception{
		//return (PageData)dao.findForObject("LocatorMapper.findByCode", pd);
		List<PageData>  list = (List<PageData>)dao.findForList("PackRuleMapper.findByCode", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

}

