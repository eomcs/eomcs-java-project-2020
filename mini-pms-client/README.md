# 37-c. 데이터 관리를 DBMS에게 맡기기 : 무결성 제약 조건 다루기

이번 훈련에서는,
- **무결성 제약 조건(integrity constraints)** 을 이용하여
  무효한 데이터가 존재하지 않도록 하는 방법을 배울 것이다.
- 테이블 간의 다대다 관계를 해소하기 위해 **관계 테이블** 을 다루는 방법을 배울 것이다.


## 훈련 목표
- **무결성 제약 조건** 의 의미를 이해한다.
- **외부 키** 를 설정하는 방법과 활용하는 것을 배운다.
- **다대다 관계** 의 문제를 이해하고 **관계 테이블** 을 이용하여 해소하는 것을 배운다.
- **조인** 을 활용하여 여러 테이블에 걸쳐 있는 데이터를 가져오는 것을 배운다.
- 테이블 간의 관계에 맞춰 객체 간의 포함 관계를 구현하는 것을 배운다.

## 훈련 내용
- 프로젝트 테이블과 작업 테이블을 변경한다.
  - 회원 이름을 저장하는 owner 컬럼을 회원 테이블에 존재하는 회원 번호를 저장하도록 외부 키(foreign key) 컬럼으로 변경한다.
  - 프로젝트 팀원 정보를 저장할 관계 테이블을 정의한다.
- 프로젝트 테이블과 작업 테이블의 변경에 맞춰 Command 구현체를 변경한다.
  - ProjectXxxCommand 클래스와 TaskXxxCommand 클래스를 변경한다.

## 실습

### 1단계 - 프로젝트 테이블(pms_project)과 작업 테이블(pms_task)에 외부 키 제약 조건을 설정한다.

- 프로젝트 테이블을 재정의한다.
```
create table pms_project(
  no int not null,
  title varchar(255) not null,
  content text not null,
  sdt date not null,
  edt date not null,
  owner int not null      /* pms_member 테이블의 'no' PK 컬럼 값을 저장해야 한다. */

  /* 다대다 관계를 표현하는 컬럼을 제거한다.*/
  /*   members varchar(255) not null */
);

alter table pms_project
  add constraint pms_project_pk primary key(no);

alter table pms_project
  modify column no int not null auto_increment;

alter table pms_project
  add constraint pms_project_fk foreign key(owner) references pms_member(no);
```

- 작업 테이블을 재정의한다.
```
create table pms_task(
  no int not null,
  content text not null,
  deadline date not null,
  owner int not null,   /* pms_member 의 PK 컬럼을 가리키는 외부키다*/
  status int default 0
);

alter table pms_task
  add constraint pms_task_pk primary key(no);

alter table pms_task
  modify column no int not null auto_increment;

alter table pms_task
  add constraint pms_task_fk foreign key(owner) references pms_member(no);
```

- 프로젝트와 멤버는 다대다 관계다!
  - 한 멤버가 0 개 이상의 프로젝트의 관리자가 될 수 있다.
    - 이것은 pms_project 테이블에 owner 컬럼을 통해 처리하였다.
  - 한 프로젝트에 1명 이상의 멤버가 참여할 수 있다.
    - 이것은 pms_project 테이블에 members 컬럼을 통해 처리하였다.
  - 문제점
    - owner 컬럼을 외부키(FK)로 설정하여 유효한 멤버 번호만 저장할 수 있게 통제할 수 있다.
    - 그러나 members 컬럼에는 여러 멤버의 번호를 저장하기 때문에 FK 로 설정할 수 없다.
    - 즉 members 컬럼에 유효하지 않은 회원 번호를 넣는 것을 막을 수 없다.
    - 이것은 다대다 관계일 때 발생하는 문제다.

- 위 다대다 관계일 때 FK를 설정하지 못하는 문제를 해결하기
  - 프로젝트에 참여하는 멤버 정보를 별도의 테이블에 저장한다.
  - 멤버가 참여하는 프로젝트를 정보를 별도의 테이블에 저장한다.
  - 즉 프로젝트와 멤버의 관계 정보를 저장할 테이블을 만들어 그 테이블에 저장한다.

