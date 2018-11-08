package com.hw.service.property;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created：panda.HuangWei
 * Date：2016-10-25
 */
@Service("orderpropertyService")
public class OrderPropertyService {
    private String USE_SUFFIX = "_USE";
    private String SORT_SUFFIX = "_SORT";
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
    * 新增tb_orderproperty
    */
    public void save(PageData pd) throws Exception {
        List<PageData> datas = buildSaveList(pd);
        //dao.save("OrderPropertyMapper.save", datas);
        dao.batchSave("OrderPropertyMapper.save", datas);
    }

    private List<PageData> buildSaveList(PageData pageData) {
        if (pageData == null) {
            return new ArrayList<>();
        }
        Map<String, Pair<String, Integer>> coluMap = WmsEnum.OrderProperty.coluSortMap;
        List<PageData> list = new ArrayList<>();
        PageData pd = null;
        for (Map.Entry<String, Pair<String, Integer>> e : coluMap.entrySet()) {
            pd = new PageData();

            pd.put("CUSTOMER_ID", pageData.getString("CUSTOMER_ID"));
            pd.put("ORDERPROPERTY_ID", UuidUtil.get32UUID());
            pd.put("CREATE_EMP", pageData.getString("CREATE_EMP"));    //创建人
            pd.put("CREATE_TM", Tools.date2Str(new Date()));    //创建时间
            pd.put("MODIFY_EMP", pageData.getString("MODIFY_EMP"));    //修改人
            pd.put("MODIFY_TM", Tools.date2Str(new Date()));    //修改时间
            pd.put("DEL_FLG", "0");

            pd.put("ORDERBATCH_TYPE", pageData.getString("ORDERBATCH_TYPE"));
            pd.put("PROPERTY_KEY", pageData.getString(e.getKey()));
            pd.put("PROPERTY_USE", pageData.getString(e.getKey() + USE_SUFFIX));
            pd.put("PROPERTY_SORT", pageData.getString(e.getKey() + SORT_SUFFIX));
            pd.put("SRC_SORT", e.getValue().getRight());
            list.add(pd);
        }
        return list;
    }

