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

### 2단계 - 데이터를 다룰 때 `Statement` 대신에 `PreparedStatement` 를 이용한다.

- com.eomcs.pms.handler.XxxCommand 변경
  - `Statement` 를 `PreparedStatement` 로 교체한다.

### 3단계 - SQL 삽입 공격을 시험한다.

- 게시글을 변경할 때 조회수와 등록일도 함께 변경하기
```
명령> /board/detail
[게시물 상세보기]
번호? 5
제목: xxx
내용: xxxx
작성자: park
등록일: 2100-01-01
조회수: 1236

명령> /board/update
[게시물 변경]
번호? 5
제목(xxx)? okok
내용(xxxx)? nono', vw_cnt=9999, cdt='2200-5-5    <== SQL 삽입 공격을 시도한다.
정말 변경하시겠습니까?(y/N) y
게시글을 변경하였습니다.

명령> /board/detail
[게시물 상세보기]
번호? 5
제목: okok
내용: nono', vw_cnt=9999, cdt='2200-5-5     <== 그냥 일반적인 값으로 다뤄졌다.
작성자: park
등록일: 2100-01-01     <== 등록일을 변경하지 못했다.
조회수: 1237           <== 조회수를 변경하지 못했다.
```

## 실습 결과
- src/main/java/com/eomcs/pms/handler/BoardXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/MemberXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/TaskXxxCommand.java 변경
