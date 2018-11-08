package com.hw.cache;

import com.hw.service.base.BaseInfoService;
import com.hw.util.*;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Panda.HuangWei.
 * @Create 2016.11.20 13:45.
 * @Version 1.0 .
 */
public class BaseInfoCache extends ReloadableSpringBean {
    protected Logger logger = Logger.getLogger(this.getClass());
    /**
     * 产品列表
     */
    private List<PageData> productList = new ArrayList<>();
    private Map<String, String> prodCodeIdMap = new HashMap<>(5120);
    private Map<String, String> prodIdCodeMap = new HashMap<>(5120);

    private Map<String, String> stockCodeIdMap = new HashMap<>();
    private Map<String, String> stockIdCodeMap = new HashMap<>();

    private Map<String, String> storageCodeIdMap = new HashMap<>(32);
    private Map<String, String> storageIdCodeMap = new HashMap<>(32);
    private Map<String, Map<Integer, String>> customerSeq2DMap = new HashMap<>();
    private Map<String, Pair<String, String>> customerSplit2DMap = new HashMap<>();

    private Map<String, String> locatorIdCodeMap = new HashMap<>(512);
    private Map<String, String> locatorCodeIdMap = new HashMap<>(512);
    private Map<String, String> locatorStorageIdMap = new HashMap<>(512);

    private Map<String, List<PageData>> stockIdStockStockageLocatorMap = new HashMap<>();
    private Map<String, Pair<String, String>> stockageIdMapStockIdCode = new HashMap<>();
    private Map<String, Pair<String, String>> locatorIdMapStockIdCode = new HashMap<>();
    private Map<String, String> stockMapILocatorMap = new HashMap<>();
    private Map<String, PageData> boxIdPdMap = new HashMap<>(51200);

    /**
     * 扫描料号与实际料号
     */
  /*  private Map<String, String> scanActualPnMap = new HashMap<>(5120);
    private Map<String, String> scanProdIdMap = new HashMap<>(5120);*/
    /**
     * map(客户,map(扫描料号,产品Id))
     */
    private Map<String, Map<String, String>> customerScanProdIdMap = new HashMap<>();
    /**
     * map(客户,map(扫描料号,产品编码))
     */
    private Map<String, Map<String, String>> customerScanProdCodeMap = new HashMap<>();

    /**
     * 库存分配规则 map(规则ID,分配规则)
     */
    private Map<String, PageData> stockAssignMap = new HashMap<>(8);
    private Map<String, String> userCustomerIdMap = new HashMap<>(32);
    private Set<String> outCustomerSet = new HashSet<>();
    //包装规则、拣货规则
    private List<PageData> packList = new ArrayList<>();
    private List<PageData> pickList = new ArrayList<>();
    //库存分配规则tb_stockasignrule
    private List<PageData> stockasignruleList = new ArrayList<>();
    //库位指定贵则tb_storageasignrule
    private List<PageData> storageasignruleList = new ArrayList<>();
    //库存周转tb_stockturn
    private List<PageData> stockturnList = new ArrayList<>();

    private Map<String, String> searchColumMap = new HashMap<>();

    private Map<String, String> customerIdCodeMap = new HashMap<>(16);
    private Map<String, String> customerCodeIdMap = new HashMap<>(16);
    private Map<String, Set<String>> customerBoxRule = new HashMap<>();

    private Map<String, List<String>> searchColumListMap = new HashMap<>();
    Map<String, List<String>> nameColumMap = new HashMap<>();

    //构建属性中所有属性纪录按客户——批次属性分组
    private Map<String, List<PageData>> customerPropertyMap = new HashMap<>();

    private Map<String, List<String>> inOutStockSearchColumListMap = new HashMap<>();
    private Map<String, List<String>> inOutStockNameColumMap = new HashMap<>();
    private Map<String, String> inOutStockSearchColumMap = new HashMap<>();

    private Set<String> allBigBoxNoSet = new HashSet<>(30000);
    private Set<String> allBoxNoSet = new HashSet<>(30000);

    private static BaseInfoCache instance;

    private BaseInfoCache() {
        BaseInfoCache.instance = this;
    }


    /**
     * @Title: getInstance   \
     */
    public static BaseInfoCache getInstance() {
        return instance;
    }

    @Resource(name = "baseInfoService")
    private BaseInfoService baseInfoService;

