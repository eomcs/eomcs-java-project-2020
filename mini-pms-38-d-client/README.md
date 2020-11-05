# 38-d. 데이터 처리 코드를 별도의 클래스로 분리하기 : 트랜잭션 적용하기

이번 훈련에서는,
- **트랜잭션** 을 다루기 위해 commit 과 rollback을 사용하는 것을 연습한다.



## 훈련 목표
- **트랜잭션** 이 필요한 상황과 이유를 이해한다.
- `commit()` 과 `rollback()`을 사용하여 트랜잭션을 다루는 방법을 연습한다.

## 훈련 내용
- **트랜잭션** 적용하지 않았을 때의 문제점을 확인한다.

## 실습

### 1단계 - `ProjectAddCommand` 를 통해 트랜잭션이 적용되지 않았을 때의 상태를 경험한다.

- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 변경
  - 프로젝트 정보를 `pms_project` 테이블에 입력 한 후,
    60초 지연했다가 프로젝트 멤버를 `pms_member_project` 테이블에 입력한다.
  - 지연되는 중에 mariadb client 프로그램으로 프로젝트 멤버로 입력할 회원을 삭제한다.
  - 60초 지연 시간이 지난 후 프로젝트 멤버를 입력할 때,
    회원이 삭제되었기 때문에 FK 컬럼 오류가 발생할 것이다.
  - 그럼에도 불구하고 `pms_project` 테이블에 프로젝트 정보는 정상적으로 입력 될 것이다.

### 2단계 - `ProjectDao` 에 트랜잭션을 적용한다.

- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 변경
  - `insert()` 메서드에서 수행하는 프로젝트 등록과 프로젝트 멤버 등록을 한 단위 작업으로 묶는다.
    - `Connection.setAutoCommit(false)` 로 설정한다.
    - 정상적으로 수행했을 때만 `commit()` 을 호출한다.
  - `update()` 메서드에서 수행하는 프로젝트 변경과 프로젝트 멤버 삭제, 등록을 한 단위 작업으로 묶는다.
  - `delete()` 메서드에서 수행하는 데이터 변경 작업을 한 단위의 작업으로 묶는다.

## 실습 결과
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
