<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8"/>
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
  <%--  <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>--%>

    <script type="text/javascript">
        //保存
        function save(pid) {
            if ($("#INSTOCK_NO").val() == "") {
                $("#INSTOCK_NO").tips({
                    side: 3,
                    msg: '请输入进货单编号入货单编号',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#INSTOCK_NO").focus();
                return false;
            }
            if ($("#CUSTOMER_ID").val() == "") {
                $("#CUSTOMER_ID").tips({
                    side: 3,
                    msg: '请输入客户',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#CUSTOMER_ID").focus();
                return false;
            }
            if ($("#INSTOCK_TYPE").val() == "") {
                $("#INSTOCK_TYPE").tips({
                    side: 3,
                    msg: '请输入入库类型',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#INSTOCK_TYPE").focus();
                return false;
            }
            if ($("#PRE_INSTOCK_DATE").val() == "") {
                $("#PRE_INSTOCK_DATE").tips({
                    side: 3,
                    msg: '请输入预计入库日期',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#PRE_INSTOCK_DATE").focus();
                return false;
            }
           // action="stockmgrin/${msg}.do"
            var saveEven = $("#MSG_ID").val();
           // alert(saveEven);
            //保存，并停在当前页面
            if (saveEven == "save") {
                if (pid == 1) {
                    $('#Form').attr("action", "stockmgrin/save.do?OPT_EVEN="+pid).submit();
                } else if (pid == 2){
                    $('#Form').attr("action", "stockmgrin/save.do?OPT_EVEN="+pid).submit();
                }
            }

            if (saveEven == "edit") {
                if (pid == 1) {
                    $('#Form').attr("action", "stockmgrin/edit.do?OPT_EVEN="+pid).submit();
                } else  if (pid == 2){
                    $('#Form').attr("action", "stockmgrin/edit.do?OPT_EVEN="+pid).submit();
                } else {
                    $('#Form').attr("action", "stockmgrin/saveConfirm.do?OPT_EVEN="+pid).submit();
                }
            }
           // $("#Form").submit();

            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }




        function getInStockNo2() {
            $("#INSTOCK_NO").val("Test-001");
        }

        function getInStockNo() {
            var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
            if (CUSTOMER_ID == null || CUSTOMER_ID == '') {
                return false;
            }

            var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
            if (INSTOCK_NO != "") {
                $("#INSTOCK_NO").select();
                return false;
            }

            $.ajax({
                type: "POST",
                url: '<%=basePath%>stockmgrin/getInStockNo.do',
                data: {CUSTOMER_ID: CUSTOMER_ID, tm: new Date().getTime()},
                dataType: 'json',
                cache: false,
                success: function (data) {
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
                    $('.date-picker').datepicker({autoclose: true});
                },
                complete: function (XMLHttpRequest, textStatus) {
                    //alert($("#table_orderproperty tr").length);
                    //如果返回值没有空的时候
                    var trLenth = $("#table_orderproperty tr").length;
                    if (trLenth == 0) {
                        $("#table_orderproperty").append("<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>");
                    }
                    //添加双击事件
                    $("#table_prod_dtl tr").dblclick(function() {
                        <c:if test="${QX.edit == 1 }">
                        var rid = $(this).attr('id');
                        if (rid == null || rid == "") {
                            add();
                        } else {
                            edit(rid);
                        }
                        </c:if>
                    });
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
            if (loadCnt == 0) {
                $('.date-picker').datepicker({autoclose: true});
            }
            loadCnt += 1;
            this.focus();
            this.onclick();
        }



    </script>
</head>
<body>
<form action="stockmgrin/${msg }.do" name="Form" id="Form" method="post">
    <input type="hidden" name="MSG_ID" id="MSG_ID" value="${msg }"/>
    <input type="hidden" name="STOCKMGRIN_ID" id="STOCKMGRIN_ID" value="${pd.STOCKMGRIN_ID}"/>
    <input type="hidden" name="P_ID" id="P_ID" value="${pd.P_ID}"/>
    <input type="hidden" name="MODIFY_FLG" id="MODIFY_FLG" value="${pd.MODIFY_FLG}"/>


    <div id="zhongxin">

        <div class="widget-box">
            <div class="widget-header widget-hea1der-small">
                <h6>入库单信息</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-toolbox">
                    <div class="btn-toolbar">
                        <div class="btn-group">
                            <c:if test="${pd==null or (pd.MODIFY_FLG=='1' or pd.MODIFY_FLG==null or pd.MODIFY_FLG=='') }">
                            <a class="btn btn-mini btn-primary"  onclick="save(1);"><span class="icon-flag"></span>保存</a>
                            <a class="btn btn-mini btn-primary"  onclick="save(2);"><span class="icon-ok"></span>保存并返回</a>


                                <c:if test="${pd!=null && pd.STOCKMGRIN_ID != null}">
                                <a class="btn btn-mini btn-success"  onclick="save(3);"><span class="icon-ok"></span>收货确认</a>
                                </c:if>
                            </c:if>
                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</a>
                        </div>
                    </div>
                </div>

                <div class="widget-main padding-3">
                    <div data-height="500">
                        <div class="content">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div class="span7">
                                        <table id="table_report0"  class="table table-striped table-hover table-condensed">

                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">入货单编号<span
                                                        class="red">*</span></td>
                                                <td colspan="7" style="vertical-align:top;">
                                                    <div class="row-fluid input-append">
                                                        <input class="span11"   readonly="readonly" type="text"
                                                               name="INSTOCK_NO" ondblclick="getInStockNo();"
                                                               id="INSTOCK_NO" value="${pd.INSTOCK_NO}"
                                                               maxlength="64" placeholder="" title="入货单编号"/>

										                       <span class="add-on"><a class="icon-file"
                                                                onclick="getInStockNo();" title="生成单号"></a></span>
                                                    </div>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">客户<span class="red">*</span></td>
                                                <td colspan="7" style="vertical-align:top;">
                                                    <c:if test="${not empty pd.CUSTOMER_ID }">
                                                    <input type="hidden" name="CUSTOMER_ID" id="CUSTOMER_ID" value="${pd.CUSTOMER_ID}"/></c:if>

                                                        <select  class="span12" name="CUSTOMER_ID" id="CUSTOMER_ID"
                                                                onchange="customerChange()" <c:if test="${not empty pd.CUSTOMER_ID }">disabled='disabled'</c:if>
                                                                style="vertical-align:top;" maxlength="32">
                                                            <c:forEach items="${customerList}" var="cnt">
                                                                <option value="${cnt.id }"
                                                                        <c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
                                                            </c:forEach>
                                                        </select>

                                                </td>
                                            </tr>

                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">预计入库日期<span
                                                        class="red">*</span></td>
                                                <td><input class="date-picker" name="PRE_INSTOCK_DATE"
                                                           id="PRE_INSTOCK_DATE" value="${pd.PRE_INSTOCK_DATE}"
                                                           type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
                                                           placeholder="" title="预计入库日期"/></td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">实际入库日期</td>
                                                <td><input class="date-picker" name="REAL_INSTOCK_DATE"
                                                           id="REAL_INSTOCK_DATE" value="${pd.REAL_INSTOCK_DATE}"
                                                           type="text" data-date-format="yyyy-mm-dd" readonly="readonly"
                                                           placeholder="" title="实际入库日期"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">入库类型</td>
                                                <td style="vertical-align:top;">
                                                    <select  name="INSTOCK_TYPE" id="INSTOCK_TYPE"
                                                            style="vertical-align:top;" maxlength="32">
                                                        <c:forEach items="${stockInTypeList}" var="cnt">
                                                            <option value="${cnt.id }"
                                                                    <c:if test="${cnt.id == pd.INSTOCK_TYPE }">selected</c:if>>${cnt.value }</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">收货状态</td>
                                                <td style="vertical-align:top;">
                                                    <%--<span style="position:relative">--%>
                                                        <select disabled='disabled'  name="INSTOCK_STATE" id="INSTOCK_STATE"
                                                                style="vertical-align:top;" maxlength="32">
                                                            <c:forEach items="${stockInStateList}" var="cnt">
                                                                <option value="${cnt.id }"
                                                                        <c:if test="${cnt.id == pd.INSTOCK_STATE }">selected</c:if>>${cnt.value }</option>
                                                            </c:forEach>
                                                        </select>
                                                       <%--<div style="position:absolute;width:220px;height:50px;left:0px;top:0px;background:#fff;opacity:0;filter:alpha(opacity=0)">
                                                       </div>
                                                    </span>--%>
                                                 </td>
                                            </tr>

                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">分配状态</td>
                                                <td style="vertical-align:top;">

                                                    <select disabled='disabled' name="ASSIGNED_STATE" id="ASSIGNED_STATE"
                                                            style="vertical-align:top;" maxlength="32">
                                                        <c:forEach items="${assignedList}" var="cnt">
                                                            <option value="${cnt.id }"
                                                                    <c:if test="${cnt.id == pd.ASSIGNED_STATE }">selected</c:if>>${cnt.value }</option>
                                                        </c:forEach>
                                                    </select>

                                                </td>


                                              <td style="width:110px;text-align: right;padding-top: 13px;">码放状态</td>
                                                       <td style="vertical-align:top;">

                                                    <select disabled='disabled' name="PUT_STATE" id="PUT_STATE"
                                                            style="vertical-align:top;" maxlength="32">
                                                        <c:forEach items="${putStateList}" var="cnt">
                                                            <option value="${cnt.id }"
                                                                    <c:if test="${cnt.id == pd.PUT_STATE }">selected</c:if>>${cnt.value }</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">总毛重</td>
                                                <td><input type="number" name="TOTAL_WEIGHT" id="TOTAL_WEIGHT"
                                                           value="${pd.TOTAL_WEIGHT}" maxlength="32" placeholder="0"
                                                           title="总毛重"/></td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">总净重</td>
                                                <td><input type="number" name="TOTAL_SUTTLE" id="TOTAL_SUTTLE"
                                                           value="${pd.TOTAL_SUTTLE}" maxlength="32" placeholder="0"
                                                           title="总净重"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">总体积</td>
                                                <td><input type="number" name="TOTAL_VOLUME" id="TOTAL_VOLUME"
                                                           value="${pd.TOTAL_VOLUME}" maxlength="32" placeholder="0"
                                                           title="总体积"/></td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">总价</td>
                                                <td><input type="number" name="TOTAL_PRICE" id="TOTAL_PRICE"
                                                           value="${pd.TOTAL_PRICE}" maxlength="32" placeholder="0"
                                                           title="总价"/></td>
                                            </tr>
                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">总期望EA数</td>
                                                <td><input type="number" name="TOTAL_EA" id="TOTAL_EA"
                                                           value="${pd.TOTAL_EA}" maxlength="32" placeholder="0"
                                                           title="总期望EA数"/></td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">优先级</td>
                                                <td style="vertical-align:top;">
                                                    <select name="PRIORITY_LEVEL" id="PRIORITY_LEVEL"
                                                            style="vertical-align:top;" maxlength="32">
                                                        <c:forEach items="${priorityLevelList}" var="cnt">
                                                            <option value="${cnt.id }"
                                                                    <c:if test="${cnt.id == pd.PRIORITY_LEVEL }">selected</c:if>>${cnt.value }</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">订单号</td>
                                                <td><input type="text" name="ORDER_NO" id="ORDER_NO"
                                                           value="${pd.ORDER_NO}" maxlength="32" placeholder=""
                                                           title="订单号"/></td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">费用录入已完成
                                                </td>
                                                <td class="left"><input class="input-small" type='checkbox'
                                                                        id="COST_STATE" name='COST_STATE' value="1"
                                                                        <c:if test="${pd.COST_STATE eq 1 }">checked="checked" </c:if>/><span
                                                        class="lbl"></span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">备注</td>
                                                <td colspan="7"><input class="span12" type="text" name="MEMO" id="MEMO"
                                                                       value="${pd.MEMO}" maxlength="32"
                                                                       placeholder="" title="备注"/></td>
                                            </tr>
                                        </table>

                                    </div>


                                    <div class="span5">

                                        订单属性
                                        <div class="widget-box "> <%--style="overflow:auto"--%>

                                            <div class="widget-body">
                                               <%-- <div class="widget-main  no-padding">--%>
                                                   <div class="widget-main  padding-3">
                                                    <div class="slim-scroll" data-height="450">
                                                        <div class="content">
                                                            <table id="table_orderproperty"
                                                                   class="table table-striped  table-hover table-condensed">
                                                                <tbody>

                                                                <c:choose>
                                                                    <c:when test="${not empty inOrderList}">
                                                                        <c:if test="${QX.cha == 1 }">
                                                                            <c:forEach items="${inOrderList}" var="var"
                                                                                       varStatus="vs">
                                                                                <tr>
                                                                                    <td class='center'
                                                                                        style="width: 30px;">${vs.index+1}</td>
                                                                                    <td style="width: 120px;">${var.PROPERTY_KEY}</td>

                                                                                    <td class="input-sm">
                                                                                            <%--如果属性排序小于等于10，则是时间输入字段--%>
                                                                                        <c:if test="${var.SRC_SORT <=10}">
                                                                                            <input class="date-picker"
                                                                                                   name="${var.PROPERTY_COLUM}"
                                                                                                   id="${var.PROPERTY_COLUM}"
                                                                                                   value="${var.PROPERTY_VALUE}" type="text"
                                                                                                   data-date-format="yyyy-mm-dd"
                                                                                                   readonly="readonly"/>
                                                                                        </c:if>
                                                                                            <%--如果属性排序大于10 小于等于20，则是数字输入字段--%>
                                                                                        <c:if test="${var.SRC_SORT >10 && var.SRC_SORT <=20}">

                                                                                            <input type="number"
                                                                                                   name="${var.PROPERTY_COLUM}"
                                                                                                   id="${var.PROPERTY_COLUM}"
                                                                                                   value="${var.PROPERTY_VALUE}"
                                                                                                   maxlength="32"
                                                                                                   placeholder="0"/>
                                                                                        </c:if>

                                                                                            <%--如果属性排序大于10 小于等于20，则是数字输入字段--%>
                                                                                        <c:if test="${var.SRC_SORT >20}">
                                                                                            <input type="text"
                                                                                                   name="${var.PROPERTY_COLUM}"
                                                                                                   id="${var.PROPERTY_COLUM}"
                                                                                                   value="${var.PROPERTY_VALUE}"
                                                                                                   maxlength="32"/>
                                                                                        </c:if>

                                                                                    </td>
                                                                                </tr>

                                                                            </c:forEach>
                                                                        </c:if>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <tr class="main_info">
                                                                            <td colspan="100" class="center">没有订单属性数据
                                                                            </td>
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
                <h6>入库单明细</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-toolbox">
                    <div class="btn-toolbar">
                        <div class="btn-group">
                            <c:if test="${pd.STOCKMGRIN_ID != null and pd.STOCKMGRIN_ID != ''}" >
                                <c:if test="${pd==null or (pd.MODIFY_FLG=='1' or pd.MODIFY_FLG==null or pd.MODIFY_FLG=='') }">
                            <a class="btn btn-mini btn-success"  onclick="addDtl();"><span class="icon-plus"></span>新增</a>
                            <%--<a class="btn btn-mini btn-warning" onclick="upload();"><span class="icon-cloud-upload"></span>上传</a>--%>
                            <a class="btn btn-mini btn-warning"  onclick="upload();"  title="从EXCEL导入"><span class="icon-cloud-upload"></span>导入</a>
                            <a class="btn btn-mini btn-danger"  onclick="makeAll('确定要删除选中的数据吗?');"><span class="icon-trash"></span>删除</a>
                                </c:if>
                            </c:if>
                            <c:if test="${pd.STOCKMGRIN_ID == null or pd.STOCKMGRIN_ID==''}" >
                                <h8 class="alert alert-success">请先保存入库单...</h8>
                            </c:if>
                        </div>
                    </div>
                </div>

                <div class="widget-main padding-3">
                    <div ><%--class="slim-scroll" data-height="125"--%>
                        <div class="content">

                            <table id="table_prod_dtl" class="table table-striped table-bordered table-hover">

                                <thead>
                                <tr>
                                    <th class="center">
                                        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                                    </th>
                                    <c:if test="${pd==null or (pd.MODIFY_FLG=='1' or pd.MODIFY_FLG==null or pd.MODIFY_FLG=='')}">
                                        <th class="center">操作</th>
                                    </c:if>
                                    <%--<th class="center">序号</th>--%>
                                    <th class="center">行号</th>
                                    <th class="center">客户</th>
                                    <th class="center">产品</th>
                                    <th class="center">期望EA数</th>
                                    <th class="center">外箱号</th>
                                    <th class="center">箱号</th>
                                    <th class="center">收货EA</th>
                                    <th class="center">分配EA</th>
                                    <th class="center">码放EA</th>
                                    <th class="center">码放库区</th>
                                    <c:choose>
                                        <c:when test="${not empty showItemList}">
                                            <c:forEach items="${showItemList}" var="var" varStatus="vs">
                                                <th class="center">${var}</th>
                                            </c:forEach>
                                        </c:when>
                                    </c:choose>
                                </tr>
                                </thead>

                                <tbody>

                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty varList}">
                                        <c:if test="${QX.cha == 1 }">
                                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                                <tr id="${var.STOCKDTLMGRIN_ID}">
                                                    <td class='center' <c:if test="${'0'==var.RECEI_FLG}">style="background-color: pink" </c:if>>
                                                        <label><input type='checkbox' name='ids' value="${var.STOCKDTLMGRIN_ID}" /><span class="lbl"></span></label>
                                                    </td>
                                                    <c:if test="${pd==null or (pd.MODIFY_FLG=='1' or pd.MODIFY_FLG==null or pd.MODIFY_FLG=='')}">
                                                        <td style="width: 30px;" class="center">
                                                            <div class='hidden-phone visible-desktop btn-group'>

                                                                <c:if test="${QX.edit != 1 && QX.del != 1 }">
                                                                    <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="icon-lock" title="无权限"></i></span>
                                                                </c:if>

                                                                <c:if test="${QX.edit == 1 }">
                                                                    <a title="编辑" onclick="edit('${var.STOCKDTLMGRIN_ID}');"   class='btn btn-mini btn-info' data-rel="tooltip" title="" data-placement="left"><i class="icon-edit"></i></a>
                                                                </c:if>
                                                                <c:if test="${QX.del == 1 }">
                                                                    <a title="删除" onclick="del('${var.STOCKDTLMGRIN_ID}');"  class='btn btn-mini btn-danger'  data-rel="tooltip" title="" data-placement="left"><i class="icon-trash"></i></a>

                                                                </c:if>
                                                            </div>
                                                        </td>
                                                    </c:if>
                                                    <td <c:if test="${'0'==var.RECEI_FLG}">style="background-color: pink" </c:if>>
                                                    ${var.ROW_NO}
                                                    </td>
                                                    <td <c:if test="${'0'==var.RECEI_FLG}">style="background-color: pink" </c:if>>
                                                      <c:forEach items="${customerList}" var="cnt">
                                                        <c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
                                                      </c:forEach>
                                                    </td>
                                                    <td  name='products' values= "${var.PRODUCT_ID}" <c:if test="${'0'==var.RECEI_FLG}">style="background-color: pink" </c:if>>
                                                        <c:forEach items="${productList}" var="cnt">
                                                            <c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td style="text-align: right;background-color: lightyellow">${var.EA_EA}</td>

                                                    <td <c:if test="${'0'==var.RECEI_FLG}">style="background-color: pink" </c:if>>
                                                    ${var.BIG_BOX_NO}
                                                    </td>
                                                    <td <c:if test="${'0'==var.RECEI_FLG}">style="background-color: pink" </c:if>>
                                                            ${var.BOX_NO}
                                                    </td>
                                                    <td style="text-align: right;background-color: lightgreen">${var.EA_ACTUAL}</td>
                                                    <td style="text-align: right;background-color: lightgreen">${var.ASSIGNED_EA}</td>
                                                    <td style="text-align: right;background-color: lightgreen">${var.PUT_EA}</td>
                                                    <td <c:if test="${'0'==var.RECEI_FLG}">style="background-color: pink" </c:if>>
                                                        <c:forEach items="${storageList}" var="cnt">
                                                            <c:if test="${cnt.id==var.PUT_STORAGE_ID}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <c:forEach items="${showItemNameList}" var="var1" varStatus="vs1">
                                                        <td <c:if test="${'0'==var.RECEI_FLG}">style="background-color: pink" </c:if>>${var[var1]}</td>
                                                    </c:forEach>

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

    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img  src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4></div>

</form>

<!-- 引入 -->
<script src="static/1.9.1/jquery.min.js"></script>
<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
<script src="static/js/bootstrap.min.js"></script>
<script src="static/js/ace-elements.min.js"></script>
<script src="static/js/ace.min.js"></script>
<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->

<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->


<script type="text/javascript" src="static/js/jquery-ui-1.10.2.custom.min.js"></script>
<script type="text/javascript" src="static/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="static/js/jquery.slimscroll.min.js"></script>

<script type="text/javascript">
    $(top.hangge());
    $(function () {

        //单选框
        $(".chzn-select").chosen({search_contains: true});
        $(".chzn-select-deselect").chosen({allow_single_deselect: true});

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
        $("#table_prod_dtl tr").dblclick(function() {
            <c:if test="${QX.edit == 1 }">
            var rid = $(this).attr('id');
            if (rid == null || rid == "") {
                add();
            } else {
                edit(rid);
            }
            </c:if>
        });

        // scrollables
        $('.slim-scroll').each(function () {
            var $this = $(this);
            $this.slimScroll({
                height: $this.data('height') || 100,
                railVisible:true
            });
        });


    });

   /* $(".datepicker").datepicker({
        language: "zh-CN",
        autoclose: true,//选中之后自动隐藏日期选择框
        clearBtn: true,//清除按钮
        todayBtn: true,//今日按钮
        format: "yyyy-mm-dd"//日期格式
    });*/

</script>
<%--增加明细的方法--%>
<script type="text/javascript">
    function addDtl() {
        //var rownum = $("#table_prod_dtl tr").length;

        var str = '';
        for(var i=0;i < document.getElementsByName('products').length;i++)
        {
           if(str=='') str += document.getElementsByName('ids')[i].value;
           else str += ',' + document.getElementsByName('ids')[i].value;
        }

        var ROW_NO =document.getElementsByName('ids').length+1;
        var CUSTOMER_ID = $("#CUSTOMER_ID").val();
        var STOCKMGRIN_ID = $("#STOCKMGRIN_ID").val();
       // alert(CUSTOMER_ID);
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = " 新增入库单明细";
        diag.URL = '<%=basePath%>stockmgrin/goAddDtl.do?CUSTOMER_ID='+CUSTOMER_ID+"&ROW_NO="+ROW_NO+"&STOCKMGRIN_ID="+STOCKMGRIN_ID+"&tm="+new Date().getTime()+"&HAD_PRODS="+str;
       // diag.data={ROW_NO:ROW_NO,CUSTOMER_ID: CUSTOMER_ID, tm: new Date().getTime()},
        diag.Width = 1100;
        diag.Height = 800;
        diag.CancelEvent = function () { //关闭事件

            if (diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none') {
                if ('${page.currentPage}' == '0') {
                    top.jzts();
                    setTimeout("self.location=self.location", 100);
                } else {
                    nextPage(${page.currentPage});
                }
            }
            //刷新明细
            refreshDtl();
            diag.close();
        };
        diag.show();
    }

    function refreshDtl() {
        $('#Form').attr("action", "stockmgrin/edit.do?OPT_EVEN="+"").submit();
        $("#zhongxin").hide();
        $("#zhongxin2").show();
    }

    function refreshDtl2() {

        var STOCKMGRIN_ID = $.trim($("#STOCKMGRIN_ID").val());
        if (STOCKMGRIN_ID == null || STOCKMGRIN_ID == '') {
            return false;
        }
        //清空table 除表头外的所有纪录
        $("#table_prod_dtl tr:not(:first)").empty();
        $.ajax({
            type: "POST",
            url: '<%=basePath%>stockmgrin/getProdDtl.do',
            data: {STOCKMGRIN_ID: STOCKMGRIN_ID, tm: new Date().getTime()},
            dataType: 'json',
            cache: false,
            success: function (data) {
                var trs = "";
                //for (var k in data) {
                    //console.log(data[k]);
                    var json = data.varList;
                    var lenth = json.length;
                    for (var i = 0; i < lenth; i++) {
                        var ROW_NO = json[i]['ROW_NO'];
                        var STOCKDTLMGRIN_ID = json[i]['STOCKDTLMGRIN_ID'];
                        var PRODUCT_ID = json[i]['PRODUCT_ID'];
                        var PRODUCT_TYPE = json[i]['PRODUCT_TYPE'];
                        var PACK_RULE = json[i]['PACK_RULE'];
                        var PACK_UNIT = json[i]['PACK_UNIT'];


                        var CUSTOMER_CN= json[i]['CUSTOMER_CN'];
                        var PRODUCT_CN= json[i]['PRODUCT_CN'];
                         var PRODUCT_TYPE_CN = json[i]['PRODUCT_TYPE_CN'];
                        var PACK_RULE_CN = json[i]['PACK_RULE_CN'];
                        var PACK_UNIT_CN = json[i]['PACK_UNIT_CN'];

                        var SCAN_CODE = json[i]['SCAN_CODE'];
                        var EA_EA = json[i]['EA_EA'];
                        var BOX_NO = json[i]['BOX_NO'];
                        var EA_ACTUAL = json[i]['EA_ACTUAL'];
                        var PUT_EA = json[i]['PUT_EA'];
                        var ASSIGNED_EA = json[i]['ASSIGNED_EA'];
                        var STORAGE_NAME = json[i]['STORAGE_NAME'];

                        trs += "<tr id='"+STOCKDTLMGRIN_ID+ "'>";
                        trs += "<td class='center' style='width: 30px;'><label><input type='checkbox' name='ids' value="+STOCKDTLMGRIN_ID+" /><span class='lbl'></span></label></td>";

                        trs +=  "<td class='center' style='width: 30px;'>"+(i+1)+"</td>";
                        trs += "<td>"+ROW_NO+"</td>";
                        trs += "<td>"+CUSTOMER_CN+"</td>";

                        trs += "<td name='products' values='"+PRODUCT_ID+"'>"+PRODUCT_CN+"</td>";
                        trs += "<td>"+SCAN_CODE+"</td>";
                        trs += "<td>"+EA_EA+"</td>";
                        trs += "<td>"+BOX_NO+"</td>";
                        trs += "<td>"+EA_ACTUAL+"</td>";
                        trs += "<td>"+ASSIGNED_EA+"</td>";
                        trs += "<td>"+PUT_EA+"</td>";
                        trs += "<td>"+STORAGE_NAME+"</td>";

                        trs += "<td style='width: 30px;' class='center'> <div class='hidden-phone visible-desktop btn-group'>";
                        trs += "<a title='编辑' onclick=\"edit('"+STOCKDTLMGRIN_ID+"');\" class='btn btn-mini btn-info' data-rel='tooltip' title='' data-placement='left'><i class='icon-edit'></i></a>";
                        trs += "<a title='删除' onclick=\"del('"+STOCKDTLMGRIN_ID+"');\"  class='btn btn-mini btn-danger'  data-rel='tooltip' title='' data-placement='left'><i class='icon-trash'></i></a>";
                        trs += " </div></td></tr>";
                    }
               // }

                $("#table_prod_dtl").append(trs);
            },
            complete: function (XMLHttpRequest, textStatus) {
                var trLenth = $("#table_prod_dtl tr").length;
                if (trLenth == 0) {
                    $("#table_prod_dtl").append("<tr class='main_info'><td colspan='100' class='center' >没有订单属性数据</td></tr>");
                }

                //添加双击事件
                $("#table_prod_dtl tr").dblclick(function() {
                    <c:if test="${QX.edit == 1 }">
                            var rid = $(this).attr('id');
                            //alert(rid);
                            if (rid == null || rid == "") {
                                add();
                            } else {
                                edit(rid);
                            }
                    </c:if>
                });

                //复选框
                $('table th input:checkbox').on('click' , function(){
                    var that = this;
                    $(this).closest('table').find('tr > td:first-child input:checkbox')
                            .each(function(){
                                this.checked = that.checked;
                                $(this).closest('tr').toggleClass('selected');
                            });

                });



                // scrollables
                $('.slim-scroll').each(function () {
                    var $this = $(this);
                    $this.slimScroll({
                        size:'50px',
                        height: $this.data('height') || 100,
                        railVisible:true,

                      /*  height:'100px',//容器高度,默认250px
                        size:'10px',//滚动条宽度,默认7px
                        position:'left',//滚动条位置,可选值:left,right,默认right
                        color:'#abc',//滚动条颜色,默认#000000
                        alwaysVisible:true,//是否禁用隐藏滚动条,默认false
                        distance:'10px,'//距离边框距离,位置由position参数决定,默认1px
                        start:'.p',//滚动条初始位置,可选值top,bottom,$(selector)--内容元素位置,默认top
                        wheelStep:'12px',滚动条滚动值,默认10px
                        railVisible:true,//滚动条背景轨迹,默认false
                            railColor:'#005612',//滚动条背景轨迹颜色,默认#333333
                            railOpacity:0.8,//滚动条背景轨迹透明度,默认0.2
                            allowPageScroll:true,//滚动条滚动到顶部或底部时是否允许页面滚动,默认false
                            scrollTo:'50px',//跳转到指定的滚动值。可以呼吁任何元素slimScroll已经启用了吗(没试过)
                            scrollBy:'50px'增加/减少当前滚动值由指定的数量(正面或负面)。可以呼吁任何元素slimScroll已经启用(没试过)
                    disableFadeOut:true,//是否禁用鼠标在内容处一定时间不动隐藏滚动条,当设置alwaysVisible为true时该参数无效,默认false
                            touchScrollStep:1000,//可以设置不同的触摸滚动事件的敏感性。负数反转方向滚动,默认200*/

                    });
                });
            }
        });
    }

    //删除
    function del(Id){
        var STOCKMGRIN_ID = $("#STOCKMGRIN_ID").val();
        bootbox.confirm("确定要删除吗?", function(result) {
            if(result) {
                //top.jzts();
                var url = "<%=basePath%>stockmgrin/deleteDtl.do?STOCKDTLMGRIN_ID="+Id+"&STOCKMGRIN_ID="+STOCKMGRIN_ID+"&tm="+new Date().getTime();
                $.get(url,function(data){
                    //nextPage(${page.currentPage});
                    refreshDtl();
                });
            }
        });
    }

    //修改
    function edit(Id){
        var STOCKMGRIN_ID = $("#STOCKMGRIN_ID").val();
        var MODIFY_FLG = $("#MODIFY_FLG").val();

        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title ="编辑入库单明细";
        diag.URL = '<%=basePath%>stockmgrin/goEditDtl.do?STOCKDTLMGRIN_ID='+Id+"&STOCKMGRIN_ID="+STOCKMGRIN_ID+"&MODIFY_FLG="+MODIFY_FLG;
        diag.Width = 1100;
        diag.Height = 800;
        diag.CancelEvent = function(){ //关闭事件
            if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                nextPage(${page.currentPage});
            }
            diag.close();
        };
        diag.show();
    }

    function upload(){
        var STOCKMGRIN_ID = $.trim($("#STOCKMGRIN_ID").val());
        var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag=true;
        diag.Title ="EXCEL 导入到系统";
        diag.URL = '<%=basePath%>stockmgrin/goUploadExcel.do?STOCKMGRIN_ID='+STOCKMGRIN_ID+"&&CUSTOMER_ID="+CUSTOMER_ID;
        diag.Width = 700;
        diag.Height = 450;
        diag.CancelEvent = function(){ //关闭事件

            /*if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
                if('${page.currentPage}' == '0'){
                    top.jzts();
                    setTimeout("self.location.reload()",100);
                }else{
                    nextPage(${page.currentPage});
                }
            }*/
            refreshDtl();
            top.hangge();
            diag.close();
        };
        diag.show();
    }

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
                            url: '<%=basePath%>stockmgrin/deleteDtlAll.do?tm='+new Date().getTime(),
                            data: {DATA_IDS:str},
                            dataType:'json',
                            //beforeSend: validateData,
                            cache: false,
                            success: function(data){
                               /* $.each(data.list, function(i, list){
                                    nextPage(${page.currentPage});
                                });*/
                                refreshDtl();
                                top.hangge();
                            }
                        });
                    }
                }
            }
        });
    }

</script>
</body>
</html>