<%@page import="com.eomcs.lms.domain.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<h1>게시물 변경(JSP)</h1>

<jsp:useBean id="board" 
    class="com.eomcs.lms.domain.Board" 
    scope="request"/>
<%
if (board.getNo() == 0) {
%>
  <p>해당 번호의 게시글이 없습니다.</p>
<% 
} else {
%>
<form action='update' method='post'>
번호: <input name='no' readonly type='text' value='<%=board.getNo()%>'><br>
내용:<br>
<textarea name='title' rows='5' cols='60'><%=board.getTitle()%></textarea><br>
등록일: <%=board.getDate()%><br>
조회수: <%=board.getViewCount()%><br>
<button>변경</button>
</form>
<%
}
%>
<jsp:include page="/footer.jsp"/>
    