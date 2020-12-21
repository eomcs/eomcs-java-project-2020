<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Refresh" content="2;url=../../index.html">
  <title>로그아웃</title>
</head>
<body>
<h1>로그아웃(JSP+EL+JSTL)</h1>
<c:if test="${empty loginUser}">
  <p>로그인 된 상태가 아닙니다.</p>
</c:if>
<c:if test="${not empty loginUser}">
  <p>${loginUser.name} 님 안녕히 가세요!</p>
</c:if>
</body>
</html>
