# 37. 데이터 관리를 전문 프로그램인 DBMS에게 맡기기

이번 훈련에서는,
- **Stateful 통신 방식** 과 **Stateless 통신 방식** 의 차이를 이해한다.
- 기존 서버 애플리케이션을 **Stateless 통신 방식** 으로 바꾼다.

**Stateful 통신 방식** 은,
- 클라이언트와 서버가 한 번 연결되면 한 쪽에서 연결을 끊을 때까지 계속 연결된 상태로 있는다.
- 보통 한 쪽에서 연결을 끊기 전에 상대편으로 먼저 알려준다.
- 장점
  - 서버에서 클라이언트와 작업 결과를 손쉽게 관리할 수 있다.
- 단점
  - 클라이언트가 명시적으로 연결을 끊기 전까지는 계속 연결된 상태로 있기 때문에
    동시에 연결할 수 있는 클라이언트 수에 제한이 있다.

**Stateless 통신 방식** 은,
- 클라이언트가 서버에 요청할 때 마다 연결한다.
- 서버의 응답이 완료되면 즉시 연결을 끊는다.
- 장점
  - 클라이언트에게 응답하는 즉시 연결을 끊기 때문에 메모리를 좀 더 효율적으로 사용할 수 있다.
  - 왜냐하면, 클라이언트가 요청하지 않을 때는 서버에서 소켓을 유지 않기 때문이다.
- 단점
  - 클라이언트에서 요청할 때마다 서버와 연결해야 하기 때문에 요청 처리 시간에 연결 시간이 포함된다.

## 훈련 목표
- **Stateful** 과 **Stateless** 통신 방식의 특징과 차이점을 이해한다.
- **Stateless** 통신 방식을 구현할 수 있다.

## 훈련 내용
- 서버에서 응답하는 즉시 연결을 끊는다.


## 실습

### 1단계 - 프로젝트에 JDBC 드라이버를 설정한다.

- build.gradle 변경
  - mvnrepository.com 또는 search.maven.org에서 mariadb jdbc driver를 검색한다.
  - 라이브러리 정보를 build.gradle 파일에 설정한다.
  - gradle을 이용하여 eclipse 설정 파일을 갱신한다.
    - `$ gradle eclipse`
    - 다운로드 받지 않은 라이브러리가 있다면 자동으로 서버에서 받을 것이다.
    - 라이브러리 정보가 변경되었다면 해당 라이브러리를 서버에서 받을 것이다.
  - 이클립스 프로젝트를 리프래시 한다.
    - 프로젝트에 mariadb jdbc driver 라이브러리가 추가되었는지 확인한다.

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

- com.eomcs.pms.listener.DataHandlerListener 변경
  - 작업 관련 데이터를 파일에서 로딩하고 파일로 저장하는 코드를 제거한다.
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
