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
	function save(){
			if($("#INSTOCK_NO").val()==""){
			$("#INSTOCK_NO").tips({
				side:3,
	            msg:'请输入进货单编号入货单编号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#INSTOCK_NO").focus();
			return false;
		}
		if($("#CUSTOMER_ID").val()==""){
			$("#CUSTOMER_ID").tips({
				side:3,
	            msg:'请输入客户',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CUSTOMER_ID").focus();
			return false;
		}
		if($("#INSTOCK_TYPE").val()==""){
			$("#INSTOCK_TYPE").tips({
				side:3,
	            msg:'请输入入库类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#INSTOCK_TYPE").focus();
			return false;
		}
		if($("#PRE_INSTOCK_DATE").val()==""){
			$("#PRE_INSTOCK_DATE").tips({
				side:3,
	            msg:'请输入预计入库日期',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PRE_INSTOCK_DATE").focus();
			return false;
		}
		if($("#REAL_INSTOCK_DATE").val()==""){
			$("#REAL_INSTOCK_DATE").tips({
				side:3,
	            msg:'请输入实际入库日期',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#REAL_INSTOCK_DATE").focus();
			return false;
		}
		if($("#INSTOCK_STATE").val()==""){
			$("#INSTOCK_STATE").tips({
				side:3,
	            msg:'请输入收货状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#INSTOCK_STATE").focus();
			return false;
		}
		if($("#PUT_STATE").val()==""){
			$("#PUT_STATE").tips({
				side:3,
	            msg:'请输入码放状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PUT_STATE").focus();
			return false;
		}
		if($("#TOTAL_WEIGHT").val()==""){
			$("#TOTAL_WEIGHT").tips({
				side:3,
	            msg:'请输入总毛重',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TOTAL_WEIGHT").focus();
			return false;
		}
		if($("#TOTAL_SUTTLE").val()==""){
			$("#TOTAL_SUTTLE").tips({
				side:3,
	            msg:'请输入总净重',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TOTAL_SUTTLE").focus();
			return false;
		}
		if($("#TOTAL_VOLUME").val()==""){
			$("#TOTAL_VOLUME").tips({
				side:3,
	            msg:'请输入总体积',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TOTAL_VOLUME").focus();
			return false;
		}
		if($("#TOTAL_PRICE").val()==""){
			$("#TOTAL_PRICE").tips({
				side:3,
	            msg:'请输入总价',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TOTAL_PRICE").focus();
			return false;
		}
		if($("#TOTAL_EA").val()==""){
			$("#TOTAL_EA").tips({
				side:3,
	            msg:'请输入总期望EA数',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#TOTAL_EA").focus();
			return false;
		}
		if($("#PRIORITY_LEVEL").val()==""){
			$("#PRIORITY_LEVEL").tips({
				side:3,
	            msg:'请输入优先级',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PRIORITY_LEVEL").focus();
			return false;
		}
		if($("#ORDER_NO").val()==""){
			$("#ORDER_NO").tips({
				side:3,
	            msg:'请输入订单号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ORDER_NO").focus();
			return false;
		}
		if($("#COST_STATE").val()==""){
			$("#COST_STATE").tips({
				side:3,
	            msg:'请输入费用录入已完成',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#COST_STATE").focus();
			return false;
		}
		if($("#MEMO").val()==""){
			$("#MEMO").tips({
				side:3,
	            msg:'请输入备注',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#MEMO").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function getInStockNo2() {
		$("#INSTOCK_NO").val("Test-001");
	}

	function getInStockNo(){
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		if (CUSTOMER_ID ==null || CUSTOMER_ID=='') {
			return false;
		}
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockmgrin/getInStockNo.do',
			data: {CUSTOMER_ID:CUSTOMER_ID,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				for (var k in data) {
					$("#INSTOCK_NO").val(data[k]);
				}
			}
		});
	}
	var loadCnt = 1;
	function customerChange() {
		var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
		if (CUSTOMER_ID == null || CUSTOMER_ID == '') {
			return false;
		}
		loadCnt = 0;
		$("#table_orderproperty").empty();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>stockmgrin/getOrderProperty.do',
			data: {CUSTOMER_ID: CUSTOMER_ID, tm: new Date().getTime()},
			dataType: 'json',
			cache: false,
			success: function (data) {
				var trs = "";
				for (var k in data) {
					//console.log(data[k]);
					var json = data[k];
					var lenth = json.length;

					for (var i = 0; i < lenth; i++) {
						//alert(json[i]['SRC_SORT']);
						var PROPERTY_KEY = json[i]['PROPERTY_KEY'];
						var SRC_SORT = json[i]['SRC_SORT'];
						var PROPERTY_COLUM = json[i]['PROPERTY_COLUM'];

						trs += "<tr><td class='center' style='width: 30px;'>" + (i + 1) + "</td><td>" + PROPERTY_KEY + "</td><td class='input-sm'>"
						var tds = "";
						if (SRC_SORT <= 10) {
							tds = "<input onclick='addClass(this);' class='date-picker' name=" + PROPERTY_COLUM + " value=''  id=" + PROPERTY_COLUM + " type='text' data-date-format='yyyy-mm-dd' readonly='readonly' " + "/>";
							//tds = "<input class='date-picker' name='test' id='test' value='2016-10-29' type='text' data-date-format='yyyy-mm-dd' readonly='readonly' />";

						}
						if (SRC_SORT > 10 && SRC_SORT <= 20) {
							tds = "<input type='number' name=" + PROPERTY_COLUM + " value='' id=" + PROPERTY_COLUM + " value=' ' maxlength='32'  placeholder='0'/>";

						}

						if (SRC_SORT > 20) {
							tds = "<input type='text' name=" + PROPERTY_COLUM + "  value='' id=" + PROPERTY_COLUM + " value='' maxlength='32'/>";
						}
						trs += tds;
						trs += "</td></tr>";
					}

					//trs += "<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>" ;

				}

				$("#table_orderproperty").append(trs);
				$('.date-picker').datepicker();
			},
			complete: function(XMLHttpRequest, textStatus){
				//alert($("#table_orderproperty tr").length);
				//如果返回值没有空的时候
				var trLenth = $("#table_orderproperty tr").length;
				if (trLenth == 0) {
					$("#table_orderproperty").append("<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>");
				}
			}/*,
			error: function(){
				trs += "<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>" ;
				$("#table_orderproperty").append(trs);
			}*/
		});
		$("#INSTOCK_NO").val('');
	}

	function addClass(obj) {
		//alert(loadCnt);
		if(loadCnt == 0) {
			$('.date-picker').datepicker();
		}
		loadCnt += 1;
		this.focus();
		this.onclick();
	}


	function add(){
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="新增";
		diag.URL = '<%=basePath%>stockmgrin/goAdd.do';
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
</script>
	</head>
<body>
	<form action="stockmgrin/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="STOCKMGRIN_ID" id="STOCKMGRIN_ID" value="${pd.STOCKMGRIN_ID}"/>
		<div id="zhongxin">
			<%--<table id="table_save" class="table table-striped  table-hover table-condensed">
				<tr>
					<td style="text-align: center;" colspan="10">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
						<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
					</td>
				</tr>
			</table>--%>

			<div class="container-fluid">
				<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
				<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
				<div class="row-fluid">
					<div class="span8">

						<table id="table_report0" class="table table-striped table-hover table-condensed">

							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">入货单编号<span
										class="red">*</span></td>
								<td colspan="7" style="vertical-align:top;">
									<div class="row-fluid input-append">
										<input class="span11" readonly="readonly" type="text" name="INSTOCK_NO"
											   id="INSTOCK_NO" value="${pd.INSTOCK_NO}"
											   maxlength="64" placeholder="入货单编号" title="入货单编号"/>
										<span class="add-on"><a class="icon-file"
																onclick="getInStockNo();" title="生成单号"></a></span>
									</div>
								</td>
							</tr>

							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">客户<span
										class="red">*</span></td>
								<td colspan="7" style="vertical-align:top;">
									<select class="span12" name="CUSTOMER_ID" id="CUSTOMER_ID" onchange="customerChange()"
											style="vertical-align:top;" maxlength="32">
										<c:forEach items="${customerList}" var="cnt">
											<option value="${cnt.id }"
													<c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">入库类型</td>
								<td colspan="7" style="vertical-align:top;">
									<select class="span12" name="INSTOCK_TYPE" id="INSTOCK_TYPE" style="vertical-align:top;" maxlength="32">
										<c:forEach items="${stockInTypeList}" var="cnt">
											<option value="${cnt.id }" <c:if test="${cnt.id == pd.INSTOCK_TYPE }">selected</c:if>>${cnt.value }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">预计入库日期</td>
								<td><input class="date-picker" name="PRE_INSTOCK_DATE" id="PRE_INSTOCK_DATE" value="${pd.PRE_INSTOCK_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="预计入库日期" title="预计入库日期"/></td>

								<td style="width:110px;text-align: right;padding-top: 13px;">实际入库日期</td>
								<td><input class="date-picker" name="REAL_INSTOCK_DATE" id="REAL_INSTOCK_DATE" value="${pd.REAL_INSTOCK_DATE}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="实际入库日期" title="实际入库日期"/></td>
							</tr>
							<tr>

								<td style="width:110px;text-align: right;padding-top: 13px;">收货状态</td>
								<td  style="vertical-align:top;">
								<span style="position:relative">
									<select  onfocus="this.blur();"  name="INSTOCK_STATE" id="INSTOCK_STATE" style="vertical-align:top;" maxlength="32">
										<c:forEach items="${stockInStateList}" var="cnt">
											<option value="${cnt.id }" <c:if test="${cnt.id == pd.INSTOCK_STATE }">selected</c:if>>${cnt.value }</option>
										</c:forEach>
									</select>
								<div style="position:absolute;width:220px;height:50px;left:0px;top:0px;background:#fff;opacity:0;filter:alpha(opacity=0)"> </div>
									 </span>
								</td>

								<td style="width:110px;text-align: right;padding-top: 13px;">码放状态</td>
								<td  style="vertical-align:top;">
									<span style="position:relative">
									<select onfocus="this.blur();" name="PUT_STATE" id="PUT_STATE" style="vertical-align:top;" maxlength="32">
										<c:forEach items="${putStateList}" var="cnt">
											<option value="${cnt.id }" <c:if test="${cnt.id == pd.PUT_STATE }">selected</c:if>>${cnt.value }</option>
										</c:forEach>
									</select>
									<div style="position:absolute;width:220px;height:50px;left:0px;top:0px;background:#fff;opacity:0;filter:alpha(opacity=0)"> </div>
						          </span>
								</td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">总毛重</td>
								<td><input type="number" name="TOTAL_WEIGHT" id="TOTAL_WEIGHT" value="${pd.TOTAL_WEIGHT}" maxlength="32"  placeholder="0"  title="总毛重"/></td>

								<td style="width:110px;text-align: right;padding-top: 13px;">总净重</td>
								<td><input type="number" name="TOTAL_SUTTLE" id="TOTAL_SUTTLE" value="${pd.TOTAL_SUTTLE}" maxlength="32"   placeholder="0" title="总净重"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">总体积</td>
								<td><input type="number" name="TOTAL_VOLUME" id="TOTAL_VOLUME" value="${pd.TOTAL_VOLUME}" maxlength="32"   placeholder="0" title="总体积"/></td>

								<td style="width:110px;text-align: right;padding-top: 13px;">总价</td>
								<td><input type="number" name="TOTAL_PRICE" id="TOTAL_PRICE" value="${pd.TOTAL_PRICE}" maxlength="32"  placeholder="0"  title="总价"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">总期望EA数</td>
								<td><input type="number" name="TOTAL_EA" id="TOTAL_EA" value="${pd.TOTAL_EA}" maxlength="32" placeholder="0"  title="总期望EA数"/></td>

								<td style="width:110px;text-align: right;padding-top: 13px;">优先级</td>
								<td  style="vertical-align:top;">
									<select  name="PRIORITY_LEVEL" id="PRIORITY_LEVEL" style="vertical-align:top;" maxlength="32">
										<c:forEach items="${priorityLevelList}" var="cnt">
											<option value="${cnt.id }" <c:if test="${cnt.id == pd.PRIORITY_LEVEL }">selected</c:if>>${cnt.value }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">订单号</td>
								<td><input type="text" name="ORDER_NO" id="ORDER_NO" value="${pd.ORDER_NO}" maxlength="32" placeholder="这里输入订单号" title="订单号"/></td>

								<td style="width:110px;text-align: right;padding-top: 13px;">费用录入已完成</td>
								<td  class="left"><input  class="input-small" type='checkbox' id="COST_STATE"  name='COST_STATE' value="1"
									<c:if test="${pd.COST_STATE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
								</td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">备注</td>
								<td colspan="7"><input class="span12"  type="text" name="MEMO" id="MEMO" value="${pd.MEMO}" maxlength="32" placeholder="这里输入备注" title="备注"/></td>
							</tr>
							</table>

					</div>

				<%--	<div class="span4"  style="height:510px; overflow:auto">
						订单属性
						<table id="table_report1" width="100%" height="100%" class="table table-striped table-bordered table-hover">
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">入货单编号</td>
								<td ><input  type="text" name="INSTOCK_NO" id="INSTOCK_NO" value="${pd.INSTOCK_NO}" maxlength="32" placeholder="这里输入进货单编号入货单编号" title="进货单编号入货单编号"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">客户</td>
								<td ><input type="text" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}" maxlength="32" placeholder="这里输入客户" title="客户"/></td>
							</tr>
							<tr>
								<td style="width:110px;text-align: right;padding-top: 13px;">入库类型</td>
								<td ><input  type="text" name="INSTOCK_TYPE" id="INSTOCK_TYPE" value="${pd.INSTOCK_TYPE}" maxlength="32" placeholder="这里输入入库类型" title="入库类型"/></td>
							</tr>
						</table>
					</div>--%>
					<div class="span4" >
						订单属性
					<div class="widget-box "  style="overflow:auto">
						<div class="widget-body">
							<div class="widget-main  no-padding">
								<div class="slim-scroll" data-height="500">
									<div class="content">
										<table id="table_orderproperty" class="table table-striped  table-hover table-condensed">
											<tbody>

											<c:choose>
												<c:when test="${not empty inOrderList}">
													<c:if test="${QX.cha == 1 }">
														<c:forEach items="${inOrderList}" var="var" varStatus="vs">
															<tr>
																<td class='center' style="width: 30px;">${vs.index+1}</td>
																<td>${var.PROPERTY_KEY}</td>
																<td class="input-sm">
																	<%--如果属性排序小于等于10，则是时间输入字段--%>
																	<c:if test="${var.SRC_SORT <=10}">
																		<input class="date-picker" name="${var.PROPERTY_COLUM}" id="${var.PROPERTY_COLUM}" value="" type="text"
																			   data-date-format="yyyy-mm-dd" readonly="readonly"/>
																	</c:if>
																	<%--如果属性排序大于10 小于等于20，则是数字输入字段--%>
																	<c:if test="${var.SRC_SORT >10 && var.SRC_SORT <=20}">

																		<input type="number" name="${var.PROPERTY_COLUM}" id="${var.PROPERTY_COLUM}" value="${pd.TOTAL_PRICE}"
																			   maxlength="32"  placeholder="0"/>
																	</c:if>

																			<%--如果属性排序大于10 小于等于20，则是数字输入字段--%>
																	<c:if test="${var.SRC_SORT >20}">
																			<input type="text" name="${var.PROPERTY_COLUM}" id="${var.PROPERTY_COLUM}" value="${pd.TOTAL_PRICE}"
																				   maxlength="32"/>
																	</c:if>

																</td>
															</tr>

														</c:forEach>
													</c:if>
												</c:when>
												<c:otherwise>
													<tr class="main_info">
														<td colspan="100" class="center" >没有订单属性数据</td>
													</tr>
												</c:otherwise>
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

			</div>

			<%--<table id="table_report" class="table table-striped  table-hover table-condensed">
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
				</td>
			</tr>
		</table>--%>

				<div class="widget-box">
					<div class="widget-header widget-hea1der-small">
						<h6>产品信息</h6>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="widget-body">
						<div class="widget-toolbox">
							<div class="btn-toolbar">
								<div class="btn-group">
									<a class="btn btn-mini btn-success" onclick="add();"><span class="icon-plus"></span>新增</a>
									<a class="btn btn-mini btn-warning" onclick="add();"><span class="icon-cloud-upload"></span>上传</a>
									<a class="btn btn-mini btn-danger" onclick="add();"><span class="icon-trash"></span>删除</a>
								</div>
							</div>
						</div>

						<div class="widget-main padding-3">
							<div class="slim-scroll" data-height="125">
								<div class="content">
									<div class="alert alert-info">
										Lorem ipsum dolor sit amet, consectetur adipiscing.
									</div>
									<div class="alert alert-danger">
										Lorem ipsum dolor sit amet, consectetur adipiscing.
									</div>
									<div class="alert alert-success">
										Lorem ipsum dolor sit amet, consectetur adipiscing.
									</div>
									<div class="alert">
										Lorem ipsum dolor sit amet, consectetur adipiscing.
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
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>