```
/* 프로젝트와 멤버의 다대다 관계를 저장할 테이블을 정의한다.*/
create table pms_member_project(
  member_no int not null,
  project_no int not null
);

/* 다대다 관계를 저장할 컬럼의 대해 FK를 설정한다. */
alter table pms_member_project
  add constraint pms_member_project_fk1 foreign key(member_no) references pms_member(no),
  add constraint pms_member_project_fk2 foreign key(project_no) references pms_project(no);

/* 프로젝트-멤버 정보가 중복 저장되지 않도록 PK로 설정한다 */
alter table pms_member_project
  add constraint pms_member_project_pk primary key(member_no, project_no);
```

### 2단계 - `pms_project` 테이블의 변경에 맞춰 외부키를 다룰 수 있도록 ProjectXxxCommand 클래스를 변경한다.

- com.eomcs.pms.handler.MemberListCommand 변경
  - findByName() 를 변경한다.
  - Member 객체를 리턴할 때 회원 번호를 추가한다.
- com.eomcs.pms.domain.Project 변경
  - owner 필드를 관리자 회원 정보를 저장하도록 Member 타입으로 변경한다.
  - members 필드를 참여자 회원 목록을 저장하도록 List<Member> 타입으로 변경한다.
- com.eomcs.pms.handler.ProjectAddCommand 변경
  - `pms_project` 테이블에 프로젝트를 입력할 때 회원 이름 대신 번호를 저장한다.
  - 프로젝트를 입력한 후 프로젝트의 멤버들은 `pms_member_project` 테이블에 입력한다.
- com.eomcs.pms.handler.ProjectListCommand 변경
  - `pms_project` 테이블과 `pms_member` 테이블을 조인하여 회원 이름을 알아낸다.
- com.eomcs.pms.handler.ProjectDetailCommand 변경
  - `pms_project` 와 `pms_member` 를 조인하여 프로젝트 관리자의 이름을 알아낸다.
  - `pms_member_project` 와 `pms_member` 를 조인하여 팀원 목록과 그 이름을 알아낸다.
- com.eomcs.pms.handler.ProjectUpdateCommand 변경
  - 팀원 목록을 변경할 때 일단 기존 팀원들을 모두 지우고 새로 등록한다.
- com.eomcs.pms.handler.ProjectDeleteCommand 변경
  - `pms_member_project` 테이블에서 팀원 목록을 먼저 삭제한다.
  - 그런 후 프로젝트 정보를 삭제한다.

### 3단계 - `pms_task` 테이블의 변경에 맞춰 외부키를 다룰 수 있도록 TaskXxxCommand 클래스를 변경한다.

- com.eomcs.pms.domain.Task 변경
  - owner 필드를 담당자 회원 정보를 저장하도록 Member 타입으로 변경한다.
- com.eomcs.pms.handler.TaskXxxCommand 변경
  - 외부키를 고려하여 등록, 조회, 변경, 삭제를 처리한다.

### 4단계 - `pms_task` 테이블에 프로젝트를 참조하는 FK 컬럼을 추가한다.

- 작업 테이블을 재정의한다.
```
create table pms_task(
  no int not null,
  content text not null,
  deadline date not null,
  owner int not null,   /* pms_member 의 PK 컬럼을 가리키는 외부키다*/
  project_no int not null, /* pms_project 의 PK 컬럼을 가리키는 외부키다*/
  status int default 0
);

alter table pms_task
  add constraint pms_task_pk primary key(no);

alter table pms_task
  modify column no int not null auto_increment;

alter table pms_task
  add constraint pms_task_fk1 foreign key(owner) references pms_member(no);

alter table pms_task
  add constraint pms_task_fk2 foreign key(project_no) references pms_project(no);
```


## 실습 결과
- src/main/java/com/eomcs/pms/domain/Project.java 변경
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/pms/handler/MemberListCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/TaskXxxCommand.java 변경
