# 38-c. 데이터 처리 코드를 별도의 클래스로 분리하기 : 의존 객체 주입과 DB 커넥션 객체 공유하기

이번 훈련에서는,
- **의존 객체 주입** 을 통해 **DB 커넥션 객체를 공유** 하는 방법을 연습해 볼 것이다.

**의존 객체 주입(Dependency Injection: DI)** 은,
- 역할을 수행하는데 필요한 객체를 외부에서 주입해 주는 방식이다.
- 외부에서 객체를 관리하기 때문에 객체 교체가 용이하다.
- 목업(mock up) 객체를 의존 객체로 주입할 수 있어 단위 테스트 하기가 쉽다.

## 훈련 목표
- **의존 객체 주입** 의 이점을 이해한다.
- **DB 커넥션 객체** 를 공유할 때 이점을 이해한다.

## 훈련 내용
- `AppInitListener` 에서 DB 커넥션을 준비한다.
- `App` 에서 DAO 객체를 생성할 때 `AppInitListener` 가 준비한 DB 커넥션을 주입한다.
- DAO 각 메서드는 DAO 생성자를 통해 주입 받은 DB 커넥션을 사용하여 SQL을 처리한다. 

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
