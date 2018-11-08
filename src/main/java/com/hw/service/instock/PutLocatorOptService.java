package com.hw.service.instock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.entity.base.Select;
import com.hw.service.property.OrderPropertyService;
import com.hw.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.print.attribute.standard.PDLOverrideSupported;
import javax.xml.crypto.dsig.keyinfo.PGPData;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-11-17
 */
@Service("putLocatorOptService")
public class PutLocatorOptService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Resource(name="stockmgrinService")
    private StockMgrInService stockmgrinService;


    public PageData findBoxForPut(PageData pd) throws Exception {
         List<PageData> list = (List<PageData>) dao.findForList("PutLocatorOptMapper.findBoxForPut", pd);
        return (list == null || list.isEmpty())? null:list.get(0);
    }

   //
   public PageData findBoxForScanPut(PageData pd) throws Exception {
       List<PageData> list = (List<PageData>) dao.findForList("PutLocatorOptMapper.findBoxForScanPut", pd);
       if (list == null || list.isEmpty()) {
           return null;
       }
       PageData rs = list.get(0);
       rs.put("PRODUCT_CN", BaseInfoCache.getInstance().getProductCode(rs.getString("PRODUCT_ID")));
       if (list.size() == 1) {
            return rs;
       }
       for (PageData p : list) {
           if (!StringUtil.isBlank(p.getString("BIG_BOX_NO"))) {
                 p.put("PRODUCT_CN", BaseInfoCache.getInstance().getProductCode(p.getString("PRODUCT_ID")));
                return p;
           }
       }
       return rs;
   }
    /*
   *获取手工码放列表
   */
    public List<PageData> findManualPutlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("PutLocatorOptMapper.findManualPutlistPage", page);
    }

    public List<PageData> findCancellistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("PutLocatorOptMapper.findCancellistPage", page);
    }

    public List<PageData> findDtlPutStatelistPage(Page page) throws Exception {
        return stockmgrinService.findDtlPutStatelistPage(page);
    }

    public void findManualPutlistPage(List<PageData> srcList, List<PageData> assignList,Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOptMapper.findAssignHead", page);
        List<PageData> list = (List<PageData>)dao.findForList("PutLocatorOptMapper.findManualPutlistPage", page);

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


    public void findCancellistPage(List<PageData> srcList, List<PageData> assignList,Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOptMapper.findAssignHead", page);
        List<PageData> list = (List<PageData>)dao.findForList("PutLocatorOptMapper.findCancellistPage", page);

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

    //扫描码放
    public void savePutScan(PageData pd) throws Exception {
        String[] boxNos = StringUtils.split(pd.getString("BOX_NOS"),Const.SPLIT_COMMA);
        String LOCATOR_ID = pd.getString("LOCATOR_ID");
        String STORAGE_ID = pd.getString("STORAGE_ID");

        Map<String, Select> locatorMap = SelectCache.getInstance().getAllLocatorIdMap();
        Select selct = locatorMap.get(LOCATOR_ID);
        STORAGE_ID = selct != null ?selct.getGroup():STORAGE_ID;

        List<PageData> boxNoList = findByBoxNosForScan(boxNos);
        if (boxNoList == null || boxNoList.isEmpty()) {
            return;
        }
        List<PageData> stockMgrInIds = findStockMgrInIdsForScan(boxNoList);
        //List<PageData> boxNoList = getByBoxNos(boxNos);
        Map<String, List<PageData>> stockmgrIdMap = groupByStockmgrId(boxNoList);
        String putState = WmsEnum.PutState.ALL.getItemKey();
        for(Map.Entry<String, List<PageData>> entry : stockmgrIdMap.entrySet()) {
            for (PageData rs : entry.getValue()) {
                //箱号表更新字段
                rs.put("PUT_LOCATOR",LOCATOR_ID);
                rs.put("PUT_STORAGE",STORAGE_ID);

                //入库单明细字段
                rs.put("PUT_LOCATOR_ID",LOCATOR_ID);
                rs.put("PUT_STORAGE_ID",STORAGE_ID);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));

                //两者相同字段
                rs.put("PUT_STATUS",putState);
                pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
                pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

                //1 更新箱号表的 码放状态、码放库区、码放库位 写入tm_box
                dao.update("BoxOptMapper.editPutState", rs);

                //2 更新入库单明细的 码放状态、码放库区、码放库位 tm_stockMgrin_dtl
                rs.put("PUT_EA",rs.getInteger("EA_ACTUAL"));
                dao.update("StockMgrInMapper.editPutStateScanPut", rs);
            }
            //更新该入库单单头的码放状态
          //  updateHeadPutState(entry.getKey());

        }
        //更新该入库单单头的码放状态
        for (PageData p : stockMgrInIds) {
            updateHeadPutState(p.getString("STOCKMGRIN_ID"));
        }
    }


    //扫描码放
    public void savePutScan1(PageData pd) throws Exception {
        String[] boxNos = StringUtils.split(pd.getString("BOX_NOS"), Const.SPLIT_COMMA);
        String LOCATOR_ID = pd.getString("LOCATOR_ID");
        String STORAGE_ID = pd.getString("STORAGE_ID");

        Map<String, Select> locatorMap = SelectCache.getInstance().getAllLocatorIdMap();
        Select selct = locatorMap.get(LOCATOR_ID);
        STORAGE_ID = selct != null ? selct.getGroup() : STORAGE_ID;
        List<PageData> boxNoList = findByBoxNosForScan(boxNos);
        if (boxNoList == null || boxNoList.isEmpty()) {
            return;
        }

        List<PageData> stockMgrInIds = findStockMgrInIdsForScan(boxNoList);
        String putState = WmsEnum.PutState.ALL.getItemKey();
        for (PageData rs : boxNoList) {
            //箱号表更新字段
            rs.put("PUT_LOCATOR", LOCATOR_ID);
            rs.put("PUT_STORAGE", STORAGE_ID);

            //入库单明细字段
            rs.put("PUT_LOCATOR_ID", LOCATOR_ID);
            rs.put("PUT_STORAGE_ID", STORAGE_ID);
            rs.put("STOCKDTLMGRIN_ID", rs.getString("BOX_ID"));

            //两者相同字段
            rs.put("PUT_STATUS", putState);
            pd.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
            pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间

            //1 更新箱号表的 码放状态、码放库区、码放库位 写入tm_box
            dao.update("BoxOptMapper.editPutState", rs);

            //2 更新入库单明细的 码放状态、码放库区、码放库位 tm_stockMgrin_dtl
            dao.update("StockMgrInMapper.editPutStateForScan", rs);
        }

        //更新该入库单单头的码放状态
        for (PageData p : stockMgrInIds) {
            updateHeadPutState(p.getString("STOCKMGRIN_ID"));
        }
    }


    public void savePutManualHead(PageData pd) throws Exception {
        String[] boxNos = StringUtils.split(pd.getString("INSTOCK_NO_CHONSES"),Const.SPLIT_COMMA);
        String LOCATOR_ID = pd.getString("LOCATOR_ID");
        String STORAGE_ID = pd.getString("STORAGE_ID");

        List<PageData> boxNoList = getByInstockNos4Put(boxNos);
        Map<String, List<PageData>> stockmgrIdMap = groupByStockmgrId(boxNoList);
        String putState = WmsEnum.PutState.ALL.getItemKey();
        for(Map.Entry<String, List<PageData>> entry : stockmgrIdMap.entrySet()) {
            for (PageData rs : entry.getValue()) {
                //箱号表更新字段
                rs.put("PUT_LOCATOR",LOCATOR_ID);
                rs.put("PUT_STORAGE",STORAGE_ID);

                //入库单明细字段
                rs.put("PUT_LOCATOR_ID",LOCATOR_ID);
                rs.put("PUT_STORAGE_ID",STORAGE_ID);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));

                //两者相同字段
                rs.put("PUT_STATUS",putState);
                rs.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
                rs.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

                //1 更新箱号表的 码放状态、码放库区、码放库位 写入tm_box
                dao.update("BoxOptMapper.editPutState", rs);

                //2 更新入库单明细的 码放状态、码放库区、码放库位 tm_stockMgrin_dtl

                rs.put("PUT_EA",rs.getInteger("EA_ACTUAL"));
                dao.update("StockMgrInMapper.editPutState", rs);
            }
            //更新该入库单单头的码放状态
            updateHeadPutState(entry.getKey());
        }
    }

    //手工码放
    public void savePutManual(PageData pd) throws Exception {
        String[] boxNos = StringUtils.split(pd.getString("BOX_NOS"),Const.SPLIT_COMMA);
        String LOCATOR_ID = pd.getString("LOCATOR_ID");
        String STORAGE_ID = pd.getString("STORAGE_ID");

        List<PageData> boxNoList = getByBoxNos(boxNos);
        Map<String, List<PageData>> stockmgrIdMap = groupByStockmgrId(boxNoList);
        String putState = WmsEnum.PutState.ALL.getItemKey();
        for(Map.Entry<String, List<PageData>> entry : stockmgrIdMap.entrySet()) {
            for (PageData rs : entry.getValue()) {
                //箱号表更新字段
                rs.put("PUT_LOCATOR",LOCATOR_ID);
                rs.put("PUT_STORAGE",STORAGE_ID);

                //入库单明细字段
                rs.put("PUT_LOCATOR_ID",LOCATOR_ID);
                rs.put("PUT_STORAGE_ID",STORAGE_ID);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));

                //两者相同字段
                rs.put("PUT_STATUS",putState);
                rs.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
                rs.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

                //1 更新箱号表的 码放状态、码放库区、码放库位 写入tm_box
                dao.update("BoxOptMapper.editPutState", rs);

                //2 更新入库单明细的 码放状态、码放库区、码放库位 tm_stockMgrin_dtl

                rs.put("PUT_EA",rs.getInteger("EA_ACTUAL"));
                dao.update("StockMgrInMapper.editPutState", rs);
            }
            //更新该入库单单头的码放状态
            updateHeadPutState(entry.getKey());
        }
    }

    //码放取消
    public void savePutCacelHead(PageData pd) throws Exception {
        String[] boxNos = StringUtils.split(pd.getString("INSTOCK_NO_CHONSES"),Const.SPLIT_COMMA);
        String LOCATOR_ID = Const.EMPTY_CH;;
        String STORAGE_ID = Const.EMPTY_CH;;

        List<PageData> boxNoList = getByInstockNos4Cancel(boxNos);
        Map<String, List<PageData>> stockmgrIdMap = groupByStockmgrId(boxNoList);
        String putState = WmsEnum.PutState.NONE.getItemKey();
        for(Map.Entry<String, List<PageData>> entry : stockmgrIdMap.entrySet()) {
            for (PageData rs : entry.getValue()) {
                //箱号表更新字段
                rs.put("PUT_LOCATOR",LOCATOR_ID);
                rs.put("PUT_STORAGE",STORAGE_ID);

                //入库单明细字段
                rs.put("PUT_LOCATOR_ID",LOCATOR_ID);
                rs.put("PUT_STORAGE_ID",STORAGE_ID);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));

                //两者相同字段
                rs.put("PUT_STATUS",putState);
                pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
                pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

                //1 更新箱号表的 码放状态、码放库区、码放库位 写入tm_box
                dao.update("BoxOptMapper.editPutState", rs);

                //2 更新入库单明细的 码放状态、码放库区、码放库位 tm_stockMgrin_dtl
                dao.update("StockMgrInMapper.editPutState", rs);
            }
            //更新该入库单单头的码放状态
            updateHeadPutState(entry.getKey());
        }

    }

    //码放取消
    public void savePutCacel(PageData pd) throws Exception {
        String[] boxNos = StringUtils.split(pd.getString("BOX_NOS"),Const.SPLIT_COMMA);
        String LOCATOR_ID = Const.EMPTY_CH;;
        String STORAGE_ID = Const.EMPTY_CH;;

        List<PageData> boxNoList = getByBoxNos(boxNos);
        Map<String, List<PageData>> stockmgrIdMap = groupByStockmgrId(boxNoList);
        String putState = WmsEnum.PutState.NONE.getItemKey();
        for(Map.Entry<String, List<PageData>> entry : stockmgrIdMap.entrySet()) {
            for (PageData rs : entry.getValue()) {
                //箱号表更新字段
                rs.put("PUT_LOCATOR",LOCATOR_ID);
                rs.put("PUT_STORAGE",STORAGE_ID);

                //入库单明细字段
                rs.put("PUT_LOCATOR_ID",LOCATOR_ID);
                rs.put("PUT_STORAGE_ID",STORAGE_ID);
                rs.put("STOCKDTLMGRIN_ID",rs.getString("BOX_ID"));

                //两者相同字段
                rs.put("PUT_STATUS",putState);
                pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
                pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间

                //1 更新箱号表的 码放状态、码放库区、码放库位 写入tm_box
                dao.update("BoxOptMapper.editPutState", rs);

                //2 更新入库单明细的 码放状态、码放库区、码放库位 tm_stockMgrin_dtl
                dao.update("StockMgrInMapper.editPutState", rs);
            }
            //更新该入库单单头的码放状态
            updateHeadPutState(entry.getKey());
        }

    }

    //更新入库单头的码放状态
    public void updateHeadPutState(String STOCKMGRIN_ID) throws Exception{
        updateSrcDtlEA(STOCKMGRIN_ID);
        PageData pd = new PageData();
        pd.put("STOCKMGRIN_ID",STOCKMGRIN_ID);

        PageData receiCnt = (PageData)dao.findForObject("StockMgrInMapper.findAllReceive", pd);
        if (receiCnt == null ) {
            return;
        }
        PageData assignCnt = (PageData)dao.findForObject("StockMgrInMapper.findPutState", pd);
        //该入库单的收货纪录总数、已码放的纪录数
        int mayNum = receiCnt.getInteger("CNT");
        int actNum = assignCnt==null?0:assignCnt.getInteger("CNT");
        //收货纪录总数 = 已码放的纪录数 ->全部分配
        //收货纪录总数 > 已码放的纪录数 >0 ->部分分配
        //已分配的纪录数 =0 ->未码放
        if(actNum == 0) {
            pd.put("PUT_STATE",WmsEnum.PutState.NONE.getItemKey());
        } else if (actNum>= mayNum && actNum>0) {
            pd.put("PUT_STATE",WmsEnum.PutState.ALL.getItemKey());
        } else {
            pd.put("PUT_STATE",WmsEnum.PutState.PART.getItemKey());
        }
        dao.update("StockMgrInMapper.editHeadPutState",pd);
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
            int PUT_EA = 0;//srcPd.getInteger("PUT_EA");
            for(int i=1,len = srcList.size();i<len;i++) {
                PUT_EA += srcList.get(i).getInteger("PUT_EA");
            }
            srcPd.put("PUT_EA",PUT_EA);
            int EA_EA = srcPd.getInteger("EA_EA");
            String state = WmsEnum.PutState.PART.getItemKey();
            if(PUT_EA == 0) {
                state = WmsEnum.PutState.NONE.getItemKey();
            } else if(PUT_EA>= EA_EA) {
                state = WmsEnum.PutState.ALL.getItemKey();
            }
            srcPd.put("PUT_STATUS",state);
            dao.update("StockMgrInMapper.editSrcDtlPutState",srcPd);
        }
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

    private List<PageData> getByInstockNos4Put(String[] InstockNos) {
        try {
            return (List<PageData>) dao.findForList("BoxOptMapper.findByInstockNos4Put", InstockNos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<PageData> getByInstockNos4Cancel(String[] InstockNos) {
        try {
            return (List<PageData>) dao.findForList("BoxOptMapper.getByInstockNos4Cancel", InstockNos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<PageData> getByBoxNos(String[] boxNos) {
        try {
            return (List<PageData>) dao.findForList("BoxOptMapper.findByBoxNos", boxNos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<PageData> findByBoxNosForScan(String[] boxNos) throws Exception{
            return (List<PageData>) dao.findForList("BoxOptMapper.findByBoxNosForScan", boxNos);
    }

    private List<PageData> findStockMgrInIdsForScan(List<PageData> boxs) throws Exception{
        List<String> ids = getStockMgrInId(boxs);
        return (List<PageData>) dao.findForList("BoxOptMapper.findStockMgrInIdsForScan", ids);
    }

    private List<String> getStockMgrInId(List<PageData> boxs) {
        List<String> list = new ArrayList<>(boxs.size());
        for (PageData p : boxs) {
            list.add(p.getString("BOX_ID"));
        }
        return list;
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



    private Map<String, List<PageData>> groupByStockmgrIdForScan(List<PageData> list) {
        Map<String, List<PageData>> map = new HashMap<>(list.size());
        for (PageData e : list) {
            String STOCKMGRIN_ID = e.getString("STOCKMGRIN_ID");
            STOCKMGRIN_ID = StringUtil.isEmpty(STOCKMGRIN_ID)?Const.SPLIT_LINE:STOCKMGRIN_ID;

            List<PageData> pageDatas = map.get(STOCKMGRIN_ID);
            if (pageDatas == null || pageDatas.isEmpty()) {
                pageDatas = new ArrayList<>();
            }
            pageDatas.add(e);
            map.put(STOCKMGRIN_ID, pageDatas);
        }
        return map;
    }


    private Map<String, List<PageData>> groupByStockmgrId2(List<PageData> list) {
        Map<String, List<PageData>> map = new HashMap<>(list.size());
        for (PageData e : list) {
            String STOCKMGRIN_ID = e.getString("STOCKMGRIN_ID");
            STOCKMGRIN_ID = StringUtil.isEmpty(STOCKMGRIN_ID)?Const.SPLIT_LINE:STOCKMGRIN_ID;
            List<PageData> pageDatas = map.get(STOCKMGRIN_ID);
            if (pageDatas == null || pageDatas.isEmpty()) {
                pageDatas = new ArrayList<>();
            }
            pageDatas.add(e);
            map.put(STOCKMGRIN_ID, pageDatas);
        }
        return map;
    }


    //PutLocatorOptMapper
}

