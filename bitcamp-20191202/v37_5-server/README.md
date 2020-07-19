# 37_5 - Application Server 구조로 변경: 서버 종료 명령 처리하기

## 학습목표

- Application Server 아키텍처의 구성과 특징을 이해한다.
- 통신 프로토콜 규칙에 따라 동작하는 서버를 만들 수 있다.
- 멀티 스레드 환경에서 스레드풀의 동작을 제어할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: 클라이언트의 '/server/stop' 요청을 처리하라.

- com.eomcs.lms.ServerApp 변경
  - '/server/stop' 명령을 처리한다.
  