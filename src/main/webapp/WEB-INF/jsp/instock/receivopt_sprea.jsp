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
			if($("#INSTOCK_NO").val()==""){
			$("#INSTOCK_NO").tips({
				side:3,
	            msg:'请输入入库单号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#INSTOCK_NO").focus();
			return false;
		}
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

		if($("#PROD_CODE").val()==""){
			$("#PROD_CODE").tips({
				side:3,
	            msg:'请输入料号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PROD_CODE").focus();
			return false;
		}

		if($("#RECEIV_QTY").val()==""){
			$("#RECEIV_QTY").tips({
				side:3,
	            msg:'请输入数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RECEIV_QTY").focus();
			return false;
		}

		if($("#PACKAGE_QTY").val()==""){
			$("#PACKAGE_QTY").tips({
				side:3,
	            msg:'请输入PACKAGE QTY',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PACKAGE_QTY").focus();
			return false;
		}

		$('#Form').attr("action", "receivopt/saveSprea.do?OPT_EVEN="+pid).submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//判断入库单是否存在
	function hasStock(){
		var result = false;
		var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
		if (INSTOCK_NO ==null || INSTOCK_NO=='') {
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'请输入入库单号',
				bg:'#AE81FF',
				time:2
			});
			result = false;
			return false;
		}
		var RECEIV_TYPE = $("#RECEIV_TYPE").val();
		var RECEIV_TYPE_5 =  RECEIV_TYPE.substr(0,5);
		var INSTOCK_NO_5 = INSTOCK_NO.substr(0,5);

		if (RECEIV_TYPE_5 != INSTOCK_NO_5 ) {
			$("#INSTOCK_NO").val('');
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'不是'+RECEIV_TYPE+"的入库单.",
				bg:'#AE81FF',
				time:2
			});
			result = false;
			return false;
		}

		$("#INSTOCK_NO").val(INSTOCK_NO);

		$.ajax({
			type: "POST",
			url: '<%=basePath%>receivopt/hadInStock.do',
			data: {INSTOCK_NO:INSTOCK_NO,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				//如果不存在
				if("success" != data.result){
					$("#INSTOCK_NO").css('background-color','red');
					$("#INSTOCK_NO").tips({
						side:3,
						msg:'入库单号不存在!',
						bg:'#AE81FF',
						time:2
					});

					setTimeout("$('#INSTOCK_NO').val('')",2000);
					result = false;
					//$('#INSTOCK_NO').focus();
				} else {
					result = true;
					//如果存在，则设置入库单Id与输入框颜色.把该入库单的产品带回来，则之后的产品判断无需与后台打交道
					$("#STOCKMGRIN_ID").val(data.STOCKMGRIN_ID);
					$("#CUSTOMER_ID").val(data.CUSTOMER_ID);
					$("#INSTOCK_NO").css('background-color','green');
				}
			},
			complete: function (XMLHttpRequest, textStatus) {

				//$("#INSTOCK_NO").focus();
				//$("#INSTOCK_NO").css('background-color','yellow');
			}
		});
		return result;
	}



	//判断料号是否存在
	function hasProd(){
		var result = false;
		var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
		if (INSTOCK_NO ==null || INSTOCK_NO=='') {
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'请输入入库单号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

        $("#INSTOCK_NO").val(INSTOCK_NO);
		var PROD_CODE = $.trim($("#PROD_CODE").val());
		if (PROD_CODE ==null || PROD_CODE=='') {
			$("#PROD_CODE").tips({
				side:3,
				msg:'请输入料号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		$("#PROD_CODE").val(PROD_CODE);
		//var STOCKMGRIN_ID = $("#STOCKMGRIN_ID").val();
		var customerId = $("#CUSTOMER_ID").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>receivopt/hadProdCommon.do',
			data: {CUSTOMER_ID: customerId,INSTOCK_NO:INSTOCK_NO,PRODUCT_CODE:PROD_CODE,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			async: false,
			success: function(data){
				//如果不存在
				if("success" != data.result){
					$("#PROD_CODE").css('background-color','red');
					$("#PROD_CODE").tips({
						side:3,
						msg:''+data.AUDIT_INFO,
						bg:'#AE81FF',
						time:2
					});
					setTimeout("$('#PROD_CODE').val('')",2000);
				} else {
					result  = true;
					$("#PROD_CODE").css('background-color','green');
					$("#STOCKMGRIN_ID").val(data.STOCKMGRIN_ID);
					$("#CUSTOMER_ID").val(data.CUSTOMER_ID);
					$("#PROD_CODE").val(data.PRODUCT_CODE);
					$("#SUPPLIER_PROD").val(data.SUPPLIER_PROD);
					//$("#RECEIV_QTY").val(Number(data.RECEI_EA));
					$("#RECEIV_QTY_HID").val(Number(data.RECEI_EA));

                    var supplierProd = data.SUPPLIER_PROD;
                    var srcSupProd = $("#SUPPLIER_PROD").val();
                    if (supplierProd == srcSupProd) {
                        var invNo = $("#INV_NO").val();
                        if (invNo == null || invNo=='') {
                            $("#INV_NO").val(data.INV_NO);
                        }
                        var remark1 = $("#REMARK1").val();
                        if (remark1 == null || remark1=='') {
                            $("#REMARK1").val(data.REMARK1);
                        }

                        var remark2 = $("#REMARK2").val();
                        if (remark2 == null || remark2=='') {
                            $("#REMARK2").val(data.REMARK2);
                        }
                        var remark3 = $("#REMARK3").val();
                        if (remark3 == null || remark3=='') {
                            $("#REMARK3").val(data.REMARK3);
                        }

                        var dateCode = $("#DATE_CODE").val();
                        if (dateCode == null || dateCode=='') {
                            $("#DATE_CODE").val(data.DATE_CODE);
                        }
                        var lotNo = $("#LOT_NO").val();
                        if (lotNo == null || lotNo=='') {
                            $("#LOT_NO").val(data.LOT_CODE);
                        }
                        var binCode = $("#BIN_CODE").val();
                        if (binCode == null || binCode=='') {
                            $("#BIN_CODE").val(data.BIN_CODE);
                        }
                        var cooCode = $("#COO").val();
                        if (cooCode == null || cooCode=='') {
                            $("#COO").val(data.COO);
                        }

                        var packQty = data.PACKAGE_QTY;
                        if (packQty != null && packQty!=''&& packQty!=0) {
                            $("#PACKAGE_QTY").val(packQty);
                        }
                    } else {
                        $("#INV_NO").val(data.INV_NO);
                        $("#REMARK1").val(data.REMARK1);
                        $("#REMARK2").val(data.REMARK2);
                        $("#REMARK3").val(data.REMARK3);
                        $("#DATE_CODE").val(data.DATE_CODE);
                        $("#LOT_NO").val(data.LOT_CODE);
                        $("#BIN_CODE").val(data.BIN_CODE);
                        $("#COO").val(data.COO);
                        $("#PACKAGE_QTY").val(data.PACKAGE_QTY);
                    }
                    $("#SUPPLIER_PROD").val(data.SUPPLIER_PROD);

                    var receiQty = $("#RECEIV_QTY").val();
                    var receiQty2 = Number(data.RECEI_EA);
                    if (receiQty == null || receiQty=='' || receiQty ==0) {
                        receiQty = 0;
                    }
                    if (receiQty ==0) {
                        $("#RECEIV_QTY").focus().val(receiQty2);
                    }else if (receiQty > receiQty2) {
                        $("#RECEIV_QTY").focus().val(receiQty2);
                    } else {
                        $("#RECEIV_QTY").focus().val(receiQty);
                    }

                    // $("#RECEIV_QTY").focus().val($("#RECEIV_QTY").val());
                    var receivQty = $("#RECEIV_QTY").val();
                    var hidqty = $("#RECEIV_QTY_HID").val();
                    if (receivQty - hidqty > 0) {
                        result = hasEA();
                    }
                }
			},
			complete: function (XMLHttpRequest, textStatus) {
				//$("#INSTOCK_NO").focus();
				//$("#INSTOCK_NO").css('background-color','yellow');
			}
		});
		return result;
	}

	function hasEA(){
		var curEA1 = $('#RECEIV_QTY').val();
		if(curEA1 <= 0) {
			$("#RECEIV_QTY").tips({
				side:3,
				msg:'必须为大于0数',
				bg:'#AE81FF',
				time:2
			});
			setTimeout("$('#RECEIV_QTY').val('')",4000);
			return false;
		}

		var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
		if (INSTOCK_NO ==null || INSTOCK_NO=='') {
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'请输入入库单号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var PROD_CODE = $.trim($("#PROD_CODE").val());
		if (PROD_CODE ==null || PROD_CODE=='') {
			$("#PROD_CODE").tips({
				side:3,
				msg:'请输入料号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		$("#INSTOCK_NO").val(INSTOCK_NO);
		$("#PROD_CODE").val(PROD_CODE);
		//var STOCKMGRIN_ID = $("#STOCKMGRIN_ID").val();
		var curEA = $('#RECEIV_QTY').val()*1;
		$("#RECEIV_QTY").css('background-color','white');
        var customerId = $('#CUSTOMER_ID').val();
        var supplierProd = $('#SUPPLIER_PROD').val();

		$.ajax({
			type: "POST",
			url: '<%=basePath%>receivopt/hadEA.do',
			data: {CUSTOMER_ID:customerId,INSTOCK_NO:INSTOCK_NO,PRODUCT_CODE:PROD_CODE,SUPPLIER_PROD:supplierProd,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				//如果不存在
				if("success" == data.result) {
					var can_ea = data.RECEI_EA*1;

					if(can_ea < curEA ) {

						// $('#RECEI_EA_DESC').val("未收货数量:"+RECEI_EA);
						if(can_ea < 0 ) {
							$("#RECEIV_QTY").tips({
								side:3,
								msg:'已超收数量:'+(-1*can_ea),
								bg:'#AE81FF',
								time:2
							});
                            if (can_ea <=0) {
                                setTimeout($("#RECEIV_QTY").val(0), 4000);
                            } else {
                                setTimeout($("#RECEIV_QTY").val(can_ea), 4000);
                            }
						} else {
							$("#RECEIV_QTY").tips({
								side:3,
								msg:'当前可收数量:'+can_ea,
								bg:'#AE81FF',
								time:3
							});
							$("#RECEIV_QTY").css('background-color','pink');
							setTimeout("",4000);
						}

					}
				}

			},
			complete: function (XMLHttpRequest, textStatus) {
				//$("#INSTOCK_NO").focus();
				//$("#INSTOCK_NO").css('background-color','yellow');
			}
		});
	}

	//清除前缀码
	function cleanPREFIX() {
		$("#PREFIX_PROD").val('');
		$("#PREFIX_QTY").val('');
		$("#PREFIX_LOTNO").val('');
		$("#PREFIX_DATECODE").val('');
		$("#PREFIX_SCAN").val('');
		return false;
	}



	 function compareCnt() {

		 var PROD_CODE = $.trim($("#PROD_CODE").val());
		 var PRODUCT_CODE_CNT = $("#PRODUCT_CODE_CNT").val();
		 var curEA = $('#RECEIV_QTY').val();

		 var prods = PRODUCT_CODE_CNT.split(",");
		 var isExits = false;
		 var RECEI_EA = 0;
		 for(var index in prods) {
			 var prod = prods[index].split("-");
			 if(prod[0] == PROD_CODE) {
				 RECEI_EA = prod[1];
			 }
		 }

		 if(RECEI_EA - curEA <0 ) {
			// $('#RECEI_EA_DESC').val("未收货数量:"+RECEI_EA);
			 $("#RECEIV_QTY").tips({
				 side:3,
				 msg:'当前数量已经超过入库单可收数量:'+RECEI_EA,
				 bg:'#AE81FF',
				 time:2
			 });
			 setTimeout("",4000);
		 }
	 }


	function auditBoxNo(){

		var rs =auditBigBoxNo();
		if (rs == false) {
			return false;
		}

		var result = false;


		var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
		if (INSTOCK_NO ==null || INSTOCK_NO=='') {
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'请输入入库单号',
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

		$("#INSTOCK_NO").val(INSTOCK_NO);
		$("#BOX_NO").val(BOX_NO);
        var CUSTOMER_ID = $("#CUSTOMER_ID").val();
        var stockMgrInId = $("#STOCKMGRIN_ID").val();

		$.ajax({
			type: "POST",
			//url: '<%=basePath%>receivopt/auditSperaBoxNo.do',
            url: '<%=basePath%>receivopt/auditCommonBoxNo.do',
			data: {INSTOCK_NO:INSTOCK_NO,BOX_NO:BOX_NO,CUSTOMER_ID:CUSTOMER_ID,STOCKMGRIN_ID:stockMgrInId,tm:new Date().getTime()},
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
					result = true;
					$("#BOX_NO").css('background-color','green');
				}
			}
		});
		return result;
	}


	function auditQty() {
		var cnt = $("#PACKAGE_QTY").val();
		if(cnt < 1) {
			$("#PACKAGE_QTY").tips({
				side:3,
				msg:'必须为大于0数',
				bg:'#AE81FF',
				time:2
			});
			setTimeout("$('#PACKAGE_QTY').val('')",4000);
			return false;
		}
		var cnt2 = $("#RECEIV_QTY").val();
		var n = cnt2%cnt;
        if( n != 0) {
			$("#PACKAGE_QTY").tips({
				side:3,
				msg:'必须与(前缀/数量)为倍数关系',
				bg:'#AE81FF',
				time:2
			});
			setTimeout("$('#PACKAGE_QTY').val('')",4000);
			return false;
		}
		return true;
		//!isNaN(t)
	}

	function auditBigBoxNo(){
        var result = false;

		var BIG_BOX_NO = $.trim($("#BIG_BOX_NO").val());
		if (BIG_BOX_NO ==null || BIG_BOX_NO=='') {
			result = true;
			return result;
		}

		var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
		if (INSTOCK_NO ==null || INSTOCK_NO=='') {
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'请输入入库单号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		var BOX_NO = $.trim($("#BOX_NO").val());

		//console.info(BIG_BOX_NO);

		if (BOX_NO == BIG_BOX_NO) {
			$("#BIG_BOX_NO").tips({
				side:3,
				msg:'不能与内箱号一致',
				bg:'#AE81FF',
				time:2
			});
			$("#BIG_BOX_NO").css('background-color','white');
			$("#BOX_NO").css('background-color','white');
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

		//$("#INSTOCK_NO").val(INSTOCK_NO);
		//$("#BIG_BOX_NO").val(BIG_BOX_NO);
		var CUSTOMER_ID = $("#CUSTOMER_ID").val();

		$.ajax({
			type: "POST",
			url: '<%=basePath%>receivopt/auditBigBoxNo.do',
			data: {INSTOCK_NO:INSTOCK_NO,BOX_NO:BIG_BOX_NO,CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
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
					result = true;
					$("#BIG_BOX_NO").css('background-color','green');
				}
			}
		});
		return result;
	}

	function setQrCode(checkb) {
		if(checkb.checked == true ) {
			$("#QR_CODE_USE").val('1');
			$("#QR_CODE").val('');
			$("#QR_CODE").removeAttr("readOnly");
			$("#QR_CODE").attr("placeholder"," ");
		} else {
			$("#QR_CODE_USE").val('0');
			$("#QR_CODE").val('');
			$("#QR_CODE").attr("readOnly","readOnly");
			$("#QR_CODE").attr("placeholder"," ");
		}
	}

</script>


	</head>
<body>
<%--action="receivopt/saveSprea.do" onsubmit="save(1);return false;" action="receivopt/saveSprea.do"--%>
	<form name="Form" id="Form" method="post">
		<input type="hidden" name="RECEIVOPT_ID" id="RECEIVOPT_ID" value="${pd.RECEIVOPT_ID}"/>
		<input type="hidden" name="RECEIV_TYPE" id="RECEIV_TYPE" value="${pd.RECEIV_TYPE}"/>
		<input type="hidden" name="STOCKMGRIN_ID" id="STOCKMGRIN_ID" value="${pd.STOCKMGRIN_ID}"/>
		<input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/>
		<input type="hidden" name="HAD_PROD" id="HAD_PROD" value="${pd.HAD_PROD}"/>
		<input type="hidden" name="PRODUCT_CODE_CNT" id="PRODUCT_CODE_CNT" value="${pd.PRODUCT_CODE_CNT}"/>
		<input type="hidden" name="GROUP_KEY" id="GROUP_KEY" value="${pd.GROUP_KEY}"/>
		<input type="hidden" name="SUPPLIER_PROD" id="SUPPLIER_PROD" value="${pd.SUPPLIER_PROD}"/>

		<input type="hidden" name="RECEIV_QTY_HID" id="RECEIV_QTY_HID" value="${pd.RECEIV_QTY_HID}"/>
		<input type="hidden" name="OPT_EVEN" id="OPT_EVEN" value="1"/>

		<div id="zhongxin">

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6><strong> SPREA收货</strong>
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
									<c:choose>
										<c:when test="${pd.RECEIV_TYPE != null and pd.RECEIV_TYPE == 'SPREA'}">
											<tr>
												<td style="width:120px;text-align: right;padding-top: 13px;">入库单号<span class="red">*</span></td>
												<td colspan="7"><input  type="text" onblur="hasStock()" style="background-color: yellow" name="INSTOCK_NO" id="INSTOCK_NO" value="${pd.INSTOCK_NO}" maxlength="32" placeholder=" " title="入库单号"/>

													二维码
													<input class="input-small" type='checkbox' id="QR_CODE_USE" name='QR_CODE_USE' value="${pd.QR_CODE_USE}"
														   onclick="setQrCode(this);"
														   <c:if test="${pd.QR_CODE_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>

													</td>

											</tr>

											<tr>
												<td style="width:70px;text-align: right;padding-top: 13px;">外箱号</td>
												<td><input type="text" name="BIG_BOX_NO" id="BIG_BOX_NO" onblur="auditBigBoxNo();" value="${pd.BIG_BOX_NO}" maxlength="32" placeholder=" " title="BIG_BOX_NO"/></td>

												<td style="width:120px;text-align: right;padding-top: 13px;">板号</td>
												<td><input type="text" name="BOARD_NO" id="BOARD_NO" value="${pd.BOARD_NO}" maxlength="32" placeholder=" " title="板号"/></td>

											</tr>

											<tr>
												<td style="width:120px;text-align: right;padding-top: 13px;">箱号<span class="red">*</span></td>
												<td colspan="7"> <input type="text" onblur="auditBoxNo();" name="BOX_NO" id="BOX_NO" value="${pd.BOX_NO}" maxlength="32" placeholder=" " title="箱号"/></td>

											</tr>

											<tr>
												<td style="width:120px;text-align: right;padding-top: 13px;">二维码</td>
												<td colspan="7"><input class="span9" type="text" name="QR_CODE" id="QR_CODE" value="${pd.QR_CODE}"
																	   <c:if test="${pd.QR_CODE_USE eq 0 }">readOnly="readOnly" </c:if>
																	    maxlength="256" placeholder=" " title="二维码"/>

												</td>
												<%--onchange="splitQrCode(this.value)"--%>
											</tr>

											<tr>
												<td style="width:110px;text-align: right;padding-top: 13px;">前缀/料号<span class="red">*</span></td>
												<td><input class ="input-mini" type="text" name="PREFIX_PROD" id="PREFIX_PROD" value="${pd.PREFIX_PROD}" maxlength="32" placeholder=" " title="前缀料号"/>
													<input type="text" name="PROD_CODE" id="PROD_CODE"  value="${pd.PROD_CODE}" maxlength="32" placeholder=" " title="料号"/>
												</td>
												<%--onblur="hasProd();"--%>

												<td style="width:110px;text-align: right;padding-top: 13px;">前缀/DateCode</td>
												<td><input class="input-mini" type="text" name="PREFIX_DATECODE"
														   id="PREFIX_DATECODE" value="${pd.PREFIX_DATECODE}" maxlength="32"
														   placeholder="" title="前缀DATECODE"/>
													<input class="input-medium" type="text" name="DATE_CODE" id="DATE_CODE"
														   value="${pd.DATE_CODE}" maxlength="32" placeholder=" " title="DATECODE"/>
												</td>
											</tr>

											<tr>
												<td style="width:110px;text-align: right;padding-top: 13px;">前缀/扫描LotNo</td>
												<td><input class ="input-mini" type="text" name="PREFIX_LOTNO" id="PREFIX_LOTNO" value="${pd.PREFIX_LOTNO}" maxlength="32" placeholder=" " title="前缀LOTNO"/>
										            <input class ="input-small" type="text" name="LOT_NO" id="LOT_NO" value="${pd.LOT_NO}" maxlength="32" placeholder=" " title="LOT_NO"/></td>



												<td style="width:70px;text-align: right;padding-top: 13px;">BIN</td>
												<td><input type="text" name="BIN_CODE" id="BIN_CODE" value="${pd.BIN_CODE}" maxlength="32" placeholder=" " title="BIN_CODE"/></td>

											</tr>

											<tr>
												<td style="width:120px;text-align: right;padding-top: 13px;">前缀/扫描码/数量<span class="red">*</span></td>
												<td><input class ="input-mini" type="text" name="PREFIX_SCAN" id="PREFIX_SCAN" value="${pd.PREFIX_SCAN}" maxlength="32" placeholder="" title="前缀扫描码数量"/>
													<input class ="input-small" type="text" name="SCAN_CODE" id="SCAN_CODE" value="${pd.SCAN_CODE}" maxlength="32" placeholder="" title="扫描码"/>
													<input class ="input-small" type="number"  onblur="hasEA();" min="1" name="RECEIV_QTY" id="RECEIV_QTY"
														   onkeyup="value=value.replace(/[^/d] /g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^/d]/g,''))"
														   value="${pd.RECEIV_QTY}" maxlength="8" placeholder="0" title="描码数量"/>
													</td>

												<td style="width:70px;text-align: right;padding-top: 13px;">COO</td>
												<td><input type="text" name="COO" id="COO" value="${pd.COO}"
														   maxlength="32" placeholder=" " title="COO"/>
												</td>
											</tr>
											<tr>
												<td style="width:120px;text-align: right;padding-top: 13px;">Remark1/2/3</td>
												<td ><input class="input-mini" type="text" name="REMARK1"
															id="REMARK1" maxlength="32"
															value="${pd.REMARK1}" placeholder="" title="Remark1"/>
													<input class="input-small" type="text" name="REMARK2" id="REMARK2"
														   value="${pd.REMARK2}" maxlength="32" placeholder="" title="Remark2"/>
													<input class="input-small" type="text" onblur="hasEA();" min="1"
														   name="REMARK3" id="REMARK3"
														   value="${pd.REMARK3}" maxlength="8" placeholder=" " title="Remark3"/>

												<td style="width:110px;text-align: right;padding-top: 13px;">Package Qty</td>
												<td><input type="number" min="1" name="PACKAGE_QTY" id="PACKAGE_QTY" value="${pd.PACKAGE_QTY}" maxlength="32" onblur="auditQty()"
														   onkeyup="value=value.replace(/[^/d] /g,'') "onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^/d]/g,''))"
														   placeholder=" " title="PACKAGE QTY"/></td>
											</tr>


											<tr>
												<td style="width:110px;text-align: right;padding-top: 13px;">InvNo</td>
												<td> <input class="input-medium" type="text" name="INV_NO" id="INV_NO"
															value="${pd.INV_NO}" maxlength="32" placeholder=" " title="INV_NO"/>
												</td>

												<td style="width:110px;text-align: right;padding-top: 13px;">已扫描数/箱数</td>
												<td><input class="input-small" type="text"  name="CNT_SCAN" id="CNT_SCAN"
														   value="${pd.CNT_SCAN}" maxlength="32" style="text-align: right" readOnly="readOnly" />
													<input class="input-small" type="text" name="BOX_SCAN" id="BOX_SCAN"
														   value="${pd.BOX_SCAN}" maxlength="32" style="text-align: right" readOnly="readOnly" />
												</td>
											</tr>

											<%--<tr>
												<td style="width:120px;text-align: right;padding-top: 13px;">已扫描数</td>
												<td><input type="number" readOnly="readOnly" min="0" name="CNT_SCAN" id="CNT_SCAN" value="${pd.CNT_SCAN}" maxlength="32" placeholder=" " title="CNT_SCAN" style="text-align: right"/></td>

												<td style="width:110px;text-align: right;padding-top: 13px;">已扫描箱数</td>
												<td><input type="number" readOnly="readOnly" min="0" name="BOX_SCAN" id="BOX_SCAN" value="${pd.BOX_SCAN}" maxlength="32" placeholder=" " title="PACKAGE QTY" style="text-align: right"/></td>
											</tr>--%>
										</c:when>
									</c:choose>


								</table>

							</div>
						</div>
					</div>
					<div class="widget-toolbox" align="center">
						<div class="btn-toolbar">
							<div class="btn-group">
								<a class="btn btn-mini btn-primary" onclick="save(1);"><span class="icon-ok"></span>提交</a>
							</div>
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							<div class="btn-group">
							     <a class="btn btn-mini btn-info" onclick="top.Dialog.close();"><span class="icon-search"></span>扫描完成</a>
							</div>
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							<div class="btn-group">
								<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</a>
							</div>
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
							<div class="btn-group">
								<%--<button class="btn btn-mini btn-warning" onclick="cleanPREFIX();"><span class="icon-refresh"></span>清除前置码</button>--%>
								<a class="btn btn-mini btn-warning" onclick="cleanPREFIX();"><span class="icon-refresh"></span>清除前置码</a>
							</div>
							</div>

						</div>
					</div>
				</div>

			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>本次收货明细</h6>
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
										<th class="center">外箱号</th>
										<th class="center">箱号</th>
										<th class="center">料号</th>
										<th class="center">扫描数量</th>
										<th class="center">DateCode</th>
										<th class="center">LotCode</th>
										<th class="center">BIN</th>
										<th class="center">COO</th>
										<th class="center">PackageQty</th>
										<th class="center">Remark1</th>
										<th class="center">Remark2</th>
										<th class="center">Remark3</th>
										<th class="center">供应商料号</th>
									</tr>
									</thead>

									<tbody>

									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr>
														<td>${var.BIG_BOX_NO}</td>
														<td>${var.BOX_NO}</td>
															<%--料号--%>
														<td>${var.PROD_CODE}</td>
															<%--扫描数量--%>
														<td>${var.RECEIV_QTY}</td>
														<td>${var.DATE_CODE}</td>
														<td>${var.LOT_NO}</td>
														<td>${var.BIN_CODE}</td>
														<td>${var.COO}</td>
														<td>${var.PACKAGE_QTY}</td>
														<td>${var.REMARK1}</td>
														<td>${var.REMARK2}</td>
														<td>${var.REMARK3}</td>
														<td>${var.SUPPLIER_PROD}</td>
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

			var INSTOCKNO = $("#INSTOCK_NO").val();
			if (INSTOCKNO==null || INSTOCKNO=='') {
				$("#INSTOCK_NO").focus();
			} else {
				var a = $("#INSTOCK_NO").val();
				$("#INSTOCK_NO").focus().val(a);

				var b = $("#BIG_BOX_NO").val();
				$("#BIG_BOX_NO").focus().val(b);

				$("#BOX_NO").focus();
			}


			/*$('#INSTOCK_NO').bind('input propertychange', function() {
				if ($(this).val().length >=18 ) {
					var rs = hasStock();
					if (rs == true) {
						$('#INSTOCK_NO').focus().val($(this).val());
						//$('#BIG_BOX_NO').focus();
					}
					return false;
				}
			});*/

			/*$('#BIG_BOX_NO').bind('input propertychange', function() {
				//console.info($(this).val());
				var BIG_BOX_NO = $(this).val();
				if(BIG_BOX_NO.length>=17 ) {
					var rs = auditBigBoxNo();
					if (rs == true) {
						$('#BOX_NO').focus();
						//$('#BOX_NO').focus();
					}
				}
			});*/


			$('#RECEIV_QTY').bind('input propertychange', function() {
				//console.info($(this).val());
				var RECEIV_QTY_IN = $(this).val();
				var RECEIV_QTY_HID = $("#RECEIV_QTY_HID").val();

				if(RECEIV_QTY_IN - RECEIV_QTY_HID> 0 ) {
					$("#RECEIV_QTY").tips({
						side:3,
						msg:'最多只能再收货:'+RECEIV_QTY_HID,
						bg:'#AE81FF',
						time:2
					});
					$("#RECEIV_QTY").val(RECEIV_QTY_HID);
					$("#RECEIV_QTY").focus();
					return false;
				}

			});


			$("input").keypress(function (e) {
				var keyCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
				if (keyCode == 13){
					if($(this).attr('id')=="INSTOCK_NO" ||$(this).attr('id')=="BOX_NO"||$(this).attr('id')=="PROD_CODE"
							||$(this).attr('id')=="BIG_BOX_NO"
					        ||$(this).attr('id')=="BIN_CODE"
							||$(this).attr('id')=="PACKAGE_QTY"
							|| $(this).attr('id') == "DATE_CODE"
							|| $(this).attr('id') == "LOT_NO"
							|| $(this).attr('id') == "INV_NO"
							|| $(this).attr('id') == "RECEIV_QTY"
							|| $(this).attr('id') == "QR_CODE") {
						var result = false;
						if ($(this).attr('id')=="INSTOCK_NO") {
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
							}
						}

						if ($(this).attr('id')=="BOX_NO") {
							result = auditBoxNo();
							if(!result){
								return false;
							} else {
								if ($('#QR_CODE_USE').prop('checked')) {
									$('#QR_CODE').focus();
								} else {
									$('#PROD_CODE').focus();
								}
								return false;
							}
						}
						if ($(this).attr('id')=="PROD_CODE") {
							result = hasProd();
							if(!result){
								return false;
							} else {
								$('#DATE_CODE').focus().val($("#DATE_CODE").val());;
								return false;
							}
						}

						if ($(this).attr('id')=="PACKAGE_QTY") {
							result = auditQty();
							if(!result){
								return false;
							}
						}
						if ($(this).attr('id') == "QR_CODE") {
							//+$("#PROD_CODE").focus().val($("#PROD_CODE").val());
                            splitQrCode(this.value);
							return false;
						}

						if ($(this).attr('id') == "RECEIV_QTY") {
							$("#COO").focus().val($("#COO").val());
							return false;
						}

						if ($(this).attr('id') == "DATE_CODE") {
							$("#LOT_NO").focus().val($("#LOT_NO").val());
							return false;
						}

						if ($(this).attr('id') == "LOT_NO") {
							$("#BIN_CODE").focus().val($("#BIN_CODE").val());
							return false;
						}
						if ($(this).attr('id') == "BIN_CODE") {
							$("#RECEIV_QTY").focus().val($("#RECEIV_QTY").val());
							return false;
						}

						/*if ($(this).attr('id')=="BIN_CODE") {
							choseSubmit();
						}*/

						if ($(this).attr('id') == "INV_NO") {
							choseSubmit();
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
				//	$("#OPT_EVEN").val(5);
					save(1);
				} else {
					$("#BIN_CODE").focus();;
				}
			});
		}


		function onEnter1() {
			//INSTOCK_NO BOX_NO PROD_CODE RECEIV_QTY COO PACKAGE_QTY
			$("#INSTOCK_NO").val('');
			$("#BOX_NO").val('');
			$("#PROD_CODE").val('');
			$("#RECEIV_QTY").val('');
			$("#COO").val('');
			$("#PACKAGE_QTY").val('');
			$("#INSTOCK_NO").focus();
			return false;
			//!isNaN(t)
		}

			function splitQrCode(value) {
				var arr = value.split(',');
				var len = arr.length;
				$("#DATE_CODE").val(arr[2]);
				$("#LOT_NO").val(arr[1]);
				$("#BIN_CODE").val(arr[len-1]);
				$("#COO").val(arr[4]);
				$("#RECEIV_QTY").val(arr[3]);
				$("#PROD_CODE").val(arr[0]);

				var receivQty = $("#RECEIV_QTY").val();
				//默认等于扫描的数量
				$('#PACKAGE_QTY').val(receivQty);

 				var prod = $("#PROD_CODE").val();
                var rsult = hasProd();
                if (!rsult) {
                    $("#PROD_CODE").focus().val(prod);
                    return false;
                }

				$("#RECEIV_QTY").focus().val(receivQty);

				var hidqty = $("#RECEIV_QTY_HID").val();
				if (receivQty -hidqty>0 ) {
					hasEA();
                   return false;
				}

				var bin = $("#BIN_CODE").val();
				$("#BIN_CODE").focus().val(bin);

				save(1);
			}


		</script>
</body>
</html>