<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head><title>게시글목록</title></head>
<body>

<jsp:include page="../header.jsp"></jsp:include>

<h1>게시물 목록(JSP+EL+JSTL)</h1>

<a href='form'>새 글</a><br>

<table border='1'>
<thead><tr><th>번호</th><th>제목</th><th>작성자</th><th>등록일</th><th>조회수</th></tr></thead>
<tbody>

<c:forEach items="${list}" var="b">
<tr>
  <td>${b.no}</td>
  <td><a href='detail?no=${b.no}'>${b.title}</a></td>
  <td>${b.writer.name}</td>
  <td>${b.registeredDate}</td>
  <td>${b.viewCount}</td>
</tr>
</c:forEach>
</tbody>
</table>

<form action='list' method='get'>
검색어: <input type='text' name='keyword' value=''>
<button>검색</button>
</form>

</body>
</html>
    