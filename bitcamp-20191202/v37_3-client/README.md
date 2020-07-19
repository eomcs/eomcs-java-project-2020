# 37_3 - Application Server 구조로 변경: 규칙2에 따라 통신하는 클라이언트 만들기

## 학습목표

- Application Server 아키텍처의 구성과 특징을 이해한다.
- 통신 프로토콜 규칙에 따라 동작하는 클라이언트를 만들 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ClientApp.java 변경
- src/main/java/com/eomcs/lms/handler/ 폴더 삭제
- src/main/java/com/eomcs/lms/dao/ 폴더 삭제
- src/main/java/com/eomcs/lms/domain/ 폴더 삭제

## 실습  

### 훈련1: 서버의 추가 데이터 입력 요구에 응답할 수 있도록 통신 규칙을 변경하라. 

규칙2) 사용자 입력을 포함하는 경우
```
[클라이언트]                                        [서버]
서버에 연결 요청        -------------->           연결 승인
명령(CRLF)              -------------->           명령처리
화면 출력               <--------------           응답(CRLF)
사용자 입력 요구        <--------------           !{}!(CRLF)
입력값(CRLF)            -------------->           입력 값 처리
화면 출력               <--------------           응답(CRLF)
사용자 입력 요구        <--------------           !{}!(CRLF)
입력값(CRLF)            -------------->           입력 값 처리
명령어 실행 완료        <--------------           !end!(CRLF)
서버와 연결 끊기
```

### 훈련2: '통신 규칙2'에 따라 서버 요청에 응답하라.

- com.eomcs.lms.ClientApp 변경

