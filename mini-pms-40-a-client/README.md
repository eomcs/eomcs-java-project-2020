# 40-a. 커맨드 실행 전/후에 기능 추가하기: 디자인 패턴 적용 전

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
- **Chain of Responsibility 패턴** 을 적용하기 전의 구조에서 새 기능을 추가하는 것을 체험한다.
- 새 기능을 추가할 때 마다 기존 코드를 손대는 방식의 문제점을 이해한다.

## 훈련 내용
- **Chain of Responsibility 패턴** 을 적용하기 전에 새 기능을 추가하는 것을 체험한다.
- 로그인 사용자만 커맨드를 실행하도록 기능을 변경한다.
- 사용자의 명령 요청을 로그 파일에 남긴다.

## 실습

### 1단계 - 로그인을 하지 않은 사용자의 커맨드 실행을 거부한다.

다음과 같이 동작하도록 구현한다.
```
명령> /board/add
로그인이 필요합니다.

명령> /login
[로그인]
이메일? x1@test.com
암호? 2222
사용자 정보가 맞지 않습니다.

명령> /board/add
...
```
- com.eomcs.pms.App 변경
  - 커맨드 객체를 실행하기 전에 로그인 여부를 검사한다.
  - 백업: App01.java

### 2단계 - 커맨드 실행 전에 사용자가 입력한 모든 명령을 로그 파일에 남긴다.

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
