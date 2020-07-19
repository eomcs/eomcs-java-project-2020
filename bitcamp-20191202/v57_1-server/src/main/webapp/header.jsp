<%@page import="com.eomcs.lms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>Bitcamp-LMS</title>
<link rel='stylesheet' href='https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css' integrity='sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh' crossorigin='anonymous'>
<style>
body {
  background-color: LightGray;
}
div.container {
  background: white;
  border: 1px solid gray;
  width: 600px;
}
</style>
</head>
<body>
<nav class='navbar navbar-expand-lg navbar-dark bg-dark'>
<a class='navbar-brand' href='#'>비트캠프</a>
<button class='navbar-toggler' type='button' data-toggle='collapse' data-target='#navbarNav' aria-controls='navbarNav' aria-expanded='false' aria-label='Toggle navigation'>
  <span class='navbar-toggler-icon'></span>
</button>
<div class='collapse navbar-collapse' id='navbarNav'>
  <ul class='navbar-nav mr-auto'>
    <li class='nav-item'>
      <a class='nav-link' href='../board/list'>게시글</span></a>
    </li>
    <li class='nav-item'>
      <a class='nav-link' href='../lesson/list'>수업</a>
    </li>
    <li class='nav-item'>
      <a class='nav-link' href='../member/list'>회원</a>
    </li>
    <li class='nav-item'>
      <a class='nav-link' href='../auth/login'>로그인</a>
    </li>
  </ul>
<% 
Member loginUser = (Member) request.getSession().getAttribute("loginUser");
if (loginUser != null) {
%>
  <span class='navbar-text'><%=loginUser.getName()%></span>
  <a href='../auth/logout' class='btn btn-success btn-sm'>로그아웃</a>
<%
} else {
%>
  <a href='../auth/login' class='btn btn-success btn-sm'>로그인</a>
<%
}
%> 
</div>
</nav>
<div class='container'>