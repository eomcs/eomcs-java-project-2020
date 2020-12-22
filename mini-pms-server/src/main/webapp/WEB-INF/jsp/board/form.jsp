<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 등록</title>
</head>
<body>

<jsp:include page="../header.jsp"></jsp:include>

<h1>게시글 등록</h1>
<form action="add" method="post">
제목: <input type="text" name="title"><br>
내용: <textarea name="content" cols="60" rows="10"></textarea><br>
<button>등록</button>
</form>
</body>
</html>
