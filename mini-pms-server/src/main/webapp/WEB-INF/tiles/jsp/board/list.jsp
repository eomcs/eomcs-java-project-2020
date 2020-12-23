<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>게시물 목록(JSP+EL+JSTL)</h1>

<a href='form' class="btn btn-primary btn-sm">새 글</a><br>
 
<table class="table table-striped table-hover">
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

<form action='list' method='get' class="row row-cols-lg-auto g-3 align-items-center">
	<div class="col-12">
	  <input class="form-control" name='keyword' id="keyword" 
	  placeholder="검색어 입력" value="${param.keyword}">
	</div>
	<div class="col-12">
    <button class="btn btn-primary">검색</button>
  </div>
</form>
