<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>로그인 사용자(JSP+EL+JSTL)</h1>
사용자 번호: ${loginUser.no}<br>
이름: ${loginUser.name}<br>
이메일: ${loginUser.email}<br>
사진: <img src="../upload/${loginUser.photo}_120x120.jpg"><br>
전화: ${loginUser.tel}<br>
등록일: ${loginUser.registeredDate}<br>

