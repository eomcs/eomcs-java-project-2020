# 35. 동일한 자원으로 더 많은 클라이언트 요청을 처리하는 방법 : Stateful을 Stateless로 전환하기

이번 훈련에서는,
- **자바 네트워크 API** 를 사용하여 클라이언트/서버 통신 애플리케이션을 만든다. 

## 훈련 목표
- Gradle 빌드 도구를 이용하여 자바 애플리케이션 프로젝트를 만드는 것을 연습한다.
- Gradle 빌드 도구를 이용하여 Eclipse IDE 용 프로젝트로 전환하는 것을 연습한다.

## 훈련 내용
- 자바 프로젝트로 사용할 폴더를 만들고 Gradle 빌드 도구로 초기화시킨다.
- Gradle eclipse 플러그인을 사용하여 Eclipse IDE 용 설정 파일을 준비한다.


## 실습

### 1단계 - 클라이언트에게 응답을 완료하면 연결을 끊는다.

- `ServerApp` 변경
  - 응답 완료 후 연결을 끊는 방식으로 변경한다.

#### 작업 파일
- com.eomcs.pms.ServerApp 변경


## 실습 결과
- src/main/java/com/eomcs/pms/ServerApp.java 변경