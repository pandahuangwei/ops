<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />


		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->


		<script type="text/javascript">
			
			//保存
			function save(){
				if($("#excel").val()=="" ){

					$("#excel").tips({
						side:3,
			            msg:'请选择文件',
			            bg:'#AE81FF',
			            time:3
			        });
					return false;
				}
				
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
			
			function fileType(obj){
				var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
			    if(fileType != '.xls' && fileType != '.doc' && fileType != '.pdf'&& fileType != '.xlsx'){
			    	$("#excel").tips({
						side:3,
			            msg:'请上传xls、doc或pdf格式的文件',
			            bg:'#AE81FF',
			            time:3
			        });
			    	$("#excel").val('');
			    	document.getElementById("excel").files[0] = '请选择xls、doc或pdf格式的文件';
			    }
			}
		</script>
	</head>
<body>
	<form action="customer/uploadDeal.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>上传文件</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-toolbox">
						<div class="btn-toolbar">
							<div class="btn-group">
								<a class="btn btn-mini btn-primary" onclick="save();">上传</a>
								<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
							</div>
						</div>
					</div>

					<div class="widget-main padding-3">
						<div class="slim-scroll" data-height="125">
							<div class="content">


								<tr>

									<td style="width:70px;text-align: right;padding-top: 13px;">生效日期</td>
									<td><input class="date-picker" name="CONTRACTUAL_ACTIVE" id="CONTRACTUAL_ACTIVE" value="${pd.CONTRACTUAL_ACTIVE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  title="生效日期"/></td>

									<td style="width:70px;text-align: right;padding-top: 13px;">失效日期</td>
									<td><input class="date-picker" name="CONTRACTUAL_DISABLE" id="CONTRACTUAL_DISABLE" value="${pd.CONTRACTUAL_DISABLE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly"  title="失效日期"/></td>

								</tr>

								<table id="table_prod_dtl" class="table table-striped table-bordered table-hover">
								<input type="hidden" id="CUSTOMER_ID" name="CUSTOMER_ID" value="${pd.CUSTOMER_ID}" />
									<tr>
										<td style="padding-top: 20px;"><input type="file" id="excel" name="excel" style="width:50px;" onchange="fileType(this)" /></td>
									</tr>
								</table>


							</div>
						</div>
					</div>
				</div>
			</div>

		<%--<table style="width:95%;" >
			
			<tr>
				<td style="padding-top: 20px;"><input type="file" id="excel" name="excel" style="width:50px;" onchange="fileType(this)" /></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">导入</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
					<a class="btn btn-mini btn-success" onclick="window.location.href='<%=basePath%>/user/downExcel.do'">下载模版</a>
				</td>
			</tr>
		</table>--%>
		</div>


		<div id="zhongxin2" class="center" style="display:none"><br/><img src="static/images/jzx.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='static/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 	window.jQuery || document.write("<script src='static/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script src="static/js/bootstrap.min.js"></script>
		<!-- ace scripts -->
		<script src="static/assets/js/ace/elements.fileinput.js"></script>
		<script src="static/assets/js/ace/ace.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>

	    <script src="static/js/ace.min.js"></script>
	    <script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->

		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//上传
			$('#excel').ace_file_input({
				no_file:'请选择EXCEL/WORD/PDF ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'xls|xlsx|doc|pdf',
				blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});

			$(function() {

				//日期框
				$('.date-picker').datepicker({autoclose: true});

			});

			$('#CONTRACTUAL_ACTIVE').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var startTime = e.date;
				$('#CONTRACTUAL_DISABLE').datepicker('setStartDate',startTime);
			});
			//结束时间：
			$('#CONTRACTUAL_DISABLE').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var endTime = e.date;
				$('#CONTRACTUAL_ACTIVE').datepicker('setEndDate',endTime);
			});
		});



		</script>
	
</body>
</html>