package com.hw.controller.stock;

import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.stock.StockAuditService;
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
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 库存调整
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Controller
@RequestMapping(value = "/stockaudit")
public class StockAuditController extends BaseController {

    String menuUrl = "stockaudit/list.do"; //菜单地址(权限用)
    @Resource(name = "stockAuditService")
    private StockAuditService stockAuditService;


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

            setSelect(mv);
            pd.put("AUDIT_STATE_S", VaryUtils.genListOrNull(pd.getString("AUDIT_STATE_S")));
            page.setPd(pd);
            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(isSearch(searchFlag)) {
                varList = stockAuditService.list(page);
            }
            mv.setViewName("stock/stockaudit_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
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
     * 新增
     */
    @RequestMapping(value="/saveAuditAddApply")
    public ModelAndView saveAuditAddApply() throws Exception{
        logBefore(logger, "新增Box");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        stockAuditService.saveAuditAddApply(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }


    @RequestMapping(value="/goAuditQty")
    public ModelAndView goAuditQty(){
        logBefore(logger, "去新增Box页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            setSelect(mv);
            mv.setViewName("stock/stockaudit_qty");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value="/saveAuditQty")
    public ModelAndView saveAuditQty() throws Exception{
        logBefore(logger, "新增Box");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        stockAuditService.saveAuditQtyApply(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    @RequestMapping(value="/saveAuditDel")
    public void saveAuditDel(PrintWriter out){
        logBefore(logger, "删除箱号");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            stockAuditService.saveAuditDelApply(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
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


    /**
     * 审核列表
     */
    @RequestMapping(value = "/list2")
    public ModelAndView list2(Page page) {
        logBefore(logger, "列表Box");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();

            setSelectAudit(mv);
            pd.put("AUDIT_STATE_S", VaryUtils.genListOrNull(pd.getString("AUDIT_STATE_S")));
            page.setPd(pd);


            List<PageData> varList = stockAuditService.list2(page);
            mv.setViewName("stock/stockaudit_list2");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value="/saveAuditPass")
    public void saveAuditPass(PrintWriter out){
        logBefore(logger, "通过批准箱号");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            stockAuditService.saveAuditPass(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
    }

    @RequestMapping(value="/saveAudiReject")
    public void saveAudiReject(PrintWriter out){
        logBefore(logger, "驳回批准箱号");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            stockAuditService.editAuditReject(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
    }

    private void setSelectAudit(ModelAndView mv) {
        setSelect(mv);
        List<Select> userList = SelectCache.getInstance().getAllUser();


        mv.addObject("userList", userList);

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
