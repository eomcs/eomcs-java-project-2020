<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../header.jsp"/>

<h1>게시물 상세정보(JSP + EL + JSTL)</h1>

<c:if test="${not empty board}">
번호: ${board.no}<br>
제목: ${board.title}<br>
등록일: ${board.date}<br>
조회수: ${board.viewCount}<br>
<p><a href='delete?no=${board.no}'>삭제</a> 
<a href='updateForm?no=${board.no}'>변경</a></p>
</c:if>

<c:if test="${empty requestScope.board}">
<p>해당 게시물이 없습니다.</p>
</c:if>

<jsp:include page="../footer.jsp"/>
    