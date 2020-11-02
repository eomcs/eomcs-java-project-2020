# 37-a. 데이터 관리를 DBMS에게 맡기기 : JDBC API 사용하기

이번 훈련에서는,
- **DBMS** 와 **JDBC API** 를 이용하여 데이터를 저장하고 조회하는 것을 배운다.

**데이터베이스(Database)** 는,
- 상호 연관이 있는 데이터의 모임을 가리킨다.
- 여러 사람이 공유하고 사용할 목적으로 통합 관리되는 정보의 집합이다.(wikipedia.org)
- 논리적으로 연관된 데이터를 검색과 갱신이 쉽도록 고도로 구조화한 것이다.
- 목적(wikipedia.og)
  - 데이터 중복 최소화
  - 데이터 공유
  - 일관성, 무결성, 보안성 유지
  - 데이터 접근 용이성
  - 데이터 저장 공간 절약

**데이터베이스 관리 시스템(Database Management System: DBMS)** 은,
- 데이터베이스의 목적을 달성할 수 있도록 편리하고 효율적인 환경을 제공하는 소프트웨어다.

**JDBC(Java Database Connectivity) API** 는,
- 자바 애플리케이션이 DBMS에 연동할 때 사용할 클래스와 호출 규칙을 정의한 라이브러리다
- DBMS 제작자는 *JDBC API* 의 구현체를 제공한다.

**JDBC 드라이버** 는,
- *JDBC API* 규약에 따라 만든 클래스 라이브러리다.
- 각각의 DBMS 제작자가 배포한다.

**JDBC 드라이버** 의 종류,
- Type 1
  - *JDBC-ODBC 브릿지 드라이버(bridge driver)* 라 부른다.
  - *ODBC API* 를 사용하여 DBMS와 연동한다.
  - *ODBC API* 를 제공하는 엑셀이나 액세스 등의 파일 DB에 접근할 때 용이하다.
  - 따라서 이 드라이버를 사용하는 컴퓨터에 ODBC 드라이버가 설치되어 있어야 한다.
  - 여러 계층을 경유하기 때문에 실행 속도가 느리다.
  - **JRE(Java Runtime Environment)** 에 기본으로 포함되어 있다.
- Type 2
  - *네이티브 드라이버(native driver)* 라 부른다.
  - DBMS 제작자가 제공하는 *데이터베이스 API(C/C++) = Vendor API = Native API* 를 호출하여 DBMS와 연동한다.
  - 따라서 이 드라이버를 사용하는 컴퓨터에 *데이터베이스 API* 가 설치되어 있어야 한다.
  - DBMS 제작자가 별도의 써드 파티 업체에서 제공한다.
- Type 3
  - *네트워크 프로토콜 드라이버(network protocol driver)* 라 부른다.
  - *미들웨어* 를 경유하여 DBMS와 연동한다.
  - 따라서 특정 DBMS에 종속되지 않으며, 하나의 드라이버로 여러 DBMS에 접근할 수 있다.
  - C/C++ 함수를 호출하지 않고 *미들웨어 전용 프로토콜* 로 직접 통신하기 때문에 100% 순수 자바 코드로 구현되었다.
  - *미들웨어* 구입 비용이 추가로 든다.
- Type 4
  - *씬 드라이버(thin driver)* 라 부른다.
  - DBMS와 연동할 때 *DBMS 전용 프로토콜* 로 직접 통신한다.
  - DBMS와 직접 통신하기 때문에 실행 속도가 빠르다.
  - 내부적으로 C/C++ 함수를 호출하지 않기 때문에 100% 순수 자바 코드로 구현되었다.
  - 단, 특정 DBMS에 종속된다.
  - DBMS 제작자에서 제공한다.

## 훈련 목표
- **JDBC API** 를 사용하여 **DBMS** 를 사용하는 방법을 배운다.
- **JDBC API** 와 **JDBC 드라이버** 의 관계를 이해한다.
- *JVM* 이 java.sql.Driver 구현체를 로딩하고 구동시키는 원리를 이해한다.
- 프로젝트에 **JDBC 드라이버** 를 추가하는 방법을 배운다.

## 훈련 내용
- 데이터를 파일에서 읽고 파일로 쓰는 기존 코드를 JDBC API를 사용하는 코드를 변경한다.


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
