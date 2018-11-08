package com.hw.controller.rp;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.rp.StockNowProsRpService;
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
 * @author Panda.HuangWei.
 * @since 2017-05-28 21:28.
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/stockproperties")
public class StockNowProsRpController extends BaseController {
    String menuUrl = "stockproperties/list.do"; //菜单地址(权限用)

    @Resource(name = "stockNowProsRpService")
    private StockNowProsRpService stockNowProsRpService;

    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "及时库存汇总");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {

            pd = this.getPageData();
            pd.put("CREATE_EMP", getCurUserName());
            page.setPd(pd);
            String customerId = pd.getString("CUSTOMER_ID");
            if (StringUtil.isEmpty(customerId)) {
                List<Select> customerList = SelectCache.getInstance().getAllCustomer();
                pd.put("CUSTOMER_ID", customerList.get(0).getId());
            }

            List<PageData> varList = new ArrayList<>();
            if (page.getCurrentPage() > 2) {
                varList = stockNowProsRpService.list(page);
            } else {
                String searchFlag = pd.getString("SEARCH_FLAG");
                if (StringUtil.isNotEmpty(searchFlag) && Const.DEL_YES.equals(searchFlag)) {
                    stockNowProsRpService.deleteRp(page);
                    List<PageData> list = stockNowProsRpService.calcStock(page);
                    stockNowProsRpService.saveCalc(list);
                    varList = stockNowProsRpService.list(page);
                }
            }

            setSelectList(mv);
            mv.setViewName("rp/stocknowpros_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value="/goLookStockNowDtl")
    public ModelAndView goLookStockNowDtl(Page page){
        logBefore(logger, "去及时库存汇总stocknowpros_look明细页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {

            page.setPd(pd);
            page.setShowCount(Const.SHOW_SIZE);
            List<PageData> varList = stockNowProsRpService.listLookDtl(page);

            setSelect(mv);
            mv.addObject("varList", varList);
            mv.setViewName("rp/stocknowpros_look");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value="/excelStockNow")
    public ModelAndView excelStockNow(){
        logBefore(logger, "导出即时库存汇总到excel");
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            pd.put("CREATE_EMP", getCurUserName());
            Page page = new Page();
            page.setPd(pd);

            Map<String,Object> dataMap = new HashMap<String,Object>();
            dataMap.put("titleName", "stockNow");

            List<String> titles = stockNowProsRpService.getRpHead();
            dataMap.put("titles", titles);

            List<PageData> varList = stockNowProsRpService.getRpDtl(page);
            dataMap.put("varList", varList);
            //  StockNowExcelView erv = new StockNowExcelView();
            StockNowXlsxView erv = new StockNowXlsxView();
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    private void setSelect(ModelAndView mv) {
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();
        List<Select> productList;
        if (BaseInfoCache.getInstance().isOutCustomer()) {
            productList = SelectCache.getInstance().getAllProduct(customerList.get(0).getId());
        } else {
            productList = SelectCache.getInstance().getAllProduct(null);
        }
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

    private void setSelectList(ModelAndView mv) {
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();
        List<Select> productList;
        if (BaseInfoCache.getInstance().isOutCustomer()) {
            productList = SelectCache.getInstance().getAllProduct(customerList.get(0).getId());
        } else {
            productList = SelectCache.getInstance().getAllProduct(null);
        }
        mv.addObject("customerList", customerList);
        mv.addObject("productList", productList);

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
