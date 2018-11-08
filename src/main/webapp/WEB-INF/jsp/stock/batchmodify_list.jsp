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

	<script type="text/javascript">
		//TXT_FOU TXT_FIV TXT_SIX
		function setRemark1(checkb) {
			if (checkb.checked == true) {
				$("#TXT_SIX_USE").val('1');
				$("#TXT_FOU_NEW").val('');
				$("#TXT_FOU_NEW").removeAttr("readOnly");
				$("#TXT_FOU_NEW").attr("placeholder", " ");
			} else {
				$("#TXT_FOU_USE").val('0');
				$("#TXT_FOU_NEW").val('');
				$("#TXT_FOU_NEW").attr("readOnly", "readOnly");
				$("#TXT_FOU_NEW").attr("placeholder", " ");
			}
		}

		function setRemark2(checkb) {
			if (checkb.checked == true) {
				$("#TXT_FIV_USE").val('1');
				$("#TXT_FIV_NEW").val('');
				$("#TXT_FIV_NEW").removeAttr("readOnly");
				$("#TXT_FIV_NEW").attr("placeholder", " ");
			} else {
				$("#TXT_FIV_USE").val('0');
				$("#TXT_FIV_NEW").val('');
				$("#TXT_FIV_NEW").attr("readOnly", "readOnly");
				$("#TXT_FIV_NEW").attr("placeholder", " ");
			}
		}

		function setRemark3(checkb) {
			if (checkb.checked == true) {
				$("#TXT_SIX_USE").val('1');
				$("#TXT_SIX_NEW").val('');
				$("#TXT_SIX_NEW").removeAttr("readOnly");
				$("#TXT_SIX_NEW").attr("placeholder", " ");
			} else {
				$("#TXT_SIX_USE").val('0');
				$("#TXT_SIX_NEW").val('');
				$("#TXT_SIX_NEW").attr("readOnly", "readOnly");
				$("#TXT_SIX_NEW").attr("placeholder", " ");
			}
		}

		function save() {
			var remark1Use = $('#TXT_FOU_USE').prop('checked');
			var remark2Use = $('#TXT_FIV_USE').prop('checked');
			var remark3Use = $('#TXT_SIX_USE').prop('checked');

			if (remark1Use == false && remark2Use == false && remark3Use ==false ) {
				$("#table_remark").tips({
					side: 3,
					msg: 'Remark1/2/3至少需要修改一项',
					bg: '#AE81FF',
					time: 3
				});
				return false;
			}

			var modifyAll = $('#MODIFY_ALL').prop('checked');
			var str = '';
			if (modifyAll == false) {
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
					//本次修改的记录ID
					$('#DATA_IDS').val(str);
				}
			}

			$('#Form').attr("action", "batchModify/saveRemarkHistory.do").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	</script>
	</head>
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="batchModify/list.do" method="post" name="Form" id="Form">
				<input type="hidden" id="SEARCH_FLAG" name="SEARCH_FLAG" value="1"/>
				<input type="hidden" id="BATCH_ID" name="BATCH_ID" value="${pd.BATCH_ID}"/>
				<input type="hidden" id="DATA_IDS" name="DATA_IDS" value="${pd.DATA_IDS}"/>

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
											<td style="width:110px;text-align: right;padding-top: 13px;">客户</td>
											<td><select  class="chzn-select" name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" data-placeholder=" ">

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

											<td style="width:110px;text-align: right;padding-top: 13px;">入库单号</td>
											<td><input   id="INSTOCK_NO" type="text" name="INSTOCK_NO" value="${pd.INSTOCK_NO}" placeholder=""/></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">发票号</td>
											<td><input  id="BILL_NO" type="text" name="BILL_NO" value="${pd.BILL_NO}" /></td>

										<tr>
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

										<td style="width:110px;text-align: right;padding-top: 13px;">外箱号</td>
										<td><input  id="BIG_BOX_NO" type="text" name="BIG_BOX_NO" value="${pd.BIG_BOX_NO}" /></td>

										<td style="width:110px;text-align: right;padding-top: 13px;">箱号</td>
										<td><input  id="BOX_NO" type="text" name="BOX_NO" value="${pd.BOX_NO}" /></td>
										</tr>
										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">分配库存状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="ASSIGNED_STOCK_STATE_S" id="ASSIGNED_STOCK_STATE_S" value="${pd.ASSIGNED_STOCK_STATE_S}">
												<select  multiple class="chzn-select"  name="ASSIGNED_STOCK_STATE" id="ASSIGNED_STOCK_STATE" data-placeholder="多选..." style="vertical-align:top;" >
													<%--<option value=""></option>--%>
													<c:forEach items="${assignedList}" var="cnt">
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.ASSIGNED_STOCK_STATE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

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
												<input type="hidden" name="CARGO_STATE_S" id="CARGO_STATE_S"  value="${pd.CARGO_STATE_S}">
												<select  multiple class="chzn-select"  name="CARGO_STATE" id="CARGO_STATE" style="vertical-align:top;" data-placeholder="多选...">
													<c:forEach items="${cargoStateList}" var="cnt">
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.CARGO_STATE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
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

											<td style="width:70px;text-align: right;padding-top: 13px;">ReMark1</td>
											<td><input  id="TXT_FOU" type="text" name="TXT_FOU" value="${pd.TXT_FOU}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">ReMark2</td>
											<td><input  id="TXT_FIV" type="text" name="TXT_FIV" value="${pd.TXT_FIV}" /></td>

										</tr>
										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">ReMark3</td>
											<td><input  id="TXT_SIX" type="text" name="TXT_SIX" value="${pd.TXT_SIX}" /></td>

											<td  colspan="7" style="text-align: left;padding-top: 13px;">
												<span class="lbl">库存EA数</span>
												<input  class="input-small" type='checkbox' id="EA_SHOW"  name='EA_SHOW' value="1"
														<c:if test="${pd.EA_SHOW eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
												<span class="lbl">分配EA数</span>
												<input  class="input-small" type='checkbox' id="ASSIGNED_SHOW"  name='ASSIGNED_SHOW' value="1"
														<c:if test="${pd.ASSIGNED_SHOW eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
												<span class="lbl">冻结EA数</span>
												<input  class="input-small" type='checkbox' id="FREEZE_SHOW"  name='FREEZE_SHOW' value="1"
														<c:if test="${pd.FREEZE_SHOW eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
												<span class="lbl">可用EA数</span>
												<input  class="input-small" type='checkbox' id="USE_SHOW"  name='USE_SHOW' value="1"
														<c:if test="${pd.USE_SHOW eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>

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

								<button class="btn btn-mini btn-inverse" type="reset"><i class="icon-fire"></i>重置</button>
							</div>
						</div>
					</div>
				</div>
				<table id="table_remark" class="table table-striped table-condensed table-hover">
					<tr>
						<td style="width:70px;text-align: right;padding-top: 13px;">ReMark1</td>
						<td colspan="7"><input  id="TXT_FOU_NEW" type="text" name="TXT_FOU_NEW" value="${pd.TXT_FOU_NEW}" maxlength="32"
												<c:if test="${pd.TXT_FOU_USE eq 0 }">readOnly="readOnly" </c:if>/>
							<input  class="input-small" type='checkbox' id="TXT_FOU_USE"  name='TXT_FOU_USE' value="1"   onclick="setRemark1(this);"
									<c:if test="${pd.TXT_FOU_USE eq 1 }">checked="checked" </c:if> title="修改Remark1"/><span class="lbl"></span>
							&nbsp&nbsp&nbsp&nbsp&nbsp
							<span class="lbl">ReMark2</span>
							<input  id="TXT_FIV_NEW" type="text" name="TXT_FIV_NEW" value="${pd.TXT_FIV_NEW}" maxlength="32"
									<c:if test="${pd.TXT_FIV_USE eq 0 }">readOnly="readOnly" </c:if>/>
							<input  class="input-small" type='checkbox' id="TXT_FIV_USE"  name='TXT_FIV_USE' value="1" onclick="setRemark2(this);"
									<c:if test="${pd.TXT_FIV_USE eq 1 }">checked="checked" </c:if> title="修改Remark2"/><span class="lbl"></span>
							&nbsp&nbsp&nbsp&nbsp&nbsp
							<span class="lbl">ReMark3</span>
							<input  id="TXT_SIX_NEW" type="text" name="TXT_SIX_NEW" value="${pd.TXT_SIX_NEW}" maxlength="32"
									<c:if test="${pd.TXT_SIX_USE eq 0 }">readOnly="readOnly" </c:if>/>
							<input  class="input-small" type='checkbox' id="TXT_SIX_USE"  name='TXT_SIX_USE' value="1" onclick="setRemark3(this);"
									<c:if test="${pd.TXT_SIX_USE eq 1 }">checked="checked" </c:if> title="修改Remark3"/><span class="lbl"></span>

							&nbsp&nbsp&nbsp&nbsp&nbsp
							<span class="lbl">修改全部</span>
							<input  class="input-small" type='checkbox' id="MODIFY_ALL"  name='MODIFY_ALL' value="1"
									<c:if test="${pd.MODIFY_ALL eq 1 }">checked="checked" </c:if> title="修改全部"/><span class="lbl"></span>
							&nbsp&nbsp&nbsp&nbsp&nbsp
							<button class="btn btn-success btn-mini" onclick="save();" title="修改" data-rel="tooltip">
								<span class="icon-edit"></span>修改</button>
								<c:if test="${not empty pd.MODIFY_BATCH}">
									<span class="alert alert-success" >批号：${pd.MODIFY_BATCH} 修改记录数: ${pd.MODIFY_CNT}</span>
								</c:if>
						</td>

					<%--	<td style="width:110px;text-align: right;padding-top: 13px;">ReMark2</td>
						<td><input  id="TXT_FIV_NEW" type="text" name="TXT_FIV_NEW" value="${pd.TXT_FIV_NEW}" /></td>

						<td style="width:110px;text-align: right;padding-top: 13px;">ReMark3</td>
						<td><input  id="TXT_SIX_NEW" type="text" name="TXT_SIX_NEW" value="${pd.TXT_SIX_NEW}" /></td>--%>
					</tr>
				</table>
			<!-- 检索  -->
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
							<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">客户</th>
						<th class="center">入库单号</th>
						<th class="center">发票号</th>
						<th class="center">仓库</th>
						<th class="center">库区</th>
						<th class="center">库位</th>
						<th class="center">外箱号</th>
						<th class="center">箱号</th>
						<th class="center">料号</th>
						<th class="center">DateCode</th>
						<th class="center">LotCode</th>
						<th class="center">Remark1</th>
						<th class="center">Remark2</th>
						<th class="center">Remark3</th>
						<th class="center">库存EA数</th>
						<th class="center">分配EA数</th>
						<th class="center">冻结EA数</th>
						<th class="center">可用EA数</th>
					</tr>
				</thead>
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr id="${var.P_ID}">
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.P_ID}" /><span class="lbl"></span></label>
								</td>
								<td>
									<c:forEach items="${customerList}" var="cnt">
										<c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
									</c:forEach>
								</td>
								<td>${var.INSTOCK_NO}</td>
								<td>${var.BILL_NO}</td>
								<td>${var.PUT_STOCK}</td>
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
								<td>${var.BIG_BOX_NO}</td>
								<td>${var.BOX_NO}</td>
                                <td>
									<c:forEach items="${productList}" var="cnt">
										<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
									</c:forEach>
								</td>
								<td>${var.DATE_CODE}</td>
								<td>${var.LOT_NO}</td>
								<td>${var.REMARK1}</td>
								<td>${var.REMARK2}</td>
								<td>${var.REMARK3}</td>
								<td>${var.USE_EA}</td>
								<td>${var.ASSIGNED_EA}</td>
								<td>${var.FREEZE_EA}</td>
								<td>${var.USE_EA}</td>
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
			$("#ASSIGNED_STOCK_STATE_S").val($("#ASSIGNED_STOCK_STATE").val());
			$("#PICK_STATE_S").val($("#PICK_STATE").val());
			$("#CARGO_STATE_S").val($("#CARGO_STATE").val());
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

			$('#EA_SHOW').on('click', function() {
				if ($(this).prop('checked')) {
					$('tr').find('th:eq(15)').show();
					$('tr').find('td:eq(15)').show();
				} else {
					$('tr').find('th:eq(15)').hide();
					$('tr').find('td:eq(15)').hide();
				}
			});


			$('#ASSIGNED_SHOW').on('click', function() {
				if ($(this).prop('checked')) {
					$('tr').find('th:eq(16)').show();
					$('tr').find('td:eq(16)').show();
				} else {
					$('tr').find('th:eq(16)').hide();
					$('tr').find('td:eq(16)').hide();
				}
			});
			$('#FREEZE_SHOW').on('click', function() {
				if ($(this).prop('checked')) {
					$('tr').find('th:eq(17)').show();
					$('tr').find('td:eq(17)').show();
				} else {
					$('tr').find('th:eq(17)').hide();
					$('tr').find('td:eq(17)').hide();
				}
			});
			$('#USE_SHOW').on('click', function() {
				if ($(this).prop('checked')) {
					$('tr').find('th:eq(18)').show();
					$('tr').find('td:eq(18)').show();
				} else {
					$('tr').find('th:eq(18)').hide();
					$('tr').find('td:eq(18)').hide();
				}
			});

			if ($('#EA_SHOW').prop('checked')) {
				$('tr').find('th:eq(15)').show();
				$('tr').find('td:eq(15)').show();
			}else {
				$('tr').find('th:eq(15)').hide();
				$('tr').find('td:eq(15)').hide();
			}
			if ($('#ASSIGNED_SHOW').prop('checked')) {
				$('tr').find('th:eq(16)').show();
				$('tr').find('td:eq(16)').show();
			} else {
				$('tr').find('th:eq(16)').hide();
				$('tr').find('td:eq(16)').hide();
			}

			if ($('#FREEZE_SHOW').prop('checked')) {
				$('tr').find('th:eq(17)').show();
				$('tr').find('td:eq(17)').show();
			} else {
				$('tr').find('th:eq(17)').hide();
				$('tr').find('td:eq(17)').hide();
			}

			if ($('#USE_SHOW').prop('checked')) {
				$('tr').find('th:eq(18)').show();
				$('tr').find('td:eq(18)').show();
			} else {
				$('tr').find('th:eq(18)').hide();
				$('tr').find('td:eq(18)').hide();
			}

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
		function toExcel(){
			window.location.href='<%=basePath%>box/excel.do';
		}
		</script>
		
	</body>
</html>

