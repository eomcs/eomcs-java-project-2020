# 44_1 - UI 객체에서 비즈니스 로직 분리하기

비즈니스 로직을 별도의 클래스로 분리하면,
UI 구현 방식이 변경되더라도 비즈니스 로직을 재사용할 수 있다.

## 학습목표

- Presentation/Service(Business)/Persistence Layer의 구조를 이해한다.

### Presentation Layer

- UI를 담당한다.

### Business(Service) Layer

- 업무 로직을 담당한다.
- 트랜잭션 제어를 담당한다.

### Persistence Layer

- 데이터 저장을 담당한다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/service/BoardService.java 추가
- src/main/java/com/eomcs/lms/service/LessonService.java 추가
- src/main/java/com/eomcs/lms/service/MemberService.java 추가
- src/main/java/com/eomcs/lms/service/PhotoBoardService.java 추가
- src/main/java/com/eomcs/lms/service/impl/BoardServiceImpl.java 추가
- src/main/java/com/eomcs/lms/service/impl/LessonServiceImpl.java 추가
- src/main/java/com/eomcs/lms/service/impl/MemberServiceImpl.java 추가
- src/main/java/com/eomcs/lms/service/impl/PhotoBoardServiceImpl.java 추가
- src/main/java/com/eomcs/lms/ServerApp.java 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/servlet/XxxServlet.java 변경

## 실습  

### 훈련1: PhotoBoardXxxServlet 에서 비즈니스 로직을 분리한다.

- com.eomcs.lms.service 패키지 추가
- com.eomcs.lms.service.PhotoBoardService 인터페이스 추가
- com.eomcs.lms.service.LessonService 인터페이스 추가
- com.eomcs.lms.service.impl.PhotoBoardServiceImpl 클래스 추가
- com.eomcs.lms.service.impl.LessonServiceImpl 클래스 추가
- com.eomcs.lms.servlet.PhotoBoardXxxServlet 변경
  - 비즈니스 로직과 트랜잭션 제어 코드를 서비스 객체로 옮긴다.
- com.eomcs.lms.DataLoaderListener 변경
  - 서비스를 객체를 생성한다.
- com.eomcs.lms.ServerApp 변경
  - 서블릿에 서비스 객체를 주입한다.
  
### 훈련2: BoardXxxServlet 에서 비즈니스 로직을 분리한다.

- com.eomcs.lms.service.BoardService 인터페이스 추가
- com.eomcs.lms.service.impl.BoardServiceImpl 클래스 추가
- com.eomcs.lms.servlet.BoardXxxServlet 변경
  - 비즈니스 로직과 트랜잭션 제어 코드를 서비스 객체로 옮긴다.
- com.eomcs.lms.DataLoaderListener 변경
  - 서비스를 객체를 생성한다.
- com.eomcs.lms.ServerApp 변경
  - 서블릿에 서비스 객체를 주입한다.
  
### 훈련3: MemberXxxServlet 에서 비즈니스 로직을 분리한다.

- com.eomcs.lms.service.MemberService 인터페이스 추가
- com.eomcs.lms.service.impl.MemberServiceImpl 클래스 추가
- com.eomcs.lms.servlet.MemberXxxServlet 변경
  - 비즈니스 로직과 트랜잭션 제어 코드를 서비스 객체로 옮긴다.
- com.eomcs.lms.servlet.LoginServlet 변경
  - 비즈니스 로직과 트랜잭션 제어 코드를 서비스 객체로 옮긴다.
- com.eomcs.lms.DataLoaderListener 변경
  - 서비스를 객체를 생성한다.
- com.eomcs.lms.ServerApp 변경
  - 서블릿에 서비스 객체를 주입한다.
  
### 훈련4: LessonXxxServlet 에서 비즈니스 로직을 분리한다.

- com.eomcs.lms.service.LessonService 인터페이스 변경
- com.eomcs.lms.service.impl.LessonServiceImpl 클래스 변경
- com.eomcs.lms.servlet.LessonXxxServlet 변경
  - 비즈니스 로직과 트랜잭션 제어 코드를 서비스 객체로 옮긴다.
- com.eomcs.lms.DataLoaderListener 변경
  - 서비스를 객체를 생성한다.
- com.eomcs.lms.ServerApp 변경
  - 서블릿에 서비스 객체를 주입한다.