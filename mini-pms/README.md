# 10 - 프로젝트 게시판 추가

프로젝트 참여자들이 의견을 나눌 게시판을 추가한다.

## 훈련 목표

- 새 기능에 맞는 클래스를 정의하고 사용하는 것을 연습한다

## 훈련 내용

- 게시글을 다룰 클래스를 정의한다
- 정의한 클래스를 사용하여 게시글의 등록과 목록 출력을 처리한다

## 실습

### 1단계 - 게시글 입력을 처리한다

다음과 같이 게시글을 입력하는 기능을 추가한다.

```console
명령> /board/add
[새 게시글]
번호? 1
제목? 제목입니다.
내용? 내용입니다.

게시글을 등록하였습니다.
명령>
```

#### 작업 파일 

- com.eomcs.pms.handler.BoardHandler  클래스 추가
  - add() 메서드 정의
- com.eomcs.pms.App 변경
  - `/board/add` 명령 처리 추가


### 2단계 - 핸들러 클래스들을 별도의 패키지로 분류한다

- 핸들러 패키지 생성
    - `com.eomcs.pms.handler` 패키지 생성
- com.eomcs.pms.handler.MemberHandler 패키지 변경
  - 다른 패키지에서 메서드를 호출할 수 있도록 사용 범위를 public 으로 확장한다.
- com.eomcs.pms.handler.ProjectHandler 패키지 변경
  - 다른 패키지에서 메서드를 호출할 수 있도록 사용 범위를 public 으로 확장한다.
- com.eomcs.pms.handler.TaskHandler 패키지 변경
  - 다른 패키지에서 메서드를 호출할 수 있도록 사용 범위를 public 으로 확장한다.
- com.eomcs.pms.App 클래스 변경
    - 핸들러 클래스에 대해 import 문 변경

## 실습 결과

- com.eomcs.util 패키지 추가
- src/main/java/com/eomcs/util/Prompt.java 패키지 변경
- com.eomcs.pms.handler 패키지 추가
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 패키지 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 패키지 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 패키지 변경
- src/main/java/com/eomcs/pms/App.java 변경