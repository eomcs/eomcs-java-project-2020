<%@page import="com.eomcs.pms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>회원상세정보</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>회원 상세 정보(JSP)</h1>
<%
Member member = (Member) request.getAttribute("member");
if (member == null) {
  response.setHeader("Refresh", "2;url=list");%>
  <p>해당 번호의 회원이 없습니다.</p>
<%
} else {
%>
<form action='updatePhoto' method='post' enctype='multipart/form-data'>
<input type='hidden' name='no' value='<%=member.getNo()%>'><br>
<a href='../upload/<%=member.getPhoto()%>'>
<img src='../upload/<%=member.getPhoto()%>_120x120.jpg'></a><br>
<input type='file' name='photo'>
<button>변경</button>
</form>
<br>
<form action='update' method='post'>
번호: <input type='text' name='no' value='<%=member.getNo()%>' readonly><br>
이름: <input type='text' name='name' value='<%=member.getName()%>'><br>
이메일: <input type='email' name='email' value='<%=member.getEmail()%>'><br>
암호: <input type='password' name='password'><br>
전화: <input type='tel' name='tel' value='<%=member.getTel()%>'><br>
등록일: 2020-11-27<br>
<button>변경</button>
<a href='delete?no=<%=member.getNo()%>'>[삭제]</a>
 <a href='list'>[목록]</a> 
</form>
<%} %>
</body>
</html>
