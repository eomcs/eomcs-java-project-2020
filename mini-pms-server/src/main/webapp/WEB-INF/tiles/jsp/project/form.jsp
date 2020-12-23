<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<h1>프로젝트 생성(JSP+EL+JSP)</h1>
<form action='add' method='post'>
프로젝트명: <input type='text' name='title'><br>
내용: <textarea name='content' rows='10' cols='50'></textarea><br>
기간: <input type='date' name='startDate'> ~ 
      <input type='date' name='endDate'><br>
팀원: <br>
<ul>
<c:forEach items="${members}" var="m">
  <li><input type='checkbox' name='memberNo' value='${m.no}'>${m.name}</li>
</c:forEach>
</ul><br>
<button>생성</button>
</form>
