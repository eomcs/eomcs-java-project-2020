# 42_2 - SQL 삽입 공격과 자바 시큐어 코딩: PreparedStatement로 전환하기

PreparedStatement를 이용하여 SQL 문과 값을 분리하여 실행하면,
SQL 삽입 공격을 막을 수 있다.


## 학습목표

- SQL 삽입 공격이 무엇인지 안다.
- SQL 삽입 공격을 막기 위한 방법을 안다.
- Statement와 PreparedStatement의 차이점을 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/mariadb/MemberDaoImpl.java 변경

## 실습  

### 훈련1: 기존의 Statement 객체를 PreparedStatement 객체로 모두 바꿔라.

- com.eomcs.lms.dao.mariadb.XxxDaoImpl 변경
  - Statement를 PreparedStatement 로 변경한다.

### 훈련2: SQL 삽입 공격을 통해 유효하지 않은 사용자 정보로 로그인 해 보라.

'ClientApp' 실행 예:
```
명령> /auth/login
이메일?
user3@test.com
암호?
aaa') or (email='user3@test.com' and 'a'='a
사용자의 정보가 유효하지 않습니다.

```






