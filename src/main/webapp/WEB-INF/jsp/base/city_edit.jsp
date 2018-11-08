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

			if($("#PROVINCE_ID").val()=="" || $("#PROVINCE_ID").val()== null){
			$("#PROVINCE_ID").tips({
				side:3,
	            msg:'请输入省份名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROVINCE_ID").focus();
			return false;
		}
		if($("#CITY_CODE").val()==""){
			$("#CITY_CODE").tips({
				side:3,
	            msg:'请输入城市代码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CITY_CODE").focus();
			return false;
		}
		if($("#CITY_PARENT_CN").val()==""){
			$("#CITY_PARENT_CN").tips({
				side:3,
	            msg:'请输入上级城市代码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CITY_PARENT_CN").focus();
			return false;
		}
		if($("#CITY_CN").val()==""){
			$("#CITY_CN").tips({
				side:3,
	            msg:'请输入城市名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CITY_CN").focus();
			return false;
		}
		/*if($("#OFFER_FLG").val()==""){
			$("#OFFER_FLG").tips({
				side:3,
	            msg:'请输入是否报价线路',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#OFFER_FLG").focus();
			return false;
		}
		if($("#CITY_EN").val()==""){
			$("#CITY_EN").tips({
				side:3,
	            msg:'请输入城市英文名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CITY_EN").focus();
			return false;
		}
		if($("#CITY_AREA").val()==""){
			$("#CITY_AREA").tips({
				side:3,
	            msg:'请输入城市区域',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CITY_AREA").focus();
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
		}*/
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	/**
	 * 获取市列表
	 */
	function getProvince3(provinceID) {
		$('#PROVINCE_ID').empty();
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		$.ajax({
			type : "POST",
			url : '<%=basePath%>city/getProvince.do',
			data: {PROVINCECN:'',COUNTRYID:COUNTRYID,tm:new Date().getTime()},
			success : function(data) {
				$.each(data, function(i, it) {
					alert(it[i]);
					$("<option value='+it[i].PROVINCE_ID+' code='+it[i].PROVINCE_CN+'>'+it[i].PROVINCE_CN+'</option>");
					}).appendTo($('#PROVINCE_ID'));
			}
		});
	}

	//根据国家id获取城市
	function getProvince(){
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		if (COUNTRYID ==null || COUNTRYID=='') {
			return false;
		}
        //动态删除select中的所有options
		document.getElementById("PROVINCE_ID").options.length=0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>city/getProvince.do',
			data: {PROVINCECN:'',COUNTRYID:COUNTRYID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#PROVINCE_ID_chzn").remove();
				$("#PROVINCE_ID").append($("<option value=''></option>"));
				for (var k in data) {
					$("#PROVINCE_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
			},
			complete: function (XMLHttpRequest, textStatus) {
				$("#PROVINCE_ID").attr("class","chzn-select");
				$(".chzn-select").chosen({search_contains: true});

			}
		});
	}

	function getCity(){
		var PROVINCEID = $.trim($("#PROVINCE_ID").val());
		if (PROVINCEID ==null || PROVINCEID=='') {
			return false;
		}
		//动态删除select中的所有options
		document.getElementById("CITY_PARENT_ID").options.length=0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>city/getCity.do',
			data: {PROVINCECN:'',PROVINCEID:PROVINCEID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				for (var k in data) {
					$("#CITY_PARENT_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
			}
		});
	}


	//判断编码是否存在
	function hasCode(){
		var NUMBER = $.trim($("#CITY_CODE").val());
		if (NUMBER ==null || NUMBER=='') {
			$("#CITY_CODE").tips({
				side:3,
				msg:'请输入代码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		var CITYID = $.trim($("#CITY_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>city/hasCityCode.do',
			data: {CITYCODE:NUMBER,COUNTRYID:COUNTRYID,CITYID:CITYID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#CITY_CODE").tips({
						side:3,
						msg:'代码已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#CITY_CODE').val('')",2000);
				}
			}
		});
	}

	//判断名称是否存在
	function hasCn(){
		var NUMBER = $.trim($("#CITY_CN").val());
		if (NUMBER ==null || NUMBER=='') {
			$("#CITY_CN").tips({
				side:3,
				msg:'请输入名称',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		var CITYID = $.trim($("#CITY_ID").val());

		$.ajax({
			type: "POST",
			url: '<%=basePath%>city/hasCityCn.do',
			data: {CITYCN:NUMBER,COUNTRYID:COUNTRYID,CITYID:CITYID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#CITY_CN").tips({
						side:3,
						msg:'名称已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#CITY_CN').val('')",2000);
				}
			}
		});
	}

</script>
	</head>
<body>
	<form action="city/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="CITY_ID" id="CITY_ID" value="${pd.CITY_ID}"/>
		<div id="zhongxin">
			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>市/县</h6>
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
										<td style="width:80px;text-align: right;padding-top: 13px;">国家<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select class="chzn-select" name="COUNTRY_ID" id="COUNTRY_ID" data-placeholder="请选择国家" style="vertical-align:top;" maxlength="32"
													onChange="getProvince()">
												<option value=""></option>
												<c:forEach items="${countryList}" var="cnt">
													<option value="${cnt.COUNTRY_ID }" <c:if test="${cnt.COUNTRY_ID == pd.COUNTRY_ID }">selected</c:if>>${cnt.COUNTRY_CN }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:80px;text-align: right;padding-top: 13px;">省份名称<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select  class="chzn-select" name="PROVINCE_ID" id="PROVINCE_ID" data-placeholder="请选择省份" style="vertical-align:top;" maxlength="32"
													 >
												<option value=""></option>
												<c:forEach items="${provinceList}" var="cnt">
													<option value="${cnt.PROVINCE_ID }" <c:if test="${cnt.PROVINCE_ID == pd.PROVINCE_ID }">selected</c:if>>${cnt.PROVINCE_CN }</option>
												</c:forEach>
											</select>
										</td>
									</tr>

									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">城市代码<span class="red">*</span></td>
										<td><input type="text" name="CITY_CODE" id="CITY_CODE" value="${pd.CITY_CODE}" maxlength="32" placeholder="" title="城市代码" onblur="hasCode()"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">城市名称<span class="red">*</span></td>
										<td><input type="text" name="CITY_CN" id="CITY_CN" value="${pd.CITY_CN}" maxlength="32" placeholder="" title="城市名称" onblur="hasCn()"/></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">上级城市代码</td>
										<td style="vertical-align:top;">
											<select  class="chzn-select" name="CITY_PARENT_ID" id="CITY_PARENT_ID" data-placeholder="请选择上级城市" style="vertical-align:top;" maxlength="32">
												<option value=""></option>
												<c:forEach items="${cityAllList}" var="cnt">
													<option value="${cnt.CITY_ID }" <c:if test="${cnt.CITY_ID == pd.CITY_PARENT_ID }">selected</c:if>>${cnt.CITY_CN }</option>
												</c:forEach>

											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">是否报价线路</td>
										<td style="vertical-align:top;">
											<select  name="OFFER_FLG" id="OFFER_FLG" style="vertical-align:top;width: 120px;">
												<option value="1">是</option>
												<option value="0">否</option>
											</select>
										</td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">城市英文名称</td>
										<td><input type="text" name="CITY_EN" id="CITY_EN" value="${pd.CITY_EN}" maxlength="32" placeholder="" title="城市英文名称"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">城市区域</td>
										<td><input type="text" name="CITY_AREA" id="CITY_AREA" value="${pd.CITY_AREA}" maxlength="32" placeholder="" title="城市区域"/></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">备注</td>
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
			$(".chzn-select").chosen({search_contains: true});
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker({autoclose: true});
			
		});
		
		</script>
</body>
</html>