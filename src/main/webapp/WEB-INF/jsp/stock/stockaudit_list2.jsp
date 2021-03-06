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


	function makeAdd(msg){
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
					if(msg == '确定批准选择的箱号吗?'){
						//top.jzts();
						/*$.ajax({
							type: "POST",
							url: '<%=basePath%>stockaudit/saveAuditPass.do?tm='+new Date().getTime(),
							data: {BOX_IDS:str},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								/!*$.each(data.list, function(i, list){
									nextPage(${page.currentPage});
								});*!/
								$("#Form").submit();
							}
						});*/
						var url = '<%=basePath%>stockaudit/saveAuditPass.do?BOX_IDS='+str+'&tm=' + new Date().getTime();
						$.get(url,function(data){
							//$("#Form").submit();
							search();
						});
					}
				}
			}
		});
	}

	function makeReject(msg){
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
					if(msg == '确定驳回选择的箱号吗?'){
						//top.jzts();
						/*$.ajax({
							type: "POST",
							url: '<%=basePath%>stockaudit/saveAudiReject.do?tm='+new Date().getTime(),
							data: {BOX_IDS:str},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								alert(1);
								$("#Form").submit();
								/!*$.each(data.list, function(i, list){
									nextPage(${page.currentPage});
								});*!/
							}
						});*/
						var url = '<%=basePath%>stockaudit/saveAudiReject.do?BOX_IDS='+str+'&tm=' + new Date().getTime();
						$.get(url,function(data){
							//$("#Form").submit();

							search();
						});
					}
				}
			}
		});
	}

