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

	//拣货 按入库单ID
	function manualPick(pid) {
		var rs1 = auditRepeatBox();
		if (rs1 == false) {
			return false;
		}

        var nopassCnt = 0;
		var str = '';
		for (var i = 0; i < document.getElementsByName('ids').length; i++) {
			var id2 = "ids"+i;
			var id = "idnos"+i;
			var boxid = "boxid"+i;
			var stock_no = $("#"+id).val();
			var box_no = $.trim($("#"+id2).val());

			var result = auditBoxNo(stock_no,box_no,i);
			if(result) {
				nopassCnt = nopassCnt+1;
			}
			var box_id =  $("#"+boxid).val();
			if (str == '') {
				str += box_id+"-"+box_no;
			} else {
				str += ',' + box_id+"-"+box_no;
			}

		}
		//console.log(str);
		if (nopassCnt != 0) {
			return;
		}

		if (str == '') {
			bootbox.dialog("请输入新的箱号!",
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

			return;
		} else {
			//该值从上一页面带入，如果上面的校验通过，则提交后台拣货
			var STOCKMGROUT_ID_CHOSE = $('#STOCKMGROUT_ID_CHOSE').val();
			var STOCKMGROUT_IDS = $('#STOCKMGROUT_IDS').val();
			var OPT_EVEN =  $('#OPT_EVEN').val();
			$('#Form').attr("action", "pickout/savePickBeakBox.do?OPT_EVEN="+OPT_EVEN+"&STOCKMGROUT_ID_CHOSE="+STOCKMGROUT_ID_CHOSE+"&NEW_BOX_LIST="+str+"&STOCKMGROUT_IDS="+STOCKMGROUT_IDS).submit();

			//$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}

	function auditRepeatBox() {
		var result = false;

		var str = '';
		for (var i = 0; i < document.getElementsByName('ids').length; i++) {
			var id2 = "ids"+i;
			var box_no = $.trim($("#"+id2).val());

			if (str == '') {
				str += box_no;
			} else {
				str += ',' +box_no;
			}

		}
		if (str == '') {
			bootbox.dialog("您没有输入箱号!",
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
			return false;
		}

		$.ajax({
			type: "POST",
			url: '<%=basePath%>pickout/hadRepeatBox.do',
			data: {NEW_BOX_LIST:str,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				if("error" == data.result){
					$("#TIPS").tips({
						side:3,
						msg:'箱号重复'+data.RS,
						bg:'#AE81FF',
						time:2
					});
					setTimeout($("#TIPS").val(''),2000);
				} else {
					result = true;
				}
			}
		});
		return result;

	}

	function auditBoxNo(outstockNo,box_No,index){
	/*	var a = document.getElementsByName('ids').length;
		console.log(1);
		console.log(obj);
		var arr = obj.split('-');
		var dex = arr[1];

		var id = "idnos"+dex;
		console.log(id);
		console.log($("#"+id).val());*/
		var result = false;
		var id = "ids"+index;
		var BOX_NO = $.trim(box_No);
		if (BOX_NO ==null || BOX_NO=='' ||BOX_NO.length <8) {
			$("#"+id).tips({
				side:3,
				msg:'请输入新的箱号',
				bg:'#AE81FF',
				time:2
			});
			result = true;
			return result;
		}

		var RECEIV_TYPE_5 =  outstockNo.substr(0,5);
		var BOX_NO_5 = BOX_NO.substr(0,5);
		if (RECEIV_TYPE_5 != BOX_NO_5 ) {
			$("#"+id).val('');
			$("#"+id).tips({
				side:3,
				msg:'不是'+RECEIV_TYPE_5+"的箱号.",
				bg:'#AE81FF',
				time:2
			});
			result = true;
			return result;
		}

		$.ajax({
			type: "POST",
			url: '<%=basePath%>receivopt/auditBoxNo.do',
			data: {INSTOCK_NO:outstockNo,BOX_NO:box_No,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				//如果不存在
				if("success" == data.result){
					result = true;
					$("#"+id).css('background-color','red');
					$("#"+id).tips({
						side:3,
						msg:''+data.auditRs,
						bg:'#AE81FF',
						time:2
					});
					setTimeout($("#"+id).val(''),3000);
				} else {
					$("#"+id).css('background-color','green');
				}
			}
		});
		return result;
	}


</script>

	<form action="pickout/goPickManual.do" name="Form" id="Form" method="post">
		<%--拣货明细-出库单明细ID--%>
		<input type="hidden" name="STOCKMGROUT_ID_CHOSE" id="STOCKMGROUT_ID_CHOSE" value="${pd.STOCKMGROUT_ID_CHOSE}"/>
		<%--出库单ID--%>
		<input type="hidden" name="STOCKMGROUT_IDS" id="STOCKMGROUT_IDS" value="${pd.STOCKMGROUT_IDS}"/>

		<input type="hidden" name="ASSIGN_TYPE" id="ASSIGN_TYPE" value="${pd.ASSIGN_TYPE}"/>
		<input type="hidden" name="ASSIGN_NAME" id="ASSIGN_NAME" value="${pd.ASSIGN_NAME}"/>
		<input type="hidden" name="OPT_EVEN" id="OPT_EVEN" value="${pd.OPT_EVEN}"/>

		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong>产品拣货信息</strong>
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
								<a class="btn btn-mini btn-primary" id="TIPS" onclick="manualPick(2);"><span class="icon-ok"></span>拆箱拣货</a>
							</div>

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
									<thead>
									<tr>
										<th class="center">出库单</th>
										<th class="center">产品</th>
										<th class="center">CaseNumber</th>
										<th class="center">拆分箱号</th>
										<th class="center">CS EA</th>
										<th class="center">分配EA</th>
									</tr>
									</thead>
									<tbody>

									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														<td>${var.OUTSTOCK_NO}</td>
														<td>
															<c:forEach items="${allProductList}" var="cnt">
																<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<td>${var.BOX_NO}</td>
														<td><input type='text' id="ids${vs.index}" name='ids'/>
															<input type='hidden' id="idnos${vs.index}" name='idnos' value="${var.OUTSTOCK_NO}"/>
															<input type='hidden' id="boxid${vs.index}" name='boxids' value="${var.BOX_ID}"/>
														</td>
														<td style="text-align: right;background-color: pink">${var.EA_NUM}</td>
														<td style="text-align: right;background-color: pink">${var.ASSIGNED_STOCK_NUM}</td>
													</tr>

												</c:forEach>
											</c:if>
										</c:when>
									</c:choose>
									</tbody>
								</table>

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

			$("input").keypress(function (e) {
				var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
				if (keyCode == 13) {
					//console.info(this.id);
					var id = this.id;
					var index = id.replace("ids","")*1+1;
					var nextId = "#ids"+index;
					var vl = $("#ids"+index).val();
					if (vl != null || vl !="") {
						$("#ids"+index).focus().val(vl);
					} else {
						$("#ids"+index).focus();
					}

					return false;
				}else
					return true;
			});

		});
		
		</script>
</body>
</html>