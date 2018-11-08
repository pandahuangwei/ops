package com.hw.service.stock;

import com.hw.dao.DaoSupport;
import com.hw.entity.Page;
import com.hw.util.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created：panda.HuangWei
 * Date：2016-12-14
 */
@Service("stockAuditService")
public class StockAuditService {

    @Resource(name = "daoSupport")
    private DaoSupport dao;


    public List<PageData> list(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockAuditMapper.stockAuditDatalistPage", page);
    }

    public List<PageData> list2(Page page) throws Exception {
        return (List<PageData>) dao.findForList("StockAuditMapper.stockAuditData2listPage", page);
    }

    //库存数量调整 申请，将箱的状态修改为申请中，并修改数量
    public void saveAuditQtyApply(PageData pd) throws Exception{

        pd.put("AUDIT_APPLY_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_APPLY_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_TYPE",WmsEnum.AuditType.ADUIT.getItemKey());
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.APPLY.getItemKey());
        dao.update("StockAuditMapper.editAuditQtyApply",pd);
    }

    public void saveAuditQtyPass(PageData pd) throws Exception{

        pd.put("AUDIT_PASS_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_PASS_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.PASS.getItemKey());
        dao.update("StockAuditMapper.editAuditQtyPass",pd);
    }

    public void saveAuditQtyReject(PageData pd) throws Exception{

        pd.put("AUDIT_PASS_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_PASS_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.REJECT.getItemKey());
        dao.update("StockAuditMapper.editAuditQtyReject",pd);
    }


    public void saveAuditPass(PageData pd) throws Exception{
        pd.put("IDS",pd.getList("BOX_IDS"));
        pd.put("AUDIT_PASS_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_PASS_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.PASS.getItemKey());
        dao.update("StockAuditMapper.editAuditPass",pd);
    }

    public void editAuditReject(PageData pd) throws Exception{
        pd.put("IDS",pd.getList("BOX_IDS"));
        pd.put("AUDIT_PASS_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_PASS_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.REJECT.getItemKey());
        dao.update("StockAuditMapper.editAuditReject",pd);
    }


    //==========删除箱号  start=======================
    public void saveAuditDelApply(PageData pd) throws Exception{

        pd.put("AUDIT_APPLY_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_APPLY_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_TYPE",WmsEnum.AuditType.DEL.getItemKey());
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.APPLY.getItemKey());
        dao.update("StockAuditMapper.editAuditDelApply",pd);
    }

    public void saveAuditDelPass(PageData pd) throws Exception{

        pd.put("AUDIT_PASS_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_PASS_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.PASS.getItemKey());
        dao.update("StockAuditMapper.editAuditDelPass",pd);
    }

    public void saveAuditDelReject(PageData pd) throws Exception{

        pd.put("AUDIT_PASS_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_PASS_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.REJECT.getItemKey());
        dao.update("StockAuditMapper.editAuditDelReject",pd);
    }
    //==========删除箱号  end=======================

    //==========新增箱号  start=======================
    public void saveAuditAddApply(PageData pd) throws Exception{
        pd.put("BOX_ID", UuidUtil.get32UUID());
        pd.put("RECEIPT_STATE", WmsEnum.InstockState.NONE.getItemKey());
        pd.put("ASSIGNED_STATE",WmsEnum.AssignedState.NONE.getItemKey());
        pd.put("PUT_STATUS",WmsEnum.PutState.NONE.getItemKey());
        pd.put("ASSIGNED_STOCK_STATE",WmsEnum.AssignedState.NONE.getItemKey());
        pd.put("PICK_STATE",WmsEnum.PickState.NONE.getItemKey());
        pd.put("CARGO_STATE",WmsEnum.CargoState.NONE.getItemKey());
        pd.put("DEPOT_TYPE",WmsEnum.DepotState.NONE.getItemKey());
        pd.put("FREEZE_STATE", WmsEnum.FreezeState.NONE.getItemKey());

        pd.put("CREATE_EMP", SessionUtil.getCurUserName());	//创建人
        pd.put("CREATE_TM", Tools.date2Str(new Date()));	//创建时间
        pd.put("MODIFY_EMP", SessionUtil.getCurUserName());	//修改人
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));	//修改时间
        pd.put("DEL_FLG", "2");	//删除标志

        pd.put("AUDIT_APPLY_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_APPLY_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_TYPE",WmsEnum.AuditType.ADD.getItemKey());
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.APPLY.getItemKey());

        dao.save("StockAuditMapper.saveBoxAudit",pd);
    }

    public void saveAuditAddPass(PageData pd) throws Exception{

        pd.put("AUDIT_PASS_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_PASS_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.PASS.getItemKey());
        dao.update("StockAuditMapper.editAuditAddPass",pd);
    }

    public void saveAuditAddReject(PageData pd) throws Exception{

        pd.put("AUDIT_PASS_EMP",SessionUtil.getCurUserName());
        pd.put("AUDIT_PASS_TM",Tools.date2Str(new Date()));
        pd.put("AUDIT_STATE",WmsEnum.AuditFlg.REJECT.getItemKey());
        dao.update("StockAuditMapper.editAuditAddReject",pd);
    }



    //==========新增箱号  end=======================

  /*
    EA_NUM_BF = #{EA_NUM_BF},
    EA_NUM_AF = #{EA_NUM_AF},
    AUDIT_STATE = #{AUDIT_STATE},
    AUDIT_APPLY_EMP = #{AUDIT_APPLY_EMP},
    AUDIT_APPLY_TM = #{AUDIT_APPLY_TM},
    AUDIT_TYPE = #{AUDIT_TYPE}*/



}

