# 23_1 - 일반화(generalization) 상속 기법을 사용하여 ArrayList와 LinkedList의 공통점 분리하기

## 학습 목표

- 상속 기법 중 일반화 기법을 수행할 수 있다.
- 클래스의 공통점을 유지보수 하기 쉽게 분리하기 위해 상속을 이용할 수 있다.
- 다형적 변수를 이용하여 다양한 서브 타입 객체를 다룰 수 있다.
- 의존성 주입(DI) 기법을 이용하여 객체를 부품처럼 교체하기 쉽도록 만들 수 있다.

### 주요 개념

- 일반화(Generalization)
  - 서브 클래스의 공통 분모를 추출하여 수퍼 클래스로 정의하고 상속 관계를 맺는 것.
- 다형적 변수(Polymorphic Variables)
  - Handler에서 사용할 목록 관리 객체를 수퍼 클래스의 레퍼런스로 선언하는 것.
  - 이를 통해 List의 서브 객체로 교체하기 쉽도록 만드는 것.
- 의존성 주입(DI; Dependency Injection)
  - Handler가 의존하는 객체를 내부에서 생성하지 않고 생성자를 통해 외부에서 주입 받는 것.
  - 이를 통해 의존 객체 교체가 쉽도록 만드는 것.

## 실습 소스 및 결과

- src/main/java/com/eomcs/util/List.java 추가
- src/main/java/com/eomcs/util/ArrayList.java 변경
- src/main/java/com/eomcs/util/LinkedList.java 변경
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/lms/App.java 변경

## 실습

### 훈련1. 데이터 목록을 다루는 ArrayList와 LinkedList의 공통점을 찾아 별도의 클래스로 분리하라.

- List.java
    - ArrayList와 LinkedList의 공통 멤버를(필드와 메서드)를 선언한다.
    - 서브 클래스에서 반드시 재정의 해야 하는 메서드는 추상 메서드로 구현하지 않고 놓아 둔다. 
- ArrayList.java
    - `AbstractList`를 상속 받는다.
    - 상속 받은 추상 메서드를 구현한다.
- LinkedList.java
    - `AbstractList`를 상속 받는다.
    - 상속 받은 추상 메서드를 구현한다.
- LessonHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 추상클래스를 필드로 선언한다.
    - 생성자에서 구체적으로 구현한 서브 클래스를 공급받도록 변경한다.
    - 따라서 특정 클래스(ArrayList나 LinkedList)에 의존하는 코드를 제거한다.
- MemberHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 추상클래스를 필드로 선언한다.
    - 생성자에서 구체적으로 구현한 서브 클래스를 공급받도록 변경한다.
    - 따라서 특정 클래스(ArrayList나 LinkedList)에 의존하는 코드를 제거한다.
- BoardHandler.java
    - ArrayList 또는 LinkedList를 직접 지정하는 대신에 추상클래스를 필드로 선언한다.
    - 생성자에서 구체적으로 구현한 서브 클래스를 공급받도록 변경한다.
    - 따라서 특정 클래스(ArrayList나 LinkedList)에 의존하는 코드를 제거한다.
- App.java
    - XxxHandler가 사용할 의존 객체(AbstractList 객체)를 준비한다.
    - XxxHandler를 생성할 때 해당 의존 객체를 넘겨준다.
