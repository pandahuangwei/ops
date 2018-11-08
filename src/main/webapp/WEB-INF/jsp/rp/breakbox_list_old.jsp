<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../system/admin/top.jsp"%>
</head>
<body>
<script type="text/javascript">
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

	//新增
	function add(){
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="新增";
		diag.URL = '<%=basePath%>box/goAdd.do?OPT_EVEN=stockAudit';
		diag.Width = 1200;
		diag.Height = 800;
		diag.CancelEvent = function(){ //关闭事件
			$("#Form").submit();
			diag.close();
		};
		diag.show();
	}


</script>
<div class="container-fluid" id="main-container">


	<div id="page-content" class="clearfix">

		<div class="row-fluid">

			<div class="row-fluid">

				<!-- 检索  -->
				<form action="breakbox/list.do" method="post" name="Form" id="Form">
					<input type="hidden" id="SEARCH_FLAG" name="SEARCH_FLAG" value="1"/>

					<div class="widget-box">
						<div class="widget-header widget-hea1der-small">
							<h6>查询条件</h6>
							<div class="widget-toolbar">
								<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
							</div>
						</div>
						<div class="widget-body">
							<div class="widget-main padding-3">
								<%--<div class="slim-scroll"> --%> <%--data-height="125"--%>

									<div class="content">

										<table id="table_prod_dtl" class="table table-striped table-condensed table-hover">

											<tr>

												<td style="width:110px;text-align: right;padding-top: 13px;">客户</td>
												<td><select  class="chzn-select" name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" data-placeholder=" ">
													<option value=""></option>
													<c:forEach items="${customerList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">产品</td>

												<td  style="vertical-align:top;">
													<select  class="chzn-select"  name="PRODUCT_ID" id="PRODUCT_ID" style="vertical-align:top;" data-placeholder=" "
													><option value=""></option>
														<c:forEach items="${productList}" var="cnt">
															<option value="${cnt.id }"
																	<c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:110px;text-align: right;padding-top: 13px;">内箱号</td>
												<td><input  id="BOX_NO" type="text" name="BOX_NO" value="${pd.BOX_NO}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">外箱号</td>
												<td><input  id="BIG_BOX_NO" type="text" name="BIG_BOX_NO" value="${pd.BIG_BOX_NO}" /></td>
											</tr>
											<tr>
												<td style="width:110px;text-align: right;padding-top: 13px;">入库单号</td>
												<td><input  id="INSTOCK_NO" type="text" name="INSTOCK_NO" value="${pd.INSTOCK_NO}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">出库单号</td>
												<td><input  id="OUTSTOCK_NO" type="text" name="OUTSTOCK_NO" value="${pd.OUTSTOCK_NO}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">库位</td>
												<td><input  id="LOCATOR_CODE" type="text" name="LOCATOR_CODE" value="${pd.LOCATOR_CODE}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">COO</td>
												<td><input  id="COO" type="text" name="COO" value="${pd.COO}" /></td>

											</tr>

											<tr>
												<td style="width:110px;text-align: right;padding-top: 13px;">DateCode</td>
												<td><input  id="DATE_CODE" type="text" name="DATE_CODE" value="${pd.DATE_CODE}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">LotCode</td>
												<td><input  id="LOT_NO" type="text" name="LOT_NO" value="${pd.LOT_NO}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;"></td>
												<td></td>

												<td style="width:110px;text-align: right;padding-top: 13px;"></td>
												<td></td>
											</tr>

										<%--	<tr>

												<td style="width:110px;text-align: right;padding-top: 13px;">InvoiceNo.</td>
												<td><input  id="INVOICE_NO" type="text" name="INVOICE_NO" value="${pd.INVOICE_NO}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">InvoiceNo.1</td>
												<td><input  id="INVOICE_NO1" type="text" name="INVOICE_NO1" value="${pd.INVOICE_NO1}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">HAWB/HBL</td>
												<td><input  id="HAWB_HBL" type="text" name="HAWB_HBL" value="${pd.HAWB_HBL}" /></td>
											</tr>
											--%>
										</table>

									<%--</div>--%>
								</div>
							</div>
							<div class="widget-toolbox padding-8 clearfix">
								<div class="btn-group">
									<button class="btn btn-primary btn-mini" onclick="search();" title="查询" data-rel="tooltip">
										<span class="icon-search"></span>查询</button>
									<a class="btn btn-success btn-mini" onclick="downLoad();" title="导出本次查询数据" data-rel="tooltip">
										<span class="icon-cloud-download"></span>导出</a>
									<button class="btn btn-mini btn-inverse" type="reset"><i class="icon-fire"></i>重置</button>
								</div>
							</div>
						</div>
					</div>

					<!-- 检索  -->
					<div class="widget-box "> <%--style="overflow:auto"--%>

						<div class="widget-body">
							<%-- <div class="widget-main  no-padding">--%>
							<div class="widget-main  padding-3">
							<%--	<div class="slim-scroll" data-width="1000"> height:450px;--%>
									<div class="content">
									<%--	<div style="overflow-x:auto; width:1800px; ">--%>
										<div style="position:relative; width:1800px; overflow-x:auto;">
										<table id="table_report" class="table table-striped table-bordered table-hover">

											<thead>
											<tr>
												<th class="center">组</th>
												<th class="center">Reotec Reference no.</th>
												<th class="center">入库单号</th>
												<th class="center">产品编码</th>
												<th class="center">Case Number</th>
												<th class="center">库位</th>
												<th class="center">SeparateQty</th>
												<th class="center">DateCode</th>
												<th class="center">LotCode</th>
												<th class="center">BIN</th>
												<th class="center">COO</th>
												<th class="center">Remark1</th>
												<th class="center">Remark2</th>
												<th class="center">Remark3</th>
												<th class="center">HAWB/HBL</th>
												<th class="center">收货数量(EA)</th>
												<th class="center">发货状态</th>
												<th class="center">發貨單號</th>
												<th class="center">Reotec Reference no.1</th>
												<th class="center">SI Ref.</th>
												<th class="center">发货箱号</th>
												<th class="center">发货数量(EA)</th>

											</tr>
											</thead>
											<tbody>

											<!-- 开始循环 -->
											<c:choose>
												<c:when test="${not empty varList}">
													<c:if test="${QX.cha == 1 }">
														<c:forEach items="${varList}" var="var" varStatus="vs">
															<tr>
																<td>${var.P_ID}</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.INVOICE_NO} </c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.INSTOCK_NO} </c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.PRODUCT_CODE} </c:if>

																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.BOX_NO}</c:if>

																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.LOCATOR_CODE}</c:if>

																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.SEPARATE_QTY}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																		<c:if test="${0==var.P_FLG}">${var.DATE_CODE}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																		<c:if test="${0==var.P_FLG}">${var.LOT_NO}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																		<c:if test="${0==var.P_FLG}">${var.BIN_CODE}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.COO}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.REMARK1}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.REMARK2}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.REMARK3}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink" </c:if>>
																	<c:if test="${0==var.P_FLG}">${var.HAWB_HBL}</c:if>
																</td>
																<td <c:if test="${0==var.P_FLG}">style="background-color: pink;text-align: right" </c:if> >
																	<c:if test="${0==var.P_FLG}">${var.EA_NUM}</c:if>
																</td>
																<td  <c:if test="${'未发货' eq var.DEPOT_STATE}">style="background-color: red" </c:if>
																	<c:if test="${'部分发货' eq var.DEPOT_STATE}">style="background-color: yellow" </c:if>
																	<c:if test="${'已发货'eq var.DEPOT_STATE}">style="background-color: green" </c:if>
																>${var.DEPOT_STATE}</td>
																<td>${var.OUTSTOCK_NO}</td>
																<td>${var.INVOICE_NO1}</td>
																<td>${var.SI_REFERENCE}</td>
																<td>${var.SEND_BOX}</td>
																<td>${var.SEND_EA}</td>
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



					<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
				</form>
			</div>




			<!-- PAGE CONTENT ENDS HERE -->
		</div><!--/row-->

	</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->