    @Override
    public void reload() {
        List<PageData> list = baseInfoService.findAllProduct();
        Map<String, String> codeIdMap = new HashMap<>(5120);
        Map<String, String> idCodeMap = new HashMap<>(5120);
        baseInfoService.buildProdCodeIdMap(list, codeIdMap, idCodeMap);

        Map<String, Map<String, String>> customerScanProdIdMapTmp = new HashMap<>(5120);
        Map<String, Map<String, String>> customerScanProdCodeMapTmp = baseInfoService.findScanProduct(customerScanProdIdMapTmp);
        Map<String, PageData> stockAssign = baseInfoService.findStockAssign();
        //构建仓库、库区、库位的关系
        Map<String, String> stockMapILocatorTmp = new HashMap<>();
        Map<String, Pair<String, String>> stockageIdMapStockIdCodeTmp = new HashMap<>();
        Map<String, Pair<String, String>> locatorIdMapStockIdCodeTmp = new HashMap<>();
        Map<String, List<PageData>> stockIdStockStockageLocatorMapTmp = baseInfoService.buildStockStockageLocator(stockMapILocatorTmp, stockageIdMapStockIdCodeTmp, locatorIdMapStockIdCodeTmp);

        Map<String, String> stockCodeIdMap2 = new HashMap<>();
        Map<String, String> stockIdCodeMap2 = new HashMap<>();
        baseInfoService.findStockMap(stockCodeIdMap2, stockIdCodeMap2);

        Map<String, String> locatorCodeIdMap2 = new HashMap<>();
        Map<String, String> locatorStorageIdMap2 = new HashMap<>();
        Map<String, String> locatorIdCodeMap2 = baseInfoService.findLocatorIdCodeMap(locatorCodeIdMap2, locatorStorageIdMap2);

        Map<String, String> storageCodeIdMap2 = new HashMap<>();
        Map<String, String> storageIdCodeMap2 = new HashMap<>();
        baseInfoService.findStoreMap(storageCodeIdMap2, storageIdCodeMap2);

        Set<String> allBigBoxNoSetTmp = new HashSet<>(30000);
        Set<String> allBoxNoSetTmp = new HashSet<>(30000);
        Map<String, PageData> allBoxIdMapTmp = baseInfoService.findAllBox(allBigBoxNoSetTmp, allBoxNoSetTmp);
        Map<String, Pair<String, String>> customerSplit2DMapTmp = new HashMap<>();
        Map<String, Map<Integer, String>> customerSeq2DMapTmp = baseInfoService.findCustomerSeq2DMap(customerSplit2DMapTmp);

        List<PageData> allPackList = baseInfoService.findAllPackList();
        List<PageData> allPickList = baseInfoService.findAllPickList();
        List<PageData> allStockasignruleList = baseInfoService.findAllStockasignruleList();
        List<PageData> allStorageasignruleList = baseInfoService.findAllStorageasignruleList();
        List<PageData> allStockturnList = baseInfoService.findAllStockturnList();
        Map<String, String> customerMap = baseInfoService.findCustomerMap();
        Map<String, String> customCodeIdMap = baseInfoService.buildCustomerCodeIdMap(customerMap);

        Map<String, List<String>> searchColumList = new HashMap<>();
        Map<String, List<String>> nameColum = new HashMap<>();
        Map<String, String> searchColumMap1 = baseInfoService.buildSearchColum(customerMap, searchColumList, nameColum);

        Map<String, List<String>> inOutStockSearchColumListTmp = new HashMap<>();
        Map<String, List<String>> inOutStockNameColumTmp = new HashMap<>();
        Map<String, String> inOutStockSearchColumMapTmp = baseInfoService.buildOutStockSearchColum(customerMap, inOutStockSearchColumListTmp, inOutStockNameColumTmp);

        Map<String, Set<String>> customBoxRule = baseInfoService.findCustomerBoxRule(customerMap);

        Map<String, List<PageData>> customerPropertyMapTmp = baseInfoService.buildAllProperties();

        Map<String, String> userCustomerIdMapTmp = new HashMap<>();
        Set<String> outCustomerSetTmp = new HashSet<>();
        baseInfoService.findAllUser(userCustomerIdMapTmp, outCustomerSetTmp);

        if (codeIdMap != null && !codeIdMap.isEmpty()) {
            prodCodeIdMap = codeIdMap;
            prodIdCodeMap = idCodeMap;
            productList = list;
            customerScanProdIdMap = customerScanProdIdMapTmp;
            customerScanProdCodeMap = customerScanProdCodeMapTmp;
            stockCodeIdMap = stockCodeIdMap2;
            stockIdCodeMap = stockIdCodeMap2;
            stockIdStockStockageLocatorMap = stockIdStockStockageLocatorMapTmp;
            stockageIdMapStockIdCode = stockageIdMapStockIdCodeTmp;
            locatorIdMapStockIdCode = locatorIdMapStockIdCodeTmp;
            stockMapILocatorMap = stockMapILocatorTmp;
            stockAssignMap = stockAssign;
            boxIdPdMap = allBoxIdMapTmp;
            customerSeq2DMap = customerSeq2DMapTmp;
            customerSplit2DMap = customerSplit2DMapTmp;
            storageCodeIdMap = storageCodeIdMap2;
            storageIdCodeMap = storageIdCodeMap2;

            locatorIdCodeMap = locatorIdCodeMap2;
            locatorCodeIdMap = locatorCodeIdMap2;
            locatorStorageIdMap = locatorStorageIdMap2;

            allBigBoxNoSet = allBigBoxNoSetTmp;
            allBoxNoSet = allBoxNoSetTmp;

            packList = allPackList;
            pickList = allPickList;
            stockasignruleList = allStockasignruleList;
            storageasignruleList = allStorageasignruleList;
            stockturnList = allStockturnList;
            customerIdCodeMap = customerMap;
            customerCodeIdMap = customCodeIdMap;
            searchColumMap = searchColumMap1;
            customerBoxRule = customBoxRule;
            searchColumListMap = searchColumList;
            nameColumMap = nameColum;

            customerPropertyMap = customerPropertyMapTmp;
            userCustomerIdMap = userCustomerIdMapTmp;
            outCustomerSet = outCustomerSetTmp;

            inOutStockSearchColumListMap = inOutStockSearchColumListTmp;
            inOutStockNameColumMap = inOutStockNameColumTmp;
            inOutStockSearchColumMap = inOutStockSearchColumMapTmp;
        }
    }


