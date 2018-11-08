package com.hw.controller.instock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.instock.AssignOptService;
import com.hw.service.instock.ReceivOptService;
import com.hw.service.instock.StockMgrInService;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
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

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 分配
 * Created：panda.HuangWei
 * Date：2016-11-23
 */
@Controller
@RequestMapping(value="/assignopt")
public class AssignOptController extends BaseController {
	
	String menuUrl = "assignopt/list.do"; //菜单地址(权限用)
	@Resource(name="assignoptService")
	private AssignOptService assignoptService;

	@Resource(name="stockmgrinService")
	private StockMgrInService stockmgrinService;

	@RequestMapping(value="/saveHead")
	public ModelAndView saveHead(Page page) throws Exception{
		logBefore(logger, "按单头分配");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除标志
		//操作类型：1 自动分配 2 手工分配 3 缺省分配
		String optEven = pd.getString("ASSIGN_TYPE");
		setSelect(mv,pd);

		String ids = pd.getString("INSTOCK_NOS");
		String[] noArr = StringUtils.split(ids,Const.SPLIT_COMMA);

		if(StringUtil.isNotEmpty(optEven)) {
			assignoptService.saveAssignHead(pd);
		}

		pd.put("INSTOCK_NO",noArr);
		page.setPd(pd);
		List<PageData> srcList = new ArrayList<>();
		List<PageData> assignList = new ArrayList<>();
		getListByOpt(srcList,assignList,page);

		mv.addObject("srcList", srcList);
		mv.addObject("varList", assignList);

		mv.setViewName("instock/assignopt_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

		return mv;
	}

	@RequestMapping(value="/save")
	public ModelAndView save(Page page) throws Exception{
		logBefore(logger, "新增assignopt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除标志
        //操作类型：1 自动分配 2 手工分配 3 缺省分配
        String optEven = pd.getString("ASSIGN_TYPE");
		setSelect(mv,pd);
		//如果为空，则是翻页，不需要做其他操作
		/*if (StringUtil.isEmpty(optEven)) {
			String ids = pd.getString("INSTOCK_NOS");
			String[] noArr = StringUtils.split(ids,Const.SPLIT_COMMA);
			pd.put("INSTOCK_NO",noArr);
			page.setPd(pd);
			List<PageData> varList = getListByOpt(page);
			mv.addObject("varList", varList);
			mv.setViewName("instock/assignopt_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			return mv;
		}*/

		String ids = pd.getString("INSTOCK_NOS");
		String[] noArr = StringUtils.split(ids,Const.SPLIT_COMMA);

		if(StringUtil.isNotEmpty(optEven)) {
			assignoptService.saveAssign(pd);
		}

		pd.put("INSTOCK_NO",noArr);
		page.setPd(pd);
		List<PageData> srcList = new ArrayList<>();
		List<PageData> assignList = new ArrayList<>();
		getListByOpt(srcList,assignList,page);

		mv.addObject("srcList", srcList);
		mv.addObject("varList", assignList);

		mv.setViewName("instock/assignopt_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

		return mv;
	}
	/**
	 * 去分配页面
	 */
	@RequestMapping(value="/goAssign")
	public ModelAndView goAssign(Page page){
		logBefore(logger, "去分配assignopt页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("INSTOCK_NO",VaryUtils.genListOrNull(pd.getString("INSTOCK_NOS")));
			page.setPd(pd);
			setSelect(mv,pd);

			String optEven = page.getPd().getString("ASSIGN_TYPE");
			if(Const.OPT_EVEN_1.equals(optEven)) {
				List<PageData> locators = assignoptService.findStockLocator();
				if(locators== null || locators.isEmpty()) {
					pd.put("AUTO_FLG","没有存货区,不能进行自动分配!");
				}
			}

			if(Const.OPT_EVEN_1.equals(optEven)) {
				pd.put("ASSIGN_NAME","自动分配");
			} else if(Const.OPT_EVEN_2.equals(optEven)) {
				pd.put("ASSIGN_NAME","手工分配");
			}else if(Const.OPT_EVEN_3.equals(optEven)) {
				pd.put("ASSIGN_NAME","缺省分配");
			} else {
				pd.put("ASSIGN_NAME","取消分配");
			}

			List<PageData> srcList = new ArrayList<>();
			List<PageData> assignList = new ArrayList<>();
			getListByOpt(srcList,assignList,page);

			mv.addObject("srcList", srcList);
			mv.addObject("varList", assignList);

            pd.put("ASSIGN_TYPE",optEven);
			mv.setViewName("instock/assignopt_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}


	private void getListByOpt(List<PageData> srcList, List<PageData> assignList,Page page) throws Exception {
		//操作类型：1 自动分配 2 手工分配 3 缺省分配 4 取消分配
		String optEven = page.getPd().getString("ASSIGN_TYPE");
		if(Const.OPT_EVEN_4.equals(optEven)) {
			 assignoptService.findAssignCacellistPage(srcList,assignList,page);
		} else {
            assignoptService.findAssignlistPage(srcList,assignList,page);
		}
	}

	private void setSelect(ModelAndView mv,PageData pd) {
		List<Select> customerList = SelectCache.getInstance().getAllCustomer();

		List<Select> allProduct = SelectCache.getInstance().getAllProduct(null);

		//入库类型
		List<Select> stockInTypeList = SelectCache.getInstance().getStockInType();
		List<Select> stockInStateList = SelectCache.getInstance().getStockInState();

		List<Select> assignedList = SelectCache.getInstance().getAssigned();
		List<Select> putStateList = SelectCache.getInstance().getPutState();
		List<Select> priorityLevelList = SelectCache.getInstance().getPriorityLevel();

		mv.addObject("allProductList",allProduct);
		mv.addObject("stockInTypeList",stockInTypeList);
		mv.addObject("stockInStateList",stockInStateList);
		mv.addObject("assignedList",assignedList);
		mv.addObject("putStateList",putStateList);
		mv.addObject("priorityLevelList",priorityLevelList);

		mv.addObject("customerList",customerList);
		//库区
		List<Select> storageList = SelectCache.getInstance().getAllStorage();
		List<Select> allLocatorForShowList = SelectCache.getInstance().getdAllLocatorForShow();

		mv.addObject("allLocatorForShowList",allLocatorForShowList);
		mv.addObject("storageList",storageList);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除assignopt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			assignoptService.delete(pd);
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
		logBefore(logger, "修改assignopt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		assignoptService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表assignopt");

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<Select> list = new ArrayList<>();
			setSelect(mv,pd);
			setMutiple(pd);
			if (list != null && !list.isEmpty()) {
				String customerId = list.get(0).getId();
				pd.put("CUSTOMER_ID",customerId);

				List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
				mv.addObject("allProductList",allProduct);
			}

			page.setPd(pd);
			List<PageData>	varList = stockmgrinService.list(page);	//列出StockMgrIn列表

			setMutipleAfter(pd);
			mv.setViewName("instock/assignopt_list");
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


	/**
	 * 去查看页面
	 */
	@RequestMapping(value="/goLook")
	public ModelAndView goLook(Page page){
		logBefore(logger, "去查看ReceivOpt收货页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			String INSTOCK_NO = pd.getString("INSTOCK_NO");
			pd.put("INSTOCK_NO",pd.getList("INSTOCK_NO"));
			page.setPd(pd);
			setSelect(mv,pd);

			List<PageData> srcList = new ArrayList<>();
			List<PageData> assignList = new ArrayList<>();

			assignoptService.findDtlAssignlistPage(srcList,assignList,page);	//根据ID读取

			mv.addObject("srcList", srcList);
			mv.addObject("varList", assignList);

			pd.put("INSTOCK_NO",INSTOCK_NO);
			mv.setViewName("instock/assignopt_look");
			mv.addObject("msg", "goLook");
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
		logBefore(logger, "去修改assignopt页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = assignoptService.findById(pd);	//根据ID读取
			mv.setViewName("instock/assignopt_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}


	/**
	 * 去取消收货界面
	 */
	@RequestMapping(value="/goCancel")
	public ModelAndView goCancel(Page page){
		logBefore(logger, "去取消收货界面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> varList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)) {
				String[] IDS = DATA_IDS.split(",");
				pd.put("IDS",IDS);
				page.setPd(pd);;
				varList = assignoptService.findCancelDtlName(page);    //根据ID读取
			}

			mv.addObject("varList", varList);
			mv.setViewName("instock/assignopt_edit");
			mv.addObject("msg", "saveCancel");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}


	/**
	 * 取消收货
	 */
	@RequestMapping(value="/saveCancel")
	public ModelAndView saveCancel() throws Exception{
		logBefore(logger, "取消收货assignopt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除标志

		List<PageData> varList = new ArrayList<PageData>();
		String IDS = pd.getString("IDS");
		if(StringUtil.isEmpty(IDS)) {
			return mv;
		}

		String[] dtlIds =  StringUtils.split(IDS,Const.SPLIT_COMMA);

		assignoptService.saveCancel(pd,dtlIds);

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}


	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除assignopt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				assignoptService.deleteAll(ArrayDATA_IDS);
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

	/**
	 * 判断入库单是否存在
	 */
	@RequestMapping(value = "/hadInStock")
	@ResponseBody
	public Object hadInStock() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//boolean hadPd = stockmgrinService.isExists(pd.getString("INSTOCK_NO"));
			//PageData pData = stockmgrinService.findByInStockNo(pd.getString("INSTOCK_NO"));
			List<PageData> list = stockmgrinService.findDtlByInStockNo(pd.getString("INSTOCK_NO"));
			if (!list.isEmpty()) {
				StringBuffer hadProd = new StringBuffer(512);
				StringBuffer hadProdId = new StringBuffer(512);
				StringBuffer prodCnt = new StringBuffer(512);
				int count = 0;
				for(PageData e : list) {
					if (count != 0) {
						hadProd.append(Const.SPLIT_COMMA);
						hadProdId.append(Const.SPLIT_COMMA);
					}
					if (count == 0) {
						map.put("STOCKMGRIN_ID", e.getString("STOCKMGRIN_ID"));
						map.put("CUSTOMER_ID", e.getString("CUSTOMER_ID"));
					}
					hadProd.append(e.getString("PRODUCT_CODE"));
					prodCnt.append(e.getString("PRODUCT_CODE")+Const.SPLIT_LINE+e.getString("RECEI_EA"));
					hadProdId.append(e.getString("PRODUCT_ID"));
					count++;
				}
				map.put("HAD_PROD",hadProd.toString());
				map.put("PRODUCT_CODE_CNT",prodCnt.toString());
				errInfo = "success";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}


	/**
	 * 判断入库单是否存在
	 */
	@RequestMapping(value = "/auditBoxNo")
	@ResponseBody
	public Object auditBoxNo() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			String box_no = auditBoxNo(pd.getString("BOX_NO"));
			if (StringUtil.isNotEmpty(box_no)) {
				map.put("auditRs", box_no);
				errInfo = "success";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	private static String auditBoxNo(String boxNo) {
		if(StringUtil.isEmpty(boxNo)) {
			return "箱号为空";
		}

		if (boxNo.length() != Const.BOXNO_SIZE) {
			return "箱号长度不符";
		}

		String customer = boxNo.substring(0, 5);
		if(!StringUtil.isStr(customer)) {
			return "前5位不全是字母";
		}
		String secode = boxNo.substring(5,6);
		if(!Const.BOX_M_SEC.contains(secode)) {
			return "第6位不是M,O,I其中一个";
		}

		String yy = boxNo.substring(6,8);
		if(!StringUtil.isNumeric(yy)) {
			return "第7,8位不是有效年份";
		}

		String third = boxNo.substring(8,12);
		Set<String> boxRule = BaseInfoCache.getInstance().getBoxRule(Const.EMPTY_CH);
		if(!boxRule.contains(third)) {
			return "第9-12位不是"+boxRule.toString();
		}

		String serialNo = boxNo.substring(12);
		if(!StringUtil.isNumeric(serialNo)) {
			return "后5位不是有效的流水号";
		}

		return Const.EMPTY_CH;
	}

	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出assignopt到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("入库单号");	//1
			titles.add("箱号");	//2
			titles.add("前缀料号");	//3
			titles.add("料号");	//4
			titles.add("前缀数量");	//5
			titles.add("数量");	//6
			titles.add("COO");	//7
			titles.add("PACKAGE QTY");	//8
			titles.add("板号");	//9
			titles.add("二维码");	//10
			titles.add("前缀LOTNO");	//11
			titles.add("LOT_NO");	//12
			titles.add("前缀DATECODE");	//13
			titles.add("DATECODE");	//14
			titles.add("前缀扫描码数量");	//15
			titles.add("扫描码");	//16
			titles.add("描码数量");	//17
			titles.add("创建人");	//18
			titles.add("创建时间");	//19
			titles.add("修改人");	//20
			titles.add("修改时间");	//21
			titles.add("删除标志");	//22
			dataMap.put("titles", titles);
			List<PageData> varOList = assignoptService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("INSTOCK_NO"));	//1
				vpd.put("var2", varOList.get(i).getString("BOX_NO"));	//2
				vpd.put("var3", varOList.get(i).getString("PREFIX_PROD"));	//3
				vpd.put("var4", varOList.get(i).getString("PROD_CODE"));	//4
				vpd.put("var5", varOList.get(i).getString("PREFIX_QTY"));	//5
				vpd.put("var6", varOList.get(i).getString("RECEIV_QTY"));	//6
				vpd.put("var7", varOList.get(i).getString("COO"));	//7
				vpd.put("var8", varOList.get(i).getString("PACKAGE_QTY"));	//8
				vpd.put("var9", varOList.get(i).getString("BOARD_NO"));	//9
				vpd.put("var10", varOList.get(i).getString("QR_CODE"));	//10
				vpd.put("var11", varOList.get(i).getString("PREFIX_LOTNO"));	//11
				vpd.put("var12", varOList.get(i).getString("LOT_NO"));	//12
				vpd.put("var13", varOList.get(i).getString("PREFIX_DATECODE"));	//13
				vpd.put("var14", varOList.get(i).getString("DATE_CODE"));	//14
				vpd.put("var15", varOList.get(i).getString("PREFIX_SCAN"));	//15
				vpd.put("var16", varOList.get(i).getString("SCAN_CODE"));	//16
				vpd.put("var17", varOList.get(i).getString("SCAN_QTY"));	//17
				vpd.put("var18", varOList.get(i).getString("CREATE_EMP"));	//18
				vpd.put("var19", varOList.get(i).getString("CREATE_TM"));	//19
				vpd.put("var20", varOList.get(i).getString("MODIFY_EMP"));	//20
				vpd.put("var21", varOList.get(i).getString("MODIFY_TM"));	//21
				vpd.put("var22", varOList.get(i).getString("DEL_FLG"));	//22
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
