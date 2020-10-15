# 35. 동일한 자원으로 더 많은 클라이언트 요청을 처리하는 방법 : Stateful을 Stateless로 전환하기

이번 훈련에서는,
- **자바 네트워크 API** 를 사용하여 클라이언트/서버 통신 애플리케이션을 만든다. 

## 훈련 목표
- 네트워크 API를 사용하여 간단한 메시지를 주고 받는 Client 만들기를 연습한다.

## 훈련 내용
- Socket 클래스를 이용하여 서버에 접속한다.
- 소켓의 입출력 스트림을 이용하여 서버와 데이터를 주고 받는다.


## 실습

### 1단계 - 서버에 연결할 때 한 번만 요청/응답하도록 변경한다.

서버에 연결하면 quit 명령을 보낼 때까지 계속 연결되어 있는 
기존의 stateful 통신 방법을,
서버에 연결했을 때 한 번만 요청하고 응답하는 stateless 통신 방법으로 변경한다.

- com.eomcs.pms.ClientApp 변경

#### 작업 파일
- com.eomcs.pms.ClientApp 변경

## 실습 결과
- src/main/java/com/eomcs/pms/ClientApp.java 변경



## 학습목표

- 클라이언트를 서버와 통신 연결할 수 있다.
- 소켓을 통해 데이터를 입출력 할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ClientApp.java 변경

## 실습  

### 훈련 1: 서버와 연결하라.

- ClientApp.java 변경

### 훈련 2: 서버에 요청 메시지를 전송하고 응답 메시지를 수신하라.

- ClientApp.java 변경
