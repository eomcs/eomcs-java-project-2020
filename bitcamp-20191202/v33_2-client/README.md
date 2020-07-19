# 33_2 - 리팩토링: 요청할 때 마다 프록시와 커맨드를 생성하는 부분을 개선한다.

## 학습목표

- 리팩토링의 목적을 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/proxy/XxxDaoProxy.java 변경
- src/main/java/com/eomcs/lms/ClientApp.java 변경

## 실습  

### 훈련 1: 프록시 클래스 생성 부분을 변경하라.

- com.eomcs.lms.dao.proxy.XxxDaoProxy 변경한다.
  - 요청할 때 서버에 연결한다.
