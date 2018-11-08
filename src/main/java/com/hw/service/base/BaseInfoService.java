package com.hw.service.base;

import com.hw.dao.DaoSupport;
import com.hw.entity.base.Select;
import com.hw.util.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Panda.HuangWei on 2016/10/22.
 */
@Service("baseInfoService")
public class BaseInfoService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**
     * 根获取所有的产品
     * @return 产品列表
     */
    public List<PageData> findAllProduct(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findAllProduct", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取扫描料号与实际料号的对应关系
     * @return map(客户Id,map(扫描料号,实际料号))
     */
    public Map<String,Map<String,String>> findScanProduct(Map<String,Map<String,String>> customerScanProdIdMap) {

        try {
            Map<String,Map<String,String>> map = new HashMap<>();
            List<PageData> list = (List<PageData>) dao.findForList("BaseInfoMapper.findScanProduct", null);
            if (list == null || list.isEmpty()) {
                return map;
            }
            for (PageData e : list) {
                String customerId = e.getString("CUSTOMER_ID");
                String scanPn = StringUtil.trimSpace(e.getString("SCAN_PN").toUpperCase());
                Map<String, String> scanCodeProdCodeMap = map.get(customerId);
                scanCodeProdCodeMap = scanCodeProdCodeMap !=null?scanCodeProdCodeMap:new HashMap<>(10240);
                scanCodeProdCodeMap.put(scanPn, e.getString("PRODUCT_CODE"));
                map.put(customerId,scanCodeProdCodeMap);

                Map<String, String> scanCodeProdIdMap= customerScanProdIdMap.get(customerId);
                scanCodeProdIdMap = scanCodeProdIdMap != null ?scanCodeProdIdMap:new HashMap<>(10240);
                scanCodeProdIdMap.put(scanPn, e.getString("ACTUAL_PN"));
                customerScanProdIdMap.put(customerId,scanCodeProdIdMap);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * 获取库存分配规则
     * @return map(规则ID,分配规则)
     */
    public Map<String, PageData> findStockAssign() {

        try {
            Map<String, PageData> map = new HashMap<>();
            List<PageData> list = (List<PageData>) dao.findForList("BaseInfoMapper.findStockAssign", null);
            if (list == null || list.isEmpty()) {
                return map;
            }
            // PRODUCE_CYCLE  IN_STOCK_CYCLE 1先进先出 2 先进后出 PRODUCE_LEVEL
            for (PageData e : list) {
                if(StringUtil.isEmpty(e.getString("PRODUCE_LEVEL"))) {
                    e.put("PRODUCE_LEVEL",Const.FIRST_IN_FIRST_OUT);
                }

                if(StringUtil.isEmpty(e.getString("IN_STOCK_CYCLE"))) {
                    e.put("IN_STOCK_CYCLE",Const.FIRST_IN_FIRST_OUT);
                }
                if(StringUtil.isEmpty(e.getString("PRODUCE_CYCLE"))) {
                    e.put("PRODUCE_CYCLE",Const.FIRST_IN_FIRST_OUT);
                }
                map.put(e.getString("STOCKASIGNRULE_ID"), e);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    public Map<String,String> findLocatorIdCodeMap(Map<String, String> locatorCodeIdMap2,Map<String, String> locatorStorageIdMap) {
        try {
            List<PageData> locatorList = findLocatorList();
            Map<String,String> map = new HashMap<>(locatorList.size());
            for(PageData p : locatorList) {
                map.put(p.getString("LOCATOR_ID"),p.getString("LOCATOR_CODE"));
                locatorCodeIdMap2.put(p.getString("LOCATOR_CODE"),p.getString("LOCATOR_ID"));
                locatorStorageIdMap.put(p.getString("LOCATOR_ID"),p.getString("STORAGE_CODE"));
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    /**
     * 查询所有的仓库
     * @return
     * @throws Exception
     */
    public List<PageData> findStorageList() throws Exception{
        return (List<PageData>) dao.findForList("BaseInfoMapper.findStorageList", null);
    }

    public void findStockMap(Map<String,String> codeIdMap,Map<String,String> idCodeMap) {
        try {
            List<PageData> list = (List<PageData>) dao.findForList("BaseInfoMapper.findStockList", null);
            Map<String,String> map = new HashMap<>(list.size());
            for(PageData p : list) {
                idCodeMap.put(p.getString("STOCK_ID"),p.getString("STOCK_CODE"));
                codeIdMap.put(p.getString("STOCK_CODE"),p.getString("STOCK_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void findStoreMap(Map<String,String> codeIdMap,Map<String,String> idCodeMap) {
        try {
            List<PageData> list = findStorageList();
            Map<String,String> map = new HashMap<>(list.size());
            for(PageData p : list) {
                idCodeMap.put(p.getString("STORAGE_ID"),p.getString("STORAGE_CODE"));
                codeIdMap.put(p.getString("STORAGE_CODE"),p.getString("STORAGE_ID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有的库位
     * @return
     * @throws Exception
     */
    public List<PageData> findLocatorList() throws Exception{
       return (List<PageData>) dao.findForList("BaseInfoMapper.findLocatorList", null);
    }


    /**
     * 构建产品编码对应产品ID的map
     * @param list 产品列表
     */
    public  void buildProdCodeIdMap(List<PageData> list, Map<String,String> codeIdMap, Map<String,String> idCodeMap) {

        if(list == null || list.isEmpty()) {
            return;
        }

        for(PageData e :list) {
            String prodId = e.getString("PRODUCT_ID");
            String prodCode = e.getString("PRODUCT_CODE");

            if(StringUtil.isEmpty(prodId)||StringUtil.isEmpty(prodCode)) {
                continue;
            }
            codeIdMap.put(prodCode,prodId);
            idCodeMap.put(prodId,prodCode);
        }
    }
    //包装规则、拣货规则
    public List<PageData> findAllPackList(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findAllPackList", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //拣货规则
    public List<PageData> findAllPickList(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findAllPickList", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //库存分配规则tb_stockasignrule
    public List<PageData> findAllStockasignruleList(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findAllStockasignruleList", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    //库位分配规则tb_storageasignrule
    public List<PageData> findAllStorageasignruleList(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findAllStorageasignruleList", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }




    //库存周转tb_stockturn
    public List<PageData> findAllStockturnList(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findAllStockturnList", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public Map<String,String> findCustomerMap() {
        List<Select> allCustomer = findAllCustomer();
        Map<String,String> map = new HashMap<>();
        for (Select s :allCustomer) {
            map.put(s.getId(),s.getCode());
        }
        return map;
    }

    public Map<String,String> buildCustomerCodeIdMap(Map<String,String> map) {
        Map<String,String> rs = new HashMap<>();
        for (Map.Entry<String,String> e: map.entrySet()) {
            rs.put(e.getValue(),e.getKey());
        }
        return rs;
    }


    //构建动态的的查询字段
    public Map<String, String> buildSearchColum(Map<String,String> customerMap,Map<String,List<String>> customerColum,Map<String,List<String>> nameColum) {
        Map<String, String> map = new HashMap<>();
        //map(客户id-类型,map(数据库定义字段名，数据库字段))
        Map<String, Map<String, String>> mapMap = buildAllProperties(findAllPropertys());

        for (Map.Entry<String,String> entry : customerMap.entrySet()) {
            String customer = entry.getKey();
            Map<String, String> order = mapMap.get(customer + Const.SPLIT_LINE + WmsEnum.OrderPropertyID.IN_ORDER.getItemKey());
            if (order == null) {
                continue;
            }
            Map<String, String> batch = mapMap.get(customer + Const.SPLIT_LINE + WmsEnum.OrderPropertyID.IN_BATCH.getItemKey());
            if (batch == null) {
                continue;
            }
            Map<String, String> order1 = mapMap.get(customer + Const.SPLIT_LINE + WmsEnum.OrderPropertyID.OUT_ORDER.getItemKey());
            if (order1 == null) {
                continue;
            }
            Map<String, String> batch1 = mapMap.get(customer + Const.SPLIT_LINE + WmsEnum.OrderPropertyID.OUT_BATCH.getItemKey());
            if (batch1 == null) {
                continue;
            }
            List<String> colums = new ArrayList<>();
            List<String> colums2 = new ArrayList<>();

            List<String> columNames = new ArrayList<>();
            List<String> columNames2 = new ArrayList<>();

            map.put(customer+Const.INSTOCK_MID,RpSqlGenUtil.buildInboundSql(order,batch,colums,columNames));
            map.put(customer+Const.OUTSTOCK_MID,RpSqlGenUtil.buildOutboundSql(order1,batch1,colums2,columNames2));

            List<String> colums3 = new ArrayList<>();
            List<String> columNames3 = new ArrayList<>();
            map.put(customer+Const.REMARK_ASSIGNED,RpSqlGenUtil.buildInboundRemarkSql(order,batch,colums3,columNames3));

            customerColum.put(customer+Const.INSTOCK_MID,colums);
            customerColum.put(customer+Const.OUTSTOCK_MID,colums2);

            customerColum.put(customer+Const.INSTOCK_MID,colums);
            customerColum.put(customer+Const.OUTSTOCK_MID,colums2);

            nameColum.put(customer+Const.INSTOCK_MID,columNames);
            nameColum.put(customer+Const.OUTSTOCK_MID,columNames2);
        }

        return map;
    }

    /**
     * 构建出库单的动态的的查询字段
     * @param customerMap 客户IDCodeMap
     * @param customerColum 客户
     * @param nameColum 查询的地段名称
     * @return 客户id-标识,对应的查询字段
     */
    public Map<String, String> buildOutStockSearchColum(Map<String,String> customerMap,Map<String,List<String>> customerColum,Map<String,List<String>> nameColum) {
        Map<String, String> map = new HashMap<>();
        //map(客户id-类型,map(数据库定义字段名，数据库字段))
        Map<String, Map<String, String>> mapMap = buildAllProperties(findAllPropertys());

        for (Map.Entry<String,String> entry : customerMap.entrySet()) {
            String customer = entry.getKey();
            Map<String, String> inStockOrder = mapMap.get(customer + Const.SPLIT_LINE + WmsEnum.OrderPropertyID.IN_ORDER.getItemKey());
            if (inStockOrder == null) {
                continue;
            }
            Map<String, String> inStockBatch = mapMap.get(customer + Const.SPLIT_LINE + WmsEnum.OrderPropertyID.IN_BATCH.getItemKey());
            if (inStockBatch == null) {
                continue;
            }
            Map<String, String> outStockOrder = mapMap.get(customer + Const.SPLIT_LINE + WmsEnum.OrderPropertyID.OUT_ORDER.getItemKey());
            if (outStockOrder == null) {
                continue;
            }
            Map<String, String> outStockBatch = mapMap.get(customer + Const.SPLIT_LINE + WmsEnum.OrderPropertyID.OUT_BATCH.getItemKey());
            if (outStockBatch == null) {
                continue;
            }
            List<String> inOrderColums = new ArrayList<>();
            List<String> inBatchColums2 = new ArrayList<>();

            List<String> inOrderColumNames = new ArrayList<>();
            List<String> inBatchColumNames2 = new ArrayList<>();

            //订单属性对应字段sql,批次属性对应字段sql
            Pair<String, String> pair = RpSqlGenUtil.buildInOutStockPropertiesSql(inStockOrder, inStockBatch, inOrderColums, inOrderColumNames, inBatchColums2, inBatchColumNames2);
            //入库单的属性名称字段
            nameColum.put(customer+Const.INSTOCK_ORDER,inOrderColumNames);
            nameColum.put(customer+Const.INSTOCK_BATCH,inBatchColumNames2);
            //入库单的属性查询生效字段
            customerColum.put(customer+Const.INSTOCK_ORDER,inOrderColums);
            customerColum.put(customer+Const.INSTOCK_BATCH,inBatchColums2);

            map.put(customer+Const.INSTOCK_ORDER,pair.getLeft());
            map.put(customer+Const.INSTOCK_BATCH,pair.getRight());
            map.put(customer+Const.INSTOCK_BATCH_EMPTY,StringUtil.replaceProperties2Empty(pair.getRight()));

            //出库单的属性构建
            List<String> outOrderColums = new ArrayList<>();
            List<String> outBatchColums2 = new ArrayList<>();

            List<String> outOrderColumNames = new ArrayList<>();
            List<String> outBatchColumNames2 = new ArrayList<>();

            //订单属性对应字段sql,批次属性对应字段sql
            pair = RpSqlGenUtil.buildInOutStockPropertiesSql(outStockOrder,outStockBatch, outOrderColums, outOrderColumNames, outBatchColums2, outBatchColumNames2);
            //入库单的属性名称字段
            nameColum.put(customer+Const.OUTSTOCK_ORDER,outOrderColumNames);
            nameColum.put(customer+Const.OUTSTOCK_BATCH,outBatchColumNames2);
            //入库单的属性查询生效字段
            customerColum.put(customer+Const.OUTSTOCK_ORDER,outOrderColums);
            customerColum.put(customer+Const.OUTSTOCK_BATCH,outBatchColums2);

            map.put(customer+Const.OUTSTOCK_ORDER,pair.getLeft());
            map.put(customer+Const.OUTSTOCK_BATCH,pair.getRight());
            map.put(customer+Const.OUTSTOCK_BATCH_EMPTY,StringUtil.replaceProperties2Empty(pair.getRight()));
        }

        return map;
    }

    public List<PageData> findAllPropertys(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findAllProperty", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<PageData> findAllBoxRule(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findAllBoxRule", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public void findAllUser(Map<String, String> user2CustomerIdMapTmp,Set<String> outCustomerSet) {
        try {
            List<PageData> list = (List<PageData>) dao.findForList("BaseInfoMapper.findAllUser", null);
            for (PageData p : list) {
                String username = p.getString("USERNAME");
                user2CustomerIdMapTmp.put(username, p.getString("CUSTOMER_ID"));
                if (Const.DEL_YES.equals(p.getString("OUT_CUSTOMER"))) {
                    outCustomerSet.add(username);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  public Map<String,Set<String>> findCustomerBoxRule(Map<String,String> customerMap) {
      List<PageData> allBoxRule = findAllBoxRule();

      Map<String,Set<String>> map = new HashMap<>();
      Set<String> all = new HashSet<>();
      for (PageData p:allBoxRule) {
          String cust = p.getString("CUSTOMER_ID");
          String code = p.getString("MID_CODE");
          all.add(code);
          if (StringUtil.isEmpty(cust)) {
              for (Map.Entry<String,String> entry :customerMap.entrySet()) {
                  String customer = entry.getKey();

                  Set<String> set = map.get(customer);
                  if (set == null) {
                      set = new HashSet<>();
                  }
                  set.add(code);
                  map.put(customer,set);
              }
          } else {
              Set<String> set = map.get(cust);
              if (set == null) {
                  set = new HashSet<>();
              }
              set.add(code);
              map.put(cust,set);
          }
      }
         map.put(Const.EMPTY_CH,all);
        return map;
  }

    /**
     * 构建属性中所有属性纪录按客户——批次属性分组
     * @return (客户id_属性ID,构建后的属性列表)
     */
    public Map<String,List<PageData>> buildAllProperties() {
        List<PageData> allProperties = findAllProperties();
        Map<String, List<PageData>> stringListMap = groupByCustomerAndPro(allProperties);
        return buildProperties(stringListMap);
    }

    public Map<String,List<PageData>> buildStockStockageLocator(Map<String,String> stockMapILocator,Map<String,Pair<String,String>> storageIdMapStockIdCode
            ,Map<String,Pair<String,String>> locatorIdMapStockIdCode) {
        List<PageData> list = findStockStockageLocatorList();
        Map<String,List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p: list) {
            String stockId = p.getString("STOCK_ID");
            String stockCode = p.getString("STOCK_CODE");
            List<PageData> dataList = map.get(stockId);
            dataList = dataList != null?dataList:new ArrayList<PageData>();
            dataList.add(p);
            map.put(stockId,dataList);
            storageIdMapStockIdCode.put(p.getString("STORAGE_ID"),Pair.of(stockId, stockCode));
            locatorIdMapStockIdCode.put(p.getString("LOCATOR_ID"),Pair.of(stockId, stockCode));

            String locatorCode = p.getString("LOCATOR_CODE");
            if (Const.PICK_DEFAULT_LOCATOR_CODE.equalsIgnoreCase(locatorCode)) {
                stockMapILocator.put(stockId,p.getString("LOCATOR_ID"));
            }
        }
        return map;
    }
    //c.STOCK_ID, STOCK_CODE, a.LOCATOR_CODE, a.LOCATOR_ID, b.STORAGE_ID, b.STORAGE_CODE

    /**
     * 获取所有箱号记录
     * @return (箱Id,对应实体)
     */
    public Map<String,PageData> findAllBox(Set<String> allBigBoxNoSet,Set<String> allBoxNoSet) {
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findBoxList", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        Map<String,PageData> map = new HashMap<>(51200);
        for (PageData pd : list) {
            map.put(pd.getString("BOX_ID"),pd);
            String bigBoxNo = pd.getString("BIG_BOX_NO");
            String boxNo = pd.getString("BOX_NO");
            if (StringUtil.isNoBlank(bigBoxNo)) {
                allBigBoxNoSet.add(bigBoxNo);
            }
            if (StringUtil.isNoBlank(boxNo)) {
                allBoxNoSet.add(bigBoxNo);
            }

        }
        return map;
    }

    public Map<String,Map<Integer,String>>  findCustomerSeq2DMap(Map<String,Pair<String,String>> customerSplit2DMap) {
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findCustomer2Dist", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        //PN, DATE_CODE, LOT_CODE, BIN, COO, QTY, REMARK1, REMARK2, REMARK3, INV_NO
        Map<String,Map<Integer,String>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }

        for (PageData pd : list) {
           // customerSplit2DMap.put(pd.getString("CUSTOMER_ID"), Pair.of(pd.getString("SEPERATOR"), replaceColum(pd.getString("RECEIVOPT_2D"))));

            Map<Integer,String> map1 = new TreeMap<>();
            int pn = pd.getInteger("PN");
            int dateCode = pd.getInteger("DATE_CODE");
            int lotCode = pd.getInteger("LOT_CODE");
            int bin = pd.getInteger("BIN");
            int coo = pd.getInteger("COO");

            int qty = pd.getInteger("QTY");
            int remark1 = pd.getInteger("REMARK1");
            int remark2 = pd.getInteger("REMARK2");
            int remark3 = pd.getInteger("REMARK3");
            int invNo = pd.getInteger("INV_NO");

            add2Map(map1,pn,"PN");
            add2Map(map1,dateCode,"DATE_CODE");
            add2Map(map1,lotCode,"LOT_CODE");
            add2Map(map1,bin,"BIN");
            add2Map(map1,coo,"COO");

            add2Map(map1,qty,"QTY");
            add2Map(map1,remark1,"REMARK1");
            add2Map(map1,remark2,"REMARK2");
            add2Map(map1,remark3,"REMARK3");
            add2Map(map1,invNo,"INV_NO");

            map.put(pd.getString("CUSTOMER_ID"),map1);
            customerSplit2DMap.put(pd.getString("CUSTOMER_ID"), Pair.of(pd.getString("SEPERATOR"), replaceColum(build2D(pd.getString("SEPERATOR"),map1))));
        }
        return map;
    }

    private void add2Map(Map<Integer,String> map,int invNo,String cloum){
        if (invNo == 0) {
          return;
        }
        map.put(invNo,cloum);
    }

    private String build2D(String splitCh,Map<Integer,String> map) {
        StringBuffer sb = new StringBuffer(128);
        int i = 0,size = map.size();
        for (Map.Entry<Integer,String> entry : map.entrySet()) {
            i++;
            //拆分数组时候，数据的下表从0 开始，因此这里需要-1
            sb.append(entry.getKey()-1).append(Const.SPLIT_LINE).append(entry.getValue());
            if (i == size) {
                continue;
            }
            sb.append(splitCh);
        }
        return sb.toString();
    }

    /**
     * 需要将客户表设值的字段转为为通用收货界面对应的字段名称
     * @param customer2QR 客户二维码配置对应的二维码字符串
     * @return 替换后的字符串
     */
    private String replaceColum(String customer2QR) {
        if (StringUtil.isBlank(customer2QR)) {
            return null;
        }
        String rsStr = customer2QR;
        for (Map.Entry<String,String> entry :WmsEnum.Receive2D.colummap.entrySet()) {
            rsStr = rsStr.replace(entry.getKey(),entry.getValue());
        }
        return rsStr;
    }

    /**
     * 获取仓库、库区、库位列表
     * @return 仓库库区库位ID与Code
     */
    private List<PageData> findStockStockageLocatorList() {
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findStockStockageLocatorList", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<PageData> findAllProperties(){
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>)dao.findForList("BaseInfoMapper.findPropertyByCustAndType", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 将查询出来的属性纪录按客户——批次属性分组
     * @param list 系统中的所有订单属性，批次属性
     * @return (客户id_属性ID,对应的属性列表)
     */
    private Map<String,List<PageData>> groupByCustomerAndPro(List<PageData> list) {
        Map<String,List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData pd : list) {
            String key = StringUtil.genKey(pd.getString("CUSTOMER_ID"),pd.getString("ORDERBATCH_TYPE"));
            List<PageData> lst = map.get(key);
            if (lst == null) {
                lst = new ArrayList<>(64);
            }
            lst.add(pd);
            map.put(key,lst);
        }
        return map;
    }

    /**
     * 构建批次属性
     * @param srcMap (客户id_属性ID,对应的属性列表)
     * @return (客户id_属性ID,构建后的属性列表)
     */
    private Map<String,List<PageData>> buildProperties(Map<String,List<PageData>> srcMap) {
        Map<String,List<PageData>> map = new HashMap<>();
        for (Map.Entry<String,List<PageData>> entry : srcMap.entrySet()) {
            String key = entry.getKey();
            List<PageData> lst = buildProperties(entry.getValue());
            map.put(key,lst);
        }
        return map;
    }

    //与批次属性中方法一致，多写一次冗余的代码
    private List<PageData> buildProperties(List<PageData> list) {
        Map<Integer, String> sortColuMap = WmsEnum.OrderProperty.sortColuMap;
        List<PageData> lst = new ArrayList<>();
        for (PageData e : list) {
            int sort = Integer.parseInt(e.get("SRC_SORT").toString());
            String colum = sortColuMap.get(sort);
            PageData rs = creatPd(e);
            String propertySort = e.getString("PROPERTY_SORT");
            if (StringUtil.isEmpty(propertySort)) {
                rs.put("PROPERTY_SORT",sort);
            }
            rs.put("PROPERTY_COLUM",colum);
            rs.put("PROPERTY_VALUE",Const.EMPTY_CH);
            rs.put("SRC_SORT",sort);
            lst.add(rs);
        }
        lst.sort((e1,e2) -> Integer.parseInt(e1.get("PROPERTY_SORT").toString())-Integer.parseInt(e2.get("PROPERTY_SORT").toString()));
        return lst;
    }

    private PageData creatPd(PageData p) {
        PageData pd = new PageData();
        pd.put("PROPERTY_KEY",p.getString("PROPERTY_KEY"));
        pd.put("PROPERTY_SORT",p.getString("PROPERTY_SORT"));
        pd.put("SRC_SORT",p.getString("SRC_SORT"));
        return pd;
    }
    /**
     * 根获取所有的客户
     * @return 客户列表
     */
    private List<Select> findAllCustomer(){
        List<Select> list = new ArrayList<>();
        try {
            list = (List<Select>)dao.findForList("SelectMapper.findAllCustomer", null);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    private Map<String,Map<String,String>> buildAllProperties(List<PageData> list) {
        Map<Integer, String> sortColuMap = WmsEnum.OrderProperty.sortColuMap;
        List<PageData> lst = new ArrayList<>();
        Map<String,Map<String,String>> map = new HashMap<>();

        for (PageData e : list) {
            String customer = e.getString("CUSTOMER_ID");
            String propertyType = e.getString("ORDERBATCH_TYPE");

            //定义的字段名
            String columKey = e.getString("PROPERTY_KEY");

            //原始的字段名
            String columValue = sortColuMap.get(Integer.parseInt(e.get("SRC_SORT").toString()));
            if(StringUtil.isEmpty(columKey)) {
                continue;
            }

            columKey = StringUtil.trimSpace(columKey);
            String key = customer+Const.SPLIT_LINE+propertyType;
            Map<String, String> cloumMap = map.get(key);
            if(cloumMap == null) {
                cloumMap = new HashMap<>();
            }
            cloumMap.put(columKey,columValue);
            map.put(key,cloumMap);
        }

        return map;
    }





}
