# 58_1 - Front Controller 설계 기법 적용하기

## 학습목표

- Front Controller 설계 기법의 이점을 이해한다.

### Front Controller

- 컨트롤러들의 공통 기능을 가져와서 통합 처리한다.
- 외부의 접점을 하나로 줄임으로써 요청을 제어하기가 쉬워진다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/servlet/DispatcherServlet.java 추가
- src/main/java/com/eomcs/lms/servlet/XxxServlet.java 변경
- src/main/webapp/member/detail.jsp 변경
- src/main/webapp/photoboard/detail.jsp 변경
- src/main/webapp/index.html 변경

## 실습  

### 훈련1: 프론트 컨트롤러 역할을 수행할 서블릿을 만든다.

- com.eomcs.lms.servlet.DispatcherServlet 추가
  - /app/* 요청을 처리한다.

### 훈련2: 프론트 컨트롤러 역할에 맞춰서 기존의 서블릿을 페이지 컨트롤러로 변경한다.

- com.eomcs.lms.servlet.XxxServlet 변경
  - 직접 JSP를 인클루딩 하는 대신에 JSP URL을 ServletRequest에 저장한다.
  - 직접 리다이렉트 하는 대신에 리다이렉트 URL을 ServletRequest에 저장한다.
  - 직접 예외처리 서블릿으로 포워딩 하는 대신에 ServletRequest에 저장한다.
- src/main/webapp/member/detail.jsp 변경
  - 절대 경로를 이용하여 사진 경로 표현
- src/main/webapp/photoboard/detail.jsp 변경
  - 절대 경로를 이용하여 사진 경로 표현
  
### 훈련3: index.html의 메뉴 링크를 개정한다.

- src/main/webapp/index.html 변경
  - /app/* 경로에 맞춰 메뉴 링크를 변경한다.