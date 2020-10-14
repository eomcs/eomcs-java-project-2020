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
  - 백업: ServerApp01.java


### 2단계 - `ClientHandler` 를 `ServerApp` 의 중첩 클래스로 만든다.

- `ServerApp` 클래스 변경
  - `ClientHandler` 클래스를 중첩 클래스(static nested class)로 만든다.

#### 작업 파일
- com.eomcs.pms.ServerApp 변경
  - 백업: ServerApp02.java

### 3단계 - `ClientHandler` 를 `ServerApp.main()` 의 익명 클래스로 만든다.

- `ServerApp` 클래스 변경
  - `ClientHandler` 스태틱 중첩 클래스를 main()의 익명 클래스로 만든다.

#### 작업 파일
- com.eomcs.pms.ServerApp 변경
  - 백업: ServerApp03.java

### 4단계 - `ClientHandler` 를 `ServerApp.main()` 의 익명 클래스로 만든다. II

- `ServerApp` 클래스 변경
  - 익명 클래스의 코드를 밖깥 클래스의 멤버로 만들어 놓고 사용한다.
  - 왜? 코드를 읽기 쉽도록 하기 위함이다.
  - 코드가 여러 블록에 중접되면 될 수록 들여쓰기 하면서 
    코드를 읽기가 불편해진다.

#### 작업 파일
- com.eomcs.pms.ServerApp 변경
  - 백업: ServerApp04.java

### 5단계 - 익명 클래스를 람다(lambda) 문법으로 정의한다.

- `ServerApp` 클래스 변경
  - 익명 클래스가 메서드 한 개짜리 인터페이스를 구현하고,
    그 코드도 간단하다면, 
    람다 문법으로 표현하는 것이 편하다.

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

