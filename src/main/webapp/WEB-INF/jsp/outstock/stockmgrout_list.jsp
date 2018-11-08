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
    function getProductByCustom(){
        var CUSTOMERID = $.trim($("#CUSTOMER_ID").val());
        if (CUSTOMERID ==null || CUSTOMERID=='') {
            return false;
        }
        //动态删除select中的所有options
        //alert(document.getElementById("PROVINCE_ID").options.length);
        document.getElementById("PRODUCT_ID").options.length=0;
        $.ajax({
            type: "POST",
            url: '<%=basePath%>product/getProductByCustom.do',
            data: {CUSTOMER_ID:CUSTOMERID,tm:new Date().getTime()},
            dataType:'json',
            cache: false,
            success: function(data){
                $("#PRODUCT_ID_chzn").remove();
                $("#PRODUCT_ID").append($("<option value=''></option>"));
                for (var k in data) {
                    $("#PRODUCT_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                $("#PRODUCT_ID").attr("class","chzn-select");
                $(".chzn-select").chosen({search_contains: true});
            }
        });
    }
</script>
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="stockmgrout/list.do" method="post" name="Form" id="Form">


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
											<td style="width:110px;text-align: right;padding-top: 13px;">发货单号</td>
											<td><input   id="OUTSTOCK_NO" type="text" name="OUTSTOCK_NO" value="${pd.OUTSTOCK_NO}"
													   placeholder=""/></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">订单号</td>
											<td><input  id="ORDER_NO" type="text" name="ORDER_NO" value="${pd.ORDER_NO}" /></td>

											<%--<td style="width:110px;text-align: right;padding-top: 13px;">装车状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="LOADED_TYPE_S" id="LOADED_TYPE_S"  value="${pd.LOADED_TYPE_S}">
												<select  multiple class="chzn-select"  name="LOADED_TYPE" id="LOADED_TYPE" style="vertical-align:top;" data-placeholder="多选...">
													<c:forEach items="${loadTypeList}" var="cnt">
														<option value="${cnt.id }" <c:if test="${fn:contains(pd.LOADED_TYPE_S,cnt.id) }">selected</c:if> >${cnt.value }</option>
													</c:forEach>
												</select>
											</td>--%>

											<td style="width:110px;text-align: right;padding-top: 13px;">分配状态</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="ASSIGNED_STATE_S" id="ASSIGNED_STATE_S"  value="${pd.ASSIGNED_STATE_S}">
												<select  multiple class="chzn-select" name="ASSIGNED_STATE" id="ASSIGNED_STATE" style="vertical-align:top;" data-placeholder="多选...">
													<c:forEach items="${assignedList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${fn:contains(pd.ASSIGNED_STATE_S,cnt.id) }">selected</c:if>
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

										</tr>


										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">客户</td>
											<td><select  class="chzn-select" onChange="getProductByCustom()" name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" data-placeholder=" ">
												<option value=""></option>
													<c:forEach items="${customerList}" var="cnt">
													<option value="${cnt.id }"
															<c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;">发货方式</td>
											<td style="vertical-align:top;">
												<input type="hidden" name="OUTSTOCK_WAY_S" id="OUTSTOCK_WAY_S"  value="${pd.OUTSTOCK_WAY_S}">
												<select  multiple class="chzn-select" name="OUTSTOCK_WAY" id="OUTSTOCK_WAY" style="vertical-align:top;" data-placeholder="多选...">
													<c:forEach items="${outStockWayList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${fn:contains(pd.OUTSTOCK_WAY_S,cnt.id) }">selected</c:if>
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
										</tr>

										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">产品</td>
											<td  style="vertical-align:top;">
												<select  class="chzn-select"  name="PRODUCT_ID" id="PRODUCT_ID" style="vertical-align:top;" data-placeholder=" ">
													<option value=""></option>
													<c:forEach items="${productList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>


											<td style="width:110px;text-align: right;padding-top: 13px;">SI Reference</td>
											<td><input  id="SI_REFERENCE" type="text" name="SI_REFERENCE" value="${pd.SI_REFERENCE}"/>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;">发货类型</td>
											<td >
												<input type="hidden" name="OUTSTOCK_TYPE_S" id="OUTSTOCK_TYPE_S"  value="${pd.OUTSTOCK_TYPE_S}">
												<select  multiple class="chzn-select"  name="OUTSTOCK_TYPE" id="OUTSTOCK_TYPE" style="vertical-align:top;"  data-placeholder="多选...">

													 <c:forEach items="${outStockTypeList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${fn:contains(pd.OUTSTOCK_TYPE_S,cnt.id) }">selected</c:if>
														>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:110px;text-align: right;padding-top: 13px;"></td>
											<td></td>
										</tr>

										<tr>
											<td style="width:110px;text-align: right;padding-top: 13px;">预计出库日期</td>
											<td colspan="3">
												<input class="date-picker" name="PRE_OUTSTOCK_DATE_BEGIN"
													   id="PRE_OUTSTOCK_DATE_BEGIN" value="${pd.PRE_OUTSTOCK_DATE_BEGIN}" type="text"
													   data-date-format="yyyy-mm-dd" readonly="readonly"
													   placeholder=""/>
											到
											<input class="date-picker" name="PRE_OUTSTOCK_DATE_END" id="PRE_OUTSTOCK_DATE_END"
													   value="${pd.PRE_OUTSTOCK_DATE_END}" type="text"
													   data-date-format="yyyy-mm-dd" readonly="readonly"
													   placeholder=""/>

											</td>
											<td style="width:110px;text-align: right;padding-top: 13px;">实际发货时间</td>
											<td colspan="3">
												<input class="date-picker" name="ACT_OUTSTOCK_DATE_BEGIN"
													   id="ACT_OUTSTOCK_DATE_BEGIN" value="${pd.ACT_OUTSTOCK_DATE_BEGIN}" type="text"
													   data-date-format="yyyy-mm-dd" readonly="readonly"
													   placeholder=""/>
												到
												<input class="date-picker" name="ACT_OUTSTOCK_DATE_END" id="ACT_OUTSTOCK_DATE_END"
													   value="${pd.ACT_OUTSTOCK_DATE_END}" type="text"
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
								<button class="btn btn-primary btn-mini" onclick="search();" title="检索" data-rel="tooltip">
									<span class="icon-search"></span>查询</button>


									<c:if test="${QX.add == 1 }">
										<a class="btn btn-mini btn-success" onclick="add();"><span class="icon-plus"></span>新增</a>
									</c:if>
								<c:if test="${QX.del == 1 }">
									<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >
										<span class="icon-trash"></span>删除
									</a>
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
						<th class="center">发货单号</th>
						<th class="center">客户</th>
						<th class="center">SI Reference</th>
						<th class="center">预计出库日期</th>
						<th class="center">分配状态</th>
						<th class="center">拣货状态</th>
						<th class="center">配载状态</th>
						<th class="center">发货状态</th>
						<th class="center">发货类型</th>
					<%--	<th class="center">装车状态</th>--%>
				<%--		<th class="center">总价</th>
						<th class="center">总期望EA数</th>
						<th class="center">优先级</th>--%>
						<%--<th class="center">订单号</th>--%>
						<%--<th class="center">费用</th>--%>
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
							<tr id='${var.STOCKMGROUT_ID}'>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.STOCKMGROUT_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${var.OUTSTOCK_NO}</td>
										<td>
											<c:forEach items="${customerList}" var="cnt">
												<c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
											</c:forEach>
										</td>

								      <td>${var.SI_REFERENCE}</td>
										<td>${var.PRE_OUT_DATE}</td>
									   <%--分配状态--%>
										<td  <c:if test="${100==var.ASSIGNED_STATE}">style="background-color: red" </c:if>
											 <c:if test="${101==var.ASSIGNED_STATE}">style="background-color: yellow" </c:if>
											 <c:if test="${102==var.ASSIGNED_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${assignedList}" var="cnt">
												<c:if test="${cnt.id==var.ASSIGNED_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
									   <%--拣货状态--%>
										<td <c:if test="${139==var.PICK_STATE}">style="background-color: red" </c:if>
											<c:if test="${140==var.PICK_STATE}">style="background-color: yellow" </c:if>
											<c:if test="${141==var.PICK_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${pickStateList}" var="cnt">
												<c:if test="${cnt.id==var.PICK_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
									<%--配载状态--%>
										<td <c:if test="${161==var.CARGO_STATE}">style="background-color: red" </c:if>
											<c:if test="${162==var.CARGO_STATE}">style="background-color: yellow" </c:if>
											<c:if test="${163==var.CARGO_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${cargoStateList}" var="cnt">
												<c:if test="${cnt.id==var.CARGO_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
									   <%--发货状态--%>
										<td  <c:if test="${142==var.DEPOT_STATE}">style="background-color: red" </c:if>
											 <c:if test="${143==var.DEPOT_STATE}">style="background-color: yellow" </c:if>
											 <c:if test="${144==var.DEPOT_STATE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${depotTypeList}" var="cnt">
												<c:if test="${cnt.id==var.DEPOT_STATE}">${cnt.value}</c:if>
											</c:forEach>
										</td>
									   <%--发货类型--%>
										  <td>
											<c:forEach items="${outStockWayList}" var="cnt">
												<c:if test="${cnt.id==var.OUTSTOCK_WAY}">${cnt.value}</c:if>
											</c:forEach>
										</td>

									   <%--装车状态--%>
										<%--<td <c:if test="${133==var.LOADED_TYPE}">style="background-color: red" </c:if>
											<c:if test="${134==var.LOADED_TYPE}">style="background-color: yellow" </c:if>
											<c:if test="${135==var.LOADED_TYPE}">style="background-color: green" </c:if>
										>
											<c:forEach items="${loadTypeList}" var="cnt">
												<c:if test="${cnt.id==var.LOADED_TYPE}">${cnt.value}</c:if>
											</c:forEach>
										</td>--%>
										<%--<td>${var.TOTAL_WEIGHT}</td>
										<td>${var.TOTAL_SUTTLE}</td>
										<td>${var.TOTAL_VOLUME}</td>
										<td>${var.TOTAL_PRICE}</td>
										<td>${var.TOTAL_EA}</td>
										<td>${var.PRIORITY_LEVEL}</td>--%>
									<%--	<td>${var.ORDER_NO}</td>--%>
										<%--<td align="center">
											<input disabled="true"   type='checkbox' <c:if test="${var.COST_STATE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>--%>
										<%--<td>${var.MEMO}</td>--%>

                                <td style="width: 30px;" class="center">
                                    <div class='hidden-phone visible-desktop btn-group'>

                                        <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                            <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
                                        </c:if>
                                             <c:if test="${QX.edit == 1 }">
                                                    <a title="编辑" onclick="edit('${var.STOCKMGROUT_ID}');" class='btn btn-mini btn-info' data-rel="tooltip" title="" data-placement="left"><i class="icon-edit"></i></a>
                                                </c:if>
                                                <c:if test="${QX.del == 1 }">
                                                    <a title="删除" onclick="del('${var.STOCKMGROUT_ID}');" class='btn btn-mini btn-danger'  data-rel="tooltip" title="" data-placement="left"><i class="icon-trash"></i></a>
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
      //LOADED_TYPE_S  ASSIGNED_STATE_S DEPOT_STATE_S  PICK_STATE_S  OUTSTOCK_TYPE_S OUTSTOCK_WAY_S

			$("#LOADED_TYPE_S").val($("#LOADED_TYPE").val());
			$("#ASSIGNED_STATE_S").val($("#ASSIGNED_STATE").val());
			$("#DEPOT_STATE_S").val($("#DEPOT_STATE").val());
			$("#PICK_STATE_S").val($("#PICK_STATE").val());
			$("#OUTSTOCK_TYPE_S").val($("#OUTSTOCK_TYPE").val());
			$("#OUTSTOCK_WAY_S").val($("#OUTSTOCK_WAY").val());
			$("#CARGO_STATE_S").val($("#CARGO_STATE").val());


			$("#Form").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>stockmgrout/goAdd.do';
			 diag.Width = 1200;
			 diag.Height = 800;
			 diag.CancelEvent = function(){ //关闭事件
				/* if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				}*/
				 $("#Form").submit();
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>stockmgrout/delete.do?STOCKMGROUT_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			var MODIFY_FLG = $("#MODIFY_FLG").val();

			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>stockmgrout/goEdit.do?STOCKMGROUT_ID='+Id+"&MODIFY_FLG="+MODIFY_FLG;
			 diag.Width = 1200;
			 diag.Height = 800;
			 diag.CancelEvent = function(){ //关闭事件
				/* if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${page.currentPage});
				}*/
				 $("#Form").submit();
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


			$('#PRE_OUTSTOCK_DATE_BEGIN').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var startTime = e.date;
				$('#PRE_OUTSTOCK_DATE_END').datepicker('setStartDate',startTime);
			});
			//结束时间：
			$('#PRE_OUTSTOCK_DATE_END').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var endTime = e.date;
				$('#PRE_OUTSTOCK_DATE_BEGIN').datepicker('setEndDate',endTime);
			});

			$('#ACT_OUTSTOCK_DATE_BEGIN').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var startTime = e.date;
				$('#ACT_OUTSTOCK_DATE_END').datepicker('setStartDate',startTime);
			});
			//结束时间：
			$('#ACT_OUTSTOCK_DATE_END').datepicker({
				todayBtn : "linked",
				autoclose : true,
				todayHighlight : true
				//endDate : new Date()
			}).on('changeDate',function(e){
				var endTime = e.date;
				$('#ACT_OUTSTOCK_DATE_BEGIN').datepicker('setEndDate',endTime);
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
								url: '<%=basePath%>stockmgrout/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>stockmgrout/excel.do';
		}
		</script>
		
	</body>
</html>

