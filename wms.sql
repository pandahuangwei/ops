/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 8.0.12 : Database - wms
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`wms` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `wms`;

/*Table structure for table `rp_breakbox` */

DROP TABLE IF EXISTS `rp_breakbox`;

CREATE TABLE `rp_breakbox` (
  `P_ID` int(11) DEFAULT NULL,
  `P_FLG` int(11) DEFAULT NULL,
  `BOX_ID` varchar(100) DEFAULT NULL,
  `BOX_NO` varchar(255) DEFAULT NULL COMMENT '箱号',
  `PRODUCT_CODE` varchar(255) DEFAULT NULL COMMENT '产品',
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `INSTOCK_NO` varchar(255) DEFAULT NULL COMMENT '入库单',
  `OUTSTOCK_NO` varchar(255) DEFAULT NULL COMMENT '出库单',
  `EA_NUM` varchar(255) DEFAULT NULL COMMENT 'EA',
  `SEPARATE_QTY` varchar(255) DEFAULT NULL,
  `COO` varchar(255) DEFAULT NULL,
  `LOCATOR_CODE` varchar(255) DEFAULT NULL COMMENT '库位',
  `INVOICE_NO` varchar(255) DEFAULT NULL,
  `INVOICE_NO1` varchar(255) DEFAULT NULL,
  `HAWB_HBL` varchar(255) DEFAULT NULL,
  `SEND_EA` varchar(255) DEFAULT NULL,
  `SEND_BOX` varchar(255) DEFAULT NULL,
  `DEPOT_STATE` varchar(255) DEFAULT NULL,
  `SI_REFERENCE` varchar(255) DEFAULT NULL,
  `CREATE_EMP` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rp_breakbox` */

/*Table structure for table `rp_instock` */

DROP TABLE IF EXISTS `rp_instock`;

CREATE TABLE `rp_instock` (
  `INSTOCK_NO` varchar(32) DEFAULT NULL COMMENT '进货单编号入货单编号',
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '客户',
  `PRE_INSTOCK_DATE` varchar(32) DEFAULT NULL COMMENT '预计入库日期',
  `REAL_INSTOCK_DATE` varchar(32) DEFAULT NULL COMMENT '实际入库日期',
  `ORDER_NO` varchar(32) DEFAULT NULL COMMENT '订单号',
  `PRODUCT_ID` varchar(32) DEFAULT NULL COMMENT '产品',
  `EA_EA` varchar(8) DEFAULT NULL COMMENT 'EA期望数',
  `EA_ACTUAL` varchar(32) DEFAULT NULL COMMENT '实际收货数',
  `ASSIGNED_EA` varchar(32) DEFAULT '0' COMMENT '分配EA',
  `PUT_EA` varchar(32) DEFAULT '0' COMMENT '码放EA',
  `INSTOCK_TYPE` varchar(32) DEFAULT NULL COMMENT '入库类型',
  `TOTAL_WEIGHT` varchar(16) DEFAULT NULL COMMENT '总毛重',
  `TOTAL_SUTTLE` varchar(16) DEFAULT NULL COMMENT '总净重',
  `TOTAL_VOLUME` varchar(16) DEFAULT NULL COMMENT '总体积',
  `TOTAL_PRICE` varchar(16) DEFAULT NULL COMMENT '总价',
  `TOTAL_EA` varchar(8) DEFAULT NULL COMMENT '总期望EA数',
  `PRIORITY_LEVEL` varchar(32) DEFAULT NULL COMMENT '优先级',
  `COST_STATE` varchar(32) DEFAULT NULL COMMENT '费用录入已完成',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `INSTOCK_STATE` varchar(32) DEFAULT NULL COMMENT '收货状态',
  `ASSIGNED_STATE` varchar(32) DEFAULT NULL COMMENT '分配状态',
  `PUT_STATE` varchar(32) DEFAULT NULL COMMENT '码放状态',
  `CARRIER_ETA` varchar(32) DEFAULT NULL,
  `CARRIER_ATA` varchar(32) DEFAULT NULL,
  `DATE03` varchar(32) DEFAULT NULL,
  `DATE04` varchar(32) DEFAULT NULL,
  `DATE05` varchar(32) DEFAULT NULL,
  `DATE06` varchar(32) DEFAULT NULL,
  `DATE07` varchar(32) DEFAULT NULL,
  `DATE08` varchar(32) DEFAULT NULL,
  `DATE09` varchar(32) DEFAULT NULL,
  `DATE10` varchar(32) DEFAULT NULL,
  `GW` varchar(8) DEFAULT NULL,
  `TOTAL_INVOICE_VALUE` varchar(8) DEFAULT NULL,
  `C1_NUM_THR` varchar(8) DEFAULT NULL,
  `NUMBER04` varchar(8) DEFAULT NULL,
  `NUMBER05` varchar(8) DEFAULT NULL,
  `NUMBER06` varchar(8) DEFAULT NULL,
  `NUMBER07` varchar(8) DEFAULT NULL,
  `NUMBER08` varchar(8) DEFAULT NULL,
  `NUMBER09` varchar(8) DEFAULT NULL,
  `NUMBER10` varchar(8) DEFAULT NULL,
  `INBOUND_REFERENCE` varchar(64) DEFAULT NULL,
  `MANUFACTURER` varchar(64) DEFAULT NULL,
  `SHIPPER` varchar(64) DEFAULT NULL,
  `MAWB_MBL` varchar(64) DEFAULT NULL,
  `HAWB_HBL` varchar(64) DEFAULT NULL,
  `OF_PACKAGE` varchar(64) DEFAULT NULL,
  `DECLARATION` varchar(64) DEFAULT NULL,
  `CURRENCY` varchar(64) DEFAULT NULL,
  `FLIGHT_NO` varchar(64) DEFAULT NULL,
  `TRUCK_MANIFEST` varchar(64) DEFAULT NULL,
  `TRANSPORTMODE` varchar(64) DEFAULT NULL,
  `C1_TXT_TWELVE` varchar(64) DEFAULT NULL,
  `C1_TXT_THIRT` varchar(64) DEFAULT NULL,
  `C1_TXT_FOURT` varchar(64) DEFAULT NULL,
  `C1_TXT_FIFT` varchar(64) DEFAULT NULL,
  `CASHADVANCE` varchar(64) DEFAULT NULL,
  `PICKAREA` varchar(64) DEFAULT NULL,
  `C1_TXT_EIGHT` varchar(64) DEFAULT NULL,
  `OT` varchar(64) DEFAULT NULL,
  `INVOICENO` varchar(64) DEFAULT NULL,
  `TIME_ONE` varchar(32) DEFAULT NULL,
  `TIME_TWO` varchar(32) DEFAULT NULL,
  `TIME_THR` varchar(32) DEFAULT NULL,
  `TIME_FOU` varchar(32) DEFAULT NULL,
  `TIME_FIV` varchar(32) DEFAULT NULL,
  `TIME_SIX` varchar(32) DEFAULT NULL,
  `TIME_SEV` varchar(32) DEFAULT NULL,
  `TIME_EIG` varchar(32) DEFAULT NULL,
  `TIME_NIG` varchar(32) DEFAULT NULL,
  `TIME_TEN` varchar(32) DEFAULT NULL,
  `NUM_ONE` varchar(8) DEFAULT NULL,
  `NUM_TWO` varchar(8) DEFAULT NULL,
  `NUM_THR` varchar(8) DEFAULT NULL,
  `NUM_FOU` varchar(8) DEFAULT NULL,
  `NUM_FIV` varchar(8) DEFAULT NULL,
  `NUM_SIX` varchar(8) DEFAULT NULL,
  `NUM_SEV` varchar(8) DEFAULT NULL,
  `NUM_EIG` varchar(8) DEFAULT NULL,
  `NUM_NIG` varchar(8) DEFAULT NULL,
  `NUM_TEN` varchar(8) DEFAULT NULL,
  `TXT_ONE` varchar(64) DEFAULT NULL,
  `TXT_TWO` varchar(64) DEFAULT NULL,
  `TXT_THR` varchar(64) DEFAULT NULL,
  `TXT_FOU` varchar(64) DEFAULT NULL,
  `TXT_FIV` varchar(64) DEFAULT NULL,
  `TXT_SIX` varchar(64) DEFAULT NULL,
  `TXT_SEV` varchar(64) DEFAULT NULL,
  `TXT_EIG` varchar(64) DEFAULT NULL,
  `TXT_NIG` varchar(64) DEFAULT NULL,
  `TXT_TEN` varchar(64) DEFAULT NULL,
  `TXT_ELEVEN` varchar(64) DEFAULT NULL,
  `TXT_TWELVE` varchar(64) DEFAULT NULL,
  `TXT_THIRT` varchar(64) DEFAULT NULL,
  `TXT_FOURT` varchar(64) DEFAULT NULL,
  `TXT_FIFT` varchar(64) DEFAULT NULL,
  `TXT_SIXT` varchar(64) DEFAULT NULL,
  `TXT_SEVENT` varchar(64) DEFAULT NULL,
  `TXT_EIGHT` varchar(64) DEFAULT NULL,
  `TXT_NINET` varchar(64) DEFAULT NULL,
  `TXT_TWENT` varchar(64) DEFAULT NULL,
  `PUT_LOCATOR` varchar(255) DEFAULT NULL COMMENT '码放库位',
  `BOX_LOT_NO` varchar(256) DEFAULT NULL,
  `BOX_DATE_CODE` varchar(256) DEFAULT NULL,
  `BOX_SEPARATE_QTY` varchar(256) DEFAULT NULL,
  `BOX_COO` varchar(256) DEFAULT NULL,
  `BOX_BIN_CODE` varchar(256) DEFAULT NULL,
  `BOX_BIG_BOX_NO` varchar(255) DEFAULT NULL COMMENT '外箱号',
  `BOX_BOX_NO` varchar(255) DEFAULT NULL COMMENT '箱号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rp_instock` */

/*Table structure for table `rp_outstock` */

DROP TABLE IF EXISTS `rp_outstock`;

CREATE TABLE `rp_outstock` (
  `OUTSTOCK_NO` varchar(32) DEFAULT NULL COMMENT '发货单号',
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '客户',
  `PRE_OUT_DATE` varchar(32) DEFAULT NULL COMMENT '预计发货日期',
  `REAL_OUT_DATE` varchar(32) DEFAULT NULL COMMENT '实际发货日期',
  `ORDER_NO` varchar(32) DEFAULT NULL COMMENT '订单号',
  `PRODUCT_ID` varchar(32) DEFAULT NULL COMMENT '产品',
  `EA_EA` varchar(8) DEFAULT NULL COMMENT 'EA期望数',
  `ASSIGNED_EA` varchar(8) DEFAULT NULL COMMENT '分配EA数',
  `OUTSTOCK_TYPE` varchar(32) DEFAULT NULL COMMENT '出库类型',
  `OUTSTOCK_WAY` varchar(32) DEFAULT NULL COMMENT '发货方式',
  `PRIORITY_LEVEL` varchar(32) DEFAULT NULL COMMENT '优先级',
  `TOTAL_WEIGHT` varchar(8) DEFAULT NULL COMMENT '毛重',
  `TOTAL_SUTTLE` varchar(8) DEFAULT NULL COMMENT '净重',
  `TOTAL_VOLUME` varchar(8) DEFAULT NULL COMMENT '体积',
  `TOTAL_PRICE` varchar(8) DEFAULT NULL COMMENT '总价',
  `COST_STATE` varchar(32) DEFAULT NULL COMMENT '费用录入已完成',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `ASSIGNED_STATE` varchar(32) DEFAULT NULL COMMENT '分配状态',
  `PICK_STATE` varchar(32) DEFAULT NULL COMMENT '拣货状态',
  `CARGO_STATE` varchar(32) DEFAULT NULL COMMENT '配载状态',
  `DEPOT_STATE` varchar(32) DEFAULT NULL COMMENT '发货状态',
  `C1` varchar(32) DEFAULT NULL,
  `C2` varchar(32) DEFAULT NULL,
  `C3` varchar(32) DEFAULT NULL,
  `C4` varchar(32) DEFAULT NULL,
  `C5` varchar(32) DEFAULT NULL,
  `C6` varchar(32) DEFAULT NULL,
  `C7` varchar(32) DEFAULT NULL,
  `C8` varchar(32) DEFAULT NULL,
  `C9` varchar(32) DEFAULT NULL,
  `C10` varchar(32) DEFAULT NULL,
  `C11` varchar(32) DEFAULT NULL,
  `C12` varchar(32) DEFAULT NULL,
  `C13` varchar(32) DEFAULT NULL,
  `C14` varchar(32) DEFAULT NULL,
  `C15` varchar(32) DEFAULT NULL,
  `C16` varchar(32) DEFAULT NULL,
  `C17` varchar(32) DEFAULT NULL,
  `C18` varchar(32) DEFAULT NULL,
  `C19` varchar(32) DEFAULT NULL,
  `C20` varchar(32) DEFAULT NULL,
  `C21` varchar(128) DEFAULT NULL,
  `C22` varchar(128) DEFAULT NULL,
  `C23` varchar(128) DEFAULT NULL,
  `C24` varchar(128) DEFAULT NULL,
  `C25` varchar(128) DEFAULT NULL,
  `C26` varchar(128) DEFAULT NULL,
  `C27` varchar(128) DEFAULT NULL,
  `C28` varchar(128) DEFAULT NULL,
  `C29` varchar(128) DEFAULT NULL,
  `C30` varchar(128) DEFAULT NULL,
  `C31` varchar(128) DEFAULT NULL,
  `C32` varchar(128) DEFAULT NULL,
  `C33` varchar(128) DEFAULT NULL,
  `C34` varchar(128) DEFAULT NULL,
  `C35` varchar(128) DEFAULT NULL,
  `C36` varchar(128) DEFAULT NULL,
  `C37` varchar(128) DEFAULT NULL,
  `C38` varchar(128) DEFAULT NULL,
  `C39` varchar(128) DEFAULT NULL,
  `C40` varchar(128) DEFAULT NULL,
  `TIME_ONE` varchar(32) DEFAULT NULL,
  `TIME_TWO` varchar(32) DEFAULT NULL,
  `TIME_THR` varchar(32) DEFAULT NULL,
  `TIME_FOU` varchar(32) DEFAULT NULL,
  `TIME_FIV` varchar(32) DEFAULT NULL,
  `TIME_SIX` varchar(32) DEFAULT NULL,
  `TIME_SEV` varchar(32) DEFAULT NULL,
  `TIME_EIG` varchar(32) DEFAULT NULL,
  `TIME_NIG` varchar(32) DEFAULT NULL,
  `TIME_TEN` varchar(32) DEFAULT NULL,
  `NUM_ONE` varchar(32) DEFAULT NULL,
  `NUM_TWO` varchar(32) DEFAULT NULL,
  `NUM_THR` varchar(32) DEFAULT NULL,
  `NUM_FOU` varchar(32) DEFAULT NULL,
  `NUM_FIV` varchar(32) DEFAULT NULL,
  `NUM_SIX` varchar(32) DEFAULT NULL,
  `NUM_SEV` varchar(32) DEFAULT NULL,
  `NUM_EIG` varchar(32) DEFAULT NULL,
  `NUM_NIG` varchar(32) DEFAULT NULL,
  `NUM_TEN` varchar(32) DEFAULT NULL,
  `TXT_ONE` varchar(128) DEFAULT NULL,
  `TXT_TWO` varchar(128) DEFAULT NULL,
  `TXT_THR` varchar(128) DEFAULT NULL,
  `TXT_FOU` varchar(128) DEFAULT NULL,
  `TXT_FIV` varchar(128) DEFAULT NULL,
  `TXT_SIX` varchar(128) DEFAULT NULL,
  `TXT_SEV` varchar(128) DEFAULT NULL,
  `TXT_EIG` varchar(128) DEFAULT NULL,
  `TXT_NIG` varchar(128) DEFAULT NULL,
  `TXT_TEN` varchar(128) DEFAULT NULL,
  `TXT_ELEVEN` varchar(128) DEFAULT NULL,
  `TXT_TWELVE` varchar(128) DEFAULT NULL,
  `TXT_THIRT` varchar(128) DEFAULT NULL,
  `TXT_FOURT` varchar(128) DEFAULT NULL,
  `TXT_FIFT` varchar(128) DEFAULT NULL,
  `TXT_SIXT` varchar(128) DEFAULT NULL,
  `TXT_SEVENT` varchar(128) DEFAULT NULL,
  `TXT_EIGHT` varchar(128) DEFAULT NULL,
  `TXT_NINET` varchar(128) DEFAULT NULL,
  `TXT_TWENT` varchar(128) DEFAULT NULL,
  `PUT_LOCATOR` varchar(255) DEFAULT NULL COMMENT '码放库位',
  `BOX_LOT_NO` varchar(256) DEFAULT NULL,
  `BOX_DATE_CODE` varchar(256) DEFAULT NULL,
  `BOX_SEPARATE_QTY` varchar(256) DEFAULT NULL,
  `BOX_COO` varchar(256) DEFAULT NULL,
  `BOX_BIN_CODE` varchar(256) DEFAULT NULL,
  `BOX_BIG_BOX_NO` varchar(255) DEFAULT NULL COMMENT '外箱号',
  `BOX_BOX_NO` varchar(255) DEFAULT NULL COMMENT '箱号',
  `BOX_REMARK1` varchar(256) DEFAULT NULL,
  `BOX_REMARK2` varchar(256) DEFAULT NULL,
  `BOX_REMARK3` varchar(256) DEFAULT NULL,
  `RECEIV_QTY` varchar(32) DEFAULT NULL,
  `EA_NUM` varchar(255) DEFAULT NULL COMMENT 'EA数量',
  `ASSIGNED_STOCK_STATE` varchar(255) DEFAULT NULL COMMENT '分配库存状态',
  `ASSIGNED_STOCK_NUM` varchar(255) DEFAULT NULL COMMENT '分配库存数量',
  `FREEZE_STATE` varchar(255) DEFAULT NULL COMMENT '冻结状态',
  `SEND_EA` varchar(8) DEFAULT NULL COMMENT '发货EA数',
  `CARGO_EA` varchar(8) DEFAULT NULL COMMENT '配载EA',
  `PICK_EA` varchar(8) DEFAULT NULL COMMENT '拣货EA数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rp_outstock` */

/*Table structure for table `rp_stockdtl` */

DROP TABLE IF EXISTS `rp_stockdtl`;

CREATE TABLE `rp_stockdtl` (
  `INSTOCK_NO` varchar(32) DEFAULT NULL COMMENT '进货单编号入货单编号',
  `REAL_INSTOCK_DATE` varchar(32) DEFAULT NULL COMMENT '实际入库日期',
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '客户',
  `PRODUCT_ID` varchar(32) DEFAULT NULL COMMENT '产品',
  `BIG_BOX_NO` varchar(255) DEFAULT NULL COMMENT '外箱号',
  `BOX_NO` varchar(255) DEFAULT NULL COMMENT '箱号',
  `TIME_TWO` varchar(32) DEFAULT NULL,
  `DATE_CODE` varchar(256) DEFAULT NULL,
  `LOT_NO` varchar(256) DEFAULT NULL,
  `BIN_CODE` varchar(256) DEFAULT NULL,
  `COO` varchar(256) DEFAULT NULL,
  `REMARK1` varchar(64) DEFAULT NULL,
  `REMARK2` varchar(64) DEFAULT NULL,
  `TXT_SIX` varchar(64) DEFAULT NULL,
  `INVNO` varchar(64) DEFAULT NULL,
  `PUT_LOCATOR` varchar(255) DEFAULT NULL COMMENT '码放库位',
  `RECEIV_QTY` varchar(32) DEFAULT NULL,
  `IN_EA` varchar(1) NOT NULL DEFAULT '',
  `EA_ACTUAL` varchar(32) DEFAULT NULL COMMENT '实际收货数',
  `FREEZE_EA` varchar(1) NOT NULL DEFAULT '',
  `USE_EA` varchar(1) NOT NULL DEFAULT '',
  `ASSIGNED_STATE` varchar(255) DEFAULT NULL COMMENT '分配状态',
  `PUT_STATUS` varchar(255) DEFAULT NULL COMMENT '码放状态',
  `FREEZE_STATE` varchar(255) DEFAULT NULL COMMENT '冻结状态',
  `ASSIGNED_STOCK_STATE` varchar(255) DEFAULT NULL COMMENT '分配库存状态',
  `ORDER_NO` varchar(32) DEFAULT NULL COMMENT '订单号',
  `INB_REF_A` varchar(64) DEFAULT NULL,
  `INVNO_A` varchar(64) DEFAULT NULL,
  `SCAN_CODE` varchar(256) DEFAULT NULL COMMENT '供应商料号',
  `SEPARATE_QTY` varchar(256) DEFAULT NULL,
  `TXT_THR` varchar(64) DEFAULT NULL,
  `TXT_SEV` varchar(64) DEFAULT NULL,
  `TXT_EIG` varchar(64) DEFAULT NULL,
  `TXT_NIG` varchar(64) DEFAULT NULL,
  `TXT_ELEVEN` varchar(64) DEFAULT NULL,
  `TXT_TWELVE` varchar(64) DEFAULT NULL,
  `TXT_FOURT` varchar(64) DEFAULT NULL,
  `TXT_SEVENT` varchar(64) DEFAULT NULL,
  `TXT_TWENT` varchar(64) DEFAULT NULL,
  `MANUFACTUER` varchar(64) DEFAULT NULL,
  `SHIPPER` varchar(64) DEFAULT NULL,
  `EA_NUM` varchar(255) DEFAULT NULL COMMENT 'EA数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rp_stockdtl` */

/*Table structure for table `rp_stocknow` */

DROP TABLE IF EXISTS `rp_stocknow`;

CREATE TABLE `rp_stocknow` (
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '客户',
  `PRODUCT_ID` varchar(32) DEFAULT NULL COMMENT '产品',
  `TOTAL_EA` varchar(8) DEFAULT NULL COMMENT '库存EA',
  `ASSIGNED_EA` varchar(8) DEFAULT NULL COMMENT '分配EA',
  `FREEZE_EA` varchar(8) DEFAULT NULL COMMENT '冻结EA',
  `USE_EA` varchar(8) DEFAULT NULL COMMENT '可用EA',
  `CREATE_EMP` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `COMB_ID` varchar(256) DEFAULT NULL,
  `BOXS` varchar(4000) DEFAULT NULL COMMENT '箱号',
  `LOCATORS` varchar(4000) DEFAULT NULL COMMENT '库位',
  FULLTEXT KEY `NewIndex1` (`CUSTOMER_ID`),
  FULLTEXT KEY `NewIndex2` (`PRODUCT_ID`,`BOXS`,`LOCATORS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `rp_stocknow` */

/*Table structure for table `tb_city` */

DROP TABLE IF EXISTS `tb_city`;

CREATE TABLE `tb_city` (
  `CITY_ID` varchar(100) NOT NULL,
  `PROVINCE_ID` varchar(255) DEFAULT NULL COMMENT '省份名称',
  `CITY_CODE` varchar(255) DEFAULT NULL COMMENT '城市代码',
  `CITY_PARENT_ID` varchar(255) DEFAULT NULL COMMENT '上级城市代码',
  `CITY_CN` varchar(255) DEFAULT NULL COMMENT '城市名称',
  `OFFER_FLG` int(11) NOT NULL COMMENT '是否报价线路',
  `CITY_EN` varchar(255) DEFAULT NULL COMMENT '城市英文名称',
  `CITY_AREA` varchar(255) DEFAULT NULL COMMENT '城市区域',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除标志',
  PRIMARY KEY (`CITY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_city` */

insert  into `tb_city`(`CITY_ID`,`PROVINCE_ID`,`CITY_CODE`,`CITY_PARENT_ID`,`CITY_CN`,`OFFER_FLG`,`CITY_EN`,`CITY_AREA`,`MEMO`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('07c533e5322d4b8fa1eaca70ae72fb6b','20','GZ010','','广州市',1,'GUANGZHOU','','','admin','2016-11-29 13:18:32','admin','2016-11-29 13:18:32',0),('55d6916bc90e4f8887e02bb1932eefc0','20','SZ0755','','深圳',1,'SHENZHEN','','','admin','2016-11-29 13:17:59','admin','2016-11-29 13:17:59',0),('93d0ca18493d456d827cce8dacfdc177','60516b9f8cba4c0dba0a02004d9d1bc1','JL','','九龙',1,'QUANWANG','','','admin','2016-11-29 13:19:49','admin','2016-11-29 13:19:49',0),('eebd4ab2905d4564a060786eb645fd17','60516b9f8cba4c0dba0a02004d9d1bc1','QW','','荃湾',1,'JIULONG','','','admin','2016-11-29 13:19:30','admin','2016-11-29 13:19:30',0);

/*Table structure for table `tb_country` */

DROP TABLE IF EXISTS `tb_country`;

CREATE TABLE `tb_country` (
  `COUNTRY_ID` varchar(100) NOT NULL,
  `COUNTRY_CODE` varchar(255) DEFAULT NULL COMMENT '国家代码',
  `COUNTRY_CN` varchar(255) DEFAULT NULL COMMENT '国家名称',
  `COUNTRY_EN` varchar(255) DEFAULT NULL COMMENT '国家英文名称',
  `CUSTOM_CODE` varchar(255) DEFAULT NULL COMMENT '海关代码',
  `COUNTRY_PRE` varchar(255) DEFAULT NULL COMMENT '国家电话前缀',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除标志',
  `sort_by` int(11) DEFAULT '2' COMMENT '排序顺序',
  PRIMARY KEY (`COUNTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_country` */

insert  into `tb_country`(`COUNTRY_ID`,`COUNTRY_CODE`,`COUNTRY_CN`,`COUNTRY_EN`,`CUSTOM_CODE`,`COUNTRY_PRE`,`MEMO`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`sort_by`) values ('1','CHINA','中国','CHINA','086','086','','admin','2016-11-01 20:58:07','admin','2016-11-01 20:58:07',0,2),('50a84679187f46dd93e18a8bb5903848','HONGKONG','香港','HONGKONG','096','096','','admin','2016-11-01 20:59:06','admin','2016-11-01 20:59:06',0,2);

/*Table structure for table `tb_customer` */

DROP TABLE IF EXISTS `tb_customer`;

CREATE TABLE `tb_customer` (
  `CUSTOMER_ID` varchar(100) NOT NULL,
  `CUSTOMER_CODE` varchar(255) DEFAULT NULL COMMENT '客户编码',
  `CUSTOMER_CN` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `CUSTOMER_SHORT` varchar(255) DEFAULT NULL COMMENT '客户简称',
  `CUSTOMER_EN` varchar(255) DEFAULT NULL COMMENT '英文名称',
  `SEACH_CODE` varchar(255) DEFAULT NULL COMMENT '检索码',
  `UP_COMPANY` varchar(255) DEFAULT NULL COMMENT '上级企业',
  `COUNTRY_ID` varchar(255) DEFAULT NULL COMMENT '国家',
  `PROVINCE_ID` varchar(255) DEFAULT NULL COMMENT '省/州',
  `CITY_ID` varchar(255) DEFAULT NULL COMMENT '城市',
  `CUSTOMER_ADDR` varchar(255) DEFAULT NULL COMMENT '地址',
  `INTEL_ADDR` varchar(255) DEFAULT NULL COMMENT '网站地址',
  `CUSTOMS_CODE` varchar(255) DEFAULT NULL COMMENT '海关编号',
  `OPERATING_UNIT` varchar(255) DEFAULT NULL COMMENT '经营单位',
  `PAY_PERIOD` int(11) NOT NULL COMMENT '付款周期',
  `TEL_PHONE` varchar(255) DEFAULT NULL COMMENT '电话',
  `SERVIC_ER` varchar(255) DEFAULT NULL COMMENT '客服人员名称',
  `SALE_MAN` varchar(255) DEFAULT NULL COMMENT '业务员',
  `CUSTOMER_FINANCE` varchar(255) DEFAULT NULL COMMENT '客户财务编号',
  `SUPPLIER_FINANCE` varchar(255) DEFAULT NULL COMMENT '供应商财务编码',
  `PAY_MERCHANT` varchar(255) DEFAULT NULL COMMENT '付款客户',
  `CUSTOMER_TYPE` varchar(255) DEFAULT NULL COMMENT '企业类型',
  `TYPE_SUPPLIER` int(11) NOT NULL COMMENT '供应商',
  `TYPE_FACTOR` int(11) NOT NULL COMMENT '厂商',
  `TYPE_FOB` int(11) NOT NULL COMMENT '货代',
  `TYPE_SEND` int(11) NOT NULL COMMENT '发货人',
  `TYPE_TAKE` int(11) NOT NULL COMMENT '收货人',
  `TYPE_CAR` int(11) NOT NULL COMMENT '车队',
  `TYPE_CUSTOM` int(11) NOT NULL COMMENT '报关行',
  `BELONE_COMPANY` varchar(255) DEFAULT NULL COMMENT '所属公司',
  `USE_FLG` int(11) NOT NULL COMMENT '启用',
  `CONTACTS_CN` varchar(255) DEFAULT NULL COMMENT '名称',
  `CONTACTS_EN` varchar(255) DEFAULT NULL COMMENT '英文名',
  `CONTACTS_CITY_ID` varchar(255) DEFAULT NULL COMMENT '城市',
  `CONTACTS_ADDR` varchar(255) DEFAULT NULL COMMENT '地址',
  `CONTACTS_DEPT` varchar(255) DEFAULT NULL COMMENT '部门',
  `CONTACTS_POST` varchar(255) DEFAULT NULL COMMENT '职务',
  `CONTACTS_PHONE` varchar(255) DEFAULT NULL COMMENT '电话',
  `CONTACTS_FAX` varchar(255) DEFAULT NULL COMMENT '传真',
  `CONTACTS_EMAIL` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `CONTACTS_BIRTH` varchar(255) DEFAULT NULL COMMENT '生日',
  `CONTACTS_MEMORI` varchar(255) DEFAULT NULL COMMENT '纪念日',
  `RULE_PACK` varchar(255) DEFAULT NULL COMMENT '包装规则',
  `RULE_PUT` varchar(255) DEFAULT NULL COMMENT '码放规则',
  `RULE_PICK` varchar(255) DEFAULT NULL COMMENT '拣货规则',
  `RULE_PICK_ASIGN` varchar(255) DEFAULT NULL COMMENT '拣货分配规则',
  `RULE_DEFALUT_ASIGN` varchar(255) DEFAULT NULL COMMENT '预分配规则',
  `RULE_BATCH` varchar(255) DEFAULT NULL COMMENT '批次属性规则',
  `RULE_STOCK` varchar(255) DEFAULT NULL COMMENT '库存周转规则',
  `RULE_STORAGE` varchar(255) DEFAULT NULL COMMENT '库位指定规则',
  `RULE_BULK` varchar(255) DEFAULT NULL COMMENT '体积重量计算方式',
  `SUPR_TAKE` int(11) NOT NULL COMMENT '超量收货',
  `SUPR_SEND` int(11) NOT NULL COMMENT '超量发货',
  `CONTRACTUAL_FILE` varchar(255) DEFAULT NULL COMMENT '文件名',
  `CONTRACTUAL_ACTIVE` varchar(255) DEFAULT NULL COMMENT '生效日期',
  `CONTRACTUAL_DISABLE` varchar(255) DEFAULT NULL COMMENT '失效日期',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除',
  `RULE_STORAGEASIGNR` varchar(128) DEFAULT NULL COMMENT '库位分配规则',
  `RULE_STOCKASIGN` varchar(128) DEFAULT NULL COMMENT '库存分配规则',
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_customer` */

insert  into `tb_customer`(`CUSTOMER_ID`,`CUSTOMER_CODE`,`CUSTOMER_CN`,`CUSTOMER_SHORT`,`CUSTOMER_EN`,`SEACH_CODE`,`UP_COMPANY`,`COUNTRY_ID`,`PROVINCE_ID`,`CITY_ID`,`CUSTOMER_ADDR`,`INTEL_ADDR`,`CUSTOMS_CODE`,`OPERATING_UNIT`,`PAY_PERIOD`,`TEL_PHONE`,`SERVIC_ER`,`SALE_MAN`,`CUSTOMER_FINANCE`,`SUPPLIER_FINANCE`,`PAY_MERCHANT`,`CUSTOMER_TYPE`,`TYPE_SUPPLIER`,`TYPE_FACTOR`,`TYPE_FOB`,`TYPE_SEND`,`TYPE_TAKE`,`TYPE_CAR`,`TYPE_CUSTOM`,`BELONE_COMPANY`,`USE_FLG`,`CONTACTS_CN`,`CONTACTS_EN`,`CONTACTS_CITY_ID`,`CONTACTS_ADDR`,`CONTACTS_DEPT`,`CONTACTS_POST`,`CONTACTS_PHONE`,`CONTACTS_FAX`,`CONTACTS_EMAIL`,`CONTACTS_BIRTH`,`CONTACTS_MEMORI`,`RULE_PACK`,`RULE_PUT`,`RULE_PICK`,`RULE_PICK_ASIGN`,`RULE_DEFALUT_ASIGN`,`RULE_BATCH`,`RULE_STOCK`,`RULE_STORAGE`,`RULE_BULK`,`SUPR_TAKE`,`SUPR_SEND`,`CONTRACTUAL_FILE`,`CONTRACTUAL_ACTIVE`,`CONTRACTUAL_DISABLE`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`RULE_STORAGEASIGNR`,`RULE_STOCKASIGN`) values ('a269cbcdf38341eab4dea64e2717f346','SPREA','展讯科技有限公司','SPREA','SPREA','','','1','20','55d6916bc90e4f8887e02bb1932eefc0','中国广东省深圳','','','',0,'','','','','','',NULL,0,0,0,0,0,0,0,'',1,'','','','','','','','','','','','',NULL,'',NULL,NULL,'',NULL,'','',0,0,NULL,NULL,NULL,'admin','2016-11-30 20:22:22','admin','2016-11-30 20:22:22',0,'',''),('aedef24f2cd14d0888e1fed85d271ce7','REOTEC','瑞普有限公司','REOTEC','REOTEC','','','1','20','55d6916bc90e4f8887e02bb1932eefc0','中国广东省深圳','','','',0,'','','','','','',NULL,0,0,0,0,0,0,0,'',1,'','','','','','','','','','','','',NULL,'',NULL,NULL,'',NULL,'','',0,0,NULL,NULL,NULL,'admin','2016-11-30 20:19:33','admin','2016-11-30 20:19:33',0,'','');

/*Table structure for table `tb_customer_2d` */

DROP TABLE IF EXISTS `tb_customer_2d`;

CREATE TABLE `tb_customer_2d` (
  `CUSTOMER_ID` varchar(255) NOT NULL COMMENT '客户',
  `RECEIVOPT_2D` varchar(255) DEFAULT NULL COMMENT '收货二维码',
  `PN` varchar(255) DEFAULT NULL COMMENT 'PN',
  `DATE_CODE` varchar(255) DEFAULT NULL COMMENT 'DATECODE',
  `LOT_CODE` varchar(255) DEFAULT NULL COMMENT 'LOT_CODE',
  ` BIN` varchar(255) DEFAULT NULL COMMENT ' BIN',
  `COO` varchar(255) DEFAULT NULL COMMENT 'COO',
  `QTY` varchar(255) DEFAULT NULL COMMENT 'PACKAGE QTY',
  `REMARK1` varchar(255) DEFAULT NULL COMMENT 'REMARK1',
  `REMARK2` varchar(255) DEFAULT NULL COMMENT 'REMARK2',
  `REMARK3` varchar(255) DEFAULT NULL COMMENT 'REMARK3',
  `INV_NO` varchar(255) DEFAULT NULL COMMENT 'INV_NO',
  `SEPERATOR` varchar(255) DEFAULT NULL COMMENT 'SEPERATOR',
  PRIMARY KEY (`CUSTOMER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_customer_2d` */

/*Table structure for table `tb_customer_deal` */

DROP TABLE IF EXISTS `tb_customer_deal`;

CREATE TABLE `tb_customer_deal` (
  `DEAL_ID` varchar(128) DEFAULT NULL,
  `CUSTOMER_ID` varchar(128) DEFAULT NULL,
  `DEAL_NAME` varchar(4000) DEFAULT NULL COMMENT '合约名称',
  `DEAL_PATH` varchar(4000) DEFAULT NULL COMMENT '合约存放路径',
  `ACTIVE_DT` varchar(128) DEFAULT NULL COMMENT '生效日期',
  `DISABLE_DT` varchar(128) DEFAULT NULL COMMENT '失效日期',
  `CREATE_EMP` varchar(256) DEFAULT NULL,
  `CREATE_TM` varchar(128) DEFAULT NULL,
  `MODIFY_EMP` varchar(256) DEFAULT NULL,
  `MODIFY_TM` varchar(128) DEFAULT NULL,
  `DEL_FLG` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_customer_deal` */

insert  into `tb_customer_deal`(`DEAL_ID`,`CUSTOMER_ID`,`DEAL_NAME`,`DEAL_PATH`,`ACTIVE_DT`,`DISABLE_DT`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('830a6bc1fa62482f9d092b929effab36','77190aa356d249248f42605f58cbff68','039f2250ff6347c1b27ec72672005e8e.xlsx','D:/workspace/idea/ops/build/classes/artifacts/ops/exploded/ops-1.0-SNAPSHOT.war/WEB-INF/classes/../../uploadFiles/deal/20161108','2016-11-08','2016-11-23','admin','2016-11-08 10:46:53','admin','2016-11-08 10:46:53','1'),('d6969fce8d374f549d419f1752cd636a','77190aa356d249248f42605f58cbff68','57a29f05578849089365def3606b11b9.xlsx','D:/workspace/idea/ops/build/classes/artifacts/ops/exploded/ops-1.0-SNAPSHOT.war/WEB-INF/classes/../../uploadFiles/deal/','2016-11-08','2016-11-15','admin','2016-11-08 13:50:09','admin','2016-11-08 13:50:09','1'),('30edbba585664563adf686a55e79aed9','77190aa356d249248f42605f58cbff68','Users.xls.xls','D:/workspace/idea/ops/build/classes/artifacts/ops/exploded/ops-1.0-SNAPSHOT.war/WEB-INF/classes/../../uploadFiles/deal/','2016-11-07','2016-11-29','admin','2016-11-08 13:51:27','admin','2016-11-08 13:51:27','1'),('3a2a29ba34234da99e0f340ee7b3d699','77190aa356d249248f42605f58cbff68','Partnum.xls','D:/workspace/idea/ops/build/classes/artifacts/ops/exploded/ops-1.0-SNAPSHOT.war/WEB-INF/classes/../../uploadFiles/deal/','2016-11-22','2016-12-07','admin','2016-11-08 13:55:37','admin','2016-11-08 13:55:37','1'),('716f8b107e8a4de6a22548321cc83e7c','77190aa356d249248f42605f58cbff68','测试报告-1102.xlsx','D:/workspace/idea/ops/build/classes/artifacts/ops/exploded/ops-1.0-SNAPSHOT.war/WEB-INF/classes/../../uploadFiles/deal/','2016-11-02','2016-11-10','admin','2016-11-08 14:16:18','admin','2016-11-08 14:16:18','0'),('05cdb87f276d43c0acca5746ec145da6','77190aa356d249248f42605f58cbff68','report.pdf','D:/workspace/idea/ops/build/classes/artifacts/ops/exploded/ops-1.0-SNAPSHOT.war/WEB-INF/classes/../../uploadFiles/deal/','2016-11-02','2016-11-09','admin','2016-11-08 14:18:36','admin','2016-11-08 14:18:36','0');

/*Table structure for table `tb_customer_deal_copy` */

DROP TABLE IF EXISTS `tb_customer_deal_copy`;

CREATE TABLE `tb_customer_deal_copy` (
  `DEAL_ID` varchar(128) DEFAULT NULL,
  `DEAL_NAME` varchar(4000) DEFAULT NULL COMMENT '合约名称',
  `DEAL_PATH` varchar(4000) DEFAULT NULL COMMENT '合约存放路径',
  `ACTIVE_DT` varchar(128) DEFAULT NULL COMMENT '生效日期',
  `DISABLE_DT` varchar(128) DEFAULT NULL COMMENT '失效日期',
  `CREATE_EMP` varchar(256) DEFAULT NULL,
  `CREATE_TM` varchar(128) DEFAULT NULL,
  `MODIFY_EMP` varchar(256) DEFAULT NULL,
  `MODIFY_TM` varchar(128) DEFAULT NULL,
  `DEL_FLG` varchar(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tb_customer_deal_copy` */

/*Table structure for table `tb_customer_seq` */

DROP TABLE IF EXISTS `tb_customer_seq`;

CREATE TABLE `tb_customer_seq` (
  `SEQ_ID` varchar(64) DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `SEQ_TYPE` varchar(32) DEFAULT NULL,
  `cur_year` varchar(32) DEFAULT NULL,
  `cur_seq` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_customer_seq` */

insert  into `tb_customer_seq`(`SEQ_ID`,`customer_id`,`SEQ_TYPE`,`cur_year`,`cur_seq`) values ('1','2e9af32be19849babcf34efacef04d0d','-R-','16',0),('2','2e9af32be19849babcf34efacef04d0d','-S-','16',0),('3','77190aa356d249248f42605f58cbff68','-R-','16',0),('4','77190aa356d249248f42605f58cbff68','-S-','16',0),(NULL,'aedef24f2cd14d0888e1fed85d271ce7','-S-','16',5),(NULL,'aedef24f2cd14d0888e1fed85d271ce7','-R-','16',3),(NULL,'a269cbcdf38341eab4dea64e2717f346','-S-','16',1),(NULL,'a269cbcdf38341eab4dea64e2717f346','-R-','16',2),(NULL,'a825a60c70f2479f975604d8ad69eefe','-S-','16',0),(NULL,'a825a60c70f2479f975604d8ad69eefe','-R-','16',0);

/*Table structure for table `tb_datamgr` */

DROP TABLE IF EXISTS `tb_datamgr`;

CREATE TABLE `tb_datamgr` (
  `DATAMGR_ID` varchar(100) NOT NULL,
  `FUNCTION_NAME` varchar(255) DEFAULT NULL COMMENT '功能名称',
  `TABLE_CN` varchar(255) DEFAULT NULL COMMENT '表名',
  `TABLE_DESC` varchar(255) DEFAULT NULL COMMENT '描述',
  `TOTAL_CNT` varchar(255) DEFAULT NULL COMMENT '总记录',
  `VALID_CNT` varchar(255) DEFAULT NULL COMMENT '有效纪录',
  `NOVALID_CNT` varchar(255) DEFAULT NULL COMMENT '无效纪录',
  PRIMARY KEY (`DATAMGR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_datamgr` */

insert  into `tb_datamgr`(`DATAMGR_ID`,`FUNCTION_NAME`,`TABLE_CN`,`TABLE_DESC`,`TOTAL_CNT`,`VALID_CNT`,`NOVALID_CNT`) values ('05a8eb1487ae49dc938ca644476bef8d','收货资料','TM_STOCKMGRIN','','0','0','0'),('1','客户管理','TB_CUSTOMER',' 客户表','2','2','0'),('10','入库单订单/批次属性','TM_STOCKMGRIN_PROPERTY',NULL,'0','0','0'),('11','入库单明细','TM_STOCKMGRIN_DTL',NULL,'0','0','0'),('12','库区','TB_STORAGE',NULL,'4','4','0'),('13','库位分配规则','TB_STORAGEASIGNRULE',NULL,'2','2','0'),('2','库位','TB_LOCATOR',NULL,'2','2','0'),('2c98bd98900243adbd195735d3b96bd9','收货资料','tm_stockmgrin_property','','0','0','0'),('3','订单/批次属性','TB_ORDERPROPERTY',NULL,'200','200','0'),('4','包装规则','TB_PACKRULE',NULL,'6','6','0'),('5','拣货规则','TB_PICKRULE',NULL,'3','3','0'),('6','产品','TB_PRODUCT','产品定义','3','3','0'),('7','仓库','TB_STOCK',NULL,'2','2','0'),('8','库存分配规则','TB_STOCKASIGNRULE',NULL,'2','2','0'),('9','入库单','TB_STOCKMGRIN',NULL,'0','0','0'),('b7c49da0947946a7ad51cc789b4d5497','收货资料','TM_STOCKMGRIN_DTL','','0','0','0'),('d8a46dfee1e9486fb9fde88feb3f3923','收货资料','tm_receivopt','','0','0','0'),('f8d7901845a74049bcfabf037c8270c4','收货资料','tm_box','','0','0','0');

/*Table structure for table `tb_locator` */

DROP TABLE IF EXISTS `tb_locator`;

CREATE TABLE `tb_locator` (
  `LOCATOR_ID` varchar(100) NOT NULL,
  `LOCATOR_CODE` varchar(255) DEFAULT NULL COMMENT '库位编码',
  `STORAGE_CODE` varchar(255) DEFAULT NULL COMMENT '库区编码',
  `ROW_UNIT` varchar(255) DEFAULT NULL COMMENT '排',
  `FLOOR_UNIT` varchar(255) DEFAULT NULL COMMENT '层',
  `QUEUE_UNIT` varchar(255) DEFAULT NULL COMMENT '列',
  `LOCATOR_STATE` varchar(128) NOT NULL COMMENT '库位属性',
  `LOCATOR_USE` varchar(128) NOT NULL COMMENT '库位使用',
  `LOCATOR_TYPE` varchar(128) NOT NULL COMMENT '库位类型',
  `LOCATOR_UNIT` varchar(128) NOT NULL COMMENT '库位处理',
  `TURNOVER_CYCLE` varchar(128) NOT NULL COMMENT '周转周期',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `VOLUME_LIMIT` varchar(255) DEFAULT NULL COMMENT '体积限制',
  `WEIGHT_LIMIT` varchar(255) DEFAULT NULL COMMENT '重量限制',
  `QUANTITY_LIMIT` varchar(255) DEFAULT NULL COMMENT '数量限制',
  `PALLET_LIMIT` varchar(255) DEFAULT NULL COMMENT '托盘限制',
  `LONG_UNIT` varchar(255) DEFAULT NULL COMMENT '长',
  `WIDE_UNIT` varchar(255) DEFAULT NULL COMMENT '宽',
  `HIGH_UNIT` varchar(255) DEFAULT NULL COMMENT '高',
  `PLIES_UNIT` int(11) NOT NULL DEFAULT '0' COMMENT '层数',
  `PRODUCT_MIX` int(11) NOT NULL DEFAULT '0' COMMENT '产品混合',
  `BATCH_MIX` int(11) DEFAULT '0' COMMENT '批号混合',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL DEFAULT '0' COMMENT '删除',
  `LOCATOR_PRIORITY_LEVEL` varchar(128) DEFAULT NULL COMMENT '库位优先级',
  PRIMARY KEY (`LOCATOR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_locator` */

insert  into `tb_locator`(`LOCATOR_ID`,`LOCATOR_CODE`,`STORAGE_CODE`,`ROW_UNIT`,`FLOOR_UNIT`,`QUEUE_UNIT`,`LOCATOR_STATE`,`LOCATOR_USE`,`LOCATOR_TYPE`,`LOCATOR_UNIT`,`TURNOVER_CYCLE`,`MEMO`,`VOLUME_LIMIT`,`WEIGHT_LIMIT`,`QUANTITY_LIMIT`,`PALLET_LIMIT`,`LONG_UNIT`,`WIDE_UNIT`,`HIGH_UNIT`,`PLIES_UNIT`,`PRODUCT_MIX`,`BATCH_MIX`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`LOCATOR_PRIORITY_LEVEL`) values ('47c5f6ef973c4c58989072a6f4bac1e7','QW003','ade3b6c3e1e441098a4ed7734e822f86',NULL,NULL,NULL,'1','10','11','15','18','','','','','',NULL,NULL,NULL,0,0,0,'admin','2016-11-30 20:18:26','admin','2016-11-30 20:18:26',0,'97'),('4ac956ae67454bc9ba0e284ca04a167c','QW002','ade3b6c3e1e441098a4ed7734e822f86',NULL,NULL,NULL,'1','10','11','15','18','','','','','',NULL,NULL,NULL,0,0,0,'admin','2016-11-30 20:18:15','admin','2016-11-30 20:18:15',0,'97'),('624e14234d8b4936a63c3b68cd803aeb','QW001','2b4576eb61f9476ba466abe939396031',NULL,NULL,NULL,'1','10','11','15','18','','','','','',NULL,NULL,NULL,0,0,0,'admin','2016-11-30 20:18:01','admin','2016-11-30 20:18:01',0,'97'),('d12c8d3c0ef048ca8467f576e59a7331','QW004','6e7103c495e149998184630ea437aa26',NULL,NULL,NULL,'1','10','11','15','18','','','','','',NULL,NULL,NULL,0,0,0,'admin','2016-11-30 20:18:38','admin','2016-11-30 20:18:38',0,'97'),('qsfp','缺省分配',NULL,NULL,NULL,NULL,'','','','','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,0,0,NULL,NULL,NULL,NULL,2,NULL);

/*Table structure for table `tb_orderproperty` */

DROP TABLE IF EXISTS `tb_orderproperty`;

CREATE TABLE `tb_orderproperty` (
  `ORDERPROPERTY_ID` varchar(100) NOT NULL,
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `ORDERBATCH_TYPE` varchar(255) DEFAULT NULL COMMENT 'MEMO',
  `PROPERTY_KEY` varchar(255) DEFAULT NULL COMMENT '属性代码',
  `PROPERTY_USE` varchar(255) DEFAULT NULL COMMENT '属性启用',
  `PROPERTY_SORT` varchar(255) DEFAULT NULL COMMENT '属性排序',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除',
  `SRC_SORT` int(11) DEFAULT NULL,
  PRIMARY KEY (`ORDERPROPERTY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_orderproperty` */

insert  into `tb_orderproperty`(`ORDERPROPERTY_ID`,`CUSTOMER_ID`,`ORDERBATCH_TYPE`,`PROPERTY_KEY`,`PROPERTY_USE`,`PROPERTY_SORT`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`SRC_SORT`) values ('00c70b0892b644a98c1ee35ec00bea33','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,19),('03169fb58cc548ad920526009e290511','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,32),('042e497b62c2461eba69772dbcc611ab','aedef24f2cd14d0888e1fed85d271ce7','63','Remark 3','1','7','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,26),('05a1e03897c444bf87849cb6cc77e2d9','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,18),('060bc7d59bbf474389315d57239a852c','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,37),('06c378a2c30d480b916ace2b4ea87374','a269cbcdf38341eab4dea64e2717f346','60','CASH ADVANCE','1','8','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,26),('0702de4c0254427eb00d6d7ad0ca2563','aedef24f2cd14d0888e1fed85d271ce7','62','Reel ID','1','11','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,29),('07048f1f5ee74aae9665eb6bd7ef8ca3','aedef24f2cd14d0888e1fed85d271ce7','61','SI Reference','1','13','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,30),('0736804d658240478a09c6f7d298fe41','a269cbcdf38341eab4dea64e2717f346','62','goodsbase','1','3','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,22),('0751ce2e364c44dfa51740a743486cb0','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,16),('0754b89ed63244b5b672f386836b6fc3','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,18),('07964463edbd4338b968c483e70c7fa6','a269cbcdf38341eab4dea64e2717f346','63','HOLD','1','9','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,28),('08d08e228b7e45caaade4c6730c57b82','a269cbcdf38341eab4dea64e2717f346','61','包装单位','1','15','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,30),('0a8bd3b6e99b4d628dbfe3acd1e7b37c','a269cbcdf38341eab4dea64e2717f346','60','CHK进货来源','1','22','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,37),('0ae69f918e1445d9b6d8cf685906c6ba','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,20),('0b104853293e4283b910b83166b77ccb','aedef24f2cd14d0888e1fed85d271ce7','63','Separate QTY','1','17','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,11),('0bbe9c42c1024212b8264aff07ad2224','aedef24f2cd14d0888e1fed85d271ce7','61','MAWB/MBL','1','2','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,22),('0c0766f129414f65a8ad4b504f6ddf17','aedef24f2cd14d0888e1fed85d271ce7','61','Currency','1','8','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,25),('0cb9e933f85942188a7d46ca7c21160f','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,8),('0d116101ee024a3781a9ec2ce514115c','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,19),('0dab55a990ba450882153a4adbd75f1c','a269cbcdf38341eab4dea64e2717f346','62','CaseNumber','1','2','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,21),('0f056ce5a32b454eb4ce9e3dc6d0fca7','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,38),('0f292ac15bb449b9bdf4b9eb71f6b5a6','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,9),('0f5aba9a5ae04f6c91144ed1bc92451d','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,5),('0f91cddc04c5403a801367385f1f3ab9','aedef24f2cd14d0888e1fed85d271ce7','62','DATE CODE 转化','1','10','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,2),('0fd1779fb77a4b92a154c4c329bac353','a269cbcdf38341eab4dea64e2717f346','61','Delivery Area','1','10','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,27),('10eb66ac261741148c9963b5159d2835','aedef24f2cd14d0888e1fed85d271ce7','62','Scan  Code','1','15','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,33),('137c2bf212d7462187607f3f991788ab','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,40),('14255c5d24b845f988c2cd32aaedefca','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,6),('14c86b770f1749ba9a829a50b08550e6','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,20),('162097bdebef49db89279a31990bb43c','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,14),('17512ce5b82b4d68a573c6f51306b139','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,40),('175d692e91364d5196c671c7e577b1be','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,12),('17d283dddf5a43e5b5adf7c9e599752c','aedef24f2cd14d0888e1fed85d271ce7','62','Customer Inv #','1','3','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,22),('18d5b5d8a79d4f7cbf140808b1aaa149','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,7),('18de3f19e5ac4d1cb84b3bf838efe236','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,10),('198cc93a71684baca765e13feef16d56','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,10),('1993269f25694801b7a526b5ac167cff','aedef24f2cd14d0888e1fed85d271ce7','63','入仓时间','1','20','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,3),('19fc23d7f6e34a689eb8392801f468b0','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,35),('1ac716584158421ab0ebb15408dafb3f','a269cbcdf38341eab4dea64e2717f346','63','COO','1','11','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,29),('1bad81a06d8b4e2382436ad387cf0b3b','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,36),('1d7bf6273de541cc8a20a92b14837456','aedef24f2cd14d0888e1fed85d271ce7','62','Remark 1','1','5','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,24),('1e0b05a006b5473abf48edcca871f521','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,16),('1e34f71e46cd4d8385f025901cb9572f','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,15),('1f6ce2c4884f4bdcb3a903bd10022e1f','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,40),('208d43cfc3cc4274a86f024004e021d8','aedef24f2cd14d0888e1fed85d271ce7','63','打印分类','1','4','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,23),('22ac55f7794248fdaa003c51f21a80c9','a269cbcdf38341eab4dea64e2717f346','63','特别标注（1）','1','7','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,26),('22c05b84c8d24a93beb2577ca1e0ed04','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,5),('22db75f102774d698a7b2a88d94ca06f','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,18),('240e94b70a1d419ea86920c101be5d5b','a269cbcdf38341eab4dea64e2717f346','61','收货人','1','3','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,23),('243482c4f7e148a0969649e1c99d3d37','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,18),('245d8002352f45ea82fc60c1d84986e8','aedef24f2cd14d0888e1fed85d271ce7','63','外箱号','1','12','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,30),('2475d4c3a7584e1298d6502567755e5e','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,8),('247a50b06be6438bb96613c96d2a7c9f','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,40),('253ce48642a74c8ebdcbe51acd97a3b7','a269cbcdf38341eab4dea64e2717f346','60','改单次数','1','19','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,14),('25b83432c789400582b9452a813ebb2c','aedef24f2cd14d0888e1fed85d271ce7','61','Declaration #','1','10','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,27),('25f536ee3791488aa95e4ce2c9cc413e','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,7),('29923a5933e44c17bf770f117df0aa39','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,38),('29d3bd30fc64434db8e2e10f837f3aac','a269cbcdf38341eab4dea64e2717f346','62','lotType','1','5','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,24),('2a77cc4f264a48679e802527744d0356','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,2),('2c61942f83774419bd04efd7ebbc6402','a269cbcdf38341eab4dea64e2717f346','61','TransportMode','1','24','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,36),('2df05fd3e25a48f2a460da70ec970c2e','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,16),('2e8ca26ac79043458580ceb6310772a2','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,15),('2e9cfcbcc5914da0ab9ff335f1d43845','aedef24f2cd14d0888e1fed85d271ce7','62','DESC','1','8','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,27),('3036f84922584656b18246302d3d9b99','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,2),('31cbb914d42d41a3adc70053f529e990','aedef24f2cd14d0888e1fed85d271ce7','60','Carrier ATA','1','7','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,2),('3367318a465242829b44d9212e5b559a','a269cbcdf38341eab4dea64e2717f346','60','包装单位','1','14','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,31),('33ba54550cec4d62ab7c63fd472efb17','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,20),('33d08034b3e04cd199e35824a2b9b9b8','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,10),('33e089d59471478588d319d1436edb2c','a269cbcdf38341eab4dea64e2717f346','62','入库日期','1','1','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,1),('33f14d23a030499b9ddd83abcfee9eff','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,8),('344f20d1eb6b494781d17037a53327ea','a269cbcdf38341eab4dea64e2717f346','61','货币','1','14','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,29),('3467267f2891461b8ff40b22e842d064','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,7),('350add63d9644616b352168cfc0f3dc2','aedef24f2cd14d0888e1fed85d271ce7','62','规格','1','9','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,28),('3537ee9aeb7c4668819d2d55924f53a7','aedef24f2cd14d0888e1fed85d271ce7','63','CaseNumber','1','2','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,21),('3541671500404901811de975056f65cf','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,6),('355bc1529eee4a6987d7899fae3537b7','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,3),('36fe4a51b0894c408e9dcce3ec55b4e9','a269cbcdf38341eab4dea64e2717f346','61','OT','1','11','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,14),('3724aeed971042d8a87128adcd50d480','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,20),('373634ecbd60488180cf96d3f6848d9e','aedef24f2cd14d0888e1fed85d271ce7','61','Shipper Name','1','11','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,28),('373b628551234f269f92702bc1b9508e','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,6),('384e26ff195c477b9a5633c5baecbb69','aedef24f2cd14d0888e1fed85d271ce7','60','HAWB/HBL','1','5','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,25),('3877417be7d44c6fb44f34f4b70aae32','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,37),('38e6fb53370d4b2ea03210915a96ca37','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,39),('393df4c8c91942babcaea568482c3450','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,13),('39ca72c51a6945078f10a7422c073dc1','a269cbcdf38341eab4dea64e2717f346','61','出库重量','1','7','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,12),('3a297d523861468dbaf74d9efffa4dbe','aedef24f2cd14d0888e1fed85d271ce7','62','入仓时间','1','20','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,3),('3ae8ed83c8304037bd7abc99d0df96bb','a269cbcdf38341eab4dea64e2717f346','61','船名航次/航班','1','13','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,28),('3af4048a3699495893753870cfd37255','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,4),('3b24106ad26544cf9cdd5338f896d1cc','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,4),('3d469a4768964e4c922d08f303aaa683','aedef24f2cd14d0888e1fed85d271ce7','60','Total Invoice Value','1','11','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,12),('3dce42d5023f4f4a83b61f7bac1ff8f9','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,20),('4016657c4cba426ba1b9b882a92fa597','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,14),('4111329c50484301aca12fb60d4e1be5','a269cbcdf38341eab4dea64e2717f346','63','特别标注（2）','1','6','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,25),('41465549baec476eb32082ca1e0601ac','aedef24f2cd14d0888e1fed85d271ce7','63','DESC','1','8','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,27),('4244249e9f6144ef965e82c44c97e722','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,33),('426034d096e84feb8b671111d3562f83','a269cbcdf38341eab4dea64e2717f346','63','扫描料号','1','8','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,27),('42f3392a38f949cba4e1a12a9eeb79e9','a269cbcdf38341eab4dea64e2717f346','63','出库外箱号','1','15','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,33),('43c22c1843b24a11a4de8c7d6a4cc999','aedef24f2cd14d0888e1fed85d271ce7','60','Flight No/Vsl+Voy','1','13','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,29),('44a106f8b5a8474fb34699d4cf1f1d25','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,8),('4537bfeef0fb452ea77ce94634499fb3','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,18),('4658cfdf9e3549c18061b11a1ff8d1bc','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,37),('466a1c1f269e45f28bdc7c8a74f447b8','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,19),('47661667ae4b436285f43c7e539afed9','a269cbcdf38341eab4dea64e2717f346','62','HOLD','1','9','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,28),('4909dfa5ac444a6c91d34df2320f256e','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,40),('4988befbe0654a24ac3899f646587594','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,15),('498cf2b18fa04534a722b858059cd36a','a269cbcdf38341eab4dea64e2717f346','60','PICK AREA','1','16','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,33),('4a1cc3ea95344597960f5745a35dd779','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,17),('4b8a4bc3656340e3b6abcf7c97e4975d','a269cbcdf38341eab4dea64e2717f346','63','goodsbase','1','3','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,22),('4ba1134dc78b411290ce16c35d30398e','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,5),('4ba38a8bd9754958aa3661df30246f1b','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,2),('4bb9bcf0ff4f4ab69123218586498504','aedef24f2cd14d0888e1fed85d271ce7','62','Separate QTY','1','17','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,11),('4c4a4f9acfbf4b24b4b9cbd6fe91b164','aedef24f2cd14d0888e1fed85d271ce7','63','Remark 2','1','6','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,25),('4cda058345ee4d7ca13c3f88cce2521b','a269cbcdf38341eab4dea64e2717f346','61','UDR','1','17','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,32),('4cf91cb9703747d18d0d82875d517ca7','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,36),('4e3ef37a85ce445a9a67bc9acdc2719d','a269cbcdf38341eab4dea64e2717f346','60','货币','1','13','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,30),('4e4a583d640447ad8b6f0cb022473e45','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,6),('4ea41b2e8342465ab0bdc860f778f281','a269cbcdf38341eab4dea64e2717f346','61','改单次数','1','22','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,17),('4f0ce8a4616844e4befd61717eb037d6','a269cbcdf38341eab4dea64e2717f346','60','总重','1','3','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,12),('4f359a7b3b6949f994f89bb4ede5043a','a269cbcdf38341eab4dea64e2717f346','62','扫描料号','1','8','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,27),('4f89a119da7741b8986d1611f8011b7b','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,38),('5255e71663924dc1ae0580cae5be464d','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,4),('52676e21b2d948f0a4c74b841f1321d0','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,17),('55014123dba14ae79215e6bf18f420a0','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,36),('557171b47daf4178bdb785cb4fb3afd4','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,6),('5612f62ab3514b98be4a189e52a46ea5','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,5),('578cff0e67b64561862c987635a61ebd','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,8),('57eece94bfeb4f66b7acf658b6a7853e','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,14),('58295db96dc44f9bb429f0811864263d','a269cbcdf38341eab4dea64e2717f346','61','目的港口','1','16','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,31),('58e4dcc78c034df6bb269b34ec1001f2','aedef24f2cd14d0888e1fed85d271ce7','60','Truck Manifest#','1','14','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,30),('59122367e709438fb9566975833bc33b','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,9),('59230500ede74f568e66e36b8ed3b02c','a269cbcdf38341eab4dea64e2717f346','60','UDR','1','18','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,34),('592a8d83f2de4c0897bddc482985d9f2','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,39),('5b25a3e41bb74a8fbf02a51dc9f1a8f4','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,15),('5b8863ffc5064530a3910bd12e7c10ae','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,3),('5bb23046215d44c3b142e9c1bc35814b','aedef24f2cd14d0888e1fed85d271ce7','60','MAWB/MBL','1','4','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,24),('5c5caa0eb2fc46b282cf2c73229c4814','aedef24f2cd14d0888e1fed85d271ce7','60','Inbound Reference','1','1','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,21),('5c9b45daaafa41f5abba72b5c60fbb20','a269cbcdf38341eab4dea64e2717f346','63','IDS','1','4','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,23),('5e275626ce7140068ef1ae27f999e406','a269cbcdf38341eab4dea64e2717f346','61','MAWB','1','20','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,33),('5ea47d7d1ee143888adec1805e5dbffd','a269cbcdf38341eab4dea64e2717f346','62','COO','1','11','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,29),('5fc10b288125429c96290f41eb0f8f4e','aedef24f2cd14d0888e1fed85d271ce7','61','ETD','1','4','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,1),('6075568e3cf64acead3fdc1caca20e5c','a269cbcdf38341eab4dea64e2717f346','60','MAWB','1','6','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,24),('611479004cc44b21a2638304cd9f3bd4','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,39),('6284ebf7971b469481722c55854f655c','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,4),('62b69482da954ff1a9ac98451a8248c6','aedef24f2cd14d0888e1fed85d271ce7','63','LOT CODE','1','19','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,36),('6315f010001644a6b09fd4e932117322','a269cbcdf38341eab4dea64e2717f346','62','特别标注（1）','1','7','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,26),('640042054fe94ff29049889406cbcbee','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,40),('6431aa6c95c74d9493266167c8396745','a269cbcdf38341eab4dea64e2717f346','60','Invoive No.','1','1','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,21),('6459fda571c8416788b585fec518fb65','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,3),('66b0e6b0fddf43bcbba2f02d23f67934','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,5),('6721007f32864afd80544160a66c4882','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,7),('67584ab0e73b4bd4a42496c0736b7e0e','a269cbcdf38341eab4dea64e2717f346','61','HAWB','1','21','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,34),('68082defa45e457886a847206fefbd9d','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,14),('68b5b3066ada41b294809217ad1c9756','aedef24f2cd14d0888e1fed85d271ce7','63','COO','1','18','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,35),('6932c1002c754bb9b2eb61429747239d','aedef24f2cd14d0888e1fed85d271ce7','63','Remark 1','1','5','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,24),('698dd5dd72ae4e81a830eec4fa5ea006','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,3),('6995e922dc414deda5d020e1c36542a0','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,5),('69bd537486de455e8b8de9ea1327a782','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,39),('6b95ac52513a4248b6efa4677a41e832','aedef24f2cd14d0888e1fed85d271ce7','62','COO','1','18','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,35),('6d2b93cb4d3b400fb51c9904e508a27d','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,6),('6dac3d15998a49be8290634dac2e9bc1','a269cbcdf38341eab4dea64e2717f346','63','入库日期','1','1','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,1),('6f00fd456c544ecf88dc81b0f6fdb0ff','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,17),('6f9bd58e3d1044bab4b21048d8268772','a269cbcdf38341eab4dea64e2717f346','63','Separate QTY','1','10','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,11),('6fb1789df74b41ada91f38557d4ae270','aedef24f2cd14d0888e1fed85d271ce7','62','LOT CODE','1','19','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,36),('70a566c39e9749b9bfcdfa108fab6b35','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,10),('71b15db4a33b42f3bb42a6ee79dac1fb','aedef24f2cd14d0888e1fed85d271ce7','60','Carrier ETA','1','6','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,1),('720038b1ff8c4febb1809f1315c89514','a269cbcdf38341eab4dea64e2717f346','60','总价','1','2','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,11),('72f827a916e24ff8a59d57ebd7658d81','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,19),('73b331a61834400bb1d73846226dd4df','a269cbcdf38341eab4dea64e2717f346','63','DATE CODE','1','13','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,31),('743d39bff60f4ed7af467d42f0da6629','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,9),('764206bd702f4d5dad15454f792843c3','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,10),('766444df598d48fa84fbb4f95d615eda','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,15),('76ef9911906342cfb187065ecbe04fd1','a269cbcdf38341eab4dea64e2717f346','60','船名航次/航班','1','12','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,29),('78c23d94fef748b68a6a772b40713c09','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,19),('79b183a48ab9494fa25c9574228c8200','a269cbcdf38341eab4dea64e2717f346','61','标签','1','5','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,25),('7a879c3be7ab4934af4f6c30c5568070','a269cbcdf38341eab4dea64e2717f346','60','发货港口','1','15','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,32),('7b1c6eedc01746b2b7e06985e339a106','aedef24f2cd14d0888e1fed85d271ce7','60','Declaration #','1','10','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,27),('7b7838f298634544a8d664ec133d2ea0','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,32),('7bb6aea168f9463badced9fc9edd2ff7','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,18),('7c28d6303d4b4a23882cf27d2f3e86a9','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,16),('7c842ab537b64999bd51f3055a7f98e2','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,5),('7d5fcbb36caf457e970068e421ca3cea','a269cbcdf38341eab4dea64e2717f346','61','运输公司','1','8','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,26),('7e080c0fc62d4b15a40d5b1bf66e37fa','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,8),('7e34afb2877d41e988d3aa96369d4a5a','aedef24f2cd14d0888e1fed85d271ce7','62','中箱号','1','13','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,31),('7f082340ed194c6589573fb214bb9c5f','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,12),('7fabcde3352a4a4181910ad235d1dcee','a269cbcdf38341eab4dea64e2717f346','61','ConsigneeAddress','1','25','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,37),('7fbb592411c64bf89abef7a99a4cd430','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,20),('7fc658bfd26548c18a27c37980feea07','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,3),('8288e007fddb414ca6299c079dc21b5b','aedef24f2cd14d0888e1fed85d271ce7','62','CPN','1','16','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,34),('837dab8370644ec1adb652137195984e','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,17),('83ab1e36e1544986b5e9c93753a6c086','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,39),('83b160201580420bbfbb632a1c1feebc','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,34),('843a7d866dea4f9c9d397be5a5f8b280','a269cbcdf38341eab4dea64e2717f346','60','HAWB','1','5','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,23),('858843e904df44b2951b07915ca894cf','a269cbcdf38341eab4dea64e2717f346','63','外箱号','1','14','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,32),('876646f9b8ca47e4959852f95e484e45','aedef24f2cd14d0888e1fed85d271ce7','63','出库外箱号','1','23','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,39),('87d691b2b70e4b998b163d9896473304','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,15),('884bbb30f2824edba7da8248cf209b9c','a269cbcdf38341eab4dea64e2717f346','60','TransportMode','1','21','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,36),('8874f0cf29044afcadc72e3371685daa','a269cbcdf38341eab4dea64e2717f346','61','车型','1','1','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,21),('89209423c496486e88bdb24adc8e2134','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,19),('8ac9f7d0229c420ebf53fb66221cdac2','aedef24f2cd14d0888e1fed85d271ce7','63','DATE CODE 转化','1','10','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,2),('8af8b37728ff4e24a319bdd3a01dd714','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,13),('8bc77a8c57b840e8a6141e51cfefaf6f','aedef24f2cd14d0888e1fed85d271ce7','61','Shipper Address','1','12','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,29),('8d0ad0c584cf4ae1ad59157c0f7f86c3','a269cbcdf38341eab4dea64e2717f346','61','出货箱数','1','9','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,13),('8db176c2855a4e2e973211e805448513','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,18),('8e16981b0d3f47af9d614f28036a0469','aedef24f2cd14d0888e1fed85d271ce7','63','入库日期','1','1','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,1),('8e92d6aebbb4498194fd2cdd81f2c27e','aedef24f2cd14d0888e1fed85d271ce7','62','外箱号','1','12','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,30),('9146544885914d78b12845dd7df7024c','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,4),('91cf1e338bc64eaf8379fc17f370b2c7','aedef24f2cd14d0888e1fed85d271ce7','61','Total Invoice Value','1','7','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,11),('9323aab8878a4bd8a75ab73c567f698f','a269cbcdf38341eab4dea64e2717f346','63','LOT CODE','1','12','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,30),('93bbbf63cd1e41e1be3692f0297a50f4','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,37),('94c17275f960474988927353ef5dc846','aedef24f2cd14d0888e1fed85d271ce7','61','Reotec Reference No','1','9','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,26),('9525c97cb36f4a3baec49f54a6dde295','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,36),('9583984c16b44d26abd9b7ee375c3c52','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,7),('96b7641b69e84e75b590d8a8762151ff','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,12),('98294678b5dd4536957386475d56be50','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,10),('99c09e36ec624eb6adfc5b303f8e3152','a269cbcdf38341eab4dea64e2717f346','62','DATE CODE','1','13','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,31),('9a5911ca92ce47c1aedc2fe58686b28c','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,40),('9a851cf50b7c47598b9b8f2a03ddbbfa','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,16),('9bbb0b888a784447abdbecd76b9f807e','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,34),('9d858a74e73a42e7b4287056556e1ea9','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,13),('9e05bd1e98f64c3faaee877de5e312a1','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,13),('9fe3c676a8db42c38605a6075df0e625','a269cbcdf38341eab4dea64e2717f346','61','拆箱箱数','1','19','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,16),('a06e3b00f1e246d2ae9399ba655afb94','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,14),('a12b88fe343b49a2ba53a14f0d971008','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,13),('a21630a7b3a945bea90cbdf0800814d1','a269cbcdf38341eab4dea64e2717f346','61','价值','1','18','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,15),('a45675b747bc400f8062ef4461b1650c','a269cbcdf38341eab4dea64e2717f346','60','到港日期','1','11','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,1),('a4e904bc1dd94a04a5cd12b2698ee815','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,20),('a6feb7c7bda248528fc1e0c5c84b3535','aedef24f2cd14d0888e1fed85d271ce7','60','# of package','1','8','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,26),('a960e420fc8b430090c47bffbc9aa312','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,9),('a97a1985d04141e3be3236b12f741b6b','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,35),('a9e27b74248941eb9212fb71a58165f2','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,39),('abc0cf4fb2cb4da2997d8713acab88ed','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,12),('acc7d6b2139b41888b693876d0c0309a','a269cbcdf38341eab4dea64e2717f346','60','FLAG','1','23','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,38),('add484077b5e42ffbbd9493cdaad9fc1','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,7),('ae713ec700ed41bf8cf7108abdad68c3','aedef24f2cd14d0888e1fed85d271ce7','60','Currency','1','12','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,28),('aeb3fe113fb54bb68f94ba91b106dc4b','a269cbcdf38341eab4dea64e2717f346','61','打带箱数','1','6','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,11),('b0f369c4a9804586bc9ed0582375ab18','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,17),('b14137c5f0bf4e9dae499c6612ff4ba6','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,9),('b18b8de7a161408d9138c16dce45c522','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,10),('b33bc604402a4a228063aae20454b32a','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,20),('b4a1551dd126443190bd36065ec5f1b7','aedef24f2cd14d0888e1fed85d271ce7','62','Remark 2','1','6','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,25),('b5159442578f464db6266bbbaa997c21','aedef24f2cd14d0888e1fed85d271ce7','60','Manufacturer','1','2','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,22),('b618c07454bd4e0ea61f9cd32ec42b09','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,10),('b824693f4b2140c4b846025aceea81a1','aedef24f2cd14d0888e1fed85d271ce7','63','CPO','1','14','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,32),('b893dc1b3490468faae87cf89e12c5db','a269cbcdf38341eab4dea64e2717f346','60','是否报关','1','20','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,35),('ba360498285443658392306f46d2ca66','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,4),('baea65cd009e432b89c357d49fbd0c6e','aedef24f2cd14d0888e1fed85d271ce7','62','入仓箱号','1','21','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,37),('bc8956076ddd43098a8ffaee29846ac0','aedef24f2cd14d0888e1fed85d271ce7','62','CaseNumber','1','2','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,21),('bd71b3a29a754e2da77763463ecf18af','aedef24f2cd14d0888e1fed85d271ce7','63','CPN','1','16','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,34),('be5bcefb13e84fd0bc47ef081481c1e1','a269cbcdf38341eab4dea64e2717f346','61','出货模式','1','4','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,24),('bed321e4f1674f9c8470cf9dcac87a90','a269cbcdf38341eab4dea64e2717f346','61','是否报关','1','23','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,35),('bf0d9471f98249219745df4847db565e','a269cbcdf38341eab4dea64e2717f346','63','lotType','1','5','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,24),('bf51df979600406fb63df11da6230892','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,9),('bf59a456eccc42f2b2e9904c89e50c9c','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,6),('c0cc78a2b5c24b41b923d0420222d32f','aedef24f2cd14d0888e1fed85d271ce7','63','DATE CODE 实物','1','22','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,38),('c2ab4e4f062c44ffab876706d2824024','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,5),('c345d575e9524d148507131686d56673','a269cbcdf38341eab4dea64e2717f346','61','出口日期','1','12','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,1),('c385bf656e2c43cea33fb9c25887e4f0','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,15),('c3fd204c56dd4c84a70921e707a9d5cf','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,31),('c60e4dcd4d6444978f9d94f277f87c06','aedef24f2cd14d0888e1fed85d271ce7','62','打印分类','1','4','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,23),('c6233c31200849f6a558cef1f138d5db','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,40),('c7ec169352424651810e970da701efca','aedef24f2cd14d0888e1fed85d271ce7','60','Shipper','1','3','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,23),('c992428d2a714737b07bc93b724b5e2d','a269cbcdf38341eab4dea64e2717f346','60','OT','1','17','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,13),('ca0fb1a8fec74ff18b6cc71e72eac0ed','aedef24f2cd14d0888e1fed85d271ce7','62','Remark 3','1','7','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,26),('ca53e03ee1a24060954ff082089f626e','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,33),('cb1c7c4b7131484ab309f76d89a87d01','a269cbcdf38341eab4dea64e2717f346','63','CaseNumber','1','2','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,21),('cb78995e1d2a4cfab6ecceed06ae4241','aedef24f2cd14d0888e1fed85d271ce7','61','Consignee Name','1','1','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,21),('cbb5320f43f244ddb84c669bf96a3d30','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,7),('cc7962891c0d46f29333134a2d8eb6bb','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,38),('cdb5445a156e449aaf0b979289707f4f','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,19),('cec276b9aff7459fae1e0a25ed39a9df','a269cbcdf38341eab4dea64e2717f346','60','运输公司','1','10','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,28),('cfb29a7f48a34105a287bb98612a9d3f','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,7),('d021eb48934b455d9e6db1d29a39b817','aedef24f2cd14d0888e1fed85d271ce7','61','Truck Manifest#','1','6','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,24),('d05008e97a3349e48c71a7f63dea4798','aedef24f2cd14d0888e1fed85d271ce7','61','HAWB/HBL','1','3','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,23),('d0ca7c61137c428bad5a1846b63b448f','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,16),('d157e4a1aaa14227a76a85d1fb271b0d','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,17),('d236e4179b58482c9106719027c91f27','aedef24f2cd14d0888e1fed85d271ce7','63','中箱号','1','13','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,31),('d3853831144b4243a1df3c2c3b72ab6e','a269cbcdf38341eab4dea64e2717f346','60','Vender Name','1','7','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,25),('d4ad2d90b4da4f74ae17c3e6c38fbfa8','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,39),('d4ce1c9c7d644a2393cb355a27040500','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,8),('d4cfbaf4c1964e71b5de9a33805ddb98','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,35),('d52075f5f31e4eb3adc35b09cc6a1da7','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,3),('d571c231242c4248a727116e6042c5c7','aedef24f2cd14d0888e1fed85d271ce7','62','DATE CODE 实物','1','22','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,38),('d5c33517cfa04aeaa47f76e09af2fed0','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,19),('d60c91a0ae364ca2a3a45ab9c6762cd9','a269cbcdf38341eab4dea64e2717f346','62','Separate QTY','1','10','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,11),('d961f64aaef745fdbe14f22d5ccc86d0','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,6),('d9ec035b1a76435aab72fd932de3df4c','aedef24f2cd14d0888e1fed85d271ce7','63','规格','1','9','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,28),('dca3ee6752824b84a85c48280eb9136e','aedef24f2cd14d0888e1fed85d271ce7','63','Scan  Code','1','15','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,33),('dd2ba30d070a49d0ab2ce7edbb4bd4eb','aedef24f2cd14d0888e1fed85d271ce7','63','',NULL,'','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,9),('de8828f8bd304192b22fb2a061a3f046','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,14),('dec739fb1e364ec0802dc832d0a96761','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,4),('e1db606e2f26470facbef3409dcff36d','aedef24f2cd14d0888e1fed85d271ce7','61','ATD','1','5','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,2),('e393f9750cf146f794f0ce7fbf2a162e','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,34),('e97dc3f6a05a4b609d67168c1efcc059','a269cbcdf38341eab4dea64e2717f346','61','FLAG','1','26','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,38),('e9dcd113cb7b46ba968301c7dd35ba4b','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,18),('ea07c2ed10b840998b59e783987fd25f','aedef24f2cd14d0888e1fed85d271ce7','62','CPO','1','14','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,32),('ea626e0fcc974740a137dedd5e571a7e','aedef24f2cd14d0888e1fed85d271ce7','62','入库日期','1','1','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,1),('eb31ee7584fc41d9a5dbf1bc99246c3d','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,12),('ebca5109b2eb4430b16a8940b52e7c87','a269cbcdf38341eab4dea64e2717f346','62','IDS','1','4','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,23),('ec30be0e6d0e494ebf9dc0b4a5030a4f','aedef24f2cd14d0888e1fed85d271ce7','60','GW','1','9','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,11),('ede6c72d84c344aa97e3ff4263e05466','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,17),('ee36d9c8dc0443cdb1d2d53cb29fccf1','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,4),('eecdf1455f05480b86badded802a5307','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,35),('f06282e05a2142928d105bd19aeaa730','a269cbcdf38341eab4dea64e2717f346','60','原始包装','1','4','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,22),('f33d6f9ba07241efa63de2e23f462358','aedef24f2cd14d0888e1fed85d271ce7','63','入仓箱号','1','21','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,37),('f399f7f1692043f4b98e99124808cfe6','aedef24f2cd14d0888e1fed85d271ce7','63','Customer Inv #','1','3','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,22),('f43118b5dc854b9aaceced3e73d2b353','aedef24f2cd14d0888e1fed85d271ce7','61','',NULL,'','admin','2016-11-30 23:11:28','admin','2016-11-30 23:11:28',0,31),('f46b5e6b507f4a71a50a007aba023d9b','a269cbcdf38341eab4dea64e2717f346','61','INVOICE','1','2','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,22),('f6a8bdbe61c44431bc55f5f594c37c6f','a269cbcdf38341eab4dea64e2717f346','62','特别标注（2）','1','6','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,25),('f6f34977eb664d4abb2c7dcf4d43ddf3','a269cbcdf38341eab4dea64e2717f346','61','',NULL,'','admin','2016-11-30 23:20:31','admin','2016-11-30 23:20:31',0,8),('f845c296ba9b4f1f8b3a3c23a62dde6e','aedef24f2cd14d0888e1fed85d271ce7','62','',NULL,'','admin','2016-11-30 22:57:52','admin','2016-11-30 22:57:52',0,16),('f92c2ca57dc54a4487d52701e58f28f1','a269cbcdf38341eab4dea64e2717f346','60','',NULL,'','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,2),('fa93d4bdbebb4fe28d576d617cc863a6','aedef24f2cd14d0888e1fed85d271ce7','63','Reel ID','1','11','admin','2016-11-30 23:15:40','admin','2016-11-30 23:15:40',0,29),('fab7f67ae6a74a46aaafc15a573e7330','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,33),('fb9e2579f31d4a06ba3aab053dd1309b','aedef24f2cd14d0888e1fed85d271ce7','60','',NULL,'','admin','2016-11-30 22:52:13','admin','2016-11-30 22:52:13',0,13),('fbf2b5dc8cce46f1b677151d04d5a9a9','a269cbcdf38341eab4dea64e2717f346','60','收货方式','1','9','admin','2016-11-30 23:03:16','admin','2016-11-30 23:03:16',0,27),('fcd39235126841f39e0384975a41e398','a269cbcdf38341eab4dea64e2717f346','62','LOT CODE','1','12','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,30),('fda28a60785c454aaf28d4954832bd98','a269cbcdf38341eab4dea64e2717f346','62','',NULL,'','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,34),('ff2d0df4a75149d2a3b9c1feb2bab398','a269cbcdf38341eab4dea64e2717f346','63','',NULL,'','admin','2016-11-30 23:22:54','admin','2016-11-30 23:22:54',0,9),('ffe6b9e9421d4ddfbbc31dd0b0d20b1f','a269cbcdf38341eab4dea64e2717f346','62','外箱号','1','14','admin','2016-11-30 23:06:37','admin','2016-11-30 23:06:37',0,32);

/*Table structure for table `tb_packrule` */

DROP TABLE IF EXISTS `tb_packrule`;

CREATE TABLE `tb_packrule` (
  `PACKRULE_ID` varchar(100) NOT NULL,
  `PACK_CN` varchar(255) DEFAULT NULL COMMENT '包装规则',
  `PACK_DSC` varchar(255) DEFAULT NULL COMMENT '包装描述',
  `STANDARD_PACK` varchar(255) DEFAULT NULL COMMENT '标准包装',
  `EA_NUM` varchar(255) DEFAULT NULL COMMENT 'EA数量',
  `EA_DSC` varchar(255) DEFAULT NULL COMMENT 'EA描述',
  `EA_DOCK` varchar(255) DEFAULT NULL COMMENT 'EA装箱',
  `EA_REPL` varchar(255) DEFAULT NULL COMMENT 'EA补货',
  `EA_IN` varchar(255) DEFAULT NULL COMMENT 'EA收货',
  `EA_OUT` varchar(255) DEFAULT NULL COMMENT 'EA出货',
  `EA_LONG` varchar(255) DEFAULT NULL COMMENT 'EA长',
  `EA_WIDE` varchar(255) DEFAULT NULL COMMENT 'EA宽',
  `EA_HIGH` varchar(255) DEFAULT NULL COMMENT 'EA高',
  `EA_VOLUME` varchar(255) DEFAULT NULL COMMENT 'EA体积',
  `EA_WEIGH` varchar(255) DEFAULT NULL COMMENT 'EA重量',
  `INPACK_NUM` varchar(255) DEFAULT NULL COMMENT 'INPACK数量',
  `INPACK_DSC` varchar(255) DEFAULT NULL COMMENT 'INPACK描述',
  `INPACK_DOCK` varchar(255) DEFAULT NULL COMMENT 'INPACK装箱',
  `INPACK_REPL` varchar(255) DEFAULT NULL COMMENT 'INPACK补货',
  `INPACK_IN` varchar(255) DEFAULT NULL COMMENT 'INPACK收货',
  `INPACK_OUT` varchar(255) DEFAULT NULL COMMENT 'INPACK出货',
  `INPACK_LONG` varchar(255) DEFAULT NULL COMMENT 'INPACK长',
  `INPACK_WIDE` varchar(255) DEFAULT NULL COMMENT 'INPACK宽',
  `INPACK_HIGH` varchar(255) DEFAULT NULL COMMENT 'INPACK高',
  `INPACK_VOLUME` varchar(255) DEFAULT NULL COMMENT 'INPACK体积',
  `INPACK_WEIGH` varchar(255) DEFAULT NULL COMMENT 'INPACK重量',
  `BOX_NUM` varchar(255) DEFAULT NULL COMMENT 'BOX数量',
  `BOX_DSC` varchar(255) DEFAULT NULL COMMENT 'BOX描述',
  `BOX_DOCK` varchar(255) DEFAULT NULL COMMENT 'BOX装箱',
  `BOX_REPL` varchar(255) DEFAULT NULL COMMENT 'BOX补货',
  `BOX_IN` varchar(255) DEFAULT NULL COMMENT 'BOX收货',
  `BOX_OUT` varchar(255) DEFAULT NULL COMMENT 'BOX出货',
  `BOX_LONG` varchar(255) DEFAULT NULL COMMENT 'BOX长',
  `BOX_WIDE` varchar(255) DEFAULT NULL COMMENT 'BOX宽',
  `BOX_HIGH` varchar(255) DEFAULT NULL COMMENT 'BOX高',
  `BOX_VOLUME` varchar(255) DEFAULT NULL COMMENT 'BOX体积',
  `BOX_WEIGH` varchar(255) DEFAULT NULL COMMENT 'BOX重量',
  `PALLET_NUM` varchar(255) DEFAULT NULL COMMENT 'PALLET数量',
  `PALLET_DSC` varchar(255) DEFAULT NULL COMMENT 'PALLET描述',
  `PALLET_DOCK` varchar(255) DEFAULT NULL COMMENT 'PALLET装箱',
  `PALLET_REPL` varchar(255) DEFAULT NULL COMMENT 'PALLET补货',
  `PALLET_IN` varchar(255) DEFAULT NULL COMMENT 'PALLET收货',
  `PALLET_OUT` varchar(255) DEFAULT NULL COMMENT 'PALLET出货',
  `PALLET_LONG` varchar(255) DEFAULT NULL COMMENT 'PALLET长',
  `PALLET_WIDE` varchar(255) DEFAULT NULL COMMENT 'PALLET宽',
  `PALLET_HIGH` varchar(255) DEFAULT NULL COMMENT 'PALLET高',
  `PALLET_VOLUME` varchar(255) DEFAULT NULL COMMENT 'PALLET体积',
  `PALLET_WEIGH` varchar(255) DEFAULT NULL COMMENT 'PALLET重量',
  `PALLET_TI` varchar(255) DEFAULT NULL COMMENT 'PALLETTI',
  `PALLET_HI` varchar(255) DEFAULT NULL COMMENT 'PALLETTI',
  `OTHER_NUM` varchar(255) DEFAULT NULL COMMENT 'OTHER数量',
  `OTHER_DSC` varchar(255) DEFAULT NULL COMMENT 'OTHER描述',
  `OTHER_DOCK` varchar(255) DEFAULT NULL COMMENT 'OTHER装箱',
  `OTHER_REPL` varchar(255) DEFAULT NULL COMMENT 'OTHER补货',
  `OTHER_IN` varchar(255) DEFAULT NULL COMMENT 'OTHER收货',
  `OTHER_OUT` varchar(255) DEFAULT NULL COMMENT 'OTHER出货',
  `OTHER_LONG` varchar(255) DEFAULT NULL COMMENT 'OTHER长',
  `OTHER_WIDE` varchar(255) DEFAULT NULL COMMENT 'OTHER宽',
  `OTHER_HIGH` varchar(255) DEFAULT NULL COMMENT 'OTHER高',
  `OTHER_VOLUME` varchar(255) DEFAULT NULL COMMENT 'OTHER体积',
  `OTHER_WEIGH` varchar(255) DEFAULT NULL COMMENT 'OTHER重量',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除',
  PRIMARY KEY (`PACKRULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_packrule` */

insert  into `tb_packrule`(`PACKRULE_ID`,`PACK_CN`,`PACK_DSC`,`STANDARD_PACK`,`EA_NUM`,`EA_DSC`,`EA_DOCK`,`EA_REPL`,`EA_IN`,`EA_OUT`,`EA_LONG`,`EA_WIDE`,`EA_HIGH`,`EA_VOLUME`,`EA_WEIGH`,`INPACK_NUM`,`INPACK_DSC`,`INPACK_DOCK`,`INPACK_REPL`,`INPACK_IN`,`INPACK_OUT`,`INPACK_LONG`,`INPACK_WIDE`,`INPACK_HIGH`,`INPACK_VOLUME`,`INPACK_WEIGH`,`BOX_NUM`,`BOX_DSC`,`BOX_DOCK`,`BOX_REPL`,`BOX_IN`,`BOX_OUT`,`BOX_LONG`,`BOX_WIDE`,`BOX_HIGH`,`BOX_VOLUME`,`BOX_WEIGH`,`PALLET_NUM`,`PALLET_DSC`,`PALLET_DOCK`,`PALLET_REPL`,`PALLET_IN`,`PALLET_OUT`,`PALLET_LONG`,`PALLET_WIDE`,`PALLET_HIGH`,`PALLET_VOLUME`,`PALLET_WEIGH`,`PALLET_TI`,`PALLET_HI`,`OTHER_NUM`,`OTHER_DSC`,`OTHER_DOCK`,`OTHER_REPL`,`OTHER_IN`,`OTHER_OUT`,`OTHER_LONG`,`OTHER_WIDE`,`OTHER_HIGH`,`OTHER_VOLUME`,`OTHER_WEIGH`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('2e1d303b6ce04c2788b4a0373de90666','SPREA','展讯规则',NULL,'1','',NULL,NULL,'1','1','','','','','','','',NULL,NULL,'0','0','','','','','','20','',NULL,NULL,'1','1','','','','','','','',NULL,NULL,'0','0','','','','','','','','10','',NULL,NULL,'1','1','','','','','','admin','2016-11-30 20:33:32','admin','2016-11-30 20:33:32',0),('823d15ddc2c2423da4284acc8d1e273a','REOTEC','瑞普规则',NULL,'1','',NULL,NULL,'1','1','','','','','','0','',NULL,NULL,'0','0','','','','','','12','',NULL,NULL,'1','1','','','','','','0','',NULL,NULL,'0','0','','','','','','','','0','',NULL,NULL,'0','0','','','','','','admin','2016-11-30 20:33:08','admin','2016-11-30 20:33:08',0);

/*Table structure for table `tb_pickrule` */

DROP TABLE IF EXISTS `tb_pickrule`;

CREATE TABLE `tb_pickrule` (
  `PICKRULE_ID` varchar(100) NOT NULL,
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `PICK_RULE_CODE` varchar(255) DEFAULT NULL COMMENT '拣货规则编码',
  `PICK_RULE_CN` varchar(255) DEFAULT NULL COMMENT '拣货规则名称',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `PROD_COMB_USE` varchar(255) DEFAULT NULL COMMENT '按料号合并拣货',
  `OUT_PROD_DAY` varchar(255) DEFAULT NULL COMMENT '按出货日期合并拣货',
  `OUT_PROD_USE` varchar(255) DEFAULT NULL COMMENT '启用',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  PRIMARY KEY (`PICKRULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_pickrule` */

insert  into `tb_pickrule`(`PICKRULE_ID`,`CUSTOMER_ID`,`PICK_RULE_CODE`,`PICK_RULE_CN`,`MEMO`,`PROD_COMB_USE`,`OUT_PROD_DAY`,`OUT_PROD_USE`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('8aa0fd9789d84442868f611bb384747a','a269cbcdf38341eab4dea64e2717f346','SPREA','展讯拣货规则','','1','2016-11-30','1','admin','2016-11-30 20:43:50','admin','2016-11-30 20:43:50','0'),('e91d07e0cc8a451284d7fe32426a073f','aedef24f2cd14d0888e1fed85d271ce7','REOTEC','REOTEC拣货规则','','1','2016-11-30','1','admin','2016-11-30 20:43:22','admin','2016-11-30 20:43:22','0');

/*Table structure for table `tb_pictures` */

DROP TABLE IF EXISTS `tb_pictures`;

CREATE TABLE `tb_pictures` (
  `PICTURES_ID` varchar(100) NOT NULL,
  `TITLE` varchar(255) DEFAULT NULL COMMENT '标题',
  `NAME` varchar(255) DEFAULT NULL COMMENT '文件名',
  `PATH` varchar(255) DEFAULT NULL COMMENT '路径',
  `CREATETIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MASTER_ID` varchar(255) DEFAULT NULL COMMENT '属于',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`PICTURES_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_pictures` */

insert  into `tb_pictures`(`PICTURES_ID`,`TITLE`,`NAME`,`PATH`,`CREATETIME`,`MASTER_ID`,`BZ`) values ('1d16c4e6ac2d46fda5802462819b3162','图片','ef09a150ba8f4f36864fbfa6540ffda8.jpg','20150803/ef09a150ba8f4f36864fbfa6540ffda8.jpg','2015-08-03 14:31:32','1','图片管理处上传'),('aa07d74f97fe4171be10067f6e738820','图片','c238f8ac2343484daee37c70855c217a.jpg','20150803/c238f8ac2343484daee37c70855c217a.jpg','2015-08-03 14:33:08','1','图片管理处上传'),('bd0f0dbf926b41c986e14d7e3008e65a','图片','f91e764e253f4de384bec4c7e6342af3.jpg','20150803/f91e764e253f4de384bec4c7e6342af3.jpg','2015-08-03 14:31:32','1','图片管理处上传'),('ccde8af6778f42e7924ede153d9c1465','图片','25dba768c6a04904b2cfce26730ee50d.jpg','20150803/25dba768c6a04904b2cfce26730ee50d.jpg','2015-08-03 14:31:33','1','图片管理处上传');

/*Table structure for table `tb_pn_map` */

DROP TABLE IF EXISTS `tb_pn_map`;

CREATE TABLE `tb_pn_map` (
  `PNMAP_ID` varchar(100) NOT NULL,
  `CUSTOMER_ID` varchar(32) DEFAULT NULL,
  `SCAN_PN` varchar(255) DEFAULT NULL COMMENT '扫描料号',
  `ACTUAL_PN` varchar(255) DEFAULT NULL COMMENT '实际料号',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  PRIMARY KEY (`PNMAP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_pn_map` */

insert  into `tb_pn_map`(`PNMAP_ID`,`CUSTOMER_ID`,`SCAN_PN`,`ACTUAL_PN`,`MEMO`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('3e73f1f311b64d4b8be1988b57d9ec8b',NULL,'CDE','a88f276153e54542a7dc7fea1987437c','','admin','2016-11-30 23:40:56','admin','2016-11-30 23:40:56','0'),('a8e8642e0be947088ed539cf24eeb81e',NULL,'A','c3d5f3a4a2ce4d9f96d00d1f1bdd931e','','admin','2016-11-30 23:41:09','admin','2016-11-30 23:41:09','0'),('b41d48dbc39c4099b1b64092b8befdc2',NULL,'B','5b76263cb49c47baae0a3fd23212578c','','admin','2016-11-30 23:41:16','admin','2016-11-30 23:41:16','0'),('df2fb815db0f40879221b97e5bdee4ff',NULL,'ABC','e3f7a70bf1524c1bbdaddb7c8fb6c57d','','admin','2016-11-30 23:40:45','admin','2016-11-30 23:40:45','0');

/*Table structure for table `tb_product` */

DROP TABLE IF EXISTS `tb_product`;

CREATE TABLE `tb_product` (
  `PRODUCT_ID` varchar(100) NOT NULL,
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '货主',
  `STOCK_ID` varchar(255) DEFAULT NULL COMMENT '仓库',
  `PRODUCT_CODE` varchar(255) DEFAULT NULL COMMENT '产品编号',
  `PRODUCT_CN` varchar(255) DEFAULT NULL COMMENT '产品描述',
  `PRODUCT_EN` varchar(255) DEFAULT NULL COMMENT '英文描述',
  `LONG_UNIT` varchar(255) DEFAULT NULL COMMENT '长cm',
  `WIDE_UNIT` varchar(255) DEFAULT NULL COMMENT '宽cm',
  `HIGH_UNIT` varchar(255) DEFAULT NULL COMMENT '高cm',
  `UNIT_PRICE` varchar(255) DEFAULT NULL COMMENT '单价',
  `TOTAL_WEIGHT_UNIT` varchar(128) NOT NULL COMMENT '毛重单位',
  `TOTAL_WEIGHT` varchar(255) DEFAULT NULL COMMENT '毛重',
  `TOTAL_SUTTLE_UNIT` varchar(128) NOT NULL COMMENT '净重单位',
  `TOTAL_SUTTLE` varchar(255) DEFAULT NULL COMMENT '净重',
  `VOLUME_UNIT` varchar(128) NOT NULL COMMENT '体积单位',
  `VOLUME_UN` varchar(255) DEFAULT NULL COMMENT '体积',
  `PRODUCT_TYPE` varchar(255) DEFAULT NULL COMMENT '货物类型',
  `STORAGE_ID` varchar(255) DEFAULT NULL COMMENT '码放库区',
  `LOCATOR_ID` varchar(255) DEFAULT NULL COMMENT '码放库位',
  `FREEZE_REJECT_CODE` varchar(128) NOT NULL COMMENT '冻结/拒收代码',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `DANGER_FLG` int(11) NOT NULL COMMENT '危险品',
  `MANAGER_FLG` int(11) NOT NULL COMMENT '管理序列号',
  `COMBI_FLG` int(11) NOT NULL COMMENT '组合件',
  `ACTIVE_FLG` int(11) NOT NULL COMMENT '激活',
  `RULE_PACK` varchar(255) DEFAULT NULL COMMENT '包装规则',
  `RULE_PICK` varchar(255) DEFAULT NULL COMMENT '拣货规则',
  `RULE_STORAGE` varchar(255) DEFAULT NULL COMMENT '库位指定规则',
  `RULE_STORAGE_ASIGN` varchar(255) DEFAULT NULL COMMENT '库位分配规则',
  `RULE_STOCK_ASIGN` varchar(255) DEFAULT NULL COMMENT '库存分配规则',
  `USE_VALIDITY` int(11) NOT NULL COMMENT '启用有效期',
  `PERIOD_TYPE` varchar(128) NOT NULL COMMENT '周期类型',
  `INBOUND_DAY` int(11) NOT NULL COMMENT '入库生命天数',
  `OUTBOUND_DAY` int(11) NOT NULL COMMENT '出库生命天数',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除',
  `STOCKTURN_ID` varchar(64) DEFAULT NULL COMMENT '库存周转规则',
  PRIMARY KEY (`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_product` */

insert  into `tb_product`(`PRODUCT_ID`,`CUSTOMER_ID`,`STOCK_ID`,`PRODUCT_CODE`,`PRODUCT_CN`,`PRODUCT_EN`,`LONG_UNIT`,`WIDE_UNIT`,`HIGH_UNIT`,`UNIT_PRICE`,`TOTAL_WEIGHT_UNIT`,`TOTAL_WEIGHT`,`TOTAL_SUTTLE_UNIT`,`TOTAL_SUTTLE`,`VOLUME_UNIT`,`VOLUME_UN`,`PRODUCT_TYPE`,`STORAGE_ID`,`LOCATOR_ID`,`FREEZE_REJECT_CODE`,`MEMO`,`DANGER_FLG`,`MANAGER_FLG`,`COMBI_FLG`,`ACTIVE_FLG`,`RULE_PACK`,`RULE_PICK`,`RULE_STORAGE`,`RULE_STORAGE_ASIGN`,`RULE_STOCK_ASIGN`,`USE_VALIDITY`,`PERIOD_TYPE`,`INBOUND_DAY`,`OUTBOUND_DAY`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`STOCKTURN_ID`) values ('5b76263cb49c47baae0a3fd23212578c','a269cbcdf38341eab4dea64e2717f346','e31596b5c7c242b0b79cecdb91cd6ee2','S--PRODUCT2','展讯产品测试2','S--PRODUCT2','15','15','15','10','26','','32','','38','3.375','95','ade3b6c3e1e441098a4ed7734e822f86','','42','',0,0,0,0,'2e1d303b6ce04c2788b4a0373de90666','8aa0fd9789d84442868f611bb384747a','120','ac8fe2d2075e4f94a1aa12b9a82e8385','d20672d3fb2143b49df97ca251e4937f',0,'0',0,0,'admin','2016-11-30 23:39:59','admin','2016-11-30 23:39:59',0,'7792f4e4074749dc873959281b26cd1c'),('a88f276153e54542a7dc7fea1987437c','aedef24f2cd14d0888e1fed85d271ce7','e31596b5c7c242b0b79cecdb91cd6ee2','P-REOTEC2','瑞普产品测试2','P-REOTEC2','10','12','10','12','26','10','32','6','38','1.2','95','ade3b6c3e1e441098a4ed7734e822f86','47c5f6ef973c4c58989072a6f4bac1e7','42','',0,0,0,0,'823d15ddc2c2423da4284acc8d1e273a','e91d07e0cc8a451284d7fe32426a073f','120','c19355a691924072b9115d28126f6ef6','b2ca4d083e084e3793763e7e51d4a037',0,'0',0,0,'admin','2016-11-30 23:34:04','admin','2016-11-30 23:34:04',0,'0ad3858f8baa4b0a820b8edf580f9138'),('c3d5f3a4a2ce4d9f96d00d1f1bdd931e','a269cbcdf38341eab4dea64e2717f346','e31596b5c7c242b0b79cecdb91cd6ee2','S--PRODUCT1','展讯产品测试1','S--PRODUCTEST1','10','10','15','10','26','10','32','7','38','1.5','95','2b4576eb61f9476ba466abe939396031','624e14234d8b4936a63c3b68cd803aeb','42','',0,0,0,0,'2e1d303b6ce04c2788b4a0373de90666','8aa0fd9789d84442868f611bb384747a','120','ac8fe2d2075e4f94a1aa12b9a82e8385','d20672d3fb2143b49df97ca251e4937f',0,'0',0,0,'admin','2016-11-30 23:38:56','admin','2016-11-30 23:38:56',0,'7792f4e4074749dc873959281b26cd1c'),('e3f7a70bf1524c1bbdaddb7c8fb6c57d','aedef24f2cd14d0888e1fed85d271ce7','e31596b5c7c242b0b79cecdb91cd6ee2','P-REOTEC1','瑞普产品测试1','P-REOTECTEST1','10','10','10','10','26','10','32','8','38','0.001','95','ade3b6c3e1e441098a4ed7734e822f86','47c5f6ef973c4c58989072a6f4bac1e7','41','',0,0,0,0,'823d15ddc2c2423da4284acc8d1e273a','e91d07e0cc8a451284d7fe32426a073f','120','c19355a691924072b9115d28126f6ef6','b2ca4d083e084e3793763e7e51d4a037',0,'0',0,0,'admin','2016-11-30 23:33:08','admin','2016-11-30 23:33:08',0,'0ad3858f8baa4b0a820b8edf580f9138');

/*Table structure for table `tb_province` */

DROP TABLE IF EXISTS `tb_province`;

CREATE TABLE `tb_province` (
  `PROVINCE_ID` varchar(100) NOT NULL,
  `PROVINCE_CODE` varchar(255) DEFAULT NULL COMMENT '省/州代码',
  `PROVINCE_CN` varchar(255) DEFAULT NULL COMMENT '省/州名称',
  `COUNTRY_ID` varchar(255) DEFAULT NULL COMMENT '国家',
  `PROVINCE_EN` varchar(255) DEFAULT NULL COMMENT '省/州英文名称',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除标志',
  `SORT_BY` int(11) DEFAULT '2' COMMENT '排序字段',
  PRIMARY KEY (`PROVINCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_province` */

insert  into `tb_province`(`PROVINCE_ID`,`PROVINCE_CODE`,`PROVINCE_CN`,`COUNTRY_ID`,`PROVINCE_EN`,`MEMO`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`SORT_BY`) values ('0fee80963b7f4b7ead525d0cfe3fcdb8','020','广东省','b58fe5e289ce4cc48f5e0794cc5c6139','广东省','','admin','2016-11-01 21:00:59','admin','2016-11-01 21:00:59',0,2),('10','310000','上海市','1','Shanghai Shi',NULL,NULL,NULL,NULL,NULL,0,10),('11','320000','江苏省','1','Jiangsu Sheng',NULL,NULL,NULL,NULL,NULL,0,11),('12','330000','浙江省','1','Zhejiang Sheng',NULL,NULL,NULL,NULL,NULL,0,12),('13','340000','安徽省','1','Anhui Sheng',NULL,NULL,NULL,NULL,NULL,0,13),('14','350000','福建省','1','Fujian Sheng ',NULL,NULL,NULL,NULL,NULL,0,14),('15','360000','江西省','1','Jiangxi Sheng',NULL,NULL,NULL,NULL,NULL,0,15),('16','370000','山东省','1','Shandong Sheng ',NULL,NULL,NULL,NULL,NULL,0,16),('17','410000','河南省','1','Henan Sheng',NULL,NULL,NULL,NULL,NULL,0,17),('18','420000','湖北省','1','Hubei Sheng',NULL,NULL,NULL,NULL,NULL,0,18),('19','430000','湖南省','1','Hunan Sheng',NULL,NULL,NULL,NULL,NULL,0,19),('2','110000','北京市','1','Beijing Shi',NULL,NULL,NULL,NULL,NULL,0,2),('20','440000','广东省','1','Guangdong Sheng',NULL,NULL,NULL,NULL,NULL,0,20),('21','450000','广西壮族自治区','1','Guangxi Zhuangzu Zizhiqu',NULL,NULL,NULL,NULL,NULL,0,21),('22','460000','海南省','1','Hainan Sheng',NULL,NULL,NULL,NULL,NULL,0,22),('23','500000','重庆市','1','Chongqing Shi',NULL,NULL,NULL,NULL,NULL,0,23),('24','510000','四川省','1','Sichuan Sheng',NULL,NULL,NULL,NULL,NULL,0,24),('25','520000','贵州省','1','Guizhou Sheng',NULL,NULL,NULL,NULL,NULL,0,25),('26','530000','云南省','1','Yunnan Sheng',NULL,NULL,NULL,NULL,NULL,0,26),('27','540000','西藏自治区','1','Xizang Zizhiqu',NULL,NULL,NULL,NULL,NULL,0,27),('28','610000','陕西省','1','Shanxi Sheng ',NULL,NULL,NULL,NULL,NULL,0,28),('29','620000','甘肃省','1','Gansu Sheng',NULL,NULL,NULL,NULL,NULL,0,29),('3','120000','天津市','1','Tianjin Shi',NULL,NULL,NULL,NULL,NULL,0,3),('30','630000','青海省','1','Qinghai Sheng',NULL,NULL,NULL,NULL,NULL,0,30),('31','640000','宁夏回族自治区','1','Ningxia Huizu Zizhiqu',NULL,NULL,NULL,NULL,NULL,0,31),('32','650000','新疆维吾尔自治区','1','Xinjiang Uygur Zizhiqu',NULL,NULL,NULL,NULL,NULL,0,32),('4','130000','河北省','1','Hebei Sheng',NULL,NULL,NULL,NULL,NULL,0,4),('48ca382483104e00aaf24313a6cc69bc','11','212','1','11','11','admin','2016-10-31 22:21:53','admin','2016-10-31 22:21:53',0,2),('5','140000','山西省','1','Shanxi Sheng ',NULL,NULL,NULL,NULL,NULL,0,5),('6','150000','内蒙古自治区','1','Nei Mongol Zizhiqu',NULL,NULL,NULL,NULL,NULL,0,6),('60516b9f8cba4c0dba0a02004d9d1bc1','HK','香港','50a84679187f46dd93e18a8bb5903848','HONGKONG','','admin','2016-11-29 13:19:05','admin','2016-11-29 13:19:05',0,2),('7','210000','辽宁省','1','Liaoning Sheng',NULL,NULL,NULL,NULL,NULL,0,7),('8','220000','吉林省','1','Jilin Sheng',NULL,NULL,NULL,NULL,NULL,0,8),('9','230000','黑龙江省','1','Heilongjiang Sheng',NULL,NULL,NULL,NULL,NULL,0,9),('9bde5d15ae0a4607b8105f0a7cd4e454','021','上海市','b58fe5e289ce4cc48f5e0794cc5c6139','上海','','admin','2016-11-01 21:01:21','admin','2016-11-01 21:01:58',0,2),('b066d3edfa2c4a24a398ad620e6d5581','111','111','1','111','','admin','2016-10-31 22:21:22','admin','2016-10-31 22:21:22',0,2);

/*Table structure for table `tb_select` */

DROP TABLE IF EXISTS `tb_select`;

CREATE TABLE `tb_select` (
  `SELECT_ID` varchar(100) NOT NULL,
  `GROUP_KEY` varchar(255) DEFAULT NULL COMMENT '分组',
  `GROUP_NAME` varchar(255) DEFAULT NULL COMMENT '参数分组',
  `C_CODE` varchar(255) DEFAULT NULL COMMENT '参数编码',
  `C_VALUE` varchar(255) DEFAULT NULL COMMENT '参数名称',
  `C_DESC` varchar(255) DEFAULT NULL COMMENT '描述',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除标',
  `C_SORT` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`SELECT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_select` */

insert  into `tb_select`(`SELECT_ID`,`GROUP_KEY`,`GROUP_NAME`,`C_CODE`,`C_VALUE`,`C_DESC`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`C_SORT`) values ('1','LOCATOR_STATE','库位属性','1','正常',NULL,NULL,NULL,'0',1),('10','LOCATOR_USE','库位使用','6','理货站和组装工作区',NULL,NULL,NULL,'0',6),('100','ASSIGNED_STATE','分配状态','1','未分配',NULL,NULL,NULL,'0',1),('101','ASSIGNED_STATE','分配状态','2','部分分配',NULL,NULL,NULL,'0',2),('102','ASSIGNED_STATE','分配状态','3','全部分配',NULL,NULL,NULL,'0',3),('103','LOCATOR_PRIORITY_LEVEL','库位优先级','4','4',NULL,NULL,NULL,'0',4),('104','LOCATOR_PRIORITY_LEVEL','库位优先级','5','5',NULL,NULL,NULL,'0',5),('11','LOCATOR_TYPE','库位类型','1','货架',NULL,NULL,NULL,'0',1),('110','CURRENCY_TYPE','币种','RMB','RMB人民币',NULL,NULL,NULL,'0',1),('111','CURRENCY_TYPE','币种','HKD','HKD港币',NULL,NULL,NULL,'0',2),('112','CURRENCY_TYPE','币种','USD','USD美元',NULL,NULL,NULL,'0',3),('113','CURRENCY_TYPE','币种','EUR','EUR欧元',NULL,NULL,NULL,'0',4),('12','LOCATOR_TYPE','库位类型','2','地面平仓',NULL,NULL,NULL,'0',2),('120','RULE_STORAGE','库位指定规则','1','人工指定库位',NULL,NULL,NULL,'0',1),('121','RULE_STORAGE','库位指定规则','2','收获时计算库位',NULL,NULL,NULL,'0',2),('122','RULE_STORAGE','库位指定规则','3','码放时计算库位',NULL,NULL,NULL,'0',3),('125','RULE_BULK','体积重量计算方式','1','录入整票单证重量',NULL,NULL,NULL,'0',1),('126','RULE_BULK','体积重量计算方式','2','按产品手工录入',NULL,NULL,NULL,'0',2),('127','RULE_BULK','体积重量计算方式','3','按产品自动计算',NULL,NULL,NULL,'0',3),('13','LOCATOR_TYPE','库位类型','3','重力式货架',NULL,NULL,NULL,'0',3),('130','BOOlEAN_FLG','是否','2','2','','admin','2016-11-04 09:46:57','1',2),('131','BOOlEAN_FLG','是否','2','243','','admin','2016-11-04 09:47:13','0',3),('132','BOOlEAN_FLG','是否','1','11','','admin','2016-11-06 17:58:39','0',1),('133','LOADED_TYPE','装车状态','1','未装车','','admin','2016-11-04 01:47:07','0',1),('134','LOADED_TYPE','装车状态','2','部分装车','','admin','2016-11-04 01:47:07','0',2),('135','LOADED_TYPE','装车状态','3','全部装车','','admin','2016-11-04 01:47:07','0',3),('136','OUTSTOCK_WAY','发货方式','1','按拣货发货','','admin','2016-11-04 01:47:07','0',1),('137','OUTSTOCK_WAY','发货方式','2','按出货单发货','','admin','2016-11-04 01:47:07','0',2),('138','OUTSTOCK_WAY','发货方式','3','按配载单发货','','admin','2016-11-04 01:47:07','0',3),('139','PICK_STATE','拣货状态','1','未拣货','','admin','2016-11-04 01:47:07','0',1),('14','LOCATOR_TYPE','库位类型','4','窄巷道货架',NULL,NULL,NULL,'0',4),('140','PICK_STATE','拣货状态','2','部分拣货','','admin','2016-11-04 01:47:07','0',2),('141','PICK_STATE','拣货状态','3','全部拣货','','admin','2016-11-04 01:47:07','0',3),('142','DEPOT_TYPE','发货状态','1','未发货','','admin','2016-11-04 01:47:07','0',1),('143','DEPOT_TYPE','发货状态','2','部分发货','','admin','2016-11-04 01:47:07','0',2),('144','DEPOT_TYPE','发货状态','3','全部发货','','admin','2016-11-04 01:47:07','0',3),('145','OUTSTOCK_TYPE','出库类型','1','一般','','admin','2016-11-04 01:47:07','0',1),('146','OUTSTOCK_TYPE','出库类型','2','退货','','admin','2016-11-04 01:47:07','0',2),('147','CAR_TYPE','车型','small','小型车',NULL,NULL,NULL,'0',1),('148','CAR_TYPE','车型','middle','中型车',NULL,NULL,NULL,'0',2),('149','CAR_TYPE','车型','large','大型车',NULL,NULL,NULL,'0',3),('15','LOCATOR_UNIT','库位处理','1','其他',NULL,NULL,NULL,'0',1),('150','PACK_UNIT','包装单位','1','EA','',NULL,NULL,'0',1),('151','PACK_UNIT','包装单位','2','IP','',NULL,NULL,'0',2),('152','TP_HAULIER','承运人','1','AAAA',NULL,NULL,NULL,'0',1),('153','BATCH_PROPERTY','批次属性','1','入库日期','','','','0',1),('154','BATCH_PROPERTY','批次属性','2','Case Number','','','','0',2),('155','BATCH_PROPERTY','批次属性','3','入库单号','','','','0',3),('156','SORT_BY','排序','ASC','从小到大','','','','0',1),('157','SORT_BY','排序','DESC','从大到小','','','','0',2),('158','FREEZE_STATE','冻结状态','1','未冻结',NULL,NULL,NULL,'0',1),('159','FREEZE_STATE','冻结状态','2','部分冻结',NULL,NULL,NULL,'0',2),('16','LOCATOR_UNIT','库位处理','2','箱',NULL,NULL,NULL,'0',2),('160','FREEZE_STATE','冻结状态','3','全部冻结',NULL,NULL,NULL,'0',3),('161','CARGO_STATE','配载状态','1','未配载',NULL,NULL,NULL,'0',1),('162','CARGO_STATE','配载状态','2','部分配载',NULL,NULL,NULL,'0',2),('163','CARGO_STATE','配载状态','3','全部配载',NULL,NULL,NULL,'0',3),('164','PACK_UNIT','包装单位','3','CS','','admin','2016-11-30 23:29:21','0',3),('165','PACK_UNIT','包装单位','4','PL','','admin','2016-11-30 23:29:32','0',4),('166','PACK_UNIT','包装单位','5','OT','','admin','2016-11-30 23:29:45','0',5),('17','LOCATOR_UNIT','库位处理','3','托盘',NULL,NULL,NULL,'0',3),('18','TURNOVER_CYCLE','周转周期','1','快速周转',NULL,NULL,NULL,'0',1),('19','TURNOVER_CYCLE','周转周期','2','中速周转',NULL,NULL,NULL,'0',2),('2','LOCATOR_STATE','库位属性','2','占用',NULL,NULL,NULL,'0',2),('20','TURNOVER_CYCLE','周转周期','3','慢速中转',NULL,NULL,NULL,'0',3),('23','TOTAL_WEIGHT_UNIT','毛重单位','0','',NULL,NULL,NULL,'0',0),('24','TOTAL_WEIGHT_UNIT','毛重单位','1','其他',NULL,NULL,NULL,'0',1),('25','TOTAL_WEIGHT_UNIT','毛重单位','2','托盘',NULL,NULL,NULL,'0',2),('26','TOTAL_WEIGHT_UNIT','毛重单位','3','箱',NULL,NULL,NULL,'0',3),('27','TOTAL_WEIGHT_UNIT','毛重单位','4','内包装',NULL,NULL,NULL,'0',4),('28','TOTAL_WEIGHT_UNIT','毛重单位','5','EA',NULL,NULL,NULL,'0',5),('29','TOTAL_SUTTLE_UNIT','净重单位','0','',NULL,NULL,NULL,'0',0),('3','LOCATOR_STATE','库位属性','3','封存',NULL,NULL,NULL,'0',3),('30','TOTAL_SUTTLE_UNIT','净重单位','1','其他',NULL,NULL,NULL,'0',1),('31','TOTAL_SUTTLE_UNIT','净重单位','2','托盘',NULL,NULL,NULL,'0',2),('32','TOTAL_SUTTLE_UNIT','净重单位','3','箱',NULL,NULL,NULL,'0',3),('33','TOTAL_SUTTLE_UNIT','净重单位','4','内包装',NULL,NULL,NULL,'0',4),('34','TOTAL_SUTTLE_UNIT','净重单位','5','EA',NULL,NULL,NULL,'0',5),('35','VOLUME_UNIT','体积单位','0','',NULL,NULL,NULL,'0',0),('36','VOLUME_UNIT','体积单位','1','其他',NULL,NULL,NULL,'0',1),('37','VOLUME_UNIT','体积单位','2','托盘',NULL,NULL,NULL,'0',2),('38','VOLUME_UNIT','体积单位','3','箱',NULL,NULL,NULL,'0',3),('39','VOLUME_UNIT','体积单位','4','内包装',NULL,NULL,NULL,'0',4),('4','LOCATOR_STATE','库位属性','4','损坏',NULL,NULL,NULL,'0',4),('40','VOLUME_UNIT','体积单位','5','EA',NULL,NULL,NULL,'0',5),('41','FREEZE_REJECT_CODE','冻结/拒收代码','0','',NULL,NULL,NULL,'0',0),('42','FREEZE_REJECT_CODE','冻结/拒收代码','1','正常',NULL,NULL,NULL,'0',1),('43','FREEZE_REJECT_CODE','冻结/拒收代码','2','货物损坏',NULL,NULL,NULL,'0',2),('44','FREEZE_REJECT_CODE','冻结/拒收代码','3','过期产品',NULL,NULL,NULL,'0',3),('45','FREEZE_REJECT_CODE','冻结/拒收代码','4','库位非活动状态',NULL,NULL,NULL,'0',4),('46','FREEZE_REJECT_CODE','冻结/拒收代码','5','其他',NULL,NULL,NULL,'0',5),('47','FREEZE_REJECT_CODE','冻结/拒收代码','6','质检-上架',NULL,NULL,NULL,'0',6),('48','FREEZE_REJECT_CODE','冻结/拒收代码','7','上架-质检',NULL,NULL,NULL,'0',7),('49','PERIOD_TYPE','周期类型','1','生产日期',NULL,NULL,NULL,'0',1),('5','LOCATOR_USE','库位使用','1','存储库位',NULL,NULL,NULL,'0',1),('50','PERIOD_TYPE','周期类型','2','失效日期',NULL,NULL,NULL,'0',2),('51','PERIOD_TYPE','周期类型','0','',NULL,NULL,NULL,'0',0),('6','LOCATOR_USE','库位使用','2','过渡库位',NULL,NULL,NULL,'0',2),('60','ORDERBATCH_TYPE','订单/批次属性','1','入库单订单属性',NULL,NULL,NULL,'0',1),('61','ORDERBATCH_TYPE','订单/批次属性','2','出库单订单属性',NULL,NULL,NULL,'0',2),('62','ORDERBATCH_TYPE','订单/批次属性','3','入库单批次属性',NULL,NULL,NULL,'0',3),('63','ORDERBATCH_TYPE','订单/批次属性','4','出库单批次属性',NULL,NULL,NULL,'0',4),('7','LOCATOR_USE','库位使用','3','箱拣货库位',NULL,NULL,NULL,'0',3),('70','INSTOCK_TYPE','入库类型','1','一般',NULL,NULL,NULL,'0',1),('71','INSTOCK_TYPE','入库类型','2','退货',NULL,NULL,NULL,'0',2),('75','INSTOCK_STATE','收货状态','1','未收货',NULL,NULL,NULL,'0',1),('76','INSTOCK_STATE','收货状态','2','部分收货',NULL,NULL,NULL,'0',2),('77','INSTOCK_STATE','收货状态','3','全部收货',NULL,NULL,NULL,'0',3),('8','LOCATOR_USE','库位使用','4','件拣货库位',NULL,NULL,NULL,'0',4),('80','PUT_STATE','码放状态','1','未码放',NULL,NULL,NULL,'0',1),('81','PUT_STATE','码放状态','2','部分码放',NULL,NULL,NULL,'0',2),('82','PUT_STATE','码放状态','3','全部码放',NULL,NULL,NULL,'0',3),('9','LOCATOR_USE','库位使用','5','箱/件拣货库位',NULL,NULL,NULL,'0',5),('90','PRIORITY_LEVEL','优先级','1','1',NULL,NULL,NULL,'0',1),('91','PRIORITY_LEVEL','优先级','2','2',NULL,NULL,NULL,'0',2),('92','PRIORITY_LEVEL','优先级','3','3',NULL,NULL,NULL,'0',3),('93','PRIORITY_LEVEL','优先级','4','4',NULL,NULL,NULL,'0',4),('94','PRIORITY_LEVEL','优先级','5','5',NULL,NULL,NULL,'0',5),('95','PRODUCT_TYPE','货物类型','1','电子产品','',NULL,NULL,'0',1),('96','PRODUCT_TYPE','货物类型','2','快消产品','',NULL,NULL,'0',2),('97','LOCATOR_PRIORITY_LEVEL','库位优先级','1','1',NULL,NULL,NULL,'0',1),('98','LOCATOR_PRIORITY_LEVEL','库位优先级','2','2',NULL,NULL,NULL,'0',2),('99','LOCATOR_PRIORITY_LEVEL','库位优先级','3','3',NULL,NULL,NULL,'0',3);

DROP TABLE IF EXISTS `tb_box_rule`;
CREATE TABLE `tb_box_rule` (
   `CUSTOMER_ID` varchar(32) DEFAULT NULL,
   `MID_CODE` varchar(255) DEFAULT NULL,
   `MEMO` varchar(255) DEFAULT NULL,
   `CREATE_EMP` varchar(255) DEFAULT NULL,
   `CREATE_TM` varchar(255) DEFAULT NULL,
   `BOXRULE_ID` varchar(32) DEFAULT NULL
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*Table structure for table `tb_stock` */

DROP TABLE IF EXISTS `tb_stock`;

CREATE TABLE `tb_stock` (
  `STOCK_ID` varchar(100) NOT NULL,
  `STOCK_CODE` varchar(255) DEFAULT NULL COMMENT '仓库编码',
  `STOCK_NAME` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `STOCK_DSC` varchar(255) DEFAULT NULL COMMENT '仓库介绍',
  `CITY_ID` varchar(255) DEFAULT NULL COMMENT '城市名称',
  `STOCK_ADDR` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `HEAD_EMP` varchar(255) DEFAULT NULL COMMENT '负责人',
  `HEAD_MOBILE` varchar(255) DEFAULT NULL COMMENT '负责人电话',
  `HEAD_FAX` varchar(255) DEFAULT NULL COMMENT '负责人传真',
  `HEAD_MAIL` varchar(255) DEFAULT NULL COMMENT '负责人邮箱',
  `USE_FLG` int(11) NOT NULL COMMENT '启用',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除',
  PRIMARY KEY (`STOCK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stock` */

insert  into `tb_stock`(`STOCK_ID`,`STOCK_CODE`,`STOCK_NAME`,`STOCK_DSC`,`CITY_ID`,`STOCK_ADDR`,`MEMO`,`HEAD_EMP`,`HEAD_MOBILE`,`HEAD_FAX`,`HEAD_MAIL`,`USE_FLG`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('c92a38811e3647f692a0ee44af796af3','SZ001','深圳1号仓库','深圳仓库','55d6916bc90e4f8887e02bb1932eefc0','深圳','','','','','',1,'','2016-11-29 13:20:52','','2016-11-29 13:20:52',0),('e31596b5c7c242b0b79cecdb91cd6ee2','QW001','荃湾1号仓','荃湾一号仓','eebd4ab2905d4564a060786eb645fd17','荃湾','','','','','',1,'','2016-11-29 13:21:37','','2016-11-29 13:21:37',0);

/*Table structure for table `tb_stockasignrule` */

DROP TABLE IF EXISTS `tb_stockasignrule`;

CREATE TABLE `tb_stockasignrule` (
  `STOCKASIGNRULE_ID` varchar(100) NOT NULL,
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `STOCKASIGN_CODE` varchar(255) DEFAULT NULL COMMENT '库存分配规则编码',
  `STOCKASIGN_CN` varchar(255) DEFAULT NULL COMMENT '库存分配规则名称',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `IN_STOCK_DAY` varchar(255) DEFAULT NULL COMMENT '入库日期',
  `IN_STOCK_CYCLE` varchar(255) DEFAULT NULL COMMENT '先进先出',
  `PRODUCE_DAY` varchar(255) DEFAULT NULL COMMENT '生产日期',
  `PRODUCE_CYCLE` varchar(255) DEFAULT NULL COMMENT '先进先出',
  `PRODUCE_LEVEL` varchar(255) DEFAULT NULL,
  `CUSTOMER_ASIGN` varchar(255) DEFAULT NULL COMMENT '客户指定',
  `CUSTOMER_ASIGN_USE` varchar(255) DEFAULT NULL COMMENT '启用',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  PRIMARY KEY (`STOCKASIGNRULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stockasignrule` */

insert  into `tb_stockasignrule`(`STOCKASIGNRULE_ID`,`CUSTOMER_ID`,`STOCKASIGN_CODE`,`STOCKASIGN_CN`,`MEMO`,`IN_STOCK_DAY`,`IN_STOCK_CYCLE`,`PRODUCE_DAY`,`PRODUCE_CYCLE`,`PRODUCE_LEVEL`,`CUSTOMER_ASIGN`,`CUSTOMER_ASIGN_USE`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('b2ca4d083e084e3793763e7e51d4a037','aedef24f2cd14d0888e1fed85d271ce7','REOTEC','瑞普规则','','730','1','730','1',NULL,NULL,NULL,'','2016-11-30 20:42:25','','2016-11-30 20:42:25','0'),('d20672d3fb2143b49df97ca251e4937f','a269cbcdf38341eab4dea64e2717f346','SPREA','展讯分配规则','','730','1','730','1',NULL,NULL,NULL,'','2016-11-30 20:43:03','','2016-11-30 20:43:03','0');

/*Table structure for table `tb_stockmgrin` */

DROP TABLE IF EXISTS `tb_stockmgrin`;

CREATE TABLE `tb_stockmgrin` (
  `STOCKMGRIN_ID` varchar(100) NOT NULL,
  `INSTOCK_NO` varchar(255) DEFAULT NULL COMMENT '进货单编号入货单编号',
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `INSTOCK_TYPE` varchar(255) DEFAULT NULL COMMENT '入库类型',
  `PRE_INSTOCK_DATE` varchar(255) DEFAULT NULL COMMENT '预计入库日期',
  `REAL_INSTOCK_DATE` varchar(255) DEFAULT NULL COMMENT '实际入库日期',
  `INSTOCK_STATE` varchar(255) DEFAULT NULL COMMENT '收货状态',
  `PUT_STATE` varchar(255) DEFAULT NULL COMMENT '码放状态',
  `TOTAL_WEIGHT` varchar(255) DEFAULT NULL COMMENT '总毛重',
  `TOTAL_SUTTLE` varchar(255) DEFAULT NULL COMMENT '总净重',
  `TOTAL_VOLUME` varchar(255) DEFAULT NULL COMMENT '总体积',
  `TOTAL_PRICE` varchar(255) DEFAULT NULL COMMENT '总价',
  `TOTAL_EA` varchar(255) DEFAULT NULL COMMENT '总期望EA数',
  `PRIORITY_LEVEL` varchar(255) DEFAULT NULL COMMENT '优先级',
  `ORDER_NO` varchar(255) DEFAULT NULL COMMENT '订单号',
  `COST_STATE` varchar(255) DEFAULT NULL COMMENT '费用录入已完成',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  `ASSIGNED_STATE` varchar(255) DEFAULT NULL COMMENT '分配状态',
  PRIMARY KEY (`STOCKMGRIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stockmgrin` */

insert  into `tb_stockmgrin`(`STOCKMGRIN_ID`,`INSTOCK_NO`,`CUSTOMER_ID`,`INSTOCK_TYPE`,`PRE_INSTOCK_DATE`,`REAL_INSTOCK_DATE`,`INSTOCK_STATE`,`PUT_STATE`,`TOTAL_WEIGHT`,`TOTAL_SUTTLE`,`TOTAL_VOLUME`,`TOTAL_PRICE`,`TOTAL_EA`,`PRIORITY_LEVEL`,`ORDER_NO`,`COST_STATE`,`MEMO`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`ASSIGNED_STATE`) values ('4046684b69774745a204861197ad98db','REOTEC-R-1611120000','2e9af32be19849babcf34efacef04d0d','70','2016-11-12','2016-11-11','1','1','11','','11','','','90','',NULL,'','admin','2016-11-12 12:00:39','admin','2016-11-12 12:00:39','1','1'),('7eba2d2695404cbaa59d2a870214b7b7','REOTEC-R-1611120001','2e9af32be19849babcf34efacef04d0d','70','2016-11-13','2016-11-14','1','1','2','5','','444','','90','',NULL,'44444','admin','2016-11-12 15:00:00','admin','2016-11-12 15:00:00','0','1'),('8aba490c35084c35959a1d43e17ecd2a','111-R-161101000000','1b9422da7a9f43058ed28abb8f7ac944',NULL,NULL,NULL,'1','1','','','','',NULL,NULL,NULL,NULL,'','admin','2016-11-01 22:43:41','admin','2016-11-01 22:43:41','1','1');

/*Table structure for table `tb_stockmgrin_dtl` */

DROP TABLE IF EXISTS `tb_stockmgrin_dtl`;

CREATE TABLE `tb_stockmgrin_dtl` (
  `STOCKDTLMGRIN_ID` varchar(100) NOT NULL,
  `STOCKMGRIN_ID` varchar(255) DEFAULT NULL COMMENT '主表ID',
  `ROW_NO` varchar(255) DEFAULT NULL COMMENT '行号',
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `PRODUCT_ID` varchar(255) DEFAULT NULL COMMENT '产品',
  `PRODUCT_TYPE` varchar(255) DEFAULT NULL COMMENT '产品类型',
  `PACK_RULE` varchar(255) DEFAULT NULL COMMENT '包装规则',
  `PACK_UNIT` varchar(255) DEFAULT NULL COMMENT '包装单位',
  `OT_EA` varchar(255) DEFAULT NULL COMMENT '其他期望数',
  `OT_RATIO` varchar(255) DEFAULT NULL COMMENT '其他比例',
  `PALLET_EA` varchar(255) DEFAULT NULL COMMENT '托盘期望数',
  `PALLET_RATIO` varchar(255) DEFAULT NULL COMMENT '托盘比例',
  `BOX_EA` varchar(255) DEFAULT NULL COMMENT '箱期望数',
  `BOX_RATIO` varchar(255) DEFAULT NULL COMMENT '箱比例',
  `INPACK_EA` varchar(255) DEFAULT NULL COMMENT '内期望数',
  `INPACK_RATIO` varchar(255) DEFAULT NULL COMMENT '内比例',
  `EA_EA` varchar(255) DEFAULT NULL COMMENT 'EA期望数',
  `EA_RATIO` varchar(255) DEFAULT NULL COMMENT 'EA比例',
  `LONG_UT` varchar(255) DEFAULT NULL COMMENT '长CM',
  `WIDE_UT` varchar(255) DEFAULT NULL COMMENT '宽CM',
  `HIGH_UT` varchar(255) DEFAULT NULL COMMENT '高CM',
  `UNIT_VOLUME` varchar(255) DEFAULT NULL COMMENT '单位体积',
  `TOTAL_VOLUME` varchar(255) DEFAULT NULL COMMENT '体积',
  `TOTAL_WEIGHT` varchar(255) DEFAULT NULL COMMENT '毛重',
  `TOTAL_SUTTLE` varchar(255) DEFAULT NULL COMMENT '净重',
  `TOTAL_PRICE` varchar(255) DEFAULT NULL COMMENT '总价',
  `UNIT_PRICE` varchar(255) DEFAULT NULL COMMENT '单价',
  `CURRENCY_TYPE` varchar(255) DEFAULT NULL COMMENT '币种',
  `STORAGE_ID` varchar(255) DEFAULT NULL COMMENT '收货库区',
  `LOCATOR_ID` varchar(255) DEFAULT NULL COMMENT '收货库位',
  `CAR_TYPE` varchar(255) DEFAULT NULL COMMENT '车型',
  `CAR_PLATE` varchar(255) DEFAULT NULL COMMENT '车牌号',
  `SEAL_NO` varchar(255) DEFAULT NULL COMMENT '封条号',
  `TP_HAULIER` varchar(255) DEFAULT NULL COMMENT '承运人',
  `FREEZE_CODE` varchar(255) DEFAULT NULL COMMENT '冻结拒收代码',
  `MGR_SERIAL` varchar(255) DEFAULT NULL COMMENT '管理序列号',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `IMP_FLG` varchar(255) DEFAULT NULL COMMENT '导入',
  `RECEIPT_STATE` varchar(255) DEFAULT NULL COMMENT '收货状态',
  `DISTR_STATE` varchar(255) DEFAULT NULL COMMENT '分配状态',
  `PUT_STATUS` varchar(255) DEFAULT NULL COMMENT '码放状态',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  PRIMARY KEY (`STOCKDTLMGRIN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stockmgrin_dtl` */

insert  into `tb_stockmgrin_dtl`(`STOCKDTLMGRIN_ID`,`STOCKMGRIN_ID`,`ROW_NO`,`CUSTOMER_ID`,`PRODUCT_ID`,`PRODUCT_TYPE`,`PACK_RULE`,`PACK_UNIT`,`OT_EA`,`OT_RATIO`,`PALLET_EA`,`PALLET_RATIO`,`BOX_EA`,`BOX_RATIO`,`INPACK_EA`,`INPACK_RATIO`,`EA_EA`,`EA_RATIO`,`LONG_UT`,`WIDE_UT`,`HIGH_UT`,`UNIT_VOLUME`,`TOTAL_VOLUME`,`TOTAL_WEIGHT`,`TOTAL_SUTTLE`,`TOTAL_PRICE`,`UNIT_PRICE`,`CURRENCY_TYPE`,`STORAGE_ID`,`LOCATOR_ID`,`CAR_TYPE`,`CAR_PLATE`,`SEAL_NO`,`TP_HAULIER`,`FREEZE_CODE`,`MGR_SERIAL`,`MEMO`,`IMP_FLG`,`RECEIPT_STATE`,`DISTR_STATE`,`PUT_STATUS`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('0df24dac56534118b5b9871bbda63110','7eba2d2695404cbaa59d2a870214b7b7','1','2e9af32be19849babcf34efacef04d0d','b5e9d905828640bf8d7e70f73987fea3','95',NULL,'150','4','','5','','3','','5','','1','','1','2','3','6','','2','5','444','4','110','d80df0e9e82e4990ad8d3e3362264453','16666a0b425d47c089eb22187e992068','147','3333','444','3444','444','444','44444',NULL,NULL,NULL,NULL,'admin','2016-11-12 23:17:13','admin','2016-11-12 23:17:13','0');

/*Table structure for table `tb_stockmgrin_property` */

DROP TABLE IF EXISTS `tb_stockmgrin_property`;

CREATE TABLE `tb_stockmgrin_property` (
  `P_ID` varchar(255) NOT NULL,
  `STOCKMGRIN_ID` varchar(255) DEFAULT NULL,
  `STOCKDTLMGRIN_ID` varchar(255) DEFAULT NULL,
  `ORDERBATCH_TYPE` varchar(255) DEFAULT NULL,
  `TIME_ONE` varchar(255) DEFAULT NULL,
  `TIME_TWO` varchar(255) DEFAULT NULL,
  `TIME_THR` varchar(255) DEFAULT NULL,
  `TIME_FOU` varchar(255) DEFAULT NULL,
  `TIME_FIV` varchar(255) DEFAULT NULL,
  `TIME_SIX` varchar(255) DEFAULT NULL,
  `TIME_SEV` varchar(255) DEFAULT NULL,
  `TIME_EIG` varchar(255) DEFAULT NULL,
  `TIME_NIG` varchar(255) DEFAULT NULL,
  `TIME_TEN` varchar(255) DEFAULT NULL,
  `NUM_ONE` varchar(255) DEFAULT NULL,
  `NUM_TWO` varchar(255) DEFAULT NULL,
  `NUM_THR` varchar(255) DEFAULT NULL,
  `NUM_FOU` varchar(255) DEFAULT NULL,
  `NUM_FIV` varchar(255) DEFAULT NULL,
  `NUM_SIX` varchar(255) DEFAULT NULL,
  `NUM_SEV` varchar(255) DEFAULT NULL,
  `NUM_EIG` varchar(255) DEFAULT NULL,
  `NUM_NIG` varchar(255) DEFAULT NULL,
  `NUM_TEN` varchar(255) DEFAULT NULL,
  `TXT_ONE` varchar(255) DEFAULT NULL,
  `TXT_TWO` varchar(255) DEFAULT NULL,
  `TXT_THR` varchar(255) DEFAULT NULL,
  `TXT_FOU` varchar(255) DEFAULT NULL,
  `TXT_FIV` varchar(255) DEFAULT NULL,
  `TXT_SIX` varchar(255) DEFAULT NULL,
  `TXT_SEV` varchar(255) DEFAULT NULL,
  `TXT_EIG` varchar(255) DEFAULT NULL,
  `TXT_NIG` varchar(255) DEFAULT NULL,
  `TXT_TEN` varchar(255) DEFAULT NULL,
  `TXT_ELEVEN` varchar(255) DEFAULT NULL,
  `TXT_TWELVE` varchar(255) DEFAULT NULL,
  `TXT_THIRT` varchar(255) DEFAULT NULL,
  `TXT_FOURT` varchar(255) DEFAULT NULL,
  `TXT_FIFT` varchar(255) DEFAULT NULL,
  `TXT_SIXT` varchar(255) DEFAULT NULL,
  `TXT_SEVENT` varchar(255) DEFAULT NULL,
  `TXT_EIGHT` varchar(255) DEFAULT NULL,
  `TXT_NINET` varchar(255) DEFAULT NULL,
  `TXT_TWENT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stockmgrin_property` */

insert  into `tb_stockmgrin_property`(`P_ID`,`STOCKMGRIN_ID`,`STOCKDTLMGRIN_ID`,`ORDERBATCH_TYPE`,`TIME_ONE`,`TIME_TWO`,`TIME_THR`,`TIME_FOU`,`TIME_FIV`,`TIME_SIX`,`TIME_SEV`,`TIME_EIG`,`TIME_NIG`,`TIME_TEN`,`NUM_ONE`,`NUM_TWO`,`NUM_THR`,`NUM_FOU`,`NUM_FIV`,`NUM_SIX`,`NUM_SEV`,`NUM_EIG`,`NUM_NIG`,`NUM_TEN`,`TXT_ONE`,`TXT_TWO`,`TXT_THR`,`TXT_FOU`,`TXT_FIV`,`TXT_SIX`,`TXT_SEV`,`TXT_EIG`,`TXT_NIG`,`TXT_TEN`,`TXT_ELEVEN`,`TXT_TWELVE`,`TXT_THIRT`,`TXT_FOURT`,`TXT_FIFT`,`TXT_SIXT`,`TXT_SEVENT`,`TXT_EIGHT`,`TXT_NINET`,`TXT_TWENT`) values ('0ac69448486b49b0acf3f2eb73a46ca0','8aba490c35084c35959a1d43e17ecd2a','204f9fbdf2ae46d0b4bfd168f92c2f1e','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('12ebed11c1fe47aea54fcff8840200ce','7eba2d2695404cbaa59d2a870214b7b7','5eea495d8b6544b4b7a2b0c305dc95dc','62','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('16246f298c1942e286c9f7a32206fcf4','7eba2d2695404cbaa59d2a870214b7b7','b5595cd099504b49bf726dd75133879a','62','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('1b259decdc1845a185f3d218cb0c741b','8aba490c35084c35959a1d43e17ecd2a','a35764d35605487389e3cbb20fbe25a4','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('2b534f5b11c14fa9960fb8219543072b','7eba2d2695404cbaa59d2a870214b7b7','039fcc6d7c37428887bacc6d589c70e7','62','2016-11-09','2016-11-09','','','','','','','','','1','','','2','','','','','','','','333','4444','','','','','','','','','','','','','','','','',''),('3192338873ed47509ed23719c7a7c4e5','8aba490c35084c35959a1d43e17ecd2a','5e1e596751704fc78b320986e09f1679','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('3ffc13c4d84a452f9cae91b6a54f5352','8aba490c35084c35959a1d43e17ecd2a','6e94197749bc41ef97736cdb5b6790c0','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('40603de4313d48f9b99303244c7c5c56','7eba2d2695404cbaa59d2a870214b7b7','7d881191d0e74c37ab58c8260eaa9931','62','2016-11-10','2016-11-11','','','','','','','','','1','','','2','','','','','','','','34','5','','','','','','','','','','','','','','','','',''),('411c51ece6454a3aaa09a0fc9699ecb6','8aba490c35084c35959a1d43e17ecd2a','c843bdedd9c846d4a0836039772ced0b','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('42892068dbef4033a8c6e53e9f8a4529','8aba490c35084c35959a1d43e17ecd2a',NULL,'1','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('4a5a192b806b426483a83595b1c8e42d','7eba2d2695404cbaa59d2a870214b7b7','5cb88daa8af54ab896ef170c8f2ecd4b','62','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('531871c8399f42e08e0823bb9e3cd660','8aba490c35084c35959a1d43e17ecd2a','b406bc4ac3ef414dbfcbe34b5c5ca080','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('573897e2259d4378bf881f89cb5dbc07','8aba490c35084c35959a1d43e17ecd2a','f2a3c30350884827a8174e505a9404e7','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('635b64d1c7764700889bf05ebb70a288','7eba2d2695404cbaa59d2a870214b7b7','a7573b1c510946c5b1d8a546135d93a7','62','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('6c5add4dcd004a29a6a222c0e8209175','8aba490c35084c35959a1d43e17ecd2a','8217ee839cd7466db4a4e5e2c2e28d8e','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('76959872f895456ba5d1996c431ecee1','8aba490c35084c35959a1d43e17ecd2a','5831159554c74a80b54d65a79eb16de5','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('79fd02139b914e88ae969029ddec34ea','7eba2d2695404cbaa59d2a870214b7b7','00898bc9c0b14855989176809b4bbf13','62','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('7d7e6c8659534874b4f0e24c678c88ae','8aba490c35084c35959a1d43e17ecd2a','aaa43409b82c44d49946515f742a04ae','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('85eaffbd5e8b4ba1ab8d10af6f0a56aa','8aba490c35084c35959a1d43e17ecd2a','350c7bb0fe56476cb911e9a79d5f3dfc','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('8c304e78f9714eea9e6474462329ba87','4046684b69774745a204861197ad98db',NULL,NULL,'','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('903841fa4e0241429529afa7d867428d','8aba490c35084c35959a1d43e17ecd2a','f7ed57f22c6645f9aa8ba097d1110658','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('b47ac1ce63ec4a1587c877dd87639c7e','8aba490c35084c35959a1d43e17ecd2a','884d8c76bb7443a98433f9e9236d6661','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('b6d1fdbe60af43f1bcbf5d0a4d1bfac0','7eba2d2695404cbaa59d2a870214b7b7','0df24dac56534118b5b9871bbda63110','62','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('c08151aa27f5435e9c80306de543357a','8aba490c35084c35959a1d43e17ecd2a','bb33121c8c4547ffbd495e0995fd4cf2','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('c75742a12c0e44809bc98a0dbf790dcc','8aba490c35084c35959a1d43e17ecd2a','90a3d8b8803c492d841333e8691aaf46','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('d11d3d1129034983b0b84c4387bd7b0e','8aba490c35084c35959a1d43e17ecd2a','83fc7b95e4c9429a960e45a9ab143596','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('e3cddd7fefe1479b81ba30f6f267d034','8aba490c35084c35959a1d43e17ecd2a','e939e4b4903e46538c15a56b27f91297','3','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''),('e6518496dca04440b59bb93f1df4bdfd','7eba2d2695404cbaa59d2a870214b7b7',NULL,'60','2016-11-10','','','','','','','','','','2222','','','','','','','','','','12121','','','','','','','','','','','','','','','','','','','');

/*Table structure for table `tb_stockmgrout` */

DROP TABLE IF EXISTS `tb_stockmgrout`;

CREATE TABLE `tb_stockmgrout` (
  `STOCKMGROUT_ID` varchar(100) NOT NULL,
  `OUTSTOCK_NO` varchar(255) DEFAULT NULL COMMENT '发货单号',
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `OUTSTOCK_TYPE` varchar(255) DEFAULT NULL COMMENT '出库类型',
  `PRE_OUT_DATE` varchar(255) DEFAULT NULL COMMENT '预计发货日期',
  `REAL_OUT_DATE` varchar(255) DEFAULT NULL COMMENT '实际发货日期',
  `OUTSTOCK_WAY` varchar(255) DEFAULT NULL COMMENT '发货方式',
  `DEPOT_TYPE` varchar(255) DEFAULT NULL COMMENT '发货状态',
  `PICK_STATE` varchar(255) DEFAULT NULL COMMENT '拣货状态',
  `TOTAL_WEIGHT` varchar(255) DEFAULT NULL COMMENT '毛重',
  `LOADED_TYPE` varchar(255) DEFAULT NULL COMMENT '装车状态',
  `TOTAL_SUTTLE` varchar(255) DEFAULT NULL COMMENT '净重',
  `TOTAL_VOLUME` varchar(255) DEFAULT NULL COMMENT '体积',
  `TOTAL_PRICE` varchar(255) DEFAULT NULL COMMENT '总价',
  `TOTAL_EA` varchar(255) DEFAULT NULL COMMENT '总期望EA数',
  `PRIORITY_LEVEL` varchar(255) DEFAULT NULL COMMENT '优先级',
  `ORDER_NO` varchar(255) DEFAULT NULL COMMENT '订单号',
  `COST_STATE` varchar(255) DEFAULT NULL COMMENT '费用录入已完成',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  `ASSIGNED_STATE` varchar(255) DEFAULT NULL COMMENT '分配状态',
  PRIMARY KEY (`STOCKMGROUT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stockmgrout` */

insert  into `tb_stockmgrout`(`STOCKMGROUT_ID`,`OUTSTOCK_NO`,`CUSTOMER_ID`,`OUTSTOCK_TYPE`,`PRE_OUT_DATE`,`REAL_OUT_DATE`,`OUTSTOCK_WAY`,`DEPOT_TYPE`,`PICK_STATE`,`TOTAL_WEIGHT`,`LOADED_TYPE`,`TOTAL_SUTTLE`,`TOTAL_VOLUME`,`TOTAL_PRICE`,`TOTAL_EA`,`PRIORITY_LEVEL`,`ORDER_NO`,`COST_STATE`,`MEMO`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`ASSIGNED_STATE`) values ('35accc838873415eb6275596749faeb3','REOTEC-S-1611110019','2e9af32be19849babcf34efacef04d0d','145','2016-11-11',NULL,'136','142','139','',NULL,'','','',NULL,'90','122',NULL,'','admin','2016-11-11 19:35:14','admin','2016-11-11 19:35:35','0','100');

/*Table structure for table `tb_stockmgrout_dtl` */

DROP TABLE IF EXISTS `tb_stockmgrout_dtl`;

CREATE TABLE `tb_stockmgrout_dtl` (
  `STOCKDTLMGROUT_ID` varchar(100) NOT NULL,
  `STOCKMGROUT_ID` varchar(255) DEFAULT NULL COMMENT '主表ID',
  `ROW_NO` varchar(255) DEFAULT NULL COMMENT '行号',
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `PRODUCT_ID` varchar(255) DEFAULT NULL COMMENT '产品',
  `PRODUCT_TYPE` varchar(255) DEFAULT NULL COMMENT '产品类型',
  `PACK_RULE` varchar(255) DEFAULT NULL COMMENT '包装规则',
  `PACK_UNIT` varchar(255) DEFAULT NULL COMMENT '包装单位',
  `OT_EA` varchar(255) DEFAULT NULL COMMENT '其他期望数',
  `OT_RATIO` varchar(255) DEFAULT NULL COMMENT '其他比例',
  `PALLET_EA` varchar(255) DEFAULT NULL COMMENT '托盘期望数',
  `PALLET_RATIO` varchar(255) DEFAULT NULL COMMENT '托盘比例',
  `BOX_EA` varchar(255) DEFAULT NULL COMMENT '箱期望数',
  `BOX_RATIO` varchar(255) DEFAULT NULL COMMENT '箱比例',
  `INPACK_EA` varchar(255) DEFAULT NULL COMMENT '内期望数',
  `INPACK_RATIO` varchar(255) DEFAULT NULL COMMENT '内比例',
  `EA_EA` varchar(255) DEFAULT NULL COMMENT 'EA期望数',
  `EA_RATIO` varchar(255) DEFAULT NULL COMMENT 'EA比例',
  `LONG_UT` varchar(255) DEFAULT NULL COMMENT '长CM',
  `WIDE_UT` varchar(255) DEFAULT NULL COMMENT '宽CM',
  `HIGH_UT` varchar(255) DEFAULT NULL COMMENT '高CM',
  `UNIT_VOLUME` varchar(255) DEFAULT NULL COMMENT '单位体积',
  `TOTAL_VOLUME` varchar(255) DEFAULT NULL COMMENT '体积',
  `TOTAL_WEIGHT` varchar(255) DEFAULT NULL COMMENT '毛重',
  `TOTAL_SUTTLE` varchar(255) DEFAULT NULL COMMENT '净重',
  `TOTAL_PRICE` varchar(255) DEFAULT NULL COMMENT '总价',
  `UNIT_PRICE` varchar(255) DEFAULT NULL COMMENT '单价',
  `CURRENCY_TYPE` varchar(255) DEFAULT NULL COMMENT '币种',
  `STORAGE_ID` varchar(255) DEFAULT NULL COMMENT '收货库区',
  `LOCATOR_ID` varchar(255) DEFAULT NULL COMMENT '收货库位',
  `CAR_TYPE` varchar(255) DEFAULT NULL COMMENT '车型',
  `CAR_PLATE` varchar(255) DEFAULT NULL COMMENT '车牌号',
  `SEAL_NO` varchar(255) DEFAULT NULL COMMENT '封条号',
  `TP_HAULIER` varchar(255) DEFAULT NULL COMMENT '承运人',
  `FREEZE_CODE` varchar(255) DEFAULT NULL COMMENT '冻结拒收代码',
  `MGR_SERIAL` varchar(255) DEFAULT NULL COMMENT '管理序列号',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `IMP_FLG` varchar(255) DEFAULT NULL COMMENT '导入',
  `RECEIPT_STATE` varchar(255) DEFAULT NULL COMMENT '收货状态',
  `DISTR_STATE` varchar(255) DEFAULT NULL COMMENT '分配状态',
  `PUT_STATUS` varchar(255) DEFAULT NULL COMMENT '码放状态',
  `RULE_ASSIGNED` varchar(255) DEFAULT NULL COMMENT '分配规则',
  `RULE_STOCKTURN` varchar(255) DEFAULT NULL COMMENT '库存周转规则',
  `RULE_PICK` varchar(255) DEFAULT NULL COMMENT '拣货规则',
  `SEND_EA` varchar(128) DEFAULT NULL COMMENT '发货EA数',
  `PREPLAN_EA` varchar(128) DEFAULT NULL COMMENT '预配EA数',
  `ASSIGNED_EA` varchar(128) DEFAULT NULL COMMENT '分配EA数',
  `PICK_EA` varchar(128) DEFAULT NULL COMMENT '拣货EA数',
  `LOADED_EA` varchar(128) DEFAULT NULL COMMENT '装车EA数',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  PRIMARY KEY (`STOCKDTLMGROUT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stockmgrout_dtl` */

insert  into `tb_stockmgrout_dtl`(`STOCKDTLMGROUT_ID`,`STOCKMGROUT_ID`,`ROW_NO`,`CUSTOMER_ID`,`PRODUCT_ID`,`PRODUCT_TYPE`,`PACK_RULE`,`PACK_UNIT`,`OT_EA`,`OT_RATIO`,`PALLET_EA`,`PALLET_RATIO`,`BOX_EA`,`BOX_RATIO`,`INPACK_EA`,`INPACK_RATIO`,`EA_EA`,`EA_RATIO`,`LONG_UT`,`WIDE_UT`,`HIGH_UT`,`UNIT_VOLUME`,`TOTAL_VOLUME`,`TOTAL_WEIGHT`,`TOTAL_SUTTLE`,`TOTAL_PRICE`,`UNIT_PRICE`,`CURRENCY_TYPE`,`STORAGE_ID`,`LOCATOR_ID`,`CAR_TYPE`,`CAR_PLATE`,`SEAL_NO`,`TP_HAULIER`,`FREEZE_CODE`,`MGR_SERIAL`,`MEMO`,`IMP_FLG`,`RECEIPT_STATE`,`DISTR_STATE`,`PUT_STATUS`,`RULE_ASSIGNED`,`RULE_STOCKTURN`,`RULE_PICK`,`SEND_EA`,`PREPLAN_EA`,`ASSIGNED_EA`,`PICK_EA`,`LOADED_EA`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('c7b94348496948a0bda03412c3c351aa','35accc838873415eb6275596749faeb3','1','2e9af32be19849babcf34efacef04d0d','122','222','','','','','','','','','','','','',NULL,NULL,NULL,NULL,'','','','','','','','','','','','',NULL,'','',NULL,NULL,NULL,NULL,'','','','','','','','','admin','2016-11-11 19:39:05','admin','2016-11-11 19:44:50','0');

/*Table structure for table `tb_stockmgrout_property` */

DROP TABLE IF EXISTS `tb_stockmgrout_property`;

CREATE TABLE `tb_stockmgrout_property` (
  `P_ID` varchar(255) NOT NULL,
  `STOCKMGROUT_ID` varchar(255) DEFAULT NULL,
  `STOCKDTLMGROUT_ID` varchar(255) DEFAULT NULL,
  `ORDERBATCH_TYPE` varchar(255) DEFAULT NULL,
  `TIME_ONE` varchar(255) DEFAULT NULL,
  `TIME_TWO` varchar(255) DEFAULT NULL,
  `TIME_THR` varchar(255) DEFAULT NULL,
  `TIME_FOU` varchar(255) DEFAULT NULL,
  `TIME_FIV` varchar(255) DEFAULT NULL,
  `TIME_SIX` varchar(255) DEFAULT NULL,
  `TIME_SEV` varchar(255) DEFAULT NULL,
  `TIME_EIG` varchar(255) DEFAULT NULL,
  `TIME_NIG` varchar(255) DEFAULT NULL,
  `TIME_TEN` varchar(255) DEFAULT NULL,
  `NUM_ONE` varchar(255) DEFAULT NULL,
  `NUM_TWO` varchar(255) DEFAULT NULL,
  `NUM_THR` varchar(255) DEFAULT NULL,
  `NUM_FOU` varchar(255) DEFAULT NULL,
  `NUM_FIV` varchar(255) DEFAULT NULL,
  `NUM_SIX` varchar(255) DEFAULT NULL,
  `NUM_SEV` varchar(255) DEFAULT NULL,
  `NUM_EIG` varchar(255) DEFAULT NULL,
  `NUM_NIG` varchar(255) DEFAULT NULL,
  `NUM_TEN` varchar(255) DEFAULT NULL,
  `TXT_ONE` varchar(255) DEFAULT NULL,
  `TXT_TWO` varchar(255) DEFAULT NULL,
  `TXT_THR` varchar(255) DEFAULT NULL,
  `TXT_FOU` varchar(255) DEFAULT NULL,
  `TXT_FIV` varchar(255) DEFAULT NULL,
  `TXT_SIX` varchar(255) DEFAULT NULL,
  `TXT_SEV` varchar(255) DEFAULT NULL,
  `TXT_EIG` varchar(255) DEFAULT NULL,
  `TXT_NIG` varchar(255) DEFAULT NULL,
  `TXT_TEN` varchar(255) DEFAULT NULL,
  `TXT_ELEVEN` varchar(255) DEFAULT NULL,
  `TXT_TWELVE` varchar(255) DEFAULT NULL,
  `TXT_THIRT` varchar(255) DEFAULT NULL,
  `TXT_FOURT` varchar(255) DEFAULT NULL,
  `TXT_FIFT` varchar(255) DEFAULT NULL,
  `TXT_SIXT` varchar(255) DEFAULT NULL,
  `TXT_SEVENT` varchar(255) DEFAULT NULL,
  `TXT_EIGHT` varchar(255) DEFAULT NULL,
  `TXT_NINET` varchar(255) DEFAULT NULL,
  `TXT_TWENT` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`P_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stockmgrout_property` */

insert  into `tb_stockmgrout_property`(`P_ID`,`STOCKMGROUT_ID`,`STOCKDTLMGROUT_ID`,`ORDERBATCH_TYPE`,`TIME_ONE`,`TIME_TWO`,`TIME_THR`,`TIME_FOU`,`TIME_FIV`,`TIME_SIX`,`TIME_SEV`,`TIME_EIG`,`TIME_NIG`,`TIME_TEN`,`NUM_ONE`,`NUM_TWO`,`NUM_THR`,`NUM_FOU`,`NUM_FIV`,`NUM_SIX`,`NUM_SEV`,`NUM_EIG`,`NUM_NIG`,`NUM_TEN`,`TXT_ONE`,`TXT_TWO`,`TXT_THR`,`TXT_FOU`,`TXT_FIV`,`TXT_SIX`,`TXT_SEV`,`TXT_EIG`,`TXT_NIG`,`TXT_TEN`,`TXT_ELEVEN`,`TXT_TWELVE`,`TXT_THIRT`,`TXT_FOURT`,`TXT_FIFT`,`TXT_SIXT`,`TXT_SEVENT`,`TXT_EIGHT`,`TXT_NINET`,`TXT_TWENT`) values ('092e02a386d84cfca8bd428526790859','35accc838873415eb6275596749faeb3',NULL,'61','2016-11-11','','','','','','','','','','11','','','','','','','','','','22','','','','','','','','','','','','','','','','','','',''),('ae6f660e3e43443ba287d41b3f1ea714','35accc838873415eb6275596749faeb3','c7b94348496948a0bda03412c3c351aa','63','2016-11-11','','','','','','','','','','2255','','','','','','','','','','','3366','','','','','','','','','','','','','','','','','','');

/*Table structure for table `tb_stockturn` */

DROP TABLE IF EXISTS `tb_stockturn`;

CREATE TABLE `tb_stockturn` (
  `STOCKTURN_ID` varchar(100) NOT NULL,
  `STOCKTURN_CODE` varchar(255) DEFAULT NULL COMMENT '库存周转规则代码',
  `STOCKTURN_CN` varchar(255) DEFAULT NULL COMMENT '库存周转规则描述',
  `TIME_DISTANCE` varchar(255) DEFAULT NULL COMMENT '时间差(批次属性01-03有效)',
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `PRIORITY_LEVEL` varchar(255) DEFAULT NULL COMMENT '优先级',
  `BATCH_PROPERTY` varchar(255) DEFAULT NULL COMMENT '批次属性',
  `SORT_BY` varchar(255) DEFAULT NULL COMMENT '排序',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除标志',
  PRIMARY KEY (`STOCKTURN_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_stockturn` */

insert  into `tb_stockturn`(`STOCKTURN_ID`,`STOCKTURN_CODE`,`STOCKTURN_CN`,`TIME_DISTANCE`,`CUSTOMER_ID`,`PRIORITY_LEVEL`,`BATCH_PROPERTY`,`SORT_BY`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`) values ('0ad3858f8baa4b0a820b8edf580f9138','REPTEC','瑞普库存周转规则','1',NULL,'1','153','156','admin','2016-11-30 20:44:33','admin','2016-11-30 20:44:33','0'),('7792f4e4074749dc873959281b26cd1c','SPREA','展讯库存周转规则','1',NULL,'1','153','156','admin','2016-11-30 20:44:56','admin','2016-11-30 20:44:56','0');

/*Table structure for table `tb_storage` */

DROP TABLE IF EXISTS `tb_storage`;

CREATE TABLE `tb_storage` (
  `STORAGE_ID` varchar(100) NOT NULL,
  `stock_id` varchar(32) DEFAULT NULL,
  `STORAGE_CODE` varchar(255) DEFAULT NULL COMMENT '库区编码',
  `STORAGE_NAME` varchar(255) DEFAULT NULL COMMENT '库区名称',
  `STORAGE_TYPE` varchar(255) DEFAULT NULL,
  `STORAGE_PICK` varchar(255) DEFAULT NULL COMMENT '拣货过渡库位',
  `STORAGE_PUT` varchar(255) DEFAULT NULL COMMENT '码放过渡库位',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` int(11) NOT NULL COMMENT '删除标志',
  `MEMO` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`STORAGE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_storage` */

insert  into `tb_storage`(`STORAGE_ID`,`stock_id`,`STORAGE_CODE`,`STORAGE_NAME`,`STORAGE_TYPE`,`STORAGE_PICK`,`STORAGE_PUT`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`MEMO`) values ('2b4576eb61f9476ba466abe939396031',NULL,'A','进货区',NULL,NULL,NULL,'admin','2016-11-30 20:16:41','admin','2016-11-30 20:16:41',0,''),('6e7103c495e149998184630ea437aa26',NULL,'O','出货区',NULL,NULL,NULL,'admin','2016-11-30 20:17:06','admin','2016-11-30 20:17:06',0,''),('ade3b6c3e1e441098a4ed7734e822f86',NULL,'I','存货区',NULL,NULL,NULL,'admin','2016-11-30 20:16:53','admin','2016-11-30 20:16:53',0,'');

/*Table structure for table `tb_storageasignrule` */

DROP TABLE IF EXISTS `tb_storageasignrule`;

CREATE TABLE `tb_storageasignrule` (
  `STORAGEASIGNRULE_ID` varchar(100) NOT NULL,
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `STORAGEASING_CODE` varchar(255) DEFAULT NULL COMMENT '库位分配编码',
  `STORAGEASING_CN` varchar(255) DEFAULT NULL COMMENT '库位分配名称',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `SEAT_STATE` varchar(255) DEFAULT NULL COMMENT '位置优先',
  `PROD_LIMIT` varchar(255) DEFAULT NULL COMMENT '产品限制',
  `PROD_LIMIT_USE` varchar(255) DEFAULT NULL COMMENT '启用',
  `VOLUME_LIMIT` varchar(255) DEFAULT NULL COMMENT '体积限制',
  `VOLUME_LIMIT_USE` varchar(255) DEFAULT NULL COMMENT '启用',
  `LONG_LIMIT` varchar(255) DEFAULT NULL COMMENT '长限制',
  `LONG_LIMIT_USE` varchar(255) DEFAULT NULL COMMENT '启用',
  `WIDE_LIMIT` varchar(255) DEFAULT NULL COMMENT '宽限制',
  `WIDE_LIMIT_USE` varchar(255) DEFAULT NULL COMMENT '启用',
  `HIGH_LIMIT` varchar(255) DEFAULT NULL COMMENT '高限制',
  `HIGH_LIMIT_USE` varchar(255) DEFAULT NULL COMMENT '启用',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  `LOCATOR_PRIORITY_LEVEL` varchar(256) DEFAULT NULL COMMENT '库位优先级',
  PRIMARY KEY (`STORAGEASIGNRULE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_storageasignrule` */

insert  into `tb_storageasignrule`(`STORAGEASIGNRULE_ID`,`CUSTOMER_ID`,`STORAGEASING_CODE`,`STORAGEASING_CN`,`MEMO`,`SEAT_STATE`,`PROD_LIMIT`,`PROD_LIMIT_USE`,`VOLUME_LIMIT`,`VOLUME_LIMIT_USE`,`LONG_LIMIT`,`LONG_LIMIT_USE`,`WIDE_LIMIT`,`WIDE_LIMIT_USE`,`HIGH_LIMIT`,`HIGH_LIMIT_USE`,`CREATE_EMP`,`CREATE_TM`,`MODIFY_EMP`,`MODIFY_TM`,`DEL_FLG`,`LOCATOR_PRIORITY_LEVEL`) values ('ac8fe2d2075e4f94a1aa12b9a82e8385','a269cbcdf38341eab4dea64e2717f346','SPREA','展讯库位分配规则','',NULL,'95',NULL,'',NULL,'',NULL,'',NULL,'',NULL,'admin','2016-11-30 20:39:49','admin','2016-11-30 20:39:49','0','97'),('c19355a691924072b9115d28126f6ef6','aedef24f2cd14d0888e1fed85d271ce7','REOTEC','瑞普库位分配规则','',NULL,'95',NULL,'',NULL,'',NULL,'',NULL,'',NULL,'admin','2016-11-30 20:35:10','admin','2016-11-30 20:35:10','0','97');

/*Table structure for table `tm_box` */

DROP TABLE IF EXISTS `tm_box`;

CREATE TABLE `tm_box` (
  `BOX_ID` varchar(100) NOT NULL,
  `OLD_BOX_ID` varchar(32) DEFAULT NULL,
  `BOX_NO` varchar(255) DEFAULT NULL COMMENT '箱号',
  `BOARD_NO` varchar(255) DEFAULT NULL COMMENT '板号',
  `PRODUCT_ID` varchar(255) DEFAULT NULL COMMENT '产品',
  `CUSTOMER_ID` varchar(255) DEFAULT NULL COMMENT '客户',
  `BIG_BOX_STATUS` varchar(255) DEFAULT NULL COMMENT '是否外箱',
  `BIG_BOX_NO` varchar(255) DEFAULT NULL COMMENT '外箱号',
  `INSTOCK_NO` varchar(255) DEFAULT NULL COMMENT '进货单编号',
  `OUTSTOCK_NO` varchar(255) DEFAULT NULL COMMENT '发货单编号',
  `CARGO_NO` varchar(255) DEFAULT NULL COMMENT '配载单编号',
  `LONG_UNIT` varchar(255) DEFAULT NULL COMMENT '长',
  `WIDE_UNIT` varchar(255) DEFAULT NULL COMMENT '宽',
  `HIGH_UNIT` varchar(255) DEFAULT NULL COMMENT '高',
  `VOLUME_UNIT` varchar(255) DEFAULT NULL COMMENT '高',
  `TOTAL_SUTTLE` varchar(255) DEFAULT NULL COMMENT '净重',
  `TOTAL_WEIGHT` varchar(255) DEFAULT NULL COMMENT '毛重',
  `ASIGN_STORAGE` varchar(65) DEFAULT NULL COMMENT '分配库区',
  `ASIGN_LOCATOR` varchar(255) DEFAULT NULL COMMENT '分配库位',
  `PUT_STORAGE` varchar(64) DEFAULT NULL COMMENT '码放库区',
  `PUT_LOCATOR` varchar(255) DEFAULT NULL COMMENT '码放库位',
  `RECEIV_QTY` varchar(32) DEFAULT NULL,
  `EA_NUM` varchar(255) DEFAULT NULL COMMENT 'EA数量',
  `RECEIPT_STATE` varchar(255) DEFAULT NULL COMMENT '收货状态',
  `ASSIGNED_STATE` varchar(255) DEFAULT NULL COMMENT '分配状态',
  `PUT_STATUS` varchar(255) DEFAULT NULL COMMENT '码放状态',
  `ASSIGNED_STOCK_STATE` varchar(255) DEFAULT NULL COMMENT '分配库存状态',
  `ASSIGNED_STOCK_NUM` varchar(255) DEFAULT NULL COMMENT '分配库存数量',
  `PICK_STATE` varchar(255) DEFAULT NULL COMMENT '拣货状态',
  `CARGO_STATE` varchar(255) DEFAULT NULL COMMENT '配载状态',
  `DEPOT_TYPE` varchar(255) DEFAULT NULL COMMENT '发货状态',
  `FREEZE_STATE` varchar(255) DEFAULT NULL COMMENT '冻结状态',
  `BOX_STATE` varchar(255) DEFAULT NULL COMMENT '状态',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  `GROUP_KEY` varchar(255) DEFAULT NULL COMMENT '同组收货标记',
  `SUPPLIER_PROD` varchar(256) DEFAULT NULL COMMENT '供应商料号',
  `RECEIPT_TM` varchar(32) DEFAULT NULL,
  `ASSIGNED_TM` varchar(32) DEFAULT NULL,
  `PUT_TM` varchar(32) DEFAULT NULL,
  `ASSIGNED_STOCK_TM` varchar(32) DEFAULT NULL,
  `PICK_TM` varchar(32) DEFAULT NULL,
  `CARGO_TM` varchar(32) DEFAULT NULL,
  `DEPOT_TM` varchar(32) DEFAULT NULL,
  `FREEZE_TM` varchar(32) DEFAULT NULL,
  `TRANSFER_TM` varchar(32) DEFAULT NULL,
  `PUT_LOCATOR_BF` varchar(32) DEFAULT NULL,
  `CUSTOMER_ID_BF` varchar(32) DEFAULT NULL,
  `PRODRIGHT_TM` varchar(32) DEFAULT NULL,
  `AUDIT_EMP` varchar(256) DEFAULT NULL COMMENT '审核人',
  `AUDIT_TM` varchar(256) DEFAULT NULL COMMENT '审核时间',
  `AUDIT_FLG` varchar(256) DEFAULT NULL COMMENT '审核标志',
  `LOT_NO` varchar(256) DEFAULT NULL,
  `DATE_CODE` varchar(256) DEFAULT NULL,
  `SEPARATE_QTY` varchar(256) DEFAULT NULL,
  `COO` varchar(256) DEFAULT NULL,
  `BIN_CODE` varchar(256) DEFAULT NULL,
  `REMARK1` varchar(256) DEFAULT NULL,
  `REMARK2` varchar(256) DEFAULT NULL,
  `REMARK3` varchar(256) DEFAULT NULL,
  `EA_NUM_BF` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `EA_NUM_AF` varchar(256) DEFAULT NULL,
  `AUDIT_STATE` varchar(256) DEFAULT NULL,
  `AUDIT_APPLY_EMP` varchar(256) DEFAULT NULL,
  `AUDIT_APPLY_TM` varchar(256) DEFAULT NULL,
  `AUDIT_PASS_EMP` varchar(256) DEFAULT NULL,
  `AUDIT_PASS_TM` varchar(256) DEFAULT NULL,
  `AUDIT_TYPE` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`BOX_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_box` */

/*Table structure for table `tm_cargoout` */

DROP TABLE IF EXISTS `tm_cargoout`;

CREATE TABLE `tm_cargoout` (
  `CARGOOUT_ID` varchar(100) NOT NULL,
  `CARGO_NO` varchar(255) DEFAULT NULL COMMENT '配载单号',
  `CARGO_STATE` varchar(255) DEFAULT NULL COMMENT '配载状态',
  `CARGO_TM` varchar(255) DEFAULT NULL COMMENT '配载时间',
  `CAR_BATCH` varchar(255) DEFAULT NULL COMMENT '车次号',
  `DEPOT_TM` varchar(255) DEFAULT NULL COMMENT '发货时间',
  `CAR_PLATE` varchar(255) DEFAULT NULL COMMENT '车牌号',
  `CAR_TYPE` varchar(255) DEFAULT NULL COMMENT '车型',
  `SAIL_TM` varchar(255) DEFAULT NULL COMMENT '开船日期',
  `PRE_ARRIVETM` varchar(255) DEFAULT NULL COMMENT '预计到达日期',
  `CONTAINER_NO` varchar(255) DEFAULT NULL COMMENT '集装箱号',
  `SAIL_NAME` varchar(255) DEFAULT NULL COMMENT '船名',
  `SEAL_NO` varchar(255) DEFAULT NULL COMMENT '封箱号',
  `VOYAGE_NUM` varchar(255) DEFAULT NULL COMMENT '航次',
  `LADING_NO` varchar(255) DEFAULT NULL COMMENT '提单号',
  `SRC_PORT` varchar(255) DEFAULT NULL COMMENT '起运港',
  `DEST_PORT` varchar(255) DEFAULT NULL COMMENT '目的港',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除',
  PRIMARY KEY (`CARGOOUT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_cargoout` */

/*Table structure for table `tm_cargoout_dtl` */

DROP TABLE IF EXISTS `tm_cargoout_dtl`;

CREATE TABLE `tm_cargoout_dtl` (
  `STOCKDTLMGROUT_ID` varchar(64) NOT NULL,
  `CARGOOUT_ID` varchar(64) DEFAULT NULL COMMENT '主表ID',
  `STOCKMGROUT_ID` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`STOCKDTLMGROUT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_cargoout_dtl` */

/*Table structure for table `tm_pickno` */

DROP TABLE IF EXISTS `tm_pickno`;

CREATE TABLE `tm_pickno` (
  `STOCKMGROUT_ID` varchar(32) DEFAULT NULL COMMENT '出库单',
  `PICK_NO` varchar(64) DEFAULT NULL COMMENT '拣货单好'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_pickno` */

/*Table structure for table `tm_receivopt` */

DROP TABLE IF EXISTS `tm_receivopt`;

CREATE TABLE `tm_receivopt` (
  `RECEIVOPT_ID` varchar(100) NOT NULL,
  `INSTOCK_NO` varchar(255) DEFAULT NULL COMMENT '入库单号',
  `RECEIV_TYPE` varchar(255) DEFAULT NULL COMMENT '收货类型',
  `BOX_NO` varchar(255) DEFAULT NULL COMMENT '箱号',
  `PREFIX_PROD` varchar(255) DEFAULT NULL COMMENT '前缀料号',
  `PROD_CODE` varchar(255) DEFAULT NULL COMMENT '料号',
  `PREFIX_QTY` varchar(255) DEFAULT NULL COMMENT '前缀数量',
  `RECEIV_QTY` varchar(255) DEFAULT NULL COMMENT '数量',
  `COO` varchar(255) DEFAULT NULL COMMENT 'COO',
  `PACKAGE_QTY` varchar(255) DEFAULT NULL COMMENT 'PACKAGE QTY',
  `BOARD_NO` varchar(255) DEFAULT NULL COMMENT '板号',
  `QR_CODE` varchar(255) DEFAULT NULL COMMENT '二维码',
  `QR_CODE_USE` varchar(16) DEFAULT NULL COMMENT '二维码标记',
  `PREFIX_LOTNO` varchar(255) DEFAULT NULL COMMENT '前缀LOTNO',
  `LOT_NO` varchar(255) DEFAULT NULL COMMENT 'LOT_NO',
  `PREFIX_DATECODE` varchar(255) DEFAULT NULL COMMENT '前缀DATECODE',
  `DATE_CODE` varchar(255) DEFAULT NULL COMMENT 'DATECODE',
  `PREFIX_SCAN` varchar(255) DEFAULT NULL COMMENT '前缀扫描码数量',
  `SCAN_CODE` varchar(255) DEFAULT NULL COMMENT '扫描码',
  `SCAN_QTY` varchar(255) DEFAULT NULL COMMENT '描码数量',
  `GROUP_KEY` varchar(255) DEFAULT NULL COMMENT '同组操作',
  `CNT_SCAN` varchar(255) DEFAULT NULL COMMENT '已扫描数',
  `BOX_SCAN` varchar(255) DEFAULT NULL COMMENT '已扫描箱',
  `CREATE_EMP` varchar(255) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(255) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(255) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(255) DEFAULT NULL COMMENT '删除标志',
  `SUPPLIER_PROD` varchar(256) DEFAULT NULL COMMENT '供应商料号',
  `BIG_BOX_NO` varchar(255) DEFAULT NULL COMMENT '外箱号',
  `BIN_CODE` varchar(256) DEFAULT NULL,
  `REMARK1` varchar(256) DEFAULT NULL,
  `REMARK2` varchar(256) DEFAULT NULL,
  `REMARK3` varchar(256) DEFAULT NULL,
  `INV_NO` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`RECEIVOPT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_receivopt` */

/*Table structure for table `tm_stockmgrin` */

DROP TABLE IF EXISTS `tm_stockmgrin`;

CREATE TABLE `tm_stockmgrin` (
  `STOCKMGRIN_ID` varchar(32) NOT NULL,
  `INSTOCK_NO` varchar(32) DEFAULT NULL COMMENT '进货单编号入货单编号',
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '客户',
  `INSTOCK_TYPE` varchar(32) DEFAULT NULL COMMENT '入库类型',
  `PRE_INSTOCK_DATE` varchar(32) DEFAULT NULL COMMENT '预计入库日期',
  `REAL_INSTOCK_DATE` varchar(32) DEFAULT NULL COMMENT '实际入库日期',
  `INSTOCK_STATE` varchar(32) DEFAULT NULL COMMENT '收货状态',
  `PUT_STATE` varchar(32) DEFAULT NULL COMMENT '码放状态',
  `TOTAL_WEIGHT` varchar(16) DEFAULT NULL COMMENT '总毛重',
  `TOTAL_SUTTLE` varchar(16) DEFAULT NULL COMMENT '总净重',
  `TOTAL_VOLUME` varchar(16) DEFAULT NULL COMMENT '总体积',
  `TOTAL_PRICE` varchar(16) DEFAULT NULL COMMENT '总价',
  `TOTAL_EA` varchar(8) DEFAULT NULL COMMENT '总期望EA数',
  `PRIORITY_LEVEL` varchar(32) DEFAULT NULL COMMENT '优先级',
  `ORDER_NO` varchar(32) DEFAULT NULL COMMENT '订单号',
  `COST_STATE` varchar(32) DEFAULT NULL COMMENT '费用录入已完成',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(32) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(1) DEFAULT NULL COMMENT '删除',
  `ASSIGNED_STATE` varchar(32) DEFAULT NULL COMMENT '分配状态',
  `CONFIRM_STATE` varchar(1) DEFAULT '0' COMMENT '收货确认状态',
  PRIMARY KEY (`STOCKMGRIN_ID`),
  FULLTEXT KEY `NewIndex1` (`INSTOCK_NO`),
  FULLTEXT KEY `NewIndex2` (`CUSTOMER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_stockmgrin` */

/*Table structure for table `tm_stockmgrin_dtl` */

DROP TABLE IF EXISTS `tm_stockmgrin_dtl`;

CREATE TABLE `tm_stockmgrin_dtl` (
  `STOCKDTLMGRIN_ID` varchar(32) NOT NULL,
  `STOCKMGRIN_ID` varchar(32) DEFAULT NULL COMMENT '主表ID',
  `ROW_NO` varchar(3) DEFAULT NULL COMMENT '行号',
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '客户',
  `PRODUCT_ID` varchar(32) DEFAULT NULL COMMENT '产品',
  `PRODUCT_TYPE` varchar(32) DEFAULT NULL COMMENT '产品类型',
  `PACK_RULE` varchar(32) DEFAULT NULL COMMENT '包装规则',
  `PACK_UNIT` varchar(32) DEFAULT NULL COMMENT '包装单位',
  `OT_EA` varchar(8) DEFAULT NULL COMMENT '其他期望数',
  `OT_RATIO` varchar(8) DEFAULT NULL COMMENT '其他比例',
  `PALLET_EA` varchar(8) DEFAULT NULL COMMENT '托盘期望数',
  `PALLET_RATIO` varchar(8) DEFAULT NULL COMMENT '托盘比例',
  `BOX_EA` varchar(8) DEFAULT NULL COMMENT '箱期望数',
  `BOX_RATIO` varchar(8) DEFAULT NULL COMMENT '箱比例',
  `INPACK_EA` varchar(8) DEFAULT NULL COMMENT '内期望数',
  `INPACK_RATIO` varchar(8) DEFAULT NULL COMMENT '内比例',
  `EA_EA` varchar(8) DEFAULT NULL COMMENT 'EA期望数',
  `EA_RATIO` varchar(8) DEFAULT NULL COMMENT 'EA比例',
  `LONG_UT` varchar(16) DEFAULT NULL COMMENT '长CM',
  `WIDE_UT` varchar(16) DEFAULT NULL COMMENT '宽CM',
  `HIGH_UT` varchar(16) DEFAULT NULL COMMENT '高CM',
  `UNIT_VOLUME` varchar(16) DEFAULT NULL COMMENT '单位体积',
  `TOTAL_VOLUME` varchar(16) DEFAULT NULL COMMENT '体积',
  `TOTAL_WEIGHT` varchar(16) DEFAULT NULL COMMENT '毛重',
  `TOTAL_SUTTLE` varchar(16) DEFAULT NULL COMMENT '净重',
  `TOTAL_PRICE` varchar(16) DEFAULT NULL COMMENT '总价',
  `UNIT_PRICE` varchar(16) DEFAULT NULL COMMENT '单价',
  `CURRENCY_TYPE` varchar(32) DEFAULT NULL COMMENT '币种',
  `STORAGE_ID` varchar(32) DEFAULT NULL COMMENT '收货库区',
  `LOCATOR_ID` varchar(32) DEFAULT NULL COMMENT '收货库位',
  `CAR_TYPE` varchar(32) DEFAULT NULL COMMENT '车型',
  `CAR_PLATE` varchar(32) DEFAULT NULL COMMENT '车牌号',
  `SEAL_NO` varchar(32) DEFAULT NULL COMMENT '封条号',
  `TP_HAULIER` varchar(32) DEFAULT NULL COMMENT '承运人',
  `FREEZE_CODE` varchar(32) DEFAULT NULL COMMENT '冻结拒收代码',
  `MGR_SERIAL` varchar(32) DEFAULT NULL COMMENT '管理序列号',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `IMP_FLG` varchar(1) DEFAULT NULL COMMENT '导入',
  `RECEIPT_STATE` varchar(32) DEFAULT NULL COMMENT '收货状态',
  `DISTR_STATE` varchar(32) DEFAULT NULL COMMENT '分配状态',
  `PUT_STATUS` varchar(32) DEFAULT NULL COMMENT '码放状态',
  `CREATE_EMP` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(32) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(1) DEFAULT '0' COMMENT '删除',
  `BOX_NO` varchar(128) DEFAULT NULL COMMENT '箱号',
  `EA_ACTUAL` varchar(32) DEFAULT NULL COMMENT '实际收货数',
  `RECEI_FLG` varchar(2) DEFAULT '0' COMMENT '收货标记',
  `PUT_EA` varchar(32) DEFAULT '0' COMMENT '码放EA',
  `ASSIGNED_EA` varchar(32) DEFAULT '0' COMMENT '分配EA',
  `SCAN_CODE` varchar(256) DEFAULT NULL COMMENT '扫描码',
  `PUT_STORAGE_ID` varchar(32) DEFAULT NULL COMMENT '码放库区',
  `PUT_LOCATOR_ID` varchar(32) DEFAULT NULL COMMENT '码放库位',
  PRIMARY KEY (`STOCKDTLMGRIN_ID`),
  FULLTEXT KEY `NewIndex1` (`CUSTOMER_ID`,`PRODUCT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_stockmgrin_dtl` */

/*Table structure for table `tm_stockmgrin_property` */

DROP TABLE IF EXISTS `tm_stockmgrin_property`;

CREATE TABLE `tm_stockmgrin_property` (
  `P_ID` varchar(32) NOT NULL,
  `STOCKMGRIN_ID` varchar(32) DEFAULT NULL,
  `STOCKDTLMGRIN_ID` varchar(32) DEFAULT NULL,
  `ORDERBATCH_TYPE` varchar(32) DEFAULT NULL,
  `TIME_ONE` varchar(32) DEFAULT NULL,
  `TIME_TWO` varchar(32) DEFAULT NULL,
  `TIME_THR` varchar(32) DEFAULT NULL,
  `TIME_FOU` varchar(32) DEFAULT NULL,
  `TIME_FIV` varchar(32) DEFAULT NULL,
  `TIME_SIX` varchar(32) DEFAULT NULL,
  `TIME_SEV` varchar(32) DEFAULT NULL,
  `TIME_EIG` varchar(32) DEFAULT NULL,
  `TIME_NIG` varchar(32) DEFAULT NULL,
  `TIME_TEN` varchar(32) DEFAULT NULL,
  `NUM_ONE` varchar(8) DEFAULT NULL,
  `NUM_TWO` varchar(8) DEFAULT NULL,
  `NUM_THR` varchar(8) DEFAULT NULL,
  `NUM_FOU` varchar(8) DEFAULT NULL,
  `NUM_FIV` varchar(8) DEFAULT NULL,
  `NUM_SIX` varchar(8) DEFAULT NULL,
  `NUM_SEV` varchar(8) DEFAULT NULL,
  `NUM_EIG` varchar(8) DEFAULT NULL,
  `NUM_NIG` varchar(8) DEFAULT NULL,
  `NUM_TEN` varchar(8) DEFAULT NULL,
  `TXT_ONE` varchar(64) DEFAULT NULL,
  `TXT_TWO` varchar(64) DEFAULT NULL,
  `TXT_THR` varchar(64) DEFAULT NULL,
  `TXT_FOU` varchar(64) DEFAULT NULL,
  `TXT_FIV` varchar(64) DEFAULT NULL,
  `TXT_SIX` varchar(64) DEFAULT NULL,
  `TXT_SEV` varchar(64) DEFAULT NULL,
  `TXT_EIG` varchar(64) DEFAULT NULL,
  `TXT_NIG` varchar(64) DEFAULT NULL,
  `TXT_TEN` varchar(64) DEFAULT NULL,
  `TXT_ELEVEN` varchar(64) DEFAULT NULL,
  `TXT_TWELVE` varchar(64) DEFAULT NULL,
  `TXT_THIRT` varchar(64) DEFAULT NULL,
  `TXT_FOURT` varchar(64) DEFAULT NULL,
  `TXT_FIFT` varchar(64) DEFAULT NULL,
  `TXT_SIXT` varchar(64) DEFAULT NULL,
  `TXT_SEVENT` varchar(64) DEFAULT NULL,
  `TXT_EIGHT` varchar(64) DEFAULT NULL,
  `TXT_NINET` varchar(64) DEFAULT NULL,
  `TXT_TWENT` varchar(64) DEFAULT NULL,
  `TXT_21` VARCHAR(64) DEFAULT NULL,
   `TXT_22` VARCHAR(64) DEFAULT NULL,
   `TXT_23` VARCHAR(64) DEFAULT NULL,
   `TXT_24` VARCHAR(64) DEFAULT NULL,
   `TXT_25` VARCHAR(64) DEFAULT NULL,
   `TXT_26` VARCHAR(64) DEFAULT NULL,
   `TXT_27` VARCHAR(64) DEFAULT NULL,
   `TXT_28` VARCHAR(64) DEFAULT NULL,
   `TXT_29` VARCHAR(64) DEFAULT NULL,
   `TXT_30` VARCHAR(64) DEFAULT NULL,
  PRIMARY KEY (`P_ID`),
  FULLTEXT KEY `NewIndex1` (`STOCKDTLMGRIN_ID`,`ORDERBATCH_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_stockmgrin_property` */

/*Table structure for table `tm_stockmgrout` */

DROP TABLE IF EXISTS `tm_stockmgrout`;

CREATE TABLE `tm_stockmgrout` (
  `STOCKMGROUT_ID` varchar(32) NOT NULL,
  `OUTSTOCK_NO` varchar(32) DEFAULT NULL COMMENT '发货单号',
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '客户',
  `OUTSTOCK_TYPE` varchar(32) DEFAULT NULL COMMENT '出库类型',
  `PRE_OUT_DATE` varchar(32) DEFAULT NULL COMMENT '预计发货日期',
  `REAL_OUT_DATE` varchar(32) DEFAULT NULL COMMENT '实际发货日期',
  `OUTSTOCK_WAY` varchar(32) DEFAULT NULL COMMENT '发货方式',
  `ASSIGNED_STATE` varchar(32) DEFAULT NULL COMMENT '分配状态',
  `CARGO_STATE` varchar(32) DEFAULT NULL COMMENT '配载状态',
  `DEPOT_STATE` varchar(32) DEFAULT NULL COMMENT '发货状态',
  `PICK_STATE` varchar(32) DEFAULT NULL COMMENT '拣货状态',
  `TOTAL_WEIGHT` varchar(8) DEFAULT NULL COMMENT '毛重',
  `LOADED_TYPE` varchar(32) DEFAULT NULL COMMENT '装车状态',
  `TOTAL_SUTTLE` varchar(8) DEFAULT NULL COMMENT '净重',
  `TOTAL_VOLUME` varchar(8) DEFAULT NULL COMMENT '体积',
  `TOTAL_PRICE` varchar(8) DEFAULT NULL COMMENT '总价',
  `TOTAL_EA` varchar(8) DEFAULT NULL COMMENT '总期望EA数',
  `PRIORITY_LEVEL` varchar(32) DEFAULT NULL COMMENT '优先级',
  `ORDER_NO` varchar(32) DEFAULT NULL COMMENT '订单号',
  `COST_STATE` varchar(32) DEFAULT NULL COMMENT '费用录入已完成',
  `MEMO` varchar(255) DEFAULT NULL COMMENT '备注',
  `CREATE_EMP` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(32) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(1) DEFAULT NULL COMMENT '删除',
  `CONFIRM_STATE` varchar(1) DEFAULT '0',
  PRIMARY KEY (`STOCKMGROUT_ID`),
  KEY `NewIndex2` (`CUSTOMER_ID`),
  FULLTEXT KEY `NewIndex1` (`OUTSTOCK_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_stockmgrout` */

/*Table structure for table `tm_stockmgrout_dtl` */

DROP TABLE IF EXISTS `tm_stockmgrout_dtl`;

CREATE TABLE `tm_stockmgrout_dtl` (
  `STOCKDTLMGROUT_ID` varchar(32) NOT NULL,
  `STOCKMGROUT_ID` varchar(32) DEFAULT NULL COMMENT '主表ID',
  `ROW_NO` varchar(3) DEFAULT NULL COMMENT '行号',
  `CUSTOMER_ID` varchar(32) DEFAULT NULL COMMENT '客户',
  `PRODUCT_ID` varchar(32) DEFAULT NULL COMMENT '产品',
  `PRODUCT_TYPE` varchar(32) DEFAULT NULL COMMENT '产品类型',
  `PACK_RULE` varchar(32) DEFAULT NULL COMMENT '包装规则',
  `PACK_UNIT` varchar(32) DEFAULT NULL COMMENT '包装单位',
  `OT_EA` varchar(8) DEFAULT NULL COMMENT '其他期望数',
  `OT_RATIO` varchar(8) DEFAULT NULL COMMENT '其他比例',
  `PALLET_EA` varchar(8) DEFAULT NULL COMMENT '托盘期望数',
  `PALLET_RATIO` varchar(8) DEFAULT NULL COMMENT '托盘比例',
  `BOX_EA` varchar(8) DEFAULT NULL COMMENT '箱期望数',
  `BOX_RATIO` varchar(8) DEFAULT NULL COMMENT '箱比例',
  `INPACK_EA` varchar(8) DEFAULT NULL COMMENT '内期望数',
  `INPACK_RATIO` varchar(8) DEFAULT NULL COMMENT '内比例',
  `EA_EA` varchar(8) DEFAULT NULL COMMENT 'EA期望数',
  `EA_RATIO` varchar(8) DEFAULT NULL COMMENT 'EA比例',
  `LONG_UT` varchar(8) DEFAULT NULL COMMENT '长CM',
  `WIDE_UT` varchar(8) DEFAULT NULL COMMENT '宽CM',
  `HIGH_UT` varchar(8) DEFAULT NULL COMMENT '高CM',
  `UNIT_VOLUME` varchar(8) DEFAULT NULL COMMENT '单位体积',
  `TOTAL_VOLUME` varchar(16) DEFAULT NULL COMMENT '体积',
  `TOTAL_WEIGHT` varchar(16) DEFAULT NULL COMMENT '毛重',
  `TOTAL_SUTTLE` varchar(16) DEFAULT NULL COMMENT '净重',
  `TOTAL_PRICE` varchar(16) DEFAULT NULL COMMENT '总价',
  `UNIT_PRICE` varchar(16) DEFAULT NULL COMMENT '单价',
  `CURRENCY_TYPE` varchar(32) DEFAULT NULL COMMENT '币种',
  `STORAGE_ID` varchar(32) DEFAULT NULL COMMENT '收货库区',
  `LOCATOR_ID` varchar(32) DEFAULT NULL COMMENT '收货库位',
  `CAR_TYPE` varchar(32) DEFAULT NULL COMMENT '车型',
  `CAR_PLATE` varchar(128) DEFAULT NULL COMMENT '车牌号',
  `SEAL_NO` varchar(128) DEFAULT NULL COMMENT '封条号',
  `TP_HAULIER` varchar(32) DEFAULT NULL COMMENT '承运人',
  `FREEZE_CODE` varchar(32) DEFAULT NULL COMMENT '冻结拒收代码',
  `MGR_SERIAL` varchar(128) DEFAULT NULL COMMENT '管理序列号',
  `MEMO` varchar(128) DEFAULT NULL COMMENT '备注',
  `IMP_FLG` varchar(1) DEFAULT NULL COMMENT '导入',
  `ASSIGNED_STATE` varchar(32) DEFAULT NULL COMMENT '分配状态',
  `PICK_STATE` varchar(32) DEFAULT NULL COMMENT '拣货状态',
  `CARGO_STATE` varchar(32) DEFAULT NULL COMMENT '配载状态',
  `LOADED_STATE` varchar(32) DEFAULT NULL COMMENT '装车状态',
  `DEPOT_STATE` varchar(32) DEFAULT NULL COMMENT '发货状态',
  `RULE_ASSIGNED` varchar(32) DEFAULT NULL COMMENT '分配规则',
  `RULE_STOCKTURN` varchar(32) DEFAULT NULL COMMENT '库存周转规则',
  `RULE_PICK` varchar(32) DEFAULT NULL COMMENT '拣货规则',
  `SEND_EA` varchar(8) DEFAULT NULL COMMENT '发货EA数',
  `PREPLAN_EA` varchar(8) DEFAULT NULL COMMENT '预配EA数',
  `ASSIGNED_EA` varchar(8) DEFAULT NULL COMMENT '分配EA数',
  `PICK_EA` varchar(8) DEFAULT NULL COMMENT '拣货EA数',
  `CARGO_EA` varchar(8) DEFAULT NULL COMMENT '配载EA',
  `LOADED_EA` varchar(8) DEFAULT NULL COMMENT '装车EA数',
  `CREATE_EMP` varchar(32) DEFAULT NULL COMMENT '创建人',
  `CREATE_TM` varchar(32) DEFAULT NULL COMMENT '创建时间',
  `MODIFY_EMP` varchar(32) DEFAULT NULL COMMENT '修改人',
  `MODIFY_TM` varchar(32) DEFAULT NULL COMMENT '修改时间',
  `DEL_FLG` varchar(1) DEFAULT NULL COMMENT '删除',
  `BOX_NO` varchar(64) DEFAULT NULL COMMENT '箱号',
  `ASSIGNED_FLG` varchar(1) DEFAULT NULL COMMENT '分配标志',
  `CARGO_TYPE` varchar(1) DEFAULT NULL COMMENT '配载标志1详情配载2缺省配载',
  FULLTEXT KEY `NewIndex1` (`STOCKDTLMGROUT_ID`),
  FULLTEXT KEY `NewIndex2` (`STOCKMGROUT_ID`),
  FULLTEXT KEY `NewIndex3` (`CUSTOMER_ID`,`PRODUCT_ID`),
  FULLTEXT KEY `NewIndex4` (`STOCKMGROUT_ID`,`CUSTOMER_ID`),
  FULLTEXT KEY `NewIndex5` (`BOX_NO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_stockmgrout_dtl` */

/*Table structure for table `tm_stockmgrout_property` */

DROP TABLE IF EXISTS `tm_stockmgrout_property`;

CREATE TABLE `tm_stockmgrout_property` (
  `P_ID` varchar(32) NOT NULL,
  `STOCKMGROUT_ID` varchar(32) DEFAULT NULL,
  `STOCKDTLMGROUT_ID` varchar(32) DEFAULT NULL,
  `ORDERBATCH_TYPE` varchar(32) DEFAULT NULL,
  `TIME_ONE` varchar(32) DEFAULT NULL,
  `TIME_TWO` varchar(32) DEFAULT NULL,
  `TIME_THR` varchar(32) DEFAULT NULL,
  `TIME_FOU` varchar(32) DEFAULT NULL,
  `TIME_FIV` varchar(32) DEFAULT NULL,
  `TIME_SIX` varchar(32) DEFAULT NULL,
  `TIME_SEV` varchar(32) DEFAULT NULL,
  `TIME_EIG` varchar(32) DEFAULT NULL,
  `TIME_NIG` varchar(32) DEFAULT NULL,
  `TIME_TEN` varchar(32) DEFAULT NULL,
  `NUM_ONE` varchar(32) DEFAULT NULL,
  `NUM_TWO` varchar(32) DEFAULT NULL,
  `NUM_THR` varchar(32) DEFAULT NULL,
  `NUM_FOU` varchar(32) DEFAULT NULL,
  `NUM_FIV` varchar(32) DEFAULT NULL,
  `NUM_SIX` varchar(32) DEFAULT NULL,
  `NUM_SEV` varchar(32) DEFAULT NULL,
  `NUM_EIG` varchar(32) DEFAULT NULL,
  `NUM_NIG` varchar(32) DEFAULT NULL,
  `NUM_TEN` varchar(32) DEFAULT NULL,
  `TXT_ONE` varchar(128) DEFAULT NULL,
  `TXT_TWO` varchar(128) DEFAULT NULL,
  `TXT_THR` varchar(128) DEFAULT NULL,
  `TXT_FOU` varchar(128) DEFAULT NULL,
  `TXT_FIV` varchar(128) DEFAULT NULL,
  `TXT_SIX` varchar(128) DEFAULT NULL,
  `TXT_SEV` varchar(128) DEFAULT NULL,
  `TXT_EIG` varchar(128) DEFAULT NULL,
  `TXT_NIG` varchar(128) DEFAULT NULL,
  `TXT_TEN` varchar(128) DEFAULT NULL,
  `TXT_ELEVEN` varchar(128) DEFAULT NULL,
  `TXT_TWELVE` varchar(128) DEFAULT NULL,
  `TXT_THIRT` varchar(128) DEFAULT NULL,
  `TXT_FOURT` varchar(128) DEFAULT NULL,
  `TXT_FIFT` varchar(128) DEFAULT NULL,
  `TXT_SIXT` varchar(128) DEFAULT NULL,
  `TXT_SEVENT` varchar(128) DEFAULT NULL,
  `TXT_EIGHT` varchar(128) DEFAULT NULL,
  `TXT_NINET` varchar(128) DEFAULT NULL,
  `TXT_TWENT` varchar(128) DEFAULT NULL,
  `TXT_21` VARCHAR(64) DEFAULT NULL,
   `TXT_22` VARCHAR(64) DEFAULT NULL,
   `TXT_23` VARCHAR(64) DEFAULT NULL,
   `TXT_24` VARCHAR(64) DEFAULT NULL,
   `TXT_25` VARCHAR(64) DEFAULT NULL,
   `TXT_26` VARCHAR(64) DEFAULT NULL,
   `TXT_27` VARCHAR(64) DEFAULT NULL,
   `TXT_28` VARCHAR(64) DEFAULT NULL,
   `TXT_29` VARCHAR(64) DEFAULT NULL,
   `TXT_30` VARCHAR(64) DEFAULT NULL,
  PRIMARY KEY (`P_ID`),
  FULLTEXT KEY `NewIndex1` (`STOCKDTLMGROUT_ID`,`ORDERBATCH_TYPE`),
  FULLTEXT KEY `NewIndex2` (`STOCKMGROUT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tm_stockmgrout_property` */

/*Table structure for table `ts_app_user` */

DROP TABLE IF EXISTS `ts_app_user`;

CREATE TABLE `ts_app_user` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `PHONE` varchar(100) DEFAULT NULL,
  `SFID` varchar(100) DEFAULT NULL,
  `START_TIME` varchar(100) DEFAULT NULL,
  `END_TIME` varchar(100) DEFAULT NULL,
  `YEARS` int(10) DEFAULT NULL,
  `NUMBER` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ts_app_user` */

insert  into `ts_app_user`(`USER_ID`,`USERNAME`,`PASSWORD`,`NAME`,`RIGHTS`,`ROLE_ID`,`LAST_LOGIN`,`IP`,`STATUS`,`BZ`,`PHONE`,`SFID`,`START_TIME`,`END_TIME`,`YEARS`,`NUMBER`,`EMAIL`) values ('04762c0b28b643939455c7800c2e2412','dsfsd','f1290186a5d0b1ceab27f4e77c0c5d68','w','','55896f5ce3c0494fa6850775a4e29ff6','','','0','','18766666666','','','',0,'001','18766666666@qq.com'),('3faac8fe5c0241e593e0f9ea6f2d5870','dsfsdf','f1290186a5d0b1ceab27f4e77c0c5d68','wewe','','68f23fc0caee475bae8d52244dea8444','','','1','','18767676767','','','',0,'wqwe','qweqwe@qq.com');

/*Table structure for table `ts_dictionaries` */

DROP TABLE IF EXISTS `ts_dictionaries`;

CREATE TABLE `ts_dictionaries` (
  `ZD_ID` varchar(100) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `BIANMA` varchar(100) DEFAULT NULL,
  `ORDY_BY` int(10) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `JB` int(10) DEFAULT NULL,
  `P_BM` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ZD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ts_dictionaries` */

insert  into `ts_dictionaries`(`ZD_ID`,`NAME`,`BIANMA`,`ORDY_BY`,`PARENT_ID`,`JB`,`P_BM`) values ('212a6765fddc4430941469e1ec8c8e6c','人事部','001',1,'c067fdaf51a141aeaa56ed26b70de863',2,'BM_001'),('3cec73a7cc8a4cb79e3f6ccc7fc8858c','行政部','002',2,'c067fdaf51a141aeaa56ed26b70de863',2,'BM_002'),('48724375640341deb5ef01ac51a89c34','北京','dq001',1,'cdba0b5ef20e4fc0a5231fa3e9ae246a',2,'DQ_dq001'),('5a1547632cca449db378fbb9a042b336','研发部','004',4,'c067fdaf51a141aeaa56ed26b70de863',2,'BM_004'),('7f9cd74e60a140b0aea5095faa95cda3','财务部','003',3,'c067fdaf51a141aeaa56ed26b70de863',2,'BM_003'),('b861bd1c3aba4934acdb5054dd0d0c6e','科技不','kj',7,'c067fdaf51a141aeaa56ed26b70de863',2,'BM_kj'),('c067fdaf51a141aeaa56ed26b70de863','部门','BM',1,'0',1,'BM'),('cdba0b5ef20e4fc0a5231fa3e9ae246a','地区','DQ',2,'0',1,'DQ'),('f184bff5081d452489271a1bd57599ed','上海','SH',2,'cdba0b5ef20e4fc0a5231fa3e9ae246a',2,'DQ_SH'),('f30bf95e216d4ebb8169ff0c86330b8f','客服部','006',6,'c067fdaf51a141aeaa56ed26b70de863',2,'BM_006');

/*Table structure for table `ts_gl_qx` */

DROP TABLE IF EXISTS `ts_gl_qx`;

CREATE TABLE `ts_gl_qx` (
  `GL_ID` varchar(100) NOT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `FX_QX` int(10) DEFAULT NULL,
  `FW_QX` int(10) DEFAULT NULL,
  `QX1` int(10) DEFAULT NULL,
  `QX2` int(10) DEFAULT NULL,
  `QX3` int(10) DEFAULT NULL,
  `QX4` int(10) DEFAULT NULL,
  PRIMARY KEY (`GL_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ts_gl_qx` */

insert  into `ts_gl_qx`(`GL_ID`,`ROLE_ID`,`FX_QX`,`FW_QX`,`QX1`,`QX2`,`QX3`,`QX4`) values ('0ffb33f48cc14ace975280e01613bde8','4',0,0,0,0,0,0),('1','2',1,1,1,1,1,1),('2','1',0,0,1,1,1,1),('55896f5ce3c0494fa6850775a4e29ff6','7',1,1,1,0,0,0),('68f23fc0caee475bae8d52244dea8444','7',0,0,1,0,0,0),('a312e47001e64704ac3ce89aafff43b9','0',0,0,0,0,0,0),('d7e27529a3894a1eaada0076f8f808ae','1',0,0,0,0,0,0);

/*Table structure for table `ts_menu` */

DROP TABLE IF EXISTS `ts_menu`;

CREATE TABLE `ts_menu` (
  `MENU_ID` int(11) NOT NULL,
  `MENU_NAME` varchar(255) DEFAULT NULL,
  `MENU_URL` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `MENU_ORDER` int(11) DEFAULT '0',
  `MENU_ICON` varchar(30) DEFAULT NULL,
  `MENU_TYPE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ts_menu` */

insert  into `ts_menu`(`MENU_ID`,`MENU_NAME`,`MENU_URL`,`PARENT_ID`,`MENU_ORDER`,`MENU_ICON`,`MENU_TYPE`) values (1,'系统管理','#','0',1,'icon-desktop','2'),(2,'组织管理','role.do','1',2,NULL,'2'),(4,'会员管理','happuser/listUsers.do','1',4,NULL,'2'),(5,'系统用户','user/listUsers.do','1',3,NULL,'2'),(6,'信息管理','#','0',2,'icon-list-alt','2'),(7,'图片管理','pictures/list.do','6',1,NULL,'2'),(8,'性能监控','druid/index.html','9',1,NULL,'2'),(9,'系统工具','#','0',3,'icon-th','2'),(10,'接口测试','tool/interfaceTest.do','9',2,NULL,'2'),(11,'发送邮件','tool/goSendEmail.do','9',3,NULL,'2'),(12,'置二维码','tool/goTwoDimensionCode.do','9',4,NULL,'2'),(13,'多级别树','tool/ztree.do','9',5,NULL,'2'),(14,'地图工具','tool/map.do','9',6,NULL,'2'),(15,'微信管理','#','0',2,'icon-comments','2'),(16,'文本回复','textmsg/list.do','15',2,NULL,'2'),(17,'应用命令','command/list.do','15',4,NULL,'2'),(18,'图文回复','imgmsg/list.do','15',3,NULL,'2'),(19,'关注回复','textmsg/goSubscribe.do','15',1,NULL,'2'),(20,'在线管理','onlinemanager/list.do','1',5,NULL,'2'),(21,'打印测试','tool/printTest.do','9',7,NULL,'2'),(22,'基础资料','#','0',8,'icon-list-alt','2'),(23,'国家','country/list.do','22',1,NULL,'2'),(24,'省/州','province/list.do','22',2,NULL,'2'),(25,'市/县','city/list.do','22',3,NULL,'2'),(26,'库区','storage/list.do','22',5,NULL,'2'),(27,'库位','locator/list.do','22',6,NULL,'2'),(28,'仓库','stock/list.do','22',4,NULL,'2'),(29,'客户','customer/list.do','22',7,NULL,'2'),(30,'产品','product/list.do','22',8,NULL,'2'),(31,'数据管理','datamgr/list.do','1',10,NULL,'2'),(40,'基础属性','#','0',9,'icon-book','2'),(41,'包装规则','packrule/list.do','40',1,NULL,'2'),(42,'库位分配规则','storageasignrule/list.do','40',2,NULL,'2'),(43,'库存分配规则','stockasignrule/list.do','40',3,NULL,'2'),(44,'拣货规则','pickrule/list.do','40',4,NULL,'2'),(45,'订单属性','orderproperty/list.do','40',6,NULL,'2'),(46,'参数定义','select/list.do','40',7,NULL,'2'),(47,'料号对照表','pnmap/list.do','40',8,NULL,'2'),(48,'库存周转规则','stockturn/list.do','40',5,NULL,'2'),(49,'箱号规则','boxrule/list.do','40',9,NULL,'2'),(50,'入库管理','#','0',10,'icon-signin','2'),(51,'入库单','stockmgrin/list.do','50',1,NULL,'2'),(52,'收货','receivopt/list.do','50',2,NULL,'2'),(53,'分配','assignopt/list.do','50',4,NULL,'2'),(54,'码放','putlocatoropt/list.do','50',5,NULL,'2'),(55,'入库单查询','stockmgrinsch/list.do','50',6,NULL,'2'),(56,'称重量体积','boxwv/list.do','50',3,NULL,NULL),(57,'CS查询','box/list.do','50',7,NULL,'2'),(60,'库存管理','#','0',11,'icon-home','2'),(62,'冻结/释放','freeze/list.do','60',3,NULL,'2'),(63,'库存调整-申请','stockaudit/list.do','60',4,NULL,'2'),(64,'库位转移','locatortf/list.do','60',6,NULL,'2'),(65,'物权转移','prodright/list.do','60',7,NULL,'2'),(66,'库存调整-审核','stockaudit/list2.do','60',5,NULL,'2'),(70,'出库管理','#','0',12,'icon-signout','2'),(71,'出库单','stockmgrout/list.do','70',1,NULL,'2'),(72,'分配','assignout/list.do','70',2,NULL,'2'),(73,'拣货','pickout/list.do','70',3,NULL,'2'),(74,'配载','cargoout/list.do','70',4,NULL,'2'),(75,'发货-出货单','depotout/list.do','70',5,NULL,'2'),(76,'发货-配载单','depotout/list2.do','70',6,NULL,'2'),(80,'报表','#','0',13,'icon-bar-chart','2'),(81,'即时库存','stocknow/list.do','80',1,NULL,'2'),(82,'库存明细','stocknow/list2.do','80',2,NULL,'2'),(83,'入库报表','inbound/list.do','80',3,NULL,'2'),(84,'出库报表','outbound/list.do','80',4,NULL,'2'),(85,'拆箱纪录','breakbox/list.do','80',5,NULL,'2');

/*Table structure for table `ts_role` */

DROP TABLE IF EXISTS `ts_role`;

CREATE TABLE `ts_role` (
  `ROLE_ID` varchar(100) NOT NULL,
  `ROLE_NAME` varchar(100) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(100) DEFAULT NULL,
  `ADD_QX` varchar(255) DEFAULT NULL,
  `DEL_QX` varchar(255) DEFAULT NULL,
  `EDIT_QX` varchar(255) DEFAULT NULL,
  `CHA_QX` varchar(255) DEFAULT NULL,
  `QX_ID` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ts_role` */

insert  into `ts_role`(`ROLE_ID`,`ROLE_NAME`,`RIGHTS`,`PARENT_ID`,`ADD_QX`,`DEL_QX`,`EDIT_QX`,`CHA_QX`,`QX_ID`) values ('0ffb33f48cc14ace975280e01613bde8','用户A','54','4','0','0','0','1048630','0ffb33f48cc14ace975280e01613bde8'),('1','系统管理员','4194294','0','1','1','1','1','1'),('2','超级管理员','1181747989190988005366','1','1048822','50','34','54','2'),('4','用户组','118','0','0','0','0','0',NULL),('55896f5ce3c0494fa6850775a4e29ff6','特级会员','498','7','1048630','0','0','0','55896f5ce3c0494fa6850775a4e29ff6'),('68f23fc0caee475bae8d52244dea8444','中级会员','498','7','0','0','0','0','68f23fc0caee475bae8d52244dea8444'),('7','会员组','498','0','0','0','0','1',NULL),('d7e27529a3894a1eaada0076f8f808ae','管理员','76312332243315673161269286','1','150007913437044369522742','150007913437044369522742','150007913437044369522742','76312332243315673162317878','d7e27529a3894a1eaada0076f8f808ae');

/*Table structure for table `ts_user` */

DROP TABLE IF EXISTS `ts_user`;

CREATE TABLE `ts_user` (
  `USER_ID` varchar(100) NOT NULL,
  `USERNAME` varchar(255) DEFAULT NULL,
  `PASSWORD` varchar(255) DEFAULT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `RIGHTS` varchar(255) DEFAULT NULL,
  `ROLE_ID` varchar(100) DEFAULT NULL,
  `LAST_LOGIN` varchar(255) DEFAULT NULL,
  `IP` varchar(100) DEFAULT NULL,
  `STATUS` varchar(32) DEFAULT NULL,
  `BZ` varchar(255) DEFAULT NULL,
  `SKIN` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(32) DEFAULT NULL,
  `NUMBER` varchar(100) DEFAULT NULL,
  `PHONE` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `del_flag` varchar(1) DEFAULT '0' COMMENT '删除标记0有效，1删除',
  `OUT_CUSTOMER` varchar(32) DEFAULT NULL,
  `CUSTOMER_ID` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ts_user` */

insert  into `ts_user`(`USER_ID`,`USERNAME`,`PASSWORD`,`NAME`,`RIGHTS`,`ROLE_ID`,`LAST_LOGIN`,`IP`,`STATUS`,`BZ`,`SKIN`,`EMAIL`,`NUMBER`,`PHONE`,`del_flag`,`OUT_CUSTOMER`,`CUSTOMER_ID`) values ('1','administrator','195a93754729e783cdc7a7ad105e8f111f1b0bcf','系统管理员','1133671055321055258374707980945218933803269864762743594642571294','1','2018-10-25 00:14:09','127.0.0.1','0','最高统治者','default','admin@main.com','001','18788888888','0',NULL,NULL),('3e7f224a6b43453381aef333a9b03c48','admin','4ee1f7114a60838373fd0712c3367f70dff4cee6','管理员','','d7e27529a3894a1eaada0076f8f808ae','2018-10-28 23:14:50','127.0.0.1','0','系统管理员','default','191130254@qq.com','0','15915413958','0',NULL,NULL),('5d1717b4ec1f48738c6038fc24b64e67','panda','aeacde00cf3dbb79ba2ce5918f3f6331fb455c0d','黄伟','','2','2016-10-19 16:32:52','0:0:0:0:0:0:0:1','0','','default','huangw@qq.com','111','15915413958','1',NULL,NULL);

/*Table structure for table `ts_user_qx` */

DROP TABLE IF EXISTS `ts_user_qx`;

CREATE TABLE `ts_user_qx` (
  `U_ID` varchar(100) NOT NULL,
  `C1` int(10) DEFAULT NULL,
  `C2` int(10) DEFAULT NULL,
  `C3` int(10) DEFAULT NULL,
  `C4` int(10) DEFAULT NULL,
  `Q1` int(10) DEFAULT NULL,
  `Q2` int(10) DEFAULT NULL,
  `Q3` int(10) DEFAULT NULL,
  `Q4` int(10) DEFAULT NULL,
  PRIMARY KEY (`U_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `ts_user_qx` */

insert  into `ts_user_qx`(`U_ID`,`C1`,`C2`,`C3`,`C4`,`Q1`,`Q2`,`Q3`,`Q4`) values ('0ffb33f48cc14ace975280e01613bde8',0,0,0,0,0,0,0,0),('1',1,0,0,0,0,0,0,0),('2',1,1,1,1,1,1,1,1),('55896f5ce3c0494fa6850775a4e29ff6',0,0,0,0,0,0,0,0),('68f23fc0caee475bae8d52244dea8444',0,0,0,0,0,0,0,0),('a312e47001e64704ac3ce89aafff43b9',0,0,0,0,0,0,0,0),('d7e27529a3894a1eaada0076f8f808ae',0,0,0,0,0,0,0,0);

/*Table structure for table `weixin_command` */

DROP TABLE IF EXISTS `weixin_command`;

CREATE TABLE `weixin_command` (
  `COMMAND_ID` varchar(100) NOT NULL,
  `KEYWORD` varchar(255) DEFAULT NULL COMMENT '关键词',
  `COMMANDCODE` varchar(255) DEFAULT NULL COMMENT '应用路径',
  `CREATETIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `STATUS` int(1) NOT NULL COMMENT '状态',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`COMMAND_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `weixin_command` */

insert  into `weixin_command`(`COMMAND_ID`,`KEYWORD`,`COMMANDCODE`,`CREATETIME`,`STATUS`,`BZ`) values ('2636750f6978451b8330874c9be042c2','锁定服务器','rundll32.exe user32.dll,LockWorkStation','2015-05-10 21:25:06',1,'锁定计算机'),('46217c6d44354010823241ef484f7214','打开浏览器','C:/Program Files/Internet Explorer/iexplore.exe','2015-05-09 02:43:02',1,'打开浏览器操作'),('576adcecce504bf3bb34c6b4da79a177','关闭浏览器','taskkill /f /im iexplore.exe','2015-05-09 02:36:48',1,'关闭浏览器操作'),('854a157c6d99499493f4cc303674c01f','关闭QQ','taskkill /f /im qq.exe','2015-05-10 21:25:46',1,'关闭QQ'),('ab3a8c6310ca4dc8b803ecc547e55ae7','打开QQ','D:/SOFT/QQ/QQ/Bin/qq.exe','2015-05-10 21:25:25',1,'打开QQ');

/*Table structure for table `weixin_imgmsg` */

DROP TABLE IF EXISTS `weixin_imgmsg`;

CREATE TABLE `weixin_imgmsg` (
  `IMGMSG_ID` varchar(100) NOT NULL,
  `KEYWORD` varchar(255) DEFAULT NULL COMMENT '关键词',
  `CREATETIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `STATUS` int(11) NOT NULL COMMENT '状态',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  `TITLE1` varchar(255) DEFAULT NULL COMMENT '标题1',
  `DESCRIPTION1` varchar(255) DEFAULT NULL COMMENT '描述1',
  `IMGURL1` varchar(255) DEFAULT NULL COMMENT '图片地址1',
  `TOURL1` varchar(255) DEFAULT NULL COMMENT '超链接1',
  `TITLE2` varchar(255) DEFAULT NULL COMMENT '标题2',
  `DESCRIPTION2` varchar(255) DEFAULT NULL COMMENT '描述2',
  `IMGURL2` varchar(255) DEFAULT NULL COMMENT '图片地址2',
  `TOURL2` varchar(255) DEFAULT NULL COMMENT '超链接2',
  `TITLE3` varchar(255) DEFAULT NULL COMMENT '标题3',
  `DESCRIPTION3` varchar(255) DEFAULT NULL COMMENT '描述3',
  `IMGURL3` varchar(255) DEFAULT NULL COMMENT '图片地址3',
  `TOURL3` varchar(255) DEFAULT NULL COMMENT '超链接3',
  `TITLE4` varchar(255) DEFAULT NULL COMMENT '标题4',
  `DESCRIPTION4` varchar(255) DEFAULT NULL COMMENT '描述4',
  `IMGURL4` varchar(255) DEFAULT NULL COMMENT '图片地址4',
  `TOURL4` varchar(255) DEFAULT NULL COMMENT '超链接4',
  `TITLE5` varchar(255) DEFAULT NULL COMMENT '标题5',
  `DESCRIPTION5` varchar(255) DEFAULT NULL COMMENT '描述5',
  `IMGURL5` varchar(255) DEFAULT NULL COMMENT '图片地址5',
  `TOURL5` varchar(255) DEFAULT NULL COMMENT '超链接5',
  `TITLE6` varchar(255) DEFAULT NULL COMMENT '标题6',
  `DESCRIPTION6` varchar(255) DEFAULT NULL COMMENT '描述6',
  `IMGURL6` varchar(255) DEFAULT NULL COMMENT '图片地址6',
  `TOURL6` varchar(255) DEFAULT NULL COMMENT '超链接6',
  `TITLE7` varchar(255) DEFAULT NULL COMMENT '标题7',
  `DESCRIPTION7` varchar(255) DEFAULT NULL COMMENT '描述7',
  `IMGURL7` varchar(255) DEFAULT NULL COMMENT '图片地址7',
  `TOURL7` varchar(255) DEFAULT NULL COMMENT '超链接7',
  `TITLE8` varchar(255) DEFAULT NULL COMMENT '标题8',
  `DESCRIPTION8` varchar(255) DEFAULT NULL COMMENT '描述8',
  `IMGURL8` varchar(255) DEFAULT NULL COMMENT '图片地址8',
  `TOURL8` varchar(255) DEFAULT NULL COMMENT '超链接8',
  PRIMARY KEY (`IMGMSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `weixin_imgmsg` */

insert  into `weixin_imgmsg`(`IMGMSG_ID`,`KEYWORD`,`CREATETIME`,`STATUS`,`BZ`,`TITLE1`,`DESCRIPTION1`,`IMGURL1`,`TOURL1`,`TITLE2`,`DESCRIPTION2`,`IMGURL2`,`TOURL2`,`TITLE3`,`DESCRIPTION3`,`IMGURL3`,`TOURL3`,`TITLE4`,`DESCRIPTION4`,`IMGURL4`,`TOURL4`,`TITLE5`,`DESCRIPTION5`,`IMGURL5`,`TOURL5`,`TITLE6`,`DESCRIPTION6`,`IMGURL6`,`TOURL6`,`TITLE7`,`DESCRIPTION7`,`IMGURL7`,`TOURL7`,`TITLE8`,`DESCRIPTION8`,`IMGURL8`,`TOURL8`) values ('380b2cb1f4954315b0e20618f7b5bd8f','首页','2015-05-10 20:51:09',1,'图文回复','图文回复标题','图文回复描述','http://a.hiphotos.baidu.com/image/h%3D360/sign=c6c7e73ebc389b5027ffe654b535e5f1/a686c9177f3e6709392bb8df3ec79f3df8dc55e3.jpg','www.baidu.com','','','','','','','','','','','','','','','','','','','','','','','','','','','','');

/*Table structure for table `weixin_textmsg` */

DROP TABLE IF EXISTS `weixin_textmsg`;

CREATE TABLE `weixin_textmsg` (
  `TEXTMSG_ID` varchar(100) NOT NULL,
  `KEYWORD` varchar(255) DEFAULT NULL COMMENT '关键词',
  `CONTENT` varchar(255) DEFAULT NULL COMMENT '内容',
  `CREATETIME` varchar(255) DEFAULT NULL COMMENT '创建时间',
  `STATUS` int(11) DEFAULT NULL COMMENT '状态',
  `BZ` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`TEXTMSG_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `weixin_textmsg` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
