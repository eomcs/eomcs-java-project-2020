<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>/test/exam03.jsp</h1>

<script>
console.log("exam03 에서 만든 스크립트")
window.onload = function() {
  console.log('onload() 함수 호출!');
  $('h1').html("오호라... jquery 함수 $를 사용할 수 있네.")
};

</script>
<hr>