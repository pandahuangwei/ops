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
			if($("#PACK_CN").val()==""){
			$("#PACK_CN").tips({
				side:3,
	            msg:'请输入包装规则',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PACK_CN").focus();
			return false;
		}
		if($("#PACK_DSC").val()==""){
			$("#PACK_DSC").tips({
				side:3,
	            msg:'请输入包装描述',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PACK_DSC").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function hasCode(){
		var PACK_CN = $.trim($("#PACK_CN").val());

		if (PACK_CN ==null || PACK_CN=='') {
			$("#LOCATOR_CODE").tips({
				side:3,
				msg:'请输入规则',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var PACKRULE_ID = $.trim($("#PACKRULE_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>packrule/hasCode.do',
			data: {PACK_CN:PACK_CN,PACKRULE_ID:PACKRULE_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#PACK_CN").tips({
						side:3,
						msg:'规则已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#PACK_CN').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="packrule/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PACKRULE_ID" id="PACKRULE_ID" value="${pd.PACKRULE_ID}"/>
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>包装规则</h6>
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
										<td style="width:70px;text-align: right;padding-top: 13px;">包装规则<span class="red">*</span></td>
										<td ><input onchange="hasCode()" type="text" name="PACK_CN" id="PACK_CN" value="${pd.PACK_CN}" maxlength="32" placeholder="" title="包装规则"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">包装描述<span class="red">*</span></td>
										<td ><input type="text" name="PACK_DSC" id="PACK_DSC" value="${pd.PACK_DSC}" maxlength="32" placeholder="" title="包装描述"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">标准包装</td>
										<td >
											<input style="padding-top: 40px;" type='checkbox' id="STANDARD_PACK"  name='STANDARD_PACK' value="1"
												   <c:if test="${pd.STANDARD_PACK eq 1 }">checked="checked" </c:if>/><span class="lbl padding-top: 40px"></span>

										</td>
									</tr>
								</table>

								<table id="table_report1" class="table table-striped  table-hover table-condensed">

									<tbody>
									<tr>
										<td><strong>基础信息</strong></td>
										<td>数量</td>
										<td>描述</td>
										<td>装箱</td>
										<td>补货</td>
										<td>收货标签单元</td>
										<td>出货标签单元</td>
									</tr>
									<tr style="margin: 0">
										<td style="width:70px;text-align: right;padding-top: 13px;">EA</td>
										<td ><input class="input-mini" type="number" readonly="readonly" name="EA_NUM" id="EA_NUM" value="${pd.EA_NUM}" maxlength="32"  placeholder="0" title="EA数量"/></td>

										<td ><input class="form-control" type="text" name="EA_DSC" id="EA_DSC" value="${pd.EA_DSC}" maxlength="32" title="EA描述"/></td>

										<td  class="center"><input  class="input-small" type='checkbox' id="EA_DOCK"  name='EA_DOCK' value="1"
																	<c:if test="${pd.EA_DOCK eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td  class="center"><input  class="input-small" type='checkbox' id="EA_REPL"  name='EA_REPL' value="1"
																	<c:if test="${pd.EA_REPL eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td  class="center"><input  class="input-small" type='checkbox' id="EA_IN"  name='EA_IN' value="1"
																	<c:if test="${pd.EA_IN eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td  class="center"><input  class="input-small" type='checkbox' id="EA_OUT"  name='EA_OUT' value="1"
																	<c:if test="${pd.EA_OUT eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">内包装</td>
										<td ><input class="input-mini" type="number" name="INPACK_NUM" id="INPACK_NUM" value="${pd.INPACK_NUM}" maxlength="32"  placeholder="0"  title="EA数量"/></td>
										<div class="col-xs-4">
											<td ><input class="form-control" type="text" name="INPACK_DSC" id="INPACK_DSC" value="${pd.INPACK_DSC}" maxlength="32" title="EA描述"/></td>
										</div>
										<td class="center"><input  class="input-small" type='checkbox' id="INPACK_DOCK"  name='INPACK_DOCK' value="1"
																   <c:if test="${pd.INPACK_DOCK eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="INPACK_REPL"  name='INPACK_REPL' value="1"
																   <c:if test="${pd.INPACK_REPL eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="INPACK_IN"  name='INPACK_IN' value="1"
																   <c:if test="${pd.INPACK_IN eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="INPACK_OUT"  name='INPACK_OUT' value="1"
																   <c:if test="${pd.INPACK_OUT eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">箱</td>
										<td ><input class="input-mini" type="number" name="BOX_NUM" id="BOX_NUM" value="${pd.BOX_NUM}" maxlength="32"  placeholder="0"  title="EA数量"/></td>
										<div class="col-xs-4">
											<td ><input class="form-control" type="text" name="BOX_DSC" id="BOX_DSC" value="${pd.BOX_DSC}" maxlength="32" title="EA描述"/></td>
										</div>
										<td class="center"><input  class="input-small" type='checkbox' id="BOX_DOCK"  name='BOX_DOCK' value="1"
																   <c:if test="${pd.BOX_DOCK eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="BOX_REPL"  name='BOX_REPL' value="1"
																   <c:if test="${pd.BOX_REPL eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="BOX_IN"  name='BOX_IN' value="1"
																   <c:if test="${pd.BOX_IN eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="BOX_OUT"  name='BOX_OUT' value="1"
																   <c:if test="${pd.BOX_OUT eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">托盘</td>
										<td ><input class="input-mini" type="number" name="PALLET_NUM" id="PALLET_NUM" value="${pd.PALLET_NUM}" maxlength="32"  placeholder="0" title="EA数量"/></td>
										<div class="col-xs-4">
											<td ><input class="form-control" type="text" name="PALLET_DSC" id="PALLET_DSC" value="${pd.PALLET_DSC}" maxlength="32" title="EA描述"/></td>
										</div>
										<td class="center"><input  class="input-small" type='checkbox' id="PALLET_DOCK"  name='PALLET_DOCK' value="1"
																   <c:if test="${pd.PALLET_DOCK eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="PALLET_REPL"  name='PALLET_REPL' value="1"
																   <c:if test="${pd.PALLET_REPL eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="PALLET_IN"  name='PALLET_IN' value="1"
																   <c:if test="${pd.PALLET_IN eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="PALLET_OUT"  name='PALLET_OUT' value="1"
																   <c:if test="${pd.PALLET_OUT eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">其他</td>
										<td ><input class="input-mini" type="number" name="OTHER_NUM" id="OTHER_NUM" value="${pd.OTHER_NUM}" maxlength="32" placeholder="0" title="EA数量"/></td>
										<div class="col-xs-4">
											<td ><input class="form-control" type="text" name="OTHER_DSC" id="OTHER_DSC" value="${pd.OTHER_DSC}" maxlength="32" title="EA描述"/></td>
										</div>
										<td class="center"><input  class="input-small" type='checkbox' id="OTHER_DOCK"  name='OTHER_DOCK' value="1"
																   <c:if test="${pd.OTHER_DOCK eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="OTHER_REPL"  name='OTHER_REPL' value="1"
																   <c:if test="${pd.OTHER_REPL eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="OTHER_IN"  name='OTHER_IN' value="1"
																   <c:if test="${pd.OTHER_IN eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td class="center"><input  class="input-small" type='checkbox' id="OTHER_OUT"  name='OTHER_OUT' value="1"
																   <c:if test="${pd.OTHER_OUT eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									</tr>

									</tbody>
								</table>

								<table id="table_report" class="table table-striped  table-hover table-condensed">
									<tbody>
									<tr>
										<td><strong>附加</strong></td>
										<td>长</td>
										<td>宽</td>
										<td>高</td>
										<td>体积</td>
										<td>重量</td>
										<td>TI</td>
										<td>HI</td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">EA</td>
										<td><input class="input-mini"  type="number" name="EA_LONG" id="EA_LONG" value="${pd.EA_LONG}" maxlength="32"  placeholder="0" title="EA长"/></td>
										<td><input class="input-mini" type="number" name="EA_WIDE" id="EA_WIDE" value="${pd.EA_WIDE}" maxlength="32"  placeholder="0" title="EA宽"/></td>
										<td><input class="input-mini" type="number" name="EA_HIGH" id="EA_HIGH" value="${pd.EA_HIGH}" maxlength="32" placeholder="0" title="EA高"/></td>
										<td><input class="input-mini" type="number" name="EA_VOLUME" id="EA_VOLUME" value="${pd.EA_VOLUME}" maxlength="32" placeholder="0" title="EA体积"/></td>
										<td><input class="input-mini" type="number" name="EA_WEIGH" id="EA_WEIGH" value="${pd.EA_WEIGH}" maxlength="32" placeholder="0" title="EA重量"/></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">内包装</td>
										<td><input class="input-mini" type="number" name="INPACK_LONG" id="INPACK_LONG" value="${pd.INPACK_LONG}" maxlength="32" placeholder="0"  title="INPACK长"/></td>
										<td><input class="input-mini" type="number" name="INPACK_WIDE" id="INPACK_WIDE" value="${pd.INPACK_WIDE}" maxlength="32" placeholder="0" title="INPACK宽"/></td>
										<td><input class="input-mini" type="number" name="INPACK_HIGH" id="INPACK_HIGH" value="${pd.INPACK_HIGH}" maxlength="32"  placeholder="0" title="INPACK高"/></td>
										<td><input class="input-mini" type="number" name="INPACK_VOLUME" id="INPACK_VOLUME" value="${pd.INPACK_VOLUME}" maxlength="32"  placeholder="0" title="INPACK体积"/></td>
										<td><input class="input-mini" type="number" name="INPACK_WEIGH" id="INPACK_WEIGH" value="${pd.INPACK_WEIGH}" maxlength="32" placeholder="0" title="INPACK重量"/></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">箱</td>
										<td><input class="input-mini" type="number" name="BOX_LONG" id="BOX_LONG" value="${pd.BOX_LONG}" maxlength="32" placeholder="0"  title="BOX长"/></td>
										<td><input class="input-mini" type="number" name="BOX_WIDE" id="BOX_WIDE" value="${pd.BOX_WIDE}" maxlength="32" placeholder="0" title="BOX宽"/></td>
										<td><input class="input-mini" type="number" name="BOX_HIGH" id="BOX_HIGH" value="${pd.BOX_HIGH}" maxlength="32"  placeholder="0" title="BOX高"/></td>
										<td><input class="input-mini" type="number" name="BOX_VOLUME" id="BOX_VOLUME" value="${pd.BOX_VOLUME}" maxlength="32"  placeholder="0" title="BOX体积"/></td>
										<td><input class="input-mini" type="number" name="BOX_WEIGH" id="BOX_WEIGH" value="${pd.BOX_WEIGH}" maxlength="32"  placeholder="0" title="BOX重量"/></td>
										<td></td>
										<td></td>
									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">托盘</td>
										<td><input class="input-mini" type="number" name="PALLET_LONG" id="PALLET_LONG" value="${pd.PALLET_LONG}" maxlength="32"  placeholder="0" title="PALLET长"/></td>
										<td><input class="input-mini" type="number" name="PALLET_WIDE" id="PALLET_WIDE" value="${pd.PALLET_WIDE}" maxlength="32" placeholder="0" title="PALLET宽"/></td>
										<td><input class="input-mini" type="number" name="PALLET_HIGH" id="PALLET_HIGH" value="${pd.PALLET_HIGH}" maxlength="32"  placeholder="0" title="PALLET高"/></td>
										<td><input class="input-mini" type="number" name="PALLET_VOLUME" id="PALLET_VOLUME" value="${pd.PALLET_VOLUME}" maxlength="32"  placeholder="0" title="PALLET体积"/></td>
										<td><input class="input-mini" type="number" name="PALLET_WEIGH" id="PALLET_WEIGH" value="${pd.PALLET_WEIGH}" maxlength="32" placeholder="0" title="PALLET重量"/></td>
										<td><input class="input-mini" type="number" name="PALLET_TI" id="PALLET_TI" value="${pd.PALLET_TI}" maxlength="32"  placeholder="0" title="PALLET体积"/></td>
										<td><input class="input-mini" type="number" name="PALLET_HI" id="PALLET_HI" value="${pd.PALLET_HI}" maxlength="32" placeholder="0" title="PALLET重量"/></td>

									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">其他</td>
										<td><input class="input-mini" type="number" name="OTHER_LONG" id="OTHER_LONG" value="${pd.OTHER_LONG}" maxlength="32"  placeholder="0" title="OTHER长"/></td>
										<td><input class="input-mini" type="number" name="OTHER_WIDE" id="OTHER_WIDE" value="${pd.OTHER_WIDE}" maxlength="32"   placeholder="0" title="OTHER宽"/></td>
										<td><input class="input-mini" type="number" name="OTHER_HIGH" id="OTHER_HIGH" value="${pd.OTHER_HIGH}" maxlength="32"  placeholder="0" title="OTHER高"/></td>
										<td><input class="input-mini" type="number" name="OTHER_VOLUME" id="OTHER_VOLUME" value="${pd.OTHER_VOLUME}" maxlength="32" placeholder="0" title="OTHER体积"/></td>
										<td><input class="input-mini" type="number" name="OTHER_WEIGH" id="OTHER_WEIGH" value="${pd.OTHER_WEIGH}" maxlength="32"  placeholder="0" title="OTHER重量"/></td>
										<td></td>
										<td></td>
									</tr>
									</tbody>
								</table>

								<%--<table id="table_report2" class="table table-striped table-bordered table-hover">
									<tr>
										<td style="text-align: center;" colspan="10">
											<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
										</td>
									</tr>
								</table>
--%>


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