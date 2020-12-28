<%@page import="com.eomcs.pms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h1>회원 등록</h1>
<form id="memberForm" action="add" method="post" enctype="multipart/form-data">
이름 *: <input type="text" name="name"><br>
이메일 *: <input id="inputEmail" type="email" name="email"><br>
암호 *: <input id="inputPassword" type="password" name="password"><br>
전화: <input type="tel" name="tel"><br>
사진: <input type="file" name="photoFile"><br>
별표(*)는 필수 입력 항목입니다.<br>
<button>등록</button>
</form>
<script>
document.querySelector("#memberForm").onsubmit = () => {
	if (document.querySelector("input[name='name']").value.length < 8 ||
			document.querySelector("#inputEmail").value.length < 10 ||
			document.querySelector("#inputPassword").value.length < 4) {
		alert("필수 입력 항목을 모두 채우세요!")
	  return false;
	}
};
</script>