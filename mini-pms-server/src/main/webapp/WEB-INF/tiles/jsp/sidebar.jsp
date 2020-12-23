<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="appRoot" value="${pageContext.servletContext.contextPath}"/>
<div id='sidebar'>
<div class="list-group">
  <a href='${appRoot}/app/board/list' class="list-group-item list-group-item-action">게시글</a>
  <a href='${appRoot}/app/member/' class="list-group-item list-group-item-action">회원</a>
  <a href='${appRoot}/app/project/list' class="list-group-item list-group-item-action">프로젝트</a>
</div>
</div>