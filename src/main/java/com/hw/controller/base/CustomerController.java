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
import com.hw.service.base.SelectService;
import com.hw.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.service.base.CustomerService;

import java.util.TreeMap;
/**
 * Created：panda.HuangWei
 * Date：2016-10-23
 */
@Controller
@RequestMapping(value="/customer")
public class CustomerController extends BaseController {
	
	String menuUrl = "customer/list.do"; //菜单地址(权限用)
	@Resource(name="customerService")
	private CustomerService customerService;
	@Resource(name="selectService")
	private SelectService selectService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("CUSTOMER_ID", this.get32UUID());	//主键

		pd.put("CREATE_EMP", getCurUserName());	//修改人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", 0);	//删除



		customerService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			customerService.delete(pd);
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
		logBefore(logger, "修改Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

		customerService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Customer");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			List<Select> cityAllList =selectService.findAllCity();
			mv.addObject("cityAllList", cityAllList);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(isSearch(searchFlag)) {
				varList = customerService.list(page);	//列出Customer列表
			}

			mv.setViewName("base/customer_list");
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
		logBefore(logger, "去新增Customer页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("USE_FLG",1);
			setAddSelect(mv);

			pd.put("SEPERATOR",Const.SPLIT_COLON);
			mv.setViewName("base/customer_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	

	private void setAddSelect(ModelAndView mv) {
		List<Select> countryList = selectService.findAllCountry();
		List<Select> provinceList = new ArrayList<>();
		List<Select> cityList = new ArrayList<>();
		/*if (countryList != null && countryList.size() >=1) {
			String countryId = countryList.get(0).getId();
			provinceList = selectService.findProvinceByCountry(countryId);

			*//*if (provinceList != null && provinceList.size() >= 1) {
				String provinceId =  provinceList.get(0).getId();
				cityList = selectService.findCityByProvince(provinceId);
			}*//*
		}*/
		List<Select> cityAllList =selectService.findAllCity();
		mv.addObject("countryList", countryList);
		mv.addObject("provinceList", provinceList);
		mv.addObject("cityList", cityList);
		mv.addObject("cityAllList", cityAllList);

		List<Select> bulkRuleList = SelectCache.getInstance().getBulkRule();
		List<Select> storageRuleList = SelectCache.getInstance().getStorageRule();

		mv.addObject("bulkRuleList", bulkRuleList);
		mv.addObject("storageRuleList", storageRuleList);

		List<Select> packRuleList = SelectCache.getInstance().getPackRule();
		List<Select> pickRuleList = SelectCache.getInstance().getPickRule(null);
		List<Select> stockAsgRuleList = SelectCache.getInstance().getStockAssignedRule(null);
		List<Select> storageAsgRuleList = SelectCache.getInstance().getStorageAssignedRule(null);
		//定义为批次属性下拉
		List<Select> customerList = SelectCache.getInstance().getAllCustomer();

		mv.addObject("packRuleList", packRuleList);
		mv.addObject("pickRuleList", pickRuleList);
		mv.addObject("stockAsgRuleList", stockAsgRuleList);
		mv.addObject("storageAsgRuleList", storageAsgRuleList);
		mv.addObject("customerList", customerList);
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Customer页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {

			pd = customerService.findById(pd);	//根据ID读取
			if (StringUtil.isEmpty(pd.getString("SEPERATOR"))) {
				pd.put("SEPERATOR",Const.SPLIT_COLON);
			}

			setEditSelect(mv,pd);
			List<PageData> list = customerService.findDeal(pd);
			mv.addObject("varList",list);
			mv.setViewName("base/customer_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}


	private void setEditSelect(ModelAndView mv,PageData pd) {
		List<Select> countryList = selectService.findAllCountry();
		List<Select> provinceList = new ArrayList<>();
		List<Select> cityList = new ArrayList<>();

		String countryId = pd.getString("COUNTRY_ID");
		if (StringUtil.isNotEmpty(countryId)) {
			provinceList = selectService.findProvinceByCountry(countryId);
		} else {
			if (countryList != null && countryList.size() >=1) {
				countryId = countryList.get(0).getId();
				provinceList = selectService.findProvinceByCountry(countryId);
			}
		}

		String provinceId = pd.getString("PROVINCE_ID");
		if (StringUtil.isNotEmpty(provinceId)) {
			cityList = selectService.findCityByProvince(provinceId);
		} else {
			if (provinceList != null && provinceList.size() >= 1) {
				provinceId =  provinceList.get(0).getId();
				cityList = selectService.findCityByProvince(provinceId);
			}
		}
		List<Select> cityAllList =selectService.findAllCity();
		mv.addObject("countryList", countryList);
		mv.addObject("provinceList", provinceList);
		mv.addObject("cityList", cityList);
		mv.addObject("cityAllList", cityAllList);

		List<Select> bulkRuleList = SelectCache.getInstance().getBulkRule();
		List<Select> storageRuleList = SelectCache.getInstance().getStorageRule();

		mv.addObject("bulkRuleList", bulkRuleList);
		mv.addObject("storageRuleList", storageRuleList);

		String customerId = pd.getString("CUSTOMER_ID");
		List<Select> packRuleList = SelectCache.getInstance().getPackRule();
		List<Select> pickRuleList = SelectCache.getInstance().getPickRule(customerId);
		List<Select> stockAsgRuleList = SelectCache.getInstance().getStockAssignedRule(customerId);
		List<Select> storageAsgRuleList = SelectCache.getInstance().getStorageAssignedRule(customerId);
		List<Select> customerList = SelectCache.getInstance().getAllCustomer();

		mv.addObject("packRuleList", packRuleList);
		mv.addObject("pickRuleList", pickRuleList);
		mv.addObject("stockAsgRuleList", stockAsgRuleList);
		mv.addObject("storageAsgRuleList", storageAsgRuleList);
		mv.addObject("customerList", customerList);
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Customer");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				customerService.deleteAll(ArrayDATA_IDS);
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
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/goUpload")
	public ModelAndView goUpload() throws Exception {
		ModelAndView mv = this.getModelAndView();

		PageData pd = new PageData();
		pd = this.getPageData();

		mv.addObject("pd", pd);
		mv.setViewName("base/customer_ct_upload");
		return mv;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value = "/uploadDeal")
	@ResponseBody
	public ModelAndView uploadDeal(@RequestParam(value = "excel", required = false) MultipartFile file,
								   @RequestParam(value = "CUSTOMER_ID", required = false) String customerId,
								   @RequestParam(value = "CONTRACTUAL_ACTIVE", required = false) String activeDt,
								   @RequestParam(value = "CONTRACTUAL_DISABLE", required = false) String disableDt
	) throws Exception {
		logBefore(logger, "上传合约");
		Map<String, String> map = new HashMap<String, String>();
		ModelAndView mv = this.getModelAndView();

		String fileName = "";//ffile = DateUtil.getDays(),
		String filePath = "";
		PageData pd = new PageData();
		pd = this.getPageData();
		if (Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			if (null != file && !file.isEmpty()) {
				filePath = PathUtil.getClasspath() + Const.CUSTOMER_FILE_PATH;        //文件上传路径
				//fileName = FileUpload.fileUp(file, filePath, this.get32UUID());                //执行上传
				String name = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
				fileName = FileUpload.fileUp(file, filePath, name);
			} else {
				System.out.println("上传失败");
			}

			pd.put("DEAL_ID", this.get32UUID());            //主键
			pd.put("CUSTOMER_ID", customerId);
			pd.put("DEAL_NAME", fileName);
			pd.put("DEAL_PATH", filePath);

			pd.put("ACTIVE_DT", activeDt);
			pd.put("DISABLE_DT", disableDt);

			pd.put("CREATE_EMP", getCurUserName());	//修改人
			pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
			pd.put("MODIFY_EMP", getCurUserName());	//修改人
			pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
			pd.put("DEL_FLG", 0);	//删除

			customerService.saveDeal(pd);
		}
		map.put("result", "ok");
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}

	@RequestMapping(value = "/getDeal")
	@ResponseBody
	public Object getDeal() {
		Map<String,List<PageData>> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			List<PageData> dtl = customerService.findDeal(pd);

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

	@RequestMapping(value="/delDeal")
	public void delDeal(PrintWriter out){
		logBefore(logger, "删除合约");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			customerService.delDeal(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}

	/**
	 * 下载合约
	 */
	@RequestMapping(value = "/downDeal")
	public void downDeal(HttpServletResponse response) throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		PageData data = customerService.findDealById(pd);

		String fileName = data.getString("DEAL_NAME");
		String path = data.getString("DEAL_PATH");

		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.CUSTOMER_FILE_PATH + fileName, fileName);
	}
	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Customer到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("客户编码");	//1
			titles.add("客户名称");	//2
			titles.add("客户简称");	//3
			titles.add("英文名称");	//4
			titles.add("检索码");	//5
			titles.add("上级企业");	//6
			titles.add("国家");	//7
			titles.add("省/州");	//8
			titles.add("城市");	//9
			titles.add("地址");	//10
			titles.add("网站地址");	//11
			titles.add("海关编号");	//12
			titles.add("经营单位");	//13
			titles.add("付款周期");	//14
			titles.add("电话");	//15
			titles.add("客服人员名称");	//16
			titles.add("业务员");	//17
			titles.add("客户财务编号");	//18
			titles.add("供应商财务编码");	//19
			titles.add("付款客户");	//20
			titles.add("企业类型");	//21
			titles.add("供应商");	//22
			titles.add("厂商");	//23
			titles.add("货代");	//24
			titles.add("发货人");	//25
			titles.add("收货人");	//26
			titles.add("车队");	//27
			titles.add("报关行");	//28
			titles.add("所属公司");	//29
			titles.add("启用");	//30
			titles.add("名称");	//31
			titles.add("英文名");	//32
			titles.add("城市");	//33
			titles.add("地址");	//34
			titles.add("部门");	//35
			titles.add("职务");	//36
			titles.add("电话");	//37
			titles.add("传真");	//38
			titles.add("邮箱");	//39
			titles.add("生日");	//40
			titles.add("纪念日");	//41
			titles.add("包装规则");	//42
			titles.add("码放规则");	//43
			titles.add("拣货规则");	//44
			titles.add("拣货分配规则");	//45
			titles.add("预分配规则");	//46
			titles.add("批次属性规则");	//47
			titles.add("库存周转规则");	//48
			titles.add("库位指定规则");	//49
			titles.add("体积重量计算方式");	//50
			titles.add("超量收货");	//51
			titles.add("超量发货");	//52
			titles.add("文件名");	//53
			titles.add("生效日期");	//54
			titles.add("失效日期");	//55
			titles.add("创建人");	//56
			titles.add("创建时间");	//57
			titles.add("修改人");	//58
			titles.add("修改时间");	//59
			titles.add("删除");	//60
			dataMap.put("titles", titles);
			List<PageData> varOList = customerService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("CUSTOMER_CODE"));	//1
				vpd.put("var2", varOList.get(i).getString("CUSTOMER_CN"));	//2
				vpd.put("var3", varOList.get(i).getString("CUSTOMER_SHORT"));	//3
				vpd.put("var4", varOList.get(i).getString("CUSTOMER_EN"));	//4
				vpd.put("var5", varOList.get(i).getString("SEACH_CODE"));	//5
				vpd.put("var6", varOList.get(i).getString("UP_COMPANY"));	//6
				vpd.put("var7", varOList.get(i).getString("COUNTRY_ID"));	//7
				vpd.put("var8", varOList.get(i).getString("PROVINCE_ID"));	//8
				vpd.put("var9", varOList.get(i).getString("CITY_ID"));	//9
				vpd.put("var10", varOList.get(i).getString("CUSTOMER_ADDR"));	//10
				vpd.put("var11", varOList.get(i).getString("INTEL_ADDR"));	//11
				vpd.put("var12", varOList.get(i).getString("CUSTOMS_CODE"));	//12
				vpd.put("var13", varOList.get(i).getString("OPERATING_UNIT"));	//13
				vpd.put("var14", varOList.get(i).get("PAY_PERIOD").toString());	//14
				vpd.put("var15", varOList.get(i).getString("TEL_PHONE"));	//15
				vpd.put("var16", varOList.get(i).getString("SERVIC_ER"));	//16
				vpd.put("var17", varOList.get(i).getString("SALE_MAN"));	//17
				vpd.put("var18", varOList.get(i).getString("CUSTOMER_FINANCE"));	//18
				vpd.put("var19", varOList.get(i).getString("SUPPLIER_FINANCE"));	//19
				vpd.put("var20", varOList.get(i).getString("PAY_MERCHANT"));	//20
				vpd.put("var21", varOList.get(i).getString("CUSTOMER_TYPE"));	//21
				vpd.put("var22", varOList.get(i).get("TYPE_SUPPLIER").toString());	//22
				vpd.put("var23", varOList.get(i).get("TYPE_FACTOR").toString());	//23
				vpd.put("var24", varOList.get(i).get("TYPE_FOB").toString());	//24
				vpd.put("var25", varOList.get(i).get("TYPE_SEND").toString());	//25
				vpd.put("var26", varOList.get(i).get("TYPE_TAKE").toString());	//26
				vpd.put("var27", varOList.get(i).get("TYPE_CAR").toString());	//27
				vpd.put("var28", varOList.get(i).get("TYPE_CUSTOM").toString());	//28
				vpd.put("var29", varOList.get(i).getString("BELONE_COMPANY"));	//29
				vpd.put("var30", varOList.get(i).get("USE_FLG").toString());	//30
				vpd.put("var31", varOList.get(i).getString("CONTACTS_CN"));	//31
				vpd.put("var32", varOList.get(i).getString("CONTACTS_EN"));	//32
				vpd.put("var33", varOList.get(i).getString("CONTACTS_CITY_ID"));	//33
				vpd.put("var34", varOList.get(i).getString("CONTACTS_ADDR"));	//34
				vpd.put("var35", varOList.get(i).getString("CONTACTS_DEPT"));	//35
				vpd.put("var36", varOList.get(i).getString("CONTACTS_POST"));	//36
				vpd.put("var37", varOList.get(i).getString("CONTACTS_PHONE"));	//37
				vpd.put("var38", varOList.get(i).getString("CONTACTS_FAX"));	//38
				vpd.put("var39", varOList.get(i).getString("CONTACTS_EMAIL"));	//39
				vpd.put("var40", varOList.get(i).getString("CONTACTS_BIRTH"));	//40
				vpd.put("var41", varOList.get(i).getString("CONTACTS_MEMORI"));	//41
				vpd.put("var42", varOList.get(i).getString("RULE_PACK"));	//42
				vpd.put("var43", varOList.get(i).getString("RULE_PUT"));	//43
				vpd.put("var44", varOList.get(i).getString("RULE_PICK"));	//44
				vpd.put("var45", varOList.get(i).getString("RULE_PICK_ASIGN"));	//45
				vpd.put("var46", varOList.get(i).getString("RULE_DEFALUT_ASIGN"));	//46
				vpd.put("var47", varOList.get(i).getString("RULE_BATCH"));	//47
				vpd.put("var48", varOList.get(i).getString("RULE_STOCK"));	//48
				vpd.put("var49", varOList.get(i).getString("RULE_STORAGE"));	//49
				vpd.put("var50", varOList.get(i).getString("RULE_BULK"));	//50
				vpd.put("var51", varOList.get(i).get("SUPR_TAKE").toString());	//51
				vpd.put("var52", varOList.get(i).get("SUPR_SEND").toString());	//52
				vpd.put("var53", varOList.get(i).getString("CONTRACTUAL_FILE"));	//53
				vpd.put("var54", varOList.get(i).getString("CONTRACTUAL_ACTIVE"));	//54
				vpd.put("var55", varOList.get(i).getString("CONTRACTUAL_DISABLE"));	//55
				vpd.put("var56", varOList.get(i).getString("CREATE_EMP"));	//56
				vpd.put("var57", varOList.get(i).getString("CREATE_TM"));	//57
				vpd.put("var58", varOList.get(i).getString("MODIFY_EMP"));	//58
				vpd.put("var59", varOList.get(i).getString("MODIFY_TM"));	//59
				vpd.put("var60", varOList.get(i).get("DEL_FLG").toString());	//60
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
			if (customerService.findByCode(pd) != null) {
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
			if (customerService.findByCn(pd) != null) {
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
