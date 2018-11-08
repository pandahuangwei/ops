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

		if($("#BOX_NO").val()==""){
			$("#BOX_NO").tips({
				side:3,
	            msg:'请输入箱号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BOX_NO").focus();
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
		if($("#CUSTOMER_ID").val()==""){
			$("#CUSTOMER_ID").tips({
				side:3,
	            msg:'请输入客户',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CUSTOMER_ID").focus();
			return false;
		}
		if($("#INSTOCK_NO").val()==""){
			$("#INSTOCK_NO").tips({
				side:3,
	            msg:'请输入进货单编号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#INSTOCK_NO").focus();
			return false;
		}
		if($("#ASIGN_STORAGE").val()==""){
			$("#ASIGN_STORAGE").tips({
				side:3,
	            msg:'请输入分配库区',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ASIGN_STORAGE").focus();
			return false;
		}
		if($("#ASIGN_LOCATOR").val()==""){
			$("#ASIGN_LOCATOR").tips({
				side:3,
	            msg:'请输入分配库位',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ASIGN_LOCATOR").focus();
			return false;
		}
		if($("#PUT_STORAGE").val()==""){
			$("#PUT_STORAGE").tips({
				side:3,
	            msg:'请输入码放库区',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PUT_STORAGE").focus();
			return false;
		}
		if($("#PUT_LOCATOR").val()==""){
			$("#PUT_LOCATOR").tips({
				side:3,
	            msg:'请输入码放库位',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PUT_LOCATOR").focus();
			return false;
		}
		if($("#EA_NUM").val()==""){
			$("#EA_NUM").tips({
				side:3,
	            msg:'请输入EA数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#EA_NUM").focus();
			return false;
		}

		var result = hadstockProd();
		if (!result) {
			return;
		}
		var OPT_EVEN = $("#OPT_EVEN").val();
		//库存调整
		if ("stockAudit"== OPT_EVEN) {
			$('#Form').attr("action", "stockaudit/saveAuditAddApply.do").submit();
		} else {
			$("#Form").submit();
		}
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//判断入库单是否存在
	function hasStock(){
		var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
		if (INSTOCK_NO ==null || INSTOCK_NO=='') {
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'请输入入库单号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var RECEIV_TYPE = $("#RECEIV_TYPE").val();
		var RECEIV_TYPE_5 =  RECEIV_TYPE.substr(0,5);
		var INSTOCK_NO_5 = INSTOCK_NO.substr(0,5);

		if (RECEIV_TYPE_5 != INSTOCK_NO_5 ) {
			$("#INSTOCK_NO").val('');
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'不是'+RECEIV_TYPE+"的入库单.",
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		$("#INSTOCK_NO").val(INSTOCK_NO);

		$.ajax({
			type: "POST",
			url: '<%=basePath%>box/hadInStock.do',
			data: {INSTOCK_NO:INSTOCK_NO,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				//如果不存在
				if("success" != data.result){
					$("#INSTOCK_NO").css('background-color','red');
					$("#INSTOCK_NO").tips({
						side:3,
						msg:'入库单号不存在!',
						bg:'#AE81FF',
						time:2
					});
					setTimeout("$('#INSTOCK_NO').val('')",2000);
				} else {
					//如果存在，则设置入库单Id与输入框颜色.把该入库单的产品带回来，则之后的产品判断无需与后台打交道
					$("#STOCKMGRIN_ID").val(data.STOCKMGRIN_ID);
					$("#INSTOCK_NO").css('background-color','green');
				}
			},
			complete: function (XMLHttpRequest, textStatus) {
				//$("#INSTOCK_NO").focus();
				//$("#INSTOCK_NO").css('background-color','yellow');
			}
		});
	}


	function auditBoxNo(){
		var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
		if (INSTOCK_NO ==null || INSTOCK_NO=='') {
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'请输入入库单号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var BOX_NO = $.trim($("#BOX_NO").val());
		if (BOX_NO ==null || BOX_NO=='') {
			$("#BOX_NO").tips({
				side:3,
				msg:'请输入箱号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var RECEIV_TYPE = $("#RECEIV_TYPE").val();
		var RECEIV_TYPE_5 =  RECEIV_TYPE.substr(0,5);
		var BOX_NO_5 = BOX_NO.substr(0,5);
		if (RECEIV_TYPE_5 != BOX_NO_5 ) {
			$("#BOX_NO").val('');
			$("#BOX_NO").tips({
				side:3,
				msg:'不是'+RECEIV_TYPE+"的箱号.",
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		$("#INSTOCK_NO").val(INSTOCK_NO);
		$("#BOX_NO").val(BOX_NO);

		$.ajax({
			type: "POST",
			url: '<%=basePath%>receivopt/auditBoxNo.do',
			data: {INSTOCK_NO:INSTOCK_NO,BOX_NO:BOX_NO,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				//如果不存在
				if("success" == data.result){
					$("#BOX_NO").css('background-color','red');
					$("#BOX_NO").tips({
						side:3,
						msg:''+data.auditRs,
						bg:'#AE81FF',
						time:2
					});
					setTimeout("$('#BOX_NO').val('')",3000);
				} else {
					$("#BOX_NO").css('background-color','green');
				}
			}
		});
	}


	function hadstockProd() {
          var result = false;
		var STOCKMGRIN_ID = $("#STOCKMGRIN_ID").val();
		var PRODUCT_ID = $("#PRODUCT_ID").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>box/hadStockProd.do',
			data: {STOCKMGRIN_ID:STOCKMGRIN_ID,PRODUCT_ID:PRODUCT_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				//如果不存在
				if("success" != data.result){
					if("STOP" == data.result) {
						$("#INSTOCK_NO").css('background-color','red');
						$("#INSTOCK_NO").tips({
							side:3,
							msg:'入库单对应的产品已经收货,不能再添加',
							bg:'#AE81FF',
							time:2
						});
						setTimeout("$('#INSTOCK_NO').val('')",3000);
					} else {
						result = true;
					}

				} else {
					$("#BOX_NO").css('background-color','green');
				}
			}
		});
		return result;
	}



</script>
	</head>
<body>
	<form action="box/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="OPT_EVEN" id="OPT_EVEN" value="${pd.OPT_EVEN}">
		<div id="zhongxin">


			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong>新增箱号</strong>

					</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-main padding-3">
						<div class="slim-scroll">
							<div class="content">

							</div>
						</div>
					</div>
					<div class="widget-toolbox">
						<div class="btn-toolbar">
							<div class="btn-group">
								<div class="btn-group">
									<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
									<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
								</div>
							</div>

						</div>
					</div>
					<div class="widget-main padding-3">
						<div>
							<div class="content">
								<table id="table_report" class="table table-striped  table-hover table-condensed">
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">箱号<span class="red">*</span></td>
										<td><input type="text" name="BOX_NO" onblur="auditBoxNo();" id="BOX_NO" value="${pd.BOX_NO}" maxlength="32" placeholder="" title="箱号"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">客户<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select  class="chzn-select"   name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" >
												<c:forEach items="${customerList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">产品<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select   class="chzn-select"  name="PRODUCT_ID" id="PRODUCT_ID"  style="vertical-align:top;"  data-placeholder=" ">
												<c:forEach items="${productList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">进货单号<span class="red">*</span></td>
										<td><input type="text" name="INSTOCK_NO" id="INSTOCK_NO" onblur="hasStock();" value="${pd.INSTOCK_NO}" maxlength="32" placeholder="" title="进货单号"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">EA数量<span class="red">*</span></td>
										<td><input type="number" min="1" name="EA_NUM" id="EA_NUM" value="${pd.EA_NUM}" maxlength="32" placeholder="" title="EA数量"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">板号</td>
										<td><input type="text" name="BOARD_NO" id="BOARD_NO" value="${pd.BOARD_NO}" maxlength="32" placeholder="" title="板号"/></td>


									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">长CM</td>
										<td><input type="number" name="LONG_UNIT" id="LONG_UNIT" value="${pd.LONG_UNIT}" maxlength="32" placeholder="" title="长"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">宽CM</td>
										<td><input type="number" name="WIDE_UNIT" id="WIDE_UNIT" value="${pd.WIDE_UNIT}" maxlength="32" placeholder="" title="宽"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">高CM</td>
										<td><input type="number" name="HIGH_UNIT" id="HIGH_UNIT" value="${pd.HIGH_UNIT}" maxlength="32" placeholder="" title="高"/></td>


									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">体积CBM</td>
										<td><input type="number" name="VOLUME_UNIT" id="VOLUME_UNIT" value="${pd.VOLUME_UNIT}" maxlength="32" placeholder="" title="高"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">净重</td>
										<td><input type="number" name="TOTAL_SUTTLE" id="TOTAL_SUTTLE" value="${pd.TOTAL_SUTTLE}" maxlength="32" placeholder="" title="净重"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">毛重</td>
										<td><input type="number" name="TOTAL_WEIGHT" id="TOTAL_WEIGHT" value="${pd.TOTAL_WEIGHT}" maxlength="32" placeholder="" title="毛重"/></td>
									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">COO</td>
										<td><input type="text" name="COO" id="COO" value="${pd.COO}" maxlength="32" placeholder="" title="COO"/></td>


										<td style="width:70px;text-align: right;padding-top: 13px;">DateCode</td>
										<td><input type="text"  name="DATE_CODE" id="DATE_CODE" value="${pd.DATE_CODE}" maxlength="32" placeholder="" title="净重"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">最小包装数</td>
										<td><input type="text" name="SEPARATE_QTY" id="SEPARATE_QTY" value="${pd.SEPARATE_QTY}" maxlength="32" placeholder="" title="毛重"/></td>

									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">Lot No</td>
										<td><input type="text"  name="LOT_NO" id="LOT_NO" value="${pd.LOT_NO}" maxlength="32" placeholder="" title="COO"/></td>


										<td style="width:70px;text-align: right;padding-top: 13px;">BIN</td>
										<td><input type="text"  name="BIN_CODE" id="BIN_CODE" value="${pd.BIN_CODE}" maxlength="32" placeholder="" title="净重"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;"></td>
										<td></td>

									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">分配库区<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select class="chzn-select" name="ASIGN_STORAGE" id="ASIGN_STORAGE"  style="vertical-align:top;" >
												<c:forEach items="${storageList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASIGN_STORAGE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">分配库位<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select class="chzn-select" name="ASIGN_LOCATOR" id="ASIGN_LOCATOR"  style="vertical-align:top;" >
												<c:forEach items="${locatorList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASIGN_LOCATOR }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">码放库区<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select class="chzn-select" name="PUT_STORAGE" id="PUT_STORAGE"  style="vertical-align:top;" >
												<c:forEach items="${storageList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_STORAGE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">码放库位<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select class="chzn-select" name="PUT_LOCATOR" id="PUT_LOCATOR"  style="vertical-align:top;" >
												<c:forEach items="${locatorList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_LOCATOR }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>


										<td style="width:70px;text-align: right;padding-top: 13px;">收货状态</td>
										<td style="vertical-align:top;">
											<select  name="RECEIPT_STATE" id="RECEIPT_STATE" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${stockInStateList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.RECEIPT_STATE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">分配状态</td>
										<td style="vertical-align:top;">
											<select  name="ASSIGNED_STATE" id="ASSIGNED_STATE" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${assignedList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASSIGNED_STATE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>

										<td style="width:70px;text-align: right;padding-top: 13px;">码放状态</td>
										<td style="vertical-align:top;">
											<select  name="PUT_STATUS" id="PUT_STATUS" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${putStateList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_STATUS }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">分配库存状态</td>
										<td style="vertical-align:top;">
											<select  name="ASSIGNED_STOCK_STATE" id="ASSIGNED_STOCK_STATE" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${assignedList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASSIGNED_STOCK_STATE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">拣货状态</td>
										<td style="vertical-align:top;">
											<select  name="PICK_STATE" id="PICK_STATE" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${pickStateList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.PICK_STATE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">配载状态</td>
										<td style="vertical-align:top;">
											<select  name="CARGO_STATE" id="CARGO_STATE" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${cargoStateList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.CARGO_STATE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">发货状态</td>
										<td style="vertical-align:top;">
											<select  name="DEPOT_TYPE" id="DEPOT_TYPE" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${depotTypeList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.DEPOT_TYPE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">冻结状态</td>
										<td style="vertical-align:top;">
											<select  name="FREEZE_STATE" id="FREEZE_STATE" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${freezeStateList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.FREEZE_STATE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>


									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">供应商料号</td>
										<td><input type="text" name="SUPPLIER_PROD" id="SUPPLIER_PROD" value="${pd.SUPPLIER_PROD}" maxlength="32" placeholder="" title="供应商料号"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">备注</td>
										<td colspan="7"><input class="span7" type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder="这里输入备注" title="备注"/></td>
									</tr>
								</table>

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
			$(".chzn-select").chosen({search_contains: true});
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker({autoclose: true});
			
		});
		
		</script>
</body>
</html>