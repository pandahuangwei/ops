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
			<form action="stockmgrinsch/list.do" method="post" name="Form" id="Form">
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
							<%--<div style="overflow-y:auto; overflow-x:auto; ”>--%>
								<div class="content">

									<table id="table_prod_dtl" class="table table-striped table-condensed table-hover">

										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">入库单号</td>
											<td><input   id="INSTOCK_NO" type="text" name="INSTOCK_NO" value="${pd.INSTOCK_NO}"
													   placeholder=""/></td>
											<td style="width:110px;text-align: right;padding-top: 13px;">客户</td>
											<td><select  class="chzn-select" name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" data-placeholder=" ">
												<option value=""></option>
												<c:forEach items="${customerList}" var="cnt">
													<option value="${cnt.id }"
															<c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
											</td>
											<td style="width:110px;text-align: right;padding-top: 13px;">入库类型</td>
											<td >
												<input type="hidden" name="INSTOCK_TYPE_S" id="INSTOCK_TYPE_S" value="${pd.INSTOCK_TYPE_S}">
												<select multiple class="chzn-select"  name="INSTOCK_TYPE" id="INSTOCK_TYPE" style="vertical-align:top;"  data-placeholder="多选...">

													 <c:forEach items="${stockInTypeList}" var="cnt">
														<%--<option value="${cnt.id }"<c:if test="${cnt.id == pd.INSTOCK_TYPE }">selected</c:if>>${cnt.value }</option>--%>
														 <option value="${cnt.id }"
																 <c:if test="${fn:contains(pd.INSTOCK_TYPE_S,cnt.id) }">selected</c:if>
														 >${cnt.value }</option>
													</c:forEach>
												</select>
											</td>
											<td style="width:110px;text-align: right;padding-top: 13px;">收货状态</td>
											<td>
												<input type="hidden" name="INSTOCK_STATE_S" id="INSTOCK_STATE_S" value="${pd.INSTOCK_STATE_S}">
												<select multiple class="chzn-select"  name="INSTOCK_STATE" data-placeholder="多选..."  id="INSTOCK_STATE">
													<%--<option value="">全部</option>--%>
													<c:forEach items="${stockInStateList}" var="cnt">
														<%--<option value="${cnt.id }" <c:if test="${cnt.id == pd.INSTOCK_STATE }">selected</c:if>>${cnt.value }</option>--%>
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.INSTOCK_STATE_S,cnt.id) }">selected</c:if>  >${cnt.value }</option>

													</c:forEach>
												</select>
											</td>
										</tr>
										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">订单号</td>
											<td><input  id="ORDER_NO" type="text" name="ORDER_NO" value="${pd.ORDER_NO}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">货主</td>
											<td><select  class="chzn-select" name="DTL_CUSTOMER_ID" id="DTL_CUSTOMER_ID" style="vertical-align:top;" data-placeholder=" ">
												<option value=""></option>
												<c:forEach items="${customerList}" var="cnt">
													<option value="${cnt.id }"
															<c:if test="${cnt.id == pd.DTL_CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
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
												<input type="hidden" name="PUT_STATE_S" id="PUT_STATE_S"  value="${pd.PUT_STATE_S}">
												<select multiple class="chzn-select"  name="PUT_STATE" id="PUT_STATE" data-placeholder="多选..."  style="vertical-align:top;" >
													<%--<option value="">全部</option>--%>
													<c:forEach items="${putStateList}" var="cnt">
														<%--<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_STATE }">selected</c:if>>${cnt.value }</option>--%>
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.PUT_STATE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
													</c:forEach>
												</select>
											</td>


										</tr>
										<tr>
										<td style="width:110px;text-align: right;padding-top: 13px;">THI RefNo</td>
										<td><input   id="INBOUND_REFERENCE" type="text" name="INBOUND_REFERENCE" value="${pd.INBOUND_REFERENCE}" placeholder=""/></td>

										<td style="width:110px;text-align: right;padding-top: 13px;">HAWB/HBL</td>
										<td><input   id="HAWB_HBL" type="text" name="HAWB_HBL" value="${pd.HAWB_HBL}" placeholder=""/></td>


										<td style="width:110px;text-align: right;padding-top: 13px;">Shipper</td>
										<td><input   id="SHIPPER" type="text" name="SHIPPER" value="${pd.SHIPPER}" placeholder=""/></td>


										<td style="width:110px;text-align: right;padding-top: 13px;"># of package</td>
										<td><input   id="OF_PACKAGE" type="text" name="OF_PACKAGE" value="${pd.OF_PACKAGE}" placeholder=""/></td>
										</tr>
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">产品</td>
											<td  style="vertical-align:top;">
												<select  class="chzn-select"  name="PRODUCT_ID" id="PRODUCT_ID" style="vertical-align:top;" data-placeholder=" "
													><option value=""></option>
													<c:forEach items="${allProductList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											</td>
											<td style="width:110px;text-align: right;padding-top: 13px;">费用状态</td>
											<td>
												<select class="chzn-select" name="COST_STATE" id="COST_STATE" data-placeholder="请选择" >
													<option value="">全部</option>
													<option value="1">已完成</option>
													<option value="0">未完成</option>
												</select>
											</td>

											<td style="width:70px;text-align: right;padding-top: 13px;">预计入库日期</td>
											<td colspan="7">
												<%--<span class="input-icon input-icon-right">--%>
												<input class="date-picker" name="PRE_INSTOCK_DATE_BEGIN"
													   id="PRE_INSTOCK_DATE_BEGIN" value="${pd.PRE_INSTOCK_DATE_BEGIN}" type="text"
													   data-date-format="yyyy-mm-dd" readonly="readonly"
													   placeholder=""/>
												<%--	<i class="icon-time"></i>
				                                 </span>--%>
											到
											<input class="date-picker" name="PRE_INSTOCK_DATE_END" id="PRE_INSTOCK_DATE_END"
													   value="${pd.PRE_INSTOCK_DATE_END}" type="text"
													   data-date-format="yyyy-mm-dd" readonly="readonly"
													   placeholder=""/>
											</td>
											<%--<td style="vertical-align:top;">
												<button class="btn btn-primary btn-mini" onclick="search();" title="检索"
														data-rel="tooltip">
													<span class="icon-search"></span>查询</button>
											</td>

											<td style="vertical-align:top;">
												<c:if test="${QX.add == 1 }">
													<a class="btn btn-mini btn-success" onclick="add();"><spanclass="icon-plus"></span>新增</a>
												</c:if>
											</td>--%>

											<%--  <c:if test="${QX.cha == 1 }">
                                              <td style="vertical-align:top;">
                                                  <a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><span class="icon-download"></span>下载</a>
                                              </td>
                                              </c:if>--%>
										</tr>
									</table>


								</div>
							</div>
						</div>
						<div class="widget-toolbox padding-8 clearfix">
							<div class="btn-group">
								<button class="btn btn-primary btn-mini" onclick="search();" title="检索" data-rel="tooltip">
									<span class="icon-search"></span>查询</button>

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
						<th class="center">入货单编号</th>
						<%--<th class="center">客户</th>--%>
						<th class="center">THI RefNo</th>
						<th class="center">HAWB/HBL</th>
						<th class="center">Shipper</th>
						<th class="center"># of package</th>
						<th class="center">入库类型</th>
						<th class="center">预计入库日期</th>
						<th class="center">实际入库日期</th>
						<th class="center">收货状态</th>
						<th class="center">分配状态</th>
						<th class="center">码放状态</th>
						<%--<th class="center">总毛重</th>
						<th class="center">总净重</th>
						<th class="center">总体积</th>
						<th class="center">总价</th>--%>
						<th class="center">总期望EA数</th>
						<th class="center">优先级</th>
						<%--	<th class="center">订单号</th>--%>
						<%--	<th class="center">费用录入已完成</th>--%>
						<%--<th class="center">备注</th>--%>




						<th class="center">操作</th>
					</tr>
					</thead>

					<tbody>

					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty varList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${varList}" var="var" varStatus="vs">
									<tr id="${var.STOCKMGRIN_ID}">
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' <c:if test="${ 75 != var.INSTOCK_STATE }"> disabled="disabled" </c:if> value="${var.STOCKMGRIN_ID}" /><span class="lbl"></span></label>
										</td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${var.INSTOCK_NO}</td>
											<%--<td>
                                                <c:forEach items="${customerList}" var="cnt">
                                                    <c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
                                                </c:forEach>
                                            </td>--%>
										<td>${var.INBOUND_REFERENCE}</td>
										<td>${var.HAWB_HBL}</td>
										<td>${var.SHIPPER}</td>
										<td>${var.OF_PACKAGE}</td>


										<td>
											<c:forEach items="${stockInTypeList}" var="cnt">
												<c:if test="${cnt.id==var.INSTOCK_TYPE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
										<td>${var.PRE_INSTOCK_DATE}</td>
										<td>${var.REAL_INSTOCK_DATE}</td>
										<td <c:if test="${75==var.INSTOCK_STATE}">style="background-color: red" </c:if>
											<c:if test="${76==var.INSTOCK_STATE}">style="background-color: yellow" </c:if>
											<c:if test="${77==var.INSTOCK_STATE}">style="background-color: green" </c:if>>

											<c:forEach items="${stockInStateList}" var="cnt">
												<c:if test="${cnt.id==var.INSTOCK_STATE}">${cnt.value}</c:if>
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

										<td <c:if test="${80==var.PUT_STATE}">style="background-color: red" </c:if>
											<c:if test="${81==var.PUT_STATE}">style="background-color: yellow" </c:if>
											<c:if test="${82==var.PUT_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${putStateList}" var="cnt">
												<c:if test="${cnt.id==var.PUT_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
											<%--		<td>${var.TOTAL_WEIGHT}</td>
                                                    <td>${var.TOTAL_SUTTLE}</td>
                                                    <td>${var.TOTAL_VOLUME}</td>
                                                    <td>${var.TOTAL_PRICE}</td>--%>
										<td>${var.TOTAL_EA}</td>

										<td>
											<c:forEach items="${priorityLevelList}" var="cnt">
												<c:if test="${cnt.id==var.PRIORITY_LEVEL}">${cnt.value}</c:if>
											</c:forEach>
										</td>
											<%--<td>${var.ORDER_NO}</td>--%>
											<%--	<td align="center">
                                                    <input disabled="true"   type='checkbox' <c:if test="${var.COST_STATE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
                                                </td>--%>
											<%--<td>${var.MEMO}</td>--%>

										<td style="width: 30px;" class="center">
											<div class='hidden-phone visible-desktop btn-group'>

												<c:if test="${QX.edit != 1 && QX.del != 1 }">
													<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
												</c:if>
												<c:if test="${QX.edit == 1 }">
													<a title="编辑" onclick="edit('${var.STOCKMGRIN_ID}');" class='btn btn-mini btn-info' data-rel="tooltip" title="" data-placement="left"><i class="icon-edit"></i></a>
												</c:if>
												<c:if test="${ 75 == var.INSTOCK_STATE }">
													<c:if test="${QX.del == 1 }">
														<a title="删除" onclick="del('${var.STOCKMGRIN_ID}');" class='btn btn-mini btn-danger'  data-rel="tooltip" title="" data-placement="left"><i class="icon-trash"></i></a>
													</c:if>
												</c:if>
											</div>
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
					<%--<c:if test="${QX.add == 1 }">
                    <a class="btn btn-mini btn-success" onclick="add();"><span class="icon-plus"></span>新增</a>
					</c:if>
					<c:if test="${QX.del == 1 }">
                    <a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >
                            <span class="icon-trash"></span>删除
					</a>
					</c:if>--%>
				</td>
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
			//设值多选框 入库状态，码放状态、分配状态 INSTOCK_STATE_S PUT_STATE_S ASSIGNED_STATE_S
			var instockState = $("#INSTOCK_STATE").val();
			$("#INSTOCK_STATE_S").val(instockState);

			var putState = $("#PUT_STATE").val();
			$("#PUT_STATE_S").val(putState);

			var assignState = $("#ASSIGNED_STATE").val();
			$("#ASSIGNED_STATE_S").val(assignState);

			var instockType = $("#INSTOCK_TYPE").val();
			$("#INSTOCK_TYPE_S").val(instockType);


			$("#Form").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>stockmgrinsch/goAdd.do';
			 diag.Width = 1100;
			 diag.Height = 800;
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
					var url = "<%=basePath%>stockmgrinsch/delete.do?STOCKMGRIN_ID="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>stockmgrinsch/goEdit.do?STOCKMGRIN_ID='+Id;
			 diag.Width = 1100;
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
				<%--<c:if test="${QX.edit == 1 }">--%>
				<%--var rid = $(this).attr('id');--%>
				<%--edit(rid);--%>
				<%--</c:if>--%>

				<c:if test="${QX.edit == 1 }">
				var rid = $(this).attr('id');
				//alert(rid);
				if (rid == null || rid == "") {
					add();
				} else {
					edit(rid);
				}
				</c:if>
			});


			$('#PRE_INSTOCK_DATE_BEGIN').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var startTime = e.date;
				$('#PRE_INSTOCK_DATE_END').datepicker('setStartDate',startTime);
			});
			//结束时间：
			$('#PRE_INSTOCK_DATE_END').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var endTime = e.date;
				$('#PRE_INSTOCK_DATE_BEGIN').datepicker('setEndDate',endTime);
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
								url: '<%=basePath%>stockmgrinsch/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>stockmgrinsch/excel.do';
		}
		</script>
		
	</body>
</html>

