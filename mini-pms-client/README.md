# 40-b. 커맨드 실행 전/후에 기능 추가하기: Chain of Responsibility 패턴 적용

이번 훈련에서는,
- **Chain of Responsibility 디자인 패턴** 을 응용하는 방법을 배울 것이다.

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 필터에게 제공할 정보를 다루는 Request 객체를 정의한다.

- com.eomcs.pms.handler.Request 생성
  - 사용자가 요청한 명령어를 보관한다.
  - App 객체가 값을 공유하기 위해 만든 context 맵 객체를 보관한다.
- com.eomcs.pms.App 변경
  - 사용자 입력이 들어오면 Request 객체를 준비한다.
  - 백업: App01.java

### 2단계 - 커맨드 실행 전/후에 삽입되는 필터의 호출 규칙을 정의한다.

- com.eomcs.pms.filter.CommandFilterManager 클래스 생성
  - 필터를 관리한다.
  - 또한 실행 순서에 따라 필터를 실행시킨다.
- com.eomcs.pms.filter.FilterChain 인터페이스 생성
  - 필터가 다음 필터를 실행시키기 위해 필터 관리자에게 요청하는 메서드의 규칙을 정의한다.
- com.eomcs.pms.filter.CommandFilter 인터페이스 생성
  - 필터 관리자가 호출할 메서드 규칙을 정의한다.

### 3단계 - 필터 관리자를 App 클래스에 적용한다.

- com.eomcs.pms.App 변경
  - 필터 관리자를 준비하고 호출하는 코드를 추가한다.

### 3단계 - 사용자가 입력한 명령어를 로그 파일에 기록하는 필터 만들기

기존에 작성한 코드를 필터로 옮긴다.

- com.eomcs.pms.App 변경
  - 커맨드 객체를 실행하기 전에 로그인 여부를 검사한다.
  - 백업: App01.java

### 2단계 - 커맨드 실행 전에 사용자가 입력한 모든 명령을 파일로 로그를 남긴다.

다음과 같이 동작하도록 구현한다.
```
명령> /login
...
명령> /board/list
...
명령> /board/add
...
명령> hul...
...
명령> 이게뭐야
...
```

- /command.log 파일 생성
  - 파일 출력 예:
```
/login
/board/list
/board/add
hul...
이게뭐야
```



## 실습 결과
- src/main/java/com/eomcs/pms/App.java 변경
