# 36_1 - DBMS를 도입하여 데이터 관리를 맡기기

## 학습목표

- 오픈 소스 DBMS `MariaDB`를 설치할 수 있다.
- DBMS에 사용자와 데이터베이스를 추가할 수 있다.
- 테이블 생성과 예제 데이터를 입력할 수 있다.
- JDBC API의 목적을 이해한다.
  - DBMS와의 통신을 담당하는 proxy 객체의 사용 규칙을 통일하는 것.
- JDBC Driver의 의미를 이해한다.
  - JDBC API 규칙에 따라 정의한 클래스들(라이브러리).
- JDBC API를 활용하여 DBMS에 데이터를 입력, 조회, 변경, 삭제할 수 있다.
- JDBC 프로그래밍 코드를 클래스로 캡슐화 할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/mariadb 패키지 생성
- src/main/java/com/eomcs/lms/dao/mariadb/BoardDaoImpl.java 생성
- src/main/java/com/eomcs/lms/dao/mariadb/LessonDaoImpl.java 생성
- src/main/java/com/eomcs/lms/dao/mariadb/MemberDaoImpl.java 생성
- src/main/java/com/eomcs/lms/handler/BoardAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/BoardDeleteCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/LessonDeleteCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberAddCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberUpdateCommand.java 변경
- src/main/java/com/eomcs/lms/handler/MemberDeleteCommand.java 변경
- src/main/java/com/eomcs/lms/ClientApp.java 변경

## 실습  

### 훈련1: 애플리케이션에서 사용할 사용자와 데이터베이스를 MariaDB에 추가한다.

MariaDB 에 연결하기 

```
$ mysql -u root -p
Enter password: 암호입력
...

MariaDB [(none)]>
```

사용자 생성하기
```
MariaDB [(none)]> CREATE USER 'study'@'localhost' IDENTIFIED BY '1111';
```

데이터베이스 생성하기
```
MariaDB [(none)]> CREATE DATABASE studydb
  CHARACTER SET utf8
  COLLATE utf8_general_ci;
```

사용자에게 DB 사용 권한을 부여하기
```
GRANT ALL ON studydb.* TO 'study'@'localhost';
```

MariaDB에 `study` 사용자 아이디로 다시 접속하기
```
$ mysql -u study -p
Enter password: 1111
...

MariaDB [(none)]>
```

`study` 아이디로 사용할 수 있는 데이터베이스 목록 보기
```
MariaDB [(none)]> show databases;
+--------------------+
| Database           |
+--------------------+
| studydb            |
| information_schema |
| test               |
+--------------------+
3 rows in set (0.000 sec)

MariaDB [(none)]> 
```



### 훈련2: 애플리케이션에서 사용할 테이블과 예제 데이터를 준비하라.

`study` 아이디로 MariaDB에 접속한 후 기본으로 사용할 데이터베이스를 `studydb`로 설정하기
```
MariaDB [(none)]> use studydb;
...

Database changed
MariaDB [eomcs]> 
``` 

애플리케이션에서 사용할 테이블 생성하기. 
```
다음 파일의 내용을 복사하여 MariaDB 명령창에 붙여 넣고 실행한다.
bitcamp-study    (Git 저장소)
    /bitcamp-docs   
        /db
            /project-ddl.sql  (테이블 정의 SQL 문이 들어 있는 파일)
```

생성된 테이블에 예제 데이터 입력하기. 
```
다음 파일의 내용을 복사하여 MariaDB 명령창에 붙여 넣고 실행한다.
bitcamp-study    (Git 저장소)
    /bitcamp-docs   
        /db
            /project-data.sql  (INSERT SQL 문이 들어 있는 파일)
```

### 훈련3: 프로젝트에 `MariaDB` JDBC 드라이버를 추가하라.

- build.gradle
    - 의존 라이브러리에 MariaDB JDBC Driver 정보를 추가한다.
    - 예를 들면, `mvnrepository.com`에서 키워드 `mariadb jdbc`로 검색하면 **MariaDB Java Client** 라이브러리를 찾을 수 있다.
    - 최신 버전의 라이브러리 정보를 가져오면 된다.

build.gradle 파일
```
plugins {
    id 'java'
    id 'application'
    id 'eclipse'
}

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
    sourceCompatibility = '1.8'
    targetCompatibility = '1.8'
}

mainClassName = 'App'

dependencies {
    compile group: 'org.mariadb.jdbc', name: 'mariadb-java-client', version: '2.3.0'
    compile 'com.google.guava:guava:23.0'
    testCompile 'junit:junit:4.12'
}

repositories {
    jcenter()
}
```

`gradle`을 실행하여 이클립스 설정 파일을 갱신하기
```
$ gradle eclipse
```

이클립스 워크스페이스의 프로젝트를 갱신하기
> 이클립스에서도 프로젝트에 `Refresh`를 수행해야 라이브러리가 적용된다.

### 훈련4: MariaDB에서 제공하는 Proxy를 사용하여 DBMS와 연동하여 데이터 처리 작업을 수행할 DAO를 정의하라.

- com.eomcs.lms.dao.mariadb 패키지 생성한다.
- com.eomcs.lms.dao.mariadb.BoardDaoImpl 추가한다.
- com.eomcs.lms.dao.mariadb.LessonDaoImpl 추가한다.
- com.eomcs.lms.dao.mariadb.MemberDaoImpl 추가한다.

### 훈련5: Command 객체의 기존 DAO를 MariaDB 연동 DAO로 교체하라. 

- com.eomcs.lms.ClientApp 변경한다.

### 훈련6: DBMS 연동에 맞춰서 Command 객체를 변경하라.

- com.eomcs.lms.handler.XxxAddCommand 변경한다.
- com.eomcs.lms.handler.XxxUpdateCommand 변경한다.
- com.eomcs.lms.handler.XxxDeleteCommand 변경한다.



