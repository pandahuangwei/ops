<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

		if($("#C_CODE").val()==""){
			$("#C_CODE").tips({
				side:3,
	            msg:'请输入参数编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#C_CODE").focus();
			return false;
		}
		if($("#C_VALUE").val()==""){
			$("#C_VALUE").tips({
				side:3,
	            msg:'请输入参数名称',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#C_VALUE").focus();
			return false;
		}

		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}

	//删除
	function del(Id){
		bootbox.confirm("确定要删除吗?", function(result) {
			if(result) {
				/*var rownum = $("#table_report2 tr").length;
				alert(rownum);
				if(rownum==2) {
					bootbox.dialog("只剩一个选项了,再删该下拉菜单就用不了...!",
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
				}*/

				//top.jzts();
				var url = "<%=basePath%>select/delete.do?SELECT_ID="+Id+"&tm="+new Date().getTime();
				$.get(url,function(data){
					//nextPage(${page.currentPage});
					refreshDtl();
				});
			}
		});
		top.hangge();
	}

	//批量操作
	function makeAll(msg){

		var rownum = $("#table_report2 tr").length;
		bootbox.confirm(msg, function(result) {
			if(result) {
				var str = '';
				var coutids = 0;
				for(var i=0;i < document.getElementsByName('ids').length;i++)
				{
					if(document.getElementsByName('ids')[i].checked){
						if(str=='') str += document.getElementsByName('ids')[i].value;
						else str += ',' + document.getElementsByName('ids')[i].value;
						coutids += 1;
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
					//如果全选了 不能删除
					/*if(rownum==coutids+1) {
						bootbox.dialog("全部删掉了该下拉菜单就用不了...!",
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
					}*/

					if(msg == '确定要删除选中的数据吗?'){
						//top.jzts();
						$.ajax({
							type: "POST",
							url: '<%=basePath%>select/deleteAll.do?tm='+new Date().getTime(),
							data: {DATA_IDS:str},
							dataType:'json',
							//beforeSend: validateData,
							cache: false,
							success: function(data){
								refreshDtl();
								/*$.each(data.list, function(i, list){
									nextPage(${page.currentPage});
								});*/
							}
						});
					}
				}
			}
		});
	}

	//新增
	function add(){
		top.jzts();

		var GROUP_KEY = $("#GROUP_KEY").val();
		var GROUP_NAME = $("#GROUP_NAME").val();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="新增";
		diag.URL = '<%=basePath%>select/goAddDtl.do?GROUP_KEY='+GROUP_KEY+"&GROUP_NAME="+GROUP_NAME;
		diag.Width = 750;
		diag.Height = 300;
		diag.CancelEvent = function(){ //关闭事件
			/*if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				if('${page.currentPage}' == '0'){
					top.jzts();
					setTimeout("self.location=self.location",100);
				}else{
					nextPage(${page.currentPage});
				}
			}*/
			refreshDtl();
			diag.close();
		};
		diag.show();
	}

	//修改
	function edit(Id){
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag=true;
		diag.Title ="编辑";
		diag.URL = '<%=basePath%>select/goEditDtl.do?SELECT_ID='+Id;
		diag.Width = 750;
		diag.Height = 300;
		diag.CancelEvent = function(){ //关闭事件
			/*if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				nextPage(${page.currentPage});
			}*/
			refreshDtl();
			diag.close();
		};
		diag.show();
	}

	function refreshDtl() {
		var GROUP_KEY = $.trim($("#GROUP_KEY").val());

		//清空table 除表头外的所有纪录
		$("#table_report2 tr:not(:first)").empty();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>select/getDtl.do',
			data: {GROUP_KEY: GROUP_KEY, tm: new Date().getTime()},
			dataType: 'json',
			cache: false,
			success: function (data) {
				var trs = "";
				for (var k in data) {
					//console.log(data[k]);
					var json = data[k];
					var lenth = json.length;
					for (var i = 0; i < lenth; i++) {
						var SELECT_ID = json[i]['SELECT_ID'];
						var GROUP_NAME = json[i]['GROUP_NAME'];
						var C_CODE = json[i]['C_CODE'];
						var C_VALUE = json[i]['C_VALUE'];
						var C_SORT = json[i]['C_SORT'];


						trs +="<tr id='"+SELECT_ID+"'>";
						trs += "<td class='center' style='width: 30px;'>";
						trs += "<label><input type='checkbox' name='ids' value="+SELECT_ID+" /><span class='lbl'></span></label></td>";
						trs += "<td class='center' style='width: 30px;'>"+(i+1)+"</td>";
						trs += "<td>"+GROUP_NAME+"</td>";
						trs += "<td>"+C_CODE+"</td>";
						trs += "<td>"+C_VALUE+"</td>";

						trs += "<td  class='center' style='width: 30px;'>"+C_SORT+"</td>";
						trs += "<td style='width: 30px;' class='center'> <div class='btn-group'>";
						trs += "<a title='编辑' onclick=\"edit('"+SELECT_ID+"');\" class='btn btn-mini btn-info' data-rel='tooltip'  data-placement='left'><i class='icon-edit'></i></a>";
						trs += "<a title='删除' onclick=\"del('"+SELECT_ID+"');\" class='btn btn-mini btn-danger'  data-rel='tooltip' data-placement='left'><i class='icon-trash'></i></a>";

						trs += " </div></td></tr>";
					}
				}

				$("#table_report2").append(trs);
			},
			complete: function (XMLHttpRequest, textStatus) {
				var trLenth = $("#table_report2 tr").length;
				if (trLenth == 0) {
					$("#table_report2").append("<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>");
				}

				$("#table_report2 tr").dblclick(function() {
					<c:if test="${QX.edit == 1 }">
					var rid = $(this).attr('id');
					edit(rid);
					</c:if>
				});
			}
		});
	}



</script>
	</head>
<body>
	<form action="select/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="SELECT_ID" id="SELECT_ID" value="${pd.SELECT_ID}"/>
		<input type="hidden" name="GROUP_KEY" id="GROUP_KEY" value="${pd.GROUP_KEY}"/>
		<input type="hidden" name="GROUP_NAME" id="GROUP_NAME" value="${pd.GROUP_NAME}"/>

		<div id="zhongxin">
			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>${pd.GROUP_NAME}</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-toolbox">
						<div class="btn-toolbar">
							<div class="btn-group">
								<a class="btn btn-mini btn-success" onclick="add();"><span class="icon-plus"></span>新增</a>
								<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><span class="icon-trash"></span>删除</a>
							</div>
						</div>
					</div>

					<div class="widget-main padding-3">
						<div class="slim-scroll" data-height="125">
							<div class="content">
								<table id="table_report2" class="table table-striped table-bordered table-hover">

									<thead>
									<tr>
										<th class="center">
                                        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                                        </th>
										<th class="center">序号</th>
										<th class="center">参数</th>
										<th class="center">选项编码</th>
										<th class="center">选项名称</th>
										<th class="center">选项顺序</th>
										<th class="center">操作</th>
									</tr>
									</thead>

									<tbody>

									<!-- 开始循环 -->
									<c:choose>
										<c:when test="${not empty varList}">
											<c:if test="${QX.cha == 1 }">
												<c:forEach items="${varList}" var="var" varStatus="vs">
													<tr id="${var.SELECT_ID}">
														<td class='center' style="width: 30px;">
                                                                <label><input type='checkbox' name='ids' value="${var.SELECT_ID}" /><span class="lbl"></span></label>
														</td>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td>${var.GROUP_NAME}</td>
														<td>${var.C_CODE}</td>
														<td>${var.C_VALUE}</td>
														<td  class='center' style="width: 30px;">${var.C_SORT}</td>

														<td style="width: 30px;" class="center">
															<div class='btn-group'>

																<c:if test="${QX.edit != 1 && QX.del != 1 }">
																	<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
																</c:if>
																<c:if test="${QX.edit == 1 }">
																	<a title="编辑" onclick="edit('${var.SELECT_ID}');" class='btn btn-mini btn-info' data-rel="tooltip" title="" data-placement="left"><i class="icon-edit"></i></a>
																</c:if>
																<c:if test="${QX.del == 1 }">
																	<a title="删除" onclick="del('${var.SELECT_ID}');" class='btn btn-mini btn-danger'  data-rel="tooltip" title="" data-placement="left"><i class="icon-trash"></i></a>
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
								<%--<div class="page-header position-relative">
									<table style="width:100%;">
										<tr>
											<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>
--%>

							</div>
						</div>
					</div>
				</div>
			</div>


		</div>
		<div class="alert alert-info">温馨提示：如若该选项已经在系统中使用，请不要删除，以免造成历史数据缺失...</div>
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
			
		});

		//添加双击事件
		$("#table_report2 tr").dblclick(function() {
			<c:if test="${QX.edit == 1 }">
			var rid = $(this).attr('id');
			edit(rid);
			</c:if>
		});

		</script>
</body>
</html>