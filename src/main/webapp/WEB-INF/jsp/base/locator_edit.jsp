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
			if($("#LOCATOR_CODE").val()==""){
			$("#LOCATOR_CODE").tips({
				side:3,
	            msg:'请输入库位编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LOCATOR_CODE").focus();
			return false;
		}
		if($("#STORAGE_CODE").val()==""){
			$("#STORAGE_CODE").tips({
				side:3,
	            msg:'请输入库区编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STORAGE_CODE").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function hasCode(){
		var LOCATORCODE = $.trim($("#LOCATOR_CODE").val());

		if (LOCATORCODE ==null || LOCATORCODE=='') {
			$("#LOCATOR_CODE").tips({
				side:3,
				msg:'请输入代码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var storageId = $.trim($("#STORAGE_CODE").val());
		var LOCATORID = $.trim($("#LOCATOR_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>locator/hasCode.do',
			data: {LOCATORCODE:LOCATORCODE,LOCATORID:LOCATORID,STORAGE_ID:storageId,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#LOCATOR_CODE").tips({
						side:3,
						msg:'编号已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#LOCATOR_CODE').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="locator/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="LOCATOR_ID" id="LOCATOR_ID" value="${pd.LOCATOR_ID}"/>
		<div id="zhongxin" class="row-fluid">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>库位</h6>
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
										<table id="table_report2" class="table table-striped  table-hover table-condensed">
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">库区<span class="red">*</span></td>
												<td style="vertical-align:top;" colspan="7">
													<select class="span11" name="STORAGE_CODE" id="STORAGE_CODE" style="vertical-align:top;" maxlength="32">
														<c:forEach items="${storageList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.STORAGE_CODE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">库位编码<span class="red">*</span></td>
												<td><input type="text" name="LOCATOR_CODE" id="LOCATOR_CODE" value="${pd.LOCATOR_CODE}" maxlength="32" title="库位编码" onblur="hasCode()"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">库位属性</td>
												<td style="vertical-align:top;">
													<select  name="LOCATOR_STATE" id="LOCATOR_STATE" style="vertical-align:top;" maxlength="32" onchange="hasCode()">
														<c:forEach items="${locatorStateList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_STATE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

											</tr>



											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">排</td>
												<td><input type="number" name="ROW" id="ROW_UNIT" value="${pd.ROW_UNIT}" maxlength="32" placeholder="0" title="排"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">层</td>
												<td><input type="number" name="FLOOR" id="FLOOR_UNIT" value="${pd.FLOOR_UNIT}" maxlength="32" placeholder="0" title="层"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">列</td>
												<td><input type="number" name="QUEUE" id="QUEUE_UNIT" value="${pd.QUEUE_UNIT}" maxlength="32" placeholder="0" title="列"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">库位优先级</td>
												<td style="vertical-align:top;">
													<select  name="LOCATOR_PRIORITY_LEVEL" id="LOCATOR_PRIORITY_LEVEL" style="vertical-align:top;" maxlength="32">
														<c:forEach items="${locatorPriLevelList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_PRIORITY_LEVEL }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">库位使用</td>
												<td style="vertical-align:top;">
													<select  name="LOCATOR_USE" id="LOCATOR_USE" style="vertical-align:top;" maxlength="32">
														<c:forEach items="${locatorUseList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_USE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">库位类型</td>
												<td style="vertical-align:top;">
													<select  name="LOCATOR_TYPE" id="LOCATOR_TYPE" style="vertical-align:top;" maxlength="32">
														<c:forEach items="${locatorTypeList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_TYPE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">库位处理</td>
												<td style="vertical-align:top;">
													<select  name="LOCATOR_UNIT" id="LOCATOR_UNIT" style="vertical-align:top;" maxlength="32">
														<c:forEach items="${locatorUnitList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_UNIT }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">周转周期</td>
												<td style="vertical-align:top;">
													<select  name="TURNOVER_CYCLE" id="TURNOVER_CYCLE" style="vertical-align:top;" maxlength="32">
														<c:forEach items="${turnoverCycleList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.TURNOVER_CYCLE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>


											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">备注</td>

												<td colspan="7"><input class="span11"  type="text"  name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32"  title="备注"/></td>

											</tr>
										</table>
									</div>
								</div>
								<div class="tabbable">
									<ul class="nav nav-tabs" id="myTablimit">
										<li class="active"><a data-toggle="tab" href="#home0">限制</a></li>
										<li><a data-toggle="tab" href="#profile">尺寸 </a></li>
									</ul>
									<div  class="tab-content">
										<div id="home0" class="tab-pane in active">
											<table id="table_report3" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">体积限制CBM</td>
													<td><input type="number" min="0" name="VOLUME_LIMIT" id="VOLUME_LIMIT" value="${pd.VOLUME_LIMIT}" maxlength="32" placeholder="0" title="体积限制"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">重量限制</td>
													<td><input type="number" min="0" name="WEIGHT_LIMIT" id="WEIGHT_LIMIT" value="${pd.WEIGHT_LIMIT}" maxlength="32" placeholder="0" title="重量限制"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">数量限制</td>
													<td><input type="number" min="0" name="QUANTITY_LIMIT" id="QUANTITY_LIMIT" value="${pd.QUANTITY_LIMIT}" maxlength="32" placeholder="0" title="数量限制"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">托盘限制</td>
													<td><input type="number" min="0" name="PALLET_LIMIT" id="PALLET_LIMIT" value="${pd.PALLET_LIMIT}" maxlength="32" placeholder="0" title="托盘限制"/></td>
												</tr>
											</table>
										</div>
										<div id="profile" class="tab-pane">
											<table id="table_report" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">长CM</td>
													<td><input type="number" min="0" name="LONG" id="LONG_UNIT" value="${pd.LONG_UNIT}" maxlength="32" placeholder="0" title="长"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">宽CM</td>
													<td><input type="number" min="0" name="WIDE" id="WIDE_UNIT" value="${pd.WIDE_UNIT}" maxlength="32" placeholder="0" title="宽"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">高CM</td>
													<td><input type="number" min="0" name="HIGH" id="HIGH_UNIT" value="${pd.HIGH_UNIT}" maxlength="32" placeholder="0" title="高"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">层数</td>
													<td><input type="number"  min="0" name="PLIES" id="PLIES_UNIT" value="${pd.PLIES_UNIT}" maxlength="32" placeholder="0" title="层数"/></td>
												</tr>
											</table>
										</div>

									</div>
								</div>

								<table id="table_report4" class="table table-striped  table-hover table-condensed">
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">产品混合</td>
										<%--<label><input type='checkbox' name='ids' value="${var.LOCATOR_ID}" /><span class="lbl"></span></label>--%>
										<td><input type='checkbox' id="PRODUCT_MIX" name='PRODUCT_MIX' value="1"
												   <c:if test="${pd.PRODUCT_MIX eq 1 }">checked="checked" </c:if>/><span class="lbl"></span></td>

										<%--<td><input type="number" name="PRODUCT_MIX" id="PRODUCT_MIX" value="${pd.PRODUCT_MIX}" maxlength="32" placeholder="这里输入产品混合" title="产品混合"/></td>--%>

										<td style="width:70px;text-align: right;padding-top: 13px;">批号混合</td>
										<td><input type='checkbox' id="BATCH_MIX"  name='BATCH_MIX' value="1"
												   <c:if test="${pd.BATCH_MIX eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>

									<%--<tr>
										<td style="text-align: center;" colspan="10">
											<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
										</td>
									</tr>--%>
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
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker({autoclose: true});


		});
		
		</script>
</body>
</html>