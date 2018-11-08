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
			if($("#COUNTRY_CODE").val()==""){
			$("#COUNTRY_CODE").tips({
				side:3,
	            msg:'请输入国家代码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#COUNTRY_CODE").focus();
			return false;
		}
		if($("#COUNTRY_CN").val()==""){
			$("#COUNTRY_CN").tips({
				side:3,
	            msg:'请输入国家名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#COUNTRY_CN").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//判断编码是否存在
	function hasCtyCode(COUNTRYCODE){
		var NUMBER = $.trim($("#COUNTRY_CODE").val());
		if (NUMBER ==null || NUMBER=='') {
			$("#COUNTRY_CODE").tips({
				side:3,
				msg:'请输入国家代码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>country/hasCountryCode.do',
			data: {COUNTRYCODE:NUMBER,COUNTRYID:COUNTRYID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#COUNTRY_CODE").tips({
						side:3,
						msg:'编号已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#COUNTRY_CODE').val('')",2000);
				}
			}
		});
	}

	//判断编码是否存在
	function hasCtyCn(COUNTRYCN){
		var NUMBER = $.trim($("#COUNTRY_CN").val());
		if (NUMBER ==null || NUMBER=='') {
			$("#COUNTRY_CN").tips({
				side:3,
				msg:'请输入国家名称',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>country/hasCtyCn.do',
			data: {COUNTRYCN:NUMBER,COUNTRYID:COUNTRYID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#COUNTRY_CN").tips({
						side:3,
						msg:'名称已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#COUNTRY_CN').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="country/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="COUNTRY_ID" id="COUNTRY_ID" value="${pd.COUNTRY_ID}"/>
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>国家</h6>
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
										<td style="width:80px;text-align: right;padding-top: 13px;">国家代码:
											<span class="red">*</span>
										</td>
										<td><input type="text" name="COUNTRY_CODE" id="COUNTRY_CODE" value="${pd.COUNTRY_CODE}" maxlength="32" placeholder="" title="国家代码"
												   onblur="hasCtyCode('${pd.COUNTRY_CODE }')"/></td>

										<td style="width:80px;text-align: right;padding-top: 13px;">国家名称:
											<span class="red">*</span>
										</td>
										<td><input type="text" name="COUNTRY_CN" id="COUNTRY_CN" value="${pd.COUNTRY_CN}" maxlength="32" placeholder="" title="国家名称"
												   onblur="hasCtyCn('${pd.COUNTRY_CN }')"/></td>
									</tr>
									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">国家英文名称:</td>
										<td><input type="text" name="COUNTRY_EN" id="COUNTRY_EN" value="${pd.COUNTRY_EN}" maxlength="32" placeholder="" title="国家英文名称"/></td>

										<td style="width:80px;text-align: right;padding-top: 13px;">海关代码:</td>
										<td><input type="text" name="CUSTOM_CODE" id="CUSTOM_CODE" value="${pd.CUSTOM_CODE}" maxlength="32" placeholder="" title="海关代码"/></td>
									</tr>
									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">国家电话前缀:</td>
										<td><input type="text" name="COUNTRY_PRE" id="COUNTRY_PRE" value="${pd.COUNTRY_PRE}" maxlength="32" placeholder="" title="国家电话前缀"/></td>

										<td style="width:80px;text-align: right;padding-top: 13px;"></td>
										<td></td>
									</tr>
									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">备注:</td>
										<td colspan="7"><input class="span12" type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder="" title="备注"/></td>
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