# 37-c. 데이터 관리를 DBMS에게 맡기기 : 무결성 제약 조건 다루기

이번 훈련에서는,
- **무결성 제약 조건(integrity constraints)** 중에서 외부 키를 사용하는 방법을 배울 것이다.


## 훈련 목표
- **무결성 제약 조건** 의 의미를 이해한다.
- **외부 키** 를 설정하는 방법과 활용하는 방법을 연습한다.

## 훈련 내용
-

## 실습

### 1단계 - 프로젝트 테이블(pms_project)과 작업 테이블(pms_task)에 외부 키 제약 조건을 설정한다.

- 프로젝트와 멤버는 다대다 관계다!
  - 한 멤버가 0 개 이상의 프로젝트에 참여할 수 있다.
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

- 프로젝트 테이블을 재정의한다.
```
create table pms_project(
  no int not null,
  title varchar(255) not null,
  content text not null,
  sdt date not null,
  edt date not null,
  owner int not null,      /* pms_member 테이블의 'no' PK 컬럼 값을 저장해야 한다. */
  members varchar(255) not null
);

alter table pms_project
  add constraint pms_project_pk primary key(no);

alter table pms_project
  modify column no int not null auto_increment;

alter table pms_project
  add constraint pms_project_fk foreign key(owner) references pms_member(no);
```



## 실습 결과
- src/main/java/com/eomcs/pms/handler/BoardXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/MemberXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/TaskXxxCommand.java 변경
