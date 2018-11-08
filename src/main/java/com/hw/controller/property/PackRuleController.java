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
import com.hw.service.property.PackRuleService;

/**
 * Created：panda.HuangWei
 * Date：2016-10-24
 */
@Controller
@RequestMapping(value="/packrule")
public class PackRuleController extends BaseController {
	
	String menuUrl = "packrule/list.do"; //菜单地址(权限用)
	@Resource(name="packruleService")
	private PackRuleService packruleService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增PackRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PACKRULE_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", 0);	//删除
		packruleService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除PackRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			packruleService.delete(pd);
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
		logBefore(logger, "修改PackRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", 0);	//删除

		packruleService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表PackRule");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(StringUtil.isNotEmpty(searchFlag)&&searchFlag.equals("1")) {
				varList = packruleService.list(page);    //列出PackRule列表
			}
			mv.setViewName("property/packrule_list");
			mv.addObject("varList", varList);
			mv.addObject("pd", pd);
			mv.addObject(Const.SESSION_QX,this.getHC());	//按钮权限
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd(){
		logBefore(logger, "去新增PackRule页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd.put("EA_NUM",1);
			mv.setViewName("property/packrule_edit");
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
		logBefore(logger, "去修改PackRule页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			pd = packruleService.findById(pd);	//根据ID读取
			mv.setViewName("property/packrule_edit");
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
		logBefore(logger, "批量删除PackRule");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				packruleService.deleteAll(ArrayDATA_IDS);
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


	@RequestMapping(value = "/hasCode")
	@ResponseBody
	public Object hasCode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (packruleService.findByCode(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出PackRule到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("包装规则");	//1
			titles.add("包装描述");	//2
			titles.add("标准包装");	//3
			titles.add("EA数量");	//4
			titles.add("EA描述");	//5
			titles.add("EA装箱");	//6
			titles.add("EA补货");	//7
			titles.add("EA收货");	//8
			titles.add("EA出货");	//9
			titles.add("EA长");	//10
			titles.add("EA宽");	//11
			titles.add("EA高");	//12
			titles.add("EA体积");	//13
			titles.add("EA重量");	//14
			titles.add("INPACK数量");	//15
			titles.add("INPACK描述");	//16
			titles.add("INPACK装箱");	//17
			titles.add("INPACK补货");	//18
			titles.add("INPACK收货");	//19
			titles.add("INPACK出货");	//20
			titles.add("INPACK长");	//21
			titles.add("INPACK宽");	//22
			titles.add("INPACK高");	//23
			titles.add("INPACK体积");	//24
			titles.add("INPACK重量");	//25
			titles.add("BOX数量");	//26
			titles.add("BOX描述");	//27
			titles.add("BOX装箱");	//28
			titles.add("BOX补货");	//29
			titles.add("BOX收货");	//30
			titles.add("BOX出货");	//31
			titles.add("BOX长");	//32
			titles.add("BOX宽");	//33
			titles.add("BOX高");	//34
			titles.add("BOX体积");	//35
			titles.add("BOX重量");	//36
			titles.add("PALLET数量");	//37
			titles.add("PALLET描述");	//38
			titles.add("PALLET装箱");	//39
			titles.add("PALLET补货");	//40
			titles.add("PALLET收货");	//41
			titles.add("PALLET出货");	//42
			titles.add("PALLET长");	//43
			titles.add("PALLET宽");	//44
			titles.add("PALLET高");	//45
			titles.add("PALLET体积");	//46
			titles.add("PALLET重量");	//47
			titles.add("PALLETTI");	//48
			titles.add("PALLETTI");	//49
			titles.add("OTHER数量");	//50
			titles.add("OTHER描述");	//51
			titles.add("OTHER装箱");	//52
			titles.add("OTHER补货");	//53
			titles.add("OTHER收货");	//54
			titles.add("OTHER出货");	//55
			titles.add("OTHER长");	//56
			titles.add("OTHER宽");	//57
			titles.add("OTHER高");	//58
			titles.add("OTHER体积");	//59
			titles.add("OTHER重量");	//60
			titles.add("创建人");	//61
			titles.add("创建时间");	//62
			titles.add("修改人");	//63
			titles.add("修改时间");	//64
			titles.add("删除");	//65
			dataMap.put("titles", titles);
			List<PageData> varOList = packruleService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("PACK_CN"));	//1
				vpd.put("var2", varOList.get(i).getString("PACK_DSC"));	//2
				vpd.put("var3", varOList.get(i).getString("STANDARD_PACK"));	//3
				vpd.put("var4", varOList.get(i).getString("EA_NUM"));	//4
				vpd.put("var5", varOList.get(i).getString("EA_DSC"));	//5
				vpd.put("var6", varOList.get(i).getString("EA_DOCK"));	//6
				vpd.put("var7", varOList.get(i).getString("EA_REPL"));	//7
				vpd.put("var8", varOList.get(i).getString("EA_IN"));	//8
				vpd.put("var9", varOList.get(i).getString("EA_OUT"));	//9
				vpd.put("var10", varOList.get(i).getString("EA_LONG"));	//10
				vpd.put("var11", varOList.get(i).getString("EA_WIDE"));	//11
				vpd.put("var12", varOList.get(i).getString("EA_HIGH"));	//12
				vpd.put("var13", varOList.get(i).getString("EA_VOLUME"));	//13
				vpd.put("var14", varOList.get(i).getString("EA_WEIGH"));	//14
				vpd.put("var15", varOList.get(i).getString("INPACK_NUM"));	//15
				vpd.put("var16", varOList.get(i).getString("INPACK_DSC"));	//16
				vpd.put("var17", varOList.get(i).getString("INPACK_DOCK"));	//17
				vpd.put("var18", varOList.get(i).getString("INPACK_REPL"));	//18
				vpd.put("var19", varOList.get(i).getString("INPACK_IN"));	//19
				vpd.put("var20", varOList.get(i).getString("INPACK_OUT"));	//20
				vpd.put("var21", varOList.get(i).getString("INPACK_LONG"));	//21
				vpd.put("var22", varOList.get(i).getString("INPACK_WIDE"));	//22
				vpd.put("var23", varOList.get(i).getString("INPACK_HIGH"));	//23
				vpd.put("var24", varOList.get(i).getString("INPACK_VOLUME"));	//24
				vpd.put("var25", varOList.get(i).getString("INPACK_WEIGH"));	//25
				vpd.put("var26", varOList.get(i).getString("BOX_NUM"));	//26
				vpd.put("var27", varOList.get(i).getString("BOX_DSC"));	//27
				vpd.put("var28", varOList.get(i).getString("BOX_DOCK"));	//28
				vpd.put("var29", varOList.get(i).getString("BOX_REPL"));	//29
				vpd.put("var30", varOList.get(i).getString("BOX_IN"));	//30
				vpd.put("var31", varOList.get(i).getString("BOX_OUT"));	//31
				vpd.put("var32", varOList.get(i).getString("BOX_LONG"));	//32
				vpd.put("var33", varOList.get(i).getString("BOX_WIDE"));	//33
				vpd.put("var34", varOList.get(i).getString("BOX_HIGH"));	//34
				vpd.put("var35", varOList.get(i).getString("BOX_VOLUME"));	//35
				vpd.put("var36", varOList.get(i).getString("BOX_WEIGH"));	//36
				vpd.put("var37", varOList.get(i).getString("PALLET_NUM"));	//37
				vpd.put("var38", varOList.get(i).getString("PALLET_DSC"));	//38
				vpd.put("var39", varOList.get(i).getString("PALLET_DOCK"));	//39
				vpd.put("var40", varOList.get(i).getString("PALLET_REPL"));	//40
				vpd.put("var41", varOList.get(i).getString("PALLET_IN"));	//41
				vpd.put("var42", varOList.get(i).getString("PALLET_OUT"));	//42
				vpd.put("var43", varOList.get(i).getString("PALLET_LONG"));	//43
				vpd.put("var44", varOList.get(i).getString("PALLET_WIDE"));	//44
				vpd.put("var45", varOList.get(i).getString("PALLET_HIGH"));	//45
				vpd.put("var46", varOList.get(i).getString("PALLET_VOLUME"));	//46
				vpd.put("var47", varOList.get(i).getString("PALLET_WEIGH"));	//47
				vpd.put("var48", varOList.get(i).getString("PALLET_TI"));	//48
				vpd.put("var49", varOList.get(i).getString("PALLET_HI"));	//49
				vpd.put("var50", varOList.get(i).getString("OTHER_NUM"));	//50
				vpd.put("var51", varOList.get(i).getString("OTHER_DSC"));	//51
				vpd.put("var52", varOList.get(i).getString("OTHER_DOCK"));	//52
				vpd.put("var53", varOList.get(i).getString("OTHER_REPL"));	//53
				vpd.put("var54", varOList.get(i).getString("OTHER_IN"));	//54
				vpd.put("var55", varOList.get(i).getString("OTHER_OUT"));	//55
				vpd.put("var56", varOList.get(i).getString("OTHER_LONG"));	//56
				vpd.put("var57", varOList.get(i).getString("OTHER_WIDE"));	//57
				vpd.put("var58", varOList.get(i).getString("OTHER_HIGH"));	//58
				vpd.put("var59", varOList.get(i).getString("OTHER_VOLUME"));	//59
				vpd.put("var60", varOList.get(i).getString("OTHER_WEIGH"));	//60
				vpd.put("var61", varOList.get(i).getString("CREATE_EMP"));	//61
				vpd.put("var62", varOList.get(i).getString("CREATE_TM"));	//62
				vpd.put("var63", varOList.get(i).getString("MODIFY_EMP"));	//63
				vpd.put("var64", varOList.get(i).getString("MODIFY_TM"));	//64
				vpd.put("var65", varOList.get(i).get("DEL_FLG").toString());	//65
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
