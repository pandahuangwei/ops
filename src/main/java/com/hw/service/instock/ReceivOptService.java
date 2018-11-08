package com.hw.service.instock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.service.property.OrderPropertyService;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-11-17
 */
@Service("receivoptService")
public class ReceivOptService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "orderpropertyService")
    private OrderPropertyService orderpropertyService;

    @Resource(name = "stockmgrinService")
    private StockMgrInService stockmgrinService;

    @Resource(name = "assignoptService")
    private AssignOptService assignoptService;

    @Resource(name = "putLocatorOptService")
    private PutLocatorOptService putLocatorOptService;

    /*
    * 新增
    */
    public void save(PageData pd) throws Exception {
        dao.save("ReceivOptMapper.save", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd) throws Exception {
        dao.delete("ReceivOptMapper.delete", pd);
    }

    public boolean isExistBox(PageData pd) throws Exception {
        PageData rs = (PageData) dao.findForObject("ReceivOptMapper.countBoxCnt", pd);
        if (rs != null && rs.getInteger("CNT") >= 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断同一个箱号再同一张单下可以重复
     */
    public boolean isExistBoxExcSameBill(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("ReceivOptMapper.findBoxIdByNo", pd);
        if (list == null || list.isEmpty()) {
            return false;
        }
        List<String> lst = new ArrayList<>();
        for(PageData p : list) {
            lst.add(p.getString("BOX_ID"));
        }
        List<PageData> inIdList = (List<PageData>) dao.findForList("ReceivOptMapper.findInIdByInstockNo", lst);
        if (inIdList == null || inIdList.isEmpty() || inIdList.size() >1) {
            return false;
        }

        String stockmgrinId = inIdList.get(0).getString("STOCKMGRIN_ID");
        if (StringUtil.isNotEmpty(stockmgrinId) && stockmgrinId.equals(pd.getString("STOCKMGRIN_ID"))) {
            return false;
        }

        return true;
    }

    /*
    * 修改
    */
    public void edit(PageData pd) throws Exception {
        dao.update("ReceivOptMapper.edit", pd);
    }

    /*
    *获取本次收货明细
    */
    public List<PageData> findCurReceivDtl(Page page) throws Exception {
        return (List<PageData>) dao.findForList("ReceivOptMapper.findCurReceivlistPage", page);
    }

    /*
    *列表
    */
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("ReceivOptMapper.datalistPage", page);
    }

    /*
    *列表 findReceiveInstock
    */
    public List<PageData> findReceiInstock(Page page) throws Exception {
        return (List<PageData>) dao.findForList("ReceivOptMapper.datalistPage", page);
    }


    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("ReceivOptMapper.listAll", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("ReceivOptMapper.findById", pd);
    }

    public String findByCustomerId(PageData pd) throws Exception {
        PageData rs = (PageData) dao.findForObject("ReceivOptMapper.findCustomerId", pd);
        return rs.getString("CUSTOMER_ID");
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("ReceivOptMapper.deleteAll", ArrayDATA_IDS);
    }


    //==========================收货==================================================

    public List<PageData> findDtlName(Page page) throws Exception {
        return stockmgrinService.findDtlName(page);
    }

    /**
     * 根据入库单得到已收货明细
     */
    public List<PageData> findCancelDtl(Page page) throws Exception {
        //String[] instockIds
      //  return stockmgrinService.findCancelDtlName(page);
        return (List<PageData>) dao.findForList("ReceivOptMapper.findCancelDtllistPage", page);
    }


    public PageData findDtlByStockIdAndProdId(PageData pd) throws Exception {
        return findDtlBySIdAndProdId(pd);
    }

    private boolean isNoHadDtl(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("StockMgrInMapper.findHadDtl", pd);
        return (list == null || list.size() == 0) ? true : false;
    }

    /**
     * 每次算实际收货数量的时候，将全部数量统计起来
     */
    private PageData findDtlBySIdAndProdId(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("StockMgrInMapper.findDtlListByStockIdAndProdId", pd);
        if (list == null || list.isEmpty()) {
            return null;
        }

        if (list.size() == 1) {
            return list.get(0);
        }

        list.sort((e1, e2) -> e1.getInteger("RECEI_FLG") - e2.getInteger("RECEI_FLG"));

        PageData rs = new PageData(list.get(0), true);
        int ea = 0;
        for (int i = 1, size = list.size(); i < size; i++) {
            ea += list.get(i).getInteger("EA_ACTUAL");
        }
        rs.put("EA_ACTUAL", ea);
        return rs;
    }


    //根据条件 判断收货状态
    public String getInstockState(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("StockMgrInMapper.findInstockState", pd);

        int total = list.size();
        int zeroCnt = 0;
        int hadRecCnt = 0;
        //根据实际EA与期望EA比较
        for (PageData rs : list) {
            int EA_ACTUAL = rs == null ? 0 : rs.getInteger("EA_ACTUAL");
            int EA_EA = rs == null ? 0 : rs.getInteger("EA_EA");

            if (EA_ACTUAL == 0) {
                zeroCnt += 1;
            }

            if (EA_ACTUAL >= EA_EA) {
                hadRecCnt += 1;
            }

        }

        if (total == zeroCnt) {
            return WmsEnum.InstockState.NONE.getItemKey();
        }

        if (hadRecCnt == total) {
            return WmsEnum.InstockState.ALL.getItemKey();
        }
        return WmsEnum.InstockState.PART.getItemKey();
    }


    //

    /**
     * 收货 操作
     *
     * @param pd
     */
    public void saveReceive(PageData pd) throws Exception {
        if (pd.getInteger("RECEIV_QTY") == 0) {
            return;
        }
        //得到属性列表
        String customerId = pd.getString("CUSTOMER_ID");
        String productId = pd.getString("PRODUCT_ID");
        if (StringUtil.isBlank(productId)) {
            productId =BaseInfoCache.getInstance().getProdIdByCustomerIdAndScanCode(customerId,pd.getString("SUPPLIER_PROD"));
            pd.put("PRODUCT_ID",productId);
        }
        // List<PageData> propertyList = orderpropertyService.getPropertyINBatch(pd.getString("CUSTOMER_ID"));
        List<PageData> propertyList = BaseInfoCache.getInstance().getPropertyINBatch(customerId);
        //2 根据属性列表值，往其中设值,并组装批次属性明细字段
        setProperties(propertyList, pd, false);
        PageData propertyDtl = buildBatchProperty(pd);
        //根据入库单ID与产品获取到入库单该产品的明细
      //  pd.put("PRODUCT_ID", BaseInfoCache.getInstance().getProductId(pd.getString("PROD_CODE")));
        PageData dtlPd = findDtlByStockIdAndProdId(pd);
        //设值 实际收货数 = 已经收货数+本次收货数量
        dtlPd.put("EA_ACTUAL", dtlPd.getInteger("EA_ACTUAL") + pd.getInteger("RECEIV_QTY"));
        dtlPd.put("RECEI_FLG", Const.RECEIVE_NO);

        String id = UuidUtil.get32UUID();

        //更新原始明细纪录
        dao.update("StockMgrInMapper.editDtlByReceive", dtlPd);

        //保存本次收货纪录、本次收货纪录对应的属性明细
        //id 在入库单明细、箱号表、收货明细中共用

        PageData curDtlPd = setDtl(pd, dtlPd, false);
        curDtlPd.put("STOCKDTLMGRIN_ID", id);
        propertyDtl.put("STOCKDTLMGRIN_ID", id);

        dao.save("StockMgrInMapper.saveProdDtl", curDtlPd);
        dao.save("StockMgrInMapper.saveOrderDtl", propertyDtl);

        //往收货纪录中插入本次收货明细
        pd.put("RECEIVOPT_ID", id);    //主键
        dao.save("ReceivOptMapper.save", pd);
        //往箱号表插入本次收货明细
        pd.put("BOX_ID", id);    //主键
        dao.save("BoxOptMapper.save", StockUtils.buildReceiveBox(pd, false));

        //更新收货状态
        setState(pd);
    }


    /**
     * 导入收货操作
     *
     * @param impNoProdIdMap   本次导入的数据
     * @param existNoProdIdMap 数据库已经存在的数据
     * @throws Exception 处理异常
     */
    public void saveReceiveImp(Map<String, Map<String, List<PageData>>> impNoProdIdMap, Map<String, Map<String, List<PageData>>> existNoProdIdMap) throws Exception {
        for (Map.Entry<String, Map<String, List<PageData>>> entry : impNoProdIdMap.entrySet()) {
            String inStockNo = entry.getKey();
            doReceiveImpOneStock(inStockNo, entry.getValue(), existNoProdIdMap.get(inStockNo));
        }
    }

    /**
     * 对一张入库单进行导入收货操作
     *
     * @param inStockNo        入库单
     * @param impNoProdIdMap   本次导入的数据
     * @param existNoProdIdMap 数据库已经存在的数据
     * @throws Exception 处理异常
     */
    private void doReceiveImpOneStock(String inStockNo, Map<String, List<PageData>> impNoProdIdMap, Map<String, List<PageData>> existNoProdIdMap) throws Exception {
        String inStockId = null;
        for (Map.Entry<String, List<PageData>> entry : impNoProdIdMap.entrySet()) {
            String prodId = entry.getKey();
            List<PageData> hadReceiveDtl = new ArrayList<>();
            //该产品的原始纪录
            PageData srcPd = filterSrcAndReceivDtl(hadReceiveDtl, existNoProdIdMap.get(prodId));
            doReceiveImpOneProd(prodId, srcPd, entry.getValue(), hadReceiveDtl);
            inStockId = srcPd.getString("STOCKMGRIN_ID");
        }

        //更新入库单的收货状态、分配状态、码放状态
        if (StringUtil.isNotEmpty(inStockId)) {
            setState(inStockId);
            assignoptService.updateAssignState(inStockId);
            putLocatorOptService.updateHeadPutState(inStockId);
        }
    }

    /**
     * 对一个产品的导入收货
     *
     * @param prodId        对一个产品的导入收货
     * @param srcPd         该产品在数据库入库单中对应的原始纪录
     * @param impDtl        本次收货明细
     * @param hadReceiveDtl 已经收货的明细
     * @throws Exception 异常处理
     */
    private void doReceiveImpOneProd(String prodId, PageData srcPd, List<PageData> impDtl, List<PageData> hadReceiveDtl) throws Exception {
        int hadEaActual = calcHadReceiveEaActual(hadReceiveDtl);
        int impEaActual = calcHadReceiveEaActual(impDtl);
        String customerId = srcPd.getString("CUSTOMER_ID");

        //得到属性列表
        //List<PageData> propertyList = orderpropertyService.getPropertyINBatch(customerId);
        List<PageData> propertyList = BaseInfoCache.getInstance().getPropertyINBatch(customerId);
        String startId = DateUtil.getYYMMDDHHMMSSMS();
        for (int i = 0, size = impDtl.size(); i < size; i++) {
            PageData pd = impDtl.get(i);
            //收货明细、箱号、入库单明细，共用ID
            String id = StringUtil.genId(startId, i);
            pd.put("P_ID", id);
            //pd.put("STOCKMGRIN_ID", id);
            pd.put("STOCKDTLMGRIN_ID", id);

            setProperties(propertyList, pd, true);
            PageData propertyDtl = buildBatchProperty(pd);
            //根据入库单ID与产品获取到入库单该产品的明细
            //就是传入的 srcPd
            //设值 实际收货数 = 已经收货数+本次收货数量
            int receiQty = hadEaActual + impEaActual;
            srcPd.put("EA_ACTUAL", receiQty);
            srcPd.put("PUT_EA", receiQty);
            srcPd.put("ASSIGNED_EA", receiQty);
            srcPd.put("RECEI_FLG", Const.RECEIVE_NO);
            //更新原始明细纪录
            dao.update("StockMgrInMapper.editDtlByReceive", srcPd);

            //保存本次收货纪录、本次收货纪录对应的属性明细
            //id 在入库单明细、箱号表、收货明细中共用

            PageData curDtlPd = setDtl(pd, srcPd, true);
            curDtlPd.put("STOCKDTLMGRIN_ID", id);
            propertyDtl.put("STOCKDTLMGRIN_ID", id);
            propertyDtl.put("STOCKMGRIN_ID", curDtlPd.getString("STOCKMGRIN_ID"));

            dao.save("StockMgrInMapper.saveProdDtl", curDtlPd);
            dao.save("StockMgrInMapper.saveOrderDtl", propertyDtl);

            //往收货纪录中插入本次收货明细
            pd.put("RECEIVOPT_ID", id);    //主键
            dao.save("ReceivOptMapper.save", pd);
            //往箱号表插入本次收货明细
            pd.put("BOX_ID", id);    //主键
            pd.put("RECEIV_QTY", curDtlPd.getString("RECEIV_QTY"));    //主键
            dao.save("BoxOptMapper.save", StockUtils.buildReceiveBox(pd, true));
        }

    }

    /**
     * 计算已经收货的实际数量
     *
     * @param hadReceiveDtl 已经收货的明细
     * @return 实际收货数量
     */
    private int calcHadReceiveEaActual(List<PageData> hadReceiveDtl) {
        if (hadReceiveDtl == null || hadReceiveDtl.isEmpty()) {
            return 0;
        }
        int eaActual = 0;
        for (PageData p : hadReceiveDtl) {
            int EA_ACTUAL = p == null ? 0 : p.getInteger("EA_ACTUAL");
            eaActual += EA_ACTUAL;
        }
        return eaActual;
    }

    /**
     * 入库单该产品的已经入库的信息区别为原始纪录(一个实体)，已经收货的纪录(list可为empty)
     *
     * @param hadReciveDtl 已经收货的纪录(list可为empty)
     * @param existDtl     数据库该产品的所有纪录
     */
    private PageData filterSrcAndReceivDtl(List<PageData> hadReciveDtl, List<PageData> existDtl) {
        //该产品的原始纪录
        PageData srcPd = null;
        for (PageData pd : existDtl) {
            if (pd.getString("RECEI_FLG").equals(Const.RECEIVE_NO)) {
                srcPd = new PageData(pd, true);
                continue;
            }
            hadReciveDtl.add(pd);
        }
        return srcPd;
    }

    /**
     * 取消收货 操作
     *
     * @param pd     前台返回值
     * @param dtlIds 需要取消收货的id列表
     * @throws Exception 处理异常
     */
    public void saveCancel(PageData pd, String[] dtlIds) throws Exception {
        //1、根据dtlIds 得到需要取消的纪录
        List<PageData> dtlReceiveList = stockmgrinService.findDtlReceive(dtlIds);

        //2、对相同的入库单中产品一致的进行合并
        Map<String, Map<String, Integer>> receiveMap = calcCancelReceiveMap(dtlReceiveList);
        //3、根据合并的结果进行更新原入库单明细的产品EA
        modifyEa(receiveMap);

        //4、删除收货纪录
        for (String id : dtlIds) {
            PageData delPd = new PageData();
            delPd.put("MODIFY_EMP", SessionUtil.getCurUserName());
            delPd.put("MODIFY_TM", Tools.date2Str(new Date()));
            delPd.put("MEMO", "取消收货");

            //4、删除入库单明细Id
            delPd.put("STOCKDTLMGRIN_ID", id);
            dao.update("StockMgrInMapper.delDtlReceive", delPd);
            delPd.put("STOCKDTLMGRIN_ID", id);

            //5、删除box纪录
            delPd.put("BOX_ID", id);
            dao.update("BoxOptMapper.deleteAll", delPd);

            //6、删除收货明细纪录
            delPd.put("RECEIVOPT_ID", id);
            dao.update("ReceivOptMapper.deleteReceivDtl", delPd);

            //更新收货状态
            String instockState = getInstockState(pd);
            pd.put("INSTOCK_STATE", instockState);
            pd.put("REAL_INSTOCK_DATE",Tools.date2Str(new Date()));
            dao.save("StockMgrInMapper.editStockState", pd);
        }

        //7 更新入库单头信息
        for (Map.Entry<String, Map<String, Integer>> e : receiveMap.entrySet()) {
            //更新收货状态
            PageData pd1 = new PageData();
            pd1.put("STOCKMGRIN_ID", e.getKey());

            String instockState = getInstockState(pd1);
            pd1.put("INSTOCK_STATE", instockState);
            pd.put("REAL_INSTOCK_DATE",Tools.date2Str(new Date()));
            dao.save("StockMgrInMapper.editStockState", pd1);
        }
    }


    public List<PageData> auditData(List<PageData> listPd, Map<String, Map<String, List<PageData>>> impNoProdIdMap, Map<String, Map<String, List<PageData>>> existNoProdIdMap) {
        List<PageData> rsList = new ArrayList<>();

        List<String> inStockNos = filterInstockNo(listPd);
        Map<String, Map<String, List<PageData>>> noProdMap = new HashMap<>();
        //已经存在入库单中的内箱，外箱,已按入库单号分组
        Map<String,Set<String>> noInBoxMap = new HashMap<>();
        Map<String,Set<String>> noOutBoxMap = new HashMap<>();
        Map<String, List<PageData>> inStockNoMap = findListDtlByInStockNo(inStockNos, noProdMap, noInBoxMap,noOutBoxMap);

        //本次导入的数据按入库单、产品分组
        Map<String, Map<String, List<PageData>>> impNoProdMap = new HashMap<>();
        Map<String, List<PageData>> noListMap = new HashMap<>();
        List<PageData> list = setDataColum(listPd);
        filterByInStockNo(list, impNoProdMap, noListMap);

        Set<String> noExistNo = new HashSet<>();
        Set<String> noProd = new HashSet<>();
        Set<String> noProdSet = new HashSet<>();
        Set<String> storageSet = new HashSet<>();
        Set<String> locatorSet = new HashSet<>();
        //本次导入的外箱号，当前入库单的外箱号
        Set<String> thisTimeBigBoxSet = new HashSet<>();
        Set<String> thisNoBigBoxSet = new HashSet<>();

        Set<String> thisTimeBoxSet = new HashSet<>();
        Set<String> thisNoBoxSet = new HashSet<>();

        //按入库单分组之后循环，以此判断不同入库单之间的箱号不能重复
        for (Map.Entry<String, List<PageData>> entry : noListMap.entrySet()) {
            String inStock = entry.getKey();
             //当前入库单已经存在的内箱号，与外箱号
            Set<String> inSet = noInBoxMap.get(inStock);
            Set<String> outSet =  noOutBoxMap.get(inStock);
            if (inSet != null) {
                thisNoBoxSet.addAll(inSet);
            }
            if (outSet != null) {
                thisNoBigBoxSet.addAll(outSet);
            }

            PageData pd;
            for (PageData p : entry.getValue()) {
                String inStockNo = p.getString("INSTOCK_NO");
                String scanProd = p.getString("SUPPLIER_PROD");
                String stockCode = p.getString("STORAGE_CODE");
                String locatorCode = p.getString("LOCATOR_CODE");
                String bigBoxNo = p.getString("BIG_BOX_NO");
                String boxNo = p.getString("BOX_NO");
                String key = StringUtil.genKey(inStockNo, scanProd);
                thisNoBoxSet.add(boxNo);
                thisNoBigBoxSet.add(bigBoxNo);

                if (noExistNo.contains(inStockNo) || noProd.contains(scanProd) || noProdSet.contains(key)
                        || storageSet.contains(stockCode) || locatorSet.contains(locatorCode)) {
                    continue;
                }

                pd = new PageData();
                List<PageData> pageDatas = inStockNoMap.get(inStockNo);
                if (pageDatas == null || pageDatas.isEmpty()) {
                    noExistNo.add(inStockNo);
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "入库单不存在");
                    rsList.add(pd);
                    continue;
                }

                //判断扫描料号是否存在
                String prodId = p.getString("PRODUCT_ID");
                if (StringUtil.isEmpty(prodId)) {
                    noProd.add(scanProd);
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "扫描料号没有对应的实际料号");
                    rsList.add(pd);
                    continue;
                }

                String stockId = p.getString("STOCK_ID");
                if (StringUtil.isNotEmpty(stockCode) && StringUtil.isEmpty(stockId)) {
                    storageSet.add(scanProd);
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "仓库不存在:" + stockCode);
                    rsList.add(pd);
                    continue;
                }

                String locatorId = p.getString("LOCATOR_ID");
                if (StringUtil.isEmpty(stockId) && StringUtil.isEmpty(locatorId)) {
                    storageSet.add(scanProd);
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "仓库找到默认的(I)库位:" + stockCode);
                    rsList.add(pd);
                    continue;
                }

                if (StringUtil.isNotEmpty(locatorCode) && StringUtil.isEmpty(locatorId)) {
                    locatorSet.add(scanProd);
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "库位不存在:" + locatorCode);
                    rsList.add(pd);
                    continue;
                }
                //如果当前单不包含，但是其他单包含了，则说明该箱号重复了不能导入
                if (thisTimeBoxSet.contains(boxNo) ||
                        BaseInfoCache.getInstance().getAllBoxSet().contains(boxNo)) {
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "内箱号已经存在其他入库单中:" + boxNo);
                    rsList.add(pd);
                    continue;
                }

                if (thisTimeBigBoxSet.contains(bigBoxNo) ||
                        BaseInfoCache.getInstance().getAllBigBoxSet().contains(bigBoxNo)) {
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "外箱号已经存在其他入库单中:" + bigBoxNo);
                    rsList.add(pd);
                    continue;
                }

                if (!isExistNoAndProdId(inStockNo, prodId, noProdMap)) {
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "料号不存在该入库单");
                    rsList.add(pd);
                    continue;
                }

                int impEa = calcHadImpEa(inStockNo, prodId, impNoProdMap);
                PageData pageData = calcHadReceiv(inStockNo, prodId, noProdMap);
                int hadEa = pageData.getInteger("EA_EA");
                int receivEa = pageData.getInteger("EA_ACTUAL");

                if (impEa + receivEa > hadEa) {
                    noProdSet.add(key);
                    pd.put("INSTOCK_NO", inStockNo);
                    pd.put("SUPPLIER_PROD", scanProd);

                    pd.put("MEMO", "入库单期望EA:" + hadEa + ",已收EA:" + receivEa + ",本次导入EA:" + impEa);
                    rsList.add(pd);
                    continue;
                }
            }
            thisTimeBigBoxSet.addAll(thisNoBigBoxSet);
            thisTimeBoxSet.addAll(thisNoBoxSet);

        }

        if (rsList.isEmpty()) {
            existNoProdIdMap.putAll(noProdMap);
            impNoProdIdMap.putAll(impNoProdMap);
        }
        return rsList;
    }

    /**
     * 计算入库单该产品的期望Ea和已经收货的Ea
     *
     * @param inStockNo    入库单
     * @param prodId       产品ID
     * @param impNoProdMap 本次导入的收货纪录
     * @return EA_ACTUAL
     */
    private int calcHadImpEa(String inStockNo, String prodId, Map<String, Map<String, List<PageData>>> impNoProdMap) {
        Map<String, List<PageData>> noListMap = impNoProdMap.get(inStockNo);
        List<PageData> list = noListMap.get(prodId);
        int total = 0;
        for (PageData p : list) {
            total += p.getInteger("EA_ACTUAL");
        }
        return total;
    }

    /**
     * 计算入库单该产品的期望Ea和已经收货的Ea
     *
     * @param inStockNo 入库单
     * @param prodId    产品ID
     * @param noProdMap 已经存在的入库单明细
     * @return EA_EA, EA_ACTUAL
     */
    private PageData calcHadReceiv(String inStockNo, String prodId, Map<String, Map<String, List<PageData>>> noProdMap) {
        Map<String, List<PageData>> noListMap = noProdMap.get(inStockNo);
        List<PageData> list = noListMap.get(prodId);
        PageData pd = new PageData();
        if (list.size() == 1) {
            pd.put("EA_EA", list.get(0).getInteger("EA_EA"));
            pd.put("EA_ACTUAL", 0);
            return pd;
        }
        //降序排列 第一笔是原始纪录
        list.sort((e1, e2) -> e1.getInteger("RECEI_FLG") - e2.getInteger("RECEI_FLG"));
        pd.put("EA_EA", list.get(0).getInteger("EA_EA"));
        int total = 0;
        for (int i = 1, size = list.size(); i < size; i++) {
            total += list.get(i).getInteger("EA_ACTUAL");
        }
        pd.put("EA_ACTUAL", total);
        return pd;
    }

    /**
     * 判断料号是否存在该入库单中
     *
     * @param inStockNo 入库单号
     * @param prodId    产品ID
     * @param noProdMap 已存在的入库单明细
     * @return boolean
     */
    private boolean isExistNoAndProdId(String inStockNo, String prodId, Map<String, Map<String, List<PageData>>> noProdMap) {
        Map<String, List<PageData>> noListMap = noProdMap.get(inStockNo);
        if (noListMap == null || noListMap.isEmpty()) {
            return false;
        }
        List<PageData> list = noListMap.get(prodId);
        if (list == null || list.isEmpty()) {
            return false;
        }
        return true;
    }

    public List<PageData> setDataColum(List<PageData> listPd) {
        List<PageData> rsList = new ArrayList<>(listPd.size());
        PageData pd;
        for (PageData p : listPd) {
            pd = new PageData();
            String customerCode = p.getString("var0");
            //设置客户
            String customerId = BaseInfoCache.getInstance().getCustomerId(customerCode);
            pd.put("CUSTOMER_CODE", customerCode);
            pd.put("CUSTOMER_ID", customerId);

            String INSTOCK_NO = p.getString("var1");
            pd.put("INSTOCK_NO", INSTOCK_NO);

            pd.put("BIG_BOX_NO", p.getString("var2"));
            pd.put("BOX_NO", p.getString("var3"));
            pd.put("QR_CODE", p.getString("var4"));

            String stockCode = p.getString("var5");
            pd.put("STOCK_CODE", stockCode);
            pd.put("STOCK_ID", BaseInfoCache.getInstance().getStockId(stockCode));

            String locatorCode = p.getString("var6");
            pd.put("LOCATOR_CODE", locatorCode);
            pd.put("LOCATOR_ID", BaseInfoCache.getInstance().getLocatorId(locatorCode));

            //如果库位为空，则通过仓库获取对应的I库位
            if (StringUtil.isEmpty(pd.getString("LOCATOR_ID"))) {
                pd.put("LOCATOR_ID", BaseInfoCache.getInstance().getILocatorIdByStockId(pd.getString("STOCK_ID")));
            }

            //如果库位id不为空，需要设值仓库ID
            if (StringUtil.isEmpty(pd.getString("STORAGE_ID")) && StringUtil.isNotEmpty(pd.getString("LOCATOR_ID"))) {
                pd.put("STORAGE_ID", BaseInfoCache.getInstance().getStorageIdByLocatorId(pd.getString("LOCATOR_ID")));
            }


            pd.put("REMARK1", p.getString("var7"));
            pd.put("REMARK2", p.getString("var8"));
            pd.put("REMARK3", p.getString("var9"));

            String scanProd = p.getString("var10");
            pd.put("SUPPLIER_PROD", scanProd);
            pd.put("PRODUCT_ID", BaseInfoCache.getInstance().getProdIdByCustomerIdAndScanCode(customerId,scanProd));

            pd.put("LOT_NO", p.getString("var11"));
            pd.put("DATE_CODE", p.getString("var12"));
            pd.put("SEPARATE_QTY", p.getString("var13"));
            pd.put("BIN_CODE", p.getString("var14"));
            pd.put("COO", p.getString("var15"));
            pd.put("EA_NUM", p.getInteger("var16"));
            pd.put("EA_NUM_2", p.getInteger("var17"));
            pd.put("TOTAL_NUM", p.getInteger("var18"));
            //		SeparateQty1	SeparateQty2	GoodsBase	IDS	LotType	InvNo	TEXT03	TEXT11	TEXT12	TEXT14	TEXT17	TEXT20
            pd.put("SeparateQty1", p.getInteger("var19"));
            pd.put("SeparateQty2", p.getInteger("var20"));
            pd.put("GoodsBase", p.getInteger("var21"));

            pd.put("IDS", p.getInteger("var22"));
            pd.put("LotType", p.getInteger("var23"));
            pd.put("InvNo", p.getInteger("var24"));
            pd.put("TEXT03", p.getInteger("var25"));

            pd.put("TEXT11", p.getInteger("var26"));
            pd.put("TEXT12", p.getInteger("var27"));
            pd.put("TEXT14", p.getInteger("var28"));
            pd.put("TEXT17", p.getInteger("var29"));
            pd.put("TEXT20", p.getInteger("var30"));
            pd.put("TEXT20", p.getInteger("var30"));
            pd.put("RECEI_FLG", Const.RECEIVE_YES);

            int eaNum = pd.getInteger("EA_NUM");
            String dateCode = pd.getString("DATE_CODE");
            String[] dateCodes = StringUtils.split(dateCode, Const.SPLIT_SLANT);
            if (eaNum != 0) {
                PageData pDate = new PageData(pd, true);
                pDate.put("DATE_CODE", dateCodes[0]);
                pDate.put("SEPARATE_QTY", pd.getString("SeparateQty1"));
                pDate.put("EA_ACTUAL", eaNum);
                rsList.add(pDate);
            }


            int eaNum2 = pd.getInteger("EA_NUM_2");
            if (eaNum2 != 0) {
                PageData pDate = new PageData(pd, true);
                if (dateCodes.length >= 2) {
                    pDate.put("DATE_CODE", dateCodes[1]);
                } else {
                    pDate.put("DATE_CODE", dateCodes[0]);
                }
                pDate.put("SEPARATE_QTY", pd.getString("SeparateQty2"));
                pDate.put("EA_ACTUAL", eaNum2);
                rsList.add(pDate);
            }
            /*if (StringUtil.isNotEmpty(dateCode)) {
				String[] dateCodes = StringUtils.split(dateCode, Const.SPLIT_SLANT);
				if (StringUtil.isNotEmpty(dateCodes[0])) {
					PageData pDate = new PageData(pd);
					pDate.put("DATE_CODE",dateCodes[0]);
					pDate.put("SEPARATE_QTY",pd.getString("SeparateQty1"));
					pDate.put("EA_ACTUAL",pd.getString("EA_NUM"));
					rsList.add(pDate);
				}

				if (dateCodes.length>=2 && StringUtil.isNotEmpty(dateCodes[1])) {
					PageData pDate = new PageData(pd);
					pDate.put("DATE_CODE",dateCodes[1]);
					pDate.put("SEPARATE_QTY",pd.getString("SeparateQty2"));
					pDate.put("EA_ACTUAL",pd.getString("EA_NUM_2"));
					rsList.add(pDate);
				}
			} else {
				pd.put("SEPARATE_QTY",pd.getString("SeparateQty2"));
				pd.put("EA_ACTUAL",pd.getString("EA_NUM_2"));
				rsList.add(pd);
			}*/
        }

        return rsList;
    }

    /**
     * 更新入库单的收货状态
     *
     * @param stockId 入库单ID
     * @throws Exception 异常处理
     */
    private void setState(String stockId) throws Exception {
        PageData pd = new PageData();
        pd.put("STOCKMGRIN_ID", stockId);
        setState(pd);
    }

    /**
     * 更新入库单的收货状态
     *
     * @param pd 带STOCKMGRIN_ID的实体
     * @throws Exception 异常处理
     */
    private void setState(PageData pd) throws Exception {
        //更新收货状态
        String instockState = getInstockState(pd);
        pd.put("INSTOCK_STATE", instockState);
        pd.put("REAL_INSTOCK_DATE",Tools.date2Str(new Date()));
        dao.save("StockMgrInMapper.editStockState", pd);
    }

    private Map<String, List<PageData>> findListDtlByInStockNo(List<String> instockNos, Map<String, Map<String, List<PageData>>> noProdMap,
                         Map<String,Set<String>> noInBoxMap,Map<String,Set<String>> noOutBoxMap) {
        List<PageData> list = null;
        try {
            list = (List<PageData>) dao.findForList("ReceivOptMapper.findDtlByInstockNo", instockNos);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String, List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            String inStockNo = p.getString("INSTOCK_NO");
            String productId = p.getString("PRODUCT_ID");

            List<PageData> lst = map.get(inStockNo);
            if (lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(inStockNo, lst);

            Map<String, List<PageData>> listMap = noProdMap.get(inStockNo);
            if (listMap == null) {
                listMap = new HashMap<>();
            }
            List<PageData> dataList = listMap.get(productId);
            if (dataList == null) {
                dataList = new ArrayList<>();
            }
            dataList.add(p);
            listMap.put(productId, dataList);
            noProdMap.put(inStockNo, listMap);

            Set<String> set = noInBoxMap.get(inStockNo);
            set = set!= null ?set:new HashSet<>();
            set.add( p.getString("BOX_NO"));
            noInBoxMap.put(inStockNo,set);

            if (Const.DEL_YES.equals(p.getString("RECEI_FLG"))) {
                String bigBoxNo = BaseInfoCache.getInstance().getBigBoxNo(p.getString("STOCKDTLMGRIN_ID"));
                Set<String> set2 = noOutBoxMap.get(inStockNo);
                set2 = set2 != null ? set2 : new HashSet<>();
                set2.add(bigBoxNo);
                noOutBoxMap.put(inStockNo, set);
            }

        }
        return map;
    }

    private void filterByInStockNo(List<PageData> list, Map<String, Map<String, List<PageData>>> noProdMap,Map<String, List<PageData>> noListMap) {
        for (PageData p : list) {
            String inStockNo = p.getString("INSTOCK_NO");
            String productId = p.getString("PRODUCT_ID");

            Map<String, List<PageData>> listMap = noProdMap.get(inStockNo);
            if (listMap == null) {
                listMap = new HashMap<>();
            }
            List<PageData> dataList = listMap.get(productId);
            if (dataList == null) {
                dataList = new ArrayList<>();
            }
            dataList.add(p);
            listMap.put(productId, dataList);
            noProdMap.put(inStockNo, listMap);

            List<PageData> lst = noListMap.get(inStockNo);
            lst = lst != null ? lst : new ArrayList<>();
            lst.add(p);
            noListMap.put(inStockNo, lst);
        }
    }


    private List<String> filterInstockNo(List<PageData> listPd) {
        Set<String> set = new HashSet<>(listPd.size());
        for (PageData p : listPd) {
            String INSTOCK_NO = p.getString("var1");
            if (StringUtil.isEmpty(INSTOCK_NO)) {
                continue;
            }
            set.add(INSTOCK_NO);
        }
        return new ArrayList<>(set);
    }

    /**
     * 根据退货纪录更新入库单明细数据
     *
     * @param map 取消退货纪录map(入库单ID,map(产品，取消退货数))
     * @throws Exception 处理异常
     */
    private void modifyEa(Map<String, Map<String, Integer>> map) throws Exception {
        for (Map.Entry<String, Map<String, Integer>> et1 : map.entrySet()) {
            String id = et1.getKey();
            for (Map.Entry<String, Integer> et2 : et1.getValue().entrySet()) {
                PageData pd = new PageData();
                pd.put("STOCKMGRIN_ID", id);
                pd.put("PRODUCT_ID", et2.getKey());
                pd.put("EA_ACTUAL", et2.getValue());
                dao.update("StockMgrInMapper.editDtlReceive", pd);
            }
        }
    }

    /**
     * 将纪录构建成map(入库单ID,map(产品，取消退货数))
     *
     * @param list 需要退货的纪录
     * @return map(入库单ID, map(产品，取消退货数))
     */
    private Map<String, Map<String, Integer>> calcCancelReceiveMap(List<PageData> list) {
        Map<String, Map<String, Integer>> map = new HashMap<>();
        //STOCKDTLMGRIN_ID, STOCKMGRIN_ID, ROW_NO, CUSTOMER_ID, PRODUCT_ID, BOX_NO, EA_ACTUAL
        for (PageData p : list) {
            String STOCKMGRIN_ID = p.getString("STOCKMGRIN_ID");
            String PRODUCT_ID = p.getString("PRODUCT_ID");
            Integer EA_ACTUAL = p.getInteger("EA_ACTUAL");
            Map<String, Integer> midMap = map.get(STOCKMGRIN_ID);
            if (midMap == null || midMap.isEmpty()) {
                midMap = new HashMap<>();
            }

            Integer cnt = midMap.get(PRODUCT_ID);
            cnt = cnt == null ? EA_ACTUAL : cnt + EA_ACTUAL;

            midMap.put(PRODUCT_ID, cnt);
            map.put(STOCKMGRIN_ID, midMap);
        }
        return map;
    }

    private PageData setDtl(PageData pd, PageData dtlPd, boolean isImp) {
        PageData prodDtl = new PageData(dtlPd, true);
        //设值箱号，实际收货数、收货标记
        prodDtl.put("BOX_NO", pd.getString("BOX_NO"));
        if (isImp) {
            prodDtl.put("EA_ACTUAL", pd.getInteger("EA_ACTUAL"));
            prodDtl.put("EA_EA", pd.getInteger("EA_ACTUAL"));
            prodDtl.put("PUT_EA", pd.getInteger("EA_ACTUAL"));
            prodDtl.put("ASSIGNED_EA", pd.getInteger("EA_ACTUAL"));
            prodDtl.put("RECEIV_QTY", pd.getInteger("EA_ACTUAL"));
            prodDtl.put("STORAGE_ID", pd.getString("STORAGE_ID"));
            prodDtl.put("LOCATOR_ID", pd.getString("LOCATOR_ID"));
            prodDtl.put("PUT_STORAGE_ID", pd.getString("STORAGE_ID"));
            prodDtl.put("PUT_LOCATOR_ID", pd.getString("LOCATOR_ID"));
            prodDtl.put("DISTR_STATE", WmsEnum.AssignedState.ALL.getItemKey());
            prodDtl.put("PUT_STATUS", WmsEnum.PutState.ALL.getItemKey());
        } else {
            prodDtl.put("EA_ACTUAL", pd.getInteger("RECEIV_QTY"));
            prodDtl.put("EA_EA", pd.getInteger("RECEIV_QTY"));
            prodDtl.put("PUT_EA", Const.ZERO);
            prodDtl.put("ASSIGNED_EA", Const.ZERO);
            prodDtl.put("DISTR_STATE", WmsEnum.AssignedState.NONE.getItemKey());
            prodDtl.put("PUT_STATUS", WmsEnum.PutState.NONE.getItemKey());
        }

        prodDtl.put("RECEI_FLG", Const.RECEIVE_YES);
        //设值 入库单明细
        //收货状态
        prodDtl.put("RECEIPT_STATE", WmsEnum.InstockState.ALL.getItemKey());    //创建人
        prodDtl.put("CREATE_EMP", SessionUtil.getCurUserName());    //创建人
        prodDtl.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间
        prodDtl.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
        prodDtl.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        prodDtl.put("DEL_FLG", "0");    //删除标志
        prodDtl.put("MEMO", "收货");    //删除标志
        //收货状态
        //RECEIPT_STATE


        return prodDtl;
    }

    private void setProperties(List<PageData> propertyList, PageData pd, boolean isImp) {
        for (PageData e : propertyList) {
            String cloumValue = e.getString("PROPERTY_KEY");
            if (StringUtil.isEmpty(cloumValue)) {
                continue;
            }


            String bpCloum = StringUtil.trimSpace(cloumValue);
            //如果字符串是属性配置中的值的话，根据其对应的排序，取到属性对应的字段名
            Integer srcSort = e.getInteger("SRC_SORT");
            String columKey = WmsEnum.OrderProperty.sortColuMap.get(srcSort);

            if (isImp) {
                //导入字段才需要设值
                // SeparateQty1 TODO
                if (bpCloum.equalsIgnoreCase(Const.IMP_SeparateQty)) {
                    pd.put(columKey, pd.getString(Const.IMP_SeparateQty));
                    continue;
                }
				/*if (bpCloum.equalsIgnoreCase(Const.IMP_SeparateQty2)) {
					pd.put(columKey,pd.getString(Const.IMP_SeparateQty2));
					continue;
				}*/

                if (bpCloum.equalsIgnoreCase(Const.IMP_GoodsBase)) {
                    pd.put(columKey, pd.getString(Const.IMP_GoodsBase));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_IDS)) {
                    pd.put(columKey, pd.getString(Const.IMP_IDS));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_LotType)) {
                    pd.put(columKey, pd.getString(Const.IMP_LotType));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_InvNo)) {
                    pd.put(columKey, pd.getString(Const.IMP_InvNo));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_TEXT03)) {
                    pd.put(columKey, pd.getString(Const.IMP_TEXT03));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_TEXT11)) {
                    pd.put(columKey, pd.getString(Const.IMP_TEXT11));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_TEXT12)) {
                    pd.put(columKey, pd.getString(Const.IMP_TEXT12));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_TEXT14)) {
                    pd.put(columKey, pd.getString(Const.IMP_TEXT14));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_TEXT17)) {
                    pd.put(columKey, pd.getString(Const.IMP_TEXT17));
                    continue;
                }
                if (bpCloum.equalsIgnoreCase(Const.IMP_TEXT20)) {
                    pd.put(columKey, pd.getString(Const.IMP_TEXT20));
                    continue;
                }
            }

            if (bpCloum.equalsIgnoreCase(Const.IMP_REMARK1)) {
                pd.put(columKey, pd.getString(Const.IMP_REMARK1));
                continue;
            }
            if (bpCloum.equalsIgnoreCase(Const.IMP_REMARK2)) {
                pd.put(columKey, pd.getString(Const.IMP_REMARK2));
                continue;
            }
            if (bpCloum.equalsIgnoreCase(Const.IMP_REMARK3)) {
                pd.put(columKey, pd.getString(Const.IMP_REMARK3));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.RECEIVE_INV_NO_1) || bpCloum.equalsIgnoreCase(Const.RECEIVE_INV_NO_2)) {
                //页面传入值
                pd.put(columKey, pd.getString(Const.RECEIVE_INV_NO_PAGE));
                continue;
            }

            //入库日期  CaseNumber ,Scan Code, Separate QTY COO LOT CODE BIN
            if (bpCloum.equalsIgnoreCase(Const.BP_INBOUNDTM)) {
                pd.put(columKey, Tools.date2Str(new Date()));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_CASENUMBER)) {
                pd.put(columKey, pd.getString(Const.BP_CASENUMBER_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_COO)) {
                pd.put(columKey, pd.getString(Const.BP_COO_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_DATACODE) || bpCloum.equalsIgnoreCase(Const.BP_DATECODE)
                    || bpCloum.startsWith(Const.BP_DATECODE2) || bpCloum.startsWith(Const.BP_DATECODE3)) {
                pd.put(columKey, pd.getString(Const.BP_DATACODE_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_SCANCODE)) {
                pd.put(columKey, pd.getString(Const.BP_SCANCODE_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_PACKAGEQTY_1) || bpCloum.equalsIgnoreCase(Const.BP_PACKAGEQTY_2)) {
                pd.put(columKey, pd.getString(Const.BP_PACKAGEQTY_SRC));
                continue;
            }
            if (bpCloum.equalsIgnoreCase(Const.BP_LOT_NO) || bpCloum.equalsIgnoreCase(Const.BP_LOTCODE)) {
                pd.put(columKey, pd.getString(Const.BP_LOTNO_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_BIN)) {
                pd.put(columKey, pd.getString(Const.BP_BIN_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_OUT_BOX)) {
                pd.put(columKey, pd.getString(Const.BP_OUT_BOX_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_OUT_BOX)) {
                pd.put(columKey, pd.getString(Const.BP_OUT_BOX_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_REMARK_CN) || bpCloum.equalsIgnoreCase(Const.BP_REMARK_EN)) {
                pd.put(columKey, pd.getString(Const.BP_BIN_SRC));
                continue;
            }
        }
    }


    private void setProperties2(List<PageData> propertyList, PageData pd) {
        for (PageData e : propertyList) {
            String cloumValue = e.getString("PROPERTY_KEY");
            if (StringUtil.isEmpty(cloumValue)) {
                continue;
            }
            Pair<Boolean, String> pair = isIn(cloumValue);
            if (pair.getKey()) {
                //如果字符串是属性配置中的值的话，根据其对应的排序，取到属性对应的字段名
                Integer srcSort = e.getInteger("SRC_SORT");
                String columKey = WmsEnum.OrderProperty.sortColuMap.get(srcSort);
                pd.put(columKey, pair.getValue());
            }
        }
    }

    private PageData buildBatchProperty(PageData pd) throws Exception{
        PageData batch = findSrcProdBatch(pd);
        List<String> columList = WmsEnum.OrderProperty.columList;
        //属性明细的数据
        PageData pdtl = new PageData();
        for (String colum : columList) {
            String value = pd.getString(colum);
            if (StringUtil.isEmpty(value)) {
                String columValue = StringUtil.isNoBlank(batch.getString(colum))?batch.getString(colum):Const.EMPTY_CH;
                pdtl.put(colum, columValue);
            } else {
                pdtl.put(colum, value);
            }
        }

        //设值ID，入库单ID，入库单属性类型
        String pid = StringUtil.isEmpty(pd.getString("P_ID")) ? UuidUtil.get32UUID() : pd.getString("P_ID");
        pdtl.put("P_ID", pid);
        pdtl.put("STOCKMGRIN_ID", pd.getString("STOCKMGRIN_ID"));
        pdtl.put("STOCKDTLMGRIN_ID", pd.getString("STOCKDTLMGRIN_ID"));
        pdtl.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_BATCH.getItemValue()));
        return pdtl;
    }
    //根据入库单Id、产品获取其原始记录的批次属性
    private PageData findSrcProdBatch(PageData pd) throws Exception {
        return  (PageData)dao.findForObject("ReceivOptMapper.findBatchByStockAndProdId", pd);
    }

    private Pair<Boolean, String> isIn(String colum) {
        String trimColum = StringUtil.trimSpace(colum);
        if (trimColum.equalsIgnoreCase(Const.BP_COO)) {
            return Pair.of(true, Const.BP_COO_SRC);
        }
        if (trimColum.equalsIgnoreCase(Const.BP_PACKAGEQTY_1) ||
                trimColum.equalsIgnoreCase(Const.BP_PACKAGEQTY_2)) {
            return Pair.of(true, Const.BP_PACKAGEQTY_SRC);
        }
        if (trimColum.equalsIgnoreCase(Const.BP_LOT_NO) ||
                trimColum.equalsIgnoreCase(Const.BP_LOTCODE)) {
            return Pair.of(true, Const.BP_LOTNO_SRC);
        }
        if (trimColum.equalsIgnoreCase(Const.BP_DATACODE)) {
            return Pair.of(true, Const.BP_DATACODE_SRC);
        }
        return Pair.of(false, null);
    }

}

