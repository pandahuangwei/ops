package com.hw.service.base;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.hw.entity.base.Country;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-10-20
*/
@Service("countryService")
public class CountryService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("CountryMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("CountryMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("CountryMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CountryMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("CountryMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CountryMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("CountryMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByCtyCode(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("CountryMapper.findByCtyCode", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
				//(PageData)dao.findForObject("CountryMapper.findByCtyCode", pd);
	}

	public PageData findByCtyCn(PageData pd)throws Exception{
		//return (PageData)dao.findForObject("CountryMapper.findByCtyCn", pd);
		List<PageData>  list = (List<PageData>)dao.findForList("CountryMapper.findByCtyCn", pd);
		return  (list!= null && !list.isEmpty())?list.get(0):null;
	}

	/**
	 * 获取所有国家
	 * @return 国家列表
	 * @throws Exception e
     */
	public List<Country> findAll(){
		try {
			return (List<Country>)dao.findForList("CountryMapper.findAll", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

}

