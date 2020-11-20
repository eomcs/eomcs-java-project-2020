# 43. 애플리케이션 서버 아키텍처로 전환하기 : 클라이언트 + 서버 + 커맨드 + DBMS

이번 훈련에서는,
- 자바의 **Concurrent 프레임워크** 에서 제공하는 스레드풀을 프로젝트에 적용해 볼 것이다.

**Concurrent 프레임워크** 는,
- 동시성 프로그래밍을 위해 자바에서 제공하는 프레임워크이다.
- 스레드풀이나 비동기 입출력, 간단한 태스크 프레임워크를 포함하고 있다.


## 훈련 목표
- 자바에서 제공하는 **Concurrent 프레임워크** 의 **스레드풀** 사용법을 연습한다.

## 훈련 내용
- 자바에서 제공하는 **스레드풀** 을 사용하여 클라이언트 요청을 다룬다.

## 실습

- **mini-pms-36-b-server** 프로젝트의 소스 파일을 가지고 작업한다.
- **mini-pms-42-client** 프로젝트의 소스를 가져와서 합친다.

### 1단계 - 프로젝트에 라이브러리 추가하기   

- build.gradle 변경
  - MariaDB JDBC 드라이버 추가
  - Mybatis 라이브러리 추가
  - `$ gradle eclipse` 실행
  - 이클립스에서 프로젝트 갱신

### 2단계 - Mybatis 설정 파일 및 SQL 매퍼 파일을 가져온다.  

- `mini-pms-42-client/src/main/resources` 폴더를 가져온다.

### 3단계 - 도메인 클래스를 가져온다.

- `mini-pms-42-client/src/main/com/eomcs/pms/domain` 패키지를 가져온다.
  - 기존 클래스를 덮어쓴다.

### 4단계 - DAO 인터페이스와 구현체를 가져온다.

- `mini-pms-42-client/src/main/com/eomcs/pms/dao` 패키지를 가져온다.

### 5단계 - 서비스 인터페이스와 구현체를 가져온다.

- `mini-pms-42-client/src/main/com/eomcs/pms/service` 패키지를 가져온다.

### 6단계 - SqlSessionFactoryProxy와 SqlSessionProxy 소스를 가져온다.

- `mini-pms-42-client/src/main/com/eomcs/util` 패키지에서 파일을 가져온다.


### 7단계 - 리스너를 통해 Mybatis, DAO, Service 객체를 준비한다.

- com.eomcs.pms.listener.DataHandlerListener 변경
  - Mybatis 관련 객체를 준비한다.
  - DAO 객체를 준비한다.
  - Service 객체 생성
  - `mini-pms-42-client/src/main/com/eomcs/pms/listener/AppInitListener.java` 에서 관련 코드를 가져온다.


### 7단계 - 게시글 커맨드 객체를 변경한다.

데이터를 다룰 때 Service 객체를 사용한다.

- com.eomcs.pms.handler.Command 인터페이스 변경
  - `execute(PrintWriter, BufferedReader, Map<String,Object> context)` 파라미터를 변경한다.
  - 요청 처리 객체 간에 값을 공유하기 위해 context 맵 객체를 파라미터로 받는다.
- com.eomcs.pms.handler.BoardXxxCommand 클래스 변경
  - `BoardService` 구현체를 사용하여 사용자가 보낸 게시글을 DBMS 저장한다.
- com.eomcs.pms.listener.RequestMappingListener 변경
  - 테스트 하는 동안 사용할 로그인 사용자 정보를 임의 생성하여 주입해둔다.
  - BoardXxxCommand 객체에 주입

### 8단계 - 회원, 프로젝트, 작업 커맨드, 로그인/로그아웃 등 커맨드 객체를 변경한다.

화이팅!  

### 9단계 - 필터 추가한다.

- `mini-pms-42-client/src/main/com/eomcs/pms/filter` 패키지에서 관련 코드를 가져온다.
- com.eomcs.pms.listener.AppInitListener 클래스 변경
  - `mini-pms-42-client/src/main/com/eomcs/pms/App.java` 클래스에서 관련 코드를 가져온다.
  - `CommandFilterManager` 객체를 준비한다.
- com.eomcs.pms.App 클래스 변경
  - context 맵에 보관된 필터 체인을 사용하여 클라이언트 요청을 처리한다.
- com.eomcs.pms.handler.Request 클래스 변경
  - 커맨드 객체가 클라이언트의 데이터를 읽고 쓸 때 사용할 스트림 객체를 보관한다.


## 실습 결과

- src/main/java/com/eomcs/pms/ServerApp.java 변경
