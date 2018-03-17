<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title></title>
</head>
<body>


<div id="mydiv">

</div>

<script type="text/javascript" src="third-party/jquery-1.10.2.min.js"></script>
<script type="text/javascript">

    $(function () {
        function getUrlParam(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg); //匹配目标参数
            if (r != null) return unescape(r[2]); return null; //返回参数值
        }
        var value = getUrlParam("id");
        $.ajax({
            url:"MessageServlet",
            dataType:"json",
            data:{"id":value,"methodName":"getMessageById"},
            async:true,
            type:"get",
            success:function(data) {
                console.log(data);
                window.document.title=data.tittle;
                $("#mydiv").html(data.message);
            }
        });
    });

</script>
</body>
</html>
