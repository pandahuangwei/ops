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
			if($("#PROVINCE_CODE").val()==""){
			$("#PROVINCE_CODE").tips({
				side:3,
	            msg:'请输入省/州代码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROVINCE_CODE").focus();
			return false;
		}
		if($("#PROVINCE_CN").val()==""){
			$("#PROVINCE_CN").tips({
				side:3,
	            msg:'请输入省/州名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROVINCE_CN").focus();
			return false;
		}
		/*
		if($("#COUNTRY_ID").val()==""){
			$("#COUNTRY_ID").tips({
				side:3,
	            msg:'请输入国家',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#COUNTRY_ID").focus();
			return false;
		}
		if($("#PROVINCE_EN").val()==""){
			$("#PROVINCE_EN").tips({
				side:3,
	            msg:'请输入省/州英文名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROVINCE_EN").focus();
			return false;
		}
		if($("#MEMO").val()==""){
			$("#MEMO").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MEMO").focus();
			return false;
		}
		if($("#CREATE_TM").val()==""){
			$("#CREATE_TM").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATE_TM").focus();
			return false;
		}*/
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}


	//判断编码是否存在
	function hasCode(){
		var NUMBER = $.trim($("#PROVINCE_CODE").val());
		if (NUMBER ==null || NUMBER=='') {
			$("#PROVINCE_CODE").tips({
				side:3,
				msg:'请输入代码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		var PROVINCEID = $.trim($("#PROVINCE_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>province/hasProvinceCode.do',
			data: {PROVINCECODE:NUMBER,COUNTRYID:COUNTRYID,PROVINCEID:PROVINCEID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#PROVINCE_CODE").tips({
						side:3,
						msg:'编号已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#PROVINCE_CODE').val('')",2000);
				}
			}
		});
	}

	//判断名称是否存在
	function hasCn(){
		var NUMBER = $.trim($("#PROVINCE_CN").val());
		if (NUMBER ==null || NUMBER=='') {
			$("#PROVINCE_CN").tips({
				side:3,
				msg:'请输入名称',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		var PROVINCEID = $.trim($("#PROVINCE_ID").val());

		$.ajax({
			type: "POST",
			url: '<%=basePath%>province/hasProvinceCn.do',
			data: {PROVINCECN:NUMBER,COUNTRYID:COUNTRYID,PROVINCEID:PROVINCEID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#PROVINCE_CN").tips({
						side:3,
						msg:'名称已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#PROVINCE_CN').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="province/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PROVINCE_ID" id="PROVINCE_ID" value="${pd.PROVINCE_ID}"/>
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>省份</h6>
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

									<%--<tr>
                                        <td style="width:70px;text-align: right;padding-top: 13px;">国家:</td>
                                        <td><input type="text" name="COUNTRY_ID" id="COUNTRY_ID" value="${pd.COUNTRY_ID}" maxlength="32" placeholder="这里输入国家" title="国家"/></td>
                                    </tr>--%>

									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">国家<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select class="chzn-select" name="COUNTRY_ID" id="COUNTRY_ID" data-placeholder="请选择国家" style="vertical-align:top;" maxlength="32">

												<c:forEach items="${countryList}" var="cnt">
													<option value="${cnt.COUNTRY_ID }" <c:if test="${cnt.COUNTRY_ID == pd.COUNTRY_ID }">selected</c:if>>${cnt.COUNTRY_CN }</option>
												</c:forEach>
											</select>
										</td>
									</tr>

									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">省/州代码<span class="red">*</span></td>
										<td><input type="text" name="PROVINCE_CODE" id="PROVINCE_CODE" value="${pd.PROVINCE_CODE}" maxlength="32" placeholder=" " title="省/州代码"
												   onblur="hasCode()"/></td>
									</tr>
									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">省/州名称<span class="red">*</span></td>
										<td><input type="text" name="PROVINCE_CN" id="PROVINCE_CN" value="${pd.PROVINCE_CN}" maxlength="32" placeholder=" " title="省/州名称"
												   onblur="hasCn()"/></td>
									</tr>

									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">省/州英文名称</td>
										<td><input type="text" name="PROVINCE_EN" id="PROVINCE_EN" value="${pd.PROVINCE_EN}" maxlength="32" placeholder=" " title="省/州英文名称"/></td>
									</tr>
									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">备注</td>
										<td><input type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder=" " title="备注"/></td>
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
			$(".chzn-select").chosen({search_contains: true});
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker({autoclose: true});
			
		});
		
		</script>
</body>
</html>