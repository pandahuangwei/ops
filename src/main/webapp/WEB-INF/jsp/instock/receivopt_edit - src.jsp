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
			if($("#INSTOCK_NO").val()==""){
			$("#INSTOCK_NO").tips({
				side:3,
	            msg:'请输入入库单号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#INSTOCK_NO").focus();
			return false;
		}
		if($("#BOX_NO").val()==""){
			$("#BOX_NO").tips({
				side:3,
	            msg:'请输入箱号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BOX_NO").focus();
			return false;
		}
		if($("#PREFIX_PROD").val()==""){
			$("#PREFIX_PROD").tips({
				side:3,
	            msg:'请输入前缀料号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PREFIX_PROD").focus();
			return false;
		}
		if($("#PROD_CODE").val()==""){
			$("#PROD_CODE").tips({
				side:3,
	            msg:'请输入料号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROD_CODE").focus();
			return false;
		}
		if($("#PREFIX_QTY").val()==""){
			$("#PREFIX_QTY").tips({
				side:3,
	            msg:'请输入前缀数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PREFIX_QTY").focus();
			return false;
		}
		if($("#RECEIV_QTY").val()==""){
			$("#RECEIV_QTY").tips({
				side:3,
	            msg:'请输入数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RECEIV_QTY").focus();
			return false;
		}
		if($("#COO").val()==""){
			$("#COO").tips({
				side:3,
	            msg:'请输入COO',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#COO").focus();
			return false;
		}
		if($("#PACKAGE_QTY").val()==""){
			$("#PACKAGE_QTY").tips({
				side:3,
	            msg:'请输入PACKAGE QTY',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PACKAGE_QTY").focus();
			return false;
		}
		if($("#BOARD_NO").val()==""){
			$("#BOARD_NO").tips({
				side:3,
	            msg:'请输入板号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#BOARD_NO").focus();
			return false;
		}
		if($("#QR_CODE").val()==""){
			$("#QR_CODE").tips({
				side:3,
	            msg:'请输入二维码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#QR_CODE").focus();
			return false;
		}
		if($("#PREFIX_LOTNO").val()==""){
			$("#PREFIX_LOTNO").tips({
				side:3,
	            msg:'请输入前缀LOTNO',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PREFIX_LOTNO").focus();
			return false;
		}
		if($("#LOT_NO").val()==""){
			$("#LOT_NO").tips({
				side:3,
	            msg:'请输入LOT_NO',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LOT_NO").focus();
			return false;
		}
		if($("#PREFIX_DATECODE").val()==""){
			$("#PREFIX_DATECODE").tips({
				side:3,
	            msg:'请输入前缀DATECODE',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PREFIX_DATECODE").focus();
			return false;
		}
		if($("#DATE_CODE").val()==""){
			$("#DATE_CODE").tips({
				side:3,
	            msg:'请输入DATECODE',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#DATE_CODE").focus();
			return false;
		}
		if($("#PREFIX_SCAN").val()==""){
			$("#PREFIX_SCAN").tips({
				side:3,
	            msg:'请输入前缀扫描码数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PREFIX_SCAN").focus();
			return false;
		}
		if($("#SCAN_CODE").val()==""){
			$("#SCAN_CODE").tips({
				side:3,
	            msg:'请输入扫描码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SCAN_CODE").focus();
			return false;
		}
		if($("#SCAN_QTY").val()==""){
			$("#SCAN_QTY").tips({
				side:3,
	            msg:'请输入描码数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#SCAN_QTY").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="receivopt/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="RECEIVOPT_ID" id="RECEIVOPT_ID" value="${pd.RECEIVOPT_ID}"/>
		<div id="zhongxin">
        <table id="table_report" class="table table-striped  table-hover table-condensed">

			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">入库单号</td>
				<td><input type="text" name="INSTOCK_NO" id="INSTOCK_NO" value="${pd.INSTOCK_NO}" maxlength="32" placeholder="这里输入入库单号" title="入库单号"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">箱号</td>
				<td><input type="text" name="BOX_NO" id="BOX_NO" value="${pd.BOX_NO}" maxlength="32" placeholder="这里输入箱号" title="箱号"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">前缀料号</td>
				<td><input type="text" name="PREFIX_PROD" id="PREFIX_PROD" value="${pd.PREFIX_PROD}" maxlength="32" placeholder="这里输入前缀料号" title="前缀料号"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">料号</td>
				<td><input type="text" name="PROD_CODE" id="PROD_CODE" value="${pd.PROD_CODE}" maxlength="32" placeholder="这里输入料号" title="料号"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">前缀数量</td>
				<td><input type="text" name="PREFIX_QTY" id="PREFIX_QTY" value="${pd.PREFIX_QTY}" maxlength="32" placeholder="这里输入前缀数量" title="前缀数量"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">数量</td>
				<td><input type="text" name="RECEIV_QTY" id="RECEIV_QTY" value="${pd.RECEIV_QTY}" maxlength="32" placeholder="这里输入数量" title="数量"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">COO</td>
				<td><input type="text" name="COO" id="COO" value="${pd.COO}" maxlength="32" placeholder="这里输入COO" title="COO"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">PACKAGE QTY</td>
				<td><input type="text" name="PACKAGE_QTY" id="PACKAGE_QTY" value="${pd.PACKAGE_QTY}" maxlength="32" placeholder="这里输入PACKAGE QTY" title="PACKAGE QTY"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">板号</td>
				<td><input type="text" name="BOARD_NO" id="BOARD_NO" value="${pd.BOARD_NO}" maxlength="32" placeholder="这里输入板号" title="板号"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">二维码</td>
				<td><input type="text" name="QR_CODE" id="QR_CODE" value="${pd.QR_CODE}" maxlength="32" placeholder="这里输入二维码" title="二维码"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">前缀LOTNO</td>
				<td><input type="text" name="PREFIX_LOTNO" id="PREFIX_LOTNO" value="${pd.PREFIX_LOTNO}" maxlength="32" placeholder="这里输入前缀LOTNO" title="前缀LOTNO"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">LOT_NO</td>
				<td><input type="text" name="LOT_NO" id="LOT_NO" value="${pd.LOT_NO}" maxlength="32" placeholder="这里输入LOT_NO" title="LOT_NO"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">前缀DATECODE</td>
				<td><input type="text" name="PREFIX_DATECODE" id="PREFIX_DATECODE" value="${pd.PREFIX_DATECODE}" maxlength="32" placeholder="这里输入前缀DATECODE" title="前缀DATECODE"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">DATECODE</td>
				<td><input type="text" name="DATE_CODE" id="DATE_CODE" value="${pd.DATE_CODE}" maxlength="32" placeholder="这里输入DATECODE" title="DATECODE"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">前缀扫描码数量</td>
				<td><input type="text" name="PREFIX_SCAN" id="PREFIX_SCAN" value="${pd.PREFIX_SCAN}" maxlength="32" placeholder="这里输入前缀扫描码数量" title="前缀扫描码数量"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">扫描码</td>
				<td><input type="text" name="SCAN_CODE" id="SCAN_CODE" value="${pd.SCAN_CODE}" maxlength="32" placeholder="这里输入扫描码" title="扫描码"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">描码数量</td>
				<td><input type="text" name="SCAN_QTY" id="SCAN_QTY" value="${pd.SCAN_QTY}" maxlength="32" placeholder="这里输入描码数量" title="描码数量"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
				</td>
			</tr>
		</table>
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