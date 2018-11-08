package com.hw.cache;

import com.hw.entity.base.Select;
import com.hw.service.base.BaseInfoService;
import com.hw.service.base.SelectService;
import com.hw.util.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Panda.HuangWei on 2016/10/22.
 */
public class SelectCache extends ReloadableSpringBean {
    protected Logger logger = Logger.getLogger(this.getClass());

    private Map<String, List<Select>> selectMap = new HashMap<>();

    private List<Select> customerList = new ArrayList<>();

    private List<Select> userList = new ArrayList<>();
    private List<Select> allLocatorList = new ArrayList<>();
    private List<Select> allStorageList = new ArrayList<>();
    private List<Select> allLocatorForShowList = new ArrayList<>();
    private List<Select> allStockList = new ArrayList<>();
    private List<PageData> allStockPdList = new ArrayList<>();
    //产品的缓存
    private List<Select> allProductList = new ArrayList<>(10240);
    private Map<String, List<Select>> allProductGroupByCustomerMap = new HashMap<>();
    private Map<String,Map<String,String>> customProdIdValueMap = new HashMap<>();
    private Set<String> allProductCodeSet = new HashSet<>(10240);
    private Map<String, Set<String>> allProdCodeGroupByCustomerMap = new HashMap<>();
    private Map<String, PageData> prodCodePdMap = new HashMap<>();
    private Map<String, Map<String, PageData>> customerProdCodePdMap = new HashMap<>();
    private Map<String, PageData> prodIdPdMap = new HashMap<>();

    //包装规则
    private Map<String, List<PageData>> packRuleIdMap = new HashMap<>();
    //库位分配规则
    private Map<String, List<Select>> customerStorageAssignedRuleMap = new HashMap<>();
    private List<Select> stockTurnRuleList = new ArrayList<>();
    private Map<String, List<Select>> customerStockAssignedRuleMap = new HashMap<>();
    private Map<String, List<Select>> customerPickRuleMap = new HashMap<>();
    private List<Select> packRuleList = new ArrayList<>();
    private Map<String, List<Select>> storageIdListMap = new HashMap<>();
    private List<Select> rpSearchTypeList = new ArrayList<>();

    private List<Select> batchModifyList = new ArrayList<>();

    private static SelectCache instance;

    private SelectCache() {
        SelectCache.instance = this;
    }

    /**
     * @Title: getInstance   
     */
    public static SelectCache getInstance() {
        return instance;
    }

    @Resource(name = "selectService")
    private SelectService selectService;

    @Override
    public void reload() {
        logger.info("begin load select check form...");
        Map<String, List<Select>> map = groupBy(selectService.findAll());

        List<Select> allCustomer = selectService.findAllCustomer();

        userList = selectService.findAllUser();

        List<Select> allLocator = selectService.findAllLocator();
        List<Select> allStorage = selectService.findAllStorage();
        List<Select> allLocatorForShow = selectService.findAllLocatorForShow();
        List<Select> allStock = selectService.findAllStock();
        List<PageData> allStockPd = selectService.findAllStockPd();
        //产品
        List<Select> allProduct = new ArrayList<>(10240);
        Map<String,Map<String,String>> customProdIdValueMapTmp = new HashMap<>();
       // Set<String> allProductCodeSet = new HashSet<>(10240);
        Map<String, List<Select>> allProductGroupByCustomer = selectService.findAllProduct(allProduct,customProdIdValueMapTmp);
        Set<String> allProductCode = new HashSet<>(10240);
        Map<String, Set<String>> allProdCodeGroupByCustomer = selectService.findAllProdCode(allProductCode);
        Map<String, PageData> prodCodePd = new HashMap<>(5200);
        Map<String, Map<String, PageData>> customerProdCodePd = selectService.findAllProductMap(prodCodePd);
        Map<String, PageData> prodIdMap = selectService.findProductIdMap();

        Map<String, List<PageData>> packRuleById = selectService.findPackRuleById();
        Map<String, List<Select>> storageAssignedRule = selectService.findStorageAssignedRule();
        List<Select> stockTurnRule = selectService.findStockTurnRule();

        Map<String, List<Select>> stockAssignedRule = selectService.findStockAssignedRule();
        Map<String, List<Select>> pickRule = selectService.findPickRule();
        List<Select> packRule = selectService.findPackRule();

        Map<String, List<Select>> storageIdList = selectService.groupByStorageId(allLocator);
        List<Select> rpSearchType = selectService.findRpSearchType();

        List<Select> batchModifyListTmp = selectService.findModifyBatch();
        if (map != null) {
            selectMap = map;
        }

        if (allCustomer != null && allCustomer.size() >= 0) {
            customerList = allCustomer;
            allLocatorList = allLocator;
            allStorageList = allStorage;
            allLocatorForShowList = allLocatorForShow;
            allStockList = allStock;
            allStockPdList = allStockPd;
            allProductList = allProduct;
            allProductGroupByCustomerMap = allProductGroupByCustomer;
            customProdIdValueMap = customProdIdValueMapTmp;

            allProductCodeSet = allProductCode;
            allProdCodeGroupByCustomerMap = allProdCodeGroupByCustomer;
            prodCodePdMap = prodCodePd;
            customerProdCodePdMap = customerProdCodePd;
            prodIdPdMap = prodIdMap;

            packRuleIdMap = packRuleById;
            customerStorageAssignedRuleMap = storageAssignedRule;
            stockTurnRuleList = stockTurnRule;
            customerStockAssignedRuleMap = stockAssignedRule;
            customerPickRuleMap = pickRule;

            packRuleList = packRule;
            storageIdListMap = storageIdList;
            rpSearchTypeList = rpSearchType;
            batchModifyList =batchModifyListTmp;
        }

        logger.info("end load select check form...");
    }

