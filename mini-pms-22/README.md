# 22 - 상속 : 일반화(generalization)를 이용한 공통점 분리

**상속(inheritance)** 을 구현하는 방법에는 두 가지가 있다. 

- **전문화(specialization)** 와 **일반화(generalization)** 이다.

**전문화** 는

- 기존 클래스의 기능을 그대로 활용할 수 있도록 연결하고
- 여기에 새 기능을 추가하거나 기존 기능을 변경하여 좀 더 특수 목적의 서브 클래스를 만드는 기법이다.
- 마치 부모로부터 무언가를 물려 받는 것과 같아서 **상속** 이라는 문법의 대표적인 기법으로 알려져 있다.
- 그래서 객체지향 프로그래밍의 상속을 얘기할 때는 대부분 **전문화** 를 가르키는 말이다.

**일반화** 는

- 클래스들의 공통 분모를 추출하여 수퍼 클래스를 정의하는 기법이다.
- 그리고 새로 정의한 수퍼 클래스와 부모/자식 관계를 맺는다.
- 프로그래밍 처음부터 상속을 고려하여 수퍼 클래스를 정의하는 것이 아니라 
  코드를 리팩토링하는 과정에서 수퍼 클래스를 정의하는 것이기 때문에 초보 개발자에게 적합하다.
- 보통 일반화를 통해 추출된 수퍼 클래스는 서브 클래스에게 공통 분모를 상속해주는 것이 목적이다.
- 직접 인스턴스를 생성하고 사용하기 위해 만든 클래스가 아니다.
- 그래서 일반화를 통해 도출한 수퍼 클래스는 보통 추상 클래스로 정의한다.

이번 훈련에서는 이런 **상속(inheritance)** 의 기법 중에서 **일반화(generalization)** 기법을 연습할 것이다. 


## 훈련 목표

- 상속의 기법에서 **전문화** 와 **일반화** 기법을 이해하고 구현하는 방법을 배운다.
- **추상 클래스** 의 용도를 이해하고 활용법을 연습한다.
- **다형적 변수(ploymorphic variables)** 를 이용하여 서브 클래스의 인스턴스를 다루는 것을 연습한다.
- **의존성 주입(dependency injection; DI)** 의 의미를 이해한다.

## 훈련 내용

- `ArrayList`, `LinkedList` 의 공통 분모를 추출하여 수퍼 클래스를 정의한다.
- XxxHandler 에서 사용할 List 객체를 외부에서 주입 받는 방식으로 변경한다.  

### 주요 개념

- 일반화(Generalization)
  - 서브 클래스의 공통 분모를 추출하여 수퍼 클래스로 정의하고 상속 관계를 맺는 것.
- 다형적 변수(Polymorphic Variables)
  - Handler에서 사용할 목록 관리 객체를 수퍼 클래스의 레퍼런스로 선언하는 것.
  - 이를 통해 List의 서브 객체로 교체하기 쉽도록 만드는 것.
- 의존성 주입(DI; Dependency Injection)
  - Handler가 의존하는 객체를 내부에서 생성하지 않고 생성자를 통해 외부에서 주입 받는 것.
  - 이를 통해 의존 객체 교체가 쉽도록 만드는 것.

## 실습

### 1단계 - `ArrayList` 와 `LinkedList` 에 대해 *일반화* 를 수행한다. 

- `List` 클래스
    - `ArrayList` 와 `LinkedList` 의 공통 멤버를(필드와 메서드)를 선언한다.
    - 서브 클래스에서 재정의 해야 하는 메서드는 몸체가 빈 상태로 정의한다. 
- `ArrayList` 클래스
    - `List` 를 상속 받는다.
    - 상속 받은 메서드를 오버라이딩 한다.
- `LinkedList` 클래스
    - `List` 를 상속 받는다.
    - 상속 받은 메서드를 오버라이딩 한다.

#### 작업 파일

- com.eomcs.util.List 추상 클래스 정의
- com.eomcs.util.ArrayList 클래스 변경
- com.eomcs.util.LinkedList 클래스 변경

### 2단계 - XxxHandler 의 의존 객체를 외부에서 주입 받는다.

- `BoardHandler`, `MemberHandler`, `ProjectHandler`, `TaskHandler` 클래스
  - 다형적 변수의 특징을 이용하여 `ArrayList` 또는 `LinkedList` 객체를 모두 담을 수 있도록 
    `List` 타입의 필드로 선언한다.
  - 생성자의 파라미터를 통해 `List` 클래스의 서브 클래스를 공급받도록 변경한다.
  - 이를 통해 특정 클래스 `ArrayList` 나 `LinkedList` 에 대한 의존성이 제거된다.


#### 작업 파일

- com.eomcs.pms.handler.BoardHandler 클래스 변경
- com.eomcs.pms.handler.MemberHandler 클래스 변경
- com.eomcs.pms.handler.ProjectHandler 클래스 변경
- com.eomcs.pms.handler.TaskHandler 클래스 변경


### 3단계 - `App` 에서 XxxHandler의 의존 객체를 주입한다.

- `App` 클래스
    - XxxHandler가 사용할 의존 객체(`List` 의 서브 클래스)를 준비한다.
    - XxxHandler를 생성할 때 해당 의존 객체를 넘겨준다.

#### 작업 파일

- com.eomcs.pms.App 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/List.java 추가
- src/main/java/com/eomcs/util/ArraytList.java 변경
- src/main/java/com/eomcs/util/LinkedList.java 변경
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
