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
	
	
	//保存
	function save(pid){
			if($("#OUTSTOCK_NO").val()==""){
			$("#OUTSTOCK_NO").tips({
				side:3,
	            msg:'请输入出库单号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#OUTSTOCK_NO").focus();
			return false;
		}
		if($("#BIG_BOX_NO").val()==""){
			$("#BIG_BOX_NO").tips({
				side:3,
				msg:'请输入外箱号',
				bg:'#AE81FF',
				time:2
			});
			$("#BIG_BOX_NO").focus();
			return false;
		}
		var BIG_BOX_SUBM = $('#BIG_BOX_SUBM').val();
		if (BIG_BOX_SUBM =='1') {

		} else {
			if($("#BOX_NO").val()==""){
				$("#BOX_NO").tips({
					side:3,
					msg:'请输入箱号',
					bg:'#AE81FF',
					time:2
				});
				$("#BOX_NO").focus();
				return false;
			}
		}

		//$('#Form').attr("action", "pickout/savePickManualSprea.do?OPT_EVEN="+pid);

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}


	//判断入库单是否存在
	function hasStock(){
		var result = false;
		var OUTSTOCK_NO = $.trim($("#OUTSTOCK_NO").val());
		if (OUTSTOCK_NO ==null || OUTSTOCK_NO=='') {
			$("#OUTSTOCK_NO").tips({
				side:3,
				msg:'请输入出库单号',
				bg:'#AE81FF',
				time:2
			});
			result = false;
			return false;
		}
		var RECEIV_TYPE = $("#RECEIV_TYPE").val();
		var RECEIV_TYPE_5 =  RECEIV_TYPE.substr(0,5);
		var OUTSTOCK_NO_5 = OUTSTOCK_NO.substr(0,5);

		if (RECEIV_TYPE_5 != OUTSTOCK_NO_5 ) {
			$("#OUTSTOCK_NO").val('');
			$("#OUTSTOCK_NO").tips({
				side:3,
				msg:'不是'+RECEIV_TYPE+"的入库单.",
				bg:'#AE81FF',
				time:2
			});
			result = false;
			return false;
		}

		$("#OUTSTOCK_NO").val(OUTSTOCK_NO);

		$.ajax({
			type: "POST",
			url: '<%=basePath%>pickout/hadOutStock.do',
			data: {OUTSTOCK_NO:OUTSTOCK_NO,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				//如果不存在
				if("success" != data.result){
					$("#OUTSTOCK_NO").css('background-color','red');
					$("#OUTSTOCK_NO").tips({
						side:3,
						msg:'出库单号不存在或全部拣货!',
						bg:'#AE81FF',
						time:2
					});

					setTimeout("$('#OUTSTOCK_NO').val('')",2000);
					result = false;
				} else {
					result = true;
					//如果存在，则设置入库单Id与输入框颜色.把该入库单的产品带回来，则之后的产品判断无需与后台打交道
					$("#STOCKMGROUT_ID").val(data.STOCKMGROUT_ID);
					$("#CUSTOMER_ID").val(data.CUSTOMER_ID);
					$("#OUTSTOCK_NO").css('background-color','green');
				}
			},
			complete: function (XMLHttpRequest, textStatus) {

				//$("#INSTOCK_NO").focus();
				//$("#INSTOCK_NO").css('background-color','yellow');
			}
		});
		return result;
	}


	function auditBoxNo(){

		var rs =auditBigBoxNo();
		if (rs == false) {
			return false;
		}

		var result = false;


		var OUTSTOCK_NO = $.trim($("#OUTSTOCK_NO").val());
		if (OUTSTOCK_NO ==null || OUTSTOCK_NO=='') {
			$("#OUTSTOCK_NO").tips({
				side:3,
				msg:'请输入出库单号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var BOX_NO = $.trim($("#BOX_NO").val());
		if (BOX_NO ==null || BOX_NO=='') {
			$("#BOX_NO").tips({
				side:3,
				msg:'请输入箱号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var RECEIV_TYPE = $("#RECEIV_TYPE").val();
		var RECEIV_TYPE_5 =  RECEIV_TYPE.substr(0,5);
		var BOX_NO_5 = BOX_NO.substr(0,5);
		if (RECEIV_TYPE_5 != BOX_NO_5 ) {
			$("#BOX_NO").val('');
			$("#BOX_NO").tips({
				side:3,
				msg:'不是'+RECEIV_TYPE+"的箱号.",
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		$("#OUTSTOCK_NO").val(OUTSTOCK_NO);
		$("#BOX_NO").val(BOX_NO);
        var CUSTOMER_ID = $("#CUSTOMER_ID").val();
		var STOCKMGROUT_ID= $("#STOCKMGROUT_ID").val();

		$.ajax({
			type: "POST",
			url: '<%=basePath%>pickout/auditSperaBoxNo.do',
			data: {OUTSTOCK_NO:OUTSTOCK_NO,BOX_NO:BOX_NO,CUSTOMER_ID:CUSTOMER_ID,STOCKMGROUT_ID:STOCKMGROUT_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				//如果不存在
				if("success" == data.result){
					$("#BOX_NO").css('background-color','red');
					$("#BOX_NO").tips({
						side:3,
						msg:''+data.auditRs,
						bg:'#AE81FF',
						time:2
					});
					setTimeout("$('#BOX_NO').val('')",3000);
				} else {
					$("#PRODUCT_ID").val(data.PRODUCT_ID);
					result = true;
					$("#BOX_NO").css('background-color','green');
				}
			}
		});
		return result;
	}


	function auditBigBoxNo(){
        var result = false;

		var BIG_BOX_NO = $.trim($("#BIG_BOX_NO").val());
		if (BIG_BOX_NO ==null || BIG_BOX_NO=='') {
			result = true;
			return result;
		}

		var OUTSTOCK_NO = $.trim($("#OUTSTOCK_NO").val());
		if (OUTSTOCK_NO ==null || OUTSTOCK_NO=='') {
			$("#OUTSTOCK_NO").tips({
				side:3,
				msg:'请输入出库单号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var BOX_NO = $.trim($("#BOX_NO").val());

		//console.info(BIG_BOX_NO);

		if (BOX_NO == BIG_BOX_NO) {
			$("#BIG_BOX_NO").css('background-color','white');
			$("#BOX_NO").css('background-color','white');

			$("#BIG_BOX_NO").tips({
				side:3,
				msg:'不能与内箱号一致',
				bg:'#AE81FF',
				time:2
			});
			setTimeout("$('#BIG_BOX_NO').val('')",1000);
			$('#BIG_BOX_NO').focus();
			return false;
		}

		var RECEIV_TYPE = $("#RECEIV_TYPE").val();
		var RECEIV_TYPE_5 =  RECEIV_TYPE.substr(0,5);
		var BOX_NO_5 = BIG_BOX_NO.substr(0,5);
		if (RECEIV_TYPE_5 != BOX_NO_5 ) {
			$("#BIG_BOX_NO").val('');
			$("#BIG_BOX_NO").tips({
				side:3,
				msg:'不是'+RECEIV_TYPE+"的箱号.",
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var CUSTOMER_ID = $("#CUSTOMER_ID").val();
		var STOCKMGROUT_ID= $("#STOCKMGROUT_ID").val();

		$.ajax({
			type: "POST",
			url: '<%=basePath%>pickout/auditBigBoxNo.do',
			data: {OUTSTOCK_NO:OUTSTOCK_NO,BIG_BOX_NO:BIG_BOX_NO,CUSTOMER_ID:CUSTOMER_ID,STOCKMGROUT_ID:STOCKMGROUT_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				//如果不存在
				if("success" == data.result){
					$("#BIG_BOX_NO").css('background-color','red');
					$("#BIG_BOX_NO").tips({
						side:3,
						msg:''+data.auditRs,
						bg:'#AE81FF',
						time:2
					});
					setTimeout("$('#BIG_BOX_NO').val('')",3000);
				} else {
					$("#PRODUCT_ID").val(data.PRODUCT_ID);
					$("#BIG_BOX_SUBM").val(data.BIG_BOX_SUBM);
					result = true;
					$("#BIG_BOX_NO").css('background-color','green');
					var bigBoxSubm = $('#BIG_BOX_SUBM').val();
					if (bigBoxSubm =='1') {
						$("#BIG_BOX_NO").tips({
							side:3,
							msg:'外箱号全部分配给该出库单,请按回车确认拣货.',
							bg:'#AE81FF',
							time:2
						});
					}
				}
				$("#CNT_SCAN").val(0);
			}
		});
		return result;
	}


</script>


	</head>
<body>
<%--onsubmit="save(1);return false;"  onsubmit="save(1);return false;"--%>
	<form  action="pickout/savePickManualSprea.do"  name="Form" id="Form" method="post">
		<input type="hidden" name="STOCKMGROUT_ID" id="STOCKMGROUT_ID" value="${pd.STOCKMGROUT_ID}"/>
		<input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/>
		<input type="hidden" name="RECEIV_TYPE" id="RECEIV_TYPE" value="SPREA"/>
		<input type="hidden" name="PRODUCT_ID" id="PRODUCT_ID" value="${pd.PRODUCT_ID}"/>
		<input type="hidden" name="BIG_BOX_SUBM" id="BIG_BOX_SUBM" value="${pd.BIG_BOX_SUBM}"/>

		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong>SPREA拣货</strong>
					</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">

					<div class="widget-main padding-3">
						<div class="slim-scroll" data-height="125">
							<div class="content">
								<table id="table_report" class="table table-striped  table-hover table-condensed">

											<tr>
												<td style="width:120px;text-align: right;padding-top: 13px;">出库单号<span class="red">*</span></td>
												<td colspan="7"><input  type="text" onblur="hasStock()" style="background-color: yellow" name="OUTSTOCK_NO" id="OUTSTOCK_NO" value="${pd.OUTSTOCK_NO}" maxlength="32" placeholder=" " title="入库单号"/>

											</tr>
									        <tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">外箱号<span class="red">*</span></td>
												<td><input type="text" name="BIG_BOX_NO" id="BIG_BOX_NO" onblur="auditBigBoxNo();" value="${pd.BIG_BOX_NO}" maxlength="32" placeholder=" " title="BIG_BOX_NO"/></td>

												<td style="width:120px;text-align: right;padding-top: 13px;">箱号</td>
												<td colspan="7"> <input type="text" onblur="auditBoxNo();" name="BOX_NO" id="BOX_NO" value="${pd.BOX_NO}" maxlength="32" placeholder=" " title="箱号"/></td>
											</tr>

											<tr>
												<td style="width:120px;text-align: right;padding-top: 13px;">已扫描箱数</td>
												<td colspan="7"><input type="number" readOnly="readOnly" min="0" name="CNT_SCAN" id="CNT_SCAN" value="${pd.CNT_SCAN}" maxlength="32" placeholder=" " title="CNT_SCAN" style="text-align: right"/></td>

											<%--<td style="width:110px;text-align: right;padding-top: 13px;">已扫描箱数</td>
												<td><input type="number" readOnly="readOnly" min="0" name="BOX_SCAN" id="BOX_SCAN" value="${pd.BOX_SCAN}" maxlength="32" placeholder=" " title="PACKAGE QTY" style="text-align: right"/></td>--%>
											</tr>


								</table>

							</div>
						</div>
					</div>
					<div class="widget-toolbox" align="center">
						<div class="btn-toolbar">
							<div class="btn-group">
								<button class="btn btn-mini btn-primary" onclick="save(1);"><span class="icon-ok"></span>提交</button>
							</div>
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							<div class="btn-group">
							     <button class="btn btn-mini btn-info" onclick="top.Dialog.close();"><span class="icon-search"></span>扫描完成</button>
							</div>
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							<div class="btn-group">
								<button class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</button>
							</div>
							</div>

						</div>
					</div>
				</div>

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>本次拣货明细</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">

					<div class="widget-main padding-3">
						<div ><%-- class="slim-scroll" data-height="125"--%>
							<div class="content">

								<table id="table_prod_dtl" class="table table-striped table-bordered table-hover">
									<thead>
									<tr>
										<th class="center">行号</th>
										<th class="center">外箱号</th>
										<th class="center">箱号</th>
										<th class="center">料号</th>
										<th class="center">拣货EA</th>
										<th class="center">存储库位</th>
										<th class="center">拣货状态</th>
										<th class="center">DateCode</th>
										<th class="center">LotCode</th>
										<th class="center">BIN</th>
										<th class="center">COO</th>
									</tr>
									</thead>

									<tbody>

									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>

														<td>${var.ROW_NO}</td>
														<td>${var.BIG_BOX_NO}</td>
														<td>${var.BOX_NO}</td>
														<%--料号--%>
														<td>${var.PRODUCT_CODE}</td>
														<%--拣货数量--%>
														<td>${var.PICK_EA}</td>

														<td>${var.LOCATOR_CODE}</td>

															<%--拣货状态--%>
														<td <c:if test="${139==var.PICK_STATE}">style="background-color: red" </c:if>
															<c:if test="${140==var.PICK_STATE}">style="background-color: yellow" </c:if>
															<c:if test="${141==var.PICK_STATE}">style="background-color: green" </c:if>
														>
															<c:forEach items="${pickStateList}" var="cnt">
																<c:if test="${cnt.id==var.PICK_STATE}">${cnt.value}</c:if>
															</c:forEach>
														</td>
													</tr>
													<td>${var.DATE_CODE}</td>
													<td>${var.LOT_CODE}</td>
													<td>${var.BIN}</td>
													<td>${var.COO}</td>
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

		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen({search_contains: true});
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker({autoclose: true});

			var INSTOCKNO = $("#OUTSTOCK_NO").val();
			if (INSTOCKNO == null || INSTOCKNO == '') {
				$("#OUTSTOCK_NO").focus();
			} else {
				var BOX_NO = $("#BOX_NO").val();
				if (BOX_NO == null || BOX_NO == '') {
					$("#BIG_BOX_NO").val('');
					$("#BIG_BOX_NO").focus();
				} else {
					$("#BOX_NO").val('');
					$("#BOX_NO").focus();
				}
			}

			$('#OUTSTOCK_NO').bind('input propertychange', function() {
				if ($(this).val().length >=18 ) {
					var rs = hasStock();
					if (rs == true) {
						$('#OUTSTOCK_NO').focus().val($(this).val());
						//$('#BIG_BOX_NO').focus();
					}
					return false;
				}
			});

			$('#BIG_BOX_NO').bind('input propertychange', function() {
				if ($(this).val().length >=17 ) {
					var rs = auditBigBoxNo();
					if (rs == true) {
						$('#BIG_BOX_NO').focus().val($(this).val());
						//$('#BIG_BOX_NO').focus();
					}
					return false;
				}
			});

			$('#BOX_NO').bind('input propertychange', function() {
				//console.info($(this).val());
				var BOX_NO = $(this).val();
				if(BOX_NO.length>=17 ) {
					var rs = auditBigBoxNo();
					if (rs == true) {
						$('#BOX_NO').focus().val(BOX_NO);
					} else {
						$('#BOX_NO').val('');

						$("#BIG_BOX_NO").css('background-color','white');
						$('#BIG_BOX_NO').focus();
						//$('#BOX_NO').focus();
					}
				}
			});

			/*$('#BIG_BOX_NO').bind('input propertychange', function() {
				//console.info($(this).val());
				var BIG_BOX_NO = $(this).val();
				if(BIG_BOX_NO.length>=17 ) {
					var rs = auditBigBoxNo();
					/!*if (rs == true) {
						$('#BOX_NO').focus();
					}*!/
					if(!rs){
						return false;
					} else {
						var BIG_BOX_SUBM = $('#BIG_BOX_SUBM').val();
						if (BIG_BOX_SUBM =='1') {
							//choseSubmit();
							return false;
						} else {
							$("#BOX_NO").focus();
							return false;
						}
					}
				}
			});

			$('#BOX_NO').bind('input propertychange', function() {
				//console.info($(this).val());
				var BOX_NO = $(this).val();
				if(BOX_NO.length>=17 ) {
					var rs = auditBigBoxNo();
					if (rs == true) {
						$('#BOX_NO').focus().val(BOX_NO);
					} else {
						$('#BOX_NO').val('');
						//$('#BIG_BOX_NO').focus();
						$('#BOX_NO').focus();
					}
				}
			});*/


			$("input").keypress(function (e) {
				var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
				if (keyCode == 13){
					if($(this).attr('id')=="OUTSTOCK_NO" ||$(this).attr('id')=="BOX_NO"||$(this).attr('id')=="BIG_BOX_NO") {
						var result = false;
						if ($(this).attr('id')=="OUTSTOCK_NO") {

							result = hasStock();
							if(!result){
								return false;
							} else {
								$("#BIG_BOX_NO").focus();
								return false;
							}
						}
						if ($(this).attr('id')=="BIG_BOX_NO") {
							result = auditBigBoxNo();
							if(!result){
								return false;
							} else {
								var BIG_BOX_SUBM = $('#BIG_BOX_SUBM').val();
                                if (BIG_BOX_SUBM =='1') {
									choseSubmit();
									return false;
								} else {
									$("#BOX_NO").focus();
									return false;
								}
							}
						}

						if ($(this).attr('id')=="BOX_NO") {
							result = auditBoxNo();
							if(!result){
								return false;
							} else {
								choseSubmit();
							}
						}
						if(result) {
							var i;
							for (i = 0; i < this.form.elements.length; i++)
								if (this == this.form.elements[i])
									break;
							i = (i + 1) % this.form.elements.length;
							this.form.elements[i].focus();
							return false;
						}
					} else {
						var i;
						for (i = 0; i < this.form.elements.length; i++)
							if (this == this.form.elements[i])
								break;
						i = (i + 1) % this.form.elements.length;
						this.form.elements[i].focus();
						return false;
					}


				}
				else
					return true;
			});
		});
/*
		$(document).ready(function(){

		});*/

		function choseSubmit() {
			bootbox.confirm("确定要保存吗?", function(result) {
				if(result) {
				///	$("#Form").submit();
					save(1);
				} else {
					$("#BOX_NO").focus();
				}
			});
		}


		</script>
</body>
</html>