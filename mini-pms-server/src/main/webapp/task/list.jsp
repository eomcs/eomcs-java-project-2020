<%@page import="com.eomcs.pms.domain.Task"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! 
private String getStatusLabel(int status) {
  switch (status) {
    case 1:
      return "진행중";
    case 2:
      return "완료";
    default:
      return "준비";
  }
}
%>
<a href='../task/add?projectNo=<%=request.getParameter("no")%>'>새 작업</a><br>
<table border='1'>
<thead>
<tr>
  <th>번호</th>
  <th>작업</th>
  <th>마감일</th>
  <th>작업자</th>
  <th>상태</th>
  <th></th>
</tr>
</thead>

<tbody>
<% 
Exception ex = (Exception) request.getAttribute("exception");
if (ex != null) {%>
<tr>
  <td colspan="6">작업 목록을 가져오는 중 오류 발생!</td>
</tr>
<%} else {
List<Task> tasks = (List<Task>) request.getAttribute("tasks");
for (Task t : tasks) {
%>
<tr>
  <td><%=t.getNo()%></td>
  <td><a href='../task/detail?no=<%=t.getNo()%>'><%=t.getContent()%></a></td>
  <td><%=t.getDeadline()%></td>
  <td><%=t.getOwner().getName()%></td>
  <td><%=getStatusLabel(t.getStatus())%></td>
  <td><a href='../task/delete?no=<%=t.getNo()%>&projectNo=<%=request.getParameter("no")%>'>[삭제]</a></td>
</tr>
<%} 
}%>
</tbody>
</table>