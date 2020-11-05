# 39. 로그인/로그아웃 구현하기

이번 훈련에서는,
- **로그인/로그아웃** 을 다루는 방법을 연습한다.



## 훈련 목표
- **트랜잭션** 이 필요한 상황과 이유를 이해한다.
- `commit()` 과 `rollback()`을 사용하여 트랜잭션을 다루는 방법을 연습한다.

## 훈련 내용
- **트랜잭션** 적용하지 않았을 때의 문제점을 확인한다.

## 실습

### 1단계 - 로그인을 처리하는 `LoginCommand` 클래스를 작성한다.

다음과 같이 동작하도록 로그인을 구현한다.
```
> /login
[로그인]
이메일? x1@test.com
암호? 2222
사용자 정보가 맞지 않습니다.

> /login
[로그인]
이메일? x1@test.com
암호? 2222
x1 님 환영합니다.
```
- com.eomcs.pms.handler.LoginCommand 생성
  - 사용자 이메일과 암호를 받아 인증을 수행한다.
- com.eomcs.pms.dao.MemberDao 변경
  - `findByEmailPassword()` 메서드를 추가한다.


## 실습 결과
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
