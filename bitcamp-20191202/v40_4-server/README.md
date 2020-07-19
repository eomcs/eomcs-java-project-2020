# 40_4 - Connection을 스레드에 보관하기: 트랜잭션 관리자 도입하기

## 학습목표

- Connection을 제어하여 트랜잭션을 관리하는 코드를 캡슐화할 수 있다.
- 트랜잭션 관리의 필요성을 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/sql/PlatformTransactionManager.java 추가
- src/main/java/com/eomcs/lms/servlet/PhotoBoardAddServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardUpdateServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardDeleteServlet.java 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: 트랜잭션 제어 코드를 캡슐화 하라.

- com.eomcs.sql.PlatformTransactionManager 추가
  - 트랜잭션을 시작시키고, 커밋/롤백하는 메서드를 정의한다.
  
  
### 훈련2: PhotoBoardAddServlet에 트랜잭션 관리자를 적용하라.

- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - PlatformTransactionManager를 주입 받는다.
  - 트랜잭션 관리자를 통해 트랜잭션을 제어한다.
  
### 훈련3: PhotoBoardUpdateServlet에 트랜잭션 관리자를 적용하라.

- com.eomcs.lms.servlet.PhotoBoardUpdateServlet 변경
  - PlatformTransactionManager를 주입 받는다.
  - 트랜잭션 관리자를 통해 트랜잭션을 제어한다.
  
### 훈련4: PhotoBoardDeleteServlet에 트랜잭션 관리자를 적용하라.

- com.eomcs.lms.servlet.PhotoBoardDeleteServlet 변경
  - PlatformTransactionManager를 주입 받는다.
  - 트랜잭션 관리자를 통해 트랜잭션을 제어한다.

### 훈련5: DataLoaderListener에서 트랜잭션 관리자를 준비하라.

- com.eomcs.lms.DataLoaderListener 변경
  - PlatformTransactionManager 객체를 준비한다.

### 훈련6: 트랜잭션 관리자를 서블릿에 주입하라.

- com.eomcs.lms.ServerApp 변경
  - 맵에서 PlatformTransactionManager 객체를 꺼내 서블릿 객체에 주입한다.
  
### 훈련7: /photoboard/add, /photoboard/update, /photoboard/delete을 테스트 해 보라.