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
				<form action="inbound/list.do" method="post" name="Form" id="Form">
					<input type="hidden" id="SEARCH_FLAG" name="SEARCH_FLAG" value="${pd.SEARCH_FLAG}"/>

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
												<%--<td style="width:70px;text-align: right;padding-top: 13px;">库区</td>
												<input type="hidden" id="STORAGE_ID_S" name="STORAGE_ID_S" value="${pd.STORAGE_ID_S}"/>
												<td style="vertical-align:top;">
													<select  class="chzn-select" name="STORAGE_ID" id="STORAGE_ID" data-placeholder=" " style="vertical-align:top;" onchange="getLocator()">
														<option value=""></option>
														<c:forEach items="${storageList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.STORAGE_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">库位</td>
												<input type="hidden" id="LOCATOR_ID_S" name="LOCATOR_ID_S" value="${pd.LOCATOR_ID_S}"/>
												<td style="vertical-align:top;">
													<select  name="LOCATOR_ID" id="LOCATOR_ID" style="vertical-align:top;" data-placeholder=" ">
														<option value=""></option>
														<c:forEach items="${locatorList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>--%>


												<td style="width:110px;text-align: right;padding-top: 13px;">客户</td>
												<input type="hidden" id="CUSTOMER_ID_S" name="CUSTOMER_ID_S" value="${pd.CUSTOMER_ID_S}"/>
												<td><select  class="chzn-select" name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" data-placeholder=" ">
													<option value=""></option>
													<c:forEach items="${customerList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">产品</td>
												<input type="hidden" id="PRODUCT_ID_S" name="PRODUCT_ID_S" value="${pd.PRODUCT_ID_S}"/>
												<td  style="vertical-align:top;">
													<select  class="chzn-select"  name="PRODUCT_ID" id="PRODUCT_ID" style="vertical-align:top;" data-placeholder=" "
													><option value=""></option>
														<c:forEach items="${productList}" var="cnt">
															<option value="${cnt.id }"
																	<c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

													<td style="width:110px;text-align: right;padding-top: 13px;">箱号</td>
													<td><input  id="BOX_NO" type="text" name="BOX_NO" value="${pd.BOX_NO}" /></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">外箱号</td>
													<td><input  id="BIG_BOX_NO" type="text" name="BIG_BOX_NO" value="${pd.BIG_BOX_NO}" /></td>
											</tr>
											<tr>

												<td style="width:110px;text-align: right;padding-top: 13px;">库位</td>
												<td><input  id="LOCATOR_CODE" type="text" name="LOCATOR_CODE" value="${pd.LOCATOR_CODE}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">入库单号</td>
												<td><input  id="INSTOCK_NO" type="text" name="INSTOCK_NO" value="${pd.INSTOCK_NO}" /></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">Data Code</td>
												<td><input  id="DATE_CODE" type="text" name="DATE_CODE" value="${pd.DATE_CODE}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">Lot Code</td>
												<td><input  id="LOT_NO" type="text" name="LOT_NO" value="${pd.LOT_NO}" /></td>
											</tr>
											<tr>
												<td style="width:110px;text-align: right;padding-top: 13px;">COO</td>
												<td><input  id="COO" type="text" name="COO" value="${pd.COO}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">客户RefNo</td>
												<td><input  id="TXT_TWENT" type="text" name="TXT_TWENT" value="${pd.TXT_TWENT}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">THI RefNo</td>
												<td><input  id="TXT_ONE" type="text" name="TXT_ONE" value="${pd.TXT_ONE}" /></td>

												<td style="width:70px;text-align: right;padding-top: 13px;">ReMark1</td>
												<td><input  id="TXT_FOU" type="text" name="TXT_FOU" value="${pd.TXT_FOU}" /></td>

											</tr>
											<tr>
												<td style="width:110px;text-align: right;padding-top: 13px;">ReMark2</td>
												<td><input  id="TXT_FIV" type="text" name="TXT_FIV" value="${pd.TXT_FIV}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">ReMark3</td>
												<td><input  id="TXT_SIX" type="text" name="TXT_SIX" value="${pd.TXT_SIX}" /></td>

												<td style="width:110px;text-align: right;padding-top: 13px;"></td>
												<td></td>

												<td style="width:70px;text-align: right;padding-top: 13px;"></td>
												<td></td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">入库日期</td>
												<td colspan="5">
													<input type="hidden" id="REAL_INSTOCK_DATE_BEGIN_S" name="REAL_INSTOCK_DATE_BEGIN_S" value="${pd.REAL_INSTOCK_DATE_BEGIN_S}"/>
													<input type="hidden" id="REAL_INSTOCK_DATE_END_S" name="REAL_INSTOCK_DATE_END_S" value="${pd.REAL_INSTOCK_DATE_END_S}"/>
													<input class="date-picker" name="REAL_INSTOCK_DATE_BEGIN"
														   id="REAL_INSTOCK_DATE_BEGIN" value="${pd.REAL_INSTOCK_DATE_BEGIN}" type="text"
														   data-date-format="yyyy-mm-dd" readonly="readonly" placeholder=""/>
													到
													<input class="date-picker" name="REAL_INSTOCK_DATE_END" id="REAL_INSTOCK_DATE_END"
														   value="${pd.REAL_INSTOCK_DATE_END}" type="text"
														   data-date-format="yyyy-mm-dd" readonly="readonly"
														   placeholder=""/>
												</td>
												<td style="width:110px;text-align: right;padding-top: 13px;">数据时效</td>
												<td><select class="chzn-select" name="SEARCH_TYPE" id="SEARCH_TYPE"
															style="vertical-align:top;" data-placeholder=" ">
													<c:forEach items="${rpSearchTypeList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${cnt.id == pd.SEARCH_TYPE }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
												</td>
											</tr>

										</table>

									<%--</div>--%>
								</div>
							</div>
							<div class="widget-toolbox padding-8 clearfix">
								<div class="btn-group">
									<button class="btn btn-primary btn-mini" onclick="search();" title="查询" data-rel="tooltip">
										<span class="icon-search"></span>查询</button>
									<a class="btn btn-success btn-mini" onclick="downLoad();" title="导出" data-rel="tooltip">
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
												<th class="center">入库单编号</th>
												<th class="center">客户</th>
												<th class="center">预计入库日期</th>
												<th class="center">实际入库日期</th>
												<th class="center">订单号</th>
												<th class="center">箱.外箱号</th>
												<th class="center">箱.内箱号</th>
												<th class="center">料号</th>
												<th class="center">期望EA</th>
												<th class="center">收货EA</th>
												<th class="center">分配EA</th>
												<th class="center">码放EA</th>
												<th class="center">入库类型</th>
												<th class="center">总毛重</th>
												<th class="center">总净重</th>
												<th class="center">总体积</th>
												<th class="center">总价</th>
												<th class="center">总期望EA</th>
												<th class="center">优先级</th>
												<th class="center">费用录入完成</th>
												<th class="center">备注</th>

												<th class="center">收货状态</th>
												<th class="center">分配状态</th>
												<th class="center">码放状态</th>

												<th class="center">入库单.Carrier ETA</th>
												<th class="center">入库单.Carrier ATA</th>

												<th class="center">入库单.Date03</th>
												<th class="center">入库单.Date04</th>
												<th class="center">入库单.Date05</th>
												<th class="center">入库单.Date06</th>
												<th class="center">入库单.Date07</th>
												<th class="center">入库单.Date08</th>
												<th class="center">入库单.Date09</th>
												<th class="center">入库单.Date10</th>

												<th class="center">GW</th>
												<th class="center">Total Invoice Value</th>
												<th class="center">改單次數</th>

												<th class="center">入库单.Number04</th>
												<th class="center">入库单.Number05</th>
												<th class="center">入库单.Number06</th>
												<th class="center">入库单.Number07</th>
												<th class="center">入库单.Number08</th>
												<th class="center">入库单.Number09</th>
												<th class="center">入库单.Number10</th>

												<th class="center">THI RefNo</th>
												<th class="center">Manufacturer</th>
												<th class="center">Shipper</th>
												<th class="center">MAWB/MBL</th>
												<th class="center">HAWB/HBL</th>
												<th class="center"># of package</th>

												<th class="center">Declaration #</th>
												<th class="center">Currency</th>
												<th class="center">Flight No/Vsl+Voy</th>
												<th class="center">Truck Manifest#</th>
												<th class="center">TransportMode</th>
												<th class="center">包裝單位</th>
												<th class="center">發貨港口</th>
												<th class="center">FLAG</th>
												<th class="center">收貨方式</th>
												<th class="center">CashAdvance</th>
												<th class="center">PickArea</th>
												<th class="center">是否報關</th>
												<th class="center">OT</th>

												<th class="center">客户RefNo</th>
												<th class="center">入库日期</th>
												<th class="center">DATE CODE 转化</th>
												<th class="center">入仓时间</th>

												<th class="center">明细.Date04</th>
												<th class="center">明细.Date05</th>
												<th class="center">明细.Date06</th>
												<th class="center">明细.Date07</th>
												<th class="center">明细.Date08</th>
												<th class="center">明细.Date09</th>
												<th class="center">明细.Date10</th>

												<th class="center">SeparateQty</th>

												<th class="center">明细.Number02</th>
												<th class="center">明细.Number03</th>
												<th class="center">明细.Number04</th>
												<th class="center">明细.Number05</th>
												<th class="center">明细.Number06</th>
												<th class="center">明细.Number07</th>
												<th class="center">明细.Number08</th>
												<th class="center">明细.Number09</th>
												<th class="center">明细.Number10</th>

												<th class="center">CaseNumber(内箱号)</th>
												<th class="center">InvNo</th>

												<th class="center">明细.TEXT03</th>
												<th class="center">明细.Remark1</th>
												<th class="center">明细.Remark2</th>
												<th class="center">明细.Remark3</th>
												<th class="center">明细.TEXT07</th>
												<th class="center">明细.TEXT08</th>
												<th class="center">明细.TEXT09</th>
												<th class="center">明细.外箱号</th>
												<th class="center">明细.TEXT11</th>
												<th class="center">明细.TEXT12</th>

												<th class="center">Scan Code(料号)</th>
												<th class="center">明细.TEXT14</th>
												<th class="center">明细.COO</th>
												<th class="center">明细.LotCode</th>
												<th class="center">明细.TEXT17</th>
												<th class="center">明细.DC</th>
												<th class="center">明细.BIN</th>
												<th class="center">明细.TEXT20</th>
												<th class="center">库位</th>
											</tr>
											</thead>
											<tbody>

											<!-- 开始循环 -->
											<c:choose>
												<c:when test="${not empty varList}">
													<c:if test="${QX.cha == 1 }">
														<c:forEach items="${varList}" var="var" varStatus="vs">
															<tr id="${var.COMB_ID}">

																<td>${var.INSTOCK_NO}</td>
																<td>
																	<c:forEach items="${customerList}" var="cnt">
																		<c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
																	</c:forEach>
																</td>
																<td>${var.PRE_INSTOCK_DATE}</td>
																<td>${var.REAL_INSTOCK_DATE}</td>
																<td>${var.ORDER_NO}</td>
																<td>${var.BOX_BIG_BOX_NO}</td>
																<td>${var.BOX_BOX_NO}</td>
																<td>
																	<c:forEach items="${productList}" var="cnt">
																		<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
																	</c:forEach>
																</td>
																<td>${var.EA_EA}</td>
																<td>${var.EA_ACTUAL}</td>
																<td>${var.ASSIGNED_EA}</td>
																<td>${var.PUT_EA}</td>
																<td>
																	<c:forEach items="${stockInTypeList}" var="cnt">
																		<c:if test="${cnt.id==var.INSTOCK_TYPE}">${cnt.value}</c:if>
																	</c:forEach>
																</td>
																<td>${var.TOTAL_WEIGHT}</td>
																<td>${var.TOTAL_SUTTLE}</td>
																<td>${var.TOTAL_VOLUME}</td>
																<td>${var.TOTAL_PRICE}</td>
																<td>${var.TOTAL_EA}</td>
																<td>
																	<c:forEach items="${priorityLevelList}" var="cnt">
																		<c:if test="${cnt.id==var.PRIORITY_LEVEL}">${cnt.value}</c:if>
																	</c:forEach>
																</td>
																<td>
																	<input disabled="true"  type='checkbox' <c:if test="${var.COST_STATE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
																</td>
																<td>${var.MEMO}</td>
																<td	<c:if test="${75==var.INSTOCK_STATE}">style="background-color: red" </c:if>
																	<c:if test="${76==var.INSTOCK_STATE}">style="background-color: yellow" </c:if>
																	<c:if test="${77==var.INSTOCK_STATE}">style="background-color: green" </c:if>>

																	<c:forEach items="${stockInStateList}" var="cnt">
																		<c:if test="${cnt.id==var.INSTOCK_STATE}">${cnt.value}</c:if>
																	</c:forEach>
																</td>
																<td <c:if test="${100==var.ASSIGNED_STATE}">style="background-color: red" </c:if>
																	<c:if test="${101==var.ASSIGNED_STATE}">style="background-color: yellow" </c:if>
																	<c:if test="${102==var.ASSIGNED_STATE}">style="background-color: green" </c:if>>
																	<c:forEach items="${assignedList}" var="cnt">
																		<c:if test="${cnt.id==var.ASSIGNED_STATE}">${cnt.value}</c:if>
																	</c:forEach>
																</td>
																<td <c:if test="${80==var.PUT_STATE}">style="background-color: red" </c:if>
																	<c:if test="${81==var.PUT_STATE}">style="background-color: yellow" </c:if>
																	<c:if test="${82==var.PUT_STATE}">style="background-color: green" </c:if>
																>
																	<c:forEach items="${putStateList}" var="cnt">
																		<c:if test="${cnt.id==var.PUT_STATE}">${cnt.value}</c:if>
																	</c:forEach></td>
																<td>${var.CARRIER_ETA}</td>
																<%--<td>${var.TIME_ONE}</td>--%>
																<td>${var.CARRIER_ATA}</td>
																<td>${var.DATE03}</td>
																<td>${var.DATE04}</td>
																<td>${var.DATE05}</td>
																<td>${var.DATE06}</td>
																<td>${var.DATE07}</td>
																<td>${var.DATE08}</td>
																<td>${var.DATE09}</td>
																<td>${var.DATE10}</td>
																<td>${var.GW}</td>
																<td>${var.TOTAL_INVOICE_VALUE}</td>
																<td>${var.C1_NUM_THR}</td>
																<td>${var.NUMBER04}</td>
																<td>${var.NUMBER05}</td>
																<td>${var.NUMBER06}</td>
																<td>${var.NUMBER07}</td>
																<td>${var.NUMBER08}</td>
																<td>${var.NUMBER09}</td>
																<td>${var.NUMBER10}</td>
																<td>${var.INBOUND_REFERENCE}</td>
																<td>${var.MANUFACTURER}</td>
																<td>${var.SHIPPER}</td>
																<td>${var.MAWB_MBL}</td>
																<td>${var.HAWB_HBL}</td>
																<td>${var.OF_PACKAGE}</td>

																<td>${var.DECLARATION}</td>
																<td>${var.CURRENCY}</td>
																<td>${var.FLIGHT_NO}</td>
																<td>${var.TRUCK_MANIFEST}</td>
																<td>${var.TRANSPORTMODE}</td>
																<td>${var.C1_TXT_TWELVE}</td>
																<td>${var.C1_TXT_THIRT}</td>
																<td>${var.C1_TXT_FOURT}</td>
																<td>${var.C1_TXT_FIFT}</td>
																<td>${var.CASHADVANCE}</td>
																<td>${var.PICKAREA}</td>
																<td>${var.C1_TXT_EIGHT}</td>
																<td>${var.OT}</td>
																<td>${var.INVOICENO}</td>
																<td>${var.TIME_ONE}</td>
																<td>${var.TIME_TWO}</td>
																<td>${var.TIME_THR}</td>
																<td>${var.TIME_FOU}</td>
																<td>${var.TIME_FIV}</td>
																<td>${var.TIME_SIX}</td>
																<td>${var.TIME_SEV}</td>
																<td>${var.TIME_EIG}</td>
																<td>${var.TIME_NIG}</td>
																<td>${var.TIME_TEN}</td>
																<td>${var.NUM_ONE}</td>
																<td>${var.NUM_TWO}</td>
																<td>${var.NUM_THR}</td>
																<td>${var.NUM_FOU}</td>
																<td>${var.NUM_FIV}</td>
																<td>${var.NUM_SIX}</td>
																<td>${var.NUM_SEV}</td>
																<td>${var.NUM_EIG}</td>
																<td>${var.NUM_NIG}</td>
																<td>${var.NUM_TEN}</td>
																<td>${var.TXT_ONE}</td>
																<td>${var.TXT_TWO}</td>
																<td>${var.TXT_THR}</td>
																<td>${var.TXT_FOU}</td>
																<td>${var.TXT_FIV}</td>
																<td>${var.TXT_SIX}</td>
																<td>${var.TXT_SEV}</td>
																<td>${var.TXT_EIG}</td>
																<td>${var.TXT_NIG}</td>
																<td>${var.TXT_TEN}</td>
																<td>${var.TXT_ELEVEN}</td>
																<td>${var.TXT_TWELVE}</td>
																<td>${var.TXT_THIRT}</td>
																<td>${var.TXT_FOURT}</td>
																<td>${var.TXT_FIFT}</td>
																<td>${var.TXT_SIXT}</td>
																<td>${var.TXT_SEVENT}</td>
																<td>${var.TXT_EIGHT}</td>
																<td>${var.TXT_NINET}</td>
																<td>${var.TXT_TWENT}</td>

																<td>
																<c:forEach items="${locatorList}" var="cnt">
																	<c:if test="${cnt.id==var.PUT_LOCATOR}">${cnt.value}</c:if>
																</c:forEach>
																</td>
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
		$("#SEARCH_FLAG").val("1");
		$("#Form").submit();
	}

    function downLoad(){
        $("#SEARCH_FLAG").val("1");
        $('#Form').attr("action", "inbound/excelRp.do").submit();
    }

	function downLoad2(){
		//var STORAGE_ID = $.trim($("#STORAGE_ID").val());
		//var LOCATOR_ID = $.trim($("#LOCATOR_ID").val());
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		var PRODUCT_ID = $.trim($("#PRODUCT_ID").val());
		var REAL_INSTOCK_DATE_BEGIN = $.trim($("#REAL_INSTOCK_DATE_BEGIN").val());
		var REAL_INSTOCK_DATE_END = $.trim($("#REAL_INSTOCK_DATE_END").val());
		//var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
	//	var BOX_NO = $.trim($("#BOX_NO").val());REAL_INSTOCK_DATE_BEGIN_S REAL_INSTOCK_DATE_END_S


		window.location.href='<%=basePath%>inbound/excelRp.do?SEARCH_FLAG=1&REAL_INSTOCK_DATE_BEGIN='+REAL_INSTOCK_DATE_BEGIN+"&REAL_INSTOCK_DATE_END="+REAL_INSTOCK_DATE_END+"&CUSTOMER_ID="
		+CUSTOMER_ID+"&PRODUCT_ID="+PRODUCT_ID+"&BOX_NO="+$.trim($("#BOX_NO").val())
				+"&BIG_BOX_NO="+$.trim($("#BIG_BOX_NO").val())
				+"&DATE_CODE="+$.trim($("#DATE_CODE").val())
				+"&LOT_NO="+$.trim($("#LOT_NO").val())
				+"&COO="+$.trim($("#COO").val())
				+"&TXT_TWENT="+$.trim($("#TXT_TWENT").val())
				+"&TXT_ONE="+$.trim($("#TXT_ONE").val())
				+"&TXT_FOU="+$.trim($("#TXT_FOU").val())
				+"&TXT_FIV="+$.trim($("#TXT_FIV").val())
				+"&TXT_SIX="+$.trim($("#TXT_SIX").val())
				+"&SEARCH_TYPE="+$.trim($("#SEARCH_TYPE").val());
		/*//top.jzts();
		$("#SEARCH_FLAG").val("1");
		$("#Form").submit();*/

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

