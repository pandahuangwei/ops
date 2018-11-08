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
			if($("#CUSTOMER_ID").val()==""){
				$("#CUSTOMER_ID").tips({
					side:3,
					msg:'请输入货主',
					bg:'#AE81FF',
					time:2
				});
				$("#CUSTOMER_ID").focus();
				return false;
			}
			if($("#STOCK_ID").val()==""){
				$("#STOCK_ID").tips({
					side:3,
					msg:'请输入仓库',
					bg:'#AE81FF',
					time:2
				});
				$("#STOCK_ID").focus();
				return false;
			}
			if($("#PRODUCT_CODE").val()==""){
				$("#PRODUCT_CODE").tips({
					side:3,
					msg:'请输入产品编号',
					bg:'#AE81FF',
					time:2
				});
				$("#PRODUCT_CODE").focus();
				return false;
			}
			if($("#PRODUCT_CN").val()==""){
				$("#PRODUCT_CN").tips({
					side:3,
					msg:'请输入产品描述',
					bg:'#AE81FF',
					time:2
				});
				$("#PRODUCT_CN").focus();
				return false;
			}

			if($("#RULE_PICK").val()==""){
				$("#RULE_PICK").tips({
					side:3,
					msg:'请输入拣货规则',
					bg:'#AE81FF',
					time:2
				});
				$("#RULE_PICK").focus();
				return false;
			}
			if($("#RULE_PACK").val()==""){
				$("#RULE_PACK").tips({
					side:3,
					msg:'请输入包装规则',
					bg:'#AE81FF',
					time:2
				});
				$("#RULE_PACK").focus();
				return false;
			}

			if($("#RULE_STOCK_ASIGN").val()==""){
				$("#RULE_STOCK_ASIGN").tips({
					side:3,
					msg:'请输入库存分配规则',
					bg:'#AE81FF',
					time:2
				});
				$("#RULE_STOCK_ASIGN").focus();
				return false;
			}
			if($("#RULE_STORAGE_ASIGN").val()==""){
				$("#RULE_STORAGE_ASIGN").tips({
					side:3,
					msg:'请输入库位分配规则',
					bg:'#AE81FF',
					time:2
				});
				$("#RULE_STORAGE_ASIGN").focus();
				return false;
			}
			if($("#RULE_STORAGE").val()==""){
				$("#RULE_STORAGE").tips({
					side:3,
					msg:'请输入库位指定规则',
					bg:'#AE81FF',
					time:2
				});
				$("#RULE_STORAGE").focus();
				return false;
			}


			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}


		function getlocator(){
			var STORAGE_ID = $.trim($("#STORAGE_ID").val());
			if (STORAGE_ID ==null || STORAGE_ID=='') {
				return false;
			}
			//动态删除select中的所有options
			document.getElementById("LOCATOR_ID").options.length=0;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>product/getlocator.do',
				data: {STORAGE_ID:STORAGE_ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#LOCATOR_ID").append(("<option value=''></option>"));
					for (var k in data) {
						$("#LOCATOR_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
					}
				}
			});
		}

		function changeCustomer() {
			getPickRule();
			getStockAsgRule();
			getStorageAssignedRule();
			hasCode();
			hasCn();
		}

		function getPickRule(){
			var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
			if (CUSTOMER_ID ==null || CUSTOMER_ID=='') {
				return false;
			}
			//动态删除select中的所有options
			document.getElementById("RULE_PICK").options.length=0;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>product/getPickRule.do',
				data: {CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#RULE_PICK").append(("<option value=''></option>"));
					for (var k in data) {
						$("#RULE_PICK").append($("<option value='"+k+"'>"+data[k]+"</option>"));
					}
				}
			});
		}

		function getStockAsgRule() {
			var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
			if (CUSTOMER_ID == null || CUSTOMER_ID == '') {
				return false;
			}
			//动态删除select中的所有options
			document.getElementById("RULE_STOCK_ASIGN").options.length = 0;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>product/getStockAsgRule.do',
				data: {CUSTOMER_ID: CUSTOMER_ID, tm: new Date().getTime()},
				dataType: 'json',
				cache: false,
				success: function (data) {
					$("#RULE_STOCK_ASIGN").append(("<option value=''></option>"));
					for (var k in data) {
						$("#RULE_STOCK_ASIGN").append($("<option value='" + k + "'>" + data[k] + "</option>"));
					}
				}
			});
		}

		function getStorageAssignedRule(){
			var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
			if (CUSTOMER_ID ==null || CUSTOMER_ID=='') {
				return false;
			}
			//动态删除select中的所有options
			document.getElementById("RULE_STORAGE_ASIGN").options.length=0;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>product/getStorageAssignedRule.do',
				data: {CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#RULE_STORAGE_ASIGN").append(("<option value=''></option>"));
					for (var k in data) {
						$("#RULE_STORAGE_ASIGN").append($("<option value='"+k+"'>"+data[k]+"</option>"));
					}
				}
			});
		}

		function calcUnitVolume() {
			//VOLUME_UN LONG_UNIT HIGH_UNIT WIDE_UNIT
			var LONG_UT = $("#LONG_UNIT").val();
			var WIDE_UT = $("#HIGH_UNIT").val();
			var HIGH_UT = $("#WIDE_UNIT").val();
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
			var VOLUME_M = VOLUME_CM/1000000;
			$("#VOLUME_UN").val(VOLUME_M);
		}

		function hasCode(){
			var productCode = $.trim($("#PRODUCT_CODE").val());

			if (productCode ==null || productCode=='') {
				$("#PRODUCT_CODE").tips({
					side:3,
					msg:'请输入代码',
					bg:'#AE81FF',
					time:2
				});
				return false;
			}

			var productId = $.trim($("#PRODUCT_ID").val());
			var customerId = $.trim($("#CUSTOMER_ID").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>product/hasCode.do',
				data: {PRODUCT_ID:productId,CUSTOMER_ID:customerId,PRODUCT_CODE:productCode,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						$("#PRODUCT_CODE").tips({
							side:3,
							msg:'编号已存在',
							bg:'#AE81FF',
							time:3
						});
						setTimeout("$('#PRODUCT_CODE').val('')",2000);
					}
				}
			});
		}

		function hasCn(){
			var productCn = $.trim($("#PRODUCT_CN").val());

			if (productCn ==null || productCn=='') {
				$("#PRODUCT_CN").tips({
					side:3,
					msg:'请输入描述',
					bg:'#AE81FF',
					time:2
				});
				return false;
			}

			var productId = $.trim($("#PRODUCT_ID").val());
			var customerId = $.trim($("#CUSTOMER_ID").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>product/hasCn.do',
				data: {PRODUCT_ID:productId,CUSTOMER_ID:customerId,PRODUCT_CN:productCn,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						$("#PRODUCT_CN").tips({
							side:3,
							msg:'相同客户描述已存在',
							bg:'#AE81FF',
							time:3
						});
						setTimeout("$('#PRODUCT_CN').val('')",2000);
					}
				}
			});
		}
	</script>
