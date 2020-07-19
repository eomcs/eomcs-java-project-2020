<%@ page import="com.eomcs.lms.domain.Board"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

  <h1>게시글(JSP + EL)</h1>
  <a href='add'>새 글</a><br>
  <table border='1'>
  <tr>
    <th>번호</th>
    <th>제목</th>
    <th>등록일</th>
    <th>조회수</th>
  </tr>
<jsp:useBean id="list" 
  type="java.util.List<Board>"
  class="java.util.ArrayList"
  scope="request"/>
<% 
  for(Board item : list) {
    pageContext.setAttribute("item", item);
%>
  <tr>
    <td>${item.no}</td> 
    <td><a href='detail?no=${item.no}'>=> ${item.title}</a></td> 
    <td>${item.date}</td> 
    <td>${item.viewCount}</td>
  </tr>
<%
  }
%>
</table>

<jsp:include page="/footer.jsp"/>
    