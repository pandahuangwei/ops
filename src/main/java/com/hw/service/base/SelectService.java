package com.hw.service.base;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Panda.HuangWei on 2016/10/22.
 */
@Service("selectService")
public class SelectService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
	* 新增
	*/
    public void save(PageData pd)throws Exception{
        dao.save("SelectMapper.save", pd);
        dao.update("SelectMapper.updateByKey", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd)throws Exception{
        dao.delete("SelectMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd)throws Exception{
        dao.update("SelectMapper.edit", pd);
    }

    /*
    *列表
    */
    public List<PageData> list(Page page)throws Exception{
        return (List<PageData>)dao.findForList("SelectMapper.datalistPage", page);
       /* Map<String,PageData> map = new HashMap<>(32);
        for (PageData e:list) {
            String key = e.getString("GROUP_KEY");
            if (!map.containsKey(key)) {
                map.put(key,e);
            }
        }
        List<PageData> lst = new ArrayList<>();
        for (Map.Entry<String,PageData> m : map.entrySet()) {
            PageData pd = new PageData();
            PageData p = m.getName();
            pd.put("GROUP_KEY",p.getString("GROUP_KEY"));
            pd.put("GROUP_NAME",p.getString("GROUP_NAME"));
            pd.put("C_CODE",p.getString("C_CODE"));
            pd.put("C_VALUE",p.getString("C_VALUE"));
            pd.put("C_DESC",p.getString("C_DESC"));
            pd.put("C_SORT",p.getString("C_SORT"));
            pd.put("SELECT_ID",p.getString("SELECT_ID"));

            lst.add(pd);
        }
        return lst;*/
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("SelectMapper.listAll", pd);
    }

    public List<PageData> findByGroupKey(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("SelectMapper.findByGroupKey", pd);
    }

    public List<PageData> findByActiveGroupKey(PageData pd)throws Exception{
        return (List<PageData>)dao.findForList("SelectMapper.findByActiveGroupKey", pd);
    }

    public PageData findByCode(PageData pd)throws Exception{
        List<PageData>  list = (List<PageData>)dao.findForList("SelectMapper.findByCcode", pd);
        return (list!= null && !list.isEmpty())?list.get(0):null;
    }

    public PageData findByCn(PageData pd)throws Exception{
        List<PageData>  list = (List<PageData>)dao.findForList("SelectMapper.findByCvalue", pd);
        return (list!= null && !list.isEmpty())?list.get(0):null;
    }
    public List<PageData> filterActive(List<PageData> list) {
        List<PageData> lst = new ArrayList<>();
        if (list == null ||list.isEmpty()) {
            return lst;
        }

        for(PageData p : list) {
            if (Const.DEL_NO.equals(p.getString("DEL_FLG"))) {
                lst.add(p);
            }
        }
        return lst;
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("SelectMapper.findById", pd);
    }

    public int findMaxId(){
        try{
            PageData p= (PageData)dao.findForObject("SelectMapper.findMaxId", null);
            double id = Double.parseDouble(p.getString("SELECT_ID"));
            return new Double(id).intValue();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return DateUtil.getCurTime();
    }

    public int getNextId() {
       return  findMaxId()+1;
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
        dao.delete("SelectMapper.deleteAll", ArrayDATA_IDS);
    }


    /**
     * 获取所有的下拉选项
     * @return
     */
    public List<Select> findAll(){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAll", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 获取所有的国家
     * @return 国家列表
     */
    public List<Select> findAllCountry(){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllCountry", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取所有的省份
     * @return 省份列表
     */
    public List<Select> findAllProvince(){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllProvince", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取所有的城市
     * @return 城市列表
     */
    public List<Select> findAllCity(){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllCity", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据国家ID获取所有的省份
     * @return 省份列表
     */
    public List<Select> findProvinceByCountry(String countryId){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findProvinceByCountry", countryId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根据省份ID获取所有的城市
     * @return 城市列表
     */
    public List<Select> findCityByProvince(String provinceId){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findCityByProvince", provinceId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取所有的仓库
     * @return 仓库列表
     */
    public List<Select> findAllStock(){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllStock", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 根获取所有的客户
     * @return 客户列表
     */
    public List<Select> findAllCustomer(){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllCustomer", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Select> findAllUser(){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllUser", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根获取所有的产品
     * @return 产品列表
     *//*
    public List<PageData> findAllProduct(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("SelectMapper.findAllProduct", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }*/

    /**
     * 根获取所有的库区
     * @return 库区列表
     */
    public List<Select> findAllStorage() {
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllStorage", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根获取所有的库位(不包含缺省分配库位)
     * @return 库位列表
     */
    public List<Select> findAllLocator() {
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllLocator", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根获取所有的库位,包含缺省分配库位
     * @return 库位列表
     */
    public List<Select> findAllLocatorForShow() {
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllLocatorForShow", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     *
     * @param list
     * @return (StorageId,list)
     */
    public Map<String,List<Select>>  groupByStorageId(List<Select> list) {
        Map<String,List<Select>> map = new HashMap<>();
        for (Select e : list) {
            List<Select> lst = map.get(e.getGroup());
            if (lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(e);
            map.put(e.getGroup(),lst);
        }
        return map;
    }

    /**.
     * 根据库区得到库位
     * @param storageId 库区ID
     * @return 库位列表
     */
    public List<Select> findLocatorByStorage(String storageId) {
        List<Select> allLocator = findAllLocator();
        List<Select> list = new ArrayList<>();
        if (allLocator == null || allLocator.isEmpty()) {
            return list;
        }

        if(StringUtil.isEmpty(storageId)) {
            return allLocator;
        }

        for (Select e : allLocator) {
            if (storageId.equals(e.getGroup())) {
                list.add(e);
            }
        }
        return list;
    }

    /**
     * 获取所有的包装规则
     * @return 包装规则
     */
    public List<Select> findPackRule() {
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findPackRule", null);
        } catch(Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public Map<String,List<Select>> findPickRule() {
        Map<String,List<Select>> map = new HashMap<>();
        try {
            List<Select> list =  (List<Select>)dao.findForList("SelectMapper.findPickRule", null);
            for (Select s : list) {
                List<Select> lst = map.get(s.getGroup());
                if (lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(s);
                map.put(s.getGroup(),lst);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return  map;
    }

    /**
     * 获取拣货规则
     * @param customerId 客户Id
     * @return 拣货规则
     */
    public List<Select> findPickRule(String customerId) {
        List<Select> list = new ArrayList<>();
        if (StringUtil.isEmpty(customerId)) {
            return list;
        }
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findPickRule", customerId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 按客户Id分组的库存分配规则
     * @return (客户Id,库位分配规则)
     */
    public Map<String,List<Select>> findStockAssignedRule() {
        Map<String,List<Select>> map = new HashMap<>();
        try {
            List<Select> list = (List<Select>)dao.findForList("SelectMapper.findStockAssignedRule", null);
            for (Select s : list) {
                List<Select> lst = map.get(s.getGroup());
                if (lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(s);
                map.put(s.getGroup(),lst);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return  map;
    }

    /**
     * 获取库存分配规则
     * @param customerId 客户Id
     * @return 库存分配规则
     */
    public List<Select> findStockAssignedRule(String customerId) {
        List<Select> list = new ArrayList<>();
        if (StringUtil.isEmpty(customerId)) {
            return list;
        }
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findStockAssignedRule", customerId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取所有客户的库位分配规则
     * @return (客户Id,库位分配规则)
     */
    public Map<String,List<Select>> findStorageAssignedRule() {
        Map<String,List<Select>> map = new HashMap<>();
        try {
            List<Select> list = (List<Select>)dao.findForList("SelectMapper.findStorageAssignedRule", null);
            for (Select s : list) {
                List<Select> lst = map.get(s.getGroup());
                if (lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(s);
                map.put(s.getGroup(),lst);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取库位分配规则
     * @param customerId 客户Id
     * @return 库位分配规则
     */
    public List<Select> findStorageAssignedRule(String customerId) {
        List<Select> list = new ArrayList<>();
        if (StringUtil.isEmpty(customerId)) {
            return list;
        }
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findStorageAssignedRule", customerId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取库存周转规则
     *
     * @return 库存周转规则
     */
    public List<Select> findStockTurnRule() {
        List<Select> list = new ArrayList<>();
        try {
            return (List<Select>) dao.findForList("SelectMapper.findStockTurnRule", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 根获取客户所有的产品
     * @return 产品列表
     */
    public Map<String,List<Select>> findAllProduct(List<Select> allProductList,Map<String,Map<String,String>> customProdIdValueMap){
        Map<String,List<Select>> map = new HashMap<>();
        try {
            List<Select> list = (List<Select>)dao.findForList("SelectMapper.findAllProduct", null);
            for (Select s : list) {
                //s.getGroup() 这里映射为 客户ID
                List<Select> lst = map.get(s.getGroup());
                if (lst == null || lst.isEmpty()) {
                    lst = new ArrayList<>(5200);
                }
                lst.add(s);
                map.put(s.getGroup(),lst);
                Map<String, String> map1 = customProdIdValueMap.get(s.getGroup());
                map1 = map1 != null?map1:new HashMap<>(2560);
                map1.put(s.getId(),s.getValue());
                customProdIdValueMap.put(s.getGroup(),map1);
            }
            allProductList.addAll(list);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public List<Select> findAllProduct(String customerId){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllProduct", customerId);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 按客户ID分组全部产品编码
     * @param prodCodeSet 产品编码集合
     * @return (客户ID,产品集合)
     */
    public Map<String,Set<String>> findAllProdCode(Set<String> prodCodeSet){
        Map<String,Set<String>> map = new HashMap<>();
        try {
            List<Select> list = (List<Select>)dao.findForList("SelectMapper.findAllProdCode", null);
            for(Select s :list) {
                Set<String> set = map.get(s.getGroup());
                if (set == null || set.isEmpty()) {
                    set = new HashSet<>(5200);
                }
                set.add(s.getValue());
                map.put(s.getGroup(),set);

                prodCodeSet.add(s.getValue());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public Set<String> findAllProdCode(String customerId){
        List<Select> list = new ArrayList<>();
        Set<String> set = new HashSet<>(128);
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllProdCode", customerId);
            for(Select s :list) {
                set.add(s.getValue());
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return set;
    }

    public Map<String,List<PageData>> findPackRuleById(){
        Map<String,List<PageData>> map = new HashMap<>();
        try {
            List<PageData> list =(List<PageData>)dao.findForList("SelectMapper.findPackRuleById", null);
            for (PageData rs : list) {
                setDefalut(rs);
                String packRuleId = rs.getString("PACKRULE_ID");
                List<PageData> lst = map.get(packRuleId);
                if (lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(rs);
                map.put(packRuleId,lst);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public PageData findPackRuleById(String packRuleId){
        try {
            PageData rs =(PageData)dao.findForObject("SelectMapper.findPackRuleById", packRuleId);
            setDefalut(rs);
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PageData> findPackRuleListById(String packRuleId){
        PageData pageData = findPackRuleById(packRuleId);
        List<PageData> list = new ArrayList<>();
        if(pageData != null) {
            list.add(pageData);
        }
        return list;
    }

    public Map<String,PageData> findProductIdMap(){
        Map<String,PageData> map = new HashMap<>(10240);
        try {
            List<PageData> list =  (List<PageData>)dao.findForList("SelectMapper.findProduct", null);

            for (PageData rs : list) {
                String productId = rs.getString("PRODUCT_ID");
                if (rs == null || StringUtil.isEmpty(productId)) {
                  continue;
                }
                setColumValue(rs);
                map.put(productId,rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据产品id获取产品的信息
     * @param productId 产品Id
     * @return 产品
     */
    public PageData findProduct(String productId){
        try {
            PageData rs =  (PageData)dao.findForObject("SelectMapper.findProduct", productId);
            if (rs != null) {
                setColumValue(rs);
            }
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<PageData> findProductList(String customerID) {
        try {
            return (List<PageData>) dao.findForList("SelectMapper.findProductByCustomerId", customerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<PageData> findAllStockPd() {
        try {
            return (List<PageData>) dao.findForList("SelectMapper.findAllStockPd", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public  Map<String,Map<String,PageData>> findAllProductMap( Map<String,PageData> prodCodeMap) {
        List<PageData> list =  findProductList(null);
        if (list == null || list.isEmpty()) {
            return new HashMap<>();
        }

        Map<String,Map<String,PageData>> map = new HashMap<>();

        for(PageData e : list) {
            String customerId = e.getString("CUSTOMER_ID");
            String key = e.getString("PRODUCT_CODE");
            if(StringUtil.isEmpty(key)) {
                continue;
            }
            setColumValue(e);
            prodCodeMap.put(key,e);


            Map<String, PageData> codePdMap = map.get(customerId);
            if (codePdMap == null) {
                codePdMap = new HashMap<>(5200);
            }
            codePdMap.put(key,e);
            map.put(customerId,codePdMap);
        }
        return map;
    }


    public Map<String,PageData> findProductMap(String customerId) {
        List<PageData> list =  findProductList(customerId);
        if (list == null || list.isEmpty()) {
            return new HashMap<>();
        }
        Map<String,PageData> map = new HashMap<>(list.size());
        for(PageData e : list) {
            String key = e.getString("PRODUCT_CODE");
            if(StringUtil.isEmpty(key)) {
                continue;
            }
            setColumValue(e);
            map.put(key,e);
        }
        return map;
    }


    public  Set<String> findProdCodeSet(String customerID) {
        List<PageData> list = findProductList(customerID);
        Set<String> set = new HashSet<>(list.size()*2);
        for(PageData e : list) {
            set.add(e.getString("PRODUCT_CODE"));
        }
        return set;
    }

    //产品编码 列表
    public Set<String> buildMap(Map<String,PageData> map) {
        Set<String> set =  new HashSet<>();
        if (map ==null || map.isEmpty()) {
            return set;
        }
        for(Map.Entry<String,PageData> e : map.entrySet()) {
            set.add(e.getValue().getString("PRODUCT_CODE"));
        }
        return set;
    }

    public Map<String,PageData> toUpCaseKey(Map<String,PageData> map) {
        Map<String,PageData> rsMap = new HashMap<>(map.size());
        for (Map.Entry<String,PageData> key : map.entrySet()) {
            if (key == null || StringUtil.isEmpty(key.getKey())) {
              continue;
            }
            rsMap.put(StringUtil.trimSpace(key.getKey().toUpperCase()),key.getValue());
        }
        return rsMap;
    }

    public List<Select> findRpSearchType() {
        List<Select> list = new ArrayList<>();
        list.add(new Select(WmsEnum.RpSearchType.OLD.getItemKey(),WmsEnum.RpSearchType.OLD.getItemValue()));
        list.add(new Select(WmsEnum.RpSearchType.NEW.getItemKey(),WmsEnum.RpSearchType.NEW.getItemValue()));
        return list;
    }

    public List<Select> findModifyBatch() {
        try {
            List<Select> list = (List<Select>)dao.findForList("SelectMapper.findModifyBatch", null);
            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }
            list.sort((e1,e2)-> Integer.parseInt(e2.getValue()) - Integer.parseInt(e1.getValue()));
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void setValue(PageData pd,String colum) {
        if (StringUtil.isEmpty(pd.getString(colum))) {
            pd.put(colum,Const.ZERO);
        }
    }

    private void setColumValue(PageData pd) {
        // B.EA_NUM,B.INPACK_NUM,B.BOX_NUM,B.PALLET_NUM ,B.OTHER_NUM,B.EA_IN,B.INPACK_IN,B.BOX_IN,B.PALLET_IN ,B.OTHER_IN
        setDefalut(pd);
       //TOTAL_WEIGHT_UNIT,A.TOTAL_WEIGHT,A.TOTAL_SUTTLE_UNIT,A.TOTAL_SUTTLE,A.VOLUME_UNIT,A.VOLUME_UN
      //WEIGHT_UNIT_TYPE SUTTLE_UNIT_TYPE VOLUME_UNIT_TYPE UNIT_WEIGHT UNIT_SUTTLE
        pd.put("WEIGHT_UNIT_TYPE", WmsEnum.WeightUnit.getValue(pd.getString("TOTAL_WEIGHT_UNIT")));
        pd.put("SUTTLE_UNIT_TYPE", WmsEnum.SuttleUnit.getValue(pd.getString("TOTAL_SUTTLE_UNIT")));
        pd.put("VOLUME_UNIT_TYPE", WmsEnum.VolumeUnit.getValue(pd.getString("VOLUME_UNIT")));

        setValue(pd,"LONG_UNIT");
        setValue(pd,"WIDE_UNIT");
        setValue(pd,"HIGH_UNIT");

        //毛重、净重、单价
        setValue(pd,"TOTAL_WEIGHT");
        setValue(pd,"TOTAL_SUTTLE");
        setValue(pd,"UNIT_PRICE");
        setValue(pd,"EA_NUM");

        //产品配置的毛重，净重
        pd.put("UNIT_SUTTLE", pd.getString("TOTAL_SUTTLE"));
        pd.put("UNIT_WEIGHT", pd.getString("TOTAL_WEIGHT"));


        double EA_NUM = Double.parseDouble(pd.getString("EA_NUM"));
        double totalPrice =  EA_NUM* Double.parseDouble(pd.getString("UNIT_PRICE"));

        pd.put("TOTAL_PRICE",String.valueOf(totalPrice));

        double volume = Double.parseDouble(pd.getString("LONG_UNIT"))*
                Double.parseDouble(pd.getString("WIDE_UNIT"))*
                Double.parseDouble(pd.getString("HIGH_UNIT"));

         double volumeChange = volume/1000;
        pd.put("UNIT_VOLUME",String.valueOf(volumeChange));

         /* double totalVolume = volumeChange*EA_NUM;
        pd.put("TOTAL_VOLUME",String.valueOf(totalVolume));*/

        pd.put("TOTAL_VOLUME",0);

        pd.put("PACK_RULE",pd.getString("RULE_PACK"));
    }

    private void setDefalut(PageData pd) {

        pd.put("EA_NUM",getDefault(pd.getString("EA_NUM")));
        pd.put("INPACK_NUM",getDefault(pd.getString("INPACK_NUM")));
        pd.put("BOX_NUM",getDefault(pd.getString("BOX_NUM")));
        pd.put("PALLET_NUM",getDefault(pd.getString("PALLET_NUM")));
        pd.put("OTHER_NUM",getDefault(pd.getString("OTHER_NUM")));


        pd.put("EA_IN",getDefault(pd.getString("EA_IN")));
        pd.put("INPACK_IN",getDefault(pd.getString("INPACK_IN")));
        pd.put("BOX_IN",getDefault(pd.getString("BOX_IN")));
        pd.put("PALLET_IN",getDefault(pd.getString("PALLET_IN")));
        pd.put("OTHER_IN",getDefault(pd.getString("OTHER_IN")));

        pd.put("EA_OUT",getDefault(pd.getString("EA_OUT")));
        pd.put("INPACK_OUT",getDefault(pd.getString("INPACK_OUT")));
        pd.put("BOX_OUT",getDefault(pd.getString("BOX_OUT")));
        pd.put("PALLET_OUT",getDefault(pd.getString("PALLET_OUT")));
        pd.put("OTHER_OUT",getDefault(pd.getString("OTHER_OUT")));

        pd.put("TOTAL_WEIGHT",getDefault(pd.getString("TOTAL_WEIGHT")));
        pd.put("TOTAL_SUTTLE",getDefault(pd.getString("TOTAL_SUTTLE")));
        pd.put("VOLUME_UN",getDefault(pd.getString("VOLUME_UN")));


        //TOTAL_WEIGHT_UNIT,A.TOTAL_WEIGHT,A.TOTAL_SUTTLE_UNIT,A.TOTAL_SUTTLE,A.VOLUME_UNIT,A.VOLUME_UN
    }

    private String getDefault(String str) {
        return StringUtil.isEmpty(str)?"0":str;
    }

}
