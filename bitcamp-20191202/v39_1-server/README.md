# 39_1 - Connection 개별화하기: 메서드 호출마다 DBMS와 연결하기


## 학습목표

- Connection을 공유할 때 발생하는 문제점을 안다.
- 왜 그런 문제가 발생하는지 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/mariadb/XxxDaoImpl.java 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경

## 실습  

### 훈련1: 각 메서드를 호출할 때 DBMS와 연결하라.

- com.eomcs.lms.dao.mariadb.XxxDaoImpl 변경
  - 생성자에서 파라미터로 Connection 객체를 받는 대신에 DB 연결 정보를 받는다. 
  - 각 메서드에서 JDBC URL과 username, password를 사용하여 DBMS에 연결한다.
- com.eomcs.lms.DataLoaderListener 변경
  - Connection 객체를 생성하지 않는다.
  - 대신 DBMS 연결 정보를 준비하여 DAO 구현체를 생성할 때 넘겨준다.


  