# 57_1 - JSP를 활용하여 서블릿에서 출력 기능을 분리하기  

## 학습목표

- JSP 구동원리를 이해한다.
- JSP를 사용하여 출력기능을 구현할 수 있다.
- 서블릿과 JSP를 연동하여 클라이언트 요청을 처리할 수 있다.
- MVC 아키텍처를 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/servlet/BoardListServlet.java 변경
- src/main/webapp/board/list.jsp 추가
- src/main/webapp/header.jsp 추가
- src/main/webapp/footer.jsp 추가
- src/main/java/com/eomcs/lms/servlet/BoardDetailServlet.java 변경
- src/main/webapp/board/detail.jsp 추가
- src/main/java/com/eomcs/lms/servlet/BoardAddServlet.java 변경
- src/main/webapp/board/form.jsp 추가
- src/main/java/com/eomcs/lms/servlet/BoardUpdateServlet.java 변경
- src/main/webapp/board/updateform.jsp 추가
- src/main/java/com/eomcs/lms/servlet/LessonListServlet.java 변경
- src/main/webapp/lesson/list.jsp 추가
- src/main/java/com/eomcs/lms/servlet/LessonSearchServlet.java 변경
- src/main/webapp/lesson/search.jsp 추가
- src/main/java/com/eomcs/lms/servlet/LessonAddServlet.java 변경
- src/main/webapp/lesson/form.jsp 추가
- src/main/java/com/eomcs/lms/servlet/LessonDetailServlet.java 변경
- src/main/webapp/lesson/detail.jsp 추가
- src/main/java/com/eomcs/lms/servlet/MemberListServlet.java 변경
- src/main/webapp/member/list.jsp 추가
- src/main/java/com/eomcs/lms/servlet/MemberSearchServlet.java 변경
- src/main/webapp/member/search.jsp 추가
- src/main/java/com/eomcs/lms/servlet/MemberAddServlet.java 변경
- src/main/webapp/member/form.jsp 추가
- src/main/java/com/eomcs/lms/servlet/MemberDetailServlet.java 변경
- src/main/webapp/member/detail.jsp 추가
- src/main/java/com/eomcs/lms/servlet/PhotoBoardListServlet.java 변경
- src/main/webapp/photoboard/list.jsp 추가
- src/main/java/com/eomcs/lms/servlet/PhotoBoardAddServlet.java 변경
- src/main/webapp/photoboard/form.jsp 추가
- src/main/java/com/eomcs/lms/servlet/PhotoBoardDetailServlet.java 변경
- src/main/webapp/photoboard/detail.jsp 추가
- src/main/java/com/eomcs/lms/servlet/LoginServlet.java 변경
- src/main/webapp/auth/form.jsp 추가
- src/main/webapp/auth/login.jsp 추가

## 실습  

### 훈련1: 게시글 목록 출력에 JSP를 적용한다.

- src/main/webapp/board/list.jsp 추가
  - BoardListServlet의 출력을 이 JSP에 작성한다.
- com.eomcs.lms.servlet.BoardListServlet 변경
  - 서비스 객체를 통해 출력할 데이터를 준비한다.
  - 출력은 JSP에게 위임한다.
  - 상단 메뉴바와 하단 스크립트 코드 부분을 별도의 JSP로 분리한다.
- src/main/webapp/header.jsp 추가
  - 상단 메뉴를 출력한다.
- src/main/webapp/footer.jsp 추가
  - 하단 스크립트 태그를 출력한다.
  
### 훈련2: 게시글 상세 정보 출력에 JSP를 적용한다.

- src/main/webapp/board/detail.jsp 추가
  - BoardDetailServlet의 출력을 이 JSP에 작성한다.
- com.eomcs.lms.servlet.BoardDetailServlet 변경
  - 서비스 객체를 통해 출력할 데이터를 준비한다.
  - 출력은 JSP에게 위임한다.

### 훈련3: 게시글 입력, 변경폼 출력에 JSP를 적용한다.

- src/main/webapp/board/form.jsp 추가
  - BoardAddServlet의 출력을 이 JSP에 작성한다.
- com.eomcs.lms.servlet.BoardAddServlet 변경
  - 출력은 JSP에게 위임한다.
- src/main/webapp/board/updateform.jsp 추가
  - BoardUpdateServlet의 출력을 이 JSP에 작성한다.
- com.eomcs.lms.servlet.BoardUpdateServlet 변경
  - 서비스 객체를 통해 출력할 데이터를 준비한다.
  - 출력은 JSP에게 위임한다.
  
### 훈련4: 수업관리에 JSP를 적용한다.

- com.eomcs.lms.servlet.LessonListServlet 변경
  - src/main/webapp/lesson/list.jsp 추가
- com.eomcs.lms.servlet.LessonSearchServlet 변경
  - src/main/webapp/lesson/search.jsp 추가
- com.eomcs.lms.servlet.LessonAddServlet 변경
  - src/main/webapp/lesson/form.jsp 추가
- com.eomcs.lms.servlet.LessonDetailServlet 변경
  - src/main/webapp/lesson/detail.jsp 추가
  
### 훈련5: 회원관리에 JSP를 적용한다.

- com.eomcs.lms.servlet.MemberListServlet 변경
  - src/main/webapp/member/list.jsp 추가
- com.eomcs.lms.servlet.MemberSearchServlet 변경
  - src/main/webapp/member/search.jsp 추가
- com.eomcs.lms.servlet.MemberAddServlet 변경
  - src/main/webapp/member/form.jsp 추가
- com.eomcs.lms.servlet.MemberDetailServlet 변경
  - src/main/webapp/member/detail.jsp 추가
  
### 훈련6: 사진게시물 관리에 JSP를 적용한다.

- com.eomcs.lms.servlet.PhotoBoardListServlet 변경
  - src/main/webapp/photoboard/list.jsp 추가
- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - src/main/webapp/photoboard/form.jsp 추가
- com.eomcs.lms.servlet.PhotoBoardDetailServlet 변경
  - src/main/webapp/photoboard/detail.jsp 추가
  
### 훈련7: 로그인에 JSP를 적용한다.

- com.eomcs.lms.servlet.LoginServlet 변경
  - src/main/webapp/auth/form.jsp 추가
  - src/main/webapp/auth/login.jsp 추가