package com.hw.service.property;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.hw.util.Const;
import com.hw.util.StringUtil;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-11-07
*/
@Service("pnmapService")
public class PnMapService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("PnMapMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("PnMapMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("PnMapMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PnMapMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PnMapMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PnMapMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PnMapMapper.deleteAll", ArrayDATA_IDS);
	}

	public PageData findByPn(PageData pd)throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("PnMapMapper.findByPn", pd);
		return (list!= null && !list.isEmpty())?list.get(0):null;
	}

	public Set<String> findByAll()throws Exception{
		List<PageData>  list = (List<PageData>)dao.findForList("PnMapMapper.findAll", null);
		Set<String> set = new HashSet<>();
		if(list== null || list.isEmpty()) {
			return set;
		}

		for(PageData e : list) {
			String SCAN_PN = e.getString("SCAN_PN");
			String ACTUAL_PN = e.getString("ACTUAL_PN");
			if (StringUtil.isEmpty(SCAN_PN) || StringUtil.isEmpty(ACTUAL_PN)) {
				continue;
			}
			//set.add(SCAN_PN+ Const.SPLIT_LINE+ACTUAL_PN);
			set.add(SCAN_PN);
		}

		return set;
	}
	
}

