
var rownum = $("#INSTOCK_NO").val();
//保存
function saveDtl(){
	if($("#ROW_NO").val()==""){
		$("#ROW_NO").tips({
			side:3,
			msg:'请输入行号',
			bg:'#AE81FF',
			time:2
		});
		$("#ROW_NO").focus();
		return false;
	}

	/*if($("#CUSTOMER_ID").val()==""){
	 $("#CUSTOMER_ID").tips({
	 side:3,
	 msg:'请输入客户',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#CUSTOMER_ID").focus();
	 return false;
	 }
	 if($("#PRODUCT_ID").val()==""){
	 $("#PRODUCT_ID").tips({
	 side:3,
	 msg:'请输入产品',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#PRODUCT_ID").focus();
	 return false;
	 }
	 if($("#PRODUCT_TYPE").val()==""){
	 $("#PRODUCT_TYPE").tips({
	 side:3,
	 msg:'请输入产品类型',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#PRODUCT_TYPE").focus();
	 return false;
	 }
	 if($("#PACK_RULE").val()==""){
	 $("#PACK_RULE").tips({
	 side:3,
	 msg:'请输入包装规则',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#PACK_RULE").focus();
	 return false;
	 }
	 if($("#PACK_UNIT").val()==""){
	 $("#PACK_UNIT").tips({
	 side:3,
	 msg:'请输入包装单位',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#PACK_UNIT").focus();
	 return false;
	 }
	 if($("#OT_EA").val()==""){
	 $("#OT_EA").tips({
	 side:3,
	 msg:'请输入其他期望数',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#OT_EA").focus();
	 return false;
	 }
	 if($("#OT_RATIO").val()==""){
	 $("#OT_RATIO").tips({
	 side:3,
	 msg:'请输入其他比例',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#OT_RATIO").focus();
	 return false;
	 }
	 if($("#PALLET_EA").val()==""){
	 $("#PALLET_EA").tips({
	 side:3,
	 msg:'请输入托盘期望数',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#PALLET_EA").focus();
	 return false;
	 }
	 if($("#PALLET_RATIO").val()==""){
	 $("#PALLET_RATIO").tips({
	 side:3,
	 msg:'请输入托盘比例',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#PALLET_RATIO").focus();
	 return false;
	 }
	 if($("#BOX_EA").val()==""){
	 $("#BOX_EA").tips({
	 side:3,
	 msg:'请输入箱期望数',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#BOX_EA").focus();
	 return false;
	 }
	 if($("#BOX_RATIO").val()==""){
	 $("#BOX_RATIO").tips({
	 side:3,
	 msg:'请输入箱比例',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#BOX_RATIO").focus();
	 return false;
	 }
	 if($("#INPACK_EA").val()==""){
	 $("#INPACK_EA").tips({
	 side:3,
	 msg:'请输入内期望数',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#INPACK_EA").focus();
	 return false;
	 }
	 if($("#INPACK_RATIO").val()==""){
	 $("#INPACK_RATIO").tips({
	 side:3,
	 msg:'请输入内比例',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#INPACK_RATIO").focus();
	 return false;
	 }
	 if($("#EA_EA").val()==""){
	 $("#EA_EA").tips({
	 side:3,
	 msg:'请输入EA期望数',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#EA_EA").focus();
	 return false;
	 }
	 if($("#EA_RATIO").val()==""){
	 $("#EA_RATIO").tips({
	 side:3,
	 msg:'请输入EA比例',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#EA_RATIO").focus();
	 return false;
	 }
	 if($("#LONG_UT").val()==""){
	 $("#LONG_UT").tips({
	 side:3,
	 msg:'请输入长CM',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#LONG_UT").focus();
	 return false;
	 }
	 if($("#WIDE_UT").val()==""){
	 $("#WIDE_UT").tips({
	 side:3,
	 msg:'请输入宽CM',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#WIDE_UT").focus();
	 return false;
	 }
	 if($("#HIGH_UT").val()==""){
	 $("#HIGH_UT").tips({
	 side:3,
	 msg:'请输入高CM',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#HIGH_UT").focus();
	 return false;
	 }
	 if($("#UNIT_VOLUME").val()==""){
	 $("#UNIT_VOLUME").tips({
	 side:3,
	 msg:'请输入单位体积',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#UNIT_VOLUME").focus();
	 return false;
	 }
	 if($("#TOTAL_VOLUME").val()==""){
	 $("#TOTAL_VOLUME").tips({
	 side:3,
	 msg:'请输入体积',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#TOTAL_VOLUME").focus();
	 return false;
	 }
	 if($("#TOTAL_WEIGHT").val()==""){
	 $("#TOTAL_WEIGHT").tips({
	 side:3,
	 msg:'请输入毛重',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#TOTAL_WEIGHT").focus();
	 return false;
	 }
	 if($("#TOTAL_SUTTLE").val()==""){
	 $("#TOTAL_SUTTLE").tips({
	 side:3,
	 msg:'请输入净重',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#TOTAL_SUTTLE").focus();
	 return false;
	 }
	 if($("#TOTAL_PRICE").val()==""){
	 $("#TOTAL_PRICE").tips({
	 side:3,
	 msg:'请输入总价',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#TOTAL_PRICE").focus();
	 return false;
	 }
	 if($("#UNIT_PRICE").val()==""){
	 $("#UNIT_PRICE").tips({
	 side:3,
	 msg:'请输入单价',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#UNIT_PRICE").focus();
	 return false;
	 }
	 if($("#CURRENCY_TYPE").val()==""){
	 $("#CURRENCY_TYPE").tips({
	 side:3,
	 msg:'请输入币种',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#CURRENCY_TYPE").focus();
	 return false;
	 }
	 if($("#STORAGE_ID").val()==""){
	 $("#STORAGE_ID").tips({
	 side:3,
	 msg:'请输入收货库区',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#STORAGE_ID").focus();
	 return false;
	 }
	 if($("#LOCATOR_ID").val()==""){
	 $("#LOCATOR_ID").tips({
	 side:3,
	 msg:'请输入收货库位',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#LOCATOR_ID").focus();
	 return false;
	 }
	 if($("#CAR_TYPE").val()==""){
	 $("#CAR_TYPE").tips({
	 side:3,
	 msg:'请输入车型',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#CAR_TYPE").focus();
	 return false;
	 }
	 if($("#CAR_PLATE").val()==""){
	 $("#CAR_PLATE").tips({
	 side:3,
	 msg:'请输入车牌号',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#CAR_PLATE").focus();
	 return false;
	 }
	 if($("#SEAL_NO").val()==""){
	 $("#SEAL_NO").tips({
	 side:3,
	 msg:'请输入封条号',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#SEAL_NO").focus();
	 return false;
	 }
	 if($("#TP_HAULIER").val()==""){
	 $("#TP_HAULIER").tips({
	 side:3,
	 msg:'请输入承运人',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#TP_HAULIER").focus();
	 return false;
	 }
	 if($("#FREEZE_CODE").val()==""){
	 $("#FREEZE_CODE").tips({
	 side:3,
	 msg:'请输入冻结拒收代码',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#FREEZE_CODE").focus();
	 return false;
	 }
	 if($("#MGR_SERIAL").val()==""){
	 $("#MGR_SERIAL").tips({
	 side:3,
	 msg:'请输入管理序列号',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#MGR_SERIAL").focus();
	 return false;
	 }
	 if($("#MEMO").val()==""){
	 $("#MEMO").tips({
	 side:3,
	 msg:'请输入备注',
	 bg:'#AE81FF',
	 time:2
	 });
	 $("#MEMO").focus();
	 return false;
	 }*/

	/*$("#Form").submit();
	$("#zhongxin3").hide();
	$("#zhongxin4").show();*/
}