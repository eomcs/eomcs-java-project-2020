# 16 - 다형성과 형변환 응용

이번 훈련에서는 **다형성(polymorphism)** 의 특징을 이용하는 사례를 다룰 것이다.

**다형성** 이란?
- 한 방식, 한 이름으로 다양한 타입의 데이터나 메서드를 다루는 기법이다.
- 같은 이름의 변수를 사용하여 여러 타입의 데이터를 다루는 것 : 다형적 변수(polymorphic variable) 
- 같은 이름의 메서드를 사용하여 여러 종류의 파라미터를 다루는 것 : 오버로딩(overloading)
  - 메서드를 호출할 때 전달하는 아규먼트에 따라 호출될 메서드가 결정된다.
- 부모 메서드와 같은 이름의 시그너처를 갖는 메서드를 정의하는 것 : 오버라이딩(overriding)
  - 메서드를 호출하는 객체의 타입에 따라 호출될 메서드가 결정된다.

다형성 문법을 잘 이용하면, 
- 한 개의 변수로 다양한 종류의 값을 다룰 수 있어 편리하다.
- 같은 기능을 하는 메서드에 대해 같은 이름을 사용할 수 있어 프로그래밍의 일관성을 유지할 수 있다.
- 상속 받은 메서드를 서브 클래스의 역할에 맞게 재정의 할 수 있어, 또한 프로그래밍의 일관성을 제공한다.

## 훈련 목표

- 다형적 변수(polymorphic variables)를 활용하여 다양한 타입의 객체를 다루는 방법을 배운다.
- 형변환을 연습한다.

## 훈련 내용

- 다형적 변수를 이용하여 Board, Member, Project, Task 타입의 객체를 모두 다룰 수 있는 ArrayList 클래스를 정의한다. 
- Board, Member, Project, Task 타입에 따라 개별적으로 만든 XxxList 클래스를 ArrayList로 교체한다.
- 원래 타입의 객체를 다룰 때는 형변환을 이용한다. 


## 실습

### 1단계 - `Board`, `Member`, `Project`, `Task` 타입의 객체를 모두 다룰 수 있는 `ArrayList` 클래스를 만든다.

- `BoardList`, `MemberList`, `ProjectList`, `TaskList` 클래스를 합쳐 한 클래스(`ArrayList`)로 만든다.

#### 작업 파일

- com.eomcs.util.ArrayList 클래스 생성

### 2단계 - XxxHandler 에서 사용하던 XxxList 클래스를 `ArrayList` 로 교체한다.

- `BoardHandler` 에서 `Board` 인스턴스 목록을 다루기 위해 사용하던 `BoardList` 클래스를 `ArrayList` 클래스로 변경한다. 
- `MemberHandler` 에서 `Member` 인스턴스 목록을 다루기 위해 사용하던 `MemberList` 클래스를 `ArrayList` 클래스로 변경한다. 
- `ProjectHandler` 에서 `Project` 인스턴스 목록을 다루기 위해 사용하던 `ProjectList` 클래스를 `ArrayList` 클래스로 변경한다. 
- `TaskHandler` 에서 `Task` 인스턴스 목록을 다루기 위해 사용하던 `TaskList` 클래스를 `ArrayList` 클래스로 변경한다. 
  
#### 작업 파일

- com.eomcs.pms.handler.BoardHandler 클래스 변경
- com.eomcs.pms.handler.MemberHandler 클래스 변경
- com.eomcs.pms.handler.ProjectHandler 클래스 변경
- com.eomcs.pms.handler.TaskHandler 클래스 변경
- com.eomcs.pms.handler.BoardList 클래스 삭제
- com.eomcs.pms.handler.MemberList 클래스 삭제
- com.eomcs.pms.handler.ProjectList 클래스 삭제
- com.eomcs.pms.handler.TaskList 클래스 삭제


## 실습 결과

- src/main/java/com/eomcs/util/ArrayList.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
- src/main/java/com/eomcs/pms/handler/BoardList.java 삭제
- src/main/java/com/eomcs/pms/handler/MemberList.java 삭제
- src/main/java/com/eomcs/pms/handler/ProjectList.java 삭제
- src/main/java/com/eomcs/pms/handler/TaskList.java 삭제