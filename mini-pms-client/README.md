# 41-b. DB 프로그래밍 더 쉽고 간단히 하는 방법 : Mybatis 기타 기능 활용하기

이번 훈련에서는,
- 실무에서 자주 쓰이는 *퍼시스턴스 프레임워크* 중에 하나인 **마이바티스** 프레임워크의 사용법을 배울 것이다.

**퍼시스턴스 프레임워크(Persistence Framework)** 는,
- 데이터의 저장, 조회, 변경, 삭제를 다루는 클래스 및 설정 파일들의 집합이다.(위키백과)
- JDBC 프로그래밍의 번거로움 없이 간결하게 데이터베이스와 연동할 수 있다.
- 소스 코드에서 SQL 문을 분리하여 관리한다.

**마이바티스(Mybatis)** 는,
- *퍼시스턴스 프레임워크* 중의 하나이다.
- JDBC 프로그래밍을 캡슐화하여 데이터베이스 연동을 쉽게 하도록 도와준다.
- 자바 소스 파일에서 SQL을 분리하여 별도의 파일로 관리하기 때문에
  자바 소스 코드를 간결하게 유지할 수 있다.
- JDBC 프로그래밍 할 때와 마찬가지로 직접 SQL을 다루기 때문에
  레거시(legacy) 시스템에서 사용하는 데이터베이스와 연동할 때 유리하다.
- SQL을 통해 데이터베이스와 연동한다고 해서 보통 **SQL 매퍼(mapper)** 라 부른다.

## 훈련 목표
- **Mybatis SQL 맵퍼** 의 특징과 동작 원리를 이해한다.
- Mybatis 퍼시스턴스 프레임워크를 설정하고 다루는 방법을 배운다.

## 훈련 내용
-

## 실습

### 1단계 - fully-qualified class name 에 대해 별명을 부여하기

- src/main/resources/com/eomcs/pms/conf/mybatis-config.xml 변경
  - 클래스 이름에 대해 별명을 지정한다.
```
<typeAliases>
  <typeAlias type="com.eomcs.pms.domain.Board" alias="board"/>
  <typeAlias type="com.eomcs.pms.domain.Member" alias="member"/>
  <typeAlias type="com.eomcs.pms.domain.Project" alias="project"/>
  <typeAlias type="com.eomcs.pms.domain.Task" alias="task"/>
</typeAliases>
```
- src/main/resources/com/eomcs/pms/mapper/XxxMapper.xml 변경
  - 클래스 이름 대신 별명을 사용한다.

일부 자바 클래스에서 대해서 미리 별명이 부여되었다.
- 예)
  - int -> _int
  - java.lang.Integer -> int
  - java.lang.String -> string
  - java.util.Map -> map
  - java.util.HashMap -> hashmap

### 2단계 - 특정 패키지에 소속된 전체 클래스에 대해 별명 부여하기

- src/main/resources/com/eomcs/pms/conf/mybatis-config.xml 변경
```
<typeAliases>
  <package name="com.eomcs.pms.domain"/>
</typeAliases>
```

- src/main/resources/com/eomcs/pms/conf/jdbc.properties
  - 마이바티스 홈 : <http://www.mybatis.org>
  - `MyBatis` 설정 파일에서 참고할 DBMS 접속 정보를 등록한다.
- src/main/resources/com/eomcs/pms/conf/mybatis-config.xml
  - `MyBatis` 설정 파일이다.
  - DBMS 서버의 접속 정보를 갖고 있는 jdbc.properties 파일의 경로를 등록한다.
  - DBMS 서버 정보를 설정한다.
  - DB 커넥션 풀을 설정한다.


### 3단계: BoardDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.pms.dao.mariadb.BoardDaoImpl 클래스 변경
  - SQL을 뜯어내어 BoardMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
  - 백업: BoardDaoImpl01.java
- com/eomcs/pms/mapper/BoardMapper.xml 추가
  - BoardDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.
- com/eomcs/pms/conf/mybatis-config.xml 변경
  - BoardMapper.xml 파일의 경로를 등록한다.

### 4단계: App 에서 사용하는 객체를 AppInitListener 에서 모두 준비한다.

- com.eomcs.pms.dao.mariadb.BoardDaoImpl 클래스 변경
  - 각 메서드에서 SqlSessionFactory를 준비하는 대신에 생성자의 파라미터로 주입 받는다.
- com.eomcs.pms.listener.AppInitListener 클래스 변경
  - `SqlSessionFactory` 객체를 생성한다.
  - `XxxDao` 구현체 생성 코드도 이 클래스로 옮긴다.
  - `Command` 구현체 생성 코드도 이 클래스로 옮긴다.
- com.eomcs.pms.App 클래스 변경
  - DAO 구현체 생성 코드와 Command 구현체 생성 코드를 제거한다.
  - commandMap 객체 생성 코드도 제거한다.

### 5단계: MemberDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.pms.dao.mariadb.MemberDaoImpl 클래스 변경
  - SQL을 뜯어내어 MemberMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com/eomcs/pms/mapper/MemberMapper.xml 추가
  - MemberDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.
- com/eomcs/pms/conf/mybatis-config.xml 변경
  - MemberMapper.xml 파일의 경로를 등록한다.

### 6단계: ProjectDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.pms.dao.mariadb.ProjectDaoImpl 클래스 변경
  - SQL을 뜯어내어 ProjectMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com.eomcs.pms.dao.TaskDao 인터페이스 변경
  - 프로젝트의 작업을 삭제하는 deleteByProjectNo() 메서드 추가
- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - 프로젝트의 작업을 삭제하는 deleteByProjectNo() 메서드 구현
    - 프로젝트에 종속된 작업을 삭제하는 SQL을 뜯어내어 TaskMapper.xml로 옮긴다.
    - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com/eomcs/pms/mapper/ProjectMapper.xml 추가
  - ProjectDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.
- com/eomcs/pms/mapper/TaskMapper.xml 추가
  - ProjectDaoImpl 에 있던 프로젝트의 작업을 삭제하는 SQL문을 이 파일로 옮긴다.
- com/eomcs/pms/conf/mybatis-config.xml 변경
  - ProjectMapper.xml 파일의 경로를 등록한다.
  - TaskMapper.xml 파일의 경로를 등록한다.

### 7단계: TaskDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.pms.dao.mariadb.TaskDaoImpl 클래스 변경
  - SQL을 뜯어내어 TaskMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com/eomcs/pms/mapper/TaskMapper.xml 변경
  - TaskDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.


## 실습 결과
- build.gradle 변경
- src/main/resources/com/eomcs/pms/conf/jdbc.properties 생성
- src/main/resources/com/eomcs/pms/mapper/BoardMapper.xml 생성
- src/main/resources/com/eomcs/pms/mapper/MemberMapper.xml 생성
- src/main/resources/com/eomcs/pms/mapper/ProjectMapper.xml 생성
- src/main/resources/com/eomcs/pms/mapper/TaskMapper.xml 생성
- src/main/java/com/eomcs/pms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/ProjectDaoImpl.java 변경
- src/main/java/com/eomcs/pms/dao/TaskDao.java 변경
- src/main/java/com/eomcs/pms/dao/mariadb/TaskDaoImpl.java 변경
- src/main/java/com/eomcs/pms/listener/AppInitListener.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
