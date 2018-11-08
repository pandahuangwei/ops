package com.hw.service.outstock;

import com.hw.cache.BaseInfoCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-12-4
 */

@Service("cargoOutService")
public class CargoOutService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "stockmgroutService")
    private StockMgrOutService stockmgroutService;

    //进入菜单的首页
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("CargoOutMapper.datalistPage", page);
    }

    //分配界面
    public List<PageData> findDtl2CargoOutlistPage(Page page) throws Exception {

        List<PageData> list = (List<PageData>) dao.findForList("CargoOutMapper.findDtl2CargoOutlistPage", page);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        for(PageData p :list) {
            p.put("CONBI_IDS",p.getString("STOCKDTLMGROUT_ID")+Const.SPLIT_LINE+p.getString("STOCKMGROUT_ID"));
            p.put("BIG_BOX_NO", BaseInfoCache.getInstance().getBigBoxNo(p.getString("STOCKDTLMGROUT_ID")));
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
   //findCargoLookHeadlist1Page findCargoLookDtllist1Page
    public void findDtlCargoLooklistPage( List<PageData> srcList,List<PageData> assignList,Page page)  throws Exception {
        List<PageData> headList = (List<PageData>) dao.findForList("CargoOutMapper.findCargoLookHeadlist1Page", page);
        List<PageData> dtlList = (List<PageData>) dao.findForList("CargoOutMapper.findCargoLookDtllist1Page", page);
        if (headList == null || headList.isEmpty()) {
           return;
        }
        List<PageData> datas = filterById(headList);
        srcList.addAll(datas);
        assignList.addAll(dtlList);
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
    public List<PageData> findDtl2CargoCancellistPage(Page page) throws Exception {

        List<PageData> list = (List<PageData>) dao.findForList("CargoOutMapper.findDtl2CargoCancellistPage", page);
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }

        for(PageData p :list) {
            p.put("CONBI_IDS",p.getString("STOCKDTLMGROUT_ID")+Const.SPLIT_LINE+p.getString("STOCKMGROUT_ID"));
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


    public void saveCargo(PageData pd) throws Exception {
        //1 查询往前组装的id为 stockdtlmgrout_id-stockmgrout_id
        String[] conbimIds = StringUtils.split(pd.getString("STOCKMGROUT_ID_CHOSE"), Const.SPLIT_COMMA);
        if (conbimIds == null || conbimIds.length == 0) {
            return;
        }
        String cargoType = pd.getString("CARGO_TYPE");
        List<String> dtlIdList = new ArrayList<>();
        List<String> combList = new ArrayList<>();
        //2 按出库单ID分组,并且返回处理的明细,用于构建配载主表与出库单明细的关系
        Map<String, Set<String>> pidListMap = genPidListMap(conbimIds, dtlIdList, combList);

        saveCargo(pd, pidListMap, dtlIdList,combList, cargoType);

    }

    /**
     * 保存配载(详情配载、缺省配载)
     *
     * @param pd 全台参数
     * @throws Exception 异常
     */
    private void saveCargo(PageData pd, Map<String, Set<String>> pidListMap, List<String> dtlIdList,  List<String> combList, String cargoType) throws Exception {
        List<String> cargoIdList = new ArrayList<>();
        //3 循环按单据处理
        for (Map.Entry<String, Set<String>> ent : pidListMap.entrySet()) {
            //4 根据出库单Id，获取其中已经拣货的全部有效数据
            List<PageData> allDtlByPid = findAllDtlByPid(ent.getKey());
            //5 过滤出需要处理的数据
            List<PageData> pageDatas;
            if(Const.OPT_EVEN_4.equals(cargoType)) {
                pageDatas = filterCancelList(allDtlByPid, ent.getValue());
            } else {
                pageDatas = filterActList(allDtlByPid, ent.getValue());
            }

            //6 按产品分组之后，逐组处理
            Map<String, List<PageData>> prodDtlMap = genProdDtlMap(pageDatas);

            //7 处理出库单单身配载状态、单头状态，箱号表的状态
            if (Const.OPT_EVEN_4.equals(cargoType)) {
                cargoIdList.addAll(findCargoPidByDtlId(dtlIdList, ent.getKey()));
                dowithOneStockCargoCancel(prodDtlMap, ent.getKey());
            } else {
                dowithOneStockCargoSave(prodDtlMap, ent.getKey(),cargoType);
            }
        }

        //1 代表详情配载 2 缺省配载
        if (Const.OPT_EVEN_1.equals(cargoType)) {
            //详情配载，需要保存配载表与出库单的明细关系
            saveCargo(pd, combList);
        } else if (Const.OPT_EVEN_4.equals(cargoType)) {
            //取消配载的话，如果全部明细删掉的话，删除配载单
            deleteCargo(cargoIdList);
        }
    }


    /**
     * 保存配载单的主信息，子信息与出库单明细的关联信息
     *
     * @param pd        前台传入参数
     * @param dtlIdList 明细表的Id
     */
    private void saveCargo(PageData pd, List<String> dtlIdList) throws Exception {
        String CARGOOUT_ID = UuidUtil.get32UUID();
        pd.put("CARGOOUT_ID", CARGOOUT_ID);    //主键
        pd.put("CREATE_EMP", SessionUtil.getCurUserName());    //创建人
        pd.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间
        pd.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
        pd.put("DEL_FLG", 0);    //删除
        //状态变为全部配载
        pd.put("CARGO_STATE", WmsEnum.CargoState.ALL.getItemKey());    //修改时间

        List<PageData> dtlList = new ArrayList<>(dtlIdList.size());
        PageData dtlPd;
        for (String ids : dtlIdList) {
            String[] arr = StringUtils.split(ids,Const.SPLIT_LINE);
            dtlPd = new PageData();
            dtlPd.put("STOCKDTLMGROUT_ID", arr[0]);
            dtlPd.put("CARGOOUT_ID", CARGOOUT_ID);
            dtlPd.put("STOCKMGROUT_ID", arr[1]);

            dtlList.add(dtlPd);
        }
        dao.save("CargoOutMapper.saveCargo", pd);
        dao.batchSave("CargoOutMapper.saveCargoDtl", dtlList);
    }

    /**
     * 删除配载单
     *
     * @param cargoIdList 明细表的Id
     */
    private void deleteCargo(List<String> cargoIdList) throws Exception {
        if (cargoIdList == null || cargoIdList.isEmpty()) {
            return;
        }
        List<PageData> datas = findCargoCntById(cargoIdList);
        for (PageData p : datas) {
            if (p.getInteger("CNT") == 0) {
                dao.delete("CargoOutMapper.deleteCargoById", p.getString("CARGOOUT_ID"));
            }
        }
    }

    /**
     * 处理一张出库单的状态及其明细对应的箱号纪录
     *
     * @param prodDtlMap 按产品分组后的明细
     * @param stockId    出库单主表Id
     * @param cargoType  配载类型 1 详情配载，2 缺省配载
     */
    private void dowithOneStockCargoSave(Map<String, List<PageData>> prodDtlMap, String stockId, String cargoType) throws Exception {

        boolean updFlag = false;
        for (Map.Entry<String, List<PageData>> ent : prodDtlMap.entrySet()) {
            List<PageData> list = ent.getValue();
            if (list == null || list.size() == 1) {
                continue;
            }
            //按分配标记升序，第一个就是原始纪录，从第二个开始处理即可
            list.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
            PageData srcDtlPd = list.get(0);
            String STOCKMGROUT_ID = srcDtlPd.getString("STOCKMGROUT_ID");
            if (StringUtil.isEmpty(STOCKMGROUT_ID)) {
                srcDtlPd.put("STOCKMGROUT_ID",stockId);
            }

            int cargoCnt = 0;
            //PICK_EA CARGO_EA
            List<String> dtlIdList = new ArrayList<>();
            for (int i = 1, size = list.size(); i < size; i++) {
                cargoCnt += list.get(i).getInteger("PICK_EA");
                dtlIdList.add(list.get(i).getString("STOCKDTLMGROUT_ID"));
            }

            srcDtlPd.put("CARGO_EA", srcDtlPd.getInteger("CARGO_EA")+cargoCnt);

            //更新原始纪录的配载状态、配载数量(CARGO_STATE CARGO_EA).
            modifySrcCargoState(srcDtlPd);
            //更新拣货明细纪录的配载状态、配载数量(CARGO_STATE CARGO_EA,CARGO_TYPE).
            srcDtlPd.put("CARGO_TYPE",cargoType);
            srcDtlPd.put("IDS", dtlIdList);

            Page page = new Page();
            srcDtlPd.put("CARGO_STATE",WmsEnum.CargoState.ALL.getItemKey());
            page.setPd(srcDtlPd);
            modifyStockCargoState(page);
            //更新箱号表纪录的配载状态
           // modifyBoxCargoState(page);
            modifyBoxCargoState(dtlIdList);
            updFlag = true;
        }
        //处理单头的配载状态
        if (updFlag) {
            updateCargoState(stockId);
        }
    }

    private Map<String,Integer> findCargoEaByid(List<String> idList)  throws Exception{
        List<PageData> list = (List<PageData>)dao.findForList("CargoOutMapper.findCargoEaByid",idList);
        return groupByDtlId(list);
    }

    private  Map<String,Integer> findBoxEaByid(List<String> idList)  throws Exception{
        List<PageData> list =(List<PageData>)dao.findForList("CargoOutMapper.findBoxEaByid",idList);
        return groupByDtlId(list);
    }

    private  Map<String,Integer> groupByDtlId(List<PageData>  list ) {
        Map<String,Integer> map = new HashMap<>();
        for (PageData p : list) {
            map.put(p.getString("BOX_ID"),p.getInteger("CARGO_EA"));
        }
        return map;
    }

    //更改配载状态为
    private void modifyBoxCargoState(List<String> idList) throws Exception {
        Map<String, Integer> pickEaMap = findCargoEaByid(idList);
        Map<String, Integer> boxEaMap = findBoxEaByid(idList);

        for (Map.Entry<String, Integer> entry : boxEaMap.entrySet()) {
            PageData pd = new PageData();
            int boxEa = entry.getValue();
            Integer integer = pickEaMap.get(entry.getKey());
            int pickEa = integer== null ?0 :integer;

            String CARGO_STATE = WmsEnum.CargoState.ALL.getItemKey();;
            if (pickEa ==0) {
                CARGO_STATE = WmsEnum.CargoState.NONE.getItemKey();
            } else if (pickEa >0 && pickEa<boxEa) {
                CARGO_STATE = WmsEnum.CargoState.PART.getItemKey();
            }
            pd.put("CARGO_STATE", CARGO_STATE);	//PICK_STATE
            pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
            pd.put("BOX_ID", entry.getKey());	//BOX_ID
            dao.update("CargoOutMapper.editDtlBoxCargoStateById",pd);
        }
    }

    /**
     * 取消一张出库单的状态及其明细对应的箱号纪录
     *
     * @param prodDtlMap 按产品分组后的明细
     * @param stockId    出库单主表Id
     */
    private void dowithOneStockCargoCancel(Map<String, List<PageData>> prodDtlMap, String stockId) throws Exception {

        boolean updFlag = false;

        for (Map.Entry<String, List<PageData>> ent : prodDtlMap.entrySet()) {
            List<PageData> list = ent.getValue();
            if (list == null || list.size() == 1) {
                continue;
            }
            //按分配标记升序，第一个就是原始纪录，从第二个开始处理即可
            list.sort((e1, e2) -> e1.getInteger("ASSIGNED_FLG") - e2.getInteger("ASSIGNED_FLG"));
            PageData srcDtlPd = list.get(0);
            String STOCKMGROUT_ID = srcDtlPd.getString("STOCKMGROUT_ID");
            if (StringUtil.isEmpty(STOCKMGROUT_ID)) {
                srcDtlPd.put("STOCKMGROUT_ID",stockId);
            }

            int cargoCnt = 0;
            //PICK_EA CARGO_EA
            List<String> dtlIdList = new ArrayList<>();
            for (int i = 1, size = list.size(); i < size; i++) {
                cargoCnt += list.get(i).getInteger("PICK_EA");
                dtlIdList.add(list.get(i).getString("STOCKDTLMGROUT_ID"));
            }

            srcDtlPd.put("CARGO_EA", srcDtlPd.getInteger("CARGO_EA") - cargoCnt);
            //更新原始纪录的配载状态、配载数量(CARGO_STATE CARGO_EA).

            modifySrcCargoState(srcDtlPd);
            //更新拣货明细纪录的配载状态、配载数量(CARGO_STATE CARGO_EA,CARGO_TYPE).
            //取消配载的时候将配载类型设值为0
            srcDtlPd.put("CARGO_STATE",WmsEnum.CargoState.NONE.getItemKey());
            srcDtlPd.put("CARGO_TYPE", Const.ZERO);
            srcDtlPd.put("IDS", dtlIdList);
            Page page = new Page();
            page.setPd(srcDtlPd);
            modifyStockCargoStateNone(page);
            //更新箱号表纪录的配载状态
            //modifyBoxCargoState(page);
            modifyBoxCargoState(dtlIdList);
            //删除已经配载的纪录
            //deleteCargoDtl(dtlIdList);
            deleteCargoDtl(page);
            updFlag = true;
        }
        //处理单头的配载状态
        if (updFlag) {
            updateCargoState(stockId);
        }
    }

    /**
     * 根据出库单ID去更新出库单表头的配载状态.逻辑:<br>
     * 1、如果所有的已配载EA数都是0，则是：未配载;<br>
     * 2、如果所有的已配载EA数都大于等于期望EA的话，则是:全部配载;<br>
     * 3、其他情况则是:部分配载
     *
     * @param stockmgroutId 出库单ID
     */
    private void updateCargoState(String stockmgroutId) {
        try {
            PageData pd = new PageData();
            pd.put("STOCKMGROUT_ID", stockmgroutId);
            // EA_EA, ASSIGNED_EA
            List<PageData> list = (List<PageData>) dao.findForList("CargoOutMapper.findDtlOutList", pd);

            int dtlCnt = list.size();
            int eaCnt = 0;
            int cargoEACnt = 0;
            for (PageData p : list) {
                //期望EA、已分配EA
                int EA = p.getInteger("EA_EA");
                int cargoEA = p.getInteger("CARGO_EA");
                if (cargoEA == 0) {
                    cargoEACnt += 1;
                }

                if (cargoEA >= EA) {
                    eaCnt += 1;
                }
            }

            String cargoState = WmsEnum.CargoState.PART.getItemKey();
            if (cargoEACnt == dtlCnt) {
                cargoState = WmsEnum.CargoState.NONE.getItemKey();
            } else if (eaCnt == dtlCnt) {
                cargoState = WmsEnum.CargoState.ALL.getItemKey();
            }

            pd.put("CARGO_STATE", cargoState);
            pd.put("MODIFY_EMP", SessionUtil.getCurUserName());    //修改人
            pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
            //修改出库单表头的库存配载状态
            dao.update("CargoOutMapper.editHeadStockCargoInfo", pd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新原始纪录的配载状态、配载数量
     *
     * @param srcPd 非拣货明细
     * @throws Exception e
     */
    private void modifySrcCargoState(PageData srcPd) throws Exception {
        int EA_EA = srcPd.getInteger("EA_EA");
        int CARGO_EA = srcPd.getInteger("CARGO_EA");
        String pickState;
        if (CARGO_EA == 0) {
            pickState = WmsEnum.CargoState.NONE.getItemKey();
        } else if (EA_EA > CARGO_EA) {
            pickState = WmsEnum.CargoState.PART.getItemKey();
        } else {
            pickState = WmsEnum.CargoState.ALL.getItemKey();
        }
        srcPd.put("CARGO_STATE", pickState);
        dao.update("CargoOutMapper.editDtlStockCargoStateByID", srcPd);
    }


    //更改配载状态为163，数量等于分配数量
    private void modifyStockCargoState(Page page) throws Exception {
        dao.update("CargoOutMapper.editDtlStockCargoState", page);
    }
    //更改配载状态为163，数量等于分配数量
    private void modifyStockCargoStateNone(Page page) throws Exception {
        dao.update("CargoOutMapper.editDtlStockCargoStateNone", page);
    }


    //更改配载状态为163，数量等于分配数量
    private void modifyBoxCargoState(Page page) throws Exception {
        page.getPd().put("MODIFY_TM", Tools.date2Str(new Date()));
        dao.update("CargoOutMapper.editDtlBoxCargoState", page);
    }

    //删除已经配载的纪录
    private void deleteCargoDtl(List<String> ids) throws Exception {
        dao.update("CargoOutMapper.deleteCargoDtl", ids);
    }

    private void deleteCargoDtl(Page page) throws Exception {
        dao.update("CargoOutMapper.deleteCargoDtl", page);
    }


    /**
     * 根据明细ID获取配载单的主ID
     *
     * @param list 出库单明细ID列表
     * @return 配载单的ID
     * @throws Exception 异常处理
     */
    private List<String> findCargoPidByDtlId(List<String> list,String stockId) throws Exception {
        Page page = new Page();
        PageData pd = new PageData();
        pd.put("IDS",list);
        pd.put("STOCKMGROUT_ID",stockId);
        page.setPd(pd);
        List<PageData> lst = (List<PageData>) dao.findForList("CargoOutMapper.findCargoPidByDtlId", page);
        List<String> idList = new ArrayList<>();
        if (lst == null || lst.isEmpty()) {
            return idList;
        }
        for (PageData p : lst) {
            idList.add(p.getString("CARGOOUT_ID"));
        }
        return idList;
    }


    /**
     * 根据前台传入的明细ID，过滤出库单明细需要配载的纪录.<br>
     * 有效数据：明细id对应纪录，以及明细相应的产品对应的原始产品纪录.
     *
     * @param dtlList 某入库单的所有明细
     * @param idSet   需要处理的明细Id
     * @return 集合
     */
    private List<PageData> filterActList(List<PageData> dtlList, Set<String> idSet) {
        //循环将出货标记ASSIGNED_FLG='0'的原始纪录得到，还有明细id等于前台传入id的纪录
        List<PageData> list = new ArrayList<>(dtlList.size());
        for (PageData p : dtlList) {
            //如果已经配载的纪录过滤掉
            if((WmsEnum.CargoState.ALL.getItemKey().equals(p.getString("CARGO_STATE"))) && p.getInteger("ASSIGNED_FLG") == 1) {
                continue;
            }

            if (p.getInteger("ASSIGNED_FLG") == 0 || idSet.contains(p.getString("STOCKDTLMGROUT_ID"))) {
                list.add(p);
            }
        }
        return list;
    }

    /**
     * 根据前台传入的明细ID，过滤出库单明细需要配载的纪录.<br>
     * 有效数据：明细id对应纪录，以及明细相应的产品对应的原始产品纪录.
     *
     * @param dtlList 某入库单的所有明细
     * @param idSet   需要处理的明细Id
     * @return 集合
     */
    private List<PageData> filterCancelList(List<PageData> dtlList, Set<String> idSet) {
        //循环将出货标记ASSIGNED_FLG='0'的原始纪录得到，还有明细id等于前台传入id的纪录
        List<PageData> list = new ArrayList<>(dtlList.size());
        for (PageData p : dtlList) {
            //如果已经配载的纪录过滤掉
            if((WmsEnum.CargoState.NONE.getItemKey().equals(p.getString("CARGO_STATE"))) && p.getInteger("ASSIGNED_FLG") == 1) {
                continue;
            }

            if (p.getInteger("ASSIGNED_FLG") == 0 || idSet.contains(p.getString("STOCKDTLMGROUT_ID"))) {
                list.add(p);
            }
        }
        return list;
    }

    /**
     * 根据出库单Id，获取其中已经拣货的全部有效数据
     *
     * @param id
     * @return
     * @throws Exception
     */
    private List<PageData> findAllDtlByPid(String id) throws Exception {
        return (List<PageData>) dao.findForList("CargoOutMapper.findDtlCargoPdById", id);
    }

    private List<PageData> findCargoCntById(List<String> list) throws Exception {
        return (List<PageData>) dao.findForList("CargoOutMapper.findCargoCntById", list);
    }

    /**
     * 将组合ID(STOCKDTLMGROUT_ID-STOCKMGROUT_ID)按出库单ID分组，以及明细
     *
     * @param conbimIds 组合ID
     * @return map 主表ID，明细Id
     */
    private Map<String, Set<String>> genPidListMap(String[] conbimIds, List<String> dtlIdList, List<String> combList) {
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
            combList.add(arr[0]+Const.SPLIT_LINE+pId);
            list.add(arr[0]);
            map.put(pId, list);
        }
        return map;
    }


    /**
     * 按产品分组之后，逐组处理
     *
     * @param dtlList 组合ID
     * @return map 主表ID，明细
     */
    private Map<String, List<PageData>> genProdDtlMap(List<PageData> dtlList) {
        Map<String, List<PageData>> map = new HashMap<>();
        if (dtlList == null || dtlList.size() == 0) {
            return map;
        }
        for (PageData p : dtlList) {
            String pId = p.getString("PRODUCT_ID");
            List<PageData> list = map.get(pId);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(p);
            map.put(pId, list);
        }
        return map;
    }

    //去除重复的纪录，只显示主表纪录
    private List<PageData> filterById(List<PageData> srcList) {
        List<PageData> rs = new ArrayList<>();
        for(PageData p : srcList) {
            if(!isContain(rs,p)) {
                rs.add(p);
                continue;
            }

            for(PageData p2 : rs) {
                if(p2.getString("STOCKMGROUT_ID").equals(p.getString("STOCKMGROUT_ID"))) {
                    //EA_EA ASSIGNED_EA PICK_EA
                    p2.put("EA_EA",p2.getInteger("EA_EA")+p.getInteger("EA_EA"));
                    p2.put("ASSIGNED_EA",p2.getInteger("ASSIGNED_EA")+p.getInteger("ASSIGNED_EA"));
                    p2.put("PICK_EA",p2.getInteger("PICK_EA")+p.getInteger("PICK_EA"));
                    p2.put("CARGO_EA",p2.getInteger("CARGO_EA")+p.getInteger("CARGO_EA"));
                }
            }
        }
        return rs;
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
    @Deprecated
    public void findDtlCargoLooklistPage_bak( List<PageData> srcList,List<PageData> assignList,Page page)  throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("CargoOutMapper.findDtl2CargoLookllistPage", page);
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
}
