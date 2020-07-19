# 40_2 - Connection을 스레드에 보관하기: 한 스레드에서 Connection 을 여러 번 사용할 때 발생하는 문제점 해결하기

## 학습목표

- Connection을 close() 하면 DBMS와의 연결이 끊어짐을 이해한다.
- Proxy 설계 기법(design pattern)을 이용하여 Connection의 close()를 커스터마이징 한다.
- Proxy 디자인 패턴의 구원 원리와 목적을 이해한다.

### 변경하기 전의 문제점 및 테스트

- DAO가 작업을 수행(예: findAll(), insert() 호출되는 것)한 후에는
  try-with-resource 문장에 의해 Connection 객체의 close()가 자동 호출된다.
- 따라서 스레드에 보관된 Connection 객체는 더이상 사용할 수 없다.
- 테스트:
  - "/photoboard/list" 명령을 실행해 보라.
  - LessonDao.findByNo()가 실행된 후에 커넥션이 닫히기 때문에,
    PhotoBoardDao.findAllByLessonNo()를 실행하면 오류가 발생할 것이다.  
  - 이렇게 같은 스레드에서 커넥션을 여러 번 사용할 경우에 오류가 발생한다.

### 해결책

- Connection을 사용하고 난 후에 닫지 않게 한다.
- 방법1: 
  - try-with-resources 블럭 밖에 Connection 변수를 둔다.
  - 그래서 Connection 객체가 자동으로 닫히지 않게 한다.
  - 문제: 
    - 기존의 표준 코딩 방식에 어긋난다.
    - 즉 자원을 사용했으면 닫도록 코딩하는 게 일반적이다.
    - 이렇게 스레드에서 커넥션을 여러 번 사용하는 경우라고 해서 
      특별하게 코딩한다면 유지보수 하기가 어렵다.
- 방법2:
  - Connection 객체를 커스터마이징 한다.
  - close()가 호출될 때 진짜로 닫지는 않는다.
  - 기존 표준 코딩에 어긋나지 않는다.
  - 즉 자원을 사용했으면 닫도록 코딩하는 규칙을 준수하고 있다.
  - 다만 중간에서 '조작질'을 할 뿐이다.

  

## 실습 소스 및 결과

- src/main/java/com/eomcs/sql/ConnectionProxy.java 추가
- src/main/java/com/eomcs/util/ConnectionFactory.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: Connection의 일을 대행할 프록시를 정의하라.

- com.eomcs.sql.ConnectionProxy 추가
  - close()를 구현한다.
    - 호출되면 아무런 일을 하지 않게 한다.
    - 즉 커넥션을 닫지 않는다.
  - realClose() 추가한다.
    - 실제 커넥션을 닫는 일을 한다.
  - 나머지 메서드는 원래 Connection 객체에 위임한다.
    - eclipse / 소스창의 컨텍스트 메뉴 / source /generate delegate methods... 실행 
    
### 훈련2: ConnectionFactory가 ConnectionProxy 객체를 리턴하게 하라.

- com.eomcs.util.ConnectionFactory 변경
  - getConnection() 변경
  - 원래의 Connection 객체 대신에 ConnectionProxy를 리턴한다.
  
### 훈련3: 스레드에서 Connection을 제거하기 전에 서버와의 연결을 끊어라.

- com.eomcs.util.ConnectionFactory 변경
  - removeConnection()이 스레드에서 제거한 Connection을 리턴하게 변경한다.
- com.eomcs.lms.ServerApp 변경
  - ConnectionFactory에서 리턴 받은 Connection 객체에 대해 
    realClose()를 호출한다.
    
  