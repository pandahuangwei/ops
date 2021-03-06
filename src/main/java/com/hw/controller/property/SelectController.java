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
import com.hw.service.base.SelectService;

/**
 * Created：panda.HuangWei
 * Date：2016-11-03
 */
@Controller
@RequestMapping(value="/select")
public class SelectController extends BaseController {
	
	String menuUrl = "select/list.do"; //菜单地址(权限用)
	@Resource(name="selectService")
	private SelectService selectService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Select");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("SELECT_ID", selectService.getNextId());	//主键
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", 0);	//删除标
		if(StringUtil.isEmpty(pd.getString("C_SORT"))) {
			pd.put("C_SORT", 2);
		}

		selectService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Select");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			selectService.delete(pd);
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
		logBefore(logger, "修改Select");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		selectService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Select");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(isSearch(searchFlag)) {
				varList = selectService.list(page);	//列出Select列表
			}
			mv.setViewName("property/select_list");
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
		logBefore(logger, "去新增Select页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("property/select_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAddDtl")
	public ModelAndView goAddDtl(){
		logBefore(logger, "去新增Select页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("property/select_edit_dtl");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

	@RequestMapping(value="/goEditDtl")
	public ModelAndView goEditDtl(){
		logBefore(logger, "去修改Select页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = selectService.findById(pd);	//根据ID读取
			mv.setViewName("property/select_edit_dtl");
			mv.addObject("msg", "edit");
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
		logBefore(logger, "去修改Select页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			Page p = new Page();
			List<PageData> datas = selectService.findByGroupKey(pd);//根据ID读取
			p.setPd(pd);
			if (datas != null && !datas.isEmpty()) {
				String GROUP_NAME = datas.get(0).getString("GROUP_NAME");
				pd.put("GROUP_NAME",GROUP_NAME);
			}

			List<PageData> list = selectService.filterActive(datas);
			mv.setViewName("property/select_edit");
			mv.addObject("varList", list);
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit2")
	public ModelAndView goEdit2(){
		logBefore(logger, "去修改Select页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = selectService.findById(pd);	//根据ID读取
			mv.setViewName("property/select_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Select");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				selectService.deleteAll(ArrayDATA_IDS);
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


	@RequestMapping(value = "/getDtl")
	@ResponseBody
	public Object getDtl() {
		Map<String,List<PageData>> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			List<PageData> dtl = selectService.findByActiveGroupKey(pd);

			if (dtl == null || dtl.size() ==0) {
				return AppUtil.returnObject(pd, map);
			}

			map.put("varList",dtl);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
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
			if (selectService.findByCode(pd) != null) {
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
			if (selectService.findByCn(pd) != null) {
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
		logBefore(logger, "导出Select到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("分组");	//1
			titles.add("参数分组");	//2
			titles.add("参数编码");	//3
			titles.add("参数名称");	//4
			titles.add("描述");	//5
			titles.add("修改人");	//6
			titles.add("修改时间");	//7
			titles.add("删除标");	//8
			titles.add("排序");	//9
			dataMap.put("titles", titles);
			List<PageData> varOList = selectService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("GROUP_KEY"));	//1
				vpd.put("var2", varOList.get(i).getString("GROUP_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("C_CODE"));	//3
				vpd.put("var4", varOList.get(i).getString("C_VALUE"));	//4
				vpd.put("var5", varOList.get(i).getString("C_DESC"));	//5
				vpd.put("var6", varOList.get(i).getString("MODIFY_EMP"));	//6
				vpd.put("var7", varOList.get(i).getString("MODIFY_TM"));	//7
				vpd.put("var8", varOList.get(i).getString("DEL_FLG"));	//8
				vpd.put("var9", varOList.get(i).get("C_SORT").toString());	//9
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
