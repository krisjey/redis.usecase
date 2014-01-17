<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
	out.println("[{\"value\":\"Some Name\"},{\"value\":\"Some Othername\"} ,{\"value\":\"" +request.getParameter("term") + "\"}]");
	//out.println(request.getParameter("q"));
%>