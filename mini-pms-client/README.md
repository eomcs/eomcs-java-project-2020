# 37-c. 데이터 관리를 DBMS에게 맡기기 : 무결성 제약 조건 다루기

이번 훈련에서는,
- **무결성 제약 조건(integrity constraints)** 중에서 외부 키를 사용하는 방법을 배울 것이다.
- **관계 테이블** 을 만들고 사용하는 것을 배울 것이다.


## 훈련 목표
- **무결성 제약 조건** 의 의미를 이해한다.
- **외부 키** 를 설정하는 방법과 활용하는 방법을 연습한다.
- **다대다 관계** 의 문제점을 이해하고 해소하는 방법을 연습한다.
- **관계 테이블** 의 의미를 이해한다.

## 훈련 내용
-

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

### 2단계 - 프로젝트를 등록하거나, 조회, 변경할 때 회원 번호(FK)를 사용한다.

- com.eomcs.pms.handler.MemberListCommand 변경
  - findByName() 에서 데이터베이스에서 가져온 회원 저장할 때,
    회원 번호를 설정하는 코드를 추가한다.
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
  
## 실습 결과
- src/main/java/com/eomcs/pms/handler/BoardXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/MemberXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/TaskXxxCommand.java 변경
