package com.hw.controller.stock;

import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.stock.LocatorTfService;
import com.hw.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Controller
@RequestMapping(value="/locatortf")
public class LocatorTfController extends BaseController {
	
	String menuUrl = "locatortf/list.do"; //菜单地址(权限用)
	@Resource(name="locatorTfService")
	private LocatorTfService locatorTfService;



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
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
				varList = locatorTfService.list(page);
			}
			mv.setViewName("stock/locatortf_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}


	@RequestMapping(value = "/goLocatorTf")
	public ModelAndView goLocatorTf(Page page) {
		logBefore(logger, "去手工分配assignout页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<Select> storageList = SelectCache.getInstance().getAllStorage();
			List<Select> locatorList = SelectCache.getInstance().getdAllLocatorForShow();

			mv.addObject("storageList", storageList);
			mv.addObject("locatorList", locatorList);
			mv.setViewName("stock/locatortf_edit");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 冻结
	 */
	@RequestMapping(value="/saveLocatortf")
	public ModelAndView saveLocatortf() throws Exception{
		logBefore(logger, "冻结");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
        locatorTfService.saveLocatorTf(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
			mv.setViewName("stock/putscan_scan");
			mv.addObject("msg", "save");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return mv;
	}


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

		//putLocatorOptService.savePutScan(pd);

		mv.setViewName("stock/putscan_scan");
		pd.put("success","success");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);

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
