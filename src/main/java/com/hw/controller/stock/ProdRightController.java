package com.hw.controller.stock;

import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.stock.LocatorTfService;
import com.hw.service.stock.ProdRightService;
import com.hw.util.Const;
import com.hw.util.Jurisdiction;
import com.hw.util.PageData;
import com.hw.util.StringUtil;
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
@RequestMapping(value="/prodright")
public class ProdRightController extends BaseController {
	
	String menuUrl = "prodright/list.do"; //菜单地址(权限用)
	@Resource(name="prodRightService")
	private ProdRightService prodRightService;



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
				varList = prodRightService.list(page);
			}
			mv.setViewName("stock/prodright_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}


	@RequestMapping(value = "/goProdright")
	public ModelAndView goProdright(Page page) {
		logBefore(logger, "去手工分配assignout页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			List<Select> customerList = SelectCache.getInstance().getAllCustomer();
			mv.addObject("customerList", customerList);
			mv.setViewName("stock/prodright_edit");
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
	@RequestMapping(value="/saveProdright")
	public ModelAndView saveProdright() throws Exception{
		logBefore(logger, "冻结");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		prodRightService.saveLocatorTf(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
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
