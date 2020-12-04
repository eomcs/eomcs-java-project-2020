<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="com.eomcs.pms.domain.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>작업등록</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>작업 등록(JSP)</h1>
<%
Project project = (Project) request.getAttribute("project");
%>
<form action='add' method='post'>
<input type='hidden' name='projectNo' value='<%=project.getNo()%>'>
프로젝트명: <%=project.getTitle()%><br>
작업내용: <textarea name='content' rows='10' cols='60'></textarea><br>
마감일: <input type='date' name='deadline'><br>
담당자: <br>
<p>
<%for (Member m : project.getMembers()) {
  if (m.getState() == 0) continue;
%>
  <input type='radio' name='owner' value='<%=m.getNo()%>'><%=m.getName()%>, 
<%} %> 
</p>
작업상태: 
<select name='status'>
  <option value='0'>준비</option>
  <option value='1'>진행중</option>
  <option value='2'>완료</option>
</select><br>
<button>등록</button>
</form>
</body>
</html>
