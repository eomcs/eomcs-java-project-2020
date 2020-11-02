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
