<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="com.eomcs.pms.domain.Project"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!
private String getMembersString(List<Member> list) {
  StringBuilder members = new StringBuilder();
  for (Member member : list) {
    if (members.length() > 0) {
      members.append(",");
    }
    members.append(member.getName());
  }
  return members.toString();
}
%>
<!DOCTYPE html>
<html>
<head>
<title>프로젝트목록</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>프로젝트 목록(JSP)</h1>
<a href='form'>새 프로젝트</a><br>

<%
List<Project> list = (List<Project>) request.getAttribute("list");
%>

<table border='1'>
<thead>
<tr>
  <th>번호</th>
  <th>프로젝트명</th>
  <th>시작일 ~ 종료일</th>
  <th>관리자</th>
  <th>팀원</th>
</tr>
</thead>

<tbody>
<%for (Project p : list) {%>
<tr>
  <td><%=p.getNo()%></td>
  <td><a href='detail?no=<%=p.getNo()%>'><%=p.getTitle()%></a></td>
  <td><%=p.getStartDate()%> ~ <%=p.getEndDate()%></td>
  <td><%=p.getOwner().getName()%></td>
  <td>[<%=getMembersString(p.getMembers())%>]</td>
</tr>
<%} %>
</tbody>
</table>
<p>
<%
String keyword = request.getParameter("keyword");
%>
<form action='list' method='get'>
검색어: <input type='text' name='keyword' value='<%=keyword != null ? keyword : ""%>'>
<button>검색</button>
</form>
</p>
<hr>
<h2>상세 검색</h2>
<p>
<%
String keywordTitle = request.getParameter("keywordTitle");
String keywordOwner = request.getParameter("keywordOwner");
String keywordMember = request.getParameter("keywordMember");
%>
<form action='list' method='get'>
프로젝트명: <input type='text' name='keywordTitle' 
  value='<%=keywordTitle != null ? keywordTitle : ""%>'><br>
관리자: <input type='text' name='keywordOwner' 
  value='<%=keywordOwner != null ? keywordOwner : ""%>'><br>
멤버: <input type='text' name='keywordMember' 
  value='<%=keywordMember != null ? keywordMember : ""%>'><br>
<button>검색</button>
</form>
</p>
</body>
</html>
