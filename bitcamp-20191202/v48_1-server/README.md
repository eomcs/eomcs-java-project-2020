# 48_1 - 인터페이스 대신 애노테이션으로 메서드 구분하기

인터페이스는 규칙이기 때문에 구현이 매우 엄격하다.
메서드 이름에서 파라미터 타입/개수, 리턴 타입까지 정확하게 구현해야 한다.
애노테이션을 사용하면 인터페이스 보다 더 유현하게 규칙을 처리할 수 있다.

## 학습목표

- 애노테이션을 정의하고 활용할 수 있다.
- 리플랙션 API를 활용하여 애노테이션 정보를 다룰 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/util/RequestMapping.java 추가
- src/main/java/com/eomcs/lms/servlet/Servlet.java 삭제
- src/main/java/com/eomcs/lms/servlet/XxxServlet.java 변경
- src/main/java/com/eomcs/util/ApplicationContext.java 변경
- src/main/java/com/eomcs/lms/ContextLoaderListener.java 변경
- src/main/java/com/eomcs/util/RequestHandler.java 추가
- src/main/java/com/eomcs/util/RequestMappingHandlerMapping.java 추가
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: 명령어를 처리할 메서드를 지정할 때 사용할 애노테이션을 정의한다.

- com.eomcs.util.RequestMapping 애노테이션 추가

### 훈련2: 명령어를 처리할 메서드에 @RequestMapping 애노테이션을 붙인다.

- com.eomcs.lms.servlet.Servlet 인터페이스 삭제
- com.eomcs.lms.servlet.XxxServlet 변경
  - 메서드 선언부에 @RequestMapping 애노테이션을 붙인다.

### 훈련3: 특정 애노테이션이 붙은 객체의 이름 목록을 리턴하는 메서드를 추가한다.

- com.eomcs.util.ApplicationContext 변경
  - getBeanNamesForAnnotation()을 추가한다.
  
### 훈련4: @Component 애노테이션이 붙은 객체를 찾는다.

- com.eomcs.lms.ContextLoaderListener 변경
  - ApplicationContext가 보관하고 있는 객체 중에서 @Component가 붙은 객체만 찾는다. 
  
### 훈련5: @Component가 붙은 객체에서 @RequestMapping이 붙은 메서드가 있는지 찾는다.

- com.eomcs.lms.ContextLoaderListener 변경
  - @Component가 붙은 객체를 조사하여 @RequestMapping이 선언된 메서드 있는지 알아낸다.
  - getRequestHandler()를 추가한다.
  
### 훈련6: request handler의 정보를 저장할 클래스를 정의한다.

- com.eomcs.util.RequestHandler 추가
  - 객체 주소와, 메서드 정보, 객체 이름을 보관한다.
- com.eomcs.lms.ContextLoaderListener 변경
  - request handler를 RequestHandler 객체에 담는다.
  
### 훈련7: request handler 목록을 보관할 클래스를 정의한다.

- com.eomcs.util.RequestMappingHandlerMapping 추가
  - @RequestMapping 애노테이션이 붙은 메서드의 정보를 보관한다. 

### 훈련8: 훈련6에서 찾은 request handler를 목록에 보관한다.

- com.eomcs.lms.ContextLoaderListener 변경
  - RequestHandler 객체를 RequestMappingHandlerMapping 객체에 보관한다.
  
### 훈련9: RequestMappingHandlerMapping 에 보관된 객체를 사용하여 명령을 처리한다.

- com.eomcs.lms.ServerApp 변경
  - 공유 맵에서 이 객체를 꺼낸다.
  - RequestMappingHandlerMapping 객체에서 명령을 처리할 메서드를 꺼낸다.
  - 그 메서드를 호출하여 클라이언트에게 응답한다.
  