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
	
	//保存
	function saveManual(pid){
		var str = '';
		for (var i = 0; i < document.getElementsByName('ids').length; i++) {
			if (document.getElementsByName('ids')[i].checked) {
				if (str == '') str += document.getElementsByName('ids')[i].value + '-'+document.getElementsByName('assignEa')[i].value;
				else str += ',' + document.getElementsByName('ids')[i].value + '-'+document.getElementsByName('assignEa')[i].value;
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
			var STOCKDTLMGROUT_IDS = $("#STOCKDTLMGROUT_IDS").val();
			$('#Form').attr("action", "assignout/saveManual.do?OPT_EVEN="+pid+"&STOCKDTLMGROUT_IDS="+STOCKDTLMGROUT_IDS+"&STOCKDTLMGROUT_ID_CHOSE="+str).submit();

			//$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

	}
	   function auditQty1(value) {
		   var arr = value.split('-');
		   var inputea = arr[0];
		   var srcea = arr[1];
		   var index=arr[2];
		   //assignEa
		   if (inputea.search("^-?\\d+$") != 0) {
			   $("#assignEa"+index).tips({
				   side:3,
				   msg:'请输入数字',
				   bg:'#AE81FF',
				   time:2
			   });
			   setTimeout($("#assignEa"+index).val(''),2000);
			   return false;
		   }

		   if (inputea==null || inputea=='') {
			   inputea = 0;
		   }


		  // alert(srcea); alert(inputea);
		   if(inputea-srcea > 0) {
			   //alert(2);
			   $("#assignEa"+index).tips({
				   side:3,
				   msg:'只能是范围在1到'+srcea+'之间的整数',
				   bg:'#AE81FF',
				   time:2
			   });
			   setTimeout($("#assignEa"+index).val(''),2000);
			   return false;
		   }

	   }

	function isInteger(obj) {
		return obj%1 === 0
	}

	function auditQty2(value) {
		auditQty1(value);

		var arr = value.split('-');
		var inputea = arr[0];
		var srcea = arr[1];
		var index=arr[2];

		var SeparateQty =$("#SeparateQty"+index).val();
		var divSpq = inputea/SeparateQty;
		//console.info(divSpq);
		//var re = /^-?\\d+$/;

		if (!isInteger(divSpq)) {
			$("#assignEa"+index).tips({
				side:3,
				msg:'分配数与SeparateQty不能整除',
				bg:'#AE81FF',
				time:2
			});
			setTimeout("",2000);
		}

		//console.info(index);
		if (document.getElementsByName('ids')[index].checked) {
			auditChoseQty();
		}

	}

	//检索
	function search(){
		top.jzts();
		$("#Form").submit();
	}
</script>

	<form action="assignout/goManualBox.do" name="Form" id="Form" method="post">

		<input type="hidden" name="STOCKDTLMGROUT_IDS" id="STOCKDTLMGROUT_IDS" value="${pd.STOCKDTLMGROUT_IDS}"/>
		<input type="hidden" name="STOCKMGROUT_IDS_CHOSE" id="STOCKMGROUT_IDS_CHOSE" value="${pd.STOCKMGROUT_IDS_CHOSE}"/>

		<input type="hidden" name="ASSIGN_TYPE" id="ASSIGN_TYPE" value="${pd.ASSIGN_TYPE}"/>
		<input type="hidden" name="ASSIGN_NAME" id="ASSIGN_NAME" value="${pd.ASSIGN_NAME}"/>
		<input type="hidden" name="PRODIDS_QTY" id="PRODIDS_QTY" value="${pd.PRODIDS_QTY}"/>


		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong>可分配箱号信息</strong>
					</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-main padding-3">
						<div class="slim-scroll">
							<div class="content">
								<table id="table_prod" class="table table-striped table-bordered table-hover">

									<thead>
									<tr>
										<th class="center">行号</th>
										<th class="center">出库单</th>
										<th class="center">产品</th>
										<%--<th class="center">分配状态</th>
										<th class="center">拣货状态</th>--%>
										<th class="center">期望EA</th>
										<th class="center">已分配EA</th>
										<th class="center">可分配EA</th>
										<%--<th class="center">已勾选EA</th>--%>
									</tr>
									</thead>

									<tbody>

									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty srcList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${srcList}" var="var" varStatus="vs">
													<tr>
														<td>${var.ROW_NO}</td>
														<td name='outstocknos'>${var.OUTSTOCK_NO}</td>

														<td>
															<c:forEach items="${allProductList}" var="cnt">
																<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
															</c:forEach>
														</td>

															<%--分配状态--%>
														<%--<td <c:if test="${100==var.ASSIGNED_STATE}">style="background-color: red" </c:if>
															<c:if test="${101==var.ASSIGNED_STATE}">style="background-color: yellow" </c:if>
															<c:if test="${102==var.ASSIGNED_STATE}">style="background-color: green" </c:if>>
															<c:forEach items="${assignedList}" var="cnt">
																<c:if test="${cnt.id==var.ASSIGNED_STATE}">${cnt.value}</c:if>
															</c:forEach>
														</td>--%>

															<%--拣货状态--%>
														<%--<td <c:if test="${139==var.PICK_STATE}">style="background-color: red" </c:if>
															<c:if test="${140==var.PICK_STATE}">style="background-color: yellow" </c:if>
															<c:if test="${141==var.PICK_STATE}">style="background-color: green" </c:if>>
															<c:forEach items="${pickStateList}" var="cnt">
																<c:if test="${cnt.id==var.PICK_STATE}">${cnt.value}</c:if>
															</c:forEach>
														</td>--%>


														<td style="text-align: right">${var.EA_EA}</td>
														<td style="text-align: right">${var.ASSIGNED_EA}</td>
														<td style="text-align: right">${var.CAN_ASSIGN_EA}</td>
														<%--<td>
															<input type='number' min="1" max="${var.CAN_ASSIGN_EA}"  id="assignEa${vs.index}" value="${var.CAN_ASSIGN_EA}"
																   name='assignEa' onkeyup="auditQty1(this.value+'-'+'${var.CAN_ASSIGN_EA}'+'-'+'${vs.index}')"
																   readOnly="readOnly" style="background-color: lightgreen;" class="input-small"/>
														</td>--%>


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

								<%--<div class="page-header position-relative">
									<table style="width:100%;">
										<tr>
											<td style="vertical-align:top;">
											</td>
											<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>--%>
							</div>
							</div>
						</div>
					<div class="widget-toolbox">
						<div class="btn-toolbar">
							<div class="btn-group">
								<a class="btn btn-mini btn-primary" onclick="saveManual(2);"><span class="icon-eye-open"></span>手工分配</a>
							</div>
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							<div class="btn-group">
								<button class="btn btn-success btn-mini" onclick="search();" title="检索" data-rel="tooltip">
									<span class="icon-search"></span>查询</button>
							</div>
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							<div class="btn-group">
								<button class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</button>
							</div>

						</div>

					</div>


				</div>
					<div class="widget-main padding-3">
						<div>  <%--class="slim-scroll" data-height="125"--%>
							<div class="content">
								<table id="table_search" class="table table-striped table-condensed table-hover">
									<tr>
										<td style="width:110px;text-align: right;padding-top: 13px;">外/内箱号</td>
										<td><input   id="BIG_BOX_NO" type="text" name="BIG_BOX_NO" value="${pd.BIG_BOX_NO}"
													 placeholder=""/></td>

										<td style="width:110px;text-align: right;padding-top: 13px;">DateCode</td>
										<td><input   id="DATE_CODE" type="text" name="DATE_CODE" value="${pd.DATE_CODE}"
													 placeholder=""/></td>

										<td style="width:110px;text-align: right;padding-top: 13px;">LotCode</td>
										<td><input  id="LOT_CODE" type="text" name="LOT_CODE" value="${pd.LOT_CODE}" /></td>

										<td style="width:110px;text-align: right;padding-top: 13px;">Bin</td>
										<td><input  id="BIN_CODE" type="text" name="BIN_CODE" value="${pd.BIN_CODE}" /></td>
									</tr>
									<tr>
										<td style="width:110px;text-align: right;padding-top: 13px;">COO</td>
										<td><input   id="COO" type="text" name="COO" value="${pd.COO}"/></td>

										<td style="width:110px;text-align: right;padding-top: 13px;">Remark1</td>
										<td><input   id="REMARK1" type="text" name="REMARK1" value="${pd.REMARK1}"/></td>

										<td style="width:110px;text-align: right;padding-top: 13px;">Remark2</td>
										<td><input  id="REMARK2" type="text" name="REMARK2" value="${pd.REMARK2}" /></td>

										<td style="width:110px;text-align: right;padding-top: 13px;">Remark3</td>
										<td><input  id="REMARK3" type="text" name="REMARK3" value="${pd.REMARK3}" /></td>
									</tr>
									<tr>
										<td style="width:110px;text-align: right;padding-top: 13px;">排序</td>
										<td colspan="3"  style="vertical-align:top;">
											<select class="chzn-select"  name="SORT_CLOUM" id="SORT_CLOUM"
													style="vertical-align:top;" data-placeholder=" " >
												<option value=""></option>
												<option value="BIG_BOX_NO" <c:if test="${'BIG_BOX_NO' eq pd.SORT_CLOUM }">selected</c:if>>外箱号</option>
												<option value="BOX_NO" <c:if test="${'BOX_NO' eq pd.SORT_CLOUM }">selected</c:if>>箱号</option>
												<option value="RECEIPT_TM" <c:if test="${'RECEIPT_TM' eq pd.SORT_CLOUM }">selected</c:if>>收货时间</option>
												</select>

											<select class="chzn-select"  name="SORT_BY" id="SORT_BY"
													style="vertical-align:top;" data-placeholder=" "  data-values="${pd.SORT_CLOUM}">
												<option value=""></option>
												<option value="ASC" <c:if test="${'ASC' eq pd.SORT_BY }">selected</c:if>>升序</option>
												<option value="DESC" <c:if test="${'DESC' eq pd.SORT_BY }">selected</c:if>>降序</option>
											</select>
										</td>
										<td style="width:120px;text-align: right;padding-top: 13px;">Remark1/2/3</td>
										<td colspan="3"  style="vertical-align:top;">
											<input  class="input-small" type='checkbox' id="REMARK1_EMP"  name='REMARK1_EMP' value="1"   onclick="setRemark1(this);"
											<c:if test="${pd.REMARK1_EMP eq 1 }">checked="checked" </c:if> title="查Remark1为空的值"/><span class="lbl"></span>
											<input  class="input-small" type='checkbox' id="REMARK2_EMP"  name='REMARK2_EMP' value="1"   onclick="setRemark2(this);"
													<c:if test="${pd.REMARK2_EMP eq 1 }">checked="checked" </c:if> title="查Remark2为空的值"/><span class="lbl"></span>
											<input  class="input-small" type='checkbox' id="REMARK3_EMP"  name='REMARK3_EMP' value="1"   onclick="setRemark3(this);"
													<c:if test="${pd.REMARK3_EMP eq 1 }">checked="checked" </c:if> title="查Remark3为空的值"/><span class="lbl"></span>
									</tr>
								</table>
							</div>

							<div class="content">
								<table id="table_prod_dtl" class="table table-striped table-bordered table-hover">

									<thead>
									<tr>
										<%--<th class="center">
                                             <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                                         </th>--%>
										<th class="center">
											<label></label>
										</th>
										<th class="center">行号</th>
										<th class="center">外箱号</th>
										<th class="center">内箱号</th>
										<th class="center">产品</th>
										<th class="center">分配EA</th>
										<th class="center">可分配EA</th>
										<th class="center">存储库位</th>
										<th class="center">入库时间</th>
										<th class="center">DateCode</th>
										<th class="center">LotCode</th>
										<th class="center">SeparateQty</th>
										<th class="center">COO</th>
										<th class="center">BIN</th>
										<th class="center">Remark1</th>
										<th class="center">Remark2</th>
										<th class="center">Remark3</th>
									</tr>
									</thead>

									<tbody>

									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>

														<td class='center' style="width: 30px;">
															<label><input type='checkbox' name='ids' value="${var.BOXS_ID}" /><span class="lbl"></span></label>
														</td>

														<td>${var.ROW_NO}</td>
														<td>${var.BIG_BOX_NO}</td>
														<td>${var.BOX_NO}</td>
														<td>
															<c:forEach items="${allProductList}" var="cnt">
																<c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
															</c:forEach>
															<input type='hidden' id="prodids${vs.index}" name='prodids' value="${var.PRODUCT_ID}"/>
														</td>

													<%--	<td  style="text-align: right">${var.ASSIGNED_STOCK_NUM}</td>--%>

														<td>
														<input class="input-small" type='number' min="1" max="${var.CAN_ASSIGN_EA}" id="assignEa${vs.index}" value="${var.CAN_ASSIGN_EA}"
															   name='assignEa' onkeyup="auditQty1(this.value+'-'+'${var.CAN_ASSIGN_EA}'+'-'+'${vs.index}')"
														onblur="auditQty2(this.value+'-'+'${var.CAN_ASSIGN_EA}'+'-'+'${vs.index}')"
														/>

														<input type='hidden' id="srcEa${vs.index}" name='srcEa' value="${var.CAN_ASSIGN_EA}"/>
														</td>


														<td  style="text-align: right;background-color: lightgreen">${var.CAN_ASSIGN_EA}</td>
														<%--存货区--%>
														<td>
															<c:forEach items="${locatorForShow}" var="cnt">
																<c:if test="${cnt.id==var.PUT_LOCATOR}">${cnt.value}</c:if>
															</c:forEach>
														</td>
														<td>${var.RECEIPT_TM}</td>
														<td>${var.DATE_CODE}</td>
														<td>${var.LOT_NO}</td>
														<td style="text-align: right">${var.SEPARATE_QTY}</td>
														<input type='hidden' id="SeparateQty${vs.index}" name='SeparateQty' value="${var.SEPARATE_QTY}"/>
														<td>${var.COO}</td>
														<td>${var.BIN_CODE}</td>
														<td>${var.REMARK1}</td>
														<td>${var.REMARK2}</td>
														<td>${var.REMARK3}</td>
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
			/*$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
						.each(function(){
							this.checked = that.checked;
							$(this).closest('tr').toggleClass('selected');
						});

			});*/

			$("input").keypress(function (e) {
				var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
				if (keyCode == 13) {
					//console.info(this.id);
					var id = this.id;
					var index = id.replace("assignEa","")*1+1;
					var nextId = "#assignEa"+index;
					var vl = $("#assignEa"+index).val();
					if (vl != null || vl !="") {
						$("#assignEa"+index).focus().val(vl);
					} else {
						$("#assignEa"+index).focus();
					}



				/*	var i;
					for (i = 0; i < this.form.elements.length; i++)
						if (this == this.form.elements[i])
							break;
					i = (i + 1) % this.form.elements.length;
					this.form.elements[i].focus();*/
					return false;
				}else
					return true;
			});

			//绑定多选框是否选中
			$('table input:checkbox').on('click' , function(){
				if($(this).get(0).checked) {
					//var value1 = $(this).val();
					auditChoseQty();
				}
			});


		});


		function auditChoseQty() {

			var str = '';
			for (var i = 0; i < document.getElementsByName('ids').length; i++) {
				if (document.getElementsByName('ids')[i].checked) {
					if (str == '') str += document.getElementsByName('prodids')[i].value + '-'+document.getElementsByName('assignEa')[i].value;
					else str += ',' + document.getElementsByName('prodids')[i].value + '-'+document.getElementsByName('assignEa')[i].value;
				}
			}
			//console.info(str);
			var PRODIDS_QTY = $("#PRODIDS_QTY").val();
			//console.info(PRODIDS_QTY);
            var result = false;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>assignout/auditQty.do',
				data: {BOX_IDS:str,PRODIDS_QTY:PRODIDS_QTY,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				//async: false,
				success: function(data){
					//如果不存在
					if("success" != data.result){

						/*$(obj).tips({
							side:3,
							msg:''+data.AUDIT_INFO,
							bg:'#AE81FF',
							time:2
						});
						setTimeout("",2000);*/
						bootbox.dialog(data.AUDIT_INFO,
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
					} else {
						result  = true;
					}
				},
				complete: function (XMLHttpRequest, textStatus) {
					//$("#INSTOCK_NO").focus();
					//$("#INSTOCK_NO").css('background-color','yellow');
				}
			});

			}

        function setRemark1(checkb) {
            if (checkb.checked == true) {
                $("#REMARK1_EMP").val('1');
                $("#REMARK1").val('');
                $("#REMARK1").attr("readOnly", "readOnly");
                $("#REMARK1").attr("placeholder", " ");
            } else {
                $("#REMARK1_EMP").val('0');
                $("#REMARK1").val('');
                $("#REMARK1").removeAttr("readOnly");
                $("#REMARK1").attr("placeholder", " ");
            }
        }

        function setRemark2(checkb) {
            if (checkb.checked == true) {
                $("#REMARK2_EMP").val('1');
                $("#REMARK2").val('');
                $("#REMARK2").attr("readOnly", "readOnly");
                $("#REMARK2").attr("placeholder", " ");
            } else {
                $("#REMARK2_EMP").val('0');
                $("#REMARK2").val('');
                $("#REMARK2").removeAttr("readOnly");
                $("#REMARK2").attr("placeholder", " ");
            }
        }

        function setRemark3(checkb) {
            if (checkb.checked == true) {
                $("#REMARK3_EMP").val('1');
                $("#REMARK3").val('');
                $("#REMARK3").attr("readOnly", "readOnly");
                $("#REMARK3").attr("placeholder", " ");
            } else {
                $("#REMARK3_EMP").val('0');
                $("#REMARK3").val('');
                $("#REMARK3").removeAttr("readOnly");
                $("#REMARK3").attr("placeholder", " ");
            }
        }
		</script>
</body>
</html>