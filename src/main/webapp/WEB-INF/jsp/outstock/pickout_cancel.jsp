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

	//拣货 按入库单ID
	function manualPick(pid) {
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
			$('#Form').attr("action", "pickout/savePickManual.do?OPT_EVEN="+pid+"&STOCKMGROUT_ID_CHOSE="+str).submit();

			//$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}

	//拣货 按入库单明细ID
	function manualPick2(pid) {

		var str = '';
		var nos='';
		for (var i = 0; i < document.getElementsByName('ids1').length; i++) {
			if (document.getElementsByName('ids1')[i].checked) {
				if (str == '') {
					str += document.getElementsByName('ids1')[i].value;
					nos += document.getElementsByName('pickstates')[i].value;
				}else {
					str += ',' + document.getElementsByName('ids1')[i].value;
					nos += ',' + document.getElementsByName('pickstates')[i].value;
				}
			}
		}

		if(nos !='') {
			var nosarr = new Array(); //定义一数组
			nosarr = nos.split(","); //字符分割
			var lenth =  nosarr.length;
			if (lenth>=2) {
				var isSameNo = true;
				for (i=0;i<lenth;i++ ) {
					if(nosarr[i] =='141') {
						isSameNo = false;
					}
				}

				if(!isSameNo) {
					bootbox.dialog("已经拣货的产品不能重复拣货!",
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
					return;
				}
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

			$("#zcheckbox1").tips({
				side: 3,
				msg: '点这里全选',
				bg: '#AE81FF',
				time: 8
			});

			return;
		} else {
			$('#Form').attr("action", "pickout/savePickManual.do?OPT_EVEN="+pid+"&STOCKMGROUT_ID_CHOSE="+str).submit();

			//$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}

	//取消分配 按出库单明细
	function manualCancel(pid) {
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
			$('#Form').attr("action", "pickout/saveCancelManual.do?OPT_EVEN="+pid+"&STOCKMGROUT_ID_CHOSE="+str).submit();

			//$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}

	//取消分配 按出库单已分配明细
	function manualCancel2(pid) {
		var str = '';

		for (var i = 0; i < document.getElementsByName('ids1').length; i++) {
			if (document.getElementsByName('ids1')[i].checked) {
				if (str == '') {
					str += document.getElementsByName('ids1')[i].value;

				}else {
					str += ',' + document.getElementsByName('ids1')[i].value;
				}
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

			$("#zcheckbox1").tips({
				side: 3,
				msg: '点这里全选',
				bg: '#AE81FF',
				time: 8
			});

			return;
		} else {
			$('#Form').attr("action", "pickout/saveCancelManual.do?OPT_EVEN="+pid+"&STOCKMGROUT_ID_CHOSE="+str).submit();

			//$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}

</script>

	<form action="pickout/goPickCancel.do" name="Form" id="Form" method="post">
		<input type="hidden" name="STOCKMGROUT_IDS" id="STOCKMGROUT_IDS" value="${pd.STOCKMGROUT_IDS}"/>
		<input type="hidden" name="ASSIGN_TYPE" id="ASSIGN_TYPE" value="${pd.ASSIGN_TYPE}"/>
		<input type="hidden" name="ASSIGN_NAME" id="ASSIGN_NAME" value="${pd.ASSIGN_NAME}"/>

		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong>产品拣货信息</strong>
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
								<a class="btn btn-mini btn-warning" onclick="manualCancel(1);"><span class="icon-undo"></span>整单取消</a>
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
						<div class="slim-scroll" data-height="400">  <%--class="slim-scroll" data-height="125"--%>
							<div class="content">
								<table id="table_prod_dtl" class="table table-striped table-bordered table-hover">
									<thead>
									<tr>
										<th class="center">
                                             <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                                         </th>
										<%--<th class="center">行号</th>--%>
										<th class="center">出库单</th>
										<th class="center">客户</th>
										<%--<th class="center">产品</th>--%>
										<th class="center">分配状态</th>
										<th class="center">拣货状态</th>
										<th class="center">配载状态</th>
										<th class="center">发货状态</th>
										<th class="center">EA</th>
										<th class="center">分配EA</th>
										<th class="center">拣货EA</th>
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
															<label><input type='checkbox' name='ids' value="${var.STOCKMGROUT_ID}" /><span class="lbl"></span></label>
														</td>

														<%--<td>${var.ROW_NO}</td>--%>
														<td>${var.OUTSTOCK_NO}</td>
														<td>
															<c:forEach items="${customerList}" var="cnt">
																<c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<%--<td>
															<c:forEach items="${allProductList}" var="cnt">
																<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
															</c:forEach>
														</td>--%>
															<%--分配状态--%>
														<td <c:if test="${100==var.ASSIGNED_STATE_HEAD}">style="background-color: red" </c:if>
															<c:if test="${101==var.ASSIGNED_STATE_HEAD}">style="background-color: yellow" </c:if>
															<c:if test="${102==var.ASSIGNED_STATE_HEAD}">style="background-color: green" </c:if>>
															<c:forEach items="${assignedList}" var="cnt">
																<c:if test="${cnt.id==var.ASSIGNED_STATE_HEAD}">${cnt.value}</c:if>
															</c:forEach>
														</td>
															<%--拣货状态--%>
														<td  <c:if test="${139==var.PICK_STATE_HEAD}">style="background-color: red" </c:if>
															<c:if test="${140==var.PICK_STATE_HEAD}">style="background-color: yellow" </c:if>
															<c:if test="${141==var.PICK_STATE_HEAD}">style="background-color: green" </c:if>>
															<c:forEach items="${pickStateList}" var="cnt">
																<c:if test="${cnt.id==var.PICK_STATE_HEAD}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<td <c:if test="${161==var.CARGO_STATE_HEAD}">style="background-color: red" </c:if>
															<c:if test="${162==var.CARGO_STATE_HEAD}">style="background-color: yellow" </c:if>
															<c:if test="${163==var.CARGO_STATE_HEAD}">style="background-color: green" </c:if>
														>
															<c:forEach items="${cargoStateList}" var="cnt">
																<c:if test="${cnt.id==var.CARGO_STATE_HEAD}">${cnt.value}</c:if>
															</c:forEach>
														</td>

															<%--发货状态--%>
														<td  <c:if test="${142==var.DEPOT_STATE_HEAD}">style="background-color: red" </c:if>
															 <c:if test="${143==var.DEPOT_STATE_HEAD}">style="background-color: yellow" </c:if>
															 <c:if test="${144==var.DEPOT_STATE_HEAD}">style="background-color: green" </c:if>
														>
															<c:forEach items="${depotTypeList}" var="cnt">
																<c:if test="${cnt.id==var.DEPOT_STATE_HEAD}">${cnt.value}</c:if>
															</c:forEach>
														</td>

														<td style="text-align: right">${var.EA_EA}</td>
														<td style="text-align: right">${var.ASSIGNED_EA}</td>
														<td style="text-align: right">${var.PICK_EA}</td>
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

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong> 已拣货明细</strong>
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
								<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-warning" onclick="manualCancel2(2);"><span class="icon-undo"></span>明细取消</a>
								</c:if>
							</div>
						</div>

					</div>
				</div>
				<div class="widget-main padding-3">
					<div class="slim-scroll" data-height="400">
						<div class="content">
							<table id="table_had_dtl" class="table table-striped table-bordered table-hover">

								<thead>
								<tr>
									<th class="center">
										<label><input type="checkbox" id="zcheckbox1" /><span class="lbl"></span></label>
									</th>
									<th class="center">行号</th>
									<th class="center">出库单</th>
									<th class="center">产品</th>
									<th class="center">CaseNumber</th>
									<th class="center">拣货状态</th>
									<th class="center">存储库位</th>
									<th class="center">分配EA</th>
									<th class="center">拣货EA</th>

									<th class="center">板号</th>
									<th class="center">毛重</th>
									<th class="center">净重</th>
									<th class="center">体积</th>
									<th class="center">IP数</th>
									<th class="center">CS数</th>
									<th class="center">PL数</th>
									<th class="center">OT数</th>

								</tr>
								</thead>

								<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty assignList}">
										<c:if test="${QX.cha == 1 }">
											<c:forEach items="${assignList}" var="var" varStatus="vs">
												<tr>

													<td class='center' style="width: 30px;">
														<label><input type='checkbox' name='ids1' value="${var.STOCKDTLMGROUT_ID}" /><span class="lbl"></span></label>
													</td>

													<td>${var.ROW_NO}</td>
													<td>${var.OUTSTOCK_NO}</td>
													<td>
														<c:forEach items="${allProductList}" var="cnt">
															<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
														</c:forEach>
													</td>
													<td>${var.BOX_NO}</td>

														<%--拣货状态--%>
													<td <c:if test="${139==var.PICK_STATE}">style="background-color: red" </c:if>
														<c:if test="${140==var.PICK_STATE}">style="background-color: yellow" </c:if>
														<c:if test="${141==var.PICK_STATE}">style="background-color: green" </c:if>>
														<input type="hidden" name='pickstates' value="${var.PICK_STATE}">
														<c:forEach items="${pickStateList}" var="cnt">
															<c:if test="${cnt.id==var.PICK_STATE}">${cnt.value}</c:if>
														</c:forEach>
													</td>
														<%--分配库位--%>
													<td <c:if test="${'qsfp'==var.PUT_LOCATOR}">style="background-color: pink" </c:if>>
														<c:forEach items="${allLocatorForShowList}" var="cnt">
															<c:if test="${cnt.id==var.PUT_LOCATOR}">${cnt.value}</c:if>
														</c:forEach>
													</td>
													<td style="text-align: right">${var.ASSIGNED_EA}</td>
													<td style="text-align: right">${var.PICK_EA}</td>
														<%--板号 毛重 净重 体积--%>
													<td>${var.BOARD_NO}</td>
													<td style="text-align: right">${var.TOTAL_WEIGHT}</td>
													<td style="text-align: right">${var.TOTAL_SUTTLE}</td>
													<td style="text-align: right">${var.VOLUME_UNIT}</td>
														<%--IP数 CS数 PL数 OT数--%>
													<td style="text-align: right">${var.INPACK_EA}</td>
													<td style="text-align: right">${var.BOX_EA}</td>
													<td style="text-align: right">${var.PALLET_EA}</td>
													<td style="text-align: right">${var.OT_EA}</td>
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