<%@page import="com.eomcs.lms.domain.PhotoFile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<jsp:useBean id="photoBoard" class="com.eomcs.lms.domain.PhotoBoard" scope="request"/>

<h1>사진 상세정보(JSP)</h1>
<form action='update' method='post' enctype='multipart/form-data'>
번호: <input name='no' type='text' readonly value='<%=photoBoard.getNo()%>'><br>
내용:<br>
<textarea name='title' rows='5' cols='60'><%=photoBoard.getTitle()%></textarea><br>
등록일: <%=photoBoard.getCreatedDate()%><br>
조회수: <%=photoBoard.getViewCount()%><br>
수업: <%=photoBoard.getLesson().getTitle()%><br>
<hr>
사진 파일:<br>
<p>
<%
for (PhotoFile photoFile : photoBoard.getFiles()) {
%>
<img src='../upload/photoboard/<%=photoFile.getFilepath()%>' height='80'>
<%
}
%>
</p>
사진: <input name='photo' type='file'><br>
사진: <input name='photo' type='file'><br>
사진: <input name='photo' type='file'><br>
사진: <input name='photo' type='file'><br>
사진: <input name='photo' type='file'><br>
<p><button>변경</button>
<a href='delete?no=<%=photoBoard.getNo()%>&lessonNo=<%=photoBoard.getLesson().getNo()%>'>삭제</a></p>
</form>

<jsp:include page="/footer.jsp"/>
    