# 38-b. 데이터 처리 코드를 별도의 클래스로 분리하기 : DAO 인터페이스 도입

이번 훈련에서는,
- **DAO** 의 역할을 인터페이스로 정의하고 구현체를 사용하는 것을 연습한다.



## 훈련 목표
-

## 훈련 내용
-

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
