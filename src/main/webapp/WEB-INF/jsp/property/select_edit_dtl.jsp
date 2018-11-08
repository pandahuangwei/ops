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

		if($("#C_CODE").val()==""){
			$("#C_CODE").tips({
				side:3,
	            msg:'请输入参数编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#C_CODE").focus();
			return false;
		}
		if($("#C_VALUE").val()==""){
			$("#C_VALUE").tips({
				side:3,
	            msg:'请输入参数名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#C_VALUE").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//判断编码是否存在
	function hasCode(){
		var C_CODE = $.trim($("#C_CODE").val());
		if (C_CODE ==null || C_CODE=='') {
			$("#C_CODE").tips({
				side:3,
				msg:'请输入选项编码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var SELECT_ID = $.trim($("#SELECT_ID").val());
		var GROUP_KEY = $.trim($("#GROUP_KEY").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>select/hasCode.do',
			data: {C_CODE:C_CODE,SELECT_ID:SELECT_ID,GROUP_KEY:GROUP_KEY,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#C_CODE").tips({
						side:3,
						msg:'选项编码已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#C_CODE').val('')",2000);
				}
			}
		});
	}

	function hasCn(){
		var C_VALUE = $.trim($("#C_VALUE").val());
		if (C_VALUE ==null || C_VALUE=='') {
			$("#C_VALUE").tips({
				side:3,
				msg:'请输入选项名称',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var SELECT_ID = $.trim($("#SELECT_ID").val());
		var GROUP_KEY = $.trim($("#GROUP_KEY").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>select/hasCn.do',
			data: {C_VALUE:C_VALUE,SELECT_ID:SELECT_ID,GROUP_KEY:GROUP_KEY,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#C_VALUE").tips({
						side:3,
						msg:'选项名称已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#C_VALUE').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="select/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="SELECT_ID" id="SELECT_ID" value="${pd.SELECT_ID}"/>
		<input type="hidden" name="GROUP_KEY" id="GROUP_KEY" value="${pd.GROUP_KEY}"/>
		<input type="hidden" name="GROUP_NAME" id="GROUP_NAME" value="${pd.GROUP_NAME}"/>

		<div id="zhongxin">
			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>参数定义</h6>
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
										<td style="width:70px;text-align: right;padding-top: 13px;">参数</td>
										<td><input readonly="readonly" type="text" value="${pd.GROUP_NAME}" maxlength="32" placeholder="" title="参数分组"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">描述</td>
										<td><input type="text" name="C_DESC" id="C_DESC" value="${pd.C_DESC}" maxlength="32" placeholder="" title="描述"/></td>
									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">选项编码<span class="red">*</span></td>
										<td><input type="text" name="C_CODE" id="C_CODE" value="${pd.C_CODE}" maxlength="32" placeholder="" title="参数编码" onblur="hasCode()"
												<c:if test="${pd.C_CODE != null && pd.C_CODE != ''  }"> readonly="readonly"</c:if>/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">选项名称<span class="red">*</span></td>
										<td><input type="text" name="C_VALUE" id="C_VALUE" value="${pd.C_VALUE}" maxlength="32" placeholder="" title="参数名称" onblur="hasCn()"/></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">排序</td>
										<td><input type="number" name="C_SORT" id="C_SORT" value="${pd.C_SORT}" maxlength="32" placeholder="" title="排序"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;"></td>
										<td></td>
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