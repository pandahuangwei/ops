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
	<meta name="description" content="overview & stats"/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<link href="static/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet"/>
	<link rel="stylesheet" href="static/css/font-awesome.min.css"/>
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/css/chosen.css"/>

	<link rel="stylesheet" href="static/css/ace.min.css"/>
	<link rel="stylesheet" href="static/css/ace-responsive.min.css"/>
	<link rel="stylesheet" href="static/css/ace-skins.min.css"/>

	<link rel="stylesheet" href="static/css/datepicker.css"/><!-- 日期框 -->
</head>
<body>
<script type="text/javascript">

	//保存
	function transfer(){
			//收货库区 收货库位 LOCATOR_ID STORAGE_ID
			if($("#STORAGE_ID").val()==""){
				$("#STORAGE_ID").tips({
					side:3,
					msg:'请选择库区',
					bg:'#AE81FF',
					time:2
				});
				$("#STORAGE_ID").focus();
				return false;
			}

			if($("#LOCATOR_ID").val()==""){
				$("#LOCATOR_ID").tips({
					side:3,
					msg:'请选择库位',
					bg:'#AE81FF',
					time:2
				});
				$("#LOCATOR_ID").focus();
				return false;
			}
			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();

	}


	function getLocator(){
		var STORAGE_ID = $.trim($("#STORAGE_ID").val());
		if (STORAGE_ID ==null || STORAGE_ID=='') {
			return false;
		}
		//动态删除select中的所有options
		//alert(document.getElementById("PROVINCE_ID").options.length);
		document.getElementById("LOCATOR_ID").options.length=0;
		$.ajax({
			type: "POST",
			url: '<%=basePath%>assignopt/getLocatorList.do',
			data: {STORAGE_ID:STORAGE_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#LOCATOR_ID_chzn").remove();
				$("#LOCATOR_ID").append($("<option value=''></option>"));
				for (var k in data) {
					$("#LOCATOR_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
				}
			},
			complete: function (XMLHttpRequest, textStatus) {
				$("#LOCATOR_ID").attr("class","chzn-select");
				$(".chzn-select").chosen({search_contains: true});
			}
		});
	}

</script>

<form action="locatortf/saveLocatortf.do" name="Form" id="Form" method="post">
	<input type="hidden" name="STOCKDTLMGRIN_IDS" id="STOCKDTLMGRIN_IDS" value="${pd.STOCKDTLMGRIN_IDS}"/>

	<div id="zhongxin">

		<div class="widget-box">
			<div class="widget-header widget-hea1der-small">
				<h6><strong>库位转移</strong>

				</h6>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main padding-3">
					<div class="slim-scroll">
						<div class="content">

									<table id="table_report2" class="table table-striped  table-hover table-condensed">
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">库区<span class="red">*</span></td>
												<%--<td><input type="text" name="STORAGE_ID" id="STORAGE_ID" value="${pd.STORAGE_ID}" maxlength="32" title="收货库区"/></td>--%>
											<td style="vertical-align:top;">
												<select  class="chzn-select" name="STORAGE_ID" id="STORAGE_ID" data-placeholder=" " style="vertical-align:top;" onchange="getLocator()">
													<option value=""></option>
													<c:forEach items="${storageList}" var="cnt">
														<option value="${cnt.id }" <c:if test="${cnt.id == pd.STORAGE_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:70px;text-align: right;padding-top: 13px;">库位<span class="red">*</span></td>
											<td style="vertical-align:top;">
												<select  name="LOCATOR_ID" id="LOCATOR_ID" style="vertical-align:top;" data-placeholder=" ">
													<option value=""></option>
													<c:forEach items="${locatorList}" var="cnt">
														<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>
										</tr>
									</table>
						</div>
					</div>
				</div>
				<div class="widget-toolbox">
					<div class="btn-toolbar">
						<div class="btn-group">
							<a class="btn btn-mini btn-success" onclick="transfer();"><span class="icon-ok"></span>库位转移</a>
						</div>

						<div class="btn-group">
							<button class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</button>
						</div>

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
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->


<script type="text/javascript">
	$(top.hangge());
	$(function() {

		//单选框
		$(".chzn-select").chosen({search_contains: true});
		$(".chzn-select-deselect").chosen({allow_single_deselect:true});

		//日期框
		$('.date-picker').datepicker({autoclose: true});

		//复选框
		$('table th input:checkbox').on('click' , function(){
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});

		});

	});

</script>
</body>
</html>