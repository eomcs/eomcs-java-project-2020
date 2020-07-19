<%@page import="com.eomcs.lms.domain.Member"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

  <h1>회원(JSP)</h1>
  <a href='add'>새 회원</a><br>
  <table border='1'>
  <tr>
    <th>번호</th>
    <th>이름</th>
    <th>이메일</th>
    <th>전화</th>
    <th>등록일</th>
  </tr>
  
<jsp:useBean id="list" 
  type="java.util.List<Member>"
  class="java.util.ArrayList"
  scope="request"/>
<% 
  for(Member item : list) {
%>
  <tr>
    <td><%=item.getNo()%></td> 
    <td><a href='detail?no=<%=item.getNo()%>'><%=item.getName()%></a></td> 
    <td><%=item.getEmail()%></td> 
    <td><%=item.getTel()%></td>
    <td><%=item.getRegisteredDate()%></td>
  </tr>
<%
  }
%>
  </table>
<hr>
<form action='search' method='get'>
검색어: <input name='keyword' type='text'>
<button>검색</button>
</form>

<jsp:include page="/footer.jsp"/>
    