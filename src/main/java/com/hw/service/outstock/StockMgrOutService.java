package com.hw.service.outstock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-10-27
 */
@Service("stockmgroutService")
public class StockMgrOutService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    /*
    * 新增
    */
    public void save(PageData pd) throws Exception {
        PageData pdtl = buildOrderProperty(pd);
        pdtl.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_ORDER.getItemValue()));
        dao.save("StockMgrOutMapper.save", pd);
        dao.save("StockMgrOutMapper.saveOrderDtl", pdtl);
    }

    //收货确认
    public void saveConfirm(PageData pd)throws Exception{
        pd.put("REAL_OUT_DATE", Tools.date2Str(new Date()));
        dao.update("StockMgrOutMapper.saveConfirm",pd);
    }


    private PageData buildOrderProperty(PageData pd) {
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

        //设值ID，出库单ID，出库单属性类型
        String pid = StringUtil.isEmpty(pd.getString("P_ID")) ? UuidUtil.get32UUID() : pd.getString("P_ID");
        pdtl.put("P_ID", pid);
        pdtl.put("STOCKMGROUT_ID", pd.getString("STOCKMGROUT_ID"));
        pdtl.put("STOCKDTLMGROUT_ID", pd.getString("STOCKDTLMGROUT_ID"));
        //pdtl.put("ORDERBATCH_TYPE",SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_ORDER.getItemValue()));
        return pdtl;
    }

    /*
    * 删除
    */
    public void delete(PageData pd) throws Exception {
        dao.delete("StockMgrOutMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd) throws Exception {
        PageData pdtl = buildOrderProperty(pd);
        dao.update("StockMgrOutMapper.edit", pd);
        dao.update("StockMgrOutMapper.editOrderDtl", pdtl);
    }

    /*
    *列表
    */
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockMgrOutMapper.datalistPage", page);
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StockMgrOutMapper.listAll", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd) throws Exception {
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_ORDER.getItemValue()));
        return (PageData) dao.findForObject("StockMgrOutMapper.findById", pd);
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("StockMgrOutMapper.deleteAll", ArrayDATA_IDS);
    }

    /*
  * 批量删除
  */
    public void deleteDtlAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("StockMgrOutMapper.deleteDtlAll", ArrayDATA_IDS);
    }


    /*
    * 新增产品明细
    */
    public void saveDtl(PageData pd) throws Exception {
        PageData pdtl = buildOrderProperty(pd);
        pdtl.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));


       // 分配状态 ASSIGNED_STATE 拣货状态 PICK_STATE 装车状态 LOADED_STATE 发货状态 DEPOT_STATE
        pd.put("ASSIGNED_STATE",WmsEnum.AssignedState.NONE.getItemKey());
        pd.put("PICK_STATE",WmsEnum.PickState.NONE.getItemKey());
        pd.put("LOADED_STATE",WmsEnum.LoadedState.NONE.getItemKey());
        pd.put("DEPOT_STATE",WmsEnum.DepotState.NONE.getItemKey());
        pd.put("CARGO_STATE",WmsEnum.CargoState.NONE.getItemKey());

        pd.put("ASSIGNED_EA",Const.ZERO);
        pd.put("ASSIGNED_FLG",Const.ZERO);

        pd.put("SEND_EA",Const.ZERO);
        pd.put("PREPLAN_EA",Const.ZERO);
        pd.put("PICK_EA",Const.ZERO);
        pd.put("LOADED_EA",Const.ZERO);
        pd.put("CARGO_EA",Const.ZERO);
        pd.put("CARGO_TYPE",Const.ZERO);



        dao.save("StockMgrOutMapper.saveProdDtl", pd);
        dao.save("StockMgrOutMapper.saveOrderDtl", pdtl);
    }

    /*
    * 新增产品明细
    */
    public void saveDtlExcel(PageData pd) throws Exception {
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));
        dao.save("StockMgrOutMapper.saveProdDtl", pd);
        dao.save("StockMgrOutMapper.saveOrderDtl", pd);
    }

    /*
  * 修改
  */
    public void editExcel(PageData pd) throws Exception {
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));
        dao.update("StockMgrOutMapper.editDtl", pd);
        dao.update("StockMgrOutMapper.editOrderDtl", pd);
    }

    /*
   * 修改
   */
    public void editDtl(PageData pd) throws Exception {
        PageData pdtl = buildOrderProperty(pd);
        dao.update("StockMgrOutMapper.editDtl", pd);
        dao.update("StockMgrOutMapper.editOrderDtl", pdtl);
    }

    /*
	* 删除
	*/
    public void deleteDtl(PageData pd)throws Exception{
        dao.delete("StockMgrOutMapper.deleteDtl", pd);
    }

    /*
    * 通过id获取产品明细数据
    */
    public List<PageData> findDtl(PageData pd) throws Exception {
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));
        List<PageData> list = (List<PageData>) dao.findForList("StockMgrOutMapper.findDtl", pd);
        list.sort((e1, e2) -> Integer.parseInt(e1.getString("ROW_NO")) - Integer.parseInt(e2.getString("ROW_NO")));
        return list;

    }

    /*
    * 通过id获取产品明细数据
    */
    public List<PageData> findDtlName(Page page) throws Exception {
        String sql = BaseInfoCache.getInstance().getOutStockBatchSql(page.getPd().getString("CUSTOMER_ID"));
        String sql2 = BaseInfoCache.getInstance().getOutStockBatchEmptySql(page.getPd().getString("CUSTOMER_ID"));

        page.getPd().put("DYNC_E",sql);
        page.getPd().put("DYNC_N",sql2);

        page.getPd().put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));
        List<PageData> list = (List<PageData>) dao.findForList("StockMgrOutMapper.findDtlNamelistPage", page);
        if(list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        for(PageData p : list) {
            setEmptyValue(p,"BOX_NO");
            p.put("ASSIGNED_NAME",WmsEnum.AssignedState.getName(p.getString("ASSIGNED_STOCK_STATE")));
            p.put("PICK_NAME",WmsEnum.PickState.getName(p.getString("PICK_STATE")));
            p.put("CARGO_NAME",WmsEnum.CargoState.getName(p.getString("CARGO_STATE")));
            p.put("DEPOT_NAME",WmsEnum.DepotState.getName(p.getString("DEPOT_TYPE")));
        }

      /*  ROW_NO,STOCKDTLMGROUT_ID,STOCKMGROUT_ID,T1.CUSTOMER_ID,T1.PRODUCT_ID,
                EA_EA,T1.BOX_NO,CUSTOMER_CN,PRODUCT_CN,T2.ASSIGNED_STOCK_STATE,T2.PICK_STATE,T2.CARGO_STATE,T2.DEPOT_TYPE*/

        /*list.sort((e1, e2) -> {
            if (Integer.parseInt(e1.getString("ROW_NO")) == Integer.parseInt(e2.getString("ROW_NO"))) {
                return e1.getUtilDate("CREATE_TM").compareTo(e1.getUtilDate("CREATE_TM"));
            } else {
                return  Integer.parseInt(e1.getString("ROW_NO")) - Integer.parseInt(e2.getString("ROW_NO"));
            }
        });*/
        return list;
    }

    public int findRowNo(PageData pd) throws Exception {
        PageData p = (PageData) dao.findForObject("StockMgrOutMapper.findRowNo", pd);

        return p == null ? 0 :p.getInteger("ROW_NO");
    }

    private void setEmptyValue(PageData pd,String colum) {
        if(StringUtil.isEmpty(pd.getString(colum))) {
            pd.put(colum,Const.EMPTY_CH);
        }
    }

    public PageData findDtlById(PageData pd) throws Exception {
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));
        return (PageData) dao.findForObject("StockMgrOutMapper.findDtl", pd);

    }

    public PageData findDtlByPidAndProdId(PageData pd) {
        try {
            List<PageData> list = (List<PageData>) dao.findForList("StockMgrOutMapper.findDtlByPidAndProd", pd);
            if (list != null && !list.isEmpty()) {
                return list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public PageData findByOutStockNo(String outStockNo) throws Exception {
        PageData pd = new PageData();
        pd.put("OUTSTOCK_NO", outStockNo);
        List<PageData> list = (List<PageData>) dao.findForList("StockMgrOutMapper.findByOutStockNoForCheck", pd);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 判断入库单号是否存在
     *
     * @param outStockNo 入库单号
     * @return true/false
     */
    public boolean isExists(String outStockNo) {

        try {
            PageData data = findByOutStockNo(outStockNo);
            if (data != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<PageData> auditData(String customerId, List<PageData> listPd, Set<String> prodCodeList) {
        List<PageData> rsList = new ArrayList<>();
        Set<String> set = new HashSet<>();
        //扫描料号-实际料号
        Map<String, String> sacanProdMap = BaseInfoCache.getInstance().getScanCodeProdCodeMap(customerId);

        for (int i = 0; i < listPd.size(); i++) {
            String PROD = listPd.get(i).getString("var0");
            String EA = listPd.get(i).getString("var1");

            //扫描料号对应的实际料号
            String actulProd = sacanProdMap.get(PROD);
            if(StringUtil.isEmpty(actulProd) && !prodCodeList.contains(PROD)) {
                PageData pd = new PageData();
                pd.put("PROD",PROD);
                pd.put("EA_NUM",EA);
                pd.put("ADD_FLG",1);
                pd.put("MEMO","料号在对照表中不存在,并且该客户的实际料号中也不存在.");
                rsList.add(pd);
                continue;
            }
            actulProd = StringUtil.isEmpty(actulProd)?PROD:actulProd;

            if (set.contains(actulProd)) {
                PageData pd = new PageData();
                if(PROD.equals(actulProd)) {
                    pd.put("PROD",PROD);
                    pd.put("EA_NUM",EA);
                    pd.put("ADD_FLG",0);
                    pd.put("MEMO","料号重复...");
                } else {
                    pd.put("PROD",PROD+"("+actulProd+")");
                    pd.put("EA_NUM","");
                    pd.put("ADD_FLG",0);
                    pd.put("MEMO","料号对应的实际料号重复...");
                }
                rsList.add(pd);
            } else {
                set.add(actulProd);
            }

            if (!prodCodeList.contains(actulProd)) {
                PageData pd = new PageData();
                pd.put("PROD", PROD);
                pd.put("EA_NUM", EA);
                pd.put("MEMO", "料号不属于该客户");
                rsList.add(pd);
                continue;
            }

            if (!isNum(EA)) {
                PageData pd = new PageData();
                pd.put("PROD", PROD);
                pd.put("EA_NUM", EA);
                pd.put("MEMO", "数量(EA)填写的不是数字");
                rsList.add(pd);
                continue;
            }
            listPd.get(i).put("PROD",actulProd);
            listPd.get(i).put("var0",actulProd);

        }
        return rsList;
    }

    private boolean isNum(String EA) {
        try {
            Integer.parseInt(EA);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
