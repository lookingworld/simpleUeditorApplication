<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	String rootPath = application.getRealPath( "/" );
//	response.getWriter().write( new ActionEnter( request, rootPath ).exec() );
//	String saveRootPath = (String)session.getAttribute("impath");//获取配置文件中设置的图片的上传路径
	response.getWriter().write(new ActionEnter(request,rootPath).exec());
%>