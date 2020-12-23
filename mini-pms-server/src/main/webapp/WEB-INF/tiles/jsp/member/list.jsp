<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<h1>회원 목록(JSP+EL+JSTL)</h1>

<a href='form'>새 회원</a><br>

<table border='1'>
<thead><tr><th>번호</th><th>이름</th><th>이메일</th><th>전화</th><th>등록일</th></tr></thead>
<tbody>
<c:forEach items="${list}" var="m">
<tr>
  <td>${m.no}</td>
  <td><a href='${m.no}'><img src='../../upload/${m.photo}_30x30.jpg' alt='사진'>${m.name}</a></td>
  <td>${m.email}></td>
  <td>${m.tel}</td>
  <td>${m.registeredDate}</td>
</tr>
</c:forEach>
</tbody>
</table>
