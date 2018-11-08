package com.hw.util;

import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Panda.HuangWei.
 * @version 1.0 .
 * @since 2017.01.11 11:00.
 */
public class RpSqlGenUtil {

    public static String buildInboundSql(Map<String, String> orderMap, Map<String, String> batchMap, List<String> colums, List<String> columName) {
        StringBuffer sb = new StringBuffer("B.BOX_NO AS CARTONID,");
        Map<String, String> stringMap = WmsEnum.InboundRpColum.map;

        Map<String, String> orderMap2 = toUpperCase(orderMap);
        String aliasKey = WmsEnum.InboundRpColum.INVOICENO.getItemKey();
        String cloum = orderMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C1." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        //SHIPPER
        aliasKey = WmsEnum.InboundRpColum.SHIPPER.getItemKey();
        cloum = orderMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C1." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        //产品编码
        aliasKey = WmsEnum.InboundRpColum.ITEMCODE.getItemKey();
        sb.append(" D.PRODUCT_CODE AS " + stringMap.get(aliasKey) + ",");

        Map<String, String> batchMap2 = toUpperCase(batchMap);

        //SPS
        aliasKey = WmsEnum.InboundRpColum.SPS.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        //REMARK1
        aliasKey = WmsEnum.InboundRpColum.R1.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            aliasKey = WmsEnum.InboundRpColum.REMARK1.getItemKey();
            cloum = batchMap2.get(aliasKey);
            if (StringUtil.isNotEmpty(cloum)) {
                sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
            } else {
                sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
            }
        }

