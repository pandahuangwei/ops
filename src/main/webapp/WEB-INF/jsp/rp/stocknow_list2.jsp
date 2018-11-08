<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
    function getLocator() {
        var STORAGE_ID = $.trim($("#STORAGE_ID").val());
        if (STORAGE_ID == null || STORAGE_ID == '') {
            return false;
        }
        //动态删除select中的所有options
        //alert(document.getElementById("PROVINCE_ID").options.length);
        document.getElementById("LOCATOR_ID").options.length = 0;
        $.ajax({
            type: "POST",
            url: '<%=basePath%>assignopt/getLocatorList.do',
            data: {STORAGE_ID: STORAGE_ID, tm: new Date().getTime()},
            dataType: 'json',
            cache: false,
            success: function (data) {
                $("#LOCATOR_ID_chzn").remove();
                $("#LOCATOR_ID").append($("<option value=''></option>"));
                for (var k in data) {
                    $("#LOCATOR_ID").append($("<option value='" + k + "'>" + data[k] + "</option>"));
                }
            },
            complete: function (XMLHttpRequest, textStatus) {
                $("#LOCATOR_ID").attr("class", "chzn-select");
                $(".chzn-select").chosen({search_contains: true});
            }
        });
    }

    //新增
    function add() {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "新增";
        diag.URL = '<%=basePath%>box/goAdd.do?OPT_EVEN=stockAudit';
        diag.Width = 1200;
        diag.Height = 800;
        diag.CancelEvent = function () { //关闭事件
            $("#Form").submit();
            diag.close();
        };
        diag.show();
    }
</script>

