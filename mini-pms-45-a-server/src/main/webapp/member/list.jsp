<%@page import="com.eomcs.pms.domain.Member"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>회원목록</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>회원 목록(JSP)</h1>
<a href='form.html'>새 회원</a><br>
<%
List<Member> list = (List<Member>) request.getAttribute("list");
%>
<table border='1'>
<thead><tr><th>번호</th><th>이름</th><th>이메일</th><th>전화</th><th>등록일</th></tr></thead>
<tbody>
<%for (Member m : list) {%>
<tr>
  <td><%=m.getName()%></td>
  <td><a href='detail?no=<%=m.getNo()%>'><img src='../upload/<%=m.getPhoto()%>_30x30.jpg' alt='사진'><%=m.getName()%></a></td>
  <td><%=m.getEmail()%></td>
  <td><%=m.getTel()%></td>
  <td><%=m.getRegisteredDate()%></td>
</tr>
<%}%>
</tbody>
</table>
</body>
</html>
