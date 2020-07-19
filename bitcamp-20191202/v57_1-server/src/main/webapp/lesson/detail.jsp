<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<jsp:useBean id="lesson" class="com.eomcs.lms.domain.Lesson" scope="request"/>

<h1>수업 상세정보(JSP)</h1>
<form action='update' method='post'>
번호: <input name='no' readonly type='text' value='<%=lesson.getNo()%>'><br>
강의명: <input name='title' type='text' value='<%=lesson.getTitle()%>'><br>
내용:<br>
<textarea name='description' rows='5' cols='60'><%=lesson.getDescription()%></textarea><br>
강의 시작일: <input name='startDate' type='date' value='<%=lesson.getStartDate()%>'><br>
강의 종료일: <input name='endDate' type='date' value='<%=lesson.getEndDate()%>'><br>
총 강의시간: <input name='totalHours' type='number' value='<%=lesson.getTotalHours()%>'><br>
일 강의시간: <input name='dayHours' type='number' value='<%=lesson.getDayHours()%>'><br>
<p>
<button>변경</button>
<a href='delete?no=<%=lesson.getNo()%>'>삭제</a>
<a href='../photoboard/list?lessonNo=<%=lesson.getNo()%>'>사진게시판</a>
</p>
</form>

<jsp:include page="/footer.jsp"/>
    