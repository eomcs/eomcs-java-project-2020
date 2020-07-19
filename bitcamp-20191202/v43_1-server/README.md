# 43_1 - MyBatis SQL 맵퍼 프레임워크를 사용하여 JDBC 코드를 대체하기

## 학습목표

- Mybatis SQL 맵퍼의 특징과 동작 원리를 이해한다.
- Mybatis 퍼시스턴스 프레임워크를 설정하고 사용할 수 있다. 

## 실습 소스 및 결과

- build.gradle 변경
- src/main/java/com/eomcs/lms/domain/PhotoBoard.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/BoardDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/LessonDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/PhotoBoardDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/PhotoFileDaoImpl.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardDetailServlet.java 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경
- src/main/resources/com/eomcs/lms/conf/mybatis-config.xml 추가
- src/main/resources/com/eomcs/lms/conf/jdbc.properties 추가
- src/main/resources/com/eomcs/lms/mapper/BoardMapper.xml 추가
- src/main/resources/com/eomcs/lms/mapper/LessonMapper.xml 추가
- src/main/resources/com/eomcs/lms/mapper/MemberMapper.xml 추가
- src/main/resources/com/eomcs/lms/mapper/PhotoBoardMapper.xml 추가
- src/main/resources/com/eomcs/lms/mapper/PhotoFileMapper.xml 추가

## 실습  

### 훈련1: 프로젝트에 MyBatis 라이브러리를 추가한다.

- build.gradle   
  - 의존 라이브러리 블록에서 `mybatis` 라이브러리를 등록한다.
- gradle을 이용하여 eclipse 설정 파일을 갱신한다.
  - `$ gradle eclipse`
- 이클립스에서 프로젝트를 갱신한다.
  
### 훈련2: `MyBatis` 설정 파일을 준비한다.

- com/eomcs/lms/conf/jdbc.properties
    - `MyBatis` 설정 파일에서 참고할 DBMS 접속 정보를 등록한다.
- com/eomcs/lms/conf/mybatis-config.xml
    - `MyBatis` 설정 파일이다.
    - DBMS 서버의 접속 정보를 갖고 있는 jdbc.properties 파일의 경로를 등록한다.
    - DBMS 서버 정보를 설정한다.
    - DB 커넥션 풀을 설정한다.

### 훈련3: BoardDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.lms.dao.mariadb.BoardDaoImpl 클래스 변경
  - SQL을 뜯어내어 BoardMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com/eomcs/lms/mapper/BoardMapper.xml 추가
  - BoardDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.
- com/eomcs/lms/conf/mybatis-config.xml 변경 
  - BoardMapper 파일의 경로를 등록한다.
- com.eomcs.lms.DataLoaderListener 변경
  - SqlSessionFactory 객체를 준비한다.
  - BoardDaoImpl 에 주입한다.

### 훈련4: MemberDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.lms.dao.mariadb.MemberDaoImpl 클래스 변경
  - SQL을 뜯어내어 MemberMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com/eomcs/lms/mapper/MemberMapper.xml 추가
  - MemberDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.
- com/eomcs/lms/conf/mybatis-config.xml 변경 
  - MemberMapper 파일의 경로를 등록한다.
- com.eomcs.lms.DataLoaderListener 변경
  - SqlSessionFactory 객체를 준비한다.
  - MemberDaoImpl 에 주입한다.

### 훈련5: LessonDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.lms.dao.mariadb.LessonDaoImpl 클래스 변경
  - SQL을 뜯어내어 LessonMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com/eomcs/lms/mapper/LessonMapper.xml 추가
  - LessonDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.
- com/eomcs/lms/conf/mybatis-config.xml 변경 
  - LessonMapper 파일의 경로를 등록한다.
- com.eomcs.lms.DataLoaderListener 변경
  - SqlSessionFactory 객체를 준비한다.
  - LessonDaoImpl 에 주입한다.

### 훈련6: PhotoBoardDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.lms.domain.PhotoBoard 클래스 변경
  - PhotoFile 목록 필드를 추가한다.
- com.eomcs.lms.dao.mariadb.PhotoBoardDaoImpl 클래스 변경
  - SQL을 뜯어내어 PhotoBoardMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com/eomcs/lms/mapper/PhotoBoardMapper.xml 추가
  - PhotoBoardDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.
- com/eomcs/lms/conf/mybatis-config.xml 변경 
  - PhotoBoardMapper 파일의 경로를 등록한다.
- com.eomcs.lms.DataLoaderListener 변경
  - SqlSessionFactory 객체를 준비한다.
  - PhotoBoardDaoImpl 에 주입한다.
- com.eomcs.lms.servlet.PhotoBoardDetailServlet 변경
  - PhotoFileDao 주입을 제거한다.
  - PHotoBoardDao로 첨부파일까지 모두 가져온다.
- com.eomcs.lms.ServerApp 변경
  - PhotoBoardDetailServlet에 PhotoFileDao 주입을 제거한다.
  
### 훈련7: PhotoFileDaoImpl 에 Mybatis를 적용한다.

- com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl 클래스 변경
  - SQL을 뜯어내어 PhotoFileMapper.xml로 옮긴다.
  - JDBC 코드를 뜯어내고 그 자리에 Mybatis 클래스로 대체한다.
- com/eomcs/lms/mapper/PhotoFileMapper.xml 추가
  - PhotoFileDaoImpl 에 있던 SQL문을 이 파일로 옮긴다.
- com/eomcs/lms/conf/mybatis-config.xml 변경 
  - PhotoFileMapper 파일의 경로를 등록한다.
- com.eomcs.lms.DataLoaderListener 변경
  - SqlSessionFactory 객체를 준비한다.
  - PhotoFileDaoImpl 에 주입한다.

### 훈련8: 기존의 트랜잭션이 작동하지 않음을 확인한다.

- 사진 게시글을 등록한다.
- 사진 파일을 등록할 때, 오류가 발생하도록 긴 파일명을 입력한다.
- 오류가 발생한 후에 사진 게시글이 등록되었는지 취소되었는지 확인한다.
- 취소되지 않은 이유는 Mybatis의 SqlSession이 자체적으로 커넥션을 관리하기 때문이다.
