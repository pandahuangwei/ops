package com.hw.service.system;

import java.util.List;

import javax.annotation.Resource;

import com.hw.util.StringUtil;
import com.hw.util.TypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.PageData;

/**
* Created：panda.HuangWei
* Date：2016-11-04
*/
@Service("datamgrService")
public class DataMgrService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		pd.put("TOTAL_CNT",0);
		pd.put("VALID_CNT",0);
		pd.put("NOVALID_CNT",0);
		dao.save("DataMgrMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("DataMgrMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		pd.put("TOTAL_CNT",0);
		pd.put("VALID_CNT",0);
		pd.put("NOVALID_CNT",0);
		dao.update("DataMgrMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DataMgrMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DataMgrMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DataMgrMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("DataMgrMapper.deleteAll", ArrayDATA_IDS);
	}

	public void cleanAll(String[] ArrayDATA_IDS){
		try {
			for(String id :ArrayDATA_IDS) {
                PageData pd = new PageData();
                pd.put("DATAMGR_ID",id);
                PageData result = findById(pd);
                clean(result);

				pd.put("TOTAL_CNT",0);
				pd.put("VALID_CNT",0);
				pd.put("NOVALID_CNT",0);
				//更新记录数
				editCnt(pd);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clean(PageData pd) {
		try {
			String tables = pd.getString("TABLE_CN");
			if (StringUtil.isEmpty(tables)) {
                return;
            }
			String[] table = StringUtils.split(tables, ",");
			for (String t : table) {
                PageData e = new PageData();
                e.put("TABLE_CN", t);
                dao.delete("DataMgrMapper.deleteTable", pd);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<PageData> calcNow(Page page)throws Exception{
		updateCnt(null);
		return (List<PageData>)dao.findForList("DataMgrMapper.datalistPage", page);
	}

	public void updateCnt(String id) {
		PageData pd = new PageData();
		if(StringUtil.isNotEmpty(id)) {
			pd.put("DATAMGR_ID",id);
		}
		try {
			List<PageData> list = (List<PageData>)dao.findForList("DataMgrMapper.listAll", pd);
			if (list == null || list.isEmpty()) {
				return;
			}

			for (PageData e : list) {
				String tables = e.getString("TABLE_CN");
				if(StringUtil.isEmpty(tables)) {
					continue;
				}
				String[] array = StringUtils.split(tables,",");

				StringBuffer sbt = new StringBuffer();
				StringBuffer sbv = new StringBuffer();
				StringBuffer sbn = new StringBuffer();
				for (int i = 0,len = array.length;i<len;i++) {
					if(i!= 0) {
						sbt.append(',');
						sbv.append(',');
						sbn.append(',');
					}
					Pair<Integer, Integer> pair = getCnt(array[i]);
					sbt.append(pair.getLeft()+pair.getRight());
					sbv.append(pair.getLeft());
					sbn.append(pair.getRight());
				}

				e.put("TOTAL_CNT",sbt.toString());
				e.put("VALID_CNT",sbv.toString());
				e.put("NOVALID_CNT",sbn.toString());
				//更新记录数
				editCnt(e);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}



	}

	//取得有效记录数与无效记录数
	private Pair<Integer,Integer> getCnt(String tableName) {
		PageData pd = new PageData();
		pd.put("TABLE_CN",tableName);
		pd.put("TABLE_CN2",tableName);
		try {
			PageData rs = (PageData)dao.findForObject("DataMgrMapper.findCnt", pd);
			Integer validCnt = rs.getInteger("VALID_CNT");
			Integer noValidCnt = rs.getInteger("NOVALID_CNT");
			return Pair.of(validCnt,noValidCnt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Pair.of(0,0);
	}

	public void editCnt(PageData pd)throws Exception{
		dao.update("DataMgrMapper.editCnt", pd);
	}
	
}

