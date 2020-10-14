# 34-f. 네트워크 API를 활용하여 C/S 구조로 전환하기 :  다중 클라이언트의 동시 접속 처리

이번 훈련에서는,
- **자바 네트워크 API** 를 사용하여 클라이언트/서버 통신 애플리케이션을 만든다. 

## 훈련 목표
- Gradle 빌드 도구를 이용하여 자바 애플리케이션 프로젝트를 만드는 것을 연습한다.
- Gradle 빌드 도구를 이용하여 Eclipse IDE 용 프로젝트로 전환하는 것을 연습한다.

## 훈련 내용
- 자바 프로젝트로 사용할 폴더를 만들고 Gradle 빌드 도구로 초기화시킨다.
- Gradle eclipse 플러그인을 사용하여 Eclipse IDE 용 설정 파일을 준비한다.


## 실습

### 1단계 - 클라이언트의 요청 처리 부분을 별도의 스레드로 분리하여 처리한다.

- `ClientHandler` 클래스 추가
  - Runnable 인터페이스를 구현한다.
  - 클라이언트의 요청 처리를 담당한다. 
  - ServerApp 에서 해당 코드를 가져온다.
- `ServerApp` 클래스 변경
  - 클라이언트 요청 처리를 ClientHandler에게 맡긴다.
  
#### 작업 파일
- com.eomcs.pms.ClientHandler 추가
- com.eomcs.pms.ServerApp 변경
  - 백업: ServerApp.java.01

### 2단계 - `ClientHandler` 를 `ServerApp` 의 중첩 클래스로 만든다.

- `ClientHandler` 클래스 변경
  - `ServerApp` 클래스로 옮긴다.
- `ServerApp` 클래스 변경

  
#### 작업 파일
- com.eomcs.pms.ClientHandler 추가
- com.eomcs.pms.ServerApp 변경
  - 백업: ServerApp01.java

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

