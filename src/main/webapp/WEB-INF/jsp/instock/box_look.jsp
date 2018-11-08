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


</script>
	</head>
<body>
	<form action="box/${msg }.do" name="Form" id="Form" method="post">

		<div id="zhongxin">
			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong>查看箱号信息</strong>

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
									<button class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</button>
								</div>
							</div>

						</div>
					</div>
					<div class="widget-main padding-3">
						<div>  <%--class="slim-scroll" data-height="125"--%>
							<div class="content">
								<div id="home1" class="tab-pane in active">

								<table id="table_report" class="table table-striped  table-hover table-condensed">
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">箱号</td>
										<td><input type="text" readOnly="readOnly"  name="BOX_NO" id="BOX_NO" value="${pd.BOX_NO}" maxlength="32" placeholder="" title="箱号"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">外箱号</td>
										<td><input type="text" readOnly="readOnly" name="BIG_BOX_NO" id="BIG_BOX_NO" value="${pd.BIG_BOX_NO}" maxlength="32" placeholder="" title="外箱号"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">客户</td>
										<td style="vertical-align:top;">
											<select  name="CUSTOMER_ID" id="CUSTOMER_ID" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${customerList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">产品</td>
										<td style="vertical-align:top;">
											<select  name="PRODUCT_ID" id="PRODUCT_ID" disabled='disabled' style="vertical-align:top;" >
												<c:forEach items="${productList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">进货单号</td>
										<td><input type="text" readOnly="readOnly" name="INSTOCK_NO" id="INSTOCK_NO" value="${pd.INSTOCK_NO}" maxlength="32" placeholder="" title="进货单编号"/></td>


										<td style="width:70px;text-align: right;padding-top: 13px;">出货单号</td>
										<td><input type="text" readOnly="readOnly" name="OUTSTOCK_NO" id="OUTSTOCK_NO" value="${pd.OUTSTOCK_NO}" maxlength="32" placeholder="" title="进货单编号"/></td>

									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">原始收货EA/EA数</td>
										<td><input class="input-small" type="number" readOnly="readOnly" name="RECEIV_QTY" id="RECEIV_QTY" value="${pd.RECEIV_QTY}" maxlength="32" placeholder="" title="原始收货EA数"/>
											<input class="input-small" type="number" readOnly="readOnly" name="EA_NUM" id="EA_NUM" value="${pd.EA_NUM}" maxlength="32" placeholder="" title="EA数"/>
										</td>

										<td style="width:70px;text-align: right;padding-top: 13px;">DateCode</td>
										<td><input type="text" readOnly="readOnly" name="DATE_CODE" id="DATE_CODE" value="${pd.DATE_CODE}" maxlength="32" placeholder="" title="净重"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">SeparateQty</td>
										<td><input type="text" readOnly="readOnly" name="SEPARATE_QTY" id="SEPARATE_QTY" value="${pd.SEPARATE_QTY}" maxlength="32" placeholder="" title="毛重"/></td>

									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">LotCode</td>
										<td><input type="text" readOnly="readOnly" name="LOT_NO" id="LOT_NO" value="${pd.LOT_NO}" maxlength="32" placeholder="" title="COO"/></td>


										<td style="width:70px;text-align: right;padding-top: 13px;">BIN</td>
										<td><input type="text" readOnly="readOnly" name="BIN_CODE" id="BIN_CODE" value="${pd.BIN_CODE}" maxlength="32" placeholder="" title="净重"/></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">COO</td>
										<td><input type="text" readOnly="readOnly" name="COO" id="COO" value="${pd.COO}" maxlength="32" placeholder="" title="COO"/></td>
									</tr>
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">Remark1</td>
										<td><input type="text" readOnly="readOnly" name="REMARK1" id="REMARK1" value="${pd.REMARK1}" maxlength="32" placeholder="" /></td>


										<td style="width:70px;text-align: right;padding-top: 13px;">Remark2</td>
										<td><input type="text" readOnly="readOnly" name="REMARK2" id="REMARK2" value="${pd.REMARK2}" maxlength="32" placeholder="" /></td>

										<td style="width:70px;text-align: right;padding-top: 13px;">Remark3</td>
										<td><input type="text" readOnly="readOnly" name="REMARK3" id="REMARK3" value="${pd.REMARK3}" maxlength="32" placeholder="" /></td>
									</tr>
									</table>
								</div>

								<div class="tabbable">
									<ul class="nav nav-tabs" id="myTab0">
										<li class="active"><a data-toggle="tab" href="#home0">入库信息</a></li>
										<li><a data-toggle="tab" href="#profile1">出库信息</a></li>
										<li><a data-toggle="tab" href="#profile2">库存信息</a></li>
									</ul>
									<div class="tab-content">
										<div id="home0" class="tab-pane in active">
											<table id="table_report1" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">分配库区</td>
													<td style="vertical-align:top;">
														<select  name="ASIGN_STORAGE" id="ASIGN_STORAGE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${storageList}" var="cnt">
																<option value=""></option>
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASIGN_STORAGE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">分配库位</td>
													<td style="vertical-align:top;">
														<select  name="ASIGN_LOCATOR" id="ASIGN_LOCATOR" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${locatorList}" var="cnt">
																<option value=""></option>
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASIGN_LOCATOR }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
													<td style="width:70px;text-align: right;padding-top: 13px;">码放库区</td>
													<td style="vertical-align:top;">
														<select  name="PUT_STORAGE" id="PUT_STORAGE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${storageList}" var="cnt">
																<option value=""></option>
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_STORAGE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">码放库位</td>
													<td style="vertical-align:top;">
														<select  name="PUT_LOCATOR" id="PUT_LOCATOR" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${locatorList}" var="cnt">
																<option value=""></option>
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_LOCATOR }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>


													<td style="width:70px;text-align: right;padding-top: 13px;">收货状态</td>
													<td style="vertical-align:top;">
														<select  name="RECEIPT_STATE" id="RECEIPT_STATE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${stockInStateList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.RECEIPT_STATE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
													<td style="width:70px;text-align: right;padding-top: 13px;">分配状态</td>
													<td style="vertical-align:top;">
														<select  name="ASSIGNED_STATE" id="ASSIGNED_STATE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${assignedList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASSIGNED_STATE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">码放状态</td>
													<td style="vertical-align:top;">
														<select  name="PUT_STATUS" id="PUT_STATUS" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${putStateList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_STATUS }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">收货时间</td>
													<td><input type="text" readOnly="readOnly" name="RECEIPT_TM" id="RECEIPT_TM" value="${pd.RECEIPT_TM}" maxlength="32" placeholder="" title="收货时间"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">分配时间</td>
													<td><input type="text" readOnly="readOnly" name="ASSIGNED_TM" id="ASSIGNED_TM" value="${pd.ASSIGNED_TM}" maxlength="32" placeholder="" title="分配时间"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">码放时间</td>
													<td><input type="text" readOnly="readOnly" name="PUT_TM" id="PUT_TM" value="${pd.PUT_TM}" maxlength="32" placeholder="" title="码放时间"/></td>
													<td style="width:70px;text-align: right;padding-top: 13px;"></td>
													<td></td>
													<td style="width:70px;text-align: right;padding-top: 13px;"></td>
													<td></td>

												</tr>
												</table>
											</table>
										</div>
										<div id="profile1" class="tab-pane">
											<table id="table_report2" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">分配库存状态</td>
													<td style="vertical-align:top;">
														<select  name="ASSIGNED_STOCK_STATE" id="ASSIGNED_STOCK_STATE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${assignedList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.ASSIGNED_STOCK_STATE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
													<td style="width:70px;text-align: right;padding-top: 13px;">拣货状态</td>
													<td style="vertical-align:top;">
														<select  name="PICK_STATE" id="PICK_STATE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${pickStateList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.PICK_STATE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>
													<td style="width:70px;text-align: right;padding-top: 13px;">配载状态</td>
													<td style="vertical-align:top;">
														<select  name="CARGO_STATE" id="CARGO_STATE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${cargoStateList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.CARGO_STATE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">发货状态</td>
													<td style="vertical-align:top;">
														<select  name="DEPOT_TYPE" id="DEPOT_TYPE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${depotTypeList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.DEPOT_TYPE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">分配库存数量</td>
													<td><input type="text" readOnly="readOnly" name="ASSIGNED_STOCK_NUM" id="ASSIGNED_STOCK_NUM" value="${pd.ASSIGNED_STOCK_NUM}" maxlength="32" placeholder="" title="分配库存数量"/></td>
													<td style="width:70px;text-align: right;padding-top: 13px;"></td>
													<td></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">分配库存时间</td>
													<td><input type="text" readOnly="readOnly" name="ASSIGNED_STOCK_TM" id="ASSIGNED_STOCK_TM" value="${pd.ASSIGNED_STOCK_TM}" maxlength="32" placeholder="" title="分配库存时间"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">拣货时间</td>
													<td><input type="text" readOnly="readOnly" name="PICK_TM" id="PICK_TM" value="${pd.PICK_TM}" maxlength="32" placeholder="" title="拣货时间"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">配载时间</td>
													<td><input type="text" readOnly="readOnly" name="CARGO_TM" id="CARGO_TM" value="${pd.CARGO_TM}" maxlength="32" placeholder="" title="配载时间"/></td>

												</tr>
												</table>

										</div>
										<div id="profile2" class="tab-pane">
											<table id="table_report3" class="table table-striped  table-hover table-condensed">
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">长CM</td>
													<td><input type="text" readOnly="readOnly" name="LONG_UNIT" id="LONG_UNIT" value="${pd.LONG_UNIT}" maxlength="32" placeholder="" title="长"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">宽CM</td>
													<td><input type="text" readOnly="readOnly" name="WIDE_UNIT" id="WIDE_UNIT" value="${pd.WIDE_UNIT}" maxlength="32" placeholder="" title="宽"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">高CM</td>
													<td><input type="text" readOnly="readOnly" name="HIGH_UNIT" id="HIGH_UNIT" value="${pd.HIGH_UNIT}" maxlength="32" placeholder="" title="高"/></td>

												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">体积CBM</td>
													<td><input type="text" readOnly="readOnly" name="VOLUME_UNIT" id="VOLUME_UNIT" value="${pd.VOLUME_UNIT}" maxlength="32" placeholder="" title="高"/></td>


													<td style="width:70px;text-align: right;padding-top: 13px;">净重</td>
													<td><input type="text" readOnly="readOnly" name="TOTAL_SUTTLE" id="TOTAL_SUTTLE" value="${pd.TOTAL_SUTTLE}" maxlength="32" placeholder="" title="净重"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">毛重</td>
													<td><input type="text" readOnly="readOnly" name="TOTAL_WEIGHT" id="TOTAL_WEIGHT" value="${pd.TOTAL_WEIGHT}" maxlength="32" placeholder="" title="毛重"/></td>

												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">冻结状态</td>
													<td style="vertical-align:top;">
														<select  name="FREEZE_STATE" id="FREEZE_STATE" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${freezeStateList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.FREEZE_STATE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">调整前EA</td>
													<td><input type="number" readOnly="readOnly" name="EA_NUM_BF" id="EA_NUM_BF" value="${pd.EA_NUM_BF}" maxlength="32" placeholder="" title="EA数量"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">备注</td>
													<td><input type="text" readOnly="readOnly" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder="" title="备注"/></td>
												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">创建人</td>
													<td><input type="text" readOnly="readOnly" name="CREATE_EMP" id="CREATE_EMP" value="${pd.CREATE_EMP}" maxlength="32" placeholder="" title="创建人"/></td>


													<td style="width:70px;text-align: right;padding-top: 13px;">创建时间</td>
													<td><input type="text" readOnly="readOnly" name="CREATE_TM" id="CREATE_TM" value="${pd.CREATE_TM}" maxlength="32" placeholder="" title="创建人"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">供应商料号</td>
													<td><input type="text" readOnly="readOnly" name="SUPPLIER_PROD" id="SUPPLIER_PROD" value="${pd.SUPPLIER_PROD}" maxlength="32" placeholder="" title="供应商料号"/></td>
												</tr>
												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">调整前库位</td>
													<td style="vertical-align:top;">
														<select  name="PUT_LOCATOR_BF" id="PUT_LOCATOR_BF" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${locatorList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_LOCATOR_BF }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">调整前客户</td>
													<td style="vertical-align:top;">
														<select  name="CUSTOMER_ID_BF" id="CUSTOMER_ID_BF" disabled='disabled' style="vertical-align:top;" >
															<c:forEach items="${customerList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.CUSTOMER_ID_BF }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>


													<td style="width:70px;text-align: right;padding-top: 13px;">库位调整时间</td>
													<td><input type="text" readOnly="readOnly" name="TRANSFER_TM" id="TRANSFER_TM" value="${pd.ASSIGNED_TM}" maxlength="32" placeholder="" title="分配时间"/></td>

												</tr>


												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">发货时间</td>
													<td><input type="text" readOnly="readOnly" name="DEPOT_TM" id="DEPOT_TM" value="${pd.DEPOT_TM}" maxlength="32" placeholder="" title="发货时间"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">冻结/释放时间</td>
													<td><input type="text" readOnly="readOnly" name="FREEZE_TM" id="FREEZE_TM" value="${pd.FREEZE_TM}" maxlength="32" placeholder="" title="冻结/释放时间"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">物权转移时间</td>
													<td><input type="text" readOnly="readOnly" name="PRODRIGHT_TM" id="PRODRIGHT_TM" value="${pd.PRODRIGHT_TM}" maxlength="32" placeholder="" title="物权转移时间"/></td>


												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">调整类型</td>
													<td  style="vertical-align:top;">
														<select  class="chzn-select" disabled='disabled' name="AUDIT_TYPE" id="AUDIT_TYPE" style="vertical-align:top;" data-placeholder=" "
														><option value=""></option>
															<c:forEach items="${auditTypeList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.AUDIT_TYPE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">调整状态</td>
													<td  style="vertical-align:top;">
														<select  class="chzn-select" disabled='disabled' name="AUDIT_STATE" id="AUDIT_STATE" style="vertical-align:top;"
														><option value=""></option>
															<c:forEach items="${auditStateList}" var="cnt">
																<option value="${cnt.id }" <c:if test="${cnt.id == pd.AUDIT_STATE }">selected</c:if>>${cnt.value }</option>
															</c:forEach>
														</select>
													</td>

													<td style="width:70px;text-align: right;padding-top: 13px;">申请人</td>
													<td><input type="text" readOnly="readOnly" name="AUDIT_APPLY_EMP" id="AUDIT_APPLY_EMP" value="${pd.AUDIT_APPLY_EMP}" maxlength="32" placeholder="" title="申请人"/></td>

												</tr>

												<tr>
													<td style="width:70px;text-align: right;padding-top: 13px;">申请时间</td>
													<td><input type="text" readOnly="readOnly" name="AUDIT_APPLY_TM" id="AUDIT_APPLY_TM" value="${pd.AUDIT_APPLY_TM}" maxlength="32" placeholder="" title="申请时间"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">审核人</td>
													<td><input type="text" readOnly="readOnly" name="AUDIT_PASS_EMP" id="AUDIT_PASS_EMP" value="${pd.AUDIT_PASS_EMP}" maxlength="32" placeholder="" title="申请人"/></td>

													<td style="width:70px;text-align: right;padding-top: 13px;">审核时间</td>
													<td><input type="text" readOnly="readOnly" name="AUDIT_PASS_TM" id="AUDIT_PASS_TM" value="${pd.AUDIT_PASS_TM}" maxlength="32" placeholder="" title="审核时间"/></td>

												</tr>
											</table>
										</div>
									</div>




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
			
			//单选框
			$(".chzn-select").chosen({search_contains: true});
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker({autoclose: true});
			
		});
		
		</script>
</body>
</html>