# 32_4 - 서버에 데이터를 요청하는 기능을 추가하기

## 학습목표

- 서버에 요청하고 응답 결과를 받을 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ClientApp.java 변경

## 실습  

### 훈련 1: 31번 프로젝트에서 Board 클래스를 가져오라.

- com.eomcs.lms.domain 패키지 생성한다.
- Board.java 가져온다.

### 훈련 2: 31번 프로젝트에서 게시물 관리를 처리하는 Command 객체 가져오라.

- com.eomcs.lms.handler 패키지 생성한다.
- BoardXxxCommand.java 클래스 가져온다. 

### 훈련 3: Command 객체가 서버와 통신할 수 있도록 입출력 도구를 제공하라.

- ClientApp.java 변경
  - 서버와 연결하는 코드를 적용한다.
  - 서버와 통신할 수 있는 입출력 도구를 BoardXxxCommand 객체에 제공한다.
  
### 훈련 4: BoardListCommand 가 작업할 때 서버와 통신하도록 처리하라.

- BoardListCommand.java 변경
  - 서버의 입출력 도구를 받을 수 있도록 생성자를 변경한다.
  - 데이터를 읽고 쓸 때 서버에 요청하도록 execute()를 변경한다.

### 훈련 5: BoardAddCommand 가 작업할 때 서버와 통신하도록 처리하라.

- BoardAddCommand.java 변경
  - 서버의 입출력 도구를 받을 수 있도록 생성자를 변경한다.
  - 데이터를 읽고 쓸 때 서버에 요청하도록 execute()를 변경한다.

### 훈련 6: BoardDetailCommand 가 작업할 때 서버와 통신하도록 처리하라.

- BoardDetailCommand.java 변경
  - 서버의 입출력 도구를 받을 수 있도록 생성자를 변경한다.
  - 데이터를 읽고 쓸 때 서버에 요청하도록 execute()를 변경한다.
  
### 훈련 7: BoardUpdateCommand 가 작업할 때 서버와 통신하도록 처리하라.

- BoardUpdateCommand.java 변경
  - 서버의 입출력 도구를 받을 수 있도록 생성자를 변경한다.
  - 데이터를 읽고 쓸 때 서버에 요청하도록 execute()를 변경한다.
  
### 훈련 8: BoardDeleteCommand 가 작업할 때 서버와 통신하도록 처리하라.

- BoardDeleteCommand.java 변경
  - 서버의 입출력 도구를 받을 수 있도록 생성자를 변경한다.
  - 데이터를 읽고 쓸 때 서버에 요청하도록 execute()를 변경한다.

### 훈련 9: LessonXxxCommand 가 작업할 때 서버와 통신하도록 처리하라.

- LessonXxxCommand.java 변경
  - 서버의 입출력 도구를 받을 수 있도록 생성자를 변경한다.
  - 데이터를 읽고 쓸 때 서버에 요청하도록 execute()를 변경한다.
  
### 훈련 10: MemberXxxCommand 가 작업할 때 서버와 통신하도록 처리하라.

- MemberXxxCommand.java 변경
  - 서버의 입출력 도구를 받을 수 있도록 생성자를 변경한다.
  - 데이터를 읽고 쓸 때 서버에 요청하도록 execute()를 변경한다.