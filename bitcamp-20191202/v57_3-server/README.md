# 57_3 - JSP에 JSTL 적용하기

## 학습목표

- JSTL을 사용법을 안다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/servlet/XxxDetailServlet.java 변경
- src/main/webapp/**/*.jsp 변경

## 실습  

### 훈련1: JSTL 라이브러리를 준비한다.

- search.maven.org 사이트에서 jstl 라이브러를 검색한다.
- jstl 라이브러리 정보를 build.gradle 에 추가한다.
- 'gradle eclipse'를 실행하여 프로젝트에 라이브러리를 적용한다.
- 이클립스의 프로젝트를 리프래시 한다.

### 훈련2: JSP에 JSTL을 적용한다.

- com.eomcs.lms.servlet.XxxDetailServlet 변경
  - 상세 조회에서 해당 번호의 데이터가 없더라도 예외를 발생시키지 않는다.
  - JSP에서 처리한다. 
- src/main/webapp/**/*.jsp 변경