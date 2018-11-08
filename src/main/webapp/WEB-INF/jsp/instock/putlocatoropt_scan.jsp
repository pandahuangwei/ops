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
	<script type="text/javascript">
		function getLocator(){
			var LOCATOR_CODE = $.trim($("#LOCATOR_CODE").val());
			if($("#LOCATOR_CODE").val()==""){
				$("#LOCATOR_CODE").tips({
					side:3,
					msg:'请输入码放库位',
					bg:'#AE81FF',
					time:2
				});
				$("#LOCATOR_CODE").focus();
				return false;
			}

			$("#LOCATOR_CODE").val(LOCATOR_CODE);
             var LOCATOR_ID  ="";
			$.ajax({
				type: "POST",
				url: '<%=basePath%>putlocatoropt/getLocatorList.do',
				data: {LOCATOR_CODE:LOCATOR_CODE,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				async: false,
				success: function(data){
					LOCATOR_ID =data.LOCATOR_ID;
					if(data.LOCATOR_ID == '') {
						$("#LOCATOR_CODE").tips({
							side:3,
							msg:'库位不存在!',
							bg:'#AE81FF',
							time:2
						});
						setTimeout("$('#LOCATOR_CODE').val('')",2000);
					} else {
						$("#LOCATOR_ID").val(data.LOCATOR_ID);
					}

				},
				complete: function (XMLHttpRequest, textStatus) {

				}
			});
			return LOCATOR_ID;
		}


		//保存
		function save(){
			var LOCATOR_CODE = $.trim($("#LOCATOR_CODE").val());
			if(LOCATOR_CODE==""){
				$("#LOCATOR_CODE").tips({
					side:3,
					msg:'请输入码放库位',
					bg:'#AE81FF',
					time:2
				});
				$("#LOCATOR_CODE").focus();
				return false;
			}

			var LOCATOR_ID = getLocatorId(LOCATOR_CODE);
			if(LOCATOR_ID == null || LOCATOR_ID =="") {
				$("#LOCATOR_CODE").tips({
					side:3,
					msg:'请输入码放库位',
					bg:'#AE81FF',
					time:2
				});
				$("#LOCATOR_CODE").focus();
				return false;
			} else {
				$("#LOCATOR_ID").val(LOCATOR_ID);
			}

			var zindex = $("#zindex").val();
			if(zindex == null || zindex =="" || zindex ==0) {
                $("#productc").tips({
                    side:3,
                    msg:'没有码放记录需要提交',
                    bg:'#AE81FF',
                    time:2
                });
                $("#productc").focus();
                return false;
			}

			$("#Form").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}

		function getLocatorId(LOCATOR_CODE){
			var LOCATOR_ID  ="";
			$.ajax({
				type: "POST",
				url: '<%=basePath%>putlocatoropt/getLocatorList.do',
				data: {LOCATOR_CODE:LOCATOR_CODE,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				async: false,
				success: function(data){
					LOCATOR_ID =data.LOCATOR_ID;
				}

			});
			return LOCATOR_ID;
		}

	</script>
<body>

	<form action="putlocatoropt/saveScane.do" name="Form" id="Form" method="post">
		<input type="hidden" name="zindex" id="zindex" value="0">
		<input type="hidden" name="LOCATOR_ID" id="LOCATOR_ID">

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
										<td style="width:70px;text-align: right;padding-top: 13px;">库位<span class="red">*</span></td>
										<td><input type="text" onblur="getLocator();" name="LOCATOR_CODE" id="LOCATOR_CODE" value="${pd.LOCATOR_CODE}" maxlength="32" title="码放库位"/>

										<td style="width:70px;text-align: right;padding-top: 13px;">箱号</td>
										<td><input type="text" name="BOX_NO" id="BOX_NO" value="${pd.BOX_NO}" maxlength="32" title="箱号"/>

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
             //为箱号字段绑定回车事件
			$('#BOX_NO').keypress(function(event){
				var c = $(this).val();
				if(event.keyCode == '13')
				{
					saveD();
				}
			});

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


		//保存编辑属性
		function saveD(){

			var BOX_NO =$.trim($("#BOX_NO").val());
			if(BOX_NO==""){
				$("#BOX_NO").tips({
					side:3,
					msg:'箱号不能为空.',
					bg:'#AE81FF',
					time:2
				});
				$("#BOX_NO").focus();
				return false;
			}

			if(isSame(BOX_NO)) {
				$("#BOX_NO").tips({
					side:3,
					msg:'该箱号已经扫描.',
					bg:'#AE81FF',
					time:2
				});
				$("#BOX_NO").focus();
				return false;
			}

			var rs = auditBoxNo(BOX_NO);
			var rs1 = rs.split(',hw,');
			if(rs1[0]!="PASS"){
				$("#BOX_NO").tips({
					side:3,
					msg:''+rs1[0],
					bg:'#AE81FF',
					time:2
				});
				$("#BOX_NO").focus();
				return false;
			}

			var fields = BOX_NO+ ',hw,' + rs;

			arrayField(fields);

			$("#dialog-add").css("display","none");
		}

		//保存属性后往数组添加元素
		function arrayField(value){
			arField[index] = value;
			appendC(value);
		}

		var arField = new Array();
		var index = 0;
		//追加属性列表
		function appendC(value){
			var fieldarray = value.split(',hw,');
			/*var color1 = 'style="background-color: red"';
			if(fieldarray[1] =='PASS') {
				color1 = 'style="background-color: green"';
			}*/
			//green  red
			$("#fields").append(
					'<tr>'+
					'<td class="center" name="ids">'+fieldarray[0]+'<input type="hidden" name="field0'+index+'" value="'+fieldarray[0]+'"></td>'+
					'<td class="center">'+fieldarray[2]+'<input type="hidden" name="field2'+index+'" value="'+fieldarray[2]+'"></td>'+
					'<td class="center">'+fieldarray[3]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'+
					/*'<td class="center"'+color1+'>'+fieldarray[1]+'<input type="hidden" name="field1'+index+'" value="'+fieldarray[1]+'"></td>'+*/

					'<td class="center">'+
					'<input type="hidden" name="field'+index+'" value="'+value+'">'+
					'<a class="btn btn-mini btn-danger" title="删除" onclick="removeField(\''+index+'\')"><i class="icon-trash"></i></a>'+
					'</td>'+
					'</tr>'
			);
			index++;
			$("#zindex").val(index);
			var ROW_NO =document.getElementsByName('ids').length;
			$("#BOX_CNT").val(ROW_NO);
			$("#BOX_NO").val('');
			$("#BOX_NO").focus();
		}

		//保存属性后往数组添加元素
		function arrayField(value){
			arField[index] = value;
			appendC(value);
		}

		//判断属性名是否重复
		function isSame(value){
			for(var i=0;i<arField.length;i++){
				var array0 = arField[i].split(',hw,')[0];
				if(array0 == value){
					return true;
				}
			}
			return false;
		}


		//删除数组添加元素并重组列表
		function removeField(value){
			index = 0;
			$("#fields").html('');
			arField.splice(value,1);
			for(var i=0;i<arField.length;i++){
				appendC(arField[i]);
			}
			var ROW_NO =document.getElementsByName('ids').length;
			$("#BOX_CNT").val(ROW_NO);
		}

		</script>
	
</body>
</html>