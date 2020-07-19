# 40_1 - Connection을 스레드에 보관하기: ThreadLocal을 사용하여 Connection 보관하기

## 학습목표

- ThreadLocal의 동작 원리를 이해한다.
- ThreadLocal을 사용하여 각 스레드에 값을 저장할 수 있다.

### Connection을 Thread에 보관하는 이유?

- 여러 개의 데이터 변경(insert/update/delete) 작업을 한 단위로 묶으려면 
  같은 Connection을 사용해야 한다.
- 왜? commit()/rollback()은 커넥션 객체에 대해 실행하기 때문이다.
- 즉 트랜잭션은 각 커넥션 별로 관리된다. 
- 그래서 스레드가 실행하는 데이터 변경 작업을 한 단위로 묶으려면 
  그 스레드가 수행하는 데이터 변경 작업은 같은 커넥션으로 실행해야 한다.
- DAO의 메서드가 실행될 때 사용하는 커넥션은 스레드에서 꺼낸다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/util/ConnectionFactory.java 추가
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: 커넥션 팩토리에서 생성한 Connection 객체를 스레드에 보관하라.

- com.eomcs.util.ConnectionFactory 변경
  - getConnection() 변경
    - 스레드에 보관된 Connection 객체가 없다면, 새로 생성하여 리턴한다.
    - 새로 생성한 Connection 객체는 스레드에 보관한다.
    - 스레드에 보관된 Connection 객체가 있다면 그 객체를 꺼내 리턴한다.
    
#### 문제점

- 현재 스레드풀(ExecutorService)을 이용하여 스레드를 관리하고 있다.
- 스레드를 사용한 후(클라이언트 요청에 응답을 완료한 후)에 
  스레드를 버리지 않고 풀에 보관했다가
  다음 클라이언트 요청에 재사용한다.
- DAO가 사용하는 Connection 객체는 스레드에 보관한다.
- DAO의 메서드(예: findAll(), insert() 등)에서 Connection을 사용한 후에 
  닫는다.(이게 문제이다!!!!)
- 따라서 스레드에 보관한 Connection은 DAO 작업이 끝난 후 닫힌 상태가 된다.
- 그래서 다음 클라이언트 요청을 처리하기 위해 스레드를 재사용할 때
  그 스레드에 있는 Connection은 닫힌 Connection이기 때문에 
  DAO가 작업할 때 오류가 발생한다. 

#### 해결책

- 클라이언트에게 응답을 완료한 후에 스레드에 보관된 Connection 객체를 제거한다.
- 다음에 클라이언트 요청을 처리하기 위해 같은 스레드가 사용되더라도 
  이미 그 스레드에는 Connection 객체가 없기 때문에 
  ConnectionFactory는 새 Connection을 만들어 리턴할 것이다.
  
### 훈련2: 클라이언트에 응답을 한 후 스레드에 보관된 Connection 객체를 제거하라.

- com.eomcs.util.ConnectionFactory 변경
  - Thread에 보관된 Connection 객체를 제거하는 메서드를 추가한다.
  - removeConnection()
- com.eomcs.lms.DataLoaderListener 변경
  - ServerApp에서 ConnectionFactory를 사용할 수 있도록 맵에 보관하여 리턴한다.
- com.eomcs.lms.ServerApp 변경
  - 클라이언트 요청을 처리한 후에 
    ConnectionFactory를 통해 Thread에서 Connection을 제거한다.