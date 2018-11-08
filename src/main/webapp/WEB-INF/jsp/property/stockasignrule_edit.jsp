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
		if($("#STOCKASIGN_CODE").val()==""){
			$("#STOCKASIGN_CODE").tips({
				side:3,
	            msg:'请输入库存分配规则编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STOCKASIGN_CODE").focus();
			return false;
		}
		if($("#STOCKASIGN_CN").val()==""){
			$("#STOCKASIGN_CN").tips({
				side:3,
	            msg:'请输入库存分配规则名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STOCKASIGN_CN").focus();
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
		if($("#IN_STOCK_DAY").val()==""){
			$("#IN_STOCK_DAY").tips({
				side:3,
	            msg:'请输入入库日期',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#IN_STOCK_DAY").focus();
			return false;
		}
		if($("#IN_STOCK_CYCLE").val()==""){
			$("#IN_STOCK_CYCLE").tips({
				side:3,
	            msg:'请输入先进先出',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#IN_STOCK_CYCLE").focus();
			return false;
		}
		if($("#PRODUCE_DAY").val()==""){
			$("#PRODUCE_DAY").tips({
				side:3,
	            msg:'请输入生产日期',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PRODUCE_DAY").focus();
			return false;
		}
		if($("#PRODUCE_CYCLE").val()==""){
			$("#PRODUCE_CYCLE").tips({
				side:3,
	            msg:'请输入先进先出',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PRODUCE_CYCLE").focus();
			return false;
		}
		if($("#CUSTOMER_ASIGN").val()==""){
			$("#CUSTOMER_ASIGN").tips({
				side:3,
	            msg:'请输入客户指定',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CUSTOMER_ASIGN").focus();
			return false;
		}
		if($("#CUSTOMER_ASIGN_USE").val()==""){
			$("#CUSTOMER_ASIGN_USE").tips({
				side:3,
	            msg:'请输入启用',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CUSTOMER_ASIGN_USE").focus();
			return false;
		}*/

		if ($("#IN_STOCK_CYCLE_1").is(':checked')) {
			$("#IN_STOCK_CYCLE").val("1");
		} else if ($("#IN_STOCK_CYCLE_2").is(':checked')) {
			$("#IN_STOCK_CYCLE").val("2");
		}

		if ($("#PRODUCE_CYCLE_1").is(':checked')) {
			$("#PRODUCE_CYCLE").val("1");
		} else if ($("#PRODUCE_CYCLE_2").is(':checked')) {
			$("#PRODUCE_CYCLE").val("2");
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function setStockCycle11(obj) {

		if($(obj).is(':checked')) {
			$(obj).attr("checked",false);
		} else {
			$("#stockCycle").find(".checkbox").each(function(){
				if ($(obj) == $(this)) {
					$(obj).attr("checked",true);
				} else {
					$(this).attr("checked",false);
				}

			});
		}
	}

	function setStockCycle1() {
		$("#IN_STOCK_CYCLE_2").attr("checked",false);
	}

	function setStockCycle2() {
		$("#IN_STOCK_CYCLE_1").attr("checked",false);
	}

	function setProdCycle1() {
		$("#PRODUCE_CYCLE_2").attr("checked",false);
	}

	function setProdCycle2() {
		$("#PRODUCE_CYCLE_1").attr("checked",false);
	}

	function setProdLevel1() {
		$("#PRODUCE_LEVEL_2").attr("checked",false);
	}

	function setProdLevel2() {
		$("#PRODUCE_LEVEL_1").attr("checked",false);
	}




	function hasCode(){
		var STOCKASIGN_CODE = $.trim($("#STOCKASIGN_CODE").val());
		if (STOCKASIGN_CODE ==null || STOCKASIGN_CODE=='') {
			$("#STOCKASIGN_CODE").tips({
				side:3,
				msg:'请输入编码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var STOCKASIGNRULE_ID = $.trim($("#STOCKASIGNRULE_ID").val());
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockasignrule/hasCode.do',
			data: {STOCKASIGN_CODE:STOCKASIGN_CODE,STOCKASIGNRULE_ID:STOCKASIGNRULE_ID,CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#STOCKASIGN_CODE").tips({
						side:3,
						msg:'编码已存在,请重新输入...',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#STOCKASIGN_CODE').val('')",2000);
				}
			}
		});
	}

	function hasCn(){
		var STOCKASIGN_CN = $.trim($("#STOCKASIGN_CN").val());
		if (STOCKASIGN_CN ==null || STOCKASIGN_CN=='') {
			$("#STOCKASIGN_CN").tips({
				side:3,
				msg:'请输入名称',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var STOCKASIGNRULE_ID = $.trim($("#STOCKASIGNRULE_ID").val());
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockasignrule/hasCn.do',
			data: {STOCKASIGN_CN:STOCKASIGN_CN,STOCKASIGNRULE_ID:STOCKASIGNRULE_ID,CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#STOCKASIGN_CN").tips({
						side:3,
						msg:'名称已存在,请重新输入...',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#STOCKASIGN_CN').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="stockasignrule/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="STOCKASIGNRULE_ID" id="STOCKASIGNRULE_ID" value="${pd.STOCKASIGNRULE_ID}"/>
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>库存分配规则</h6>
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
										<td style="width:70px;text-align: right;padding-top: 13px;">库存分配规则编码<span class="red">*</span></td>
										<td><input type="text" name="STOCKASIGN_CODE" id="STOCKASIGN_CODE" value="${pd.STOCKASIGN_CODE}" maxlength="32" placeholder="" onblur="hasCode()" title="库存分配规则编码"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">库存分配规则名称<span class="red">*</span></td>
										<td><input type="text" name="STOCKASIGN_CN" id="STOCKASIGN_CN" value="${pd.STOCKASIGN_CN}" maxlength="32" placeholder="" onblur="hasCn()" title="库存分配规则名称"/></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">备注</td>
										<td colspan="7"><input class="span11"  type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32"  title="备注"/></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">入库日期</td>
										<td><input name="IN_STOCK_DAY" id="IN_STOCK_DAY" value="${pd.IN_STOCK_DAY}" type="number"   placeholder="0" title="入库日期"/>
										 <span class="red">天(小于此天数可出货)</span>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;"></td>
										<input type="hidden" name="IN_STOCK_CYCLE" id="IN_STOCK_CYCLE" value="${pd.IN_STOCK_CYCLE}"/>
										<td>先进先出
											<input  class="input-small" type='checkbox' id="IN_STOCK_CYCLE_1"  name='IN_STOCK_CYCLE' value="1"
													onclick="setStockCycle1()"	<c:if test="${pd.IN_STOCK_CYCLE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											先进后出
											<input  class="input-small" type='checkbox' id="IN_STOCK_CYCLE_2"  name='IN_STOCK_CYCLE' value="2"
													onclick="setStockCycle2()"	<c:if test="${pd.IN_STOCK_CYCLE eq 2 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>

									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">生产日期</td>
										<td><input name="PRODUCE_DAY" id="PRODUCE_DAY" value="${pd.PRODUCE_DAY}" type="number"   placeholder="0" title="生产日期"/>
											<span class="red">天(小于此天数可出货)</span>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;"></td>
										<input type="hidden" name="PRODUCE_CYCLE" id="PRODUCE_CYCLE" value="${pd.PRODUCE_CYCLE}"/>
										<td>先进先出
											<input  class="input-small" type='checkbox' id="PRODUCE_CYCLE_1"  name='PRODUCE_CYCLE' value="1"
													onclick="setProdCycle1()"	<c:if test="${pd.PRODUCE_CYCLE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											先进后出
											<input  class="input-small" type='checkbox' id="PRODUCE_CYCLE_2"  name='PRODUCE_CYCLE' value="2"
													onclick="setProdCycle2()"	<c:if test="${pd.PRODUCE_CYCLE eq 2 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>

									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">优先级</td>
										<input type="hidden" name="PRODUCE_LEVEL" id="PRODUCE_LEVEL" value="${pd.PRODUCE_LEVEL}"/>
										<td style="padding-top: 13px;">入库日期
											<input  class="input-small" type='checkbox' id="PRODUCE_LEVEL_1"  name='PRODUCE_LEVEL' value="1"
													onclick="setProdLevel1()"	<c:if test="${pd.PRODUCE_LEVEL eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
											生产日期
											<input  class="input-small" type='checkbox' id="PRODUCE_LEVEL_2"  name='PRODUCE_LEVEL' value="2"
													onclick="setProdLevel2()"	<c:if test="${pd.PRODUCE_LEVEL eq 2 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>

										<%--<td style="width:70px;text-align: right;padding-top: 13px;">客户指定</td>
										<td><input type="text" name="CUSTOMER_ASIGN" id="CUSTOMER_ASIGN" value="${pd.CUSTOMER_ASIGN}" maxlength="32" placeholder="这里输入客户指定" title="客户指定"/></td>--%>

										<td style="width:70px;text-align: right;padding-top: 13px;">启用</td>
										<td  class="left"><input  class="input-small" type='checkbox' id="CUSTOMER_ASIGN_USE"  name='CUSTOMER_ASIGN_USE' value="1"
																  <c:if test="${pd.CUSTOMER_ASIGN_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
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