<!-- 返回顶部  -->
<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
	<i class="icon-double-angle-up icon-only"></i>
</a>

<!-- 引入 -->
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>

<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
<script type="text/javascript" src="static/js/bootstrap-colorpicker.min.js"></script>

<script type="text/javascript" src="static/js/daterangepicker.min.js"></script><!-- 日期框 -->

<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->

<%--
<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>
--%>

<script type="text/javascript">

	$(top.hangge());
	//检索
	function search(){
		top.jzts();
		$('#Form').attr("action", "breakbox/list.do?SEARCH_FLAG=1").submit();

		//#$("#Form").submit();
	}

	function downLoad(){

		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		var PRODUCT_ID = $.trim($("#PRODUCT_ID").val());
		var BOX_NO = $.trim($("#BOX_NO").val());
		var HAWB_HBL = $.trim($("#HAWB_HBL").val());

		var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
		var OUTSTOCK_NO = $.trim($("#OUTSTOCK_NO").val());
		var INVOICE_NO = $.trim($("#INVOICE_NO").val());
		var INVOICE_NO1 = $.trim($("#INVOICE_NO1").val());

		window.location.href='<%=basePath%>breakbox/excelRp.do?SEARCH_FLAG=1&BOX_NO='+BOX_NO+"&HAWB_HBL="+HAWB_HBL+"&CUSTOMER_ID="
		+CUSTOMER_ID+"&PRODUCT_ID="+PRODUCT_ID+"&INSTOCK_NO="+INSTOCK_NO+"&OUTSTOCK_NO="+OUTSTOCK_NO+"&INVOICE_NO="+INVOICE_NO+"&INVOICE_NO1="+INVOICE_NO1;

	}



