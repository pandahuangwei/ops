package com.hw.controller.stock;

import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.instock.BoxService;
import com.hw.service.stock.FreezeService;
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

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Controller
@RequestMapping(value="/freeze")
public class FreezeController extends BaseController {
	
	String menuUrl = "freeze/list.do"; //菜单地址(权限用)
	@Resource(name="freezeService")
	private FreezeService freezeService;

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
				varList = freezeService.list(page);
			}
			mv.setViewName("stock/freeze_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}

	/**
	 * 冻结
	 *//*
	@RequestMapping(value="/saveFreeze")
	public ModelAndView saveFreeze() throws Exception{
		logBefore(logger, "冻结");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		freezeService.saveFreeze(pd);

		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}*/

	@RequestMapping(value="/saveFreeze")
	@ResponseBody
	public Object saveFreeze() {
		logBefore(logger, "冻结");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			try {
				freezeService.saveFreeze(pd);
				pd.put("msg", "ok");
			}catch (Exception e){
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

	@RequestMapping(value="/saveFree")
	@ResponseBody
	public Object saveFree() {
		logBefore(logger, "释放");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			try {
				freezeService.saveFree(pd);
				pd.put("msg", "ok");
			}catch (Exception e){
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
