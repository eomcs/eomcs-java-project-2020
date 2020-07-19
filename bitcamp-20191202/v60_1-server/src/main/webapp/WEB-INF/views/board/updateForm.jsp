<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<h1>게시물 변경(JSP + EL + JSTL)</h1>

<form action='update' method='post'>
번호: ${board.no}<br>
<%-- 
번호: <input name='no' readonly type='text' value='${board.no}'><br>
--%>
<input name='no' type='hidden' value='${board.no}'>
내용:<br>
<textarea name='title' rows='5' cols='60'>${board.title}</textarea><br>
등록일: ${board.date}<br>
조회수: ${board.viewCount}<br>
<button>변경</button>
</form>