    /*
    * 删除
    */
    public void delete(PageData pd) throws Exception {
        dao.delete("OrderPropertyMapper.delete", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd) throws Exception {
        //dao.update("OrderPropertyMapper.edit", pd);
        delAll(pd);
        save(pd);
    }

    /*
    *列表
    */
    public List<PageData> list(Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("OrderPropertyMapper.datalistPage", page);

        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }

        Map<String, String> coluMap = WmsEnum.OrderProperty.coluMap;
        Map<Integer, String> sortColuMap = WmsEnum.OrderProperty.sortColuMap;
        List<PageData> lst = new ArrayList<>();
        for (PageData e : list) {
            int sort = Integer.parseInt(e.get("SRC_SORT").toString());
            String colum = sortColuMap.get(sort);
            PageData rs = new PageData(e);
            rs.put("PROPERTY_CN", coluMap.get(colum));
            rs.put("SRC_SORT", sort);
            lst.add(rs);
        }
        lst.sort((e1, e2) -> Integer.parseInt(e1.get("SRC_SORT").toString()) - Integer.parseInt(e2.get("SRC_SORT").toString()));
        return lst;
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("OrderPropertyMapper.listAll", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("OrderPropertyMapper.findById", pd);
    }

    /**
     * 新增订单/批次属性的时候，如果已经存在直接查询出来;<br>
     * 如果不存在则根据参考公司的订单/批次属性查询作为新增的参数
     *
     * @param pd 前台传入的customerId以及批次属性代码
     * @return
     * @throws Exception
     */
    public PageData findForAdd(PageData pd) throws Exception {
        PageData rs = findByCustomerAndType(pd);

        if (StringUtil.isEmpty(rs.getString("CUSTOMER_ID"))) {
            PageData searchPd = new PageData();
            searchPd.put("CUSTOMER_ID", BaseInfoCache.getInstance().getCustomerId(Const.ORDERBATCH_CUSTOMERCODE));
            searchPd.put("ORDERBATCH_TYPE",pd.getString("ORDERBATCH_TYPE"));
            rs = findByCustomerAndType(searchPd);
            if (StringUtil.isNotEmpty(rs.getString("CUSTOMER_ID"))) {
                rs.put("CUSTOMER_ID",pd.getString("CUSTOMER_ID"));
                rs.put("FLAG",Const.DEL_NO);
                rs.put("CUSTOMER_CODE",Const.ORDERBATCH_CUSTOMERCODE);
            }
        } else {
            rs.put("FLAG",Const.DEL_YES);
        }
        return rs;
    }

    /*
    * 通过id获取数据
    */
    public PageData findByCustomerAndType(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("OrderPropertyMapper.findByCustomerAndType", pd);
        PageData rs = new PageData();
        if (list == null || list.isEmpty()) {
            return rs;
        }
        Map<Integer, String> sortColuMap = WmsEnum.OrderProperty.sortColuMap;

        for (PageData e : list) {
            String colum = sortColuMap.get(e.get("SRC_SORT"));
            rs.put(colum, e.getString("PROPERTY_KEY"));
            rs.put(colum + USE_SUFFIX, e.getString("PROPERTY_USE"));
            rs.put(colum + SORT_SUFFIX, e.getString("PROPERTY_SORT"));
            rs.put("ORDERPROPERTY_ID", e.getString("ORDERPROPERTY_ID"));
            rs.put("CUSTOMER_ID", e.getString("CUSTOMER_ID"));
            rs.put("ORDERBATCH_TYPE", e.getString("ORDERBATCH_TYPE"));
        }
        return rs;
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("OrderPropertyMapper.deleteAll", ArrayDATA_IDS);
    }

    public void delAll(PageData pd) throws Exception {
        dao.delete("OrderPropertyMapper.delAll", pd);
    }

    /**
     * 根据客户ID获取客户的订单属性
     *
     * @param coustomerId 客户ID
     * @return
     */
    private List<PageData> getPropertyINOrder(String coustomerId) {
        PageData pd = new PageData();
        pd.put("CUSTOMER_ID", coustomerId);
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_ORDER.getItemValue()));
        return getProperty(pd);
    }

    private List<PageData> getPropertyINBatch(String coustomerId) {
        PageData pd = new PageData();
        pd.put("CUSTOMER_ID", coustomerId);
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.IN_BATCH.getItemValue()));
        return getProperty(pd);
    }

    private List<PageData> getPropertyOutOrder(String coustomerId) {
        PageData pd = new PageData();
        pd.put("CUSTOMER_ID", coustomerId);
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_ORDER.getItemValue()));
        return getProperty(pd);
    }

    private List<PageData> getPropertyOutBatch(String coustomerId) {
        PageData pd = new PageData();
        pd.put("CUSTOMER_ID", coustomerId);
        pd.put("ORDERBATCH_TYPE", SelectCache.getInstance().getBatchId(WmsEnum.OrderPropertyType.OUT_BATCH.getItemValue()));
        return getProperty(pd);
    }

    public List<PageData> getProperty(String coustomerId, String orderBatchType) {
        PageData pd = new PageData();
        pd.put("CUSTOMER_ID", coustomerId);
        pd.put("ORDERBATCH_TYPE", orderBatchType);
        return getProperty(pd);
    }

    /**
     * 需要参数 #{CUSTOMER_ID} ,#{ORDERBATCH_TYPE}
     *
     * @param page
     * @return
     * @throws Exception
     */
    public List<PageData> getProperty(PageData page) {
        List<PageData> list = new ArrayList<>();
        try {
            list = (List<PageData>) dao.findForList("OrderPropertyMapper.findPropertyByCustAndType", page);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        return buildProperties(list);
    }

    private List<PageData> buildProperties(List<PageData> list) {
        Map<Integer, String> sortColuMap = WmsEnum.OrderProperty.sortColuMap;
        List<PageData> lst = new ArrayList<>();
        for (PageData e : list) {
            int sort = Integer.parseInt(e.get("SRC_SORT").toString());
            String colum = sortColuMap.get(sort);
            PageData rs = new PageData(e);
            String propertySort = e.getString("PROPERTY_SORT");
            if (StringUtil.isEmpty(propertySort)) {
                rs.put("PROPERTY_SORT", sort);
            }
            rs.put("PROPERTY_COLUM", colum);
            rs.put("PROPERTY_VALUE", Const.EMPTY_CH);
            rs.put("SRC_SORT", sort);
            lst.add(rs);
        }
        lst.sort((e1, e2) -> Integer.parseInt(e1.get("PROPERTY_SORT").toString()) - Integer.parseInt(e2.get("PROPERTY_SORT").toString()));
        return lst;
    }

    public PageData findByCn(PageData pd) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("OrderPropertyMapper.findByCn", pd);
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

}

