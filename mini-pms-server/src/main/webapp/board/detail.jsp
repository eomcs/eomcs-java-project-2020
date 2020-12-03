<%@page import="com.eomcs.pms.domain.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>게시글조회</title></head>
<body>

<jsp:include page="/header.jsp"></jsp:include>

<h1>게시물 조회(JSP)</h1>

<%
Board board = (Board) request.getAttribute("board");
if (board == null) {
  response.setHeader("Refresh", "2;url=list");%>
  <p>해당 번호의 게시글이 없습니다.</p>
<%
} else {
%>
<form action='update' method='post'>
번호: <input type='text' name='no' value='<%=board.getNo()%>' readonly><br>
제목: <input type='text' name='title' value='<%=board.getTitle()%>'><br>
내용: <textarea name='content'><%=board.getContent()%></textarea><br>
작성자: <%=board.getWriter().getName()%><br>
등록일: <%=board.getRegisteredDate()%><br>
조회수: <%=board.getViewCount()%><br>
<p>
<button>변경</button>
<a href='delete?no=<%=board.getNo()%>'>[삭제]</a>
<a href='list'>[목록]</a>
</p>
</form>
<%}%>
</body>
</html>
