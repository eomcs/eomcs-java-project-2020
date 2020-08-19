# 14 - UI를 처리하는 코드와 데이터를 처리하는 코드를 분리하기

객체지향 프로그래밍을 할 때 다음 두 가지를 명심해야 한다.

- Low Coupling
- High Cohesion

**관계도 낮추기(low coupling)** 란? 한 클래스가 많은 클래스에 의존하는 구조로 
작성하면 의존 클래스가 변경될 때 마다 영향을 받기 때문에 유지보수에 좋지 않다.
그래서 가능한 의존하는 클래스의 접점을 줄이는 구조로 만드는 것이 좋다.
(관계도 낮추기 => 의존 클래스의 변경에 둔감 => 코드 변경이 줄어듬)

**응집력 강화(High Cohesion)** 란? 한 클래스가 너무 다양한 역할을 수행하면
클래스의 코드가 커지고 변경 사항이 잦아지기 때문에 유지보수에 좋지 않다.
그래서 가능한 한 클래스가 한 개의 역할만 수행하게 만드는 것이 유지보수에 좋다.
역할을 작게 쪼개게 되면 해당 클래스를 다른 프로젝트에서 재사용하기가 쉽다.
(응집력 강화 => 클래스를 잘게 쪼갬 => 교체가 용이하고 재사용성이 높아짐)
  
이번 훈련의 목표는 **응집력 강화** 를 연습해 보는 것이다.
기존의 XxxHandler 클래스는 사용자에게 출력하고 사용자의 입력을 받는, 
즉 사용자와 소통하는 역할을 수행할 뿐만 아니라 
사용자가 입력한 데이터를 관리하는 일도 수행한다.
이번 훈련에서는 XxxHandler의 역할을 더 작게 쪼개어 전문화시킬 것이다.
즉 사용자의 소통을 담당하는 **UI 역할** 클래스와 
데이터 처리를 담당하는 **DAO(Data Access Object) 역할** 클래스로 분리할 것이다.
이렇게 분리하게 되면 추후 데이터 처리 부분을 다른 클래스로 교체하기 쉬워진다.
예를 들면 지금은 데이터를 배열에 보관하고 있지만, 
나중에 파일이나 DBMS에 보관하는 클래스로 교체하기가 쉬워진다.
또한 지금은 CLI(Command Line Interface) 방식으로 UI를 처리하고 있지만,
나중에 웹으로 처리하는 클래스로 교체하기가 쉬워진다.


## 훈련 목표

- 캡슐화 기법을 이용하여 데이터 처리 코드를 별개의 클래스로 분리한다.
- 배열 복제를 통해 배열의 크기를 늘린다.
- 역할에 따라 클래스를 분리하는 방법과 이점을 이해한다.

## 훈련 내용

- XxxHandler 클래스를 UI 처리와 데이터 처리 역할자로 분리한다.


## 실습

### 1단계 - BoardHandler에서 데이터 처리 코드를 분리하라.

- `BoardHandler`에서 데이터 처리 코드를 분리하여 `BoardList` 클래스로 정의한다.
- `BoardList`의 기본 생성자와 배열의 초기 크기를 설정하는 생성자를 정의한다.
- `BoardList`에 Board 객체를 등록하는 add()를 정의한다.
- `BoardList`에 데이터 목록을 리턴하는 toArray() 메서드를 정의한다.
- `BoardHandler`는 `BoardList`를 사용하여 입,출력 데이터를 처리한다.

#### 작업 파일

- com.eomcs.pms.handler.BoardList 클래스 추가
  - 백업: BoardList.java.01
- com.eomcs.pms.handler.BoardHandler 클래스 변경
  - 백업: BoardHandler.java.01

### 2단계 - Board 클래스를 패키지 멤버 클래스로 전환한다.
 
여러 클래스에서 공유하는 클래스는 중첩 클래스 정의하기 보다는 
패키지 멤버 클래스로 정의하면 관리하기가 더 쉽다. 

- BoardList 클래스에서 Board 클래스를 분리하여 패키지 멤버 클래스로 만든다.
- BoardHandler 클래스는 이 패키지 멤버 클래스를 사용한다.

#### 작업 파일

- com.eomcs.pms.domain 패키지를 생성
  - Board 나 Member 처럼 데이터 타입 역할을 하는 클래스를 
    **도메인(domain)** 클래스라 부른다.
  - 도메인 클래스를 저장할 패키지를 따로 생성한다.
- com.eomcs.pms.domain.Board 클래스 생성
- com.eomcs.pms.handler.BoardHandler 클래스 변경
- com.eomcs.pms.handler.BoardList 클래스 변경

### 3단계 - MemberHandler 클래스에서 데이터 처리 코드를 별도의 클래스로 분리한다.

- BoardHandler와 마찬가지로 데이터 처리 역할을 별도의 클래스로 분리한다.
- `Member` 도메인 클래스를 정의한다.
- `MemberList` 클래스를 추가하고 
  `MemberHandler`에서 데이터 처리 코드를 가져온다.
- `MemberHandler` 클래스는 `MemberList`를 사용하여 데이터를 처리한다.

#### 작업 파일

- com.eomcs.pms.domain.Member 클래스 생성
- com.eomcs.pms.handler.MemberList 클래스 추가
- com.eomcs.pms.handler.MemberHandler 클래스 변경

### 4단계 - ProjectHandler 클래스에서 데이터 처리 코드를 별도의 클래스로 분리한다.

- BoardHandler와 마찬가지로 데이터 처리 역할을 별도의 클래스로 분리한다.
- `Project` 도메인 클래스를 정의한다.
- `ProjectList` 클래스를 추가하고 
  `ProjectHandler`에서 데이터 처리 코드를 가져온다.
- `ProjectHandler` 클래스는 `ProjectList`를 사용하여 데이터를 처리한다.

#### 작업 파일

- com.eomcs.pms.domain.Project 클래스 생성
- com.eomcs.pms.handler.ProjectList 클래스 추가
- com.eomcs.pms.handler.ProjectHandler 클래스 변경


### 5단계 - TaskHandler 클래스에서 데이터 처리 코드를 별도의 클래스로 분리한다.

- BoardHandler와 마찬가지로 데이터 처리 역할을 별도의 클래스로 분리한다.
- `Task` 도메인 클래스를 정의한다.
- `TaskList` 클래스를 추가하고 
  `TaskHandler`에서 데이터 처리 코드를 가져온다.
- `TaskHandler` 클래스는 `ProjectList`를 사용하여 데이터를 처리한다.

#### 작업 파일

- com.eomcs.pms.domain.Task 클래스 생성
- com.eomcs.pms.handler.TaskList 클래스 추가
- com.eomcs.pms.handler.TaskHandler 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/pms/domain/Board.java 추가
- src/main/java/com/eomcs/pms/domain/Member.java 추가
- src/main/java/com/eomcs/pms/domain/Project.java 추가
- src/main/java/com/eomcs/pms/domain/Task.java 추가
- src/main/java/com/eomcs/pms/handler/BoardList.java 추가
- src/main/java/com/eomcs/pms/handler/MemberList.java 추가
- src/main/java/com/eomcs/pms/handler/ProjectList.java 추가
- src/main/java/com/eomcs/pms/handler/TaskList.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경