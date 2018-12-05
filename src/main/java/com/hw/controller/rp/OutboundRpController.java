package com.hw.controller.rp;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.rp.InboundRpService;
import com.hw.service.rp.OutboundRpService;
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
 * 入库明细
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Controller
@RequestMapping(value = "/outbound")
public class OutboundRpController extends BaseController {

    String menuUrl = "outbound/list.do"; //菜单地址(权限用)
    @Resource(name = "outboundRpService")
    private OutboundRpService outboundRpService;


    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "列表Box");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            setSelect(mv);

            String customerId = pd.getString("CUSTOMER_ID");
            if (StringUtil.isEmpty(customerId)) {
                List<Select> customerList = SelectCache.getInstance().getAllCustomer();
                pd.put("CUSTOMER_ID",customerList.get(0).getId());
            }

            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(isSearch(searchFlag)) {
                varList = outboundRpService.list(page);
            }

            mv.setViewName("rp/outbound_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/excelRp")
    public ModelAndView excelRp() {
        logBefore(logger, "导出即时库存到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("EXP", "1");
            Page page = new Page();
            page.setPd(pd);

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("titleName", "Outbound");

            List<String> titles = outboundRpService.getListHead();
            dataMap.put("titles", titles);

            List<PageData> varOList = outboundRpService.listForExcel(page);
            dataMap.put("varList", varOList);
            //StockNowExcelView erv = new StockNowExcelView();
            StockNowXlsxView erv = new StockNowXlsxView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/listDync")
    public ModelAndView listDync(Page page) {
        logBefore(logger, "列表Box");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);
            setSelect(mv);

            String customerId = pd.getString("CUSTOMER_ID");
            if (StringUtil.isEmpty(customerId)) {
                List<Select> customerList = SelectCache.getInstance().getAllCustomer();
                pd.put("CUSTOMER_ID",customerList.get(0).getId());
            }

            List<String> showItemList = BaseInfoCache.getInstance().getOutboundColum(pd.getString("CUSTOMER_ID"));
            List<String> showItemNameList = BaseInfoCache.getInstance().getOutboundNameColum(pd.getString("CUSTOMER_ID"));
            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(isSearch(searchFlag)) {
                varList = outboundRpService.list(page);
            }

            mv.addObject("showItemList", showItemList);
            mv.addObject("showItemNameList", showItemNameList);

            mv.setViewName("rp/outbound_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/excelRpDync")
    public ModelAndView excelRpDync() {
        logBefore(logger, "导出即时库存到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("EXP", "1");
            Page page = new Page();
            page.setPd(pd);

            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("titleName", "Outbound");

            List<String> showItemList = BaseInfoCache.getInstance().getOutboundColum(pd.getString("CUSTOMER_ID"));
            List<String> showItemNameList = BaseInfoCache.getInstance().getOutboundNameColum(pd.getString("CUSTOMER_ID"));


            List<String> titles = new ArrayList<String>();
            titles.add("Ref");    //2
            titles.add("CartonID");    //2
            titles.add("Tran Date");    //2
            titles.add("Invoice#");    //3
            titles.add("Item Code");    //4
            titles.add("SPS");    //5
            titles.add("Remark(1)");    //6
            titles.add("Remark(2)");    //7
            titles.add("Lot Code");
            titles.add("Date Code");
            titles.add("Order Qty");
            titles.add("Separate Qty");
            titles.add("COO");
            titles.add("Shipper/Consignee");
            for (String colum :showItemList) {
                titles.add(colum);
            }

            dataMap.put("titles", titles);
            page.setShowCount(10000);
            List<PageData> varOList = outboundRpService.list(page);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("REF"));    //1
                vpd.put("var2", varOList.get(i).getString("CARTONID"));    //1
                vpd.put("var3", varOList.get(i).getString("TRAN_DATE"));    //3
                vpd.put("var4", varOList.get(i).getString("INVOICE_NO"));    //2
                vpd.put("var5", varOList.get(i).getString("ITEM_CODE"));    //3

                vpd.put("var6", varOList.get(i).getString("SPS"));    //4
                vpd.put("var7", varOList.get(i).get("REMARK1"));    //5
                vpd.put("var8", varOList.get(i).getString("REMARK2"));    //6
                vpd.put("var9", varOList.get(i).getString("LOT_CODE"));
                vpd.put("var10", varOList.get(i).getString("DATE_CODE"));

                vpd.put("var11", varOList.get(i).getString("ORDER_QTY").toString());
                vpd.put("var12", varOList.get(i).getString("SEPARATE_QTY"));
                vpd.put("var13", varOList.get(i).getString("COO"));
                vpd.put("var14", varOList.get(i).getString("SHIPPER"));

                int j = 15;
                for (String colum :showItemNameList) {
                    vpd.put("var"+j, varOList.get(i).getString(colum));
                    j += 1;
                }

                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            //StockNowExcelView erv = new StockNowExcelView();
            StockNowXlsxView erv = new StockNowXlsxView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
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
        List<Select> outStockWayList = SelectCache.getInstance().getOutStockWay();
        List<Select> outStockTypeList = SelectCache.getInstance().getOutStockType();
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
        mv.addObject("outStockWayList", outStockWayList);
        mv.addObject("outStockTypeList", outStockTypeList);
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
