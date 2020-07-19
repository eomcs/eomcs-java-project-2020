# 56_8 - 파일 업로드 기능 추가 

## 학습목표

- multipart 형식으로 파일을 업로드하고 처리할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/servlet/MemberAddServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/MemberDetailServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/MemberUpdateServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardAddServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardDetailServlet.java 변경
- src/main/java/com/eomcs/lms/servlet/PhotoBoardUpdateServlet.java 변경

## 실습  

### 훈련1: 회원 추가에 파일 업로드 기능을 추가한다.

- com.eomcs.lms.servlet.MemberAddServlet 변경
  - 입력폼에 multipart/form-data 인코딩 적용한다.
  - 서블릿 3.0에 추가된 멀티파트 데이터 처리 기능 활용하여 파일을 저장한다.

### 훈련2: 회원 조회에 사진을 출력한다.

- com.eomcs.lms.servlet.MemberDetailServlet 변경
  - img 태그를 이용하여 사진을 출력한다.
  - 사진을 변경할 수 있도록 변경폼을 multipart/form-data로 설정한다. 

### 훈련3: 회원 변경에 파일 업로드 기능을 추가한다.

- com.eomcs.lms.servlet.MemberUpdateServlet 변경
  - 멀티파트 형식으로 넘어온 데이터를 처리한다.
  
### 훈련4: 사진게시판에 파일 업로드를 적용한다.

- com.eomcs.lms.servlet.PhotoBoardAddServlet 변경
- com.eomcs.lms.servlet.PhotoBoardDetailServlet 변경
- com.eomcs.lms.servlet.PhotoBoardUpdateServlet 변경