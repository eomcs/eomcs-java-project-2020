# 42_1 - SQL 삽입 공격과 자바 시큐어 코딩: 사용자 로그인 기능 추가

DB 프로그래밍의 핵심은 JDBC API를 사용하여 SQL문을 실행하는 것이다. 
SQL 문은 보통 사용자가 입력한 값을 가지고 작성하는데, 
여기서 보안 문제가 발생한다. 
SQL을 잘 아는 사용자가 입력 값에 SQL 문법을 포함시켜서 
내부 데이터를 조회한다거나 변경할 수 있다.
이를 방지하기 위해서는 사용자가 입력한 값을 가지고 SQL 문을 만들어서는 안된다.


## 학습목표

- SQL 삽입 공격이 무엇인지 안다.
- SQL 삽입 공격을 막기 위한 방법을 안다.
- Statement와 PreparedStatement의 차이점을 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/MemberDao.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/lms/servlet/LoginServlet.java 추가
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: 사용자 로그인 기능을 만들라.

- lms_member 테이블의 암호 초기화
  - 테스트하기 위해 모든 회원의 암호를 '1111'로 초기화 한다.
  - update lms_member set pwd=password('1111') 실행
- com.eomcs.lms.dao.MemberDao 변경
  - 이메일과 암호를 가지고 사용자를 조회하는 메서드를 추가한다.
  - Member findByEmailAndPassword(String email, String password)
- com.eomcs.lms.dao.mariadb.MemberDaoImpl 변경
  - MemberDao에 추가한 메서드를 구현한다.
  - insert(), update()의 SQL 문에서 암호를 입력하거나 변경할 때 
    password() SQL 함수로 인코딩하도록 SQL 문을 변경한다.
- com.eomcs.lms.servlet.LoginServlet 추가
  - 사용자로부터 이메일과 암호를 입력받아 인증을 수행한다.
- com.eomcs.lms.ServerApp 변경
  - "/auth/login" 명령을 처리할 LoginServlet 객체를 맵에 추가한다.
  
'ClientApp' 실행 예:
```
명령> /auth/login
이메일?
user1@test.com
암호?
1111
'홍길동'님 환영합니다.

명령> /auth/login
이메일?
user1@test.com
암호?
2222
사용자가 정보가 유효하지 않습니다.
```

### 훈련2: SQL 삽입 공격을 통해 유효하지 않은 사용자 정보로 로그인 해 보라.

'ClientApp' 실행 예:
```
명령> /auth/login
이메일?
user3@test.com
암호?
aaa') or (email='user3@test.com' and 'a'='a
'user3'님 환영합니다.

```

분명히 잘못된 암호를 넣었는데도 불구하고 로그인에 성공했다. 
이것이 SQL 삽입 공격이다. 

`MemberDao.findByEmailAndPassword(String email, String password)`의 
코드를 살펴보면 다음과 같이 사용자 입력한 값을 가지고 SQL문을 만들어 실행한다.

```
ResultSet rs = stmt.executeQuery(
            "select member_id, name, email, pwd, tel, photo"
                + " from lms_member"
                + " where email='" + email
                + "' and pwd=password('" + password + "')")
```

즉 다음과 같이 사용자가 입력한 값이 SQL 문장 안에 그대로 삽입된다.

```
select member_id, name, email, pwd, tel, photo
from lms_member
where email='user3@test.com' 
and pwd=password('aaa') or (email='user3@test.com' and 'a'='a')
```

사용자 암호 값 속에 포함된 
`aaa') or (email='user3@test.com' and 'a'='a` 문구 때문에 
암호에 상관없이 `where` 절 조건은 무조건 참이 된다. 
암호가 다르더라도 조건문에 포함된 사용자 이메일로 로그인에 성공하는 것이다. 




