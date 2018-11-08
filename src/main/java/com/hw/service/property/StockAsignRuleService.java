package com.hw.service.property;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-10-24
*/
@Service("stockasignruleService")
public class StockAsignRuleService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("StockAsignRuleMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("StockAsignRuleMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("StockAsignRuleMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StockAsignRuleMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StockAsignRuleMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StockAsignRuleMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StockAsignRuleMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByCode(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("StockAsignRuleMapper.findByCode", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public PageData findByCn(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("StockAsignRuleMapper.findByCn", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

}

