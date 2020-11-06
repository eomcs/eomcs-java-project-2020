# 38-b. 데이터 처리 코드를 별도의 클래스로 분리하기 : DAO 인터페이스 도입

이번 훈련에서는,
- **인터페이스** 를 활용하여 객체의 교체를 쉽게 하는 방법을 배울 것이다.

**인터페이스** 는,
- 호출자와 피호출자 사이의 메서드 호출 규칙을 정의하는 문법이다.
- 객체 간의 결합도를 느슨하게 하여 객체 교체를 용이하게 만든다.
- 인터페이스 규칙에 따라 정의한 클래스를 *구현체(implementor)* 라 부른다.

## 훈련 목표
- *인터페이스* 문법의 용도의 활용을 연습한다.

## 훈련 내용
- 기존의 DAO 클래스에서 메서드 호출 규칙을 추출하여 인터페이스로 정의한다.
- 커맨드 객체는 특정 클래스를 사용하는 대신에 인터페이스 구현체를 사용하게 만든다.

## 실습

### 1단계 - `BoardDao` 를 인터페이스로 정의한다.

- com.eomcs.pms.dao.BoardDao 인터페이스 생성
- com.eomcs.pms.dao.mariadb.BoardDaoImpl 클래스 변경
  - 기존의 BoardDao 클래스를 mariadb 하위 패키지로 옮긴 후 이름을 변경한다.
  - 위에서 정의한 인터페이스를 구현한다.

### 2단계 - `MemberDao` 를 인터페이스로 정의한다.

- com.eomcs.pms.dao.MemberDao 인터페이스 생성
- com.eomcs.pms.dao.mariadb.MemberDaoImpl 클래스 변경
  - 기존의 MemberDao 클래스를 mariadb 하위 패키지로 옮긴 후 이름을 변경한다.
  - 위에서 정의한 인터페이스를 구현한다.

### 3단계 - `ProjectDao` 를 인터페이스로 정의한다.

- com.eomcs.pms.dao.ProjectDao 인터페이스 생성
- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - 기존의 ProjectDao 클래스를 mariadb 하위 패키지로 옮긴 후 이름을 변경한다.
  - 위에서 정의한 인터페이스를 구현한다.

### 4단계 - `TaskDao` 를 인터페이스로 정의한다.

- com.eomcs.pms.dao.TaskDao 인터페이스 생성
- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - 기존의 TaskDao 클래스를 mariadb 하위 패키지로 옮긴 후 이름을 변경한다.
  - 위에서 정의한 인터페이스를 구현한다.

## 실습 결과
- src/main/java/com/eomcs/pms/dao/BoardDao.java 변경
- src/main/java/com/eomcs/pms/dao/MemberDao.java 변경
- src/main/java/com/eomcs/pms/dao/ProjectDao.java 변경
- src/main/java/com/eomcs/pms/dao/TeamDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 생성
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 생성
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 생성
- src/main/java/com/eomcs/pms/dao/mariadb/TeamDaoImpl.java 생성
- src/main/java/com/eomcs/pms/App.java 변경
