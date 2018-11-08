package com.hw.service.outstock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.service.property.OrderPropertyService;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-10-27
 */
@Service("assignOutService")
public class AssignOutService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "stockmgroutService")
    private StockMgrOutService stockmgroutService;

    @Resource(name = "orderpropertyService")
    private OrderPropertyService orderpropertyService;

    public List<PageData> list(Page page) throws Exception {
        return stockmgroutService.list(page);
    }

    public void findDtlAssignOutlistPageforLook(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> headlist = (List<PageData>) dao.findForList("AssignOutMapper.findDtlAssignOutHeadLook", page);
        List<PageData> dtllist = (List<PageData>) dao.findForList("AssignOutMapper.findDtlAssignOutDtlLooklist1Page", page);
        if (headlist != null) {
            int totalEa = 0, assignedEa = 0;
            for (PageData p : dtllist) {
                if (Const.DEL_NO.equals(p.getString("ASSIGNED_FLG"))) {
                    totalEa += p.getInteger("EA_EA");
                    assignedEa += p.getInteger("ASSIGNED_EA");
                } else {
                    assignList.add(p);
                }
            }
            PageData head = headlist.get(0);
            head.put("EA_EA", totalEa);
            head.put("ASSIGNED_EA", assignedEa);
        }

        srcList.addAll(headlist);
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }

    public void findDtlAssignOutlistPageforLook_old(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findDtlAssignOutlist1Page", page);
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

    //查找自动分配的明细
    public List<PageData> findAutoAssignOutlistPage_bak(Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findAutoAssignOutlist2Page", page);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        list.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
        return list;
    }


    public void findAutoAssignOutlistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findAutoAssignOutlist2Page", page);
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
        if (!assignList.isEmpty()) {
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
                if (e1.getInteger("OUTSTOCK_NO") == e2.getInteger("OUTSTOCK_NO")) {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                    return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
                } else {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                }
            });
        }

    }

    public void findAssignOutAutolistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOutMapper.findManualHeadlist1Page", page);
        List<PageData> dtlList = (List<PageData>) dao.findForList("AssignOutMapper.findManualDtllist1Page", page);
        if (headList != null) {
            List<PageData> tmpList = new ArrayList<>();
            for (PageData p : headList) {
                p.put("COMB_ID", p.getString("PRODUCT_ID") + Const.SPLIT_LINE + p.getString("STOCKMGROUT_ID"));
                tmpList.add(p);
            }
            srcList.addAll(tmpList);

            srcList.sort((e1, e2) -> {
                if (e1.getInteger("OUTSTOCK_NO") == e2.getInteger("OUTSTOCK_NO")) {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                    return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
                } else {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                }
            });
        }
        if (headList == null || dtlList.isEmpty()) {
            return;
        }
        assignList.addAll(dtlList);
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }

    //查找手工分配的明细
    public void findAssignOutManuallistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOutMapper.findManualHeadlist1Page", page);
        List<PageData> dtlList = (List<PageData>) dao.findForList("AssignOutMapper.findManualDtllist1Page", page);
        if (headList != null) {
            List<PageData> tmpList = new ArrayList<>();
            for (PageData p : headList) {
                p.put("COMB_ID", p.getString("PRODUCT_ID") + Const.SPLIT_LINE + p.getString("STOCKMGROUT_ID"));
                tmpList.add(p);
            }

            srcList.addAll(tmpList);
            srcList.sort((e1, e2) -> {
                if (e1.getInteger("OUTSTOCK_NO") == e2.getInteger("OUTSTOCK_NO")) {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                    return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
                } else {
                    return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
                }
            });
        }
        if (headList == null || dtlList.isEmpty()) {
            return;
        }
        assignList.addAll(dtlList);
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }


    //查找手工分配的明细 findCancelHeadlist1Page findCancelDtllist1Page
    public void findAssignOutCacellistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOutMapper.findCancelHeadlist1Page", page);
        List<PageData> dtlList = (List<PageData>) dao.findForList("AssignOutMapper.findCancelDtllist1Page", page);
        if (headList == null || headList.isEmpty()) {
            return;
        }
        for (PageData p : headList) {
            p.put("COMB_ID", p.getString("PRODUCT_ID") + Const.SPLIT_LINE + p.getString("STOCKMGROUT_ID"));
            srcList.add(p);
        }

        for (PageData p : dtlList) {
            p.put("COMB_ID", p.getString("STOCKDTLMGROUT_ID") + Const.SPLIT_LINE + p.getString("STOCKMGROUT_ID"));
            assignList.add(p);
        }

        srcList.sort((e1, e2) -> {
            if (e1.getInteger("OUTSTOCK_NO") == e2.getInteger("OUTSTOCK_NO")) {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
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

    //查找手工分配的明细
    public void findManualAssignOutlistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findManualAssignOutlist", page);
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
        //if(!assignList.isEmpty()) {
        List<PageData> datas = filterById(tmpList);
        srcList.addAll(datas);

        srcList.sort((e1, e2) -> {
            if (e1.getInteger("OUTSTOCK_NO") == e2.getInteger("OUTSTOCK_NO")) {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
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
        // }
    }


    //查找自动分配的明细
    public void findAssignBoxlist(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        // pd.put("STOCKMGROUT_IDS_CHOSE", VaryUtils.genListOrNull(pd.getString("STOCKMGROUT_IDS_CHOSE")));
        List<String> list1 = page.getPd().getList("STOCKMGROUT_IDS_CHOSE");
        List<String> idList = new ArrayList<>();
        List<String> prodList = new ArrayList<>();
        for (String s : list1) {
            String[] split = StringUtils.split(s, Const.SPLIT_LINE);
            idList.add(split[1]);
            prodList.add(split[0]);
        }
        page.getPd().put("IDS", idList);
        page.getPd().put("PRODS", prodList);

        List<PageData> headList = (List<PageData>) dao.findForList("AssignOutMapper.findAssignDtlHead", page);
        if (headList == null || headList.isEmpty()) {
            return;
        }
        srcList.addAll(headList);

        List<String> boxNoList = page.getPd().getList("BIG_BOX_NO");
        page.getPd().put("BOXS", boxNoList);

        String customer_id = headList.get(0).getString("CUSTOMER_ID");
        String inboundRemarkSql = BaseInfoCache.getInstance().getInboundRemarkSql(customer_id);
        page.getPd().put("COLUM", inboundRemarkSql);

        List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findAssignBoxlistlistPage", page);
        if (list == null || list.isEmpty()) {
            return;
        }

        for (PageData p : srcList) {
            p.put("CAN_ASSIGN_EA", p.getInteger("CAN_ASSIGN_EA"));
        }
        /*for (PageData p : headList) {
            int canAssignEa = p.getInteger("CAN_ASSIGN_EA");
            if (canAssignEa >0) {
                srcList.add(p);
            }
        }*/

        List<PageData> lst = new ArrayList<>(list.size());
        for (PageData p : list) {
            int canAssignEa = p.getInteger("CAN_ASSIGN_EA");
            if (canAssignEa > 0) {
                p.put("BOXS_ID", p.getString("BOX_ID") + Const.SPLIT_LINE + p.getString("STOCKDTLMGROUT_ID"));
                p.put("CAN_ASSIGN_EA", canAssignEa);
                lst.add(p);
            }

        }
        assignList.addAll(lst);
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }


    public void saveAssign(PageData pd) throws Exception {
        //操作类型：1 自动分配 2 手工分配 4取消分配
        String optEven = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_1.equals(optEven)) {
            saveAssignAuto(pd);
        } else if (Const.OPT_EVEN_2.equals(optEven)) {
            saveAssignManual(pd);
        }
    }

    public void saveAssignAuto(PageData pd) throws Exception {
        //选中的出库单主表ID (STOCKMGROUT_ID_CHOSE=PRODUCT_ID-STOCKMGROUT_ID)
        String[] dtlIds = StringUtils.split(pd.getString("STOCKMGROUT_ID_CHOSE"), Const.SPLIT_COMMA);
        Map<String, List<String>> stringListMap = genOutIdProdMap(dtlIds);

        for (Map.Entry<String, List<String>> entry : stringListMap.entrySet()) {

            //1 根据主表ID获取出库单明细 可以进行分配的 的产品信息
            // List<PageData> dtlList = findDtlOutByPIds(dtlIds);
            List<PageData> dtlList = findDtlOutByPIds(entry.getKey(), entry.getValue());
            //2 对明细 根据 出库单主表ID分组map(出库单主表ID,出库单对应要分配的产品明细)
            Map<String, List<PageData>> idMap = groupByPId(dtlList);
            //存出库单主Id，用于更新主表分配状态

            for (Map.Entry<String, List<PageData>> ent : idMap.entrySet()) {
                //根据明细构建map(产品，库存分配规则ID),用户下面获取当前产品对应分库存分配规则
                Map<String, String> prodAssignRuleMap = genProdAssignRuleMap(ent.getValue());

                //3 根据该出库单的产品明细 获取 未拣货分配的箱号纪录,并按产品ID分组,key=PRODUCT_ID
                Map<String, List<PageData>> prodAssignMap = getCanAssignMap(ent.getValue());
                if (prodAssignMap == null || prodAssignMap.isEmpty()) {
                    continue;
                }
                //4 根据客户对应的库存分配规则，对箱号纪录信息进行排序
                Map<String, List<PageData>> sortBoxMap = sortByStockAssignRule(prodAssignMap, prodAssignRuleMap);

                //5 将箱号纪录插入出库单明细
                insertAssignAuto(sortBoxMap, ent.getValue());
                //6 更新该入库单的分配状态
                updateAssignState(ent.getKey());
            }
        }
    }



    public void saveAssignManual(PageData pd) throws Exception {
        //选中的出库单明细ID,数组中元素为(box_id_stockdtlmgrout_id)
        String[] dtlIds = StringUtils.split(pd.getString("STOCKDTLMGROUT_ID_CHOSE"), Const.SPLIT_COMMA);
        //map(出库单明细Id，箱ID列表)
        Map<String, Integer> boxIdQtyMap = new HashMap<>();
        Map<String, List<String>> dtlIdBoxsMap = genByDtlIdMap(dtlIds, boxIdQtyMap);
        //保存出库主表ID，做更新单头分配状态使用
        Set<String> stockIdSet = new HashSet<>();
        for (Map.Entry<String, List<String>> ent : dtlIdBoxsMap.entrySet()) {
            //1 根据box_id 列表获取分配给当前出库明细的信息
            List<PageData> boxList = findBoxDtlIds(ent.getValue());

            if (boxList == null || boxList.isEmpty()) {
                continue;
            }
            //2 根据当前出库单明细ID获取当前的出库明细,因为只有一个ID，取第一个即可
            List<PageData> dtlOutByDtlIds = findDtlOutByDtlIds(new String[]{ent.getKey()});
            if (dtlOutByDtlIds == null || dtlOutByDtlIds.isEmpty()) {
                continue;
            }
            PageData dtlPd = dtlOutByDtlIds.get(0);

            stockIdSet.add(dtlPd.getString("STOCKMGROUT_ID"));
            //3 将箱号纪录插入出库单明细
            insertAssignManual(boxList, dtlPd, boxIdQtyMap);
        }
        //6 更新该入库单的分配状态
        for (String pid : stockIdSet) {
            updateAssignState(pid);
        }
    }

    //按出库单的不是分配的产品明细取消分配
    public void saveAssignCacel1(PageData pd) throws Exception {
        //选中的出库单主表ID,数组中元素为(prodId - stockmgrout_id)
        String[] dtlIds = StringUtils.split(pd.getString("STOCKDTLMGROUT_ID_CHOSE"), Const.SPLIT_COMMA);
        //1 根据该出库单明细ID获取该ID对应已经分配的明细取消
        Map<String, List<String>> pIdProdListMap = genPIdProdListMap(dtlIds);

        for (Map.Entry<String, List<String>> en : pIdProdListMap.entrySet()) {
            String pId = en.getKey();
            List<String> prodList = en.getValue();
            PageData searchPd = new PageData();
            searchPd.put("STOCKMGROUT_ID",pId);
            searchPd.put("IDS",prodList);
            Page page = new Page();
            page.setPd(searchPd);
            List<PageData> hadAssignList = findHadAssignList(page);
           //找不到相应的明细，不必要去分配
            if (hadAssignList == null || hadAssignList.isEmpty()) {
                continue;
            }
            //产品，明细
            Map<String, List<PageData>> prodMap = genProdMap(hadAssignList);
            //产品ID，明细
            boolean updateFlg = false;
            for (Map.Entry<String, List<PageData>> entry : prodMap.entrySet()) {
                List<PageData> dtlList = entry.getValue();
                //如果只有一笔纪录，说明不用对其进行取消
                if (dtlList == null || dtlList.isEmpty() || dtlList.size() == 1) {
                    continue;
                }
                //升序
                dtlList.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
                //第一个实体是原始纪录，其他的是分配的明细
                PageData srcPd = dtlList.get(0);
                int assignEa = 0;
                for (int i = 1, len = dtlList.size(); i < len; i++) {
                    assignEa += dtlList.get(i).getInteger("ASSIGNED_EA");

                    PageData newPd = new PageData();
                    newPd.put("STOCKMGROUT_ID", dtlList.get(i).getString("STOCKMGROUT_ID"));
                    newPd.put("STOCKDTLMGROUT_ID", dtlList.get(i).getString("STOCKDTLMGROUT_ID"));

                    //删除明细
                    deleteDtlAssignByID(newPd);
                    //更新箱号表
                    newPd.put("BOX_ID", newPd.getString("STOCKDTLMGROUT_ID"));
                    newPd.put("ASSIGNED_EA", dtlList.get(i).getInteger("ASSIGNED_EA"));

                    updateBoxAssignByID(newPd);
                }

                int assigned_ea = srcPd.getInteger("ASSIGNED_EA");
                int rsEA = assigned_ea - assignEa;
                rsEA = rsEA <= 0 ? 0 : rsEA;

                srcPd.put("ASSIGNED_EA", rsEA);
                if (rsEA == 0) {
                    srcPd.put("ASSIGNED_STATE", WmsEnum.AssignedState.NONE.getItemKey());
                } else {
                    srcPd.put("ASSIGNED_STATE", WmsEnum.AssignedState.PART.getItemKey());
                }
                dao.update("AssignOutMapper.editDtlStockAssinedInfo", srcPd);
                updateFlg = true;
            }
            //6 更新该入库单的分配状态
            if (updateFlg) {
                updateAssignState(pId);
            }
        }

    }

    //导出拣货单
    public List<PageData> findPickOneForExl(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findPickOneForExl", pd);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        List<PageData> lst = new ArrayList<>();
        PageData rs;
        //参数变为var+i,之后写入excel方便一些
        //Location(var0),Carton ID (外箱）(var1)	,Carton ID （内箱）(var2),P/N#(var3),
        // DC(var4),LOT(var5),BIN(var6)COO(var7),Qty(var8),SQTY(var9)
        int totalEA = 0;
        for (PageData p : list) {
            rs = new PageData();
            rs.put("var0", BaseInfoCache.getInstance().getLocatorCode(p.getString("PUT_LOCATOR")));
            String bigBox = p.getString("BIG_BOX_NO");
            String box = p.getString("BOX_NO");
            if (StringUtil.isEmpty(bigBox)) {
                rs.put("var1", box);
            } else {
                rs.put("var1", bigBox);
            }
            //内箱号
            rs.put("var2", box);
            rs.put("var3", BaseInfoCache.getInstance().getProductCode(p.getString("PRODUCT_ID")));
            rs.put("var4", p.getString("DATE_CODE"));
            rs.put("var5", p.getString("LOT_NO"));
            rs.put("var6", p.getString("BIN_CODE"));
            rs.put("var7", p.getString("COO"));
            rs.put("var8", p.getInteger("ASSIGNED_STOCK_NUM"));
            rs.put("var9", p.getString("SEPARATE_QTY"));

            totalEA += p.getInteger("ASSIGNED_STOCK_NUM");

            Integer separateQty = p.getInteger("SEPARATE_QTY");
            if (separateQty == 0) {
                rs.put("var10", 1);
            } else {
                int incs = (int) (p.getInteger("ASSIGNED_STOCK_NUM") / separateQty);
                rs.put("var10", incs);
            }

            rs.put("sendTm", p.getString("PRE_OUT_DATE"));
            rs.put("stockNo", p.getString("OUTSTOCK_NO"));

            String RECEIPT_TM = p.getString("RECEIPT_TM");
            if (StringUtil.isEmpty("RECEIPT_TM")) {
                rs.put("RECEIPT_TM", p.getString("CREATE_TM"));
            } else {
                rs.put("RECEIPT_TM", RECEIPT_TM);
            }
            lst.add(rs);
        }
        lst.sort((e1, e2) -> e1.getUtilDate("RECEIPT_TM").compareTo(e2.getUtilDate("RECEIPT_TM")));
        PageData totalPd = new PageData();
        totalPd.put("var0", "Total:");
        totalPd.put("var1", "");
        totalPd.put("var2", "");
        totalPd.put("var3", "");
        totalPd.put("var4", "");
        totalPd.put("var5", "");
        totalPd.put("var6", "");
        totalPd.put("var7", "Total");
        totalPd.put("var8", totalEA);
        totalPd.put("var9", "");
        lst.add(totalPd);
        return lst;
    }

    //导出拣货单
    public List<PageData> findPickManyForExl(PageData pd, Set<String> title) throws Exception {
        List<String> id = pd.getList("STOCKMGROUT_ID");
        List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findPickManyForExl", id);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        List<PageData> lst = new ArrayList<>();
        PageData rs;
        //参数变为var+i,之后写入excel方便一些
        //库位	Carton ID	WMS 出库单号	P/N#	Qty (EA)	SQTY	多少内箱
        int totalEA = 0;
        int totalCS = 0;
        for (PageData p : list) {
            rs = new PageData();
            rs.put("var0", BaseInfoCache.getInstance().getLocatorCode(p.getString("PUT_LOCATOR")));
            String bigBox = p.getString("BIG_BOX_NO");
            String box = p.getString("BOX_NO");
            if (StringUtil.isEmpty(bigBox)) {
                rs.put("var1", box);
            } else {
                rs.put("var1", bigBox);
            }
            //出库单号
            String stockNo = p.getString("OUTSTOCK_NO");
            title.add(stockNo);
            String subno = stockNo.substring(stockNo.indexOf("S-") + 2, stockNo.indexOf("S-") + 8) + Const.SPLIT_LINE + StringUtil.lpadTwo(p.getString("ROW_NO"));
            rs.put("var2", subno);
            rs.put("var3", BaseInfoCache.getInstance().getProductCode(p.getString("PRODUCT_ID")));
            rs.put("var4", p.getInteger("ASSIGNED_STOCK_NUM"));
            rs.put("var5", p.getString("SEPARATE_QTY"));

            Integer separateQty = p.getInteger("SEPARATE_QTY");
            if (separateQty == 0) {
                totalCS += 1;
                rs.put("var6", 1);
            } else {
                int incs = (int) (p.getInteger("ASSIGNED_STOCK_NUM") / separateQty);
                rs.put("var6", incs);
                totalCS += incs;
            }

            totalEA += p.getInteger("ASSIGNED_STOCK_NUM");

            rs.put("sendTm", p.getString("PRE_OUT_DATE"));
            rs.put("stockNo", p.getString("OUTSTOCK_NO"));

            String RECEIPT_TM = p.getString("RECEIPT_TM");
            if (StringUtil.isEmpty("RECEIPT_TM")) {
                rs.put("RECEIPT_TM", p.getString("CREATE_TM"));
            } else {
                rs.put("RECEIPT_TM", RECEIPT_TM);
            }
            lst.add(rs);
        }
        lst.sort((e1, e2) -> e1.getUtilDate("RECEIPT_TM").compareTo(e2.getUtilDate("RECEIPT_TM")));
        PageData totalPd = new PageData();
        totalPd.put("var0", "Total:");
        totalPd.put("var1", "");
        totalPd.put("var2", "");
        totalPd.put("var3", "");
        totalPd.put("var4", totalEA);
        totalPd.put("var5", "");
        totalPd.put("var6", totalCS);

        lst.add(totalPd);
        return lst;
    }


    //按出库单的已经分配的产品明细取消分配
    public void saveAssignCacel2(PageData pd) throws Exception {
        //选中的出库单明细ID,数组中元素为(stockdtlmgrout_id)
        String[] dtlIds = StringUtils.split(pd.getString("STOCKDTLMGROUT_ID_CHOSE"), Const.SPLIT_COMMA);
        //1 根据该出库单明细ID获取该ID对应已经分配的明细取消

        //保存出库主表ID，做更新单头分配状态使用
        Set<String> stockIdSet = new HashSet<>();
        List<String> dtlIdList = new ArrayList<>();
        for (String dtlId : dtlIds) {
            PageData assignPd = findDtlAssignPdById(dtlId);
            PageData noAssignPd = findDtlAssignPdByIdAndProd(assignPd.getString("STOCKMGROUT_ID"), assignPd.getString("PRODUCT_ID"));
            stockIdSet.add(assignPd.getString("STOCKMGROUT_ID"));
            dtlIdList.add(dtlId);
            //分配数据 = 原始纪录分分配数量 - 已分配的数据
            noAssignPd.put("ASSIGNED_EA", noAssignPd.getInteger("ASSIGNED_EA") - assignPd.getInteger("ASSIGNED_EA"));
            int assignEACnt = noAssignPd.getInteger("ASSIGNED_EA");
            String assignState = WmsEnum.AssignedState.PART.getItemKey();
            if (assignEACnt == 0) {
                assignState = WmsEnum.AssignedState.NONE.getItemKey();
            }
            noAssignPd.put("ASSIGNED_STATE", assignState);
            //更新不是分配明细的分配EA，与分配状态
            updateStockAssign(noAssignPd);
            //删除明细
            deleteDtlAssignByID(assignPd);
            //更新箱号表
            assignPd.put("BOX_ID", assignPd.getString("STOCKDTLMGROUT_ID"));
            assignPd.put("ASSIGNED_EA", assignPd.getInteger("ASSIGNED_EA"));

            updateBoxAssignByID(assignPd);
        }

        //删除明细
        // deleteDtlAssignByID(dtlIdList);
        //更新箱号表
        // updateBoxAssignByID(dtlIdList);
        //6 更新该入库单的分配状态
        for (String pid : stockIdSet) {
            updateAssignState(pid);
        }

    }


    public void savePickNo(PageData pd) {
        try {
            dao.save("AssignOutMapper.savePickNo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findPickNo(PageData pd) {
        try {

            String pSiRefNo = findPSiRefNo(pd);
            if (StringUtil.isNotEmpty(pSiRefNo)) {
                return pSiRefNo;
            }

            PageData rs = (PageData) dao.findForObject("AssignOutMapper.findPickNo", pd);
            if (rs != null) {
                return rs.getString("PICK_NO");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> findPickNoMap(PageData pd) {
        List<String> id = pd.getList("STOCKMGROUT_ID");
        Map<String, String> map = new HashMap<>();
        try {
            List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findManySiRefNo", id);

            if (list == null) {
                return map;
            }

            for (PageData p : list) {
                map.put(p.getString("OUTSTOCK_NO"), p.getString("SI_REFERENCE"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    public String findPSiRefNo(PageData pd) {
        try {
            PageData rs = (PageData) dao.findForObject("AssignOutMapper.findSiRefNo", pd);
            if (rs != null) {
                return rs.getString("SI_REFERENCE");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String auditAssignQty(PageData pd) {
        //选中的出库单明细ID,数组中元素为(box_id_stockdtlmgrout_id)
        String[] dtlIds = StringUtils.split(pd.getString("BOX_IDS"), Const.SPLIT_COMMA);

        Map<String, Integer> choseMap = genByProdIdMap(dtlIds);
        String[] dtlIds2 = StringUtils.split(pd.getString("PRODIDS_QTY"), Const.SPLIT_COMMA);
        Map<String, Integer> srcMap = genByProdIdMap(dtlIds2);

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Integer> e : choseMap.entrySet()) {
            String prodId = e.getKey();
            int qty = e.getValue();
            int srcQty = srcMap.get(prodId);

            if (qty > srcQty) {
                String prodCode = BaseInfoCache.getInstance().getProductCode(prodId);
                sb.append(prodCode + "当前选择数量:" + qty + ",而可分配的数量为:" + srcQty);
            }

        }
        return sb.toString();
    }


    public String buildSiRef(Set<String> titleSet, Map<String, String> map) {
        StringBuffer sb = new StringBuffer(64);
        int count = 0;
        for (String no : titleSet) {
            if (count != 0) {
                sb.append(Const.SPLIT_COMMA);
            }
            sb.append(map.get(no));
            count++;
        }
        return sb.toString();
    }

    private PageData buildOrderProperty(PageData pd, PageData dtlPd) {
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
        String pid = StringUtil.isEmpty(dtlPd.getString("P_ID")) ? UuidUtil.get32UUID() : dtlPd.getString("P_ID");
        pdtl.put("P_ID", pid);
        pdtl.put("STOCKMGROUT_ID", dtlPd.getString("STOCKMGROUT_ID"));
        pdtl.put("STOCKDTLMGROUT_ID", dtlPd.getString("STOCKDTLMGROUT_ID"));
        pdtl.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));
        return pdtl;
    }

    private void setProperties(PageData pd) {
        //得到属性列表
        // List<PageData> propertyList = orderpropertyService.getPropertyOutBatch(pd.getString("CUSTOMER_ID"));
        List<PageData> propertyList = BaseInfoCache.getInstance().getPropertyOutBatch(pd.getString("CUSTOMER_ID"));
        for (PageData e : propertyList) {
            String cloumValue = e.getString("PROPERTY_KEY");
            if (StringUtil.isEmpty(cloumValue)) {
                continue;
            }

            String bpCloum = StringUtil.trimSpace(cloumValue);
            //如果字符串是属性配置中的值的话，根据其对应的排序，取到属性对应的字段名
            Integer srcSort = e.getInteger("SRC_SORT");
            String columKey = WmsEnum.OrderProperty.sortColuMap.get(srcSort);
            //入库日期  CaseNumber ,Scan Code, Separate QTY COO LOT CODE BIN
            if (bpCloum.equalsIgnoreCase(Const.BP_INBOUNDTM)) {
                pd.put(columKey, pd.getString("CREATE_TM"));
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

            if (bpCloum.equalsIgnoreCase(Const.BP_DATACODE)) {
                pd.put(columKey, pd.getString(Const.BP_DATACODE_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_SCANCODE)) {
                pd.put(columKey, pd.getString(Const.BP_SCANCODE_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_DATACODE) || bpCloum.equalsIgnoreCase(Const.BP_DATECODE)
                    || bpCloum.startsWith(Const.BP_DATECODE2) || bpCloum.startsWith(Const.BP_DATECODE3)) {
                pd.put(columKey, pd.getString(Const.BP_DATACODE_SRC));
                continue;
            }

            if (bpCloum.equalsIgnoreCase(Const.BP_PACKAGEQTY_1) || bpCloum.equalsIgnoreCase(Const.BP_PACKAGEQTY_2)) {
                pd.put(columKey, pd.getString(Const.BOX_SEPARATE_QTY));
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
        }
    }


    //3 手工分配 将箱号纪录插入出库单明细
    private void insertAssignManual(List<PageData> boxList, PageData dtlPd, Map<String, Integer> boxIdQtyMap) {
        PageData inserDtl;
        int ASSIGNED_EA = 0;
        //当前产品可分配EA数
        int curAssignEA = dtlPd.getInteger("EA_ACTUAL");

        boxList.sort((e1, e2) -> e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM")));
        for (PageData box : boxList) {
            inserDtl = new PageData();
            // 如果已分配的EA大于等于可分配的EA，则跳出
            if (ASSIGNED_EA >= curAssignEA) {
                break;
            }

            //如果当前可分配EA数小于等于0的话，说明已经分配完毕，跳出当前box纪录的循环
            if (curAssignEA <= 0) {
                break;
            }


            //比较当前箱的EA 与 当前明细可分配的EA ，如果box_ea > 明细可分配的EA  EA_NUM ASSIGNED_STOCK_NUM
            //箱期望EA - 已经分配库存的EA = 可进行分配的EA
            //修改为从界面获取分配的数量，如果数量取不到，则取箱可分配的数量
            Integer boxEA = boxIdQtyMap.get(box.getString("BOX_ID"));
            if (boxEA == null || boxEA == 0) {
                boxEA = box.getInteger("EA_NUM") - box.getInteger("ASSIGNED_STOCK_NUM");
            }

            //箱EA 大于 明细可分配EA的时候，
            if (curAssignEA < boxEA) {
                box.put("CAN_ASSIGN_EA", curAssignEA);
            } else {
                box.put("CAN_ASSIGN_EA", boxEA);
            }

            //1 构建存入出库明细的实体
            buildDtlPd(inserDtl, box, dtlPd);
            ASSIGNED_EA += box.getInteger("CAN_ASSIGN_EA");
            //2 保存纪录到出库明细
            saveDtl(inserDtl);
            //3 更新箱号表的库存分配状态、库存分配数据
            box.put("OUTSTOCK_NO", dtlPd.getString("OUTSTOCK_NO"));
            updateBoxStockAssignInfo(box);

            insertProperties(box, inserDtl);
        }
        dtlPd.put("ASSIGNED_EA", dtlPd.getInteger("ASSIGNED_EA") + ASSIGNED_EA);
        //4 更新当前该笔产品明细的库存分配EA
        updateStockDtlAssignEA(dtlPd);
    }

    //5 将箱号纪录插入出库单明细(匹配多次：先找1箱满足数量的，2、找2箱满足数量的，3、找3箱满足数量的)
    private void insertAssignAuto(Map<String, List<PageData>> sortBoxMap, List<PageData> dtlList) {
        for (PageData dtl : dtlList) {
            List<PageData> boxList = sortBoxMap.get(dtl.getString("PRODUCT_ID"));
            if (boxList == null || boxList.isEmpty()) {
                continue;
            }
            //当前产品可分配EA数
            int curAssignEA = dtl.getInteger("EA_ACTUAL");
            List<PageData> matchBoxList = matchAutoBoxList(curAssignEA, boxList);

            if (matchBoxList == null || matchBoxList.isEmpty()) {
                insertNotMatch(dtl,boxList);
            } else {
                insertNotMatch(dtl,matchBoxList);
            }
            //更新当前该笔产品明细的库存分配EA
            updateStockDtlAssignEA(dtl);
        }
    }

    private void insertNotMatch(PageData dtl,List<PageData> boxList) {
        PageData inserDtl;
        for (PageData box : boxList) {
            inserDtl = new PageData();
            //当前产品可分配EA数
            int curAssignEA = dtl.getInteger("EA_ACTUAL");
            //如果当前可分配EA数小于等于0的话，说明已经分配完毕，跳出当前box纪录的循环
            if (curAssignEA <= 0) {
                break;
            }

            //比较当前箱的EA 与 当前明细可分配的EA ，如果box_ea > 明细可分配的EA  EA_NUM ASSIGNED_STOCK_NUM
            //箱期望EA - 已经分配库存的EA = 可进行分配的EA
            int boxEA = box.getInteger("EA_NUM") - box.getInteger("ASSIGNED_STOCK_NUM");
            //箱EA 大于 明细可分配EA的时候，
            if (curAssignEA < boxEA) {
                box.put("CAN_ASSIGN_EA", curAssignEA);
            } else {
                box.put("CAN_ASSIGN_EA", boxEA);
            }
            //1 构建存入出库明细的实体
            buildDtlPd(inserDtl, box, dtl);
            //计算当前分配明细的实际已分配EA、可分配EA
            dtl.put("ASSIGNED_EA", dtl.getInteger("ASSIGNED_EA") + inserDtl.getInteger("ASSIGNED_EA"));
            dtl.put("EA_ACTUAL", dtl.getInteger("EA_EA") - dtl.getInteger("ASSIGNED_EA"));
            //2 保存纪录到出库明细
            saveDtl(inserDtl);

            //3 更新箱号表的库存分配状态、库存分配数据
            box.put("OUTSTOCK_NO", dtl.getString("OUTSTOCK_NO"));
            updateBoxStockAssignInfo(box);

            insertProperties(box, inserDtl);
        }
    }

    /**
     * 自动匹配分配的箱号:
     * 1、先循环找一遍，看有否数量一样的，如果一样，就是分配单独的一个箱；
     * 2、不满足1，则从第一个循环开始，先找一个想加等于明细数量的记录，此时应该是分配2比记录；
     * 3、不满足2，则先找出3比想加
     *
     * @param assignedEa   需要分配的EA数
     * @param boxList 可以进行分配的箱号
     * @return
     */
    private List<PageData> matchAutoBoxList(int assignedEa, List<PageData> boxList) {
        List<PageData> matchList = match1AutoBoxList(assignedEa, boxList);
        if (matchList != null && !matchList.isEmpty()) {
            return matchList;
        }
        matchList = match2AutoBoxList(assignedEa, boxList);
        if (matchList != null && !matchList.isEmpty()) {
            return matchList;
        }
        matchList = match3AutoBoxList(assignedEa, boxList);
        return matchList;
    }

    private List<PageData> match1AutoBoxList(int assignedEa, List<PageData> boxList) {
        List<PageData> list = new ArrayList<>();
        for (int i = 0, size = boxList.size(); i < size - 1; i++) {
            PageData box = boxList.get(i);
            int boxEAi = box.getInteger("EA_NUM") - box.getInteger("ASSIGNED_STOCK_NUM");
            if (boxEAi == assignedEa) {
                list.add(box);
            }
        }
        return list;
    }

    private List<PageData> match2AutoBoxList(int assignedEa, List<PageData> boxList) {
        List<PageData> list = new ArrayList<>();
        for (int i = 0, size = boxList.size(); i < size - 1; i++) {
            PageData box = boxList.get(i);
            int boxEAi = box.getInteger("EA_NUM") - box.getInteger("ASSIGNED_STOCK_NUM");
            for (int j = 1; j < size; j++) {
                PageData box2 = boxList.get(j);
                int boxEAj = box2.getInteger("EA_NUM") - box2.getInteger("ASSIGNED_STOCK_NUM");
                if (boxEAi + boxEAj == assignedEa) {
                    list.add(box);
                    list.add(box2);
                    return list;
                }
            }
        }
        return list;
    }

    private List<PageData> match3AutoBoxList(int assignedEa, List<PageData> boxList) {
        List<PageData> list = new ArrayList<>();
        for (int i = 0, size = boxList.size(); i < size - 2; i++) {
            PageData box = boxList.get(i);
            int boxEAi = box.getInteger("EA_NUM") - box.getInteger("ASSIGNED_STOCK_NUM");
            for (int j = i + 1; j < size - 1; j++) {
                PageData box2 = boxList.get(j);
                int boxEAj = box2.getInteger("EA_NUM") - box2.getInteger("ASSIGNED_STOCK_NUM");
                for (int k = j + 1; k < size; k++) {
                    PageData box3 = boxList.get(k);
                    int boxEAk = box3.getInteger("EA_NUM") - box3.getInteger("ASSIGNED_STOCK_NUM");
                    if ((boxEAi + boxEAj+boxEAk) == assignedEa) {
                        list.add(box);
                        list.add(box2);
                        list.add(box3);
                        return list;
                    }
                }
            }
        }
        return list;
    }

    private void insertProperties(PageData box, PageData dtlPd) {
        try {
            setProperties(box);
            PageData property = buildOrderProperty(box, dtlPd);
            dao.save("StockMgrOutMapper.saveOrderDtl", property);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据出库单ID去更新出库单表头的分配状态.逻辑:<br>
     * 1、如果所有的已分配EA数都是0，则是：未分配;<br>
     * 2、如果所有的已分配EA数都大于等于期望EA的话，则是:全部分配;<br>
     * 3、其他情况则是:部分分配
     *
     * @param stockmgroutId 出库单ID
     */
    private void updateAssignState(String stockmgroutId) {
        try {
            PageData pd = new PageData();
            pd.put("STOCKMGROUT_ID", stockmgroutId);
            // EA_EA, ASSIGNED_EA
            List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findDtlOutList", pd);

            int dtlCnt = list.size();
            int eaCnt = 0;
            int assignEACnt = 0;
            for (PageData p : list) {
                //期望EA、已分配EA
                int EA = p.getInteger("EA_EA");
                int assignedEA = p.getInteger("ASSIGNED_EA");
                if (assignedEA == 0) {
                    assignEACnt += 1;
                }

                if (assignedEA >= EA) {
                    eaCnt += 1;
                }
            }

            String assignState = WmsEnum.AssignedState.PART.getItemKey();
            if (assignEACnt == dtlCnt) {
                assignState = WmsEnum.AssignedState.NONE.getItemKey();
            } else if (eaCnt == dtlCnt) {
                assignState = WmsEnum.AssignedState.ALL.getItemKey();
            }

            pd.put("ASSIGNED_STATE", assignState);
            pd.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
            pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
            //修改出库单表头的库存分配状态
            dao.update("AssignOutMapper.editHeadStockAssinedInfo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //保存明细到出库单明细中
    private void saveDtl(PageData inserDtl) {
        try {
            dao.save("AssignOutMapper.saveAssignStockDtl", inserDtl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新箱号表的库存分配状态、库存分配数据
    private void updateBoxStockAssignInfo(PageData box) {
        int ea_num = box.getInteger("EA_NUM");
        int can_assign_ea = box.getInteger("CAN_ASSIGN_EA");
        if (ea_num > can_assign_ea) {
            box.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.PART.getItemKey());
        } else {
            box.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.ALL.getItemKey());
        }
        box.put("ASSIGNED_STOCK_NUM", box.getInteger("ASSIGNED_STOCK_NUM") + can_assign_ea);
        try {
            box.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
            box.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
            dao.update("AssignOutMapper.editBoxStockAssinedInfo", box);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //更新明细的库存分配EA
    private void updateStockDtlAssignEA(PageData dtl) {
        try {
            int assignedEa = dtl.getInteger("ASSIGNED_EA");
            int EA = dtl.getInteger("EA_EA");
            String assignState = WmsEnum.AssignedState.NONE.getItemKey();
            if (assignedEa >= EA) {
                assignState = WmsEnum.AssignedState.ALL.getItemKey();
            } else if (assignedEa != 0) {
                assignState = WmsEnum.AssignedState.PART.getItemKey();
            }
            dtl.put("ASSIGNED_STATE", assignState);
            updateStockAssign(dtl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateStockAssign(PageData dtl) throws Exception {
        dao.update("AssignOutMapper.editDtlStockAssinedInfo", dtl);
    }

    //构建存入出库明细的实体
    private void buildDtlPd(PageData inserDtl, PageData box, PageData dtl) {
        inserDtl.put("STOCKDTLMGROUT_ID", box.getString("BOX_ID"));
        inserDtl.put("CUSTOMER_ID", dtl.getString("CUSTOMER_ID"));
        inserDtl.put("STOCKMGROUT_ID", dtl.getString("STOCKMGROUT_ID"));
        inserDtl.put("PRODUCT_ID", dtl.getString("PRODUCT_ID"));
        inserDtl.put("EA_EA", box.getString("EA_NUM"));
        //分配EA 等于 箱号能分的EA
        inserDtl.put("ASSIGNED_EA", box.getString("CAN_ASSIGN_EA"));
        inserDtl.put("ASSIGNED_FLG", Const.ASSIGNED_FLG_YES);
        inserDtl.put("BOX_NO", box.getString("BOX_NO"));
        inserDtl.put("ROW_NO", dtl.getString("ROW_NO"));
        inserDtl.put("STORAGE_ID", dtl.getString("STORAGE_ID"));
        inserDtl.put("LOCATOR_ID", box.getString("PUT_LOCATOR"));

        // 分配状态 ASSIGNED_STATE 拣货状态 PICK_STATE 装车状态 LOADED_STATE 发货状态 DEPOT_STATE

        int concnt = inserDtl.getInteger("EA_EA") - inserDtl.getInteger("ASSIGNED_EA");
        String PICK_STATE = WmsEnum.AssignedState.NONE.getItemKey();
        if (concnt == 0) {
            PICK_STATE = WmsEnum.AssignedState.ALL.getItemKey();
        } else {
            PICK_STATE = WmsEnum.AssignedState.PART.getItemKey();
        }
        inserDtl.put("ASSIGNED_STATE", PICK_STATE);

        inserDtl.put("PICK_STATE", WmsEnum.PickState.NONE.getItemKey());
        inserDtl.put("LOADED_STATE", WmsEnum.LoadedState.NONE.getItemKey());
        inserDtl.put("DEPOT_STATE", WmsEnum.DepotState.NONE.getItemKey());
        inserDtl.put("CARGO_STATE", WmsEnum.CargoState.NONE.getItemKey());

        inserDtl.put("SEND_EA", Const.ZERO);
        inserDtl.put("PREPLAN_EA", Const.ZERO);
        inserDtl.put("PICK_EA", Const.ZERO);
        inserDtl.put("CARGO_EA", Const.ZERO);
        inserDtl.put("LOADED_EA", Const.ZERO);

        inserDtl.put("CREATE_EMP", SessionUtil.getCurUserName());    //创建人
        inserDtl.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间
        inserDtl.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
        inserDtl.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        inserDtl.put("DEL_FLG", 0);    //删除
    }

    //1 根据主表ID获取可以进行分配纪录，返回字段有：
    //STOCKDTLMGROUT_ID,CUSTOMER_ID, STOCKMGROUT_ID ,PRODUCT_ID,EA_EA,ASSIGNED_EA ,(EA_EA +0 - ASSIGNED_EA) EA_ACTUAL,RULE_ASSIGNED
    private List<PageData> findDtlOutByPIds(String[] dtlIds) {
        try {
            return (List<PageData>) dao.findForList("AssignOutMapper.findDtlOutByPIds", dtlIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<PageData> findDtlOutByPIds(String outId, List<String> prodIds) {
        PageData pd = new PageData();
        pd.put("STOCKMGROUT_ID", outId);
        pd.put("IDS", prodIds);
        Page page = new Page();
        page.setPd(pd);
        try {
            return (List<PageData>) dao.findForList("AssignOutMapper.findDtlOutByPIdAndProdIds", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //PRODUCT_ID-STOCKMGROUT_ID
    private Map<String, List<String>> genOutIdProdMap(String[] dtlIds) {
        Map<String, List<String>> map = new HashMap<>();
        for (String combId : dtlIds) {
            String[] split = StringUtils.split(combId, Const.SPLIT_LINE);
            String productId = split[0];
            String stockmgroutId = split[1];
            List<String> list = map.get(stockmgroutId);
            list = list != null ? list : new ArrayList<>();
            list.add(productId);
            map.put(stockmgroutId, list);
        }
        return map;
    }

    //1 根据明细ID获取可以进行分配纪录，返回字段有：
    //STOCKDTLMGROUT_ID,CUSTOMER_ID, STOCKMGROUT_ID ,PRODUCT_ID,EA_EA,ASSIGNED_EA ,(EA_EA +0 - ASSIGNED_EA) EA_ACTUAL,RULE_ASSIGNED
    private List<PageData> findDtlOutByDtlIds(String[] dtlIds) {
        try {
            return (List<PageData>) dao.findForList("AssignOutMapper.findDtlOutByDtlIds", dtlIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //2 对明细 根据 出库单主表ID分组
    private Map<String, List<PageData>> groupByPId(List<PageData> dtlList) {
        Map<String, List<PageData>> map = new HashMap<>();
        if (dtlList == null || dtlList.isEmpty()) {
            return map;
        }

        for (PageData p : dtlList) {
            String pid = p.getString("STOCKMGROUT_ID");
            List<PageData> list = map.get(pid);
            if (list == null || list.isEmpty()) {
                list = new ArrayList<>();
            }
            list.add(p);
            map.put(pid, list);
        }
        return map;
    }

    //3 根据该出库单的产品明细 与客户，获取 未拣货分配的箱号纪录,并按产品ID分组+分配规则 RULE_ASSIGNED
    //key=PRODUCT_ID
    private Map<String, List<PageData>> getCanAssignMap(List<PageData> list) {
        List<PageData> canAssignBoxs = getCanAssignBoxs(list);
        Map<String, List<PageData>> map = new HashMap<>();
        for (PageData p : canAssignBoxs) {
            String prodId = p.getString("PRODUCT_ID");
            List<PageData> lst = map.get(prodId);
            if (lst == null || lst.isEmpty()) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(prodId, lst);
        }
        return map;
    }

    //3 根据该出库单的产品明细、客户 获取 未拣货分配的箱号纪录
    private List<PageData> getCanAssignBoxs(List<PageData> list) {
        List<String> prodIdsList = getProdIds(list);

        String CUSTOMER_ID = list.get(0).getString("CUSTOMER_ID");
        PageData pd = new PageData();
        pd.put("CUSTOMER_ID", CUSTOMER_ID);
        pd.put("PRODUCT_IDS", prodIdsList);
        try {
            return (List<PageData>) dao.findForList("AssignOutMapper.findBoxOutByCustomAndProdIds", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //4 根据客户对应的库存分配规则，对箱号纪录信息进行排序
    private Map<String, List<PageData>> sortByStockAssignRule(Map<String, List<PageData>> prodAssignMap, Map<String, String> prodAssignRuleMap) {

        // PRODUCE_CYCLE  IN_STOCK_CYCLE 1先进先出 2 先进后出
        for (Map.Entry<String, List<PageData>> ent : prodAssignMap.entrySet()) {
            //key=PRODUCT_ID
            //如果只有一笔明细则不必要排序啦
            List<PageData> dtl = ent.getValue();
            if (dtl != null && dtl.size() == 1) {
                continue;
            }

            PageData stockAssign = BaseInfoCache.getInstance().getStockAssign(prodAssignRuleMap.get(ent.getKey()));
            //先去优先级 1 入库日期优先 ，2 生产日期 优先

            String level = Const.FIRST_IN_FIRST_OUT;
            String sysAssignRule = Const.FIRST_IN_FIRST_OUT;
            if (stockAssign != null) {
                sysAssignRule = stockAssign.getString("IN_STOCK_CYCLE");
                level = stockAssign.getString("PRODUCE_LEVEL");
            }

            //入库日期优先
            if (Const.FIRST_IN_FIRST_OUT.equals(level)) {
                if (Const.FIRST_IN_FIRST_OUT.equals(sysAssignRule)) {
                    ent.getValue().sort((e1, e2) -> {
                        return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
                    });
                } else {
                    ent.getValue().sort((e1, e2) -> {
                        return e2.getUtilDate("CREATE_TM").compareTo(e1.getUtilDate("CREATE_TM"));
                    });
                }
            } else { //TODO
                //生产日期的先进先出 2017-1-13 DATE_CODE 修改为 CREATE_TM
                if (Const.FIRST_IN_FIRST_OUT.equals(sysAssignRule)) {
                    ent.getValue().sort((e1, e2) -> {
                        return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
                    });
                } else {
                    ent.getValue().sort((e1, e2) -> {
                        return e2.getUtilDate("CREATE_TM").compareTo(e1.getUtilDate("CREATE_TM"));
                    });
                }
            }

        }

        return prodAssignMap;
    }

    //PRODUCT_ID RULE_ASSIGNED
    private Map<String, String> genProdAssignRuleMap(List<PageData> list) {
        Map<String, String> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            map.put(p.getString("PRODUCT_ID"), p.getString("RULE_ASSIGNED"));
        }
        return map;
    }

    //获取明细中的产品ID列表
    private List<String> getProdIds(List<PageData> list) {
        List<String> lst = new ArrayList<>(list.size());
        for (PageData p : list) {
            lst.add(p.getString("PRODUCT_ID"));
        }
       /* String[] arr = new String[lst.size()];
        return lst.toArray(arr);*/
        return lst;
    }

    /**
     * 将数组拆分为map(出库单明细Id，箱ID列表)
     *
     * @param dtlIds 数组(box_id_stockdtlmgrout_id)
     * @return map(出库单明细Id，箱ID列表)
     */
    private Map<String, List<String>> genByDtlIdMap(String[] dtlIds, Map<String, Integer> boxIdQtyMap) {
        Map<String, List<String>> map = new HashMap<>();
        for (String ids : dtlIds) {
            String[] arr = StringUtils.split(ids, Const.SPLIT_LINE);
            List<String> lst = map.get(arr[1]);
            if (lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(arr[0]);
            map.put(arr[1], lst);
            Integer qty = 0;
            try {
                qty = StringUtil.parseInt(arr[2]);
            } catch (Exception e) {
                qty = 0;
            }
            boxIdQtyMap.put(arr[0], qty);
        }
        return map;
    }

    private Map<String, Integer> genByProdIdMap(String[] dtlIds) {
        Map<String, Integer> map = new HashMap<>();
        for (String ids : dtlIds) {
            String[] arr = StringUtils.split(ids, Const.SPLIT_LINE);
            Integer i = map.get(arr[0]);
            if (i == null) {
                i = 0;
            }
            i += StringUtil.parseInt(arr[1]);
            map.put(arr[0], i);
        }
        return map;
    }

    /**
     * 将数组拆分为map(出库单Id，产品ID列表)
     *
     * @param dtlIds 数组(prod_id_stockmgrout_id)
     * @return map(出库单Id,产品ID列表)
     */
    private Map<String, List<String>> genPIdProdListMap(String[] dtlIds) {
        Map<String, List<String>> map = new HashMap<>();
        for (String ids : dtlIds) {
            String[] arr = StringUtils.split(ids, Const.SPLIT_LINE);
            List<String> lst = map.get(arr[1]);
            if (lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(arr[0]);
            map.put(arr[1], lst);
        }
        return map;
    }

    /**
     * 根据box_id获取将要进行分配的纪录
     *
     * @param list box_id列表
     * @return 列表
     */
    private List<PageData> findBoxDtlIds(List<String> list) {
        try {
            return (List<PageData>) dao.findForList("AssignOutMapper.findBoxDtlIds", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 根据产品明细找到该产品已经分配了的明细
     *
     * @param page stockdtlmgr_id列表
     * @return 列表
     */
    private List<PageData> findHadAssignList(Page page) {
        try {
            return (List<PageData>) dao.findForList("AssignOutMapper.findHadAssignList", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void deleteDtlAssignByID(List<String> list) throws Exception {
        for (String id : list) {
            PageData pd = new PageData();
            String[] arr = StringUtils.split(id, Const.SPLIT_LINE);
            pd.put("STOCKMGROUT_ID", arr[1]);
            pd.put("STOCKDTLMGROUT_ID", arr[0]);
            dao.delete("AssignOutMapper.deleteDtlAssign", pd);
        }

    }

    private void updateBoxAssignByID(List<String> list) throws Exception {
        dao.update("AssignOutMapper.deleteDBoxAssign", list);
    }

    private void deleteDtlAssignByID(PageData pd) throws Exception {
        dao.delete("AssignOutMapper.deleteDtlAssign", pd);
        dao.delete("AssignOutMapper.deleteDtlAssignProperty", pd);
    }

    private void updateBoxAssignByID(PageData pd) throws Exception {
        PageData box = (PageData) dao.findForObject("AssignOutMapper.findBoxById", pd);
        int assignEa = pd.getInteger("ASSIGNED_EA");
        int boxAssignEa = box.getInteger("ASSIGNED_STOCK_NUM");
        //如果存在原始箱号，则删除该拆箱的
        String oldBoxId = box.getString("OLD_BOX_ID");
        if (StringUtil.isNoBlank(oldBoxId)) {
            PageData tmpPd = new PageData();
            tmpPd.put("BOX_ID",oldBoxId);
            PageData oldBox = (PageData) dao.findForObject("AssignOutMapper.findBoxById", tmpPd);
            int rsEa = boxAssignEa - assignEa;
            if (rsEa <= 0) {
                rsEa = 0;
            }
            int eaNum = oldBox.getInteger("EA_NUM");
            oldBox.put("EA_NUM",eaNum+assignEa);
            if (rsEa == 0) {
                oldBox.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.NONE.getItemKey());
            } else {
                oldBox.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.PART.getItemKey());
            }

            oldBox.put("ASSIGNED_STOCK_NUM", rsEa);
            oldBox.put("OUTSTOCK_NO", Const.EMPTY_CH);
            dao.update("AssignOutMapper.updateOldBox", oldBox);
            dao.delete("AssignOutMapper.deleteMixBox", box);
        } else {
            int rsEa = boxAssignEa - assignEa;
            if (rsEa <= 0) {
                rsEa = 0;
            }
            if (rsEa == 0) {
                box.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.NONE.getItemKey());
            } else {
                box.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.PART.getItemKey());
            }

            box.put("ASSIGNED_STOCK_NUM", rsEa);
            box.put("OUTSTOCK_NO", Const.EMPTY_CH);
            dao.update("AssignOutMapper.deleteDBoxAssign", box);
        }
    }

    private void updateBoxAssignByID2(PageData pd) throws Exception {
        PageData box = (PageData) dao.findForObject("AssignOutMapper.findBoxById", pd);
        int assignEa = pd.getInteger("ASSIGNED_EA");
        int boxAssignEa = box.getInteger("ASSIGNED_STOCK_NUM");
        int rsEa = boxAssignEa - assignEa;
        if (rsEa <= 0) {
            rsEa = 0;
        }
        if (rsEa == 0) {
            box.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.NONE.getItemKey());
        } else {
            box.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.PART.getItemKey());
        }

        box.put("ASSIGNED_STOCK_NUM", rsEa);
        box.put("OUTSTOCK_NO", Const.EMPTY_CH);
        dao.update("AssignOutMapper.deleteDBoxAssign", box);
    }

    private PageData getDtlPdById(String id) throws Exception {
        PageData pd = new PageData();
        pd.put("STOCKDTLMGROUT_ID", id);
        return (PageData) dao.findForObject("AssignOutMapper.findDtlOutListByID", pd);
    }

    private PageData findDtlAssignPdById(String id) throws Exception {
        PageData pd = new PageData();
        String[] arr = StringUtils.split(id, Const.SPLIT_LINE);
        pd.put("STOCKMGROUT_ID", arr[1]);
        pd.put("STOCKDTLMGROUT_ID", arr[0]);
        return (PageData) dao.findForObject("AssignOutMapper.findDtlAssignPdById", pd);
    }

    /*根据出库单ID与产品得到明细中的不是分配明细的产品纪录*/
    private PageData findDtlAssignPdByIdAndProd(String id, String prodId) throws Exception {
        PageData pd = new PageData();
        pd.put("STOCKMGROUT_ID", id);
        pd.put("PRODUCT_ID", prodId);
        return (PageData) dao.findForObject("AssignOutMapper.findDtlAssignPdByIdAndProd", pd);
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

    @Deprecated
    public void findAssignOutManuallistPage_bak(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOutMapper.findAssignOutManualHeadlist1Page", page);
        // List<PageData> dtlList = (List<PageData>)dao.findForList("AssignOutMapper.findAssignOutManualDtllist1Page", page);
        if (headList == null || headList.isEmpty()) {
            return;
        }

        List<PageData> tmpList = new ArrayList<>();
        //根据收货标识将list分割为原始记录与分配的明细纪录
        for (PageData p : headList) {
            if (Const.ZERO_CH.equals(p.getString("ASSIGNED_FLG"))) {
                p.put("COMB_ID", p.getString("PRODUCT_ID") + Const.SPLIT_LINE + p.getString("STOCKMGROUT_ID"));
                tmpList.add(p);
            } else {
                assignList.add(p);
            }
        }
        // if(!assignList.isEmpty()) {
        //List<PageData> datas = filterById(tmpList);
        srcList.addAll(tmpList);

        srcList.sort((e1, e2) -> {
            if (e1.getInteger("OUTSTOCK_NO") == e2.getInteger("OUTSTOCK_NO")) {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });

           /* if(dtlList == null || dtlList.isEmpty()) {
                return;
            }
           assignList.addAll(dtlList);*/
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
        // }

    }

    @Deprecated
    public void findAssignOutAutolistPage_bak(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("AssignOutMapper.findAssignOutAutoHeadlist1Page", page);
        //List<PageData> dtlList = (List<PageData>)dao.findForList("AssignOutMapper.findAssignOutAutoDtllist1Page", page);

        if (headList == null || headList.isEmpty()) {
            return;
        }
        List<PageData> tmpList = new ArrayList<>();
        //根据收货标识将list分割为原始记录与分配的明细纪录
        for (PageData p : headList) {
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

           /* if(dtlList == null || dtlList.isEmpty()) {
                return;
            }
            assignList.addAll(dtlList);*/
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("OUTSTOCK_NO") == e2.getInteger("OUTSTOCK_NO")) {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
    }

    @Deprecated
    public void findAssignOutCacellistPage_bak(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("AssignOutMapper.findAssignOutCacellist1Page", page);
        if (list == null || list.isEmpty()) {
            return;
        }

        List<PageData> tmpList = new ArrayList<>();
        //根据收货标识将list分割为原始记录与分配的明细纪录
        for (PageData p : list) {
            if (Const.ZERO_CH.equals(p.getString("ASSIGNED_FLG"))) {
                tmpList.add(p);
            } else {
                p.put("COMB_ID", p.getString("STOCKDTLMGROUT_ID") + Const.SPLIT_LINE + p.getString("STOCKMGROUT_ID"));
                assignList.add(p);
            }
        }
        //  if(!assignList.isEmpty()) {
        List<PageData> datas = filterById(tmpList);
        srcList.addAll(datas);

        srcList.sort((e1, e2) -> {
            if (e1.getInteger("OUTSTOCK_NO") == e2.getInteger("OUTSTOCK_NO")) {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            } else if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
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
        //  }

    }
}
