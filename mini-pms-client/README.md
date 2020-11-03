# 38. 데이터 처리 코드를 별도의 클래스로 분리하기 : DAO 클래스 도입

이번 훈련에서는,
- **DAO(Data Access Object)** 의 역할을 이해하고 데이터 처리 코드를 분리하는 연습을 한다.


## 훈련 목표
-

## 훈련 내용
-

## 실습

### 1단계 - `BoardXxxCommand` 클래스에서 데이터 처리 코드를 분리하여 `BoardDao` 클래스를 정의한다.

- com.eomcs.pms.dao.BoardDao 클래스 생성
  - `BoardAddCommand` 에서 게시글 데이터를 입력하는 코드를 가져와서 insert() 메서드로 정의한다.
- com.eomcs.pms.dao.MemberDao 클래스 생성
  - `MemberListCommand` 에서 회원 정보를 찾는 코드를 가져와서 findByName() 메서드로 정의한다.
- `com.eomcs.pms.handler.BoardAddCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.insert()` 로 옮긴다.
- `com.eomcs.pms.handler.BoardDeleteCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.delete()` 로 옮긴다.
- - `com.eomcs.pms.handler.BoardDetailCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.findByNo()` 로 옮긴다.
- - `com.eomcs.pms.handler.BoardListCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.findAll()` 로 옮긴다.
- - `com.eomcs.pms.handler.BoardUpdateCommand` 변경
  - 데이터 처리와 관련된 코드를 `BoardDao.update()` 로 옮긴다.


## 실습 결과
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/pms/domain/Board.java 변경
- src/main/java/com/eomcs/pms/handler/TaskXxxCommand.java 변경
- src/main/java/com/eomcs/pms/handler/BoardXxxCommand.java 변경
