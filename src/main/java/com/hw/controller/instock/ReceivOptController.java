package com.hw.controller.instock;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.SameUrlData;
import com.hw.entity.base.Select;
import com.hw.service.instock.StockMgrInService;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
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
import com.hw.service.instock.ReceivOptService;

/**
 * 收货
 * Created：panda.HuangWei
 * Date：2016-11-17
 */
@Controller
@RequestMapping(value="/receivopt")
public class ReceivOptController extends BaseController {

	String menuUrl = "receivopt/list.do"; //菜单地址(权限用)
	@Resource(name="receivoptService")
	private ReceivOptService receivoptService;

	@Resource(name="stockmgrinService")
	private StockMgrInService stockmgrinService;

	//private  ConcurrentHashMap<String,PageData> ConhashMap;
	
	/**
	 * 新增
	 */
	@SameUrlData
	@RequestMapping(value="/save")
	public ModelAndView save(Page page) throws Exception{
		logBefore(logger, "新增ReceivOpt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除标志


        String optEven = pd.getString("OPT_EVEN");

		if(Const.OPT_EVEN_2.equals(optEven)) {

			mv.setViewName("instock/receivopt_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);

			return mv;
		}

		if(StringUtil.isNotEmpty(optEven)) {
			receivoptService.saveReceive(pd);
		}

		//操作事件：1 返回原页面并带值回去，2、保存后关闭页面
		PageData rs = new PageData();
		if(Const.OPT_EVEN_1.equals(optEven)) {
			rs.put("CNT_SCAN",pd.getInteger("CNT_SCAN")+pd.getInteger("RECEIV_QTY"));
			rs.put("BOX_SCAN",pd.getInteger("BOX_SCAN")+1);
		} else {
			rs.put("CNT_SCAN",pd.getInteger("CNT_SCAN"));
			rs.put("BOX_SCAN",pd.getInteger("BOX_SCAN"));
		}

		rs.put("INSTOCK_NO",pd.getString("INSTOCK_NO"));
		rs.put("RECEIVOPT_ID",pd.getString("RECEIVOPT_ID"));
		rs.put("RECEIV_TYPE",pd.getString("RECEIV_TYPE"));
		rs.put("STOCKMGRIN_ID",pd.getString("STOCKMGRIN_ID"));
		rs.put("CUSTOMER_ID",pd.getString("CUSTOMER_ID"));
		rs.put("COO",pd.getString("COO"));
		rs.put("HAD_PROD",pd.getString("HAD_PROD"));

		rs.put("GROUP_KEY", pd.getString("GROUP_KEY"));
		//前缀料号，前缀数量
		rs.put("PREFIX_PROD",pd.getString("PREFIX_PROD"));
		rs.put("PREFIX_QTY",pd.getString("PREFIX_QTY"));
		// 前缀lotno ，前缀dataCode,前缀扫描码
		rs.put("PREFIX_LOTNO",pd.getString("PREFIX_LOTNO"));
		rs.put("PREFIX_DATECODE",pd.getString("PREFIX_DATECODE"));
		rs.put("PREFIX_SCAN",pd.getString("PREFIX_SCAN"));

		rs.put("PRODUCT_CODE_CNT",subCnt(pd.getString("PRODUCT_CODE_CNT"),pd.getInteger("RECEIV_QTY"),pd.getString("PROD_CODE")));
		page.setPd(rs);
		List<PageData>	varList = receivoptService.findCurReceivDtl(page);	//列出ReceivOpt列表
		mv.addObject("varList", varList);

		mv.setViewName("instock/receivopt_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", rs);

		return mv;
	}

	private String subCnt(String prodStr,int cnt,String prodCode) {
		if(StringUtil.isEmpty(prodStr)) {
			return Const.EMPTY_CH;
		}
		String[] arr = StringUtils.split(prodStr,Const.SPLIT_COMMA);
		int count = 0;
		StringBuffer newProd = new StringBuffer(512);
		for(String prods :arr) {
          String[] prodMap = StringUtils.split(prods,Const.SPLIT_LINE);
			int oldCnt = StringUtil.parseInt(prodMap[1]);
			if(prodCode.equals(prodMap[0])) {
				oldCnt = oldCnt - cnt;
		    }
			if (count != 0) {
				newProd.append(Const.SPLIT_COMMA);
			}
			String rs = prodMap[0]+Const.SPLIT_LINE+oldCnt;
			newProd.append(rs);
			count ++;
		}
         return newProd.toString();
	}

	private void setSelect(ModelAndView mv) {
		List<Select> customerList = SelectCache.getInstance().getAllCustomer();

		List<Select> allProduct = SelectCache.getInstance().getAllProduct(null);
		List<Select> locatorList = SelectCache.getInstance().getdAllLocatorForShow();
		mv.addObject("allProductList",allProduct);
		mv.addObject("customerList",customerList);
		mv.addObject("locatorList",locatorList);
	}

	private void setSelect(ModelAndView mv,PageData pd) {
		List<Select> customerList = SelectCache.getInstance().getAllCustomer();
		//入库类型
		List<Select> stockInTypeList = SelectCache.getInstance().getStockInType();
		String customerId = null;
		if (pd != null) {
			customerId = pd.getString("CUSTOMER_ID");
		}
		//产品 customerList stockInTypeList stockInStateList assignedList putStateList
		List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);

		List<Select> stockInStateList = SelectCache.getInstance().getStockInState();
		List<Select> assignedList = SelectCache.getInstance().getAssigned();
		List<Select> putStateList = SelectCache.getInstance().getPutState();
		List<Select> priorityLevelList = SelectCache.getInstance().getPriorityLevel();

		mv.addObject("stockInTypeList",stockInTypeList);
		mv.addObject("allProductList",allProduct);
		mv.addObject("stockInStateList",stockInStateList);
		mv.addObject("assignedList",assignedList);
		mv.addObject("putStateList",putStateList);
		mv.addObject("priorityLevelList",priorityLevelList);

		mv.addObject("customerList",customerList);
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除ReceivOpt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			receivoptService.delete(pd);
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
		logBefore(logger, "修改ReceivOpt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		receivoptService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表ReceivOpt");

		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			setSelect(mv,pd);
			setMutiple(pd);
			if (StringUtil.isBlank(pd.getString("CUSTOMER_ID"))) {
				String customerId =SelectCache.getInstance().getAllCustomer().get(0).getId();
				pd.put("CUSTOMER_ID",customerId);
			/*	List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
				mv.addObject("allProductList",allProduct);*/
			}

			page.setPd(pd);
			List<PageData> varList=new ArrayList<>();
			String searchFlag = pd.getString("SEARCH_FLAG");
			if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
				varList = stockmgrinService.list(page);	//列出StockMgrIn列表
			}

			setMutipleAfter(pd);

			mv.setViewName("instock/receivopt_list");
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
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增ReceivOpt页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("CNT_SCAN",0);
			pd.put("BOX_SCAN",0);
			pd.put("COO","CHINA");
			pd.put("GROUP_KEY", this.get32UUID());
            //前缀料号，前缀数量
			pd.put("PREFIX_PROD",Const.PREFIX_PROD);
			pd.put("PREFIX_QTY",Const.PREFIX_QTY);

			mv.setViewName("instock/receivopt_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}


	@RequestMapping(value="/goAddSprea")
	public ModelAndView goAddSprea(){
		logBefore(logger, "去新增ReceivOpt页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		//ConhashMap=new ConcurrentHashMap();
		try {
			pd.put("CNT_SCAN",0);
			pd.put("BOX_SCAN",0);
			pd.put("COO","CHINA");
			pd.put("QR_CODE_USE",1);
			pd.put("GROUP_KEY", this.get32UUID());
			//前缀料号，前缀数量
			pd.put("PREFIX_PROD",Const.PREFIX_PROD);
			pd.put("PREFIX_QTY",Const.PREFIX_QTY);

			mv.setViewName("instock/receivopt_sprea");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 新增
	 */
	@RequestMapping(value="/saveSprea")
	public ModelAndView saveSprea(Page page) throws Exception {
		logBefore(logger, "新增ReceivOpt");
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("CREATE_EMP", getCurUserName());    //创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间
		pd.put("MODIFY_EMP", getCurUserName());    //修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
		pd.put("DEL_FLG", "0");    //删除标志


		String optEven = pd.getString("OPT_EVEN");

		if (Const.OPT_EVEN_2.equals(optEven)) {

			mv.setViewName("instock/receivopt_sprea");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);

			return mv;
		}
        //TODO
		/*SaveCurReceivDtl(pd);
		List<PageData> vardatas = findHashMapCurReceivDtl();
		if (!Const.OPT_EVEN_5.equals(optEven)) {
			if (StringUtil.isNotEmpty(optEven)) {
				SaveSprea(vardatas);
				vardatas.clear();
			}
		}*/
		if(StringUtil.isNotEmpty(optEven)) {
			receivoptService.saveReceive(pd);
		}

		//操作事件：1 返回原页面并带值回去，2、保存后关闭页面 TODO ||Const.OPT_EVEN_5.equals(optEven)
		PageData rs = new PageData();
		if (Const.OPT_EVEN_1.equals(optEven)) {
			rs.put("CNT_SCAN", pd.getInteger("CNT_SCAN") + pd.getInteger("RECEIV_QTY"));
			rs.put("BOX_SCAN", pd.getInteger("BOX_SCAN") + 1);
		} else {
			rs.put("CNT_SCAN", pd.getInteger("CNT_SCAN"));
			rs.put("BOX_SCAN", pd.getInteger("BOX_SCAN"));
		}

		rs.put("INSTOCK_NO", pd.getString("INSTOCK_NO"));
		rs.put("BIG_BOX_NO", pd.getString("BIG_BOX_NO"));
		rs.put("QR_CODE_USE", pd.getInteger("QR_CODE_USE"));

		rs.put("RECEIVOPT_ID", pd.getString("RECEIVOPT_ID"));
		rs.put("RECEIV_TYPE", pd.getString("RECEIV_TYPE"));
		rs.put("STOCKMGRIN_ID", pd.getString("STOCKMGRIN_ID"));
		rs.put("CUSTOMER_ID", pd.getString("CUSTOMER_ID"));
		rs.put("COO", pd.getString("COO"));
		rs.put("HAD_PROD", pd.getString("HAD_PROD"));

		rs.put("GROUP_KEY", pd.getString("GROUP_KEY"));
		//前缀料号，前缀数量
		rs.put("PREFIX_PROD", pd.getString("PREFIX_PROD"));
		rs.put("PREFIX_QTY", pd.getString("PREFIX_QTY"));
		// 前缀lotno ，前缀dataCode,前缀扫描码
		rs.put("PREFIX_LOTNO", pd.getString("PREFIX_LOTNO"));
		rs.put("PREFIX_DATECODE", pd.getString("PREFIX_DATECODE"));
		rs.put("PREFIX_SCAN", pd.getString("PREFIX_SCAN"));

		rs.put("PRODUCT_CODE_CNT", subCnt(pd.getString("PRODUCT_CODE_CNT"), pd.getInteger("RECEIV_QTY"), pd.getString("PROD_CODE")));
		page.setPd(rs);
		List<PageData> varList = receivoptService.findCurReceivDtl(page);    //列出ReceivOpt列表
		//varList.addAll(vardatas) ;
		mv.addObject("varList", varList);

		mv.setViewName("instock/receivopt_sprea");
		mv.addObject("msg", "save");
		mv.addObject("pd", rs);

		return mv;
	}
	/*
           *内存中保存本次收货明细
     */
	/*public void SaveCurReceivDtl(PageData pd) throws Exception {
		String id = UuidUtil.get32UUID();
		ConhashMap.put(id,pd);

	}
	public List<PageData> findHashMapCurReceivDtl() {
		List<PageData> varList= new ArrayList<PageData>(ConhashMap.values());
		return varList;
	}

	public  void  SaveSprea(List<PageData>varList) throws Exception {

		for (PageData pd : varList) {
			receivoptService.saveReceive(pd);
		}
		ConhashMap.clear();
	}
*/
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改ReceivOpt页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = receivoptService.findById(pd);	//根据ID读取
			mv.setViewName("instock/receivopt_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping(value="/goAddCommon")
	public ModelAndView goAddCommon(){
		logBefore(logger, "去新增ReceivOpt页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("CNT_SCAN",0);
			pd.put("BOX_SCAN",0);
			pd.put("COO","CHINA");
			pd.put("QR_CODE_USE",1);
			pd.put("GROUP_KEY", this.get32UUID());
			//前缀料号，前缀数量
			pd.put("PREFIX_PROD",Const.PREFIX_PROD);
			pd.put("PREFIX_QTY",Const.PREFIX_QTY);

			mv.setViewName("instock/receivopt_edit_com");
			mv.addObject("msg", "saveCommon");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	@RequestMapping(value="/saveCommon")
	public ModelAndView saveCommon(Page page) throws Exception{
		logBefore(logger, "新增ReceivOpt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除标志


		String optEven = pd.getString("OPT_EVEN");

		if(Const.OPT_EVEN_2.equals(optEven)) {

			mv.setViewName("instock/receivopt_edit_com");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);

			return mv;
		}

		if(StringUtil.isNotEmpty(optEven)) {

			receivoptService.saveReceive(pd);
		}

		//操作事件：1 返回原页面并带值回去，2、保存后关闭页面
		PageData rs = new PageData();
		if(Const.OPT_EVEN_1.equals(optEven)) {
			rs.put("CNT_SCAN",pd.getInteger("CNT_SCAN")+pd.getInteger("RECEIV_QTY"));
			rs.put("BOX_SCAN",pd.getInteger("BOX_SCAN")+1);
		} else {
			rs.put("CNT_SCAN",pd.getInteger("CNT_SCAN"));
			rs.put("BOX_SCAN",pd.getInteger("BOX_SCAN"));
		}

		rs.put("INSTOCK_NO",pd.getString("INSTOCK_NO"));
		rs.put("BIG_BOX_NO", pd.getString("BIG_BOX_NO"));
		rs.put("RECEIVOPT_ID",pd.getString("RECEIVOPT_ID"));
		rs.put("RECEIV_TYPE",pd.getString("RECEIV_TYPE"));
		rs.put("STOCKMGRIN_ID",pd.getString("STOCKMGRIN_ID"));
		rs.put("CUSTOMER_ID",pd.getString("CUSTOMER_ID"));
		rs.put("COO",pd.getString("COO"));
		rs.put("HAD_PROD",pd.getString("HAD_PROD"));

		rs.put("GROUP_KEY", pd.getString("GROUP_KEY"));
		//前缀料号，前缀数量
		rs.put("PREFIX_PROD",pd.getString("PREFIX_PROD"));
		rs.put("PREFIX_QTY",pd.getString("PREFIX_QTY"));
		// 前缀lotno ，前缀dataCode,前缀扫描码
		rs.put("PREFIX_LOTNO",pd.getString("PREFIX_LOTNO"));
		rs.put("PREFIX_DATECODE",pd.getString("PREFIX_DATECODE"));
		rs.put("PREFIX_SCAN",pd.getString("PREFIX_SCAN"));

		rs.put("PRODUCT_CODE_CNT",subCnt(pd.getString("PRODUCT_CODE_CNT"),pd.getInteger("RECEIV_QTY"),pd.getString("PROD_CODE")));
		page.setPd(rs);
		List<PageData>	varList = receivoptService.findCurReceivDtl(page);	//列出ReceivOpt列表
		mv.addObject("varList", varList);

		mv.setViewName("instock/receivopt_edit_com");
		mv.addObject("msg", "save");
		mv.addObject("pd", rs);

		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goLook")
	public ModelAndView goLook(Page page){
		logBefore(logger, "去查看ReceivOpt收货页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			page.setPd(pd);
			setSelect(mv);
			List<PageData>	varList = receivoptService.findDtlName(page);	//根据ID读取
			mv.addObject("varList", varList);

			mv.setViewName("instock/receivopt_look");
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
	public ModelAndView goCancel(Page page){
		logBefore(logger, "去取消收货receivopt_cancel页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<PageData> varList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)) {
				String[] IDS = DATA_IDS.split(",");
				pd.put("IDS",VaryUtils.genListOrNull(pd.getString("IDS")));
				page.setPd(pd);
				varList = receivoptService.findCancelDtl(page);    //根据ID读取
			}
			setSelect(mv);
			pd.put("DATA_IDS",DATA_IDS);
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
	 */
	@RequestMapping(value="/saveCancel")
	public ModelAndView saveCancel(Page page) throws Exception{
		logBefore(logger, "取消收货ReceivOpt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除标志



		if(Const.OPT_EVEN_4.equals(pd.getString("OPT_EVEN"))) {
			String IDS = pd.getString("IDS");
			String[] dtlIds =  StringUtils.split(IDS,Const.SPLIT_COMMA);
			receivoptService.saveCancel(pd,dtlIds);
		}


		pd.put("IDS",VaryUtils.genListOrNull(pd.getString("DATA_IDS")));
		page.setPd(pd);
		List<PageData> varList = receivoptService.findCancelDtl(page);    //根据ID读取

		mv.addObject("varList", varList);
		mv.setViewName("instock/receivopt_cancel");
		mv.addObject("msg", "saveCancel");
		mv.addObject("pd", pd);

		return mv;
	}


	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除ReceivOpt");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				receivoptService.deleteAll(ArrayDATA_IDS);
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
			PageData pData = stockmgrinService.findByInStockNo(pd.getString("INSTOCK_NO"));
			if (pData != null) {
				map.put("STOCKMGRIN_ID", pData.getString("STOCKMGRIN_ID"));
				map.put("CUSTOMER_ID", pData.getString("CUSTOMER_ID"));
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
	@RequestMapping(value = "/hadInStockCommonReceive")
	@ResponseBody
	public Object hadInStockCommonReceive() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//boolean hadPd = stockmgrinService.isExists(pd.getString("INSTOCK_NO"));
			PageData pData = stockmgrinService.findByInStockNo(pd.getString("INSTOCK_NO"));
			if (pData != null) {
				String customerId = pData.getString("CUSTOMER_ID");
				map.put("STOCKMGRIN_ID", pData.getString("STOCKMGRIN_ID"));
				map.put("CUSTOMER_ID", customerId);
				Pair<String, String> pair = BaseInfoCache.getInstance().get2DSplit(customerId);
				if (pair == null || StringUtil.isBlank(pair.getRight())) {
					map.put("QRFLAG","0");
				} else {
					map.put("SEPERATOR", pair.getLeft());
					map.put("QR2D", pair.getRight());
					map.put("QRFLAG","1");
				}
				errInfo = "success";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 判断料号是否存在
	 */
	@RequestMapping(value = "/hadProd")
	@ResponseBody
	public Object hadProd() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String customerId = pd.getString("CUSTOMER_ID");
			String supplierProd = pd.getString("PRODUCT_CODE").toUpperCase();

			logger.info("客户ID：{},供应商料号:{}",customerId,supplierProd);

			String productId = BaseInfoCache.getInstance().getProdIdByCustomerIdAndScanCode(customerId,supplierProd);
			if(StringUtil.isEmpty(productId)) {
				map.put("AUDIT_INFO", "料号在[对照表]中不存在!");
			} else {
				pd.put("PRODUCT_ID",productId);
				PageData pData = stockmgrinService.findByProdCheck(pd);
				if (pData != null) {
					map.put("STOCKMGRIN_ID", pData.getString("STOCKMGRIN_ID"));
					map.put("CUSTOMER_ID", pData.getString("CUSTOMER_ID"));
					map.put("PRODUCT_CODE", pData.getString("PRODUCT_CODE"));
					map.put("RECEI_EA", pData.getString("RECEI_EA"));
					map.put("supplierProd", supplierProd);

					errInfo = "success";
				} else {
					map.put("AUDIT_INFO", "料号在[入库单]中不存在!");
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
	@RequestMapping(value = "/hadProdCommon")
	@ResponseBody
	public Object hadProdCommon() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//boolean hadPd = stockmgrinService.isExists(pd.getString("INSTOCK_NO"));
			String customerId = pd.getString("CUSTOMER_ID");
			String supplierProd = pd.getString("PRODUCT_CODE").toUpperCase();
			logger.info("客户ID：{},供应商料号:{}",customerId,supplierProd);
			String actulProdId = BaseInfoCache.getInstance().getProdIdByCustomerIdAndScanCode(customerId,supplierProd);
			if(StringUtil.isEmpty(actulProdId)) {
				map.put("AUDIT_INFO", "料号在[对照表]中不存在!");
			} else {
				pd.put("PRODUCT_ID",actulProdId);
				PageData pData = stockmgrinService.findForReceiveCheck(pd);
				if (pData != null) {
					map.put("STOCKMGRIN_ID", pData.getString("STOCKMGRIN_ID"));
					map.put("CUSTOMER_ID", pData.getString("CUSTOMER_ID"));
					map.put("PRODUCT_CODE", pData.getString("PRODUCT_CODE"));
					map.put("RECEI_EA", pData.getString("RECEI_EA"));
					map.put("SUPPLIER_PROD", supplierProd);
					map.put("INV_NO",pData.getString("INV_NO"));
					map.put("REMARK1",pData.getString("REMARK1"));
					map.put("REMARK2",pData.getString("REMARK2"));
					map.put("REMARK3",pData.getString("REMARK3"));
					map.put("PACKAGE_QTY",pData.getString("PACKAGE_QTY"));
					map.put("COO",pData.getString("COO"));
					map.put("LOT_CODE",pData.getString("LOT_CODE"));
					map.put("DATE_CODE",pData.getString("DATE_CODE"));
					map.put("BIN_CODE",pData.getString("BIN_CODE"));
					errInfo = "success";
				} else {
					map.put("AUDIT_INFO", "料号在[入库单]中不存在!");
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
	@RequestMapping(value = "/hadEA")
	@ResponseBody
	public Object hadEA() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String customerId = pd.getString("CUSTOMER_ID");
			String supplierProd = pd.getString("SUPPLIER_PROD").toUpperCase();

			logger.info("客户ID：{},供应商料号:{}",customerId,supplierProd);

			String productId = BaseInfoCache.getInstance().getProdIdByCustomerIdAndScanCode(customerId,supplierProd);

			//检查EA是输入的真实料号
			pd.put("PRODUCT_ID",productId);
			PageData pData = stockmgrinService.findByProdCheck(pd);
			if (pData != null) {

				map.put("RECEI_EA",String.valueOf(pData.getInteger("RECEI_EA")));
				errInfo = "success";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}


	public Object hadInStock2() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			//boolean hadPd = stockmgrinService.isExists(pd.getString("INSTOCK_NO"));
			PageData pData = stockmgrinService.findByInStockNo(pd.getString("INSTOCK_NO"));
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


	@RequestMapping(value = "/auditBoxNo")
	@ResponseBody
	public Object auditBoxNo() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			String customer = pd.getString("CUSTOMER_ID");
			String instockNo = pd.getString("INSTOCK_NO");
			if (StringUtil.isEmpty(customer) && StringUtil.isNotEmpty(instockNo)) {
				customer = receivoptService.findByCustomerId(pd);
			}

			String box_no = auditBoxNo(pd.getString("BOX_NO"),instockNo,customer);
			if (StringUtil.isNotEmpty(box_no)) {
				map.put("auditRs", box_no);
				errInfo = "success";
			} else {
				//如果为空，说明该箱号符合规则，接下进行箱号重复校验
				if(receivoptService.isExistBox(pd)) {
					map.put("auditRs", "已经收货不能继续操作!");
					errInfo = "success";
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	//判断箱号是否存在，如果在同一张单的入库单下，可以重复
	@RequestMapping(value = "/auditCommonBoxNo")
	@ResponseBody
	public Object auditCommonBoxNo() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			String customer = pd.getString("CUSTOMER_ID");
			String instockNo = pd.getString("INSTOCK_NO");
			if (StringUtil.isEmpty(customer) && StringUtil.isNotEmpty(instockNo)) {
				customer = receivoptService.findByCustomerId(pd);
			}

			String box_no = auditBoxNo(pd.getString("BOX_NO"),instockNo,customer);
			if (StringUtil.isNotEmpty(box_no)) {
				map.put("auditRs", box_no);
				errInfo = "success";
			} else {
				//如果为空，说明该箱号符合规则，接下进行箱号重复校验
				if(receivoptService.isExistBoxExcSameBill(pd)) {
					map.put("auditRs", "已经收货不能继续操作!");
					errInfo = "success";
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "/auditSperaBoxNo")
	@ResponseBody
	public Object auditSperaBoxNo() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();

			String customer = pd.getString("CUSTOMER_ID");
			String instockNo = pd.getString("INSTOCK_NO");
			if (StringUtil.isEmpty(customer) && StringUtil.isNotEmpty(instockNo)) {
				customer = receivoptService.findByCustomerId(pd);
			}

			String box_no = auditBoxNo(pd.getString("BOX_NO"),instockNo,customer);
			if (StringUtil.isNotEmpty(box_no)) {
				map.put("auditRs", box_no);
				errInfo = "success";
			} else {
				//如果为空，说明该箱号符合规则，接下进行箱号重复校验
				/*if(receivoptService.isExistBox(pd)) {
					map.put("auditRs", "已经收货不能继续操作!");
					errInfo = "success";
				}*/
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
	@RequestMapping(value = "/auditBigBoxNo")
	@ResponseBody
	public Object auditBigBoxNo() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			String customer = pd.getString("CUSTOMER_ID");
			String instockNo = pd.getString("INSTOCK_NO");
			if (StringUtil.isEmpty(customer) && StringUtil.isNotEmpty(instockNo)) {
				customer = receivoptService.findByCustomerId(pd);
			}
			String box_no = auditBoxNo(pd.getString("BOX_NO"),instockNo,customer);
			if (StringUtil.isNotEmpty(box_no)) {
				map.put("auditRs", box_no);
				errInfo = "success";
			} else {
				/*//如果为空，说明该箱号符合规则，接下进行箱号重复校验
				if(receivoptService.isExistBox(pd)) {
					map.put("auditRs", "已经收货不能继续操作!");
					errInfo = "success";
				}*/
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}


	private static String auditBoxNo(String boxNo,String instockNo,String customerId) {
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
		String instock = instockNo.substring(0, 5);
		if(!instock.equals(customer)) {
			return "不是该客户的箱号";
		}

		String secode = boxNo.substring(5,6);
		if(!Const.BOX_M_SEC.contains(secode)) {
			return "第6位不是M,O,I其中一个";
		}

		String yy = boxNo.substring(6,8);
		if(!StringUtil.isNumeric(yy)) {
			return "第7,8位不是有效年份";
		}
    //TODO
		String third = boxNo.substring(8,11);
		Set<String> boxRuleSet = BaseInfoCache.getInstance().getBoxRule(customerId);
		if(!boxRuleSet.contains(third)) {
			return "第9-11位不是"+boxRuleSet.toString();
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
		logBefore(logger, "导出ReceivOpt到excel");
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
			List<PageData> varOList = receivoptService.listAll(pd);
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

	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/goSpreaExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		mv.addObject("pd", pd);
		mv.setViewName("instock/receivsprea_uploadexl");
		return mv;
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downSpreaExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "InboundImport.xlsx", "InboundImport.xlsx");
	}

	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value = "/readSpreaExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file
	) throws Exception {
		ModelAndView mv = this.getModelAndView();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}

		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "inExcel");                            //执行上传

			PageData pd = new PageData();

            //从excel读入的数据
			List<PageData> listPd = (List) ObjectExcelRead.readExcels(filePath, fileName, 2, 0, 0);    //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet

			if (listPd == null || listPd.isEmpty()) {
				pd.put("IMP_RS","EXCEL数据格式不对或没有数据...");

				mv.addObject("pd", pd);
				mv.addObject("xlsList", new ArrayList<String>());
				mv.addObject("msg", "success");
				mv.setViewName("instock/receivsprea_uploadexl");
				return mv;
			}

			//本次导入的数据分组(入库单号，产品ID，对应明细)
			Map<String,Map<String, List<PageData>>> impNoProdIdMap = new HashMap<>(2000);
			//数据库已存在的数据分组(入库单号，产品ID，对应明细)
			Map<String,Map<String, List<PageData>>> existNoProdIdMap = new HashMap<>(2000);

			List<PageData> auditRs = receivoptService.auditData(listPd, impNoProdIdMap, existNoProdIdMap);
			//如果校验数据结果不为空，说明excel中填入的数据有问题，不做导入处理
			if (auditRs != null && !auditRs.isEmpty()) {
				pd.put("IMP_RS","数据存在异常,请修改正确后重新导入...");

				mv.addObject("pd", pd);
				mv.addObject("xlsList", auditRs);
				mv.addObject("msg", "success");
				mv.setViewName("instock/receivsprea_uploadexl");
				return mv;
			}

			receivoptService.saveReceiveImp(impNoProdIdMap,existNoProdIdMap);
			  /*存入数据库操作======================================*/
			pd.put("IMP_SUCC","数据成功导入...");
			pd.put("success","success");
			mv.addObject("pd", pd);
			mv.addObject("xlsList", new ArrayList<String>());
			mv.addObject("msg", "success");


		}

		mv.setViewName("instock/receivsprea_uploadexl");
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
