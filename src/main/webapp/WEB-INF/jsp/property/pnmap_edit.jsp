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
		if($("#CUSTOMER_ID").val()=="") {
			$("#CUSTOMER_ID").tips({
				side: 3,
				msg: '请输入客户',
				bg: '#AE81FF',
				time: 2
			});
			$("#CUSTOMER_ID").focus();
			return false;
		}
		if($("#SCAN_PN").val()==""){
			$("#SCAN_PN").tips({
				side:3,
	            msg:'请输入扫描料号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SCAN_PN").focus();
			return false;
		}
		if($("#ACTUAL_PN").val()==""){
			$("#ACTUAL_PN").tips({
				side:3,
	            msg:'请输入实际料号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ACTUAL_PN").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//判断编码是否存在
	function hasCode(){
		var SCAN_PN = $.trim($("#SCAN_PN").val());
		if (SCAN_PN=="") {
			return false;
		}
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		var PNMAP_ID = $.trim($("#PNMAP_ID").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>pnmap/hasCode.do',
			data: {SCAN_PN:SCAN_PN,PNMAP_ID:PNMAP_ID,CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				if("success" != data.result){
					$("#SCAN_PN").val('');
					$("#SCAN_PN").tips({
						side:3,
						msg:'扫描料号已存在，不能重复添加',
						bg:'#AE81FF',
						time:3
					});
					setTimeout("$('#SCAN_PN').val('')",2000);
				}
			}
		});
	}

	function getProducts(customerId){
		if (customerId ==null || customerId=='') {
			return false;
		}
		//动态删除select中的所有options
		document.getElementById("ACTUAL_PN").options.length=0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>pnmap/getProducts.do',
			data: {CUSTOMER_ID:customerId,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#ACTUAL_PN_chzn").remove();
				$("#ACTUAL_PN").append($("<option value=''></option>"));
				for (var k in data) {
					$("#ACTUAL_PN").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
			},
			complete: function (XMLHttpRequest, textStatus) {
				$("#ACTUAL_PN").attr("class","chzn-select");
				$(".chzn-select").chosen({search_contains: true});

			}
		});
	}
</script>
	</head>
<body>
	<form action="pnmap/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="PNMAP_ID" id="PNMAP_ID" value="${pd.PNMAP_ID}"/>
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>料号对照</h6>
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
											<select  name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" onChange="getProducts(this.value)" >
												<c:forEach items="${customerList}" var="cnt">
													<option value="${cnt.id}" <c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td></td>
										<td></td>
									</tr>
									<tr>
										<td style="width:100px;text-align: right;padding-top: 13px;">扫描料号<span class="red">*</span></td>
										<td><input type="text" name="SCAN_PN" id="SCAN_PN" value="${pd.SCAN_PN}" maxlength="32" placeholder=" " title="扫描料号" onblur="hasCode();"/></td>

										<td style="width:100px;text-align: right;padding-top: 13px;">实际料号<span class="red">*</span></td>
										<%--<td><input type="text" name="ACTUAL_PN" id="ACTUAL_PN" value="${pd.ACTUAL_PN}" maxlength="32" placeholder=" " title="实际料号" onblur="hasCode();"/></td>--%>
										<td style="vertical-align:top;">
											<select  class="chzn-select"  name="ACTUAL_PN" id="ACTUAL_PN" style="vertical-align:top;" data-placeholder=" ">
												<option value=""></option>
												<c:forEach items="${productList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.ACTUAL_PN }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td style="width:100px;text-align: right;padding-top: 13px;">备注</td>
										<td colspan="7"><input class="span11" type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder=" " title="备注"/></td>
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