    public void initReload() {
        List<PageData> list = baseInfoService.findAllProduct();
        Map<String, String> codeIdMap = new HashMap<>(5120);
        Map<String, String> idCodeMap = new HashMap<>(5120);
        baseInfoService.buildProdCodeIdMap(list, codeIdMap, idCodeMap);

        Map<String, Map<String, String>> customerScanProdIdMapTmp = new HashMap<>(5120);
        Map<String, Map<String, String>> customerScanProdCodeMapTmp = baseInfoService.findScanProduct(customerScanProdIdMapTmp);
        if (list == null || list.isEmpty()) {
            return;
        }
        prodCodeIdMap = codeIdMap;
        prodIdCodeMap = idCodeMap;
        productList = list;
        customerScanProdIdMap = customerScanProdIdMapTmp;
        customerScanProdCodeMap = customerScanProdCodeMapTmp;
    }

    public List<PageData> getPackList() {
        return packList;
    }

    public List<PageData> getPickList() {
        return pickList;
    }

    public List<PageData> getStockasignruleList() {
        return stockasignruleList;
    }

    public List<PageData> getStorageasignruleList() {
        return storageasignruleList;
    }

    public List<PageData> getStockturnList() {
        return stockturnList;
    }

    public Map<String, String> getScanCodeProdCodeMap(String customerId) {
        return customerScanProdCodeMap.get(customerId);
    }

    /**
     * 根据仓库Id获取对应I库位的Id
     *
     * @param stockId 仓库Id
     * @return I库位的Id
     */
    public String getILocatorIdByStockId(String stockId) {
        return stockMapILocatorMap == null ? Const.EMPTY_CH : stockMapILocatorMap.get(stockId);
    }

    /**
     * 根据扫描料号获取对应产品的ID
     *
     * @param scanProd 扫描料号
     * @return
     */
    public String getProdIdByCustomerIdAndScanCode(String customerId, String scanProd) {
        Map<String, String> scanProdIdMap = customerScanProdIdMap.get(customerId);
        return scanProdIdMap == null ? Const.EMPTY_CH : scanProdIdMap.get(StringUtil.trimSpace(scanProd).toUpperCase());
    }

    public Map<String, String> getLocatorCodeIdMap() {
        return locatorCodeIdMap;
    }

    /**
     * 根据库位编码获取ID
     *
     * @param locatorCode 库位编码
     * @return
     */
    public String getLocatorId(String locatorCode) {
        return locatorCodeIdMap == null ? Const.EMPTY_CH : locatorCodeIdMap.get(locatorCode);
    }


    public String getStorageIdByLocatorId(String locatorId) {
        return locatorStorageIdMap == null ? Const.EMPTY_CH : locatorStorageIdMap.get(locatorId);
    }

    public String getStockId(String stockCode) {
        return stockCodeIdMap == null ? Const.EMPTY_CH : stockCodeIdMap.get(stockCode);
    }