        //REMARK2
        aliasKey = WmsEnum.InboundRpColum.R2.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            aliasKey = WmsEnum.InboundRpColum.REMARK2.getItemKey();
            cloum = batchMap2.get(aliasKey);
            if (StringUtil.isNotEmpty(cloum)) {
                sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
            } else {
                sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
            }
        }
        //LOT_CODE
        aliasKey = WmsEnum.InboundRpColum.LOTCODE.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        //DATECODE
        aliasKey = WmsEnum.InboundRpColum.DATECODE.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            aliasKey = WmsEnum.InboundRpColum.DATECODE_1.getItemKey();
            cloum = batchMap2.get(aliasKey);
            if (StringUtil.isNotEmpty(cloum)) {
                sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
            } else {
                sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
            }
        }

        //ASSIGNED_EA
        sb.append(" B.EA_EA AS QTY ,");

        //SEPARATE_QTY
        aliasKey = WmsEnum.InboundRpColum.SEPARATEQTY.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        String dtlSql = buildAllSql(turnKeyValue(orderMap), turnKeyValue(batchMap), colums, columName);
        String rs = sb.append(dtlSql).toString();
        return rs.substring(0, rs.length() - 1);
    }

   /* SELECT
    A.CUSTOMER_ID,
    B.BOX_NO,
    C1.TXT_ONE        INVOICE_NO,
    D.PRODUCT_CODE,
            '' SPS,
    C2.TXT_SIX        REMARK1,
    C2.TXT_FIV        REMARK2,
    C2.TXT_ELEVEN     LOT_CODE,
    C2.TXT_TWELVE     DATE_CODE,
    B.ASSIGNED_EA,
    C2.TXT_NIG        SEPARATE_QTY,
    C1.TXT_EIG        SHIPPER
    FROM TM_STOCKMGRIN A,
    TM_STOCKMGRIN_DTL B,
    TM_STOCKMGRIN_PROPERTY C1,
    TM_STOCKMGRIN_PROPERTY C2,
    TB_PRODUCT D
    WHERE A.STOCKMGRIN_ID = B.STOCKMGRIN_ID
    AND A.STOCKMGRIN_ID = C1.STOCKMGRIN_ID
    AND B.STOCKMGRIN_ID = C2.STOCKMGRIN_ID
    AND B.STOCKDTLMGRIN_ID = C2.STOCKDTLMGRIN_ID
    AND B.PRODUCT_ID = D.PRODUCT_ID*/

    public static String buildOutboundSql(Map<String, String> orderMap, Map<String, String> batchMap, List<String> colums, List<String> columName) {
        StringBuffer sb = new StringBuffer("B.BOX_NO AS CARTONID,");
        Map<String, String> stringMap = WmsEnum.InboundRpColum.map;

        Map<String, String> orderMap2 = toUpperCase(orderMap);
        String aliasKey = WmsEnum.InboundRpColum.INVOICENO.getItemKey();
        String cloum = orderMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C1." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }


        //SHIPPER
        aliasKey = WmsEnum.InboundRpColum.SHIPPER.getItemKey();
        cloum = orderMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C1." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        //SHIPPER
        aliasKey = WmsEnum.InboundRpColum.TRANDATE.getItemKey();
        cloum = orderMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C1." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }


        //产品编码
        aliasKey = WmsEnum.InboundRpColum.ITEMCODE.getItemKey();
        sb.append(" D.PRODUCT_CODE AS " + stringMap.get(aliasKey) + ",");

        Map<String, String> batchMap2 = toUpperCase(batchMap);
        //SPS
        aliasKey = WmsEnum.InboundRpColum.SPS.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        //REMARK1
        aliasKey = WmsEnum.InboundRpColum.R1.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            aliasKey = WmsEnum.InboundRpColum.REMARK1.getItemKey();
            cloum = batchMap2.get(aliasKey);
            if (StringUtil.isNotEmpty(cloum)) {
                sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
            } else {
                sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
            }
        }

        //REMARK2
        aliasKey = WmsEnum.InboundRpColum.R2.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            aliasKey = WmsEnum.InboundRpColum.REMARK2.getItemKey();
            cloum = batchMap2.get(aliasKey);
            if (StringUtil.isNotEmpty(cloum)) {
                sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
            } else {
                sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
            }
        }
        //LOT_CODE
        aliasKey = WmsEnum.InboundRpColum.LOTCODE.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        //DATECODE
        aliasKey = WmsEnum.InboundRpColum.DATECODE.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            aliasKey = WmsEnum.InboundRpColum.DATECODE_1.getItemKey();
            cloum = batchMap2.get(aliasKey);
            if (StringUtil.isNotEmpty(cloum)) {
                sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
            } else {
                sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
            }
        }

        //ASSIGNED_EA
        sb.append(" B.EA_EA AS ORDER_QTY ,");

        //SEPARATE_QTY
        aliasKey = WmsEnum.InboundRpColum.SEPARATEQTY.getItemKey();
        cloum = batchMap2.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
        }

        String dtlSql = buildAllSql(turnKeyValue(orderMap), turnKeyValue(batchMap), colums, columName);
        String rs = sb.append(dtlSql).toString();
        return rs.substring(0, rs.length() - 1);
    }


    public static String buildInboundRemarkSql(Map<String, String> orderMap, Map<String, String> batchMap1, List<String> colums, List<String> columName) {
        StringBuffer sb = new StringBuffer(" ");
        Map<String, String> stringMap = WmsEnum.InboundRpColum.map;

        Map<String, String> batchMap = toUpperCase(batchMap1);
        //REMARK1
        String aliasKey = WmsEnum.InboundRpColum.R1.getItemKey();
        String cloum = batchMap.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            aliasKey = WmsEnum.InboundRpColum.REMARK1.getItemKey();
            cloum = batchMap.get(aliasKey);
            if (StringUtil.isNotEmpty(cloum)) {
                sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
            } else {
                sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
            }
        }

        //REMARK2
        aliasKey = WmsEnum.InboundRpColum.R2.getItemKey();
        cloum = batchMap.get(aliasKey);
        if (StringUtil.isNotEmpty(cloum)) {
            sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
        } else {
            aliasKey = WmsEnum.InboundRpColum.REMARK2.getItemKey();
            cloum = batchMap.get(aliasKey);
            if (StringUtil.isNotEmpty(cloum)) {
                sb.append("C2." + cloum + " AS " + stringMap.get(aliasKey) + ",");
            } else {
                sb.append(" '' AS " + stringMap.get(aliasKey) + ",");
            }
        }

        //String dtlSql = buildAllSql(turnKeyValue(orderMap), turnKeyValue(batchMap), colums,columName);
        String rs = sb.toString();

        return rs.substring(0, rs.length() - 1);
    }

    private static Map<String, String> turnKeyValue(Map<String, String> map) {
        Map<String, String> rsMap = new HashMap<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            rsMap.put(entry.getValue(), entry.getKey());
        }
        return rsMap;
    }

    private static Map<String, String> toUpperCase(Map<String, String> map) {
        Map<String, String> rsMap = new HashMap<>();
        if (map == null || map.isEmpty()) {
            return rsMap;
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            rsMap.put(entry.getKey().toUpperCase(), entry.getValue().toUpperCase());
        }
        return rsMap;
    }

    public static Pair<String, String> buildInOutStockPropertiesSql(Map<String, String> orderMap, Map<String, String> batchMap,
                                                                    List<String> orderColums, List<String> orderColumName
            , List<String> batchColums, List<String> batchColumName) {

        //订单属性对应字段sql,批次属性对应字段sql
        return buildPropertiesSql(turnKeyValue(orderMap), turnKeyValue(batchMap), orderColums, orderColumName, batchColums, batchColumName);
    }

    private static Pair<String, String> buildPropertiesSql(Map<String, String> orderMap, Map<String, String> batchMap, List<String> orderColums, List<String> orderColumName
            , List<String> batchColums, List<String> batchColumName) {
        List<String> columList = WmsEnum.OrderProperty.columList;
        StringBuffer sb = new StringBuffer(256);
        int i = 0;
        for (String colum : columList) {
            String va = orderMap.get(colum);
            if (StringUtil.isNotEmpty(va)) {
                orderColums.add(va);
                orderColumName.add(Const.ITEM + i);
                sb.append("C1." + colum + " AS " + Const.ITEM + i + ",");

                i += 1;
            }
        }
        i = 0;
        StringBuffer sb2 = new StringBuffer(256);
        for (String colum : columList) {
            String va = batchMap.get(colum);
            if (StringUtil.isNotEmpty(va)) {
                batchColums.add(va);
                batchColumName.add(Const.ITEM + i);
                sb2.append("C2." + colum + " AS " + Const.ITEM + i + ",");
                i += 1;
            }
        }
        String orderSql = sb.toString();
        if (orderSql.length() > 2) {
            orderSql = orderSql.substring(0, orderSql.length() - 1);
        }
        String batchSql = sb2.toString();
        if (batchSql.length() > 2) {
            batchSql = batchSql.substring(0, batchSql.length() - 1);
        }

        return Pair.of(orderSql, batchSql);
    }

    private static String buildAllSql(Map<String, String> orderMap, Map<String, String> batchMap, List<String> colums, List<String> columName) {
        List<String> columList = WmsEnum.OrderProperty.columList;
        StringBuffer sb = new StringBuffer(256);
        int i = 0;
        for (String colum : columList) {
            String va = orderMap.get(colum);
            if (StringUtil.isNotEmpty(va)) {
                colums.add("H." + va);
                columName.add(Const.ITEM + i);
                sb.append("C1." + colum + " AS " + Const.ITEM + i + ",");

                i += 1;
            }
        }

        for (String colum : columList) {
            String va = batchMap.get(colum);
            if (StringUtil.isNotEmpty(va)) {
                colums.add("B." + va);
                columName.add(Const.ITEM + i);
                sb.append("C2." + colum + " AS " + Const.ITEM + i + ",");
                i += 1;
            }
        }
        return sb.toString();
    }

    private String buildOrderSql(Map<String, String> map, List<String> colums) {
        List<String> columList = WmsEnum.OrderProperty.columList;
        StringBuffer sb = new StringBuffer(256);
        int i = 0;
        for (String colum : columList) {
            String va = map.get(colum);
            if (StringUtil.isNotEmpty(va)) {
                colums.add("H." + va);
                sb.append("C1." + colum + " AS " + Const.ITEM + i + ",");
                i += 1;
            }
        }
        return sb.toString();
    }

    private String buildBatchSql(Map<String, String> map, List<String> colums) {
        List<String> columList = WmsEnum.OrderProperty.columList;
        StringBuffer sb = new StringBuffer(256);
        int i = 0;
        for (String colum : columList) {
            String va = map.get(colum);
            if (StringUtil.isNotEmpty(va)) {
                colums.add("B." + va);
                sb.append("C2." + colum + " AS " + Const.ITEM + i + ",");
                i += 1;
            }
        }
        return sb.toString();
    }

   /* SELECT
    A.OUTSTOCK_NO     REF,
    B.BOX_NO          CARTONID,
    C1.TXT_TWO        INVOICE_NO,
    '' TRAN_DATE,
    D.PRODUCT_CODE    ITEMCODE,
    ''                SPS,
    C2.TXT_SIX        REMARK1,
    C2.TXT_FIV        REMARK2,
    C2.TXT_ELEVEN     LOT_CODE,
    C2.TXT_TWELVE     DATE_CODE,
    B.EA_EA           ORDER_QTY,
    C2.TXT_NIG        SEPARATE_QTY,
    C2.TXT_TEN        COO,
    C1.TXT_SIX        SHIPPER
    FROM TM_STOCKMGROUT A,
    TM_STOCKMGROUT_DTL B,
    TM_STOCKMGROUT_PROPERTY C1,
    TM_STOCKMGROUT_PROPERTY C2,
    TB_PRODUCT D
    WHERE A.STOCKMGROUT_ID = B.STOCKMGROUT_ID
    AND A.STOCKMGROUT_ID = C1.STOCKMGROUT_ID
    AND B.STOCKMGROUT_ID = C2.STOCKMGROUT_ID
    AND B.STOCKDTLMGROUT_ID = C2.STOCKDTLMGROUT_ID
    AND B.PRODUCT_ID = D.PRODUCT_ID*/
}
