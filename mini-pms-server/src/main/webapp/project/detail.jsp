<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.util.List"%>
<%@page import="com.eomcs.pms.domain.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
private int getMemberState(List<Member> projectMembers, Member member) {
  for (Member projectMember : projectMembers) {
    if (member.getNo() == projectMember.getNo()) {
      return projectMember.getState();
    } 
  }
  return -1;
}
%>
<!DOCTYPE html>
<html>
<head>
<title>프로젝트정보</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>프로젝트 정보(JSP)</h1>
<%
Project project = (Project) request.getAttribute("project");
if (project == null) {
  response.setHeader("Refresh", "2;url=list");%>
  <p>해당 번호의 프로젝트가 없습니다.</p>
<%
} else {
%>
<form action='update' method='post'>
<input type='hidden' name='no' value='<%=project.getNo()%>'>
프로젝트명: <input type='text' name='title' value='<%=project.getTitle()%>'><br>
내용: <textarea name='content' rows='10' cols='70'><%=project.getContent()%></textarea><br>
기간: <input type='date' name='startDate' value='<%=project.getStartDate()%>'> ~ 
      <input type='date' name='endDate' value='<%=project.getEndDate()%>'><br>
관리자: <%=project.getNo()%><br>
팀원: * 는 비활성 상태의 멤버<br>
<% 
List<Member> members = (List<Member>) request.getAttribute("members");
List<Member> projectMembers = project.getMembers();
for (Member m : members) {
%>
<input type='checkbox' name='members' 
       value='<%=m.getNo()%>' 
       <%=getMemberState(projectMembers, m) == 1 ? "checked" : ""%>>
<%=m.getName()%><%=getMemberState(projectMembers, m) == 0 ? "*" : ""%>, 
<%} %>
<br>
<button>변경</button>
<a href='delete?no=<%=project.getNo()%>'>[삭제]</a>
<a href='list'>[목록]</a>
</form>
<hr>
작업:<br>

<jsp:include page="/task/list?no=<%=project.getNo()%>"></jsp:include>

<%} %>
</body>
</html>