    /**
     * 根据库区编码获取ID
     *
     * @param storageCode 仓库编码
     * @return
     */
    public String getStorageId(String storageCode) {
        return storageCodeIdMap == null ? Const.EMPTY_CH : storageCodeIdMap.get(storageCode);
    }

    //根据库区ID获取编码
    public String getStorageCode(String storageId) {
        return storageIdCodeMap == null ? Const.EMPTY_CH : storageIdCodeMap.get(storageId);
    }


    public String getInboundSql(String costomer) {
        return searchColumMap.get(costomer + Const.INSTOCK_MID);
    }

    public String getOutboundSql(String costomer) {
        return searchColumMap.get(costomer + Const.OUTSTOCK_MID);
    }

    public String getInboundRemarkSql(String costomer) {
        return searchColumMap.get(costomer + Const.REMARK_ASSIGNED);
    }

    public List<String> getInboundColum(String costomer) {
        return searchColumListMap.get(costomer + Const.INSTOCK_MID);
    }

    public List<String> getOutboundColum(String costomer) {
        return searchColumListMap.get(costomer + Const.OUTSTOCK_MID);
    }

    public List<String> getInboundNameColum(String costomer) {
        return nameColumMap.get(costomer + Const.INSTOCK_MID);
    }

    public List<String> getOutboundNameColum(String costomer) {
        return nameColumMap.get(costomer + Const.OUTSTOCK_MID);
    }

    public Set<String> getBoxRule(String customer) {
        Set<String> set = customerBoxRule.get(customer);
        return set == null ? new HashSet<>() : set;
    }

    /**
     * 根据产品ID获取产品编码
     *
     * @param prodId 产品ID
     * @return 产品编码
     */
    public String getProductCode(String prodId) {
        return prodIdCodeMap == null ? null : prodIdCodeMap.get(prodId);
    }

    /**
     * 根据产品编码获取产品ID
     *
     * @param prodCode 产品编码
     * @return 产品ID
     */
    public String getProductId(String prodCode) {
        return prodCodeIdMap == null ? null : prodCodeIdMap.get(prodCode);
    }

    /**
     * 根据库存分配规则ID获取库存分配规则
     *
     * @param id 库存分配规则ID
     * @return 库存分配规则
     */
    public PageData getStockAssign(String id) {
        return stockAssignMap == null ? null : stockAssignMap.get(id);
    }

    /**
     * 根据库位ID获取库位编码
     *
     * @param locatorId 库位ID
     * @return 库位编码
     */
    public String getLocatorCode(String locatorId) {
        return locatorIdCodeMap == null ? null : locatorIdCodeMap.get(locatorId);
    }

    public String getCustomerCode(String customerId) {
        return customerIdCodeMap == null ? null : customerIdCodeMap.get(customerId);
    }

    public String getCustomerId(String customerCode) {
        return customerCodeIdMap == null ? null : customerCodeIdMap.get(customerCode);
    }


