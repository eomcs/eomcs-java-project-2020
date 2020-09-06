# 26-a. 중첩 클래스 : 스태틱 중첩 클래스(static nested class)

이번 훈련에서는 **스태틱 중첩 클래스** 문법을 사용하여 `Iterator` 구현체를 만들어 볼 것이다.

현재 작성한 `Iterator` 구현체를 보면, 
- `ListIterator` 는 `AbstractList` 컬렉션에서만 사용된다. 
- `StackIterator` 는 `Stack` 컬렉션에서만 사용된다. 
- `QueueIterator` 는 `Queue` 컬렉션에서만 사용된다. 

이런 경우,

- `Iterator` 구현체가 사용되는 컬렉션 클래스 안에 두는 것이 유지보수에 더 좋다.
- 즉 사용되는 위치 가까이에 두는 것이 코드를 더 읽기 쉽게 하고 관리하기 편하게 만든다.

특히 `Iterator` 구현체가 컬렉션의 멤버를 사용하고 있는데,

- `Iterator` 구현체가 컬렉션의 멤버가 되면 컬렉션의 멤버에 바로 접근할 수 있어 목록 조회가 한결 편해진다.
 

이렇게 컬렉션의 목록을 조회하는 `Iterator` 구현체를 **중첩 클래스** 로 정의하면,

- 캡슐화를 통해 복잡한 구현 로직을 외부에 노출하지 않는 효과가 있다.
- 즉 외부에서는 `Iterator` 구현체를 직접 사용하지 않기 때문에,
  나중에 `Iterator` 구현체가 변경되더라도 영향을 받지 않는다.


**중첩 클래스(nested class)** 는 

- 다른 클래스 안에 정의된 클래스이다.
- `스태틱 중첩 클래스(static nested class)` 와 
  `논스태틱 중첩 클래스(non-static nested class)` 가 있다.
- `스태틱 중첩 클래스` 는 스태틱 멤버로 정의한 클래스다.
- `논스태틱 중첩 클래스` 스태틱 멤버가 아닌 중첩 클래스이다. 보통 `내부 클래스(inner class)`라 부른다.

**중첩 클래스** 의 용도와 특징은,

- 특정 클래스의 작업을 도와주는 작은 크기의 클래스를 정의할 때 주로 중첩 클래스로 정의한다.
- 클래스가 사용되는 곳에 위치하기 때문에 코드를 읽기 쉽고 관리하기가 쉽다. 
- 다른 클래스 안에 위치하기 때문에 캡슐화가 더 좋아진다. 
  캡슐화가 더 좋아진다는 것은, 
  복잡한 코드는 감추고 외부로부터의 접근은 줄이고 단순화시켜서
  코드를 더 관리하게 쉽게 만든다는 의미다.
  또한 바깥 클래스의 멤버에 대한 접근은 더 쉬워진다. 


**스태틱 중첩 클래스(static nested class)** 는

- 스태틱 멤버이기 때문에 인스턴스 멤버(필드나 메서드)에는 접근할 수 없다.
- 비록 다른 클래스 안에 있지만 일반 패키지 클래스(top-level class)처럼 사용할 수 있다.


**내부 클래스(inner class; non-static nested class)** 는 

- 인스턴스 멤버(필드나 메서드)처럼 사용한다.
- 그래서 바깥 클래스의 인스턴스 멤버를 직접 접근할 수 있다.
- 왜? 
  인스턴스 멤버이기 때문에 바깥 클래스의 인스턴스를 참조하는 `this` 내장 변수를 갖고 있다.
- 따라서 inner class 를 사용하려면 바깥 클래스의 인스턴스를 먼저 생성해야 한다.

**내부 클래스(inner class)** 의 또 다른 종류가 있는데,

- 메서드 안에 정의하는 `로컬 클래스(local class)` 와 
  이름 없이 정의하는 `익명 클래스(anonymous class)` 가 있다.


## 훈련 목표

- **스태틱 중첩 클래스** 를 만들고 사용하는 방법을 배운다.
- **스태틱 중첩 클래스** 의 용도와 이점을 이해한다.


## 훈련 내용

- `ListIterator` 구현체를 `AbstraceList` 클래스 안에 스태틱 중첩 클래스로 정의한다. 
- `StackIterator` 구현체를 `Stack` 클래스 안에 스태틱 중첩 클래스로 정의한다.
- `QueueIterator` 구현체를 `Queue` 클래스 안에 스태틱 중첩 클래스로 정의한다.


## 실습

### 1단계 - `ListIterator` 구현체를 스태틱 중첩 클래스로 정의한다. 

- `AbstractList` 클래스
  - `ListIterator` 구현체를 *스태틱 중첩 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.AbsractList 클래스 변경
- com.eomcs.util.ListIterator 클래스 삭제


### 2단계 - `StackIterator` 구현체를 스태틱 중첩 클래스로 정의한다. 

- `Stack` 클래스
  - `StackIterator` 구현체를 *스태틱 중첩 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.Stack 클래스 변경
- com.eomcs.util.StackIterator 클래스 삭제


### 3단계 - `QueueIterator` 구현체를 스태틱 중첩 클래스로 정의한다. 

- `Queue` 클래스
  - `QueueIterator` 구현체를 *스태틱 중첩 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.Queue 클래스 변경
- com.eomcs.util.QueueIterator 클래스 삭제


## 실습 결과

- src/main/java/com/eomcs/util/AbstractList.java 변경
- src/main/java/com/eomcs/util/Stack.java 변경
- src/main/java/com/eomcs/util/Queue.java 변경
- src/main/java/com/eomcs/util/ListIterator.java 삭제
- src/main/java/com/eomcs/util/StackIterator.java 삭제
- src/main/java/com/eomcs/util/QueueIterator.java 삭제
  