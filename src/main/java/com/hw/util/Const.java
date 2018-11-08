/*
* Copyright (c) 2016 Panda.HuangWei. All Rights Reserved.
*/
package com.hw.util;

import org.omg.CORBA.INTERNAL;
import org.springframework.context.ApplicationContext;

import java.util.*;

/**
 * Created : Panda.HuangWei
 * Date : 2016/9/5 14:18
 */
public class Const {
    public static final String ADMIN_CODE = "administrator";
    public static final String SESSION_SECURITY_CODE = "sessionSecCode";
    public static final String SESSION_USER = "sessionUser";
    public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
    public static final String SESSION_menuList = "menuList";			//当前菜单
    public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
    public static final String SESSION_QX = "QX";
    public static final String SESSION_userpds = "userpds";
    public static final String SESSION_USERROL = "USERROL";				//用户对象
    public static final String SESSION_USERNAME = "USERNAME";			//用户名
    public static final String TRUE = "T";
    public static final String FALSE = "F";
    public static final String LOGIN = "/login_toLogin.do";				//登录地址
    public static final String SYSNAME = "admin/config/SYSNAME.txt";	//系统名称路径
    public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
    public static final String EMAIL = "admin/config/EMAIL.txt";		//邮箱服务器配置路径
    public static final String SMS1 = "admin/config/SMS1.txt";			//短信账户配置路径1
    public static final String SMS2 = "admin/config/SMS2.txt";			//短信账户配置路径2
    public static final String FWATERM = "admin/config/FWATERM.txt";	//文字水印配置路径
    public static final String IWATERM = "admin/config/IWATERM.txt";	//图片水印配置路径
    public static final String WEIXIN	= "admin/config/WEIXIN.txt";	//微信配置路径
    public static final String WEBSOCKET = "admin/config/WEBSOCKET.txt";//WEBSOCKET配置路径
    public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";	//图片上传路径
    public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
    public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
    public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(code)|(app)|(weixin)|(static)|(main)|(websocket)).*";	//不对匹配该值的访问路径拦截（正则）

    public static final String CUSTOMER_FILE_PATH = "uploadFiles/deal/";

    public static final String PICKONE	= "admin/config/PICKONE.txt";//拣货单流水号

    public static final Set<String> ADMINUSET = new HashSet<>(Arrays.asList(new String[]{"panda","admin","administrator"}));

    public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化

    /**
     * APP Constants
     */
    //app注册接口_请求协议参数)
    public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
    public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};

    //app根据用户名获取会员信息接口_请求协议中的参数
    public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
    public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};

    public static final String SYS_SPLIT = ",hw,";

    /*权限参数*/
    public static final String AUTH_ADD = "add";
    public static final String AUTH_DEL = "del";
    public static final String AUTH_EDIT = "edit";
    public static final String AUTH_QUERY = "cha";

    public static final String AUTH_ADDS = "adds";
    public static final String AUTH_DELS = "dels";
    public static final String AUTH_EDITS = "edits";
    public static final String AUTH_QUERYS = "chas";

    //批次属性参考公司代码
    public static final String ORDERBATCH_CUSTOMERCODE = "SPREA";
    //导入收货默认库位代码
    public static final String PICK_DEFAULT_LOCATOR_CODE = "I";

    public static final String INSTOCK_ORDER = "-INO";
    public static final String INSTOCK_BATCH = "-INB";
    public static final String INSTOCK_BATCH_EMPTY = "-INB-";

    public static final String OUTSTOCK_ORDER = "-OUTO";
    public static final String OUTSTOCK_BATCH = "-OUTB";
    public static final String OUTSTOCK_BATCH_EMPTY = "-OUTB-";
    public static final String PROPERTY_ORDER = "C1.";
    public static final String PROPERTY_BATCH = "C2.";
    public static final String AS = "AS";
    public static final String EMPTY_SEARCH = "' '";

    public static final String INSTOCK_MID = "-R-";
    public static final String OUTSTOCK_MID = "-S-";

    public static final String REMARK_ASSIGNED = "-ASN-";

    public static final String CARGO_DFCUSTOMER = "1";
    public static final String CARGO_NO = "-C-";
    public static final String EMPTY_CH = "";


    public static final String DEL_YES = "1";
    public static final String DEL_NO = "0";
    //分隔符，冒号，点，逗号，横线，斜线
    public static final String SPLIT_COLON = ":";
    public static final String SPLIT_POINT = ".";
    public static final String SPLIT_COMMA = ",";
    public static final String SPLIT_SEMI= ";";
    public static final String SPLIT_LINE = "-";
    public static final String UNDER_LINE = "_";
    public static final String SPLIT_SLANT = "/";

    public static final Integer ZERO = 0;
    public static final String ZERO_CH = "0";
   //操作类型，1自动分配，2 手工分配，3 缺省分配，4取消分配，1 详情配载 2 缺省配载
    public static final String OPT_EVEN_1 = "1";
    public static final String OPT_EVEN_2 = "2";
    public static final String OPT_EVEN_3 = "3";
    public static final String OPT_EVEN_4 = "4";
    public static final String OPT_EVEN_5 = "5";

    public static final String UN_DEFIND = "unDefined";

    public static final String PREFIX_PROD = "1P";
    public static final String PREFIX_QTY = "Q";
     //空格符号
    public static final String EMPTY_SPACE = "\\s*";

    //批次属性字段
    public static final String BP_INBOUNDTM = "入库日期";
    public static final String BP_OUT_BOX = "外箱号";
    public static final String BP_COO = "COO";
    public static final String BP_PACKAGEQTY_1 = "PackageQty";
    public static final String BP_PACKAGEQTY_2 = "SeparateQTY";
    public static final String BP_DATACODE = "DC";
    public static final String BP_DATECODE = "DateCode";
    public static final String BP_DATECODE2 = "DATECODE";
    public static final String BP_DATECODE3 = "DATE CODE";
    public static final String BP_LOT_NO = "LOTNO";
    public static final String BP_LOTCODE = "LOTCODE";
    public static final String BP_SCANCODE = "ScanCode";
    public static final String BP_BIN = "BIN";
    public static final String BP_CASENUMBER = "CaseNumber";

    public static final String BP_REMARK_CN ="特別標註(1)";
    public static final String BP_REMARK_EN ="Remark1";

    //对应收货页面字段
    public static final String BP_COO_SRC = "COO";
    public static final String BP_PACKAGEQTY_SRC = "PACKAGE_QTY";
    public static final String BP_DATACODE_SRC = "DATE_CODE";
    public static final String BP_LOTNO_SRC = "LOT_NO";
    public static final String BP_SCANCODE_SRC= "SUPPLIER_PROD";
    public static final String BP_BIN_SRC = "BIN_CODE";
    public static final String BP_CASENUMBER_SRC = "BOX_NO";
    public static final String BP_OUT_BOX_SRC = "BIG_BOX_NO";

    public static final String BOX_BIG_BOX_NO = "BIG_BOX_NO";
    public static final String BOX_LOT_NO = "LOT_NO";
    public static final String BOX_DATE_CODE = "DATE_CODE";
    public static final String BOX_SEPARATE_QTY = "SEPARATE_QTY";
    public static final String BOX_COO = "COO";
    public static final String BOX_BIN_CODE = "BIN_CODE";

    //导入收货的属性字段
    public static final String IMP_REMARK1 = "REMARK1";
    public static final String IMP_REMARK2 = "REMARK2";
    public static final String IMP_REMARK3 = "REMARK3";
    public static final String IMP_SeparateQty = "SeparateQty1";
    public static final String IMP_SeparateQty1 = "SeparateQty1";
    public static final String IMP_SeparateQty2 = "SeparateQty2";
    public static final String IMP_GoodsBase = "GoodsBase";
    public static final String IMP_IDS = "IDS";
    public static final String IMP_LotType = "LotType";
    public static final String IMP_InvNo = "InvNo";
    public static final String IMP_TEXT03 = "TEXT03";
    public static final String IMP_TEXT11 = "TEXT11";
    public static final String IMP_TEXT12 = "TEXT12";
    public static final String IMP_TEXT14 = "TEXT14";
    public static final String IMP_TEXT17 = "TEXT17";
    public static final String IMP_TEXT20 = "TEXT20";

    public static final String RECEIVE_INV_NO_1 = "CustomerInv#";
    public static final String RECEIVE_INV_NO_2 = "InvNo";
    public static final String RECEIVE_INV_NO_PAGE = "INV_NO";


    public static final String RECEIVE_YES = "1";
    public static final String RECEIVE_NO = "0";
    //缺省分配库位Id
    public static final String DEFAULT_LOCATOR_ID = "qsfp";

    //箱号校验字段
    public static final String MIN_A = "A";
    public static final Integer BATCH_SHOW_SIZE = 50;
    public static final Integer BOXNO_SIZE = 17;
    public static final Set<String> BOX_M_SEC = new HashSet<>(Arrays.asList(new String[]{"O","M","I"}));
    public static final Set<String> BOX_M_Thr = new HashSet<>(Arrays.asList(new String[]{"MIX","NIC","EPC","LIT","SAM","AVX","OTH","RMA","FPC","KEM","SPR"}));

    public static final String SRC_BOX_PRE_SIX = "O";
    public static final String SPREAO_SRC_BOX_PRE = "SPREAO";
    public static final String SPREAO_SRC_BOX_MIX = "MIX";

    public static final String FIRST_IN_FIRST_OUT = "1";
    public static final String FIRST_IN_LATER_OUT = "2";

    public static final String ASSIGNED_FLG_YES = "1";
    public static final String ASSIGNED_FLG_NO = "0";

    public static final String ITEM = "ITEM_";
    public static final String SEARCH_TYPE = "SEARCH_TYPE";
    //报表参数前缀
    public static final String RP_VAR = "var";

    public static final String MODIFY_REMARK = "-M-";
    public static final String BOX_ID = "BOX_ID";

    public static final int SHOW_SIZE = 99;

    public static final int SAVE_SIZE = 100;

}
