package com.hw.util;

import com.hw.cache.BaseInfoCache;
import com.hw.cache.SelectCache;

import java.util.Date;

/**
 * @author Panda.HuangWei.
 * @Create 2016.11.20 11:46.
 * @Version 1.0 .
 */
public class StockUtils {

    /**
     * 构建收货时候保存的box信息
     *
     * @param src 界面传值
     * @return
     */
    public static PageData buildReceiveBox(PageData src,boolean isImp) {
        PageData rs = new PageData();

        rs.put("BOX_ID", getId(src, "BOX_ID"));
        rs.put("BOX_NO", isEmpty(src.getString("BOX_NO"))?Const.EMPTY_CH:src.getString("BOX_NO"));
        rs.put("BOARD_NO",isEmpty(src.getString("BOARD_NO"))?Const.EMPTY_CH:src.getString("BOARD_NO"));

        setProduct(rs,src);

        rs.put("SUPPLIER_PROD",src.getString("SUPPLIER_PROD"));
        rs.put("CUSTOMER_ID",src.getString("CUSTOMER_ID"));
        rs.put("BIG_BOX_STATUS", Const.EMPTY_CH);
        rs.put("BIG_BOX_NO", src.getString("BIG_BOX_NO"));
        rs.put("INSTOCK_NO", src.getString("INSTOCK_NO"));
        rs.put("OUTSTOCK_NO", Const.EMPTY_CH);
        rs.put("CARGO_NO", Const.EMPTY_CH);
        rs.put("LONG_UNIT", Const.EMPTY_CH);
        rs.put("WIDE_UNIT", Const.EMPTY_CH);
        rs.put("HIGH_UNIT", Const.EMPTY_CH);
        rs.put("VOLUME_UNIT", Const.EMPTY_CH);
        rs.put("TOTAL_SUTTLE", Const.EMPTY_CH);
        rs.put("TOTAL_WEIGHT", Const.EMPTY_CH);
        rs.put("ASIGN_LOCATOR", Const.EMPTY_CH);
        rs.put("PUT_LOCATOR", Const.EMPTY_CH);
        rs.put("RECEIV_QTY", src.getString("RECEIV_QTY"));
        rs.put("EA_NUM", src.getString("RECEIV_QTY"));
        //收货状态，分配状态，码放状态
        rs.put("RECEIPT_STATE", WmsEnum.InstockState.ALL.getItemKey());
        rs.put("ASSIGNED_STATE", WmsEnum.AssignedState.NONE.getItemKey());
        rs.put("PUT_STATUS", WmsEnum.PutState.NONE.getItemKey());

        rs.put("ASSIGNED_STOCK_STATE", WmsEnum.AssignedState.NONE.getItemKey());
        rs.put("ASSIGNED_STOCK_NUM", 0);
        rs.put("PICK_STATE", WmsEnum.PickState.NONE.getItemKey());
        //CARGO_STATE  DEPOT_TYPE FREEZE_STATE
        rs.put("CARGO_STATE", WmsEnum.CargoState.NONE.getItemKey());
        rs.put("DEPOT_TYPE", WmsEnum.DepotState.NONE.getItemKey());
        rs.put("FREEZE_STATE", WmsEnum.FreezeState.NONE.getItemKey());
        rs.put("BOX_STATE", Const.EMPTY_CH);
        rs.put("MEMO", Const.EMPTY_CH);
        rs.put("GROUP_KEY", src.getString("GROUP_KEY"));

        rs.put("LOT_NO", src.getString("LOT_NO"));
        rs.put("DATE_CODE", src.getString("DATE_CODE"));
        rs.put("SEPARATE_QTY", src.getString("PACKAGE_QTY"));
        rs.put("COO", src.getString("COO"));
        rs.put("BIN_CODE", src.getString("BIN_CODE"));
        rs.put("REMARK1",src.getString("REMARK1"));
        rs.put("REMARK2",src.getString("REMARK2"));
        rs.put("REMARK3",src.getString("REMARK3"));

        if (isImp) {
            String locatorId = src.getString("LOCATOR_ID");
            if (StringUtil.isNotEmpty(locatorId)) {
                rs.put("ASIGN_STORAGE", src.getString("STORAGE_ID"));
                rs.put("ASIGN_LOCATOR", locatorId);

                rs.put("PUT_STORAGE", src.getString("STORAGE_ID"));
                rs.put("PUT_LOCATOR", locatorId);

                rs.put("ASSIGNED_STATE", WmsEnum.AssignedState.ALL.getItemKey());
                rs.put("PUT_STATUS", WmsEnum.PutState.ALL.getItemKey());
            }

        }
        setBaseInfo(rs);
        return rs;
    }

    public static PageData addBox4Pros(PageData src,PageData box) {
        if (box == null) {
            src.put("LOT_CODE", src.getString("LOT_NO"));
            src.put("DATE_CODE", src.getString("DATE_CODE"));
            src.put("COO", src.getString("COO"));
            src.put("BIN_CODE", src.getString("BIN_CODE"));
        } else {
            src.put("LOT_CODE", box.getStringNot4EmpyCh("LOT_NO"));
            src.put("DATE_CODE", box.getStringNot4EmpyCh("DATE_CODE"));
            src.put("COO", box.getStringNot4EmpyCh("COO"));
            src.put("BIN_CODE", box.getStringNot4EmpyCh("BIN_CODE"));
        }
        return src;
    }

    private static String getId(PageData pd, String colum) {
        String id = pd.getString(colum);
        return StringUtil.isEmpty(id) ? UuidUtil.get32UUID() : id;
    }

    private static void setBaseInfo(PageData pd) {
        pd.put("CREATE_EMP", SessionUtil.getCurUserName());
        pd.put("CREATE_TM", Tools.date2Str(new Date()));
        pd.put("MODIFY_EMP", SessionUtil.getCurUserName());
        pd.put("MODIFY_TM", Tools.date2Str(new Date()));
        pd.put("DEL_FLG", Const.DEL_NO);
    }

    private static void setProduct(PageData dest,PageData src) {
        String productId = src.getString("PRODUCT_ID");
        if(StringUtil.isNotEmpty(productId)) {
            dest.put("PRODUCT_ID", productId);
            return;
        }
        dest.put("PRODUCT_ID",getProductIdByCode(src.getString("PROD_CODE")));
    }

    private static String getProductIdByCode(String prodCode) {
       return BaseInfoCache.getInstance().getProductId(prodCode);
    }

    private static boolean isEmpty(String str) {
        return StringUtil.isEmpty(str);
    }
}
