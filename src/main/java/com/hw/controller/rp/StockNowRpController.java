package com.hw.controller.rp;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.rp.StockReportService;
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
import java.util.*;

/**
 * 即时库存 库存明细
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Controller
@RequestMapping(value = "/stocknow")
public class StockNowRpController extends BaseController {

    String menuUrl = "stocknow/list.do"; //菜单地址(权限用)
    @Resource(name = "stockReportService")
    private StockReportService stockReportService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "及时库存明细");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();

            addCustomerId(pd);
            setSelect(mv,pd.getString("CUSTOMER_ID"));

            page.setPd(pd);
            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
                varList = stockReportService.listStockNow(page);
            }
            mv.setViewName("rp/stocknow_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value="/goLookStockNowDtl")
    public ModelAndView goLookStockNowDtl(Page page){
        logBefore(logger, "去及时库存明细页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            page.setPd(pd);

            List<PageData> varList =  stockReportService.findStockDtlListForShow(page);
            setSelect(mv,pd.getString("CUSTOMER_ID"));

            mv.addObject("varList", varList);
            mv.setViewName("rp/stocknow_look");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/list2")
    public ModelAndView list2(Page page) {
        logBefore(logger, "库存明细stocknow_list2");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            addCustomerId(pd);
            setSelect(mv,pd.getString("CUSTOMER_ID"));
            page.setPd(pd);
            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
                varList = stockReportService.listStockNowDtl(page);
            }

            mv.setViewName("rp/stocknow_list2");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    //

    @RequestMapping(value="/excelStockNow")
    public ModelAndView excelStockNow(){
        logBefore(logger, "导出即时库存到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            pd.put("EXP","1");
            Page page = new Page();
            page.setPd(pd);

            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("titleName", "stockNow");

            List<String> titles = new ArrayList<String>();
            titles.add("产品");	//2
            titles.add("客户");	//3
            titles.add("库存EA");	//4
            titles.add("分配EA");	//5
            titles.add("冻结EA");	//6
            titles.add("可用EA");	//7
            titles.add("导出时间");

            dataMap.put("titles", titles);
            List<PageData> varOList = stockReportService.listStockNow(page);
            List<PageData> varList = new ArrayList<PageData>();
            for(int i=0;i<varOList.size();i++){
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("PRODUCT_CODE"));	//1
                vpd.put("var2", varOList.get(i).getString("CUSTOMER_CN"));	//2
                vpd.put("var3", varOList.get(i).getString("TOTAL_EA"));	//3
                vpd.put("var4", varOList.get(i).getString("ASSIGNED_EA"));	//4
                vpd.put("var5", varOList.get(i).get("FREEZE_EA").toString());	//5
                vpd.put("var6", varOList.get(i).getString("USE_EA"));	//6
                vpd.put("var7", varOList.get(i).getString("CREATE_TM"));
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
          //  StockNowExcelView erv = new StockNowExcelView();
            StockNowXlsxView erv = new StockNowXlsxView();
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value="/excelStockNowDtl")
    public ModelAndView excelStockNowDtl(){
        logBefore(logger, "导出即时库存到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            pd.put("EXP","1");
            Page page = new Page();
            page.setPd(pd);

            Map<String,Object> dataMap = new HashMap<String,Object>();

            dataMap.put("titleName", "stockNowDtl");

            List<String> titles = stockReportService.getStockDtlHead();

            dataMap.put("titles", titles);
            List<PageData> varList = stockReportService.listStockDtlForExl(page);
            dataMap.put("varList", varList);
          //  StockNowExcelView erv = new StockNowExcelView();
            StockNowXlsxView erv = new StockNowXlsxView();
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    private void setSelect(ModelAndView mv,String customerId) {
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();
       /* List<Select> productList;
        if (BaseInfoCache.getInstance().isOutCustomer()) {
            productList = SelectCache.getInstance().getAllProduct(customerList.get(0).getId());
        } else {
            productList = SelectCache.getInstance().getAllProduct(null);
        }*/
        customerId = StringUtil.isEmpty(customerId)?customerList.get(0).getId():customerId;
        List<Select> productList = SelectCache.getInstance().getAllProduct(customerId);
        List<Select> storageList = SelectCache.getInstance().getAllStorage();
        List<Select> locatorList = SelectCache.getInstance().getdAllLocatorForShow();
        List<Select> stockInStateList = SelectCache.getInstance().getStockInState();
        List<Select> assignedList = SelectCache.getInstance().getAssigned();

        List<Select> putStateList = SelectCache.getInstance().getPutState();
        List<Select> pickStateList = SelectCache.getInstance().getPickState();
        List<Select> cargoStateList = SelectCache.getInstance().getCargoState();
        List<Select> depotTypeList = SelectCache.getInstance().getDepotType();
        List<Select> freezeStateList = SelectCache.getInstance().getFreezeState();
        List<Select> rpSearchTypeList = SelectCache.getInstance().findRpSearchType();

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
        mv.addObject("rpSearchTypeList", rpSearchTypeList);
    }


    /* ===============================权限================================== */
    public Map<String, String> getHC() {
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>) session.getAttribute(Const.SESSION_QX);
    }
    /* ===============================权限================================== */

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}
