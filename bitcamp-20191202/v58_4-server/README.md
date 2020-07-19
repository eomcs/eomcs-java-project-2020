# 58_4 - request handler의 파라미터 값을 자동 주입하기

## 학습목표

- Reflection API를 이용하여 메서드의 파라미터 정보를 알아낼 수 있다.
- 메서드가 원하는 값을 주입하여 메서드를 호출할 수 있다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/web/BoardController.java 변경
- src/main/java/com/eomcs/lms/web/LessonController.java 변경
- src/main/java/com/eomcs/lms/web/AuthController.java 변경
- src/main/java/com/eomcs/lms/web/MemberController.java 변경
- src/main/java/com/eomcs/lms/web/PhotoBoardController.java 변경
- src/main/java/com/eomcs/lms/servlet/DispatcherServlet.java 변경
- src/main/java/com/eomcs/lms/util/RequestHandler.java 변경
- src/main/webapp/header.jsp 변경
- src/main/webapp/board/list.jsp 변경
- src/main/webapp/board/detail.jsp 변경
- src/main/webapp/lesson/list.jsp 변경
- src/main/webapp/lesson/detail.jsp 변경
- src/main/webapp/member/list.jsp 변경
- src/main/webapp/member/form.jsp 변경
- src/main/webapp/member/detail.jsp 변경
- src/main/webapp/photoboard/list.jsp 변경
- src/main/webapp/photoboard/form.jsp 변경
- src/main/webapp/photoboard/detail.jsp 변경

## 실습  

### 훈련1: 프론트 컨트롤러에서 요청 핸들러의 파라미터 값을 준비하게 한다.

- com.eomcs.lms.servlet.DispatcherServlet 변경
  - 요청 핸들러 요청 부분을 변경한다.
- com.eomcs.lms.util.RequestHandler 변경
  - 요청 핸들러를 호출하는 메서드를 추가한다.


### 훈련2: 프론트 컨트롤러의 변경에 맞춰 페이지 컨트롤러를 변경한다.

- com.eomcs.lms.web.XxxController 변경
- src/main/webapp/header.jsp 변경
  - 로그인 링크를 변경한다.
- src/main/webapp/board/list.jsp 변경
  - "새 글" 링크의 주소를 변경한다.
- src/main/webapp/board/detail.jsp 변경
  - "변경" 링크의 주소를 바꾼다.
- src/main/webapp/lesson/list.jsp 변경
  - "새 수업" 링크의 주소를 변경한다.
- src/main/webapp/lesson/detail.jsp 변경
  - "변경" 링크의 주소를 바꾼다.
- src/main/webapp/member/list.jsp 변경
  - "새 회원" 링크의 주소를 변경한다.
- src/main/webapp/member/form.jsp 변경
  - 사진 파라미터의 이름을 photo 에서 photoFile로 바꾼다.
- src/main/webapp/member/detail.jsp 변경
  - 사진 파라미터의 이름을 photo 에서 photoFile로 바꾼다.
- src/main/webapp/photoboard/list.jsp 변경
  - "새 사진" 링크의 주소를 변경한다.
- src/main/webapp/photoboard/form.jsp 변경
  - 사진 파라미터의 이름을 photo 에서 photoFiles로 바꾼다.
- src/main/webapp/photoboard/detail.jsp 변경
  - 사진 파라미터의 이름을 photo 에서 photoFiles로 바꾼다.
  