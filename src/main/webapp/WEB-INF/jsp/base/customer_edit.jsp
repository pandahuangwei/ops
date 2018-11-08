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
			if($("#CUSTOMER_CODE").val()==""){
			$("#CUSTOMER_CODE").tips({
				side:3,
	            msg:'请输入客户编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CUSTOMER_CODE").focus();
			return false;
		}
		if($("#CUSTOMER_CN").val()==""){
			$("#CUSTOMER_CN").tips({
				side:3,
	            msg:'请输入客户名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CUSTOMER_CN").focus();
			return false;
		}
		var array = new Array();
		if(!($("#PN").val()==""||$("#PN").val()==0||$("#PN").val()==null)) {
			//判断array中是否存在
			if (array.indexOf($("#PN").val())!=-1 ) {
				$("#PN").tips({
					side:3,
					msg:'该值已存在',
					bg:'#AE81FF',
					time:2
				});
				$("#PN").focus();
				return false;
			}
			array.push($("#PN").val());
		}
		if(!($("#DATE_CODE").val()==""||$("#DATE_CODE").val()==0||$("#DATE_CODE").val()==null)) {
			if (array.indexOf($("#DATE_CODE").val())!=-1 ) {
				$("#DATE_CODE").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#DATE_CODE").focus();
				return false;
			}
			array.push($("#DATE_CODE").val());
		}
		if($("#LOT_CODE").val()!=""&&$("#LOT_CODE").val()!=0&&$("#LOT_CODE").val()!=null) {
			if (array.indexOf($("#LOT_CODE").val())!=-1 ) {
				$("#LOT_CODE").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#LOT_CODE").focus();
				return false;
			}
			array.push($("#LOT_CODE").val());
		}
		if($("#BIN").val()!=""&&$("#BIN").val()!=0&&$("#BIN").val()!=null) {
			if (array.indexOf($("#BIN").val())!=-1 ) {
				$("#BIN").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#BIN").focus();
				return false;
			}
			array.push($("#BIN").val());
		}
		if($("#COO").val()!=""&&$("#COO").val()!=0&&$("#COO").val()!=null) {
			if (array.indexOf($("#COO").val())!=-1 ) {
				$("#COO").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#COO").focus();
				return false;
			}
			array.push($("#COO").val());
		}
		if($("#Qty").val()!=""&&$("#Qty").val()!=0&&$("#Qty").val()!=null) {
			if (array.indexOf($("#Qty").val())!=-1 ) {
				$("#Qty").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#Qty").focus();
				return false;
			}
			array.push($("#Qty").val());
		}
		if($("#REMARK1").val()!=""&&$("#REMARK1").val()!=0&&$("#REMARK1").val()!=null) {
			if (array.indexOf($("#REMARK1").val())!=-1 ) {
				$("#REMARK1").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#REMARK1").focus();
				return false;
			}
			array.push($("#REMARK1").val());
		}
		if($("#REMARK2").val()!=""&&$("#REMARK2").val()!=0&&$("#REMARK2").val()!=null) {
			if (array.indexOf($("#REMARK2").val())!=-1 ) {
				$("#REMARK2").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#REMARK2").focus();
				return false;
			}
			array.push($("#REMARK2").val());
		}
		if($("#REMARK3").val()!=""&&$("#REMARK3").val()!=0&&$("#REMARK3").val()!=null) {
			if (array.indexOf($("#REMARK3").val())!=-1 ) {
				$("#REMARK3").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#REMARK3").focus();
				return false;
			}
			array.push($("#REMARK3").val());
		}
		if($("#INV_NO").val()!=""&&$("#INV_NO").val()!=0&&$("#INV_NO").val()!=null) {
			if (array.indexOf($("#INV_NO").val())!=-1 ) {
				$("#INV_NO").tips({
					side: 3,
					msg: '该值已存在',
					bg: '#AE81FF',
					time: 2
				});
				$("#INV_NO").focus();
				return false;
			}
			array.push($("#INV_NO").val());
		}
		/*for ( var i = 0; i < array.length; i++) {

			var index = array[i];
			if(index>array.length){
			    alert("数字有重复");
				return false;
			}

		}*/

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
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
			data: {ID:COUNTRYID,COUNTRYID:COUNTRYID,tm:new Date().getTime()},
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
		document.getElementById("CITY_ID").options.length=0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>city/getCity.do',
			data: {ID:PROVINCEID,PROVINCEID:PROVINCEID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#CITY_ID_chzn").remove();
				$("#CITY_ID").append($("<option value=''></option>"));
				for (var k in data) {
					$("#CITY_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
			},
			complete: function (XMLHttpRequest, textStatus) {
				$("#CITY_ID").attr("class","chzn-select");
				$(".chzn-select").chosen({search_contains: true});

			}
		});
	}

	function setAddr(){
		var country = $("#COUNTRY_ID").find("option:selected").text();
		var province = $("#PROVINCE_ID").find("option:selected").text();
		var city = $("#CITY_ID").find("option:selected").text();
		var newvalue = (country+province+city).replace(/\([^\)]*\)/g,"");
		$("#CUSTOMER_ADDR").val(newvalue);
		$("#CUSTOMER_ADDR").focus();
		return false;
	}

	//判断编码是否存在
	function hasCode(){
		var CUSTOMERCODE = $.trim($("#CUSTOMER_CODE").val());
		if (CUSTOMERCODE ==null || CUSTOMERCODE=='') {
			$("#CUSTOMER_CODE").tips({
				side:3,
				msg:'请输入代码',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var CUSTOMERID = $.trim($("#CUSTOMER_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>customer/hasCode.do',
			data: {CUSTOMERCODE:CUSTOMERCODE,CUSTOMERID:CUSTOMERID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#CUSTOMER_CODE").tips({
						side:3,
						msg:'编号已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#CUSTOMER_CODE').val('')",2000);
				}
			}
		});
	}

	//判断名称是否存在
	function hasCn(){
		var CUSTOMERCN = $.trim($("#CUSTOMER_CN").val());
		if (CUSTOMERCN ==null || CUSTOMERCN=='') {
			$("#CUSTOMER_CN").tips({
				side:3,
				msg:'请输入名称',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		var CUSTOMERID = $.trim($("#CUSTOMER_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>customer/hasCn.do',
			data: {CUSTOMERCN:CUSTOMERCN,CUSTOMERID:CUSTOMERID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#CUSTOMER_CN").tips({
						side:3,
						msg:'名称已存在',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#CUSTOMER_CN').val('')",2000);
				}
			}
		});
	}


	//打开上传excel页面
	function upload(){
		top.jzts();

		var CUSTOMER_ID = $("#CUSTOMER_ID").val();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="上传合约";
		diag.URL = '<%=basePath%>customer/goUpload.do?CUSTOMER_ID='+CUSTOMER_ID;
		diag.Width = 700;
		diag.Height = 450;
		diag.CancelEvent = function(){ //关闭事件
			refreshDeal();
			diag.close();
		};
		diag.show();
	}

	//刷新合约
	function refreshDeal() {
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		if (CUSTOMER_ID == null || CUSTOMER_ID == '') {
			return false;
		}
		//清空table 除表头外的所有纪录
		$("#table_deal_dtl tr:not(:first)").empty();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>customer/getDeal.do',
			data: {CUSTOMER_ID: CUSTOMER_ID, tm: new Date().getTime()},
			dataType: 'json',
			cache: false,
			success: function (data) {
				var trs = "";
				for (var k in data) {
					//console.log(data[k]);
					var json = data[k];
					var lenth = json.length;
					for (var i = 0; i < lenth; i++) {
						var DEAL_ID = json[i]['DEAL_ID'];
						var DEAL_NAME = json[i]['DEAL_NAME'];
						var ACTIVE_DT = json[i]['ACTIVE_DT'];
						var DISABLE_DT = json[i]['DISABLE_DT'];
						var CREATE_EMP = json[i]['CREATE_EMP'];


						trs +="<tr><td class='center' style='width: 30px;'>"+(i+1)+"</td>";
						trs +="<td >"+DEAL_NAME+"</td>";
						trs +="<td style='width: 70px;'>"+ACTIVE_DT+"</td>";
						trs +="<td style='width: 70px;'>"+DISABLE_DT+"</td>";
						trs +="<td style='width: 70px;'>"+CREATE_EMP+"</td>";
						trs +="<td style='width: 100px;' class='center'><div class='btn-group'>";
						trs +="<a title='下载' onclick=\"downDeal('"+DEAL_ID+"');\" class='btn btn-mini btn-info' data-rel='tooltip' data-placement='left'><i class='icon-cloud-download'></i></a>";
						trs +="<a title='删除' onclick=\"delDeal('"+DEAL_ID+"');\" class='btn btn-mini btn-danger'  data-rel='tooltip' data-placement='left'><i class='icon-trash'></i></a>";
						trs +="</div></td></tr>";
					}
				}

				$("#table_deal_dtl").append(trs);
			},
			complete: function (XMLHttpRequest, textStatus) {
				var trLenth = $("#table_deal_dtl tr").length;
				if (trLenth == 0) {
					$("#table_deal_dtl").append("<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>");
				}
			}
		});
	}

	//删除合约
	function delDeal(Id){
		bootbox.confirm("确定要删除该合约吗?", function(result) {
			if(result) {
				//top.jzts();
				var url = "<%=basePath%>customer/delDeal.do?DEAL_ID="+Id+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//nextPage(${page.currentPage});
					refreshDeal();
				});
			}
		});
	}

	//下载合约
	function downDeal(Id) {
		window.location.href='<%=basePath%>/customer/downDeal.do?DEAL_ID='+Id+"&tm="+new Date().getTime();
	}


</script>
	</head>
<body>
	<form action="customer/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/>
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>客户</h6>
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
								<div> <%-- class="tab-content"--%>
									<div id="home1" class="tab-pane in active">

										<table id="table_report0" class="table table-striped  table-hover table-condensed">
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">客户编码<span class="red">*</span></td>
												<td><input type="text" name="CUSTOMER_CODE" id="CUSTOMER_CODE" value="${pd.CUSTOMER_CODE}" maxlength="32" placeholder="" title="客户编码" onblur="hasCode()"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">客户名称<span class="red">*</span></td>
												<td><input type="text" name="CUSTOMER_CN" id="CUSTOMER_CN" value="${pd.CUSTOMER_CN}" maxlength="32" placeholder="" title="客户名称" onblur="hasCn()"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">客户简称</td>
												<td><input type="text" name="CUSTOMER_SHORT" id="CUSTOMER_SHORT" value="${pd.CUSTOMER_SHORT}" maxlength="32"  title="客户简称"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">英文名称</td>
												<td><input type="text" name="CUSTOMER_EN" id="CUSTOMER_EN" value="${pd.CUSTOMER_EN}" maxlength="32"  title="英文名称"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">检索码</td>
												<td><input type="text" name="SEACH_CODE" id="SEACH_CODE" value="${pd.SEACH_CODE}" maxlength="32"  title="检索码"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">上级企业</td>
												<td><input type="text" name="UP_COMPANY" id="UP_COMPANY" value="${pd.UP_COMPANY}" maxlength="32"  title="上级企业"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">国家</td>
												<td style="vertical-align:top;">
													<select  class="chzn-select"  name="COUNTRY_ID" id="COUNTRY_ID" style="vertical-align:top;" maxlength="32" data-placeholder=" " onChange="getProvince()">
														<option value=""></option>
														<c:forEach items="${countryList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.COUNTRY_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">省/州</td>
												<td style="vertical-align:top;">
													<select  name="PROVINCE_ID" id="PROVINCE_ID" style="vertical-align:top;" maxlength="32" data-placeholder=" " onChange="getCity()">
														<option value=""></option>
														<c:forEach items="${provinceList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.PROVINCE_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">城市</td>
												<td style="vertical-align:top;">
													<select  name="CITY_ID" id="CITY_ID" style="vertical-align:top;" maxlength="32" data-placeholder=" " onchange="setAddr()">
														<option value=""></option>
														<c:forEach items="${cityList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.CITY_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">地址</td>
												<td><input type="text" name="CUSTOMER_ADDR" id="CUSTOMER_ADDR" value="${pd.CUSTOMER_ADDR}" maxlength="32"  title="地址"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">网站地址</td>
												<td><input type="text" name="INTEL_ADDR" id="INTEL_ADDR" value="${pd.INTEL_ADDR}" maxlength="32"  title="网站地址"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">海关编号</td>
												<td><input type="text" name="CUSTOMS_CODE" id="CUSTOMS_CODE" value="${pd.CUSTOMS_CODE}" maxlength="32"  title="海关编号"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">经营单位</td>
												<td><input type="text" name="OPERATING_UNIT" id="OPERATING_UNIT" value="${pd.OPERATING_UNIT}" maxlength="32"  title="经营单位"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">付款周期</td>
												<td><input type="number" name="PAY_PERIOD" id="PAY_PERIOD" value="${pd.PAY_PERIOD}" maxlength="32" placeholder="0" title="付款周期"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">电话</td>
												<td><input type="text" name="TEL_PHONE" id="TEL_PHONE" value="${pd.TEL_PHONE}" maxlength="32"  title="电话"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">客服人员名称</td>
												<td><input type="text" name="SERVIC_ER" id="SERVIC_ER" value="${pd.SERVIC_ER}" maxlength="32"  title="客服人员名称"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">业务员</td>
												<td><input type="text" name="SALE_MAN" id="SALE_MAN" value="${pd.SALE_MAN}" maxlength="32"  title="业务员"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">客户财务编号</td>
												<td><input type="text" name="CUSTOMER_FINANCE" id="CUSTOMER_FINANCE" value="${pd.CUSTOMER_FINANCE}" maxlength="32" title="客户财务编号"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">供应商财务编码</td>
												<td><input type="text" name="SUPPLIER_FINANCE" id="SUPPLIER_FINANCE" value="${pd.SUPPLIER_FINANCE}" maxlength="32"  title="供应商财务编码"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">付款客户</td>
												<td><input type="text" name="PAY_MERCHANT" id="PAY_MERCHANT" value="${pd.PAY_MERCHANT}" maxlength="32"  title="付款客户"/></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">所属公司</td>
												<td><input type="text" name="BELONE_COMPANY" id="BELONE_COMPANY" value="${pd.BELONE_COMPANY}" maxlength="32"  title="所属公司"/></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">启用</td>
												<td><input type='checkbox' id="USE_FLG" name='USE_FLG' value="1"
														   <c:if test="${pd.USE_FLG eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
												</td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">企业类型</td>
												<%--<td><input type="text" name="CUSTOMER_TYPE" id="CUSTOMER_TYPE" value="${pd.CUSTOMER_TYPE}" maxlength="32" placeholder="这里输入企业类型" title="企业类型"/></td>--%>
												<td colspan="7">供应商
													<input type='checkbox' id="TYPE_SUPPLIER" name='TYPE_SUPPLIER' value="1"
														   <c:if test="${pd.TYPE_SUPPLIER eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													厂商
													<input type='checkbox' id="TYPE_FACTOR" name='TYPE_FACTOR' value="1"
														   <c:if test="${pd.TYPE_FACTOR eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													货代
													<input type='checkbox' id="TYPE_FOB" name='TYPE_FOB' value="1"
														   <c:if test="${pd.TYPE_FOB eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													发货人
													<input type='checkbox' id="TYPE_SEND" name='TYPE_SEND' value="1"
														   <c:if test="${pd.TYPE_SEND eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													收货人
													<input type='checkbox' id="TYPE_TAKE" name='TYPE_TAKE' value="1"
														   <c:if test="${pd.TYPE_TAKE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													车队
													<input type='checkbox' id="TYPE_CAR" name='TYPE_CAR' value="1"
														   <c:if test="${pd.TYPE_CAR eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
													报关行
													<input type='checkbox' id="TYPE_CUSTOM" name='TYPE_CUSTOM' value="1"
														   <c:if test="${pd.TYPE_CUSTOM eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
												</td>
											</tr>


										</table>
									</div>
								</div>

								<div class="tabbable">
									<ul class="nav nav-tabs" id="myTab">
										<li class="active"><a data-toggle="tab" href="#home">地址/联系人</a></li>
										<li><a data-toggle="tab" href="#profile">规则信息 </a></li>
										<li><a data-toggle="tab" href="#profile2">合约</a></li>
										<li><a data-toggle="tab" href="#profile3">通用收货二维码</a></li>
									</ul>
									<div class="tab-content">
										<div id="home" class="tab-pane in active">
											<table id="table_report1" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">名称</td>
													<td><input type="text" name="CONTACTS_CN" id="CONTACTS_CN" value="${pd.CONTACTS_CN}" maxlength="32"  title="名称"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">英文名</td>
													<td><input type="text" name="CONTACTS_EN" id="CONTACTS_EN" value="${pd.CONTACTS_EN}" maxlength="32"  title="英文名"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">城市</td>
													<td style="vertical-align:top;">
														<select class="chzn-select" name="CONTACTS_CITY_ID" id="CONTACTS_CITY_ID" data-placeholder=" "  style="vertical-align:top;" maxlength="32" >
															<option value=""></option>
															<c:forEach items="${cityAllList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.CONTACTS_CITY_ID }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">地址</td>
													<td><input type="text" name="CONTACTS_ADDR" id="CONTACTS_ADDR" value="${pd.CONTACTS_ADDR}" maxlength="32"  title="地址"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">部门</td>
													<td><input type="text" name="CONTACTS_DEPT" id="CONTACTS_DEPT" value="${pd.CONTACTS_DEPT}" maxlength="32" title="部门"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">职务</td>
													<td><input type="text" name="CONTACTS_POST" id="CONTACTS_POST" value="${pd.CONTACTS_POST}" maxlength="32"  title="职务"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">电话</td>
													<td><input type="text" name="CONTACTS_PHONE" id="CONTACTS_PHONE" value="${pd.CONTACTS_PHONE}" maxlength="32"  title="电话"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">传真</td>
													<td><input type="text" name="CONTACTS_FAX" id="CONTACTS_FAX" value="${pd.CONTACTS_FAX}" maxlength="32" title="传真"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">邮箱</td>
													<td><input type="text" name="CONTACTS_EMAIL" id="CONTACTS_EMAIL" value="${pd.CONTACTS_EMAIL}" maxlength="32"  title="邮箱"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">生日</td>
													<td><input class="date-picker" name="CONTACTS_BIRTH" id="CONTACTS_BIRTH" value="${pd.CONTACTS_BIRTH}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="生日" title="生日"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">纪念日</td>
													<td><input class="date-picker" name="CONTACTS_MEMORI" id="CONTACTS_MEMORI" value="${pd.CONTACTS_MEMORI}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="纪念日" title="纪念日"/></td>
												</tr>
											</table>
										</div>
										<div id="profile" class="tab-pane">
											<table id="table_report2" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">包装规则</td>
													<%--<td><input type="text" name="RULE_PACK" id="RULE_PACK" value="${pd.RULE_PACK}" maxlength="32" placeholder="这里输入包装规则" title="包装规则"/></td>--%>
													<td style="vertical-align:top;">
														<select class="chzn-select" name="RULE_PACK" id="RULE_PACK" data-placeholder=" " style="vertical-align:top;">
															<option value=""></option>
															<c:forEach items="${packRuleList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_PACK }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<%--<td style="width:70px;text-align: right;padding-top: 13px;">码放规则</td>
													<td><input type="text" name="RULE_PUT" id="RULE_PUT" value="${pd.RULE_PUT}" maxlength="32" placeholder="这里输入码放规则" title="码放规则"/></td>--%>
													<td style="width:70px;text-align: right;padding-top: 13px;">库位分配规则</td>
													<%--<td><input type="text" name="RULE_STORAGEASIGNR" id="RULE_STORAGEASIGNR" value="${pd.RULE_STORAGEASIGNR}" maxlength="32" placeholder="" title="库位分配规则"/></td>--%>
													<td style="vertical-align:top;">
														<select class="chzn-select" name="RULE_STORAGEASIGNR" id="RULE_STORAGEASIGNR" data-placeholder=" " style="vertical-align:top;">
															<option value=""></option>
															<c:forEach items="${storageAsgRuleList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_STORAGEASIGNR }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">拣货规则</td>
													<%--<td><input type="text" name="RULE_PICK" id="RULE_PICK" value="${pd.RULE_PICK}" maxlength="32" placeholder="这里输入拣货规则" title="拣货规则"/></td>--%>
													<td style="vertical-align:top;">
														<select class="chzn-select" name="RULE_PICK" id="RULE_PICK" data-placeholder=" " style="vertical-align:top;">
															<option value=""></option>
															<c:forEach items="${pickRuleList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_PICK }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<%--<td style="width:70px;text-align: right;padding-top: 13px;">拣货分配规则</td>
													<td><input type="text" name="RULE_PICK_ASIGN" id="RULE_PICK_ASIGN" value="${pd.RULE_PICK_ASIGN}" maxlength="32" placeholder="这里输入拣货分配规则" title="拣货分配规则"/></td>--%>
													<td style="width:70px;text-align: right;padding-top: 13px;">库存分配规则</td>
													<%--<td><input type="text" name="RULE_STOCKASIGN" id="RULE_STOCKASIGN" value="${pd.RULE_STOCKASIGN}" maxlength="32" placeholder="这里输入拣货分配规则" title="拣货分配规则"/></td>--%>
													<td style="vertical-align:top;">
														<select class="chzn-select" name="RULE_STOCKASIGN" id="RULE_STOCKASIGN" data-placeholder=" " style="vertical-align:top;">
															<option value=""></option>
															<c:forEach items="${stockAsgRuleList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_STOCKASIGN }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

												</tr>
												<%--<tr>
                                                    <td style="width:70px;text-align: right;padding-top: 13px;">预分配规则</td>
                                                    <td><input type="text" name="RULE_DEFALUT_ASIGN" id="RULE_DEFALUT_ASIGN" value="${pd.RULE_DEFALUT_ASIGN}" maxlength="32" placeholder="这里输入预分配规则" title="预分配规则"/></td>

                                                    <td style="width:70px;text-align: right;padding-top: 13px;">批次属性规则</td>
                                                    <td><input type="text" name="RULE_BATCH" id="RULE_BATCH" value="${pd.RULE_BATCH}" maxlength="32" placeholder="这里输入批次属性规则" title="批次属性规则"/></td>
                                                </tr>--%>
												<tr>
													<%--<td style="width:70px;text-align: right;padding-top: 13px;">库存周转规则</td>
                                                    <td><input type="text" name="RULE_STOCK" id="RULE_STOCK" value="${pd.RULE_STOCK}" maxlength="32" placeholder="这里输入库存周转规则" title="库存周转规则"/></td>
                    --%>
													<td style="width:70px;text-align: right;padding-top: 13px;">批次属性规则</td>
													<%--<td><input type="text" name="RULE_BATCH" id="RULE_BATCH" value="${pd.RULE_BATCH}" maxlength="32" placeholder="" title="批次属性规则"/></td>--%>
													<td style="vertical-align:top;">
															<select class="chzn-select" name="RULE_BATCH" id="RULE_BATCH" data-placeholder=" " style="vertical-align:top;">
																<option value=""></option>
																<c:forEach items="${customerList}" var="cnt">
																	<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_BATCH }">selected</c:if>>${cnt.value }</option>
																</c:forEach>
															</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">库位指定规则</td>
													<td>
														<%--class="chzn-select" <input type="text" name="RULE_STORAGE" id="RULE_STORAGE" value="${pd.RULE_STORAGE}" maxlength="32" placeholder="" title="库位指定规则"/>--%>
														<select class="chzn-select" name="RULE_STORAGE" id="RULE_STORAGE" data-placeholder=" "  style="vertical-align:top;" maxlength="32" >
															<option value=""></option>
															<c:forEach items="${storageRuleList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_STORAGE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">体积重量计算方式</td>
													<td>
														<%--class="chzn-select" <input type="text" name="RULE_BULK" id="RULE_BULK" value="${pd.RULE_BULK}" maxlength="32" placeholder="" title="体积重量计算方式"/>--%>
														<select  class="chzn-select" name="RULE_BULK" id="RULE_BULK" data-placeholder=" "  style="vertical-align:top;" maxlength="32" >
															<option value=""></option>
															<c:forEach items="${bulkRuleList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.RULE_BULK }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">超量收货</td>
													<td style="width:70px;text-align: left;padding-top: 13px;">
														<%--<input type="number" name="SUPR_TAKE" id="SUPR_TAKE" value="${pd.SUPR_TAKE}" maxlength="32" placeholder="" title="超量收货"/>--%>
														<input type='checkbox' id="SUPR_TAKE"  name='SUPR_TAKE' value="1"
															   <c:if test="${pd.SUPR_TAKE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
														&nbsp; &nbsp;&nbsp; 超量发货
														<input type='checkbox' id="SUPR_SEND"  name='SUPR_SEND' value="1"
															   <c:if test="${pd.SUPR_SEND eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>

													</td>
												</tr>
												<%--<tr>
                                                    <td style="width:70px;text-align: right;padding-top: 13px;">超量发货</td>
                                                    <td>
                                                        &lt;%&ndash;<input type="number" name="SUPR_SEND" id="SUPR_SEND" value="${pd.SUPR_SEND}" maxlength="32" placeholder="" title="超量发货"/>&ndash;%&gt;
                                                        <input type='checkbox' id="SUPR_SEND"  name='SUPR_SEND' value="1"
                                                               <c:if test="${pd.SUPR_SEND eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
                                                    </td>
                                                </tr>--%>
											</table>
										</div>
										<div id="profile2" class="tab-pane">
											<div class="widget-box">
												<div class="widget-body">

													<div class="widget-toolbox">
														<div class="btn-toolbar">
															<div class="btn-group">
																<a class="btn btn-mini btn-warning" onclick="upload();"><span class="icon-cloud-upload"></span>上传</a>
															</div>
														</div>
													</div>

													<div class="widget-main padding-3">
														<div class="slim-scroll" data-height="125">
															<div class="content">
																<table id="table_deal_dtl" class="table table-striped  table-condensed table-hover">
																	<thead>
																	<tr>
																		<th style="width: 30px;" class="center">序号</th>
																		<th class="center">文件名</th>
																		<th style="width: 70px;" class="center">生效日期</th>
																		<th style="width: 70px;" class="center">失效日期</th>
																		<th style="width: 70px;" class="center">创建人</th>
																		<th style="width: 100px;" class="center">操作</th>
																	</tr>
																	</thead>
																	<tbody>

																	<!-- 开始循环 -->
																	<c:choose>
																		<c:when test="${not empty varList}">
																			<c:if test="${QX.cha == 1 }">
																				<c:forEach items="${varList}" var="var" varStatus="vs">
																					<tr id="${var.DEAL_ID}">
																						<td class='center' style="width: 30px;">${vs.index+1}</td>
																						<td >${var.DEAL_NAME}</td>
																						<td style="width: 70px;">${var.ACTIVE_DT}</td>
																						<td style="width: 70px;">${var.DISABLE_DT}</td>
																						<td style="width: 70px;">${var.CREATE_EMP}</td>

																						<td style="width: 100px;" class="center">
																							<div class='btn-group'>
																								<c:if test="${QX.edit == 1 }">
																									<a title="下载" onclick="downDeal('${var.DEAL_ID}');" class='btn btn-mini btn-info' data-rel="tooltip" title="" data-placement="left"><i class="icon-cloud-download"></i></a>
																								</c:if>
																								<c:if test="${QX.del == 1 }">
																									<a title="删除" onclick="delDeal('${var.DEAL_ID}');" class='btn btn-mini btn-danger'  data-rel="tooltip" title="" data-placement="left"><i class="icon-trash"></i></a>
																								</c:if>
																							</div>
																						</td>

																					</tr>

																				</c:forEach>
																			</c:if>
																			<c:if test="${QX.cha == 0 }">
																				<tr>
																					<td colspan="100" class="center">您无权查看</td>
																				</tr>
																			</c:if>
																		</c:when>
																		<c:otherwise>
																			<tr class="main_info">
																				<td colspan="100" class="center" >没有相关数据</td>
																			</tr>
																		</c:otherwise>
																	</c:choose>


																	</tbody>

																</table>
															</div>
														</div>
													</div>
												</div>
											</div>

											<%--<p>
											<table id="table_report3" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">文件名</td>
													<td><input type="text" name="CONTRACTUAL_FILE" id="CONTRACTUAL_FILE" value="${pd.CONTRACTUAL_FILE}" maxlength="32" title="文件名"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">生效日期</td>
													<td><input class="date-picker" name="CONTRACTUAL_ACTIVE" id="CONTRACTUAL_ACTIVE" value="${pd.CONTRACTUAL_ACTIVE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  title="生效日期"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">失效日期</td>
													<td><input class="date-picker" name="CONTRACTUAL_DISABLE" id="CONTRACTUAL_DISABLE" value="${pd.CONTRACTUAL_DISABLE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  title="失效日期"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">创建人</td>
													<td><input readonly="readonly" type="text" name="CREATE_EMP" id="CREATE_EMP" value="${pd.CREATE_EMP}" maxlength="32"  title="创建人"/></td>
												</tr>
											</table>
											</p>--%>
										</div>
										<div id="profile3" class="tab-pane">
											<table id="table_2d" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">分隔符</td>
													<td colspan="5" >
														<input class="span10" type="hidden" readonly="true" name="RECEIVOPT_2D" id="RECEIVOPT_2D" value="${pd.RECEIVOPT_2D}" maxlength="32"  title=""/>
														<input class ="span1" type="text" style="background-color: #d2f2e7" name="SEPERATOR" id="SEPERATOR" value="${pd.SEPERATOR}" maxlength="32"  title="分隔符(, ; + | :)"/>

													</td>

												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">PN</td>
													<td><input min="0" max="20" type="number" name="PN" id="PN" value="${pd.PN}" maxlength="2"  title="序号"  placeholder="0"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">DateCode</td>
													<td><input min="0" max="20" type="number" name="DATE_CODE" id="DATE_CODE" value="${pd.DATE_CODE}" maxlength="2"  title="序号"  placeholder="0"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">LotCode</td>
													<td><input min="0" max="20" type="number" name="LOT_CODE" id="LOT_CODE" value="${pd.LOT_CODE}" maxlength="2"  title="序号"  placeholder="0"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">BIN</td>
													<td><input min="0" max="20" type="number" name="BIN" id="BIN" value="${pd.BIN}" maxlength="2"  title="序号"  placeholder="0"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">COO</td>
													<td><input min="0" max="20" type="number" name="COO" id="COO" value="${pd.COO}" maxlength="2"  title="序号"  placeholder="0"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">Qty</td>
													<td><input min="0" max="20" type="number" name="QTY" id="QTY" value="${pd.QTY}" maxlength="2"  title="序号"  placeholder="0"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">Remark1</td>
													<td><input min="0" max="20" type="number" name="REMARK1" id="REMARK1" value="${pd.REMARK1}" maxlength="2"  title="序号"  placeholder="0"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">Remark2</td>
													<td><input min="0" max="20" type="number" name="REMARK2" id="REMARK2" value="${pd.REMARK2}" maxlength="2"  title="序号"  placeholder="0"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">Remark3</td>
													<td><input tmin="0" max="20" type="number" name="REMARK3" id="REMARK3" value="${pd.REMARK3}" maxlength="2"  title="序号"  placeholder="0"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">InvNo</td>
													<td><input min="0" max="20" type="number" name="INV_NO" id="INV_NO" value="${pd.INV_NO}" maxlength="2"  title="序号"  placeholder="0"/></td>
												</tr>
											</table>
										</div>
									</div>
								</div>
								<%--<table id="table_report4" class="table table-striped  table-hover table-condensed">
									<tr>
										<td style="text-align: center;" colspan="10">
											<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
											<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
										</td>
									</tr>
								</table>--%>



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

	   <script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->

		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen({search_contains: true});
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker({autoclose: true});

             //#table_2d :input[type="number"]
			$('#table_2d :input').bind('input propertychange', function()
			{
				//获取.input-form下的所有 <input> 元素,并实时监听用户输入
				//逻辑
				//alert($(this).attr('id'));
				if ($(this).attr('id')=='RECEIVOPT_2D') {
					return;
				}

				if ($(this).attr('id')=='SEPERATOR') {
                    return;
				} else {
					var curInputId = $(this).attr('id');
					var curInputValue = $(this).val();
					if(isNaN(curInputValue)) {
						$("#"+curInputId).tips({
							side:3,
							msg:'不是数字',
							bg:'#AE81FF',
							time:2
						});
						//$(this).focus()
						setTimeout("$('#'+curInputId).val('0')",1000);
						return false;
					}
					var number = Number(curInputValue);
					if (!(1<=number && number<=20)) {
						$("#"+curInputId).tips({
							side:3,
							msg:'请输入0-20之间的整数',
							bg:'#AE81FF',
							time:2
						});
						//$(this).focus()
						$(this).val('');
						setTimeout("$(this).val('')",500);
						return false;
					}
				}

			})

		});
		//找出重复元素
		Array.prototype.duplicate=function() {
			var tmp = [];
			this.concat().sort().sort(function(a,b){
				if(a==b && tmp.indexOf(a) === -1) tmp.push(a);
			});
			return tmp;
		}

		var IdNameMap = {};
		IdNameMap['PN'] = 'PN';
		IdNameMap['DATE_CODE'] = 'DateCode';
		IdNameMap['LOT_CODE'] = 'LotCode';
		IdNameMap['BIN'] = 'BIN';
		IdNameMap['COO'] = 'COO';
		IdNameMap['QTY'] = 'Qty';
		IdNameMap['REMARK1'] = 'Remark1';
		IdNameMap['REMARK2'] = 'Remark2';
		IdNameMap['REMARK3'] = 'Remark3';
		IdNameMap['INV_NO'] = 'InvNo';

		function set2D() {
			var valueArr = genValueArr();
			var duplicate = valueArr.duplicate();
			if (duplicate.length >=1 ) {
				for(var i=0;i<duplicate.length;i++){
					//console.log(duplicate[i]);
					showTips(duplicate[i]);
				}
				return;
			}

			var splitCh = $("#SEPERATOR").val();
			valueArr.sort();
			var str2D='';
			for(var i=0,size=valueArr.length;i<size;i++){
				//console.log(valueArr[i]);
				str2D = str2D+getColumName(valueArr[i]);
				if (i < size-1) {
					str2D = str2D +splitCh;
				}
			}
			//console.log(str2D);
			$("#RECEIVOPT_2D").val(str2D);
		}

		//组装数组
		function genValueArr() {
			var arr = new Array();
			addValue($("#PN").val(),arr);
			addValue($("#DATE_CODE").val(),arr);
			addValue($("#LOT_CODE").val(),arr);
			addValue($("#BIN").val(),arr);
			addValue($("#COO").val(),arr);
			addValue($("#QTY").val(),arr);
			addValue($("#REMARK1").val(),arr);
			addValue($("#REMARK2").val(),arr);
			addValue($("#REMARK3").val(),arr);
			addValue($("#INV_NO").val(),arr);
			return arr;
		}

		function showTips(src) {
			if($("#PN").val()==src){
				$("#PN").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#PN").val('');
			}
			if($("#DATE_CODE").val()==src){
				$("#DATE_CODE").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#DATE_CODE").val('');
			}
			if($("#LOT_CODE").val()==src){
				$("#LOT_CODE").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#LOT_CODE").val('');
			}
			if($("#BIN").val()==src){
				$("#BIN").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#BIN").val('');
			}
			if($("#COO").val()==src){
				$("#COO").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#COO").val('');
			}
			if($("#QTY").val()==src){
				$("#QTY").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#QTY").val('');
			}
			if($("#REMARK1").val()==src){
				$("#REMARK1").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#REMARK1").val('');
			}
			if($("#REMARK2").val()==src){
				$("#REMARK2").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#REMARK2").val('');
			}
			if($("#REMARK3").val()==src){
				$("#REMARK3").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#REMARK3").val('');
			}
			if($("#INV_NO").val()==src){
				$("#INV_NO").tips({
					side:3,
					msg:'顺序重复,请修改',
					bg:'#AE81FF',
					time:2
				});
				$("#INV_NO").val('');
			}

		}


		function getColumName(src) {
			if($("#PN").val()==src){
				return IdNameMap["PN"];
			}
			if($("#DATE_CODE").val()==src){
				return IdNameMap["DATE_CODE"];
			}
			if($("#LOT_CODE").val()==src){
				return IdNameMap["LOT_CODE"];
			}
			if($("#BIN").val()==src){
				return IdNameMap["BIN"];
			}
			if($("#COO").val()==src){
				return IdNameMap["COO"];
			}
			if($("#QTY").val()==src){
				return IdNameMap["QTY"];
			}
			if($("#REMARK1").val()==src){
				return IdNameMap["REMARK1"];
			}
			if($("#REMARK2").val()==src){
				return IdNameMap["REMARK2"];
			}
			if($("#REMARK3").val()==src){
				return IdNameMap["REMARK3"];
			}
			if($("#INV_NO").val()==src){
				return IdNameMap["INV_NO"];
			}

		}

		function genColumArr() {
			var arr = new Array();
			addColum($("#PN").val(),"PN",arr);
			addColum($("#DATE_CODE").val(),"DATE_CODE",arr);
			addColum($("#LOT_CODE").val(),"LOT_CODE",arr);
			addColum($("#BIN").val(),"BIN",arr);
			addColum($("#COO").val(),"COO",arr);
			addColum($("#QTY").val(),"QTY",arr);
			addColum($("#REMARK1").val(),"REMARK1",arr);
			addColum($("#REMARK2").val(),"REMARK2",arr);
			addColum($("#REMARK3").val(),"REMARK3",arr);
			addColum($("#INV_NO").val(),"INV_NO",arr);
			return arr;
		}
		function addValue(src,arr) {
			if (src =="" || src==null) {
				src = 0;
			}
			if (1<=src && src<=20) {
				arr.push(src);
			}
		}

		function addColum(src,cloum,arr) {
			if (src =="" || src==null) {
				src = 0;
			}
			if (1<=src && src<=20) {
				arr.push(cloum);
			}
		}

		</script>
</body>
</html>