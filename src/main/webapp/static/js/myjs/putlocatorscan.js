
/**
 * @HW
 */	
	//生成
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
		
		$("#Form").submit();
	}
	
	
	//保存编辑属性
	function saveD(){

		var boxNo =$.trim($("#boxNo").val());
		if(boxNo==""){
			$("#boxNo").tips({
				side:3,
				msg:'箱号不能为空.',
				bg:'#AE81FF',
				time:2
			});
			$("#boxNo").focus();
			return false;
		}

		if(isSame(boxNo)) {
			$("#boxNo").tips({
				side:3,
				msg:'该箱号已经扫描.',
				bg:'#AE81FF',
				time:2
			});
			$("#boxNo").focus();
			return false;
		}

		var rs = auditBoxNo(boxNo);
		var rs1 = rs.split(',hw,');
		if(rs1[0]!="PASS"){
			$("#boxNo").tips({
				side:3,
				msg:''+rs1[0],
				bg:'#AE81FF',
				time:2
			});
			$("#boxNo").focus();
			return false;
		}

		var msgIndex = $("#msgIndex").val(); 	 //msgIndex不为空时是修改

		//var rownum = $("#table_report tr").length;

		var fields = boxNo+ ',hw,' + rs;

		if(msgIndex == ''){
			arrayField(fields);
		}else{
			editArrayField(fields,msgIndex);
		}
		$("#dialog-add").css("display","none");
	}
	//打开编辑属性(新增)
	function dialog_open(){
		$("#boxNo").val('');
		$("#dialog-add").css("display","block");

	}
	//打开编辑属性(修改)
	function editField(value,msgIndex){
		var efieldarray = value.split(',hw,');
		$("#dname").val(efieldarray[0]);
		$("#hcdname").val(efieldarray[0]);
		$("#dbz").val(efieldarray[2]);
		$("#boxNo").val(efieldarray[4]);
		$("#msgIndex").val(msgIndex);
		$("#dialog-add").css("display","block");
	}
	//关闭编辑属性
	function cancel_pl(){
		$("#dialog-add").css("display","none");
	}
	//赋值类型
	function setType(value){
		$("#dtype").val(value);
	}
	
	//赋值是否前台录入
	function isQian(value){
		if(value == '是'){
			$("#isQian").val('是');
			$("#boxNo").val("无");
			$("#boxNo").attr("disabled",true);
		}else{
			$("#isQian").val('否');
			$("#boxNo").val('');
			$("#boxNo").attr("disabled",false);
		}
	}
	
	
	var arField = new Array();
	var index = 0;
	//追加属性列表
	function appendC(value){
		var fieldarray = value.split(',hw,');
		var color1 = 'style="background-color: red"';
		if(fieldarray[1] =='PASS') {
			color1 = 'style="background-color: green"';
		}
        //green  red
		$("#fields").append(
			'<tr>'+
			'<td class="center" name="ids">'+fieldarray[0]+'<input type="hidden" name="field0'+index+'" value="'+fieldarray[0]+'"></td>'+
			'<td class="center">'+fieldarray[2]+'<input type="hidden" name="field2'+index+'" value="'+fieldarray[2]+'"></td>'+
			'<td class="center">'+fieldarray[3]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'+
			'<td class="center"'+color1+'>'+fieldarray[1]+'<input type="hidden" name="field1'+index+'" value="'+fieldarray[1]+'"></td>'+

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
	}




	
	//保存属性后往数组添加元素
	function arrayField(value){
		arField[index] = value;
		appendC(value);
	}
	
	//修改属性
	function editArrayField(value,msgIndex){
		arField[msgIndex] = value;
		index = 0;
		$("#fields").html('');
		for(var i=0;i<arField.length;i++){
			appendC(arField[i]);
		}
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
	