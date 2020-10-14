# 34-g. 네트워크 API를 활용하여 C/S 구조로 전환하기 : PMS 코드를 C/S로 분리

이번 훈련에서는,
- **자바 네트워크 API** 를 사용하여 클라이언트/서버 통신 애플리케이션을 만든다. 

## 훈련 목표
- Gradle 빌드 도구를 이용하여 자바 애플리케이션 프로젝트를 만드는 것을 연습한다.
- Gradle 빌드 도구를 이용하여 Eclipse IDE 용 프로젝트로 전환하는 것을 연습한다.

## 훈련 내용
- 자바 프로젝트로 사용할 폴더를 만들고 Gradle 빌드 도구로 초기화시킨다.
- Gradle eclipse 플러그인을 사용하여 Eclipse IDE 용 설정 파일을 준비한다.


## 실습

### 1단계 - JSON 데이터 포맷을 다룰 Gson 라이브러리를 추가한다.

- **build.gradle** 파일에 gson 라이브러리 정보를 추가한다.
- `$ gradle eclipse` 를 실행하여 라이브러리를 프로젝트에 추가한다.
- 이클립스 IDE에서 프로젝트를 `refresh` 한다.

### 2단계 - 기존 애플리케이션에서 관련된 패키지 및 클래스를 가져온다.

- com.eomcs.pms.domain 패키지 및 그 하위 클래스를 가져온다.
- com.eomcs.pms.handler 패키지 및 그 하위 클래스를 가져온다.
- com.eomcs.context 패키지 및 그 하위 클래스를 가져온다.
- com.eomcs.pms.listener 패키지 및 그 하위 클래스를 가져온다.
- com.eomcs.pms.App 클래스에서 옵저버 패턴과 관련된 코드를 가져온다.

#### 작업 파일
- com.eomcs.pms.domain.* 추가
- com.eomcs.pms.handler.* 추가
- com.eomcs.context.* 추가
- com.eomcs.pms.listener.* 추가
- com.eomcs.pms.ServerApp 변경


### 3단계 - 클라이언트의 "stop" 명령을 처리한다.

- `ServerApp` 변경
  - stop 변수 추가
  - 클라이언트와 연결할 때 "stop"의 상태가 true 이면 서버를 멈춘다.

#### 작업 파일
- com.eomcs.pms.ServerApp 변경

### 4단계 - 파일에서 JSON 데이터를 로딩하고 파일로 저장하는 옵저버를 등록한다.

- `ServerApp` 변경
  - AppInitListener 를 등록한다.
  - DataHandlerListener 를 등록한다.

#### 작업 파일
- com.eomcs.pms.ServerApp 변경


### 5단계 - 클라이언트의 요청을 처리하는 Command 객체를 준비한다.

- `RequestMappingListener` 생성
  - `DataHandlerListener` 가 준비한 데이터를 가지고 Command 객체를 생성한다.
- `ServerApp` 변경
  - `RequestMappingListener` 를 등록한다.

#### 작업 파일
- com.eomcs.pms.listener.RequestMappingListener 생성
- com.eomcs.pms.ServerApp 변경

### 6단계 - 클라이언트 명령이 들어오면 커맨드 객체를 찾아 실행한다.

- `ServerApp` 변경
  - `handleClient()` 에 커맨드 객체를 실행하는 코드를 추가한다.
- `Command` 구현체 변경

#### 작업 파일
- com.eomcs.pms.ServerApp 변경


  
## 실습 결과
- src/main/java/com/eomcs/pms/ClientHandler.java 추가
- src/main/java/com/eomcs/pms/ServerApp.java 변경






## 학습목표

- 클라이언트의 연결을 기다리고 데이터 통신을 수행할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련 1: 클라이언트 연결을 대기하라.

- ServerApp.java 변경

### 훈련 2: 클라이언트가 보낸 메시지를 읽고 응답 메시지를 전송하라.

- ServerApp.java 변경

