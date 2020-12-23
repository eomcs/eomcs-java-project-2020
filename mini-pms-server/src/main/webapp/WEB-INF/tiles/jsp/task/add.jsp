<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>작업 등록(JSP)</h1>

<form action='add' method='post'>
<input type='hidden' name='projectNo' value='${project.no}'>
프로젝트명: ${project.title}<br>
작업내용: <textarea name='content' rows='10' cols='50'></textarea><br>
마감일: <input type='date' name='deadline'><br>
담당자: <br>
<p>
<c:forEach items="${project.members}" var="m">
  <c:if test="${m.state == 1}">
    <input type='radio' name='ownerNo' value='${m.no}'>${m.name},
  </c:if> 
</c:forEach>
</p>
작업상태: 
<select name='status'>
  <option value='0'>준비</option>
  <option value='1'>진행중</option>
  <option value='2'>완료</option>
</select><br>
<button>등록</button>
</form>