    public void initReload() {
        //产品
        List<Select> allProduct = new ArrayList<>(10240);
        Set<String> allProductCodeSet = new HashSet<>(10240);
        Map<String,Map<String,String>> customProdIdValueMapTmp = new HashMap<>();
        Map<String, List<Select>> allProductGroupByCustomer = selectService.findAllProduct(allProduct,customProdIdValueMapTmp);
        Set<String> allProductCode = new HashSet<>(10240);
        Map<String, Set<String>> allProdCodeGroupByCustomer = selectService.findAllProdCode(allProductCode);
        Map<String, PageData> prodCodePd = new HashMap<>(5200);
        Map<String, Map<String, PageData>> customerProdCodePd = selectService.findAllProductMap(prodCodePd);
        Map<String, PageData> prodIdMap = selectService.findProductIdMap();

        allProductList = allProduct;
        allProductGroupByCustomerMap = allProductGroupByCustomer;
        customProdIdValueMap = customProdIdValueMapTmp;

        allProductCodeSet = allProductCode;
        allProdCodeGroupByCustomerMap = allProdCodeGroupByCustomer;
        prodCodePdMap = prodCodePd;
        customerProdCodePdMap = customerProdCodePd;
        prodIdPdMap = prodIdMap;
    }

    public void addBatchModify(String batchModify) {
       // batchModifyList.add(new Select(batchModify,batchModify));
       // batchModifyList.sort((e1,e2)->Integer.parseInt(e2.getValue()) - Integer.parseInt(e1.getValue()));
    }


