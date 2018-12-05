package com.hw.controller.property;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.entity.base.Province;
import com.hw.entity.base.Select;
import com.hw.entity.system.Role;
import com.hw.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
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

import com.hw.controller.BaseController;
import com.hw.entity.Page;
import com.hw.service.property.PnMapService;

/**
 * Created：panda.HuangWei
 * Date：2016-11-07
 */
@Controller
@RequestMapping(value="/pnmap")
public class PnMapController extends BaseController {
	
	String menuUrl = "pnmap/list.do"; //菜单地址(权限用)
	@Resource(name="pnmapService")
	private PnMapService pnmapService;
	
	/**
	 * 新增
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, "新增PnMap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("PNMAP_ID", this.get32UUID());	//主键
		pd.put("CREATE_EMP", getCurUserName());	//创建人
		pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
		pd.put("DEL_FLG", "0");	//删除

		pnmapService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out){
		logBefore(logger, "删除PnMap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			pnmapService.delete(pd);
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
		logBefore(logger, "修改PnMap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		pd.put("MODIFY_EMP", getCurUserName());	//修改人
		pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

		pnmapService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page){
		logBefore(logger, "列表PnMap");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		try{
			pd = this.getPageData();
			page.setPd(pd);
			setSelect(mv);
			String searchFlag = pd.getString("SEARCH_FLAG");
			List<PageData> varList=new ArrayList<>();
			if(isSearch(searchFlag)) {
				 varList = pnmapService.list(page);    //列出PnMap列表
			}
				mv.setViewName("property/pnmap_list");
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
		logBefore(logger, "去新增PnMap页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelectDync(mv);
			mv.setViewName("property/pnmap_edit");
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
		logBefore(logger, "去修改PnMap页面");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try {
			setSelectDync(mv);
			pd = pnmapService.findById(pd);	//根据ID读取
			mv.setViewName("property/pnmap_edit");
			mv.addObject("msg", "edit");
			mv.addObject("pd", pd);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}	

	private void setSelect(ModelAndView mv) {
		List<Select> customerList = SelectCache.getInstance().getAllCustomer();
		mv.addObject("customerList",customerList);

		List<Select> productList = SelectCache.getInstance().getAllProduct(null);
		mv.addObject("productList",productList);
	}

	private void setSelectDync(ModelAndView mv) {
		List<Select> customerList = SelectCache.getInstance().getAllCustomer();
		mv.addObject("customerList",customerList);

		List<Select> productList = SelectCache.getInstance().getAllProduct(customerList.get(0).getId());
		mv.addObject("productList",productList);
	}

	/**
	 * 批量删除
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() {
		logBefore(logger, "批量删除PnMap");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "dell")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			String DATA_IDS = pd.getString("DATA_IDS");
			if(null != DATA_IDS && !"".equals(DATA_IDS)){
				String ArrayDATA_IDS[] = DATA_IDS.split(",");
				pnmapService.deleteAll(ArrayDATA_IDS);
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
	 * 判断编码是否存在
	 */
	@RequestMapping(value = "/hasCode")
	@ResponseBody
	public Object hasCode() {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			if (pnmapService.findByPn(pd) != null) {
				errInfo = "error";
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		map.put("result", errInfo);                //返回结果
		return AppUtil.returnObject(new PageData(), map);
	}

	/**
	 * 下载模版
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {

		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Partnum.xls", "Partnum.xls");

	}

	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value = "/readExcel")
	public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file
	) throws Exception {
		ModelAndView mv = this.getModelAndView();
		if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
			return null;
		}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "userexcel");                            //执行上传

			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);    //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet

			Set<String> hasSet = pnmapService.findByAll();
			Map<String, PageData> productMap = SelectCache.getInstance().getProductMap(null);

			List<PageData> auditList = audit(listPd, productMap);
			PageData pd = new PageData();
			if(auditList != null && !auditList.isEmpty()) {
				pd.put("IMP_RS","数据存在异常,请修改正确后重新导入...");

				mv.addObject("xlsList", auditList);
				mv.addObject("msg", "error");
			    mv.setViewName("property/pnmap_uploadexl");
				return mv;
			}

			/**
			 * var0 :扫描料号
			 * var1 :实际料号
			 */
			List<PageData> lst = new ArrayList<>();
			for (int i = 0; i < listPd.size(); i++) {
				pd = new PageData();
				String CUSTOMER_CODE =listPd.get(i).getString("var0");
				String SCAN_PN =listPd.get(i).getString("var1");
				String ACTUAL_PN = listPd.get(i).getString("var2");
				String CUSTOMER_ID = BaseInfoCache.getInstance().getCustomerId(CUSTOMER_CODE);
				pd.put("PNMAP_ID", this.get32UUID());     //ID
				pd.put("CUSTOMER_ID", CUSTOMER_ID);
				pd.put("SCAN_PN", SCAN_PN);   //扫描料号
				PageData pageData = productMap.get(ACTUAL_PN);
				pd.put("ACTUAL_PN", pageData.getString("PRODUCT_ID")); //实际料号
				pd.put("DEL_FLG","0");
				pd.put("MEMO", "用户导入");
				pd.put("CREATE_EMP", getCurUserName());	//创建人
				pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
				pd.put("MODIFY_EMP", getCurUserName());	//修改人
				pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

				pnmapService.save(pd);
			}
            /*存入数据库操作======================================*/
			pd.put("IMP_RS","数据导入成功!");
			mv.addObject("xlsList", lst);
			mv.addObject("msg", "success");
			pd.put("success","success");
			mv.addObject("pd",pd);
		}
		mv.setViewName("property/pnmap_uploadexl");
		//mv.setViewName("save_result");
		return mv;
	}


	private List<PageData>  audit(List<PageData> listPd,Map<String, PageData> productMap) throws Exception{
		List<PageData> lst = new ArrayList<>();
		Set<String> hasSet = pnmapService.findByAll();
        Set<String> curSet = new HashSet<>(128);

		for (int i = 0; i < listPd.size(); i++) {
			PageData pd = new PageData();
			String CUSTOMER_CODE =listPd.get(i).getString("var0");
			String SCAN_PN =listPd.get(i).getString("var1");
			String ACTUAL_PN = listPd.get(i).getString("var2");
			String CUSTOMER_ID = BaseInfoCache.getInstance().getCustomerId(CUSTOMER_CODE);

			if (StringUtil.isEmpty(CUSTOMER_ID)||StringUtil.isEmpty(SCAN_PN) || StringUtil.isEmpty(ACTUAL_PN)) {
				PageData pds = new PageData();
				pds.put("CUSTOMER_CODE", CUSTOMER_CODE);   //客户
				pds.put("SCAN_PN", SCAN_PN);   //扫描料号
				pds.put("ACTUAL_PN", ACTUAL_PN); //实际料号
				pds.put("MEMO", "客户编码,扫描料号与实际料号不能为空");
				lst.add(pds);
				continue;
			}

			if(hasSet.contains(SCAN_PN)) {
				PageData pds = new PageData();
				pds.put("CUSTOMER_CODE", CUSTOMER_CODE);   //客户
				pds.put("SCAN_PN", SCAN_PN);   //扫描料号
				pds.put("ACTUAL_PN", ACTUAL_PN); //实际料号
				pds.put("MEMO", "扫描料号已经存在数据库中");
				lst.add(pds);
				continue;
			}

			if(curSet.contains(SCAN_PN)) {
				PageData pds = new PageData();
				pds.put("CUSTOMER_CODE", CUSTOMER_CODE);   //客户
				pds.put("SCAN_PN", SCAN_PN);   //扫描料号
				pds.put("ACTUAL_PN", ACTUAL_PN); //实际料号
				pds.put("MEMO", "扫描料号在EXCEL中重复");
				lst.add(pds);
				continue;
			}

			if(!productMap.containsKey(ACTUAL_PN)) {
				PageData pds = new PageData();
				pds.put("CUSTOMER_CODE", CUSTOMER_CODE);   //客户
				pds.put("SCAN_PN", SCAN_PN);   //扫描料号
				pds.put("ACTUAL_PN", ACTUAL_PN); //实际料号
				pds.put("MEMO", "实际料号在系统中不存在");
				lst.add(pds);
				continue;
			}

			curSet.add(SCAN_PN);
		}
		return lst;
	}

	@RequestMapping(value = "/getProducts")
	@ResponseBody
	public Object getProducts() {
		Map<String,String> map = new HashMap<>();
		String errInfo = "success";
		PageData pd = new PageData();
		try {
			pd = this.getPageData();
			List<Select> productList = SelectCache.getInstance().getAllProduct(pd.getString("CUSTOMER_ID"));

			if (productList == null || productList.size() == 0) {
				return AppUtil.returnObject(pd, map);
			}
			for (Select p : productList) {
				map.put(p.getId(),p.getValue());
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		//返回结果
		return AppUtil.returnObject(pd, map);
	}

	/*
	 * 导出到excel
	 * @return
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel(){
		logBefore(logger, "导出PnMap到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("扫描料号");	//1
			titles.add("实际料号");	//2
			titles.add("备注");	//3
			titles.add("创建人");	//4
			titles.add("创建时间");	//5
			titles.add("修改人");	//6
			titles.add("修改时间");	//7
			titles.add("删除");	//8
			dataMap.put("titles", titles);
			List<PageData> varOList = pnmapService.listAll(pd);
			List<PageData> varList = new ArrayList<PageData>();
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				vpd.put("var1", varOList.get(i).getString("SCAN_PN"));	//1
				vpd.put("var2", varOList.get(i).getString("ACTUAL_PN"));	//2
				vpd.put("var3", varOList.get(i).getString("MEMO"));	//3
				vpd.put("var4", varOList.get(i).getString("CREATE_EMP"));	//4
				vpd.put("var5", varOList.get(i).getString("CREATE_TM"));	//5
				vpd.put("var6", varOList.get(i).getString("MODIFY_EMP"));	//6
				vpd.put("var7", varOList.get(i).getString("MODIFY_TM"));	//7
				vpd.put("var8", varOList.get(i).getString("DEL_FLG"));	//8
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



	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value = "/goUploadExcel")
	public ModelAndView goUploadExcel() throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("property/pnmap_uploadexl");
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
