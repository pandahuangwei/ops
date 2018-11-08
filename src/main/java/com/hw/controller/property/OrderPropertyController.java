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
import com.hw.service.property.OrderPropertyService;

/**
 * Created：panda.HuangWei
 * Date：2016-10-25
 */
@Controller
@RequestMapping(value="/orderproperty")
public class OrderPropertyController extends BaseController {
	
	String menuUrl = "orderproperty/list.do"; //菜单地址(权限用)
	@Resource(name="orderpropertyService")
	private OrderPropertyService orderpropertyService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增OrderProperty");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("ORDERPROPERTY_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP",getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除

		orderpropertyService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除OrderProperty");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			orderpropertyService.delete(pd);
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
		logBefore(logger, "修改OrderProperty");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP",getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除

		orderpropertyService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表OrderProperty");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();

			List<Select> customerList = SelectCache.getInstance().getAllCustomer();
			List<Select> orderBatchList = SelectCache.getInstance().getOrderBatchType();

			mv.addObject("customerList",customerList);
			mv.addObject("orderBatchList",orderBatchList);

			if(pd.isEmpty()) {
				if (customerList == null || customerList.isEmpty()) {
					pd.put("CUSTOMER_ID",null);
				} else {
					pd.put("CUSTOMER_ID",customerList.get(0).getId());
				}

				if(orderBatchList == null || orderBatchList.isEmpty()) {
					pd.put("ORDERBATCH_TYPE",null);
				} else {
					pd.put("ORDERBATCH_TYPE",orderBatchList.get(0).getCode());
				}
			}
			page.setPd(pd);
			page.setShowCount(Const.BATCH_SHOW_SIZE);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
				varList = orderpropertyService.list(page);	//列出OrderProperty列表
			}

			mv.setViewName("property/orderproperty_list");
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
		List<Select> orderBatchList = SelectCache.getInstance().getOrderBatchType();

		mv.addObject("customerList",customerList);
		mv.addObject("orderBatchList",orderBatchList);
	}
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增OrderProperty页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			pd = orderpropertyService.findForAdd(pd);	//根据ID读取
			mv.setViewName("property/orderproperty_edit");
			if (Const.DEL_NO.equals(pd.getString("FLAG"))) {
				mv.addObject("msg", "save");
			} else {
				mv.addObject("msg", "edit");
			}
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
		logBefore(logger, "去修改OrderProperty页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			pd = orderpropertyService.findById(pd);	//根据ID读取
			mv.setViewName("property/orderproperty_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value="/goToEdit")
	public ModelAndView goToEdit(){
		logBefore(logger, "去修改OrderProperty页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelect(mv);
			//pd = orderpropertyService.findById(pd);	             //根据ID读取
			pd = orderpropertyService.findByCustomerAndType(pd);	//根据ID读取

			mv.setViewName("property/orderproperty_edit");
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
		logBefore(logger, "批量删除OrderProperty");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				orderpropertyService.deleteAll(ArrayDATA_IDS);
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

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/delAll")
	@ResponseBody
	public Object delAll() {
		logBefore(logger, "批量删除OrderProperty");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String customer_id = pd.getString("CUSTOMER_ID");
			String orderbatchType = pd.getString("ORDERBATCH_TYPE");

			if(StringUtil.isNotEmpty(customer_id) && StringUtil.isNotEmpty(orderbatchType)){
				orderpropertyService.delAll(pd);
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

	@RequestMapping(value = "/hasCn")
	@ResponseBody
	public Object hasCn() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (orderpropertyService.findByCn(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("txt", WmsEnum.OrderPropertyID.getName(pd.getString("ORDERBATCH_TYPE")));
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出OrderProperty到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("客户");	//1
			titles.add("MEMO");	//2
			titles.add("扩展日期字段1");	//3
			titles.add("扩展日期字段2");	//4
			titles.add("扩展日期字段3");	//5
			titles.add("扩展日期字段4");	//6
			titles.add("扩展日期字段5");	//7
			titles.add("扩展日期字段6");	//8
			titles.add("扩展日期字段7");	//9
			titles.add("扩展日期字段8");	//10
			titles.add("扩展日期字段9");	//11
			titles.add("扩展日期字段10");	//12
			titles.add("扩展数字字段1");	//13
			titles.add("扩展数字字段2");	//14
			titles.add("扩展数字字段3");	//15
			titles.add("扩展数字字段4");	//16
			titles.add("扩展数字字段5");	//17
			titles.add("扩展数字字段6");	//18
			titles.add("扩展数字字段7");	//19
			titles.add("扩展数字字段8");	//20
			titles.add("扩展数字字段9");	//21
			titles.add("扩展数字字段10");	//22
			titles.add("扩展文本字段1");	//23
			titles.add("扩展文本字段2");	//24
			titles.add("扩展文本字段3");	//25
			titles.add("扩展文本字段4");	//26
			titles.add("扩展文本字段5");	//27
			titles.add("扩展文本字段6");	//28
			titles.add("扩展文本字段7");	//29
			titles.add("扩展文本字段8");	//30
			titles.add("扩展文本字段9");	//31
			titles.add("扩展文本字段10");	//32
			titles.add("扩展文本字段11");	//33
			titles.add("扩展文本字段12");	//34
			titles.add("扩展文本字段13");	//35
			titles.add("扩展文本字段14");	//36
			titles.add("扩展文本字段15");	//37
			titles.add("扩展文本字段16");	//38
			titles.add("扩展文本字段17");	//39
			titles.add("扩展文本字段18");	//40
			titles.add("扩展文本字段19");	//41
			titles.add("扩展文本字段20");	//42
			titles.add("启用");	//43
			titles.add("启用");	//44
			titles.add("启用");	//45
			titles.add("启用");	//46
			titles.add("启用");	//47
			titles.add("启用");	//48
			titles.add("启用");	//49
			titles.add("启用");	//50
			titles.add("启用");	//51
			titles.add("启用");	//52
			titles.add("启用");	//53
			titles.add("启用");	//54
			titles.add("启用");	//55
			titles.add("启用");	//56
			titles.add("启用");	//57
			titles.add("启用");	//58
			titles.add("启用");	//59
			titles.add("启用");	//60
			titles.add("启用");	//61
			titles.add("启用");	//62
			titles.add("启用");	//63
			titles.add("启用");	//64
			titles.add("启用");	//65
			titles.add("启用");	//66
			titles.add("启用");	//67
			titles.add("启用");	//68
			titles.add("启用");	//69
			titles.add("启用");	//70
			titles.add("启用");	//71
			titles.add("启用");	//72
			titles.add("启用");	//73
			titles.add("启用");	//74
			titles.add("启用");	//75
			titles.add("启用");	//76
			titles.add("启用");	//77
			titles.add("启用");	//78
			titles.add("启用");	//79
			titles.add("启用");	//80
			titles.add("启用");	//81
			titles.add("启用");	//82
			titles.add("排序");	//83
			titles.add("排序");	//84
			titles.add("排序");	//85
			titles.add("排序");	//86
			titles.add("排序");	//87
			titles.add("排序");	//88
			titles.add("排序");	//89
			titles.add("排序");	//90
			titles.add("排序");	//91
			titles.add("排序");	//92
			titles.add("排序");	//93
			titles.add("排序");	//94
			titles.add("TIME_FOU_SORT");	//95
			titles.add("排序");	//96
			titles.add("排序");	//97
			titles.add("排序");	//98
			titles.add("排序");	//99
			titles.add("排序");	//100
			titles.add("排序");	//101
			titles.add("排序");	//102
			titles.add("排序");	//103
			titles.add(" 排序");	//104
			titles.add("排序");	//105
			titles.add("排序");	//106
			titles.add("排序");	//107
			titles.add("排序");	//108
			titles.add("排序");	//109
			titles.add("排序");	//110
			titles.add("排序");	//111
			titles.add("排序");	//112
			titles.add("排序");	//113
			titles.add("排序");	//114
			titles.add("排序");	//115
			titles.add("排序");	//116
			titles.add("排序");	//117
			titles.add("排序");	//118
			titles.add("排序");	//119
			titles.add("排序");	//120
			titles.add("排序");	//121
			titles.add("创建人");	//122
			titles.add("创建时间");	//123
			titles.add("修改人");	//124
			titles.add("修改时间");	//125
			titles.add("删除");	//126
			dataMap.put("titles", titles);
			List<PageData> varOList = orderpropertyService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("CUSTOMER_ID"));	//1
				vpd.put("var2", varOList.get(i).getString("ORDERBATCH_TYPE"));	//2
				vpd.put("var3", varOList.get(i).getString("NUM_ONE"));	//3
				vpd.put("var4", varOList.get(i).getString("NUM_TWO"));	//4
				vpd.put("var5", varOList.get(i).getString("NUM_THR"));	//5
				vpd.put("var6", varOList.get(i).getString("NUM_FOU"));	//6
				vpd.put("var7", varOList.get(i).getString("NUM_FIV"));	//7
				vpd.put("var8", varOList.get(i).getString("NUM_SIX"));	//8
				vpd.put("var9", varOList.get(i).getString("NUM_SEV"));	//9
				vpd.put("var10", varOList.get(i).getString("NUM_EIG"));	//10
				vpd.put("var11", varOList.get(i).getString("NUM_NIG"));	//11
				vpd.put("var12", varOList.get(i).getString("NUM_TEN"));	//12
				vpd.put("var13", varOList.get(i).getString("TIME_ONE"));	//13
				vpd.put("var14", varOList.get(i).getString("TIME_TWO"));	//14
				vpd.put("var15", varOList.get(i).getString("TIME_THR"));	//15
				vpd.put("var16", varOList.get(i).getString("TIME_FOU"));	//16
				vpd.put("var17", varOList.get(i).getString("TIME_FIV"));	//17
				vpd.put("var18", varOList.get(i).getString("TIME_SIX"));	//18
				vpd.put("var19", varOList.get(i).getString("TIME_SEV"));	//19
				vpd.put("var20", varOList.get(i).getString("TIME_EIG"));	//20
				vpd.put("var21", varOList.get(i).getString("TIME_NIG"));	//21
				vpd.put("var22", varOList.get(i).getString("TIME_TEN"));	//22
				vpd.put("var23", varOList.get(i).getString("TXT_ONE"));	//23
				vpd.put("var24", varOList.get(i).getString("TXT_TWO"));	//24
				vpd.put("var25", varOList.get(i).getString("TXT_THR"));	//25
				vpd.put("var26", varOList.get(i).getString("TXT_FOU"));	//26
				vpd.put("var27", varOList.get(i).getString("TXT_FIV"));	//27
				vpd.put("var28", varOList.get(i).getString("TXT_SIX"));	//28
				vpd.put("var29", varOList.get(i).getString("TXT_SEV"));	//29
				vpd.put("var30", varOList.get(i).getString("TXT_EIG"));	//30
				vpd.put("var31", varOList.get(i).getString("TXT_NIG"));	//31
				vpd.put("var32", varOList.get(i).getString("TXT_TEN"));	//32
				vpd.put("var33", varOList.get(i).getString("TXT_ELEVEN"));	//33
				vpd.put("var34", varOList.get(i).getString("TXT_TWELVE"));	//34
				vpd.put("var35", varOList.get(i).getString("TXT_THIRT"));	//35
				vpd.put("var36", varOList.get(i).getString("TXT_FOURT"));	//36
				vpd.put("var37", varOList.get(i).getString("TXT_FIFT"));	//37
				vpd.put("var38", varOList.get(i).getString("TXT_SIXT"));	//38
				vpd.put("var39", varOList.get(i).getString("TXT_SEVENT"));	//39
				vpd.put("var40", varOList.get(i).getString("TXT_EIGHT"));	//40
				vpd.put("var41", varOList.get(i).getString("TXT_NINET"));	//41
				vpd.put("var42", varOList.get(i).getString("TXT_TWENT"));	//42
				vpd.put("var43", varOList.get(i).getString("NUM_ONE_USE"));	//43
				vpd.put("var44", varOList.get(i).getString("NUM_TWO_USE"));	//44
				vpd.put("var45", varOList.get(i).getString("NUM_THR_USE"));	//45
				vpd.put("var46", varOList.get(i).getString("NUM_FOU_USE"));	//46
				vpd.put("var47", varOList.get(i).getString("NUM_FIV_USE"));	//47
				vpd.put("var48", varOList.get(i).getString("NUM_SIX_USE"));	//48
				vpd.put("var49", varOList.get(i).getString("NUM_SEV_USE"));	//49
				vpd.put("var50", varOList.get(i).getString("NUM_EIG_USE"));	//50
				vpd.put("var51", varOList.get(i).getString("NUM_NIG_USE"));	//51
				vpd.put("var52", varOList.get(i).getString("NUM_TEN_USE"));	//52
				vpd.put("var53", varOList.get(i).getString("TIME_ONE_USE"));	//53
				vpd.put("var54", varOList.get(i).getString("TIME_TWO_USE"));	//54
				vpd.put("var55", varOList.get(i).getString("TIME_THR_USE"));	//55
				vpd.put("var56", varOList.get(i).getString("TIME_FOU_USE"));	//56
				vpd.put("var57", varOList.get(i).getString("TIME_FIV_USE"));	//57
				vpd.put("var58", varOList.get(i).getString("TIME_SIX_USE"));	//58
				vpd.put("var59", varOList.get(i).getString("TIME_SEV_USE"));	//59
				vpd.put("var60", varOList.get(i).getString("TIME_EIG_USE"));	//60
				vpd.put("var61", varOList.get(i).getString("TIME_NIG_USE"));	//61
				vpd.put("var62", varOList.get(i).getString("TIME_TEN_USE"));	//62
				vpd.put("var63", varOList.get(i).getString("TXT_ONE_USE"));	//63
				vpd.put("var64", varOList.get(i).getString("TXT_TWO_USE"));	//64
				vpd.put("var65", varOList.get(i).getString("TXT_THR_USE"));	//65
				vpd.put("var66", varOList.get(i).getString("TXT_FOU_USE"));	//66
				vpd.put("var67", varOList.get(i).getString("TXT_FIV_USE"));	//67
				vpd.put("var68", varOList.get(i).getString("TXT_SIX_USE"));	//68
				vpd.put("var69", varOList.get(i).getString("TXT_SEV_USE"));	//69
				vpd.put("var70", varOList.get(i).getString("TXT_EIG_USE"));	//70
				vpd.put("var71", varOList.get(i).getString("TXT_NIG_USE"));	//71
				vpd.put("var72", varOList.get(i).getString("TXT_TEN_USE"));	//72
				vpd.put("var73", varOList.get(i).getString("TXT_ELEVEN_USE"));	//73
				vpd.put("var74", varOList.get(i).getString("TXT_TWELVE_USE"));	//74
				vpd.put("var75", varOList.get(i).getString("TXT_THIRT_USE"));	//75
				vpd.put("var76", varOList.get(i).getString("TXT_FOURT_USE"));	//76
				vpd.put("var77", varOList.get(i).getString("TXT_FIFT_USE"));	//77
				vpd.put("var78", varOList.get(i).getString("TXT_SIXT_USE"));	//78
				vpd.put("var79", varOList.get(i).getString("TXT_SEVENT_USE"));	//79
				vpd.put("var80", varOList.get(i).getString("TXT_EIGHT_USE"));	//80
				vpd.put("var81", varOList.get(i).getString("TXT_NINET_USE"));	//81
				vpd.put("var82", varOList.get(i).getString("TXT_TWENT_USE"));	//82
				vpd.put("var83", varOList.get(i).getString("NUM_ONE_SORT"));	//83
				vpd.put("var84", varOList.get(i).getString("NUM_TWO_SORT"));	//84
				vpd.put("var85", varOList.get(i).getString("NUM_THR_SORT"));	//85
				vpd.put("var86", varOList.get(i).getString("NUM_FOU_SORT"));	//86
				vpd.put("var87", varOList.get(i).getString("NUM_FIV_SORT"));	//87
				vpd.put("var88", varOList.get(i).getString("NUM_SIX_SORT"));	//88
				vpd.put("var89", varOList.get(i).getString("NUM_SEV_SORT"));	//89
				vpd.put("var90", varOList.get(i).getString("NUM_EIG_SORT"));	//90
				vpd.put("var91", varOList.get(i).getString("NUM_NIG_SORT"));	//91
				vpd.put("var92", varOList.get(i).getString("NUM_TEN_SORT"));	//92
				vpd.put("var93", varOList.get(i).getString("TIME_ONE_SORT"));	//93
				vpd.put("var94", varOList.get(i).getString("TIME_TWO_SORT"));	//94
				vpd.put("var95", varOList.get(i).getString("TIME_THR_SORT"));	//95
				vpd.put("var96", varOList.get(i).getString("TIME_FOU_SORT"));	//96
				vpd.put("var97", varOList.get(i).getString("TIME_FIV_SORT"));	//97
				vpd.put("var98", varOList.get(i).getString("TIME_SIX_SORT"));	//98
				vpd.put("var99", varOList.get(i).getString("TIME_SEV_SORT"));	//99
				vpd.put("var100", varOList.get(i).getString("TIME_EIG_SORT"));	//100
				vpd.put("var101", varOList.get(i).getString("TIME_NIG_SORT"));	//101
				vpd.put("var102", varOList.get(i).getString("TIME_TEN_SORT"));	//102
				vpd.put("var103", varOList.get(i).getString("TXT_ONE_SORT"));	//103
				vpd.put("var104", varOList.get(i).getString("TXT_TWO_SORT"));	//104
				vpd.put("var105", varOList.get(i).getString("TXT_THR_SORT"));	//105
				vpd.put("var106", varOList.get(i).getString("TXT_FOU_SORT"));	//106
				vpd.put("var107", varOList.get(i).getString("TXT_FIV_SORT"));	//107
				vpd.put("var108", varOList.get(i).getString("TXT_SIX_SORT"));	//108
				vpd.put("var109", varOList.get(i).getString("TXT_SEV_SORT"));	//109
				vpd.put("var110", varOList.get(i).getString("TXT_EIG_SORT"));	//110
				vpd.put("var111", varOList.get(i).getString("TXT_NIG_SORT"));	//111
				vpd.put("var112", varOList.get(i).getString("TXT_TEN_SORT"));	//112
				vpd.put("var113", varOList.get(i).getString("TXT_ELEVEN_SORT"));	//113
				vpd.put("var114", varOList.get(i).getString("TXT_TWELVE_SORT"));	//114
				vpd.put("var115", varOList.get(i).getString("TXT_THIRT_SORT"));	//115
				vpd.put("var116", varOList.get(i).getString("TXT_FOURT_SORT"));	//116
				vpd.put("var117", varOList.get(i).getString("TXT_FIFT_SORT"));	//117
				vpd.put("var118", varOList.get(i).getString("TXT_SEVENT_SORT"));	//118
				vpd.put("var119", varOList.get(i).getString("TXT_EIGHT_SORT"));	//119
				vpd.put("var120", varOList.get(i).getString("TXT_NINET_SORT"));	//120
				vpd.put("var121", varOList.get(i).getString("TXT_TWENT_SORT"));	//121
				vpd.put("var122", varOList.get(i).getString("CREATE_EMP"));	//122
				vpd.put("var123", varOList.get(i).getString("CREATE_TM"));	//123
				vpd.put("var124", varOList.get(i).getString("MODIFY_EMP"));	//124
				vpd.put("var125", varOList.get(i).getString("MODIFY_TM"));	//125
				vpd.put("var126", varOList.get(i).get("DEL_FLG").toString());	//126
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
