# 40_3 - Connection을 스레드에 보관하기: 트랜잭션 적용하기

- 여러 개의 데이터 변경(insert/update/delete) 작업을 한 단위(트랜잭션)로 
  다루려면 그 작업을 수행할 때 같은 Connection을 사용해야 한다.
- 클라이언트 요청이 들어오면 스레드가 그 요청 처리를 담당한다.
- 따라서 스레드가 실행되는 동안 수행하는 데이터 변경 작업을 
  같은 트랜잭션으로 묶고 싶다면, 같은 Connection을 사용해야 한다.
- 이런 이유로 스레드에 Connection 객체를 보관하는 것이다.

다르게 표현해 보면,
- 스레드에 Connection을 보관하면,
- 스레드가 실행하는 동안 같은 DAO는 같은 Connection을 사용하게 할 수 있다.
- 같은 Connection을 사용하여 데이터 변경 작업을 수행하면,
- 한 단위의 작업(트랜잭션)으로 묶어 제어할 수 있다.
- 즉 모든 작업이 성공했을 때 테이블에 그 결과를 적용하고,
- 단 한 개의 작업이라도 실패하면 이전에 했던 작업을 모두 취소하는 것이 가능하다.  

## 학습목표

- ConnectionFactory를 통해 얻은 Connection 객체를 가지고 트랜잭션을 다루기

### 메서드 별로 커넥션을 개별화 한 상태에서 트랜잭션을 적용하기 

- 39, 40 단계로 가면서 커넥션을 메서드에서 준비하여 사용하였다.
- 이런 관계로 PhotoBoardAddServlet/PhotoBoardUpdateServlet/PhotoBoardDeleteServlet에 
  있었던 트랜잭션 처리 코드를 제거하였다.
- 이제 다시 현 상태에서 트랜잭션 제어 코드를 추가해 보자.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/servlet/PhotoBoardAddServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardUpdateServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardDeleteServlet.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: PhotoBoardAddServlet에 트랜잭션을 적용하라.

- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - ConnectionFactory를 주입 받는다.
  - ConnectionFactory를 통해 Connection을 얻은 후에 트랜잭션을 제어한다.

### 훈련2: PhotoBoardUpdateServlet에 트랜잭션을 적용하라.

- com.eomcs.lms.servlet.PhotoBoardUpdateServlet 변경
  - ConnectionFactory를 주입 받는다.
  - ConnectionFactory를 통해 Connection을 얻은 후에 트랜잭션을 제어한다.
  
### 훈련3: PhotoBoardDeleteServlet에 트랜잭션을 적용하라.

- com.eomcs.lms.servlet.PhotoBoardDeleteServlet 변경
  - ConnectionFactory를 주입 받는다.
  - ConnectionFactory를 통해 Connection을 얻은 후에 트랜잭션을 제어한다.

### 훈련4: 트랜잭션을 다뤄야 하는 서블릿 객체에 ConnectionFactory를 주입하라.

- com.eomcs.lms.ServerApp 변경
  - PhotoBoardAddServlet, PhotoBoardUpdateServlet, PhotoBoardDeleteServlet 객체에
    ConectionFactory를 주입한다.

### 훈련5: /photoboard/add, /photoboard/update, /photoboard/delete을 테스트 해 보라.