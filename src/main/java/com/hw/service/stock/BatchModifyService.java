package com.hw.service.stock;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;
import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.service.base.CustomerService;
import com.hw.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Panda.HuangWei.
 * @since 2017-05-15 22:17.
 * To change this template use File | Settings | File Templates.
 */
@Service("batchModifyService")
public class BatchModifyService {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Resource(name = "customerService")
    private CustomerService customerService;

    public List<PageData> saveAndGetList(Page page) throws Exception {
        //先将本次查询的数据存入数据库


        dao.delete("BatchModifyMapper.deleteData2HistRemarkByEmp",page.getPd());
        dao.save("BatchModifyMapper.insertData2HistRemarkProc",page);
        List<PageData> list = findBatchModifyList(page);
        if (list == null) {
            return new ArrayList<>();
        }
        for (PageData pd : list) {
            calcAndSetEa(pd);
            pd.put("PUT_STOCK", BaseInfoCache.getInstance().getStockCodeByStockageId(pd.getString("PUT_STORAGE")));
        }
        return list;
    }

    public List<PageData> historyList(Page page) throws Exception {
        List<PageData> list = (List<PageData>) dao.findForList("BatchModifyMapper.batchDataHistorylistPage", page);
        if (list == null) {
            return new ArrayList<>();
        }
        for (PageData pd : list) {
            pd.put("PUT_STOCK", BaseInfoCache.getInstance().getStockCodeByStockageId(pd.getString("PUT_STORAGE")));
        }
        return list;
    }
    /**
     * 保存修改的remark记录.<br>
     *  0、产生批次号;<br>
     * 1、根据查询条件获取需要修改的记录；
     * @param pd 传入参数
     */
    public PageData saveRemarkHistory(PageData pd)  throws Exception{
        PageData rsPd = new PageData();
        String dataIds = pd.getString("DATA_IDS");
        if (StringUtil.isBlank(dataIds)) {
            pd.put("FLAG",null);
        } else {
            pd.put("FLAG", Const.ZERO_CH);
            pd.put("IDS", Arrays.asList(StringUtils.split(dataIds,Const.SPLIT_COMMA)));
        }
        setModifyFlag(pd);
        String modifyBatchNo = customerService.getModifyBatchNo();
        pd.put("MODIFY_BATCH",modifyBatchNo);

        Page page = new Page();
        page.setPd(pd);
        List<PageData> list = (List<PageData>) dao.findForList("BatchModifyMapper.getPropertyIdList", page);
        if (list == null || list.isEmpty()) {
            rsPd.put("MODIFY_CNT",Const.ZERO);
            return rsPd;
        } else {
            rsPd.put("MODIFY_BATCH",modifyBatchNo);
            rsPd.put("MODIFY_CNT",list.size());
        }
        //更改批次属性
        Page pageTmp;
        for (PageData p : list) {
            //TXT_FOU_USE TXT_FIV_USE TXT_SIX_USE
            p.put("TXT_FOU_USE",pd.getString("TXT_FOU_USE"));
            p.put("TXT_FIV_USE",pd.getString("TXT_FIV_USE"));
            p.put("TXT_SIX_USE",pd.getString("TXT_SIX_USE"));

            p.put("TXT_FOU_NEW",pd.getString("TXT_FOU_NEW"));
            p.put("TXT_FIV_NEW",pd.getString("TXT_FIV_NEW"));
            p.put("TXT_SIX_NEW",pd.getString("TXT_SIX_NEW"));
            pageTmp = new Page();
            pageTmp.setPd(p);
            dao.save("BatchModifyMapper.updateRemark",pageTmp);
            dao.save("BatchModifyMapper.updateBoxRemark",pageTmp);
        }

        //写入历史记录
        dao.save("BatchModifyMapper.insertData2HistRemark",page);
        //清空过程表
        dao.delete("BatchModifyMapper.deleteData2HistRemark",pd);

        //将批次增加到下拉框
        //SelectCache.getInstance().addBatchModify(modifyBatchNo);
        return rsPd;
    }

    public List<PageData> findBatchModifyList(Page page) throws Exception {
        return (List<PageData>) dao.findForList("BatchModifyMapper.batchDatalistPage", page);
        //  return (List<PageData>) dao.findForList("BatchModifyMapper.findHistRemarkProclistPage", page);
    }

    private void setModifyFlag(PageData pd) {
        if (Const.DEL_YES.equals(pd.getString("TXT_FOU_USE"))) {
            pd.put("REMARK1_M",Const.DEL_YES);
        } else {
            pd.put("REMARK1_M",Const.DEL_NO);
        }
        if (Const.DEL_YES.equals(pd.getString("TXT_FIV_USE"))) {
            pd.put("REMARK2_M",Const.DEL_YES);
        } else {
            pd.put("REMARK2_M",Const.DEL_NO);
        }
        if (Const.DEL_YES.equals(pd.getString("TXT_SIX_USE"))) {
            pd.put("REMARK3_M",Const.DEL_YES);
        } else {
            pd.put("REMARK3_M",Const.DEL_NO);
        }
    }

    public void calcAndSetEa(PageData pd) {
        //已分配未冻结的 算分配的
        if (WmsEnum.AssignedState.ALL.equals(pd.getString("ASSIGNED_STOCK_STATE"))
                && WmsEnum.FreezeState.NONE.getItemKey().equals(pd.getString("FREEZE_STATE"))) {
            pd.put("ASSIGNED_EA", pd.getInteger("ASSIGNED_STOCK_NUM"));
        } else {
            pd.put("ASSIGNED_EA", 0);
        }
        //冻结的
        if (WmsEnum.FreezeState.ALL.getItemKey().equals(pd.getString("FREEZE_STATE"))) {
            int ass_ea = 0;
            if (WmsEnum.AssignedState.ALL.equals(pd.getString("ASSIGNED_STOCK_STATE"))) {
                ass_ea = pd.getInteger("ASSIGNED_STOCK_NUM");
            } else {
                ass_ea = pd.getInteger("EA_NUM");
            }

            int FREEZE_EA = pd.getInteger("FREEZE_EA") + ass_ea;
            pd.put("FREEZE_EA", FREEZE_EA);
        } else {
            pd.put("FREEZE_EA", pd.getInteger("FREEZE_EA") + 0);
        }

        //总数
        pd.put("TOTAL_EA", pd.getInteger("EA_NUM"));
        //可用数
        int USE_EA = pd.getInteger("TOTAL_EA") - pd.getInteger("ASSIGNED_EA") - pd.getInteger("FREEZE_EA");
        pd.put("USE_EA", USE_EA);
    }
}
