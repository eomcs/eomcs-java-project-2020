<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="appRoot" value="${pageContext.servletContext.contextPath}"/>
<div id='menubar'>
<a href="${appRoot}/">미니 프로젝트 관리 시스템</a>
<c:if test="${empty loginUser}">
  <a href='${appRoot}/app/auth/login'>로그인</a>
</c:if>
<c:if test="${not empty loginUser}">
  <img src="${appRoot}/upload/${loginUser.photo}_30x30.jpg">
  <span id="user-name">${loginUser.name}</span>
  <a href='${appRoot}/app/auth/logout'>로그아웃</a>
</c:if>
</div>