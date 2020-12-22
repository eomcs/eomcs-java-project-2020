<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>     
<!DOCTYPE html>
<html>
<head>
<title>프로젝트목록</title></head>
<body>

<jsp:include page="../header.jsp"></jsp:include>

<h1>프로젝트 목록(JSP+EL+JSTL)</h1>
<a href='form'>새 프로젝트</a><br>

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
<c:forEach items="${list}" var="p">
<tr>
  <td>${p.no}</td>
  <td><a href='detail?no=${p.no}'>${p.title}</a></td>
  <td>${p.startDate} ~ ${p.endDate}</td>
  <td>${p.owner.name}</td>
  <td>[
  <c:forEach items="${p.members}" var="m">
    <c:if test="${m.state == 1}">
      ${m.name},
    </c:if>
  </c:forEach>
  ]</td>
</tr>
</c:forEach>
</tbody>
</table>

<form action='list' method='get'>
검색어: <input type='text' name='keyword' value='${param.keyword}'>
<button>검색</button>
</form>

<hr>
<h2>상세 검색</h2>

<form action='list' method='get'>
프로젝트명: <input type='text' name='keywordTitle' value='${param.keywordTitle}'><br>
관리자: <input type='text' name='keywordOwner' value='${param.keywordOwner}'><br>
멤버: <input type='text' name='keywordMember' value='${param.keywordMember}'><br>
<button>검색</button>
</form>

</body>
</html>
