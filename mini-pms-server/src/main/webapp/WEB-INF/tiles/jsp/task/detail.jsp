<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>작업 정보(JSP+EL+JSTL)</h1>

<form action='update' method='post'>
<input type='hidden' name='projectNo' value='${task.projectNo}'>
<input type='hidden' name='no' value='${task.no}'>
작업내용: <textarea name='content' rows='10' cols='50'>${task.content}</textarea><br>
마감일: <input type='date' name='deadline' value='${task.deadline}'><br>
담당자: 
<select name='ownerNo'>
<c:forEach items="${project.members}" var="m">
  <option value='${m.no}' 
  <c:if test="${m.state == 1 && m.no == task.owner.no}">selected</c:if>
  >${m.name}</option>
</c:forEach>
</select><br>
작업상태: 
<select name='status'>
<c:forTokens items="준비,진행중,완료" delims="," var="statusLabel" varStatus="status">
  <option value='${status.index}' 
    ${task.status == status.index ? "selected" : ""}
  >${statusLabel}</option>
</c:forTokens>
</select><br>
<button>변경</button>
<a href='delete?no=${task.no}&projectNo=${project.no}'>[삭제]</a>
<a href='../project/detail?no=${project.no}'>[목록]</a>
</form>
