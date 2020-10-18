# 36-b. 스레드풀을 이용하여 스레드를 재사용하기 : 자바에서 제공하는 스레드풀 사용하기

이번 훈련에서는,
- 자바의 **Concurrent 프레임워크** 에서 제공하는 스레드풀을 프로젝트에 적용해 볼 것이다.

**Concurrent 프레임워크** 는,
- 동시성 프로그래밍을 위해 자바에서 제공하는 프레임워크이다.
- 스레드풀이나 비동기 입출력, 간단한 태스크 프레임워크를 포함하고 있다.


## 훈련 목표
- 자바에서 제공하는 **Concurrent 프레임워크** 의 **스레드풀** 사용법을 연습한다.

## 훈련 내용
- 자바에서 제공하는 **스레드풀** 을 사용하여 클라이언트 요청을 다룬다.

## 실습

### 1단계 - 기존의 `ThreadPool` 대신에 `ExecutorService` 를 사용한다.  

- `com.eomcs.pms.ServerApp` 변경
  - `shutdown()` 이후에 모든 스레드의 종료 여부를 확인한다.
  - 아직 종료 안된 스레드가 있으면 강제 종료시킨다.

## 실습 결과
- src/main/java/com/eomcs/util/concurrent/ThreadPool.java 삭제
- src/main/java/com/eomcs/pms/ServerApp.java 변경
