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
import com.hw.service.property.StorageAsignRuleService;

/**
 * 库位分配规则
 * Created：panda.HuangWei
 * Date：2016-10-24
 */
@Controller
@RequestMapping(value="/storageasignrule")
public class StorageAsignRuleController extends BaseController {
	
	String menuUrl = "storageasignrule/list.do"; //菜单地址(权限用)
	@Resource(name="storageasignruleService")
	private StorageAsignRuleService storageasignruleService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增StorageAsignRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STORAGEASIGNRULE_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除
		pd.put("LOCATOR_PRIORITY_LEVEL",pd.getString("LOCATOR_PRIORITY_LEVEL_1"));
		pd.put("PROD_LIMIT",pd.getString("PROD_LIMIT_1"));


		storageasignruleService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除StorageAsignRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			storageasignruleService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	private void setSelect(ModelAndView mv) {
	List<Select> customerList = SelectCache.getInstance().getAllCustomer();
		mv.addObject("customerList",customerList);

		List<Select> locatorPriLevelList = SelectCache.getInstance().getLocatorPriorityLevel();
		mv.addObject("locatorPriLevelList",locatorPriLevelList);

		List<Select> productTypeList = SelectCache.getInstance().getProductType();
		mv.addObject("productTypeList",productTypeList);
	}
	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, "修改StorageAsignRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除
        pd.put("LOCATOR_PRIORITY_LEVEL",pd.getString("LOCATOR_PRIORITY_LEVEL_1"));
		pd.put("PROD_LIMIT",pd.getString("PROD_LIMIT_1"));

		storageasignruleService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表StorageAsignRule");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);

			setSelect(mv);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(isSearch(searchFlag)) {
				varList = storageasignruleService.list(page);    //列出StorageAsignRule列表
			}
			mv.setViewName("property/storageasignrule_list");
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
		logBefore(logger, "去新增StorageAsignRule页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			mv.setViewName("property/storageasignrule_edit");
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
		logBefore(logger, "去修改StorageAsignRule页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			pd = storageasignruleService.findById(pd);	//根据ID读取

//			List<String> locatorPriLevelList = pd.getList("LOCATOR_PRIORITY_LEVEL");
//			mv.addObject("locatorPriLevelList",locatorPriLevelList);

			mv.setViewName("property/storageasignrule_edit");
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
		logBefore(logger, "批量删除StorageAsignRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				storageasignruleService.deleteAll(ArrayDATA_IDS);
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


	@RequestMapping(value = "/hasCode")
	@ResponseBody
	public Object hasCode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (storageasignruleService.findByCode(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}


	@RequestMapping(value = "/hasCn")
	@ResponseBody
	public Object hasCn() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (storageasignruleService.findByCn(pd) != null) {
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
		logBefore(logger, "导出StorageAsignRule到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("客户");	//1
			titles.add("库位分配编码");	//2
			titles.add("库位分配名称");	//3
			titles.add("备注");	//4
			titles.add("位置优先");	//5
			titles.add("产品限制");	//6
			titles.add("启用");	//7
			titles.add("体积限制");	//8
			titles.add("启用");	//9
			titles.add("长限制");	//10
			titles.add("启用");	//11
			titles.add("宽限制");	//12
			titles.add("启用");	//13
			titles.add("高限制");	//14
			titles.add("启用");	//15
			titles.add("创建人");	//16
			titles.add("创建时间");	//17
			titles.add("修改人");	//18
			titles.add("修改时间");	//19
			titles.add("删除");	//20
			dataMap.put("titles", titles);
			List<PageData> varOList = storageasignruleService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("CUSTOMER_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("STORAGEASING_CODE"));	//2
				vpd.put("var3", varOList.get(i).getString("STORAGEASING_CN"));	//3
				vpd.put("var4", varOList.get(i).getString("MEMO"));	//4
				vpd.put("var5", varOList.get(i).getString("SEAT_STATE"));	//5
				vpd.put("var6", varOList.get(i).getString("PROD_LIMIT"));	//6
				vpd.put("var7", varOList.get(i).getString("PROD_LIMIT_USE"));	//7
				vpd.put("var8", varOList.get(i).getString("VOLUME_LIMIT"));	//8
				vpd.put("var9", varOList.get(i).getString("VOLUME_LIMIT_USE"));	//9
				vpd.put("var10", varOList.get(i).getString("LONG_LIMIT"));	//10
				vpd.put("var11", varOList.get(i).getString("LONG_LIMIT_USE"));	//11
				vpd.put("var12", varOList.get(i).getString("WIDE_LIMIT"));	//12
				vpd.put("var13", varOList.get(i).getString("WIDE_LIMIT_USE"));	//13
				vpd.put("var14", varOList.get(i).getString("HIGH_LIMIT"));	//14
				vpd.put("var15", varOList.get(i).getString("HIGH_LIMIT_USE"));	//15
				vpd.put("var16", varOList.get(i).getString("CREATE_EMP"));	//16
				vpd.put("var17", varOList.get(i).getString("CREATE_TM"));	//17
				vpd.put("var18", varOList.get(i).getString("MODIFY_EMP"));	//18
				vpd.put("var19", varOList.get(i).getString("MODIFY_TM"));	//19
				vpd.put("var20", varOList.get(i).getString("DEL_FLG"));	//20
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
