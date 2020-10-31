# 37-b. 데이터 관리를 DBMS에게 맡기기 : SQL 삽입 공격과 자바 시큐어 코딩

이번 훈련에서는,
- **SQL 삽입 공격** 과 그것을 방어하는 방법을 배울 것이다.

**SQL 삽입 공격** 은,
- 사용자의 입력 값으로 SQL 문을 만들 때 가능한 공격 방법이다.
- 값을 입력할 때 SQL 문에 영향을 끼치는 SQL 코드를 함께 삽입하는 방법이다.
- 예)
  - SQL 문: `select * from user where username='아이디' and password='암호'`
  - 아이디: `hongkildong`
  - 암호: `ok' or 1=1 --`
- 입력 값이 포함된 최종 SQL 문
  - `select * from user where username='hongkildong' and password='ok' or 1=1 --'`
  - `1=1` 조건은 무조건 참이므로 username 과 password가 일치하지 않더라도 상관없이 결과는 true 이다.
  - `--` 은 줄 끝까지 주석으로 취급하기 때문에 SQL 문의 끝에 있던 작은 따옴표를 제거하는 효과가 있다.
- 해결 방법
  - `Statement` 대신에 `PreparedStatement` 를 사용하면 된다.

**Statement** vs **PreparedStatement**,
- SQL 삽입 공격
  - `Statement` : 가능
  - `PreparedStatement` : 불가능
- 바이너리 컬럼 값 넣기
  - `Statement` : 불가능
  - `PreparedStatement` : 가능
- 코드의 간결함
  - `Statement` : 가독성이 떨어진다. String.format()으로 개선할 수 있다.
  - `PreparedStatement` : in-parameter 문법으로 SQL 문과 값을 분리하기 때문에 가독성이 좋다.
- 실행 속도
  - `Statement`
    - SQL을 실행할 때 마다 DBMS는 매번 **SQL 구문 분석 -> 최적화 -> 실행** 과정을 거친다.
  - `PreparedStatement`
    - 처음 SQL을 실행할 때 딱 한 번 **SQL 구문 분석 -> 최적화 -> 실행** 과정을 거친다.
    - 이 이후에는 내부 **SQL 공유 풀** 에 보관된 최적화된 **실행 계획** 을 바로 **실행** 한다.
    - 따라서 SQL을 준비한 후 반복하여 실행하는 경우에는 Statement 보다 빠르다.

**SQL 실행 과정(오라클 DBMS의 예)**
- SQL 구분 분석(Parsing)
  - 문법의 유효성 검사(Syntax Check)
    - 문법의 규칙을 준수하는지 검사한다.
    - 예) `select * form pms_board`
    - `form` 은 잘못된 SQL 문법이다.
  - SQL 문의 의미 검사(Semantic Check)
    - SQL 문에서 지정하는 컬럼이나 테이블, 뷰 등이 유효한지 검사한다.
    - 예) `select * from pms_okok`
    - `pms_okok` 테이블이 없다면, *semantic 오류* 이다.
  - SQL 공유 풀 검사(Shared Pool Check)
    - *공유 풀* 은 SQL 문에 대해 생성된 **실행 계획(execution plan)** 등을 보관한다.
    - 먼저 SQL 문에 대해 해시(hash) 연산을 수행하여 SQL ID를 생성한다.
    - *공유 풀* 에 저장된 값 중에서 SQL ID와 일치하는 값이 있는지 조사한다.
    - 만약 있다면, 즉시 해당 값을 꺼내 **실행 계획** 에 따라 SQL 문을 실행한다.
    - 없다면, **하드 파싱(hard parsing)** 이라는 과정을 수행한다.
- 하드 파싱
  - SQL 최적화(Optimization)
    - SQL 문을 가장 효율적으로 실행할 수 있게 재구성한다.
    - 각 문장 별로 **실행 계획(execution plan)** 이라는 명령 코드를 생성한다.
    - 여러 개의 *실행 계획* 을 검토한 후 실행 비용을 계산하여 최적의 *실행 계획*을 생성한다.
  - SQL 컴파일(Row Source Generation)
    - 최적화 단계에서 생성된 *실행 계획* 을 입력으로 받는다.
    - 각 실행 단계 별로 *결과 데이터(result set)* 를 리턴할 바이너리 명령을 생성한다.
    - 이 바이너리 명령을 **Row Source** 라고 부른다.
    - *Row Source* 는 테이블이나 뷰, 조인 또는 그룹 연산을 가리킨다.
    - **Row Source Generator** 는 실행 순서에 따라 *Row Source Tree* 를 생성한다.
- SQL 실행
  - SQL 엔진은 *Row Source Tree* 에 따라가면서 *Row Source* 바이너리 명령을 실행한다.
  - 각 *Row Source* 바이너리 명령은 테이블이나 뷰, 조인 또는 그룹 연산 결과를 생성한다.
  - 최종 실행 결과는 애플리케이션에게 리턴할 **결과 데이터(Result Set)** 이다.


## 훈련 목표
- **SQL 삽입 공격** 의 원리와 위험성을 직접 체험한다.
- **PreparedStatement** 를 사용하여 **SQL 삽입 공격** 을 막는 것을 연습한다.

## 훈련 내용
- 기존에 `Statement` 를 사용하는 코드를 모두 `PreparedStatement` 사용으로 바꾼다.


## 실습

