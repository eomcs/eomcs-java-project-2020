<%@page import="com.eomcs.pms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>회원 등록</title></head>
<body>

<jsp:include page="../header.jsp"></jsp:include>

<h1>회원 등록</h1>
<form action="add" method="post" enctype="multipart/form-data">
이름: <input type="text" name="name"><br>
이메일: <input type="email" name="email"><br>
암호: <input type="password" name="password"><br>
전화: <input type="tel" name="tel"><br>
사진: <input type="file" name="photoFile"><br>
<button>등록</button>
</form>
</body>
</html>