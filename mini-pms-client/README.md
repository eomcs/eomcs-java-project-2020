# 42-a. 트랜잭션 다루기 : 트랜잭션 관리자가 필요한 이유

이번 훈련에서는,
- *트랜잭션 관리자* 를 구현하고 활용하는 방법을 연습할 것이다.

**트랜잭션 관리자** 는,
-


## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 트랜잭션을 다루기 전에 프로젝트 상세 조회 기능 변경한다.

- com.eomcs.pms.dao.TaskDao 인터페이스 변경
  - `findAll(Map<String,Object>)` 메서드를 변경한다.
- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - `findAll(Map<String,Object>)` 메서드를 변경한다.
- src/main/resources/com/eomcs/pms/mapper/TaskMapper.xml 변경
  - `findAll` SQL 변경
- com.eomcs.pms.handler.ProjectListCommand 변경
  - `findAll(null)` 호출 코드 변경
- com.eomcs.pms.handler.ProjectDetailCommand 변경
  - 프로젝트 정보 외에 작업 목록을 추가로 출력한다.

### 2단계 - 현재 프로젝트에서 트랜잭션을 다루는 방식과 문제점을 이해한다.

- com.eomcs.pms.handler.ProjectDeleteCommand
  - `TaskDao` 를 통해 작업을 삭제한다.
  - `ProjectDao` 를 통해 프로젝트 멤버와 프로젝트를 삭제한다.
  - *프로젝트 멤버 삭제* 와 *프로젝트 삭제* 작업은 한 트랜잭션으로 묶여 있다.
  - 그러나 *작업 삭제* 는 다른 트랜잭션에서 수행한다.
  - 만약 *프로젝트 삭제* 중에 예외가 발생한다면,
    *프로젝트 멤버 삭제* 는 자동 취소되지만,
    같은 트랜잭션에 묶여있지 않은 *작업 삭제* 는 취소되지 않는다.
- 문제 상황 실습:
  - `ProjectDaoImpl` 클래스에서 *프로젝트 삭제* 를 수행하기 전에 예외를 발생시킨다.
  - 그런 후, 그 전에 수행했던 *프로젝트 멤버 삭제* 가 취소된 것을 확인한다.
  - 그러나 *작업 삭제* 가 취소되지 않은 것을 확인한다.

## 실습 결과
- src/main/resources/com/eomcs/pms/conf/mybatis-config.xml 변경
- src/main/resources/com/eomcs/pms/mapper/BoardMapper.xml 생성
- src/main/resources/com/eomcs/pms/mapper/MemberMapper.xml 생성
- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 생성
- src/main/resources/com/eomcs/pms/mapper/TaskMapper.xml 생성
- src/main/java/com/eomcs/pms/dao/BoardDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/BoardSearchCommand.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/ProjectDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectSearchCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectDetailSearchCommand.java 변경
- src/main/java/com/eomcs/pms/listener/AppInitListener.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
