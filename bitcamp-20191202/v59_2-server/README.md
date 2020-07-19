# 59_2 - Spring WebMVC 적용하기 II : MultipartResolver, ViewResolver 등

## 학습목표

- @RequestMapping, @GetMapping, @PostMapping의 사용법을 안다.
- 멀티파트 데이터를 처리하기 위해 MultipartResolver를 설정할 수 있다.
- ViewResolver의 동작원리를 이해한다.
- ViewResolver를 교체할 수 있다.

## 실습 소스 및 결과

- src/main/webapp/WEB-INF/web.xml 변경
- src/main/java/com/eomcs/lms/AppWebApplicationInitializer.java 추가
- src/main/java/com/eomcs/lms/AppConfig.java 변경
- src/main/java/com/eomcs/lms/web/XxxController.java 변경
- src/main/webapp/**/*.jsp 이동 및 변경
  - src/main/webapp/WEB-INF/jsp/ 폴더로 이동
  
## 실습  

### 훈련1: WebApplicationInitializer를 사용하여 DispatcherServlet을 설정한다.

- com.eomcs.lms.web.AppWebApplicationInitializer 추가 
- com.eomcs.lms.AppConfig 변경
  - ViewResolver 객체 등록
  - MultipartResolver 객체 등록
  - WebMVC 관련 애노테이션을 처리할 객체 등록 : @EnableWebMVC
 

### 훈련2: JSP 파일을 /WEB-INF/jsp/ 폴더로 옮긴다.

- src/main/webapp/**/*.jsp 를 /WEB-INF/jsp/ 로 옮긴다.
- src/main/webapp/**/*.jsp 변경
  - header.jsp, footer.jsp 경로를 변경한다.

### 훈련3: 페이지 컨트롤러를 Spring WebMVC 에 맞춰 변경한다.

- com.eomcs.lms.web.*Controller 변경
  - @RequestMapping을 클래스 선언부에도 붙인다.
  - @RequestMapping 대신에 @GetMapping 또는 @PostMapping을 사용한다.
  - 멀티파트 파라미터 값을 받는 객체를 Part에서 MultipartFile로 교체한다.
  - InternalResourceViewResolver의 동작에 맞춰 리턴 값을 변경한다.

### 훈련4: 날짜 형식의 요청 데이터를 다룰 프로퍼티 에디터를 등록한다.

- com.eomcs.lms.web.GlobalControllerAdvice 추가 
  - 페이지 컨트롤러에 보조할 객체를 등록하기 위해 @ControllerAdvice 클래스를 정의한다.
  - 날짜 파라미터를 처리하기 위해 @InitBinder 메서드를 정의한다.

