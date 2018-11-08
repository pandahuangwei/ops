package com.hw.controller.property;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hw.cache.SelectCache;
import com.hw.entity.base.Select;
import com.hw.service.property.BoxRuleService;
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

import com.hw.controller.BaseController;
import com.hw.entity.Page;

/**
 * @author Panda.HuangWei.
 * @version 1.0 .
 * @since 2017.01.13 10:25.
 */
@Controller
@RequestMapping(value="/boxrule")
public class BoxRuleController extends BaseController {
    String menuUrl = "boxrule/list.do"; //菜单地址(权限用)
    @Resource(name="boxruleService")
    private BoxRuleService boxruleService;

    /**
     * 新增
     */
    @RequestMapping(value="/save")
    public ModelAndView save() throws Exception{
        logBefore(logger, "新增BOXRULE");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("BOXRULE_ID", this.get32UUID());	//主键
        pd.put("CREATE_EMP", getCurUserName());	//创建人
        pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间

        pd.put("MID_CODE",pd.getString("MID_CODE").toUpperCase());

        boxruleService.save(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 删除
     */
    @RequestMapping(value="/delete")
    public void delete(PrintWriter out){
        logBefore(logger, "删除BOXRULE");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            boxruleService.delete(pd);
            out.write("success");
            out.close();
        } catch(Exception e){
            logger.error(e.toString(), e);
        }

    }

    /**
     * 修改
     */
    @RequestMapping(value="/edit")
    public ModelAndView edit() throws Exception{
        logBefore(logger, "修改BOXRULE");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();

        pd.put("MID_CODE",pd.getString("MID_CODE").toUpperCase());

        boxruleService.edit(pd);
        mv.addObject("msg","success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     */
    @RequestMapping(value="/list")
    public ModelAndView list(Page page){
        logBefore(logger, "列表BOXRULE");
        //if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        try{
            pd = this.getPageData();
            page.setPd(pd);
            setSelect(mv);
            String searchFlag = pd.getString("SEARCH_FLAG");
            List<PageData> varList=new ArrayList<>();
            if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
                varList = boxruleService.list(page);	//列出BOXRULE列表
            }

            mv.setViewName("property/boxrule_list");
            mv.addObject("varList", varList);
            mv.addObject("pd", pd);
            mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    private void setSelect(ModelAndView mv) {
        List<Select> customerList = SelectCache.getInstance().getAllCustomer();

        mv.addObject("customerList",customerList);
    }
    /**
     * 去新增页面
     */
    @RequestMapping(value="/goAdd")
    public ModelAndView goAdd(){
        logBefore(logger, "去新增BOXRULE页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            setSelect(mv);

            mv.setViewName("property/boxrule_edit");
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
    @RequestMapping(value="/goEdit")
    public ModelAndView goEdit(){
        logBefore(logger, "去修改BOXRULE页面");
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try {
            setSelect(mv);
            pd = boxruleService.findById(pd);	//根据ID读取
            mv.setViewName("property/boxrule_edit");
            mv.addObject("msg", "edit");
            mv.addObject("pd", pd);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /**
     * 批量删除
     */
    @RequestMapping(value="/deleteAll")
    @ResponseBody
    public Object deleteAll() {
        logBefore(logger, "批量删除BOXRULE");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
        PageData pd = new PageData();
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            pd = this.getPageData();
            List<PageData> pdList = new ArrayList<PageData>();
            String DATA_IDS = pd.getString("DATA_IDS");
            if(null != DATA_IDS && !"".equals(DATA_IDS)){
                String ArrayDATA_IDS[] = DATA_IDS.split(",");
                boxruleService.deleteAll(ArrayDATA_IDS);
                pd.put("msg", "ok");
            }else{
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
    @RequestMapping(value="/excel")
    public ModelAndView exportExcel(){
        logBefore(logger, "导出BOXRULE到excel");
        if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        try{
            Map<String,Object> dataMap = new HashMap<String,Object>();
            List<String> titles = new ArrayList<String>();
            titles.add("客户");	//1
            titles.add("代码");	//2
            titles.add("备注");	//3
            titles.add("创建人");	//4
            titles.add("创建时间");	//5
            dataMap.put("titles", titles);
            List<PageData> varOList = boxruleService.listAll(pd);
            List<PageData> varList = new ArrayList<PageData>();
            for(int i=0;i<varOList.size();i++){
                PageData vpd = new PageData();
                vpd.put("var1", varOList.get(i).getString("CUSTOMER_ID"));	//1
                vpd.put("var2", varOList.get(i).getString("MID_CODE"));	//2
                vpd.put("var3", varOList.get(i).getString("MEMO"));	//3
                vpd.put("var4", varOList.get(i).getString("CREATE_EMP"));	//4
                vpd.put("var5", varOList.get(i).getString("CREATE_TM"));	//5
                varList.add(vpd);
            }
            dataMap.put("varList", varList);
            ObjectExcelView erv = new ObjectExcelView();
            mv = new ModelAndView(erv,dataMap);
        } catch(Exception e){
            logger.error(e.toString(), e);
        }
        return mv;
    }

    /* ===============================权限================================== */
    public Map<String, String> getHC(){
        Subject currentUser = SecurityUtils.getSubject();  //shiro管理的session
        Session session = currentUser.getSession();
        return (Map<String, String>)session.getAttribute(Const.SESSION_QX);
    }
	/* ===============================权限================================== */

    @InitBinder
    public void initBinder(WebDataBinder binder){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
    }
}