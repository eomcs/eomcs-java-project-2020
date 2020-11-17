### 42. 비즈니스 로직 분리하기 : 서비스 객체의 도입

이번 훈련에서는,
- *Mybatis* 에서 *트랜잭션* 을 다루는 방법을 배울 것이다.
- 기존 클래스의 코드를 손대지 않고 일부 기능을 변경하는 **프록시 패턴** 설계 기법을 배운다.



## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - 프로젝트 삭제 Command 객체에서 업무 코드를 분리한다.

커맨드 객체에서 비즈니스 로직을 분리하여 서비스 객체에 옮긴다.

- com.eomcs.pms.service.ProjectService 인터페이스 생성
  - 서비스 객체의 메서드 명은 보통 업무 관련 용어를 사용한다.
  - DAO 객체의 메서드 명은 데이터 관련 용어를 사용한다.
  - `delete()` 메서드 선언
- com.eomcs.pms.service.DefaultProjectService 클래스 생성
  - `delete()` 메서드 구현
    - `ProjectDeleteCommand` 에서 비즈니스 로직과 관련된 코드를 가져온다.
- com.eomcs.pms.handler.ProjectDeleteCommand 클래스 변경
  - `ProjectService` 구현체를 사용하여 프로젝트 삭제 처리


### 2단계 - 프로젝트 삭제 DAO 객체에서 업무 코드를 분리한다.

DAO 객체에서 비즈니스 로직을 분리하여 서비스 객체에 옮긴다.

- com.eomcs.pms.dao.ProjectDao 인터페이스 변경
  - `deleteMembers()` 메서드 선언 추가
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - `delete()` 메서드에서 멤버 삭제 관련 코드를 별도의 메서드 `deleteMembers()` 로 분리한다.
- com.eomcs.pms.service.DefaultProjectService 클래스 변경
  - `delete()` 메서드 변경
    - 프로젝트 멤버를 삭제하는 `deleteMembers()` 를 호출한다.

### 3단계 - 프로젝트 등록 커맨드 객체에서 비즈니스 로직을 분리한다.

- com.eomcs.pms.dao.ProjectDao 인터페이스 변경
  - `insertMembers()` 메서드 선언 추가
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - `insert()` 메서드에서 비즈니스 로직을 추출하여 별도의 메서드 `insertMembers()` 로 옮긴다.
- com.eomcs.pms.service.ProjectService 인터페이스 생성
  - `add()` 메서드 선언
- com.eomcs.pms.service.DefaultProjectService 클래스 생성
  - `add()` 메서드 구현
    - `ProjectAddCommand` 에서 비즈니스 로직과 관련된 코드를 가져온다.
- com.eomcs.pms.service.MemberService 인터페이스 생성
  - `list()` 메서드 선언
- com.eomcs.pms.service.DefaultMemberService 인터페이스 생성
  - `list()` 메서드 구현
- com.eomcs.pms.service.MemberDao 인터페이스 변경
  - `findByName()` 의 리턴 값을 `List` 객체로 변경한다.
- com.eomcs.pms.service.MemberDaoImpl 클래스 변경
  - `findByName()` 의 리턴 값을 `List` 객체로 변경한다.
- com.eomcs.pms.handler.ProjectAddCommand 클래스 변경
  - `MemberService` 구현체를 사용하여 멤버 찾기
  - `ProjectService` 구현체를 사용하여 프로젝트 등록 처리




## 실습 결과

- src/main/resources/com/eomcs/pms/mapper/TaskMapper.xml 생성
- src/main/java/com/eomcs/pms/dao/TaskDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/TaskDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/pms/handler/TaskListCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectDetailCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectDeleteCommand.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectUpdateCommand.java 변경
- src/main/java/com/eomcs/util/SqlSessionProxy.java 생성
- src/main/java/com/eomcs/util/SqlSessionFactoryProxy.java 생성
- src/main/java/com/eomcs/pms/listener/AppInitListener.java 변경
