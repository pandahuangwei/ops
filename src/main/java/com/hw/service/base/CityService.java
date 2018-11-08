package com.hw.service.base;

import java.util.List;

import javax.annotation.Resource;

import com.hw.entity.base.City;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-10-20
*/
@Service("cityService")
public class CityService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("CityMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("CityMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("CityMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CityMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CityMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CityMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CityMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	*列表(全部)
	*/
	public List<City> findByProvinceId(PageData pd)throws Exception{
		return (List<City>)dao.findForList("CityMapper.findByProvinceId", pd);
	}

	public List<City> findAll()throws Exception{
		return (List<City>)dao.findForList("CityMapper.findAll", null);
	}

	public PageData findByCityCode(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("CityMapper.findByCityCode", pd);
		return (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public PageData findByCityCn(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("CityMapper.findByCityCn", pd);
		return (list!= null && !list.isEmpty())?list.get(0):null;
	}

}

