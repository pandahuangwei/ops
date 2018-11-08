package com.hw.controller.instock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.base.CustomerService;
import com.hw.service.instock.StockMgrInService;
import com.hw.service.property.OrderPropertyService;
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
 * Date：2016-10-27
 */
@Controller
@RequestMapping(value="/stockmgrin")
public class StockMgrInController extends BaseController {
	
	String menuUrl = "stockmgrin/list.do"; //菜单地址(权限用)
	@Resource(name="stockmgrinService")
	private StockMgrInService stockmgrinService;
	@Resource(name="orderpropertyService")
	private OrderPropertyService orderpropertyService;
	@Resource(name="customerService")
	private CustomerService customerService;

	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(Page page) throws Exception{
		logBefore(logger, "新增StockMgrIn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("STOCKMGRIN_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", 0);	//删除
		pd.put("INSTOCK_STATE", WmsEnum.InstockState.NONE.getItemKey());
		pd.put("ASSIGNED_STATE", WmsEnum.AssignedState.NONE.getItemKey());
		pd.put("PUT_STATE",WmsEnum.PutState.NONE.getItemKey());
		pd.put("EA_ACTUAL", 0);
        pd.put("CONFIRM_STATE",0);

		String optEven = pd.getString("OPT_EVEN");
		stockmgrinService.save(pd);
		if (Const.OPT_EVEN_1.equals(optEven)) {
			try {
				List<Select> list = new ArrayList<>();


				pd = stockmgrinService.findById(pd);	//根据ID读取
				String customerId = pd.getString("CUSTOMER_ID");
				if (pd != null && StringUtil.isNotEmpty(customerId)) {
					//List<PageData> inOrderList = orderpropertyService.getPropertyINOrder(customerId);
					List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyINOrder(customerId);
					setPropertyValue(inOrderList,pd);
					mv.addObject("inOrderList",inOrderList);
				}

				setSelect(mv,list,customerId);

				//查询产品明细
				pd.put("STOCKDTLMGRIN_ID",null);
				//List<PageData> dtl = stockmgrinService.findDtl(pd);
				page.setPd(pd);
				List<PageData> dtl = stockmgrinService.findDtlName(page);
				mv.addObject("varList", dtl);

				mv.setViewName("instock/stockmgrin_edit");
				mv.addObject("msg", "edit");
				mv.addObject("pd", pd);
			} catch (Exception e) {
				logger.error(e.toString(), e);
			}
		} else {
			mv.addObject("msg","success");
			mv.setViewName("save_result");
		}

		return mv;
	}


	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除StockMgrIn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			stockmgrinService.delete(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}

	@RequestMapping(value="/saveConfirm")
	public ModelAndView saveConfirm(Page page) throws Exception{
		logBefore(logger, "收货确认");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String optEven = pd.getString("OPT_EVEN");

		if(Const.OPT_EVEN_3.equals(optEven)) {
			stockmgrinService.saveConfirm(pd);
		}


		if(Const.OPT_EVEN_2.equals(optEven)){
			mv.addObject("msg","success");
			mv.setViewName("save_result");
			return mv;
		}

		try {
			List<Select> list = new ArrayList<>();


			pd = stockmgrinService.findById(pd);	//根据ID读取
			String customerId = pd.getString("CUSTOMER_ID");
			if (pd != null && StringUtil.isNotEmpty(customerId)) {
				//List<PageData> inOrderList = orderpropertyService.getPropertyINOrder(customerId);
				List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyINOrder(customerId);
				setPropertyValue(inOrderList,pd);
				mv.addObject("inOrderList",inOrderList);
			}

			setSelect(mv,list,customerId);

			String INSTOCK_STATE = pd == null?null:pd.getString("CONFIRM_STATE");
			if (Const.ZERO_CH.equals(INSTOCK_STATE) || StringUtil.isEmpty(INSTOCK_STATE)) {
				pd.put("MODIFY_FLG","1");
			} else {
				pd.put("MODIFY_FLG","0");
			}

			//查询产品明细
			pd.put("STOCKDTLMGRIN_ID",null);
			//List<PageData> dtl = stockmgrinService.findDtl(pd);
			page.setPd(pd);
			List<PageData> dtl = stockmgrinService.findDtlName(page);

			mv.addObject("varList", dtl);

			mv.setViewName("instock/stockmgrin_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return mv;
	}

	/**
	 * 修改
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit(Page page) throws Exception{
		logBefore(logger, "修改StockMgrIn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		String optEven = pd.getString("OPT_EVEN");

		if(StringUtil.isNotEmpty(optEven)) {
			stockmgrinService.edit(pd);
		}


		if(Const.OPT_EVEN_2.equals(optEven)){
			mv.addObject("msg","success");
			mv.setViewName("save_result");
		    return mv;
		}

		try {
			List<Select> list = new ArrayList<>();


			pd = stockmgrinService.findById(pd);	//根据ID读取
			String customerId = pd.getString("CUSTOMER_ID");
			if (pd != null && StringUtil.isNotEmpty(customerId)) {
				//List<PageData> inOrderList = orderpropertyService.getPropertyINOrder(customerId);
				List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyINOrder(customerId);
				setPropertyValue(inOrderList,pd);
				mv.addObject("inOrderList",inOrderList);
			}
			setSelect(mv,list,customerId);

			//查询产品明细
			pd.put("STOCKDTLMGRIN_ID",null);
			//List<PageData> dtl = stockmgrinService.findDtl(pd);
			page.setPd(pd);
			List<PageData> dtl = stockmgrinService.findDtlName(page);

			mv.addObject("varList", dtl);

			List<String> showItemList = BaseInfoCache.getInstance().getInStockBatchColum(pd.getString("CUSTOMER_ID"));
			List<String> showItemNameList = BaseInfoCache.getInstance().getInStockBatchColumNames(pd.getString("CUSTOMER_ID"));

			mv.addObject("showItemList", showItemList);
			mv.addObject("showItemNameList", showItemNameList);

			mv.setViewName("instock/stockmgrin_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表StockMgrIn");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<Select> list = new ArrayList<>();
			setMutiple(pd);
			setSelect(mv,list,null);
			page.setPd(pd);

			String customerId = pd.getString("CUSTOMER_ID");
			if (StringUtil.isBlank(customerId)) {
				customerId =SelectCache.getInstance().getAllCustomer().get(0).getId();
				pd.put("CUSTOMER_ID",customerId);
			}

			List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
			mv.addObject("allProductList",allProduct);


			List<PageData>	varList = stockmgrinService.list(page);	//列出StockMgrIn列表

			setMutipleAfter(pd);

			mv.setViewName("instock/stockmgrin_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	//设值多选框 入库状态，码放状态、分配状态 INSTOCK_TYPE_S,INSTOCK_STATE_S PUT_STATE_S ASSIGNED_STATE_S
	private void setMutiple(PageData pd) {
		pd.put("INSTOCK_TYPE_S1",pd.getString("INSTOCK_TYPE_S"));
		pd.put("INSTOCK_STATE_S1",pd.getString("INSTOCK_STATE_S"));
		pd.put("PUT_STATE_S1",pd.getString("PUT_STATE_S"));
		pd.put("ASSIGNED_STATE_S1",pd.getString("ASSIGNED_STATE_S"));

		pd.put("INSTOCK_TYPE_S",VaryUtils.genListOrNull(pd.getString("INSTOCK_TYPE_S")));
		pd.put("INSTOCK_STATE_S",VaryUtils.genListOrNull(pd.getString("INSTOCK_STATE_S")));
		pd.put("PUT_STATE_S",VaryUtils.genListOrNull(pd.getString("PUT_STATE_S")));
		pd.put("ASSIGNED_STATE_S",VaryUtils.genListOrNull(pd.getString("ASSIGNED_STATE_S")));
	}

	private void setMutipleAfter(PageData pd) {
		pd.put("INSTOCK_TYPE_S",pd.getString("INSTOCK_TYPE_S1"));
		pd.put("INSTOCK_STATE_S",pd.getString("INSTOCK_STATE_S1"));
		pd.put("PUT_STATE_S",pd.getString("PUT_STATE_S1"));
		pd.put("ASSIGNED_STATE_S",pd.getString("ASSIGNED_STATE_S1"));
	}

	private void setSelect(ModelAndView mv,List<Select> list,String customerId) {

		List<Select> customerList = SelectCache.getInstance().getAllCustomer();
       //入库类型\入库状态\码放状态\优先级
		List<Select> stockInTypeList = SelectCache.getInstance().getStockInType();
		List<Select> stockInStateList = SelectCache.getInstance().getStockInState();
		List<Select> putStateList = SelectCache.getInstance().getPutState();
		List<Select> priorityLevelList = SelectCache.getInstance().getPriorityLevel();
		List<Select> assignedList = SelectCache.getInstance().getAssigned();


		mv.addObject("customerList",customerList);
		mv.addObject("stockInTypeList",stockInTypeList);
		mv.addObject("stockInStateList",stockInStateList);
		mv.addObject("putStateList",putStateList);
		mv.addObject("priorityLevelList",priorityLevelList);
		mv.addObject("assignedList",assignedList);
		List<Select> productList = SelectCache.getInstance().getAllProduct(customerId);
		List<Select> productTypeList = SelectCache.getInstance().getProductType();
		List<Select> packRuleList = SelectCache.getInstance().getPackRule();
		List<Select> storageList = SelectCache.getInstance().getAllStorage();
		List<Select> locatorList = SelectCache.getInstance().getLocator(null);
		List<Select> currencyTypeList = SelectCache.getInstance().getCurrencyType();
		List<Select> carTypeList = SelectCache.getInstance().getCarType();
		List<Select> packUnitList = SelectCache.getInstance().getPackUnit();
		//承运人
		List<Select> tpHaulierList = SelectCache.getInstance().getTpHaulier();
		List<Select> freeRejectList = SelectCache.getInstance().getFreeReject();


		mv.addObject("productList",productList);
		mv.addObject("productTypeList",productTypeList);
		mv.addObject("packRuleList",packRuleList);
		mv.addObject("storageList",storageList);
		mv.addObject("locatorList",locatorList);
		mv.addObject("currencyTypeList",currencyTypeList);
		mv.addObject("carTypeList",carTypeList);
		mv.addObject("packUnitList",packUnitList);
		mv.addObject("tpHaulierList",tpHaulierList);
		mv.addObject("freeRejectList",freeRejectList);

		if (list != null) {
			list.addAll(customerList);
		}
	}

	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增StockMgrIn页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<Select> customerList = new ArrayList<>();
			setSelect(mv,customerList,null);
			//获取客户列表的第一次个值
			if (customerList != null && customerList.size() >=1) {
				String customerId = customerList.get(0).getId();
				//List<PageData> inOrderList = orderpropertyService.getPropertyINOrder(customerId);
				List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyINOrder(customerId);
				mv.addObject("inOrderList",inOrderList);
			}
			pd.put("PRE_INSTOCK_DATE",Tools.date2StrYYMMDD(new Date()));
			pd.put("MODIFY_FLG","1");

			mv.setViewName("instock/stockmgrin_edit");
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
	public ModelAndView goEdit(Page page){
		logBefore(logger, "去修改StockMgrIn页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<Select> list = new ArrayList<>();



			pd = stockmgrinService.findById(pd);	//根据ID读取
			String customerId = pd.getString("CUSTOMER_ID");
			if (pd != null && StringUtil.isNotEmpty(customerId)) {
				//List<PageData> inOrderList = orderpropertyService.getPropertyINOrder(customerId);
				List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyINOrder(customerId);
				setPropertyValue(inOrderList,pd);
				mv.addObject("inOrderList",inOrderList);

				List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
				mv.addObject("allProductList",allProduct);

			}
			setSelect(mv,list,customerId);

			String INSTOCK_STATE = pd == null?null:pd.getString("CONFIRM_STATE");
			if (Const.ZERO_CH.equals(INSTOCK_STATE) || StringUtil.isEmpty(INSTOCK_STATE)) {
				pd.put("MODIFY_FLG","1");
			} else {
				pd.put("MODIFY_FLG","0");
			}


			//查询产品明细
			pd.put("STOCKDTLMGRIN_ID",null);
			//List<PageData> dtl = stockmgrinService.findDtl(pd);
			page.setPd(pd);
			List<PageData> dtl = stockmgrinService.findDtlName(page);
			mv.addObject("varList", dtl);
			List<String> showItemList = BaseInfoCache.getInstance().getInStockBatchColum(pd.getString("CUSTOMER_ID"));
			List<String> showItemNameList = BaseInfoCache.getInstance().getInStockBatchColumNames(pd.getString("CUSTOMER_ID"));

			mv.addObject("showItemList", showItemList);
			mv.addObject("showItemNameList", showItemNameList);

			mv.setViewName("instock/stockmgrin_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	

	private void setPropertyValue(List<PageData> inOrderList,PageData pd) {
		for (PageData e : inOrderList) {
			String propertyColum = e.getString("PROPERTY_COLUM");
			String value = pd.getString(propertyColum);
			if (StringUtil.isEmpty(value)) {
				e.put("PROPERTY_VALUE",Const.EMPTY_CH);
			} else {
				e.put("PROPERTY_VALUE",value);
			}
		}
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除StockMgrIn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				stockmgrinService.deleteAll(ArrayDATA_IDS);
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
	 * 批量删除
	 */
	@RequestMapping(value="/deleteDtlAll")
	@ResponseBody
	public Object deleteDtlAll() {
		logBefore(logger, "批量删除StockDtlMgrIn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				stockmgrinService.deleteDtlAll(ArrayDATA_IDS);
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
		logBefore(logger, "导出StockMgrIn到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("进货单编号入货单编号");	//1
			titles.add("客户");	//2
			titles.add("入库类型");	//3
			titles.add("预计入库日期");	//4
			titles.add("实际入库日期");	//5
			titles.add("收货状态");	//6
			titles.add("码放状态");	//7
			titles.add("总毛重");	//8
			titles.add("总净重");	//9
			titles.add("总体积");	//10
			titles.add("总价");	//11
			titles.add("总期望EA数");	//12
			titles.add("优先级");	//13
			titles.add("订单号");	//14
			titles.add("费用录入已完成");	//15
			titles.add("备注");	//16
			titles.add("创建人");	//17
			titles.add("创建时间");	//18
			titles.add("修改人");	//19
			titles.add("修改时间");	//20
			titles.add("删除");	//21
			dataMap.put("titles", titles);
			List<PageData> varOList = stockmgrinService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("INSTOCK_NO"));	//1
				vpd.put("var2", varOList.get(i).getString("CUSTOMER_ID"));	//2
				vpd.put("var3", varOList.get(i).getString("INSTOCK_TYPE"));	//3
				vpd.put("var4", varOList.get(i).getString("PRE_INSTOCK_DATE"));	//4
				vpd.put("var5", varOList.get(i).getString("REAL_INSTOCK_DATE"));	//5
				vpd.put("var6", varOList.get(i).getString("INSTOCK_STATE"));	//6
				vpd.put("var7", varOList.get(i).getString("PUT_STATE"));	//7
				vpd.put("var8", varOList.get(i).getString("TOTAL_WEIGHT"));	//8
				vpd.put("var9", varOList.get(i).getString("TOTAL_SUTTLE"));	//9
				vpd.put("var10", varOList.get(i).getString("TOTAL_VOLUME"));	//10
				vpd.put("var11", varOList.get(i).getString("TOTAL_PRICE"));	//11
				vpd.put("var12", varOList.get(i).getString("TOTAL_EA"));	//12
				vpd.put("var13", varOList.get(i).getString("PRIORITY_LEVEL"));	//13
				vpd.put("var14", varOList.get(i).getString("ORDER_NO"));	//14
				vpd.put("var15", varOList.get(i).getString("COST_STATE"));	//15
				vpd.put("var16", varOList.get(i).getString("MEMO"));	//16
				vpd.put("var17", varOList.get(i).getString("CREATE_EMP"));	//17
				vpd.put("var18", varOList.get(i).getString("CREATE_TM"));	//18
				vpd.put("var19", varOList.get(i).getString("MODIFY_EMP"));	//19
				vpd.put("var20", varOList.get(i).getString("MODIFY_TM"));	//20
				vpd.put("var21", varOList.get(i).getString("DEL_FLG"));	//21
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

	@RequestMapping(value = "/getInStockNo")
	@ResponseBody
	public Object getInStockNo() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String customerId = pd.getString("CUSTOMER_ID");
			String stockNo = customerService.getInStockNo(customerId);
			if (stockNo == null) {
				return AppUtil.returnObject(pd, map);
			}

			map.put("StockNo",stockNo);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}


	@RequestMapping(value = "/getOrderProperty")
	@ResponseBody
	public Object getOrderProperty() {
		Map<String,List<PageData>> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String customerId = pd.getString("CUSTOMER_ID");
			//List<PageData> inOrderList = orderpropertyService.getPropertyINOrder(customerId);
			List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyINOrder(customerId);
			if (inOrderList == null || inOrderList.size() ==0) {
				return AppUtil.returnObject(pd, map);
			}

			map.put("inOrderList",inOrderList);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}
	//选择产品，需要带出的属性
	@RequestMapping(value = "/getProdProperty")
	@ResponseBody
	public Object getProdProperty() {
		Map<String,List<PageData>> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String productId = pd.getString("PRODUCT_ID");
			List<PageData> productList = SelectCache.getInstance().getProductList(productId);

			if (productList == null || productList.isEmpty()) {
				return AppUtil.returnObject(pd, map);
			}
			map.put("productList",productList);

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}

	@RequestMapping(value = "/getPackRule")
	@ResponseBody
	public Object getPackRule() {
		Map<String,List<PageData>> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String packRuleId = pd.getString("PACK_RULE");
			List<PageData> packRuleList = SelectCache.getInstance().getPackRuleListById(packRuleId);

			if (packRuleList == null || packRuleList.isEmpty()) {
				return AppUtil.returnObject(pd, map);
			}
			map.put("packRuleList",packRuleList);
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

	/*-------明细操作-------------------------*/

	@RequestMapping(value="/goAddDtl")
	public ModelAndView goAddDtl(){
		logBefore(logger, "去新增StockDtlMgrIn页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<Select> customerList = new ArrayList<>();


			int ROW_NO = pd.getInteger("ROW_NO");
			if(ROW_NO > 1) {
				int ROW_NO2 = stockmgrinService.findRowNo(pd);
				pd.put("ROW_NO",ROW_NO2);
			}

			//获取客户列表的第一次个值
			String customerId = pd.getString("CUSTOMER_ID");
			if (pd != null && StringUtil.isNotEmpty(customerId)) {
				//List<PageData> inOrderList = orderpropertyService.getPropertyINBatch(customerId);
				List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyINBatch(customerId);
				mv.addObject("inOrderList",inOrderList);

				List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
				mv.addObject("allProductList",allProduct);
			}

			setSelect(mv,customerList,customerId);

			mv.setViewName("instock/stockdtlmgrin_edit");
			mv.addObject("msg", "saveDtl");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEditDtl")
	public ModelAndView goEditDtl(){
		logBefore(logger, "去修改StockDtlMgrIn页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<Select> list = new ArrayList<>();

            String MODIFY_FLG = pd.getString("MODIFY_FLG");
			pd = stockmgrinService.findDtlById(pd);	//根据ID读取

			pd.put("MODIFY_FLG",MODIFY_FLG);
			String customerId = pd.getString("CUSTOMER_ID");
			if (pd != null && StringUtil.isNotEmpty(customerId)) {
				//List<PageData> inOrderList = orderpropertyService.getPropertyINBatch(customerId);
				List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyINBatch(customerId);
				setPropertyValue(inOrderList,pd);
				mv.addObject("inOrderList",inOrderList);

				List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
				mv.addObject("allProductList",allProduct);
			}
			setSelect(mv,list,customerId);

			String packRule = pd.getString("PACK_RULE");
			if(StringUtil.isNotEmpty(packRule)) {
				List<PageData> packRuleList = SelectCache.getInstance().getPackRuleListById(packRule);
				if(packRuleList != null &&  !packRuleList.isEmpty()) {
					PageData eaPd = packRuleList.get(0);
					setEaPd(pd,eaPd);
				}

			}

			String prodId =  pd.getString("PRODUCT_ID");
			PageData product = SelectCache.getInstance().getProduct(prodId);
			setEaProd(pd,product);

			mv.setViewName("instock/stockdtlmgrin_edit");
			mv.addObject("msg", "editDtl");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}
	private void setEaProd(PageData rs,PageData src) {
		rs.put("WEIGHT_UNIT_TYPE", src.getString("WEIGHT_UNIT"));
		rs.put("SUTTLE_UNIT_TYPE",src.getString("SUTTLE_UNIT"));
		rs.put("VOLUME_UNIT_TYPE", src.getString("VOLUME_UNIT"));
		//产品配置的毛重，净重
		rs.put("UNIT_SUTTLE", src.getString("UNIT_SUTTLE"));
		rs.put("UNIT_WEIGHT", src.getString("UNIT_WEIGHT"));

		rs.put("TOTAL_WEIGHT_1", src.getString("TOTAL_WEIGHT"));
		rs.put("TOTAL_SUTTLE_1", src.getString("TOTAL_SUTTLE"));
		rs.put("UNIT_PRICE_1", src.getString("UNIT_PRICE"));

	}

	private void setEaPd(PageData rs,PageData src) {
		//B.EA_NUM,B.INPACK_NUM,B.BOX_NUM,B.PALLET_NUM ,B.OTHER_NUM,
			//	B.EA_IN,B.INPACK_IN,B.BOX_IN,B.PALLET_IN ,B.OTHER_IN,
		rs.put("EA_NUM_1",src.getString("EA_NUM"));
		rs.put("INPACK_NUM_1",src.getString("INPACK_NUM"));
		rs.put("BOX_NUM_1",src.getString("BOX_NUM"));
		rs.put("PALLET_NUM_1",src.getString("PALLET_NUM"));
		rs.put("OTHER_NUM_1",src.getString("OTHER_NUM"));

		rs.put("EA_IN_1",src.getString("EA_IN"));
		rs.put("INPACK_IN_1",src.getString("INPACK_IN"));
		rs.put("BOX_IN_1",src.getString("BOX_IN"));
		rs.put("PALLET_IN_1",src.getString("PALLET_IN"));
		rs.put("OTHER_IN_1",src.getString("OTHER_IN"));
	}

	/*
	<input type="hidden" name="EA_IN_1" id="EA_IN_1" value="0"/>
	<input type="hidden" name="INPACK_IN_1" id="INPACK_IN_1" value="0"/>
	<input type="hidden" name="BOX_IN_1" id="BOX_IN_1" value="0"/>
	<input type="hidden" name="PALLET_IN_1" id="PALLET_IN_1" value="0"/>
	<input type="hidden" name="OTHER_IN_1" id="OTHER_IN_1" value="0"/>*/

	/**
	 * 新增
	 */
	@RequestMapping(value="/saveDtl")
	public ModelAndView saveDtl() throws Exception{
		logBefore(logger, "新增StockDtlMgrIn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();


		pd.put("STOCKDTLMGRIN_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", 0);	//删除
		pd.put("IMP_FLG","0");
		pd.put("EA_ACTUAL", 0);
		pd.put("RECEI_FLG", 0);
		pd.put("RECEIPT_STATE", WmsEnum.InstockState.NONE.getItemKey());
		pd.put("DISTR_STATE", WmsEnum.AssignedState.NONE.getItemKey());
		pd.put("PUT_STATUS", WmsEnum.PutState.NONE.getItemKey());
		String customerId = pd.getString("CUSTOMER_ID");

		stockmgrinService.saveDtl(pd);

		setSelect(mv,null,customerId);
		//查询产品明细

		/*pd.put("STOCKDTLMGRIN_ID",null);
		List<PageData> dtl = stockmgrinService.findDtl(pd);
		mv.addObject("varList", dtl);*/

		//pd = stockmgrinService.findById(pd);	//根据ID读取
		//mv.setViewName("instock/stockmgrin_edit");
		mv.setViewName("save_result");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/deleteDtl")
	public void deleteDtl(PrintWriter out){
		logBefore(logger, "删除StockMgrIn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			stockmgrinService.deleteDtl(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}

	}


	/**
	 * 修改
	 */
	@RequestMapping(value="/editDtl")
	public ModelAndView editDtl() throws Exception{
		logBefore(logger, "修改StockMgrIn");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

		stockmgrinService.editDtl(pd);
		/*mv.addObject("msg","success");
		mv.setViewName("save_result");*/

		mv.setViewName("save_result");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);

		return mv;
	}


	/**
	 * 判断产品是否存在
	 */
	@RequestMapping(value = "/hadProd")
	@ResponseBody
	public Object hadProd() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			PageData hadPd = stockmgrinService.findDtlByPidAndProdId(pd);
			if (hadPd != null) {
				String rowNo = hadPd.getString("ROW_NO");
				map.put("rowno", rowNo);
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "/getProductList")
	@ResponseBody
	public Object getProductList() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String customerId = pd.getString("CUSTOMER_ID");
			List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
			if (allProduct == null || allProduct.size() == 0) {
				return AppUtil.returnObject(pd, map);
			}
			for (Select p : allProduct) {
				map.put(p.getId(),p.getValue());
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}


	@RequestMapping(value = "/getLocatorList")
	@ResponseBody
	public Object getLocatorList() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String storageId = pd.getString("STORAGE_ID");
			List<Select> allProduct = SelectCache.getInstance().getLocator(storageId);
			if (allProduct == null || allProduct.size() == 0) {
				return AppUtil.returnObject(pd, map);
			}
			for (Select p : allProduct) {
				map.put(p.getId(),p.getValue());
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}


	@RequestMapping(value = "/getProdDtl")
	@ResponseBody
	public Object getProdDtl(Page page) {
		Map<String,List<PageData>> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			pd.put("STOCKDTLMGRIN_ID",null);
			//List<PageData> dtl = stockmgrinService.findDtl(pd);
			page.setPd(pd);
			List<PageData> dtl = stockmgrinService.findDtlName(page);

			if (dtl == null || dtl.size() ==0) {
				return AppUtil.returnObject(pd, map);
			}

			/*List<PageData> customerList = SelectCache.getInstance().getAllCustomerPd();
			map.put("customerList",customerList);*/

			map.put("varList",dtl);

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
		mv.setViewName("instock/stockdtlmgrin_uploadexl");
		return mv;
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Instock.xls", "Instock.xls");
	}

	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file,
								  @RequestParam(value = "STOCKMGRIN_ID", required = false) String stockMgrinId,
								  @RequestParam(value = "CUSTOMER_ID", required = false) String customerId
	) throws Exception {
		ModelAndView mv = this.getModelAndView();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}

		if (StringUtil.isEmpty(stockMgrinId) || StringUtil.isEmpty(customerId)) {
			return null;
		}

		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "inExcel");                            //执行上传

			PageData pd = new PageData();
			pd.put("STOCKMGRIN_ID",stockMgrinId);
			pd.put("CUSTOMER_ID",customerId);

			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);    //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			Map<String, PageData> productMap = SelectCache.getInstance().getProductMap(customerId);
			Map<String, PageData> productUpCaseMap = SelectCache.getInstance().toUpCaseKey(productMap);
			Set<String> prodCodeSet = SelectCache.getInstance().getProductSet(productMap);

			List<PageData> auditRsList = auditData(customerId, listPd, prodCodeSet);
			//如果校验数据结果不为空，说明excel中填入的数据有问题，不做导入处理
			if (auditRsList != null && !auditRsList.isEmpty()) {
				pd.put("IMP_RS","数据存在异常,请修改正确后重新导入...");

				mv.addObject("pd", pd);
				mv.addObject("xlsList", auditRsList);
				mv.addObject("msg", "success");
				mv.setViewName("instock/stockdtlmgrin_uploadexl");
				return mv;
			}


			List<PageData> filterList = megreList(listPd);

			//已经存在的明细
			List<PageData> hadList = stockmgrinService.findDtl(pd);
			//List<PageData> inBatchList = orderpropertyService.getPropertyINBatch(customerId);
			List<PageData> inBatchList = BaseInfoCache.getInstance().getPropertyINBatch(customerId);
			//需要特殊处理的批次属性字段
			String colum = "HOLD";
			PageData pageData = getPageDate(inBatchList,colum);
			String colum2 = "";
			if(pageData != null) {
				Map<Integer, String> sortColuMap = WmsEnum.OrderProperty.sortColuMap;
				colum2 = sortColuMap.get(pageData.getString("SRC_SORT"));
			}


			//起始行号
			int rowNo =(hadList == null || hadList.isEmpty() ? 0:hadList.size())+1;
			//新插入的列表、需要更新的列表
			List<PageData> insertList = new ArrayList<>();
			List<PageData> updateList = new ArrayList<>();
			for(PageData et1 : filterList) {
				boolean isExists = false;
				String newProd = et1.getString("PROD");
				PageData insertPd = productMap.get(newProd);
				if (insertPd == null) {
					insertPd = productUpCaseMap.get(StringUtil.trimSpace(newProd));
				}
				String newProdID = insertPd.getString("PRODUCT_ID");
				String holdValue = et1.getString("HOLD");

				//如果该料号存在明细中，则需update
				for (PageData et2: hadList) {
					String hadProdId = et2.getString("PRODUCT_ID");
					if (newProdID.equals(hadProdId)) {
						isExists = true;
						int total = et2.getInteger("EA_EA") + et1.getInteger("EA");
						et2.put("EA_EA",total);
						double volume = Double.parseDouble(et2.getString("UNIT_VOLUME"))*total;
						et2.put("TOTAL_VOLUME",volume);
						double price = Double.parseDouble(et2.getString("UNIT_PRICE"))*total;
						et2.put("TOTAL_PRICE",price);

						//如果存在hold的批次属性则往其设值
						if (StringUtil.isNotEmpty(colum2)) {
							et2.put(colum2,holdValue);
						}
						et2.put("MODIFY_EMP", getCurUserName());	//修改人
						et2.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
						updateList.add(et2);
					}

				}

				//如果该料号不存在明细中，则需insert
				if(!isExists) {
					insertPd.put("LONG_UT",insertPd.get("LONG_UNIT"));
					insertPd.put("WIDE_UT",insertPd.get("WIDE_UNIT"));
					insertPd.put("HIGH_UT",insertPd.get("HIGH_UNIT"));
					insertPd.put("UNIT_VOLUME",insertPd.get("UNIT_VOLUME"));

					insertPd.put("TOTAL_WEIGHT",insertPd.get("TOTAL_WEIGHT"));
					insertPd.put("TOTAL_SUTTLE",insertPd.get("TOTAL_SUTTLE"));
					insertPd.put("TOTAL_VOLUME",insertPd.get("TOTAL_VOLUME"));

					insertPd.put("PRODUCT_TYPE",insertPd.get("PRODUCT_TYPE"));
					insertPd.put("RULE_PACK",insertPd.get("RULE_PACK"));
					insertPd.put("UNIT_PRICE",insertPd.get("UNIT_PRICE"));

						/*insertPd.put("OT_EA",insertPd.get("OTHER_NUM"));
						insertPd.put("PALLET_EA",insertPd.get("PALLET_NUM"));
						insertPd.put("BOX_EA",insertPd.get("BOX_NUM"));
						insertPd.put("INPACK_EA",insertPd.get("INPACK_NUM"));*/
					insertPd.put("EA_EA",et1.getInteger("EA"));

						/*{STOCKMGRIN_ID}, #{}, #{CUSTOMER_ID}, #{PRODUCT_ID}*/

					insertPd.put("STOCKMGRIN_ID",stockMgrinId);
					insertPd.put("ROW_NO",rowNo++);
					insertPd.put("CUSTOMER_ID",customerId);
					insertPd.put("STOCKDTLMGRIN_ID",UuidUtil.get32UUID());
					insertPd.put("DEL_FLG","0");
					insertPd.put("IMP_FLG","1");
					insertPd.put("RECEI_FLG","0");
					insertPd.put("EA_ACTUAL",0);


					insertPd.put("MEMO", "用户导入");
					insertPd.put("CREATE_EMP", getCurUserName());	//创建人
					insertPd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
					insertPd.put("MODIFY_EMP", getCurUserName());	//修改人
					insertPd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

					insertPd.put("RECEIPT_STATE", WmsEnum.InstockState.NONE.getItemKey());
					insertPd.put("DISTR_STATE", WmsEnum.AssignedState.NONE.getItemKey());
					insertPd.put("PUT_STATUS", WmsEnum.PutState.NONE.getItemKey());
					insertList.add(insertPd);
				}
			}

			for (PageData e :insertList) {
				stockmgrinService.saveDtl(e);
			}

			for (PageData e :updateList) {
				stockmgrinService.edit(e);
			}

            /*存入数据库操作======================================*/
			pd.put("IMP_SUCC","数据成功导入...");
			pd.put("success","success");
			mv.addObject("pd", pd);
			mv.addObject("xlsList", new ArrayList<String>());
			mv.addObject("msg", "success");
		}
		mv.setViewName("instock/stockdtlmgrin_uploadexl");
		//mv.setViewName("save_result");
		return mv;
	}

	private List<PageData> auditData(String customerId, List<PageData> listPd,Set<String> prodCodeList) {
		List<PageData> rsList = new ArrayList<>();
		Set<String> set = new HashSet<>();
		//扫描料号-实际料号
		Map<String, String> sacanProdMap = BaseInfoCache.getInstance().getScanCodeProdCodeMap(customerId);

		for (int i = 0; i < listPd.size(); i++) {
			String PROD = listPd.get(i).getString("var0");
			String EA = listPd.get(i).getString("var1");
			//扫描料号对应的实际料号
			String actulProd = sacanProdMap.get(PROD);
			if(StringUtil.isEmpty(actulProd) && !prodCodeList.contains(PROD)) {
				PageData pd = new PageData();
				pd.put("PROD",PROD);
				pd.put("EA_NUM","");
				pd.put("ADD_FLG",1);
				pd.put("MEMO","料号在对照表中不存在,并且该客户的实际料号中也不存在.");
				rsList.add(pd);
				continue;
			}

			actulProd = StringUtil.isEmpty(actulProd)?PROD:actulProd;
			if (set.contains(actulProd)) {
				PageData pd = new PageData();
				if(PROD.equals(actulProd)) {
					pd.put("PROD",PROD);
					pd.put("EA_NUM","");
					pd.put("ADD_FLG",0);
					pd.put("MEMO","料号重复...");
				} else {
					pd.put("PROD",PROD+"("+actulProd+")");
					pd.put("EA_NUM","");
					pd.put("ADD_FLG",0);
					pd.put("MEMO","料号对应的实际料号重复...");
				}
				rsList.add(pd);
			} else {
				set.add(actulProd);
			}

			if (!prodCodeList.contains(actulProd)) {
				PageData pd = new PageData();
				pd.put("PROD",PROD);
				pd.put("EA_NUM",EA);
				pd.put("ADD_FLG",0);
				pd.put("MEMO","料号不属于该客户");
				rsList.add(pd);
				continue;
			}

			if (!isNum(EA)) {
				PageData pd = new PageData();
				pd.put("PROD",PROD);
				pd.put("EA_NUM",EA);
				pd.put("ADD_FLG",0);
				pd.put("MEMO","数量(EA)填写的不是数字");
				rsList.add(pd);
				continue;
			}

			listPd.get(i).put("PROD",actulProd);
		}
		return rsList;
	}

	//合并重复记录
	private List<PageData> megreList(List<PageData> list) {
		Set<String> prodRpSet = new HashSet<>();
		List<PageData> filterList = filter(list,prodRpSet);

		if (prodRpSet.isEmpty()) {
			return filterList;
		} else {
			return megreRepeat(filterList,prodRpSet);
		}
	}



	//合并重复记录
	private List<PageData> megreRepeat(List<PageData> list,Set<String> set) {
		List<PageData> lst = new ArrayList<>();
		List<PageData> rpLst = new ArrayList<>();
		for(PageData e : list) {
			String prod = e.getString("PROD");
			if (set.contains(prod)) {
				boolean isExist = false;
				for(int i=0,size = rpLst.size();i<size;i++) {
					PageData pd = rpLst.get(i);
					String prod1 = pd.getString("PROD");

					if(prod1.equals(prod)) {
						isExist = true;
						int totalEA = e.getInteger("EA") + pd.getInteger("EA");
						pd.put("EA",totalEA);
						String hold =  pd.getString("HOLD");
						if (StringUtil.isEmpty(hold)) {
							pd.put("HOLD",e.getString("HOLD"));
						}
					}
				}
				if (!isExist) {
					rpLst.add(e);
				}

			} else {
				lst.add(e);
			}
		}
		lst.addAll(rpLst);
		return lst;
	}


	private List<PageData> filter(List<PageData> list,Set<String> prodRpSet) {
		if (list == null || list.isEmpty()) {
			return new ArrayList<>();
		}
		Set<String> prodSet = new HashSet<>();
		List<PageData> lst = new ArrayList<>();

		for (int i = 0,size = list.size(); i < size; i++) {
			PageData pd = new PageData();
			String prodCode = list.get(i).getString("var0");
			String ea = list.get(i).getString("var1");
			String hold = list.get(i).getString("var2");

			pd.put("PROD",prodCode);
			pd.put("EA",ea);
			pd.put("HOLD",hold);
			lst.add(pd);
			if (prodSet.contains(prodCode)) {
				prodRpSet.add(prodCode);
			} else {
				prodSet.add(prodCode);
			}
		}
		return lst;
	}

	private PageData getPageDate(List<PageData> inBatchList,String colum) {
		if (inBatchList == null || inBatchList.isEmpty()) {
			return new PageData();
		}
		for (PageData e :inBatchList) {

			if(colum.equalsIgnoreCase(e.getString("PROPERTY_KEY"))) {
				return e;
			}
		}
		return null;
	}

	private boolean isNum(String EA) {
		try {
			Integer.parseInt(EA);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
