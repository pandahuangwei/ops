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
	function save(pid){

		if (pid == 2) {
			//收货库区 收货库位 LOCATOR_ID STORAGE_ID
			if($("#STORAGE_ID").val()==""){
				$("#STORAGE_ID").tips({
					side:3,
					msg:'请选择收货库区',
					bg:'#AE81FF',
					time:2
				});
				$("#STORAGE_ID").focus();
				return false;
			}

			if($("#LOCATOR_ID").val()==""){
				$("#LOCATOR_ID").tips({
					side:3,
					msg:'请选择收货库位',
					bg:'#AE81FF',
					time:2
				});
				$("#LOCATOR_ID").focus();
				return false;
			}

		}

		var str = '';
		for (var i = 0; i < document.getElementsByName('ids').length; i++) {
			if (document.getElementsByName('ids')[i].checked) {
				if (str == '') str += document.getElementsByName('ids')[i].value;
				else str += ',' + document.getElementsByName('ids')[i].value;
			}
		}

		if (str == '') {
			bootbox.dialog("您没有选择任何内容!",
					[
						{
							"label": "关闭",
							"class": "btn-small btn-success",
							"callback": function () {
								//Example.show("great success");
							}
						}
					]
			);

			$("#zcheckbox").tips({
				side: 3,
				msg: '点这里全选',
				bg: '#AE81FF',
				time: 8
			});

			return;
		} else {
			/*//判断是哪个按钮的事件
			if (pid == 1) {
				$('#Form').attr("action", "assignopt/save.do?OPT_EVEN="+pid+"&BOX_NOS="+str).submit();
			} else {
				$('#Form').attr("action", "assignopt/save.do?OPT_EVEN="+pid+"&BOX_NOS="+str).submit();
			}*/

			$('#Form').attr("action", "assignopt/save.do?OPT_EVEN="+pid+"&BOX_NOS="+str).submit();

			//$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

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

	<form action="assignout/goAssign.do" name="Form" id="Form" method="post">
		<input type="hidden" name="INSTOCK_NOS" id="INSTOCK_NOS" value="${pd.INSTOCK_NOS}"/>
		<input type="hidden" name="ASSIGN_TYPE" id="ASSIGN_TYPE" value="${pd.ASSIGN_TYPE}"/>
			<input type="hidden" name="ASSIGN_NAME" id="ASSIGN_NAME" value="${pd.ASSIGN_NAME}"/>

		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong> ${pd.ASSIGN_NAME}</strong>
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

									<c:if test="${pd.ASSIGN_TYPE == 1}">
										<a class="btn btn-mini btn-primary" onclick="save('${pd.ASSIGN_TYPE}');"><span class="icon-cogs"></span>自动分配</a>
									</c:if>
									<c:if test="${pd.ASSIGN_TYPE == 2}">
										<a class="btn btn-mini btn-primary" onclick="save('${pd.ASSIGN_TYPE}');"><span class="icon-eye-open"></span>手工分配</a>
									</c:if>
								<c:if test="${pd !=null and pd.ASSIGN_TYPE == 4 }">
									<a class="btn btn-mini btn-warning" onclick="save('${pd.ASSIGN_TYPE}');"><span class="icon-undo"></span>取消分配</a>
								</c:if>
							</div>
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							<div class="btn-group">
								<button class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</button>
							</div>

						</div>

					</div>
				</div>
					<div class="widget-main padding-3">
						<div>  <%--class="slim-scroll" data-height="125"--%>
							<div class="content">
								<table id="table_report" class="table table-striped  table-hover table-condensed">
									<thead>
									<tr>
										<th class="center">
											<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
										</th>

										<th class="center">序号</th>
										<th class="center">入库单号</th>
										<th class="center">客户</th>
										<th class="center">产品</th>
										<th class="center">箱号</th>
										<th class="center">收货状态</th>
										<th class="center">分配状态</th>
										<th class="center">分配库区</th>
										<th class="center">分配库位</th>
										<th class="center">码放状态</th>
										<th class="center">EA数量</th>
										<th class="center">长CM</th>
										<th class="center">宽CM</th>
										<th class="center">高CM</th>
										<th class="center">体积M³</th>
										<th class="center">净重KG</th>
										<th class="center">毛重KG</th>
									</tr>
									</thead>


									<tbody>

									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														<td class='center' style="width: 30px;">
															<label><input type='checkbox' name='ids' value="${var.BOX_NO}" /><span class="lbl"></span></label>
														</td>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td>${var.INSTOCK_NO}</td>
														<td>
															<c:forEach items="${customerList}" var="cnt">
																<c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<td><%--产品--%>
															<c:forEach items="${allProductList}" var="cnt">
																<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<td>${var.BOX_NO}</td>

															<%--收货状态--%>
														<td <c:if test="${75==var.RECEIPT_STATE}">style="background-color: red" </c:if>
															<c:if test="${76==var.RECEIPT_STATE}">style="background-color: yellow" </c:if>
															<c:if test="${77==var.RECEIPT_STATE}">style="background-color: green" </c:if>>
															<c:forEach items="${stockInStateList}" var="cnt">
																<c:if test="${cnt.id==var.RECEIPT_STATE}">${cnt.value}</c:if>
															</c:forEach>
														</td>

															<%--分配状态--%>
														<td <c:if test="${100==var.ASSIGNED_STATE}">style="background-color: red" </c:if>
															<c:if test="${101==var.ASSIGNED_STATE}">style="background-color: yellow" </c:if>
															<c:if test="${102==var.ASSIGNED_STATE}">style="background-color: green" </c:if>>
															<c:forEach items="${assignedList}" var="cnt">
																<c:if test="${cnt.id==var.ASSIGNED_STATE}">${cnt.value}</c:if>
															</c:forEach>
														</td>

														<%--分配库区--%>
														<td>
															<c:forEach items="${storageList}" var="cnt">
																<c:if test="${cnt.id==var.ASIGN_STORAGE}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<%--分配库位--%>
														<td <c:if test="${'qsfp'==var.ASIGN_LOCATOR}">style="background-color: pink" </c:if>>
															<c:forEach items="${allLocatorForShowList}" var="cnt">
																<c:if test="${cnt.id==var.ASIGN_LOCATOR}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<%--码放状态--%>
														<td <c:if test="${80==var.PUT_STATUS}">style="background-color: red" </c:if>
															<c:if test="${81==var.PUT_STATUS}">style="background-color: yellow" </c:if>
															<c:if test="${82==var.PUT_STATUS}">style="background-color: green" </c:if>
														>
															<c:forEach items="${putStateList}" var="cnt">
																<c:if test="${cnt.id==var.PUT_STATUS}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<td>${var.EA_NUM}</td>
														<td>${var.LONG_UNIT}</td>
														<td>${var.WIDE_UNIT}</td>
														<td>${var.HIGH_UNIT}</td>
														<td>${var.VOLUME_UNIT}</td>
														<td>${var.TOTAL_SUTTLE}</td>
														<td>${var.TOTAL_WEIGHT}</td>

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
								<div class="page-header position-relative">
									<table style="width:100%;">
										<tr>
											<td style="vertical-align:top;">
											</td>
											<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
										</tr>
									</table>
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