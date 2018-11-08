package com.hw.service.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-10-22
*/
@Service("storageService")
public class StorageService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("StorageMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("StorageMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("StorageMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StorageMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StorageMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StorageMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("StorageMapper.deleteAll", ArrayDATA_IDS);
	}

	public List<PageData> findAll(){
		List<PageData> lst = new ArrayList<>();
		try {
			lst = (List<PageData>)dao.findForList("StorageMapper.findAll", null);
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return lst;
	}

	public PageData findByCode(PageData pd)throws Exception{
		//return (PageData)dao.findForObject("StorageMapper.findByCode", pd);
		List<PageData>  list = (List<PageData>)dao.findForList("StorageMapper.findByCode", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public PageData findByCn(PageData pd)throws Exception{
		//return (PageData)dao.findForObject("StorageMapper.findByCn", pd);
		List<PageData>  list = (List<PageData>)dao.findForList("StorageMapper.findByCn", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

}

