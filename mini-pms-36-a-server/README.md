# 36-a. 스레드풀을 이용하여 스레드를 재사용하기 : 스레드풀 구현하기

이번 훈련에서는,
- **스레드풀** 구현을 통해 구동 원리와 목적을 이해한다.

**스레드풀** 은,
- 스레드를 풀링 기법(pooling)을 이용하여 관리한다.
- 스레드를 사용한 후에 버리지 않고 재사용하여 가비지 생성을 줄인다.

**풀링 기법(pooling)** 은,
- 사용한 객체를 버리지 않고 보관해 두었다가 재사용하는 객체 관리 기법이다.
- 동일한 객체를 자주 생성하고,
- 생성한 객체를 쓰고 버리는 상황에서 대량의 가비지가 생성되는 경우에 적합한다.
- GoF의 '디자인 패턴'에서 Flyweight 패턴의 한 예이다.

## 훈련 목표
- **스레드풀** 을 직접 구현해 보면서 그 구동 원리와 이점을 이해한다.

## 훈련 내용
- 스레드풀을 정의한다.
- 스레드풀을 사용하여 클라이언트 응답을 처리한다.

## 실습

### 1단계 - 스레드풀을 정의한다.

- `com.eomcs.util.concurrent.ThreadPool` 추가
  - 백업: ThreadPool01.java

### 2단계 - 클라이언트가 접속하면 요청 처리를 ThreadPool에게 맡긴다.

- `com.eomcs.pms.ServerApp` 변경
  - 백업: ServerApp01.java
  - `stop` 명령을 수행했을 때 서버가 완전히 종료하지 못한다.
  - 왜냐하면 스레드풀에 실행 중인 스레드가 있기 때문이다. 

### 3단계 - 스레드풀 종료 기능을 추가한다.

- `com.eomcs.util.concurrent.ThreadPool` 변경
  - 스레드풀이 관리하는 스레드들을 종료하는 `shutdown()` 메서드를 추가한다.
- `com.eomcs.pms.ServerApp` 변경
  - `stop` 명령이 들어오면 `ThreadPool.shutdown()` 을 통해 스레드를 종료시킨다.

## 실습 결과
- src/main/java/com/eomcs/util/concurrent/ThreadPool.java 추가
- src/main/java/com/eomcs/pms/ServerApp.java 변경
