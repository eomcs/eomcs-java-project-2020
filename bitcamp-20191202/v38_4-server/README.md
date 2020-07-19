# 38_4 - 트랜잭션 적용 후: 사진 게시글 입력과 첨부 파일 입력을 한 단위로 다루기


## 학습목표

- 여러 개의 DB 변경 작업을 한 작업 단위로 묶는 트랜잭션을 다룰 수 있다.
- `commit`과 `rollback`을 활용할 수 있다.
- 트랜잭션 사용하기 전의 문제점을 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/servlet/PhotoBoardAddServlet.java 변경
- src/main/java/com/eomcs/servlet/PhotoBoardUpdateServlet.java 변경
- src/main/java/com/eomcs/servlet/PhotoBoardDeleteServlet.java 변경

## 실습  

### 훈련1: 트랜잭션을 사용하기 전의 문제점을 확인하라.

사진 게시물을 입력할 때, 
첨부 파일 일부는 DB 컬럼에서 허용된 길이 보다 더 긴 값을 갖게 한다.
이 때 오류가 발생하는데, 그럼에도 불구하고 사진 게시글이 정상적으로 입력되고,
오류가 발생하기 전에 입력한 첨부파일이 정상적으로 입력 되는 것을 확인한다.

`ClientApp` 실행 예:
```
명령> /photoboard/add
제목?
ok2
수업?
1
최소 한 개의 사진 파일을 등록해야 합니다.
파일명 입력 없이 그냥 엔터를 치면 파일 추가를 마칩니다.
사진 파일?
ok1.gif
사진 파일?
ok2.gif
사진 파일?
0123456789001234567890012345678900123456789001234567890
0123456789001234567890012345678900123456789001234567890
0123456789001234567890012345678900123456789001234567890
0123456789001234567890012345678900123456789001234567890
0123456789001234567890012345678900123456789001234567890
0123456789001234567890012345678900123456789001234567890
사진 파일?

java.sql.SQLDataException: (conn=12) Data too long for column 'PATH' at row 1 : (conn=12) Data too long for column 'PATH' at row 1

명령> /photoboard/detail
번호?
1
제목: ok2
작성일: 2018-11-14
조회수: 0
수업: 2
사진 파일:
> ok1.gif
> ok2.gif
```

### 훈련2: 사진 게시글 입력과 첨부파일 입력을 한 단위로 다뤄라. 

사진 게시글과 첨부파일을 입력하는 코드를 트랜잭션으로 묶어 한 단위로 다룬다.

- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
  - 게시글 입력과 첨부파일 입력 부분을 실행하기 전에 수동 commit으로 설정한다.
  - 성공하면 commit(), 실패하면 rollback() 한다.
  
### 훈련3: 사진 게시글 변경과 첨부파일 삭제, 입력을 한 단위로 다뤄라. 

- com.eomcs.lms.servlet.PhotoBoardUpdateServlet 변경
  - 게시글 변경과 첨부파일 삭제,입력 부분을 실행하기 전에 수동 commit으로 설정한다.
  - 성공하면 commit(), 실패하면 rollback() 한다.

### 훈련4: 사진 게시글 삭제와 첨부파일 삭제를 한 단위로 다뤄라. 

- com.eomcs.lms.servlet.PhotoBoardDeleteServlet 변경
  - 게시글 삭제와 첨부파일 삭제를 실행하기 전에 수동 commit으로 설정한다.
  - 성공하면 commit(), 실패하면 rollback() 한다.
  