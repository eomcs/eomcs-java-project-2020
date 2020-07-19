# 43_3 - MyBatis의 dynamic sql 문법 사용하기

## 학습목표

- Mybatis에서 동적 SQL 생성하는 방법을 안다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/util/Prompt.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경
- src/main/java/com/eomcs/lms/mapper/XxxMapper.xml 변경
- src/main/java/com/eomcs/lms/dao/PhotoFileDao.java 변경
- src/main/java/com/eomcs/lms/dao/LessonDao.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/LessonDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/MemberDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/PhotoBoardDaoImpl.java 변경
- src/main/java/com/eomcs/lms/dao/mariadb/PhotoFileDaoImpl.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardAddServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardUpdateServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/MemberUpdateServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/LessonUpdateServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/LessonSearchServlet.java 변경

## 실습  

### 훈련1: `sql` 태그를 사용하여 공통 SQL 코드를 분리한다.

- src/main/resources/com/eomcs/lms/mapper/*Mapper.xml
  
### 훈련2: `foreach` 태그를를 사용하여 insert 문 생성하기

사진 게시글의 첨부파일을 입력할 때, 여러 값들을 한 번에 입력하기 

- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - 파일 목록을 한 번에 insert 하기
- com.eomcs.lms.servlet.PhotoBoardUpdateServlet 변경
  - 파일 목록을 한 번에 insert 하기  
- com.eomcs.lms.dao.PhotoFileDao 변경
  - insert(PhotoFile) 메서드를 insert(PhotoBoard) 로 변경한다.
- com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl 변경
  - insert()를 변경한다.
- src/main/resources/com/eomcs/lms/mapper/PhotoFileMapper.xml 변경
  - insertPhotoFile SQL 변경한다.
  - `foreach` 태그를 적용하여 여러 개의 값을 한 번에 insert 한다.
  
### 훈련3: `set` 태그를 사용하여 update할 때 일부 컬럼만 변경한다.

데이터를 변경할 때 일부 컬럼의 값만 변경하기

- src/main/resources/com/eomcs/lms/mapper/LessonMapper.xml 변경
  - updateLesson SQL을 변경한다.
- com.eomcs.util.Prompt 변경
  - 클라이언트가 보낸 값을 지정된 타입으로 변경할 수 없을 때의 예외를 처리한다.
- com.eomcs.lms.servlet.LessonUpdateServlet 변경
  - 클라이언트가 값을 보내지 않은 항목은 빈문자열이나 null, 0으로 설정한다.
  - 이 경우 update 대상 컬럼에서 제외된다.
- src/main/resources/com/eomcs/lms/mapper/MemberMapper.xml 변경
  - updateMember SQL을 변경한다.
- com.eomcs.lms.servlet.MemberUpdateServlet 변경
  - 클라이언트가 값을 보내지 않은 항목은 빈문자열이나 null, 0으로 설정한다.
  - 이 경우 update 대상 컬럼에서 제외된다.
  
### 훈련4: `where` 태그를 사용하여 검색 조건을 변경한다. 

수업을 검색(수업명, 시작일, 종료일, 총강의시간, 일강의시간)하는 기능을 추가한다.
검색 조건은 AND 연산으로 처리한다.

- src/main/resources/com/eomcs/lms/mapper/LessonMapper.xml 변경
  - selectLesson SQL문을 변경한다.
  - `where` 태그를 적용하여 조건을 만족하는 데이터를 찾는다. 
- com.eomcs.lms.dao.LessonDao 변경
  - findByKeyword() 메서드를 추가한다.
- com.eomcs.lms.servlet.LessonSearchServlet 추가 
  - 검색 요청을 처리한다.
- com.eomcs.lms.ServerApp 변경
  - LessonSearchServlet 객체 등록 





  
  
  
  
  
  
  