</script>
<div class="container-fluid" id="main-container">


	<div id="page-content" class="clearfix">

		<div class="row-fluid">

			<div class="row-fluid">

				<!-- 检索  -->
				<form action="stockaudit/list2.do" method="post" name="Form" id="Form">
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
												<td style="width:70px;text-align: right;padding-top: 13px;">库区</td>
												<td style="vertical-align:top;">
													<select  class="chzn-select" name="STORAGE_ID" id="STORAGE_ID" data-placeholder=" " style="vertical-align:top;" onchange="getLocator()">
														<option value=""></option>
														<c:forEach items="${storageList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.STORAGE_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">库位</td>
												<td style="vertical-align:top;">
													<select  name="LOCATOR_ID" id="LOCATOR_ID" style="vertical-align:top;" data-placeholder=" ">
														<option value=""></option>
														<c:forEach items="${locatorList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_ID }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>


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
											</tr>
											<tr>
												<td style="width:110px;text-align: right;padding-top: 13px;">入库单号</td>
												<td><input   id="INSTOCK_NO" type="text" name="INSTOCK_NO" value="${pd.INSTOCK_NO}" placeholder=""/></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">箱号/板号</td>
												<td><input   id="BOX_NO" type="text" name="BOX_NO" value="${pd.BOX_NO}" placeholder=""/></td>


												<td style="width:70px;text-align: right;padding-top: 13px;">调整类型</td>
												<td  style="vertical-align:top;">
													<select  class="chzn-select"  name="AUDIT_TYPE" id="AUDIT_TYPE" style="vertical-align:top;" data-placeholder=" "
													><option value=""></option>
														<c:forEach items="${auditTypeList}" var="cnt">
															<option value="${cnt.id }"
																	<c:if test="${cnt.id == pd.AUDIT_TYPE }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">调整状态</td>
												<td  style="vertical-align:top;">
													<input type="hidden" name="AUDIT_STATE_S" id="AUDIT_STATE_S"  value="${pd.AUDIT_STATE_S}">
													<select  class="chzn-select"  name="AUDIT_STATE" id="AUDIT_STATE" style="vertical-align:top;"  data-placeholder="多选..."
													><option value=""></option>
														<c:forEach items="${auditStateList}" var="cnt">
															<option value="${cnt.id }" <c:if test="${fn:contains(pd.AUDIT_STATE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
														</c:forEach>
													</select>
												</td>
											</tr>
											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">申请人</td>
												<td  style="vertical-align:top;">
													<select  class="chzn-select"  name="AUDIT_APPLY_EMP" id="AUDIT_APPLY_EMP" style="vertical-align:top;" data-placeholder=" "
													><option value=""></option>
														<c:forEach items="${userList}" var="cnt">
															<option value="${cnt.id }"
																	<c:if test="${cnt.id == pd.AUDIT_APPLY_EMP }">selected</c:if>>${cnt.value }</option>
														</c:forEach>
													</select>
												</td>

                                               <td style="width:70px;text-align: right;padding-top: 13px;">申请日期</td>
                                               <td colspan="7">

                                                   <input class="date-picker" name="AUDIT_APPLY_TM_BEGIN"
                                                          id="AUDIT_APPLY_TM_BEGIN" value="${pd.AUDIT_APPLY_TM_BEGIN}" type="text"
                                                          data-date-format="yyyy-mm-dd" readonly="readonly" placeholder=""/>
                                                   到
                                                   <input class="date-picker" name="AUDIT_APPLY_TM_END" id="AUDIT_APPLY_TM_END"
                                                          value="${pd.AUDIT_APPLY_TM_END}" type="text"
                                                          data-date-format="yyyy-mm-dd" readonly="readonly"
                                                          placeholder=""/>
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
										<a class="btn btn-mini btn-success" onclick="makeAdd('确定批准选择的箱号吗?');"><span class="icon-ok"></span>批准</a>
										<a class="btn btn-mini btn-warning" onclick="makeReject('确定驳回选择的箱号吗?');"><span class="icon-ban-circle"></span>驳回</a>
									</c:if>
									<button class="btn btn-mini btn-inverse" type="reset"><i class="icon-fire"></i>重置</button>
								</div>
							</div>
						</div>
					</div>

					<!-- 检索  -->


					<table id="table_report" class="table table-striped table-bordered table-hover">

						<thead>
						<tr>
							<th class="center">
								<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
							</th>
							<th class="center">序号</th>
							<th class="center">箱号</th>
							<th class="center">产品</th>
							<th class="center">客户</th>
							<th class="center">EA数</th>
							<th class="center">入库日期</th>
							<th class="center">存储库位</th>
							<th class="center">调整类型</th>
							<th class="center">调整状态</th>
							<%--<th class="center">IP数</th>
							<th class="center">CS数</th>
							<th class="center">PL数</th>
							<th class="center">OT数</th>--%>
						</tr>
						</thead>
						<tbody>

						<!-- 开始循环 -->
						<c:choose>
							<c:when test="${not empty varList}">
								<c:if test="${QX.cha == 1 }">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr id="${var.BOX_ID}">
											<td class='center' style="width: 30px;">
												<label><input type='checkbox' name='ids' value="${var.BOX_ID}" /><span class="lbl"></span></label>
											</td>
											<td class='center' style="width: 30px;">${vs.index+1}</td>



											<td>${var.BOX_NO}</td>

											<td>
												<c:forEach items="${productList}" var="cnt">
													<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
												</c:forEach>
											</td>
											<td>
												<c:forEach items="${customerList}" var="cnt">
													<c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
												</c:forEach>
											</td>


											<td style="text-align: right">
												<c:if test="${3==var.AUDIT_TYPE}">${var.EA_AUDIT} </c:if>
												<c:if test="${1==var.AUDIT_TYPE}">${var.EA_EA} </c:if>
												<c:if test="${2==var.AUDIT_TYPE}">${var.EA_EA} </c:if>
											</td>
											<td>${var.REAL_INSTOCK_DATE}</td>
											<%--style="background-color: lightgreen"--%>
											<td >
												<c:forEach items="${locatorList}" var="cnt">
													<c:if test="${cnt.id==var.PUT_LOCATOR}">${cnt.value}</c:if>
												</c:forEach>
											</td>

												<%--IP数 CS数 PL数 OT数--%>
											<%--<td style="text-align: right">${var.INPACK_EA}</td>
											<td style="text-align: right">${var.BOX_EA}</td>
											<td style="text-align: right">${var.PALLET_EA}</td>
											<td style="text-align: right">${var.OT_EA}</td>--%>
												<%--<td>${var.MEMO}</td>--%>

											<td>
												<c:forEach items="${auditTypeList}" var="cnt">
													<c:if test="${cnt.id==var.AUDIT_TYPE}">${cnt.value}</c:if>
												</c:forEach>
											</td>
											<td <c:if test="${3==var.AUDIT_STATE}">style="background-color: red" </c:if>
												<c:if test="${1==var.AUDIT_STATE}">style="background-color: yellow" </c:if>
												<c:if test="${2==var.AUDIT_STATE}">style="background-color: green" </c:if>
											>
												<c:forEach items="${auditStateList}" var="cnt">
													<c:if test="${cnt.id==var.AUDIT_STATE}">${cnt.value}</c:if>
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
<script type="text/javascript">

	$(top.hangge());

	//检索
	function search(){
		top.jzts();
		$("#AUDIT_STATE_S").val($("#AUDIT_STATE").val());
		$("#Form").submit();
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
				look(rid);
			}
			</c:if>
		});


		$('#AUDIT_APPLY_TM_BEGIN').datepicker({
			todayBtn : "linked",
			autoclose : true,
			todayHighlight : true
			//endDate : new Date()
		}).on('changeDate',function(e){
			var startTime = e.date;
			$('#AUDIT_APPLY_TM_END').datepicker('setStartDate',startTime);
		});
		//结束时间：
		$('#AUDIT_APPLY_TM_END').datepicker({
			todayBtn : "linked",
			autoclose : true,
			todayHighlight : true
			//endDate : new Date()
		}).on('changeDate',function(e){
			var endTime = e.date;
			$('#AUDIT_APPLY_TM_BEGIN').datepicker('setEndDate',endTime);
		});

	});

	function look(Id){
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="查看";
		diag.URL = '<%=basePath%>box/goLook.do?BOX_ID='+Id;
		diag.Width = 1000;
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

</body>
</html>