<div class="container-fluid" id="main-container">
    <div id="page-content" class="clearfix">
        <div class="row-fluid">
            <div class="row-fluid">
                <!-- 检索  -->
                <form action="stocknow/list2.do" method="post" name="Form" id="Form">
                    <input type="hidden" id="SEARCH_FLAG" name="SEARCH_FLAG" value="${pd.SEARCH_FLAG}"/>
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
                                    <div class="content">

                                        <table id="table_prod_dtl"
                                               class="table table-striped table-condensed table-hover">

                                            <tr>
                                                <%--<td style="width:70px;text-align: right;padding-top: 13px;">库区</td>
                                                <input type="hidden" id="STORAGE_ID_S" name="STORAGE_ID_S" value="${pd.STORAGE_ID_S}"/>
                                                <td style="vertical-align:top;">
                                                    <select  class="chzn-select" name="STORAGE_ID" id="STORAGE_ID" data-placeholder=" " style="vertical-align:top;" onchange="getLocator()">
                                                        <option value=""></option>
                                                        <c:forEach items="${storageList}" var="cnt">
                                                            <option value="${cnt.id }" <c:if test="${cnt.id == pd.STORAGE_ID }">selected</c:if>>${cnt.value }</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width:70px;text-align: right;padding-top: 13px;">库位</td>
                                                <input type="hidden" id="LOCATOR_ID_S" name="LOCATOR_ID_S" value="${pd.LOCATOR_ID_S}"/>
                                                <td style="vertical-align:top;">
                                                    <select  name="LOCATOR_ID" id="LOCATOR_ID" style="vertical-align:top;" data-placeholder=" ">
                                                        <option value=""></option>
                                                        <c:forEach items="${locatorList}" var="cnt">
                                                            <option value="${cnt.id }" <c:if test="${cnt.id == pd.LOCATOR_ID }">selected</c:if>>${cnt.value }</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>--%>


                                                <td style="width:110px;text-align: right;padding-top: 13px;">客户</td>
                                                <input type="hidden" id="CUSTOMER_ID_S" name="CUSTOMER_ID_S" value="${pd.CUSTOMER_ID_S}"/>
                                                <td><select class="chzn-select" name="CUSTOMER_ID" id="CUSTOMER_ID"
                                                            style="vertical-align:top;" data-placeholder=" ">
                                                    <option value=""></option>
                                                    <c:forEach items="${customerList}" var="cnt">
                                                        <option value="${cnt.id }"
                                                                <c:if test="${cnt.id == pd.CUSTOMER_ID }">selected</c:if>>${cnt.value }</option>
                                                    </c:forEach>
                                                </select>
                                                </td>

                                                <td style="width:70px;text-align: right;padding-top: 13px;">产品</td>
                                                <input type="hidden" id="PRODUCT_ID_S" name="PRODUCT_ID_S"
                                                       value="${pd.PRODUCT_ID_S}"/>
                                                <td style="vertical-align:top;">
                                                    <select class="chzn-select" name="PRODUCT_ID" id="PRODUCT_ID"
                                                            style="vertical-align:top;" data-placeholder=" "
                                                    >
                                                        <option value=""></option>
                                                        <c:forEach items="${productList}" var="cnt">
                                                            <option value="${cnt.id }"
                                                                    <c:if test="${cnt.id == pd.PRODUCT_ID }">selected</c:if>>${cnt.value }</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">箱号</td>
                                                <td><input id="BOX_NO" type="text" name="BOX_NO" value="${pd.BOX_NO}"/>
                                                </td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">外箱号</td>
                                                <td><input id="BIG_BOX_NO" type="text" name="BIG_BOX_NO"
                                                           value="${pd.BIG_BOX_NO}"/></td>

                                            </tr>
                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">库位</td>
                                                <td><input id="LOCATOR_CODE" type="text" name="LOCATOR_CODE"
                                                           value="${pd.LOCATOR_CODE}"/></td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">DateCode
                                                </td>
                                                <td><input id="DATE_CODE" type="text" name="DATE_CODE"
                                                           value="${pd.DATE_CODE}"/></td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">COO</td>
                                                <td><input id="COO" type="text" name="COO" value="${pd.COO}"/></td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">LotCode
                                                </td>
                                                <td><input id="LOT_NO" type="text" name="LOT_NO" value="${pd.LOT_NO}"/>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td style="width:110px;text-align: right;padding-top: 13px;">入库单号</td>
                                                <td><input id="INSTOCK_NO" type="text" name="INSTOCK_NO"
                                                           value="${pd.INSTOCK_NO}"/></td>


                                                <td style="width:70px;text-align: right;padding-top: 13px;">入库日期</td>
                                                <td colspan="3">
                                                    <input type="hidden" id="REAL_INSTOCK_DATE_BEGIN_S"
                                                           name="REAL_INSTOCK_DATE_BEGIN_S"
                                                           value="${pd.REAL_INSTOCK_DATE_BEGIN_S}"/>
                                                    <input type="hidden" id="REAL_INSTOCK_DATE_END_S"
                                                           name="REAL_INSTOCK_DATE_END_S"
                                                           value="${pd.REAL_INSTOCK_DATE_END_S}"/>
                                                    <input class="date-picker" name="REAL_INSTOCK_DATE_BEGIN"
                                                           id="REAL_INSTOCK_DATE_BEGIN"
                                                           value="${pd.REAL_INSTOCK_DATE_BEGIN}" type="text"
                                                           data-date-format="yyyy-mm-dd" readonly="readonly"
                                                           placeholder=""/>
                                                    到
                                                    <input class="date-picker" name="REAL_INSTOCK_DATE_END"
                                                           id="REAL_INSTOCK_DATE_END"
                                                           value="${pd.REAL_INSTOCK_DATE_END}" type="text"
                                                           data-date-format="yyyy-mm-dd" readonly="readonly"
                                                           placeholder=""/>
                                                </td>

                                                <td style="width:110px;text-align: right;padding-top: 13px;">数据时效</td>
                                                <td><select class="chzn-select" name="SEARCH_TYPE" id="SEARCH_TYPE"
                                                            style="vertical-align:top;" data-placeholder=" ">
                                                    <c:forEach items="${rpSearchTypeList}" var="cnt">
                                                        <option value="${cnt.id }"
                                                                <c:if test="${cnt.id == pd.SEARCH_TYPE }">selected</c:if>>${cnt.value }</option>
                                                    </c:forEach>
                                                </select>
                                                </td>

                                            </tr>
                                            <%--<tr>--%>
                                            <%--<td style="width:110px;text-align: right;padding-top: 13px;">入库单号</td>
                                            <input type="hidden" id="INSTOCK_NO_S" name="INSTOCK_NO_S" value="${pd.INSTOCK_NO_S}"/>
                                            <td><input   id="INSTOCK_NO" type="text" name="INSTOCK_NO" value="${pd.INSTOCK_NO}" placeholder=""/></td>

                                            <td style="width:110px;text-align: right;padding-top: 13px;">箱号/板号</td>
                                            <input type="hidden" id="BOX_NO_S" name="BOX_NO_S" value="${pd.BOX_NO_S}"/>
                                            <td><input   id="BOX_NO" type="text" name="BOX_NO" value="${pd.BOX_NO}" placeholder=""/></td>

                                            <td style="width:110px;text-align: right;padding-top: 13px;"></td>
                                            <td></td>

                                            <td style="width:110px;text-align: right;padding-top: 13px;"></td>
                                            <td></td>--%>
                                            <%--
                                           <td style="width:70px;text-align: right;padding-top: 13px;">入库日期</td>
                                           <td colspan="7">

                                               <input class="date-picker" name="REAL_INSTOCK_DATE_BEGIN"
                                                      id="REAL_INSTOCK_DATE_BEGIN" value="${pd.REAL_INSTOCK_DATE_BEGIN}" type="text"
                                                      data-date-format="yyyy-mm-dd" readonly="readonly" placeholder=""/>
                                               到
                                               <input class="date-picker" name="REAL_INSTOCK_DATE_END" id="REAL_INSTOCK_DATE_END"
                                                      value="${pd.REAL_INSTOCK_DATE_END}" type="text"
                                                      data-date-format="yyyy-mm-dd" readonly="readonly"
                                                      placeholder=""/>
                                           </td>
                                            --%>
                                            <%--</tr>--%>
                                        </table>

                                    </div>
                                </div>
                            </div>
                            <div class="widget-toolbox padding-8 clearfix">
                                <div class="btn-group">
                                    <button class="btn btn-primary btn-mini" onclick="search();" title="查询" data-rel="tooltip">
                                        <span class="icon-search"></span>查询</button>
                                    <a class="btn btn-success btn-mini" onclick="downLoad();" title="导出" data-rel="tooltip">
                                        <span class="icon-cloud-download"></span>导出</a>
                                    <button class="btn btn-mini btn-inverse" type="reset"><i class="icon-fire"></i>重置</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 检索  -->

                    <div class="widget-box ">
                        <div class="widget-body">
                            <div class="widget-main  padding-3">
                                <div class="content">
                                    <div style="position:relative; width:1800px; overflow-x:auto;">
                                        <table id="table_report" class="table table-striped table-bordered table-hover">
                                            <thead>
                                            <tr>
                                                <th class="center">入库单号</th>
                                                <th class="center">实际入库日期</th>
                                                <th class="center">客户</th>
                                                <th class="center">料号</th>
                                                <th class="center">入库单明細.外箱号</th>
                                                <th class="center">入库单明細.CaseNumber(内箱号)</th>
                                                <th class="center">入库单明細.DATE CODE 转化</th>
                                                <th class="center">入库单明細.DC</th>
                                                <th class="center">入库单明細.LotCode</th>
                                                <th class="center">入库单明細.BIN</th>
                                                <th class="center">入库单明細.COO</th>
                                                <th class="center">入库单明細.Remark1</th>
                                                <th class="center">入库单明細.Remark2</th>
                                                <th class="center">入库单明細.Remark3</th>
                                                <th class="center">入库单明細.InvNo</th>
                                                <th class="center">库位</th>
                                                <th class="center">原始收货EA</th>
                                                <th class="center">在库EA</th>
                                                <th class="center">分配EA</th>
                                                <th class="center">冻结EA</th>
                                                <th class="center">可用EA</th>
                                                <th class="center">分配状态</th>
                                                <th class="center">码放状态</th>
                                                <th class="center">冻结状态</th>
                                                <th class="center">分配库存状态</th>
                                                <th class="center">订单号(入库)</th>
                                                <th class="center">入库单.THI RefNo</th>
                                                <th class="center">入库单.客户RefNo</th>
                                                <th class="center">入库单明細.Scan Code(料号)</th>
                                                <th class="center">入库单明細.SeparateQty</th>
                                                <th class="center">入库单明細.TEXT03</th>
                                                <th class="center">入库单明細.TEXT07</th>
                                                <th class="center">入库单明細.TEXT08</th>
                                                <th class="center">入库单明細.TEXT09</th>
                                                <th class="center">入库单明細.TEXT11</th>
                                                <th class="center">入库单明細.TEXT12</th>
                                                <th class="center">入库单明細.TEXT14</th>
                                                <th class="center">入库单明細.TEXT17</th>
                                                <th class="center">入库单明細.TEXT20</th>
                                                <th class="center">入库单.Manufacturer</th>
                                                <th class="center">	入库单.Shipper</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            <!-- 开始循环 -->
                                            <c:choose>
                                                <c:when test="${not empty varList}">
                                                    <c:if test="${QX.cha == 1 }">
                                                        <c:forEach items="${varList}" var="var" varStatus="vs">
                                                            <tr id="${var.BOX_ID}">
                                                                <td>${var.INSTOCK_NO}</td>
                                                                <td>${var.REAL_INSTOCK_DATE}</td>
                                                                <td>
                                                                    <c:forEach items="${customerList}" var="cnt">
                                                                        <c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
                                                                    </c:forEach>
                                                                </td>
                                                                <td><%--产品--%>
                                                                    <c:forEach items="${productList}" var="cnt">
                                                                        <c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
                                                                    </c:forEach>
                                                                </td>
                                                                <td>${var.BIG_BOX_NO}</td>
                                                                <td>${var.BOX_NO}</td>
                                                                <td>${var.TIME_TWO}</td>
                                                                <td>${var.DATE_CODE}</td>
                                                                <td>${var.LOT_NO}</td>
                                                                <td>${var.BIN_CODE}</td>
                                                                <td>${var.COO}</td>
                                                                <td>${var.REMARK1}</td>
                                                                <td>${var.REMARK2}</td>
                                                                <td>${var.TXT_SIX}</td>
                                                                <td>${var.INVNO}</td>

                                                                    <%--存储库位--%>
                                                                <td
                                                                        <c:if test="${'qsfp'==var.PUT_LOCATOR}">style="background-color: pink" </c:if>>
                                                                    <c:forEach items="${locatorList}" var="cnt">
                                                                        <c:if test="${cnt.id==var.PUT_LOCATOR}">${cnt.value}</c:if>
                                                                    </c:forEach>
                                                                </td>
                                                                <td style="text-align: right">${var.RECEIV_QTY}</td>
                                                                <td style="text-align: right">${var.IN_EA}</td>
                                                                <td style="text-align: right">${var.EA_ACTUAL}</td>
                                                                <td style="text-align: right">${var.FREEZE_EA}</td>
                                                                <td style="text-align: right">${var.USE_EA}</td>

                                                                    <%--收货状态--%>
                                                                    <%--	<td <c:if test="${75==var.RECEIPT_STATE}">style="background-color: red" </c:if>
                                                                            <c:if test="${76==var.RECEIPT_STATE}">style="background-color: yellow" </c:if>
                                                                            <c:if test="${77==var.RECEIPT_STATE}">style="background-color: green" </c:if>>
                                                                            <c:forEach items="${stockInStateList}" var="cnt">
                                                                                <c:if test="${cnt.id==var.RECEIPT_STATE}">${cnt.value}</c:if>
                                                                            </c:forEach>
                                                                        </td>--%>
                                                                    <%--分配状态--%>

                                                                <td
                                                                        <c:if test="${100==var.ASSIGNED_STATE}">style="background-color: red" </c:if>
                                                                        <c:if test="${101==var.ASSIGNED_STATE}">style="background-color: yellow" </c:if>
                                                                        <c:if test="${102==var.ASSIGNED_STATE}">style="background-color: green" </c:if>>
                                                                    <c:forEach items="${assignedList}" var="cnt">
                                                                        <c:if test="${cnt.id==var.ASSIGNED_STATE}">${cnt.value}</c:if>
                                                                    </c:forEach>
                                                                </td>
                                                                    <%--码放状态--%>
                                                                <td
                                                                        <c:if test="${80==var.PUT_STATUS}">style="background-color: red" </c:if>
                                                                        <c:if test="${81==var.PUT_STATUS}">style="background-color: yellow" </c:if>
                                                                        <c:if test="${82==var.PUT_STATUS}">style="background-color: green" </c:if>
                                                                >
                                                                    <c:forEach items="${putStateList}" var="cnt">
                                                                        <c:if test="${cnt.id==var.PUT_STATUS}">${cnt.value}</c:if>
                                                                    </c:forEach>
                                                                </td>
                                                                    <%--冻结状态--%>
                                                                <td
                                                                        <c:if test="${158==var.FREEZE_STATE}">style="background-color: red" </c:if>
                                                                        <c:if test="${159==var.FREEZE_STATE}">style="background-color: yellow" </c:if>
                                                                        <c:if test="${160==var.FREEZE_STATE}">style="background-color: green" </c:if>
                                                                >
                                                                    <c:forEach items="${freezeStateList}" var="cnt">
                                                                        <c:if test="${cnt.id==var.FREEZE_STATE}">${cnt.value}</c:if>
                                                                    </c:forEach>
                                                                </td>
                                                                    <%--分配库存状态--%>
                                                                <td
                                                                        <c:if test="${100==var.ASSIGNED_STOCK_STATE}">style="background-color: red" </c:if>
                                                                        <c:if test="${101==var.ASSIGNED_STOCK_STATE}">style="background-color: yellow" </c:if>
                                                                        <c:if test="${102==var.ASSIGNED_STOCK_STATE}">style="background-color: green" </c:if>>
                                                                    <c:forEach items="${assignedList}" var="cnt">
                                                                        <c:if test="${cnt.id==var.ASSIGNED_STOCK_STATE}">${cnt.value}</c:if>
                                                                    </c:forEach>
                                                                </td>
                                                                <td>${var.ORDER_NO}</td>
                                                                <td>${var.INB_REF_A}</td>
                                                                <td>${var.INVNO_A}</td>
                                                                <td>${var.SCAN_CODE}</td>
                                                                <td style="text-align: right">${var.SEPARATE_QTY}</td>
                                                                <td>${var.TXT_THR}</td>
                                                                <td>${var.TXT_SEV}</td>
                                                                <td>${var.TXT_EIG}</td>
                                                                <td>${var.TXT_NIG}</td>
                                                                <td>${var.TXT_ELEVEN}</td>
                                                                <td>${var.TXT_TWELVE}</td>
                                                                <td>${var.TXT_FOURT}</td>
                                                                <td>${var.TXT_SEVENT}</td>
                                                                <td>${var.TXT_TWENT}</td>
                                                                <td>${var.MANUFACTUER}</td>
                                                                <td>${var.SHIPPER}</td>
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
                                                        <td colspan="100" class="center">没有相关数据</td>
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

                    <div class="page-header position-relative">
                        <table style="width:100%;">
                            <tr>
                                <td style="vertical-align:top;">
                                </td>
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
<script type="text/javascript" src="static/js/bootstrap-colorpicker.min.js"></script>

