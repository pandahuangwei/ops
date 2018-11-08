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
	<%@ include file="../admin/top.jsp"%>
	</head>
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="datamgr/list.do" method="post" name="Form" id="Form">
				<input type="hidden" id="SEARCH_FLAG" name="SEARCH_FLAG" value="1"/>
			<table>
				<tr>
					<%--<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="field1" value="" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td><input class="span10 date-picker" name="lastLoginStart" id="lastLoginStart" value="${pd.lastLoginStart}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="开始日期"/></td>
					<td><input class="span10 date-picker" name="lastLoginEnd" id="lastLoginEnd" value="${pd.lastLoginEnd}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="结束日期"/></td>
					<td style="vertical-align:top;"> 
					 	<select class="chzn-select" name="field2" id="field2" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
							<option value=""></option>
							<option value="">全部</option>
							<option value="">1</option>
							<option value="">2</option>
					  	</select>
					</td>--%>

                    <td style="vertical-align:top;">
                        <button class="btn btn-primary btn-mini" onclick="search();"   title="检索" data-rel="tooltip">
                            <span class="icon-search"></span>查询</button>
                    </td>

					<td style="vertical-align:top;">
						<c:if test="${QX.add == 1 }">
							<a class="btn btn-mini btn-success" onclick="calc();" title="实时计算表数据" ><span class="icon-pencil"></span>实时查询</a>
						</c:if>
					</td>

                    <td style="vertical-align:top;">
                        <c:if test="${QX.add == 1 }">
                            <a class="btn btn-mini btn-success" onclick="add();"><span class="icon-plus"></span>新增</a>
                        </c:if>
                    </td>

					<td style="vertical-align:top;">
							<c:if test="${QX.del == 1 }">
								<a class="btn btn-mini btn-warning" onclick="cleanAll('确定要清除选中配置的表数据吗?');" title="清除数据" >
									<span class="icon-remove"></span>清除数据
								</a>
							</c:if>
					</td>

                    <td style="vertical-align:top;">
                        <c:if test="${QX.del == 1 }">
                            <a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的配置吗?');" title="删除配置" >
                                <span class="icon-trash"></span>删除选项
                            </a>
                        </c:if>
                    </td>

					<%--<td style="vertical-align:top;">
                        <button class="btn btn-primary btn-mini" onclick="search();"  title="检索" data-rel="tooltip"> <span class="icon-search"></span>查询</button>
					</td>
					<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;">
						<a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><span class="icon-download"></span>下载</a>
					</td>
					</c:if>--%>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<input type="hidden" name="CALC_FLG" id="CALC_FLG" value="${pd.CALC_FLG}"/>
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
						<th class="center">功能名称</th>
						<th class="center">表名</th>
						<th class="center">备注</th>
						<th class="center">总记录</th>
						<th class="center">有效纪录</th>
						<th class="center">无效纪录</th>
						<th class="center">操作</th>
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
									<label><input type='checkbox' name='ids' value="${var.DATAMGR_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>${var.FUNCTION_NAME}</td>
										<td>${var.TABLE_CN}</td>
										<td>${var.TABLE_DESC}</td>
										<td>${var.TOTAL_CNT}</td>
										<td>${var.VALID_CNT}</td>
										<td>${var.NOVALID_CNT}</td>

                                <td style="width: 30px;" class="center">
                                    <div class='hidden-phone visible-desktop btn-group'>

                                        <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                            <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
                                        </c:if>
                                             <c:if test="${QX.edit == 1 }">
                                                    <a title="编辑" onclick="edit('${var.DATAMGR_ID}');" class='btn btn-mini btn-info' data-rel="tooltip"  data-placement="left"><i class="icon-edit"></i></a>
                                                </c:if>
										        &nbsp;
										       <c:if test="${QX.del == 1 }">
											  <a title="清空数据" onclick="clean('${var.DATAMGR_ID}');" class='btn btn-mini btn-warning'  data-rel="tooltip"  data-placement="left"><i class="icon-remove"></i></a>
										      </c:if>
										      &nbsp;
                                                <c:if test="${QX.del == 1 }">
                                                    <a title="删除配置" onclick="del('${var.DATAMGR_ID}');" class='btn btn-mini btn-danger'  data-rel="tooltip"  data-placement="left"><i class="icon-trash"></i></a>
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
				<%--<td style="vertical-align:top;">
					<c:if test="${QX.add == 1 }">
                    <a class="btn btn-mini btn-success" onclick="add();"><span class="icon-plus"></span>新增</a>
					</c:if>
					<c:if test="${QX.del == 1 }">
                    <a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >
                            <span class="icon-remove"></span>删除
					</a>
					</c:if>
				</td>--%>
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
			$("#CALC_FLG").val(0);
			$("#Form").submit();
		}

		function calc(){
			top.jzts();
			$("#CALC_FLG").val(1);
			$("#Form").submit();
		}
		
		//新增
		function add(){
			 //top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>datamgr/goAdd.do';
			 diag.Width = 600;
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
					var url = "<%=basePath%>datamgr/delete.do?DATAMGR_ID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}

		function clean(Id){
			bootbox.confirm("确定要清空该表的数据吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>datamgr/clean.do?DATA_IDS="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${page.currentPage});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			// top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>datamgr/goEdit.do?DATAMGR_ID='+Id;
			 diag.Width = 600;
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
						if(msg == '确定要删除选中的配置吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>datamgr/deleteAll.do?tm='+new Date().getTime(),
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

		//批量操作
		function cleanAll(msg){
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
						if(msg == '确定要清除选中配置的表数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>datamgr/cleanAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>datamgr/excel.do';
		}
		</script>
		
	</body>
</html>