</script>

<script type="text/javascript">

	$(function() {


		//下拉框
		$(".chzn-select").chosen({search_contains: true});
		$(".chzn-select-deselect").chosen({allow_single_deselect:true});

		//日期框
		$('.date-picker').datepicker({
			clearBtn: true,
			autoclose: true
		});

		// scrollables
		/*$('.slim-scroll').each(function () {
			var $this = $(this);
			$this.slimScroll({
				width: $this.data('width') || 100,
				railVisible:true
			});
		});*/

		//复选框
		$('table th input:checkbox').on('click' , function(){
			var that = this;
			$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						if(!this.disabled){
							this.checked = that.checked;
							$(this).closest('tr').toggleClass('selected');
						}
					});

		});
		$("button[type='reset']").click(function(){
			$(':input').attr("value",'');
			$(".chzn-select").each(function(){
				$(this).appendTo('<option value=""></option>');
				$(this).trigger("liszt:updated");
				$(this).chosen();
			});//重置bootstrap-select显示
			$(this).find("option").attr("selected", false);  //重置原生select的值
			$(this).find("option:first").attr("selected", true);
			$("#Form").submit();
		});
		//添加双击事件
		$("#table_report tr").dblclick(function() {

			<c:if test="${QX.edit == 1 }">
			var rid = $(this).attr('id');
			if (rid == null || rid == "") {
			} else {
				showDtl(rid);
			}
			</c:if>
		});


		$('#REAL_INSTOCK_DATE_BEGIN').datepicker({
			todayBtn : "linked",
			autoclose : true,
			todayHighlight : true
			//endDate : new Date()
		}).on('changeDate',function(e){
			var startTime = e.date;
			$('#REAL_INSTOCK_DATE_END').datepicker('setStartDate',startTime);
		});
		//结束时间：
		$('#REAL_INSTOCK_DATE_END').datepicker({
			todayBtn : "linked",
			autoclose : true,
			todayHighlight : true
			//endDate : new Date()
		}).on('changeDate',function(e){
			var endTime = e.date;
			$('#REAL_INSTOCK_DATE_BEGIN').datepicker('setEndDate',endTime);
		});

	});


	function showDtl(ids) {
		//alert(ids);
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="查看明细";
		diag.URL = '<%=basePath%>stocknow/goLookStockNowDtl.do?IDS='+ids;
		diag.Width = 1200;
		diag.Height = 800;
		diag.CancelEvent = function(){ //关闭事件
			diag.close();
		};
		diag.show();
	}

	//修改
	function look(Id){
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="查看";
		diag.URL = '<%=basePath%>box/goLook.do?BOX_ID='+Id;
		diag.Width = 1200;
		diag.Height = 600;
		diag.CancelEvent = function(){ //关闭事件
			/*if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
			 nextPage(${page.currentPage});
			 }*/
			diag.close();
		};
		diag.show();
	}

	//导出excel
	function toExcel(){
		window.location.href='<%=basePath%>inbound/excelRp.do';
	}
</script>

</body>
</html>

