# 40-b. 커맨드 실행 전/후에 기능 추가하기: Chain of Responsibility 패턴 적용

이번 훈련에서는,
- **Chain of Responsibility 디자인 패턴** 을 응용하는 방법을 배울 것이다.

**Chain of Responsibility 패턴** 은,
- 작업 요청을 받은 객체(sender)가 실제 작업자(receiver)에게 그 책임을 위임하는 구조에서 사용하는 설계 기법이다.
- 작업자 간에 연결 고리를 구축하여 작업을 나누어 처리할 수 있다.
- 체인 방식이기 때문에 작업에 참여하는 모든 객체가 서로 알 필요가 없다.
- 오직 자신과 연결된 다음 작업자만 알면 되기 때문에 객체 간에 결합도를 낮추는 효과가 있다.
- 런타임에서 연결 고리를 변경하거나 추가할 수 있어, 상황에 따라 실시간으로 기능을 추가하거나 삭제할 수 있다.
- 보통 필터링을 구현할 때 이 설계 기법을 많이 사용한다.

## 훈련 목표
- **Chain of Responsibility 패턴** 을 구현하는 것을 연습한다.
- **Chain of Responsibility 패턴** 을 적용한 후의 이점을 이해한다.

## 훈련 내용
- **Chain of Responsibility 패턴** 을 프로젝트에 적용한다.
- **Chain of Responsibility 패턴** 을 적용한 후에 새 기능을 추가하는 것을 체험한다.

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
