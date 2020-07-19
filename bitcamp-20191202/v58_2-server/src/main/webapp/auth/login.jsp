<%@page import="com.eomcs.lms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/header.jsp"/>

<h1>로그인 결과(JSP + EL + JSTL)</h1>
<c:if test="${not empty loginUser}">
<p>'${loginUser.name}'님 환영합니다.</p>
</c:if>

<c:if test="${empty loginUser}">
<p>사용자 정보가 유효하지 않습니다.</p>
</c:if>

<jsp:include page="/footer.jsp"/>
    