# 37_5 - Application Server 구조로 변경: 서버 종료 명령 처리하기

## 학습목표

- Application Server 아키텍처의 구성과 특징을 이해한다.
- 통신 프로토콜 규칙에 따라 동작하는 클라이언트를 만들 수 있다.
- 멀티 스레드 환경에서 스레드풀의 동작을 제어할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ClientApp.java 변경

## 실습  

### 훈련1: '/server/stop' 명령을 처리하라.

- com.eomcs.lms.ClientApp 변경
  - 사용자가 '/server/stop'을 입력했을 때 서버가 종료 작업을 즉시 할 수 있도록 두 번 요청한다.

