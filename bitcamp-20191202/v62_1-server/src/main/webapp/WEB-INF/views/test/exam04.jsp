<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>/test/exam04.jsp</h1>
<script>
console.log("exam04 에서 만든 스크립트")
$(function() {
  console.log('$()로 등록한 함수 호출!');
  $('h1').html("$()를 사용하여 문서 로딩 후 자동으로 호출될 함수 등록!");
});
$(() => {
  console.log('$()로 등록한 함수 호출2!');
  $('h1').html("$()를 사용하여 문서 로딩 후 자동으로 호출될 함수 등록2!");
});
</script>
<hr>