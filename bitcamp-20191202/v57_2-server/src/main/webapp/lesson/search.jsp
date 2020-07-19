<%@page import="com.eomcs.lms.domain.Lesson"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

  <h1>강의 검색 결과(JSP + EL)</h1>
  <table border='1'>
  <tr>
    <th>번호</th>
    <th>강의</th>
    <th>기간</th>
    <th>총강의시간</th>
  </tr>
  
<jsp:useBean id="list" 
  type="java.util.List<Lesson>"
  class="java.util.ArrayList"
  scope="request"/>
<% 
  for(Lesson item : list) {
    pageContext.setAttribute("item", item);
%>
    <tr>
      <td>${item.no}</td> 
      <td><a href='detail?no=${item.no}'>${item.title}</a></td> 
      <td>${item.startDate} ~ ${item.endDate}</td> 
      <td>${item.totalHours}</td>
    </tr>
<%
  }
%>
  </table>

<jsp:include page="/footer.jsp"/>
    