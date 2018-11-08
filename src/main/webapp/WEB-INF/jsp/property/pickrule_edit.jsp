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
	            msg:'请输入客户',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CUSTOMER_ID").focus();
			return false;
		}
		if($("#PICK_RULE_CODE").val()==""){
			$("#PICK_RULE_CODE").tips({
				side:3,
	            msg:'请输入拣货规则编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PICK_RULE_CODE").focus();
			return false;
		}
		if($("#PICK_RULE_CN").val()==""){
			$("#PICK_RULE_CN").tips({
				side:3,
	            msg:'请输入拣货规则名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PICK_RULE_CN").focus();
			return false;
		}
		/*if($("#MEMO").val()==""){
			$("#MEMO").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MEMO").focus();
			return false;
		}
		if($("#PROD_COMB_USE").val()==""){
			$("#PROD_COMB_USE").tips({
				side:3,
	            msg:'请输入按料号合并拣货',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROD_COMB_USE").focus();
			return false;
		}
		if($("#OUT_PROD_DAY").val()==""){
			$("#OUT_PROD_DAY").tips({
				side:3,
	            msg:'请输入按出货日期合并拣货',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#OUT_PROD_DAY").focus();
			return false;
		}
		if($("#OUT_PROD_USE").val()==""){
			$("#OUT_PROD_USE").tips({
				side:3,
	            msg:'请输入启用',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#OUT_PROD_USE").focus();
			return false;
		}*/
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}


	function hasCode(){
		var PICK_RULE_CODE = $.trim($("#PICK_RULE_CODE").val());
		if (PICK_RULE_CODE ==null || PICK_RULE_CODE=='') {
			$("#PICK_RULE_CODE").tips({
				side:3,
				msg:'请输入编码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var PICKRULE_ID = $.trim($("#PICKRULE_ID").val());
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>pickrule/hasCode.do',
			data: {PICK_RULE_CODE:PICK_RULE_CODE,PICKRULE_ID:PICKRULE_ID,CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#PICK_RULE_CODE").tips({
						side:3,
						msg:'编码已存在,请重新输入...',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#PICK_RULE_CODE').val('')",2000);
				}
			}
		});
	}

	function hasCn(){
		var PICK_RULE_CN = $.trim($("#PICK_RULE_CN").val());
		if (PICK_RULE_CN ==null || PICK_RULE_CN=='') {
			$("#PICK_RULE_CN").tips({
				side:3,
				msg:'请输入名称',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var PICKRULE_ID = $.trim($("#PICKRULE_ID").val());
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>pickrule/hasCn.do',
			data: {PICK_RULE_CN:PICK_RULE_CN,PICKRULE_ID:PICKRULE_ID,CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#PICK_RULE_CN").tips({
						side:3,
						msg:'名称已存在,请重新输入...',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#PICK_RULE_CN').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="pickrule/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PICKRULE_ID" id="PICKRULE_ID" value="${pd.PICKRULE_ID}"/>
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>拣货规则</h6>
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
										<td style="width:160px;text-align: right;padding-top: 13px;">客户<span class="red">*</span></td>
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
										<td style="width:160px;text-align: right;padding-top: 13px;">拣货规则编码<span class="red">*</span></td>
										<td><input type="text" name="PICK_RULE_CODE" id="PICK_RULE_CODE" value="${pd.PICK_RULE_CODE}" maxlength="32" placeholder=""  onblur="hasCode()" title="拣货规则编码"/></td>

										<td style="width:160px;text-align: right;padding-top: 13px;">拣货规则名称<span class="red">*</span></td>
										<td><input type="text" name="PICK_RULE_CN" id="PICK_RULE_CN" value="${pd.PICK_RULE_CN}" maxlength="32" placeholder="" onblur="hasCn()" title="拣货规则名称"/></td>
									</tr>
									<tr>
										<td style="width:160px;text-align: right;padding-top: 13px;">备注</td>
										<td colspan="7"><input class="span11"   type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32"  title="备注"/></td>
									</tr>
									<tr>
										<td style="width:160px;text-align: right;padding-top: 13px;">按料号合并拣货</td>
										<td  class="left"><input  class="input-small" type='checkbox' id="PROD_COMB_USE"  name='PROD_COMB_USE' value="1"
																  <c:if test="${pd.PROD_COMB_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td style="width:160px;text-align: right;padding-top: 13px;">按出货日期合并拣货</td>
										<td><input type="text" class="date-picker" data-date-format="yyyy-mm-dd"  readOnly="readOnly" name="OUT_PROD_DAY" id="OUT_PROD_DAY" value="${pd.OUT_PROD_DAY}" placeholder="出货日期在此之前合并拣货" title="按出货日期合并拣货"/></td>

										<td style="width:160px;text-align: right;padding-top: 13px;">启用</td>
										<td  class="left"><input  class="input-small" type='checkbox' id="OUT_PROD_USE"  name='OUT_PROD_USE' value="1"
																  <c:if test="${pd.OUT_PROD_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
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