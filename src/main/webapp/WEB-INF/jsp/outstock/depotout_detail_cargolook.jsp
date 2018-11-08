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


</script>

	<form action="depotout/goDepotCargoLook.do" name="Form" id="Form" method="post">
		<input type="hidden" name="CARGOOUT_ID" id="CARGOOUT_ID" value="${pd.CARGOOUT_ID}"/>
		<input type="hidden" name="CARGO_TYPE" id="CARGO_TYPE" value="${pd.CARGO_TYPE}"/>
		<input type="hidden" name="ASSIGN_NAME" id="ASSIGN_NAME" value="${pd.ASSIGN_NAME}"/>

		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong>配载详情信息</strong>
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
								<button class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</button>
							</div>

						</div>

					</div>
				</div>

					<div class="widget-main padding-3">
						<div class="slim-scroll" data-height="400">  <%--class="slim-scroll" data-height="125"--%>
							<div class="content">
								<table id="table_prod_dtl" class="table table-striped table-bordered table-hover">
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">配载单号</td>
										<td>
											<div class="row-fluid input-append">
											<input type="text" name="CARGO_NO" id="CARGO_NO"  value="${pd.CARGO_NO}" maxlength="32" placeholder="" readOnly="readOnly"/>

											</div>
										</td>

										<td style="width:120px;text-align: right;padding-top: 13px;">配载状态</td>
										<td style="vertical-align:top;">
											<select  class="chzn-select" name="CARGO_STATE" id="CARGO_STATE" style="vertical-align:top;" disabled="disabled">
												<option value=""></option>
												<c:forEach items="${cargoStateList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.CARGO_STATE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

										<td style="width:120px;text-align: right;padding-top: 13px;">配载时间</td>
										<td><input type="text" disabled="disabled" name="CARGO_TM" id="CARGO_TM" value="${pd.CARGO_TM}" maxlength="32" placeholder=""  class="date-picker" data-date-format="yyyy-mm-dd" /></td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">发货时间</td>
										<td><input type="text" disabled="disabled" name="DEPOT_TM" id="DEPOT_TM" value="${pd.DEPOT_TM}" maxlength="32" placeholder=""  class="date-picker" data-date-format="yyyy-mm-dd"/></td>


										<td style="width:120px;text-align: right;padding-top: 13px;">车次号</td>
										<td><input type="text" readOnly="readOnly" name="CAR_BATCH" id="CAR_BATCH" value="${pd.CAR_BATCH}" maxlength="32" placeholder="" title="车次号"/></td>

										<td style="width:120px;text-align: right;padding-top: 13px;">车牌号</td>
										<td><input type="text"  readOnly="readOnly" name="CAR_PLATE" id="CAR_PLATE" value="${pd.CAR_PLATE}" maxlength="32" placeholder="" title="车牌号"/></td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">开船日期</td>
										<td><input type="text" disabled="disabled"  name="SAIL_TM" id="SAIL_TM" value="${pd.SAIL_TM}" maxlength="32" placeholder=""  class="date-picker" data-date-format="yyyy-mm-dd" /></td>


										<td style="width:120px;text-align: right;padding-top: 13px;">预计到达日期</td>
										<td><input type="text" disabled="disabled" name="PRE_ARRIVETM" id="PRE_ARRIVETM" value="${pd.PRE_ARRIVETM}" maxlength="32" placeholder=""  class="date-picker" data-date-format="yyyy-mm-dd"/></td>

										<td style="width:120px;text-align: right;padding-top: 13px;">车型</td>
											<%--<td><input type="text" name="CAR_TYPE" id="CAR_TYPE" value="${pd.CAR_TYPE}" maxlength="32" placeholder="这里输入车型" title="车型"/></td>--%>
										<td style="vertical-align:top;">
											<select disabled="disabled" class="chzn-select" name="CAR_TYPE" id="CAR_TYPE" style="vertical-align:top;" data-placeholder=" ">
												<option value=""></option>
												<c:forEach items="${carTypeList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.CAR_TYPE }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>

									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">集装箱号</td>
										<td><input readOnly="readOnly" type="text" name="CONTAINER_NO" id="CONTAINER_NO" value="${pd.CONTAINER_NO}" maxlength="32" placeholder="" title="集装箱号"/></td>

										<td style="width:120px;text-align: right;padding-top: 13px;">船名</td>
										<td><input readOnly="readOnly" type="text" name="SAIL_NAME" id="SAIL_NAME" value="${pd.SAIL_NAME}" maxlength="32" placeholder="" title="船名"/></td>


										<td style="width:120px;text-align: right;padding-top: 13px;">航次</td>
										<td><input readOnly="readOnly" type="text" name="VOYAGE_NUM" id="VOYAGE_NUM" value="${pd.VOYAGE_NUM}" maxlength="32" placeholder="" title="航次"/></td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">起运港</td>
										<td><input readOnly="readOnly" type="text" name="SRC_PORT" id="SRC_PORT" value="${pd.SRC_PORT}" maxlength="32" placeholder="" title="起运港"/></td>

										<td style="width:120px;text-align: right;padding-top: 13px;">目的港</td>
										<td><input readOnly="readOnly" type="text" name="DEST_PORT" id="DEST_PORT" value="${pd.DEST_PORT}" maxlength="32" placeholder="" title="目的港"/></td>

										<td style="width:120px;text-align: right;padding-top: 13px;">提单号</td>
										<td><input  readOnly="readOnly" type="text" name="LADING_NO" id="LADING_NO" value="${pd.LADING_NO}" maxlength="32" placeholder="" title="提单号"/></td>
									</tr>
									<tr>
										<td style="width:120px;text-align: right;padding-top: 13px;">封箱号</td>
										<td><input readOnly="readOnly" type="text" name="SEAL_NO" id="SEAL_NO" value="${pd.SEAL_NO}" maxlength="32" placeholder="" title="封箱号"/></td>

										<td style="width:120px;text-align: right;padding-top: 13px;">备注</td>
										<td colspan="7"><input class="span7" readOnly="readOnly"   type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder="" title="备注"/></td>
									</tr>
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

				</div>
				<div class="widget-main padding-3">
					<div class="slim-scroll" data-height="400">
						<div class="content">
							<table id="table_had_dtl" class="table table-striped table-bordered table-hover">

								<thead>
								<tr>
									<%--<th class="center">
										<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
									</th>--%>
									<%--<th class="center">行号</th>--%>
									<th class="center">出库单</th>
									<th class="center">产品</th>
									<th class="center">CaseNumber</th>
									<%--<th class="center">拣货状态</th>--%>
									<th class="center">配载状态</th>
									<th class="center">存储库位</th>
									<th class="center">分配EA</th>
									<th class="center">拣货EA</th>
									<th class="center">配载EA</th>

								</tr>
								</thead>

								<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty assignList}">
										<c:if test="${QX.cha == 1 }">
											<c:forEach items="${assignList}" var="var" varStatus="vs">
												<tr>

												<%--	<td class='center' style="width: 30px;">
														<label><input type='checkbox' name='ids' value="${var.CONBI_IDS}" /><span class="lbl"></span></label>
													</td>--%>

													<%--<td>${var.ROW_NO}</td>--%>
													<td>${var.OUTSTOCK_NO}</td>
													<td>
														<c:forEach items="${allProductList}" var="cnt">
															<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
														</c:forEach>
													</td>
													<td>${var.BOX_NO}</td>

														<%--拣货状态--%>
													<%--<td <c:if test="${139==var.PICK_STATE}">style="background-color: red" </c:if>
														<c:if test="${140==var.PICK_STATE}">style="background-color: yellow" </c:if>
														<c:if test="${141==var.PICK_STATE}">style="background-color: green" </c:if>>
														<input type="hidden" name='pickstates' value="${var.PICK_STATE}">
														<c:forEach items="${pickStateList}" var="cnt">
															<c:if test="${cnt.id==var.PICK_STATE}">${cnt.value}</c:if>
														</c:forEach>
													</td>--%>
														<%--配载状态--%>
													<td <c:if test="${161==var.CARGO_STATE}">style="background-color: red" </c:if>
														<c:if test="${162==var.CARGO_STATE}">style="background-color: yellow" </c:if>
														<c:if test="${163==var.CARGO_STATE}">style="background-color: green" </c:if>>
														<input type="hidden" name='cargostates' value="${var.CARGO_STATE}">
														<c:forEach items="${cargoStateList}" var="cnt">
															<c:if test="${cnt.id==var.CARGO_STATE}">${cnt.value}</c:if>
														</c:forEach>
													</td>

														<%--分配库位--%>
													<td <c:if test="${'qsfp'==var.LOCATOR_ID}">style="background-color: pink" </c:if>>
														<c:forEach items="${allLocatorForShowList}" var="cnt">
															<c:if test="${cnt.id==var.LOCATOR_ID}">${cnt.value}</c:if>
														</c:forEach>
													</td>
													<td style="text-align: right">${var.ASSIGNED_EA}</td>
													<td style="text-align: right">${var.PICK_EA}</td>
													<td style="text-align: right">${var.CARGO_EA}</td>
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
                             <%--分页--%>
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