# 58_3 - CRUD 페이지 컨트롤러를 한 클래스로 합친다.

## 학습목표

- CRUD로 분리된 클래스를 한 개의 클래스로 합칠 수 있다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/web/BoardController.java 추가
- src/main/java/com/eomcs/lms/web/LessonController.java 추가
- src/main/java/com/eomcs/lms/web/AuthController.java 추가
- src/main/java/com/eomcs/lms/web/MemberController.java 추가
- src/main/java/com/eomcs/lms/web/PhotoBoardController.java 추가
- src/main/java/com/eomcs/lms/servlet/BoardXxxServlet.java 삭제
- src/main/java/com/eomcs/lms/servlet/LessonXxxServlet.java 삭제
- src/main/java/com/eomcs/lms/servlet/LoginServlet.java 삭제
- src/main/java/com/eomcs/lms/servlet/LogoutXxxServlet.java 삭제
- src/main/java/com/eomcs/lms/servlet/MemberXxxServlet.java 삭제
- src/main/java/com/eomcs/lms/servlet/PhotoBoardXxxServlet.java 삭제
- src/main/java/com/eomcs/lms/ContextLoaderListener.java 변경

## 실습  

### 훈련1: 게시판 기능의 CRUD 페이지 컨트롤러를 한 클래스로 합친다.

- com.eomcs.lms.web.BoardController 추가
  - BoardAddController, BoardListController, BoardDetailConroller, 
    BoardUpdateController, BoardDeleteController의 메서드를 가져온다.
- com.eomcs.lms.web.BoardXxxController 삭제

### 훈련2: 수업,회원,로그인/로그아웃,사진게시판도 훈련1과 마찬가지로 처리한다.

- com.eomcs.lms.web.LessonController 추가
  - com.eomcs.lms.web.LessonXxxController 삭제
- com.eomcs.lms.web.AuthController 추가
  - com.eomcs.lms.web.LoginController 삭제
  - com.eomcs.lms.web.LogoutController 삭제
- com.eomcs.lms.web.MemberController 추가
  - com.eomcs.lms.web.MemberXxxController 삭제
- com.eomcs.lms.web.PhotoBoardController 추가
  - com.eomcs.lms.web.PhotoBoardXxxController 삭제

### 훈련3: 페이지 컨트롤러에 있는 여러 개의 요청 핸들러를 관리 목록에 등록한다.

- com.eomcs.lms.ContextLoaderListener 변경
  - 페이지 컨트롤러에서 모든 @RequestMapping 메서드를 찾아 등록한다.