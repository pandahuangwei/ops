<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="box/list.do" method="post" name="Form" id="Form">
				<div class="widget-box">
					<div class="widget-header widget-hea1der-small">
						<h6>查询条件</h6>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="widget-body">
						<div class="widget-main padding-3">
							<div class="slim-scroll" data-height="125">
								<div class="content">

									<table id="table_prod_dtl" class="table table-striped table-condensed table-hover">

										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">入库单号</td>
											<td><input   id="INSTOCK_NO" type="text" name="INSTOCK_NO" value="${pd.INSTOCK_NO}"
														 placeholder=""/></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">外箱/内箱号</td>
											<td><input  id="BOX_NO" type="text" name="BOX_NO" value="${pd.BOX_NO}" /></td>

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
										<tr>

										<td style="width:70px;text-align: right;padding-top: 13px;">分配库区</td>
										<td  style="vertical-align:top;">
											<select  class="chzn-select"  name="ASIGN_STORAGE" id="ASIGN_STORAGE" style="vertical-align:top;" data-placeholder=" "
											><option value=""></option>
												<c:forEach items="${storageList}" var="cnt">
													<option value="${cnt.id }"
															<c:if test="${cnt.id == pd.ASIGN_STORAGE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">分配库位</td>
										<td  style="vertical-align:top;">
											<select  class="chzn-select"  name="ASIGN_LOCATOR" id="ASIGN_LOCATOR" style="vertical-align:top;" data-placeholder=" "
											><option value=""></option>
												<c:forEach items="${locatorList}" var="cnt">
													<option value="${cnt.id }"
															<c:if test="${cnt.id == pd.ASIGN_LOCATOR }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">码放库区</td>
										<td  style="vertical-align:top;">
											<select  class="chzn-select"  name="PUT_STORAGE" id="PUT_STORAGE" style="vertical-align:top;" data-placeholder=" "
											><option value=""></option>
												<c:forEach items="${storageList}" var="cnt">
													<option value="${cnt.id }"
															<c:if test="${cnt.id == pd.PUT_STORAGE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">码放库位</td>
										<td  style="vertical-align:top;">
											<select  class="chzn-select"  name="PUT_LOCATOR" id="PUT_LOCATOR" style="vertical-align:top;" data-placeholder=" "
											><option value=""></option>
												<c:forEach items="${locatorList}" var="cnt">
													<option value="${cnt.id }"
															<c:if test="${cnt.id == pd.PUT_LOCATOR }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										</tr>
										<tr>

											<td style="width:110px;text-align: right;padding-top: 13px;">收货状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="RECEIPT_STATE_S" id="RECEIPT_STATE_S" value="${pd.ASSIGNED_STATE_S}">
												<select  multiple class="chzn-select"  name="RECEIPT_STATE" id="RECEIPT_STATE" data-placeholder="多选..." style="vertical-align:top;" >
													<%--<option value=""></option>--%>
													<c:forEach items="${stockInStateList}" var="cnt">
														<%--<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASSIGNED_STATE }">selected</c:if>>${cnt.value }</option>--%>
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.RECEIPT_STATE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;">分配状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="ASSIGNED_STATE_S" id="ASSIGNED_STATE_S" value="${pd.ASSIGNED_STATE_S}">
												<select  multiple class="chzn-select"  name="ASSIGNED_STATE" id="ASSIGNED_STATE" data-placeholder="多选..." style="vertical-align:top;" >
													<%--<option value=""></option>--%>
													<c:forEach items="${assignedList}" var="cnt">
														<%--<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASSIGNED_STATE }">selected</c:if>>${cnt.value }</option>--%>
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.ASSIGNED_STATE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;">码放状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="PUT_STATE_S" id="PUT_STATE_S"  value="${pd.INSTOCK_STATE_S}">
												<select multiple class="chzn-select"  name="PUT_STATE" id="PUT_STATE" data-placeholder="多选..."  style="vertical-align:top;" >
													<%--<option value="">全部</option>--%>
													<c:forEach items="${putStateList}" var="cnt">
														<%--<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_STATE }">selected</c:if>>${cnt.value }</option>--%>
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.PUT_STATE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;">分配库存状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="ASSIGNED_STOCK_STATE_S" id="ASSIGNED_STOCK_STATE_S" value="${pd.ASSIGNED_STOCK_STATE_S}">
												<select  multiple class="chzn-select"  name="ASSIGNED_STOCK_STATE" id="ASSIGNED_STOCK_STATE" data-placeholder="多选..." style="vertical-align:top;" >
													<%--<option value=""></option>--%>
													<c:forEach items="${assignedList}" var="cnt">
														<%--<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASSIGNED_STATE }">selected</c:if>>${cnt.value }</option>--%>
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.ASSIGNED_STOCK_STATE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

										</tr>
										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">拣货状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="PICK_STATE_S" id="PICK_STATE_S"  value="${pd.PICK_STATE_S}">
												<select  multiple class="chzn-select"  name="PICK_STATE" id="PICK_STATE"
														 style="vertical-align:top;" data-placeholder="多选..." >
													<c:forEach items="${pickStateList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${fn:contains(pd.PICK_STATE_S,cnt.id) }">selected</c:if>
														>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;">配载状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="CARGO_STATE_S" id="CARGO_STATE_S"  value="${pd.PICK_STATE_S}">
												<select  multiple class="chzn-select"  name="CARGO_STATE" id="CARGO_STATE"
														 style="vertical-align:top;" data-placeholder="多选..." >
													<c:forEach items="${cargoStateList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${fn:contains(pd.CARGO_STATE_S,cnt.id) }">selected</c:if>
														>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>


											<td style="width:110px;text-align: right;padding-top: 13px;">发货状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="DEPOT_STATE_S" id="DEPOT_STATE_S"  value="${pd.DEPOT_STATE_S}">
												<select multiple class="chzn-select"  name="DEPOT_STATE" id="DEPOT_STATE"
														style="vertical-align:top;" data-placeholder="多选...">
													<c:forEach items="${depotTypeList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${fn:contains(pd.DEPOT_STATE_S,cnt.id) }">selected</c:if>
														>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;">冻结状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="FREEZE_STATE_S" id="FREEZE_STATE_S"  value="${pd.FREEZE_STATE_S}">
												<select multiple class="chzn-select"  name="FREEZE_STATE" id="FREEZE_STATE"
														style="vertical-align:top;" data-placeholder="多选...">
													<c:forEach items="${freezeStateList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${fn:contains(pd.FREEZE_STATE_S,cnt.id) }">selected</c:if>
														>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

										</tr>

										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">Data Code</td>
											<td><input  id="DATE_CODE" type="text" name="DATE_CODE" value="${pd.DATE_CODE}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">Lot Code</td>
											<td><input  id="LOT_NO" type="text" name="LOT_NO" value="${pd.LOT_NO}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">COO</td>
											<td><input  id="COO" type="text" name="COO" value="${pd.COO}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">BIN</td>
											<td><input  id="BIN_CODE" type="text" name="BIN_CODE" value="${pd.BIN_CODE}" /></td>
										</tr>
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">ReMark1</td>
											<td><input  id="REMARK1" type="text" name="REMARK1" value="${pd.REMARK1}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">ReMark2</td>
											<td><input  id="REMARK2" type="text" name="REMARK2" value="${pd.REMARK2}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">ReMark3</td>
											<td><input  id="REMARK3" type="text" name="REMARK3" value="${pd.REMARK3}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;"></td>
											<td></td>

										</tr>

										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">入库日期</td>
											<td colspan="3">
												<input class="date-picker" name="CREATE_TM_BEGIN"
													   id="CREATE_TM_BEGIN" value="${pd.CREATE_TM_BEGIN}" type="text"
													   data-date-format="yyyy-mm-dd" readonly="readonly"
													   placeholder=""/>
												到
												<input class="date-picker" name="CREATE_TM_END" id="CREATE_TM_END"
													   value="${pd.CREATE_TM_END}" type="text"
													   data-date-format="yyyy-mm-dd" readonly="readonly"
													   placeholder=""/>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;">排序</td>
											<td colspan="3"  style="vertical-align:top;">
												<select class="chzn-select"  name="SORT_CLOUM" id="SORT_CLOUM"
														style="vertical-align:top;" data-placeholder=" " >
													<option value=""></option>
													<option value="BIG_BOX_NO" <c:if test="${'BIG_BOX_NO' eq pd.SORT_CLOUM }">selected</c:if>>外箱号</option>
													<option value="BOX_NO" <c:if test="${'BOX_NO' eq pd.SORT_CLOUM }">selected</c:if>>箱号</option>
													<option value="INSTOCK_NO" <c:if test="${'INSTOCK_NO' eq pd.SORT_CLOUM }">selected</c:if>>进货单</option>
													<option value="PRODUCT_CODE" <c:if test="${'PRODUCT_CODE' eq pd.SORT_CLOUM }">selected</c:if>>产品</option>
													<option value="RECEIPT_TM" <c:if test="${'RECEIPT_TM' eq pd.SORT_CLOUM }">selected</c:if>>收货时间</option>
													<option value="SSIGNED_TM" <c:if test="${'SSIGNED_TM' eq pd.SORT_CLOUM }">selected</c:if>>分配时间</option>
													<option value="PUT_TM" <c:if test="${'PUT_TM' eq pd.SORT_CLOUM }">selected</c:if>>码放时间</option>
													<option value="ASSIGNED_STOCK_TM" <c:if test="${'ASSIGNED_STOCK_TM' eq pd.SORT_CLOUM }">selected</c:if>>分配库存时间</option>
													<option value="PICK_TM" <c:if test="${'PICK_TM' eq pd.SORT_CLOUM }">selected</c:if>>拣货时间</option>
													<option value="CARGO_TM" <c:if test="${'CARGO_TM' eq pd.SORT_CLOUM }">selected</c:if>>配载时间</option>
													<option value="DEPOT_TM" <c:if test="${'DEPOT_TM' eq pd.SORT_CLOUM }">selected</c:if>>发货时间</option>
												</select>

											<select class="chzn-select"  name="SORT_BY" id="SORT_BY"
													style="vertical-align:top;" data-placeholder=" "  data-values="${pd.SORT_CLOUM}">
												<option value=""></option>
												<option value="ASC" <c:if test="${'ASC' eq pd.SORT_BY }">selected</c:if>>升序</option>
												<option value="DESC" <c:if test="${'DESC' eq pd.SORT_BY }">selected</c:if>>降序</option>
											</select>

											</td>

										</tr>
									</table>
								</div>
							</div>
						</div>
						<div class="widget-toolbox padding-8 clearfix">
							<div class="btn-group">
								<button class="btn btn-primary btn-mini" onclick="search();" title="查询" data-rel="tooltip">
									<span class="icon-search"></span>查询</button>

								<c:if test="${QX.add == 1 }">
									<a class="btn btn-success btn-mini" onclick="downLoad();" title="导出，需要一定时间生成Excel" data-rel="tooltip">
										<span class="icon-cloud-download"></span>导出</a>

								</c:if>

								<button class="btn btn-mini btn-inverse" type="reset"><i class="icon-fire"></i>重置</button>
							</div>
						</div>
					</div>
				</div>
			<!-- 检索  -->
				<div class="widget-box ">
					<div class="widget-body">
						<div class="widget-main  padding-3">
							<div class="content">
								<div style="position:relative; width:1800px; overflow-x:auto;">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">序号</th>
						<th class="center">外箱号</th>
						<th class="center">箱号</th>
						<th class="center">客户</th>
						<th class="center">产品</th>
						<th class="center">LotCode</th>
						<th class="center">DateCode</th>
						<th class="center">BIN</th>
						<th class="center">COO</th>
						<th class="center">SeperateQty</th>
						<th class="center">Remark1</th>
						<th class="center">Remark2</th>
						<th class="center">Remark3</th>
						<th class="center">原始收货EA数</th>
						<th class="center">可用EA数</th>
						<th class="center">进货单编号</th>
						<th class="center">码放库区</th>
						<th class="center">码放库位</th>
						<th class="center">收货状态</th>
						<th class="center">分配状态</th>
						<th class="center">码放状态</th>
						<th class="center">分配库存状态</th>
						<th class="center">拣货状态</th>
						<th class="center">配载状态</th>
						<th class="center">发货状态</th>
						<th class="center">冻结状态</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr id="${var.BOX_ID}">
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								        <td>${var.BIG_BOX_NO}</td>
										<td>${var.BOX_NO}</td>
										<td>
											<c:forEach items="${customerList}" var="cnt">
												<c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
											</c:forEach>
										</td>
								        <td>
											<c:forEach items="${productList}" var="cnt">
												<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
											</c:forEach>
										</td>
								         <td>${var.LOT_NO}</td>
								         <td>${var.DATE_CODE}</td>
										<td>${var.BIN_CODE}</td>
										<td>${var.COO}</td>
										<td>${var.SEPARATE_QTY}</td>
										<td>${var.REMARK1}</td>
										<td>${var.REMARK2}</td>
										<td>${var.REMARK3}</td>
								        <td style="text-align: right">${var.RECEIV_QTY}</td>
								        <td style="text-align: right">${var.USE_EA}</td>
										<td>${var.INSTOCK_NO}</td>
										<td>
											<c:forEach items="${storageList}" var="cnt">
												<c:if test="${cnt.id==var.PUT_STORAGE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
										<td>
											<c:forEach items="${locatorList}" var="cnt">
												<c:if test="${cnt.id==var.PUT_LOCATOR}">${cnt.value}</c:if>
											</c:forEach>
										</td>


										<td <c:if test="${75==var.RECEIPT_STATE}">style="background-color: red" </c:if>
											<c:if test="${76==var.RECEIPT_STATE}">style="background-color: yellow" </c:if>
											<c:if test="${77==var.RECEIPT_STATE}">style="background-color: green" </c:if>>

											<c:forEach items="${stockInStateList}" var="cnt">
												<c:if test="${cnt.id==var.RECEIPT_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>

										<td  <c:if test="${100==var.ASSIGNED_STATE}">style="background-color: red" </c:if>
											 <c:if test="${101==var.ASSIGNED_STATE}">style="background-color: yellow" </c:if>
											 <c:if test="${102==var.ASSIGNED_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${assignedList}" var="cnt">
												<c:if test="${cnt.id==var.ASSIGNED_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
										<td <c:if test="${80==var.PUT_STATUS}">style="background-color: red" </c:if>
											<c:if test="${81==var.PUT_STATUS}">style="background-color: yellow" </c:if>
											<c:if test="${82==var.PUT_STATUS}">style="background-color: green" </c:if>
										>
											<c:forEach items="${putStateList}" var="cnt">
												<c:if test="${cnt.id==var.PUT_STATUS}">${cnt.value}</c:if>
											</c:forEach>
										</td>

										<td  <c:if test="${100==var.ASSIGNED_STOCK_STATE}">style="background-color: red" </c:if>
											 <c:if test="${101==var.ASSIGNED_STOCK_STATE}">style="background-color: yellow" </c:if>
											 <c:if test="${102==var.ASSIGNED_STOCK_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${assignedList}" var="cnt">
												<c:if test="${cnt.id==var.ASSIGNED_STOCK_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>

										<td <c:if test="${139==var.PICK_STATE}">style="background-color: red" </c:if>
											<c:if test="${140==var.PICK_STATE}">style="background-color: yellow" </c:if>
											<c:if test="${141==var.PICK_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${pickStateList}" var="cnt">
												<c:if test="${cnt.id==var.PICK_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>

										<td <c:if test="${161==var.CARGO_STATE}">style="background-color: red" </c:if>
											<c:if test="${162==var.CARGO_STATE}">style="background-color: yellow" </c:if>
											<c:if test="${163==var.CARGO_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${cargoStateList}" var="cnt">
												<c:if test="${cnt.id==var.CARGO_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
										<td  <c:if test="${142==var.DEPOT_TYPE}">style="background-color: red" </c:if>
											 <c:if test="${143==var.DEPOT_TYPE}">style="background-color: yellow" </c:if>
											 <c:if test="${144==var.DEPOT_TYPE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${depotTypeList}" var="cnt">
												<c:if test="${cnt.id==var.DEPOT_TYPE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
										<td  <c:if test="${158==var.FREEZE_STATE}">style="background-color: red" </c:if>
											 <c:if test="${159==var.FREEZE_STATE}">style="background-color: yellow" </c:if>
											 <c:if test="${160==var.FREEZE_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${freezeStateList}" var="cnt">
												<c:if test="${cnt.id==var.FREEZE_STATE}">${cnt.value}</c:if>
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
			</div></div></div></div></div>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
		</div>
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
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
		//检索
		function search(){
			top.jzts();
           //
			$("#RECEIPT_STATE_S").val($("#RECEIPT_STATE").val());
			$("#ASSIGNED_STATE_S").val($("#ASSIGNED_STATE").val());
			$("#PUT_STATE_S").val($("#PUT_STATE").val());
			$("#ASSIGNED_STOCK_STATE_S").val($("#ASSIGNED_STOCK_STATE").val());
			$("#PICK_STATE_S").val($("#PICK_STATE").val());
			$("#CARGO_STATE_S").val($("#CARGO_STATE").val());
			$("#DEPOT_STATE_S").val($("#DEPOT_STATE").val());
			$("#FREEZE_STATE_S").val($("#FREEZE_STATE").val());

			$("#Form").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>box/goAdd.do';
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>box/delete.do?BOX_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>box/goEdit.do?BOX_ID='+Id;
			 diag.Width = 450;
			 diag.Height = 355;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}
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
			diag.Height = 800;
			diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage(${page.currentPage});
				}
				diag.close();
			};
			diag.show();
		}

		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//下拉框
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

			$("button[type='reset']").click(function(){
				$(':input').attr("value",'');
				$(".chzn-select").each(function(){
					$(this).trigger("liszt:updated");
					$(this).chosen();
				//	$(this).chosen({search_contains: true});

				});//重置bootstrap-select显示
				$(this).find("option").attr("selected", false);  //重置原生select的值
				$(this).find("option:first").attr("selected", false);
				$("#Form").submit();
			});

			//添加双击事件
			$("#table_report tr").dblclick(function() {
				<c:if test="${QX.edit == 1 }">
				var rid = $(this).attr('id');
				//alert(rid);
				if (rid == null || rid == "") {
					//add();
				} else {
					look(rid);
				}
				</c:if>
			});

			$('#CREATE_TM_BEGIN').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var startTime = e.date;
				$('#CREATE_TM_END').datepicker('setStartDate',startTime);
			});
			//结束时间：
			$('#CREATE_TM_END').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var endTime = e.date;
				$('#CREATE_TM_BEGIN').datepicker('setEndDate',endTime);
			});
			
		});
		
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>box/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${page.currentPage});
									 });
								}
							});
						}
					}
				}
			});
		}
		
		//导出excel
		function downLoad(){
			//window.location.href='<%=basePath%>box/excel.do';
            //top.jzts();
            //
            $("#RECEIPT_STATE_S").val($("#RECEIPT_STATE").val());
            $("#ASSIGNED_STATE_S").val($("#ASSIGNED_STATE").val());
            $("#PUT_STATE_S").val($("#PUT_STATE").val());
            $("#ASSIGNED_STOCK_STATE_S").val($("#ASSIGNED_STOCK_STATE").val());
            $("#PICK_STATE_S").val($("#PICK_STATE").val());
            $("#CARGO_STATE_S").val($("#CARGO_STATE").val());
            $("#DEPOT_STATE_S").val($("#DEPOT_STATE").val());
            $("#FREEZE_STATE_S").val($("#FREEZE_STATE").val());

            $('#Form').attr("action", "box/excel.do").submit();

		}
		</script>

	</body>
</html>

