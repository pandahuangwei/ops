package com.hw.controller.base;

import com.hw.controller.BaseController;
import com.hw.entity.base.City;
import com.hw.entity.base.Country;
import com.hw.entity.base.Province;
import com.hw.entity.base.Select;
import com.hw.service.base.CityService;
import com.hw.service.base.CountryService;
import com.hw.service.base.ProvinceService;
import com.hw.service.base.SelectService;
import com.hw.util.AppUtil;
import com.hw.util.PageData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 不再使用
 * Created by Panda.HuangWei on 2016/10/21.
 */
@Controller
@RequestMapping(value = "/select2")
public class SelectController2 extends BaseController {

    @Resource(name = "cityService")
    private CityService cityService;

    @Resource(name = "provinceService")
    private ProvinceService provinceService;

    @Resource(name = "countryService")
    private CountryService countryService;

    @Resource(name = "selectService")
    private SelectService selectService;


    //获取国家
    @RequestMapping(value = "country.do")
    public Map<String, Object> country() {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            List<Country> list = countryService.findAll();
            modelMap.put("data", list);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return modelMap;
    }

    //获取省市区
    @RequestMapping(value = "province.do")
    public Object province(String country_id) {
        Map<String,String> map = new HashMap<>();
        PageData pd = new PageData();
        try {

            pd = this.getPageData();
            pd.put("country_id",country_id);

            List<Province> list = provinceService.findByCountryId(pd);
            if (list == null || list.size() == 0) {
                return AppUtil.returnObject(pd, map);
            }

            for (Province p : list) {
                map.put(p.getPROVINCE_ID(),p.getPROVINCE_CN());
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return AppUtil.returnObject(pd, map);
    }

    //获取市区
    @RequestMapping(value = "city.do")
    public Map<String, Object> city(String province_id) {
        Map<String, Object> modelMap = new HashMap<>();
        try {
            PageData pd = new PageData();
            pd = this.getPageData();
            pd.put("province_id",province_id);
            List<City> list = cityService.findByProvinceId(pd);
            modelMap.put("data", list);
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return modelMap;
    }


    //获取省市区
    @RequestMapping(value = "/getProvince")
    @ResponseBody
    public Object getProvince() {
        Map<String,String> map = new HashMap<>();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String countryId = pd.getString("COUNTRYID");
            List<Select> list = selectService.findProvinceByCountry(countryId);
            if (list == null || list.size() == 0) {
                return AppUtil.returnObject(pd, map);
            }

            for (Select p : list) {
                map.put(p.getId(),p.getValue());
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return AppUtil.returnObject(pd, map);
    }

    //获取省市区
    @RequestMapping(value = "/getCity")
    @ResponseBody
    public Object getCity(String id) {
        Map<String,String> map = new HashMap<>();
        PageData pd = new PageData();
        try {
            pd = this.getPageData();
            String provinceId = pd.getString("PROVINCEID");
            List<Select> list = selectService.findCityByProvince(provinceId);
            if (list == null || list.size() == 0) {
                return AppUtil.returnObject(pd, map);
            }

            for (Select p : list) {
                map.put(p.getId(),p.getValue());
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return AppUtil.returnObject(pd, map);
    }

}
