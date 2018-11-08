package com.hw.controller.instock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.instock.AssignOptService;
import com.hw.service.instock.PutLocatorOptService;
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
 * Created：panda.HuangWei
 * Date：2016-11-23
 */
@Controller
@RequestMapping(value="/putlocatoropt")
public class PutLocatorOptController extends BaseController {
	
	String menuUrl = "putlocatoropt/list.do"; //菜单地址(权限用)
	@Resource(name="putLocatorOptService")
	private PutLocatorOptService putLocatorOptService;

	@Resource(name="stockmgrinService")
	private StockMgrInService stockmgrinService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(Page page) throws Exception{
		logBefore(logger, "新增putlocatoropt");
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

		//如果为空，则是翻页，不需要做其他操作
		if (StringUtil.isEmpty(optEven)) {
			String ids = pd.getString("INSTOCK_NOS");
			String[] noArr = StringUtils.split(ids,Const.SPLIT_COMMA);
			pd.put("INSTOCK_NO",noArr);
			page.setPd(pd);
		//	List<PageData> varList = getListByOpt(page);
			//mv.addObject("varList", varList);
			mv.setViewName("instock/putlocatoropt_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			return mv;
		}

		if(Const.OPT_EVEN_2.equals(optEven)) {
			mv.setViewName("instock/putlocatoropt_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
			return mv;
		}

		if(StringUtil.isNotEmpty(optEven)) {

		}

		//操作事件：1 返回原页面并带值回去，2、保存后关闭页面
		PageData rs = new PageData();
		if(Const.OPT_EVEN_1.equals(optEven)) {

		} else {

		}

		page.setPd(rs);
	/*	List<PageData>	varList = assignoptService.findCurReceivDtl(page);	//列出putlocatoropt列表
		mv.addObject("varList", varList);*/

		mv.setViewName("instock/putlocatoropt_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", rs);

		return mv;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value="/saveScane")
	public ModelAndView saveScane(Page page) throws Exception{
		logBefore(logger, "新增putlocatoropt");
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

		String zindext = pd.getString("zindex");	   	   			//属性总数
		int zindex = 0;
		if(null != zindext && !"".equals(zindext)){
			zindex = Integer.parseInt(zindext);
		}
		StringBuffer boxArr = new StringBuffer(128);
		for(int i=0; i< zindex; i++){
			boxArr.append(pd.getString("field"+i).split(Const.SYS_SPLIT)[0]);
			if(i != zindex -1) {
				boxArr.append(Const.SPLIT_COMMA);
			}
		}
		pd.put("BOX_NOS",boxArr.toString());

		putLocatorOptService.savePutScan(pd);

		mv.setViewName("instock/putlocatoropt_scan");
		pd.put("success","success");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value="/saveScane1")
	public ModelAndView saveScane1(Page page) throws Exception{
		logBefore(logger, "新增putlocatoropt");
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

		String zindext = pd.getString("zindex");	   	   			//属性总数
		int zindex = 0;
		if(null != zindext && !"".equals(zindext)){
			zindex = Integer.parseInt(zindext);
		}
		StringBuffer boxArr = new StringBuffer(128);
		for(int i=0; i< zindex; i++){
			boxArr.append(pd.getString("field"+i).split(Const.SYS_SPLIT)[0]);
			if(i != zindex -1) {
				boxArr.append(Const.SPLIT_COMMA);
			}
		}
		pd.put("BOX_NOS",boxArr.toString());

		putLocatorOptService.savePutScan1(pd);

		mv.setViewName("stock/putscan_scan");
		pd.put("success","success");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 去分配页面
	 */
	@RequestMapping(value="/goScanPut")
	public ModelAndView goScanPut(Page page){
		logBefore(logger, "去分配putlocatoropt页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("INSTOCK_NO",VaryUtils.genListOrNull(pd.getString("INSTOCK_NOS")));
			page.setPd(pd);
			setSelect(mv);
			//List<PageData> varList = getListByOpt(page);

			//mv.addObject("varList", varList);
			mv.setViewName("instock/putlocatoropt_scan");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去手工码放
	 */
	@RequestMapping(value="/goManualPut")
	public ModelAndView goManualPut(Page page){
		logBefore(logger, "去手工码放putlocatoropt_manual页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("INSTOCK_NO",VaryUtils.genListOrNull(pd.getString("INSTOCK_NOS")));
			page.setPd(pd);
			setSelect(mv);

			List<PageData> srcList = new ArrayList<>();
			List<PageData> assignList= new ArrayList<>();

			getListByInstockNo(srcList,assignList,page);

			mv.addObject("srcList", srcList);
			mv.addObject("varList", assignList);
			mv.setViewName("instock/putlocatoropt_manual");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 去手工码放
	 */
	@RequestMapping(value="/goManualCacel")
	public ModelAndView goManualCacel(Page page){
		logBefore(logger, "去手工码放putlocatoropt_manual页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("INSTOCK_NO",VaryUtils.genListOrNull(pd.getString("INSTOCK_NOS")));
			page.setPd(pd);
			setSelect(mv);

			List<PageData> srcList = new ArrayList<>();
			List<PageData> assignList= new ArrayList<>();

			putLocatorOptService.findCancellistPage(srcList,assignList,page);

			mv.addObject("srcList", srcList);
			mv.addObject("varList", assignList);

			mv.setViewName("instock/putlocatoropt_cancel");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping(value="/saveManualHead")
	public ModelAndView saveManualHead(Page page) throws Exception{
		logBefore(logger, "手工码放与取消码放putlocatoropt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//1 手工码放 2 取消码放
		String optEven = pd.getString("OPT_EVEN");

		String ids = pd.getString("INSTOCK_NOS");
		String[] noArr = StringUtils.split(ids,Const.SPLIT_COMMA);
		pd.put("INSTOCK_NO",noArr);
		page.setPd(pd);

		//手工码放
		if(Const.OPT_EVEN_1.equals(optEven)) {
			putLocatorOptService.savePutManualHead(pd);
		}

		if(Const.OPT_EVEN_2.equals(optEven)) {
			putLocatorOptService.savePutCacel(pd);
		}
		setSelect(mv);
		List<PageData> srcList = new ArrayList<>();
		List<PageData> assignList= new ArrayList<>();

		getListByInstockNo(srcList,assignList,page);

		mv.addObject("srcList", srcList);
		mv.addObject("varList", assignList);
		mv.setViewName("instock/putlocatoropt_manual");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 手工码放/取消码放
	 */
	@RequestMapping(value="/saveManual")
	public ModelAndView saveManual(Page page) throws Exception{
		logBefore(logger, "手工码放与取消码放putlocatoropt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//1 手工码放 2 取消码放
		String optEven = pd.getString("OPT_EVEN");

		String ids = pd.getString("INSTOCK_NOS");
		String[] noArr = StringUtils.split(ids,Const.SPLIT_COMMA);
		pd.put("INSTOCK_NO",noArr);
		page.setPd(pd);

		//手工码放
		if(Const.OPT_EVEN_1.equals(optEven)) {
          putLocatorOptService.savePutManual(pd);
		}

		if(Const.OPT_EVEN_2.equals(optEven)) {
			 putLocatorOptService.savePutCacel(pd);
		}
		setSelect(mv);
		List<PageData> srcList = new ArrayList<>();
		List<PageData> assignList= new ArrayList<>();

		getListByInstockNo(srcList,assignList,page);

		mv.addObject("srcList", srcList);
		mv.addObject("varList", assignList);

		mv.setViewName("instock/putlocatoropt_manual");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

		return mv;
	}

	@RequestMapping(value="/saveCancelHead")
	public ModelAndView saveCancelHead(Page page) throws Exception{
		logBefore(logger, "手工码放与取消码放putlocatoropt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//1 手工码放 2 取消码放
		String optEven = pd.getString("OPT_EVEN");

		String ids = pd.getString("INSTOCK_NOS");
		String[] noArr = StringUtils.split(ids,Const.SPLIT_COMMA);
		pd.put("INSTOCK_NO",noArr);
		page.setPd(pd);

		//手工码放
		if(Const.OPT_EVEN_1.equals(optEven)) {
			putLocatorOptService.savePutManual(pd);
		}

		if(Const.OPT_EVEN_2.equals(optEven)) {
			putLocatorOptService.savePutCacelHead(pd);
		}
		setSelect(mv);
		List<PageData> varList =putLocatorOptService.findCancellistPage(page);
		mv.addObject("varList", varList);
		mv.setViewName("instock/putlocatoropt_cancel");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

		return mv;
	}

	@RequestMapping(value="/saveCancel")
	public ModelAndView saveCancel(Page page) throws Exception{
		logBefore(logger, "手工码放与取消码放putlocatoropt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		//1 手工码放 2 取消码放
		String optEven = pd.getString("OPT_EVEN");

		String ids = pd.getString("INSTOCK_NOS");
		String[] noArr = StringUtils.split(ids,Const.SPLIT_COMMA);
		pd.put("INSTOCK_NO",noArr);
		page.setPd(pd);

		//手工码放
		if(Const.OPT_EVEN_1.equals(optEven)) {
			putLocatorOptService.savePutManual(pd);
		}

		if(Const.OPT_EVEN_2.equals(optEven)) {
			putLocatorOptService.savePutCacel(pd);
		}
		setSelect(mv);
		List<PageData> varList =putLocatorOptService.findCancellistPage(page);
		mv.addObject("varList", varList);
		mv.setViewName("instock/putlocatoropt_cancel");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

		return mv;
	}

	private void getListByInstockNo(List<PageData> srcList,List<PageData> assignList,Page page) throws Exception {
		putLocatorOptService.findManualPutlistPage(srcList,assignList,page);
	}


	private void setSelect(ModelAndView mv) {
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

		mv.addObject("storageList",storageList);
		List<Select> allLocatorList = SelectCache.getInstance().getAllLocator();
		List<Select> allLocatorForShowList = SelectCache.getInstance().getdAllLocatorForShow();

		mv.addObject("allLocatorForShowList",allLocatorForShowList);
		mv.addObject("allLocatorList",allLocatorList);
	}

	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除putlocatoropt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			//assignoptService.delete(pd);
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
		logBefore(logger, "修改putlocatoropt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//assignoptService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表putlocatoropt");

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			List<Select> list = new ArrayList<>();
			setSelect(mv);
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

			mv.setViewName("instock/putlocatoropt_list");
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
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改putlocatoropt页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			//pd = assignoptService.findById(pd);	//根据ID读取
			mv.setViewName("instock/putlocatoropt_edit");
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
	@RequestMapping(value="/goLook")
	public ModelAndView goLook(Page page){
		logBefore(logger, "去查看码放信息页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			page.setPd(pd);
			List<PageData>	varList =  putLocatorOptService.findDtlPutStatelistPage(page);	//根据ID读取
			mv.addObject("varList", varList);
			setSelect(mv);

			mv.setViewName("instock/putlocatoropt_look");
			mv.addObject("msg", "goLook");
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
	public ModelAndView goCancel(){
		logBefore(logger, "去查看putlocatoropt收货页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> varList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)) {
				String[] IDS = DATA_IDS.split(",");
				//varList = assignoptService.findCancelDtlName(IDS);    //根据ID读取
			}

			mv.addObject("varList", varList);
			mv.setViewName("instock/receivopt_cancel");
			mv.addObject("msg", "saveCancel");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}


	/**
	 * 取消收货
	 *//*
	@RequestMapping(value="/saveCancel")
	public ModelAndView saveCancel() throws Exception{
		logBefore(logger, "取消收货putlocatoropt");
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

		//assignoptService.saveCancel(pd,dtlIds);

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
*/

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除putlocatoropt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				//assignoptService.deleteAll(ArrayDATA_IDS);
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

	@RequestMapping(value = "/getLocatorList")
	@ResponseBody
	public Object getLocatorList() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		map.put("LOCATOR_ID","");
		try {
			pd = this.getPageData();
			String LOCATOR_CODE = pd.getString("LOCATOR_CODE");

			List<Select> allProduct = SelectCache.getInstance().getAllLocator();
			if (allProduct == null || allProduct.size() == 0) {
				return AppUtil.returnObject(pd, map);
			}
			for (Select p : allProduct) {
				if(LOCATOR_CODE.equalsIgnoreCase(p.getValue())) {
					map.put("LOCATOR_ID",p.getId());
				}
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
	@RequestMapping(value = "/auditBoxNo")
	@ResponseBody
	public Object auditBoxNo() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			map.put("auditRs", "PASS"+",hw,"+" "+",hw,"+" ");

			String box_no = auditBoxNo(pd.getString("BOX_NO"));
			if (StringUtil.isNotEmpty(box_no)) {
				map.put("auditRs", box_no+",hw,"+" "+",hw,"+" ");
				errInfo = "success";
			} else {
				//如果为空，说明该箱号符合规则，接下进行箱号重复校验
				PageData boxForPut = putLocatorOptService.findBoxForPut(pd);
				if(boxForPut != null) {
					String ASIGN_LOCATOR = boxForPut.getString("ASIGN_LOCATOR");
					String PUT_LOCATOR = boxForPut.getString("PUT_LOCATOR");
					String INSTOCK_NO = boxForPut.getString("INSTOCK_NO");
					String PRODUCT_CN = BaseInfoCache.getInstance().getProductCode(boxForPut.getString("PRODUCT_ID"));

					/*if(StringUtil.isEmpty(ASIGN_LOCATOR)) {
						map.put("auditRs", "该箱号还没收货!"+",hw,"+INSTOCK_NO+",hw,"+PRODUCT_CN);
						errInfo = "success";
					} else if (StringUtil.isNotEmpty(PUT_LOCATOR)) {
						map.put("auditRs", "该箱号已经码放!"+",hw,"+INSTOCK_NO+",hw,"+PRODUCT_CN);
						errInfo = "success";
					} else {
						map.put("auditRs", "PASS"+",hw,"+INSTOCK_NO+",hw,"+PRODUCT_CN);
					}*/
					map.put("auditRs", "PASS"+",hw,"+INSTOCK_NO+",hw,"+PRODUCT_CN);
				} else {
					map.put("auditRs", "该箱号还没收货!"+",hw,"+" "+",hw,"+" ");
				}

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
	@RequestMapping(value = "/auditBoxNoForScan")
	@ResponseBody
	public Object auditBoxNoForScan() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			map.put("auditRs", "PASS"+",hw,"+" "+",hw,"+" ");

			/*String box_no = auditBoxNo(pd.getString("BOX_NO"));
			if (StringUtil.isNotEmpty(box_no)) {
				map.put("auditRs", box_no+",hw,"+" "+",hw,"+" ");
				errInfo = "success";
			} else {*/
				//如果为空，说明该箱号符合规则，接下进行箱号重复校验
				PageData boxForPut = putLocatorOptService.findBoxForScanPut(pd);
				if(boxForPut != null) {
					String asignLocator = boxForPut.getString("ASIGN_LOCATOR");
					String PUT_LOCATOR = boxForPut.getString("PUT_LOCATOR");
					String bigBoxNo = boxForPut.getString("BIG_BOX_NO");
					String PRODUCT_CN = boxForPut.getString("PRODUCT_CN");
					String isBig = StringUtil.isBlank(bigBoxNo)?"否":"是";
					map.put("auditRs", "PASS"+",hw,"+isBig+",hw,"+PRODUCT_CN);
					/*if(StringUtil.isEmpty(asignLocator)) {
						map.put("auditRs", "该箱号还没收货!"+",hw,"+INSTOCK_NO+",hw,"+PRODUCT_CN);
						errInfo = "success";
					} else if (StringUtil.isNotEmpty(PUT_LOCATOR)) {
						map.put("auditRs", "该箱号已经码放!"+",hw,"+INSTOCK_NO+",hw,"+PRODUCT_CN);
						errInfo = "success";
					} else {
						map.put("auditRs", "PASS"+",hw,"+INSTOCK_NO+",hw,"+PRODUCT_CN);
					}*/
				} else {
					map.put("auditRs", "该箱号还没收货!"+",hw,"+" "+",hw,"+" ");
				}

		/*	}*/
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

		String third = boxNo.substring(8,11);
		Set<String> boxRule = BaseInfoCache.getInstance().getBoxRule(Const.EMPTY_CH);
		if(!boxRule.contains(third)) {
			return "第9-11位不是"+boxRule.toString();
		}

		String serialNo = boxNo.substring(12);
		if(!StringUtil.isNumeric(serialNo)) {
			return "后5位不是有效的流水号";
		}

		return Const.EMPTY_CH;
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
