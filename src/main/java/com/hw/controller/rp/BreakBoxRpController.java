package com.hw.controller.rp;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.rp.BreakboxRpService;
import com.hw.service.rp.InboundRpService;
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
 * 拆箱纪录
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Controller
@RequestMapping(value = "/breakbox")
public class BreakBoxRpController extends BaseController {

    String menuUrl = "breakbox/list.do"; //菜单地址(权限用)
    @Resource(name = "breakboxRpService")
    private BreakboxRpService breakboxRpService;


    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "列表Box");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            pd.put("CREATE_EMP",getCurUserName());
            page.setPd(pd);


            String customerId = pd.getString("CUSTOMER_ID");
            if (StringUtil.isEmpty(customerId)) {
                List<Select> customerList = SelectCache.getInstance().getAllCustomer();
                pd.put("CUSTOMER_ID",customerList.get(0).getId());
            }

            List<String> showItemList = BaseInfoCache.getInstance().getInboundColum(pd.getString("CUSTOMER_ID"));
            List<String> showItemNameList = BaseInfoCache.getInstance().getInboundNameColum(pd.getString("CUSTOMER_ID"));
            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(isSearch(searchFlag)) {
                varList = breakboxRpService.list(page);
            }

            setSelect(mv,pd.getString("CUSTOMER_ID"));
            mv.setViewName("rp/breakbox_list");
            mv.addObject("showItemList", showItemList);
            mv.addObject("showItemNameList", showItemNameList);
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value="/excelRp")
    public ModelAndView excelRp(){
        logBefore(logger, "导出拆箱记录excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            pd.put("EXP","1");
            Page page = new Page();
            pd.put("CREATE_EMP",getCurUserName());
            page.setPd(pd);

            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("titleName", "BreakBox");

            List<String> titles = breakboxRpService.getHeadList();
            dataMap.put("titles", titles);

            List<PageData> varList = breakboxRpService.listForExcel(page);
            dataMap.put("varList", varList);

           // StockNowExcelView erv = new StockNowExcelView();
            BreakBoxXlsxView erv = new BreakBoxXlsxView();
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    private void setSelect(ModelAndView mv,String customerId) {
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();
        customerId = StringUtil.isNotEmpty(customerId)?customerId:customerList.get(0).getId();
        List<Select> productList = SelectCache.getInstance().getAllProduct(customerId);;
      /*  if (BaseInfoCache.getInstance().isOutCustomer()) {
            productList = SelectCache.getInstance().getAllProduct(customerList.get(0).getId());
        } else {
            productList = SelectCache.getInstance().getAllProduct(null);
        }*/
        //List<Select> productList = SelectCache.getInstance().getAllProduct(null);
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
