package com.hw.controller.outstock;

import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.base.CustomerService;
import com.hw.service.outstock.CargoOutService;
import com.hw.service.outstock.PickOutService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 拣货分配
 * Created by Panda.HuangWei on 2016/11/10.
 */
@Controller
@RequestMapping(value = "/cargoout")
public class CargoOutController extends BaseController {

    String menuUrl = "cargoout/list.do"; //菜单地址(权限用)
    @Resource(name = "cargoOutService")
    private CargoOutService cargoOutService;
    @Resource(name="customerService")
    private CustomerService customerService;

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
            List<PageData> varList = cargoOutService.list(page);    //列出StockMgroUT列表

            setMutipleAfter(pd);

            mv.setViewName("outstock/cargoout_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/goLook")
    public ModelAndView goLook(Page page) {
        logBefore(logger, "去查看ReceivOpt收货页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {

            page.setPd(pd);
            List<PageData> srcList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            setSelect(mv);
            List<Select> customerList = SelectCache.getInstance().getAllCustomer();
            mv.addObject("customerList", customerList);
            List<Select> depotTypeList = SelectCache.getInstance().getDepotType();
            List<Select> loadTypeList = SelectCache.getInstance().getLoadType();
            mv.addObject("depotTypeList", depotTypeList);
            mv.addObject("loadTypeList", loadTypeList);

            cargoOutService.findDtlCargoLooklistPage(srcList, assignList, page);    //根据ID读取
            mv.addObject("varList", srcList);
            mv.addObject("assignList", assignList);


            mv.setViewName("outstock/cargoout_look");
            mv.addObject("msg", "goLook");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/goCargoDetail")
    public ModelAndView goCargoDetail(Page page) {
        logBefore(logger, "去配载cargoout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("CARGO_STATE", WmsEnum.CargoState.NONE.getItemKey());
            pd.put("CARGO_TYPE",  pd.getString("CARGO_TYPE"));

            setSelect(mv);

            String optEven = pd.getString("CARGO_TYPE");
            pd.put("ASSIGN_NAME", "配载");
           /* //1 详情操作 ，2 缺省分配
            if(Const.OPT_EVEN_1.equals(optEven)) {
                //需要查询配载单头 与配载(已经拣货的纪录)明细

            } else if(Const.OPT_EVEN_2.equals(optEven)){
               //只需要查询配载明细(已经拣货的纪录)即可

            }*/
            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);

            pd.put("IDS",ids);
            page.setPd(pd);
            List<PageData> assignList = cargoOutService.findDtl2CargoOutlistPage(page);


            mv.addObject("varList", assignList);

            mv.setViewName("outstock/cargoout_detail");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/goCargoCancel")
    public ModelAndView goCargoCancel(Page page) {
        logBefore(logger, "去配载cargoout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("CARGO_STATE", WmsEnum.CargoState.NONE.getItemKey());
            pd.put("CARGO_TYPE",  pd.getString("CARGO_TYPE"));

            setSelect(mv);

            String optEven = pd.getString("CARGO_TYPE");
            pd.put("ASSIGN_NAME", "配载");

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);

            pd.put("IDS",ids);
            page.setPd(pd);
            List<PageData> assignList = cargoOutService.findDtl2CargoCancellistPage(page);


            mv.addObject("varList", assignList);

            mv.setViewName("outstock/cargoout_cancel");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 去拣货页面
     */
    @RequestMapping(value = "/saveCargo")
    public ModelAndView saveCargo(Page page) {
        logBefore(logger, "保存cargoout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("CARGO_STATE", WmsEnum.CargoState.NONE.getItemKey());
            pd.put("CARGO_TYPE",  pd.getString("CARGO_TYPE"));

            String optEven = pd.getString("CARGO_TYPE");
            pd.put("ASSIGN_NAME", "配载");

            cargoOutService.saveCargo(pd);

            setSelect(mv);
            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);

            pd.put("IDS",ids);
            page.setPd(pd);
            List<PageData> assignList = cargoOutService.findDtl2CargoOutlistPage(page);

            mv.addObject("varList", assignList);
            mv.setViewName("outstock/cargoout_detail");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去拣货页面
     */
    @RequestMapping(value = "/saveCargoCancel")
    public ModelAndView saveCargoCancel(Page page) {
        logBefore(logger, "保存cargoout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("CARGO_STATE", WmsEnum.CargoState.NONE.getItemKey());
            pd.put("CARGO_TYPE",  pd.getString("CARGO_TYPE"));

            String optEven = pd.getString("CARGO_TYPE");
            pd.put("ASSIGN_NAME", "配载");

            cargoOutService.saveCargo(pd);

            setSelect(mv);
            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);

            pd.put("IDS",ids);
            page.setPd(pd);
            List<PageData> assignList = cargoOutService.findDtl2CargoCancellistPage(page);

            mv.addObject("varList", assignList);
            mv.setViewName("outstock/cargoout_cancel");
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
        List<Select> carTypeList = SelectCache.getInstance().getCarType();

        mv.addObject("customerList", customerList);
        mv.addObject("allLocatorForShowList",allLocatorForShowList);
        mv.addObject("allProductList", allProduct);
        mv.addObject("assignedList", assignedList);
        mv.addObject("pickStateList", pickStateList);
        mv.addObject("storageList", storageList);
        mv.addObject("cargoStateList", cargoStateList);
        mv.addObject("carTypeList", carTypeList);
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
        //库存分配规则
        List<Select> stockAssignedRuleList = SelectCache.getInstance().getStockAssignedRule(customerId);
        mv.addObject("cargoStateList", cargoStateList);
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



    @RequestMapping(value = "/getCargoNo")
    @ResponseBody
    public Object getCargoNo() {
        Map<String,String> map = new HashMap<>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String cargoNo = customerService.getCargoNo();
            if (cargoNo == null) {
                return AppUtil.returnObject(pd, map);
            }

            map.put("cargoNo",cargoNo);

        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        //返回结果
        return AppUtil.returnObject(pd, map);
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
