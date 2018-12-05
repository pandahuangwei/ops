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
import javax.servlet.http.HttpServletResponse;

import com.hw.cache.SelectCache;
import com.hw.entity.base.Select;
import com.hw.service.base.StorageService;
import com.hw.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.service.base.LocatorService;

/**
 * Created：panda.HuangWei
 * Date：2016-10-22
 */
@Controller
@RequestMapping(value="/locator")
public class LocatorController extends BaseController {
	
	String menuUrl = "locator/list.do"; //菜单地址(权限用)
	@Resource(name="locatorService")
	private LocatorService locatorService;

	@Resource(name="storageService")
	private StorageService storageService;
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Locator");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String plies = pd.getString("PLIES_UNIT");
		if (StringUtil.isEmpty(plies)) {
			pd.put("PLIES_UNIT",0);
		}
		String prod = pd.getString("PRODUCT_MIX");
		if (StringUtil.isEmpty(prod)) {
			pd.put("PRODUCT_MIX",0);
		}
		String batch = pd.getString("BATCH_MIX");
		if (StringUtil.isEmpty(batch)) {
			pd.put("BATCH_MIX",0);
		}


		pd.put("LOCATOR_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", 0);	//删除
		locatorService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Locator");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			locatorService.delete(pd);
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
		logBefore(logger, "修改Locator");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String plies = pd.getString("PLIES_UNIT");
		if (StringUtil.isEmpty(plies)) {
			pd.put("PLIES_UNIT",0);
		}
		String prod = pd.getString("PRODUCT_MIX");
		if (StringUtil.isEmpty(prod)) {
			pd.put("PRODUCT_MIX",0);
		}
		String batch = pd.getString("BATCH_MIX");
		if (StringUtil.isEmpty(batch)) {
			pd.put("BATCH_MIX",0);
		}
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

		locatorService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Locator");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{

			String searchField = pd.getString("searchField");
			if(StringUtil.isEmpty(searchField)) {
				pd.put("searchField",searchField);
			}

			setSelect(mv);
			pd = this.getPageData();
			page.setPd(pd);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(isSearch(searchFlag)) {
				varList = locatorService.list(page);	//列出Locator列表
			}

