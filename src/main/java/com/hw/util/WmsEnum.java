package com.hw.util;

import com.hw.entity.base.Select;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Panda.HuangWei on 2016/10/25.
 */
public interface WmsEnum {
    public boolean eq(String key);

    public String getItemKey();

    public String getItemValue();

    public enum OrderProperty implements WmsEnum {

        TIME_ONE("TIME_ONE", "扩展日期字段1", 1),
        TIME_TWO("TIME_TWO", "扩展日期字段2", 2),
        TIME_THR("TIME_THR", "扩展日期字段3", 3),
        TIME_FOU("TIME_FOU", "扩展日期字段4", 4),
        TIME_FIV("TIME_FIV", "扩展日期字段5", 5),
        TIME_SIX("TIME_SIX", "扩展日期字段6", 6),
        TIME_SEV("TIME_SEV", "扩展日期字段7", 7),
        TIME_EIG("TIME_EIG", "扩展日期字段8", 8),
        TIME_NIG("TIME_NIG", "扩展日期字段9", 9),
        TIME_TEN("TIME_TEN", "扩展日期字段10", 10),

        NUM_ONE("NUM_ONE", "扩展数字字段1", 11),
        NUM_TWO("NUM_TWO", "扩展数字字段2", 12),
        NUM_THR("NUM_THR", "扩展数字字段3", 13),
        NUM_FOU("NUM_FOU", "扩展数字字段4", 14),
        NUM_FIV("NUM_FIV", "扩展数字字段5", 15),
        NUM_SIX("NUM_SIX", "扩展数字字段6", 16),
        NUM_SEV("NUM_SEV", "扩展数字字段7", 17),
        NUM_EIG("NUM_EIG", "扩展数字字段8", 18),
        NUM_NIG("NUM_NIG", "扩展数字字段9", 19),
        NUM_TEN("NUM_TEN", "扩展数字字段10", 20),


        TXT_ONE("TXT_ONE", "扩展文本字段1", 21),
        TXT_TWO("TXT_TWO", "扩展文本字段2", 22),
        TXT_THR("TXT_THR", "扩展文本字段3", 23),
        TXT_FOU("TXT_FOU", "扩展文本字段4", 24),
        TXT_FIV("TXT_FIV", "扩展文本字段5", 25),
        TXT_SIX("TXT_SIX", "扩展文本字段6", 26),
        TXT_SEV("TXT_SEV", "扩展文本字段7", 27),
        TXT_EIG("TXT_EIG", "扩展文本字段8", 28),
        TXT_NIG("TXT_NIG", "扩展文本字段9", 29),
        TXT_TEN("TXT_TEN", "扩展文本字段10", 30),

        TXT_ELEVEN("TXT_ELEVEN", "扩展文本字段11", 31),
        TXT_TWELVE("TXT_TWELVE", "扩展文本字段12", 32),
        TXT_THIRT("TXT_THIRT", "扩展文本字段13", 33),
        TXT_FOURT("TXT_FOURT", "扩展文本字段14", 34),
        TXT_FIFT("TXT_FIFT", "扩展文本字段15", 35),
        TXT_SIXT("TXT_SIXT", "扩展文本字段16", 36),
        TXT_SEVENT("TXT_SEVENT", "扩展文本字段17", 37),
        TXT_EIGHT("TXT_EIGHT", "扩展文本字段18", 38),
        TXT_NINET("TXT_NINET", "扩展文本字段19", 39),
        TXT_TWENT("TXT_TWENT", "扩展文本字段20", 40),
        TXT_21("TXT_21", "扩展文本字段21", 41),
        TXT_22("TXT_22", "扩展文本字段22", 42),
        TXT_23("TXT_23", "扩展文本字段23", 43),
        TXT_24("TXT_24", "扩展文本字段24", 44),
        TXT_25("TXT_25", "扩展文本字段25", 45),
        TXT_26("TXT_26", "扩展文本字段26", 46),
        TXT_27("TXT_27", "扩展文本字段27", 47),
        TXT_28("TXT_28", "扩展文本字段28", 48),
        TXT_29("TXT_29", "扩展文本字段29", 49),
        TXT_30("TXT_30", "扩展文本字段30", 50);

