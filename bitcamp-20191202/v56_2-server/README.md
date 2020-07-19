# 56_2 - 서블릿을 이용하여 스프링 IoC 컨테이너 준비하기

## 학습목표

- 서블릿을 통해 공통 자원을 준비시킬 수 있다.
- load-on-startup 의 용도를 이해한다.
- init() 메서드의 용도를 이해한다.

## 실습 소스 및 결과

- com.eomcs.lms.ContextLoaderListener 변경
- com.eomcs.lms.servlet.AppInitServlet 추가
- src/main/webapp/WEB-INF/web.xml 추가


## 실습  

### 훈련1: 서블릿에서 Spring IoC 컨테이너를 준비하여 공유하게 한다.

- com.eomcs.lms.ContextLoaderListener 변경
  - 리스너를 비활성화시킨다.
    - @WebListener 애노테이션을 주석으로 막는다.
- com.eomcs.lms.servlet.AppInitServlet 추가
  - 웹 애플리케이션이 시작할 때 자동 생성되게 한다.
  - BoardAddFormServlet의 코드를 doGet() 메서드로 옮긴다.
    - @WebServlet 애노테이션에 loadOnStartup 속성 추가
  - service() 또는 doXxx() 메서드는 오버라이딩 하지 않는다.
    - 왜? 클라이언트 요청을 처리하는 일을 하지 않는다.
    - 이 클래스는 다른 서블릿이 사용할 IoC 컨테이너를 준비한다.

