# 33-b. `Observer` 디자인 패턴 : `Observer` 객체를 통해 파일 다루기

이번 훈련에서는,
- **Observer 디자인 패턴** 의 활용을 연습할 것이다.

## 훈련 목표
- Observer 패턴 구조에서 기능을 추가하는 방법을 연습한다.
- 발행자와 구독자 간에 데이터를 공유하는 방법을 연습한다.

## 훈련 내용
- App 클래스가 하던 데이터 로딩 및 저장 기능을 옵저버로 옮긴다.
- 옵저버와 발행자 간의 객체를 공유한다.


## 실습

### 1단계 - 발행자와 옵저버(구독자) 간의 데이터를 공유할 수 있도록 규칙에 파라미터를 추가한다.

- `ApplicationContextListener` 인터페이스 변경
  - contextInitialized(), contextDestroyed() 메서드에 Map 타입의 파라미터 추가한다.
- `AppInitListener` 클래스 변경
  - 변경된 규칙에 따라 구현 메서드에 파라미터 추가한다.
- `App` 클래스 변경
  - 옵저버를 호출할 때 맵 객체를 넘겨준다.

#### 작업 파일
- com.eomcs.context.ApplicationContext 변경
- com.eomcs.context.AppInitContext 변경
- com.eomcs.pms.App 변경


### 2단계 - 파일에서 데이터를 로딩하고 저장하는 기능을 옵저버로 옮긴다.

- `DataHandlerListener` 클래스 생성
  - 옵저버의 규칙인 ApplicationContextListener 를 구현한다.
  - contextInitialized()에서 게시글, 회원, 프로젝트, 작업 데이터를 파일에서 로딩한다.
  - contextDestroyed()에서 그 데이터를 파일에 JSON 형식으로 저장한다.
  - App 클래스에서 파일 데이터를 로딩하고 저장하는 코드를 이 클래로 옮긴다.
- `App` 클래스 변경
  - `DataHandlerListener` 옵저버를 등록한다.

#### 작업 파일
- com.eomcs.pms.listener.DataHandlerListener 생성
- com.eomcs.pms.App 변경


## 실습 결과
- src/main/java/com/eomcs/context/ApplicationContextListener.java 변경
- src/main/java/com/eomcs/pms/listener/AppInitListener.java 변경
- src/main/java/com/eomcs/pms/listener/DataHandlerListener.java 추가
- src/main/java/com/eomcs/pms/App.java 변경
