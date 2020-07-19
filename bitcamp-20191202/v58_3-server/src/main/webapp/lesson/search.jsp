<%@page import="com.eomcs.lms.domain.Lesson"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

  <h1>강의 검색 결과(JSP + EL + JSTL)</h1>
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

<jsp:include page="/footer.jsp"/>
    