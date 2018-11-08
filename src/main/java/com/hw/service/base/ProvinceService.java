package com.hw.service.base;

import java.util.List;

import javax.annotation.Resource;

import com.hw.entity.base.Province;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-10-20
*/
@Service("provinceService")
public class ProvinceService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("ProvinceMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("ProvinceMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("ProvinceMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ProvinceMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ProvinceMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ProvinceMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ProvinceMapper.deleteAll", ArrayDATA_IDS);
	}

	/*
	* 通过id获取数据
	*/
	public PageData findByProvinceCode(PageData pd)throws Exception{
		//return (PageData)dao.findForObject("ProvinceMapper.findByProvinceCode", pd);
		List<PageData>  list = (List<PageData>)dao.findForList("ProvinceMapper.findByProvinceCode", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	/*
	* 通过id获取数据
	*/
	public PageData findByProvinceCn(PageData pd)throws Exception{
		//return (PageData)dao.findForObject("ProvinceMapper.findByProvinceCn", pd);
		List<PageData>  list = (List<PageData>)dao.findForList("ProvinceMapper.findByProvinceCn", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	/*
	*列表(全部)
	*/
	public List<Province> findByCountryId(PageData pd)throws Exception{
		return (List<Province>)dao.findForList("ProvinceMapper.findByCountryId", pd);
	}
}

