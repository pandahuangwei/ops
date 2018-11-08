package com.hw.service.outstock;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 *
 * 发货
 * Created：panda.HuangWei
 * Date：2016-12-8
 */

@Service("depotOutService")
public class DepotOutService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("DepotOutMapper.datalistPage", page);
    }

    //按配载出货界面列表
    public List<PageData> list2(Page page) throws Exception {
        List<PageData> srclist = (List<PageData>) dao.findForList("DepotOutMapper.cargoDatalistPage", page);
        if (srclist == null || srclist.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> ids = new ArrayList<>();
        for(PageData p : srclist) {
            ids.add(p.getString("CARGOOUT_ID"));
        }
        List<PageData> dtllist = (List<PageData>) dao.findForList("DepotOutMapper.findOutNoByCargoIds", ids);
        Map<String, List<String>> cargoIdMap = groupByCargoId(dtllist);
        for (PageData p : srclist) {
            p.put("OUTSTOCK_NO", VaryUtils.genString(cargoIdMap.get(p.getString("CARGOOUT_ID"))));
        }
        return srclist;
    }


    public void findDtlDepotOutLooklistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {

        List<PageData> headList = (List<PageData>) dao.findForList("DepotOutMapper.findDtlDepotOutLooklistHead", page);
        List<PageData> list = (List<PageData>) dao.findForList("DepotOutMapper.findDtlDepotOutLooklistPage", page);

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

    //发货
    public void findDtlDepotOutSavelistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("DepotOutMapper.findDtlDepotOutSavelistPage", page);
        if (list == null || list.isEmpty()) {
            return;
        }
        List<PageData> tmpList = new ArrayList<>();
        //根据收货标识将list分割为原始记录与分配的明细纪录
        for (PageData p : list) {
            if (Const.ZERO_CH.equals(p.getString("ASSIGNED_FLG"))) {
                tmpList.add(p);
            } else {
                p.put("COMB_IDS",p.getString("STOCKDTLMGROUT_ID")+Const.SPLIT_LINE+p.getString("STOCKMGROUT_ID"));
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

    //发货
    public void findDtlDepotOutCacellistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("DepotOutMapper.findDtlDepotOutCacellistPage", page);
        if (list == null || list.isEmpty()) {
            return;
        }
        List<PageData> tmpList = new ArrayList<>();
        //根据收货标识将list分割为原始记录与分配的明细纪录
        for (PageData p : list) {
            if (Const.ZERO_CH.equals(p.getString("ASSIGNED_FLG"))) {
                tmpList.add(p);
            } else {
                p.put("COMB_IDS",p.getString("STOCKDTLMGROUT_ID")+Const.SPLIT_LINE+p.getString("STOCKMGROUT_ID"));
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

    public PageData findDtlDepotCargoLooklistPage(List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("DepotOutMapper.findDtlDepotLooklistPage", page);
        PageData pd = (PageData)dao.findForObject("DepotOutMapper.findCargoById", page);
        assignList.addAll(list);
        assignList.sort((e1, e2) -> {
            if (e1.getInteger("ROW_NO") == e2.getInteger("ROW_NO")) {
                return e1.getUtilDate("CREATE_TM").compareTo(e2.getUtilDate("CREATE_TM"));
            } else {
                return e1.getInteger("ROW_NO") - e2.getInteger("ROW_NO");
            }
        });
        return pd;
    }


    //发货
    public void findDtlDepotCargolistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("DepotOutMapper.findDtlDepotCargolistPage", page);
        if (list == null || list.isEmpty()) {
            return;
        }
        List<PageData> tmpList = new ArrayList<>();
        //根据收货标识将list分割为原始记录与分配的明细纪录
        for (PageData p : list) {
            tmpList.add(p);
            p.put("COMB_IDS", p.getString("STOCKDTLMGROUT_ID") + Const.SPLIT_LINE + p.getString("STOCKMGROUT_ID"));
            assignList.add(p);

        }
        List<PageData> datas = filterByCargoId(tmpList);
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

    //发货
    public void findDtlDepotCargoCacellistPage(List<PageData> srcList, List<PageData> assignList, Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("DepotOutMapper.findDtlDepotCargoCacellist2Page", page);
        if (list == null || list.isEmpty()) {
            return;
        }
        List<PageData> tmpList = new ArrayList<>();
        //根据收货标识将list分割为原始记录与分配的明细纪录
        for (PageData p : list) {
            tmpList.add(p);
                p.put("COMB_IDS",p.getString("STOCKDTLMGROUT_ID")+Const.SPLIT_LINE+p.getString("STOCKMGROUT_ID"));
                assignList.add(p);

        }
        List<PageData> datas = filterByCargoId(tmpList);
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

    //按出库单整单、按明细发货
    public void saveDepot(PageData pd) throws Exception {
        String opt_even = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_1.equals(opt_even)) {
            saveDepotStock(pd);
        } else if (Const.OPT_EVEN_2.equals(opt_even)) {
            saveDepotStockDtl(pd);
        }
    }



    ///按出库单整单、按明细发货取消发货
    public void saveDepotCancel(PageData pd) throws Exception {
        String opt_even = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_1.equals(opt_even)) {
            saveDepotCancelStock(pd);
        } else if (Const.OPT_EVEN_2.equals(opt_even)) {
            saveDepotCancelStockDtl(pd);
        }
    }

    //按配载单整单、按明细发货
    public void saveDepotCargo(PageData pd) throws Exception {
        String opt_even = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_1.equals(opt_even)) {
            saveDepotCargoId(pd);
        } else if (Const.OPT_EVEN_2.equals(opt_even)) {
            saveDepotStockDtl(pd);
        }
    }

    ///按配载单整单、按明细发货取消发货
    public void saveDepotCargoCancel(PageData pd) throws Exception {
        String opt_even = pd.getString("OPT_EVEN");
        if (Const.OPT_EVEN_1.equals(opt_even)) {
            saveDepotCancelCargo(pd);
        } else if (Const.OPT_EVEN_2.equals(opt_even)) {
            saveDepotCancelStockDtl(pd);
        }
    }

    /**
     * 按配载单ID进行全部发货.<br>
     *  1.根据配载单ID找到该单据已经配载的纪录;<br>
     * 2.更改明细的发货状态、发货数量(DEPOT_STATE DEPOT_EA).<br>
     * 3.更改箱号纪录的发货状态(DEPOT_STATE).<br>
     * 4.更改该出库单主表的发货状态(DEPOT_STATE).
     * @param pd 前台参数
     */
    private void saveDepotCargoId(PageData pd) throws Exception {
        List<String> pIdList = pd.getList("STOCKMGROUT_ID_CHOSE");

        Set<String> cargoDtlIds = findCargoDtlByCargoIds(pIdList);
       if(cargoDtlIds == null || cargoDtlIds.isEmpty()) {
           return;
       }
        //根据配载单Id获取所有的明细
        List<PageData> dataList = findDtlCargoByCargoIds(pIdList);

        //过滤未发货的明细并按出库单ID分组
        Map<String, List<PageData>> pidMap = filterAndGroupById(dataList,cargoDtlIds);
        //更改明细状态，发货数量、箱号表的发货状态，出库单的发货状态
        modifyDepotState(pidMap);
    }

    /**
     *   按配载单明细ID进行单条纪录发货.<br>
     *  1.根据配载单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的发货状态、发货数量(DEPOT_STATE DEPOT_EA).<br>
     * 3.更改箱号纪录的发货状态(DEPOT_STATE).<br>
     * 4.更改该出库单主表的发货状态(DEPOT_STATE).
     * @param pd 前台参数
     */
    private void saveDepotCargoDtl(PageData pd) throws Exception {
        saveDepotStockDtl(pd);
    }

    /**
     *   按配载单ID进行全部取消发货.<br>
     *  1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的发货状态、发货数量(DEPOT_STATE DEPOT_EA).<br>
     * 3.更改箱号纪录的发货状态(DEPOT_STATE).<br>
     * 4.更改该出库单主表的发货状态(DEPOT_STATE).
     * @param pd 前台参数
     */
    private void saveDepotCancelCargo(PageData pd) throws Exception {
        List<String> pIdList = pd.getList("STOCKMGROUT_ID_CHOSE");
        //根据Id获取所有的明细
        List<PageData> dataList = findDtlCargoByCargoIds(pIdList);
        //过滤未发货的明细并按出库单ID分组
        Map<String, List<PageData>> pidMap = filterAndGroupById4Cancel(dataList);
        //更改明细状态，发货数量、箱号表的发货状态，出库单的发货状态
        modifyDepotStateCacel(pidMap);
    }

    /**
     *   按配载单明细ID进行单条纪录发货.<br>
     *  1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的发货状态、发货数量(DEPOT_STATE DEPOT_EA).<br>
     * 3.更改箱号纪录的发货状态(DEPOT_STATE).<br>
     * 4.更改该出库单主表的发货状态(DEPOT_STATE).
     * @param pd 前台参数
     */
    private void saveDepotCancelCargoDtl(PageData pd) throws Exception {
        saveDepotCancelStockDtl(pd);
    }

    /**
     * 按出库单ID进行全部发货.<br>
     *  1.根据出库单ID找到该单据已经配载的纪录;<br>
     * 2.更改明细的发货状态、发货数量(DEPOT_STATE DEPOT_EA).<br>
     * 3.更改箱号纪录的发货状态(DEPOT_STATE).<br>
     * 4.更改该出库单主表的发货状态(DEPOT_STATE).
     * @param pd 前台参数
     */
    private void saveDepotStock(PageData pd) throws Exception {

        List<String> pIdList = pd.getList("STOCKMGROUT_ID_CHOSE");
        //根据Id获取所有的明细
        List<PageData> dataList = findAllStockDtlByPids(pIdList);
        //过滤未发货的明细并按出库单ID分组
        Map<String, List<PageData>> pidMap = filterAndGroupById(dataList);
        //更改明细状态，发货数量、箱号表的发货状态，出库单的发货状态
        modifyDepotState(pidMap);
    }

    /**
     *   按出库单明细ID进行单条纪录发货.<br>
     *  1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的发货状态、发货数量(DEPOT_STATE DEPOT_EA).<br>
     * 3.更改箱号纪录的发货状态(DEPOT_STATE).<br>
     * 4.更改该出库单主表的发货状态(DEPOT_STATE).
     * @param pd 前台参数
     */
    private void saveDepotStockDtl(PageData pd) throws Exception {
        //1 查询往前组装的id为 stockdtlmgrout_id-stockmgrout_id
        String[] conbimIds = StringUtils.split(pd.getString("STOCKMGROUT_ID_CHOSE"), Const.SPLIT_COMMA);
        if (conbimIds == null || conbimIds.length == 0) {
            return;
        }
        String cargoType = pd.getString("DEPOT_TYPE");
        List<String> dtlIdList = new ArrayList<>();
        List<String> pidList = new ArrayList<>();
        //2 按出库单ID分组,并且返回处理的明细,用于构建配载主表与出库单明细的关系
        Map<String, Set<String>> pidListMap = genPidListMap(conbimIds, dtlIdList, pidList);

        //根据Id获取所有的明细
        List<PageData> dataList = findAllStockDtlByPids(pidList);
        Map<String, List<PageData>> pidMap = filterAndGroupById(dataList, dtlIdList);
        //更改明细状态，发货数量、箱号表的发货状态，出库单的发货状态
        modifyDepotState(pidMap);
    }

    /**
     *   按出库单ID进行全部取消发货.<br>
     *  1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的发货状态、发货数量(DEPOT_STATE DEPOT_EA).<br>
     * 3.更改箱号纪录的发货状态(DEPOT_STATE).<br>
     * 4.更改该出库单主表的发货状态(DEPOT_STATE).
     * @param pd 前台参数
     */
    private void saveDepotCancelStock(PageData pd) throws Exception {
        List<String> pIdList = pd.getList("STOCKMGROUT_ID_CHOSE");
        //根据Id获取所有的明细
        List<PageData> dataList = findAllStockDtlByPids(pIdList);
        //过滤未发货的明细并按出库单ID分组
        Map<String, List<PageData>> pidMap = filterAndGroupById4Cancel(dataList);
        //更改明细状态，发货数量、箱号表的发货状态，出库单的发货状态
        modifyDepotStateCacel(pidMap);
    }

    /**
     *   按出库单明细ID进行单条纪录发货.<br>
     *  1.根据出库单ID找到该单据已经分配的纪录;<br>
     * 2.更改明细的发货状态、发货数量(DEPOT_STATE DEPOT_EA).<br>
     * 3.更改箱号纪录的发货状态(DEPOT_STATE).<br>
     * 4.更改该出库单主表的发货状态(DEPOT_STATE).
     * @param pd 前台参数
     */
    private void saveDepotCancelStockDtl(PageData pd) throws Exception {
        //1 查询往前组装的id为 stockdtlmgrout_id-stockmgrout_id
        String[] conbimIds = StringUtils.split(pd.getString("STOCKMGROUT_ID_CHOSE"), Const.SPLIT_COMMA);
        if (conbimIds == null || conbimIds.length == 0) {
            return;
        }
        String cargoType = pd.getString("DEPOT_TYPE");
        List<String> dtlIdList = new ArrayList<>();
        List<String> pidList = new ArrayList<>();
        //2 按出库单ID分组,并且返回处理的明细,用于构建配载主表与出库单明细的关系
        Map<String, Set<String>> pidListMap = genPidListMap(conbimIds, dtlIdList, pidList);

        //根据Id获取所有的明细
        List<PageData> dataList = findAllStockDtlByPids(pidList);
        Map<String, List<PageData>> pidMap = filterAndGroupById4Cancel(dataList, dtlIdList);
        //更改明细状态，发货数量、箱号表的发货状态，出库单的发货状态
        modifyDepotStateCacel(pidMap);
        
    }

    //修改出库单的发货状态 map(出库单ID，出库明细)
    private void modifyDepotState(Map<String, List<PageData>> map) throws Exception{
        for (Map.Entry<String, List<PageData>> ent : map.entrySet()) {
            //按产品分组后，逐组产品处理
            Map<String, List<PageData>> prodMap = genProdMap(ent.getValue());
            boolean updateFlg = false;
            //按产品分组处理处理
            for(Map.Entry<String, List<PageData>> dtl : prodMap.entrySet()) {
                List<PageData> dtlList = dtl.getValue();
                if (dtlList.size() == 1) {
                    continue;
                }
                //升序
                dtlList.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
                PageData srcPd = dtlList.get(0);

                //第一个是原始纪录，从第二个开始循环
                List<String> dtlId = new ArrayList<>();
                int depotEa = 0;
                for(int i=1,len=dtlList.size();i<len;i++) {
                    PageData dtlPd = dtlList.get(i);
                    depotEa += dtlPd.getInteger("CARGO_EA");
                    dtlId.add(dtlPd.getString("STOCKDTLMGROUT_ID"));
                }
                if(depotEa == 0) {
                    continue;
                }
                srcPd.put("DEPOT_EA", srcPd.getInteger("DEPOT_EA")+depotEa);
                //更改原始纪录的发货状态、发货数量(DEPOT_STATE DEPOT_EA).
                modifySrcDepotState(srcPd);

                //更改明细的发货状态、发货数量(DEPOT_STATE PICK_EA).
                modifyStockDepotStateAll(dtlId);
                //更改箱号纪录的发货状态(DEPOT_STATE).
                modifyBoxPickStateALL(dtlId);
                updateFlg = true;
            }
           if(updateFlg) {
               updateDepotState(ent.getKey());
           }
        }
    }

    //修改出库单的发货状态 map(出库单ID，出库明细)
    private void modifyDepotStateCacel(Map<String, List<PageData>> map) throws Exception{
        for (Map.Entry<String, List<PageData>> ent : map.entrySet()) {
            //按产品分组后，逐组产品处理
            Map<String, List<PageData>> prodMap = genProdMap(ent.getValue());
            boolean updateFlg = false;
            //按产品分组处理处理
            for(Map.Entry<String, List<PageData>> dtl : prodMap.entrySet()) {
                List<PageData> dtlList = dtl.getValue();
                if (dtlList.size() == 1) {
                    continue;
                }
                //升序
                dtlList.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
                PageData srcPd = dtlList.get(0);

                //第一个是原始纪录，从第二个开始循环
                List<String> dtlId = new ArrayList<>();
                int depotEa = 0;
                for(int i=1,len=dtlList.size();i<len;i++) {
                    PageData dtlPd = dtlList.get(i);
                    depotEa += dtlPd.getInteger("CARGO_EA");
                    dtlId.add(dtlPd.getString("STOCKDTLMGROUT_ID"));
                }
                if(depotEa == 0) {
                    continue;
                }
                int depot_ea = srcPd.getInteger("DEPOT_EA") - depotEa;
                depot_ea = depot_ea <0?0:depot_ea;
                srcPd.put("DEPOT_EA", depot_ea);
                //更改原始纪录的发货状态、发货数量(DEPOT_STATE DEPOT_EA).
                modifySrcDepotState(srcPd);
                //更改明细的发货状态、发货数量(DEPOT_STATE PICK_EA).
                modifyStockDepotStateNone(dtlId);
                //更改箱号纪录的发货状态(DEPOT_STATE).
                modifyBoxPickStateNone(dtlId);
                updateFlg = true;
            }
            if(updateFlg) {
                updateDepotState(ent.getKey());
            }
        }
    }

    /**
     * 根据出库单ID去更新出库单表头的发货状态.逻辑:<br>
     *     1、如果所有的已发货EA数都是0，则是：未发货;<br>
     *     2、如果所有的已发货EA数都大于等于期望EA的话，则是:全部发货;<br>
     *     3、其他情况则是:部分发货
     * @param stockmgroutId 出库单ID
     */
    private void updateDepotState(String stockmgroutId) {
        try {
            PageData pd = new PageData();
            pd.put("STOCKMGROUT_ID",stockmgroutId);
            // EA_EA, ASSIGNED_EA
            List<PageData> list = (List<PageData>) dao.findForList("DepotOutMapper.findDtlOutList", pd);

            int dtlCnt = list.size();
            int eaCnt = 0;
            int depotEACnt = 0;
            for(PageData p :list) {
                //期望EA、已分配EA
                int EA = p.getInteger("EA_EA");
                int  depotEA = p.getInteger("DEPOT_EA");
                if(depotEA == 0) {
                    depotEACnt += 1;
                }

                if(depotEA >= EA) {
                    eaCnt += 1;
                }
            }

            String assignState = WmsEnum.DepotState.PART.getItemKey();
            if(depotEACnt == dtlCnt) {
                assignState = WmsEnum.DepotState.NONE.getItemKey();
            } else if(eaCnt == dtlCnt) {
                assignState = WmsEnum.DepotState.ALL.getItemKey();
            }

            pd.put("DEPOT_STATE",assignState);
            pd.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
            pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
            //修改出库单表头的库存分配状态
            dao.update("DepotOutMapper.editHeadStockDepotInfo",pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modifyStockDepotStateAll(List<String> list) throws Exception {
        dao.update("DepotOutMapper.editDtlStockDepotStateAll", list);
    }

    private void modifyStockDepotStateNone(List<String> list) throws Exception {
        dao.update("DepotOutMapper.editDtlStockDepotStateNone", list);
    }

    //更改发货状态为发货141
    private void modifyBoxPickStateALL(List<String> idList) throws Exception {
        Page page = new Page();
        PageData pd = new PageData();
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
        pd.put("IDS", idList);
        page.setPd(pd);
        dao.update("DepotOutMapper.editDtlBoxDepotStateALL",page);
    }

    //更改发货状态为发货139
    private void modifyBoxPickStateNone(List<String> idList) throws Exception {
        Page page = new Page();
        PageData pd = new PageData();
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
        pd.put("IDS", idList);
        page.setPd(pd);
        dao.update("DepotOutMapper.editDtlBoxDepotStateNone",page);
    }

    private List<PageData> findAllStockDtlByPids(List<String> list)  throws Exception{
        return (List<PageData>) dao.findForList("DepotOutMapper.findDtlCargoPdByIds", list);
    }

    private List<PageData> findDtlCargoByCargoIds(List<String> list)  throws Exception{
        return (List<PageData>) dao.findForList("DepotOutMapper.findDtlCargoByCargoIds", list);
    }


    //通过配载单，找到配载单的明细
    private Set<String> findCargoDtlByCargoIds(List<String> list)  throws Exception{
        List<PageData> lst =(List<PageData>) dao.findForList("DepotOutMapper.findCargoDtlByCargoIds", list);
        Set<String> rs = new HashSet<>();
       if(lst == null || lst.isEmpty()) {
           return rs;
       }
        for(PageData p : lst) {
            rs.add(p.getString("STOCKDTLMGROUT_ID"));
        }
        return rs;
    }


    //去除重复的纪录，只显示主表纪录
    private List<PageData> filterById(List<PageData> srcList) {

        Map<String,PageData> map = new HashMap<>();
        for(PageData p : srcList) {
            String pid = p.getString("STOCKMGROUT_ID");
            PageData data = map.get(pid);
            if (data == null) {
                data = new PageData();
                data.put("OUTSTOCK_NO",p.getString("OUTSTOCK_NO"));
                data.put("PICK_STATE_HEAD",p.getString("PICK_STATE_HEAD"));
                data.put("CARGO_STATE_HEAD",p.getString("CARGO_STATE_HEAD"));
                data.put("DEPOT_STATE_HEAD",p.getString("DEPOT_STATE_HEAD"));
                data.put("EA_EA",p.getString("EA_EA"));
                data.put("ASSIGNED_EA",p.getString("ASSIGNED_EA"));
                data.put("PICK_EA",p.getString("PICK_EA"));
                data.put("STOCKMGROUT_ID",pid);
            } else {
                if (Const.ZERO_CH.equals(p.getString("ASSIGNED_FLG"))) {
                    data.put("EA_EA",p.getInteger("EA_EA")+data.getInteger("EA_EA"));
                    data.put("ASSIGNED_EA",p.getInteger("ASSIGNED_EA")+data.getInteger("ASSIGNED_EA"));
                    data.put("PICK_EA",p.getInteger("PICK_EA")+data.getInteger("PICK_EA"));
                }
            }
            map.put(pid,data);
        }


        return new ArrayList<>(map.values());
    }

    //去除重复的纪录，只显示主表纪录
    private List<PageData> filterByCargoId(List<PageData> srcList) {
        List<PageData> rs = new ArrayList<>();
        for(PageData p : srcList) {
            if(!isContain(rs,p)) {
                rs.add(p);
                continue;
            }

            for(PageData p2 : rs) {
                if(p2.getString("CARGOOUT_ID").equals(p.getString("CARGOOUT_ID"))) {
                    //EA_EA ASSIGNED_EA PICK_EA
                    p2.put("EA_EA_TOTAL",p2.getInteger("EA_EA")+p.getInteger("EA_EA"));
                    p2.put("ASSIGNED_EA_TOTAL",p2.getInteger("ASSIGNED_EA")+p.getInteger("ASSIGNED_EA"));
                    p2.put("PICK_EA_TOTAL",p2.getInteger("PICK_EA")+p.getInteger("PICK_EA"));
                    p2.put("CARGO_EA_TOTAL",p2.getInteger("CARGO_EA")+p.getInteger("CARGO_EA"));
                    p2.put("DEPOT_EA_TOTAL",p2.getInteger("DEPOT_EA")+p.getInteger("DEPOT_EA"));
                }
            }
        }
        return rs;
    }

    /**
     * 更新原始纪录的发货状态、发货数量
     * @param srcPd 非发货明细
     * @throws Exception e
     */
    private void modifySrcDepotState(PageData srcPd) throws Exception {
        int EA_EA = srcPd.getInteger("EA_EA");
        int PICK_EA = srcPd.getInteger("DEPOT_EA");
        String depotState;
        if(PICK_EA == 0) {
            depotState = WmsEnum.DepotState.NONE.getItemKey();
        } else if(EA_EA >PICK_EA){
            depotState = WmsEnum.DepotState.PART.getItemKey();
        } else {
            depotState = WmsEnum.DepotState.ALL.getItemKey();
        }
        srcPd.put("DEPOT_STATE",depotState);
        dao.update("DepotOutMapper.editDtlStockDepotStateByID",srcPd);
    }

    //判断列表中的是否包含该实体
    private boolean isContain(List<PageData> list,PageData pd) {
        if(list.isEmpty()) {
            return false;
        }
        for(PageData p : list) {
            if(p.getString("STOCKMGROUT_ID").equals(pd.getString("STOCKMGROUT_ID"))) {
                return true;
            }
        }
        return false;
    }

    //将得到的明细按产品分组
    private Map<String,List<PageData>> genProdMap(List<PageData> list) {
        Map<String,List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for(PageData p : list) {
            String prodId = p.getString("PRODUCT_ID");

            List<PageData> lst = map.get(prodId);
            if(lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(prodId,lst);
        }
        return map;
    }

    //过滤未发货的明细并按出库单ID分组
    private Map<String, List<PageData>> filterAndGroupById(List<PageData> list) {
        Map<String, List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            if (Const.ASSIGNED_FLG_YES.equals(p.getString("ASSIGNED_FLG")) && WmsEnum.DepotState.ALL.getItemKey().equals(p.getString("DEPOT_STATE"))) {
                continue;
            }

            String pid = p.getString("STOCKMGROUT_ID");
            List<PageData> lst = map.get(pid);
            if (lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(pid, lst);


        }
        return map;
    }

    //过滤未发货的明细并按出库单ID分组
    private Map<String, List<PageData>> filterAndGroupById(List<PageData> list, Set<String> cargoDtlIds) {
        Map<String, List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (PageData p : list) {
            if (Const.ASSIGNED_FLG_YES.equals(p.getString("ASSIGNED_FLG")) && WmsEnum.DepotState.ALL.getItemKey().equals(p.getString("DEPOT_STATE"))) {
                continue;
            }
            if (Const.ASSIGNED_FLG_NO.equals(p.getString("ASSIGNED_FLG")) || cargoDtlIds.contains(p.getString("STOCKDTLMGROUT_ID"))) {
                String pid = p.getString("STOCKMGROUT_ID");
                List<PageData> lst = map.get(pid);
                if (lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(p);
                map.put(pid, lst);
            }

        }
        return map;
    }

    //过滤未发货的明细并按出库单ID分组
    private Map<String,List<PageData>> filterAndGroupById(List<PageData> list,List<String> dtlIdList) {
        Map<String,List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        Set<String> dtlIdSet = new HashSet<>(dtlIdList);
        for(PageData p : list) {
            if(Const.ASSIGNED_FLG_YES.equals(p.getString("ASSIGNED_FLG")) &&  WmsEnum.DepotState.ALL.getItemKey().equals(p.getString("DEPOT_STATE"))) {
                continue;
            }
            if( Const.ASSIGNED_FLG_NO.equals(p.getString("ASSIGNED_FLG")) || dtlIdSet.contains(p.getString("STOCKDTLMGROUT_ID"))) {
                String pid = p.getString("STOCKMGROUT_ID");
                List<PageData> lst = map.get(pid);
                if(lst == null) {
                    lst = new ArrayList<>();
                }
                lst.add(p);
                map.put(pid,lst);
            }

        }
        return map;
    }

    //过滤未发货的明细并按出库单ID分组
    private Map<String,List<PageData>> filterAndGroupById4Cancel(List<PageData> list,List<String> dtlIdList) {
        Map<String,List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        Set<String> dtlIdSet = new HashSet<>(dtlIdList);
        for(PageData p : list) {
            if(Const.ASSIGNED_FLG_YES.equals(p.getString("ASSIGNED_FLG")) && WmsEnum.DepotState.NONE.getItemKey().equals(p.getString("DEPOT_STATE"))) {
                continue;
            }
            if (Const.ASSIGNED_FLG_NO.equals(p.getString("ASSIGNED_FLG")) || dtlIdSet.contains(p.getString("STOCKDTLMGROUT_ID"))) {


            String pid = p.getString("STOCKMGROUT_ID");
            List<PageData> lst = map.get(pid);
            if(lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(pid,lst);
            }
        }
        return map;
    }

    //过滤未发货的明细并按出库单ID分组
    private Map<String,List<PageData>> filterAndGroupById4Cancel(List<PageData> list) {
        Map<String,List<PageData>> map = new HashMap<>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for(PageData p : list) {
            if(Const.ASSIGNED_FLG_YES.equals(p.getString("ASSIGNED_FLG")) && WmsEnum.DepotState.NONE.getItemKey().equals(p.getString("DEPOT_STATE"))) {
                continue;
            }

            String pid = p.getString("STOCKMGROUT_ID");
            List<PageData> lst = map.get(pid);
            if(lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(p);
            map.put(pid,lst);
        }
        return map;
    }


    /**
     * 将组合ID(STOCKDTLMGROUT_ID-STOCKMGROUT_ID)按出库单ID分组，以及明细
     *
     * @param conbimIds 组合ID
     * @return map 主表ID，明细Id
     */
    private Map<String, Set<String>> genPidListMap(String[] conbimIds, List<String> dtlIdList, List<String> pidList) {
        Map<String, Set<String>> map = new HashMap<>();
        if (conbimIds == null || conbimIds.length == 0) {
            return map;
        }
        for (String cIds : conbimIds) {
            String[] arr = StringUtils.split(cIds, Const.SPLIT_LINE);
            String pId = arr[1];
            Set<String> list = map.get(pId);
            if (list == null) {
                list = new HashSet<>();
            }
            dtlIdList.add(arr[0]);
            pidList.add(pId);
            list.add(arr[0]);
            map.put(pId, list);
        }
        return map;
    }

    private Map<String,List<String>> groupByCargoId(List<PageData> dtllist) {
        Map<String, List<String>> map = new HashMap<>();
        if (dtllist == null || dtllist.isEmpty()) {
            return map;
        }

        for(PageData p : dtllist) {
            String cargoId = p.getString("CARGOOUT_ID");
            List<String> lst = map.get(cargoId);
            if(lst == null) {
                lst = new ArrayList<>();
            }
            lst.add(p.getString("OUTSTOCK_NO"));
            map.put(cargoId,lst);
        }
        return map;
    }
}
