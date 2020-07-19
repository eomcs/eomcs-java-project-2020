<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="../header.jsp"/>

<h1>회원 입력(JSP)</h1>
<form action='add' method='post' enctype='multipart/form-data'>
이름: <input name='name' type='text'><br>
이메일: <input name='email' type='email'><br>
암호: <input name='password' type='password'><br>
사진: <input name='photoFile' type='file'><br>
전화: <input name='tel' type='tel'><br>
<button>제출</button>
</form>

<jsp:include page="../footer.jsp"/>
    