    private Map<String, List<Select>> groupBy(List<Select> list) {
        if (list == null || list.size() == 0) {
            return new HashMap<>();
        }
        Map<String, List<Select>> selectMap = new HashMap<>();
        for (Select s : list) {
            List<Select> lst = selectMap.get(s.getGroup());
            if (lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(s);
            selectMap.put(s.getGroup(), lst);
        }
        return selectMap;
    }

    private List<Select> getByType(String type) {
        List<Select> list = selectMap.get(type);
        if (list != null && list.size() > 1) {
            list.sort((e1, e2) -> e1.getSort() - e2.getSort());
        }
        return list;
    }


    public List<Select> getStorageType() {
        return getByType(SelectUtil.STORAGE_TYPE);
    }

    /**
     * 库位属性
     *
     * @return 库位属性
     */
    public List<Select> getLocatorState() {
        return getByType(SelectUtil.LOCATOR_STATE);
    }

    /**
     * 库位使用
     *
     * @return 库位使用
     */
    public List<Select> getLocatorUse() {
        return getByType(SelectUtil.LOCATOR_USE);
    }

    /**
     * 库位类型
     *
     * @return 库位类型
     */
    public List<Select> getLocatorType() {
        return getByType(SelectUtil.LOCATOR_TYPE);
    }

    /**
     * 库位处理
     *
     * @return 库位处理
     */
    public List<Select> getLocatorUnit() {
        return getByType(SelectUtil.LOCATOR_UNIT);
    }

    /**
     * 周转周期
     *
     * @return 周转周期
     */
    public List<Select> getTurnoverCycle() {
        return getByType(SelectUtil.TURNOVER_CYCLE);
    }

    /**
     * 是否
     *
     * @return 是否
     */
    public List<Select> getBoolean() {
        return getByType(SelectUtil.BOOlEAN_FLG);
    }

    /**
     * 毛重单位
     *
     * @return 毛重单位
     */
    public List<Select> getTotalWeight() {
        return getByType(SelectUtil.TOTAL_WEIGHT_UNIT);
    }

    /**
     * 净重单位
     *
     * @return 净重单位
     */
    public List<Select> getTotalSuttle() {
        return getByType(SelectUtil.TOTAL_SUTTLE_UNIT);
    }

    /**
     * 体积单位
     *
     * @return 体积单位
     */
    public List<Select> getVolume() {
        return getByType(SelectUtil.VOLUME_UNIT);
    }

    /**
     * 冻结/拒收代码
     *
     * @return 冻结/拒收代码
     */
    public List<Select> getFreeReject() {
        return getByType(SelectUtil.FREEZE_REJECT_CODE);
    }

    /**
     * 周期类型
     *
     * @return 周期类型
     */
    public List<Select> getPeriod() {
        return getByType(SelectUtil.PERIOD_TYPE);
    }

    /**
     * 分配状态
     *
     * @return 分配状态
     */
    public List<Select> getAssigned() {
        return getByType(SelectUtil.ASSIGNED_STATE);
    }

    /**
     * 周期类型
     *
     * @return 周期类型
     */
    public List<Select> getOrderBatchType() {
        return getByType(SelectUtil.ORDERBATCH_TYPE);
    }

    //装车状态
    public List<Select> getLoadType() {
        return getByType(SelectUtil.LOADED_TYPE);
    }

    //发货方式
    public List<Select> getOutStockWay() {
        return getByType(SelectUtil.OUTSTOCK_WAY);
    }

    //拣货状态
    public List<Select> getPickState() {
        return getByType(SelectUtil.PICK_STATE);
    }


    //配载状态
    public List<Select> getCargoState() {
        return getByType(SelectUtil.CARGO_STATE);
    }

    //冻结状态
    public List<Select> getFreezeState() {
        return getByType(SelectUtil.FREEZE_STATE);
    }

    //发货状态
    public List<Select> getDepotType() {
        return getByType(SelectUtil.DEPOT_TYPE);
    }

    //包装单位
    public List<Select> getPackUnit() {
        return getByType(SelectUtil.PACK_UNIT);
    }


    public String getBatchId(String batchName) {
        List<Select> list = getOrderBatchType();
        for (Select s : list) {
            if (s.getValue().equals(batchName)) {
                return s.getId();
            }
        }
        return null;
    }

    //入库类型
    public List<Select> getStockInType() {
        return getByType(SelectUtil.INSTOCK_TYPE);
    }

    //入库状态/收货状态
    public List<Select> getStockInState() {
        return getByType(SelectUtil.INSTOCK_STATE);
    }

    //码放状态
    public List<Select> getPutState() {
        return getByType(SelectUtil.PUT_STATE);
    }

    //优先级
    public List<Select> getPriorityLevel() {
        return getByType(SelectUtil.PRIORITY_LEVEL);
    }

    //体积重量计算方式
    public List<Select> getBulkRule() {
        return getByType(SelectUtil.RULE_BULK);
    }

    //库位指定规则
    public List<Select> getStorageRule() {
        return getByType(SelectUtil.RULE_STORAGE);
    }

    //币种
    public List<Select> getCurrencyType() {
        return getByType(SelectUtil.CURRENCY_TYPE);
    }

    //车型
    public List<Select> getCarType() {
        return getByType(SelectUtil.CAR_TYPE);
    }

    //货物类型
    public List<Select> getProductType() {
        return getByType(SelectUtil.PRODUCT_TYPE);
    }

    //库位优先级
    public List<Select> getLocatorPriorityLevel() {
        return getByType(SelectUtil.LOCATOR_PRIORITY_LEVEL);
    }

    //出库类型
    public List<Select> getOutStockType() {
        return getByType(SelectUtil.OUTSTOCK_TYPE);
    }

    //承运人
    public List<Select> getTpHaulier() {
        return getByType(SelectUtil.TP_HAULIER);
    }

    //库存周转规则批次属性
    public List<Select> getStockTurnBatchPro() {
        return getByType(SelectUtil.BATCH_PROPERTY);
    }

    public List<Select> getSortBy() {
        return getByType(SelectUtil.SORT_BY);
    }

    /**
     * 客户
     *
     * @return 客户列表
     */
    public List<Select> getAllCustomer() {
        if (BaseInfoCache.getInstance().isOutCustomer()) {
            return getOutCustomer();
        } else {
            return getAllCustomers();
        }
    }

    public List<Select> getAllCustomers() {
        if (PropertyConfig.instance.isUseCache()) {
            return customerList;
        } else {
            return selectService.findAllCustomer();
        }
    }

    /**
     * 获取当前登录用户所属的外部客户
     * @return 所属客户列表
     */
    public List<Select> getOutCustomer() {
        List<Select> list = getAllCustomers();
        if (BaseInfoCache.getInstance().isOutCustomer()) {
            String customerId = BaseInfoCache.getInstance().getBeloneCustomerId();
            if (StringUtil.isBlank(customerId)) {
                return list;
            }
            List<Select> rs = new ArrayList<>();
            for (Select s : list) {
                if (customerId.equals(s.getId())) {
                    rs.add(s);
                }
            }
            return rs;
        } else {
            return list;
        }
    }

    public List<Select> getAllUser() {
        return userList;
    }


    public String getCustomerCode(String id) {
        List<Select> allCustomer = getAllCustomer();
        for (Select s : allCustomer) {
            if (s.getId().equals(id)) {
                return s.getCode();
            }
        }
        return null;
    }

    public List<PageData> getAllCustomerPd() {
        List<Select> clist;
        if (PropertyConfig.instance.isUseCache()) {
            clist = customerList;
        } else {
            clist = selectService.findAllCustomer();
        }

        List<PageData> list = new ArrayList<>();
        for (Select s : clist) {
            PageData pd = new PageData();
            pd.put("CUSTOMER_ID", s.getId());
            pd.put("CUSTOMER_CN", s.getValue());
            pd.put("CUSTOMER_CODE", s.getCode());
            list.add(pd);
        }
        return list;
    }

    /**
     * @return 获取所有的库区
     */
    public List<Select> getAllStorage() {
        if (PropertyConfig.instance.isUseCache()) {
            return allStorageList;
        } else {
            return selectService.findAllStorage();
        }
    }

    //获取所有库区map(库区编码，对应实体)
    public Map<String, Select> getAllStorageMap() {
        List<Select> list = getAllStorage();
        Map<String, Select> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (Select s : list) {
            map.put(s.getCode(), s);
        }
        return map;
    }

    /**
     * @return 获取所有的库位
     */
    public List<Select> getAllLocator() {
        if (PropertyConfig.instance.isUseCache()) {
            return allLocatorList;
        } else {
            return selectService.findAllLocator();
        }

    }

    //获取所有库位map(库区编码，对应实体)
    public Map<String, Select> getAllLocatorMap() {
        List<Select> list = getAllLocator();
        Map<String, Select> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (Select s : list) {
            map.put(s.getValue(), s);
        }
        return map;
    }

    public Map<String, Select> getAllLocatorIdMap() {
        List<Select> list = getAllLocator();
        Map<String, Select> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (Select s : list) {
            map.put(s.getId(), s);
        }
        return map;
    }

    /**
     * @return 根获取所有的库位, 包含缺省分配库位
     */
    public List<Select> getdAllLocatorForShow() {
        if (PropertyConfig.instance.isUseCache()) {
            return allLocatorForShowList;
        } else {
            return selectService.findAllLocatorForShow();
        }
    }


    /**
     * 根据库区获取库位
     *
     * @param storageId 库区ID
     * @return 库位列表
     */
    public List<Select> getLocator(String storageId) {
        if (PropertyConfig.instance.isUseCache()) {
            if (StringUtil.isEmpty(storageId)) {
                return allLocatorForShowList;
            } else {
                return storageIdListMap.get(storageId);
            }
        }
        return selectService.findLocatorByStorage(storageId);
    }


    /**
     * 获取所有的包装规则
     *
     * @return 包装规则
     */
    public List<Select> getPackRule() {
        if (PropertyConfig.instance.isUseCache()) {
            return packRuleList;
        }
        return selectService.findPackRule();
    }

    /**
     * 获取拣货规则
     *
     * @param customerId 客户Id
     * @return 拣货规则
     */
    public List<Select> getPickRule(String customerId) {
        if (PropertyConfig.instance.isUseCache()) {
            return customerPickRuleMap.get(customerId);
        }
        return selectService.findPickRule(customerId);
    }


    /**
     * 获取库存分配规则
     *
     * @param customerId 客户Id
     * @return 库存分配规则
     */
    public List<Select> getStockAssignedRule(String customerId) {
        if (PropertyConfig.instance.isUseCache()) {
            return customerStockAssignedRuleMap.get(customerId);
        }
        return selectService.findStockAssignedRule(customerId);
    }

    /**
     * 获取所有的库存周转规则
     *
     * @return 包装规则
     */
    public List<Select> getStockTurnRule() {
        if (PropertyConfig.instance.isUseCache()) {
            return stockTurnRuleList;
        }
        return selectService.findStockTurnRule();
    }

    /**
     * 获取库位分配规则
     *
     * @param customerId 客户Id
     * @return 库位分配规则
     */
    public List<Select> getStorageAssignedRule(String customerId) {
        if (PropertyConfig.instance.isUseCache()) {
            return customerStorageAssignedRuleMap.get(customerId);
        }
        return selectService.findStorageAssignedRule(customerId);
    }


    /**
     * 根获取客户所有的产品
     *
     * @return 产品列表
     */
    public List<Select> getAllProduct(String customerId) {
        if (PropertyConfig.instance.isUseCache()) {
            if (!StringUtil.isBlank(customerId)) {
                return allProductGroupByCustomerMap.get(customerId);
            } else {
                return allProductList;
            }
        } else {
            return selectService.findAllProduct(customerId);
        }
    }

    //根据客户获取产品的下拉列表
    public Map<String,String> getProdSelectMap(String customerId) {
        return customProdIdValueMap.get(customerId);
    }

    public Set<String> getAllProdCode(String customerId) {
        if (PropertyConfig.instance.isUseCache()) {
            if (StringUtil.isNotEmpty(customerId)) {
                return allProdCodeGroupByCustomerMap.get(customerId);
            } else {
                return allProductCodeSet;
            }
        } else {
            return selectService.findAllProdCode(customerId);
        }
    }

    //获取所有的仓库
    public List<Select> getAllStock() {
        if (PropertyConfig.instance.isUseCache()) {
            return allStockList;
        } else {
            return selectService.findAllStock();
        }
    }

    public List<PageData> getAllStockPd() {
        if (PropertyConfig.instance.isUseCache()) {
            return allStockPdList;
        } else {
            return selectService.findAllStockPd();
        }
    }


    /**
     * 根据产品id获取产品的信息
     *
     * @param productId 产品Id
     * @return 产品
     */
    public PageData getProduct(String productId) {
        PageData rs = prodIdPdMap.get(productId);
        if (rs == null) {
            return selectService.findProduct(productId);
        }
        return rs;
        //TOTAL_WEIGHT_UNIT,A.TOTAL_WEIGHT,A.TOTAL_SUTTLE_UNIT,A.TOTAL_SUTTLE,A.VOLUME_UNIT,A.VOLUME_UN
    }

    public List<PageData> getPackRuleListById(String packRuleId) {
        if (PropertyConfig.instance.isUseCache()) {
            return packRuleIdMap.get(packRuleId);
        } else {
            return selectService.findPackRuleListById(packRuleId);
        }
    }

    /**
     * 根据产品id获取产品的信息
     *
     * @param productId 产品Id
     * @return 产品
     */
    public List<PageData> getProductList(String productId) {
        PageData product = getProduct(productId);
        List<PageData> list = new ArrayList<>();
        if (product != null) {
            list.add(product);
        }
        return list;
    }

    public Set<String> getProdCodeSet(String customerId) {
        if (PropertyConfig.instance.isUseCache()) {
            if (StringUtil.isNotEmpty(customerId)) {
                return allProdCodeGroupByCustomerMap.get(customerId);
            } else {
                return allProductCodeSet;
            }
        } else {
            return selectService.findAllProdCode(customerId);
        }
        /// return selectService.findProdCodeSet(customerId);
    }

    //得到 产品编码，对应实体
    public Map<String, PageData> getProductMap(String customerId) {
        if (PropertyConfig.instance.isUseCache()) {
            if (StringUtil.isNotEmpty(customerId)) {
                return customerProdCodePdMap.get(customerId);
            } else {
                return prodCodePdMap;
            }
        } else {
            return selectService.findProductMap(customerId);
        }
    }

    public Set<String> getProductSet(Map<String, PageData> map) {
        return selectService.buildMap(map);
    }

    public Map<String, PageData> toUpCaseKey(Map<String, PageData> map) {
        return selectService.toUpCaseKey(map);
    }

    public List<Select> findRpSearchType(){
        return rpSearchTypeList;
    }

    public List<Select>  getBatchModify() {
       return batchModifyList;
    }
}
