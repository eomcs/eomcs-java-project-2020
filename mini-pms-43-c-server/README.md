# 43-c. 애플리케이션 서버 아키텍처로 전환하기 : 커맨드 객체 생성 자동화

이번 훈련에서는,
-

## 훈련 목표
-

## 훈련 내용
-

## 실습


### 1단계 - 리플랙션 API 를 사용하여 커맨드 객체를 자동으로 찾아 인스턴스를 생성한다.

- com.eomcs.pms.handler.CommandAnno 애노테이션 생성
- com.eomcs.pms.handler.XxxCommand 클래스 변경
  - 클래서 선언부에 @CommandAnno(명령) 애노테이션을 붙인다.
- com.eomcs.pms.listener.RequestMappingListener 클래스 변경
  - 커맨드 클래스가 있는 패키지를 뒤져서 클래스를 찾아 인스턴스를 생성한다.



## 실습 결과

- src/main/java/com/eomcs/pms/ServerApp.java 변경
