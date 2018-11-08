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
			if($("#STOCK_CODE").val()==""){
			$("#STOCK_CODE").tips({
				side:3,
	            msg:'请输入仓库编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STOCK_CODE").focus();
			return false;
		}
		if($("#STOCK_NAME").val()==""){
			$("#STOCK_NAME").tips({
				side:3,
	            msg:'请输入仓库名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STOCK_NAME").focus();
			return false;
		}
		/*if($("#STOCK_DSC").val()==""){
			$("#STOCK_DSC").tips({
				side:3,
	            msg:'请输入仓库介绍',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STOCK_DSC").focus();
			return false;
		}
		if($("#CITY_ID").val()==""){
			$("#CITY_ID").tips({
				side:3,
	            msg:'请输入城市名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CITY_ID").focus();
			return false;
		}
		if($("#STOCK_ADDR").val()==""){
			$("#STOCK_ADDR").tips({
				side:3,
	            msg:'请输入仓库地址',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STOCK_ADDR").focus();
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
		if($("#HEAD_EMP").val()==""){
			$("#HEAD_EMP").tips({
				side:3,
	            msg:'请输入负责人',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#HEAD_EMP").focus();
			return false;
		}
		if($("#HEAD_MOBILE").val()==""){
			$("#HEAD_MOBILE").tips({
				side:3,
	            msg:'请输入负责人电话',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#HEAD_MOBILE").focus();
			return false;
		}
		if($("#HEAD_FAX").val()==""){
			$("#HEAD_FAX").tips({
				side:3,
	            msg:'请输入负责人传真',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#HEAD_FAX").focus();
			return false;
		}
		if($("#HEAD_MAIL").val()==""){
			$("#HEAD_MAIL").tips({
				side:3,
	            msg:'请输入负责人邮箱',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#HEAD_MAIL").focus();
			return false;
		}
		if($("#USE_FLG").val()==""){
			$("#USE_FLG").tips({
				side:3,
	            msg:'请输入启用',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USE_FLG").focus();
			return false;
		}*/
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#HEAD_MOBILE").val()==""){

		}else if($("#HEAD_MOBILE").val().length != 11 && !myreg.test($("#HEAD_MOBILE").val())){
			$("#HEAD_MOBILE").tips({
				side:3,
				msg:'手机号格式不正确',
				bg:'#AE81FF',
				time:3
			});
			$("#HEAD_MOBILE").focus();
			return false;
		}

		if($("#HEAD_MAIL").val()==""){

		}else if(!ismail($("#HEAD_MAIL").val())){
			$("#HEAD_MAIL").tips({
				side:3,
				msg:'邮箱格式不正确',
				bg:'#AE81FF',
				time:3
			});
			$("#HEAD_MAIL").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
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
				$("#PROVINCE_ID").append($("<option value=''></option>"));
				for (var k in data) {
					$("#PROVINCE_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
				//$(".chzn-select").chosen({search_contains: true});
				//$("#PROVINCE_ID").attr(" class", "chzn-select");
			},
			complete: function (XMLHttpRequest, textStatus) {
				//$("#PROVINCE_ID").addClass("chzn-select");function(){$(this).addClass("chzn-select")}
				/*$("#PROVINCE_ID").find("option").each();
				$("#PROVINCE_ID :first").addClass("chzn-select");*/
			}
		});
	}

	function getCity(){
		var PROVINCEID = $.trim($("#PROVINCE_ID").val());
		if (PROVINCEID ==null || PROVINCEID=='') {
			return false;
		}
		//动态删除select中的所有options
		document.getElementById("CITY_ID").options.length=0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>city/getCity.do',
			data: {PROVINCECN:'',PROVINCEID:PROVINCEID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#CITY_ID").append($("<option value=''></option>"));
				for (var k in data) {
					$("#CITY_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
				//$("#CITY_ID").attr(" class", "chzn-select");
			}
		});
	}




	//判断编码是否存在
	function hasCode(){
		var NUMBER = $.trim($("#STOCK_CODE").val());
		if (NUMBER ==null || NUMBER=='') {
			$("#STOCK_CODE").tips({
				side:3,
				msg:'请输入代码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		var STOCKID = $.trim($("#STOCK_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stock/hasStockCode.do',
			data: {STOCKCODE:NUMBER,COUNTRYID:COUNTRYID,STOCKID:STOCKID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#STOCK_CODE").tips({
						side:3,
						msg:'代码已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#STOCK_CODE').val('')",2000);
				}
			}
		});
	}

	//判断名称是否存在
	function hasCn(){
		var NUMBER = $.trim($("#STOCK_NAME").val());
		if (NUMBER ==null || NUMBER=='') {
			$("#STOCK_NAME").tips({
				side:3,
				msg:'请输入名称',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var COUNTRYID = $.trim($("#COUNTRY_ID").val());
		var STOCKID = $.trim($("#STOCK_ID").val());

		$.ajax({
			type: "POST",
			url: '<%=basePath%>stock/hasStockCn.do',
			data: {STOCKCN:NUMBER,COUNTRYID:COUNTRYID,STOCKID:STOCKID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#STOCK_NAME").tips({
						side:3,
						msg:'名称已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#STOCK_NAME').val('')",2000);
				}
			}
		});
	}


</script>
	</head>
<body>
	<form action="stock/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="STOCK_ID" id="STOCK_ID" value="${pd.STOCK_ID}"/>
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>仓库</h6>
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
										<td style="width:100px;text-align: right;padding-top: 13px;">仓库编码<span class="red">*</span></td>
										<td><input type="text" name="STOCK_CODE" id="STOCK_CODE" value="${pd.STOCK_CODE}" maxlength="32" placeholder="" title="仓库编码"  onblur="hasCode()"/></td>

										<td style="width:100px;text-align: right;padding-top: 13px;" >仓库名称<span class="red">*</span></td>
										<td><input type="text" name="STOCK_NAME" id="STOCK_NAME" value="${pd.STOCK_NAME}" maxlength="32" placeholder="" title="仓库名称"  onblur="hasCn()"/></td>
									</tr>
									<tr>
										<td style="width:100px;text-align: right;padding-top: 13px;">仓库介绍</td>
										<td><input type="text" name="STOCK_DSC" id="STOCK_DSC" value="${pd.STOCK_DSC}" maxlength="32" placeholder="" title="仓库介绍"/></td>

										<td style="width:80px;text-align: right;padding-top: 13px;">国家</td>
										<td style="vertical-align:top;">
											<select name="COUNTRY_ID" id="COUNTRY_ID" data-placeholder="请选择国家" style="vertical-align:top;" maxlength="32"
													onChange="getProvince()">
												<option value=""></option>
												<c:forEach items="${countryList}" var="cnt">
													<option value="${cnt.COUNTRY_ID }" <c:if test="${cnt.COUNTRY_ID == pd.COUNTRY_ID }">selected</c:if>>${cnt.COUNTRY_CN }</option>
												</c:forEach>
											</select>
										</td>
									</tr>

									<%--<span class="red">*</span>--%>
									<tr>
										<td style="width:80px;text-align: right;padding-top: 13px;">省份名称</td>
										<td style="vertical-align:top;">
											<select name="PROVINCE_ID" id="PROVINCE_ID" data-placeholder="请选择省份" style="vertical-align:top;" maxlength="32"
													onChange="getCity()">
												<option value=""></option>
												<c:forEach items="${provinceList}" var="cnt">
													<option value="${cnt.PROVINCE_ID }" <c:if test="${cnt.PROVINCE_ID == pd.PROVINCE_ID }">selected</c:if>>${cnt.PROVINCE_CN }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:100px;text-align: right;padding-top: 13px;">城市名称</td>
										<td style="vertical-align:top;">
											<select  name="CITY_ID" id="CITY_ID" style="vertical-align:top;" maxlength="32">
												<c:forEach items="${cityList}" var="cnt">
													<option value="${cnt.CITY_ID }" <c:if test="${cnt.CITY_ID == pd.CITY_ID }">selected</c:if>>${cnt.CITY_CN }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td style="width:100px;text-align: right;padding-top: 13px;">仓库地址</td>
										<td colspan="7"><input class="span11" type="text" name="STOCK_ADDR" id="STOCK_ADDR" value="${pd.STOCK_ADDR}" maxlength="32" placeholder="" title="仓库地址"/></td>
									</tr>
									<tr>
										<td style="width:100px;text-align: right;padding-top: 13px;">备注</td>
										<td colspan="7"><input class="span11"  type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder="" title="备注"/></td>
									</tr>
									<tr>
										<td style="width:100px;text-align: right;padding-top: 13px;">负责人</td>
										<td><input type="text" name="HEAD_EMP" id="HEAD_EMP" value="${pd.HEAD_EMP}" maxlength="32" placeholder="" title="负责人"/></td>

										<td style="width:100px;text-align: right;padding-top: 13px;">负责人电话</td>
										<td><input type="number" name="HEAD_MOBILE" id="HEAD_MOBILE" value="${pd.HEAD_MOBILE}" maxlength="32" placeholder="" title="负责人电话"/></td>
									</tr>
									<tr>
										<td style="width:100px;text-align: right;padding-top: 13px;">负责人传真</td>
										<td><input type="text" name="HEAD_FAX" id="HEAD_FAX" value="${pd.HEAD_FAX}" maxlength="32" placeholder="" title="负责人传真"/></td>

										<td style="width:100px;text-align: right;padding-top: 13px;">负责人邮箱</td>
										<td><input type="email" name="HEAD_MAIL" id="HEAD_MAIL" value="${pd.HEAD_MAIL}" maxlength="32" placeholder="" title="负责人邮箱"/></td>
									</tr>
									<tr>
										<td style="width:100px;text-align: right;padding-top: 13px;">启用</td>
										<c:choose>
											<c:when test="${not empty pd.USE_FLG}">
												<td><input type='checkbox' id="USE_FLG"  name='USE_FLG' value="1"
														   <c:if test="${pd.USE_FLG eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
												</td>
											</c:when>
											<c:otherwise>
												<td><input type='checkbox' id="USE_FLG"  name='USE_FLG' value="1" checked="checked"/><span class="lbl"></span>
												</td>
											</c:otherwise>
										</c:choose>
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