package com.hw.controller.outstock;

import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.base.CustomerService;
import com.hw.service.outstock.AssignOutService;
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
@RequestMapping(value = "/assignout")
public class AssignOutController extends BaseController {

    String menuUrl = "assignout/list.do"; //菜单地址(权限用)
    @Resource(name = "stockmgroutService")
    private StockMgrOutService stockmgroutService;
    @Resource(name = "orderpropertyService")
    private OrderPropertyService orderpropertyService;
    @Resource(name = "customerService")
    private CustomerService customerService;

    @Resource(name = "assignOutService")
    private AssignOutService assignOutService;


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
            List<PageData> varList = assignOutService.list(page);    //列出StockMgroUT列表

            setMutipleAfter(pd);

            mv.setViewName("outstock/assignout_list");
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


    /**
     * 去查看页面
     */
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



            assignOutService.findDtlAssignOutlistPageforLook(srcList, assignList, page);    //根据ID读取
            mv.addObject("varList", srcList);
            mv.addObject("assignList", assignList);
            String customerId = null;
            if(srcList != null && !srcList.isEmpty()) {
                customerId = srcList.get(0).getString("CUSTOMER_ID");
            }
            setSelectLook(mv,customerId);
            List<Select> customerList = SelectCache.getInstance().getAllCustomer();
            mv.addObject("customerList", customerList);
            List<Select> depotTypeList = SelectCache.getInstance().getDepotType();
            List<Select> loadTypeList = SelectCache.getInstance().getLoadType();
            mv.addObject("depotTypeList", depotTypeList);
            mv.addObject("loadTypeList", loadTypeList);

