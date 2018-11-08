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


    </script>
</head>
<body>
<form action="putlocatoropt/goLook.do" name="Form" id="Form" method="post">
    <input type="hidden" name="STOCKMGRIN_ID" id="STOCKMGRIN_ID" value="${pd.STOCKMGRIN_ID}">
      <div id="zhongxin">

        <div class="widget-box">
            <div class="widget-header widget-hea1der-small">
                <h6>码放分配明细</h6>
                <div class="widget-toolbar">
                    <a href="#" data-action="collapse"><i class="icon-chevron-up"></i></a>
                </div>
            </div>
            <div class="widget-body">
                <div class="widget-toolbox">
                    <div class="btn-toolbar">
                        <div class="btn-group">
                            <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();"><span class="icon-off"></span>关闭</a>
                        </div>
                    </div>
                </div>

                <div class="widget-main padding-3">
                    <div> <%-- class="slim-scroll" data-height="125"--%>
                        <div class="content">

                            <table id="table_prod_dtl" class="table table-striped table-bordered table-hover">

                                <thead>
                                <tr>
                                   <%-- <th class="center">
                                        <label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
                                    </th>--%>
                                    <th class="center">行号</th>
                                    <th class="center">入库单</th>
                                    <th class="center">客户</th>
                                    <th class="center">产品</th>
                                    <th class="center">OutCarton</th>
                                    <th class="center">InCarton</th>
                                    <th class="center">收货状态</th>
                                    <th class="center">分配状态</th>
                                    <th class="center">分配库位</th>
                                    <th class="center">码放状态</th>
                                    <th class="center">码放库位</th>
                                    <th class="center">收货EA</th>
                                    <th class="center">分配EA</th>
                                    <th class="center">码放EA</th>

                                </tr>
                                </thead>

                                <tbody>

                                <!-- 开始循环 -->
                                <c:choose>
                                    <c:when test="${not empty varList}">
                                        <c:if test="${QX.cha == 1 }">
                                            <c:forEach items="${varList}" var="var" varStatus="vs">
                                                <tr id="${var.STOCKDTLMGRIN_ID}">

                                                    <td>${var.ROW_NO}</td>
                                                    <td>${var.INSTOCK_NO}</td>

                                                    <td>
                                                        <c:forEach items="${customerList}" var="cnt">
                                                            <c:if test="${cnt.id==var.CUSTOMER_ID}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td>
                                                        <c:forEach items="${allProductList}" var="cnt">
                                                            <c:if test="${cnt.id==var.PRODUCT_ID}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td>${var.BIG_BOX_NO}</td>
                                                    <td>${var.BOX_NO}</td>
                                                        <%--收货状态--%>
                                                    <td <c:if test="${75==var.RECEIPT_STATE}">style="background-color: red" </c:if>
                                                        <c:if test="${76==var.RECEIPT_STATE}">style="background-color: yellow" </c:if>
                                                        <c:if test="${77==var.RECEIPT_STATE}">style="background-color: green" </c:if>>

                                                        <c:forEach items="${stockInStateList}" var="cnt">
                                                            <c:if test="${cnt.id==var.RECEIPT_STATE}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>

                                                        <%--分配状态--%>
                                                    <td <c:if test="${100==var.DISTR_STATE}">style="background-color: red" </c:if>
                                                        <c:if test="${101==var.DISTR_STATE}">style="background-color: yellow" </c:if>
                                                        <c:if test="${102==var.DISTR_STATE}">style="background-color: green" </c:if>>
                                                        <c:forEach items="${assignedList}" var="cnt">
                                                            <c:if test="${cnt.id==var.DISTR_STATE}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <td <c:if test="${'qsfp'==var.LOCATOR_ID}">style="background-color: pink" </c:if>
                                                    >
                                                        <c:forEach items="${allLocatorForShowList}" var="cnt">
                                                            <c:if test="${cnt.id==var.LOCATOR_ID}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>

                                                    <%--码放状态--%>
                                                    <td <c:if test="${80==var.PUT_STATUS}">style="background-color: red" </c:if>
                                                        <c:if test="${81==var.PUT_STATUS}">style="background-color: yellow" </c:if>
                                                        <c:if test="${82==var.PUT_STATUS}">style="background-color: green" </c:if>>

                                                        <c:forEach items="${putStateList}" var="cnt">
                                                            <c:if test="${cnt.id==var.PUT_STATUS}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>
                                                    <%--码放库位--%>
                                                    <td <c:if test="${'qsfp'==var.PUT_LOCATOR_ID}">style="background-color: pink" </c:if>
                                                    >
                                                        <c:forEach items="${allLocatorForShowList}" var="cnt">
                                                            <c:if test="${cnt.id==var.PUT_LOCATOR_ID}">${cnt.value}</c:if>
                                                        </c:forEach>
                                                    </td>

                                                    <td>${var.EA_ACTUAL}</td>
                                                    <td>${var.ASSIGNED_EA}</td>
                                                    <td>${var.PUT_EA}</td>
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

        // scrollables
        $('.slim-scroll').each(function () {
            var $this = $(this);
            $this.slimScroll({
                height: $this.data('height') || 100,
                railVisible:true
            });
        });

    });

</script>
<%--增加明细的方法--%>
<script type="text/javascript">


</script>
</body>
</html>