<script type="text/javascript" src="static/js/daterangepicker.min.js"></script><!-- 日期框 -->

<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
<!-- 引入 -->
<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
<script type="text/javascript">

    $(top.hangge());
    //检索
    function search() {
        top.jzts();
        $("#SEARCH_FLAG").val("1");
        $("#Form").submit();
    }

    function downLoad() {
        //var STORAGE_ID = $.trim($("#STORAGE_ID").val());
        //var LOCATOR_ID = $.trim($("#LOCATOR_ID").val());
        var CUSTOMER_ID = $.trim($("#CUSTOMER_ID").val());
        var PRODUCT_ID = $.trim($("#PRODUCT_ID").val());
        var REAL_INSTOCK_DATE_BEGIN = $.trim($("#REAL_INSTOCK_DATE_BEGIN").val());
        var REAL_INSTOCK_DATE_END = $.trim($("#REAL_INSTOCK_DATE_END").val());
        //var INSTOCK_NO = $.trim($("#INSTOCK_NO").val());
        //	var BOX_NO = $.trim($("#BOX_NO").val());REAL_INSTOCK_DATE_BEGIN_S REAL_INSTOCK_DATE_END_S

        window.location.href = '<%=basePath%>stocknow/excelStockNowDtl.do?SEARCH_FLAG=1&REAL_INSTOCK_DATE_BEGIN=' + REAL_INSTOCK_DATE_BEGIN + "&REAL_INSTOCK_DATE_END=" + REAL_INSTOCK_DATE_END + "&CUSTOMER_ID="
                + CUSTOMER_ID + "&PRODUCT_ID=" + PRODUCT_ID
                +"&SEARCH_TYPE="+$.trim($("#SEARCH_TYPE").val())
                +"&DATE_CODE="+$.trim($("#DATE_CODE").val())
                +"&COO="+$.trim($("#COO").val())
                +"&LOT_NO="+$.trim($("#LOT_NO").val())
                +"&INSTOCK_NO="+$.trim($("#INSTOCK_NO").val());
        /*//top.jzts();
         $("#SEARCH_FLAG").val("1");
         $("#Form").submit();*/

    }


