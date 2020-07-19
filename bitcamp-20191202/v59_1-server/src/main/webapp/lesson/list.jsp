<%@page import="com.eomcs.lms.domain.Lesson"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

  <h1>강의(JSP + EL + JSTL)</h1>
  <a href='form'>새 강의</a><br>
  <table border='1'>
  <tr>
    <th>번호</th>
    <th>강의</th>
    <th>기간</th>
    <th>총강의시간</th>
  </tr>

<c:forEach items="${list}" var="item">
    <tr>
      <td>${item.no}</td> 
      <td><a href='detail?no=${item.no}'>${item.title}</a></td> 
      <td>${item.startDate} ~ ${item.endDate}</td> 
      <td>${item.totalHours}</td>
    </tr>
</c:forEach>

  </table>
<hr>
<form action='search' method='get'>
강의명: <input name='title' type='text'><br>
강의 시작일: <input name='startDate' type='date'><br>
강의 종료일: <input name='endDate' type='date'><br>
총 강의시간: <input name='totalHours' type='number'><br>
일 강의시간: <input name='dayHours' type='number'><br>
<button>검색</button>
</form>

<jsp:include page="/footer.jsp"/>
    