### 1단계 - SQL 삽입 공격을 시험한다.

- 게시글을 변경할 때 조회수와 등록일도 함께 변경하기
```
명령> /board/detail
[게시물 상세보기]
번호? 5
제목: bbb
내용: bbbb
작성자: park
등록일: 2020-10-31
조회수: 1

명령> /board/update
[게시물 변경]
번호? 5
제목(bbb)? xxx
내용(bbbb)? xxxx', vw_cnt=1234, cdt='2100-1-1     <== 이 부분이 SQL 삽입 공격이다.
정말 변경하시겠습니까?(y/N) y
게시글을 변경하였습니다.

명령> /board/detail
[게시물 상세보기]
번호? 5
제목: xxx
내용: xxxx
작성자: park
등록일: 2100-01-01     <== 등록일이 변경되었다.
조회수: 1235           <== 조회수가 임의적으로 변경되었다.
```

### 2단계 - DBMS에 게시글을 저장할 테이블을 생성한다.

```
create table pms_board(
  no int not null,
  title varchar(255) not null,
  content text not null,
  writer varchar(30) not null,
  cdt datetime default now(),
  vw_cnt int default 0
);

alter table pms_board
  add constraint pms_board_pk primary key(no);

alter table pms_board
  modify column no int not null auto_increment;

```

### 3단계 - DBMS를 이용하여 게시글을 저장하고 로딩한다.

- com.eomcs.pms.listener.DataHandlerListener 변경
  - 게시글 관련 데이터를 파일에서 로딩하고 파일로 저장하는 코드를 제거한다.
- com.eomcs.pms.handler.BoardAddCommand 변경
  - 데이터를 저장할 때 JDBC API를 사용한다.
- com.eomcs.pms.handler.BoardListCommand 변경
  - 데이터를 조회할 때 JDBC API를 사용한다.
- com.eomcs.pms.handler.BoardDetailCommand 변경
  - 데이터를 조회할 때 JDBC API를 사용한다.
- com.eomcs.pms.handler.BoardUpdateCommand 변경
  - 데이터를 변경할 때 JDBC API를 사용한다.
- com.eomcs.pms.handler.BoardDeleteCommand 변경
  - 데이터를 삭제할 때 JDBC API를 사용한다.
- com.eomcs.pms.App 변경
  - BoardXxxCommand 변경에 맞춰 소스 코드를 정리한다.

### 4단계 - DBMS에 회원 정보를 저장할 테이블을 만들고, 이 테이블을 사용하여 회원 정보를 관리한다.

```
create table pms_member(
  no int not null,
  name varchar(30) not null,
  email varchar(50) not null,
  password varchar(50) not null,
  photo varchar(255),
  tel varchar(20),
  cdt datetime default now()
);

alter table pms_member
  add constraint pms_member_pk primary key(no);

alter table pms_member
  modify column no int not null auto_increment;
```

- com.eomcs.pms.listener.DataHandlerListener 변경
  - 회원 관련 데이터를 파일에서 로딩하고 파일로 저장하는 코드를 제거한다.
- com.eomcs.pms.handler.MemberXxxCommand 변경
  - 데이터를 저장하고 조회, 변경, 삭제할 때 JDBC API를 사용한다.
- com.eomcs.pms.App 변경
  - MemberXxxCommand 변경에 맞춰 소스 코드를 정리한다.

### 5단계 - DBMS에 프로젝트 정보를 저장할 테이블을 만들고, 이 테이블을 사용하여 프로젝트 정보를 관리한다.

```
create table pms_project(
  no int not null,
  title varchar(255) not null,
  content text not null,
  sdt date not null,
  edt date not null,
  owner varchar(30) not null,
  members varchar(255) not null
);

alter table pms_project
  add constraint pms_project_pk primary key(no);

alter table pms_project
  modify column no int not null auto_increment;
```

- com.eomcs.pms.listener.DataHandlerListener 변경
  - 프로젝트 관련 데이터를 파일에서 로딩하고 파일로 저장하는 코드를 제거한다.
- com.eomcs.pms.handler.ProjectXxxCommand 변경
  - 데이터를 저장하고 조회, 변경, 삭제할 때 JDBC API를 사용한다.
- com.eomcs.pms.App 변경
  - ProjectXxxCommand 변경에 맞춰 소스 코드를 정리한다.

### 6단계 - DBMS에 작업 정보를 저장할 테이블을 만들고, 이 테이블을 사용하여 작업 정보를 관리한다.

```
create table pms_task(
  no int not null,
  content text not null,
  deadline date not null,
  owner varchar(30) not null,
  status int default 0
);

alter table pms_task
  add constraint pms_task_pk primary key(no);

alter table pms_task
  modify column no int not null auto_increment;
```

- com.eomcs.pms.listener.DataHandlerListener 삭제
  - 이제 더이상 파일 입출력 기능이 필요 없다.
- com.eomcs.pms.handler.TaskXxxCommand 변경
  - 데이터를 저장하고 조회, 변경, 삭제할 때 JDBC API를 사용한다.
- com.eomcs.pms.App 변경
  - TaskXxxCommand 변경에 맞춰 소스 코드를 정리한다.


## 실습 결과
- src/main/java/com/eomcs/pms/listener/DataHandlerListener.java 삭제
- src/main/java/com/eomcs/pms/handler/BoardXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/MemberXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/TaskXxxCommand.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
