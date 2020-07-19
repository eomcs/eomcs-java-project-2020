<%@page import="com.eomcs.lms.domain.PhotoBoard"%>
<%@page import="com.eomcs.lms.domain.Lesson"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<jsp:useBean id="lesson" class="com.eomcs.lms.domain.Lesson" scope="request"/>

  <h1>강의 사진(JSP) - <a href='../lesson/detail?no=<%=lesson.getNo()%>'><%=lesson.getTitle()%></a></h1>  
  <a href='add?lessonNo=<%=lesson.getNo()%>'>새 사진</a><br>
  <table border='1'>
  <tr>
    <th>번호</th>
    <th>제목</th>
    <th>등록일</th>
    <th>조회수</th>
  </tr>
  
<jsp:useBean id="list" 
  type="java.util.List<PhotoBoard>"
  class="java.util.ArrayList"
  scope="request"/>  
<% 
  for(PhotoBoard item : list) {
%>
  <tr>
    <td><%=item.getNo()%></td> 
    <td><a href='detail?no=<%=item.getNo()%>'><%=item.getTitle()%></a></td> 
    <td><%=item.getCreatedDate()%></td> 
    <td><%=item.getViewCount()%></td>
  </tr>
<%
  }
%>
  </table>

<jsp:include page="/footer.jsp"/>
    