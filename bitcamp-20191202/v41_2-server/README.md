# 41_2 - 트랜잭션 관리자를 사용하는 코드를 캡슐화하기

## 학습목표

- 트랜잭션 관리자를 사용하는 코드를 캡슐화 할 수 있다.
- 반복되는 코드를 캡슐화하여 코드를 단순화시킬 수 있다.
- 인터페이스의 사용 목적과 활용법을 이해한다.
- 익명 클래스를 정의할 수 있다.
- 람다 문법을 활용할 수 있다.
- bitcamp-java-project-client의 v37_1 버전에도 유사한 방법이 있다.
  - com.eomcs.lms.dao.proxy.DaoProxyHelper : 템플릿 역할을 수행 
  - com.eomcs.lms.dao.proxy.Worker : 템플릿에 삽입될 작업

## 실습 소스 및 결과

- src/main/java/com/eomcs/sql/TransactionCallback.java 추가
- src/main/java/com/eomcs/sql/TransactionTemplate.java 추가
- src/main/java/com/eomcs/lms/servlet/PhotoBoardAddServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardUpdateServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardDeleteServlet.java 변경

## 실습  

### 훈련1: 트랜잭션 관리자를 사용하는 코드를 캡슐화하여 별도의 클래스로 분리하라.

- com.eomcs.sql.TransactionTemplate 추가
  - 트랜잭션 관리자를 사용하는 코드를 메서드로 정의한다.
- com.eomcs.sql.TransactionCallback 인터페이스 추가
  - TransactionTemplate이 작업을 실행할 때 호출할 메서드 규칙을 정의한다.
  - 트랜잭션 작업은 이 규칙에 따라 작성해야 한다.
  
### 훈련2: 트랜잭션을 사용할 곳에 TransactionTemplate을 적용하라.

- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - PlatformTransactionManager를 직접 사용하는 대신에 TransactionTemplate을 사용한다.
- com.eomcs.lms.servlet.PhotoBoardUpdateServlet 변경
  - PlatformTransactionManager를 직접 사용하는 대신에 TransactionTemplate을 사용한다.
- com.eomcs.lms.servlet.PhotoBoardDeleteServlet 변경
  - PlatformTransactionManager를 직접 사용하는 대신에 TransactionTemplate을 사용한다.