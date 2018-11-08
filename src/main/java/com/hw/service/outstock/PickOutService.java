package com.hw.service.outstock;

import com.hw.cache.BaseInfoCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created：panda.HuangWei
 * Date：2016-12-4
 */

@Service("pickOutService")
public class PickOutService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "stockmgroutService")
    private StockMgrOutService stockmgroutService;

    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("PickOutMapper.datalistPage", page);
    }

    //查看界面
    public void findDtlAssignOutlistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {

        List<PageData> headList = (List<PageData>) dao.findForList("PickOutMapper.findDtlPickOutManualHead", page);
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findDtlAssignOutlistPage", page);
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

    //分配界面
    public void findDtlPickOutlistPage(List<PageData> srcList, List<PageData> assignList, List<String> ids) throws Exception {

        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findDtlAssignOutlist2Page", ids);
        if (list == null || list.isEmpty()) {
            return;
        }
        List<PageData> tmpList = new ArrayList<>();
        //根据收货标识将list分割为原始记录与分配的明细纪录
        for (PageData p : list) {
            if (Const.ZERO_CH.equals(p.getString("ASSIGNED_FLG"))) {
                tmpList.add(p);
            } else {
                assignList.add(p);
            }
        }
        List<PageData> datas = filterById(tmpList);
        srcList.addAll(datas);

        srcList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }

    //分配界面
    public void findDtlPickOutManuallistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {

        List<PageData> headList = (List<PageData>) dao.findForList("PickOutMapper.findDtlPickOutManualHead", page);
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findDtlPickOutManuallistPage", page);
        if (list == null || list.isEmpty()) {
            return;
        }
        assignList.addAll(list);
        //明细不为空的时候才需要进行下步的过滤与排序
        if (!assignList.isEmpty()) {
            srcList.addAll(headList);
            assignList.sort((e1, e2) -> {
                if (e1.getString("OUTSTOCK_NO").equals(e2.getString("OUTSTOCK_NO"))) {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                    return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
                } else {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                }
            });
        }
    }

    //分配界面
    public void findDtlPickCacellistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("PickOutMapper.findDtlPickOutManualHead", page);
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findDtlAssignOutCancellistPage", page);
        if (list == null || list.isEmpty()) {
            return;
        }
        assignList.addAll(list);
        //明细不为空的时候才需要进行下步的过滤与排序
        if (!assignList.isEmpty()) {
            srcList.addAll(headList);


            assignList.sort((e1, e2) -> {
                if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                    return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
                } else {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                }
            });
        }
    }

    public void findDtlPickOutSpreaListPage(List<PageData> srcList, Page page) throws Exception {
        List<PageData> assignList = (List<PageData>) dao.findForList("PickOutMapper.findDtlPickOutSprealistPage", page);
        if (assignList == null || assignList.isEmpty()) {
            return;
        }
        srcList.addAll(assignList);

        for (PageData p : srcList) {
            StockUtils.addBox4Pros(p,BaseInfoCache.getInstance().getBoxPd(p.getString("BOX_ID")));
            p.put("PRODUCT_CODE", BaseInfoCache.getInstance().getProductCode(p.getString("PRODUCT_ID")));
            String locatorId = p.getString("PUT_LOCATOR");
            if (StringUtil.isNotEmpty(locatorId)) {
                p.put("LOCATOR_CODE", BaseInfoCache.getInstance().getLocatorCode(locatorId));
            } else {
                p.put("LOCATOR_CODE", Const.EMPTY_CH);
            }
        }

        srcList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }


    /**
     * 按出库单ID进行全部拣货.<br>
     * 1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).<br>
     * 3.更改箱号纪录的拣货状态(PICK_STATE).<br>
     * 4.更改该出库单主表的拣货状态(PICK_STATE).
     *
     * @param pd
     */
    public void savePickManual1(PageData pd) throws Exception {
        List<String> listStockId = pd.getList("STOCKMGROUT_ID_CHOSE");
        if (listStockId.isEmpty()) {
            return;
        }

        for (String stockId : listStockId) {
            List<PageData> dtlList = findAllDtlById(stockId);
            Map<String, List<PageData>> prodMap = genProdMap(dtlList);
            optSavelPickDtl(prodMap, stockId);
        }
    }

    /**
     * 按出库单明细ID进行单条纪录拣货.<br>
     * 1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).<br>
     * 3.更改箱号纪录的拣货状态(PICK_STATE).<br>
     * 4.更改该出库单主表的拣货状态(PICK_STATE).
     *
     * @param pd
     */
    public void savePickManual2(PageData pd) throws Exception {
        List<String> listStockId = pd.getList("STOCKMGROUT_ID_CHOSE");
        if (listStockId.isEmpty()) {
            return;
        }

        List<PageData> dtlByDtlId = findDtlByDtlId(listStockId);
        //构建为出库单id，对应需要拣货的明细
        Map<String, List<PageData>> listMap = genStockIdMap(dtlByDtlId);

        for (Map.Entry<String, List<PageData>> ent : listMap.entrySet()) {
            Map<String, List<PageData>> prodMap = genProdMap(ent.getValue());
            optSavelPickDtl(prodMap, ent.getKey());
        }
    }

    /**
     * SPREA 客户利用外箱进行拣货
     * 按出库单明细ID进行单条纪录拣货.<br>
     * 1.根据出库单ID、外箱号找到该单据已经分配的纪录;<br>
     * 2.更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).<br>
     * 3.更改箱号纪录的拣货状态(PICK_STATE).<br>
     * 4.更改该出库单主表的拣货状态(PICK_STATE).
     *
     * @param pd
     */
    public void savePickManualSprea(PageData pd) throws Exception {

        List<PageData> spreaList = findListBySprea(pd);
        //构建为出库单id，对应需要拣货的明细
        Map<String, List<PageData>> listMap = genStockIdMap(spreaList);

        for (Map.Entry<String, List<PageData>> ent : listMap.entrySet()) {
            Map<String, List<PageData>> prodMap = genProdMap(ent.getValue());
            optSavelPickDtlSprea(prodMap, ent.getKey(), pd);
        }
    }

    //根据批次属性拣货
    public void savePickByBatch(PageData pd) throws Exception {
        pd.put("BOX_NO",Const.EMPTY_CH);
        List<PageData> spreaList = findListBySprea(pd);
        //构建为出库单id，对应需要拣货的明细
        Map<String, List<PageData>> listMap = genStockIdMap(spreaList);

        for (Map.Entry<String, List<PageData>> ent : listMap.entrySet()) {
            Map<String, List<PageData>> prodMap = genProdMap(ent.getValue());
            optSavePickDtlProperties(prodMap, ent.getKey(), pd);
        }
    }

    /**
     * 拆箱拣货.<br>
     * 按出库单明细ID进行单条纪录拣货.<br>
     * 1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).<br>
     * 3.更改箱号纪录的拣货状态(PICK_STATE).<br>
     * 4.更改该出库单主表的拣货状态(PICK_STATE).
     *
     * @param pd
     */
    public void savePickManual3(PageData pd) throws Exception {
        //1 单头拆箱拣货，2是单身明细拆箱拣货
        String opt_even = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_1.equals(opt_even)) {
            breakBox(pd);
        } else if (Const.OPT_EVEN_2.equals(opt_even)) {
            breakBoxDtl(pd);
        }

    }

    //主表拆箱拣货
    private void breakBox(PageData pd) throws Exception {
        List<String> listStockId = pd.getList("STOCKMGROUT_IDS");
        if (listStockId.isEmpty()) {
            return;
        }

        //新的需要拆分的箱号格式为:boxid-新的箱号，boxid-新的箱号
        String NEW_BOX_LIST = pd.getString("NEW_BOX_LIST");
        //将箱号ID-新箱号 格式的字符串构建为，map(箱号id,新箱号)
        Map<String, String> idBoxMap = genIdBoxMap(NEW_BOX_LIST);
        //根据主表ID找到所有需要拣货的明细
        List<PageData> dtlByDtlId = findDtlByPId(pd.getList("STOCKMGROUT_IDS"));
        // List<PageData> dtlByDtlId = findDtlByDtlId(listStockId);
        //构建为按出库出库单id分组，对应需要拣货的明细
        Map<String, List<PageData>> listMap = genStockIdMap(dtlByDtlId);

        for (Map.Entry<String, List<PageData>> ent : listMap.entrySet()) {
            Map<String, List<PageData>> prodMap = genProdMap(ent.getValue());
            optSavelPickDtlBreakBox(prodMap, idBoxMap, ent.getKey());
        }
    }

    //明细拆箱拣货
    private void breakBoxDtl(PageData pd) throws Exception {
        List<String> listStockId = pd.getList("STOCKMGROUT_ID_CHOSE");
        if (listStockId.isEmpty()) {
            return;
        }

        //新的需要拆分的箱号格式为:boxid-新的箱号，boxid-新的箱号
        String NEW_BOX_LIST = pd.getString("NEW_BOX_LIST");
        //将箱号ID-新箱号 格式的字符串构建为，map(箱号id,新箱号)
        Map<String, String> idBoxMap = genIdBoxMap(NEW_BOX_LIST);
        //根据主表ID找到所有需要拣货的明细
        List<PageData> dtlByDtlId = findDtlByPId(pd.getList("STOCKMGROUT_IDS"));
        // List<PageData> dtlByDtlId = findDtlByDtlId(listStockId);
        //构建为按出库出库单id分组，对应需要拣货的明细
        Map<String, List<PageData>> listMap = genStockIdMap(dtlByDtlId, listStockId);

        for (Map.Entry<String, List<PageData>> ent : listMap.entrySet()) {
            Map<String, List<PageData>> prodMap = genProdMap(ent.getValue());
            optSavelPickDtlBreakBox(prodMap, idBoxMap, ent.getKey());
        }
    }

    /**
     * 按出库单ID进行全部取消拣货.<br>
     * 1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).<br>
     * 3.更改箱号纪录的拣货状态(PICK_STATE).<br>
     * 4.更改该出库单主表的拣货状态(PICK_STATE).
     *
     * @param pd
     */
    public void savePickCacel1(PageData pd) throws Exception {

        List<String> listStockId = pd.getList("STOCKMGROUT_ID_CHOSE");
        if (listStockId.isEmpty()) {
            return;
        }

        for (String stockId : listStockId) {
            List<PageData> dtlList = findAllDtlById(stockId);
            Map<String, List<PageData>> prodMap = genProdMap(dtlList);
            optCacelPickDtl(prodMap, stockId);
        }
    }

    /**
     * 按出库单明细ID进行单条纪录拣货.<br>
     * 1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).<br>
     * 3.更改箱号纪录的拣货状态(PICK_STATE).<br>
     * 4.更改该出库单主表的拣货状态(PICK_STATE).
     *
     * @param pd
     */
    public void savePickCacel2(PageData pd) throws Exception {
        List<String> listStockId = pd.getList("STOCKMGROUT_ID_CHOSE");
        if (listStockId.isEmpty()) {
            return;
        }

        List<PageData> dtlByDtlId = findDtlByDtlId(listStockId);
        //构建为出库单id，对应需要拣货的明细
        Map<String, List<PageData>> listMap = genStockIdMap(dtlByDtlId);

        for (Map.Entry<String, List<PageData>> ent : listMap.entrySet()) {
            Map<String, List<PageData>> prodMap = genProdMap(ent.getValue());
            optCacelPickDtl(prodMap, ent.getKey());
        }
    }


    //按id更新拣货
    private void optSavelPickDtl(Map<String, List<PageData>> map, String stockId) throws Exception {
        boolean updateFlg = false;
        for (Map.Entry<String, List<PageData>> ent : map.entrySet()) {
            List<PageData> dtlList = ent.getValue();
            if (dtlList.size() == 1) {
                continue;
            }
            //升序
            dtlList.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
            PageData srcPd = dtlList.get(0);
            //第一个是原始纪录，从第二个开始循环
            List<String> dtlId = new ArrayList<>();
            int pickEa = 0;
            for (int i = 1, len = dtlList.size(); i < len; i++) {
                PageData dtlPd = dtlList.get(i);
                if (WmsEnum.PickState.ALL.getItemKey().equals(dtlPd.getString("PICK_STATE"))) {
                    continue;
                }
                pickEa += dtlPd.getInteger("ASSIGNED_EA");
                dtlId.add(dtlPd.getString("STOCKDTLMGROUT_ID"));
            }
            if (pickEa == 0) {
                continue;
            }
            srcPd.put("PICK_EA", srcPd.getInteger("PICK_EA") + pickEa);
            //更改原始纪录的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifySrcPickState(srcPd);
            //更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifyStockPickStateALL(dtlId, stockId);
            //更改箱号纪录的拣货状态(PICK_STATE).
            modifyBoxPickStateALL(dtlId);
            updateFlg = true;
        }
        //根据入库单ID，更新单头拣货状态
        if (updateFlg) {
            updatePickState(stockId);
        }
    }

    //按id更新拣货
    private void optSavelPickDtlSprea(Map<String, List<PageData>> map, String stockId, PageData pageData) throws Exception {
        boolean updateFlg = false;
        for (Map.Entry<String, List<PageData>> ent : map.entrySet()) {
            List<PageData> dtlList = ent.getValue();
            if (dtlList.size() == 1) {
                continue;
            }
            //升序
            dtlList.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
            PageData srcPd = dtlList.get(0);
            //第一个是原始纪录，从第二个开始循环
            List<String> dtlId = new ArrayList<>();
            int pickEa = 0;
            for (int i = 1, len = dtlList.size(); i < len; i++) {
                PageData dtlPd = dtlList.get(i);
                if (WmsEnum.PickState.ALL.getItemKey().equals(dtlPd.getString("PICK_STATE"))) {
                    continue;
                }
                pickEa += dtlPd.getInteger("ASSIGNED_EA");
                dtlId.add(dtlPd.getString("STOCKDTLMGROUT_ID"));
            }
            if (pickEa == 0) {
                continue;
            }
            srcPd.put("PICK_EA", srcPd.getInteger("PICK_EA") + pickEa);
            //更改原始纪录的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifySrcPickState(srcPd);
            //更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifyStockPickStateALL(dtlId, stockId);
            //更改箱号纪录的拣货状态(PICK_STATE).

            modifyBoxPickStatePage(dtlId, pageData);
            updateFlg = true;
        }
        //根据入库单ID，更新单头拣货状态
        if (updateFlg) {
            updatePickState(stockId);
        }
    }

    //按属性更新拣货
    private void optSavePickDtlProperties(Map<String, List<PageData>> map, String stockId, PageData pageData) throws Exception {
        boolean updateFlg = false;
        for (Map.Entry<String, List<PageData>> ent : map.entrySet()) {
            List<PageData> dtlList = ent.getValue();
            if (dtlList.size() == 1) {
                continue;
            }
            //升序
            dtlList.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
            PageData srcPd = dtlList.get(0);
            //第一个是原始纪录，从第二个开始循环
            List<String> dtlId = new ArrayList<>();
            int pickEa = 0;
            for (int i = 1, len = dtlList.size(); i < len; i++) {
                if (pickEa != 0) {
                    break;
                }
                PageData dtlPd = dtlList.get(i);
                if (WmsEnum.PickState.ALL.getItemKey().equals(dtlPd.getString("PICK_STATE"))) {
                    continue;
                }
                if (!isSameProperties(dtlPd,pageData)) {
                    continue;
                }

                pickEa += dtlPd.getInteger("ASSIGNED_EA");
                dtlId.add(dtlPd.getString("STOCKDTLMGROUT_ID"));
            }
            if (pickEa == 0) {
                continue;
            }
            srcPd.put("PICK_EA", srcPd.getInteger("PICK_EA") + pickEa);
            //更改原始纪录的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifySrcPickState(srcPd);
            //更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifyStockPickStateALL(dtlId, stockId);
            //更改箱号纪录的拣货状态(PICK_STATE).

            modifyBoxPickStatePage(dtlId, pageData);
            updateFlg = true;
        }
        //根据入库单ID，更新单头拣货状态
        if (updateFlg) {
            updatePickState(stockId);
        }
    }

    private boolean isSameProperties(PageData dbPd, PageData pd) {
        int eaNum = dbPd.getInteger("EA_NUM");
        int qty = pd.getInteger("QTY");
        String prodDb = StringUtil.isBlank(dbPd.getString("PRODUCT_ID")) ? Const.EMPTY_CH : dbPd.getString("PRODUCT_ID");
        String binDb = StringUtil.isBlank(dbPd.getString("BIN_CODE")) ? Const.EMPTY_CH : dbPd.getString("BIN_CODE");
        String dcDb = StringUtil.isBlank(dbPd.getString("DATE_CODE")) ? Const.EMPTY_CH : dbPd.getString("DATE_CODE");
        String lotDb = StringUtil.isBlank(dbPd.getString("LOT_NO")) ? Const.EMPTY_CH : dbPd.getString("LOT_NO");

        String prod = StringUtil.isBlank(pd.getString("PRODUCT_ID")) ? Const.EMPTY_CH : pd.getString("PRODUCT_ID");
        String bin = StringUtil.isBlank(pd.getString("BIN_CODE")) ? Const.EMPTY_CH : pd.getString("BIN_CODE");
        String dc = StringUtil.isBlank(pd.getString("DATE_CODE")) ? Const.EMPTY_CH : pd.getString("DATE_CODE");
        String lot = StringUtil.isBlank(pd.getString("LOT_NO")) ? Const.EMPTY_CH : pd.getString("LOT_NO");

        return eaNum == qty&& prodDb.equalsIgnoreCase(prod) && binDb.equalsIgnoreCase(bin) && dcDb.equalsIgnoreCase(dc) && lotDb.equalsIgnoreCase(lot);
    }
    //拆箱拣货
    private void optSavelPickDtlBreakBox(Map<String, List<PageData>> map, Map<String, String> idBoxMap, String stockId) throws Exception {

        boolean updateFlg = false;
        for (Map.Entry<String, List<PageData>> ent : map.entrySet()) {
            List<PageData> dtlList = ent.getValue();
            if (dtlList.size() == 1) {
                continue;
            }
            //升序
            dtlList.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
            PageData srcPd = dtlList.get(0);
            //第一个是原始纪录，从第二个开始循环
            List<String> dtlId = new ArrayList<>();
            int pickEa = 0;
            for (int i = 1, len = dtlList.size(); i < len; i++) {
                PageData dtlPd = dtlList.get(i);
                if (WmsEnum.PickState.ALL.getItemKey().equals(dtlPd.getString("PICK_STATE"))) {
                    continue;
                }
                String STOCKDTLMGROUT_ID = dtlPd.getString("STOCKDTLMGROUT_ID");
                //需要拆箱的新箱号
                String newBoxNo = idBoxMap.get(STOCKDTLMGROUT_ID);
                //如果能获取到新箱号，则说明需要拆箱
                if (StringUtil.isNotEmpty(newBoxNo)) {
                    //新建一笔箱号纪录、将明细的纪录修改为新的纪录
                    String newId = UuidUtil.get32UUID();
                    dtlPd.put("OLD_BOX_ID", STOCKDTLMGROUT_ID);
                    //PageData upPd = new PageData();
                    PageData upPd = new PageData(findOldBoxPd(dtlPd));
                    int srcEaNum = upPd.getInteger("EA_NUM");

                    upPd.put("NEW_STOCKDTLMGROUT_ID", newId);
                    upPd.put("OLD_STOCKDTLMGROUT_ID", STOCKDTLMGROUT_ID);
                    upPd.put("BOX_ID", newId);
                    upPd.put("OLD_BOX_ID", STOCKDTLMGROUT_ID);
                    upPd.put("BOX_NO", newBoxNo);
                    upPd.put("CREATE_EMP", SessionUtil.getCurUserName());    //创建人
                    upPd.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间
                    upPd.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
                    upPd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
                    upPd.put("EA_NUM", upPd.getInteger("ASSIGNED_STOCK_NUM"));    //修改时间
                    upPd.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.ALL.getItemKey());    //修改时间
                    upPd.put("PICK_STATE", WmsEnum.PickState.ALL.getItemKey());    //修改时间

                    //新建箱号纪录
                    saveNewBox(upPd);
                    //更新原先的箱号纪录
                    upPd.put("EA_NUM", srcEaNum - upPd.getInteger("ASSIGNED_STOCK_NUM"));
                    upPd.put("PICK_TM", Tools.date2Str(new Date()));    //修改时间
                    editSrcBox(upPd);
                    //管理新的箱号纪录到出库明细中
                    upPd.put("NEW_BOX_NO", newBoxNo);
                    upPd.put("STOCKMGROUT_ID", stockId);
                    editSrcDtl2NewDtl(upPd);
                    dtlId.add(newId);
                } else {
                    dtlId.add(STOCKDTLMGROUT_ID);
                }
                pickEa += dtlPd.getInteger("ASSIGNED_EA");
            }
            if (pickEa == 0) {
                continue;
            }
            srcPd.put("PICK_EA", srcPd.getInteger("PICK_EA") + pickEa);
            //更改原始纪录的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifySrcPickState(srcPd);
            //更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifyStockPickStateALL(dtlId, stockId);
            //更改箱号纪录的拣货状态(PICK_STATE).
            modifyBoxPickStateALL(dtlId);
            updateFlg = true;
        }
        //根据入库单ID，更新单头拣货状态
        if (updateFlg) {
            updatePickState(stockId);
        }
    }


    //按id更新取消拣货
    private void optCacelPickDtl(Map<String, List<PageData>> map, String stockId) throws Exception {
        boolean updateFlg = false;
        for (Map.Entry<String, List<PageData>> ent : map.entrySet()) {
            List<PageData> dtlList = ent.getValue();
            if (dtlList.size() == 1) {
                continue;
            }

            //升序
            dtlList.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
            PageData srcPd = dtlList.get(0);

            List<String> dtlId = new ArrayList<>();
            int pickEa = 0;
            for (int i = 1, len = dtlList.size(); i < len; i++) {
                PageData dtlPd = dtlList.get(i);
                if (WmsEnum.PickState.NONE.getItemKey().equals(dtlPd.getString("PICK_STATE"))) {
                    continue;
                }

                pickEa += dtlPd.getInteger("ASSIGNED_EA");
                dtlId.add(dtlPd.getString("STOCKDTLMGROUT_ID"));
            }
            if (pickEa == 0) {
                continue;
            }

            int newPickEa = srcPd.getInteger("PICK_EA") - pickEa;
            newPickEa = newPickEa <= 0 ? 0 : newPickEa;
            srcPd.put("PICK_EA", newPickEa);

            //更改原始纪录的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifySrcPickState(srcPd);
            //更改明细的拣货状态、拣货数量(PICK_STATE PICK_EA).
            modifyStockPickStateNONE(dtlId, stockId);
            //更改箱号纪录的拣货状态(PICK_STATE).
            modifyBoxPickStateNONE(dtlId);
            updateFlg = true;
        }
        //根据入库单ID，更新单头拣货状态
        if (updateFlg) {
            updatePickState(stockId);
        }
    }


    /**
     * 根据出库单ID去更新出库单表头的拣货状态.逻辑:<br>
     * 1、如果所有的已拣货EA数都是0，则是：未拣货;<br>
     * 2、如果所有的已拣货EA数都大于等于期望EA的话，则是:全部拣货;<br>
     * 3、其他情况则是:部分拣货
     *
     * @param stockmgroutId 出库单ID
     */
    private void updatePickState(String stockmgroutId) {
        try {
            PageData pd = new PageData();
            pd.put("STOCKMGROUT_ID", stockmgroutId);
            // EA_EA, ASSIGNED_EA
            List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findDtlOutList", pd);

            int dtlCnt = list.size();
            int eaCnt = 0;
            int pickEACnt = 0;
            for (PageData p : list) {
                //期望EA、已分配EA
                int EA = p.getInteger("EA_EA");
                int pickEA = p.getInteger("PICK_EA");
                if (pickEA == 0) {
                    pickEACnt += 1;
                }

                if (pickEA >= EA) {
                    eaCnt += 1;
                }
            }

            String assignState = WmsEnum.PickState.PART.getItemKey();
            if (pickEACnt == dtlCnt) {
                assignState = WmsEnum.PickState.NONE.getItemKey();
            } else if (eaCnt == dtlCnt) {
                assignState = WmsEnum.PickState.ALL.getItemKey();
            }

            pd.put("PICK_STATE", assignState);
            pd.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
            pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
            //修改出库单表头的库存分配状态
            dao.update("PickOutMapper.editHeadStockPickInfo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将得到的明细按产品分组
    private Map<String, List<PageData>> genProdMap(List<PageData> list) {
        Map<String, List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            String prodId = p.getString("PRODUCT_ID");

            List<PageData> lst = map.get(prodId);
            if (lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(prodId, lst);
        }
        return map;
    }

    //将得到的明细按出库单ID分组
    private Map<String, List<PageData>> genStockIdMap(List<PageData> list) {
        Map<String, List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            String id = p.getString("STOCKMGROUT_ID");

            List<PageData> lst = map.get(id);
            if (lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(id, lst);
        }
        return map;
    }

    //将得到的明细按出库单ID分组
    private Map<String, List<PageData>> genStockIdMap(List<PageData> list, List<String> listId) {
        Map<String, List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            String id = p.getString("STOCKMGROUT_ID");
            //需要原始纪录以及需要进行拣货的纪录
            if (Const.ASSIGNED_FLG_NO.equals(p.getString("ASSIGNED_FLG")) || listId.contains(p.getString("STOCKDTLMGROUT_ID"))) {
                List<PageData> lst = map.get(id);
                if (lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(p);
                map.put(id, lst);
            }
        }
        return map;
    }

    public List<PageData> findBeakBoxDtl(PageData pd) throws Exception {
        List<String> list = pd.getList("STOCKMGROUT_ID_CHOSE");
        String STOCKMGROUT_ID = pd.getString("STOCKMGROUT_IDS");
        Page page = new Page();
        PageData pd1 = new PageData();
        pd1.put("IDS1", list);
        pd1.put("STOCKMGROUT_ID", STOCKMGROUT_ID);
        page.setPd(pd1);
        List<PageData> rs = (List<PageData>) dao.findForList("PickOutMapper.findBreakBoxList", page);

        List<PageData> rslst = new ArrayList<>();
        if (rs == null || rs.isEmpty()) {
            return rslst;
        }
        for (PageData p : rs) {
            if (p.getInteger("EA_NUM") - p.getInteger("ASSIGNED_STOCK_NUM") > 0 && p.getInteger("ASSIGNED_STOCK_NUM") != 0) {
                rslst.add(p);
            }
        }
        return rslst;
    }


    public List<PageData> findBeakBoxDtlByPid(PageData pd) throws Exception {
        List<String> list = pd.getList("STOCKMGROUT_ID_CHOSE");
        List<PageData> rs = (List<PageData>) dao.findForList("PickOutMapper.findBeakBoxDtlByPid", list);

        List<PageData> rslst = new ArrayList<>();
        if (rs == null || rs.isEmpty()) {
            return rslst;
        }
        for (PageData p : rs) {
            if (p.getInteger("EA_NUM") - p.getInteger("ASSIGNED_STOCK_NUM") > 0 && p.getInteger("ASSIGNED_STOCK_NUM") != 0) {
                rslst.add(p);
            }
        }
        return rslst;
    }

    public PageData findOutStock(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findOutStockByNo", pd);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    public List<PageData> findListBySprea(PageData pd) throws Exception {
        String box_no = pd.getString("BOX_NO");
        String pickPros = pd.getString("PICK_PROS");
        //属性拣货
        if (Const.DEL_YES.equals(pickPros)) {
            return findByProperties(pd);
        } else if (StringUtil.isEmpty(box_no)) {
            //根据外箱号的信息区拣货
            return findListByBigBoxForPick(pd);
        } else {
            //内箱号拣货超找纪录
            List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findListByBoxForPick", pd);
            if (list == null || list.isEmpty()) {
                return new ArrayList<>();
            }
            List<PageData> rs = new ArrayList<>();
            for (PageData p : list) {
                if (p.getInteger("ASSIGNED_FLG") == 0) {
                    rs.add(p);
                    continue;
                }
                String boxNo = p.getString("BOX_NO") == null ? "" : p.getString("BOX_NO");
                if (boxNo.equals(pd.getString("BOX_NO"))) {
                    rs.add(p);
                }
            }
            return rs;
        }

    }


    public boolean isAllAssigned2Me(List<PageData> boxAssignedList ,PageData pd) {
        if (boxAssignedList == null || boxAssignedList.isEmpty()) {
            return false;
        }
        String stockmgroutId = pd.getString("STOCKMGROUT_ID");
        for (PageData p : boxAssignedList) {
            if (!stockmgroutId.equals(p.getString("BOX_ID"))) {
                return false;
            }
        }
        return true;
    }

    public List<PageData> findBigBoxAssignedList(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("PickOutMapper.findBigBoxAssignedList", pd);
    }

    public List<PageData> findListByBigBox(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("PickOutMapper.findListByBigBox", pd);
    }

    /**
     * 根据外箱号获取该外箱号的所有记录
     * @param pd 外箱号
     * @return 箱号列表
     * @throws Exception 异常
     */
    public Map<String,PageData> findBigBoxList(PageData pd) throws Exception {
        return groupByBoxId((List<PageData>) dao.findForList("PickOutMapper.findBigBoxList", pd));
    }

    public Map<String,PageData> findAssignedBoxList(PageData pd) throws Exception {
        return groupByBoxId((List<PageData>) dao.findForList("PickOutMapper.findAssignedBoxList", pd));
    }

    private Map<String,PageData> groupByBoxId(List<PageData> list) {
        Map<String,PageData> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            map.put(p.getString("BOX_ID"),p);
        }
        return map;
    }


    public boolean isExistsBox(Map<String, PageData> boxIdMap, Map<String, PageData> assignedBoxIdMap) {
        if (boxIdMap == null || boxIdMap.isEmpty() || assignedBoxIdMap == null
                || assignedBoxIdMap.isEmpty()) {
            return false;
        }

        for (Map.Entry<String, PageData> entry : boxIdMap.entrySet()) {
            PageData p = assignedBoxIdMap.get(entry.getKey());
            if (p != null && StringUtil.isNotEmpty(p.getString("BOX_ID"))) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllAssigned(Map<String, PageData> boxIdMap, Map<String, PageData> assignedBoxIdMap) {
        if (boxIdMap == null || boxIdMap.isEmpty() || assignedBoxIdMap == null
                || assignedBoxIdMap.isEmpty()) {
            return false;
        }

        for (Map.Entry<String, PageData> entry : boxIdMap.entrySet()) {
            PageData p = assignedBoxIdMap.get(entry.getKey());
            if (p == null || StringUtil.isEmpty(p.getString("BOX_ID"))) {
                return false;
            }
            int boxNum = entry.getValue().getInteger("EA_NUM");
            int assignedEa = p.getInteger("ASSIGNED_EA");
            if (boxNum != assignedEa) {
                return false;
            }
        }
        return true;
    }

    public Set<String> turn2IdSet(List<PageData> list, String stockMgrOutId) {
        return list.stream().map(p -> p.getString("STOCKMGROUT_ID")).collect(Collectors.toSet());
    }

    public boolean isAllBelongMe(Set<String> set, String stockMgrOutId) {
        //如果包含该入库单并且
        boolean isExist = set.contains(stockMgrOutId);
        return isExist && set.size() == 1;
    }

    public boolean isAllAssigned(List<PageData> list) {
        for (PageData p : list) {
            if (StringUtil.isEmpty(p.getString("STOCKMGROUT_ID"))) {
                return false;
            }
        }
        return true;
    }

    public PageData findListByBox(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findListByBox", pd);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    public String findByCustomerId(PageData pd) throws Exception {
        PageData rs = (PageData) dao.findForObject("PickOutMapper.findCustomerId", pd);
        return rs.getString("CUSTOMER_ID");
    }

    public List<PageData> findByProperties(PageData pd) throws Exception {
        List<PageData> list = findListByPropertiesForPick(pd);
        List<PageData> lst = new ArrayList<>();
        for (PageData p : list) {
            if (p.getInteger("ASSIGNED_FLG") == 0) {
                lst.add(p);
                continue;
            }
            if (WmsEnum.PickState.ALL.getItemKey().equals(p.getString("PICK_STATE"))) {
                continue;
            }
            if (isSameProperties(p,pd)) {
                lst.add(p);
            }
            if (lst.size() == 2) {
                return lst;
            }
        }
        return lst;
    }

    private List<PageData> findListByBigBoxForPick(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findListByBigBoxForPick", pd);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<PageData> lst = new ArrayList<>();
        String bigBoxNo = pd.getString("BIG_BOX_NO");
        for (PageData p : list) {
            if (p.getInteger("ASSIGNED_FLG") == 0) {
                lst.add(p);
                continue;
            }
            String bigBoxNo2 = p.getString("BIG_BOX_NO") == null ? "" : p.getString("BIG_BOX_NO");
            if (bigBoxNo2.equals(bigBoxNo)) {
                lst.add(p);
            }
        }
        return lst;
    }

    private List<PageData> findListByPropertiesForPick(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findListByPropertiesForPick", pd);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }

    //根据出库单ID找到该出库单的所有明细
    private List<PageData> findAllDtlById(String id) throws Exception {
        PageData pd = new PageData();
        pd.put("STOCKMGROUT_ID", id);
        return (List<PageData>) dao.findForList("PickOutMapper.findDtlPickPdById", pd);
    }

    /**
     * 根据出库单明细ID找到该出库单的所有(已分配未拣货)明细，
     *
     * @param list 明细Id列表
     * @return 带原始记录的列表
     * @throws Exception 异常
     * @since 2017-5-7 替换该低效sql
     */
    @Deprecated
    private List<PageData> findDtlByDtlId_bak(List<String> list) throws Exception {
        return (List<PageData>) dao.findForList("PickOutMapper.findDtlByDtlId", list);
    }

    /**
     * 根据出库单明细ID找到该出库单的所有(已分配未拣货)明细，
     *
     * @param list 明细Id列表
     * @return 带原始记录的列表
     * @throws Exception 异常
     * @since 2017-5-7
     */
    private List<PageData> findDtlByDtlId(List<String> list) throws Exception {
        List<PageData> pidProdList = (List<PageData>) dao.findForList("PickOutMapper.findPidAndProdByDtlId", list);
        return (List<PageData>) dao.findForList("PickOutMapper.findDtlByPidProd", pidProdList);
    }


    //根据出库单主表ID找到该出库单的所有(已分配未拣货)明细
    private List<PageData> findDtlByPId(List<String> list) throws Exception {
        return (List<PageData>) dao.findForList("PickOutMapper.findDtlByPId", list);
    }


    //更改拣货状态为拣货141，数量等于分配数量
    private void modifyStockPickStateALL(List<String> idList, String stockId) throws Exception {
        Page page = new Page();
        PageData pd = new PageData();
        pd.put("STOCKMGROUT_ID", stockId);    //修改时间
        pd.put("IDS", idList);
        page.setPd(pd);
        dao.update("PickOutMapper.editDtlStockPickStateALL", page);
    }

    //更改拣货状态为拣货139，数量等于0
    private void modifyStockPickStateNONE(List<String> idList, String stockId) throws Exception {
        Page page = new Page();
        PageData pd = new PageData();
        pd.put("STOCKMGROUT_ID", stockId);    //修改时间
        pd.put("IDS", idList);
        page.setPd(pd);
        dao.update("PickOutMapper.editDtlStockPickStateNONE", page);
    }

    /**
     * 查询箱号对应的ID已经在出库单上的拣货数量
     *
     * @param idList 箱号ID列表
     * @return (箱ID, 共拣货的数量)
     * @throws Exception 异常
     */
    private Map<String, Integer> findPickEaByid(List<String> idList) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findPickEaByid", idList);
        return groupByDtlId(list);
    }

    /**
     * 查询箱号ID对应的拣货数量
     *
     * @param idList 箱号ID列表
     * @return (箱ID, 共拣货的数量)
     * @throws Exception 异常
     */
    private Map<String, Integer> findBoxEaByid(List<String> idList) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findBoxEaByid", idList);
        return groupByDtlId(list);
    }

    /**
     * 查询箱号ID对应的拆箱前的旧箱号实体
     *
     * @param idList 箱号ID列表
     * @return (箱ID, 共拣货的数量)
     * @throws Exception 异常
     */
    private Map<String, PageData> findOldBoxPdById(List<String> idList) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("PickOutMapper.findOldBoxPdById", idList);
        Map<String, PageData> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData pd : list) {
            map.put(pd.getString("SRC_BOX_ID"), pd);
        }
        return map;
    }


    private Map<String, PageData> findBreakBoxPdById(List<java.lang.String> idList) throws Exception {
        List<com.hw.util.PageData> list = (List<com.hw.util.PageData>) dao.findForList("PickOutMapper.findBreakBoxPdById", idList);
        Map<String, PageData> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (com.hw.util.PageData pd : list) {
            map.put(pd.getString("BOX_ID"), pd);
        }
        return map;
    }

    private Map<String, Integer> groupByDtlId(List<PageData> list) {
        Map<String, Integer> map = new HashMap<>();
        for (PageData p : list) {
            map.put(p.getString("BOX_ID"), p.getInteger("PICK_EA"));
        }
        return map;
    }

    //更改拣货状态为拣货141
    private void modifyBoxPickStateALL(List<String> idList) throws Exception {
        Map<String, Integer> pickEaMap = findPickEaByid(idList);
        Map<String, Integer> boxEaMap = findBoxEaByid(idList);

        for (Map.Entry<String, Integer> entry : boxEaMap.entrySet()) {
            restoreCurBoxPickState(pickEaMap, entry);
        }
    }

    /**
     * 还原箱的状态.<br>
     * 1、需要还原到旧箱逻辑:<br>
     * 1.0、当前箱不能有再拆箱记录(正常情况下不存在);
     * 1.1、将拆分后的箱EA还原回旧箱，并修改旧箱的分配状态、分配数量、拣货状态;<br>
     * 1.2、删除当前箱记录;<br>
     * 1.3、将旧箱号重新更新回该出库单,更新字段，<br>
     * 单身：STOCKDTLMGROUT_ID,PICK_STATE,PICK_EA,BOX_NO.<br>
     * 单头：PICK_STATE
     * 2、没有对应旧箱逻辑:<br>
     * 2.0 修改当前箱的拣货状态.
     *
     * @param idList 需要还原的箱号ID列表
     * @throws Exception 异常
     */
    private void restoreBoxPickState(List<String> idList) throws Exception {
        //当前箱号ID对应的拣货数量
        Map<String, Integer> boxEaMap = findBoxEaByid(idList);
        //当前箱号ID对应的拆箱前的旧箱号实体(箱号ID,旧箱实体)
        Map<String, PageData> oldBoxPdMap = findOldBoxPdById(idList);
        //当前箱对应其拆箱的记录(箱号ID,新拆箱实体)
        Map<String, PageData> breakBoxPdById = findBreakBoxPdById(idList);

        for (Map.Entry<String, Integer> entry : boxEaMap.entrySet()) {
            PageData oldBoxPd = oldBoxPdMap.get(entry.getKey());
            if (isHadOldAndNoNewBox(oldBoxPd, breakBoxPdById.get(entry.getKey()))) {
                Map<String, Integer> pickEaMap = findPickEaByid(new ArrayList<>(oldBoxPdMap.keySet()));
                Integer integer = pickEaMap.get(entry.getKey());
                int pickEa = integer == null ? 0 : integer;
                restoreCurAndOldBoxPickState(entry, oldBoxPd, pickEa);
            } else {
                //无旧箱处理
                //当前箱号对应的ID已经在出库单上的拣货数量(包含分配到其他出库单的)
                Map<String, Integer> pickEaMap = findPickEaByid(idList);
                restoreCurBoxPickState(pickEaMap, entry);
            }
        }
    }

    /**
     * 还原当前箱到旧箱上
     *
     * @param entry    (当前箱ID，箱EA)
     * @param oldBoxPd 旧箱实体
     * @param pickEa   该箱已经拣货数量
     * @throws Exception 异常
     */
    private void restoreCurAndOldBoxPickState(Map.Entry<String, Integer> entry, PageData oldBoxPd, int pickEa) throws Exception {
        PageData boxPd = new PageData();
        int oldBoxTotalEaNum = oldBoxPd.getInteger("EA_NUM") + entry.getValue();
        Integer oldBoxAssignedNum = oldBoxPd.getInteger("ASSIGNED_STOCK_NUM") + entry.getValue();

        boxPd.put("EA_NUM", oldBoxTotalEaNum);
        boxPd.put("BOX_ID", oldBoxPd.getString("BOX_ID"));
        boxPd.put("BOX_NO", oldBoxPd.getString("BOX_NO"));
        //计算分配状态、拣货状态
        String assignedStockState;
        if (oldBoxAssignedNum == 0) {
            assignedStockState = WmsEnum.AssignedState.NONE.getItemKey();
        } else if (oldBoxTotalEaNum > oldBoxAssignedNum) {
            assignedStockState = WmsEnum.AssignedState.PART.getItemKey();
        } else {
            assignedStockState = WmsEnum.AssignedState.ALL.getItemKey();
        }

        String pickState = WmsEnum.PickState.ALL.getItemKey();
        if (pickEa == 0) {
            pickState = WmsEnum.PickState.NONE.getItemKey();
        } else if (pickEa > 0 && pickEa < oldBoxTotalEaNum) {
            pickState = WmsEnum.PickState.PART.getItemKey();
        }

        boxPd.put("ASSIGNED_STOCK_STATE", assignedStockState);
        boxPd.put("ASSIGNED_STOCK_NUM", oldBoxAssignedNum);
        boxPd.put("PICK_STATE", pickState);
        boxPd.put("MODIFY_TM", Tools.date2Str(new Date()));
        boxPd.put("MODIFY_EMP", SessionUtil.getCurUserName());

        boxPd.put("STOCKDTLMGROUT_ID", entry.getKey());

        dao.update("PickOutMapper.editDtlBoxInfoCancelPick", boxPd);
        dao.update("PickOutMapper.editDtlBoxAssignedPickState", boxPd);
        dao.delete("PickOutMapper.delBoxById", boxPd);
    }

    /**
     * 是否存在旧的箱,且无新拆箱
     *
     * @param oldPd 根据ID得到的旧箱实体
     * @param oldPd 根据ID得到的新拆箱实体
     * @return 存在有旧的箱且该箱对应无新拆箱，则true,否则false
     */
    private boolean isHadOldAndNoNewBox(PageData oldPd, PageData breakPd) {
        return oldPd != null && StringUtil.isNotEmpty(oldPd.getString("BOX_ID"))
                && (breakPd == null || (breakPd != null && StringUtil.isEmpty(breakPd.getString("BOX_ID"))));
    }

    /**
     * 还原当前箱的状态，无还远旧箱逻辑
     *
     * @param pickEaMap (箱ID，已拣货EA)
     * @param entry     (箱ID，箱EA)
     * @throws Exception 异常
     */
    private void restoreCurBoxPickState(Map<String, Integer> pickEaMap, Map.Entry<String, Integer> entry) throws Exception {
        PageData pd = new PageData();
        int boxEa = entry.getValue();
        Integer integer = pickEaMap.get(entry.getKey());
        int pickEa = integer == null ? 0 : integer;

        String PICK_STATE = WmsEnum.PickState.ALL.getItemKey();
        if (pickEa == 0) {
            PICK_STATE = WmsEnum.PickState.NONE.getItemKey();
        } else if (pickEa > 0 && pickEa < boxEa) {
            PICK_STATE = WmsEnum.PickState.PART.getItemKey();
        }
        pd.put("PICK_STATE", PICK_STATE);    //PICK_STATE
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        pd.put("BOX_ID", entry.getKey());    //BOX_ID
        dao.update("PickOutMapper.editDtlBoxPickStateById", pd);
    }

    //更改拣货状态为拣货141
    private void modifyBoxPickStatePage(List<String> idList, PageData pageData) throws Exception {

        Map<String, Integer> pickEaMap = findPickEaByid(idList);
        Map<String, Integer> boxEaMap = findBoxEaByid(idList);

        for (Map.Entry<String, Integer> entry : boxEaMap.entrySet()) {
            PageData pd = new PageData();
            int boxEa = entry.getValue();
            Integer integer = pickEaMap.get(entry.getKey());
            int pickEa = integer == null ? 0 : integer;

            String PICK_STATE = WmsEnum.PickState.ALL.getItemKey();
            ;
            if (pickEa == 0) {
                PICK_STATE = WmsEnum.PickState.NONE.getItemKey();
            } else if (pickEa > 0 && pickEa < boxEa) {
                PICK_STATE = WmsEnum.PickState.PART.getItemKey();
            }
            pd.put("BIG_BOX_NO", pageData.getString("BIG_BOX_NO"));
            pd.put("PICK_STATE", PICK_STATE);    //PICK_STATE
            pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
            pd.put("BOX_ID", entry.getKey());    //BOX_ID
            dao.update("PickOutMapper.editDtlBoxPickStateByIdSprea", pd);
        }
    }


    private void modifyBoxPickStateALL_bak(List<String> idList) throws Exception {
        Page page = new Page();
        PageData pd = new PageData();
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        pd.put("IDS", idList);
        page.setPd(pd);
        dao.update("PickOutMapper.editDtlBoxPickStateALL", page);
    }


    /**
     * 更改拣货状态为拣货139,
     *
     * @param idList
     * @throws Exception
     * @since 2017-5-7拆箱还原到旧箱号上去.
     */
    private void modifyBoxPickStateNONE(List<String> idList) throws Exception {
        //2017-5-7之前的逻辑
        //modifyBoxPickStateALL(idList);
        //2017-5-7新逻辑
        restoreBoxPickState(idList);
    }

    private void modifyBoxPickStateNONE_bak(List<String> idList) throws Exception {
        Page page = new Page();
        PageData pd = new PageData();
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        pd.put("IDS", idList);
        page.setPd(pd);
        dao.update("PickOutMapper.editDtlBoxPickStateNONE", page);
    }

    private PageData findOldBoxPd(PageData pd) throws Exception {
        return (PageData) dao.findForObject("PickOutMapper.findOldBoxPd", pd);
    }

    private void saveNewBox(PageData pd) throws Exception {
        dao.save("PickOutMapper.saveNewBox", pd);
    }

    private void editSrcBox(PageData pd) throws Exception {
        dao.update("PickOutMapper.editSrcBox", pd);
    }

    private void editSrcDtl2NewDtl(PageData pd) throws Exception {
        dao.update("PickOutMapper.editSrcDtl2NewDtl", pd);
        dao.update("PickOutMapper.editSrcProsDtl2NewDtl", pd);
    }


    //去除重复的纪录，只显示主表纪录
    private List<PageData> filterById(List<PageData> srcList) {
        List<PageData> rs = new ArrayList<>();
        for (PageData p : srcList) {
            if (!isContain(rs, p)) {
                rs.add(p);
                continue;
            }

            for (PageData p2 : rs) {
                if (p2.getString("STOCKMGROUT_ID").equals(p.getString("STOCKMGROUT_ID"))) {
                    //EA_EA ASSIGNED_EA PICK_EA
                    p2.put("EA_EA", p2.getInteger("EA_EA") + p.getInteger("EA_EA"));
                    p2.put("ASSIGNED_EA", p2.getInteger("ASSIGNED_EA") + p.getInteger("ASSIGNED_EA"));
                    p2.put("PICK_EA", p2.getInteger("PICK_EA") + p.getInteger("PICK_EA"));
                }
            }
        }
        return rs;
    }

    /**
     * 更新原始纪录的拣货状态、拣货数量
     *
     * @param srcPd 非拣货明细
     * @throws Exception e
     */
    private void modifySrcPickState(PageData srcPd) throws Exception {
        int EA_EA = srcPd.getInteger("EA_EA");
        int PICK_EA = srcPd.getInteger("PICK_EA");
        String pickState = WmsEnum.PickState.NONE.getItemKey();
        if (PICK_EA == 0) {
            pickState = WmsEnum.PickState.NONE.getItemKey();
        } else if (EA_EA > PICK_EA) {
            pickState = WmsEnum.PickState.PART.getItemKey();
        } else {
            pickState = WmsEnum.PickState.ALL.getItemKey();
        }
        srcPd.put("PICK_STATE", pickState);
        dao.update("PickOutMapper.editDtlStockPickStateByID", srcPd);
    }

    //判断列表中的是否包含该实体
    private boolean isContain(List<PageData> list, PageData pd) {
        if (list.isEmpty()) {
            return false;
        }
        for (PageData p : list) {
            if (p.getString("STOCKMGROUT_ID").equals(pd.getString("STOCKMGROUT_ID"))) {
                return true;
            }
        }
        return false;
    }

    //将箱号ID-新箱号 格式的字符串构建为，map(箱号id,新箱号)
    private Map<String, String> genIdBoxMap(String str) {
        String[] arr = StringUtils.split(str, Const.SPLIT_COMMA);
        Map<String, String> map = new HashMap<>();
        for (String s : arr) {
            String[] idbox = StringUtils.split(s, Const.SPLIT_LINE);
            map.put(idbox[0], idbox[1]);
        }
        return map;
    }

}
