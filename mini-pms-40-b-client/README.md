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

- com.eomcs.pms.filter.CommandFilter 인터페이스 생성
  - 필터 관리자가 호출할 메서드 규칙을 정의한다.
- com.eomcs.pms.filter.FilterChain 인터페이스 생성
  - 필터가 다음 필터를 실행시키기 위해 필터 관리자에게 요청하는 메서드의 규칙을 정의한다.
- com.eomcs.pms.filter.CommandFilterManager 클래스 생성
  - 필터를 관리한다.
  - 또한 실행 순서에 따라 필터를 실행시킨다.

### 3단계 - 필터 관리자를 App 클래스에 적용한다.

- com.eomcs.pms.App 변경
  - 필터 관리자를 준비하고 호출하는 코드를 추가한다.
  - 기존에 커맨드 객체를 실행하는 코드를 주석으로 막는다.
  - 백업: App02.java

### 4단계 - 사용자가 입력한 명령을 처리할 커맨드를 찾아 실행시키는 부분을 필터로 처리한다.

- com.eomcs.pms.filter.DefaultCommandFilter 생성
  - `App` 클래스에서 커맨드를 실행하는 코드를 뜯어 온다.
- com.eomcs.pms.App 변경
  - 필터 관리자에 DefaultCommandFilter를 등록한다.
  - 백업: App03.java

### 5단계 - 로그인 여부를 검사하는 필터를 만들어 등록한다.

- com.eomcs.pms.filter.AuthCommandFilter 생성
  - `DefaultCommandFilter` 클래스에서 로그인 여부를 검사하는 코드를 뜯어 온다.
- com.eomcs.pms.App 변경
  - 필터 관리자에 `AuthCommandFilter`를 등록한다.
  - 백업: App04.java
  -
### 6단계 - 사용자가 입력한 명령어를 로그 파일에 기록하는 필터 만들기

- com.eomcs.pms.filter.LogCommandFilter 생성
  - `App` 클래스에서 파일에 로그를 남기는 코드를 뜯어 온다.
- com.eomcs.pms.App 변경
  - 필터 관리자에 `LogCommandFilter`를 등록한다.

## 실습 결과
- src/main/java/com/eomcs/pms/handler/Request.java 생성
- src/main/java/com/eomcs/pms/filter/FilterChain.java 생성
- src/main/java/com/eomcs/pms/filter/CommandFilter.java 생성
- src/main/java/com/eomcs/pms/filter/CommandFilterManager.java 생성
- src/main/java/com/eomcs/pms/filter/AuthCommandFilter.java 생성
- src/main/java/com/eomcs/pms/filter/LogCommandFilter.java 생성
- src/main/java/com/eomcs/pms/App.java 변경
