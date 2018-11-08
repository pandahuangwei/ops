package com.hw.controller.outstock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.base.CustomerService;
import com.hw.service.outstock.StockMgrOutService;
import com.hw.service.property.OrderPropertyService;
import com.hw.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Panda.HuangWei on 2016/11/10.
 */
@Controller
@RequestMapping(value="/stockmgrout")
public class StockMgrOutController  extends BaseController {

    String menuUrl = "stockmgrout/list.do"; //菜单地址(权限用)
    @Resource(name = "stockmgroutService")
    private StockMgrOutService stockmgroutService;
    @Resource(name = "orderpropertyService")
    private OrderPropertyService orderpropertyService;
    @Resource(name = "customerService")
    private CustomerService customerService;


    /**
     * 新增
     */
    @RequestMapping(value = "/save")
    public ModelAndView save(Page page) throws Exception {
        logBefore(logger, "新增StockMgrout");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("STOCKMGROUT_ID", this.get32UUID());    //主键
        pd.put("CREATE_EMP", getCurUserName());    //创建人
        pd.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间
        pd.put("MODIFY_EMP", getCurUserName());    //修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        pd.put("DEL_FLG", 0);    //删除
        pd.put("CONFIRM_STATE",0);

        //setDefalut(pd);
        pd.put("ASSIGNED_STATE", WmsEnum.AssignedState.NONE.getItemKey());
        pd.put("PICK_STATE", WmsEnum.PickState.NONE.getItemKey());
        pd.put("LOADED_TYPE", WmsEnum.LoadedState.NONE.getItemKey());
        pd.put("DEPOT_STATE", WmsEnum.DepotState.NONE.getItemKey());
        pd.put("CARGO_STATE", WmsEnum.CargoState.NONE.getItemKey());


        stockmgroutService.save(pd);
        String optEven = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_1.equals(optEven)) {
            try {
                List<Select> list = new ArrayList<>();



                pd = stockmgroutService.findById(pd);    //根据ID读取
                String customerId = pd.getString("CUSTOMER_ID");
                if (pd != null && StringUtil.isNotEmpty(customerId)) {
                  //  List<PageData> inOrderList = orderpropertyService.getPropertyOutOrder(customerId);
                    List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutOrder(customerId);
                    pd.put("CUSTOMERCODE",SelectCache.getInstance().getCustomerCode(customerId));
                    setPropertyValue(inOrderList, pd);
                    mv.addObject("inOrderList", inOrderList);
                }
                setSelect(mv, list, pd);
                //查询产品明细
                pd.put("STOCKDTLMGROUT_ID", null);
                page.setPd(pd);
                //List<PageData> dtl = stockmgroutService.findDtl(pd);
                List<PageData> dtl = stockmgroutService.findDtlName(page);

                mv.addObject("varList", dtl);

                mv.setViewName("outstock/stockmgrout_edit");
                mv.addObject("msg", "edit");
                mv.addObject("pd", pd);
            } catch (Exception e) {
                logger.error(e.toString(), e);
            }
        } else {
            mv.addObject("msg", "success");
            mv.setViewName("save_result");
        }


