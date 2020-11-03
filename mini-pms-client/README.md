# 37-d. 데이터 관리를 DBMS에게 맡기기 : 무결성 제약 조건 다루기 II

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

### 1단계 - `pms_task` 테이블에 프로젝트를 참조하는 FK 컬럼을 추가한다.

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

/* 다음과 같이 회원 번호와 프로젝트 번호를
   pms_member, pms_project 각 테이블에 대해서 FK를 설정하면,
   프로젝트 회원이 아닌 경우에도 작업을 등록하는 문제가 있다. */
/*   
alter table pms_task
  add constraint pms_task_fk1 foreign key(owner) references pms_member(no);

alter table pms_task
  add constraint pms_task_fk2 foreign key(project_no) references pms_project(no);
*/

alter table pms_task
  add constraint pms_task_fk1 foreign key(owner, project_no)
      references pms_member_project(member_no, project_no);
      
```

- com.eomcs.pms.domain.Task 변경
  - 프로젝트 번호를 저장할 필드를 추가한다.
- com.eomcs.pms.handler.TaskXxxCommand 변경
  - 작업 정보를 등록하거나 조회, 변경할 때 프로젝트 번호도 함께 다룬다.


select mp.member_no, m.name
from pms_member_project mp inner join pms_member m
on mp.member_no=m.no
where mp.project_no=3
order by m.name asc

```
> /task/add
프로젝트들:
  1, 가나다
  2, 하하하
  3, 오호라
프로젝트 번호? 4
유효하지 않은 프로젝트 번호 입니다.
프로젝트 번호? 3

작업내용? 아아아아아
마감일? 2020-1-1
상태?
0: 신규
1: 진행중
2: 완료
> 1
담당자?
  1, 홍길동
  7, 임꺽정
  9, 유관순
> 11
유효한 담당자 번호가 아닙니다.
> 7
작업을 등록하였습니다.
```

## 실습 결과
- src/main/java/com/eomcs/pms/domain/Project.java 변경
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/pms/handler/MemberListCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/TaskXxxCommand.java 변경
