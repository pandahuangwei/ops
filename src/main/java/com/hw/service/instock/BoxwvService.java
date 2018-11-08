package com.hw.service.instock;

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
* Date：2016-11-21
*/
@Service("boxwvService")
public class BoxwvService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public PageData findByBoxNo(PageData pd)throws Exception{
		List<PageData> list =(List<PageData>)dao.findForList("BoxwvMapper.findByBoxNo", pd);
		if (list == null || list.isEmpty()) {
			list =(List<PageData>)dao.findForList("BoxwvMapper.findByBigBoxNo", pd);
		}
		PageData rs = null;
		if(list != null && !list.isEmpty()) {
			rs = list.get(0);
			//BOX_ID, BOX_NO, LONG_UNIT, WIDE_UNIT, HIGH_UNIT, VOLUME_UNIT, TOTAL_SUTTLE, TOTAL_WEIGHT
			setDefault(rs,"LONG_UNIT");
			setDefault(rs,"WIDE_UNIT");
			setDefault(rs,"HIGH_UNIT");
			setDefault(rs,"VOLUME_UNIT");
			setDefault(rs,"TOTAL_WEIGHT");
		}
		return rs;
	}


	public void saveBox(PageData pd)throws Exception{

		List<PageData> list =(List<PageData>)dao.findForList("BoxwvMapper.findByBoxNo", pd);
		if (list != null && !list.isEmpty()) {
			for (PageData p : list) {
				pd.put("BOX_ID",p.getString("BOX_ID"));
				dao.update("BoxwvMapper.saveBox", pd);
			}
		} else {
			list =(List<PageData>)dao.findForList("BoxwvMapper.findByBigBoxNo", pd);
			if (list == null || list.isEmpty()) {
				return;
			}
			boolean wvFlg = Const.DEL_YES.equals(pd.getString("WV_FLG_1"))?true:false;
			if (wvFlg) {
				int totalWeight = pd.getInteger("TOTAL_WEIGHT");
				int weight = totalWeight/list.size();
				int sum = 0;
				for (int i =0,size=list.size();i<size; i++) {
					PageData p = list.get(i);
					if (i == size-1) {
						pd.put("TOTAL_WEIGHT",totalWeight-sum);
					}else {
						pd.put("TOTAL_WEIGHT",weight);
					}
					sum += weight;
					pd.put("BOX_ID",p.getString("BOX_ID"));
					dao.update("BoxwvMapper.saveBox", pd);
				}
			} else {
				for (PageData p : list) {
					pd.put("BOX_ID",p.getString("BOX_ID"));
					dao.update("BoxwvMapper.saveBox", pd);
				}
			}

		}
	}

	private void setDefault(PageData pd,String colum) {
		String str = pd.getString(colum);
		if(StringUtil.isEmpty(str)) {
			pd.put(colum, Const.ZERO);
		}
	}


	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("BoxwvMapper.save", pd);
	}



	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("BoxwvMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("BoxwvMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("BoxwvMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("BoxwvMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("BoxwvMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("BoxwvMapper.deleteAll", ArrayDATA_IDS);
	}

}

