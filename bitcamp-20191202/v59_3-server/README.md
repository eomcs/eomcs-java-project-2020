# 59_3 - Spring WebMVC 적용하기 III : DispatcherServlet 여러 개 설정하기

## 학습목표

- Spring의 WebApplicationInitializer 구현체를 통해 
  여러 개의 DispatcherServlet을 설정할 수 있다.
- 공통 컴포넌트와 DispatcherServlet 전용 컴포넌트를 분리하여 관리할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/web/AppWebApplicationInitializer.java 변경
- src/main/java/com/eomcs/lms/AppConfig.java 변경
- src/main/java/com/eomcs/lms/web/AppWebConfig.java 추가
- src/main/java/com/eomcs/lms/admin/AdminWebApplicationInitializer.java 변경
- src/main/java/com/eomcs/lms/admin/AdminWebConfig.java 추가
- src/main/java/com/eomcs/lms/admin/BoardController.java 추가
- src/main/webapp/WEB-INF/admin/ 폴더 생성
- src/main/webapp/WEB-INF/admin/board/*.jsp 추가
  
## 실습  

### 훈련1: IoC 컨테이너의 설정을 공통 부분과 서블릿 부분으로 분리한다.

- com.eomcs.lms.AppConfig 변경
  - DispatcherServlet 관련 객체는 AppWebConfig로 분리한다.
- com.eomcs.lms.web.AppWebConfig 추가
  - DispatcherServlet과 관련된 객체를 설정한다.
- com.eomcs.lms.web.AppWebApplicationInitializer 변경 
  - root config에는 AppConfig로 설정한다.
  - servlet config는 AppWebConfig로 설정한다.
  
### 훈련2: 관리자 기능을 별도의 DispatcherServlet으로 분리한다.

- com.eomcs.lms.AppConfig 변경
  - 관리자 기능을 구현한 com.eomcs.lms.admin 패키지를 제외한다.
- com.eomcs.lms.admin.AdminWebConfig 추가
  - /admin/* 요청을 처리할 DispatcherServlet과 관련된 객체를 설정한다.
- com.eomcs.lms.admin.AdminWebApplicationInitializer 변경 
  - /admin/* 요청을 처리할 DispatcherServlet을 설정한다.
- com.eomcs.lms.admin 패키지 추가
- com.eomcs.lms.admin.BoardController 추가
  - /admin/board/* 요청을 처리할 페이지 컨트롤러를 추가한다. 
- src/main/webapp/WEB-INF/admin/ 폴더를 추가한다.
  - /admin/* 요청과 관련된 JSP 파일을 둔다.