</head>
<body>
<form action="product/${msg }.do" name="Form" id="Form" method="post">
	<input type="hidden" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}"/>
	<div id="zhongxin">

		<div class="widget-box">
			<div class="widget-header widget-hea1der-small">
				<h6>产品</h6>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-toolbox">
					<div class="btn-toolbar">
						<div class="btn-group">
							<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
							<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
						</div>
					</div>
				</div>

				<div class="widget-main padding-3">
					<div class="slim-scroll" data-height="125">
						<div class="content">
							<div> <%-- class="tab-content"--%>
								<div id="home1" class="tab-pane in active">
									<table id="table_report0" class="table table-striped  table-hover table-condensed">
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">货主<span class="red">*</span></td>
											<td style="vertical-align:top;">
												<select class="chzn-select" name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" maxlength="32"
														onchange="changeCustomer()">
													<c:forEach items="${customerList}" var="cnt">
														<option value="${cnt.id }" <c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:70px;text-align: right;padding-top: 13px;">仓库<span class="red">*</span></td>
											<td style="vertical-align:top;">
												<select class="chzn-select" name="STOCK_ID" id="STOCK_ID" style="vertical-align:top;" maxlength="32">
													<c:forEach items="${stockList}" var="cnt">
														<option value="${cnt.id }" <c:if test="${cnt.id == pd.STOCK_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>
										</tr>
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">产品编号<span class="red">*</span></td>
											<td><input type="text" name="PRODUCT_CODE" id="PRODUCT_CODE" value="${pd.PRODUCT_CODE}" maxlength="32" placeholder=" " title="产品编号" onblur="hasCode()"/></td>

											<td style="width:70px;text-align: right;padding-top: 13px;">产品描述<span class="red">*</span></td>
											<td><input type="text" name="PRODUCT_CN" id="PRODUCT_CN" value="${pd.PRODUCT_CN}" maxlength="32" placeholder=" " title="产品描述" onblur="hasCn()"/></td>
										</tr>
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">英文描述</td>
											<td colspan="7"><input class="span11"  type="text" name="PRODUCT_EN" id="PRODUCT_EN" value="${pd.PRODUCT_EN}" maxlength="32" placeholder=" " title="英文描述"/></td>
										</tr>
									</table>
								</div>
							</div>

							<div class="tabbable">
								<ul class="nav nav-tabs" id="myTab0">
									<li class="active"><a data-toggle="tab" href="#home0">产品信息</a></li>
									<li><a data-toggle="tab" href="#profile1">规则</a></li>
									<li><a data-toggle="tab" href="#profile2">货物生命周期</a></li>
								</ul>
								<div class="tab-content">
									<div id="home0" class="tab-pane in active">
										<table id="table_report1" class="table table-striped  table-hover table-condensed">
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">长cm</td>
												<td><input type="number" min="0" onkeyup="calcUnitVolume();" onblur="calcUnitVolume();" name="LONG_UNIT" id="LONG_UNIT" value="${pd.LONG_UNIT}" maxlength="32" placeholder="0" title="长cm"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">宽cm</td>
												<td><input type="number"  min="0" onkeyup="calcUnitVolume();" onblur="calcUnitVolume();" name="WIDE_UNIT" id="WIDE_UNIT" value="${pd.WIDE_UNIT}" maxlength="32" placeholder="0" title="宽cm"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">高cm</td>
												<td><input type="number"  min="0" onkeyup="calcUnitVolume();" onblur="calcUnitVolume();" name="HIGH_UNIT" id="HIGH_UNIT" value="${pd.HIGH_UNIT}" maxlength="32" placeholder="0" title="高cm"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">单价</td>
												<td><input type="number"  min="0" name="UNIT_PRICE" id="UNIT_PRICE" value="${pd.UNIT_PRICE}" maxlength="32" placeholder="0" title="单价"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">毛重单位</td>
												<td style="vertical-align:top;">
													<select class="chzn-select" name="TOTAL_WEIGHT_UNIT" id="TOTAL_WEIGHT_UNIT" style="vertical-align:top;"  data-placeholder=" "  maxlength="32">
														<c:forEach items="${totalWeightList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.TOTAL_WEIGHT_UNIT }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">毛重</td>
												<td><input type="number"  min="0" name="TOTAL_WEIGHT" id="TOTAL_WEIGHT" value="${pd.TOTAL_WEIGHT}" maxlength="32"  placeholder="0" title="毛重"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">净重单位</td>
												<td style="vertical-align:top;">
													<select class="chzn-select" name="TOTAL_SUTTLE_UNIT" id="TOTAL_SUTTLE_UNIT" style="vertical-align:top;" data-placeholder=" " maxlength="32">
														<c:forEach items="${totalSuttleList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.TOTAL_SUTTLE_UNIT }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">净重</td>
												<td><input type="number"  min="0" name="TOTAL_SUTTLE" id="TOTAL_SUTTLE" value="${pd.TOTAL_SUTTLE}" maxlength="32" placeholder="0" title="净重"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">体积单位</td>
												<td style="vertical-align:top;">
													<select class="chzn-select" name="VOLUME_UNIT" id="VOLUME_UNIT" style="vertical-align:top;" data-placeholder=" " maxlength="32">
														<c:forEach items="${volumeList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.VOLUME_UNIT }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">体积</td>
												<td><input type="number"  min="0" name="VOLUME_UN" id="VOLUME_UN" value="${pd.VOLUME_UN}" maxlength="32" placeholder="0" title="体积"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">货物类型</td>
												<%--<td><input type="text" name="PRODUCT_TYPE" id="PRODUCT_TYPE" value="${pd.PRODUCT_TYPE}" maxlength="32"  title="货物类型"/></td>--%>
												<td style="vertical-align:top;">
													<select class="chzn-select" name="PRODUCT_TYPE" id="PRODUCT_TYPE" style="vertical-align:top;" data-placeholder=" " maxlength="32">
														<option value=""></option>
														<c:forEach items="${productTypeList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.PRODUCT_TYPE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">码放库区</td>
												<td>
													<select onchange="getlocator()" name="STORAGE_ID" id="STORAGE_ID" style="vertical-align:top;" data-placeholder=" " maxlength="32">
														<option value=""></option>
														<c:forEach items="${storageList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.STORAGE_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">码放库位</td>
												<td>
													<select  name="LOCATOR_ID" id="LOCATOR_ID" style="vertical-align:top;" data-placeholder=" " maxlength="32">
														<option value=""></option>
														<c:forEach items="${locatorList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">冻结/拒收代码</td>
												<td style="vertical-align:top;">
													<select class="chzn-select" name="FREEZE_REJECT_CODE" id="FREEZE_REJECT_CODE" style="vertical-align:top;" data-placeholder=" " maxlength="32">
														<c:forEach items="${freeRejectList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.FREEZE_REJECT_CODE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">备注</td>
												<td colspan="7"><input class="span11"  type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32"  title="备注"/></td>
											</tr>
											<tr>
												<td colspan="7">
													激活
													<input type='checkbox' id="ACTIVE_FLG" name='ACTIVE_FLG' value="1"
														   <c:if test="${pd.ACTIVE_FLG eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													危险品
													<input type='checkbox' id="DANGER_FLG" name='DANGER_FLG' value="1"
														   <c:if test="${pd.DANGER_FLG eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													管理序列号
													<input type='checkbox' id="MANAGER_FLG" name='MANAGER_FLG' value="1"
														   <c:if test="${pd.MANAGER_FLG eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													组合件
													<input type='checkbox' id="COMBI_FLG" name='COMBI_FLG' value="1"
														   <c:if test="${pd.COMBI_FLG eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
												</td>
											</tr>
										</table>
									</div>
									<div id="profile1" class="tab-pane">
										<table id="table_report2" class="table table-striped  table-hover table-condensed">
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">包装规则<span class="red">*</span></td>
												<%--<td><input type="text" name="RULE_PACK" id="RULE_PACK" value="${pd.RULE_PACK}" maxlength="32"  title="包装规则"/></td>--%>
												<td style="vertical-align:top;">
													<select name="RULE_PACK" id="RULE_PACK" data-placeholder=" " style="vertical-align:top;">
														<option value=""></option>
														<c:forEach items="${packRuleList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_PACK }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">拣货规则<span class="red">*</span></td>
												<%--<td><input type="text" name="RULE_PICK" id="RULE_PICK" value="${pd.RULE_PICK}" maxlength="32"  title="拣货规则"/></td>--%>
												<td style="vertical-align:top;">
													<select  name="RULE_PICK" id="RULE_PICK" data-placeholder=" " style="vertical-align:top;">
														<option value=""></option>
														<c:forEach items="${pickRuleList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_PICK }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">库位指定规则<span class="red">*</span></td>
												<%--<td><input type="text" name="RULE_STORAGE" id="RULE_STORAGE" value="${pd.RULE_STORAGE}" maxlength="32" title="库位指定规则"/></td>--%>
												<td style="vertical-align:top;">
													<select name="RULE_STORAGE" id="RULE_STORAGE" data-placeholder=" " style="vertical-align:top;">
														<option value=""></option>
														<c:forEach items="${storageRuleList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_STORAGE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">库位分配规则<span class="red">*</span></td>
												<%--<td><input type="text" name="RULE_STORAGE_ASIGN" id="RULE_STORAGE_ASIGN" value="${pd.RULE_STORAGE_ASIGN}" maxlength="32" p title="库位分配规则"/></td>--%>
												<td style="vertical-align:top;">
													<select  name="RULE_STORAGE_ASIGN" id="RULE_STORAGE_ASIGN" data-placeholder=" " style="vertical-align:top;">
														<option value=""></option>
														<c:forEach items="${storageAsgRuleList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_STORAGE_ASIGN }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">库存分配规则<span class="red">*</span></td>
												<%--<td><input type="text" name="RULE_STOCK_ASIGN" id="RULE_STOCK_ASIGN" value="${pd.RULE_STOCK_ASIGN}" maxlength="32"  title="库存分配规则"/></td>--%>
												<td style="vertical-align:top;">
													<select  name="RULE_STOCK_ASIGN" id="RULE_STOCK_ASIGN" data-placeholder=" " style="vertical-align:top;">
														<option value=""></option>
														<c:forEach items="${stockAsgRuleList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_STOCK_ASIGN }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">库存周转规则<span class="red">*</span></td>
												<%--<td><input type="text" name="RULE_STOCK_ASIGN" id="RULE_STOCK_ASIGN" value="${pd.RULE_STOCK_ASIGN}" maxlength="32"  title="库存分配规则"/></td>--%>
												<td style="vertical-align:top;">
													<select  name="STOCKTURN_ID" id="STOCKTURN_ID" data-placeholder=" " style="vertical-align:top;">
														<option value=""></option>
														<c:forEach items="${stockTurnRuleList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.STOCKTURN_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>
										</table>
									</div>
									<div id="profile2" class="tab-pane">
										<table id="table_report3" class="table table-striped  table-hover table-condensed">
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">启用有效期</td>
												<td>
													<input type='checkbox' id="USE_VALIDITY" name='USE_VALIDITY' value="1"
														   <c:if test="${pd.USE_VALIDITY eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">周期类型</td>
												<td style="vertical-align:top;">
													<select  name="PERIOD_TYPE" id="PERIOD_TYPE" style="vertical-align:top;" data-placeholder=" " maxlength="32">
														<option value=""></option>
														<c:forEach items="${periodList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.PERIOD_TYPE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">入库生命天数</td>
												<td><input type="number" name="INBOUND_DAY" id="INBOUND_DAY" value="${pd.INBOUND_DAY}" maxlength="32"  title="入库生命天数"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">出库生命天数</td>
												<td><input type="number" name="OUTBOUND_DAY" id="OUTBOUND_DAY" value="${pd.OUTBOUND_DAY}" maxlength="32"  title="出库生命天数"/></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<%--<table id="table_report4" class="table table-striped  table-hover table-condensed">
                                <tr>
                                    <td style="text-align: center;" colspan="10">
                                        <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                        <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
                                    </td>
                                </tr>
                            </table>--%>
						</div>
					</div>
				</div>
			</div>
		</div>


	</div>

	<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>

</form>


<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript">
	$(top.hangge());
	$(function() {

		//单选框
		$(".chzn-select").chosen();
		$(".chzn-select-deselect").chosen({allow_single_deselect:true});

		//日期框
		$('.date-picker').datepicker({autoclose: true});

	});

</script>
</body>
</html>