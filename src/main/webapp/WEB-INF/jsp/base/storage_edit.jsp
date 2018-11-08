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
    <script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="static/js/jquery.tips.js"></script>

    <script type="text/javascript">


        //保存
        function save() {
            if ($("#STOCK_ID").val() == "") {
                $("#STOCK_ID").tips({
                    side: 3,
                    msg: '请选择仓库',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#STOCK_ID").focus();
                return false;
            }

            if ($("#STORAGE_CODE").val() == "") {
                $("#STORAGE_CODE").tips({
                    side: 3,
                    msg: '请输入库区编码',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#STORAGE_CODE").focus();
                return false;
            }
            if ($("#STORAGE_NAME").val() == "") {
                $("#STORAGE_NAME").tips({
                    side: 3,
                    msg: '请输入库区名称',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#STORAGE_NAME").focus();
                return false;
            }
            if ($("#STORAGE_TYPE").val() == "") {
                $("#STORAGE_TYPE").tips({
                    side: 3,
                    msg: '请输入库区类型',
                    bg: '#AE81FF',
                    time: 2
                });
                $("#STORAGE_TYPE").focus();
                return false;
            }

            $("#Form").submit();
            $("#zhongxin").hide();
            $("#zhongxin2").show();
        }

        //判断编码是否存在
        function hasCode() {
            var stockId = $.trim($("#STOCK_ID").val());
            if (stockId == null || stockId == '') {
                $("#STOCK_ID").tips({
                    side: 3,
                    msg: '请选择仓库',
                    bg: '#AE81FF',
                    time: 2
                });
                return false;
            }

            var NUMBER = $.trim($("#STORAGE_CODE").val());
            if (NUMBER == null || NUMBER == '') {
                $("#STORAGE_CODE").tips({
                    side: 3,
                    msg: '请输入代码',
                    bg: '#AE81FF',
                    time: 2
                });
                return false;
            }


            var STORAGEID = $.trim($("#STORAGE_ID").val());
            $.ajax({
                type: "POST",
                url: '<%=basePath%>storage/hasCode.do',
                data: {STOCK_ID:stockId,STORAGEID: STORAGEID, STORAGECODE: NUMBER, tm: new Date().getTime()},
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if ("success" != data.result) {
                        $("#STORAGE_CODE").tips({
                            side: 3,
                            msg: '代码已存在',
                            bg: '#AE81FF',
                            time: 3
                        });
                        setTimeout("$('#STORAGE_CODE').val('')", 2000);
                    }
                }
            });
        }

        //判断名称是否存在
        function hasCn() {
            var stockId = $.trim($("#STOCK_ID").val());
            if (stockId == null || stockId == '') {
                $("#STOCK_ID").tips({
                    side: 3,
                    msg: '请选择仓库',
                    bg: '#AE81FF',
                    time: 2
                });
                return false;
            }

            var NUMBER = $.trim($("#STORAGE_NAME").val());
            if (NUMBER == null || NUMBER == '') {
                $("#STORAGE_NAME").tips({
                    side: 3,
                    msg: '请输入名称',
                    bg: '#AE81FF',
                    time: 2
                });
                return false;
            }
            var STORAGEID = $.trim($("#STORAGE_ID").val());
            $.ajax({
                type: "POST",
                url: '<%=basePath%>storage/hasCn.do',
                data: {STOCK_ID:stockId,STORAGEID: STORAGEID, STORAGENAME: NUMBER, tm: new Date().getTime()},
                dataType: 'json',
                cache: false,
                success: function (data) {
                    if ("success" != data.result) {
                        $("#STORAGE_NAME").tips({
                            side: 3,
                            msg: '名称已存在',
                            bg: '#AE81FF',
                            time: 3
                        });
                        setTimeout("$('#STORAGE_NAME').val('')", 2000);
                    }
                }
            });
        }

    </script>
</head>
<body>
<form action="storage/${msg }.do" name="Form" id="Form" method="post">
    <input type="hidden" name="STORAGE_ID" id="STORAGE_ID" value="${pd.STORAGE_ID}"/>
    <div id="zhongxin">
        <div class="widget-box">
            <div class="widget-header widget-hea1der-small">
                <h6>库区</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-toolbox">
                    <div class="btn-toolbar">
                        <div class="btn-group">
                            <a class="btn btn-mini btn-primary" onclick="save();">保存</a>
                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">关闭</a>
                        </div>
                    </div>
                </div>

                <div class="widget-main padding-3">
                    <div class="slim-scroll" data-height="125">
                        <div class="content">

                            <table id="table_report" class="table table-striped  table-hover table-condensed">

                                <tr>
                                    <td style="width:70px;text-align: right;padding-top: 13px;">仓库<span
                                            class="red">*</span></td>
                                    <td style="vertical-align:top;">
                                        <select class="chzn-select" name="STOCK_ID" id="STOCK_ID"
                                                data-placeholder="请选择仓库" style="vertical-align:top;" maxlength="32">
                                            <option value=""></option>
                                            <c:forEach items="${stockList}" var="cnt">
                                                <option value="${cnt.id }"
                                                        <c:if test="${cnt.id == pd.STOCK_ID }">selected</c:if>>${cnt.value }</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>

                                <tr>
                                    <td style="width:70px;text-align: right;padding-top: 13px;">库区编码<span
                                            class="red">*</span></td>
                                    <td><input type="text" name="STORAGE_CODE" id="STORAGE_CODE"
                                               value="${pd.STORAGE_CODE}" maxlength="32" placeholder="" title="库区编码"
                                               onblur="hasCode()"/></td>
                                </tr>
                                <tr>
                                    <td style="width:70px;text-align: right;padding-top: 13px;">库区名称<span
                                            class="red">*</span></td>
                                    <td colspan="7"><input class="span12" type="text" name="STORAGE_NAME"
                                                           id="STORAGE_NAME" value="${pd.STORAGE_NAME}" maxlength="32"
                                                           placeholder="" title="库区名称" onblur="hasCn()"/></td>
                                </tr>
                                <tr>
                                    <td style="width:70px;text-align: right;padding-top: 13px;">库区类型<span
                                            class="red">*</span></td>
                                    <td style="vertical-align:top;">
                                        <select class="chzn-select" name="STORAGE_TYPE" id="STORAGE_TYPE"
                                                data-placeholder="请选择上级城市" style="vertical-align:top;" maxlength="32">
                                            <option value=""></option>
                                            <c:forEach items="${storageTypeList}" var="cnt">
                                                <option value="${cnt.id }"
                                                        <c:if test="${cnt.id == pd.STORAGE_TYPE }">selected</c:if>>${cnt.value }</option>
                                            </c:forEach>

                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td style="width:70px;text-align: right;padding-top: 13px;">备注</td>
                                    <td colspan="7"><input class="span12" type="text" name="MEMO" id="MEMO"
                                                           value="${pd.MEMO}" maxlength="32" placeholder="" title="备注"/>
                                    </td>
                                </tr>


                                <%--<tr>
                                    <td style="width:70px;text-align: right;padding-top: 13px;">拣货过渡库位</td>
                                    <td><input type="text" name="STORAGE_PICK" id="STORAGE_PICK" value="${pd.STORAGE_PICK}" maxlength="32" placeholder="这里输入拣货过渡库位" title="拣货过渡库位"/></td>
                                </tr>
                                <tr>
                                    <td style="width:70px;text-align: right;padding-top: 13px;">码放过渡库位</td>
                                    <td><input type="text" name="STORAGE_PUT" id="STORAGE_PUT" value="${pd.STORAGE_PUT}" maxlength="32" placeholder="这里输入码放过渡库位" title="码放过渡库位"/></td>
                                </tr>--%>
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
            </div>
        </div>


    </div>

    <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img
            src="static/images/jiazai.gif"/><br/><h4 class="lighter block green">提交中...</h4></div>

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
    $(function () {

        //单选框
        $(".chzn-select").chosen({search_contains: true});
        $(".chzn-select-deselect").chosen({allow_single_deselect: true});

        //日期框
        $('.date-picker').datepicker({autoclose: true});

    });

</script>
</body>
</html>