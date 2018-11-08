package com.hw.controller.outstock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.base.CustomerService;
import com.hw.service.outstock.AssignOutService;
import com.hw.service.outstock.PickOutService;
import com.hw.service.outstock.StockMgrOutService;
import com.hw.service.property.OrderPropertyService;
import com.hw.util.*;
import org.apache.commons.lang3.tuple.Pair;
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
@RequestMapping(value = "/pickout")
public class PickOutController extends BaseController {

    String menuUrl = "pickout/list.do"; //菜单地址(权限用)
    @Resource(name = "pickOutService")
    private PickOutService pickOutService;


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
            List<PageData> varList = pickOutService.list(page);    //列出StockMgroUT列表
            setMutipleAfter(pd);
            mv.setViewName("outstock/pickout_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
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
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_ID"));
            pd.put("IDS",ids);
            page.setPd(pd);
            List<PageData> srcList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            setSelect(mv);
           /* List<Select> customerList = SelectCache.getInstance().getAllCustomer();
            mv.addObject("customerList", customerList);*/


            pickOutService.findDtlAssignOutlistPage(srcList, assignList, page);    //根据ID读取
            mv.addObject("varList", srcList);
            mv.addObject("assignList", assignList);


            mv.setViewName("outstock/pickout_look");
            mv.addObject("msg", "goLook");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 去拣货页面
     */
    @RequestMapping(value = "/goPickManual")
    public ModelAndView goPickManual(Page page) {
        logBefore(logger, "去拣货pickout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("IDS", ids);
            page.setPd(pd);

            setSelect(mv);

            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "拣货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

           // pickOutService.findDtlPickOutlistPage(varList, assignList, ids);
            pickOutService.findDtlPickOutManuallistPage(varList, assignList, page);


            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/pickout_manual");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 保存拣货信息
     */
    @RequestMapping(value = "/savePickManual")
    public ModelAndView savePickManual(Page page) {
        logBefore(logger, "按出库单ID拣货...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("IDS", ids);

            String opt_even = pd.getString("OPT_EVEN");
            if(Const.OPT_EVEN_1.equals(opt_even)) {
                pickOutService.savePickManual1(pd);
            } else if(Const.OPT_EVEN_2.equals(opt_even)) {
                pickOutService.savePickManual2(pd);
            }
            pd.put("IDS", ids);
            page.setPd(pd);

            setSelect(mv);

            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "拣货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            //pickOutService.findDtlPickOutlistPage(varList, assignList, ids);
            pickOutService.findDtlPickOutManuallistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/pickout_manual");
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
    @RequestMapping(value = "/goPickManualSprea")
    public ModelAndView goPickManualSprea(Page page) {
        logBefore(logger, "去拣货Sprea - pickout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {

            page.setPd(pd);



            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "拣货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            pd.put("CNT_SCAN",0);
            pd.put("BIG_BOX_SUBM", Const.DEL_NO);
            mv.setViewName("outstock/pickout_sprea");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/goPickManualCommon")
    public ModelAndView goPickManualCommon(Page page) {
        logBefore(logger, "去通用拣货pickout_common页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            page.setPd(pd);
            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "通用拣货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            pd.put("CNT_SCAN",0);
            pd.put("BIG_BOX_SUBM", Const.DEL_NO);
            mv.setViewName("outstock/pickout_common");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/savePickManualCommon")
    public ModelAndView savePickManualCommon(Page page) {
        logBefore(logger, "Sprea pick box...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            page.setPd(pd);
            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "通用拣货");
            int pickProd = pd.getInteger("PICK_PROS");
            if (pickProd == 1){
                pickOutService.savePickByBatch(pd);
            }else {
                pickOutService.savePickManualSprea(pd);
            }

            List<PageData> varList = new ArrayList<>();
            pickOutService.findDtlPickOutSpreaListPage(varList, page);

            setSelect(mv);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            if (StringUtil.isNotEmpty(pd.getString("BOX_NO"))) {
                int CNT_SCAN=  pd.getInteger("CNT_SCAN")+1;
                pd.put("CNT_SCAN",CNT_SCAN);
            } else {
                pd.put("CNT_SCAN",0);
            }
            pd.put("PICK_PROS",0);
            pd.put("BIG_BOX_SUBM", Const.DEL_NO);
            mv.setViewName("outstock/pickout_common");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value = "/savePickManualSprea")
    public ModelAndView savePickManualSprea(Page page) {
        logBefore(logger, "Sprea pick box...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            page.setPd(pd);

            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "拣货");
            int pickProd = pd.getInteger("PICK_PROS");
            if (pickProd == 1){
                pickOutService.savePickByBatch(pd);
            }else {
                pickOutService.savePickManualSprea(pd);
            }

            List<PageData> varList = new ArrayList<>();
            pickOutService.findDtlPickOutSpreaListPage(varList, page);

            setSelect(mv);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            if (StringUtil.isNotEmpty(pd.getString("BOX_NO"))) {
                int CNT_SCAN=  pd.getInteger("CNT_SCAN")+1;
                pd.put("CNT_SCAN",CNT_SCAN);
            } else {
                pd.put("CNT_SCAN",0);
            }
            pd.put("PICK_PROS",0);
            pd.put("BIG_BOX_SUBM", Const.DEL_NO);
            mv.setViewName("outstock/pickout_sprea");
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
    @RequestMapping(value = "/goPickCancel")
    public ModelAndView goPickCancel(Page page) {
        logBefore(logger, "去拣货pickout页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("IDS", ids);
            page.setPd(pd);

            setSelect(mv);

            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "拣货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            pickOutService.findDtlPickCacellistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/pickout_cancel");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 保存拣货信息
     */
    @RequestMapping(value = "/saveCancelManual")
    public ModelAndView saveCancelManual(Page page) {
        logBefore(logger, "按出库单ID拣货...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("IDS", ids);

            String opt_even = pd.getString("OPT_EVEN");
            if(Const.OPT_EVEN_1.equals(opt_even)) {
                pickOutService.savePickCacel1(pd);
            } else if(Const.OPT_EVEN_2.equals(opt_even)) {
                pickOutService.savePickCacel2(pd);
            }

            page.setPd(pd);

            setSelect(mv);

            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "拣货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

           // pickOutService.findDtlPickOutlistPage(varList, assignList, ids);
            pickOutService.findDtlPickCacellistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/pickout_cancel");
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
    @RequestMapping(value = "/goBeakBox")
    public ModelAndView goBeakBox(Page page) {
        logBefore(logger, "去拆箱页面页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_ID_CHOSE");
            page.setPd(pd);
            List<Select> allProduct = SelectCache.getInstance().getAllProduct(null);
            mv.addObject("allProductList", allProduct);
            List<PageData> beakBoxDtl = new ArrayList<>();
            //操作事件1 整单拣货，2 明细拣货
            String optEven = page.getPd().getString("OPT_EVEN");
            if(Const.OPT_EVEN_1.equals(optEven)) {
                beakBoxDtl= pickOutService.findBeakBoxDtlByPid(pd);
            } if(Const.OPT_EVEN_2.equals(optEven)) {
               beakBoxDtl = pickOutService.findBeakBoxDtl(pd);
            }


            pd.put("STOCKMGROUT_IDS", pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_ID_CHOSE", STOCKMGROUT_IDS);
            pd.put("OPT_EVEN", optEven);
            mv.addObject("varList", beakBoxDtl);

            mv.setViewName("outstock/pickout_breakbox");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 保存拆箱拣货信息
     */
    @RequestMapping(value = "/savePickBeakBox")
    public ModelAndView savePickBeakBox(Page page) {
        logBefore(logger, "按出库单ID拣货...");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try { //  NEW_BOX_LIST STOCKMGROUT_ID_CHOSE

            //需要进行拣货的明细，格式为出库单明细ID:id,id
            String STOCKMGROUT_ID_CHOSE = pd.getString("STOCKMGROUT_ID_CHOSE");
            //新的需要拆分的箱号格式为:boxid-新的箱号，boxid-新的箱号
            String NEW_BOX_LIST = pd.getString("NEW_BOX_LIST");

            //拆箱拣货
            pickOutService.savePickManual3(pd);

           /* String STOCKMGROUT_IDS = pd.getString("STOCKMGROUT_IDS");
            List<String> ids = VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS"));
            pd.put("STOCKMGROUT_IDS", ids);
            pd.put("IDS", ids);
            page.setPd(pd);

            setSelect(mv);

            String optEven = page.getPd().getString("ASSIGN_TYPE");
            pd.put("ASSIGN_NAME", "拣货");
            List<PageData> varList = new ArrayList<>();
            List<PageData> assignList = new ArrayList<>();

            //pickOutService.findDtlPickOutlistPage(varList, assignList, ids);
            pickOutService.findDtlPickOutManuallistPage(varList, assignList, page);

            pd.put("STOCKMGROUT_IDS", STOCKMGROUT_IDS);
            pd.put("ASSIGN_TYPE", optEven);
            mv.addObject("varList", varList);
            mv.addObject("assignList", assignList);

            mv.setViewName("outstock/pickout_manual");*/
            mv.setViewName("save_result");
            mv.addObject("msg", "save");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }



    /**
     *按出库单明细 判断产品是否存在
     */
    @RequestMapping(value = "/hadNeedBreakBox")
    @ResponseBody
    public Object hadNeedBreakBox() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            List<PageData> beakBoxDtl = pickOutService.findBeakBoxDtl(pd);
            if (!beakBoxDtl.isEmpty()) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }


    @RequestMapping(value = "/hadRepeatBox")
    @ResponseBody
    public Object hadRepeatBox() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            List<String> new_box_list = pd.getList("NEW_BOX_LIST");
            Set<String> repeatElem = VaryUtils.getRepeatElem(new_box_list);
            if (!repeatElem.isEmpty()) {
                map.put("RS",repeatElem.toString());
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    /**
     * 按出库单判断产品是否存在
     */
    @RequestMapping(value = "/hadBreakBox")
    @ResponseBody
    public Object hadBreakBox() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "success";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            List<PageData> beakBoxDtl = pickOutService.findBeakBoxDtlByPid(pd);
            if (!beakBoxDtl.isEmpty()) {
                errInfo = "error";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }


    /**
     * 判断入库单是否存在
     */
    @RequestMapping(value = "/hadOutStock")
    @ResponseBody
    public Object hadOutStock() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "error";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            PageData pData = pickOutService.findOutStock(pd);
            if (pData != null) {
                map.put("STOCKMGROUT_ID", pData.getString("STOCKMGROUT_ID"));
                map.put("CUSTOMER_ID", pData.getString("CUSTOMER_ID"));
                Pair<String, String> pair = BaseInfoCache.getInstance().get2DSplit(pData.getString("CUSTOMER_ID"));
                if (pair == null || StringUtil.isBlank(pair.getRight())) {
                    map.put("QRFLAG","0");
                } else {
                    map.put("SEPERATOR", pair.getLeft());
                    map.put("QR2D", pair.getRight());
                    map.put("QRFLAG","1");
                }
                errInfo = "success";
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    @RequestMapping(value = "/auditCommBigBoxNo")
    @ResponseBody
    public Object auditCommBigBoxNo() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "error";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String customer = pd.getString("CUSTOMER_ID");
            String instockNo = pd.getString("OUTSTOCK_NO");
            if (StringUtil.isEmpty(customer) && StringUtil.isNotEmpty(instockNo)) {
                customer = pickOutService.findByCustomerId(pd);
                pd.put("CUSTOMER_ID",customer);
            }
            String auditInfo = auditBoxNo(pd.getString("BIG_BOX_NO"),instockNo,customer);
            if (StringUtil.isNotEmpty(auditInfo)) {
                map.put("auditRs", auditInfo);
                errInfo = "success";
            } else {
                String bigBoxNo = pd.getString("BIG_BOX_NO");
                if (isSrcBoxComm(bigBoxNo)) {

                } else {
                    //如果为空，说明该箱号符合规则，接下外箱号及是否原箱号校验
                    errInfo = auditCommBigBoxIsExist(pd,map);
                }

            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    @RequestMapping(value = "/auditBigBoxNo")
    @ResponseBody
    public Object auditBigBoxNo() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "error";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String customer = pd.getString("CUSTOMER_ID");
            String instockNo = pd.getString("OUTSTOCK_NO");
            if (StringUtil.isEmpty(customer) && StringUtil.isNotEmpty(instockNo)) {
                customer = pickOutService.findByCustomerId(pd);
                pd.put("CUSTOMER_ID",customer);
            }
            String auditInfo = auditBoxNo(pd.getString("BIG_BOX_NO"),instockNo,customer);
            if (StringUtil.isNotEmpty(auditInfo)) {
                map.put("auditRs", auditInfo);
                errInfo = "success";
            } else {
                String bigBoxNo = pd.getString("BIG_BOX_NO");
                if (isSrcBox(bigBoxNo)) {

                } else {
                    //如果为空，说明该箱号符合规则，接下外箱号及是否原箱号校验
                    errInfo = auditBigBoxIsExist(pd,map);
                }

            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    @RequestMapping(value = "/auditSperaBoxNo")
    @ResponseBody
    public Object auditSperaBoxNo() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "error";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();

            String customer = pd.getString("CUSTOMER_ID");
            String outStockNo = pd.getString("OUTSTOCK_NO");
            if (StringUtil.isEmpty(customer) && StringUtil.isNotEmpty(outStockNo)) {
                customer = pickOutService.findByCustomerId(pd);
                pd.put("CUSTOMER_ID",customer);
            }

            String box_no = auditBoxNo(pd.getString("BOX_NO"),outStockNo,customer);
            if (StringUtil.isNotEmpty(box_no)) {
                map.put("auditRs", box_no);
                errInfo = "success";
            } else {
                //如果为空，说明该箱号符合规则，接下进行箱号重复校验

                errInfo =  auditBoxIsExist(pd,map);
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    @RequestMapping(value = "/auditProperties")
    @ResponseBody
    public Object auditProperties() {
        Map<String, String> map = new HashMap<String, String>();
        String errInfo = "error";
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
           // BIG_BOX_NO:,CUSTOMER_ID:,STOCKMGROUT_ID:,PRODUCT_ID:,PRODUCT_CODE
            String customer = pd.getString("CUSTOMER_ID");
            String outStockNo = pd.getString("OUTSTOCK_NO");
            if (StringUtil.isEmpty(customer) && StringUtil.isNotEmpty(outStockNo)) {
                customer = pickOutService.findByCustomerId(pd);
                pd.put("CUSTOMER_ID",customer);
            }
            customer = pd.getString("CUSTOMER_ID");
            String scanProdCode = pd.getString("PRODUCT_CODE");
            String productId = pd.getString("PRODUCT_ID");
            String prodId = BaseInfoCache.getInstance().getProdIdByCustomerIdAndScanCode(customer,scanProdCode);
            //扫描料号与实际料号不匹配
            if (StringUtil.isBlank(prodId)) {
                map.put("auditRs", "扫描料号对应的实际料号不存在");
                errInfo = "success";
            } else {
                pd.put("PRODUCT_ID",prodId);
                List<PageData> list = pickOutService.findByProperties(pd);
                if (list == null || list.isEmpty() || list.size() !=2) {
                    map.put("auditRs", "没有符合属性条件的待拣货箱，请确认");
                    errInfo = "success";
                } else {
                    map.put("PRODUCT_ID", prodId);
                    map.put("PRODUCT_CODE", BaseInfoCache.getInstance().getProductCode(prodId));
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        map.put("result", errInfo);                //返回结果
        return AppUtil.returnObject(new PageData(), map);
    }

    private String auditBigBoxIsExist(PageData pd,Map<String, String> map) throws Exception {
        Map<String, PageData> boxIdMap = pickOutService.findBigBoxList(pd);
        Map<String, PageData> dtlIdMap = pickOutService.findAssignedBoxList(pd);
        String bigBoxNo = pd.getString("BIG_BOX_NO");
        if (!pickOutService.isExistsBox(boxIdMap,dtlIdMap)) {
            if (!isSrcBox(bigBoxNo)) {
                map.put("auditRs", "必须输入第6位O,9-11位为MIX的箱号!");
                map.put("BIG_BOX_SUBM", Const.DEL_NO);
                return "success";
            }
        } else {
            if (pickOutService.isAllAssigned(boxIdMap,dtlIdMap)) {
                map.put("PRODUCT_ID", getProdId(boxIdMap));
                map.put("BIG_BOX_SUBM", Const.DEL_YES);
                map.put("auditRs", "此外箱已全部分配!");
            } else {
                map.put("auditRs", "此外箱非全部分配请扫描MIX类新箱号!");
                return "success";
            }
        }
        return "error";
    }

    private String getProdId(Map<String, PageData> boxIdMap) {
        for (Map.Entry<String, PageData>  e : boxIdMap.entrySet()) {
            return e.getValue().getString("PRODUCT_ID");
        }
        return null;
    }

    private String auditCommBigBoxIsExist(PageData pd,Map<String, String> map) throws Exception {
        Map<String, PageData> boxIdMap = pickOutService.findBigBoxList(pd);
        Map<String, PageData> dtlIdMap = pickOutService.findAssignedBoxList(pd);
        String bigBoxNo = pd.getString("BIG_BOX_NO");
        if (!pickOutService.isExistsBox(boxIdMap,dtlIdMap)) {
            if (!isSrcBoxComm(bigBoxNo)) {
                map.put("auditRs", "必须输入第6位O,9-11位为MIX的箱号!");
                map.put("BIG_BOX_SUBM", Const.DEL_NO);
                return "success";
            }
        } else {
            if (pickOutService.isAllAssigned(boxIdMap,dtlIdMap)) {
                map.put("PRODUCT_ID", getProdId(boxIdMap));
                map.put("BIG_BOX_SUBM", Const.DEL_YES);
                map.put("auditRs", "此外箱已全部分配!");
            } else {
                map.put("auditRs", "此外箱非全部分配请扫描MIX类新箱号!");
                return "success";
            }
        }
        return "error";
    }

    private String auditBigBoxIsExist_1(PageData pd,Map<String, String> map) throws Exception {
        List<PageData> listByBigBox = pickOutService.findListByBigBox(pd);

        if (listByBigBox == null || listByBigBox.isEmpty()) {
            String bigBoxNo = pd.getString("BIG_BOX_NO");
            if (!isSrcBox(bigBoxNo)) {
                map.put("auditRs", "出库单不包含该外箱号!");
                map.put("BIG_BOX_SUBM", Const.DEL_NO);
                return "success";
            }
        } else {
            List<PageData> boxAssignedList = pickOutService.findBigBoxAssignedList(pd);
            //判断是否全部分配给该出库单
            if (pickOutService.isAllAssigned2Me(boxAssignedList,pd)) {

            } else {
                map.put("auditRs", "此外箱非全部分配请扫描MIX类新箱号!");
                return "success";
            }
            /*String stockmgroutId = pd.getString("STOCKMGROUT_ID");
            Set<String> set = pickOutService.turn2IdSet(listByBigBox, stockmgroutId);
            if (set.contains(stockmgroutId) && set.size() != 1) {
                map.put("auditRs", "此外箱非全部分配请扫描MIX类新箱号!");
                return "success";
            } else if (!set.contains(stockmgroutId)) {
                map.put("auditRs", "此外箱已全部分配给该出库单!");
                return "success";
            } else {
                return "error";
            }*/
           /* //该箱是否全部分配给了该出库单
            if (pickOutService.isAllBelongMe(listByBigBox,pd.getString("STOCKMGROUT_ID"))) {

            }
            if (pickOutService.isAllAssigned(listByBigBox)) {
                String productId = listByBigBox.get(0).getString("PRODUCT_ID");
                map.put("PRODUCT_ID", productId);
                map.put("BIG_BOX_SUBM", Const.DEL_YES);
                map.put("auditRs", "此外箱已全部分配!");
            } else {
                map.put("auditRs", "此外箱非全部分配请扫描MIX类新箱号!");
                return "success";
            }*/

        }
        return "error";
    }

    private String auditBoxIsExist( PageData pd,Map<String, String> map) throws Exception {
        PageData rs = pickOutService.findListByBox(pd);
        String errInfo;
        if (rs == null) {
            map.put("auditRs", "箱号不存在该入库单或已经拣货!");
            return "success";
        }else {
            String productId = rs.getString("PRODUCT_ID");
            map.put("PRODUCT_ID", productId);
            map.put("OLD_PRODUCT_CODE", BaseInfoCache.getInstance().getProductCode(productId));
            map.put("OLD_LOT_NO", rs.getString("LOT_NO"));
            map.put("OLD_DATE_CODE", rs.getString("DATE_CODE"));
            map.put("OLD_BIN_CODE", rs.getString("BIN_CODE"));
            map.put("OLD_QTY", rs.getString("EA_NUM"));
        }
        return "error";
    }

    private boolean isSrcBox(String stockNo) {
        String instock = stockNo.substring(0, 6);
        if (!Const.SPREAO_SRC_BOX_PRE.equals(instock)) {
            return false;
        }
        String third = stockNo.substring(8,11);
        if (!Const.SPREAO_SRC_BOX_MIX.equals(third)) {
            return false;
        }
        return true;
    }

    private boolean isSrcBoxComm(String stockNo) {
        String instock = stockNo.substring(5, 6);
        if (!Const.SRC_BOX_PRE_SIX.equals(instock)) {
            return false;
        }
        String third = stockNo.substring(8,11);
        if (!Const.SPREAO_SRC_BOX_MIX.equals(third)) {
            return false;
        }
        return true;
    }


    private static String auditBoxNo(String boxNo,String instockNo,String customerId) {
        if(StringUtil.isEmpty(boxNo)) {
            return "箱号为空";
        }

        if (boxNo.length() != Const.BOXNO_SIZE) {
            return "箱号长度不符";
        }

        String customer = boxNo.substring(0, 5);
        if(!StringUtil.isStr(customer)) {
            return "前5位不全是字母";
        }
        String instock = instockNo.substring(0, 5);
        if(!instock.equals(customer)) {
            return "不是该客户的箱号";
        }

        String secode = boxNo.substring(5,6);
        if(!Const.BOX_M_SEC.contains(secode)) {
            return "第6位不是M,O,I其中一个";
        }

        String yy = boxNo.substring(6,8);
        if(!StringUtil.isNumeric(yy)) {
            return "第7,8位不是有效年份";
        }
        //TODO
        String third = boxNo.substring(8,11);
        Set<String> boxRuleSet = BaseInfoCache.getInstance().getBoxRule(customerId);
        if(!boxRuleSet.contains(third)) {
            return "第9-11位不是"+boxRuleSet.toString();
        }

        String serialNo = boxNo.substring(12);
        if(!StringUtil.isNumeric(serialNo)) {
            return "后5位不是有效的流水号";
        }
        return Const.EMPTY_CH;
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
        List<Select> loadTypeList = SelectCache.getInstance().getLoadType();
        mv.addObject("depotTypeList", depotTypeList);
        mv.addObject("loadTypeList", loadTypeList);

        mv.addObject("cargoStateList", cargoStateList);

        mv.addObject("customerList", customerList);
        mv.addObject("allLocatorForShowList",allLocatorForShowList);
        mv.addObject("allProductList", allProduct);
        mv.addObject("assignedList", assignedList);
        mv.addObject("pickStateList", pickStateList);
        mv.addObject("storageList", storageList);
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
