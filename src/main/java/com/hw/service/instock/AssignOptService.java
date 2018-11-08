package com.hw.service.instock;

import com.hw.cache.SelectCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.service.property.OrderPropertyService;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.dsig.keyinfo.PGPData;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-11-17
 */
@Service("assignoptService")
public class AssignOptService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "orderpropertyService")
    private OrderPropertyService orderpropertyService;

    @Resource(name = "stockmgrinService")
    private StockMgrInService stockmgrinService;

    /*
    * 新增
    */
    public void save(PageData pd) throws Exception {
        dao.save("AssignOptMapper.save", pd);
    }

    /*
    *获取本次需要分配的明细
    */
    public List<PageData> findAssignlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("AssignOptMapper.findAssignlistPage", page);
    }

    /*
   *获取本次取消分配的明细
   */
    public List<PageData> findAssignCacellistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("AssignOptMapper.findAssignCacellistPage", page);
    }

    public void findAssignCacellistPage(List<PageData> srcList, List<PageData> assignList,Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOptMapper.findAssignHead", page);
        List<PageData> list = (List<PageData>) dao.findForList("AssignOptMapper.findAssignCacellistPage", page);
        if (list == null || list.isEmpty()) {
            return;
        }
        srcList.addAll(headList);
        assignList.addAll(list);
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }

    public void findAssignlistPage(List<PageData> srcList, List<PageData> assignList,Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOptMapper.findAssignHead", page);
        List<PageData> list = (List<PageData>)dao.findForList("AssignOptMapper.findAssignlistPage", page);

        if (list == null || list.isEmpty()) {
            return;
        }
        srcList.addAll(headList);
        assignList.addAll(list);

        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }


    /**
     * 获取库位 库区,优先级 <br>
     * LOCATOR_ID,STORAGE_ID,LOCATOR_LEVEL
     * @return
     * @throws Exception
     */
    public List<PageData> findStockLocator() throws Exception {
        return (List<PageData>) dao.findForList("AssignOptMapper.findStockLocator", null);
    }

    /**
     * 获取库位分配规则
     * @return
     * @throws Exception
     */
    public List<PageData> findStorageAssigneRule(String customerId) throws Exception {
        PageData pd = new PageData();
        pd.put("CUSTOMER_ID",customerId);
        return (List<PageData>) dao.findForList("AssignOptMapper.findStorageAssigneRule", pd);
    }


    /*
    * 删除
    */
    public void delete(PageData pd) throws Exception {
        dao.delete("AssignOptMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd) throws Exception {
        dao.update("AssignOptMapper.edit", pd);
    }

    /*
    *获取本次收货明细
    */
    public List<PageData> findCurReceivDtl(Page page) throws Exception {
        return (List<PageData>) dao.findForList("AssignOptMapper.findCurReceivlistPage", page);
    }

    /*
    *列表
    */
    public List<PageData>   list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("AssignOptMapper.datalistPage", page);
    }

    /*
    *列表 findReceiveInstock
    */
    public List<PageData> findReceiInstock(Page page) throws Exception {
        return (List<PageData>) dao.findForList("AssignOptMapper.datalistPage", page);
    }


    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("AssignOptMapper.listAll", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("AssignOptMapper.findById", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("AssignOptMapper.deleteAll", ArrayDATA_IDS);
    }


    //==========================收货==================================================

    public List<PageData> findDtlName(Page page) throws Exception {
        return stockmgrinService.findDtlName(page);
    }
   
    public void findDtlAssignlistPage(List<PageData> srcList,List<PageData> assignList,Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOptMapper.findAssignHead", page);
        List<PageData> list = (List<PageData>)dao.findForList("AssignOptMapper.findDtllistPage", page);
        if (list == null || list.isEmpty()) {
            return;
        }
        srcList.addAll(headList);
        assignList.addAll(list);

        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }


    /**
     * 根据入库单得到已收货明细
     */
    public List<PageData> findCancelDtlName(Page page) throws Exception {
        return stockmgrinService.findCancelDtlName(page);
    }

    //根据条件 判断收货状态  editStockState
    public String findInstockState(PageData pd) throws Exception {
        PageData rs = (PageData) dao.findForObject("StockMgrInMapper.findInstockState", pd);
        int cnt = rs.getInteger("CNT");
        //如果cnt== 0,全部收货,否则为部分收货
        return cnt == 0 ?
                WmsEnum.InstockState.ALL.getItemKey() : WmsEnum.InstockState.PART.getItemKey();
    }

    private List<PageData> getByBoxNos(String[] boxNos) {
        try {
            return (List<PageData>) dao.findForList("BoxOptMapper.findByBoxNos", boxNos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<PageData> getBoxNosByInstockNo(String[] instockNos) {
        try {
            return (List<PageData>) dao.findForList("BoxOptMapper.findByInstockNos", instockNos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<PageData> findBoxNosByInstockNoForCalcel(String[] instockNos) {
        try {
            return (List<PageData>) dao.findForList("BoxOptMapper.findBoxNosByInstockNoForCalcel", instockNos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

   private Map<String, List<PageData>> groupByInstockNo(List<PageData> list) {
       Map<String, List<PageData>> map = new HashMap<>(list.size());
       for (PageData e : list) {
           String INSTOCK_NO = e.getString("INSTOCK_NO");
           List<PageData> pageDatas = map.get(INSTOCK_NO);
           if (pageDatas == null || pageDatas.isEmpty()) {
               pageDatas = new ArrayList<>();
           }
           pageDatas.add(e);
           map.put(INSTOCK_NO, pageDatas);
       }
       return map;
   }

    private Map<String, List<PageData>> groupByCustomer(List<PageData> list) {
        Map<String, List<PageData>> map = new HashMap<>(list.size());
        for (PageData e : list) {
            String CUSTOMER_ID = e.getString("CUSTOMER_ID");
            List<PageData> pageDatas = map.get(CUSTOMER_ID);
            if (pageDatas == null || pageDatas.isEmpty()) {
                pageDatas = new ArrayList<>();
            }
            pageDatas.add(e);
            map.put(CUSTOMER_ID, pageDatas);
        }
        return map;
    }

   //根据入库单Id分组
    private Map<String, List<PageData>> groupByStockmgrId(List<PageData> list) {
        Map<String, List<PageData>> map = new HashMap<>(list.size());
        for (PageData e : list) {
            String STOCKMGRIN_ID = e.getString("STOCKMGRIN_ID");
            List<PageData> pageDatas = map.get(STOCKMGRIN_ID);
            if (pageDatas == null || pageDatas.isEmpty()) {
                pageDatas = new ArrayList<>();
            }
            pageDatas.add(e);
            map.put(STOCKMGRIN_ID, pageDatas);
        }
        return map;
    }

    //按整单分配
    public void saveAssignHead(PageData pd) throws Exception {
        //操作类型：1 自动分配 2 手工分配 3 缺省分配 4取消分配
        String OPT_EVEN = pd.getString("OPT_EVEN");
        String[] boxNos = StringUtils.split(pd.getString("INSTOCK_NO_CHOSE"),Const.SPLIT_COMMA);

        List<PageData> list;
        if(Const.OPT_EVEN_4.equals(OPT_EVEN)) {
            list = findBoxNosByInstockNoForCalcel(boxNos);
        } else {
            list = getBoxNosByInstockNo(boxNos);
        }

        Map<String, List<PageData>> groupByInstockNomap = groupByInstockNo(list);

        for (Map.Entry<String, List<PageData>> entry : groupByInstockNomap.entrySet()) {
            Map<String, List<PageData>> customerMap = groupByCustomer(entry.getValue());
            if (Const.OPT_EVEN_1.equals(OPT_EVEN)) {
                saveAutoAssign(customerMap);
            } else if(Const.OPT_EVEN_2.equals(OPT_EVEN)) {
                saveManual(list,pd);
            } else if(Const.OPT_EVEN_3.equals(OPT_EVEN)) {
                savDefaultAssign(customerMap);
            } else if(Const.OPT_EVEN_4.equals(OPT_EVEN)) {
                saveCacelAssign(list,pd);
            }
        }
    }

    /**
     * 自动分配：
     * 1、找到存货区的所有库位.<br>
     * 2、
     * 收货 操作
     *
     * @param pd
     */
    public void saveAssign(PageData pd) throws Exception {
        //操作类型：1 自动分配 2 手工分配 3 缺省分配 4取消分配
        String OPT_EVEN = pd.getString("OPT_EVEN");
        String[] boxNos = StringUtils.split(pd.getString("BOX_NOS"),Const.SPLIT_COMMA);
        // A.BOX_ID,A.BOX_NO,A.BOARD_NO,A.PRODUCT_ID,A.CUSTOMER_ID,B.STOCKMGRIN_ID
        List<PageData> list = getByBoxNos(boxNos);
        Map<String, List<PageData>> customerMap = groupByCustomer(list);

        if (Const.OPT_EVEN_1.equals(OPT_EVEN)) {
            saveAutoAssign(customerMap);
        } else if(Const.OPT_EVEN_2.equals(OPT_EVEN)) {
            saveManual(list,pd);
        } else if(Const.OPT_EVEN_3.equals(OPT_EVEN)) {
            savDefaultAssign(customerMap);
        } else if(Const.OPT_EVEN_4.equals(OPT_EVEN)) {
             saveCacelAssign(list,pd);
        }
    }


    /**
     * 自动收货逻辑
     */
    public void saveAutoAssign(Map<String, List<PageData>> customerMap) throws Exception {
        List<PageData> stockLocator = findStockLocator();

        for (Map.Entry<String, List<PageData>> entry : customerMap.entrySet()) {
            String CUSTOMER_ID = entry.getKey();
            List<PageData> storageAssigneRule = findStorageAssigneRule(CUSTOMER_ID);
            PageData locator = getLocator(storageAssigneRule, stockLocator);
            //BOX_ID,BOX_NO,BOARD_NO,PRODUCT_ID,CUSTOMER_ID,STOCKMGRIN_ID
           //LOCATOR_ID,STORAGE_ID,LOCATOR_LEVEL
            String LOCATOR_ID = locator.getString("LOCATOR_ID");
            String STORAGE_ID = locator.getString("STORAGE_ID");
            String assignState = WmsEnum.AssignedState.ALL.getItemKey();
            Set<String> stockMgrIdSet = new HashSet<>();
            for(PageData rs : entry.getValue()) {
                rs.put("ASIGN_LOCATOR",LOCATOR_ID);
                rs.put("ASIGN_STORAGE",STORAGE_ID);

                rs.put("LOCATOR_ID",LOCATOR_ID);
                rs.put("STORAGE_ID",STORAGE_ID);

                rs.put("ASSIGNED_STATE",assignState);
                rs.put("DISTR_STATE",assignState);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));
                //1 将分配库位 库区写入tm_box
                rs.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
                rs.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
                dao.update("BoxOptMapper.editAssinedState", rs);

               //实际收货数等于分配数
                // 2 将分配库位 库区写入tm_stockMgrin_dtl
                rs.put("ASSIGNED_EA",rs.getInteger("EA_ACTUAL"));
                dao.update("StockMgrInMapper.editAssinedState", rs);

                stockMgrIdSet.add(rs.getString("STOCKMGRIN_ID"));
            }

            //更新入库单头信息,如果全部分配，则更更新全部分配，否则为部分分配
            //ASSIGNED_STATE ASIGN_STORAGE ASIGN_LOCATOR
           //STORAGE_ID LOCATOR_ID DISTR_STATE

            for (String STOCKMGRIN_ID : stockMgrIdSet) {
                updateAssignState(STOCKMGRIN_ID);
            }

        }

    }

    //手工分配
    private void saveManual(List<PageData> list, PageData pd) throws Exception{
        String LOCATOR_ID = pd.getString("LOCATOR_ID");
        String STORAGE_ID = pd.getString("STORAGE_ID");

        Map<String, List<PageData>> stockmgrIdMap = groupByStockmgrId(list);
        String assignState = WmsEnum.AssignedState.ALL.getItemKey();
        for(Map.Entry<String, List<PageData>> entry : stockmgrIdMap.entrySet()) {
            for(PageData rs: entry.getValue()) {
                rs.put("ASIGN_LOCATOR",LOCATOR_ID);
                rs.put("ASIGN_STORAGE",STORAGE_ID);

                rs.put("LOCATOR_ID",LOCATOR_ID);
                rs.put("STORAGE_ID",STORAGE_ID);

                rs.put("ASSIGNED_STATE",assignState);
                rs.put("DISTR_STATE",assignState);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));
                rs.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
                //1 将分配库位 库区写入tm_box
                dao.update("BoxOptMapper.editAssinedState", rs);


                // 2 将分配库位 库区写入tm_stockMgrin_dtl
                rs.put("ASSIGNED_EA",rs.getInteger("EA_ACTUAL"));
                dao.update("StockMgrInMapper.editAssinedState", rs);
            }
            updateAssignState(entry.getKey());
        }
    }

    /**
     * 缺省收货逻辑
     */
    public void savDefaultAssign(Map<String, List<PageData>> customerMap) throws Exception {
        List<PageData> stockLocator = findStockLocator();

        for (Map.Entry<String, List<PageData>> entry : customerMap.entrySet()) {
            String CUSTOMER_ID = entry.getKey();
            List<PageData> storageAssigneRule = findStorageAssigneRule(CUSTOMER_ID);
            PageData locator = getLocator(storageAssigneRule, stockLocator);
            //BOX_ID,BOX_NO,BOARD_NO,PRODUCT_ID,CUSTOMER_ID,STOCKMGRIN_ID
            //LOCATOR_ID,STORAGE_ID,LOCATOR_LEVEL
            // String LOCATOR_ID = locator.getString("LOCATOR_ID");
            //缺省分配的默认id
            String LOCATOR_ID = Const.DEFAULT_LOCATOR_ID;
            String STORAGE_ID = locator.getString("STORAGE_ID");
            String assignState = WmsEnum.AssignedState.ALL.getItemKey();
            Set<String> stockMgrIdSet = new HashSet<>();
            for(PageData rs : entry.getValue()) {
                rs.put("ASIGN_LOCATOR",LOCATOR_ID);
                rs.put("ASIGN_STORAGE",STORAGE_ID);

                rs.put("LOCATOR_ID",LOCATOR_ID);
                rs.put("STORAGE_ID",STORAGE_ID);

                rs.put("ASSIGNED_STATE",assignState);
                rs.put("DISTR_STATE",assignState);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));
                //1 将分配库位 库区写入tm_box
                dao.update("BoxOptMapper.editAssinedState", rs);


                // 2 将分配库位 库区写入tm_stockMgrin_dtl
                rs.put("ASSIGNED_EA",rs.getInteger("EA_ACTUAL"));
                dao.update("StockMgrInMapper.editAssinedState", rs);

                stockMgrIdSet.add(rs.getString("STOCKMGRIN_ID"));
            }

            //更新入库单头信息,如果全部分配，则更更新全部分配，否则为部分分配
            //ASSIGNED_STATE ASIGN_STORAGE ASIGN_LOCATOR
            //STORAGE_ID LOCATOR_ID DISTR_STATE

            for (String STOCKMGRIN_ID : stockMgrIdSet) {
                updateAssignState(STOCKMGRIN_ID);
            }

        }

    }

    //取消分配
    private void saveCacelAssign(List<PageData> list, PageData pd) throws Exception{
        //取消分配，需要将库位，库区的字段更新为null
        String LOCATOR_ID = Const.EMPTY_CH;
        String STORAGE_ID = Const.EMPTY_CH;

        Map<String, List<PageData>> stockmgrIdMap = groupByStockmgrId(list);
        String assignState = WmsEnum.AssignedState.NONE.getItemKey();
        for(Map.Entry<String, List<PageData>> entry : stockmgrIdMap.entrySet()) {
            for(PageData rs: entry.getValue()) {
                rs.put("ASIGN_LOCATOR",LOCATOR_ID);
                rs.put("ASIGN_STORAGE",STORAGE_ID);

                rs.put("LOCATOR_ID",LOCATOR_ID);
                rs.put("STORAGE_ID",STORAGE_ID);

                rs.put("ASSIGNED_STATE",assignState);
                rs.put("DISTR_STATE",assignState);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));

                rs.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
                rs.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
                //1 将分配库位 库区写入tm_box
                dao.update("BoxOptMapper.editAssinedState", rs);

                // 2 将分配库位 库区写入tm_stockMgrin_dtl
                rs.put("ASSIGNED_EA",0);
                dao.update("StockMgrInMapper.editAssinedState", rs);
            }
            updateAssignState(entry.getKey());
        }
    }

   private void updateSrcDtlEA(String STOCKMGRIN_ID)  throws Exception{
       PageData pd = new PageData();
       pd.put("STOCKMGRIN_ID",STOCKMGRIN_ID);
       List<PageData> list = (List<PageData>)dao.findForList("StockMgrInMapper.findDtlListByPid",pd);
       if(list == null || list.isEmpty()) {
           return;
       }
       Map<String, List<PageData>> map = groupByProdId(list);
       for(Map.Entry<String, List<PageData>> entry : map.entrySet()) {
           List<PageData> srcList = entry.getValue();
           if(srcList.isEmpty() || srcList.size()==1) {
               continue;
           }
           srcList.sort((e1,e2)->e1.getInteger("RECEI_FLG")-e2.getInteger("RECEI_FLG"));

           PageData srcPd = srcList.get(0);
           int ASSIGNED_EA = 0;//srcPd.getInteger("ASSIGNED_EA");
           for(int i=1,len = srcList.size();i<len;i++) {
               ASSIGNED_EA += srcList.get(i).getInteger("ASSIGNED_EA");
           }
           srcPd.put("ASSIGNED_EA",ASSIGNED_EA);
           int EA_EA = srcPd.getInteger("EA_EA");
           String state = WmsEnum.AssignedState.PART.getItemKey();
           if(ASSIGNED_EA == 0) {
               state = WmsEnum.AssignedState.NONE.getItemKey();
           } else if(ASSIGNED_EA>= EA_EA) {
               state = WmsEnum.AssignedState.ALL.getItemKey();
           }
           srcPd.put("DISTR_STATE",state);
           dao.update("StockMgrInMapper.editSrcDtlState",srcPd);
       }
   }

    public void updateAssignState(String STOCKMGRIN_ID) throws Exception{
        updateSrcDtlEA(STOCKMGRIN_ID);
        PageData pd = new PageData();
        pd.put("STOCKMGRIN_ID",STOCKMGRIN_ID);

        PageData receiCnt = (PageData)dao.findForObject("StockMgrInMapper.findAllReceive", pd);
        PageData assignCnt = (PageData)dao.findForObject("StockMgrInMapper.findAssinedState", pd);
        //该入库单的收货纪录总数、已分配的纪录数
        int mayNum = receiCnt.getInteger("CNT");
        int actNum = assignCnt == null ? 0 : assignCnt.getInteger("CNT");
        //收货纪录总数 = 已分配的纪录数 ->全部分配
        //收货纪录总数 > 已分配的纪录数 >0 ->部分分配
        //已分配的纪录数 =0 ->未分配
        if(actNum == 0) {
            pd.put("ASSIGNED_STATE",WmsEnum.AssignedState.NONE.getItemKey());
        } else if (actNum >= mayNum && actNum>0) {
            pd.put("ASSIGNED_STATE",WmsEnum.AssignedState.ALL.getItemKey());
        } else {
            pd.put("ASSIGNED_STATE",WmsEnum.AssignedState.PART.getItemKey());
        }
         dao.update("StockMgrInMapper.editHeadAssinedState",pd);
    }

    /**
     * 筛选合适使用的库位
     * @param storageAssigneRule 库位分配规则
     * @param stockLocator
     * @return
     */
    private PageData getLocator(List<PageData> storageAssigneRule,List<PageData> stockLocator) {
      if(storageAssigneRule == null || storageAssigneRule.isEmpty()) {
          return stockLocator.get(0);
      }
        //LOCATOR_ID,STORAGE_ID,LOCATOR_LEVEL
        for (PageData p : storageAssigneRule) {
            List<String> priorityLevels = p.getList("LOCATOR_PRIORITY_LEVEL");
            for(PageData s : stockLocator) {
                String locatorLevel = s.getString("LOCATOR_LEVEL");
                if (priorityLevels.contains(locatorLevel)) {
                    return s;
                }
            }
        }
        return stockLocator.get(0);
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
            dao.update("BoxOptMapper.deleteAll", dtlIds);

            //6、删除收货明细纪录
            delPd.put("RECEIVOPT_ID", id);
            dao.update("AssignOptMapper.deleteReceivDtl", delPd);

        }

        //7 更新入库单头信息
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
                pd.put("STOCKDTLMGRIN_ID", id);
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

    private PageData setDtl(PageData pd, PageData dtlPd) {
        PageData prodDtl = new PageData(dtlPd);
        //设值箱号，实际收货数、收货标记
        prodDtl.put("BOX_NO", pd.getString("BOX_NO"));
        prodDtl.put("EA_ACTUAL", pd.getInteger("RECEIV_QTY"));
        prodDtl.put("EA_EA", pd.getInteger("RECEIV_QTY"));
        prodDtl.put("RECEI_FLG", Const.RECEIVE_YES);
        //设值 入库单明细

        prodDtl.put("CREATE_EMP", pd.getString("CREATE_EMP"));    //创建人
        prodDtl.put("CREATE_TM", pd.getString("CREATE_TM"));    //创建时间
        prodDtl.put("MODIFY_EMP", pd.getString("MODIFY_EMP"));    //修改人
        prodDtl.put("MODIFY_TM", pd.getString("MODIFY_TM"));    //修改时间
        prodDtl.put("DEL_FLG", "0");    //删除标志
        prodDtl.put("MEMO", "收货");    //删除标志
        //收货状态
        //RECEIPT_STATE


        return prodDtl;
    }

    private void setProperties(List<PageData> propertyList, PageData pd) {
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

    private PageData buildBatchProperty(PageData pd) {
        List<String> columList = WmsEnum.OrderProperty.columList;
        //属性明细的数据
        PageData pdtl = new PageData();
        for (String colum : columList) {
            String value = pd.getString(colum);
            if (StringUtil.isEmpty(value)) {
                pdtl.put(colum, Const.EMPTY_CH);
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

    private Map<String,List<PageData>>  groupByProdId(List<PageData> list) {
        Map<String,List<PageData>> map = new HashMap<>();
        if(list == null || list.isEmpty()) {
            return map;
        }
        for(PageData p : list) {
            String prodId = p.getString("PRODUCT_ID");
            List<PageData> lst = map.get(prodId);
            if(lst == null || lst.isEmpty()) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(prodId,lst);
        }
        return map;
    }
}

