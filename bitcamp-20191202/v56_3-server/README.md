# 56_3 - 리다이렉트와 리프래시 활용

## 학습목표

- 리프래시를 구현할 수 있다.
- 리다이렉트를 구현할 수 있다.
- 리프래시와 리다이렉트의 차이점을 이해한다.
- HttpSession 보관소를 활용할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/servlet/ErrorServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/XxxServlet 변경


## 실습  

### 훈련1: 오류 내용을 출력할 서블릿을 만든다.

- com.eomcs.lms.servelt.ErrorServlet 추가 
  - 세션에 보관된 메시지를 출력한다.

### 훈련2: 추가/변경/삭제 서블릿에 리프래시, 리다이렉트를 적용한다.

- com.eomcs.lms.servlet.XxxServlet 변경
  - 정상적으로 실행했을 경우 목록 화면으로 리다이렉트 한다.
  - 오류가 발생했을 경우 ErrorServlet 으로 리다이렉트 한다.