            mv.setViewName("outstock/assignout_look");
            mv.addObject("msg", "goLook");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去自动分配页面
     */
    @RequestMapping(value = "/goAssignAuto")
    public ModelAndView goAssignAuto(Page page) {
        logBefore(logger, "去自动分配assignout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            pd.put("STOCKMGROUT_IDS", VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS")));
            page.setPd(pd);
            setSelect(mv, null, pd);

            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "自动分配");

            List<PageData> srcList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

           // assignOutService.findAutoAssignOutlistPage(srcList,assignList,page);    //根据ID读取
            assignOutService.findAssignOutAutolistPage(srcList,assignList,page);    //根据ID读取

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", srcList);
            mv.addObject("assignList", assignList);
            mv.setViewName("outstock/assignout_auto");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 自动分配
     */
    @RequestMapping(value = "/saveAuto")
    public ModelAndView saveAuto(Page page) throws Exception {
        logBefore(logger, "自动分配assignout");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("STOCKMGROUT_ID", this.get32UUID());    //主键
        pd.put("MODIFY_EMP", getCurUserName());    //修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间


        String optEven = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_1.equals(optEven)) {
            try {
                String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");

                //查询产品明细
                assignOutService.saveAssign(pd);

                pd.put("STOCKMGROUT_IDS", VaryUtils.genListOrNull(STOCKMGROUT_IDS));
                page.setPd(pd);
                List<PageData> srcList = new ArrayList<>();
                List<PageData> assignList = new ArrayList<>();

               // assignOutService.findAutoAssignOutlistPage(srcList,assignList,page);    //根据ID读取
                assignOutService.findAssignOutAutolistPage(srcList,assignList,page);    //根据ID读取
                pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
                setSelect(mv, null, pd);

                pd.put("ASSIGN_TYPE", optEven);
                mv.addObject("varList", srcList);
                mv.addObject("assignList", assignList);
                mv.setViewName("outstock/assignout_auto");
                mv.addObject("msg", "save");
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

    /**
     * 去手工分配页面
     */
    @RequestMapping(value = "/goAssignManual")
    public ModelAndView goAssignManual(Page page) {
        logBefore(logger, "去手工分配goAssignManual页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            pd.put("STOCKMGROUT_IDS", VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS")));
            page.setPd(pd);
            //setSelect(mv, null, pd);


            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "手工分配");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

           // assignOutService.findManualAssignOutlistPage(varList, assignList, page);
            assignOutService.findAssignOutManuallistPage(varList, assignList, page);

            String customerId = null;
            if(varList != null && !varList.isEmpty()) {
                customerId = varList.get(0).getString("CUSTOMER_ID");
            }
            setSelectLook(mv,customerId);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/assignout_manual");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去取消分配页面
     */
    @RequestMapping(value = "/goAssignCancel")
    public ModelAndView goAssignCancel(Page page) {
        logBefore(logger, "去取消分配assignout_cancel页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            pd.put("STOCKMGROUT_IDS", VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS")));
            page.setPd(pd);
            //setSelect(mv, null, pd);


            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "取消分配");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

           // assignOutService.findManualAssignOutlistPage(varList, assignList, page);
            assignOutService.findAssignOutCacellistPage(varList, assignList, page);

            String customerId = null;
            if(varList != null && !varList.isEmpty()) {
                customerId = varList.get(0).getString("CUSTOMER_ID");
            }
            setSelectLook(mv,customerId);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/assignout_cancel");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去手工分配选择箱号页面
     */
    @RequestMapping(value = "/goManualBox")
    public ModelAndView goManualBox(Page page) {
        logBefore(logger, "去手工分配assignout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            page.setShowCount(99);
            String STOCKDTLMGROUT_IDS = pd.getString("STOCKDTLMGROUT_IDS");
            String STOCKMGROUT_IDS_CHOSE = pd.getString("STOCKMGROUT_IDS_CHOSE");

            List<Select> assignedList = SelectCache.getInstance().getAssigned();
            List<Select> pickStateList = SelectCache.getInstance().getPickState();
            mv.addObject("assignedList", assignedList);
            mv.addObject("pickStateList", pickStateList);

            page.setPd(pd);
            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "手工分配");

            List<Select> locatorForShow = SelectCache.getInstance().getdAllLocatorForShow();
            mv.addObject("locatorForShow", locatorForShow);
            List<Select> allProduct = SelectCache.getInstance().getAllProduct(null);
            mv.addObject("allProductList", allProduct);

            List<PageData> srcList = new ArrayList<>();
            List<PageData> assignList= new ArrayList<>();

            assignOutService.findAssignBoxlist(srcList,assignList,page);
            StringBuffer sb = new StringBuffer();
            int count = 0;
            for (PageData r :srcList) {
                if (count != 0) {
                    sb.append(Const.SPLIT_COMMA) ;
                }
                sb.append(r.getString("PRODUCT_ID") + Const.SPLIT_LINE + r.getString("CAN_ASSIGN_EA"));
                count++;
            }
            pd.put("PRODIDS_QTY",sb.toString());

            mv.addObject("srcList", srcList);
            mv.addObject("varList", assignList);
            pd.put("STOCKDTLMGROUT_IDS", STOCKDTLMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);

            pd.put("STOCKMGROUT_IDS_CHOSE",STOCKMGROUT_IDS_CHOSE);
            pd.put("STOCKDTLMGROUT_IDS",STOCKMGROUT_IDS_CHOSE);
            mv.setViewName("outstock/assignout_manual_box");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 手工分配
     */
    @RequestMapping(value = "/saveManual")
    public ModelAndView saveManual(Page page) throws Exception {
        logBefore(logger, "手工分配assignout");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        String optEven = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_2.equals(optEven)) {
            try {
                String STOCKDTLMGROUT_IDS = pd.getString("STOCKDTLMGROUT_IDS");
                pd.put("STOCKDTLMGROUT_IDS", VaryUtils.genListOrNull(pd.getString("STOCKDTLMGROUT_IDS")));


                String STOCKDTLMGROUT_ID_CHOSE = pd.getString("STOCKDTLMGROUT_ID_CHOSE");

                //查询产品明细
                assignOutService.saveAssign(pd);

                pd.put("STOCKMGROUT_IDS", VaryUtils.genListOrNull(STOCKDTLMGROUT_ID_CHOSE));

               pd.put("STOCKMGROUT_IDS_CHOSE", STOCKDTLMGROUT_IDS);
                page.setPd(pd);

                List<PageData> srcList = new ArrayList<>();
                List<PageData> assignList= new ArrayList<>();

                assignOutService.findAssignBoxlist(srcList,assignList,page);

                mv.addObject("srcList", srcList);
                mv.addObject("varList", assignList);

                String customerId = null;
                if(srcList != null && !srcList.isEmpty()) {
                    customerId = srcList.get(0).getString("CUSTOMER_ID");
                }
                setSelectLook(mv,customerId);
                   //根据ID读取
                pd.put("STOCKMGROUT_IDS", STOCKDTLMGROUT_ID_CHOSE);
                //setSelect(mv, null, pd);

                pd.put("ASSIGN_TYPE", optEven);
                mv.setViewName("outstock/assignout_manual_box");
                mv.addObject("msg", "save");
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


    /**
     * 手工-整行取消分配
     */
    @RequestMapping(value = "/saveCancelManual1")
    public ModelAndView saveCancelManual1(Page page) throws Exception {
        logBefore(logger, "手工取消分配assignout");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        String optEven = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_4.equals(optEven)) {
            try {
                String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");

                pd.put("STOCKDTLMGROUT_ID_CHOSE", pd.getString("STOCKDTLMGROUT_ID_CHOSE"));
                //查询产品明细
                assignOutService.saveAssignCacel1(pd);


                pd.put("STOCKMGROUT_IDS", VaryUtils.genListOrNull(STOCKMGROUT_IDS));
                page.setPd(pd);


                List<PageData> varList = new ArrayList<>();
                List<PageData> assignList = new ArrayList<>();
               // assignOutService.findManualAssignOutlistPage(varList, assignList, page);
                assignOutService.findAssignOutCacellistPage(varList, assignList, page);

                String customerId = null;
                if(varList != null && !varList.isEmpty()) {
                    customerId = varList.get(0).getString("CUSTOMER_ID");
                }
                setSelectLook(mv,customerId);

                pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
                pd.put("ASSIGN_TYPE", optEven);
                mv.addObject("varList", varList);
                mv.addObject("assignList", assignList);

                pd.put("ASSIGN_TYPE", optEven);
                mv.setViewName("outstock/assignout_cancel");
                mv.addObject("msg", "save");
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


    /**
     * 手工分配 按已经分配的明细取消
     */
    @RequestMapping(value = "/saveCancelManual2")
    public ModelAndView saveCancelManual12(Page page) throws Exception {
        logBefore(logger, "手工取消分配2assignout");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        String optEven = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_4.equals(optEven)) {
            try {

                String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
                String STOCKDTLMGROUT_ID_CHOSE = pd.getString("STOCKDTLMGROUT_ID_CHOSE");


                pd.put("STOCKDTLMGROUT_ID_CHOSE", STOCKDTLMGROUT_ID_CHOSE);

                assignOutService.saveAssignCacel2(pd);


                pd.put("STOCKMGROUT_IDS", VaryUtils.genListOrNull(STOCKMGROUT_IDS));
                page.setPd(pd);

                List<PageData> varList = new ArrayList<>();
                List<PageData> assignList = new ArrayList<>();
                //assignOutService.findManualAssignOutlistPage(varList, assignList, page);
                assignOutService.findAssignOutCacellistPage(varList, assignList, page);

                String customerId = null;
                if(varList != null && !varList.isEmpty()) {
                    customerId = varList.get(0).getString("CUSTOMER_ID");
                }
                setSelectLook(mv,customerId);

                pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
                pd.put("ASSIGN_TYPE", optEven);
                mv.addObject("varList", varList);
                mv.addObject("assignList", assignList);

                pd.put("ASSIGN_TYPE", optEven);
                // mv.addObject("varList", varList);
                mv.setViewName("outstock/assignout_cancel");
                mv.addObject("msg", "save");
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


    /**
     * 去分配页面
     */
    @RequestMapping(value = "/goAssign")
    public ModelAndView goAssign(Page page) {
        logBefore(logger, "去分配assignout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            pd.put("STOCKMGROUT_IDS", VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS")));
            page.setPd(pd);
            setSelect(mv, null, pd);

            String optEven = page.getPd().getString("ASSIGN_TYPE");

            if (Const.OPT_EVEN_1.equals(optEven)) {
                pd.put("ASSIGN_NAME", "自动分配");
            } else if (Const.OPT_EVEN_2.equals(optEven)) {
                pd.put("ASSIGN_NAME", "手工分配");
            } else if (Const.OPT_EVEN_3.equals(optEven)) {
                pd.put("ASSIGN_NAME", "缺省分配");
            } else {
                pd.put("ASSIGN_NAME", "取消分配");
            }


            List<PageData> varList = null;//getListByOpt(page);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.setViewName("instock/assignopt_edit");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 判断产品是否存在
     */
    @RequestMapping(value = "/hadOne")
    @ResponseBody
    public Object hadOne() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            List<PageData> varOList = assignOutService.findPickOneForExl(pd);
            if (varOList == null || varOList.isEmpty()) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    @RequestMapping(value = "/auditQty")
    @ResponseBody
    public Object auditQty() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String rs = assignOutService.auditAssignQty(pd);
            if (StringUtil.isNotEmpty(rs)) {
                map.put("AUDIT_INFO", rs);
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

     //导出拣货单，单票
    @RequestMapping(value="/excelPickOne")
    public ModelAndView excelPickOne(){
        logBefore(logger, "导出拣货单excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{

            Page page = new Page();
            page.setPd(pd);

            Map<String,Object> dataMap = new HashMap<String,Object>();
            boolean isExist = true;
            String pickNo = assignOutService.findPickNo(pd);
            if(StringUtil.isEmpty(pickNo)) {
                pickNo = PickNoUtil.getPickOne();
                isExist = false;
            }
            dataMap.put("siRef",pickNo);

            List<PageData> varOList = assignOutService.findPickOneForExl(pd);
            if(varOList == null || varOList.isEmpty()) {
                return mv;
            }


            PageData tmp = varOList.get(0);
            dataMap.put("stockNo",tmp.getString("stockNo"));
            dataMap.put("sendTm",tmp.getString("sendTm"));

            dataMap.put("varList", varOList);
            PickOneExcelView erv = new PickOneExcelView();
            mv = new ModelAndView(erv,dataMap);
            if (!isExist) {
                pd.put("PICK_NO",pickNo);
                assignOutService.savePickNo(pd);
                PickNoUtil.setPickOne(pickNo);
            }
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }



    @RequestMapping(value="/excelPickMany")
    public ModelAndView excelPickMany(){
        logBefore(logger, "导出拣货单excel-many");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{

            Page page = new Page();
            page.setPd(pd);

            Map<String,Object> dataMap = new HashMap<String,Object>();
            Set<String> titleSet = new HashSet<>();
            List<PageData> varOList = assignOutService.findPickManyForExl(pd,titleSet);
            Map<String, String> pickNoMap = assignOutService.findPickNoMap(pd);
            String siRef = assignOutService.buildSiRef(titleSet,pickNoMap);

            if(varOList == null || varOList.isEmpty()) {
                return mv;
            }
            dataMap.put("stockNo",VaryUtils.genString(titleSet));
            dataMap.put("SiRef",siRef);

            dataMap.put("varList", varOList);
            PickManyExcelView erv = new PickManyExcelView();
            mv = new ModelAndView(erv,dataMap);

        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }


    /**
     * 判断产品是否存在
     */
    @RequestMapping(value = "/hadMany")
    @ResponseBody
    public Object hadMany() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            Set<String> titleSet = new HashSet<>();
            List<PageData> varOList = assignOutService.findPickManyForExl(pd,titleSet);
            if (varOList == null || varOList.isEmpty()) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    private void setSelectLook(ModelAndView mv,String customerId) {
        List<Select> allProduct = SelectCache.getInstance().getAllProduct(customerId);
        List<Select> assignedList = SelectCache.getInstance().getAssigned();
        List<Select> pickStateList = SelectCache.getInstance().getPickState();
        List<Select> storageList = SelectCache.getInstance().getAllStorage();
        List<Select> cargoStateList = SelectCache.getInstance().getCargoState();
        List<Select> allLocatorListForShow = SelectCache.getInstance().getdAllLocatorForShow();

        mv.addObject("cargoStateList", cargoStateList);
        mv.addObject("allProductList", allProduct);
        mv.addObject("assignedList", assignedList);
        mv.addObject("pickStateList", pickStateList);
        mv.addObject("storageList", storageList);
        mv.addObject("allLocatorListForShow", allLocatorListForShow);
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

        List<Select> allLocatorListForShow = SelectCache.getInstance().getdAllLocatorForShow();
        mv.addObject("productTypeList", productTypeList);
        mv.addObject("packRuleList", packRuleList);
        mv.addObject("storageList", storageList);
        mv.addObject("locatorList", locatorList);
        mv.addObject("currencyTypeList", currencyTypeList);
        mv.addObject("carTypeList", carTypeList);
        mv.addObject("packUnitList", packUnitList);
        mv.addObject("tpHaulierList", tpHaulierList);
        mv.addObject("stockTurnRuleList", stockTurnRuleList);
        mv.addObject("allLocatorListForShow", allLocatorListForShow);

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
