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
			<form action="depotout/list2.do" method="post" name="Form" id="Form">
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

											<td style="width:110px;text-align: right;padding-top: 13px;">配载单号</td>
											<td><input  id="CARGO_NO" type="text" name="CARGO_NO" value="${pd.CARGO_NO}" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">发货单号</td>
											<td><input  id="OUTSTOCK_NO" type="text" name="OUTSTOCK_NO" value="${pd.OUTSTOCK_NO}"  placeholder=""/></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">配载时间</td>
											<td><input  id="CARGO_TM" type="text" name="CARGO_TM" value="${pd.CARGO_TM}"   class="date-picker"   data-date-format="yyyy-mm-dd" readonly="readonly"
														placeholder="" /></td>

											<td style="width:110px;text-align: right;padding-top: 13px;">发货时间</td>
											<td><input  id="DEPOT_TM" type="text" name="DEPOT_TM" value="${pd.DEPOT_TM}"   class="date-picker"   data-date-format="yyyy-mm-dd" readonly="readonly"
														placeholder=""/></td>
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

											<td style="width:110px;text-align: right;padding-top: 13px;">产品</td>
											<td  style="vertical-align:top;">
												<select  class="chzn-select"  name="PRODUCT_ID" id="PRODUCT_ID" style="vertical-align:top;" data-placeholder=" ">
													<option value=""></option>
													<c:forEach items="${allProductList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
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

										</tr>

										<tr>

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
											<td style="width:110px;text-align: right;padding-top: 13px;"></td>
											<td></td>
											<td style="width:110px;text-align: right;padding-top: 13px;"></td>
											<td></td>
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
										<a class="btn btn-mini btn-info"  onclick="manualDepot(1);"><span class="icon-ok"></span>配载发货</a>
									</c:if>
								<c:if test="${QX.add == 1 }">
									<a class="btn btn-mini btn-warning" onclick="cancelDepot(4);"><span class="icon-undo"></span>取消发货</a>
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
						<th class="center">配载单号</th>
						<th class="center">配载状态</th>
						<th class="center">配载时间</th>
						<th class="center">发货时间</th>
						<th class="center">提单号</th>
						<th class="center">起运港</th>
						<th class="center">目的港</th>
						<th class="center">出货单</th>
					</tr>
					</thead>

					<tbody>

					<!-- 开始循环 -->
					<c:choose>
						<c:when test="${not empty varList}">
							<c:if test="${QX.cha == 1 }">
								<c:forEach items="${varList}" var="var" varStatus="vs">
									<tr id="${var.CARGOOUT_ID}">
										<td class='center' style="width: 30px;">
											<label><input type='checkbox' name='ids' value="${var.CARGOOUT_ID}" /><span class="lbl"></span></label>
										</td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${var.CARGO_NO}</td>
										<td>${var.CARGO_STATE}</td>
										<td>${var.CARGO_TM}</td>
										<td>${var.DEPOT_TM}</td>
										<td>${var.LADING_NO}</td>
										<td>${var.SRC_PORT}</td>
										<td>${var.DEST_PORT}</td>
										<td>${var.OUTSTOCK_NO}</td>

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
		</form>

	</div>
  </div>
</div>
</div>
</div>

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


			$("#Form").submit();
		}

		//去手工分配分配页面
		function manualDepot(opttype) {
			var title1 = "发货";
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
				top.jzts();
				var diag = new top.Dialog();
				diag.Drag = true;
				diag.Title = title1;
				diag.URL = '<%=basePath%>depotout/goDepotCargoDepot.do?DEPOT_TYPE=' + opttype +"&CARGOOUT_ID="+str;
				diag.Width = 1100;
				diag.Height = 800;
				diag.CancelEvent = function () { //关闭事件
					$("#Form").submit();
					diag.close();
				};
				diag.show();
			}
		}


		//去取消拣货页面
		function cancelDepot(opttype) {
			var title1 = "取消拣货";
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
				top.jzts();
				var diag = new top.Dialog();
				diag.Drag = true;
				diag.Title = title1;
				diag.URL = '<%=basePath%>depotout/goDepotCargoCacel.do?DEPOT_TYPE=' + opttype +"&CARGOOUT_ID="+str;
				diag.Width = 1000;
				diag.Height = 800;
				diag.CancelEvent = function () { //关闭事件
					$("#Form").submit();
					diag.close();
				};
				diag.show();
			}
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
				if (rid == null || rid == "") {
					//add();
				} else {
					look(rid);
				}

				</c:if>
			});




		});


		//查看明细
		function look(Id){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag=true;
			diag.Title ="查看配载明细";
			diag.URL = '<%=basePath%>depotout/goDepotCargoLook.do?CARGOOUT_ID='+Id;
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

