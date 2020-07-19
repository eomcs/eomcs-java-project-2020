# 37_2 - Application Server 구조로 변경: Servlet + DAO 적용 + 통신 규칙1

## 학습목표

- Application Server 아키텍처의 구성과 특징을 이해한다.
- 통신 프로토콜 규칙에 따라 동작하는 서버를 만들 수 있다.
- DBMS 연동을 위해 프로젝트에 JDBC 드라이버를 추가할 수 있다. 

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/mariadb 디렉토리 생성
- src/main/java/com/eomcs/lms/dao/mariadb/BoardDaoImpl.java 추가
- src/main/java/com/eomcs/lms/dao/mariadb/LessonDaoImpl.java 추가
- src/main/java/com/eomcs/lms/dao/mariadb/MemberDaoImpl.java 추가
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/servlet/BoardListServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/LessonListServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/MemberListServlet.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: MariaDB JDBC Driver를 프로젝트에 추가하라.

- build.gradle 변경
  - mvnrepository.com 또는 search.maven.org 에서 'mariadb jdbc' 검색한다.
  - 라이브러리 정보를 dependencies {} 블록에 추가한다.
- 프로젝트의 이클립스 설정 파일 갱신 
  - 'gradle cleanEclipse' 명령으로 기존 이클립스 설정의 제거한다.
  - 'gradle eclipse' 명령으로 이클립스 설정 파일을 생성한다.
  - 이클립스 IDE에서 프로젝트를 refresh 한다.
- 프로젝트에 추가되었는지 확인한다.
  - 라이브러리 목록을 확인한다.
  
### 훈련2: 클라이언트 프로젝트에서 만든 DAO 관련 클래스를 가져오라.

- com.eomcs.lms.dao.mariadb 패키지 생성
- com.eomcs.lms.dao.mariadb.BoardDaoImpl 복사해오기
- com.eomcs.lms.dao.mariadb.LessonDaoImpl 복사해오기
- com.eomcs.lms.dao.mariadb.MemberDaoImpl 복사해오기

### 훈련3: Connection 객체를 준비해서 DAO를 생성할 때 주입하라.

- com.eomcs.lms.DataLoaderListener 변경
  - Connection 객체 생성
  - mariadb 관련 DAO 객체 생성

### 훈련4: '통신 규칙1'에 따라 동작하도록 BoardListServlet을 변경하라.

- com.eomcs.lms.servlet.Servlet 변경
  - service(Scanner in, PrintStream out) 메서드 추가한다.
  - 기존 구현체가 영향 받지 않도록 default 로 선언한다.
- com.eomcs.lms.servlet.BoardListServlet 변경
  - service(Scanner in, PrintStream out) 메서드 구현으로 변경한다.
  - '통신 규칙1'에 따라 클라이언트에게 결과를 응답한다.
  - 클라이언트의 BoardListCommand 클래스의 소스를 참고하라.
  
### 훈련5: 클라이언트의 '/board/list' 요청을 BoardListServlet으로 처리하라.

- com.eomcs.lms.ServerApp 변경
  - 클라이언트 명령을 처리할 서블릿을 찾아 실행한다. 
  
### 훈련6: 클라이언트의 '/member/list' 요청을 MemberListServlet으로 처리하라.

- com.eomcs.lms.servlet.MemberListServlet 변경
  - 기존 service() 메서드를 service(Scanner in, PrintStream out)으로 변경한다.
  - '통신 규칙1'에 따라 응답하도록 변경한다.
- com.eomcs.lms.ServerApp 변경
  - '/member/list' 요청을 처리할 MemberListServlet을 서블릿맵에 등록한다.
  
### 훈련7: 클라이언트의 '/lesson/list' 요청을 LessonListServlet으로 처리하라.

- com.eomcs.lms.servlet.LessonListServlet 변경
  - 기존 service() 메서드를 service(Scanner in, PrintStream out)으로 변경한다.
  - '통신 규칙1'에 따라 응답하도록 변경한다.
- com.eomcs.lms.ServerApp 변경
  - '/lesson/list' 요청을 처리할 LessonListServlet을 서블릿맵에 등록한다.
  
  
  