package com.hw.controller.stock;

import com.hw.cache.SelectCache;
import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.stock.BatchModifyService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Panda.HuangWei.
 * @since 2017-05-15 22:15.
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/batchModify")
public class BatchModifyController extends BaseController {

    String menuUrl = "batchModify/list.do"; //批量修改界面
    String menuUrl2 = "batchModify/list2.do";//批量修改历史查询

    @Resource(name = "batchModifyService")
    private BatchModifyService batchModifyService;

    /**
     * 列表
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) {
        logBefore(logger, "批量修改batchModify");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            pd.put("MODIFY_EMP", getCurUserName());
            pd.put("BATCH_ID", UuidUtil.get32UUID());
            page.setPd(pd);

            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(Const.DEL_YES.equals(searchFlag)) {
                setMutiple(pd);
                varList = batchModifyService.saveAndGetList(page);
            }

            setSelect(mv,pd.getString("CUSTOMER_ID"));
            setDefault(pd);
            setMutipleAfter(pd);

            mv.setViewName("stock/batchmodify_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    @RequestMapping(value="/saveRemarkHistory")
    public ModelAndView saveRemarkHistory() throws Exception{
        logBefore(logger, "保存批量修改Remark");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        pd.put("MODIFY_EMP", getCurUserName());	//修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

        PageData rs = batchModifyService.saveRemarkHistory(pd);
        setSelect(mv,pd.getString("CUSTOMER_ID"));

        mv.addObject("batchModifyList", SelectCache.getInstance().getBatchModify());
        pd.put("MODIFY_BATCH",rs.getString("MODIFY_BATCH"));
        pd.put("MODIFY_CNT",rs.getString("MODIFY_CNT"));
        setDefault(pd);
        clearPd(pd);

        mv.setViewName("stock/batchmodify_list");
        mv.addObject("pd", pd);

        return mv;
    }


    @RequestMapping(value = "/list2")
    public ModelAndView list2(Page page) {
        logBefore(logger, "批量修改历史batchModify");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            page.setPd(pd);

            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(Const.DEL_YES.equals(searchFlag)) {
                varList = batchModifyService.historyList(page);
            }

            setSelect(mv,pd.getString("CUSTOMER_ID"));
            mv.addObject("batchModifyList", SelectCache.getInstance().getBatchModify());
            //setDefault(pd);

            mv.setViewName("stock/batchmodify_history");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX, this.getHC());    //按钮权限
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    //默认显示字段
    private void setDefault(PageData pd) {
        if (!Const.DEL_YES.equals(pd.getString("SEARCH_FLAG"))) {
            pd.put("EA_SHOW",Const.DEL_YES);
            pd.put("ASSIGNED_SHOW",Const.DEL_YES);
            pd.put("FREEZE_SHOW",Const.DEL_YES);
            pd.put("USE_SHOW",Const.DEL_YES);
        }

        pd.put("TXT_FOU_USE",Const.DEL_NO);
        pd.put("TXT_FIV_USE",Const.DEL_NO);
        pd.put("TXT_SIX_USE",Const.DEL_NO);
        pd.put("MODIFY_ALL",Const.DEL_NO);
    }

    private void clearPd(PageData pd) {
        pd.put("TXT_FOU_NEW",Const.EMPTY_CH);
        pd.put("TXT_FIV_NEW",Const.EMPTY_CH);
        pd.put("TXT_SIX_NEW",Const.EMPTY_CH);
        pd.put("BATCH_ID",Const.EMPTY_CH);
        pd.put("DATA_IDS",Const.EMPTY_CH);
    }

    private void setSelect(ModelAndView mv,String customerId) {
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();
        List<Select> productList = SelectCache.getInstance().getAllProduct(null);
        List<Select> storageList = SelectCache.getInstance().getAllStorage();
        List<Select> locatorList = SelectCache.getInstance().getdAllLocatorForShow();

        List<Select> assignedList = SelectCache.getInstance().getAssigned();
        List<Select> pickStateList = SelectCache.getInstance().getPickState();
        List<Select> cargoStateList = SelectCache.getInstance().getCargoState();
        List<Select> freezeStateList = SelectCache.getInstance().getFreezeState();

        mv.addObject("customerList", customerList);
        mv.addObject("productList", productList);
        mv.addObject("storageList", storageList);
        mv.addObject("locatorList", locatorList);

        mv.addObject("assignedList", assignedList);
        mv.addObject("pickStateList", pickStateList);
        mv.addObject("cargoStateList", cargoStateList);
        mv.addObject("freezeStateList", freezeStateList);
    }
    private void setMutiple(PageData pd) {
        //分配库存状态
        pd.put("ASSIGNED_STOCK_STATE_S1", pd.getString("ASSIGNED_STOCK_STATE_S"));
        //拣货状态
        pd.put("PICK_STATE_S1", pd.getString("PICK_STATE_S"));
        //配载状态
        pd.put("CARGO_STATE_S1", pd.getString("CARGO_STATE_S"));
        //冻结状态
        pd.put("FREEZE_STATE_S1", pd.getString("FREEZE_STATE_S"));

        //分配库存状态
        pd.put("ASSIGNED_STOCK_STATE_S", VaryUtils.genListOrNull(pd.getString("ASSIGNED_STOCK_STATE_S")));
        //拣货状态
        pd.put("PICK_STATE_S", VaryUtils.genListOrNull(pd.getString("PICK_STATE_S")));
        //配载状态
        pd.put("CARGO_STATE_S", VaryUtils.genListOrNull(pd.getString("CARGO_STATE_S")));
        //冻结状态
        pd.put("FREEZE_STATE_S", VaryUtils.genListOrNull(pd.getString("FREEZE_STATE_S")));
    }

    private void setMutipleAfter(PageData pd) {

        //分配库存状态
        pd.put("ASSIGNED_STOCK_STATE_S", pd.getString("ASSIGNED_STOCK_STATE_S1"));
        //拣货状态
        pd.put("PICK_STATE_S", pd.getString("PICK_STATE_S1"));
        //配载状态
        pd.put("CARGO_STATE_S", pd.getString("CARGO_STATE_S1"));
        //冻结状态
        pd.put("FREEZE_STATE_S", pd.getString("FREEZE_STATE_S1"));
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
