<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h1>게시물 조회(JSP+EL+JSTL)</h1>

<form action='update' method='post'>
번호: <input type='text' name='no' value='${board.no}' readonly><br>
제목: <input type='text' name='title' value='${board.title}'><br>
내용: <textarea name='content'>${board.content}</textarea><br>
작성자: ${board.writer.name}<br>
등록일: ${board.registeredDate}<br>
조회수: ${board.viewCount}<br>
<p>
<button>변경</button>
<a href='delete?no=${board.no}'>[삭제]</a>
<a href='list'>[목록]</a>
</p>
</form>

