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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../system/admin/top.jsp"%>
</head>
<body>
<script type="text/javascript">


	//保存
	function save(){
		var BOX_NO = $.trim($("#BOX_NO").val());
		if(BOX_NO==""){
			$("#BOX_NO").tips({
				side:3,
				msg:'请输入箱号',
				bg:'#AE81FF',
				time:2
			});
			$("#BOX_NO").focus();
			return false;
		}

		<%-- BOX_ID, BOX_NO, LONG_UNIT, WIDE_UNIT, HIGH_UNIT, VOLUME_UNIT, TOTAL_SUTTLE, TOTAL_WEIGHT--%>
		var BOX_ID = $("#BOX_ID").val();
		var LONG_UNIT = $.trim($("#LONG_UNIT").val());
		var WIDE_UNIT = $.trim($("#WIDE_UNIT").val());
		var HIGH_UNIT = $.trim($("#HIGH_UNIT").val());
		var VOLUME_UNIT = $.trim($("#VOLUME_UNIT").val());
		var TOTAL_WEIGHT = $.trim($("#TOTAL_WEIGHT").val());
		var IN_CNT = $.trim($("#IN_CNT").val());
		var BIG_BOX_STATUS = $("#BIG_BOX_STATUS").val();
        var WV_FLG_1 = $("#WV_FLG_1").val();


		$.ajax({
			type: "POST",
			url: '<%=basePath%>boxwv/saveBox.do?BOX_NO='+BOX_NO+"&BOX_ID="+BOX_ID+"&LONG_UNIT="+LONG_UNIT+"&WIDE_UNIT="+WIDE_UNIT+"&HIGH_UNIT="+HIGH_UNIT+"&VOLUME_UNIT="+VOLUME_UNIT+"&TOTAL_WEIGHT="+TOTAL_WEIGHT+"&IN_CNT="+IN_CNT+"&BIG_BOX_STATUS="+BIG_BOX_STATUS+"&WV_FLG_1="+WV_FLG_1,
			//data: {BOX_NO:BOX_NO,BOX_ID:BOX_ID,LONG_UNIT:LONG_UNIT,WIDE_UNIT:WIDE_UNIT,HIGH_UNIT:HIGH_UNIT,VOLUME_UNIT:VOLUME_UNIT,TOTAL_WEIGHT:TOTAL_WEIGHT,IN_CNT:IN_CNT,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			//async:false,
			success: function(data){
				//如果成功
				if("success" != data.result){
					$("#BOX_NO").css('background-color','red');
					$("#BOX_NO").tips({
						side:3,
						msg:'箱号不存在',
						bg:'#AE81FF',
						time:2
					});
					setTimeout("$('#BOX_NO').val('')",3000);
				} else {
					//如果存在，则设置入库单Id与输入框颜色.把该入库单的产品带回来，则之后的产品判断无需与后台打交道
					<%-- BOX_ID, BOX_NO, LONG_UNIT, WIDE_UNIT, HIGH_UNIT, VOLUME_UNIT, TOTAL_SUTTLE, TOTAL_WEIGHT--%>
					$("#BOX_ID").val(data.BOX_ID);
					$("#LONG_UNIT_1").val(data.LONG_UNIT_1);
					$("#WIDE_UNIT_1").val(data.WIDE_UNIT_1);
					$("#HIGH_UNIT_1").val(data.HIGH_UNIT_1);
					$("#VOLUME_UNIT_1").val(data.VOLUME_UNIT_1);
					$("#TOTAL_WEIGHT_1").val(data.TOTAL_WEIGHT_1);
					$("#BIG_BOX_STATUS").val(data.BIG_BOX_STATUS);


					$("#IN_CNT").val(data.IN_CNT);

					$("#BOX_NO").val('');
					$("#LONG_UNIT").val('');
					$("#WIDE_UNIT").val('');
					$("#HIGH_UNIT").val('');
					$("#VOLUME_UNIT").val('');
					$("#TOTAL_WEIGHT").val('');

					$("#BOX_NO").tips({
						side:3,
						msg:'保存成功,请继续...',
						bg:'#AE81FF',
						time:2
					});
					setTimeout("$('#BOX_NO').val('')",3000);

					$("#BOX_NO").css('background-color','yellow');
					$("#BOX_NO").focus();
				}
			},
			complete: function (XMLHttpRequest, textStatus) {
				$("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
			}
		});


		/*$("#Form").submit();
		 $("#zhongxin").hide();
		 $("#zhongxin2").show();*/
	}

	function clean() {
		$("#BIG_BOX_STATUS").val('');
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	function copyPro() {

		var LONG_UNIT_1 = $("#LONG_UNIT_1").val();
		var WIDE_UNIT_1 = $("#WIDE_UNIT_1").val();
		var HIGH_UNIT_1 = $("#HIGH_UNIT_1").val();
		var VOLUME_UNIT_1 =  $("#VOLUME_UNIT_1").val();
		var TOTAL_WEIGHT_1 =  $("#TOTAL_WEIGHT_1").val();

		if(LONG_UNIT_1 ==null || LONG_UNIT_1 =='') {
			LONG_UNIT_1 = 0;
		}
		if(WIDE_UNIT_1 ==null || WIDE_UNIT_1 =='') {
			LONG_UNIT_1 = 0;
		}
		if(HIGH_UNIT_1 ==null || HIGH_UNIT_1 =='') {
			HIGH_UNIT_1 = 0;
		}
		if(VOLUME_UNIT_1 ==null || VOLUME_UNIT_1 =='') {
			VOLUME_UNIT_1 = 0;
		}
		if(TOTAL_WEIGHT_1 ==null || TOTAL_WEIGHT_1 =='') {
			TOTAL_WEIGHT_1 = 0;
		}

		$("#LONG_UNIT").val(LONG_UNIT_1);
		$("#WIDE_UNIT").val(WIDE_UNIT_1);
		$("#HIGH_UNIT").val(HIGH_UNIT_1);
		$("#VOLUME_UNIT").val(VOLUME_UNIT_1);
		$("#TOTAL_WEIGHT").val(TOTAL_WEIGHT_1);
	}

	//判断入库单是否存在
	function hadBox(){
		var BOX_NO = $.trim($("#BOX_NO").val());
		if (BOX_NO ==null || BOX_NO=='') {
			$("#INSTOCK_NO").tips({
				side:3,
				msg:'请输入箱号',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}

		$("#BOX_NO").val(BOX_NO);

		$.ajax({
			type: "POST",
			url: '<%=basePath%>boxwv/hadBox.do',
			data: {BOX_NO:BOX_NO,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				//如果不存在
				if("success" != data.result){
					$("#BOX_NO").css('background-color','red');
					$("#BOX_NO").tips({
						side:3,
						msg:'箱号不存在',
						bg:'#AE81FF',
						time:2
					});
					setTimeout("$('#BOX_NO').val('')",3000);
				} else {
					//如果存在，则设置入库单Id与输入框颜色.把该入库单的产品带回来，则之后的产品判断无需与后台打交道
					<%-- BOX_ID, BOX_NO, LONG_UNIT, WIDE_UNIT, HIGH_UNIT, VOLUME_UNIT, TOTAL_SUTTLE, TOTAL_WEIGHT--%>
					$("#BOX_ID").val(data.BOX_ID);
					$("#LONG_UNIT").val(data.LONG_UNIT);
					$("#WIDE_UNIT").val(data.WIDE_UNIT);
					$("#HIGH_UNIT").val(data.HIGH_UNIT);
					$("#VOLUME_UNIT").val(data.VOLUME_UNIT);
					$("#TOTAL_WEIGHT").val(data.TOTAL_WEIGHT);

					$("#BOX_NO").css('background-color','green');
				}
			}
		});
	}

	function setWvFlg1(checkb) {
		$("#WV_FLG_2").attr("checked",false);
		/*$("#LONG_UNIT").val('');
		 $("#LONG_UNIT").removeAttr("readOnly");
		 $("#LONG_UNIT").attr("placeholder","0");

		 $("#WIDE_UNIT").val('');
		 $("#WIDE_UNIT").removeAttr("readOnly");
		 $("#WIDE_UNIT").attr("placeholder","0");

		 $("#HIGH_UNIT").val('');
		 $("#HIGH_UNIT").removeAttr("readOnly");
		 $("#HIGH_UNIT").attr("placeholder","0");

		 $("#VOLUME_UNIT").val('');
		 $("#VOLUME_UNIT").removeAttr("readOnly");
		 $("#VOLUME_UNIT").attr("placeholder","0");*/

		if(checkb.checked == true ) {
			$("#LONG_UNIT").val('');
			$("#LONG_UNIT").attr("readOnly","readOnly");
			$("#LONG_UNIT").attr("placeholder"," ");

			$("#WIDE_UNIT").val('');
			$("#WIDE_UNIT").attr("readOnly","readOnly");
			$("#WIDE_UNIT").attr("placeholder"," ");

			$("#HIGH_UNIT").val('');
			$("#HIGH_UNIT").attr("readOnly","readOnly");
			$("#HIGH_UNIT").attr("placeholder"," ");

			$("#VOLUME_UNIT").val('');
			$("#VOLUME_UNIT").attr("readOnly","readOnly");
			$("#VOLUME_UNIT").attr("placeholder"," ");
		} else {
			$("#LONG_UNIT").val('');
			$("#LONG_UNIT").removeAttr("readOnly");
			$("#LONG_UNIT").attr("placeholder","0");

			$("#WIDE_UNIT").val('');
			$("#WIDE_UNIT").removeAttr("readOnly");
			$("#WIDE_UNIT").attr("placeholder","0");

			$("#HIGH_UNIT").val('');
			$("#HIGH_UNIT").removeAttr("readOnly");
			$("#HIGH_UNIT").attr("placeholder","0");

			$("#VOLUME_UNIT").val('');
			$("#VOLUME_UNIT").removeAttr("readOnly");
			$("#VOLUME_UNIT").attr("placeholder","0");
		}

	}

	function setWvFlg2(checkb) {
		$("#WV_FLG_1").attr("checked",false);
		$("#LONG_UNIT").val('');
		$("#LONG_UNIT").removeAttr("readOnly");
		$("#LONG_UNIT").attr("placeholder","0");

		$("#WIDE_UNIT").val('');
		$("#WIDE_UNIT").removeAttr("readOnly");
		$("#WIDE_UNIT").attr("placeholder","0");

		$("#HIGH_UNIT").val('');
		$("#HIGH_UNIT").removeAttr("readOnly");
		$("#HIGH_UNIT").attr("placeholder","0");

		$("#VOLUME_UNIT").val('');
		$("#VOLUME_UNIT").removeAttr("readOnly");
		$("#VOLUME_UNIT").attr("placeholder","0");
	}

	function  calcVolume() {
		var long = $.trim($("#LONG_UNIT").val());
		var wide = $.trim($("#WIDE_UNIT").val());
		var high = $.trim($("#HIGH_UNIT").val());
		if(long ==null || long=='') {
			long = 0;
		}
		if(wide ==null || wide=='') {
			wide = 0;
		}
		if(high ==null || high=='') {
			high = 0;
		}

		var volume = (0.01*long*0.01*wide*0.01*high).toFixed(4);

		$("#VOLUME_UNIT").val(volume);
	}

</script>
<div class="container-fluid" id="main-container">


	<div id="page-content" class="clearfix">

		<div class="row-fluid">
			<!-- 检索  -->
			<form action="boxwv/list.do" method="post" name="Form" id="Form">

				<%-- BOX_ID, BOX_NO, LONG_UNIT, WIDE_UNIT, HIGH_UNIT, VOLUME_UNIT, TOTAL_SUTTLE, TOTAL_WEIGHT--%>
				<input type="hidden" name="BOX_ID" id="BOX_ID" value="${pd.BOX_ID}"/>
				<input type="hidden" name="LONG_UNIT_1" id="LONG_UNIT_1" value="${pd.LONG_UNIT_1}"/>
				<input type="hidden" name="WIDE_UNIT_1" id="WIDE_UNIT_1" value="${pd.WIDE_UNIT_1}"/>
				<input type="hidden" name="HIGH_UNIT_1" id="HIGH_UNIT_1" value="${pd.HIGH_UNIT_1}"/>
				<input type="hidden" name="VOLUME_UNIT_1" id="VOLUME_UNIT_1" value="${pd.VOLUME_UNIT_1}"/>
				<input type="hidden" name="TOTAL_WEIGHT_1" id="TOTAL_WEIGHT_1" value="${pd.TOTAL_WEIGHT_1}"/>

				<input type="hidden" name="BIG_BOX_STATUS" id="BIG_BOX_STATUS" value="${pd.BIG_BOX_STATUS}"/>


				<div class="widget-box">
					<div class="widget-header widget-hea1der-small">
						<h6>称重量体积
						</h6>
						<div class="widget-toolbar">
							<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
						</div>
					</div>
					<div class="widget-body">

						<div class="widget-main padding-3">
							<div class="slim-scroll" data-height="125">
								<div class="content">
									<table id="table_report_1" class="table table-striped  table-hover table-condensed">
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;"></td>
											<td style="padding-top: 13px;" colspan="7">&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
												&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
												<input  type='checkbox' id="WV_FLG_1"  name='WV_FLG_1' value="1" onclick="setWvFlg1(this);"
														<c:if test="${pd.WV_FLG eq 1 }">checked="checked" </c:if>/><span class="lbl padding-top: 40px"></span>
												<strong>只称重</strong>
												&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;
												<input  type='checkbox' id="WV_FLG_2"  name='WV_FLG_2' value="1" onclick="setWvFlg2(this);"
														<c:if test="${pd.WV_FLG eq 1 }">checked="checked" </c:if>/><span class="lbl padding-top: 40px"></span>
												<strong>按件</strong>
											</td>

										</tr>

										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">箱号</td>
											<td><input  class="span11" onblur="hadBox();" style="background-color: yellow" type="text" name="BOX_NO" id="BOX_NO" value="${pd.BOX_NO}" maxlength="32" placeholder=" " title="箱号"/></td>
										</tr>
										<tr>
											<td style="width:70px;text-align: right;padding-top: 13px;">长CM</td>
											<td><input type="number" min="0" onkeyup="calcVolume();" name="LONG_UNIT" id="LONG_UNIT" value="${pd.LONG_UNIT}" maxlength="32" placeholder="0" title="长CM"/></td>

											<td style="width:70px;text-align: right;padding-top: 13px;">宽CM</td>
											<td><input type="number"  min="0"  onkeyup="calcVolume();" name="WIDE_UNIT" id="WIDE_UNIT" value="${pd.WIDE_UNIT}" maxlength="32" placeholder="0" title="宽"/></td>

											<td style="width:70px;text-align: right;padding-top: 13px;">高CM</td>
											<td><input type="number"  min="0"  onkeyup="calcVolume();" name="HIGH_UNIT" id="HIGH_UNIT" value="${pd.HIGH_UNIT}" maxlength="32" placeholder="0" title="高"/></td>
										</tr>
										<tr>

											<td style="width:70px;text-align: right;padding-top: 13px;">毛重KG</td>
											<td><input type="number"  min="0" name="TOTAL_WEIGHT" id="TOTAL_WEIGHT" value="${pd.TOTAL_WEIGHT}" maxlength="8" placeholder="0" title="毛重"/></td>

											<td style="width:70px;text-align: right;padding-top: 13px;">体积CBM</td>
											<td><input type="number" min="0"  name="VOLUME_UNIT" id="VOLUME_UNIT" value="${pd.VOLUME_UNIT}" maxlength="8" placeholder="0" title="体积"/></td>

											<td style="width:70px;text-align: right;padding-top: 13px;">录入条数</td>
											<td><input type="number"  min="0"  readOnly="readOnly" name="IN_CNT" id="IN_CNT" value="${pd.IN_CNT}" maxlength="32" placeholder="0"/></td>
										</tr>
										<%--<tr>
                                            <td style="text-align: center;" colspan="10">
                                                <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                                                <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
                                            </td>
                                        </tr>--%>
									</table>

								</div>
							</div>
						</div>
						<div class="widget-toolbox" align="center">
							<div class="btn-toolbar">
								<div class="btn-group">

									<a class="btn btn-mini btn-primary" onclick="save();"><span class="icon-ok"></span>称量提交</a>
								</div>
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								<div class="btn-group">
									<a class="btn btn-mini btn-info" onclick="copyPro();"><span class="icon-search"></span>复制上一条数据</a>
								</div>
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								<div class="btn-group">
									<a class="btn btn-mini btn-danger" onclick="clean();"><span class="icon-off"></span>完成入库</a>
								</div>
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
								&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;

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
						<th class="center">外箱号</th>
						<th class="center">箱号</th>
						<th class="center">长CM</th>
						<th class="center">宽</th>
						<th class="center">高</th>
						<th class="center">体积</th>
						<th class="center">毛重</th>
						<th class="center">按件</th>
						<%--<th class="center">操作</th>--%>
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
											<label><input type='checkbox' name='ids' value="${var.BOXWV_ID}" /><span class="lbl"></span></label>
										</td>
										<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${var.BIG_BOX_NO}</td>
										<td>${var.BOX_NO}</td>
										<td>${var.LONG_UNIT}</td>
										<td>${var.WIDE_UNIT}</td>
										<td>${var.HIGH_UNIT}</td>
										<td>${var.VOLUME_UNIT}</td>
										<td>${var.TOTAL_WEIGHT}</td>
										<td>${var.WV_FLG}</td>

										<%--<td style="width: 30px;" class="center">
											<div class='hidden-phone visible-desktop btn-group'>

												<c:if test="${QX.edit != 1 && QX.del != 1 }">
													<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
												</c:if>
												<c:if test="${QX.edit == 1 }">
													<a title="编辑" onclick="edit('${var.BOXWV_ID}');" class='btn btn-mini btn-info' data-rel="tooltip" title="" data-placement="left"><i class="icon-edit"></i></a>
												</c:if>
												<c:if test="${QX.del == 1 }">
													<a title="删除" onclick="del('${var.BOXWV_ID}');" class='btn btn-mini btn-danger'  data-rel="tooltip" title="" data-placement="left"><i class="icon-trash"></i></a>
												</c:if>
											</div>
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
		$("#Form").submit();
	}

	//新增
	function add(){
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="新增";
		diag.URL = '<%=basePath%>boxwv/goAdd.do';
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
				var url = "<%=basePath%>boxwv/delete.do?BOXWV_ID="+Id+"&tm="+new Date().getTime();
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
		diag.URL = '<%=basePath%>boxwv/goEdit.do?BOXWV_ID='+Id;
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
							url: '<%=basePath%>boxwv/deleteAll.do?tm='+new Date().getTime(),
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
		window.location.href='<%=basePath%>boxwv/excel.do';
	}
</script>

</body>
</html>

