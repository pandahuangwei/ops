<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
	<link rel="icon" href="static/login/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="static/login/favicon.ico" type="image/x-icon" />
	<link rel="bookmark" href="static/login/favicon.ico" type="image/x-icon" />
<!-- jsp文件头和头部 -->
<%@ include file="top.jsp"%>

</head>
<body>

	<div class="container-fluid" id="main-container">


			<div id="page-content" class="clearfix">

				<div class="page-header position-relative">

				</div>
				<!--/page-header-->
				<div class="row-fluid">

					<div class="space-6"></div>
					<div class="row-fluid">
						<div class="center">
							<div style="float:left;">
								报表模组:
								<table id="table_report" class="table table-striped table-bordered table-hover">
									<tbody>
									<tr>
									<td>1</td>
									<td>20170512增加(数据时效)字段查询</td>
									<td><div style="text-align:left;" class="alert alert-danger">
										即时数据:对即时数据进行分析,查询比较慢;
									</div>
										<div style="text-align:left;" class="alert alert-success">
											30分钟前数据：缓存数据比较快.
										</div></td>
									</tr>
									</tbody>
								</table>
							</div>

						</div>


					</div>
				</div>

				<!--/row-->

		</div>
		<!-- #main-content -->
	</div>
	<!--/.fluid-container#main-container-->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i
		class="icon-double-angle-up icon-only"></i>
	</a>
	<!-- basic scripts -->
	<script src="static/1.9.1/jquery.min.js"></script>
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>

	<script src="static/js/bootstrap.min.js"></script>
	<!-- page specific plugin scripts -->

	<!--[if lt IE 9]>
		<script type="text/javascript" src="static/js/excanvas.min.js"></script>
		<![endif]-->
	<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.easy-pie-chart.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.sparkline.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.pie.min.js"></script>
	<script type="text/javascript" src="static/js/jquery.flot.resize.min.js"></script>
	<!-- ace scripts -->
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- inline scripts related to this page -->


	<script type="text/javascript">

		$(top.hangge());

	</script>
</body>
</html>
