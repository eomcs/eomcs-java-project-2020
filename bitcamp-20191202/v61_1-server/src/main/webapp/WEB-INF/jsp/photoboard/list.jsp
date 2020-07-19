<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../header.jsp"/>

  <h1>강의 사진(JSP + EL + JSTL) - <a href='../lesson/detail?no=${lesson.no}'>${lesson.title}</a></h1>  
  <a href='form?lessonNo=${lesson.no}'>새 사진</a><br>
  <table border='1'>
  <tr>
    <th>번호</th>
    <th>제목</th>
    <th>등록일</th>
    <th>조회수</th>
  </tr>
  
<c:forEach items="${list}" var="item">
  <tr>
    <td>${item.no}</td> 
    <td><a href='detail?no=${item.no}'>=> ${item.title}</a></td> 
    <td>${item.createdDate}</td> 
    <td>${item.viewCount}</td>
  </tr>
</c:forEach>

  </table>

<jsp:include page="../footer.jsp"/>
    