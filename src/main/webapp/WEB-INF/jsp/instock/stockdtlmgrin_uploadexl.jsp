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
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
		<!-- ace styles -->
		<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />


		<link rel="stylesheet" href="static/css/font-awesome.min.css" />


		<script type="text/javascript">
			
			//保存
			function save(){
				if($("#excel").val()=="" || document.getElementById("excel").files[0] =='请选择xls格式的文件'){
					
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
			    if(fileType != '.xls'){
			    	$("#excel").tips({
						side:3,
			            msg:'请上传xls格式的文件',
			            bg:'#AE81FF',
			            time:3
			        });
			    	$("#excel").val('');
			    	document.getElementById("excel").files[0] = '请选择xls格式的文件';
			    }
			}
		</script>
	</head>
<body>
	<form action="stockmgrin/readExcel.do" name="Form" id="Form" method="post" enctype="multipart/form-data">
		<input type="hidden" name="STOCKMGRIN_ID" id="STOCKMGRIN_ID" value="${pd.STOCKMGRIN_ID}"/>
		<input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/>


		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>从EXCEL导入数据</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-toolbox">
						<div class="btn-toolbar">
							<div class="btn-group">
								<a class="btn btn-mini btn-primary" onclick="save();">导入</a>
								<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
								<a class="btn btn-mini btn-success" onclick="window.location.href='<%=basePath%>/stockmgrin/downExcel.do'">下载模版</a>
							</div>
						</div>
					</div>

					<div class="widget-main padding-3">
						<div class="slim-scroll" data-height="125">
							<div class="content">

								<table id="table_prod_dtl" class="table table-striped table-bordered table-hover">
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
			<c:choose>
				<c:when test="${pd.IMP_SUCC != null}">
					<div class="alert alert-success">
							${pd.IMP_SUCC}
					</div>
				</c:when>
			</c:choose>

			<c:choose>
				<c:when test="${not empty xlsList}">
					<table id="table_report_rs" class="table table-striped table-bordered table-hover">
						<c:if test="${pd.IMP_RS != null}">
							<div class="alert alert-danger">
									${pd.IMP_RS},异常纪录如下...
							</div>
						</c:if>

							<%--<div class="alert alert-danger">
                                导入异常纪录,请查看备注标记的原因...
                            </div>--%>
						<thead>
						<tr>
							<th class="center">货主料号</th>
							<th class="center">数量</th>
							<th class="center">备注</th>
							<th class="center">操作</th>
						</tr>
						</thead>

						<tbody>
						<!-- 开始循环 -->
						<c:if test="${QX.cha == 1 }">
							<c:forEach items="${xlsList}" var="it" varStatus="vs">
								<tr>
								<td>${it.PROD}</td>
								<td>${it.EA_NUM}</td>
								<td>${it.MEMO}</td>

									<td>
										<div class="btn-group">
											<c:if test="${it.ADD_FLG == 1 }">
												<a class="btn btn-mini btn-success" onclick="add('${it.PROD}');"><span class="icon-plus"></span>新增</a>
											</c:if>
										</div>
									</td>
								</tr>

							</c:forEach>
						</c:if>
						</tbody>
					</table>
				</c:when>
			</c:choose>


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

	    <script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->

	    <script src="static/js/ace.min.js"></script>

		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			//上传
			$('#excel').ace_file_input({
				no_file:'请选择EXCEL ...',
				btn_choose:'选择',
				btn_change:'更改',
				droppable:false,
				onchange:null,
				thumbnail:false, //| true | large
				whitelist:'xls|xls',
				blacklist:'gif|png|jpg|jpeg'
				//onchange:''
				//
			});

			<c:if test="${pd != null and pd.success == 'success' }">
			   closeW();
			</c:if>
			
		});

		//删除
		function closeW(){
			bootbox.confirm("数据导入成功!", function(result) {
				if(result) {
					top.Dialog.close();
				}
			});
		}

		//新增
		function add(PRODUCT_CODE){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="新增";
			diag.URL = '<%=basePath%>product/goAdd.do?PRODUCT_CODE='+PRODUCT_CODE;
			diag.Width = 800;
			diag.Height = 800;
			diag.CancelEvent = function(){ //关闭事件
				diag.close();
			};
			diag.show();
		}

		</script>
	
</body>
</html>