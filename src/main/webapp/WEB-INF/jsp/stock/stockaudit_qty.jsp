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

			if($("#EA_NUM_AF").val()==""){
				$("#EA_NUM_AF").tips({
					side:3,
					msg:'请输入EA数量',
					bg:'#AE81FF',
					time:2
				});
				$("#EA_NUM_AF").focus();
				return false;
			}

			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

	</script>
</head>
<body>
<form action="stockaudit/saveAuditQty.do" name="Form" id="Form" method="post">
	<input type="hidden" name="BOX_ID" id="BOX_ID" value="${pd.BOX_ID}">
	<div id="zhongxin">


		<div class="widget-box">
			<div class="widget-header widget-hea1der-small">
				<h6><strong>调整数量</strong>

				</h6>
				<div class="widget-toolbar">
					<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
				</div>
			</div>
			<div class="widget-body">
				<div class="widget-main padding-3">
					<div class="slim-scroll">
						<div class="content">

						</div>
					</div>
				</div>
				<div class="widget-toolbox">
					<div class="btn-toolbar">
						<div class="btn-group">
							<div class="btn-group">
								<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
								<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
							</div>
						</div>

					</div>
				</div>
				<div class="widget-main padding-3">
					<div>
						<div class="content">
							<table id="table_report" class="table table-striped  table-hover table-condensed">

								<tr>
									<td style="width:70px;text-align: right;padding-top: 13px;">调整前EA</td>
									<td><input type="number" min="1" readOnly="readOnly" name="EA_NUM_BF" id="EA_NUM_BF" value="${pd.EA_NUM_BF}" maxlength="32" placeholder="" title="调整前EA"/></td>


									<td style="width:70px;text-align: right;padding-top: 13px;">调整后EA<span class="red">*</span></td>
									<td><input type="number" min="1" name="EA_NUM_AF" id="EA_NUM_AF" value="${pd.EA_NUM_AF}" maxlength="32" placeholder="" title="调整后EA"/></td>


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
/*
		//单选框
		$(".chzn-select").chosen({search_contains: true});
		$(".chzn-select-deselect").chosen({allow_single_deselect:true});

		//日期框
		$('.date-picker').datepicker({autoclose: true});*/

	});

</script>
</body>
</html>