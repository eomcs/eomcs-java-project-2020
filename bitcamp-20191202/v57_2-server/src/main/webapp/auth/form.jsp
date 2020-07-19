<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<h1>로그인(JSP + EL)</h1>
<form action='login' method='post'>
이메일: <input name='email' type='email' value='${email}'>
<input type='checkbox' name='saveEmail'> 이메일 저장해두기<br>
암호: <input name='password' type='password'><br>
<button>로그인</button>
</form>

<jsp:include page="/footer.jsp"/>
    