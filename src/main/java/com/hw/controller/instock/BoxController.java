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

import com.hw.cache.SelectCache;
import com.hw.entity.base.Select;
import com.hw.service.instock.StockMgrInService;
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
import com.hw.service.instock.BoxService;

/**
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Controller
@RequestMapping(value="/box")
public class BoxController extends BaseController {
	
	String menuUrl = "box/list.do"; //菜单地址(权限用)
	@Resource(name="boxService")
	private BoxService boxService;
	@Resource(name="stockmgrinService")
	private StockMgrInService stockmgrinService;


	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表Box");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			setSelect(mv);
			setMutiple(pd);
			List<PageData>	varList = boxService.list(page);	//列出Box列表
			setMutipleAfter(pd);

			mv.setViewName("instock/box_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	private void setSelect(ModelAndView mv) {
		List<Select> customerList = SelectCache.getInstance().getAllCustomer();
		List<Select> productList = SelectCache.getInstance().getAllProduct(null);
		List<Select> storageList = SelectCache.getInstance().getAllStorage();
		List<Select> locatorList = SelectCache.getInstance().getdAllLocatorForShow();
		List<Select> stockInStateList = SelectCache.getInstance().getStockInState();
		List<Select> assignedList = SelectCache.getInstance().getAssigned();

		List<Select> putStateList = SelectCache.getInstance().getPutState();
		List<Select> pickStateList = SelectCache.getInstance().getPickState();
		List<Select> cargoStateList = SelectCache.getInstance().getCargoState();
		List<Select> depotTypeList = SelectCache.getInstance().getDepotType();
		List<Select> freezeStateList = SelectCache.getInstance().getFreezeState();

		List<Select> auditStateList = WmsEnum.AuditFlg.list;
		List<Select> auditTypeList = WmsEnum.AuditType.list;
		mv.addObject("auditStateList", auditStateList);
		mv.addObject("auditTypeList", auditTypeList);

		mv.addObject("customerList", customerList);
		mv.addObject("productList", productList);
		mv.addObject("storageList", storageList);
		mv.addObject("locatorList", locatorList);
		mv.addObject("stockInStateList", stockInStateList);
		mv.addObject("assignedList", assignedList);
		mv.addObject("putStateList", putStateList);
		mv.addObject("pickStateList", pickStateList);
		mv.addObject("cargoStateList", cargoStateList);
		mv.addObject("depotTypeList", depotTypeList);
		mv.addObject("freezeStateList", freezeStateList);
	}

	private void setMutiple(PageData pd) {
		//收货状态
		pd.put("RECEIPT_STATE_S1", pd.getString("RECEIPT_STATE_S"));
		//分配状态
		pd.put("ASSIGNED_STATE_S1", pd.getString("ASSIGNED_STATE_S"));
		//码放状态
		pd.put("PUT_STATE_S1", pd.getString("PUT_STATE_S"));
		//分配库存状态
		pd.put("ASSIGNED_STOCK_STATE_S1", pd.getString("ASSIGNED_STOCK_STATE_S"));
		//拣货状态
		pd.put("PICK_STATE_S1", pd.getString("PICK_STATE_S"));
		//配载状态
		pd.put("CARGO_STATE_S1", pd.getString("CARGO_STATE_S"));
		//发货状态
		pd.put("DEPOT_STATE_S1", pd.getString("DEPOT_STATE_S"));
		//冻结状态
		pd.put("FREEZE_STATE_S1", pd.getString("FREEZE_STATE_S"));

		//收货状态
		pd.put("RECEIPT_STATE_S", VaryUtils.genListOrNull(pd.getString("RECEIPT_STATE_S")));
		//分配状态
		pd.put("ASSIGNED_STATE_S", VaryUtils.genListOrNull(pd.getString("ASSIGNED_STATE_S")));
		//码放状态
		pd.put("PUT_STATE_S", VaryUtils.genListOrNull(pd.getString("PUT_STATE_S")));
         //分配库存状态
		pd.put("ASSIGNED_STOCK_STATE_S", VaryUtils.genListOrNull(pd.getString("ASSIGNED_STOCK_STATE_S")));
		//拣货状态
		pd.put("PICK_STATE_S", VaryUtils.genListOrNull(pd.getString("PICK_STATE_S")));
		//配载状态
		pd.put("CARGO_STATE_S", VaryUtils.genListOrNull(pd.getString("CARGO_STATE_S")));
		//发货状态
		pd.put("DEPOT_STATE_S", VaryUtils.genListOrNull(pd.getString("DEPOT_STATE_S")));
		//冻结状态
		pd.put("FREEZE_STATE_S", VaryUtils.genListOrNull(pd.getString("FREEZE_STATE_S")));
	}

	private void setMutipleAfter(PageData pd) {
		//收货状态
		pd.put("RECEIPT_STATE_S", pd.getString("RECEIPT_STATE_S1"));
		//分配状态
		pd.put("ASSIGNED_STATE_S", pd.getString("ASSIGNED_STATE_S1"));
		//码放状态
		pd.put("PUT_STATE_S", pd.getString("PUT_STATE_S1"));
		//分配库存状态
		pd.put("ASSIGNED_STOCK_STATE_S", pd.getString("ASSIGNED_STOCK_STATE_S1"));
		//拣货状态
		pd.put("PICK_STATE_S", pd.getString("PICK_STATE_S1"));
		//配载状态
		pd.put("CARGO_STATE_S", pd.getString("CARGO_STATE_S1"));
		//发货状态
		pd.put("DEPOT_STATE_S", pd.getString("DEPOT_STATE_S1"));
		//冻结状态
		pd.put("FREEZE_STATE_S", pd.getString("FREEZE_STATE_S1"));
	}
	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goLook")
	public ModelAndView goLook(){
		logBefore(logger, "去修改Box页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			pd = boxService.findById(pd);	//根据ID读取
			mv.setViewName("instock/box_look");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}










	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增Box");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BOX_ID", this.get32UUID());	//主键
		boxService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}


	/**
	 * 新增
	 */
	@RequestMapping(value="/saveAuditStock")
	public ModelAndView saveAuditStock() throws Exception{
		logBefore(logger, "新增Box");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("BOX_ID", this.get32UUID());	//主键
		boxService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除Box");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			boxService.delete(pd);
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
		logBefore(logger, "修改Box");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		boxService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	

	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增Box页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			mv.setViewName("instock/box_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
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

			PageData pData = stockmgrinService.findByInStockNo(pd.getString("INSTOCK_NO"));
			if (pData != null) {
				map.put("STOCKMGRIN_ID", pData.getString("STOCKMGRIN_ID"));
				errInfo = "success";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	@RequestMapping(value = "/hadStockProd")
	@ResponseBody
	public Object hadStockProd() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "error";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			map.put("pass", "PASS");
			PageData pData = stockmgrinService.findDtlPdByPidAndProd(pd);
			if (pData != null) {
				//说明已经有收获，则不能将该箱号挂到该入库单
				if(!WmsEnum.InstockState.NONE.getItemKey().equals(pData.getString("RECEIPT_STATE"))) {
					map.put("pass", "STOP");
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
	 * 去修改页面
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit(){
		logBefore(logger, "去修改Box页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = boxService.findById(pd);	//根据ID读取
			mv.setViewName("instock/box_edit");
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
		logBefore(logger, "批量删除Box");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				boxService.deleteAll(ArrayDATA_IDS);
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
	public ModelAndView exportExcel(Page page){
		logBefore(logger, "导出Box到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			page.setPd(pd);

			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = boxService.getExcelTitle();
			dataMap.put("titles", titles);

			setMutiple(pd);
			List<PageData> varList = boxService.getExcelContent(page);
			setMutipleAfter(pd);

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
