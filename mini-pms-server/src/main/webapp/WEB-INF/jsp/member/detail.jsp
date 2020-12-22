<%@page import="com.eomcs.pms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>회원상세정보</title></head>
<body>

<jsp:include page="../header.jsp"></jsp:include>

<h1>회원 상세 정보(JSP+EL+JSTL)</h1>

<form action='updatePhoto' method='post' enctype='multipart/form-data'>
<input type='hidden' name='no' value='${member.no}'><br>
<a href='../../upload/${member.photo}'>
<img src='../../upload/${member.photo}_120x120.jpg'></a><br>
<input type='file' name='photoFile'>
<button>변경</button>
</form>
<br>
<form action='update' method='post'>
번호: <input type='text' name='no' value='${member.no}' readonly><br>
이름: <input type='text' name='name' value='${member.name}'><br>
이메일: <input type='email' name='email' value='${member.email}'><br>
암호: <input type='password' name='password'><br>
전화: <input type='tel' name='tel' value='${member.tel}'><br>
등록일: 2020-11-27<br>
<button>변경</button>
<a href='delete?no=${member.no}'>[삭제]</a>
 <a href='./'>[목록]</a> 
</form>
</body>
</html>
