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

기존 기능을 변경하는 것을 연습해보자!

- src/main/resources/com/eomcs/pms/mapper/TaskMapper.xml 변경
  - `findAll` SQL 변경
- com.eomcs.pms.dao.TaskDao 인터페이스 변경
  - `findAll(Map<String,Object>)` 메서드를 변경한다.
- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - `findAll(Map<String,Object>)` 메서드를 변경한다.
- com.eomcs.pms.handler.TaskListCommand 변경
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
  - com.eomcs.pms.dao.mariadb.ProjectDaoImpl 변경
    - `ProjectDaoImpl` 클래스에서 *프로젝트 삭제* 를 수행하기 전에 예외를 발생시킨다.
    - 그런 후, 그 전에 수행했던 *프로젝트 멤버 삭제* 가 취소된 것을 확인한다.
    - 그러나 *작업 삭제* 가 취소되지 않은 것을 확인한다.
  - 이유?
    - `TaskDaoImpl.deleteByProjectNo()` 에서 사용한 `SqlSession` 객체와
      `ProjectDaoImpl.delete()` 에서 사용한 `SqlSession` 객체가 다르기 때문이다.
    - Mybatis 에서는 각 SqlSession 이 트랜잭션을 관리한다.
- **DAO** 객체에서 트랜잭션을 다루면 안되는 이유?
  - **DAO** 의 각 메서드는 작업을 수행하기 위해 현재 별도의 `SqlSession` 객체를 사용한다.
  - 트랜잭션은 `SqlSession` 객체에서 제어한다.
  - 즉 DAO 각 메서드 마다 트랜잭션이 분리되어 있다.
  - 실습 상황처럼 DAO 각 메서드 마다 트랜잭션이 분리되어 있으면,
    여러 DAO의 메서드를 묶어서 한 단위로 작업할 때
    통제할 수 없는 문제가 발생한다.
  - 해결책?
    - **DAO** 의 각 메서드가 트랜잭션을 통제하지 않도록 만든다.
    - 그럼 누가 트랜잭션을 통제하는가?
      - **DAO** 를 사용하는 **Command** 객체가 통제하게 한다.
      - 즉 트랜잭션 통제권을 **DAO** 를 사용하는 객체로 넘긴다.

### 3단계 - `Command` 객체에서 트랜잭션을 통제해 보자!

- com.eomcs.util.SqlSessionProxy 클래스 생성
  - Mybatis 의 `SqlSession` 구현체의 대리 역할을 수행할 클래스를 정의한다.
  - `close()` 메서드를 재정의한다.
  - 트랜잭션을 수행 중인 상태에서는 `close()`가 동작되지 않도록 막는다.
- com.eomcs.util.SqlSessionFactoryProxy 클래스 생성
  - Mybatis 의 `SqlSessionFactory` 구현체의 대리 역할을 수행할 클래스를 정의한다.
  - `startTransaction()`, `endTransaction()` 메서드 추가
  - `commit()`, `rollback()` 메서드 추가
  - `openSession()` 메서드 재정의
- com.eomcs.pms.listener.AppInitListener 클래스 변경
  - DAO 객체에 오리지널 `SqlSessionFactory` 대신에 프록시 객체를 주입한다.
- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - `deleteByProjectNo()` 메서드에서 SqlSession 을 얻을 때 수동 커밋 상태의
    `SqlSession` 을 사용하도록 변경한다.
  - 왜? 다른 작업과 묶을 수 있도록 하기 위함이다.
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - `delete()` 메서드에서 트랜잭션을 제어하는 코드를 없앤다.
    - `commit()` 호출하는 코드를 없앤다.
  - 왜? 트랜잭션 제어는 DAO를 사용하는 측에서 해야하기 때문이다.
  - 상황에 따라 여러 개의 DAO에서 수행한 작업을 한 트랜잭션을 묶어서 다룰 경우가 있다.
  - 이런 상황에서 각각의 DAO가 commit()/rollback() 을 하게 되면
    트랜잭션 제어가 안되기 때문이다.
- com.eomcs.pms.handler.ProjectDeleteCommand 클래스 변경
  - 여러 작업을 트랜잭션으로 묶어서 다룰 경우 트랜잭션 제어는 Command 객체에서 한다.
  - 예외없이 실행이 정상적으로 완료되었다면, SqlSessionFactoryProxy 에게 commit 요청한다.
  - 생성자에서 SqlSessionFactoryProxy 를 받아야 한다.


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