    /**
     * 根据客户ID获取客户的入库单订单属性
     *
     * @param customerId 客户ID
     * @return 订单属性列表
     */
    public List<PageData> getPropertyINOrder(String customerId) {
        String key = StringUtil.genKey(customerId, SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_ORDER.getItemValue()));
        return customerPropertyMap.get(key);
    }

    /**
     * 根据客户ID获取客户的入库单批次属性
     *
     * @param customerId 客户ID
     * @return 批次属性列表
     */
    public List<PageData> getPropertyINBatch(String customerId) {
        String key = StringUtil.genKey(customerId, SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_BATCH.getItemValue()));
        return customerPropertyMap.get(key);
    }

    /**
     * 根据客户ID获取客户的出库单订单属性
     *
     * @param customerId 客户ID
     * @return 订单属性列表
     */
    public List<PageData> getPropertyOutOrder(String customerId) {
        String key = StringUtil.genKey(customerId, SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_ORDER.getItemValue()));
        return customerPropertyMap.get(key);
    }

    /**
     * 根据客户ID获取客户的出库单批次属性
     *
     * @param customerId 客户ID
     * @return 订批次属性列表
     */
    public List<PageData> getPropertyOutBatch(String customerId) {
        String key = StringUtil.genKey(customerId, SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));
        return customerPropertyMap.get(key);
    }

    /**
     * 根据箱号ID获取外箱号
     *
     * @param boxId 箱号id
     * @return 外箱号
     */
    public String getBigBoxNo(String boxId) {
        PageData pd = boxIdPdMap.get(boxId);
        return pd == null ? Const.EMPTY_CH : (StringUtil.isEmpty(pd.getString("BIG_BOX_NO")) ? Const.EMPTY_CH : pd.getString("BIG_BOX_NO"));
    }

    /**
     * 根据BoxId获取箱号实体
     *
     * @param boxId 箱号Id
     * @return 箱实体
     */
    public PageData getBoxPd(String boxId) {
        return boxIdPdMap.get(boxId);
    }

    /**
     * 根据客户ID获取客户对应的收货二维码信息
     *
     * @param customerId 客户ID
     * @return
     */
    public Map<Integer, String> get2DMap(String customerId) {
        return customerSeq2DMap.get(customerId);
    }

    /**
     * 根据客户获取分割符，以及代表二维码的字符串
     *
     * @param customerId
     * @return 如(分隔符|, 二维码(x|Y|z))
     */
    public Pair<String, String> get2DSplit(String customerId) {
        return customerSplit2DMap.get(customerId);
    }

    /**
     * 根据库区ID获取仓库代码
     *
     * @param stockageId 库区ID
     * @return 仓库代码
     */
    public String getStockCodeByStockageId(String stockageId) {
        if (StringUtil.isEmpty(stockageId)) {
            return null;
        }
        Pair<String, String> pair = stockageIdMapStockIdCode.get(stockageId);
        if (pair == null) {
            return null;
        }
        return pair.getRight();
    }

    /**
     * 是否是外部客户
     *
     * @param userName 当前登录的用户名
     * @return boolean
     */
    public boolean isOutCustomer(String userName) {
        return outCustomerSet.contains(userName);
    }

    /**
     * 判断当前登录的用户是否外部客户
     *
     * @return 是否外部客户账号
     */
    public boolean isOutCustomer() {
        return isOutCustomer(SessionUtil.getCurUserName());
    }

    /**
     * 获取用户名对应的客户Id
     *
     * @param userName 登录用户名
     * @return 客户Id
     */
    public String getBeloneCustomerId(String userName) {
        return userCustomerIdMap.get(userName);
    }

    /**
     * 获取当前登录用户名对应的客户Id
     *
     * @return 客户Id
     */
    public String getBeloneCustomerId() {
        return userCustomerIdMap.get(SessionUtil.getCurUserName());
    }

    /**
     * 根据客货Id获取客户对应的入库单订单属性查询sql
     *
     * @param customer 客户Id
     * @return sql
     */
    public String getInStockOrderSql(String customer) {
        return inOutStockSearchColumMap.get(customer + Const.INSTOCK_ORDER);
    }

    /**
     * 根据客货Id获取客户对应的入库单批次属性查询sql
     *
     * @param customer 客户Id
     * @return sql
     */
    public String getInStockBatchSql(String customer) {
        return inOutStockSearchColumMap.get(customer + Const.INSTOCK_BATCH);
    }

    /**
     * 根据客货Id获取客户对应的出库单订单属性查询sql
     *
     * @param customer 客户Id
     * @return sql
     */
    public String getOutStockOrderSql(String customer) {
        return inOutStockSearchColumMap.get(customer + Const.OUTSTOCK_ORDER);
    }

    /**
     * 根据客货Id获取客户对应的出库单属性属性查询sql
     *
     * @param customer 客户Id
     * @return sql
     */
    public String getOutStockBatchSql(String customer) {
        return inOutStockSearchColumMap.get(customer + Const.OUTSTOCK_BATCH);
    }

    public String getOutStockBatchEmptySql(String customer) {
        return inOutStockSearchColumMap.get(customer + Const.OUTSTOCK_BATCH_EMPTY);
    }

    public String getInStockBatchEmptySql(String customer) {
        return inOutStockSearchColumMap.get(customer + Const.INSTOCK_BATCH_EMPTY);
    }

    public List<String> getOutStockBatchColum(String customer) {
        return inOutStockSearchColumListMap.get(customer + Const.OUTSTOCK_BATCH);
    }

    public List<String> getOutStockBatchColumNames(String customer) {
        return inOutStockNameColumMap.get(customer + Const.OUTSTOCK_BATCH);
    }

    public List<String> getInStockBatchColum(String customer) {
        return inOutStockSearchColumListMap.get(customer + Const.INSTOCK_BATCH);
    }

    public List<String> getInStockBatchColumNames(String customer) {
        return inOutStockNameColumMap.get(customer + Const.INSTOCK_BATCH);
    }

    /**
     * 获取所有的外箱号
     *
     * @return 外箱号列表
     */
    public Set<String> getAllBigBoxSet() {
        return allBigBoxNoSet;
    }

    /**
     * 获取所有的箱号
     *
     * @return 内箱号列表
     */
    public Set<String> getAllBoxSet() {
        return allBoxNoSet;
    }

}
