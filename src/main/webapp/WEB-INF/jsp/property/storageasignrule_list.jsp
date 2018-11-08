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
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">

	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="storageasignrule/list.do" method="post" name="Form" id="Form">
				<input type="hidden" id="SEARCH_FLAG" name="SEARCH_FLAG" value="1"/>
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
											<td style="width:100px;text-align: right;padding-top: 13px;">客户</td>
											<td style="vertical-align:top;">
												<select  class="chzn-select" name="CUSTOMER_ID" id="CUSTOMER_ID" style="vertical-align:top;" data-placeholder=" ">
													<option value=""></option>
													<c:forEach items="${customerList}" var="cnt">
														<option value="${cnt.id }"
																<c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
													</c:forEach>
												</select>
											</td>

											<td style="width:100px;text-align: right;padding-top: 13px;">库位分配编码</td>
											<td><input  type="text" name="STORAGEASING_CODE" id="STORAGEASING_CODE" value="${pd.STORAGEASING_CODE}" maxlength="32" placeholder="" title="库位分配编码"/></td>

											<td style="width:100px;text-align: right;padding-top: 13px;">库位分配名称</td>
											<td><input type="text" name="STORAGEASING_CN" id="STORAGEASING_CN" value="${pd.STORAGEASING_CN}" maxlength="32" placeholder="" title="库位分配名称"/></td>

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
							</div>
						</div>
					</div>
				</div>

			<%--<table>
				<tr>
					<td>
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
					</td>
					<td style="vertical-align:top;">
						<button class="btn btn-primary btn-mini" onclick="search();"   title="检索" data-rel="tooltip">
							<span class="icon-search"></span>查询</button>
					</td>

					<td style="vertical-align:top;">
						<c:if test="${QX.add == 1 }">
							<a class="btn btn-mini btn-success" onclick="add();"><span class="icon-plus"></span>新增</a>
						</c:if>
					</td>

					<td style="vertical-align:top;">
						<c:if test="${QX.del == 1 }">
							<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" >
								<span class="icon-trash"></span>删除
							</a>
						</c:if>
					</td>

					&lt;%&ndash;<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>&ndash;%&gt;
				</tr>
			</table>--%>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th class="center">序号</th>
						<th class="center">客户</th>
						<th class="center">库位分配编码</th>
						<th class="center">库位分配名称</th>
						<%--<th class="center">备注</th>--%>
						<th class="center">库位优先级</th>
						<th class="center">产品限制</th>
						<th class="center">启用</th>
						<th class="center">体积限制</th>
						<th class="center">启用</th>
						<th class="center">长限制</th>
						<th class="center">启用</th>
						<th class="center">宽限制</th>
						<th class="center">启用</th>
						<th class="center">高限制</th>
						<th class="center">启用</th><%--
						<th class="center">创建人</th>
						<th class="center">创建时间</th>
						<th class="center">修改人</th>
						<th class="center">修改时间</th>
						<th class="center">删除</th>--%>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty varList}">
						<c:if test="${QX.cha == 1 }">
						<c:forEach items="${varList}" var="var" varStatus="vs">
							<tr id="${var.STORAGEASIGNRULE_ID}">
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${var.STORAGEASIGNRULE_ID}" /><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
										<td>
											<c:forEach items="${customerList}" var="cnt">
												<c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
											</c:forEach>
										</td>
										<td>${var.STORAGEASING_CODE}</td>
										<td>${var.STORAGEASING_CN}</td>
										<%--<td>${var.MEMO}</td>--%>
								       <%-- <td>${var.SEAT_STATE}</td>--%>
										<td>
											<c:forEach items="${locatorPriLevelList}" var="cnt">
												<c:if test="${fn:contains(var.LOCATOR_PRIORITY_LEVEL,cnt.id)}">${cnt.value}</c:if>
											</c:forEach>
										</td>
										<td>
											<c:forEach items="${productTypeList}" var="cnt">
												<c:if test="${fn:contains(var.PROD_LIMIT,cnt.id)}">${cnt.value}</c:if>
											</c:forEach>
										</td>
										<td class="center"><%--${var.PROD_LIMIT_USE}--%>
											<input disabled="true"   type='checkbox' <c:if test="${var.PROD_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td>${var.VOLUME_LIMIT}</td>
										<td class="center"><%--${var.VOLUME_LIMIT_USE}--%>
											<input disabled="true"   type='checkbox' <c:if test="${var.VOLUME_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td>${var.LONG_LIMIT}</td>
										<td class="center"><%--${var.LONG_LIMIT_USE}--%>
											<input disabled="true"   type='checkbox' <c:if test="${var.LONG_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td>${var.WIDE_LIMIT}</td>
										<td class="center"><%--${var.WIDE_LIMIT_USE}--%>
											<input disabled="true"   type='checkbox' <c:if test="${var.WIDE_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
										<td>${var.HIGH_LIMIT}</td>
										<td class="center"><%--${var.HIGH_LIMIT_USE}--%>
											<input disabled="true"   type='checkbox' <c:if test="${var.HIGH_LIMIT_USE eq 1 }">checked="checked" </c:if>/><span class="lbl"></span>
										</td>
									<%--	<td>${var.CREATE_EMP}</td>
										<td>${var.CREATE_TM}</td>
										<td>${var.MODIFY_EMP}</td>
										<td>${var.MODIFY_TM}</td>
										<td>${var.DEL_FLG}</td>--%>

                                <td style="width: 30px;" class="center">
                                    <div class='hidden-phone visible-desktop btn-group'>

                                        <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                            <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
                                        </c:if>
                                             <c:if test="${QX.edit == 1 }">
                                                    <a title="编辑" onclick="edit('${var.STORAGEASIGNRULE_ID}');" class='btn btn-mini btn-info' data-rel="tooltip" title="" data-placement="left"><i class="icon-edit"></i></a>
                                                </c:if>
                                                <c:if test="${QX.del == 1 }">
                                                    <a title="删除" onclick="del('${var.STORAGEASIGNRULE_ID}');" class='btn btn-mini btn-danger'  data-rel="tooltip" title="" data-placement="left"><i class="icon-trash"></i></a>
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
					<a class="btn btn-small btn-success" onclick="add();">新增</a>
					</c:if>
					<c:if test="${QX.del == 1 }">
					<a class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='icon-trash'></i></a>
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
			$("#Form").submit();
		}
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>storageasignrule/goAdd.do';
			 diag.Width = 800;
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
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>storageasignrule/delete.do?STORAGEASIGNRULE_ID="+Id+"&tm="+new Date().getTime();
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
			 diag.URL = '<%=basePath%>storageasignrule/goEdit.do?STORAGEASIGNRULE_ID='+Id;
			 diag.Width = 800;
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

			//添加双击事件
			$("#table_report tr").dblclick(function() {
				<c:if test="${QX.edit == 1 }">
				var rid = $(this).attr('id');
				edit(rid);
				</c:if>
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
								url: '<%=basePath%>storageasignrule/deleteAll.do?tm='+new Date().getTime(),
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
			window.location.href='<%=basePath%>storageasignrule/excel.do';
		}
		</script>
		
	</body>
</html>

