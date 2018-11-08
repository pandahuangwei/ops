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

			if($("#STOCKTURN_CODE").val()==""){
			$("#STOCKTURN_CODE").tips({
				side:3,
	            msg:'请输入库存周转规则代码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STOCKTURN_CODE").focus();
			return false;
		}
		if($("#STOCKTURN_CN").val()==""){
			$("#STOCKTURN_CN").tips({
				side:3,
	            msg:'请输入库存周转规则描述',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STOCKTURN_CN").focus();
			return false;
		}

		if($("#BATCH_PROPERTY").val()==""){
			$("#BATCH_PROPERTY").tips({
				side:3,
	            msg:'请输入批次属性',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BATCH_PROPERTY").focus();
			return false;
		}


		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//判断编码是否存在
	function hasCode(){
		var STOCKTURN_CODE = $.trim($("#STOCKTURN_CODE").val());
		if (STOCKTURN_CODE ==null || STOCKTURN_CODE=='') {
			$("#STOCKTURN_CODE").tips({
				side:3,
				msg:'请输入规则代码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var STOCKTURN_ID = $.trim($("#STOCKTURN_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockturn/hasCode.do',
			data: {STOCKTURN_CODE:STOCKTURN_CODE,STOCKTURN_ID:STOCKTURN_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#STOCKTURN_CODE").tips({
						side:3,
						msg:'规则代码已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#STOCKTURN_CODE').val('')",2000);
				}
			}
		});
	}

	//判断名称是否存在
	function hasCn(){
		var STOCKTURN_CN = $.trim($("#STOCKTURN_CN").val());
		if (STOCKTURN_CN ==null || STOCKTURN_CN=='') {
			$("#STOCKTURN_CN").tips({
				side:3,
				msg:'请输入规则描述',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var STOCKTURN_ID = $.trim($("#STOCKTURN_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockturn/hasCn.do',
			data: {STOCKTURN_CN:STOCKTURN_CN,STOCKTURN_ID:STOCKTURN_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#STOCKTURN_CN").tips({
						side:3,
						msg:'规则描述已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#STOCKTURN_CN').val('')",2000);
				}
			}
		});
	}
	
</script>
	</head>
<body>
	<form action="stockturn/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="STOCKTURN_ID" id="STOCKTURN_ID" value="${pd.STOCKTURN_ID}"/>
		<div id="zhongxin">
			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>库存周转规则</h6>
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
										<td style="width:120px;text-align: right;padding-top: 13px;">库存周转规则代码<span class="red">*</span></td>
										<td><input type="text" onblur="hasCode();" name="STOCKTURN_CODE" id="STOCKTURN_CODE" value="${pd.STOCKTURN_CODE}" maxlength="32" placeholder=" " title="库存周转规则代码"/></td>

										<td style="width:120px;text-align: right;padding-top: 13px;">库存周转规则描述<span class="red">*</span></td>
										<td><input type="text" onblur="hasCn();" name="STOCKTURN_CN" id="STOCKTURN_CN" value="${pd.STOCKTURN_CN}" maxlength="32" placeholder="" title="库存周转规则描述"/></td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">时间差(批次属性01-03有效)</td>
										<td><input type="text" name="TIME_DISTANCE" id="TIME_DISTANCE" value="${pd.TIME_DISTANCE}" maxlength="32" placeholder=" " title="时间差(批次属性01-03有效)"/></td>

										<td style="width:120px;text-align: right;padding-top: 13px;">优先级</td>
										<td><input type="number" min = "0" name="PRIORITY_LEVEL" id="PRIORITY_LEVEL" value="${pd.PRIORITY_LEVEL}" maxlength="32" placeholder=" " title="优先级"/></td>

									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">批次属性<span class="red">*</span></td>
										<%--<td><input type="text" name="BATCH_PROPERTY" id="BATCH_PROPERTY" value="${pd.BATCH_PROPERTY}" maxlength="32" placeholder=" " title="批次属性"/></td>--%>
										<td style="vertical-align:top;">
											<select  class="chzn-select" name="BATCH_PROPERTY" id="BATCH_PROPERTY" style="vertical-align:top;" data-placeholder=" "  >
												<option value=""></option>
												<c:forEach items="${batchProList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.BATCH_PROPERTY }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:120px;text-align: right;padding-top: 13px;">排序</td>
										<td style="vertical-align:top;">
											<select  class="chzn-select" name="SORT_BY" id="SORT_BY" style="vertical-align:top;" data-placeholder=" "  >
												<option value=""></option>
												<c:forEach items="${sortByList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.SORT_BY }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

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