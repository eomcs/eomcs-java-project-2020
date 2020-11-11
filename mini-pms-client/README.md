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

### 2단계 - 특정 패키지에 소속된 전체 클래스에 대해 별명 부여한다.

- src/main/resources/com/eomcs/pms/conf/mybatis-config.xml 변경
```
<typeAliases>
  <package name="com.eomcs.pms.domain"/>
</typeAliases>
```

### 3단계 - 게시글 검색 기능을 추가한다.

마이바티스의 `if` 태그를 사용하여 동적 SQL을 작성한다.

- 검색어에 해당하는 게시글이 있을 경우,
```
명령> /board/search
검색어? ok
번호, 제목, 작성자, 등록일, 조회수
13, okok4, ccc, 2020-11-09, 0
12, okok2, aaa, 2020-11-09, 0
8, okok, ggg, 2020-11-05, 0
```

- 검색어에 해당하는 게시글이 없을 경우,
```
명령> /board/search
검색어? ㅋㅋ
```

- 검색어를 입력하지 않을 경우
```
명령> /board/search
검색어?
번호, 제목, 작성자, 등록일, 조회수
13, okok4, ccc, 2020-11-09, 0
12, okok2, aaa, 2020-11-09, 0
10, test, ggg, 2020-11-06, 0
9, hul..., aaa, 2020-11-05, 0
8, okok, ggg, 2020-11-05, 0
```

- src/main/resources/com/eomcs/pms/mapper/BoardMapper.xml 변경
  - `findAll` SQL 문을 변경한다.
- com.eomcs.pms.dao.BoardDao 인터페이스 변경
  - `findAll()` 을 `findAll(String keyword)` 으로 변경한다.
- com.eomcs.pms.dao.mariadb.BoardDaoImpl 클래스 변경
  - `findAll(String keyword)` 를 구현한다.
- com.eomcs.pms.handler.BoardSearchCommand 클래스 생성
  - `BoardDao.findAll()` 을 사용하여 검색 기능을 처리한다.
- com.eomcs.pms.listener.AppInitListener 클래스 변경
  - `/board/search` 를 처리할 `BoardSearchCommand` 객체를 등록한다.

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
