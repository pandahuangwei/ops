package com.hw.service.property;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-11-16
*/
@Service("stockturnService")
public class StockTurnService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("StockTurnMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("StockTurnMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("StockTurnMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StockTurnMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StockTurnMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StockTurnMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StockTurnMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByCode(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("StockTurnMapper.findByCode", pd);
		return (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public PageData findByCn(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("StockTurnMapper.findByCn", pd);
		return (list!= null && !list.isEmpty())?list.get(0):null;
	}
	
}

