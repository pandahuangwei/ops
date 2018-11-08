package com.hw.service.base;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created：panda.HuangWei
 * Date：2016-10-23
 */
@Service("customerService")
public class CustomerService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /*
    * 新增
    */
    public void save(PageData pd) throws Exception {
        setDefalut(pd);
        setCustomer2D(pd);

        dao.save("CustomerMapper.save", pd);
        dao.save("CustomerMapper.saveReceivopt2D", pd);
        pd.put("SEQ_ID", UuidUtil.get32UUID());
        pd.put("SEQ_TYPE", Const.OUTSTOCK_MID);
        pd.put("CUR_YEAR", DateUtil.getYY());
        dao.save("CustomerMapper.saveSeq", pd);

        pd.put("SEQ_ID", UuidUtil.get32UUID());
        pd.put("SEQ_TYPE", Const.INSTOCK_MID);
        dao.save("CustomerMapper.saveSeq", pd);
    }

    /*
    * 删除
    */
    public void delete(PageData pd) throws Exception {
        dao.delete("CustomerMapper.delete", pd);
        dao.delete("CustomerMapper.deleteReceivopt2D", pd);
    }

    /*
    * 修改
    */
    public void edit(PageData pd) throws Exception {
        setDefalut(pd);
        setCustomer2D(pd);
        dao.update("CustomerMapper.edit", pd);
        PageData object = (PageData) dao.findForObject("CustomerMapper.findReceivopt2DById", pd);
        if (object != null && !object.isEmpty())
            dao.update("CustomerMapper.editReceivopt2D", pd);
        else
            dao.save("CustomerMapper.saveReceivopt2D", pd);
    }

    /*
    *列表
    */
    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("CustomerMapper.datalistPage", page);
    }

    public List<PageData> listReceivopt2D(Page page) throws Exception {
        return (List<PageData>) dao.findForList("CustomerMapper.listPageReceivopt2D", page);
    }

    /*
    *列表(全部)
    */
    public List<PageData> listAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CustomerMapper.listAll", pd);
    }

    /*
    * 通过id获取数据
    */
    public PageData findById(PageData pd) throws Exception {
        PageData pageData = (PageData) dao.findForObject("CustomerMapper.findById", pd);
        PageData pd1 = (PageData) dao.findForObject("CustomerMapper.findReceivopt2DById", pd);
        if (pd1 != null)
            pageData.putAll(pd1);
        return pageData;
    }

    /*
    * 批量删除
    */
    public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
        dao.delete("CustomerMapper.deleteAll", ArrayDATA_IDS);
        dao.delete("CustomerMapper.deleteAllReceivopt2D", ArrayDATA_IDS);
    }

    /**
     * 获取当前id级seq
     *
     * @param customerId 客户ID
     * @param stockType  入库出库类型(input,output)
     * @return
     */
    public Pair<String, String> saveGetCustomAndSeq(String customerId, String stockType) {
        PageData pd = new PageData();
        pd.put("CUSTOMER_ID", customerId);
        pd.put("SEQ_TYPE", stockType);
        try {
            PageData object = (PageData) dao.findForObject("CustomerMapper.findSeq", pd);
            String id = object.getString("CUSTOMER_CODE");
            String year = object.getString("CUR_YEAR");
            String curYY = DateUtil.getYY();
            String nextYY;
            String seq = String.valueOf(object.getString("CUR_SEQ"));
            if (curYY.equals(year)) {
                nextYY = year;
            } else {
                seq = Const.ZERO_CH;
                nextYY = curYY;
            }
            pd.put("CUR_SEQ", Integer.parseInt(seq) + 1);
            pd.put("CUR_YEAR", nextYY);
            dao.update("CustomerMapper.updateSeq", pd);
            return Pair.of(id, seq);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Pair.of(null, null);
    }

    /**
     * 根据客户Id 生成入库单号
     *
     * @param customerId 客户Id
     * @return 入库单号
     */
    public String getInStockNo(String customerId) {
        Pair<String, String> pair = saveGetCustomAndSeq(customerId, Const.INSTOCK_MID);
        if (StringUtil.isNotEmpty(pair.getLeft())) {
            return pair.getLeft() + Const.INSTOCK_MID + DateUtil.getYYMMDD() + StringUtil.lpad(pair.getRight());
        }
        return null;
    }

    /**
     * 根据客户Id 生成入库单号
     *
     * @param customerId 客户Id
     * @return 入库单号
     */
    public String getOutStockNo(String customerId) {
        Pair<String, String> pair = saveGetCustomAndSeq(customerId, Const.OUTSTOCK_MID);
        if (StringUtil.isNotEmpty(pair.getLeft())) {
            return pair.getLeft() + Const.OUTSTOCK_MID + DateUtil.getYYMMDD() + StringUtil.lpad(pair.getRight());
        }
        return null;
    }

    public String getCargoNo() {
        Pair<String, String> pair = saveGetCustomAndSeq(Const.CARGO_DFCUSTOMER, Const.CARGO_NO);
        if (StringUtil.isNotEmpty(pair.getRight())) {
            return DateUtil.getYYMMDD() + StringUtil.lpad(pair.getRight());
        }
        return null;
    }

    public String getModifyBatchNo() {
        Pair<String, String> pair = saveGetCustomAndSeq(Const.CARGO_DFCUSTOMER, Const.MODIFY_REMARK);
        if (StringUtil.isNotEmpty(pair.getRight())) {
            return DateUtil.getYYMMDD() + StringUtil.lpad(pair.getRight());
        }
        return null;
    }

    public PageData findByCode(PageData pd) throws Exception {
        //return (PageData)dao.findForObject("CustomerMapper.findByCode", pd);
        List<PageData> list = (List<PageData>) dao.findForList("CustomerMapper.findByCode", pd);
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    public PageData findByCn(PageData pd) throws Exception {
        //return (PageData)dao.findForObject("CustomerMapper.findByCn", pd);
        List<PageData> list = (List<PageData>) dao.findForList("CustomerMapper.findByCn", pd);
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    //保存合约
    public void saveDeal(PageData pd) throws Exception {
        dao.save("CustomerMapper.saveDeal", pd);
    }

    //查询合约
    public List<PageData> findDeal(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("CustomerMapper.findDeal", pd);
    }

    //删除合约
    public void delDeal(PageData pd) throws Exception {
        dao.update("CustomerMapper.delDeal", pd);
    }

    public PageData findDealById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("CustomerMapper.findDealById", pd);
    }

    public PageData findReceivopt2DById(PageData pd) throws Exception {
        return (PageData) dao.findForObject("CustomerMapper.findReceivopt2DById", pd);
    }

    private void setCustomer2D(PageData pd) {
        String receivopt2D = "";
        Map<Integer, String> receivopt2DMap = new HashMap<>(32);
        Integer pn = pd.getInteger("PN");
        if (pn != null && pn != 0) {
            receivopt2DMap.put(pn, "PN");
        } else
            pd.put("PN", 0);
        Integer datecode = pd.getInteger("DATE_CODE");
        if (datecode != null && datecode != 0) {
            receivopt2DMap.put(datecode, "DATE_CODE");
        } else
            pd.put("DATE_CODE", 0);
        Integer lot = pd.getInteger("LOT_CODE");
        if (lot != null && lot != 0) {
            receivopt2DMap.put(lot, "LOT_CODE");
        } else
            pd.put("LOT_CODE", 0);
        Integer bin = pd.getInteger("BIN");
        if (bin != null && bin != 0) {
            receivopt2DMap.put(bin, "BIN");
        } else
            pd.put("BIN", 0);
        Integer coo = pd.getInteger("COO");
        if (coo != null && coo != 0) {
            receivopt2DMap.put(coo, "COO");
        } else
            pd.put("COO", 0);
        Integer qty = pd.getInteger("QTY");
        if (qty != null && qty != 0) {
            receivopt2DMap.put(qty, "QTY");
        } else
            pd.put("QTY", 0);
        Integer remark1 = pd.getInteger("REMARK1");
        if (remark1 != null && remark1 != 0) {
            receivopt2DMap.put(remark1, "REMARK1");
        } else
            pd.put("REMARK1", 0);
        Integer remark2 = pd.getInteger("REMARK2");
        if (remark2 != null && remark2 != 0) {
            receivopt2DMap.put(remark2, "REMARK2");
        } else
            pd.put("REMARK2", 0);
        Integer remark3 = pd.getInteger("REMARK3");
        if (remark3 != null && remark3 != 0) {
            receivopt2DMap.put(remark3, "REMARK3");
        } else
            pd.put("REMARK3", 0);
        Integer invNo = pd.getInteger("INV_NO");
        if (invNo != null && invNo != 0) {
            receivopt2DMap.put(invNo, "INV_NO");
        } else
            pd.put("INV_NO", 0);

        receivopt2DMap.keySet().iterator();
        String splitCh = StringUtil.isEmpty(pd.getString("SEPERATOR")) ? Const.SPLIT_COLON : pd.getString("SEPERATOR");
        for (String v : receivopt2DMap.values()) {
            if (StringUtil.isEmpty(receivopt2D))
                receivopt2D = v;
            else
                receivopt2D += splitCh + v;
        }
        pd.put("RECEIVOPT_2D", receivopt2D);
        pd.put("SEPERATOR", splitCh);
    }

    private void setDefalut(PageData pd) {
        String plies = pd.getString("TYPE_SUPPLIER");
        if (StringUtil.isEmpty(plies)) {
            pd.put("TYPE_SUPPLIER", 0);
        }
        String prod = pd.getString("TYPE_FACTOR");
        if (StringUtil.isEmpty(prod)) {
            pd.put("TYPE_FACTOR", 0);
        }
        String batch = pd.getString("TYPE_FOB");
        if (StringUtil.isEmpty(batch)) {
            pd.put("TYPE_FOB", 0);
        }
        String send = pd.getString("TYPE_SEND");
        if (StringUtil.isEmpty(send)) {
            pd.put("TYPE_SEND", 0);
        }

        String take = pd.getString("TYPE_TAKE");
        if (StringUtil.isEmpty(take)) {
            pd.put("TYPE_TAKE", 0);
        }

        String car = pd.getString("TYPE_CAR");
        if (StringUtil.isEmpty(car)) {
            pd.put("TYPE_CAR", 0);
        }

        String custom = pd.getString("TYPE_CUSTOM");
        if (StringUtil.isEmpty(custom)) {
            pd.put("TYPE_CUSTOM", 0);
        }

        String useFlg = pd.getString("USE_FLG");
        if (StringUtil.isEmpty(useFlg)) {
            pd.put("USE_FLG", 0);
        }

        String payPeriod = pd.getString("PAY_PERIOD");
        if (StringUtil.isEmpty(payPeriod)) {
            pd.put("PAY_PERIOD", 0);
        }
        String suprTake = pd.getString("SUPR_TAKE");
        if (StringUtil.isEmpty(suprTake)) {
            pd.put("SUPR_TAKE", 0);
        }

        String suprSend = pd.getString("SUPR_SEND");
        if (StringUtil.isEmpty(suprSend)) {
            pd.put("SUPR_SEND", 0);
        }
    }
}

