<%@page import="com.eomcs.lms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/header.jsp"/>

<h1>오류 내용</h1>
<p>${error.message}</p>
<pre>${errorDetail}</pre>

<c:if test="${not empty url}">
<p><a href='${url}'>뒤로 가기</a></p>
</c:if>

<jsp:include page="/footer.jsp"/>
    