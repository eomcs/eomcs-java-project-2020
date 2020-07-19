# 59_1 - Spring WebMVC 적용하기

## 학습목표

- Spring WebMVC 프레임워크를 프로젝트에 적용할 수 있다.
- Spring WebMVC 프레임워크의 규칙에 따라 웹 애플리케이션을 작성할 수 있다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ContextLoaderListener.java 삭제
- src/main/java/com/eomcs/lms/servlet/DispatcherServlet.java 삭제
- src/main/java/com/eomcs/lms/filter/CharacterEncodingFilter.java 삭제
- src/main/java/com/eomcs/util/RequestHandler.java 삭제
- src/main/java/com/eomcs/util/RequestMapping.java 삭제
- src/main/java/com/eomcs/util/RequestMappingHandlerMapping.java 삭제
- src/main/java/com/eomcs/lms/web/XxxController.java 변경
- src/main/webapp/WEB-INF/web.xml 변경


## 실습  

### 훈련1: Spring WebMVC 프레임워크를 프로젝트에 적용한다.

- 라이브러리 가져오기
  - search.maven.org 에서 'spring-webmvc' 검색한다.
  - 라이브러리 정보를 build.gradle에 추가한다.
  - 'gradle eclipse'를 실행하여 이클립스 설정 파일을 갱신한다.
  - 이클립스에서 프로젝트를 갱신한다.

### 훈련2: Spring WebMVC에서 제공하는 프론트 컨트롤러 서블릿을 설정한다.

- 기존의 프론트 컨트롤러 관련 클래스는 삭제한다.
  - com.eomcs.lms.ContextLoaderListener 삭제
  - com.eomcs.lms.filter.CharacterEncodingFilter 삭제
  - com.eomcs.lms.servlet.DispatcherServlet 삭제
  - com.eomcs.util.RequestMapping 삭제
  - com.eomcs.util.RequestHandler 삭제
  - com.eomcs.util.RequestMappingHandlerMapping 삭제
- src/main/webapp/WEB-INF/web.xml 변경
  - DispatcherServlet 클래스를 등록한다.
  - CharacterEncodingFilter 클래스를 등록한다.
  - multipart-config를 설정한다.

### 훈련3: 페이지 컨트롤러를 Spring WebMVC 프레임워크 사용법에 따라 변경한다.

- com.eomcs.lms.web.XxxController.java 변경
  - @Component 대신에 @Controller로 교체한다.
  - @RequestMapping 애노테이션의 패키지를 Spring WebMVC 라이브러리 것으로 교체한다.




