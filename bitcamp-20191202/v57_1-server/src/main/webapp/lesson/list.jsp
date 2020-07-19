<%@page import="com.eomcs.lms.domain.Lesson"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

  <h1>강의(JSP)</h1>
  <a href='add'>새 강의</a><br>
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
%>
    <tr>
      <td><%=item.getNo()%></td> 
      <td><a href='detail?no=<%=item.getNo()%>'><%=item.getTitle()%></a></td> 
      <td><%=item.getStartDate()%> ~ <%=item.getEndDate()%></td> 
      <td><%=item.getTotalHours()%></td>
    </tr>
<%
  }
%>
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
    