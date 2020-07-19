<%@page import="com.eomcs.lms.domain.PhotoBoard"%>
<%@page import="com.eomcs.lms.domain.PhotoFile"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<h1>사진 상세정보(JSP + EL)</h1>
<form action='update' method='post' enctype='multipart/form-data'>
번호: <input name='no' type='text' readonly value='${photoBoard.no}'><br>
내용:<br>
<textarea name='title' rows='5' cols='60'>${photoBoard.title}</textarea><br>
등록일: ${photoBoard.createdDate}<br>
조회수: ${photoBoard.viewCount}<br>
수업: ${photoBoard.lesson.title}<br>
<hr>
사진 파일:<br>
<p>
<%
PhotoBoard photoBoard = (PhotoBoard) request.getAttribute("photoBoard");
for (PhotoFile photoFile : photoBoard.getFiles()) {
  pageContext.setAttribute("photoFile", photoFile);
%>
<img src='../upload/photoboard/${photoFile.filepath}' height='80'>
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
<a href='delete?no=${photoBoard.no}&lessonNo=${photoBoard.lesson.no}'>삭제</a></p>
</form>

<jsp:include page="/footer.jsp"/>
    