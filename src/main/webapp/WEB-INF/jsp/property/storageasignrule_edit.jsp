<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

		  var chosev = $("#LOCATOR_PRIORITY_LEVEL").val();
		  $("#LOCATOR_PRIORITY_LEVEL_1").val(chosev);

		var PROD_LIMIT_1 = $("#PROD_LIMIT").val();
		$("#PROD_LIMIT_1").val(PROD_LIMIT_1);


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
		if($("#STORAGEASING_CODE").val()==""){
			$("#STORAGEASING_CODE").tips({
				side:3,
	            msg:'请输入库位分配编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STORAGEASING_CODE").focus();
			return false;
		}
		if($("#STORAGEASING_CN").val()==""){
			$("#STORAGEASING_CN").tips({
				side:3,
	            msg:'请输入库位分配名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STORAGEASING_CN").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function hasCode(){
		var STORAGEASING_CODE = $.trim($("#STORAGEASING_CODE").val());
		if (STORAGEASING_CODE ==null || STORAGEASING_CODE=='') {
			$("#STORAGEASING_CODE").tips({
				side:3,
				msg:'请输入编码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var STORAGEASIGNRULE_ID = $.trim($("#STORAGEASIGNRULE_ID").val());
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>storageasignrule/hasCode.do',
			data: {STORAGEASING_CODE:STORAGEASING_CODE,STORAGEASIGNRULE_ID:STORAGEASIGNRULE_ID,CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#STORAGEASING_CODE").tips({
						side:3,
						msg:'编码已存在,请重新输入...',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#STORAGEASING_CODE').val('')",2000);
				}
			}
		});
	}

	function hasCn(){
		var STORAGEASING_CN = $.trim($("#STORAGEASING_CN").val());
		if (STORAGEASING_CN ==null || STORAGEASING_CN=='') {
			$("#STORAGEASING_CN").tips({
				side:3,
				msg:'请输入编码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var STORAGEASIGNRULE_ID = $.trim($("#STORAGEASIGNRULE_ID").val());
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>storageasignrule/hasCn.do',
			data: {STORAGEASING_CN:STORAGEASING_CN,STORAGEASIGNRULE_ID:STORAGEASIGNRULE_ID,CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#STORAGEASING_CN").tips({
						side:3,
						msg:'编码已存在,请重新输入...',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#STORAGEASING_CN').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="storageasignrule/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="STORAGEASIGNRULE_ID" id="STORAGEASIGNRULE_ID" value="${pd.STORAGEASIGNRULE_ID}"/>
		<div id="zhongxin">
			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>库位分配规则</h6>
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
								<table id="table_report" class="table table-striped  table-hover table-condensed">
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">客户<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select  name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" maxlength="32">
												<c:forEach items="${customerList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td></td>
										<td></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">库位分配编码<span class="red">*</span></td>
										<td><input type="text" name="STORAGEASING_CODE" id="STORAGEASING_CODE" value="${pd.STORAGEASING_CODE}" maxlength="32" placeholder="" onblur="hasCode()"  title="库位分配编码"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">库位分配名称<span class="red">*</span></td>
										<td><input type="text" name="STORAGEASING_CN" id="STORAGEASING_CN" value="${pd.STORAGEASING_CN}" maxlength="32" placeholder="" onblur="hasCn()"   title="库位分配名称"/></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">备注</td>
										<td colspan="7"><input class="span11" type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder="" title="备注"/></td>
									</tr>
									<tr>
										<%--<td style="width:70px;text-align: right;padding-top: 13px;">位置优先</td>
										<td><input type="text" name="SEAT_STATE" id="SEAT_STATE" value="${pd.SEAT_STATE}" maxlength="32" placeholder="" title="位置优先"/></td>--%>

										<td style="width:70px;text-align: right;padding-top: 13px;">库位优先级</td>
										<td style="vertical-align:top;">
											<input type="hidden" name="LOCATOR_PRIORITY_LEVEL_1" id="LOCATOR_PRIORITY_LEVEL_1" va>
											<select multiple class="chzn-select" name="LOCATOR_PRIORITY_LEVEL" id="LOCATOR_PRIORITY_LEVEL" style="vertical-align:top;" data-placeholder="可多选..." maxlength="32">
												<c:forEach items="${locatorPriLevelList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${fn:contains(pd.LOCATOR_PRIORITY_LEVEL,cnt.id) }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>


										<td style="width:70px;text-align: right;padding-top: 13px;"></td>
										<td></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">产品限制</td>
										<td>
											<%--<input type="text" name="PROD_LIMIT" id="PROD_LIMIT" value="${pd.PROD_LIMIT}" maxlength="32" placeholder="" title="产品限制"/>--%>
											<input type="hidden" name="PROD_LIMIT_1" id="PROD_LIMIT_1" va>
											<select multiple class="chzn-select" name="PROD_LIMIT" id="PROD_LIMIT" style="vertical-align:top;" data-placeholder="可多选..." maxlength="32">
												<c:forEach items="${productTypeList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${fn:contains(pd.PROD_LIMIT,cnt.id) }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">启用</td>
										<td  class="left"><input  class="input-small" type='checkbox' id="PROD_LIMIT_USE"  name='PROD_LIMIT_USE' value="1"
																  <c:if test="${pd.PROD_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">体积限制CBM</td>
										<td><input type="number" min="0"  name="VOLUME_LIMIT" id="VOLUME_LIMIT" value="${pd.VOLUME_LIMIT}" maxlength="32" placeholder="0" title="体积限制"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">启用</td>
										<td  class="left"><input  class="input-small" type='checkbox' id="VOLUME_LIMIT_USE"  name='VOLUME_LIMIT_USE' value="1"
																  <c:if test="${pd.VOLUME_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">长限制CM</td>
										<td><input type="number" min="0" name="LONG_LIMIT" id="LONG_LIMIT" value="${pd.LONG_LIMIT}" maxlength="32" placeholder="0" title="长限制"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">启用</td>
										<td  class="left"><input  class="input-small" type='checkbox' id="LONG_LIMIT_USE"  name='LONG_LIMIT_USE' value="1"
																  <c:if test="${pd.LONG_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">宽限制CM</td>
										<td><input type="number" min="0" name="WIDE_LIMIT" id="WIDE_LIMIT" value="${pd.WIDE_LIMIT}" maxlength="32" placeholder="0" title="宽限制"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">启用</td>
										<td  class="left"><input  class="input-small" type='checkbox' id="WIDE_LIMIT_USE"  name='WIDE_LIMIT_USE' value="1"
																  <c:if test="${pd.WIDE_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">高限制</td>
										<td><input type="number" min="0" name="HIGH_LIMIT" id="HIGH_LIMIT" value="${pd.HIGH_LIMIT}" maxlength="32" placeholder="0" title="高限制"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">启用</td>
										<td  class="left"><input  class="input-small" type='checkbox' id="HIGH_LIMIT_USE"  name='HIGH_LIMIT_USE' value="1"
																  <c:if test="${pd.HIGH_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
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


	    <%--<script type="text/javascript" src="static/third/bootstrap-multiselect.js"></script>
	    <link rel="stylesheet" href="static/third/bootstrap-multiselect.css" />--%>

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