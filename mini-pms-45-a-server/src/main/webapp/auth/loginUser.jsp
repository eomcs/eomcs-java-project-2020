<%@page import="com.eomcs.pms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>로그인사용자</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>로그인 사용자(JSP)</h1>
<%
Member loginUser = (Member) session.getAttribute("loginUser");
%>
사용자 번호: <%=loginUser.getNo()%><br>
이름: <%=loginUser.getName()%><br>
이메일: <%=loginUser.getEmail()%><br>
사진: <img src="../upload/<%=loginUser.getPhoto()%>_120x120.jpg"><br>
전화: <%=loginUser.getTel()%><br>
등록일: <%=loginUser.getRegisteredDate()%><br>
</body></html>

