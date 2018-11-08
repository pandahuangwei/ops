<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
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

		if($("#EA_EA").val()==""){
			$("#EA_EA").tips({
				side:3,
				msg:'请输入EA',
				bg:'#AE81FF',
				time:2
			});
			$("#EA_EA").focus();
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


	*/

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function getProdcuctList(customerId) {
		alert(customerId);
		if (customerId ==null || customerId=='') {
			return false;
		}
		//动态删除select中的所有options
		//alert(document.getElementById("PROVINCE_ID").options.length);
		document.getElementById("PRODUCT_ID").options.length=0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockmgrinsch/getProductList.do',
			data: {CUSTOMER_ID:customerId,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#PRODUCT_ID_chzn").remove();
				$("#PRODUCT_ID").append($("<option value=''></option>"));
				for (var k in data) {
					$("#PRODUCT_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
			},
			complete: function (XMLHttpRequest, textStatus) {
				$("#PRODUCT_ID").attr("class","chzn-select");
				$(".chzn-select").chosen({search_contains: true});

			}
		});
	}

	function getLocator(){
		var STORAGE_ID = $.trim($("#STORAGE_ID").val());
		if (STORAGE_ID ==null || STORAGE_ID=='') {
			return false;
		}
		//动态删除select中的所有options
		//alert(document.getElementById("PROVINCE_ID").options.length);
		document.getElementById("LOCATOR_ID").options.length=0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockmgrinsch/getLocatorList.do',
			data: {STORAGE_ID:STORAGE_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#LOCATOR_ID_chzn").remove();
				$("#LOCATOR_ID").append($("<option value=''></option>"));
				for (var k in data) {
					$("#LOCATOR_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
			},
			complete: function (XMLHttpRequest, textStatus) {
				$("#LOCATOR_ID").attr("class","chzn-select");
				$(".chzn-select").chosen({search_contains: true});

			}
		});
	}

	function hadProd(PRODUCT_ID,STOCKMGRIN_ID) {
		    var result = false;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>stockmgrinsch/hadProd.do',
				data: {PRODUCT_ID:PRODUCT_ID,STOCKMGRIN_ID:STOCKMGRIN_ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				async: false,
				success: function(data){
					if("success" != data.result){
						$("#PRODUCT_ID_chzn").remove();
						$("#PRODUCT_ID").removeClass("chzn-select");

						$("#PRODUCT_ID").val('');
						$("#PRODUCT_ID").attr("class","chzn-select");
						var rowno = data.rowno;
						cleanColum();

						$("#PRODUCT_ID").tips({
							side:3,
							msg:'该产品已存在,行号为'+rowno,
							bg:'#AE81FF',
							time:3
						});

						setTimeout("$('#PRODUCT_ID').val('')",2000);
						result =  true;
					}
				},
				complete: function (XMLHttpRequest, textStatus) {
					$("#PRODUCT_ID_chzn").remove();
					$("#PRODUCT_ID").attr("class","chzn-select");
					$(".chzn-select").chosen({search_contains: true});
				}
			});
		return result;
	}

	function cleanColum() {
		$("#LONG_UT").val('');
		$("#WIDE_UT").val('');
		$("#HIGH_UT").val('');
		$("#UNIT_VOLUME").val('');

		$("#TOTAL_VOLUME").val('');

		$("#TOTAL_WEIGHT").val('');
		$("#TOTAL_SUTTLE").val('');
		$("#UNIT_PRICE").val('');
		$("#TOTAL_PRICE").val('');

		//设置select 选中的索引：
		//$("#PRODUCT_TYPE ").get(0).selectedIndex=2;//index为索引值
		$("#PRODUCT_TYPE ").val('');
		$("#PACK_RULE ").val('');

		/*$("#OT_EA").val('');
		$("#PALLET_EA").val('');
		$("#BOX_EA").val('');
		$("#INPACK_EA").val('');
		$("#EA_EA").val('');*/
	}

	function getPackRule() {
		var PACK_RULE = $.trim($("#PACK_RULE").val());
		if (PACK_RULE == null || PACK_RULE == '') {
			return false;
		}
		var STOCKMGRIN_ID = $.trim($("#STOCKMGRIN_ID").val());
		if(STOCKMGRIN_ID == null || STOCKMGRIN_ID == '') {
			alert("系统超时,请刷新页面重新操作...");
			return false;
		}

		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockmgrinsch/getPackRule.do',
			data: {PACK_RULE: PACK_RULE, tm: new Date().getTime()},
			dataType: 'json',
			cache: false,
			success: function (data) {
				var trs = "";
				for (var k in data) {
					//console.log(data[k]);
					var json = data[k];
					var lenth = json.length;

					for (var i = 0; i < lenth; i++) {

						//控制输入框的可填
						// B.EA_NUM,B.INPACK_NUM,B.BOX_NUM,B.PALLET_NUM ,B.OTHER_NUM,
						//B.EA_IN,B.INPACK_IN,B.BOX_IN,B.PALLET_IN ,B.OTHER_IN
						var EA_IN = json[i]['EA_IN'];
						var INPACK_IN = json[i]['INPACK_IN'];
						var BOX_IN = json[i]['BOX_IN'];
						var PALLET_IN = json[i]['PALLET_IN'];
						var OTHER_IN = json[i]['OTHER_IN'];

                        var EA_NUM = json[i]['EA_NUM'];
						var INPACK_NUM = json[i]['INPACK_NUM'];
						var BOX_NUM = json[i]['BOX_NUM'];
						var PALLET_NUM = json[i]['PALLET_NUM'];
						var OTHER_NUM = json[i]['OTHER_NUM'];


						setEA_NUM(EA_NUM,INPACK_NUM ,BOX_NUM, PALLET_NUM,OTHER_NUM );
						setEA_IN(EA_IN,INPACK_IN ,BOX_IN, PALLET_IN,OTHER_IN );

						cleanEa();
						//OT_RATIO
						setEa(EA_IN, INPACK_IN, BOX_IN, PALLET_IN, OTHER_IN);
						setRatio(INPACK_IN,BOX_IN,PALLET_IN,OTHER_IN);

					}
				}

				$("#table_orderproperty").append(trs);
				$('.date-picker').datepicker({autoclose: true});
			},
			complete: function (XMLHttpRequest, textStatus) {
				//alert($("#table_orderproperty tr").length);
				//如果返回值没有空的时候
				//添加双击事件
			}/*,
			 error: function(){
			 trs += "<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>" ;
			 $("#table_orderproperty").append(trs);
			 }*/
		});

	}

	function setEA_NUM(EA_NUM,INPACK_NUM ,BOX_NUM, PALLET_NUM,OTHER_NUM ) {
		$("#EA_NUM_1").val(EA_NUM);
		$("#INPACK_NUM_1").val(INPACK_NUM);
		$("#BOX_NUM_1").val(BOX_NUM);
		$("#PALLET_NUM_1").val(PALLET_NUM);
		$("#OTHER_NUM_1").val(OTHER_NUM);
	}

	function setEA_IN(EA_IN,INPACK_IN ,BOX_IN, PALLET_IN,OTHER_IN ) {
		$("#EA_IN_1").val(EA_IN);
		$("#INPACK_IN_1").val(INPACK_IN);
		$("#BOX_IN_1").val(BOX_IN);
		$("#PALLET_IN_1").val(PALLET_IN);
		$("#OTHER_IN_1").val(OTHER_IN);
	}

	function cleanEa() {
		$("#EA_EA").val('');
		$("#EA_EA").attr("placeholder","0");

		$("#INPACK_EA").removeAttr("readOnly");
		$("#INPACK_EA").val('');
		$("#INPACK_EA").attr("placeholder","0");
		$("#INPACK_RATIO").val('');
		$("#INPACK_RATIO").removeAttr("readOnly");

		$("#BOX_EA").val('');
		$("#BOX_EA").removeAttr("readOnly");
		$("#BOX_EA").attr("placeholder","0");
		$("#BOX_RATIO").val('');
		$("#BOX_RATIO").removeAttr("readOnly");

		$("#PALLET_EA").val('');
		$("#PALLET_EA").removeAttr("readOnly");
		$("#PALLET_EA").attr("placeholder","0");
		$("#PALLET_RATIO").val('');
		$("#PALLET_RATIO").removeAttr("readOnly");

		$("#OT_EA").val('');
		$("#OT_EA").removeAttr("readOnly");
		$("#OT_EA").attr("placeholder","0");
		$("#OT_RATIO").val('');
		$("#OT_RATIO").removeAttr("readOnly");
	}

	function setRatio(INPACK_IN,BOX_IN,PALLET_IN,OTHER_IN) {
		if(INPACK_IN ==1 && BOX_IN !=1 && PALLET_IN !=1 && OTHER_IN!= 1 ) {
			$("#INPACK_RATIO").val(1);
			return false;
		}

		if(INPACK_IN !=1 && BOX_IN ==1 && PALLET_IN !=1 && OTHER_IN!= 1 ) {
			$("#BOX_RATIO").val(1);
			return false;
		}

		if(INPACK_IN !=1 && BOX_IN !=1 && PALLET_IN ==1 && OTHER_IN!= 1 ) {
			$("#PALLET_RATIO").val(1);
			return false;
		}

		if(INPACK_IN !=1 && BOX_IN !=1 && PALLET_IN !=1 && OTHER_IN == 1 ) {
			$("#OT_RATIO").val(1);
			return false;
		}

	}

	function setEa(EA_IN, INPACK_IN, BOX_IN, PALLET_IN, OTHER_IN) {
		/*if(EA_IN != 1) {
			$("#EA_EA").val('');
			$("#EA_EA").attr("readOnly","readOnly");
			$("#EA_EA").attr("placeholder"," ");

			$("#EA_RATIO").val('');
			$("#EA_RATIO").attr("readOnly","readOnly");
		}*/

		if(INPACK_IN != 1) {
			$("#INPACK_EA").val('');
			$("#INPACK_EA").attr("readOnly","readOnly");
			$("#INPACK_EA").attr("placeholder"," ");

			$("#INPACK_RATIO").val('');
			$("#INPACK_RATIO").attr("readOnly","readOnly");
		}

		if(BOX_IN != 1) {
			$("#BOX_EA").val('');
			$("#BOX_EA").attr("readOnly","readOnly");
			$("#BOX_EA").attr("placeholder"," ");

			$("#BOX_RATIO").val('');
			$("#BOX_RATIO").attr("readOnly","readOnly");
		}

		if(PALLET_IN != 1) {
			$("#PALLET_EA").val('');
			$("#PALLET_EA").attr("readOnly","readOnly");
			$("#PALLET_EA").attr("placeholder"," ");

			$("#PALLET_RATIO").val('');
			$("#PALLET_RATIO").attr("readOnly","readOnly");
		}

		if(OTHER_IN != 1) {
			$("#OT_EA").val('');
			$("#OT_EA").attr("readOnly","readOnly");
			$("#OT_EA").attr("placeholder"," ");

			$("#OT_RATIO").val('');
			$("#OT_RATIO").attr("readOnly","readOnly");
		}

	}

	function getProdProperty() {
		var PRODUCT_ID = $.trim($("#PRODUCT_ID").val());
		if (PRODUCT_ID == null || PRODUCT_ID == '') {
			return false;
		}
		var STOCKMGRIN_ID = $.trim($("#STOCKMGRIN_ID").val());
		if(STOCKMGRIN_ID == null || STOCKMGRIN_ID == '') {
			alert("系统超时,请刷新页面重新操作...");
			return false;
		}

		var auditFlag = hadProd(PRODUCT_ID,STOCKMGRIN_ID);
		//alert(auditFlag);
		if (auditFlag) {
			return false;
		}

		loadCnt = 0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockmgrinsch/getProdProperty.do',
			data: {PRODUCT_ID: PRODUCT_ID, tm: new Date().getTime()},
			dataType: 'json',
			cache: false,
			success: function (data) {
				var trs = "";
				for (var k in data) {
					//console.log(data[k]);
					var json = data[k];
					var lenth = json.length;

					for (var i = 0; i < lenth; i++) {
						//alert(json[i]['SRC_SORT']);

						$("#EA_NUM_1").val(json[i]['EA_NUM']);
						$("#INPACK_NUM_1").val(json[i]['INPACK_NUM']);
						$("#BOX_NUM_1").val(json[i]['BOX_NUM']);
						$("#PALLET_NUM_1").val(json[i]['PALLET_NUM']);
						$("#OTHER_NUM_1").val(json[i]['OTHER_NUM']);

						//长、宽、高
						var LONG_UNIT = json[i]['LONG_UNIT'];
						var WIDE_UNIT = json[i]['WIDE_UNIT'];
						var HIGH_UNIT = json[i]['HIGH_UNIT'];
						var UNIT_VOLUME = LONG_UNIT*WIDE_UNIT*HIGH_UNIT;
						//毛重、净重、单价
						var TOTAL_WEIGHT = json[i]['TOTAL_WEIGHT'];
						var TOTAL_SUTTLE = json[i]['TOTAL_SUTTLE'];
						var UNIT_PRICE = json[i]['UNIT_PRICE'];

						var EA_EA = json[i]['EA_NUM'];

						$("#LONG_UT").val(LONG_UNIT);
						$("#WIDE_UT").val(WIDE_UNIT);
						$("#HIGH_UT").val(HIGH_UNIT);
						$("#UNIT_VOLUME").val(UNIT_VOLUME);

						var TOTAL_VOLUME =EA_EA*UNIT_VOLUME;
						$("#TOTAL_VOLUME").val(TOTAL_VOLUME);

						$("#TOTAL_WEIGHT").val(TOTAL_WEIGHT);
						$("#TOTAL_SUTTLE").val(TOTAL_SUTTLE);
						$("#UNIT_PRICE").val(UNIT_PRICE);
						$("#TOTAL_PRICE").val(json[i]['TOTAL_PRICE']);

						//设置select 选中的索引：
                        //$("#PRODUCT_TYPE ").get(0).selectedIndex=2;//index为索引值
						$("#PRODUCT_TYPE ").val(json[i]['PRODUCT_TYPE']);
						$("#PACK_RULE ").val(json[i]['RULE_PACK']);

					/*	$("#OT_EA").val(json[i]['OTHER_NUM']);
						$("#PALLET_EA").val(json[i]['PALLET_NUM']);
						$("#BOX_EA").val(json[i]['BOX_NUM']);
						$("#INPACK_EA").val(json[i]['INPACK_NUM']);
						$("#EA_EA").val(EA_EA);*/
						$("#WEIGHT_UNIT_TYPE ").val(json[i]['WEIGHT_UNIT_TYPE']);
						$("#SUTTLE_UNIT_TYPE ").val(json[i]['SUTTLE_UNIT_TYPE']);
						$("#VOLUME_UNIT_TYPE ").val(json[i]['VOLUME_UNIT_TYPE']);
						$("#UNIT_WEIGHT ").val(json[i]['UNIT_WEIGHT']);
						$("#UNIT_SUTTLE ").val(json[i]['UNIT_SUTTLE']);

						//控制输入框的可填
						//B.EA_IN,B.INPACK_IN,B.BOX_IN,B.PALLET_IN ,B.OTHER_IN
						var EA_IN = json[i]['EA_IN'];
						var INPACK_IN = json[i]['INPACK_IN'];
						var BOX_IN = json[i]['BOX_IN'];
						var PALLET_IN = json[i]['PALLET_IN'];
						var OTHER_IN = json[i]['OTHER_IN'];

						var EA_NUM = json[i]['EA_NUM'];
						var INPACK_NUM = json[i]['INPACK_NUM'];
						var BOX_NUM = json[i]['BOX_NUM'];
						var PALLET_NUM = json[i]['PALLET_NUM'];
						var OTHER_NUM = json[i]['OTHER_NUM'];


						setEA_NUM(EA_NUM,INPACK_NUM ,BOX_NUM, PALLET_NUM,OTHER_NUM );
						setEA_IN(EA_IN,INPACK_IN ,BOX_IN, PALLET_IN,OTHER_IN );
						cleanEa();
						//OT_RATIO
						setEa(EA_IN, INPACK_IN, BOX_IN, PALLET_IN, OTHER_IN);
						setRatio(INPACK_IN,BOX_IN,PALLET_IN,OTHER_IN);

					}
				}

				/*$("#table_orderproperty").append(trs);
				$('.date-picker').datepicker({autoclose: true});*/
			},
			complete: function (XMLHttpRequest, textStatus) {
				//alert($("#table_orderproperty tr").length);
				//如果返回值没有空的时候
				//添加双击事件
			}/*,
			 error: function(){
			 trs += "<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>" ;
			 $("#table_orderproperty").append(trs);
			 }*/
		});
	}

