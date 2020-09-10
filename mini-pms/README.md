# 28-1. 커맨드 디자인 패턴을 적용하기

이번 훈련에서는 **커맨드 패턴(command pattern)** 을 프로젝트에 적용할 것이다.

**커맨드 디자인 패턴** 은, 

- 메서드의 객체화 설계 기법이다.
- 한 개의 명령어를 처리하는 메서드를 별개의 클래스로 분리하는 기법이다. 
- 이렇게 하면 명령어가 추가될 때마다 새 클래스를 만들면 되기 때문에  
  기존 코드를 손대지 않아 유지보수에 좋다.
- 즉 기존 소스에 영향을 끼치지 않고 새 기능을 추가하는 방식이다.
- 명령처리를 별도의 객체로 분리하기 때문에 실행 내역을 관리하기 좋고,
  각 명령이 수행했던 작업을 다루기가 편하다.
- 인터페이스를 이용하면 메서드 호출 규칙을 단일화 할 수 있기 때문에 
  코딩의 일관성을 높혀줄 수 있다.
- 단 기능 추가할 때마다 해당 기능을 처리하는 새 클래스가 추가되기 때문에 
  클래스 개수는 늘어난다.
- 그러나 유지보수 측면에서는 기존 코드를 변경하는 것 보다는 
  클래스 개수가 늘어나는 것이 좋다.
- 유지보수 관점에서는 소스 코드를 일관성 있게 유지보수 할 수 있는게 더 중요한다.


## 훈련 목표

- **커맨드 패턴** 의 클래스 구조와 구동원리를 이해한다.
- **커맨드 패턴** 을 구현하는 방법을 배운다.


## 훈련 내용

- 사용자 명령을 처리할 때 호출할 메서드의 규칙을 인터페이스로 정의한다.
- 명령어를 처리하는 메서드를 인터페이스에 맞춰 별개의 클래스로 캡슐화 한다. 

## 실습

### 1단계 - 사용자 명령을 처리하는 메서드의 호출 규칙을 정의한다.

- `Command` 인터페이스를 정의한다.
  - 사용자 명령을 처리할 때 호출되는 메서드를 선언한다.

#### 작업 파일

- com.eomcs.pms.handler.Command 생성


### 2단계 - 명령을 처리하는 XxxHandler 의 각 메서드를 `Command` 구현체로 분리한다.

- 각 명령어를 처리하는 메서드를 별도의 XxxCommand 클래스를 만들어 분리한다.
  - `Command` 인터페이스 규칙에 따라 클래스를 정의한다.

#### 작업 파일
 
- com.eomcs.pms.handler.BoardAddCommand 생성
- com.eomcs.pms.handler.BoardListCommand 생성
- com.eomcs.pms.handler.BoardDetailCommand 생성
- com.eomcs.pms.handler.BoardUpdateCommand 생성
- com.eomcs.pms.handler.BoardDeleteCommand 생성
- com.eomcs.pms.handler.BoardHandler 삭제
- com.eomcs.pms.handler.MemberAddCommand 생성
- com.eomcs.pms.handler.MemberListCommand 생성
- com.eomcs.pms.handler.MemberDetailCommand 생성
- com.eomcs.pms.handler.MemberUpdateCommand 생성
- com.eomcs.pms.handler.MemberDeleteCommand 생성
- com.eomcs.pms.handler.MemberHandler 삭제
- com.eomcs.pms.handler.ProjectAddCommand 생성
- com.eomcs.pms.handler.ProjectListCommand 생성
- com.eomcs.pms.handler.ProjectDetailCommand 생성
- com.eomcs.pms.handler.ProjectUpdateCommand 생성
- com.eomcs.pms.handler.ProjectDeleteCommand 생성
- com.eomcs.pms.handler.ProjectHandler 삭제
- com.eomcs.pms.handler.TaskAddCommand 생성
- com.eomcs.pms.handler.TaskListCommand 생성
- com.eomcs.pms.handler.TaskDetailCommand 생성
- com.eomcs.pms.handler.TaskUpdateCommand 생성
- com.eomcs.pms.handler.TaskDeleteCommand 생성
- com.eomcs.pms.handler.TaskHandler 삭제


### 3단계 - 사용자가 명령어를 입력했을 때 `Command` 구현체를 실행하도록 변경한다.

- `App` 클래스가 XxxCommand 객체를 통해 처리하도록 변경한다.

#### 작업 파일

- com.eocms.pms.App 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/List.java 삭제
- src/main/java/com/eomcs/util/Iterator.java 삭제
- src/main/java/com/eomcs/util/AbstractList.java 삭제
- src/main/java/com/eomcs/util/ArrayList.java 삭제
- src/main/java/com/eomcs/util/LinkedList.java 삭제
- src/main/java/com/eomcs/util/Stack.java 삭제
- src/main/java/com/eomcs/util/Queue.java 삭제
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
- src/main/java/com/eomcs/pms/App.java 변경

  