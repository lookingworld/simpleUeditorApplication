<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>目录展示</title>
</head>
<body>


<div id="mydiv">

</div>

<script type="text/javascript" src="third-party/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
    function dateFtt(fmt,date)
    { //author: meizz
        var o = {
            "M+" : date.getMonth()+1,                 //月份
            "d+" : date.getDate(),                    //日
            "h+" : date.getHours(),                   //小时
            "m+" : date.getMinutes(),                 //分
            "s+" : date.getSeconds(),                 //秒
            "q+" : Math.floor((date.getMonth()+3)/3), //季度
            "S"  : date.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }
    $.ajax({
    url:"MessageServlet",
    dataType:"json",
    data:{"methodName":"getAllMessage"},
    async:true,
    type:"post",
    success:function(data) {
        console.log(data);
        for (var i = 0; i < data.length; i++) {
            var $div = $("<span>"+(i+1)+"</span><span><a href='demo4.jsp?id="+data[i].id+"' target='_blank'>" + data[i].tittle + "</a></span>" +
                "&nbsp;<span>"+data[i].description+"</span>&nbsp;<span>"+top.dateFtt("yyyy-MM-dd hh:mm:ss",new Date(data[i].createtime))+"</span><br>");
            $("#mydiv").append($div);
         }
    }
});
</script>
</body>
</html>
