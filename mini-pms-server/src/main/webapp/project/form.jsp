<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>프로젝트생성</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>프로젝트 생성(JSP)</h1>
<form action='add' method='post'>
프로젝트명: <input type='text' name='title'><br>
내용: <textarea name='content' rows='10' cols='60'></textarea><br>
기간: <input type='date' name='startDate'> ~ 
      <input type='date' name='endDate'><br>
팀원: <br>
<ul>
<%
List<Member> members = (List<Member>)request.getAttribute("members");
for (Member m : members) {
%>
  <li><input type='checkbox' name='members' value='<%=m.getNo()%>'><%=m.getName()%></li>
<%} %>
</ul><br>
<button>생성</button>
</form>
</body>
</html>
