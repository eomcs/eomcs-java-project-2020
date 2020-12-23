<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="appRoot" value="${pageContext.servletContext.contextPath}"/>
<div id='menubar'>
  <a href='${appRoot}/app/board/list'>게시글</a>
  <a href='${appRoot}/app/member/'>회원</a>
  <a href='${appRoot}/app/project/list'>프로젝트</a>
  <a href='${appRoot}/app/auth/login'>로그인</a>
  <a href='${appRoot}/app/auth/logout'>로그아웃</a>
</div>