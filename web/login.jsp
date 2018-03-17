<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>登录</title>
</head>
<body>
<center>
    <form action="${root}/UserServlet?methodName=login" method="post" id="form1">
    用户名：<input name="username" type="text" id="username" value=""/><br>
    密码：<input name="password" type="password" id="password" value=""/><br>
        <input type="hidden" name="methodName" value="login">
        <input type="submit" value="登陆" onclick="_login();"/><span style="color: red" id="msg">${msg}</span>
    </form>
</center>
<script type="text/javascript" src="third-party/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
    function _login() {
        if($("#username").val().trim()!=""||$("#password").val().trim()!=""){
            $("#form1").submit(true);
        }else{
            $("#msg").html("用户名密码信息不完整");
            $("#form1").submit(false);
        }
    }

</script>
</body>
</html>
