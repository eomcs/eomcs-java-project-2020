# 11 - 클래스 필드와 클래스 메서드의 한계

**클래스 필드(스태틱 필드)**는 클래스를 로딩할 때 생성된다.
클래스는 최초 사용 시점에 **한 번만 로딩**되기 때문에 스태틱 필드도 **한 번만 생성**된다.

이번 훈련에서는 이런 스태틱 필드의 구동 특성을 이해하고 
그에 따른 한계가 무엇인지 알아 볼 것이다. 

## 훈련 목표

- 클래스 필드의 한계를 이해한다.
- 클래스 필드 상태에서 기능을 확장하는 방법과 그 문제점을 확인한다.

## 훈련 내용

- 프로젝트 참여자들이 의견을 나눌 게시판을 추가한다.
  - 게시글을 등록하고 목록을 조회한다.
- 여러 개의 게시판을 추가한다.
  - 질문/답변 게실판, 일반 게시판, 공지사항 등

## 실습

### 1단계 - 게시글 입력을 처리한다

다음과 같이 게시글을 입력하는 기능을 추가한다.

```console
명령> /board/add
[새 게시글]
번호? 1
제목? 제목입니다.
내용? 내용입니다.
작성자? 홍길동
게시글을 등록하였습니다.

명령>
```

- 게시글 관리 작업을 수행할 클래스 `BoardHandler`를 만든다.
- 게시글 데이터를 위한 새 데이터 타입 `Board`을 정의한다.
- 게시글 입력을 처리할 메서드 `add()`를 정의한다.
- App 클래스에 *게시글 입력 명령* `/board/add`에 대한 처리를 추가한다.

#### 작업 파일 

- com.eomcs.pms.handler.BoardHandler  클래스 추가
  - 백업: BoardHandler_a.java
- com.eomcs.pms.App 변경
  - 백업: App_a.java


### 2단계 - 게시글 목록 출력을 처리한다.

다음과 같이 게시물(번호, 제목, 등록일, 작성자, 조회수) 목록을 출력하는 기능을 추가한다.


```
명령> /board/list
[게시글 목록]
1, 제목1, 홍길동, 2020-01-10, 0
2, 제목2, 임꺽정, 2020-01-20, 12
3, 제목3, 유관순, 2020-01-30, 7
```

- 게시글에 등록일 `registeredDate`과 조회수 `viewCount` 필드를 추가한다.
- `add()`에서 게시글을 입력을 처리할 때 등록일과 조회수를 설정한다.
- 게시글 목록을 처리할 메서드 `list()`를 정의한다.
- App 클래스에 *게시글 목록 조회 명령* `/board/list`에 대한 처리를 추가한다.

#### 작업 파일 

- com.eomcs.pms.handler.BoardHandler.Board 중첩 클래스 변경 
  - 등록일 과 조회수를 저장할 필드를 추가한다
- com.eomcs.pms.handler.BoardHandler 클래스 변경
  - add() 메서드 변경
  - list() 메서드 추가
- com.eomcs.pms.App 변경
  - 백업: App_b.java


### 3단계 - 새 게시판을 추가한다.

`BoardHandler`의 `Board[]` 배열은 클래스 필드(스태틱 필드)이기 때문에 
한 개 게시판의 게시물 목록만 관리할 수 있다.
다른 게시판을 만들려면 새로 `BoardHandler`와 똑 같은 클래스를 만들어야 한다. 

다음과 같이 동작하도록 새 클래스를 정의한다.

```
명령> /board2/add
번호? 1
내용? 게시글1
저장하였습니다.

명령> /board2/add
번호? 2
내용? 게시글2
저장하였습니다.

명령> /board/add
번호? 100
내용? 게시글100
저장하였습니다.

명령> /board2/list
1, 게시글1                  , 2019-01-01, 0
2, 게시글2                  , 2019-01-01, 0

명령> /board/list
100, 게시글100              , 2019-01-01, 0
```

- `BoardHandler`를 복제하여 `BoardHandler2` 클래스를 정의한다.
- `/board2/add`와 `/board2/list` 명령을 처리하도록 App 클래스를 변경한다.

#### 작업 파일 

- com.eomcs.pms.handler.BoardHandler2 클래스 추가
- com.eomcs.pms.App 변경
  - 백업: App_c.java
  
### 4단계 - 새 게시판을 4개 더 추가한다.

다음과 같이 동작하도록 새 클래스를 정의한다.

```
명령> /board3/add
...
명령> /board4/add
...
명령> /board5/add
...
명령> /board6/add
...
명령> /board3/list
...
명령> /board4/list
...
명령> /board5/list
...
명령> /board6/list
...
```

#### 작업 파일 

- com.eomcs.pms.handler.BoardHandler3 클래스 추가
- com.eomcs.pms.handler.BoardHandler4 클래스 추가
- com.eomcs.pms.handler.BoardHandler5 클래스 추가
- com.eomcs.pms.handler.BoardHandler6 클래스 추가
- com.eomcs.pms.App 변경

## 실습 결과

- src/main/java/com/eomcs/pms/handler/BoardHandler.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler2.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler3.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler4.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler5.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler6.java 추가
- src/main/java/com/eomcs/pms/App.java 변경
