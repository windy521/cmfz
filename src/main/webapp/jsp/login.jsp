<%@page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>love</title>
    <link href="favicon.ico" rel="shortcut icon" />
    <link href="../boot/css/bootstrap.min.css" rel="stylesheet">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script>
       
    </script>
</head>
<body style=" background: url(../img/timg.jpg); background-size: 100%;">


<div class="modal-dialog" style="margin-top: 10%;">
    <div class="modal-content">
        <div class="modal-header">

            <h4 class="modal-title text-center" id="myModalLabel">持明法洲</h4>
        </div>
        <form id="loginForm" method="post" action="${pageContext.request.contextPath}/admin/login">
        <div class="modal-body" id = "model-body">
            <div class="form-group">
                <input type="text" class="form-control"placeholder="用户名" autocomplete="off" name="username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" autocomplete="off" name="password">
            </div>
            <div class="form-group">
                <input type="text" name="clientCode" placeholder="验证码" size="10" maxlength="4"/>
                <img id="tu" src="${pageContext.request.contextPath }/createImg" width="90px"/>
                <a href="javascript:void(0)" onclick="changeImg()" >换一张</a>
            </div>
            <span id="msg"></span>
        </div>
        <div class="modal-footer">
            <div class="form-group">
                <button type="button" id="loginBtn" class="btn btn-primary form-control">登录</button>
            </div>
<%--            <div class="form-group">
                <button type="button" class="btn btn-default form-control">注册</button>
            </div>--%>

        </div>
        </form>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    function changeImg(){
        var obj = document.getElementById("tu");
        obj.src="${pageContext.request.contextPath }/createImg?xxx="+Math.random();
    }
</script>

<script type="text/javascript">
    $(function () {
        $("#loginBtn").click(function () {
            // alert("数据："+ $("#loginForm").serialize());
            $.ajax({
                type:"post",
                url:"${pageContext.request.contextPath}/admin/login",
                //把form表单的值序列化成一个字符串，如username=admin&password=admin123
                //data: $("#addForm").serialize(),
                data: $("#loginForm").serialize(),
                // data:{
                // 	"name":$("#name").val(),
                // 	"mobile":$("#mobile").val(),
                // 	"birthday":$("#birthday").val(),
                // 	"category.categoryid":$("#categoryid").val()
                // },

                dataType:"json",
                success:function (data) {
                    if(data.msg=='登陆成功'){
                        window.location.href="${pageContext.request.contextPath}/jsp/main.jsp"
                    }else {
                        $("#msg").html(data.msg);
                    }

                }
            });
        });

    });
</script>