			mv.setViewName("base/locator_list");
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
		logBefore(logger, "去新增Locator页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			mv.setViewName("base/locator_edit");
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
		logBefore(logger, "去修改Locator页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			pd = locatorService.findById(pd);	//根据ID读取
			mv.setViewName("base/locator_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	

	private void setSelect(ModelAndView mv) {
		//List<PageData>  storageList = storageService.findAll();
		List<Select> storageList = SelectCache.getInstance().getAllStorage();
		List<Select>  locatorStateList = SelectCache.getInstance().getLocatorState();
		List<Select>  locatorUseList = SelectCache.getInstance().getLocatorUse();
		List<Select>  locatorTypeList = SelectCache.getInstance().getLocatorType();
		List<Select>  locatorUnitList = SelectCache.getInstance().getLocatorUnit();
		List<Select>  turnoverCycleList = SelectCache.getInstance().getTurnoverCycle();
		List<Select>  booleanList = SelectCache.getInstance().getBoolean();
		List<Select> locatorPriLevelList = SelectCache.getInstance().getLocatorPriorityLevel();

		mv.addObject("storageList", storageList);
		mv.addObject("locatorStateList", locatorStateList);
		mv.addObject("locatorUseList", locatorUseList);
		mv.addObject("locatorTypeList", locatorTypeList);
		mv.addObject("locatorUnitList", locatorUnitList);
		mv.addObject("turnoverCycleList", turnoverCycleList);
		mv.addObject("booleanList", booleanList);
		mv.addObject("locatorPriLevelList", locatorPriLevelList);
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Locator");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				locatorService.deleteAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出Locator到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("库位编码");	//1
			titles.add("库区编码");	//2
			titles.add("排");	//3
			titles.add("层");	//4
			titles.add("列");	//5
			titles.add("库位属性");	//6
			titles.add("库位使用");	//7
			titles.add("库位类型");	//8
			titles.add("库位处理");	//9
			titles.add("周转周期");	//10
			titles.add("备注");	//11
			titles.add("体积限制");	//12
			titles.add("重量限制");	//13
			titles.add("数量限制");	//14
			titles.add("托盘限制");	//15
			titles.add("长");	//16
			titles.add("宽");	//17
			titles.add("高");	//18
			titles.add("层数");	//19
			titles.add("产品混合");	//20
			titles.add("批号混合");	//21
			titles.add("创建人");	//22
			titles.add("创建时间");	//23
			titles.add("修改人");	//24
			titles.add("修改时间");	//25
			titles.add("删除");	//26
			dataMap.put("titles", titles);
			List<PageData> varOList = locatorService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("LOCATOR_CODE"));	//1
				vpd.put("var2", varOList.get(i).getString("STORAGE_CODE"));	//2
				vpd.put("var3", varOList.get(i).getString("ROW"));	//3
				vpd.put("var4", varOList.get(i).getString("FLOOR"));	//4
				vpd.put("var5", varOList.get(i).getString("QUEUE"));	//5
				vpd.put("var6", varOList.get(i).get("LOCATOR_STATE").toString());	//6
				vpd.put("var7", varOList.get(i).get("LOCATOR_USE").toString());	//7
				vpd.put("var8", varOList.get(i).get("LOCATOR_TYPE").toString());	//8
				vpd.put("var9", varOList.get(i).get("LOCATOR_UNIT").toString());	//9
				vpd.put("var10", varOList.get(i).get("TURNOVER_CYCLE").toString());	//10
				vpd.put("var11", varOList.get(i).getString("MEMO"));	//11
				vpd.put("var12", varOList.get(i).getString("VOLUME_LIMIT"));	//12
				vpd.put("var13", varOList.get(i).getString("WEIGHT_LIMIT"));	//13
				vpd.put("var14", varOList.get(i).getString("QUANTITY_LIMIT"));	//14
				vpd.put("var15", varOList.get(i).getString("PALLET_LIMIT"));	//15
				vpd.put("var16", varOList.get(i).getString("LONG"));	//16
				vpd.put("var17", varOList.get(i).getString("WIDE"));	//17
				vpd.put("var18", varOList.get(i).getString("HIGH"));	//18
				vpd.put("var19", varOList.get(i).get("PLIES").toString());	//19
				vpd.put("var20", varOList.get(i).get("PRODUCT_MIX").toString());	//20
				vpd.put("var21", varOList.get(i).getString("BATCH_MIX"));	//21
				vpd.put("var22", varOList.get(i).getString("CREATE_EMP"));	//22
				vpd.put("var23", varOList.get(i).getString("CREATE_TM"));	//23
				vpd.put("var24", varOList.get(i).getString("MODIFY_EMP"));	//24
				vpd.put("var25", varOList.get(i).getString("MODIFY_TM"));	//25
				vpd.put("var26", varOList.get(i).get("DEL_FLG").toString());	//26
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

	@RequestMapping(value = "/hasCode")
	@ResponseBody
	public Object hasCode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData code = locatorService.findByCode(pd);
			if ( code!= null) {
				String stockId = code.getString("STOCK_ID");
				if (StringUtil.isEmpty(stockId)) {
					errInfo = "success";
					//errInfo = "error";
				} else {
					pd.put("STOCK_ID",stockId);
					if (locatorService.findStockHadLocator(pd) != null) {
						errInfo = "error";
					}
				}
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
			if (locatorService.findByCode(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.addObject("pd", pd);
		mv.setViewName("base/locator_uploadexl");
		return mv;
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Locator.xls", "Locator.xls");
	}


	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file
	) throws Exception {
		ModelAndView mv = this.getModelAndView();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "outExcel");                            //执行上传

			PageData pd = new PageData();
			List<PageData> listPd = (List) ObjectExcelRead.readLocatorExcel(filePath, fileName, 2, 0, 0);    //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			List<PageData> auditRs = new ArrayList<>();
			List<PageData> savepdList = new ArrayList<>();
			locatorService.auditList(listPd,auditRs,savepdList);
            //如果校验数据结果不为空，说明excel中填入的数据有问题，不做导入处理
			if (auditRs != null && !auditRs.isEmpty()) {
				pd.put("IMP_RS","数据存在异常,请修改正确后重新导入...");

				mv.addObject("pd", pd);
				mv.addObject("xlsList", auditRs);
				mv.addObject("msg", "success");
				mv.setViewName("base/locator_uploadexl");
				return mv;
			}

            if(savepdList != null && !savepdList.isEmpty()) {
				locatorService.saveBatch(savepdList);

			  /*存入数据库操作======================================*/
				pd.put("IMP_SUCC","数据成功导入...");
				pd.put("success","success");
				mv.addObject("pd", pd);
				mv.addObject("xlsList", new ArrayList<String>());
				mv.addObject("msg", "success");
			}
		}
		mv.setViewName("base/locator_uploadexl");
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
