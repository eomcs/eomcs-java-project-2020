<%@page import="com.eomcs.pms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Refresh" content="2;url=../index.html">
  <title>로그아웃</title>
</head>
<body>
<h1>로그아웃(JSP)</h1>
<%
Member loginUser = (Member) request.getAttribute("loginUser");
if (loginUser == null) {%>
  <p>로그인 된 상태가 아닙니다.</p>
<%} else { %>
  <p><%=loginUser.getName() %> 님 안녕히 가세요!</p>
<%} %>
</body></html>
