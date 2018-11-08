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
	function save2(){
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

		var curopt = $("#msg").val();
		//如果是新增，则判断
		if(curopt=="save") {
			var rs = hasCn();
			alert(rs);
		}

		/*if($("#ORDERBATCH_TYPE").val()==""){
			$("#ORDERBATCH_TYPE").tips({
				side:3,
	            msg:'请输入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ORDERBATCH_TYPE").focus();
			return false;
		}*/
		/*if($("#NUM_ONE").val()==""){
			$("#NUM_ONE").tips({
				side:3,
	            msg:'请输入扩展日期字段1',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NUM_ONE").focus();
			return false;
		}*/

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function save(){
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

		var curopt = $("#msg").val();
		//如果是新增，则判断
		if(curopt=="save") {
			var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
			var ORDERBATCH_TYPE = $.trim($("#ORDERBATCH_TYPE").val());
			//var txt = $("#ORDERBATCH_TYPE option:selected").text();

			$.ajax({
				type: "POST",
				url: '<%=basePath%>orderproperty/hasCn.do',
				data: {CUSTOMER_ID:CUSTOMER_ID,ORDERBATCH_TYPE:ORDERBATCH_TYPE,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					if("success" != data.result){
						$("#ORDERBATCH_TYPE").tips({
							side:3,
							msg:'该客户['+data.txt+']已存在,请不要重复新增.',
							bg:'#AE81FF',
							time:3
						});
						setTimeout("$('#ORDERBATCH_TYPE').val('')",2000);
						return false;
					} else {
						$("#Form").submit();
						$("#zhongxin").hide();
						$("#zhongxin2").show();
					}
				}
			});
		} else {
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}


	//判断是否存在
	function hasCn(){
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		var ORDERBATCH_TYPE = $.trim($("#ORDERBATCH_TYPE").val());
		var txt = $("#ORDERBATCH_TYPE").text();

		$.ajax({
			type: "POST",
			url: '<%=basePath%>orderproperty/hasCn.do',
			data: {CUSTOMER_ID:CUSTOMER_ID,ORDERBATCH_TYPE:ORDERBATCH_TYPE,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#ORDERBATCH_TYPE").tips({
						side:3,
						msg:'该客户['+txt+']已存在...',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#ORDERBATCH_TYPE').val('')",2000);
					return false;
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="orderproperty/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="msg" id="msg" value="${msg}"/>
		<input type="hidden" name="ORDERPROPERTY_ID" id="ORDERPROPERTY_ID" value="${pd.ORDERPROPERTY_ID}"/>
		<input type="hidden" name="ORDERBATCH_TYPE" id="ORDERBATCH_TYPE" value="${pd.ORDERBATCH_TYPE}"/>
		<c:if test="${msg == 'edit' }">
			<input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/>
		</c:if>

		<div id="zhongxin">
			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>订单/批次属性</h6>
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
								<table id="table_report0" class="table table-striped  table-hover table-condensed">
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">客户<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select <c:if test="${msg != 'edit' }"> name="CUSTOMER_ID" id="CUSTOMER_ID" </c:if> style="vertical-align:top;" maxlength="32"
											<c:if test="${msg == 'edit' }">disabled='disabled'</c:if>>
												<c:forEach items="${customerList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>
										<%--<c:if test="${msg == 'edit' }">disabled='disabled'</c:if>--%>
										<td style="width:95px;text-align: right;padding-top: 13px;">订单批次属性<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select  style="vertical-align:top;" maxlength="32" title="批次属性"
													 disabled='disabled'>
												<c:forEach items="${orderBatchList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.ORDERBATCH_TYPE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>
									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">备注</td>
										<td colspan="7"><input class="span11" type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" title="MEMO"/></td>
									</tr>
								</table>
								<c:if test="${pd.FLAG eq '1'}">
								<div class="alert alert-danger">
									该客户的对应的属性已经存在,不需要新增.
								</div>
							    </c:if>
								<c:if test="${pd.FLAG eq '0'}">
									<div class="alert alert-success">
										参考客户(${pd.CUSTOMER_CODE})的属性,请修改好保存.
									</div>
								</c:if>

								<table id="table_report1" class="table table-striped  table-hover table-condensed">
									<tbody>
									<tr>
										<td><strong>扩展字段</strong></td>
										<td>字段定义值</td>
										<td class="center">启用</td>
										<td>排序</td>
									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段1</td>
										<td style="width:100px;text-align: left;padding-top: 13px;"><input type="text" name="TIME_ONE" id="TIME_ONE" value="${pd.TIME_ONE}" maxlength="32"  title="扩展日期字段1"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_ONE_USE"  name='TIME_ONE_USE' value="1"
																	<c:if test="${pd.TIME_ONE_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_ONE_SORT" id="TIME_ONE_SORT" value="${pd.TIME_ONE_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段2</td>
										<td><input type="text" name="TIME_TWO" id="TIME_TWO" value="${pd.TIME_TWO}" maxlength="32"  title="扩展日期字段2"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_TWO_USE"  name='TIME_TWO_USE' value="1"
																	<c:if test="${pd.TIME_TWO_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_TWO_SORT" id="TIME_TWO_SORT" value="${pd.TIME_TWO_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段3</td>
										<td><input type="text" name="TIME_THR" id="TIME_THR" value="${pd.TIME_THR}" maxlength="32"  title="扩展日期字段3"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_THR_USE"  name='TIME_THR_USE' value="1"
																	<c:if test="${pd.TIME_THR_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_THR_SORT" id="TIME_THR_SORT" value="${pd.TIME_THR_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段4</td>
										<td><input type="text" name="TIME_FOU" id="TIME_FOU" value="${pd.TIME_FOU}" maxlength="32"  title="扩展日期字段4"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_FOU_USE"  name='TIME_FOU_USE' value="1"
																	<c:if test="${pd.TIME_FOU_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_FOU_SORT" id="TIME_FOU_SORT" value="${pd.TIME_FOU_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段5</td>
										<td><input type="text" name="TIME_FIV" id="TIME_FIV" value="${pd.TIME_FIV}" maxlength="32"  title="扩展日期字段5"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_FIV_USE"  name='TIME_FIV_USE' value="1"
																	<c:if test="${pd.TIME_FIV_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_FIV_SORT" id="TIME_FIV_SORT" value="${pd.TIME_FOU_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段6</td>
										<td><input type="text" name="TIME_SIX" id="TIME_SIX" value="${pd.TIME_SIX}" maxlength="32"  title="扩展日期字段6"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_SIX_USE"  name='TIME_SIX_USE' value="1"
																	<c:if test="${pd.TIME_SIX_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_SIX_SORT" id="TIME_SIX_SORT" value="${pd.TIME_SIX_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段7</td>
										<td><input type="text" name="TIME_SEV" id="TIME_SEV" value="${pd.TIME_SEV}" maxlength="32"  title="扩展日期字段7"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_SEV_USE"  name='TIME_SEV_USE' value="1"
																	<c:if test="${pd.TIME_SEV_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_SEV_SORT" id="TIME_SEV_SORT" value="${pd.TIME_SEV_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段8</td>
										<td><input type="text" name="TIME_EIG" id="TIME_EIG" value="${pd.TIME_EIG}" maxlength="32"  title="扩展日期字段8"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_EIG_USE"  name='TIME_EIG_USE' value="1"
																	<c:if test="${pd.TIME_EIG_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_EIG_SORT" id="TIME_EIG_SORT" value="${pd.TIME_EIG_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段9</td>
										<td><input type="text" name="TIME_NIG" id="TIME_NIG" value="${pd.TIME_NIG}" maxlength="32"  title="扩展日期字段9"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_NIG_USE"  name='TIME_NIG_USE' value="1"
																	<c:if test="${pd.TIME_NIG_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_NIG_SORT" id="TIME_NIG_SORT" value="${pd.TIME_NIG_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展日期字段10</td>
										<td><input type="text" name="TIME_TEN" id="TIME_TEN" value="${pd.TIME_TEN}" maxlength="32"  title="扩展日期字段10"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TIME_TEN_USE"  name='TIME_TEN_USE' value="1"
																	<c:if test="${pd.TIME_TEN_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TIME_TEN_SORT" id="TIME_TEN_SORT" value="${pd.TIME_TEN_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>

									<tr class="info" >
										<td style="width:95px;text-align: right;padding-top: 13px;"><strong>扩展数字字段1</strong> </td>
										<td><input type="text" name="NUM_ONE" id="NUM_ONE" value="${pd.NUM_ONE}" maxlength="32"  title="扩展数字字段1"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_ONE_USE"  name='NUM_ONE_USE' value="1"
																	<c:if test="${pd.NUM_ONE_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_ONE_SORT" id="NUM_ONE_SORT" value="${pd.NUM_ONE_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段2</td>
										<td><input type="text" name="NUM_TWO" id="NUM_TWO" value="${pd.NUM_TWO}" maxlength="32"  title="扩展数字字段2"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_TWO_USE"  name='NUM_TWO_USE' value="1"
																	<c:if test="${pd.NUM_TWO_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_TWO_SORT" id="NUM_TWO_SORT" value="${pd.NUM_TWO_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>

									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段3</td>
										<td><input type="text" name="NUM_THR" id="NUM_THR" value="${pd.NUM_THR}" maxlength="32"  title="扩展数字字段3"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_THR_USE"  name='NUM_THR_USE' value="1"
																	<c:if test="${pd.NUM_THR_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_THR_SORT" id="NUM_THR_SORT" value="${pd.NUM_THR_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段4</td>
										<td><input type="text" name="NUM_FOU" id="NUM_FOU" value="${pd.NUM_FOU}" maxlength="32"  title="扩展数字字段4"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_FOU_USE"  name='NUM_FOU_USE' value="1"
																	<c:if test="${pd.NUM_FOU_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_FOU_SORT" id="NUM_FOU_SORT" value="${pd.NUM_FOU_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段5</td>
										<td><input type="text" name="NUM_FIV" id="NUM_FIV" value="${pd.NUM_FIV}" maxlength="32"  title="扩展数字字段5"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_FIV_USE"  name='NUM_FIV_USE' value="1"
																	<c:if test="${pd.NUM_FIV_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_FIV_SORT" id="NUM_FIV_SORT" value="${pd.NUM_FIV_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段6</td>
										<td><input type="text" name="NUM_SIX" id="NUM_SIX" value="${pd.NUM_SIX}" maxlength="32"  title="扩展数字字段6"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_SIX_USE"  name='NUM_SIX_USE' value="1"
																	<c:if test="${pd.NUM_SIX_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_SIX_SORT" id="NUM_SIX_SORT" value="${pd.NUM_SIX_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段7</td>
										<td><input type="text" name="NUM_SEV" id="NUM_SEV" value="${pd.NUM_SEV}" maxlength="32"  title="扩展数字字段7"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_SEV_USE"  name='NUM_SEV_USE' value="1"
																	<c:if test="${pd.NUM_SEV_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_SEV_SORT" id="NUM_SEV_SORT" value="${pd.NUM_SEV_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段8</td>
										<td><input type="text" name="NUM_EIG" id="NUM_EIG" value="${pd.NUM_EIG}" maxlength="32"  title="扩展数字字段8"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_EIG_USE"  name='NUM_EIG_USE' value="1"
																	<c:if test="${pd.NUM_EIG_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_EIG_SORT" id="NUM_EIG_SORT" value="${pd.NUM_EIG_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段9</td>
										<td><input type="text" name="NUM_NIG" id="NUM_NIG" value="${pd.NUM_NIG}" maxlength="32"  title="扩展数字字段9"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_NIG_USE"  name='NUM_NIG_USE' value="1"
																	<c:if test="${pd.NUM_NIG_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_NIG_SORT" id="NUM_NIG_SORT" value="${pd.NUM_NIG_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展数字字段10</td>
										<td><input type="text" name="NUM_TEN" id="NUM_TEN" value="${pd.NUM_TEN}" maxlength="32"  title="扩展数字字段10"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="NUM_TEN_USE"  name='NUM_TEN_USE' value="1"
																	<c:if test="${pd.NUM_TEN_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="NUM_TEN_SORT" id="NUM_TEN_SORT" value="${pd.NUM_TEN_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr class="info">
										<td style="width:95px;text-align: right;padding-top: 13px;"><strong>扩展文本字段1</strong></td>
										<td><input type="text" name="TXT_ONE" id="TXT_ONE" value="${pd.TXT_ONE}" maxlength="32"  title="扩展文本字段1"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_ONE_USE"  name='TXT_ONE_USE' value="1"
																	<c:if test="${pd.TXT_ONE_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_ONE_SORT" id="TXT_ONE_SORT" value="${pd.TXT_ONE_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段2</td>
										<td><input type="text" name="TXT_TWO" id="TXT_TWO" value="${pd.TXT_TWO}" maxlength="32"  title="扩展文本字段2"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_TWO_USE"  name='TXT_TWO_USE' value="1"
																	<c:if test="${pd.TXT_TWO_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_TWO_SORT" id="TXT_TWO_SORT" value="${pd.TXT_TWO_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段3</td>
										<td><input type="text" name="TXT_THR" id="TXT_THR" value="${pd.TXT_THR}" maxlength="32"  title="扩展文本字段3"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_THR_USE"  name='TXT_THR_USE' value="1"
																	<c:if test="${pd.TXT_THR_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_THR_SORT" id="TXT_THR_SORT" value="${pd.TXT_THR_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段4</td>
										<td><input type="text" name="TXT_FOU" id="TXT_FOU" value="${pd.TXT_FOU}" maxlength="32"  title="扩展文本字段4"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_FOU_USE"  name='TXT_FOU_USE' value="1"
																	<c:if test="${pd.TXT_FOU_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_FOU_SORT" id="TXT_FOU_SORT" value="${pd.TXT_FOU_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段5</td>
										<td><input type="text" name="TXT_FIV" id="TXT_FIV" value="${pd.TXT_FIV}" maxlength="32"  title="扩展文本字段5"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_FIV_USE"  name='TXT_FIV_USE' value="1"
																	<c:if test="${pd.TXT_FIV_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_FIV_SORT" id="TXT_FIV_SORT" value="${pd.TXT_FIV_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段6</td>
										<td><input type="text" name="TXT_SIX" id="TXT_SIX" value="${pd.TXT_SIX}" maxlength="32"  title="扩展文本字段6"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_SIX_USE"  name='TXT_SIX_USE' value="1"
																	<c:if test="${pd.TXT_SIX_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_SIX_SORT" id="TXT_SIX_SORT" value="${pd.TXT_SIX_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段7</td>
										<td><input type="text" name="TXT_SEV" id="TXT_SEV" value="${pd.TXT_SEV}" maxlength="32"  title="扩展文本字段7"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_SEV_USE"  name='TXT_SEV_USE' value="1"
																	<c:if test="${pd.TXT_SEV_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_SEV_SORT" id="TXT_SEV_SORT" value="${pd.TXT_SEV_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段8</td>
										<td><input type="text" name="TXT_EIG" id="TXT_EIG" value="${pd.TXT_EIG}" maxlength="32"  title="扩展文本字段8"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_EIG_USE"  name='TXT_EIG_USE' value="1"
																	<c:if test="${pd.TXT_EIG_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_EIG_SORT" id="TXT_EIG_SORT" value="${pd.TXT_EIG_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段9</td>
										<td><input type="text" name="TXT_NIG" id="TXT_NIG" value="${pd.TXT_NIG}" maxlength="32"  title="扩展文本字段9"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_NIG_USE"  name='TXT_NIG_USE' value="1"
																	<c:if test="${pd.TXT_NIG_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_NIG_SORT" id="TXT_NIG_SORT" value="${pd.TXT_NIG_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段10</td>
										<td><input type="text" name="TXT_TEN" id="TXT_TEN" value="${pd.TXT_TEN}" maxlength="32"  title="扩展文本字段10"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_TEN_USE"  name='TXT_TEN_USE' value="1"
																	<c:if test="${pd.TXT_TEN_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_TEN_SORT" id="TXT_TEN_SORT" value="${pd.TXT_TEN_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段11</td>
										<td><input type="text" name="TXT_ELEVEN" id="TXT_ELEVEN" value="${pd.TXT_ELEVEN}" maxlength="32"  title="扩展文本字段11"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_ELEVEN_USE"  name='TXT_ELEVEN_USE' value="1"
																	<c:if test="${pd.TXT_ELEVEN_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_ELEVEN_SORT" id="TXT_ELEVEN_SORT" value="${pd.TXT_ELEVEN_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段12</td>
										<td><input type="text" name="TXT_TWELVE" id="TXT_TWELVE" value="${pd.TXT_TWELVE}" maxlength="32"  title="扩展文本字段12"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_TWELVE_USE"  name='TXT_TWELVE_USE' value="1"
																	<c:if test="${pd.TXT_TWELVE_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_TWELVE_SORT" id="TXT_TWELVE_SORT" value="${pd.TXT_TWELVE_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段13</td>
										<td><input type="text" name="TXT_THIRT" id="TXT_THIRT" value="${pd.TXT_THIRT}" maxlength="32"  title="扩展文本字段13"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_THIRT_USE"  name='TXT_THIRT_USE' value="1"
																	<c:if test="${pd.TXT_THIRT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_THIRT_SORT" id="TXT_THIRT_SORT" value="${pd.TXT_THIRT_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段14</td>
										<td><input type="text" name="TXT_FOURT" id="TXT_FOURT" value="${pd.TXT_FOURT}" maxlength="32"  title="扩展文本字段14"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_FOURT_USE"  name='TXT_FOURT_USE' value="1"
																	<c:if test="${pd.TXT_FOURT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_FOURT_SORT" id="TXT_FOURT_SORT" value="${pd.TXT_FOURT_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段15</td>
										<td><input type="text" name="TXT_FIFT" id="TXT_FIFT" value="${pd.TXT_FIFT}" maxlength="32"  title="扩展文本字段15"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_FIFT_USE"  name='TXT_FIFT_USE' value="1"
																	<c:if test="${pd.TXT_FIFT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_FIFT_SORT" id="TXT_FIFT_SORT" value="${pd.TXT_FIFT_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段16</td>
										<td><input type="text" name="TXT_SIXT" id="TXT_SIXT" value="${pd.TXT_SIXT}" maxlength="32"  title="扩展文本字段16"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_SIXT_USE"  name='TXT_SIXT_USE' value="1"
																	<c:if test="${pd.TXT_SIXT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_SIXT_SORT" id="TXT_SIXT_SORT" value="${pd.TXT_SIXT_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段17</td>
										<td><input type="text" name="TXT_SEVENT" id="TXT_SEVENT" value="${pd.TXT_SEVENT}" maxlength="32"  title="扩展文本字段17"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_SEVENT_USE"  name='TXT_SEVENT_USE' value="1"
																	<c:if test="${pd.TXT_SEVENT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_SEVENT_SORT" id="TXT_SEVENT_SORT" value="${pd.TXT_SEVENT_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段18</td>
										<td><input type="text" name="TXT_EIGHT" id="TXT_EIGHT" value="${pd.TXT_EIGHT}" maxlength="32"  title="扩展文本字段18"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_EIGHT_USE"  name='TXT_EIGHT_USE' value="1"
																	<c:if test="${pd.TXT_EIGHT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_EIGHT_SORT" id="TXT_EIGHT_SORT" value="${pd.TXT_EIGHT_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段19</td>
										<td><input type="text" name="TXT_NINET" id="TXT_NINET" value="${pd.TXT_NINET}" maxlength="32"  title="扩展文本字段19"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_NINET_USE"  name='TXT_NINET_USE' value="1"
																	<c:if test="${pd.TXT_NINET_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_NINET_SORT" id="TXT_NINET_SORT" value="${pd.TXT_NINET_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>
									<tr>
										<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段20</td>
										<td><input type="text" name="TXT_TWENT" id="TXT_TWENT" value="${pd.TXT_TWENT}" maxlength="32"  title="扩展文本字段20"/></td>
										<td  class="center"><input  class="input-small" type='checkbox' id="TXT_TWENT_USE"  name='TXT_TWENT_USE' value="1"
																	<c:if test="${pd.TXT_TWENT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td><input type="number" class="input-small" name="TXT_TWENT_SORT" id="TXT_TWENT_SORT" value="${pd.TXT_TWENT_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

									</tr>

									<c:if test="${pd.ORDERBATCH_TYPE eq '62' or pd.ORDERBATCH_TYPE eq '63'}">
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段21</td>
											<td><input type="text" name="TXT_21" id="TXT_21" value="${pd.TXT_21}" maxlength="32"  title="扩展文本字段21"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_21_USE"  name='TXT_21_USE' value="1"
																		<c:if test="${pd.TTXT_21_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_21_SORT" id="TXT_21_SORT" value="${pd.TXT_21_SORT}" placeholder="0" maxlength="32" title="排序"/></td>
										</tr>

										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段22</td>
											<td><input type="text" name="TXT_22" id="TXT_22" value="${pd.TXT_22}" maxlength="32"  title="扩展文本字段22"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_22_USE"  name='TXT_22_USE' value="1"
																		<c:if test="${pd.TXT_22_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_22_SORT" id="TXT_22_SORT" value="${pd.TXT_22_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段23</td>
											<td><input type="text" name="TXT_23" id="TXT_23" value="${pd.TXT_23}" maxlength="32"  title="扩展文本字段23"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_23_USE"  name='TXT_23_USE' value="1"
																		<c:if test="${pd.TXT_23_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_23_SORT" id="TXT_23_SORT" value="${pd.TXT_23_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段24</td>
											<td><input type="text" name="TXT_24" id="TXT_24" value="${pd.TXT_24}" maxlength="32"  title="扩展文本字段24"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_24_USE"  name='TXT_24_USE' value="1"
																		<c:if test="${pd.TXT_24_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_24_SORT" id="TXT_24_SORT" value="${pd.TXT_24_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段25</td>
											<td><input type="text" name="TXT_25" id="TXT_25" value="${pd.TXT_25}" maxlength="32"  title="扩展文本字段25"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_25_USE"  name='TXT_25_USE' value="1"
																		<c:if test="${pd.TXT_25_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_25_SORT" id="TXT_25_SORT" value="${pd.TXT_25_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段26</td>
											<td><input type="text" name="TXT_26" id="TXT_26" value="${pd.TXT_26}" maxlength="32"  title="扩展文本字段26"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_26_USE"  name='TXT_26_USE' value="1"
																		<c:if test="${pd.TXT_26_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_26_SORT" id="TXT_26_SORT" value="${pd.TXT_26_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段27</td>
											<td><input type="text" name="TXT_27" id="TXT_27" value="${pd.TXT_27}" maxlength="32"  title="扩展文本字段27"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_27_USE"  name='TXT_27_USE' value="1"
																		<c:if test="${pd.TXT_27_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_27_SORT" id="TXT_27_SORT" value="${pd.TXT_27_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段28</td>
											<td><input type="text" name="TXT_28" id="TXT_28" value="${pd.TXT_28}" maxlength="32"  title="扩展文本字段28"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_28_USE"  name='TXT_28_USE' value="1"
																		<c:if test="${pd.TXT_28_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_28_SORT" id="TXT_28_SORT" value="${pd.TXT_28_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段29</td>
											<td><input type="text" name="TXT_29" id="TXT_29" value="${pd.TXT_29}" maxlength="32"  title="扩展文本字段29"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_29_USE"  name='TXT_29_USE' value="1"
																		<c:if test="${pd.TXT_29_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_29_SORT" id="TXT_29_SORT" value="${pd.TXT_29_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
										<tr>
											<td style="width:95px;text-align: right;padding-top: 13px;">扩展文本字段30</td>
											<td><input type="text" name="TXT_30" id="TXT_30" value="${pd.TXT_30}" maxlength="32"  title="扩展文本字段30"/></td>
											<td  class="center"><input  class="input-small" type='checkbox' id="TXT_30_USE"  name='TXT_30_USE' value="1"
																		<c:if test="${pd.TXT_30_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											</td>
											<td><input type="number" class="input-small" name="TXT_30_SORT" id="TXT_30_SORT" value="${pd.TXT_30_SORT}" placeholder="0" maxlength="32" title="排序"/></td>

										</tr>
									</c:if>
									<%--<tr>
										<td style="text-align: center;" colspan="10">
											<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
										</td>
									</tr>--%>
									</tbody>
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