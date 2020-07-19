# 39_2 - Connection 개별화하기: 커넥션 객체 생성에 Factory Method 패턴 적용하기


## 학습목표

- Factory Method 디자인 패턴의 원리와 사용 목적을 이해한다.
- Factory Method 설계 기법에 따라 구현할 수 있다.

### Factory Method 디자인 패턴

- 객체 생성 과정이 복잡할 경우에 사용하는 설계 기법이다.
- new 연산자를 이용하여 직접 객체를 생성하는 대신에 메서드를 통해 리턴 받는다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/util/ConnectionFactory.java 추가
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/XxxDaoImpl.java 변경

## 실습  

### 훈련1: 커넥션을 생성할 때 팩토리 메서드를 사용하라.

Connection 객체는 DriverManager를 통해 생성하지만, 
생성 방법이 바뀔 수 있다.
문제는 Connection 객체 생성 방법이 바뀌면, 
DAO 구현체를 모두 변경해야 한다.
이런 문제를 해결하기 위해 커넥션 객체 생성을 별도의 클래스에 맡긴다.
그리고 메서드를 통해 커넥션 객체를 얻는다.

- com.eomcs.util.ConnectionFactory 추가
  - Connection 객체를 생성하는 메서드를 추가한다.
- com.eomcs.lms.DataLoaderListener 변경
  - ConnectionFactory 객체를 준비한다.
  - DAO 구현체에 ConnectionFactory 객체를 주입한다.
- com.eomcs.lms.dao.mariadb.XxxDaoImpl 변경
  - 생성자에서 ConnectionFactory 객체를 받는다.
  - 직접 Connection 객체를 생성하는 대신에 
  ConnectionFactory 객체를 통해 Connection 얻는다.


### 메서드 마다 커넥션을 구분하는 방식의 문제점

- 메서드 마다 별도의 커넥션을 사용한다.
- 따라서 PhotoBoardDao의 insert()와 PhotoFileDao의 insert()를 
  한 단위 작업으로 묶을 수 없다.
- 즉 사진 게시글 입력과 첨부 파일 입력을 한 단위의 작업으로 다룰 수 없다.
- 트랜잭션을 구현할 수 없다. 
  