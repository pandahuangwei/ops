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
		
		<style type="text/css">
		#dialog-add,#dialog-message,#dialog-comment{width:100%; height:100%; position:fixed; top:0px; z-index:10000; display:none;}
		.commitopacity{position:absolute; width:100%; height:500px; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
		.commitbox{width:79%; padding-left:81px; padding-top:69px; position:absolute; top:0px; z-index:99999;}
		.commitbox_inner{width:96%; height:235px;  margin:6px auto; background:#efefef; border-radius:5px;}
		.commitbox_top{width:100%; height:230px; margin-bottom:10px; padding-top:10px; background:#FFF; border-radius:5px; box-shadow:1px 1px 3px #e8e8e8;}
		.commitbox_top textarea{width:95%; height:165px; display:block; margin:0px auto; border:0px;}
		.commitbox_cen{width:95%; height:40px; padding-top:10px;}
		.commitbox_cen div.left{float:left;background-size:15px; background-position:0px 3px; padding-left:18px; color:#f77500; font-size:16px; line-height:27px;}
		.commitbox_cen div.left img{width:30px;}
		.commitbox_cen div.right{float:right; margin-top:7px;}
		.commitbox_cen div.right span{cursor:pointer;}
		.commitbox_cen div.right span.save{border:solid 1px #c7c7c7; background:#6FB3E0; border-radius:3px; color:#FFF; padding:5px 10px;}
		.commitbox_cen div.right span.quxiao{border:solid 1px #f77400; background:#f77400; border-radius:3px; color:#FFF; padding:4px 9px;}
		</style>
		
	</head>
	<script type="text/javascript">
		function getLocator(){
			var STORAGE_ID = $.trim($("#STORAGE_ID").val());
			if (STORAGE_ID ==null || STORAGE_ID=='') {
				return false;
			}
			//动态删除select中的所有options
			//alert(document.getElementById("PROVINCE_ID").options.length);
			document.getElementById("LOCATOR_ID").options.length=0;
			$.ajax({
				type: "POST",
				url: '<%=basePath%>assignopt/getLocatorList.do',
				data: {STORAGE_ID:STORAGE_ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#LOCATOR_ID_chzn").remove();
					$("#LOCATOR_ID").append($("<option value=''></option>"));
					for (var k in data) {
						$("#LOCATOR_ID").append($("<option value='"+k+"'>"+data[k]+"</option>"));
					}
				},
				complete: function (XMLHttpRequest, textStatus) {
					$("#LOCATOR_ID").attr("class","chzn-select");
					$(".chzn-select").chosen({search_contains: true});
				}
			});
		}


		//保存
		function save(){

			//收货库区 收货库位 LOCATOR_ID STORAGE_ID
			if($("#STORAGE_ID").val()==""){
				$("#STORAGE_ID").tips({
					side:3,
					msg:'请选择码放库区',
					bg:'#AE81FF',
					time:2
				});
				$("#STORAGE_ID").focus();
				return false;
			}

			if($("#LOCATOR_ID").val()==""){
				$("#LOCATOR_ID").tips({
					side:3,
					msg:'请选择码放库位',
					bg:'#AE81FF',
					time:2
				});
				$("#LOCATOR_ID").focus();
				return false;
			}

				("#Form").submit();
				$("#zhongxin").hide();
				$("#zhongxin2").show();
		}

	</script>
<body>
		
	<!-- 添加属性  -->
	<input type="hidden" name="hcdname" id="hcdname" value="" />
	<input type="hidden" name="msgIndex" id="msgIndex" value="" />
	<div id="dialog-add">
		<div class="commitopacity"></div>
	  	<div class="commitbox">
		  	<div class="commitbox_inner">
		        <div class="commitbox_top">
		        	<br/>
		        	<table>
		        		<tr>
		        			<td style="padding-left: 16px;text-align: right;">箱号：</td><td><input name="boxNo" id="boxNo" type="text" value="" placeholder=" " title="默认值"/></td>
		        			<td style="padding-left: 16px;text-align: right;"></td>
		        			<td>
		        			<div class="commitbox_cen">
				                <div class="left" id="cityname"></div>
				                <div class="right"><span class="save" onClick="saveD()">保存</span>&nbsp;&nbsp;<span class="quxiao" onClick="cancel_pl()">关闭</span></div>
				            </div>
		        			</td>
		        		</tr>

		        	</table>
		        </div>
		  	</div>
	  	</div>
	</div>

	<form action="putlocatoropt/saveScane.do" name="Form" id="Form" method="post">
		<input type="hidden" name="zindex" id="zindex" value="0">
		<div id="zhongxin">
			<div class="widget-box">
				<div class="widget-header widget-hea1der-small">
					<h6>扫描码放</h6>
					<div class="widget-toolbar">
						<a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-main padding-3">
						<div class="slim-scroll">
							<div class="content">
								<table class="table table-striped table-hover table-condensed">
									<tr>
										<td style="width:70px;text-align: right;padding-top: 13px;">码放库区<span class="red">*</span></td>
										<%--<td><input type="text" name="STORAGE_ID" id="STORAGE_ID" value="${pd.STORAGE_ID}" maxlength="32" title="收货库区"/></td>--%>
										<td style="vertical-align:top;">
											<select  class="chzn-select" name="STORAGE_ID" id="STORAGE_ID" data-placeholder=" " style="vertical-align:top;" onchange="getLocator()">
												<option value=""></option>
												<c:forEach items="${storageList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.STORAGE_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>
										&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
										<td style="width:70px;text-align: right;padding-top: 13px;">码放库位<span class="red">*</span></td>
										<td style="vertical-align:top;">
											<select  name="LOCATOR_ID" id="LOCATOR_ID" style="vertical-align:top;" data-placeholder=" ">
												<option value=""></option>
												<c:forEach items="${locatorList}" var="cnt">
													<option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_ID }">selected</c:if>>${cnt.value }</option>
												</c:forEach>
											</select>
										</td>
										&nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp;
										<td style="width:76px;text-align: right;padding-top: 13px;">已扫描箱数</td>
										<td><input  class ="input-small" type="number" readOnly="readOnly" name="BOX_CNT" id="BOX_CNT" value="" placeholder="0" /></td>
									</tr>
								</table>
							</div>
						</div>
					</div>
					<div class="widget-toolbox padding-8 clearfix">
						<div class="btn-group">
							<a class="btn btn-success btn-mini" onclick="dialog_open();"><i class="icon-plus"></i>添加箱号</a>
							<a class="btn btn-primary btn-mini" onclick="save();" id="productc"><i class="icon-camera-retro"></i>扫描码放</a>
							<a class="btn btn-danger btn-mini" onclick="top.Dialog.close();"><i class="icon-off"></i>关闭</a>
						</div>
					</div>
				</div>
			</div>
		<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">箱号</th>
						<th class="center">入库单号</th>
						<th class="center">产品</th>
						<th class="center">校验</th>
						<th class="center">操作</th>
					</tr>
				</thead>

				<tbody id="fields"></tbody>

		</table>

		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
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
	<script src="static/js/myjs/putlocatorscan.js"></script>

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

			<c:if test="${pd != null and pd.success == 'success' }">
			closeW();
			</c:if>

		});

		//删除
		function closeW(){
			bootbox.confirm("码放成功,点击确认关闭窗口.", function(result) {
				if(result) {
					top.Dialog.close();
				}
			});
		}

		function auditBoxNo(boxNo) {
			var result = "";
			$.ajax({
				type: "POST",
				url: '<%=basePath%>putlocatoropt/auditBoxNo.do',
				data: {BOX_NO:boxNo,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				async: false,
				success: function(data){
					result = data.auditRs;
					/*if("success" != data.result){
						return data.auditRs;
					}*/
				},
				complete: function (XMLHttpRequest, textStatus) {
				}
			});
			return result;
		}

		</script>
	
</body>
</html>