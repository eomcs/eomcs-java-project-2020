<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>게시물 목록(JSP+EL+JSTL)</h1>

<a href='form' class="btn btn-primary btn-sm">새 글</a><br>
 
<table class="table table-striped table-hover">
<thead><tr><th>번호</th><th>제목</th><th>작성자</th><th>등록일</th><th>조회수</th></tr></thead>
<tbody>

<c:forEach items="${list}" var="b">
<tr>
  <td>${b.no}</td>
  <td><a class="board-title" href='detail?no=${b.no}' data-no="${b.no}">${b.title}</a></td>
  <td>${b.writer.name}</td>
  <td>${b.registeredDate}</td>
  <td>${b.viewCount}</td>
</tr>
</c:forEach>
</tbody>
</table>

<div>
<c:if test="${prevPageNo == currPageNo}">
  [이전]
</c:if>
<c:if test="${prevPageNo < currPageNo}">
  <a href="?keyword=${keyword}&pageNo=${prevPageNo}&pageSize=${pageSize}">[이전]</a>
</c:if>
<span> ${currPageNo} </span>
<c:if test="${nextPageNo == currPageNo}">
  [다음]
</c:if>
<c:if test="${nextPageNo > currPageNo}">
  <a href="?keyword=${keyword}&pageNo=${nextPageNo}&pageSize=${pageSize}">[다음]</a>
</c:if>
</div>

<form action='list' method='get' class="row row-cols-lg-auto g-3 align-items-center">
  <input type="hidden" name="pageSize" value="${pageSize}">
	<div class="col-12">
	  <input class="form-control" name='keyword' id="keyword" 
	  placeholder="검색어 입력" value="${keyword}">
	</div>
	<div class="col-12">
    <button class="btn btn-primary">검색</button>
  </div>
</form>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">New message</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <div class="modal-body">
        <!-- 모달 화면 -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Send message</button>
      </div>
    </div>
  </div>
</div>

<script id="t1" type="text/x-handlebars-template">
<h1>게시물 조회(JSP+EL+JSTL)</h1>

<form action='update' method='post'>
번호: <input type='text' name='no' value='{{no}}' readonly><br>
제목: <input type='text' name='title' value='{{title}}'><br>
내용: <textarea name='content'>{{content}}</textarea><br>
작성자: {{writer.name}}<br>
등록일: {{registeredDate}}<br>
조회수: {{viewCount}}<br>
<p>
<button>변경</button>
<a href='delete?no={{no}}'>[삭제]</a>
<a href='list'>[목록]</a>
</p>
</form>
</script>

<script>
var el = document.querySelectorAll(".board-title");
var myModal = new bootstrap.Modal(document.getElementById('exampleModal'), {});
var exampleModal = document.querySelector("#exampleModal");
var exampleModalBody = exampleModal.querySelector(".modal-body");
var modalTemplateSrc = document.querySelector("#t1").innerHTML;
var modalHtmlGenerator = Handlebars.compile(modalTemplateSrc);
var boardNo;

exampleModal.addEventListener('show.bs.modal', function (event) {
  console.log("show.bs.modal")
  var xhr = new XMLHttpRequest();
  xhr.open("GET", "../ajax2/board/detail?no=" + boardNo, false);
  xhr.send();
  var board = JSON.parse(xhr.responseText);
  console.log(board);
  exampleModalBody.innerHTML = modalHtmlGenerator(board);
});

exampleModal.addEventListener('shown.bs.modal', function (event) {
	console.log("shown.bs.modal")
});

exampleModal.addEventListener('hidden.bs.modal', function (event) {
	console.log("hidden.bs.modal")
});

for (var e of el) {
	e.onclick = function(e) {
		e.preventDefault();
		boardNo = e.target.getAttribute("data-no");
		console.log("제목 click");
		myModal.show();
	};
}

</script>
