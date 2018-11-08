package com.hw.controller.base;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hw.entity.base.City;
import com.hw.entity.base.Country;
import com.hw.entity.base.Province;
import com.hw.service.base.*;
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

/**
 * Created：panda.HuangWei
 * Date：2016-10-22
 */
@Controller
@RequestMapping(value="/stock")
public class StockController extends BaseController {
	
	String menuUrl = "stock/list.do"; //菜单地址(权限用)
	@Resource(name="stockService")
	private StockService stockService;

	@Resource(name="selectService")
	private SelectService selectService;

	@Resource(name="cityService")
	private CityService cityService;

	@Resource(name="provinceService")
	private ProvinceService provinceService;

	@Resource(name="countryService")
	private CountryService countryService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Stock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STOCK_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP",  getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP",  getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除
		stockService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Stock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			stockService.delete(pd);
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
		logBefore(logger, "修改Stock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String useFlg = pd.getString("USE_FLG");
		if(StringUtil.isEmpty(useFlg)) {
			pd.put("USE_FLG",0);
		}

		stockService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Stock");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			setSelect(mv);
			pd = this.getPageData();

			String searchField = pd.getString("searchField");
			if(StringUtil.isEmpty(searchField)) {
				pd.put("searchField",searchField);
			}

			page.setPd(pd);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
				varList = stockService.list(page);	//列出Stock列表
			}

			mv.setViewName("base/stock_list");
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
		logBefore(logger, "去新增Stock页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//setSelect(mv);
			List<Country> countryList = countryService.findAll();
			mv.addObject("countryList", countryList);

			/*List<Province>  provinceList = new ArrayList<>();
			if (countryList !=null && countryList.size() >=1) {
				pd.put("COUNTRYID",countryList.get(0).getCOUNTRY_ID());
				provinceList = provinceService.findByCountryId(pd);
				mv.addObject("provinceList", provinceList);
			}

			if (provinceList != null && provinceList.size() >= 1) {
				pd.put("PROVINCEID",provinceList.get(0).getPROVINCE_ID());
				List<City> cityList = cityService.findByProvinceId(pd);
				mv.addObject("cityList", cityList);
			}*/

			mv.setViewName("base/stock_edit");
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
		logBefore(logger, "去修改Stock页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//setSelect(mv);
			List<Country> countryList = countryService.findAll();
			mv.addObject("countryList", countryList);

			pd = stockService.findById(pd);	//根据ID读取

			if (pd != null && pd.size() >= 1) {
				pd.put("PROVINCEID",pd.getString("PROVINCE_ID"));
				List<City> cityList = cityService.findByProvinceId(pd);
				mv.addObject("cityList", cityList);
			}

			if (pd !=null) {
				PageData pd2 = provinceService.findById(pd);
				if(pd2 != null && pd2.size()>=1) {
					pd.put("COUNTRYID",pd2.getString("COUNTRY_ID"));
					pd.put("COUNTRY_ID",pd2.getString("COUNTRY_ID"));
					List<Province>  provinceList = provinceService.findByCountryId(pd);
					mv.addObject("provinceList", provinceList);
				}
			}

			mv.setViewName("base/stock_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	

	private void setSelect(ModelAndView mv) {
		mv.addObject("cityList", selectService.findAllCity());
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Stock");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				stockService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Stock到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("仓库编码");	//1
			titles.add("仓库名称");	//2
			titles.add("仓库介绍");	//3
			titles.add("城市名称");	//4
			titles.add("仓库地址");	//5
			titles.add("备注");	//6
			titles.add("负责人");	//7
			titles.add("负责人电话");	//8
			titles.add("负责人传真");	//9
			titles.add("负责人邮箱");	//10
			titles.add("启用");	//11
			titles.add("创建人");	//12
			titles.add("创建时间");	//13
			titles.add("修改人");	//14
			titles.add("修改时间");	//15
			titles.add("删除");	//16
			dataMap.put("titles", titles);
			List<PageData> varOList = stockService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("STOCK_CODE"));	//1
				vpd.put("var2", varOList.get(i).getString("STOCK_NAME"));	//2
				vpd.put("var3", varOList.get(i).getString("STOCK_DSC"));	//3
				vpd.put("var4", varOList.get(i).getString("CITY_ID"));	//4
				vpd.put("var5", varOList.get(i).getString("STOCK_ADDR"));	//5
				vpd.put("var6", varOList.get(i).getString("MEMO"));	//6
				vpd.put("var7", varOList.get(i).getString("HEAD_EMP"));	//7
				vpd.put("var8", varOList.get(i).getString("HEAD_MOBILE"));	//8
				vpd.put("var9", varOList.get(i).getString("HEAD_FAX"));	//9
				vpd.put("var10", varOList.get(i).getString("HEAD_MAIL"));	//10
				vpd.put("var11", varOList.get(i).get("USE_FLG").toString());	//11
				vpd.put("var12", varOList.get(i).getString("CREATE_EMP"));	//12
				vpd.put("var13", varOList.get(i).getString("CREATE_TM"));	//13
				vpd.put("var14", varOList.get(i).getString("MODIFY_EMP"));	//14
				vpd.put("var15", varOList.get(i).getString("MODIFY_TM"));	//15
				vpd.put("var16", varOList.get(i).get("DEL_FLG").toString());	//16
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


	@RequestMapping(value = "/hasStockCode")
	@ResponseBody
	public Object hasStockCode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (stockService.findByCode(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断名称是否存在
	 */
	@RequestMapping(value = "/hasStockCn")
	@ResponseBody
	public Object hasStockCn() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (stockService.findByCn(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
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
