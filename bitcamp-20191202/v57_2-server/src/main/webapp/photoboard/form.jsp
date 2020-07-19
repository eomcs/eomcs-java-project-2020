<%@page import="com.eomcs.lms.domain.Lesson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<h1>사진 입력(JSP + EL)</h1>
<form action='add' method='post' enctype='multipart/form-data'>
강의번호: <input name='lessonNo' type='text' value='${lesson.no}' readonly><br>
강의명: ${lesson.title}<br>
내용:<br>
<textarea name='title' rows='5' cols='60'></textarea><br>
<hr>
사진: <input name='photo' type='file'><br>
사진: <input name='photo' type='file'><br>
사진: <input name='photo' type='file'><br>
사진: <input name='photo' type='file'><br>
사진: <input name='photo' type='file'><br>
<button>제출</button>
</form>

<jsp:include page="/footer.jsp"/>
    