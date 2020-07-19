# 32_11 - 서버에서 제공한 프록시 객체를 사용하여 데이터를 처리하기

## 학습목표

- 프록시 패턴의 이점을 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/BoardDao.java 추가
- src/main/java/com/eomcs/lms/dao/proxy/XxxDaoProxy.java 추가
- src/main/java/com/eomcs/lms/handler/XxxCommand.java 변경
- src/main/java/com/eomcs/lms/ClientApp.java 변경

## 실습  

### 훈련 1: 서버 프로젝트(32_11)에서 DAO 프록시 클래스를 가져오라.

- com.eomcs.lms.dao.XxxDao 인터페이스를 가져온다.
- com.eomcs.lms.dao.proxy 패키지와 그 하위 클래스를 모두 가져온다.

### 훈련 2: BoardXxxCommand 객체가 BoardDaoProxy 객체를 사용하여 데이터를 처리하게 하라.

- com.eomcs.lms.handler.BoardXxxCommand 클래스를 변경한다.
  - 입출력 스트림 필드를 제거한다.
  - 생성자에서 프록시 객체를 받는다.
  - 프록시 객체를 사용하여 데이터를 처리한다.
- com.eomcs.lms.ClientApp 변경한다.
  - BoardDaoProxy 객체를 생성한다.
  - BoardXxxCommand 객체에 주입한다.

### 훈련 3: LessonXxxCommand 객체가 LessonDaoProxy 객체를 사용하여 데이터를 처리하게 하라.

- com.eomcs.lms.handler.LessonXxxCommand 클래스를 변경한다.
  - 입출력 스트림 필드를 제거한다.
  - 생성자에서 프록시 객체를 받는다.
  - 프록시 객체를 사용하여 데이터를 처리한다.
- com.eomcs.lms.ClientApp 변경한다.
  - LessonDaoProxy 객체를 생성한다.
  - LessonXxxCommand 객체에 주입한다.
  
### 훈련 4: MemberXxxCommand 객체가 MemberDaoProxy 객체를 사용하여 데이터를 처리하게 하라.

- com.eomcs.lms.handler.MemberXxxCommand 클래스를 변경한다.
  - 입출력 스트림 필드를 제거한다.
  - 생성자에서 프록시 객체를 받는다.
  - 프록시 객체를 사용하여 데이터를 처리한다.
- com.eomcs.lms.ClientApp 변경한다.
  - MemberDaoProxy 객체를 생성한다.
  - MemberXxxCommand 객체에 주입한다.