</script>

<script type="text/javascript">

    $(function () {


        //下拉框
        $(".chzn-select").chosen({search_contains: true});
        $(".chzn-select-deselect").chosen({allow_single_deselect: true});

        //日期框
        $('.date-picker').datepicker({
            clearBtn: true,
            autoclose: true
        });

        //复选框
        $('table th input:checkbox').on('click', function () {
            var that = this;
            $(this).closest('table').find('tr > td:first-child input:checkbox')
                    .each(function () {
                        if (!this.disabled) {
                            this.checked = that.checked;
                            $(this).closest('tr').toggleClass('selected');
                        }
                    });

        });
        $("button[type='reset']").click(function () {
            $(':input').attr("value", '');
            $(".chzn-select").each(function () {
                $(this).appendTo('<option value=""></option>');
                $(this).trigger("liszt:updated");
                $(this).chosen();
            });//重置bootstrap-select显示
            $(this).find("option").attr("selected", false);  //重置原生select的值
            $(this).find("option:first").attr("selected", true);
            $("#Form").submit();
        });
        //添加双击事件
        $("#table_report tr").dblclick(function () {

            <c:if test="${QX.edit == 1 }">
            var rid = $(this).attr('id');
            if (rid == null || rid == "") {
            } else {
                look(rid);
            }
            </c:if>
        });


        $('#REAL_INSTOCK_DATE_BEGIN').datepicker({
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true
            //endDate : new Date()
        }).on('changeDate', function (e) {
            var startTime = e.date;
            $('#REAL_INSTOCK_DATE_END').datepicker('setStartDate', startTime);
        });
        //结束时间：
        $('#REAL_INSTOCK_DATE_END').datepicker({
            todayBtn: "linked",
            autoclose: true,
            todayHighlight: true
            //endDate : new Date()
        }).on('changeDate', function (e) {
            var endTime = e.date;
            $('#REAL_INSTOCK_DATE_BEGIN').datepicker('setEndDate', endTime);
        });

    });


    function showDtl(ids) {
        //alert(ids);
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "查看明细";
        diag.URL = '<%=basePath%>stocknow/goLookStockNowDtl.do?IDS=' + ids;
        diag.Width = 1200;
        diag.Height = 800;
        diag.CancelEvent = function () { //关闭事件
            diag.close();
        };
        diag.show();
    }

    //修改
    function look(Id) {
        top.jzts();
        var diag = new top.Dialog();
        diag.Drag = true;
        diag.Title = "查看";
        diag.URL = '<%=basePath%>box/goLook.do?BOX_ID=' + Id;
        diag.Width = 1200;
        diag.Height = 600;
        diag.CancelEvent = function () { //关闭事件
            /*if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
             nextPage(
            ${page.currentPage});
             }*/
            diag.close();
        };
        diag.show();
    }

    //导出excel
    function toExcel() {
        window.location.href = '<%=basePath%>stocknow/excelStockNow.do';
    }
</script>

</body>
</html>

