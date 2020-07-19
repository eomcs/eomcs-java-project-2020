# 32_5 - 특정 기능을 수행하는 코드를 메서드로 분리하기 

## 학습목표

- 기능 별로 코드를 메서드로 분리할 수 있다.
- 분리한 메서드를 사용할 수 있다.
- "Extract Method" 리팩토링 기법을 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ServerApp.java 변경

## 실습  

### 훈련 1: 클라이언트의 요청을 처리하는 코드를 기능별로 분리하라. 

- ServerApp.java 변경
  - if~ else~ 분기문에 작성한 코드를 별도의 메서드로 분리하여 정의한다.
  - listBoard() : 게시물 목록 데이터 요청 처리
  - addBoard() : 게시물 데이터 등록 요청 처리
  - detailBoard() : 게시물 조회 요청 처리
  - updateBoard() : 게시물 변경 요청 처리
  - deleteBoard() : 게시물 삭제 요청 처리
  - listMember() : 회원 목록 데이터 요청 처리
  - addMember() : 회원 데이터 등록 요청 처리
  - detailMember() : 회원 조회 요청 처리
  - updateMember() : 회원 변경 요청 처리
  - deleteMember() : 회원 삭제 요청 처리
  - listLesson() : 수업 목록 데이터 요청 처리
  - addLesson() : 수업 데이터 등록 요청 처리
  - detailLesson() : 수업 조회 요청 처리
  - updateLesson() : 수업 변경 요청 처리
  - deleteLesson() : 수업 삭제 요청 처리      
