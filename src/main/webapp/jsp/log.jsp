<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>日志页面</title>
    <link rel="stylesheet" href="${path}/statics/boot/css/bootstrap.css">
    <link rel="stylesheet" href="${path}/statics/jqgrid/css/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${path}/statics/boot/js/bootstrap.min.js"></script>
    <script src="${path}/statics/jqgrid/js/jquery.jqGrid.min.js"></script>
    <script src="${path}/statics/jqgrid/js/grid.locale-cn.js"></script>

    <script>
        $(function () {

            $("#aa").jqGrid({
                styleUI: 'Bootstrap',
                url: "${path}/log/queryAllLog",//用来加载远程数据
                datatype: "json",  //用来指定返回数据类型
                cellEdit: false,//开启单元格编辑
                autowidth: true,//自适应父容器
                colNames: ["id", "事件", "用户名", "操作时间", "操作结果"],   //表格标题
               // pager:"#pager",
                closeAfterEdit:true,
                //editurl:"${path}/article/save",
                colModel: [
                    {
                        name: "id"

                    },
                    {
                        name: "thing"
                    },
                    {
                        name: "name"
                    },
                    {
                        name: "date"
                    },
                    {
                        name: "flag"
                    }
                ]
            }).jqGrid('navGrid', '#pager', {edit : false,add : false,del : true});

        });

/*        function update(id) {
            location.href="${pageContext.request.contextPath}/article/modifyQueryOne?id="+id;
        }
        function deleteById(id) {

            if (id != null)
                jQuery("#aa").jqGrid('delGridRow', id, {
                    reloadAfterSubmit: false
                });
            else {
                alert("Please Select Row to delete!");
            }};*/


    </script>

</head>


<body>


<div class="container-fluid">
    <div class="panel panel-default">
        <!-- Default panel contents -->

        <div class="panel-heading">日志列表</div>

        <div class="panel-body">
            <%--<p><a class="btn btn-primary btn-sm" href="${path}/jsp/write.jsp" role="button">写博客</a></p>--%>
        </div>
        <table id="aa">

        </table>
        <div id="pager"></div>

    </div>
</div>
</body>
</html>







<%--





<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="path"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>日志页面</title>
    <link rel="stylesheet" href="${path}/statics/boot/css/bootstrap.css">
    <link rel="stylesheet" href="${path}/statics/jqgrid/css/ui.jqgrid-bootstrap.css">
    &lt;%&ndash;引入js文件&ndash;%&gt;
    <script src="${path}/statics/boot/js/jquery-3.3.1.min.js"></script>
    <script src="${path}/statics/boot/js/bootstrap.min.js"></script>
    <script src="${path}/statics/jqgrid/js/jquery.jqGrid.min.js"></script>
    <script src="${path}/statics/jqgrid/js/grid.locale-cn.js"></script>

    <script>
        $(function () {

            $("#aa").jqGrid({
                styleUI: 'Bootstrap',
                url: "${path}/log/queryAllLog",//用来加载远程数据
                datatype: "json",  //用来指定返回数据类型
                cellEdit: false,//开启单元格编辑
                autowidth: true,//自适应父容器
                colNames: ["id", "事件", "用户名", "操作时间", "操作结果"],   //表格标题
                pager:"#pager",
                closeAfterEdit:true,
                colModel: [
                    {
                        name: "id"

                    },
                    {
                        name: "thing"
                    },
                    {
                        name: "name"
                    },
                    {
                        name: "date"
                    },
                    {
                        name: "flag"
                    }

                ]
            });

        });

    </script>

</head>


<body>


</body>
</html>--%>
