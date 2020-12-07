<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="com.eomcs.pms.domain.Project"%>
<%@page import="com.eomcs.pms.domain.Task"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>작업정보</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>작업 정보(JSP)</h1>
<%
Project project = (Project) request.getAttribute("project");
Task task = (Task) request.getAttribute("task");
%>
<form action='update' method='post'>
<input type='hidden' name='projectNo' value='<%=task.getProjectNo()%>'>
<input type='hidden' name='no' value='<%=task.getNo()%>'>
작업내용: <textarea name='content' rows='10' cols='70'><%=task.getContent()%></textarea><br>
마감일: <input type='date' name='deadline' value='<%=task.getDeadline()%>'><br>
담당자: 
<select name='owner'>
<%for (Member m : project.getMembers()) {
  if (m.getState() == 0) continue;
  String selected = m.getNo() == task.getOwner().getNo() ? "selected" : "";
%>
  <option value='<%=m.getNo()%>' <%=selected%>><%=m.getName()%></option>
<%} %>
</select><br>
작업상태: 
<select name='status'>
<%
String[] stateLabels = {"준비", "진행중", "완료"};
for (int i = 0; i < 3; i++) {
%>
<option value='<%=i%>' <%=i == task.getStatus() ? "selected" : ""%>><%=stateLabels[i]%></option>
<%} %>
</select><br>
<button>변경</button>
<a href='delete?no=<%=task.getNo()%>&projectNo=<%=project.getNo()%>'>[삭제]</a>
<a href='../project/detail?no=<%=project.getNo()%>'>[목록]</a>
</form>
</body>
</html>
