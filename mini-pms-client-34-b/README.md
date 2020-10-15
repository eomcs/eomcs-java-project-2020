# 34-b. 네트워크 API를 활용한 C/S 아키텍처 : 간단한 메시지 송수신

이번 훈련에서는,
- **자바 네트워크 API** 를 사용하여 클라이언트/서버 통신 애플리케이션을 만든다.

## 훈련 목표
- 네트워크 API를 사용하여 간단한 메시지를 주고 받는 Client 만들기를 연습한다.

## 훈련 내용
- Socket 클래스를 이용하여 서버에 접속한다.
- 소켓의 입출력 스트림을 이용하여 서버와 데이터를 주고 받는다.


## 실습

### 1단계 -

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





## 학습목표

- 클라이언트를 서버와 통신 연결할 수 있다.
- 소켓을 통해 데이터를 입출력 할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ClientApp.java 변경

## 실습  

### 훈련 1: 서버와 연결하라.

- ClientApp.java 변경

### 훈련 2: 서버에 요청 메시지를 전송하고 응답 메시지를 수신하라.

- ClientApp.java 변경
