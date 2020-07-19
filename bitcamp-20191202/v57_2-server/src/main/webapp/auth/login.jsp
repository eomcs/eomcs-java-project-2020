<%@page import="com.eomcs.lms.domain.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<jsp:include page="/header.jsp"/>

<h1>로그인 결과</h1>
<jsp:useBean id="loginUser" 
  class="com.eomcs.lms.domain.Member"
  scope="session"/>
<%
if (loginUser.getName() != null) {
%> 
<p>'${loginUser.name}'님 환영합니다.</p>
<%
} else {
%>
<p>사용자 정보가 유효하지 않습니다.</p>
<%
}
%>
<jsp:include page="/footer.jsp"/>
    