        private String key;
        private String value;
        private Integer order;
        public static Map<String, String> coluMap = new HashMap<>(64);
        public static Map<String, Pair<String, Integer>> coluSortMap = new HashMap<>(64);
        public static Map<Integer, String> sortColuMap = new HashMap<>(64);
        public static List<String> columList = new ArrayList<>(64);

        static {
            for (OrderProperty e : OrderProperty.values()) {
                coluMap.put(e.getItemKey(), e.getItemValue());
                coluSortMap.put(e.getItemKey(), Pair.of(e.getItemValue(), e.getSort()));
                sortColuMap.put(e.getSort(), e.getItemKey());
                columList.add(e.getItemKey());
            }
        }

        private OrderProperty(String itemKey, String itemValue, Integer order) {
            this.key = itemKey;
            this.value = itemValue;
            this.order = order;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public Integer getSort() {
            return order;
        }

    }

     enum OrderPropertyID implements WmsEnum {
        IN_ORDER("60", "入库单订单属性"), OUT_ORDER("61", "出库单订单属性"), IN_BATCH("62", "入库单批次属性"), OUT_BATCH("63", "出库单批次属性");

        private String key;
        private String value;
        public static Map<String, String> map = new HashMap<>();

        static {
            for(OrderPropertyID s : OrderPropertyID.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }
        private OrderPropertyID(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

         public static String getName(String key) {
             String value = map.get(key);
             return StringUtil.isEmpty(value)? OrderPropertyID.IN_ORDER.getItemValue():value;
         }
    }


    public enum OrderPropertyType implements WmsEnum {
        IN_ORDER("1", "入库单订单属性"), OUT_ORDER("2", "出库单订单属性"), IN_BATCH("3", "入库单批次属性"), OUT_BATCH("4", "出库单批次属性");

        private String key;
        private String value;

        private OrderPropertyType(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    /**
     * 净重单位，key 对应数据库ID
     */
    public enum SuttleUnit implements WmsEnum {
        OT("30", "OT", "其他"), PL("31", "PL", "托盘"), CS("32", "CS", "箱"), IP("33", "IP", "内包装"), EA("34", "EA", "EA");

        private String key;
        private String value;
        private String desc;
        public static Map<String, String> idTypeMap = new HashMap<>();

        static {
            for(SuttleUnit s : SuttleUnit.values()) {
                idTypeMap.put(s.getItemKey(),s.getItemValue());
            }
        }
        private SuttleUnit(String itemKey, String itemValue, String desc) {
            this.key = itemKey;
            this.value = itemValue;
            this.desc = desc;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public static String getValue(String key) {
            String rs = idTypeMap.get(key);
            return StringUtil.isEmpty(rs) ? Const.UN_DEFIND : rs;
        }
    }

    /**
     * 毛重单位，key 对应数据库ID
     */
    public enum WeightUnit implements WmsEnum {
        OT("23", "OT", "其他"), PL("25", "PL", "托盘"), CS("26", "CS", "箱"), IP("27", "IP", "内包装"), EA("28", "EA", "EA");

        private String key;
        private String value;
        private String desc;
        public static Map<String, String> idTypeMap = new HashMap<>();

        static {
            for(WeightUnit s : WeightUnit.values()) {
                idTypeMap.put(s.getItemKey(),s.getItemValue());
            }
        }

        private WeightUnit(String itemKey, String itemValue, String desc) {
            this.key = itemKey;
            this.value = itemValue;
            this.desc = desc;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public static String getValue(String key) {
            String rs = idTypeMap.get(key);
            return StringUtil.isEmpty(rs) ? Const.UN_DEFIND : rs;
        }
    }

    /**
     * 体积单位，key 对应数据库ID
     */
    public enum VolumeUnit implements WmsEnum {
        OT("36", "OT", "其他"), PL("37", "PL", "托盘"), CS("38", "CS", "箱"), IP("39", "IP", "内包装"), EA("40", "EA", "EA");

        private String key;
        private String value;
        private String desc;
        public static Map<String, String> idTypeMap = new HashMap<>();

        static {
            for(VolumeUnit s : VolumeUnit.values()) {
                idTypeMap.put(s.getItemKey(),s.getItemValue());
            }
        }

        private VolumeUnit(String itemKey, String itemValue, String desc) {
            this.key = itemKey;
            this.value = itemValue;
            this.desc = desc;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public static String getValue(String key) {
            String rs = idTypeMap.get(key);
            return StringUtil.isEmpty(rs) ? Const.UN_DEFIND : rs;
        }

    }

    enum PriorityLevel implements WmsEnum {
        ONE("97", "1"), TWO("98", "2"), THR("99", "3"), FOUR("103", "4"), FIVE("104", "5");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(PriorityLevel s : PriorityLevel.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        private PriorityLevel(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? PriorityLevel.ONE.getItemValue():value;
        }
    }

    enum RpSearchType implements WmsEnum {
        OLD("OLD", "30分钟前数据"), NEW("NEW", "即时数据");

        private String key;
        private String value;

        RpSearchType(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }
    }

    public enum SortBy implements WmsEnum {
        ASC("ASC", "从小到大"), DESC("2", "从大到小");

        private String key;
        private String value;
        public static Map<String, String> keyValueMap = new HashMap<>();
        public static List<Select> list = new ArrayList<>();
        static {
            for(SortBy s : SortBy.values()) {
                keyValueMap.put(s.getItemKey(),s.getItemValue());

                Select select = new Select();
                select.setId(s.getItemKey());
                select.setValue(s.getItemValue());
                list.add(select);
            }
        }

        private SortBy(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    /**
     * 收货状态,对应tb_select 中的id
     */
    public enum InstockState implements WmsEnum {
        NONE("75", "未收货"), PART("76", "部分收货"), ALL("77", "全部收货");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for (InstockState e :InstockState.values()) {
                map.put(e.getItemKey(),e.getItemValue());
            }
        }

        private InstockState(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? InstockState.NONE.getItemValue():value;
        }

    }
    /**
     * 分配状态,对应tb_select 中的id
     */
    enum AssignedState implements WmsEnum {
        NONE("100", "未分配"), PART("101", "部分分配"), ALL("102", "全部分配");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(AssignedState s : AssignedState.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        AssignedState(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? AssignedState.NONE.getItemValue():value;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    /**
     * 码放状态,对应tb_select 中的id
     */
    public enum PutState implements WmsEnum {
        NONE("80", "未码放"), PART("81", "部分码放"), ALL("82", "全部码放");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(PutState s : PutState.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        private PutState(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? PutState.NONE.getItemValue():value;
        }
    }


    /**
     * 拣货状态,对应tb_select 中的id
     */
    public enum PickState implements WmsEnum {
            NONE("139", "未拣货"), PART("140", "部分拣货"), ALL("141", "全部拣货");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(PickState s : PickState.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        private PickState(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? PickState.NONE.getItemValue():value;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    /**
     * 发货状态,对应tb_select 中的id
     */
    public enum DepotState implements WmsEnum {
        NONE("142", "未发货"), PART("143", "部分发货"), ALL("144", "全部发货");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(DepotState s : DepotState.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        private DepotState(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? DepotState.NONE.getItemValue():value;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }



    /**
     * 冻结状态,对应tb_select 中的id
     */
    public enum FreezeState implements WmsEnum {
        NONE("158", "未冻结"), PART("159", "部分冻结"), ALL("160", "全部冻结");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(FreezeState s : FreezeState.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        private FreezeState(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? FreezeState.NONE.getItemValue():value;
        }

    }

    /**
     * 配载状态,对应tb_select 中的id
     */
    public enum CargoState implements WmsEnum {
        NONE("161", "未配载"), PART("162", "部分配载"), ALL("163", "全部配载");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(CargoState s : CargoState.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        private CargoState(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? CargoState.NONE.getItemValue():value;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    //CARGO_STATE  DEPOT_TYPE FREEZE_STATE

    /**
     * LOADED_TYPE
     * 冻结状态,对应tb_select 中的id
     */
    public enum LoadedState implements WmsEnum {
        NONE("133", "未装车"), PART("134", "部分装车"), ALL("135", "全部装车");

        private String key;
        private String value;

        private LoadedState(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    public enum StorageType implements WmsEnum {
        I("170", "进货区"), O("171", "出货区"), A("172", "存货区");

        private String key;
        private String value;

        private StorageType(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    /**
     * 冻结状态,对应tb_select 中的id
     */
    public enum AuditFlg implements WmsEnum {
        APPLY("1", "申请"), PASS("2", "通过"), REJECT("3", "驳回");

        private String key;
        private String value;
        public static List<Select> list = new ArrayList<>();
        static {
            for(AuditFlg a : AuditFlg.values()) {
                Select s = new Select();
                s.setId(a.getItemKey());
                s.setValue(a.getItemValue());
                list.add(s);
            }
        }

        private AuditFlg(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    public enum AuditType implements WmsEnum {
        ADD("1", "新增箱号"), DEL("2", "删除箱号"), ADUIT("3", "调整数量");

        private String key;
        private String value;
        public static List<Select> list = new ArrayList<>();
        static {
            for(AuditType a : AuditType.values()) {
                Select s = new Select();
                s.setId(a.getItemKey());
                s.setValue(a.getItemValue());
                list.add(s);
            }
        }

        private AuditType(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }



    /**
     * Lot Code	Date Code	Qty	Separate Qty	Shipper / Consignee

     */
    public enum InboundRpColum implements WmsEnum {
        A("BOX_NO", "CARTONID"), SHIPPER("SHIPPER", "SHIPPER"), INVOICENO("INVOICENO.", "INVOICE_NO"),ITEMCODE("PRODUCT_CODE", "ITEM_CODE"),
        SPS("SPS", "SPS"),R1("特別標註(1)", "REMARK1"),R2("特別標註(2)", "REMARK2"),REMARK1("REMARK1", "REMARK1"),REMARK2("REMARK2", "REMARK2")
        ,LOTCODE("LOTCODE", "LOT_CODE"),DATECODE("DATECODE", "DATE_CODE"),SEPARATEQTY("SEPARATEQTY", "SEPARATE_QTY"),DATECODE_1("DATECODE实物", "DATE_CODE")
        ,TRANDATE("TRANDATE", "TRAN_DATE");

        private String key;
        private String value;
        public static Map<String,String> map = new HashMap<>();
        static {
            for(InboundRpColum s : InboundRpColum.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        private InboundRpColum(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

    }

    enum InStockType implements WmsEnum {
        COMMON("70", "一般"), BACK("71", "退货");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(InStockType s : InStockType.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        private InStockType(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? InStockType.COMMON.getItemValue():value;
        }

    }

    enum OutStockType implements WmsEnum {
        COMMON("145", "一般"), BACK("146", "退货");

        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(OutStockType s : OutStockType.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        OutStockType(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? OutStockType.COMMON.getItemValue():value;
        }
    }

    enum OutStockWay implements WmsEnum {
        PICK("136", "按拣货发货"), OUT("137", "按出货单发货"), CARGO("138", "按配载单发货")
        , OUT2("167", "按出货单发货"), CARGO2("168", "按配载单发货");


        private String key;
        private String value;
        private static Map<String,String> map = new HashMap<>();
        static {
            for(OutStockWay s : OutStockWay.values()) {
                map.put(s.getItemKey(),s.getItemValue());
            }
        }

        OutStockWay(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }

        public static String getName(String key) {
            String value = map.get(key);
            return StringUtil.isEmpty(value)? OutStockWay.PICK.getItemValue():value;
        }
    }

    enum Receive2D implements WmsEnum {
        PN("PN", "PROD_CODE"), LOTCODE("LOT_CODE", "LOT_NO"), QTY("QTY", "RECEIV_QTY"), BIN("BIN", "BIN_CODE");

        private String key;
        private String value;
        //字段转换map
        public static Map<String,String> colummap = new HashMap<>();
        static {
            for(Receive2D s : Receive2D.values()) {
                colummap.put(s.getItemKey(),s.getItemValue());
            }
        }

        Receive2D(String itemKey, String itemValue) {
            this.key = itemKey;
            this.value = itemValue;
        }

        @Override
        public boolean eq(String key) {
            return this.key.equals(key);
        }

        @Override
        public String getItemKey() {
            return key;
        }

        @Override
        public String getItemValue() {
            return value;
        }
    }
}
