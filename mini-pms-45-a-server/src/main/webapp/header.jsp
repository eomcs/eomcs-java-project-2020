<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
  #menubar {
    background-color:gray;
    color:white;
    height: 30px;
    padding: 5px;
  }
  #menubar a {
    color:white; 
    text-decoration: none;
  }
  #menubar a:visited {
    color:white; 
  }
  #menubar a:hover {
    text-decoration: underline;
  }
</style>
<%
String contextPath = request.getServletContext().getContextPath();
%>
<div id='menubar'>
  <a href='<%=contextPath%>/board/list'>게시글</a>
  <a href='<%=contextPath%>/member/list'>회원</a>
  <a href='<%=contextPath%>/project/list'>프로젝트</a>
  <a href='<%=contextPath%>/auth/login'>로그인</a>
  <a href='<%=contextPath%>/auth/logout'>로그아웃</a>
</div>