</script>
	</head>
<body>
	<form action="stockmgrinsch/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="STOCKDTLMGRIN_ID" id="STOCKDTLMGRIN_ID" value="${pd.STOCKDTLMGRIN_ID}"/>
		<%--入库单ID--%>
		<input type="hidden" name="STOCKMGRIN_ID" id="STOCKMGRIN_ID" value="${pd.STOCKMGRIN_ID}"/>
		<input type="hidden" name="P_ID" id="P_ID" value="${pd.P_ID}"/>
		<input type="hidden" name="HAD_PRODS" id="HAD_PRODS" value="${pd.HAD_PRODS}"/>

		<input type="hidden" name="EA_NUM_1" id="EA_NUM_1" value="${pd.EA_NUM_1}"/>
		<input type="hidden" name="INPACK_NUM_1" id="INPACK_NUM_1" value="${pd.INPACK_NUM_1}"/>
		<input type="hidden" name="BOX_NUM_1" id="BOX_NUM_1" value="${pd.BOX_NUM_1}"/>
		<input type="hidden" name="PALLET_NUM_1" id="PALLET_NUM_1" value="${pd.PALLET_NUM_1}"/>
		<input type="hidden" name="OTHER_NUM_1" id="OTHER_NUM_1" value="${pd.OTHER_NUM_1}"/>

		<input type="hidden" name="EA_IN_1" id="EA_IN_1" value="${pd.EA_IN_1}"/>
		<input type="hidden" name="INPACK_IN_1" id="INPACK_IN_1" value="${pd.INPACK_IN_1}"/>
		<input type="hidden" name="BOX_IN_1" id="BOX_IN_1" value="${pd.BOX_IN_1}"/>
		<input type="hidden" name="PALLET_IN_1" id="PALLET_IN_1" value="${pd.PALLET_IN_1}"/>
		<input type="hidden" name="OTHER_IN_1" id="OTHER_IN_1" value="${pd.OTHER_IN_1}"/>

		<input type="hidden" name="WEIGHT_UNIT_TYPE" id="WEIGHT_UNIT_TYPE" value="${pd.WEIGHT_UNIT_TYPE}"/>
		<input type="hidden" name="SUTTLE_UNIT_TYPE" id="SUTTLE_UNIT_TYPE" value="${pd.SUTTLE_UNIT_TYPE}"/>
		<input type="hidden" name="VOLUME_UNIT_TYPE" id="VOLUME_UNIT_TYPE" value="${pd.VOLUME_UNIT_TYPE}"/>
		<input type="hidden" name="UNIT_WEIGHT" id="UNIT_WEIGHT" value="${pd.UNIT_WEIGHT}"/>
		<input type="hidden" name="UNIT_SUTTLE" id="UNIT_SUTTLE" value="${pd.UNIT_SUTTLE}"/>


		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>入库单明细</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-toolbox">
						<div class="btn-toolbar">
							<div class="btn-group">
								<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</a>
							</div>
						</div>
					</div>

					<div class="widget-main padding-3">
						<div  data-height="800">
							<div class="content">
								<div class="container-fluid">
									<div class="row-fluid">
										<div class="span7">
											<table id="table_report0" class="table table-striped table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">行号</td>
													<td><input type="number" readOnly="readOnly" name="ROW_NO" id="ROW_NO" value="${pd.ROW_NO}" maxlength="32" placeholder="" title="行号"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">客户</td>
													<td style="vertical-align:top;">
														<select  name="CUSTOMER_ID" id="CUSTOMER_ID"
																 <c:if test="${not empty pd.CUSTOMER_ID }">disabled='disabled'</c:if>
																 onchange="getProdcuctList(this)"
																style="vertical-align:top;" >
															<c:forEach items="${customerList}" var="cnt">
																<option value="${cnt.id }"
																<c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
														<c:if test="${not empty pd.CUSTOMER_ID }">
															<input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/>
														</c:if>
													</td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">产品<span class="red">*</span></td>
													<td  style="vertical-align:top;">
														<%--<input type="text" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}" maxlength="32" placeholder="" title="产品"/>--%>
														<select  class="chzn-select"  name="PRODUCT_ID" id="PRODUCT_ID" style="vertical-align:top;" data-placeholder=" "
																 <c:if test="${not empty pd.STOCKDTLMGRIN_ID }">disabled='disabled'</c:if>
														    onchange="getProdProperty()">
															<option value=""></option>
															<c:forEach items="${allProductList}" var="cnt">
																<option value="${cnt.id }"
																		<c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
															<c:if test="${not empty pd.STOCKDTLMGRIN_ID }">
																<input type="hidden" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}"/>
															</c:if>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">产品类型</td>
													<%--<td><input type="text" name="PRODUCT_TYPE" id="PRODUCT_TYPE" value="${pd.PRODUCT_TYPE}" maxlength="32" placeholder="" title="产品类型"/></td>--%>
													<td style="vertical-align:top;">
														<select name="PRODUCT_TYPE" id="PRODUCT_TYPE" style="vertical-align:top;" data-placeholder=" ">
															<option value=""></option>
															<c:forEach items="${productTypeList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.PRODUCT_TYPE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">包装规则<span class="red">*</span></td>
													<%--<td><input type="text" name="PACK_RULE" id="PACK_RULE" value="${pd.PACK_RULE}" maxlength="32" placeholder="" title="包装规则"/></td>--%>
													<td style="vertical-align:top;">
														<select name="PACK_RULE" id="PACK_RULE" data-placeholder=" " style="vertical-align:top;" onchange="getPackRule();">
															<option value=""></option>
															<c:forEach items="${packRuleList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.PACK_RULE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>



													<td style="width:70px;text-align: right;padding-top: 13px;">包装单位</td>
													<%--<td><input type="text" name="PACK_UNIT" id="PACK_UNIT" value="${pd.PACK_UNIT}" maxlength="32" placeholder="" title="包装单位"/></td>--%>

													<td style="vertical-align:top;">
														<select  class="chzn-select"  name="PACK_UNIT" id="PACK_UNIT" data-placeholder=" " style="vertical-align:top;">
															<option value=""></option>
															<c:forEach items="${packUnitList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.PACK_UNIT }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

												</tr>

												<tr>
													<td style="width:20px;text-align: left;padding-top: 13px;"></td>
													<td class="span1">期望数&nbsp; &nbsp; &nbsp; &nbsp; 比例</td>

													<td style="width:20px;text-align: left;padding-top: 13px;"></td>
													<td class="span1"></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;"><strong>其他</strong></td>
													<td>
														<input class ="input-small"  min="0" onkeyup="calcOT()" type="number" name="OT_EA" id="OT_EA" value="${pd.OT_EA}"
															   <c:if test="${pd.OTHER_IN_1 != 1}">readOnly='readOnly'</c:if>
															   maxlength="64" placeholder="0"  title="其他期望数"/>
														 <input class ="input-small"  min="0" style="background-color: #d2f2e7" type="number" name="OT_RATIO" id="OT_RATIO" value="${pd.OT_RATIO}"
																<c:if test="${pd.OTHER_IN_1 != 1}">readOnly='readOnly'</c:if>
																maxlength="32"  title="其他比例"/>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">长CM</td>
													<td><input type="number" min="0" name="LONG_UT" id="LONG_UT" value="${pd.LONG_UT}" maxlength="32" placeholder="0" title="长CM"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;"><strong>托盘</strong></td>
													<td>
														<input  class ="input-small"  min="0" onkeyup="calcPALLET()" onblur="calcPALLET();" type="number" name="PALLET_EA" id="PALLET_EA" value="${pd.PALLET_EA}"
																<c:if test="${pd.PALLET_IN_1 != 1}">readOnly='readOnly'</c:if>
																maxlength="32" placeholder="0"   title="托盘期望数"/>

														<input class ="input-small"  min="0" style="background-color: #d2f2e7" type="number" name="PALLET_RATIO" id="PALLET_RATIO" value="${pd.PALLET_RATIO}"
															   <c:if test="${pd.PALLET_IN_1 != 1}">readOnly='readOnly'</c:if>
															   maxlength="32"  title="托盘比例"/>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">宽CM</td>
													<td><input type="number" min="0" name="WIDE_UT" id="WIDE_UT" value="${pd.WIDE_UT}" maxlength="32" placeholder="0" title="宽CM"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;"><strong>箱</strong></td>
													<td><input class ="input-small"  min="0" onkeyup="calcBOX();" onblur="calcBOX();" type="number" name="BOX_EA" id="BOX_EA" value="${pd.BOX_EA}"
															   <c:if test="${pd.BOX_IN_1 != 1}">readOnly='readOnly'</c:if>
															   maxlength="32"  placeholder="0"  title="箱期望数"/>
														<input class ="input-small" min="0" type="number" style="background-color: #d2f2e7" name="BOX_RATIO" id="BOX_RATIO" value="${pd.BOX_RATIO}"
															   <c:if test="${pd.BOX_IN_1 != 1}">readOnly='readOnly'</c:if>
															   maxlength="32"  title="箱比例"/>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">高CM</td>
													<td><input type="number"  min="0" name="HIGH_UT" id="HIGH_UT" value="${pd.HIGH_UT}" maxlength="32" placeholder="0" title="高CM"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;"><strong>内包装</strong></td>
													<td><input class ="input-small"  min="0" onkeyup="calcINPACK()" onblur="calcINPACK()" type="number" name="INPACK_EA" id="INPACK_EA" value="${pd.INPACK_EA}"
															   <c:if test="${pd.INPACK_IN_1 != 1}">readOnly='readOnly'</c:if>
															   maxlength="32"  placeholder="0"  title="内期望数"/>
														<input class ="input-small"  min="0" type="number" style="background-color: #d2f2e7" name="INPACK_RATIO" id="INPACK_RATIO" value="${pd.INPACK_RATIO}"
															   <c:if test="${pd.INPACK_IN_1 != 1}">readOnly='readOnly'</c:if>
															   maxlength="32"  title="内比例"/>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">单位体积CBM</td>
													<td><input type="number" min="0" name="UNIT_VOLUME" id="UNIT_VOLUME" value="${pd.UNIT_VOLUME}" maxlength="32" placeholder="0" title="单位体积"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;"><strong>EA数<span class="red">*</span></strong></td>
													<td><input class ="input-small"  min="0" type="number" name="EA_EA" id="EA_EA" value="${pd.EA_EA}" maxlength="32" placeholder="0"  title="EA期望数"/>
														<input class ="input-small"  min="0" type="number" style="background-color: #d2f2e7" name="EA_RATIO" id="EA_RATIO" value="${pd.EA_RATIO}" maxlength="32" title="EA比例"/>
													</td>


												    <td style="width:70px;text-align: right;padding-top: 13px;">体积CBM</td>
												    <td><input type="number"  min="0" name="TOTAL_VOLUME" id="TOTAL_VOLUME" value="${pd.TOTAL_VOLUME}" maxlength="32"  placeholder="0" title="体积"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">收货库区</td>
													<%--<td><input type="text" name="STORAGE_ID" id="STORAGE_ID" value="${pd.STORAGE_ID}" maxlength="32" title="收货库区"/></td>--%>
													<td style="vertical-align:top;">
														<select  class="chzn-select" name="STORAGE_ID" id="STORAGE_ID" style="vertical-align:top;" onchange="getLocator()">
															<c:forEach items="${storageList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.STORAGE_CODE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">毛重</td>
													<td><input type="number"  min="0" name="TOTAL_WEIGHT" id="TOTAL_WEIGHT" value="${pd.TOTAL_WEIGHT}" maxlength="32" placeholder="0" title="毛重"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">收货库位</td>
													<%--<td><input type="text" name="LOCATOR_ID" id="LOCATOR_ID" value="${pd.LOCATOR_ID}" maxlength="32" title="收货库位"/></td>--%>
													<td style="vertical-align:top;">
														<select  name="LOCATOR_ID" id="LOCATOR_ID" style="vertical-align:top;" data-placeholder=" ">
															<option value=""></option>
															<c:forEach items="${locatorList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_ID }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">净重</td>
													<td><input type="number"  min="0" name="TOTAL_SUTTLE" id="TOTAL_SUTTLE" value="${pd.TOTAL_SUTTLE}" maxlength="32" placeholder="0" title="净重"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">总价</td>
													<td><input type="number"  min="0" name="TOTAL_PRICE" id="TOTAL_PRICE" value="${pd.TOTAL_PRICE}" maxlength="32" placeholder="0" title="总价"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">单价</td>
													<td><input type="text"  min="0" name="UNIT_PRICE" id="UNIT_PRICE" value="${pd.UNIT_PRICE}" maxlength="32" placeholder="0" title="单价"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">车型</td>
													<%--<td><input type="text" name="CAR_TYPE" id="CAR_TYPE" value="${pd.CAR_TYPE}" maxlength="32"  title="车型"/></td>--%>
													<td style="vertical-align:top;">
														<select  class="chzn-select" name="CAR_TYPE" id="CAR_TYPE" style="vertical-align:top;">
															<c:forEach items="${carTypeList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.CAR_TYPE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">币种</td>
													<%--<td><input type="text" name="CURRENCY_TYPE" id="CURRENCY_TYPE" value="${pd.CURRENCY_TYPE}" maxlength="32"  title="币种"/></td>--%>
													<td style="vertical-align:top;">
														<select  class="chzn-select" name="CURRENCY_TYPE" id="CURRENCY_TYPE" style="vertical-align:top;" >
															<c:forEach items="${currencyTypeList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.CURRENCY_TYPE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">集装箱号</td>
													<td><input type="text" name="CONTAINER_NO" id="CONTAINER_NO" value="${pd.CONTAINER_NO}" maxlength="32"  title="集装箱号"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">车牌号</td>
													<td><input type="text" name="CAR_PLATE" id="CAR_PLATE" value="${pd.CAR_PLATE}" maxlength="32" placeholder="" title="车牌号"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">承运人</td>
													<%--<td><input type="text" name="TP_HAULIER" id="TP_HAULIER" value="${pd.TP_HAULIER}" maxlength="32" title="承运人"/></td>--%>
													<td style="vertical-align:top;">
														<select  class="chzn-select" name="TP_HAULIER" id="TP_HAULIER" style="vertical-align:top;" data-placeholder=" "  >
															<c:forEach items="${tpHaulierList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.TP_HAULIER }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">封条号</td>
													<td><input type="text" name="SEAL_NO" id="SEAL_NO" value="${pd.SEAL_NO}" maxlength="32"  title="封条号"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">冻结/拒收代码</td>
													<td><%--<input type="text" name="FREEZE_CODE" id="FREEZE_CODE" value="${pd.FREEZE_CODE}" maxlength="32" placeholder="" title="冻结拒收代码"/>--%>
														<select  class="chzn-select" name="FREEZE_CODE" id="FREEZE_CODE" style="vertical-align:top;" data-placeholder=" "  >
															<c:forEach items="${freeRejectList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.FREEZE_CODE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">管理序列号</td>
													<td><input type="text" name="MGR_SERIAL" id="MGR_SERIAL" value="${pd.MGR_SERIAL}" maxlength="32" placeholder="" title="管理序列号"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">备注</td>
													<td colspan="7"><input class="span11" type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder="" title="备注"/></td>
												</tr>
												</table>
										</div>


										<div class="span5">
											批次属性
											<div class="widget-box " style="overflow:auto">
												<div class="widget-body">
													<div class="widget-main  no-padding">
														<div class="slim-scroll" data-height="800">
															<div class="content">
																<table id="table_orderproperty"  class="table table-striped  table-hover table-condensed">
																	<tbody>

																	<c:choose>
																		<c:when test="${not empty inOrderList}">
																			<c:if test="${QX.cha == 1 }">
																				<c:forEach items="${inOrderList}" var="var" varStatus="vs">
																					<tr>
																						<td class='center' style="width: 30px;">${vs.index+1}</td>
																						<td style="width: 120px;">${var.PROPERTY_KEY}</td>
																						<td class="input-sm">
																								<%--如果属性排序小于等于10，则是时间输入字段--%>
																							<c:if test="${var.SRC_SORT <= 10}">
																								<input class="date-picker"
																									   name="${var.PROPERTY_COLUM}"
																									   id="${var.PROPERTY_COLUM}"
																									   value="${var.PROPERTY_VALUE}" type="text"
																									   data-date-format="yyyy-mm-dd" readonly="readonly"/>
																							</c:if>
																								<%--如果属性排序大于10 小于等于20，则是数字输入字段--%>
																							<c:if test="${var.SRC_SORT >10 && var.SRC_SORT <=20}">

																								<input type="number"
																									   name="${var.PROPERTY_COLUM}"
																									   id="${var.PROPERTY_COLUM}"
																									   value="${var.PROPERTY_VALUE}"
																									   maxlength="32"
																									   placeholder="0"/>
																							</c:if>

																								<%--如果属性排序大于10 小于等于20，则是数字输入字段--%>
																							<c:if test="${var.SRC_SORT >20}">
																								<input type="text"
																									   name="${var.PROPERTY_COLUM}"
																									   id="${var.PROPERTY_COLUM}"
																									   value="${var.PROPERTY_VALUE}"
																									   maxlength="32"/>
																							</c:if>

																						</td>
																					</tr>

																				</c:forEach>
																			</c:if>
																		</c:when>
																		<c:otherwise>
																			<tr class="main_info">
																				<td colspan="100" class="center">没有订单属性数据</td>
																			</tr>
																		</c:otherwise>
																	</c:choose>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
	   <%-- <script src="static/1.9.1/jquery.min.js"></script>--%>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->

	    <script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->

		<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
		<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
		<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>

		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen({search_contains: true});
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker({autoclose: true});

			// scrollables
			$('.slim-scroll').each(function () {
				var $this = $(this);
				$this.slimScroll({
					//size:'50px',
					height: $this.data('height') || 100,
					railVisible:true,

					/*  height:'100px',//容器高度,默认250px
					 size:'10px',//滚动条宽度,默认7px
					 position:'left',//滚动条位置,可选值:left,right,默认right
					 color:'#abc',//滚动条颜色,默认#000000
					 alwaysVisible:true,//是否禁用隐藏滚动条,默认false
					 distance:'10px,'//距离边框距离,位置由position参数决定,默认1px
					 start:'.p',//滚动条初始位置,可选值top,bottom,$(selector)--内容元素位置,默认top
					 wheelStep:'12px',滚动条滚动值,默认10px
					 railVisible:true,//滚动条背景轨迹,默认false
					 railColor:'#005612',//滚动条背景轨迹颜色,默认#333333
					 railOpacity:0.8,//滚动条背景轨迹透明度,默认0.2
					 allowPageScroll:true,//滚动条滚动到顶部或底部时是否允许页面滚动,默认false
					 scrollTo:'50px',//跳转到指定的滚动值。可以呼吁任何元素slimScroll已经启用了吗(没试过)
					 scrollBy:'50px'增加/减少当前滚动值由指定的数量(正面或负面)。可以呼吁任何元素slimScroll已经启用(没试过)
					 disableFadeOut:true,//是否禁用鼠标在内容处一定时间不动隐藏滚动条,当设置alwaysVisible为true时该参数无效,默认false
					 touchScrollStep:1000,//可以设置不同的触摸滚动事件的敏感性。负数反转方向滚动,默认200*/

				});
			});
			
		});

		//	setEA_NUM(EA_NUM,INPACK_NUM ,BOX_NUM, PALLET_NUM,OTHER_NUM );
		// setEA_IN(EA_IN,INPACK_IN ,BOX_IN, PALLET_IN,OTHER_IN );

		function calcBOX() {
			var EA_IN_1 = $("#EA_IN_1").val();
			var INPACK_IN_1 = $("#INPACK_IN_1").val();
			var BOX_IN_1 = $("#BOX_IN_1").val();

			var EA_NUM_1 = $("#EA_NUM_1").val();
			var INPACK_NUM_1 = $("#INPACK_NUM_1").val();
			var BOX_NUM_1 = $("#BOX_NUM_1").val();

			if(BOX_IN_1 != 1) {
				return false;
			}

			if (BOX_IN_1 == 1 && INPACK_IN_1 == 1 ) {
				calc21(EA_IN_1,INPACK_IN_1,BOX_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1);
				if("CS" ==$("#WEIGHT_UNIT_TYPE").val() || "CS" ==$("#SUTTLE_UNIT_TYPE").val()|| "CS" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (BOX_IN_1 == 1 ) {
				calc2(EA_IN_1,INPACK_IN_1,BOX_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1);
				if("CS" ==$("#WEIGHT_UNIT_TYPE").val() || "CS" ==$("#SUTTLE_UNIT_TYPE").val()|| "CS" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

		}

		function calc2(EA_IN_1,INPACK_IN_1,BOX_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1) {
			var cnt = $("#BOX_EA").val();
			$("#EA_EA").val(cnt*BOX_NUM_1*EA_NUM_1);
		}

		function calc21(EA_IN_1,INPACK_IN_1,BOX_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1) {
			var cnt = $("#BOX_EA").val();
			$("#INPACK_EA").val(cnt*BOX_NUM_1);
			$("#EA_EA").val(cnt*BOX_NUM_1*INPACK_NUM_1*EA_NUM_1);
		}


		function calcPALLET() {
			var EA_IN_1 = $("#EA_IN_1").val();
			var INPACK_IN_1 = $("#INPACK_IN_1").val();
			var BOX_IN_1 = $("#BOX_IN_1").val();
			var PALLET_IN_1 = $("#PALLET_IN_1").val();

			var EA_NUM_1 = $("#EA_NUM_1").val();
			var INPACK_NUM_1 = $("#INPACK_NUM_1").val();
			var BOX_NUM_1 = $("#BOX_NUM_1").val();
			var PALLET_NUM_1 = $("#PALLET_NUM_1").val();

			if(PALLET_IN_1 != 1) {
				return false;
			}

			if (PALLET_IN_1 == 1&& BOX_IN_1 == 1&& INPACK_IN_1 == 1 ) {
				calc321(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1);
				if("PL" ==$("#WEIGHT_UNIT_TYPE").val() || "PL" ==$("#SUTTLE_UNIT_TYPE").val()|| "PL" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (PALLET_IN_1 == 1&& BOX_IN_1 == 1) {
				calc32(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1);
				if("PL" ==$("#WEIGHT_UNIT_TYPE").val() || "CS" ==$("#SUTTLE_UNIT_TYPE").val()|| "PL" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (PALLET_IN_1 == 1&& INPACK_IN_1 == 1 ) {
				calc31(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1);
				if("PL" ==$("#WEIGHT_UNIT_TYPE").val() || "PL" ==$("#SUTTLE_UNIT_TYPE").val()|| "PL" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (PALLET_IN_1 == 1) {
				calc3(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1);
				if("PL" ==$("#WEIGHT_UNIT_TYPE").val() || "PL" ==$("#SUTTLE_UNIT_TYPE").val()|| "PL" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}
		}

		function calc3(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1) {
			//第一级根据其他算托盘数量
			var cnt = $("#PALLET_EA").val();
			$("#EA_EA").val(cnt*PALLET_NUM_1*EA_NUM_1);
		}

		function calc31(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1) {
			//第一级根据其他算托盘数量
			var cnt = $("#PALLET_EA").val();
			$("#INPACK_EA").val(cnt*PALLET_NUM_1);
			$("#EA_EA").val(cnt*PALLET_NUM_1*INPACK_NUM_1*EA_NUM_1);
		}

		function calc32(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1) {
			//第一级根据其他算托盘数量
			var cnt = $("#PALLET_EA").val();
			$("#BOX_EA").val(cnt*PALLET_NUM_1);
			$("#EA_EA").val(cnt*PALLET_NUM_1*BOX_NUM_1*EA_NUM_1);
		}

		function calc321(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1) {
			//第一级根据其他算托盘数量
			var cnt = $("#PALLET_EA").val();
			$("#BOX_EA").val(cnt*PALLET_NUM_1);
			$("#INPACK_EA").val(cnt*PALLET_NUM_1*BOX_NUM_1);
			$("#EA_EA").val(cnt*PALLET_NUM_1*BOX_NUM_1*INPACK_NUM_1*EA_NUM_1);
		}


		//填写其他的时候计算值

		function calcOT() {
			var EA_IN_1 = $("#EA_IN_1").val();
			var INPACK_IN_1 = $("#INPACK_IN_1").val();
			var BOX_IN_1 = $("#BOX_IN_1").val();
			var PALLET_IN_1 = $("#PALLET_IN_1").val();
			var OTHER_IN_1 = $("#OTHER_IN_1").val();

			var EA_NUM_1 = $("#EA_NUM_1").val();
			var INPACK_NUM_1 = $("#INPACK_NUM_1").val();
			var BOX_NUM_1 = $("#BOX_NUM_1").val();
			var PALLET_NUM_1 = $("#PALLET_NUM_1").val();
			var OTHER_NUM_1 = $("#OTHER_NUM_1").val();

			if(OTHER_IN_1 != 1) {
				return false;
			}

			if (OTHER_IN_1 == 1 && PALLET_IN_1 == 1&& BOX_IN_1 == 1&& INPACK_IN_1 == 1 ) {
				calcOT4321(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1);
				if("OT" ==$("#WEIGHT_UNIT_TYPE").val() || "OT" ==$("#SUTTLE_UNIT_TYPE").val()|| "OT" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}

				return false;
			}

			if (OTHER_IN_1 == 1 && PALLET_IN_1 == 1&& BOX_IN_1 == 1) {
				calcOT432(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1);
				if("OT" ==$("#WEIGHT_UNIT_TYPE").val() || "OT" ==$("#SUTTLE_UNIT_TYPE").val()|| "OT" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (OTHER_IN_1 == 1 && PALLET_IN_1 == 1&& INPACK_IN_1 == 1 ) {
				calcOT431(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1);
				if("OT" ==$("#WEIGHT_UNIT_TYPE").val() || "OT" ==$("#SUTTLE_UNIT_TYPE").val()|| "OT" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (OTHER_IN_1 == 1 && BOX_IN_1 == 1&& INPACK_IN_1 == 1 ) {
				calcOT421(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1);
				if("OT" ==$("#WEIGHT_UNIT_TYPE").val() || "OT" ==$("#SUTTLE_UNIT_TYPE").val()|| "OT" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (OTHER_IN_1 == 1 && PALLET_IN_1 == 1 ) {
				calcOT43(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1);
				if("OT" ==$("#WEIGHT_UNIT_TYPE").val() || "OT" ==$("#SUTTLE_UNIT_TYPE").val()|| "OT" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (OTHER_IN_1 == 1 && BOX_IN_1 == 1 ) {
				calcOT42(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1);
				if("OT" ==$("#WEIGHT_UNIT_TYPE").val() || "OT" ==$("#SUTTLE_UNIT_TYPE").val()|| "OT" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (OTHER_IN_1 == 1 &&  INPACK_IN_1 == 1 ) {
				calcOT41(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1);
				if("OT" ==$("#WEIGHT_UNIT_TYPE").val() || "OT" ==$("#SUTTLE_UNIT_TYPE").val()|| "OT" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

			if (OTHER_IN_1 == 1) {
				calcOT4(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1);
				if("OT" ==$("#WEIGHT_UNIT_TYPE").val() || "OT" ==$("#SUTTLE_UNIT_TYPE").val()|| "OT" ==$("#VOLUME_UNIT_TYPE").val()) {
					calcWeigtVolume();
				}
				return false;
			}

		}

		function calcOT4(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1) {
			//第一级根据其他算托盘数量
			var ot_ea = $("#OT_EA").val();
			$("#EA_EA").val(ot_ea*OTHER_NUM_1*EA_NUM_1);
		}

		function calcOT41(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1) {
			//第一级根据其他算托盘数量
			var ot_ea = $("#OT_EA").val();
			$("#INPACK_EA").val(ot_ea*OTHER_NUM_1);
			$("#EA_EA").val(ot_ea*OTHER_NUM_1*INPACK_NUM_1*EA_NUM_1);
		}

		function calcOT42(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1) {
			//第一级根据其他算托盘数量
			var ot_ea = $("#OT_EA").val();
			$("#BOX_EA").val(ot_ea*OTHER_NUM_1);
			$("#EA_EA").val(ot_ea*OTHER_NUM_1*BOX_NUM_1*EA_NUM_1);
		}

		function calcOT43(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1) {
			//第一级根据其他算托盘数量
			var ot_ea = $("#OT_EA").val();
			$("#PALLET_EA").val(ot_ea*OTHER_NUM_1);
			$("#EA_EA").val(ot_ea*OTHER_NUM_1*PALLET_NUM_1*EA_NUM_1);
		}

		function calcOT421(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1) {
			//第一级根据其他算托盘数量
			var ot_ea = $("#OT_EA").val();
			$("#BOX_EA").val(ot_ea*OTHER_NUM_1);
			$("#INPACK_EA").val(ot_ea*OTHER_NUM_1*BOX_NUM_1);
			$("#EA_EA").val(ot_ea*OTHER_NUM_1*BOX_NUM_1*INPACK_NUM_1*EA_NUM_1);
		}

		function calcOT431(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1) {
			//第一级根据其他算托盘数量
			var ot_ea = $("#OT_EA").val();
			$("#PALLET_EA").val(ot_ea*OTHER_NUM_1);
			$("#INPACK_EA").val(ot_ea*OTHER_NUM_1*PALLET_NUM_1);
			$("#EA_EA").val(ot_ea*OTHER_NUM_1*PALLET_NUM_1*INPACK_NUM_1*EA_NUM_1);
		}

		function calcOT4321(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1) {
			//第一级根据其他算托盘数量
			var ot_ea = $("#OT_EA").val();
			$("#PALLET_EA").val(ot_ea*OTHER_NUM_1);
			$("#BOX_EA").val(ot_ea*OTHER_NUM_1*PALLET_NUM_1);
			$("#INPACK_EA").val(ot_ea*OTHER_NUM_1*PALLET_NUM_1*BOX_NUM_1);
			$("#EA_EA").val(ot_ea*OTHER_NUM_1*PALLET_NUM_1*BOX_NUM_1*INPACK_NUM_1*EA_NUM_1);
		}

		function calcOT432(EA_IN_1,INPACK_IN_1,BOX_IN_1,PALLET_IN_1,OTHER_IN_1,EA_NUM_1,INPACK_NUM_1,BOX_NUM_1,PALLET_NUM_1,OTHER_NUM_1) {
			var ot_ea = $("#OT_EA").val();
			$("#PALLET_EA").val(ot_ea*OTHER_NUM_1);
			$("#BOX_EA").val(ot_ea*OTHER_NUM_1*PALLET_NUM_1);
			$("#EA_EA").val(ot_ea*OTHER_NUM_1*PALLET_NUM_1*BOX_NUM_1*EA_NUM_1);
		}


		//内包装计算EA
		function calcINPACK() {
			var INPACK_IN_1 = $("#INPACK_IN_1").val();
			var INPACK_NUM_1 = $("#INPACK_NUM_1").val();
			if(INPACK_IN_1 != 1) {
				return false;
			}
			var cnt = $("#INPACK_EA").val();
			$("#EA_EA").val(cnt*INPACK_NUM_1);

			if("EA" ==$("#WEIGHT_UNIT_TYPE").val() || "EA" ==$("#SUTTLE_UNIT_TYPE").val()|| "EA" ==$("#VOLUME_UNIT_TYPE").val()) {
				calcWeigtVolume();
			}
		}

		//计算净重，毛重，单位体积、总体积
		function calcWeigtVolume() {
			calcSuttle();
			calcWeight();
			calcUnitVolume();
			calcTotalVolume();
		}

		//WEIGHT_UNIT_TYPE SUTTLE_UNIT_TYPE VOLUME_UNIT_TYPE
        //OT_EA PALLET_EA BOX_EA INPACK_EA EA_EA   EA IP CS PL OT
		//WEIGHT_UNIT_TYPE SUTTLE_UNIT_TYPE VOLUME_UNIT_TYPE UNIT_WEIGHT UNIT_SUTTLE
		//计算毛重
		function calcWeight() {
			//毛重单位
			var unit_type = $("#WEIGHT_UNIT_TYPE").val();
			var UNIT_WEIGHT = $("#UNIT_WEIGHT").val();
			//EA IP CS PL OT
			//单位为其他时
			var cnt = 0;
			if(unit_type =="OT") {
				cnt = $("#OT_EA").val();
			}
			if(unit_type =="PL") {
				cnt = $("#PALLET_EA").val();
			}
			if(unit_type =="CS") {
				cnt = $("#BOX_EA").val();
			}
			if(unit_type =="IP") {
				cnt = $("#INPACK_EA").val();
			}
			if(unit_type =="EA") {
				cnt = $("#EA_EA").val();
			}

			if(cnt == null || cnt=="") {
				cnt = 0;
			}
			$("#TOTAL_WEIGHT").val(cnt*UNIT_WEIGHT);
		}

		//计算净重
		function calcSuttle() {
			//毛重单位
			var unit_type = $("#SUTTLE_UNIT_TYPE").val();
			var UNIT_SUTTLE = $("#UNIT_SUTTLE").val();
			//EA IP CS PL OT
			//单位为其他时
			var cnt = 0;
			if(unit_type =="OT") {
				cnt = $("#OT_EA").val();
			}
			if(unit_type =="PL") {
				cnt = $("#PALLET_EA").val();
			}
			if(unit_type =="CS") {
				cnt = $("#BOX_EA").val();
			}
			if(unit_type =="IP") {
				cnt = $("#INPACK_EA").val();
			}
			if(unit_type =="EA") {
				cnt = $("#EA_EA").val();
			}

			if(cnt == null || cnt=="") {
				cnt = 0;
			}
			$("#TOTAL_SUTTLE").val(cnt*UNIT_SUTTLE);
		}


			//UNIT_VOLUME
		//计算单位体积 	LONG_UT WIDE_UT HIGH_UT
		function calcUnitVolume() {
			var LONG_UT = $("#LONG_UT").val();
			var WIDE_UT = $("#WIDE_UT").val();
			var HIGH_UT = $("#HIGH_UT").val();
			if(LONG_UT == null || LONG_UT =="") {
				LONG_UT = 0;
			}
			if(WIDE_UT == null || WIDE_UT =="") {
				WIDE_UT = 0;
			}
			if(HIGH_UT == null || HIGH_UT =="") {
				HIGH_UT = 0;
			}

			var VOLUME_CM = LONG_UT * WIDE_UT *HIGH_UT;
			var VOLUME_M = VOLUME_CM/1000;
			$("#UNIT_VOLUME").val(VOLUME_M);
		}

		//计算总体积 	LONG_UT WIDE_UT HIGH_UT
		function calcTotalVolume() {
			var UNIT_VOLUME = $("#UNIT_VOLUME").val();
			var unit_type = $("#VOLUME_UNIT_TYPE").val();
           /* alert(UNIT_VOLUME);
			alert(unit_type);*/
			var cnt = 0;
			if(unit_type =="OT") {
				cnt = $("#OT_EA").val();
			}
			if(unit_type =="PL") {
				cnt = $("#PALLET_EA").val();
			}
			if(unit_type =="CS") {
				cnt = $("#BOX_EA").val();
			}
			if(unit_type =="IP") {
				cnt = $("#INPACK_EA").val();
			}
			if(unit_type =="EA") {
				cnt = $("#EA_EA").val();
			}

			if(cnt == null || cnt=="") {
				cnt = 0;
			}

			if(UNIT_VOLUME == null || UNIT_VOLUME=="") {
				UNIT_VOLUME = 0;
			}

			$("#TOTAL_VOLUME").val(cnt*UNIT_VOLUME);
		}

		</script>
</body>
</html>