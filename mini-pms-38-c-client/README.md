# 38-c. 데이터 처리 코드를 별도의 클래스로 분리하기 : DB 커넥션 객체 공유하기

이번 훈련에서는,
- **DB 커넥션 객체 공유** 의 문제점을 이해하고 해결 방안을 알아본다.



## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 각 DAO 메서드에서 DB 커넥션을 생성하지 않고 공유한다.

- com.eomcs.pms.listener.AppInitListener 변경
  - contextInitialized() 가 호출될 때 Connection 객체를 생성하여 context 맵에 담아 놓는다.
  - contextDestroyed() 가 호출될 때 Connection 객체를 닫는다.
- com.eomcs.pms.App 변경
  - context 맵에 보관된 Connection 객체를 꺼낸다.
  - DAO 객체를 생성할 때 주입한다.
- com.eomcs.pms.dao.mariadb.XxxDaoImpl 클래스 변경
  - 생성자에서 파라미터로 DB 커넥션을 주입 받는다.
  - 각 메서드는 이렇게 주입 받은 DB 커넥션을 사용한다.



## 실습 결과
- src/main/java/com/eomcs/pms/listener/AppInitListener.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/TeamDaoImpl.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