        return mv;
    }

    private void setDefalut(PageData pd) {
        List<Select> assignedList = SelectCache.getInstance().getAssigned();
        List<Select> depotTypeList = SelectCache.getInstance().getDepotType();
        List<Select> pickStateList = SelectCache.getInstance().getPickState();
        List<Select> loadTypeList = SelectCache.getInstance().getLoadType();
        List<Select> outStockWayList = SelectCache.getInstance().getOutStockWay();

        //分配状态
        if (pd != null) {
            pd.put("ASSIGNED_STATE", getFirstId(assignedList));
            pd.put("PICK_STATE", getFirstId(pickStateList));
            pd.put("LOADED_TYPE", getFirstId(loadTypeList));
            pd.put("DEPOT_STATE", getFirstId(depotTypeList));
        }
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) {
        logBefore(logger, "删除StockMgroUT");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } //校验权限
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            stockmgroutService.delete(pd);
            out.write("success");
            out.close();
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

    }

    @RequestMapping(value = "/saveConfirm")
    public ModelAndView saveConfirm(Page page) throws Exception {
        logBefore(logger, "出货确认");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("MODIFY_EMP", getCurUserName());    //修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        pd.put("CONFIRM_STATE", 0);

        String optEven = pd.getString("OPT_EVEN");

        if (Const.OPT_EVEN_3.equals(optEven)) {
            stockmgroutService.saveConfirm(pd);
        }

        try {
            List<Select> list = new ArrayList<>();



            pd = stockmgroutService.findById(pd);    //根据ID读取
            String customerId = pd.getString("CUSTOMER_ID");
            if (pd != null && StringUtil.isNotEmpty(customerId)) {
               // List<PageData> inOrderList = orderpropertyService.getPropertyOutOrder(customerId);
                List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutOrder(customerId);
                pd.put("CUSTOMERCODE", SelectCache.getInstance().getCustomerCode(customerId));
                setPropertyValue(inOrderList, pd);
                mv.addObject("inOrderList", inOrderList);
            }
            setSelect(mv, list, pd);
            String INSTOCK_STATE = pd == null ? null : pd.getString("CONFIRM_STATE");
            if (Const.ZERO_CH.equals(INSTOCK_STATE) || StringUtil.isEmpty(INSTOCK_STATE)) {
                pd.put("MODIFY_FLG", "1");
            } else {
                pd.put("MODIFY_FLG", "0");
            }

            //查询产品明细
            pd.put("STOCKDTLMGROUT_ID", null);
            page.setPd(pd);
            //List<PageData> dtl = stockmgroutService.findDtl(pd);
            List<PageData> dtl = stockmgroutService.findDtlName(page);

            mv.addObject("varList", dtl);

            mv.setViewName("outstock/stockmgrout_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit(Page page) throws Exception {
        logBefore(logger, "修改StockMgroUT");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        pd.put("MODIFY_EMP", getCurUserName());    //修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间


        String optEven = pd.getString("OPT_EVEN");
        if(StringUtil.isNotEmpty(optEven)) {
            stockmgroutService.edit(pd);
        }

        if(Const.OPT_EVEN_2.equals(optEven)) {
            mv.addObject("msg", "success");
            mv.setViewName("save_result");
            return mv;
        }

        try {
            List<Select> list = new ArrayList<>();

            pd = stockmgroutService.findById(pd);    //根据ID读取
            String customerId = pd.getString("CUSTOMER_ID");
            if (pd != null && StringUtil.isNotEmpty(customerId)) {
               // List<PageData> inOrderList = orderpropertyService.getPropertyOutOrder(customerId);
                List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutOrder(customerId);
                pd.put("CUSTOMERCODE",SelectCache.getInstance().getCustomerCode(customerId));
                setPropertyValue(inOrderList, pd);
                mv.addObject("inOrderList", inOrderList);

                setSelect(mv, list, pd);
            }

            String INSTOCK_STATE = pd == null?null:pd.getString("CONFIRM_STATE");
            if (Const.ZERO_CH.equals(INSTOCK_STATE) || StringUtil.isEmpty(INSTOCK_STATE)) {
                pd.put("MODIFY_FLG", "1");
            } else {
                pd.put("MODIFY_FLG", "0");
            }

            //查询产品明细
            pd.put("STOCKDTLMGROUT_ID", null);
            page.setPd(pd);
            //List<PageData> dtl = stockmgroutService.findDtl(pd);
            List<PageData> dtl = stockmgroutService.findDtlName(page);
            mv.addObject("varList", dtl);
            List<String> showItemList = BaseInfoCache.getInstance().getOutStockBatchColum(pd.getString("CUSTOMER_ID"));
            List<String> showItemNameList = BaseInfoCache.getInstance().getOutStockBatchColumNames(pd.getString("CUSTOMER_ID"));

            mv.addObject("showItemList", showItemList);
            mv.addObject("showItemNameList", showItemNameList);

            mv.setViewName("outstock/stockmgrout_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }

        return mv;
    }

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
            List<PageData> varList = stockmgroutService.list(page);    //列出StockMgroUT列表
            setMutipleAfter(pd);

            mv.setViewName("outstock/stockmgrout_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    //LOADED_TYPE_S  ASSIGNED_STATE_S OUTSTOCK_WAY_S DEPOT_STATE_S  PICK_STATE_S,OUTSTOCK_TYPE_S
    private void setMutiple(PageData pd) {
        pd.put("LOADED_TYPE_S1",pd.getString("LOADED_TYPE_S"));
        pd.put("ASSIGNED_STATE_S1",pd.getString("ASSIGNED_STATE_S"));
        pd.put("OUTSTOCK_WAY_S1",pd.getString("OUTSTOCK_WAY_S"));
        pd.put("DEPOT_STATE_S1",pd.getString("DEPOT_STATE_S"));
        pd.put("PICK_STATE_S1",pd.getString("PICK_STATE_S"));
        pd.put("OUTSTOCK_TYPE_S1",pd.getString("OUTSTOCK_TYPE_S"));
        pd.put("CARGO_STATE_S1",pd.getString("CARGO_STATE_S"));


        pd.put("LOADED_TYPE_S",VaryUtils.genListOrNull(pd.getString("LOADED_TYPE_S")));
        pd.put("ASSIGNED_STATE_S",VaryUtils.genListOrNull(pd.getString("ASSIGNED_STATE_S")));
        pd.put("OUTSTOCK_WAY_S",VaryUtils.genListOrNull(pd.getString("OUTSTOCK_WAY_S")));
        pd.put("DEPOT_STATE_S",VaryUtils.genListOrNull(pd.getString("DEPOT_STATE_S")));
        pd.put("PICK_STATE_S",VaryUtils.genListOrNull(pd.getString("PICK_STATE_S")));
        pd.put("OUTSTOCK_TYPE_S",VaryUtils.genListOrNull(pd.getString("OUTSTOCK_TYPE_S")));
        pd.put("CARGO_STATE_S",VaryUtils.genListOrNull(pd.getString("CARGO_STATE_S")));
    }

    private void setMutipleAfter(PageData pd) {
        pd.put("LOADED_TYPE_S", pd.getString("LOADED_TYPE_S1"));
        pd.put("ASSIGNED_STATE_S", pd.getString("ASSIGNED_STATE_S1"));
        pd.put("OUTSTOCK_WAY_S", pd.getString("OUTSTOCK_WAY_S1"));
        pd.put("DEPOT_STATE_S", pd.getString("DEPOT_STATE_S1"));
        pd.put("PICK_STATE_S", pd.getString("PICK_STATE_S1"));
        pd.put("OUTSTOCK_TYPE_S", pd.getString("OUTSTOCK_TYPE_S1"));
        pd.put("CARGO_STATE_S",pd.getString("CARGO_STATE_S1"));
    }

    private void setSelect(ModelAndView mv, List<Select> list, PageData pd) {
        String customerId = pd.getString("CUSTOMER_ID");
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();
        //入库类型\入库状态\码放状态\优先级
        List<Select> outStockTypeList = SelectCache.getInstance().getOutStockType();
        List<Select> stockInStateList = SelectCache.getInstance().getStockInState();
        List<Select> putStateList = SelectCache.getInstance().getPutState();
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
        List<Select> productList = SelectCache.getInstance().getAllProduct(customerId);

        mv.addObject("productList",productList);
        mv.addObject("productTypeList",productTypeList);
        mv.addObject("packRuleList",packRuleList);
        mv.addObject("storageList",storageList);
        mv.addObject("locatorList",locatorList);
        mv.addObject("currencyTypeList",currencyTypeList);
        mv.addObject("carTypeList",carTypeList);
        mv.addObject("packUnitList",packUnitList);
        mv.addObject("tpHaulierList",tpHaulierList);
        mv.addObject("stockTurnRuleList",stockTurnRuleList);

        if (list != null) {
            list.addAll(customerList);
        }
    }

    private String getFirstId(List<Select> list) {
        if (list == null || list.isEmpty()) {
            return Const.EMPTY_CH;
        }
        return list.get(0).getId();
    }


    /**
     * 去新增页面
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() {
        logBefore(logger, "去新增StockMgroUT页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<Select> customerList = new ArrayList<>();
            setSelect(mv, customerList, pd);
            pd.put("MODIFY_FLG","1");
            pd.put("STOCKDTLMGROUT_ID", null);

            //分配状态
            if (pd != null) {
                pd.put("ASSIGNED_STATE",WmsEnum.AssignedState.NONE.getItemKey() );
                pd.put("PICK_STATE",WmsEnum.PickState.NONE.getItemKey() );
                pd.put("LOADED_TYPE",WmsEnum.LoadedState.NONE.getItemKey() );
                pd.put("DEPOT_STATE", WmsEnum.DepotState.NONE.getItemKey());
            }

            //获取客户列表的第一次个值
            if (customerList != null && customerList.size() >= 1) {
                String customerId = customerList.get(0).getId();
               // List<PageData> inOrderList = orderpropertyService.getPropertyOutOrder(customerId);
                List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutOrder(customerId);
                mv.addObject("inOrderList", inOrderList);
            }
            pd.put("PRE_OUT_DATE",Tools.date2StrYYMMDD(new Date()));

            mv.setViewName("outstock/stockmgrout_edit");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit(Page page) {
        logBefore(logger, "去修改StockMgrout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();

        pd = this.getPageData();

        try {
            List<Select> list = new ArrayList<>();

            pd = stockmgroutService.findById(pd);    //根据ID读取
            setSelect(mv, list, pd);
            String customerId = pd.getString("CUSTOMER_ID");
            if (pd != null && StringUtil.isNotEmpty(customerId)) {
               // List<PageData> inOrderList = orderpropertyService.getPropertyOutOrder(customerId);
                List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutOrder(customerId);
                pd.put("CUSTOMERCODE",SelectCache.getInstance().getCustomerCode(customerId));
                setPropertyValue(inOrderList, pd);
                mv.addObject("inOrderList", inOrderList);
            }

            String INSTOCK_STATE = pd == null?null:pd.getString("CONFIRM_STATE");
            if (Const.ZERO_CH.equals(INSTOCK_STATE) || StringUtil.isEmpty(INSTOCK_STATE)) {
                pd.put("MODIFY_FLG","1");
            } else {
                pd.put("MODIFY_FLG","0");
            }



            //查询产品明细 PICK_STATE
            pd.put("STOCKDTLMGROUT_ID", null);
            page.setPd(pd);
            List<PageData> dtl = stockmgroutService.findDtlName(page);
            mv.addObject("varList", dtl);

            List<String> showItemList = BaseInfoCache.getInstance().getOutStockBatchColum(pd.getString("CUSTOMER_ID"));
            List<String> showItemNameList = BaseInfoCache.getInstance().getOutStockBatchColumNames(pd.getString("CUSTOMER_ID"));

            mv.addObject("showItemList", showItemList);
            mv.addObject("showItemNameList", showItemNameList);
            mv.setViewName("outstock/stockmgrout_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }


    private void setPropertyValue(List<PageData> inOrderList, PageData pd) {

        for (PageData e : inOrderList) {
            String propertyColum = e.getString("PROPERTY_COLUM");
            String value = pd.getString(propertyColum);
            if (StringUtil.isEmpty(value)) {
                e.put("PROPERTY_VALUE", Const.EMPTY_CH);
            } else {
                e.put("PROPERTY_VALUE", value);
            }
        }
    }


    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除StockMgroUT");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {
            return null;
        } //校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                stockmgroutService.deleteAll(ArrayDATA_IDS);
                pd.put("msg", "ok");
            } else {
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

    /**
     * 批量删除
     */
    @RequestMapping(value = "/deleteDtlAll")
    @ResponseBody
    public Object deleteDtlAll() {
        logBefore(logger, "批量删除StockMgroUT");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "dell")) {
            return null;
        } //校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if (null != DATA_IDS && !"".equals(DATA_IDS)) {
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                stockmgroutService.deleteDtlAll(ArrayDATA_IDS);
                pd.put("msg", "ok");
            } else {
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

    /*
     * 导出到excel
     * @return
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel() {
        logBefore(logger, "导出StockMgroUT到excel");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "cha")) {
            return null;
        }
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("进货单编号入货单编号");    //1
            titles.add("客户");    //2
            titles.add("入库类型");    //3
            titles.add("预计入库日期");    //4
            titles.add("实际入库日期");    //5
            titles.add("收货状态");    //6
            titles.add("码放状态");    //7
            titles.add("总毛重");    //8
            titles.add("总净重");    //9
            titles.add("总体积");    //10
            titles.add("总价");    //11
            titles.add("总期望EA数");    //12
            titles.add("优先级");    //13
            titles.add("订单号");    //14
            titles.add("费用录入已完成");    //15
            titles.add("备注");    //16
            titles.add("创建人");    //17
            titles.add("创建时间");    //18
            titles.add("修改人");    //19
            titles.add("修改时间");    //20
            titles.add("删除");    //21
            dataMap.put("titles", titles);
            List<PageData> varOList = stockmgroutService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for (int i = 0; i < varOList.size(); i++) {
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("INSTOCK_NO"));    //1
                vpd.put("var2", varOList.get(i).getString("CUSTOMER_ID"));    //2
                vpd.put("var3", varOList.get(i).getString("INSTOCK_TYPE"));    //3
                vpd.put("var4", varOList.get(i).getString("PRE_INSTOCK_DATE"));    //4
                vpd.put("var5", varOList.get(i).getString("REAL_INSTOCK_DATE"));    //5
                vpd.put("var6", varOList.get(i).getString("INSTOCK_STATE"));    //6
                vpd.put("var7", varOList.get(i).getString("PUT_STATE"));    //7
                vpd.put("var8", varOList.get(i).getString("TOTAL_WEIGHT"));    //8
                vpd.put("var9", varOList.get(i).getString("TOTAL_SUTTLE"));    //9
                vpd.put("var10", varOList.get(i).getString("TOTAL_VOLUME"));    //10
                vpd.put("var11", varOList.get(i).getString("TOTAL_PRICE"));    //11
                vpd.put("var12", varOList.get(i).getString("TOTAL_EA"));    //12
                vpd.put("var13", varOList.get(i).getString("PRIORITY_LEVEL"));    //13
                vpd.put("var14", varOList.get(i).getString("ORDER_NO"));    //14
                vpd.put("var15", varOList.get(i).getString("COST_STATE"));    //15
                vpd.put("var16", varOList.get(i).getString("MEMO"));    //16
                vpd.put("var17", varOList.get(i).getString("CREATE_EMP"));    //17
                vpd.put("var18", varOList.get(i).getString("CREATE_TM"));    //18
                vpd.put("var19", varOList.get(i).getString("MODIFY_EMP"));    //19
                vpd.put("var20", varOList.get(i).getString("MODIFY_TM"));    //20
                vpd.put("var21", varOList.get(i).getString("DEL_FLG"));    //21
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv, dataMap);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/getOutStockNo")
    @ResponseBody
    public Object getOutStockNo() {
        Map<String, String> map = new HashMap<>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String customerId = pd.getString("CUSTOMER_ID");
            String stockNo = customerService.getOutStockNo(customerId);
            if (stockNo == null) {
                return AppUtil.returnObject(pd, map);
            }

            map.put("StockNo", stockNo);

        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        //返回结果
        return AppUtil.returnObject(pd, map);
    }


    @RequestMapping(value = "/getOrderProperty")
    @ResponseBody
    public Object getOrderProperty() {
        Map<String, List<PageData>> map = new HashMap<>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String customerId = pd.getString("CUSTOMER_ID");
           // List<PageData> inOrderList = orderpropertyService.getPropertyOutOrder(customerId);
            List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutOrder(customerId);
            if (inOrderList == null || inOrderList.size() == 0) {
                return AppUtil.returnObject(pd, map);
            }

            map.put("inOrderList", inOrderList);

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

	/*-------明细操作-------------------------*/

    @RequestMapping(value = "/goAddDtl")
    public ModelAndView goAddDtl() {
        logBefore(logger, "去新增StockDtlMgrout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            List<Select> customerList = new ArrayList<>();

            //获取客户列表的第一次个值
           /* if (customerList != null && customerList.size() >=1) {
                String customerId = customerList.get(0).getId();
                List<PageData> inOrderList = orderpropertyService.getPropertyOutBatch(customerId);
                mv.addObject("inOrderList",inOrderList);
            }*/

            //实时找当前行号，如果超过11的话
            int ROW_NO = pd.getInteger("ROW_NO");
            if(ROW_NO >1) {
                int ROW_NO2 =   stockmgroutService.findRowNo(pd);
                pd.put("ROW_NO",ROW_NO2);
            }

            String customerId = pd.getString("CUSTOMER_ID");
            //List<PageData> inOrderList = orderpropertyService.getPropertyOutBatch(customerId);
            List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutBatch(customerId);
            mv.addObject("inOrderList", inOrderList);

            List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
            mv.addObject("allProductList",allProduct);
            setSelect(mv, customerList, pd);

            mv.setViewName("outstock/stockdtlmgrout_edit");
            mv.addObject("msg", "saveDtl");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 新增
     */
    @RequestMapping(value = "/saveDtl")
    public ModelAndView saveDtl() throws Exception {
        logBefore(logger, "新增StockDtlMgrIn");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();


        pd.put("STOCKDTLMGROUT_ID", this.get32UUID());    //主键
        pd.put("CREATE_EMP", getCurUserName());    //创建人
        pd.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间
        pd.put("MODIFY_EMP", getCurUserName());    //修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        pd.put("DEL_FLG", 0);    //删除

        stockmgroutService.saveDtl(pd);
        pd.put("CUSTOMERCODE",SelectCache.getInstance().getCustomerCode(pd.getString("CUSTOMER_ID")));
        setSelect(mv, null, pd);
        //查询产品明细
        //pd.put("CUSTOMERCODE",SelectCache.getInstance().getCustomerCode(customerId));
		/*pd.put("STOCKDTLMGRIN_ID",null);
		List<PageData> dtl = stockmgroutService.findDtl(pd);
		mv.addObject("varList", dtl);*/

        //pd = stockmgroutService.findById(pd);	//根据ID读取
        //mv.setViewName("instock/stockmgrout_edit");
        mv.setViewName("save_result");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);

        return mv;
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/editDtl")
    public ModelAndView editDtl() throws Exception {
        logBefore(logger, "修改StockMgroutDtl");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "edit")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        pd.put("MODIFY_EMP", getCurUserName());    //修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间

        stockmgroutService.editDtl(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value="/deleteDtl")
    public void deleteDtl(PrintWriter out){
        logBefore(logger, "删除StockMgrIn");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            stockmgroutService.deleteDtl(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }

    }

    /**
     * 去修改页面
     */
    @RequestMapping(value = "/goEditDtl")
    public ModelAndView goEditDtl() {
        logBefore(logger, "去修改StockDtlMgrout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {//list
            List<Select> list = new ArrayList<>();
            String MODIFY_FLG = pd.getString("MODIFY_FLG");

            pd = stockmgroutService.findDtlById(pd);    //根据ID读取

            pd.put("MODIFY_FLG",MODIFY_FLG);

            setSelect(mv, list, pd);
            String customerId = pd.getString("CUSTOMER_ID");
            if (pd != null && StringUtil.isNotEmpty(customerId)) {
               // List<PageData> inOrderList = orderpropertyService.getPropertyOutBatch(customerId);
                List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutBatch(customerId);
                setPropertyValue(inOrderList, pd);
                mv.addObject("inOrderList", inOrderList);

                List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
                mv.addObject("allProductList",allProduct);
            }

            String packRule = pd.getString("PACK_RULE");
            if(StringUtil.isNotEmpty(packRule)) {
                List<PageData> packRuleList = SelectCache.getInstance().getPackRuleListById(packRule);
                if(packRuleList != null &&  !packRuleList.isEmpty()) {
                    PageData eaPd = packRuleList.get(0);
                    setEaPd(pd,eaPd);
                }

            }


            mv.setViewName("outstock/stockdtlmgrout_edit");
            mv.addObject("msg", "editDtl");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }
    private void setEaPd(PageData rs,PageData src) {
        //B.EA_NUM,B.INPACK_NUM,B.BOX_NUM,B.PALLET_NUM ,B.OTHER_NUM,
        //	B.EA_IN,B.INPACK_IN,B.BOX_IN,B.PALLET_IN ,B.OTHER_IN,
        rs.put("EA_NUM_1",src.getString("EA_NUM"));
        rs.put("INPACK_NUM_1",src.getString("INPACK_NUM"));
        rs.put("BOX_NUM_1",src.getString("BOX_NUM"));
        rs.put("PALLET_NUM_1",src.getString("PALLET_NUM"));
        rs.put("OTHER_NUM_1",src.getString("OTHER_NUM"));

        rs.put("EA_OUT_1",src.getString("EA_OUT"));
        rs.put("INPACK_OUT_1",src.getString("INPACK_OUT"));
        rs.put("BOX_OUT_1",src.getString("BOX_OUT"));
        rs.put("PALLET_OUT_1",src.getString("PALLET_OUT"));
        rs.put("OTHER_OUT_1",src.getString("OTHER_OUT"));
    }

    @RequestMapping(value = "/getProdProperty")
    @ResponseBody
    public Object getProdProperty() {
        Map<String,List<PageData>> map = new HashMap<>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String productId = pd.getString("PRODUCT_ID");
            List<PageData> productList = SelectCache.getInstance().getProductList(productId);

            if (productList == null || productList.isEmpty()) {
                return AppUtil.returnObject(pd, map);
            }
            map.put("productList",productList);

        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        //返回结果
        return AppUtil.returnObject(pd, map);
    }

    @RequestMapping(value = "/getPackRule")
    @ResponseBody
    public Object getPackRule() {
        Map<String,List<PageData>> map = new HashMap<>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String packRuleId = pd.getString("PACK_RULE");
            List<PageData> packRuleList = SelectCache.getInstance().getPackRuleListById(packRuleId);

            if (packRuleList == null || packRuleList.isEmpty()) {
                return AppUtil.returnObject(pd, map);
            }
            map.put("packRuleList",packRuleList);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        //返回结果
        return AppUtil.returnObject(pd, map);
    }

    @RequestMapping(value = "/getLocatorList")
    @ResponseBody
    public Object getLocatorList() {
        Map<String,String> map = new HashMap<>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String storageId = pd.getString("STORAGE_ID");
            List<Select> allProduct = SelectCache.getInstance().getLocator(storageId);
            if (allProduct == null || allProduct.size() == 0) {
                return AppUtil.returnObject(pd, map);
            }
            for (Select p : allProduct) {
                map.put(p.getId(),p.getValue());
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        //返回结果
        return AppUtil.returnObject(pd, map);
    }

    @RequestMapping(value = "/getProdDtl")
    @ResponseBody
    public Object getProdDtl() {
        Map<String, List<PageData>> map = new HashMap<>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();

            pd.put("STOCKDTLMGRIN_ID", null);
            List<PageData> dtl = stockmgroutService.findDtl(pd);

            if (dtl == null || dtl.size() == 0) {
                return AppUtil.returnObject(pd, map);
            }

            map.put("varList", dtl);

        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        //返回结果
        return AppUtil.returnObject(pd, map);
    }

    @RequestMapping(value = "/getProductList")
    @ResponseBody
    public Object getProductList() {
        Map<String,String> map = new HashMap<>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String customerId = pd.getString("CUSTOMER_ID");
            List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
            if (allProduct == null || allProduct.size() == 0) {
                return AppUtil.returnObject(pd, map);
            }
            for (Select p : allProduct) {
                map.put(p.getId(),p.getValue());
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        //返回结果
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 判断产品是否存在
     */
    @RequestMapping(value = "/hadProd")
    @ResponseBody
    public Object hadProd() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            PageData hadPd = stockmgroutService.findDtlByPidAndProdId(pd);
            if (hadPd != null) {
                String rowNo = hadPd.getString("ROW_NO");
                map.put("rowno", rowNo);
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }
    /**
     * 打开上传EXCEL页面
     */
    @RequestMapping(value = "/goUploadExcel")
    public ModelAndView goUploadExcel() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        mv.addObject("pd", pd);
        mv.setViewName("outstock/stockdtlmgrout_uploadexl");
        return mv;
    }

    /**
     * 下载模版
     */
    @RequestMapping(value = "/downExcel")
    public void downExcel(HttpServletResponse response) throws Exception {
        FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Outstock.xls", "Outstock.xls");
    }

    /**
     * 从EXCEL导入到数据库
     */
    @RequestMapping(value = "/readExcel")
    public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file,
                                  @RequestParam(value = "STOCKMGROUT_ID", required = false) String stockMgroutId,
                                  @RequestParam(value = "CUSTOMER_ID", required = false) String customerId
    ) throws Exception {
        ModelAndView mv = this.getModelAndView();
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        }

        if (StringUtil.isEmpty(stockMgroutId) || StringUtil.isEmpty(customerId)) {
            return null;
        }

        if (null != file && !file.isEmpty()) {
            String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
            String fileName = FileUpload.fileUp(file, filePath, "outExcel");                            //执行上传

            PageData pd = new PageData();
            pd.put("STOCKMGROUT_ID", stockMgroutId);
            pd.put("CUSTOMER_ID", customerId);

            List<PageData> listPd = (List) ObjectExcelRead.readOutStockExcel(filePath, fileName, 2, 0, 0);    //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
            Map<String, PageData> productMap = SelectCache.getInstance().getProductMap(customerId);
            Set<String> prodCodeSet = SelectCache.getInstance().getProductSet(productMap);


            List<PageData> auditRsList = stockmgroutService.auditData(customerId,listPd, prodCodeSet);
            //如果校验数据结果不为空，说明excel中填入的数据有问题，不做导入处理
            if (auditRsList != null && !auditRsList.isEmpty()) {
                pd.put("IMP_RS","数据存在异常,请修改正确后重新导入...");
                pd.put("CUSTOMERCODE",SelectCache.getInstance().getCustomerCode(customerId));
                mv.addObject("pd", pd);
                mv.addObject("xlsList", auditRsList);
                mv.addObject("msg", "success");
                mv.setViewName("outstock/stockdtlmgrout_uploadexl");
                return mv;
            }

            //已经存在的明细
            List<PageData> hadList = stockmgroutService.findDtl(pd);
          //  List<PageData> inBatchList = orderpropertyService.getPropertyINBatch(customerId);
          //  List<PageData> inOrderList = BaseInfoCache.getInstance().getPropertyOutBatch(customerId);
            //起始行号
            int rowNo =(hadList == null || hadList.isEmpty() ? 0:hadList.size())+1;
           //新插入的列表、需要更新的列表
            List<PageData> insertList = new ArrayList<>();
            List<PageData> updateList = new ArrayList<>();
            for (int i = 0,size = listPd.size(); i < size; i++) {
                PageData rs = new PageData();
                String newProd = listPd.get(i).getString("var0");
                int newEa = listPd.get(i).getInteger("var1");

                PageData insertPd = productMap.get(newProd);
                String newProdID = insertPd.getString("PRODUCT_ID");

                boolean isExists = false;
                //如果该料号存在明细中，则需update
                for (PageData et2: hadList) {
                    String hadProdId = et2.getString("PRODUCT_ID");
                    if (newProdID.equals(hadProdId)) {
                        isExists = true;
                        int total = et2.getInteger("EA_EA") + newEa;
                        et2.put("EA_EA",total);
                        double volume = Double.parseDouble(et2.getString("UNIT_VOLUME"))*total;
                        et2.put("TOTAL_VOLUME",volume);
                        double price = Double.parseDouble(et2.getString("UNIT_PRICE"))*total;
                        et2.put("TOTAL_PRICE",price);

                        et2.put("MODIFY_EMP", getCurUserName());	//修改人
                        et2.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

                        et2.put("TIME_THR",notNull(listPd.get(i).getString("var2")));
                        et2.put("NUM_FOU",notNull(listPd.get(i).getString("var3")));
                        et2.put("NUM_SIX",notNull(listPd.get(i).getString("var4")));
                        et2.put("TIME_EIG",notNull(listPd.get(i).getString("var5")));
                        et2.put("TIME_NIG",notNull(listPd.get(i).getString("var6")));
                        et2.put("TIME_FIV",notNull(listPd.get(i).getString("var7")));
                        et2.put("TIME_SIX",notNull(listPd.get(i).getString("var8")));
                        et2.put("TIME_SEV",notNull(listPd.get(i).getString("var9")));
                        et2.put("TIME_FOU",notNull(listPd.get(i).getString("var10")));
                        et2.put("NUM_THR",notNull(listPd.get(i).getString("var11")));
                        et2.put("TXT_TWO",notNull(listPd.get(i).getString("var12")));
                        et2.put("NUM_NIG",notNull(listPd.get(i).getString("var13")));

                        updateList.add(et2);
                    }
                }
                //如果该料号不存在明细中，则需insert
                if(!isExists) {
                    insertPd.put("LONG_UT",insertPd.get("LONG_UNIT"));
                    insertPd.put("WIDE_UT",insertPd.get("WIDE_UNIT"));
                    insertPd.put("HIGH_UT",insertPd.get("HIGH_UNIT"));
                    insertPd.put("UNIT_VOLUME",insertPd.get("UNIT_VOLUME"));

                    insertPd.put("TOTAL_WEIGHT",insertPd.get("TOTAL_WEIGHT"));
                    insertPd.put("TOTAL_SUTTLE",insertPd.get("TOTAL_SUTTLE"));
                    insertPd.put("TOTAL_VOLUME",insertPd.get("TOTAL_VOLUME"));

                    insertPd.put("PRODUCT_TYPE",insertPd.get("PRODUCT_TYPE"));
                    insertPd.put("RULE_PACK",insertPd.get("RULE_PACK"));
                    insertPd.put("UNIT_PRICE",insertPd.get("UNIT_PRICE"));

                      /*  insertPd.put("OT_EA",insertPd.get("OTHER_NUM"));
                        insertPd.put("PALLET_EA",insertPd.get("PALLET_NUM"));
                        insertPd.put("BOX_EA",insertPd.get("BOX_NUM"));
                        insertPd.put("INPACK_EA",insertPd.get("INPACK_NUM"));*/
                    insertPd.put("EA_EA",newEa);

                    insertPd.put("TXT_TWO",notNull(listPd.get(i).getString("var2")));
                    insertPd.put("TXT_TWELVE",notNull(listPd.get(i).getString("var3")));
                    insertPd.put("TXT_FOURT",notNull(listPd.get(i).getString("var4")));
                    insertPd.put("TXT_SEV",notNull(listPd.get(i).getString("var5")));
                    insertPd.put("TXT_EIG",notNull(listPd.get(i).getString("var6")));
                    insertPd.put("TXT_FOU",notNull(listPd.get(i).getString("var7")));
                    insertPd.put("TXT_FIV",notNull(listPd.get(i).getString("var8")));
                    insertPd.put("TXT_SIX",notNull(listPd.get(i).getString("var9")));
                    insertPd.put("TXT_THIRT",notNull(listPd.get(i).getString("var10")));
                    insertPd.put("TXT_ELEVEN",notNull(listPd.get(i).getString("var11")));
                    insertPd.put("TXT_EIGHT",notNull(listPd.get(i).getString("var12")));
                    insertPd.put("TXT_SIXT",notNull(listPd.get(i).getString("var13")));


						/*{STOCKMGRIN_ID}, #{}, #{CUSTOMER_ID}, #{PRODUCT_ID}*/

                    insertPd.put("STOCKMGROUT_ID",stockMgroutId);
                    insertPd.put("ROW_NO",rowNo++);
                    insertPd.put("CUSTOMER_ID",customerId);
                    insertPd.put("STOCKDTLMGROUT_ID",UuidUtil.get32UUID());
                    insertPd.put("DEL_FLG","0");
                    insertPd.put("IMP_FLG","1");

                    insertPd.put("MEMO", "用户导入");
                    insertPd.put("CREATE_EMP", getCurUserName());	//创建人
                    insertPd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
                    insertPd.put("MODIFY_EMP", getCurUserName());	//修改人
                    insertPd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

                    // 分配状态 ASSIGNED_STATE 拣货状态 PICK_STATE 装车状态 LOADED_STATE 发货状态 DEPOT_STATE
                    insertPd.put("ASSIGNED_STATE",WmsEnum.AssignedState.NONE.getItemKey());
                    insertPd.put("PICK_STATE",WmsEnum.PickState.NONE.getItemKey());
                    insertPd.put("LOADED_STATE",WmsEnum.LoadedState.NONE.getItemKey());
                    insertPd.put("DEPOT_STATE",WmsEnum.DepotState.NONE.getItemKey());
                    insertPd.put("CARGO_STATE",WmsEnum.CargoState.NONE.getItemKey());
                    insertPd.put("ASSIGNED_EA",Const.ZERO);
                    insertPd.put("ASSIGNED_FLG",Const.ZERO);

                    pd.put("SEND_EA",Const.ZERO);
                    pd.put("PREPLAN_EA",Const.ZERO);
                    pd.put("PICK_EA",Const.ZERO);
                    pd.put("LOADED_EA",Const.ZERO);
                    pd.put("CARGO_TYPE",Const.ZERO);

                    insertPd.put("P_ID",insertPd.getString("STOCKDTLMGROUT_ID"));

                    insertList.add(insertPd);
                }
            }

            for (PageData e :insertList) {
                stockmgroutService.saveDtlExcel(e);
            }

            for (PageData e :updateList) {
                stockmgroutService.editExcel(e);
            }
            pd.put("CUSTOMERCODE",SelectCache.getInstance().getCustomerCode(customerId));
            /*存入数据库操作======================================*/
            pd.put("IMP_SUCC","数据成功导入...");
            pd.put("success","success");
            mv.addObject("pd", pd);
            mv.addObject("xlsList", new ArrayList<String>());
            mv.addObject("msg", "success");

        }

        mv.setViewName("outstock/stockdtlmgrout_uploadexl");
        return mv;
    }

    private String notNull(String str) {
        return StringUtil.isEmpty(str)?Const.EMPTY_CH:str;
    }
}
