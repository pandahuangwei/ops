package com.hw.controller.base;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Province;
import com.hw.entity.base.Select;
import com.hw.service.base.ProductService;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-10-23
 */
@Controller
@RequestMapping(value="/product")
public class ProductController extends BaseController {

	String menuUrl = "product/list.do"; //菜单地址(权限用)
	@Resource(name="productService")
	private ProductService productService;
	@Resource(name="selectService")
	private SelectService selectService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Product");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PRODUCT_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", 0);	//删除

		setDefalut(pd);

		productService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Product");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			productService.delete(pd);
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
		logBefore(logger, "修改Product");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		setDefalut(pd);

		productService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Product");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			setSelect(mv,null);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(isSearch(searchFlag)) {
				varList = productService.list(page);	//列出Product列表
			}

			mv.setViewName("base/product_list");
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
		logBefore(logger, "去新增Product页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv,null);
			mv.setViewName("base/product_edit");
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
		logBefore(logger, "去修改Product页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {

			pd = productService.findById(pd);	//根据ID读取

			setSelect(mv,pd.getString("CUSTOMER_ID"));

			mv.setViewName("base/product_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

	private void setSelect(ModelAndView mv,String customerId) {
		List<Select> totalWeightList = SelectCache.getInstance().getTotalWeight();
		List<Select> totalSuttleList = SelectCache.getInstance().getTotalSuttle();
		List<Select> volumeList = SelectCache.getInstance().getVolume();
		List<Select> freeRejectList = SelectCache.getInstance().getFreeReject();
		List<Select> periodList = SelectCache.getInstance().getPeriod();
		List<Select> productTypeList = SelectCache.getInstance().getProductType();

		mv.addObject("totalWeightList", totalWeightList);
		mv.addObject("totalSuttleList", totalSuttleList);
		mv.addObject("volumeList", volumeList);
		mv.addObject("freeRejectList", freeRejectList);
		mv.addObject("periodList", periodList);
		mv.addObject("productTypeList", productTypeList);

		List<Select> customerList = selectService.findAllCustomer();
		List<Select> stockList = selectService.findAllStock();
		mv.addObject("customerList", customerList);
		mv.addObject("stockList", stockList);
		String customer = null;
		if (customerList != null && !customerList.isEmpty() ) {
			customer = customerList.get(0).getId();
		}

		customer = StringUtil.isEmpty(customerId) ?customer:customerId;

		List<Select> storageList = SelectCache.getInstance().getAllStorage();
		List<Select> locatorList = SelectCache.getInstance().getAllLocator();

		mv.addObject("storageList", storageList);
		mv.addObject("locatorList", locatorList);

		List<Select> storageRuleList = SelectCache.getInstance().getStorageRule();
		List<Select> packRuleList = SelectCache.getInstance().getPackRule();
		List<Select> pickRuleList = SelectCache.getInstance().getPickRule(customer);
		List<Select> stockAsgRuleList = SelectCache.getInstance().getStockAssignedRule(customer);
		List<Select> storageAsgRuleList = SelectCache.getInstance().getStorageAssignedRule(customer);
		List<Select> stockTurnRuleList = SelectCache.getInstance().getStockTurnRule();

		mv.addObject("storageRuleList", storageRuleList);
		mv.addObject("packRuleList", packRuleList);
		mv.addObject("pickRuleList", pickRuleList);
		mv.addObject("stockAsgRuleList", stockAsgRuleList);
		mv.addObject("storageAsgRuleList", storageAsgRuleList);
		mv.addObject("stockTurnRuleList", stockTurnRuleList);

	}


	private void setDefalut(PageData pd) {
		String totalWeightUnit = pd.getString("TOTAL_WEIGHT_UNIT");
		if (StringUtil.isEmpty(totalWeightUnit)) {
			pd.put("TOTAL_WEIGHT_UNIT",0);
		}

		String totalSuttleUnit = pd.getString("TOTAL_SUTTLE_UNIT");
		if (StringUtil.isEmpty(totalSuttleUnit)) {
			pd.put("TOTAL_SUTTLE_UNIT",0);
		}

		String volumeUnit = pd.getString("VOLUME_UNIT");
		if (StringUtil.isEmpty(volumeUnit)) {
			pd.put("VOLUME_UNIT",0);
		}

		String dangerFlg = pd.getString("DANGER_FLG");
		if (StringUtil.isEmpty(dangerFlg)) {
			pd.put("DANGER_FLG",0);
		}
		String managerFlg = pd.getString("MANAGER_FLG");
		if (StringUtil.isEmpty(managerFlg)) {
			pd.put("MANAGER_FLG",0);
		}

		String combiFlg = pd.getString("COMBI_FLG");
		if (StringUtil.isEmpty(combiFlg)) {
			pd.put("COMBI_FLG",0);
		}

		String freezeRejectCode = pd.getString("FREEZE_REJECT_CODE");
		if (StringUtil.isEmpty(freezeRejectCode)) {
			pd.put("FREEZE_REJECT_CODE",0);
		}

		String activeFlg  = pd.getString("ACTIVE_FLG");
		if (StringUtil.isEmpty(activeFlg)) {
			pd.put("ACTIVE_FLG",0);
		}

		String useValidity  = pd.getString("USE_VALIDITY");
		if (StringUtil.isEmpty(useValidity)) {
			pd.put("USE_VALIDITY",0);
		}

		String periodType  = pd.getString("PERIOD_TYPE");
		if (StringUtil.isEmpty(periodType)) {
			pd.put("PERIOD_TYPE",0);
		}

		String inboundDay  = pd.getString("INBOUND_DAY");
		if (StringUtil.isEmpty(inboundDay)) {
			pd.put("INBOUND_DAY",0);
		}

		String outboundDay  = pd.getString("OUTBOUND_DAY");
		if (StringUtil.isEmpty(outboundDay)) {
			pd.put("OUTBOUND_DAY",0);
		}

	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除Product");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				productService.deleteAll(ArrayDATA_IDS);
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
			if (productService.findByCode(pd) != null) {
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
			if (productService.findByCn(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "/getlocator")
	@ResponseBody
	public Object getlocator() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String storageId = pd.getString("STORAGE_ID");
			List<Select> list = SelectCache.getInstance().getLocator(storageId);
			if (list == null || list.isEmpty()) {
				return AppUtil.returnObject(pd, map);
			}
			for (Select p : list) {
				map.put(p.getId(),p.getValue());
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping(value = "/getPickRule")
	@ResponseBody
	public Object getPickRule() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String id = pd.getString("CUSTOMER_ID");
			List<Select> list = SelectCache.getInstance().getPickRule(id);
			if (list == null || list.isEmpty()) {
				return AppUtil.returnObject(pd, map);
			}
			for (Select p : list) {
				map.put(p.getId(),p.getValue());
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping(value = "/getStockAsgRule")
	@ResponseBody
	public Object getStockAsgRule() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String id = pd.getString("CUSTOMER_ID");
			List<Select> list = SelectCache.getInstance().getStockAssignedRule(id);;
			if (list == null || list.isEmpty()) {
				return AppUtil.returnObject(pd, map);
			}
			for (Select p : list) {
				map.put(p.getId(),p.getValue());
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping(value = "/getStorageAssignedRule")
	@ResponseBody
	public Object getStorageAssignedRule() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String id = pd.getString("CUSTOMER_ID");
			List<Select> list =  SelectCache.getInstance().getStorageAssignedRule(id);
			if (list == null || list.isEmpty()) {
				return AppUtil.returnObject(pd, map);
			}
			for (Select p : list) {
				map.put(p.getId(),p.getValue());
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
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
		mv.setViewName("base/product_uploadexl");
		return mv;
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Product.xls", "Product.xls");
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

			productService.auditList(listPd,auditRs,savepdList);
			//如果校验数据结果不为空，说明excel中填入的数据有问题，不做导入处理
			if (auditRs != null && !auditRs.isEmpty()) {
				pd.put("IMP_RS","数据存在异常,请修改正确后重新导入...");

				mv.addObject("pd", pd);
				mv.addObject("xlsList", auditRs);
				mv.addObject("msg", "success");
				mv.setViewName("base/product_uploadexl");
				return mv;
			}

			if(savepdList != null && !savepdList.isEmpty()) {
				productService.save(savepdList);

			  /*存入数据库操作======================================*/
				pd.put("IMP_SUCC","数据成功导入...");
				pd.put("success","success");
				mv.addObject("pd", pd);
				mv.addObject("xlsList", new ArrayList<String>());
				mv.addObject("msg", "success");
			}
		}

		mv.setViewName("base/product_uploadexl");
		return mv;
	}

	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出Product到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("货主");	//1
			titles.add("仓库");	//2
			titles.add("产品编号");	//3
			titles.add("产品描述");	//4
			titles.add("英文描述");	//5
			titles.add("长cm");	//6
			titles.add("宽cm");	//7
			titles.add("高cm");	//8
			titles.add("单价");	//9
			titles.add("毛重单位");	//10
			titles.add("毛重");	//11
			titles.add("净重单位");	//12
			titles.add("净重");	//13
			titles.add("体积单位");	//14
			titles.add("体积");	//15
			titles.add("货物类型");	//16
			titles.add("码放库区");	//17
			titles.add("码放库位");	//18
			titles.add("冻结/拒收代码");	//19
			titles.add("备注");	//20
			titles.add("危险品");	//21
			titles.add("管理序列号");	//22
			titles.add("组合件");	//23
			titles.add("激活");	//24
			titles.add("包装规则");	//25
			titles.add("拣货规则");	//26
			titles.add("库位指定规则");	//27
			titles.add("库位分配规则");	//28
			titles.add("库存分配规则");	//29
			titles.add("启用有效期");	//30
			titles.add("周期类型");	//31
			titles.add("入库生命天数");	//32
			titles.add("出库生命天数");	//33
			titles.add("创建人");	//34
			titles.add("创建时间");	//35
			titles.add("修改人");	//36
			titles.add("修改时间");	//37
			titles.add("删除");	//38
			dataMap.put("titles", titles);
			List<PageData> varOList = productService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("CUSTOMER_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("STOCK_ID"));	//2
				vpd.put("var3", varOList.get(i).getString("PRODUCT_CODE"));	//3
				vpd.put("var4", varOList.get(i).getString("PRODUCT_CN"));	//4
				vpd.put("var5", varOList.get(i).getString("PRODUCT_EN"));	//5
				vpd.put("var6", varOList.get(i).getString("LONG_UNIT"));	//6
				vpd.put("var7", varOList.get(i).getString("WIDE_UNIT"));	//7
				vpd.put("var8", varOList.get(i).getString("HIGH_UNIT"));	//8
				vpd.put("var9", varOList.get(i).getString("UNIT_PRICE"));	//9
				vpd.put("var10", varOList.get(i).get("TOTAL_WEIGHT_UNIT").toString());	//10
				vpd.put("var11", varOList.get(i).getString("TOTAL_WEIGHT"));	//11
				vpd.put("var12", varOList.get(i).get("TOTAL_SUTTLE_UNIT").toString());	//12
				vpd.put("var13", varOList.get(i).getString("TOTAL_SUTTLE"));	//13
				vpd.put("var14", varOList.get(i).get("VOLUME_UNIT").toString());	//14
				vpd.put("var15", varOList.get(i).getString("VOLUME_UN"));	//15
				vpd.put("var16", varOList.get(i).getString("PRODUCT_TYPE"));	//16
				vpd.put("var17", varOList.get(i).getString("STORAGE_ID"));	//17
				vpd.put("var18", varOList.get(i).getString("LOCATOR_ID"));	//18
				vpd.put("var19", varOList.get(i).get("FREEZE_REJECT_CODE").toString());	//19
				vpd.put("var20", varOList.get(i).getString("MEMO"));	//20
				vpd.put("var21", varOList.get(i).get("DANGER_FLG").toString());	//21
				vpd.put("var22", varOList.get(i).get("MANAGER_FLG").toString());	//22
				vpd.put("var23", varOList.get(i).get("COMBI_FLG").toString());	//23
				vpd.put("var24", varOList.get(i).get("ACTIVE_FLG").toString());	//24
				vpd.put("var25", varOList.get(i).getString("RULE_PACK"));	//25
				vpd.put("var26", varOList.get(i).getString("RULE_PICK"));	//26
				vpd.put("var27", varOList.get(i).getString("RULE_STORAGE"));	//27
				vpd.put("var28", varOList.get(i).getString("RULE_STORAGE_ASIGN"));	//28
				vpd.put("var29", varOList.get(i).getString("RULE_STOCK_ASIGN"));	//29
				vpd.put("var30", varOList.get(i).get("USE_VALIDITY").toString());	//30
				vpd.put("var31", varOList.get(i).get("PERIOD_TYPE").toString());	//31
				vpd.put("var32", varOList.get(i).get("INBOUND_DAY").toString());	//32
				vpd.put("var33", varOList.get(i).get("OUTBOUND_DAY").toString());	//33
				vpd.put("var34", varOList.get(i).getString("CREATE_EMP"));	//34
				vpd.put("var35", varOList.get(i).getString("CREATE_TM"));	//35
				vpd.put("var36", varOList.get(i).getString("MODIFY_EMP"));	//36
				vpd.put("var37", varOList.get(i).getString("MODIFY_TM"));	//37
				vpd.put("var38", varOList.get(i).get("DEL_FLG").toString());	//38
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

	@RequestMapping(value = "/getProductByCustom")
	@ResponseBody
	public Object getProductByCustom() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			Map<String, String> rsMap = SelectCache.getInstance().getProdSelectMap(pd.getString("CUSTOMER_ID"));
			if (rsMap == null || rsMap.isEmpty()) {
				return AppUtil.returnObject(pd, map);
			}
			map.putAll(rsMap);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
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
