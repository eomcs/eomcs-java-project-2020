# 12. 인스턴스 필드와 인스턴스 메서드가 필요한 이유

**인스턴스 필드(non-static field)** 는 new 명령을 통해 Heap 영역에 생성된다.
개별적으로 다뤄야 할 값이라면 인스턴스 필드로 선언하라.

인스턴스 필드를 다루는 메서드는 **인스턴스 메서드(non-static method)** 로 선언한다.
인스턴스 메서드는 호출할 때 반드시 유효한 레퍼런스(인스턴스 주소)가 있어야 한다.
레퍼런스는 인스턴스 메서드의 내장(built-in) 로컬 변수인 this에 저장된다.

이번 훈련에서는 클래스 필드/메서드 대신 인스턴스 필드/메서드를 사용하여
여러 개의 게시글을 다루는 연습을 할 것이다.
이를 통해 인스턴스 필드/메서드의 쓰임새를 확인한다. 

## 훈련 목표

- 인스턴스 필드와 인스턴스 메서드를 사용할 수 있다.
- 스태틱 필드와 인스턴스 필드의 차이점과 용도를 이해한다.
- 스태틱 메서드와 인스턴스 메서드의 차이점과 용도를 이해한다.

## 훈련 내용

- 여러 개의 게시판을 다루기 위해 BoardHandler의 필드와 메서드를 인스턴스 멤버로 전환한다. 
- 기존의 MemberHandler와 ProjectHandler, TaskHandler도 필드와 메서드를 인스턴스 멤버로 전환한다.

## 실습

### 1단계 - BoardHandler의 스태틱 멤버를 인스턴스 멤버로 전환한다.

- 게시글 목록(Board 인스턴스 목록)을 저장할 배열을 인스턴스 필드로 변경한다.
- 등록된 게시글 개수를 저장하는 변수를 인스턴스 필드로 변경한다.
- 게시글 등록과 목록 조회를 다루는 메서드를 인스턴스 메서드로 변경한다.

#### 작업 파일 
- com.eomcs.pms.handler.BoardHandler 클래스 변경


### 2단계 - BoardHandler의 인스턴스 멤버를 사용하여 게시글 등록/목록조회를 처리한다.

- 게시글을 보관할 인스턴스를 생성한다.
- 인스턴스를 가지고 등록과 목록조회를 처리한다.

#### 작업 파일

- com.eomcs.pms.App 클래스 변경


### 3단계 - 여러 개의 게시판을 다룰 때도 BoardHandler를 사용한다. 

- 각 게시판을 위한 인스턴스 변수를 따로 생성한다.
- 이전에 만들었던 BoardHandlerX 클래스를 삭제한다. 

#### 작업 파일

- com.eomcs.pms.App 클래스 변경
- com.eomcs.pms.handler.BoardHandler2 클래스 삭제
- com.eomcs.pms.handler.BoardHandler3 클래스 삭제
- com.eomcs.pms.handler.BoardHandler4 클래스 삭제
- com.eomcs.pms.handler.BoardHandler5 클래스 삭제
- com.eomcs.pms.handler.BoardHandler6 클래스 삭제


### 4단계 - 회원 관리와 프로젝트 관리, 작업 관리도 향후 확장성을 고려해 인스턴스 멤버로 전환한다.

- MemberHandler의 스태틱 멤버를 인스턴스 멤버로 전환한다.
- ProjectHandler의 스태틱 멤버를 인스턴스 멤버로 전환한다.
- TaskHandler의 스태틱 멤버를 인스턴스 멤버로 전환한다.

#### 작업 파일

- com.eomcs.pms.handler.MemberHandler 클래스 변경
- com.eomcs.pms.handler.ProjectHandler 클래스 변경
  - ProjectHandler가 사용할 의존 객체(MemberHandler)를 담을 레퍼런스를 추가한다.
- com.eomcs.pms.handler.TaskHandler 클래스 변경
  - TaskHandler가 사용할 의존 객체(MemberHandler)를 담을 레퍼런스를 추가한다.
- com.eomcs.pms.App 클래스 변경


#### 작업 파일

- com.eomcs.pms.App 클래스 변경
- com.eomcs.pms.handler.BoardHandler2 클래스 삭제
- com.eomcs.pms.handler.BoardHandler3 클래스 삭제
- com.eomcs.pms.handler.BoardHandler4 클래스 삭제
- com.eomcs.pms.handler.BoardHandler5 클래스 삭제
- com.eomcs.pms.handler.BoardHandler6 클래스 삭제

## 실습 결과

- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
- src/main/java/com/eomcs/pms/handler/BoardHandler2.java 삭제
- src/main/java/com/eomcs/pms/handler/BoardHandler3.java 삭제
- src/main/java/com/eomcs/pms/handler/BoardHandler4.java 삭제
- src/main/java/com/eomcs/pms/handler/BoardHandler5.java 삭제
- src/main/java/com/eomcs/pms/handler/BoardHandler6.java 삭제
- src/main/java/com/eomcs/pms/App.java 변경