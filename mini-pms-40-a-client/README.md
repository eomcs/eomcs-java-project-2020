# 40-a. 커맨드 실행 전/후에 기능 추가하기: 디자인 패턴 적용 전

이번 훈련에서는,
- **Chain of Responsibility 디자인 패턴** 을 응용하는 방법을 배울 것이다.

## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 커맨드 실행 전에 사용자 인증을 검사한다.

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
