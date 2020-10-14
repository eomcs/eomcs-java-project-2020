# 34-c. 네트워크 API를 활용하여 C/S 구조로 전환하기 : 사용자가 입력한 명령처리

이번 훈련에서는,
- **자바 네트워크 API** 를 사용하여 클라이언트/서버 통신 애플리케이션을 만든다. 

## 훈련 목표
- 네트워크 API를 사용하여 간단한 메시지를 주고 받는 Client 만들기를 연습한다.

## 훈련 내용
- Socket 클래스를 이용하여 서버에 접속한다.
- 소켓의 입출력 스트림을 이용하여 서버와 데이터를 주고 받는다.


## 실습

### 1단계 - 사용자가 입력한 명령을 서버에 전송한다.

- com.eomcs.util.Prompt 가져오기
- com.eomcs.pms.ClientApp 변경

#### 작업 파일
- com.eomcs.util.Prompt 이전 프로젝트(v33-b)에서 가져온다. 
- com.eomcs.pms.ClientApp 변경
  - 백업: ClientApp01.java


### 2단계 - 사용자가 quit 명령을 입력할 때까지 반복한다.

- com.eomcs.pms.ClientApp 변경
  
#### 작업 파일
- com.eomcs.pms.ClientApp 변경


## 실습 결과
- src/main/java/com/eomcs/util/Prompt.java 추가
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
