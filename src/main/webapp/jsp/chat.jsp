<%@page isELIgnored="false" contentType="text/html; UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="./boot/css/bootstrap.min.css">
    <link rel="stylesheet" href="./boot/css/back.css">
    <link rel="stylesheet" href="./jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <link rel="stylesheet" href="./jqgrid/css/jquery-ui.css">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="../boot/js/bootstrap.min.js"></script>
    <script src="../jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="../jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="../boot/js/ajaxfileupload.js"></script>
    <script src="../echarts/echarts.min.js"></script>
    <!-- 将https协议改为http协议 -->
    <script type="text/javascript" src="http://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script type="text/javascript">
        var goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-e16fb777a6b2422fa18ac579f346cf48", //替换为您的应用appkey
            //appkey: "BC-BC-69489dfd130b4b8195d4b1a675650c13", //替换为您的应用appkey
        });
        goEasy.subscribe({
            channel: "cmfz", //替换为您自己的channel
            onMessage: function (message) {
                var data = JSON.parse(message.content);
                $("#chatArea").append("<p>"+data+"</p>");
            }
        });
    </script>
</head>
<body>

<div id="chatArea">
    <p style="color:#2aabd2">聊天室</p>
</div>


<input type="text" id="wen" />
<button id="btn">发送</button>

<script type="text/javascript">
    $(function () {
        $("#btn").click(function () {

            $.ajax({
                type:"get",
                url:"${pageContext.request.contextPath}/chat/sendChat",
                data:{"message":$("#wen").val()},
                dataType:"json",
                success:function (arr) {

                }
            });
            $("#wen").val("");
        });


    });
</script>
</body>