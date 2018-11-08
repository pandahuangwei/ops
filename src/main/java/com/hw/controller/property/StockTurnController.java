package com.hw.controller.property;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hw.cache.SelectCache;
import com.hw.entity.base.Select;
import com.hw.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.service.property.StockTurnService;

/**
 * Created：panda.HuangWei
 * Date：2016-11-16
 */
@Controller
@RequestMapping(value="/stockturn")
public class StockTurnController extends BaseController {
	
	String menuUrl = "stockturn/list.do"; //菜单地址(权限用)
	@Resource(name="stockturnService")
	private StockTurnService stockturnService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增StockTurn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STOCKTURN_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除标志
		trim(pd);
		stockturnService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	private void setSelecet(ModelAndView mv) {
		List<Select> batchProList = SelectCache.getInstance().getStockTurnBatchPro();
		mv.addObject("batchProList",batchProList);

		List<Select> list =SelectCache.getInstance().getSortBy();
		mv.addObject("sortByList",list);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除StockTurn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			stockturnService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改StockTurn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

		stockturnService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表StockTurn");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			setSelecet(mv);
			page.setPd(pd);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
				varList = stockturnService.list(page);    //列出StockTurn列表
			}
			mv.setViewName("property/stockturn_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增StockTurn页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelecet(mv);
			mv.setViewName("property/stockturn_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	
	
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改StockTurn页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelecet(mv);
			pd = stockturnService.findById(pd);	//根据ID读取
			mv.setViewName("property/stockturn_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	

	private void trim(PageData pd) {
		if(pd == null) {
			return;
		}
		String STOCKTURN_CODE = pd.getString("STOCKTURN_CODE");
		if(StringUtil.isNotEmpty(STOCKTURN_CODE)) {
			pd.put("STOCKTURN_CODE",STOCKTURN_CODE.trim());
		}

		String STOCKTURN_CN = pd.getString("STOCKTURN_CN");
		if(StringUtil.isNotEmpty(STOCKTURN_CN)) {
			pd.put("STOCKTURN_CN",STOCKTURN_CN.trim());
		}

	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除StockTurn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				stockturnService.deleteAll(ArrayDATA_IDS);
				pd.put("msg", "ok");
			}else{
				pd.put("msg", "no");
			}
			pdList.add(pd);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}

	/**
	 * 判断编码是否存在
	 */
	@RequestMapping(value = "/hasCode")
	@ResponseBody
	public Object hasCode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			trim(pd);
			if (stockturnService.findByCode(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}


	/**
	 * 判断编码是否存在
	 */
	@RequestMapping(value = "/hasCn")
	@ResponseBody
	public Object hasCn() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			trim(pd);
			if (stockturnService.findByCn(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出StockTurn到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("库存周转规则代码");	//1
			titles.add("库存周转规则描述");	//2
			titles.add("时间差(批次属性01-03有效)");	//3
			titles.add("客户");	//4
			titles.add("优先级");	//5
			titles.add("批次属性");	//6
			titles.add("排序");	//7
			titles.add("创建人");	//8
			titles.add("创建时间");	//9
			titles.add("修改人");	//10
			titles.add("修改时间");	//11
			titles.add("删除标志");	//12
			dataMap.put("titles", titles);
			List<PageData> varOList = stockturnService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("STOCKTURN_CODE"));	//1
				vpd.put("var2", varOList.get(i).getString("STOCKTURN_CN"));	//2
				vpd.put("var3", varOList.get(i).getString("TIME_DISTANCE"));	//3
				vpd.put("var4", varOList.get(i).getString("CUSTOMER_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("PRIORITY_LEVEL"));	//5
				vpd.put("var6", varOList.get(i).getString("BATCH_PROPERTY"));	//6
				vpd.put("var7", varOList.get(i).getString("SORT_BY"));	//7
				vpd.put("var8", varOList.get(i).getString("CREATE_EMP"));	//8
				vpd.put("var9", varOList.get(i).getString("CREATE_TM"));	//9
				vpd.put("var10", varOList.get(i).getString("MODIFY_EMP"));	//10
				vpd.put("var11", varOList.get(i).getString("MODIFY_TM"));	//11
				vpd.put("var12", varOList.get(i).getString("DEL_FLG"));	//12
				varList.add(vpd);
			}
			dataMap.put("varList", varList);
			ObjectExcelView erv = new ObjectExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/* ===============================权限================================== */
	public Map<String, String> getHC(){
		Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
		Session session = currentUser.getSession();
		return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
	}
	/* ===============================权限================================== */
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
