# 23 - 추상 클래스와 추상 메서드

**추상 클래스** 는 

- 서브 클래스에 기본 기능 및 공통 분모를 상속해 주는 역할을 하는 클래스다.
- new 명령을 통해 인스턴스를 생성할 수 없다.
- **상속** 의 기법 중에서 **일반화** 를 통해 수퍼 클래스를 정의한 경우 보통 추상 클래스로 선언한다.
- 추상 메서드를 가질 수 있다.

**추상 메서드** 는 

- 서브 클래스에 따라 구현 방법이 다른 경우 보통 추상 메서드로 선언한다.
- 서브 클래스에서 반드시 구현해야 하는 메서드다.
- 즉 서브 클래스를 정의할 때 반드시 해당 메서드를 구현하도록 강제하고 싶다면 추상 메서드로 선언한다.
- 일반 클래스는 추상 메서드를 가질 수 없다. 
- 추상 클래스와 인터페이스 만이 추상 메서드를 가질 수 있다.

이번 훈련에서는 **추상 클래스** 를 선언하고 **추상 메서드** 를 정의하는 것을 연습한다.


## 훈련 목표

- **추상 클래스** 의 용도를 이해하고 활용법을 연습한다.
- **추상 메서드** 의 용도와 활용법을 연습한다.


## 훈련 내용

- `List` 클래스를 추상 클래스로 변경한다.
- `List` 클래스의 메서드를 추상 메서드로 변경한다.


## 실습

### 1단계 - `List` 클래스를 추상 클래스로 선언한다.

- `List` 클래스
  - 추상 클래스로 선언한다.
  - 이름을 `AbstractList` 로 바꾼다.
  - 서브 클래스 쪽에서 반드시 재정의 해야 하는 메서드를 추상 메서드로 바꾼다. 

#### 작업 파일

- com.eomcs.util.List 클래스의 이름 변경
  - com.eomcs.util.AbstractList 로 변경한다.

### 2단계 - XxxHandler 의 의존 객체 타입을 `List` 에서 `AbstractList` 로 변경한다.

#### 작업 파일

- com.eomcs.pms.handler.BoardHandler 클래스 변경
- com.eomcs.pms.handler.MemberHandler 클래스 변경
- com.eomcs.pms.handler.ProjectHandler 클래스 변경
- com.eomcs.pms.handler.TaskHandler 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/AbstractList.java 변경
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
