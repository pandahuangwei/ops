package com.hw.controller.instock;

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
import com.hw.service.instock.BoxwvService;

/**
 * 称重量体积
 * Created：panda.HuangWei
 * Date：2016-11-21
 */
@Controller
@RequestMapping(value="/boxwv")
public class BoxwvController extends BaseController {

	String menuUrl = "boxwv/list.do"; //菜单地址(权限用)
	@Resource(name="boxwvService")
	private BoxwvService boxwvService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Boxwv");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BOXWV_ID", this.get32UUID());	//主键
		boxwvService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value="/saveBox")
	@ResponseBody
	public Object saveBox() {
		logBefore(logger, "新增saveBox Boxwv");

		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String, String> map = new HashMap<String, String>();

		try {
			String BIG_BOX_STATUS = pd.getString("BIG_BOX_STATUS");
			if(StringUtil.isEmpty(BIG_BOX_STATUS)) {
				pd.put("BIG_BOX_STATUS",get32UUID());
			}

			boxwvService.saveBox(pd);


			// BOX_ID, BOX_NO, LONG_UNIT, WIDE_UNIT, HIGH_UNIT, VOLUME_UNIT, TOTAL_SUTTLE, TOTAL_WEIGHT
			map.put("BIG_BOX_STATUS",pd.getString("BIG_BOX_STATUS"));
			map.put("BOX_ID","");
			map.put("LONG_UNIT_1",pd.getString("LONG_UNIT"));
			map.put("WIDE_UNIT_1",pd.getString("WIDE_UNIT"));
			map.put("HIGH_UNIT_1",pd.getString("HIGH_UNIT"));
			map.put("VOLUME_UNIT_1",pd.getString("VOLUME_UNIT"));
			map.put("TOTAL_WEIGHT_1",pd.getString("TOTAL_WEIGHT"));

			map.put("LONG_UNIT",Const.ZERO_CH);
			map.put("WIDE_UNIT",Const.ZERO_CH);
			map.put("HIGH_UNIT",Const.ZERO_CH);
			map.put("VOLUME_UNIT",Const.ZERO_CH);
			map.put("TOTAL_WEIGHT",Const.ZERO_CH);
			map.put("IN_CNT",String.valueOf(pd.getInteger("IN_CNT")+1));

			map.put("result", "success");
		} catch (Exception e) {
			map.put("result", "error");
			e.printStackTrace();
		}
		return  AppUtil.returnObject(new PageData(), map);
	}



	/**
	 * 判断入库单是否存在
	 */
	@RequestMapping(value = "/hadBox")
	@ResponseBody
	public Object hadBox() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			// BOX_ID, BOX_NO, LONG_UNIT, WIDE_UNIT, HIGH_UNIT, VOLUME_UNIT, TOTAL_SUTTLE, TOTAL_WEIGHT
			PageData rs = boxwvService.findByBoxNo(pd);
			if (rs != null) {
				map.put("BOX_ID",rs.getString("BOX_ID"));
				map.put("LONG_UNIT",rs.getString("LONG_UNIT"));
				map.put("WIDE_UNIT",rs.getString("WIDE_UNIT"));
				map.put("HIGH_UNIT",rs.getString("HIGH_UNIT"));
				map.put("VOLUME_UNIT",rs.getString("VOLUME_UNIT"));
				map.put("TOTAL_WEIGHT",rs.getString("TOTAL_WEIGHT"));
				errInfo = "success";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}


	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Boxwv");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			boxwvService.delete(pd);
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
		logBefore(logger, "修改Boxwv");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		boxwvService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Boxwv");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			pd.put("IN_CNT",0);
			String BIG_BOX_STATUS = pd.getString("BIG_BOX_STATUS");
			List<PageData>	varList = new ArrayList<>();
			if(StringUtil.isNotEmpty(BIG_BOX_STATUS)) {
				varList = boxwvService.list(page);	//列出Boxwv列表
			}
			
			mv.setViewName("instock/boxwv_list");
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
		logBefore(logger, "去新增Boxwv页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			mv.setViewName("instock/boxwv_edit");
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
		logBefore(logger, "去修改Boxwv页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = boxwvService.findById(pd);	//根据ID读取
			mv.setViewName("instock/boxwv_edit");
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
		logBefore(logger, "批量删除Boxwv");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				boxwvService.deleteAll(ArrayDATA_IDS);
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

	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Boxwv到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("箱号");	//1
			titles.add("长CM");	//2
			titles.add("宽");	//3
			titles.add("高");	//4
			titles.add("体积");	//5
			titles.add("毛重");	//6
			titles.add("按件");	//7
			dataMap.put("titles", titles);
			List<PageData> varOList = boxwvService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("BOX_NO"));	//1
				vpd.put("var2", varOList.get(i).getString("LONG_UNIT"));	//2
				vpd.put("var3", varOList.get(i).getString("WIDE_UNIT"));	//3
				vpd.put("var4", varOList.get(i).getString("HIGH_UNIT"));	//4
				vpd.put("var5", varOList.get(i).getString("VOLUME_UNIT"));	//5
				vpd.put("var6", varOList.get(i).getString("TOTAL_WEIGHT"));	//6
				vpd.put("var7", varOList.get(i).getString("WV_FLG"));	//7
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
