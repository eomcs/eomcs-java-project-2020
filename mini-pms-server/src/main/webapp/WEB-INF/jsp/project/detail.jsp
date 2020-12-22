<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
<title>프로젝트정보</title></head>
<body>

<jsp:include page="../header.jsp"></jsp:include>

<h1>프로젝트 정보(JSP+EL+JSTL)</h1>

<form action='update' method='post'>
<input type='hidden' name='no' value='${project.no}'>
프로젝트명: <input type='text' name='title' value='${project.title}'><br>
내용: <textarea name='content' rows='10' cols='70'>${project.content}</textarea><br>
기간: <input type='date' name='startDate' value='${project.startDate}'> ~ 
      <input type='date' name='endDate' value='${project.endDate}'><br>
관리자: ${project.owner.name}<br>
팀원: * 는 비활성 상태의 멤버<br>
<c:forEach items="${members}" var="m">
	<input type='checkbox' name='memberNo' value='${m.no}'
	  <c:forEach items="${project.members}" var="projectMember">
	    <c:if test="${projectMember.no == m.no && projectMember.state == 1}">
	      checked
	    </c:if>
	  </c:forEach>
	>${m.name }
	  <c:forEach items="${project.members}" var="projectMember">
	    <c:if test="${projectMember.no == m.no && projectMember.state == 0}">
	      *
	    </c:if>
	  </c:forEach> 
</c:forEach>
<br>
<button>변경</button>
<a href='delete?no=${project.no}'>[삭제]</a>
<a href='list'>[목록]</a>
</form>
<hr>
작업:<br>

<jsp:include page="../task/list.jsp"></jsp:include>
 
</body>
</html>
