# 51_1 - Spring IoC 컨테이너와 MyBatis 연동하기

## 학습목표

- Spring IoC 컨테이너와 Mybatis 프레임워크를 연동할 수 있다.

## 실습 소스 및 결과

- build.gradle 변경
- src/main/java/com/eomcs/sql/PlatformTransactionManager.java 삭제
- src/main/java/com/eomcs/sql/TransactionTemplate.java 삭제
- src/main/java/com/eomcs/sql/TransactionCallback.java 삭제
- src/main/java/com/eomcs/sql/SqlSessionFactoryProxy.java 삭제
- src/main/java/com/eomcs/sql/SqlSessionProxy.java 삭제
- src/main/java/com/eomcs/sql/MybatisDaoFactory.java 삭제
- src/main/resources/com/eomcs/lms/conf/mybatis-config.xml 삭제
- src/main/java/com/eomcs/lms/service/impl/PhotoBoardServiceImpl.java 변경
- src/main/java/com/eomcs/lms/AppConfig.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: Mybatis를 Spring IoC 컨테이너와 연결할 때 사용할 의존 라이브러리를 가져온다.

- Spring IoC 컨테이너의 라이브러리 정보 찾기
  - `mvnrepository.com` 또는 `search.maven.org`에서 라이브러리를 찾는다.
  - `mybatis-spring`, `spring-jdbc` 라이브러리를 검색한다. 
    라이브러리를 검색한다.
- `build.gradle` 변경
- Gradle 빌드 도구를 사용하여 라이브러리 파일을 프로젝트로 가져오기

### 훈련2: Mybatis에서 관리했던 DB 커넥션풀(DataSource)을 Spring IoC 컨테이너로 옮긴다.

Spring IoC 컨테이너를 도입하면, 
가능한 대부분의 객체를 IoC 컨테이너에서 관리하도록 한다.
따라서 Mybatis가 사용하는 객체도 Spring IoC 컨테이너에서 관리하도록 
관리 주체를 변경한다.

- com.eomcs.lms.AppConfig 변경
  - DataSource 객체를 리턴하는 팩토리 메서드를 추가한다.
  
### 훈련3: PlatformTransactionManager를 Spring 것으로 교체한다.

- com.eomcs.sql.PlatformTransactionManager 삭제
- com.eomcs.sql.TransactionTemplate 삭제
- com.eomcs.sql.TransactionCallback 삭제
- com.eomcs.lms.AppConfig 변경
  - 기존의 PlatformTransactionManager를 생성하는 메서드를 변경한다.
  - transactionManager()를 변경한다.  

### 훈련4: 기존의 서비스 객체의 트랜잭션 관리자를 Spring의 것으로 교체한다.

- com.eomcs.lms.service.impl.PhotoBoardServiceImpl 변경
  - 트랜잭션 관련 클래스를 Spring의 것으로 교체한다.
  - 트랜잭션을 다루는 코드를 Spring 사용법에 따라 변경한다.
  
### 훈련5: Mybatis의 SqlSessionFactory를 Spring IoC 컨테이너 용으로 준비한다.

- com.eomcs.lms.AppConfig 변경
  - `mybatis-spring` 라이브러리에서 제공하는 어댑터를 사용하여 SqlSessionFactory를 교체한다. 
  - sqlSessionFactory()를 변경한다.
- com.eomcs.lms.conf.mybatis-config.xml 삭제
- com.eomcs.sql.SqlSessionFactoryProxy 삭제
- com.eomcs.sql.SqlSessionProxy 삭제

### 훈련6: DAO 생성기를 Mybatis-Spring 어댑터로 교체한다.

- com.eomcs.lms.AppConfig 변경
  - @MapperScan 애노테이션으로 설정한다.
  - 수동으로 DAO 객체를 만드는 팩토리 메서드를 모두 제거한다.
  - MybatisDaoFactory 객체를 생성하는 팩토리 메서드를 삭제한다.
- com.eomcs.sql.MybatisDaoFactory 삭제
- com.eomcs.lms.ServerApp 변경
  - SqlSessionFactory 사용 코드를 제거한다.
  - 트랜잭션은 Spring 프레임워크에서 관리하기 때문에 
    우리가 직접 스레드를 통제하면 관리할 필요가 없다.