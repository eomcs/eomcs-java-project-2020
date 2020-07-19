<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<jsp:useBean id="board" class="com.eomcs.lms.domain.Board" scope="request"/>
<%--
Board board = (Board) request.getAttribute("board");
if (board == null) {
  board = new Board();
  request.setAttribute("board", board);
}
--%>

<h1>게시물 상세정보(JSP)</h1>
번호: <%=board.getNo()%><br>
제목: <%=board.getTitle()%><br>
등록일: <%=board.getDate() %><br>
조회수: <%=board.getViewCount()%><br>
<p><a href='delete?no=<%=board.getNo()%>'>삭제</a> 
<a href='update?no=<%=board.getNo()%>'>변경</a></p>

<jsp:include page="/footer.jsp"/>
    