# 43_2 - MyBatis + 트랜잭션 적용

## 학습목표

- Mybatis로 트랜잭션을 제어할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/sql/SqlSessionProxy.java 추가
- src/main/java/com/eomcs/sql/SqlSessionFactoryProxy.java 추가
- src/main/java/com/eomcs/sql/PlatformTransactionManager.java 변경
- src/main/java/com/eomcs/sql/DataSource.java 삭제
- src/main/java/com/eomcs/sql/ConnectionProxy.java 삭제
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/LessonDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/PhotoBoardDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/PhotoFileDaoImpl.java 변경

## 실습  

### 훈련1: SqlSession 프록시를 만든다.

- com.eomcs.sql.SqlSessionProxy 클래스 추가
  - close()를 호출할 때 닫지 않도록 오버라이딩 한다.
  
### 훈련2: SqlSessionFactory 프록시를 만든다.

- com.eomcs.sql.SqlSessionFactoryProxy 클래스 추가
  - 생성한 SqlSession 객체를 스레드에 보관하기 위해 ThreadLocal 필드를 추가한다.
  - openSession(boolean) 메서드 변경한다.

### 훈련3: PlatformTransactionManager를 변경한다.

- com.eomcs.sql.PlatformTransactionManager 변경
  - Connection 대신 SqlSession을 다루도록 변경한다. 
- com.eomcs.sql.DataLoaderListener 변경
  - PlatformTransactionManager를 준비할 때 DataSource 대신 SqlSessionFactory를 주입한다.
- com.eomcs.sql.DataSource 삭제
- com.eomcs.sql.ConnectionProxy 삭제

### 훈련4: 스레드 작업을 종료했을 때 SqlSession을 진짜로 닫는다.

- com.eomcs.lms.ServerApp 변경

### 훈련5: DAO에서 commit()/rollback() 제거한다.

DAO의 작업이 단독적으로 실행될 때도 있지만,
여러 DAO가 협업으로 동작하는 경우도 있다.
이런 경우 묶인 작업 중에 한 작업이라도 오류가 발생하면,
기존의 데이터 변경 작업을 취소해야 한다.
그럴려면 일단 DAO에서 commit()이나 rollback()을 수행해서는 안된다.
즉 트랜잭션 제어는 PlatformTransactionManager를 통해서 수행해야 한다.

- com.eomcs.lms.dao.mariadb.*DaoImpl 변경
  - commit()/rollback() 호출 코드 제거한다.
