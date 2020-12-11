# 46-a. Spring WebMVC 프레임워크 도입하기 : DispatcherServlet 프론트 컨트롤러 적용

이번 훈련에서는,
-

## 훈련 목표
-

## 훈련 내용
-

## 실습


### 1단계 - Spring WebMVC 프레임워크 라이브러리 가져온다.

- search.maven.org 사이트에서 `spring-webmvc` 검색 한다.
- build.gradle 파일에 의존 라이브러리 정보를 추가한다.
- `$ gradle eclipse` 를 실행한다.
- 이클립스 웹 프로젝트를 리프래시 한다.
- 프로젝트에 해당 라이브러리가 제대로 임포트되었는지 확인한다.

### 2단계 - 프론트 컨트롤러를 설정한다.

- /WEB-INF/web.xml 변경
  - Spring WebMVC 프레임워크에서 제공해주는 프론트 컨트롤러를 등록한다.
- /WEB-INF/app-servlet.xml 생성
  - 프론트 컨트롤러(DispatcherServlet)가 사용할 설정 파일을 준비한다.

### 3단계 - 프론트 컨트롤러에서 객체를 생성할 수 있도록 설정한다.

- 페이지 컨트롤러 클래스 설정하기
  - `com.eomcs.pms.web.Controller` 인터페이스 삭제
  - `com.eomcs.pms.web.RequestMapping` 애노테이션 삭제
  - `com.eomcs.pms.web.*Controller` 클래스 변경
    - @Controller 애노테이션을 붙여 해당 클래스가 페이지 컨트롤러임을 지정하라.
    - @RequestMapping 애노테이션을 클라이언트 요청을 처리하는 메서드 선언에 붙여라.

### 4단계 - 페이지 컨트롤러의 의존 객체를 설정한다.

페이지 컨트롤러가 사용할 서비스 객체 생성을 프론트 컨트롤러에게 맡긴다.

- 서비스 클래스 설정하기
  - `com.eomcs.pms.service.Default*Service` 변경한다.
  - @Service 애노테이션을 붙여라.

### 5단계 - 서비스 객체의 의존 객체를 설정한다.

서비스 객체가 사용할 DAO 객체 생성을 프론트 컨트롤러에게 맡긴다.

- DAO 클래스 설정하기
  - `com.eomcs.pms.dao.mariadb.*DaoImpl` 변경한다.
  - @Repository 애노테이션을 붙인다.

### 6단계 - DAO 객체의 의존 객체를 설정한다.

DAO 객체가 사용할 SqlSessionFactory 객체 생성을 프론트 컨트롤러에게 맡긴다.

- SqlSessionFactory 클래스 설정하기
  - `com.eomcs.pms.util.SqlSessionFactoryProxy` 변경한다.
  - @Component 애노테이션을 붙인다.
  - 생성자 변경한다.



## 실습 결과

- src/main/java/com/eomcs/pms/ServerApp.java 변경
