<%@page import="com.eomcs.pms.domain.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>게시글목록</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>게시물 목록(JSP)</h1>
<a href='form.html'>새 글</a><br>

<%
List<Board> list = (List<Board>) request.getAttribute("list");
%>

<table border='1'>
<thead><tr><th>번호</th><th>제목</th><th>작성자</th><th>등록일</th><th>조회수</th></tr></thead>
<tbody>
<%for (Board b : list) {%>
<tr>
  <td><%=b.getNo()%></td>
  <td><a href='detail?no=<%=b.getNo()%>'><%=b.getTitle()%></a></td>
  <td><%=b.getWriter().getName()%></td>
  <td><%=b.getRegisteredDate()%></td>
  <td><%=b.getViewCount()%></td>
</tr>
<%}%>
</tbody>
</table>
<p>
<form action='list' method='get'>
검색어: <input type='text' name='keyword' value=''>
<button>검색</button>
</form>
</p>
</body>
</html>
    