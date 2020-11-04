# 38-a. 데이터 처리 코드를 별도의 클래스로 분리하기 : DAO 클래스 도입

이번 훈련에서는,
- **DAO** 의 역할을 이해하고 데이터 처리 코드를 분리하는 연습을 한다.

**DAO(Data Access Object)**
- DBMS 또는 File 을 이용하여 데이터를 저장, 조회, 변경, 삭제하는 일을 하는 객체이다.
- 데이터 처리 로직을 DAO 객체로 분리해두면 객체를 재사용하거나 교체하기가 쉬워진다.

**High Cohesion(높은 응집력)**
- 하나의 모듈(메서드, 클래스 등)이 하나의 기능(역할)을 수행하게 하는 것을 의미한다.
- 목적이 분명하도록 작성한 메서드나 클래스는 신뢰도가 높고, 재사용과 코드를 이해하기 쉽다.
- 반대로 한 개의 메서드나 클래스가 여러 기능이나 역할을 수행한다면
  유지보수나 재사용이 어렵고 코드를 이해하기도 어렵다.

## 훈련 목표
- 소프트웨어 설계의 원칙 중 하나인 "High Cohesion"의 개념을 이해한다.
- 기존 코드에서 특정 역할을 하는 코드를 분리해 내는 것을 연습한다.
- DAO 의 역할과 DAO를 도입했을 때의 이점을 이해한다.
- DAO와 DBMS 테이블의 관계를 이해한다.

## 훈련 내용
- 커맨드 클래스에서 데이터 처리 코드를 분리하여 별도의 클래스(DAO)로 정의한다.

## 실습

### 1단계 - `BoardXxxCommand` 클래스에서 데이터 처리 코드를 분리하여 `BoardDao` 클래스를 정의한다.

- com.eomcs.pms.dao.BoardDao 클래스 생성
  - `BoardAddCommand` 에서 게시글 데이터를 입력하는 코드를 가져와서 insert() 메서드로 정의한다.
- com.eomcs.pms.dao.MemberDao 클래스 생성
  - `MemberListCommand` 에서 회원 정보를 찾는 코드를 가져와서 findByName() 메서드로 정의한다.
- `com.eomcs.pms.handler.BoardAddCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.insert()` 로 옮긴다.
- `com.eomcs.pms.handler.BoardDeleteCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.delete()` 로 옮긴다.
- - `com.eomcs.pms.handler.BoardDetailCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.findByNo()` 로 옮긴다.
- - `com.eomcs.pms.handler.BoardListCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.findAll()` 로 옮긴다.
- - `com.eomcs.pms.handler.BoardUpdateCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.update()` 로 옮긴다.

### 2단계 - `MemberXxxCommand` 클래스에서 데이터 처리 코드를 분리하여 `MemberDao` 클래스를 정의한다.
- com.eomcs.pms.dao.MemberDao 클래스 변경
  - `MemberAddCommand` 에서 데이터 입력 코드를 가져와서 insert() 메서드를 정의한다.
  - `MemberDeleteCommand` 에서 데이터 삭제 코드를 가져와서 delete() 메서드를 정의한다.
  - `MemberDetailCommand` 에서 데이터 조회 코드를 가져와서 findByNo() 메서드를 정의한다.
  - `MemberListCommand` 에서 데이터 삭제 코드를 가져와서 findAll() 메서드를 정의한다.
  - `MemberUpdateCommand` 에서 데이터 삭제 코드를 가져와서 update() 메서드를 정의한다.
- com.eomcs.pms.handler.MemberXxxCommand 변경
  - 데이터 처리는 `MemberDao` 를 사용하여 처리한다.
  - `MemberListCommand` 의 findByName() 메서드를 삭제한다.

### 3단계 - `ProjectXxxCommand` 클래스에서 데이터 처리 코드를 분리하여 `ProjectDao` 클래스를 정의한다.
- com.eomcs.pms.dao.ProjectDao 클래스 생성
  - `ProjectAddCommand` 에서 데이터 입력 코드를 가져와서 insert() 메서드를 정의한다.
  - `ProjectDeleteCommand` 에서 데이터 삭제 코드를 가져와서 delete() 메서드를 정의한다.
  - `ProjectDetailCommand` 에서 데이터 조회 코드를 가져와서 findByNo() 메서드를 정의한다.
  - `ProjectListCommand` 에서 데이터 삭제 코드를 가져와서 findAll() 메서드를 정의한다.
  - `ProjectUpdateCommand` 에서 데이터 삭제 코드를 가져와서 update() 메서드를 정의한다.
- com.eomcs.pms.handler.ProjectXxxCommand 변경
  - 데이터 처리는 `ProjectDao` 를 사용하여 처리한다.

### 4단계 - `TaskXxxCommand` 클래스에서 데이터 처리 코드를 분리하여 `TaskDao` 클래스를 정의한다.
- com.eomcs.pms.dao.MemberDao 클래스 변경
  - `TaskAddCommand` 에서 프로젝트 멤버 조회 코드를 가져와서 findByProjectNo() 메서드를 정의한다.
- com.eomcs.pms.dao.TaskDao 클래스 생성
  - `TaskAddCommand` 에서 데이터 입력 코드를 가져와서 insert() 메서드를 정의한다.
  - `TaskDeleteCommand` 에서 데이터 삭제 코드를 가져와서 delete() 메서드를 정의한다.
  - `TaskDetailCommand` 에서 데이터 조회 코드를 가져와서 findByNo() 메서드를 정의한다.
  - `TaskListCommand` 에서 데이터 삭제 코드를 가져와서 findAll() 메서드를 정의한다.
  - `TaskUpdateCommand` 에서 데이터 삭제 코드를 가져와서 update() 메서드를 정의한다.
- com.eomcs.pms.handler.TaskXxxCommand 변경
  - 데이터 처리는 `TaskDao` 를 사용하여 처리한다.

## 실습 결과
- src/main/java/com/eomcs/pms/dao/BoardDao.java 생성
- src/main/java/com/eomcs/pms/dao/MemberDao.java 생성
- src/main/java/com/eomcs/pms/dao/ProjectDao.java 생성
- src/main/java/com/eomcs/pms/dao/TeamDao.java 생성
- src/main/java/com/eomcs/pms/handler/BoardXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/MemberXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/TaskXxxCommand.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
