<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<h1>게시물 상세정보(JSP + EL)</h1>
번호: ${board.no}<br>
제목: ${board.title}<br>
등록일: ${board.date}<br>
조회수: ${board.viewCount}<br>
<p><a href='delete?no=${board.no}'>삭제</a> 
<a href='update?no=${board.no}'>변경</a></p>

<jsp:include page="/footer.jsp"/>
    