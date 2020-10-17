# 34-f. 네트워크 API를 활용한 C/S 아키텍처 :  다중 클라이언트의 동시 접속 처리

이번 훈련에서는,
- **네트워크 API** 를 이용하여 데스크톱 애플리케이션을 클라이언트/서버 구조로 변경한다.

데스크톱(desktop) 애플리케이션은,
- 다른 애플리케이션과 연동하지 않고 단독적으로 실행한다.
- 보통 PC나 노트북에 설치해서 사용한다.
- 예) MS-Word, Adobe Photoshop, 메모장 등

클라이언트(Client)/서버(Server) 애플리케이션은,
- 줄여서 C/S 애플리케이션이라 부른다.
- 클라이언트는 서버에게 서비스나 자원을 요청하는 일을 한다.
- 서버는 클라이언트에게 자원이나 서비스를 제공하는 일을 한다.


## 훈련 목표
- 스레드를 이용하여 다중 클라이언트 접속을 동시에 처리하는 것을 연습한다.
- 스레드를 만드는 다양한 방법을 배운다.
- 중첩 클래스와 람다 문법의 사용을 연습한다.

## 훈련 내용
- 자바 프로젝트로 사용할 폴더를 만들고 Gradle 빌드 도구로 초기화시킨다.
- Gradle eclipse 플러그인을 사용하여 Eclipse IDE 용 설정 파일을 준비한다.


## 실습

### 0단계 - 클라이언트의 요청 처리 부분을 별도의 스레드로 분리하여 처리한다.

- `com.eomcs.pms.ClientHandlerThread` 클래스 추가
  - `Thread` 를 상속 받는다.
  - 클라이언트의 요청 처리를 담당한다.
  - `ServerApp` 에서 해당 코드를 가져온다.
- `ServerApp` 클래스 변경
  - 클라이언트 요청 처리를 `ClientHandlerThread` 에게 맡긴다.
  - 백업: ServerApp00.java

### 1단계 - 클라이언트의 요청 처리 부분을 `Runnable` 구현체로 분리하여 처리한다.

- `com.eomcs.pms.ClientHandler` 클래스 추가
  - `Runnable` 인터페이스를 구현한다.
  - 클라이언트의 요청 처리를 담당한다.
  - `ServerApp` 에서 해당 코드를 가져온다.
- `com.eomcs.pms.ServerApp` 클래스 변경
  - 클라이언트 요청 처리를 `Thread` 를 통해 `ClientHandler` 에게 맡긴다.
  - 백업: ServerApp01.java

### 2단계 - `ClientHandler` 를 `ServerApp` 의 중첩 클래스로 만든다.

- `com.eomcs.pms.ServerApp` 클래스 변경
  - `ClientHandler` 클래스를 중첩 클래스(static nested class)로 만든다.
  - 백업: ServerApp02.java

### 3단계 - `ClientHandler` 를 `ServerApp.main()` 의 익명 클래스로 만든다.

- `com.eomcs.pms.ServerApp` 클래스 변경
  - `ClientHandler` 스태틱 중첩 클래스를 main()의 익명 클래스로 만든다.
  - 백업: ServerApp03.java

### 4단계 - `ClientHandler` 를 `ServerApp.main()` 의 익명 클래스로 만든다. II

- `com.eomcs.pms.ServerApp` 클래스 변경
  - 익명 클래스의 코드를 밖깥 클래스의 멤버로 만들어 놓고 사용한다.
  - 왜? 코드를 읽기 쉽도록 하기 위함이다.
  - 코드가 여러 블록에 중접되면 될 수록 들여쓰기 하면서
    코드를 읽기가 불편해진다.
  - 백업: ServerApp04.java

### 5단계 - 익명 클래스를 람다(lambda) 문법으로 정의한다.

- `com.eomcs.pms.ServerApp` 클래스 변경
  - 익명 클래스가 메서드 한 개짜리 인터페이스를 구현하고,
    그 코드도 간단하다면,
    람다 문법으로 표현하는 것이 편하다.

## 실습 결과
- src/main/java/com/eomcs/pms/ClientHandlerThread.java 추가
- src/main/java/com/eomcs/pms/ClientHandler.java 추가
- src/main/java/com/eomcs/pms/ServerApp.java 변경
