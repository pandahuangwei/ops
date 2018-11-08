package com.hw.controller.outstock;

import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.outstock.DepotOutService;
import com.hw.service.outstock.PickOutService;
import com.hw.util.Const;
import com.hw.util.PageData;
import com.hw.util.StringUtil;
import com.hw.util.VaryUtils;
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
 * 发货
 * Created by Panda.HuangWei on 2016/11/10.
 */
@Controller
@RequestMapping(value = "/depotout")
public class DepotOutController extends BaseController {

    String menuUrl = "depotout/list.do"; //菜单地址(权限用)
    @Resource(name = "depotOutService")
    private DepotOutService depotOutService;

    /*================================按出货单================================*/
    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "列表StockMgroUT");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            addCustomerId(pd);
            setSelect(mv, null, pd);
            setMutiple(pd);

            page.setPd(pd);
            List<PageData> varList = depotOutService.list(page);    //列出StockMgroUT列表
            setMutipleAfter(pd);
            mv.setViewName("outstock/depotout_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/list2")
    public ModelAndView list2(Page page) {
        logBefore(logger, "列表StockMgroUT");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            addCustomerId(pd);

            setSelect(mv, null, pd);
            setMutiple(pd);

            page.setPd(pd);
            List<PageData> varList = depotOutService.list2(page);    //列出StockMgroUT列表
            setMutipleAfter(pd);

            mv.setViewName("outstock/depotout_list_cargo");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/goDepotLook")
    public ModelAndView goDepotLook(Page page) {
        logBefore(logger, "去拣货pickout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_ID = pd.getString("STOCKMGROUT_ID");
            pd.put("STOCKMGROUT_ID", STOCKMGROUT_ID);

            page.setPd(pd);

            setSelect(mv);

            String optEven = page.getPd().getString("DEPOT_TYPE");
            pd.put("ASSIGN_NAME", "发货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            depotOutService.findDtlDepotOutLooklistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_ID", STOCKMGROUT_ID);
            pd.put("DEPOT_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/depotout_manual_look");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/goDepotManual")
    public ModelAndView goDepotManual(Page page) {
        logBefore(logger, "去拣货pickout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);

            page.setPd(pd);

            setSelect(mv);

            String optEven = page.getPd().getString("DEPOT_TYPE");
            pd.put("ASSIGN_NAME", "发货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            depotOutService.findDtlDepotOutSavelistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("DEPOT_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/depotout_manual");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/saveDepotManual")
    public ModelAndView saveDepotManual(Page page) {
        logBefore(logger, "按出库单ID发货...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);

            String opt_even = pd.getString("OPT_EVEN");

            depotOutService.saveDepot(pd);



            String optEven = pd.getString("CARGO_TYPE");
            pd.put("ASSIGN_NAME", "发货");
            page.setPd(pd);
            setSelect(mv);
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            depotOutService.findDtlDepotOutSavelistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("CARGO_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/depotout_manual");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/goDepotCacel")
    public ModelAndView goDepotCacel(Page page) {
        logBefore(logger, "去发货取消goDepotCacel页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);

            page.setPd(pd);

            setSelect(mv);

            String optEven = page.getPd().getString("DEPOT_TYPE");
            pd.put("ASSIGN_NAME", "发货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            depotOutService.findDtlDepotOutCacellistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("DEPOT_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/depotout_manual_cancel");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/saveDepotCancel")
    public ModelAndView saveDepotCancel(Page page) {
        logBefore(logger, "按出库单ID取消发货...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);

            depotOutService.saveDepotCancel(pd);

            page.setPd(pd);
            setSelect(mv);

            String optEven = page.getPd().getString("CARGO_TYPE");
            pd.put("ASSIGN_NAME", "发货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            depotOutService.findDtlDepotOutCacellistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("CARGO_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/depotout_manual_cancel");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/goDepotCargoLook")
    public ModelAndView goDepotCargoLook(Page page) {
        logBefore(logger, "去拣货pickout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String CARGOOUT_ID = pd.getString("CARGOOUT_ID");
            pd.put("CARGOOUT_ID", CARGOOUT_ID);
            page.setPd(pd);
            setSelect(mv);

            String optEven = page.getPd().getString("DEPOT_TYPE");
            pd.put("ASSIGN_NAME", "配载信息");
            List<PageData> assignList = new ArrayList<>();

            pd= depotOutService.findDtlDepotCargoLooklistPage(assignList, page);

            pd.put("CARGOOUT_ID", CARGOOUT_ID);
            pd.put("DEPOT_TYPE", optEven);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/depotout_detail_cargolook");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/goDepotCargoDepot")
    public ModelAndView goDepotCargoDepot(Page page) {
        logBefore(logger, "去拣货pickout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String CARGOOUT_ID = pd.getString("CARGOOUT_ID");
            if (StringUtil.isNotEmpty(CARGOOUT_ID)) {

                pd.put("CARGOOUT_ID", CARGOOUT_ID);

                pd.put("IDS", VaryUtils.genList(CARGOOUT_ID));
                page.setPd(pd);
                setSelect(mv);

                String optEven = page.getPd().getString("DEPOT_TYPE");
                pd.put("ASSIGN_NAME", "配载信息");
                List<PageData> varList = new ArrayList<>();
                List<PageData> assignList = new ArrayList<>();

                depotOutService.findDtlDepotCargolistPage(varList, assignList, page);

                pd.put("CARGOOUT_ID", CARGOOUT_ID);
                pd.put("DEPOT_TYPE", optEven);
                mv.addObject("varList", varList);
                mv.addObject("assignList", assignList);
            }
            mv.setViewName("outstock/depotout_manual_cargo");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/saveDepotCargo")
    public ModelAndView saveDepotCargo(Page page) {
        logBefore(logger, "按出库单ID发货...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String CARGOOUT_ID = pd.getString("CARGOOUT_ID");
            List<String> ids = VaryUtils.genListOrNull(CARGOOUT_ID);
            pd.put("CARGOOUT_ID", ids);
            pd.put("IDS", VaryUtils.genList(CARGOOUT_ID));
            String opt_even = pd.getString("OPT_EVEN");

            depotOutService.saveDepotCargo(pd);

            page.setPd(pd);
            setSelect(mv);

            String optEven = page.getPd().getString("CARGO_TYPE");
            pd.put("ASSIGN_NAME", "发货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            depotOutService.findDtlDepotCargolistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", CARGOOUT_ID);
            pd.put("CARGO_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/depotout_manual_cargo");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    @RequestMapping(value = "/goDepotCargoCacel")
    public ModelAndView goDepotCargoCacel(Page page) {
        logBefore(logger, "去拣货pickout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String CARGOOUT_ID = pd.getString("CARGOOUT_ID");
            if (StringUtil.isNotEmpty(CARGOOUT_ID)) {

                pd.put("CARGOOUT_ID", CARGOOUT_ID);

                pd.put("IDS", VaryUtils.genList(CARGOOUT_ID));
                page.setPd(pd);
                setSelect(mv);

                String optEven = page.getPd().getString("DEPOT_TYPE");
                pd.put("ASSIGN_NAME", "配载信息");
                List<PageData> varList = new ArrayList<>();
                List<PageData> assignList = new ArrayList<>();

                depotOutService.findDtlDepotCargoCacellistPage(varList, assignList, page);

                pd.put("CARGOOUT_ID", CARGOOUT_ID);
                pd.put("DEPOT_TYPE", optEven);
                mv.addObject("varList", varList);
                mv.addObject("assignList", assignList);
            }
            mv.setViewName("outstock/depotout_manual_cargocancel");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/saveDepotCargoCacel")
    public ModelAndView saveDepotCargoCacel(Page page) {
        logBefore(logger, "按出库单ID发货...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String CARGOOUT_ID = pd.getString("CARGOOUT_ID");
            List<String> ids = VaryUtils.genListOrNull(CARGOOUT_ID);
            pd.put("CARGOOUT_ID", ids);
            pd.put("IDS", VaryUtils.genList(CARGOOUT_ID));

            depotOutService.saveDepotCargoCancel(pd);

            page.setPd(pd);
            setSelect(mv);

            String optEven = page.getPd().getString("CARGO_TYPE");
            pd.put("ASSIGN_NAME", "发货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            depotOutService.findDtlDepotCargoCacellistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", CARGOOUT_ID);
            pd.put("CARGO_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/depotout_manual_cargo");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    private void setSelect(ModelAndView mv) {
        List<Select> allProduct = SelectCache.getInstance().getAllProduct(null);
        List<Select> assignedList = SelectCache.getInstance().getAssigned();
        List<Select> pickStateList = SelectCache.getInstance().getPickState();
        List<Select> storageList = SelectCache.getInstance().getAllStorage();
        List<Select> allLocatorForShowList = SelectCache.getInstance().getdAllLocatorForShow();
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();
        List<Select> cargoStateList = SelectCache.getInstance().getCargoState();
        List<Select> depotTypeList = SelectCache.getInstance().getDepotType();
        mv.addObject("customerList", customerList);
        mv.addObject("allLocatorForShowList",allLocatorForShowList);
        mv.addObject("allProductList", allProduct);
        mv.addObject("assignedList", assignedList);
        mv.addObject("pickStateList", pickStateList);
        mv.addObject("storageList", storageList);
        mv.addObject("cargoStateList", cargoStateList);
        mv.addObject("depotTypeList", depotTypeList);
    }

    //LOADED_TYPE_S  ASSIGNED_STATE_S OUTSTOCK_WAY_S DEPOT_STATE_S  PICK_STATE_S,OUTSTOCK_TYPE_S
    private void setMutiple(PageData pd) {
        pd.put("LOADED_TYPE_S1", pd.getString("LOADED_TYPE_S"));
        pd.put("ASSIGNED_STATE_S1", pd.getString("ASSIGNED_STATE_S"));
        pd.put("OUTSTOCK_WAY_S1", pd.getString("OUTSTOCK_WAY_S"));
        pd.put("DEPOT_STATE_S1", pd.getString("DEPOT_STATE_S"));
        pd.put("PICK_STATE_S1", pd.getString("PICK_STATE_S"));
        pd.put("OUTSTOCK_TYPE_S1", pd.getString("OUTSTOCK_TYPE_S"));
        pd.put("CARGO_STATE_S1", pd.getString("CARGO_STATE_S"));

        pd.put("LOADED_TYPE_S", VaryUtils.genListOrNull(pd.getString("LOADED_TYPE_S")));
        pd.put("ASSIGNED_STATE_S", VaryUtils.genListOrNull(pd.getString("ASSIGNED_STATE_S")));
        pd.put("OUTSTOCK_WAY_S", VaryUtils.genListOrNull(pd.getString("OUTSTOCK_WAY_S")));
        pd.put("DEPOT_STATE_S", VaryUtils.genListOrNull(pd.getString("DEPOT_STATE_S")));
        pd.put("PICK_STATE_S", VaryUtils.genListOrNull(pd.getString("PICK_STATE_S")));
        pd.put("OUTSTOCK_TYPE_S", VaryUtils.genListOrNull(pd.getString("OUTSTOCK_TYPE_S")));
        pd.put("CARGO_STATE_S", VaryUtils.genListOrNull(pd.getString("CARGO_STATE_S")));
    }

    private void setMutipleAfter(PageData pd) {
        pd.put("LOADED_TYPE_S", pd.getString("LOADED_TYPE_S1"));
        pd.put("ASSIGNED_STATE_S", pd.getString("ASSIGNED_STATE_S1"));
        pd.put("OUTSTOCK_WAY_S", pd.getString("OUTSTOCK_WAY_S1"));
        pd.put("DEPOT_STATE_S", pd.getString("DEPOT_STATE_S1"));
        pd.put("PICK_STATE_S", pd.getString("PICK_STATE_S1"));
        pd.put("OUTSTOCK_TYPE_S", pd.getString("OUTSTOCK_TYPE_S1"));
        pd.put("CARGO_STATE_S", pd.getString("CARGO_STATE_S1"));
    }

    private void setSelect(ModelAndView mv, List<Select> list, PageData pd) {
        String customerId = pd.getString("CUSTOMER_ID");
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();
        //入库类型\入库状态\码放状态\优先级
        List<Select> outStockTypeList = SelectCache.getInstance().getOutStockType();
        List<Select> priorityLevelList = SelectCache.getInstance().getPriorityLevel();
        List<Select> assignedList = SelectCache.getInstance().getAssigned();
        List<Select> depotTypeList = SelectCache.getInstance().getDepotType();
        List<Select> pickStateList = SelectCache.getInstance().getPickState();
        List<Select> loadTypeList = SelectCache.getInstance().getLoadType();
        List<Select> outStockWayList = SelectCache.getInstance().getOutStockWay();
        List<Select> pickRuleList = SelectCache.getInstance().getPickRule(customerId);
        List<Select> cargoStateList = SelectCache.getInstance().getCargoState();
        mv.addObject("cargoStateList", cargoStateList);
        //库存分配规则
        List<Select> stockAssignedRuleList = SelectCache.getInstance().getStockAssignedRule(customerId);

        List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
        mv.addObject("allProductList", allProduct);

        mv.addObject("pickRuleList", pickRuleList);
        mv.addObject("customerList", customerList);

        mv.addObject("outStockTypeList", outStockTypeList);
        mv.addObject("priorityLevelList", priorityLevelList);

        mv.addObject("assignedList", assignedList);
        mv.addObject("depotTypeList", depotTypeList);
        mv.addObject("pickStateList", pickStateList);
        mv.addObject("loadTypeList", loadTypeList);
        mv.addObject("outStockWayList", outStockWayList);
        mv.addObject("stockAssignedRuleList", stockAssignedRuleList);

        List<Select> productTypeList = SelectCache.getInstance().getProductType();
        List<Select> packRuleList = SelectCache.getInstance().getPackRule();
        List<Select> storageList = SelectCache.getInstance().getAllStorage();
        List<Select> locatorList = SelectCache.getInstance().getLocator(null);
        List<Select> currencyTypeList = SelectCache.getInstance().getCurrencyType();
        List<Select> carTypeList = SelectCache.getInstance().getCarType();
        List<Select> packUnitList = SelectCache.getInstance().getPackUnit();
        //承运人
        List<Select> tpHaulierList = SelectCache.getInstance().getTpHaulier();
        //库存周转规则
        List<Select> stockTurnRuleList = SelectCache.getInstance().getStockTurnRule();


        mv.addObject("productTypeList", productTypeList);
        mv.addObject("packRuleList", packRuleList);
        mv.addObject("storageList", storageList);
        mv.addObject("locatorList", locatorList);
        mv.addObject("currencyTypeList", currencyTypeList);
        mv.addObject("carTypeList", carTypeList);
        mv.addObject("packUnitList", packUnitList);
        mv.addObject("tpHaulierList", tpHaulierList);
        mv.addObject("stockTurnRuleList", stockTurnRuleList);

        List<Select> allLocatorForShowList = SelectCache.getInstance().getdAllLocatorForShow();

        mv.addObject("allLocatorForShowList",allLocatorForShowList);

        if (list != null) {
            list.addAll(customerList);
        }
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
