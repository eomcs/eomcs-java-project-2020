# 38_2 - 트랜잭션 적용 전: 사진 게시글에 첨부파일 추가하기


## 학습목표

- 여러 개의 DB 변경 작업을 한 작업 단위로 묶는 트랜잭션을 다룰 수 있다.
- `commit`과 `rollback`을 활용할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/domain/PhotoFile.java 추가 
- src/main/java/com/eomcs/dao/PhotoFileDao.java 추가
- src/main/java/com/eomcs/dao/mariadb/PhotoFileDaoImpl.java 추가
- src/main/java/com/eomcs/dao/mariadb/PhotoBoardDaoImpl.java 변경
- src/main/java/com/eomcs/servlet/PhotoBoardDetailServlet.java 변경
- src/main/java/com/eomcs/servlet/PhotoBoardAddServlet.java 변경
- src/main/java/com/eomcs/servlet/PhotoBoardUpdateServlet.java 변경
- src/main/java/com/eomcs/servlet/PhotoBoardDeleteServlet.java 변경
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련1: `수업 사진 게시판`에 사진 파일을 첨부하는 기능을 추가하라.

- com.eomcs.lms.domain.PhotoFile 추가
  - 사진 파일의 타입을 정의한다.
- com.eomcs.lms.dao.PhotoFileDao 인터페이스 추가
  - 사진 파일의 CRUD 관련 메서드 호출 규칙을 정의한다.
- com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl 추가
  - PhotoFileDao 인터페이스를 구현한다.
- com.eomcs.lms.DataLoaderListener 변경
  - PhotoFileDao 객체를 생성한다.

### 훈련2: '/photoboard/add' 명령을 처리하라.

사진 게시글을 입력할 때 사진 파일을 첨부할 수 있게 변경한다.

- com.eomcs.lms.dao.mariadb.PhotoBoardDaoImpl 변경
  - insert() 메서드를 변경한다.
  - insert 한 후에 자동 증가 PK 값을 리턴 받는다.
- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - LessonDao 객체를 주입 받아 수업 번호의 유효성을 검사한다.
  - 사진 게시글을 입력 받을 때 첨부 파일도 입력 받는다.
- com.eomcs.lms.ServerApp 변경
  - `PhotoBoardAddServlet` 객체에 LessonDao와 PhotoFileDao 객체를 주입한다. 

`ClientApp` 실행 예:
```
명령> /photoboard/add
제목?
ok
수업 번호?
1
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
   <== 입력 없이 엔터를 치면?
최소 한 개의 사진 파일을 등록해야 합니다.
사진 파일?
a1.gif
사진 파일?
a2.gif
사진 파일?
a3.gif
사진 파일?

사진을 저장했습니다.
```
    
### 훈련3: '/photoboard/detail' 명령을 처리하라.

사진 게시글을 출력할 때 첨부 파일 목록도 함께 출력한다.

- com.eomcs.lms.dao.PhotoFileDao 인터페이스 변경
  - 사진 파일 목록을 리턴하는 메서드를 추가한다.
  - findAll(int boardNo)
- com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl 변경
  - PhotoFileDao 인터페이스에 추가된 메서드를 구현한다.
- com.eomcs.lms.servlet.PhotoBoardDetailServlet 변경
  - PhotoFileDao 의존 객체를 주입받는다.
  - 사진 게시글 다음에 첨부파일 목록을 출력한다.
- com.eomcs.lms.ServerApp 변경
  - `PhotoBoardDetailServlet` 객체에 PhotoFileDao 객체를 주입한다. 
  
`ClientApp` 실행 예:
```
명령> /photoboard/detail
번호?
7
제목: 최종 발표
작성일: 2018-11-14
조회수: 0
수업: 2
사진 파일:
> ppt1.jpeg
> pp2.jpeg
> pp3.jpeg
```

### 훈련4: PhotoFile 객체의 생성자 및 셋터의 활용

- 인스턴스의 초기 값을 설정할 수 있는 생성자를 추가한다.

생성자를 통해 인스턴스의 초기 값을 설정하기 I:
- com.eomcs.lms.domain.PhotoFile 변경
  - PhotoFile(filepath, boardNO) 생성자 추가한다.
- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - PhotoFile(filepath, boardNo) 생성자를 사용한다.

생성자를 통해 인스턴스의 초기 값을 설정하기 II:
- com.eomcs.lms.domain.PhotoFile 변경
  - PhotoFile(int no, filepath, boardNO) 생성자 추가한다.
- com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl 변경
  - PhotoFile(no, filepath, boardNo) 생성자를 사용한다.

셋터 메서드를 통해 인스턴스의 초기 값을 설정하기:
- com.eomcs.lms.domain.PhotoFile 변경
  - 셋터 메서드가 인스턴스 주소를 리턴하게 변경한다.
- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - PhotoFile 객체를 만들 때 셋터 메서드로 값을 설정한다.
- com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl 변경
  - PhotoFile 객체를 만들 때 셋터 메서드로 값을 설정한다.
  

### 훈련5: '/photoboard/update' 명령을 처리하라.

사진 게시글을 변경할 때 첨부 파일도 변경한다.

- com.eomcs.lms.dao.PhotoFileDao 인터페이스 변경
  - 사진 파일을 삭제하는 메서드를 추가한다.
  - deleteAll(int boardNo)
- com.eomcs.lms.dao.mariadb.PhotoFileDaoImpl 변경
  - PhotoFileDao 인터페이스에 추가된 메서드를 구현한다.
- com.eomcs.lms.servlet.PhotoBoardUpdateServlet 변경
  - 사진 게시글의 첨부파일을 변경한다.
- com.eomcs.lms.ServerApp 변경
  - `PhotoBoardUpdateServlet` 객체에 PhotoFileDao 객체를 주입한다. 

`ClientApp` 실행 예:
```
명령> /photoboard/update
번호?
7
제목(okok2)?
최종 발표
사진 파일:
> aaa1.jpeg
> aaa2.jpeg

사진은 일부만 변경할 수 없습니다.
전체를 새로 등록해야 합니다.
사진을 변경하시겠습니까?(y/N)
y
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?

최소 한 개의 사진 파일을 등록해야 합니다.
사진 파일?
ppt1.jpeg
사진 파일?
pp2.jpeg
사진 파일?
pp3.jpeg
사진 파일?

사진을 변경했습니다.
```

### 훈련6: '/photoboard/delete' 명령을 처리하라.

사진 게시글을 삭제할 때 첨부 파일도 삭제한다.

- com.eomcs.lms.servlet.PhotoBoardDeleteServlet 변경
  - 첨부 파일 삭제를 할 때 사용할 PhotoFileDao 객체를 주입 받는다.
  - 사진 게시글을 삭제하기 전에 첨부 파일을 먼저 삭제한다.
  - 그런 후 사진 게시글을 삭제한다. 
- com.eomcs.lms.ServerApp 변경
  - `PhotoBoardDeleteServlet` 객체에 PhotoFileDao 객체를 주입한다. 
  
`ClientApp` 실행 예:
```
명령> /photoboard/delete
번호?
99
해당 사진 게시글을 찾을 수 없습니다.

명령> /photoboard/delete
번호?
7
사진 게시글을 삭제했습니다.
```
  
