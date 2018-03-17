<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div id="mydiv">

</div>

<script type="text/javascript" src="third-party/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
$.ajax({
    url:"MessageServlet",
    data:{id:"7","methodName":"getMessageById"},
    dataType:"text",
    async:true,
    type:"get",
    success:function(data){
        console.log(data);
        $("#mydiv").html(data);
    }
});
</script>
</body>
</html>
