# 38-d. 데이터 처리 코드를 별도의 클래스로 분리하기 : 트랜잭션이 필요한 이유

이번 훈련에서는,
- **트랜잭션** 이 필요한 이유와 다루는 방법을 배울 것이다.

**트랜잭션(transaction)** 은,
- 여러 개의 데이터 변경 작업(insert/update/delete)을 한 단위로 묶은 것을 가리키는 용어다.
- 트랜잭션은 DBMS나 개발자에 의해 `ACID` 가 보장되어야 한다.

**ACID(원자성, 일관성, 고립성, 지속성)** 란?
- 원자성(Atomicity)
  - 트랜잭션으로 묶인 작업은 모두 완전히 실행되거나 전혀 실행되지 않아야 한다.
  - 계좌이체의 경우, 내 계좌에서의 *출금* 과 다른 계좌로의 *입금* 이 모두 이루어졌을 때 완료된다.
  - *출금* 만 성공하고, *입금* 이 실패했다면 *출금* 작업을 취소해야 한다.
- 일관성(Consistency)
  - 트랜잭션을 수행한 후에도 데이터는 일관된 상태를 유지해야 한다.
  - 계좌이체의 경우, 내 계좌에서 100원을 출금했다면 상대편 계좌에 100원이 정확하게 입금되어야 한다.
  - 출금과 입금이 맞지 않다면 *일관성이 없는 상태* 가 된다.
- 고립성(Isolation)
  - 트랜잭션을 수행하는 다른 트랜잭션의 연산 작업이 끼어들지 못하도록 해야 한다.
  - 한 트랜잭션이 변경 중인 데이터를 다른 트랜잭션에서 사용한다면 데이터 일관성이 깨질 수 있다.
- 지속성(Durability)
  - 트랜잭션을 성공적으로 완료한 후에 변경한 데이터는 반드시 데이터베이스에 기록되어야 한다.
  - 시스템 전원이 나가거나 장애로 인해 데이터베이스에 기록되지 않은 경우에는,
    - 복구 시스템을 통해 로그에 기록된 것을 바탕으로 데이터베이스에 저장한다.
    - 또는 트랜잭션 실패로 처리한다.

프로그래밍에서 **트랜잭션** 을 다루는 방법은,
- DB 커넥션 객체에 대해 *자동 커밋* 을 취소하면 트랜잭션이 시작된다.
- 트랜잭션이 시작된 상태에서 수행된 데이터 변경은 DBMS의 임시 데이터베이스에 그 결과가 보관된다.
- `commit()` 을 호출하면 DBMS는 임시 데이터베이스에 보관된 데이터 변경 결과를 실제 데이터베이스에 반영한다.
- `rollback()` 을 호출하면 DBMS의 임시 데이터베이스에 보관된 데이터 변경 결과를 모두 버린다.


## 훈련 목표
- **트랜잭션** 이 적용되지 않았을 때 발생되는 문제를 경험하고 트랜잭션이 필요한 이유를 이해한다.
- `commit()` 과 `rollback()`을 사용하여 *트랜잭션* 을 다루는 방법을 연습한다.

## 훈련 내용
- 프로젝트 정보를 등록, 변경, 삭제하는 작업에 **트랜잭션** 을 적용하지 않았을 때의 문제점을 확인한다.
- 프로젝트 정보를 등록할 때 프로젝트 데이터를 입력하는 것과 팀원을 입력하는 것을 한 단위 작업으로 묶어 다룬다.
- 프로젝트 변경, 삭제 또한 마찬가지이다.

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
