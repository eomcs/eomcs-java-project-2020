# 26-b. 중첩 클래스 : 논스태틱 중첩 클래스(non-static nested class; inner class)

이번 훈련에서는 **논스태틱 중첩 클래스** 문법을 사용하여 `Iterator` 구현체를 만들어 볼 것이다.

**내부 클래스(inner class; non-static nested class)** 는, 

- 바깥 클래스의 인스턴스 멤버이기 때문에   
  바깥 클래스의 다른 인스턴스 멤버(필드나 메서드)에 직접 접근할 수 있다.
- 따라서 바깥 클래스의 멤버를 사용하기 위해 내부 클래스의 객체를 생성할 때 
  생성자 파라미터로 바깥 클래스의 인스턴스를 받을 필요가 없다.

## 훈련 목표

- **논스태틱 중첩 클래스** 를 만들고 사용하는 방법을 배운다.
- **논스태틱 중첩 클래스** 의 용도와 이점을 이해한다.


## 훈련 내용

- `ListIterator` 구현체를 논스태틱 중첩 클래스로 정의한다. 
- `StackIterator` 구현체를 논스태틱 중첩 클래스로 정의한다.
- `QueueIterator` 구현체를 논스태틱 중첩 클래스로 정의한다.


## 실습

### 1단계 - `ListIterator` 구현체를 논스태틱 중첩 클래스로 정의한다. 

- `AbstractList` 클래스
  - `ListIterator` 구현체를 *논스태틱 중첩 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.AbsractList 클래스 변경


### 2단계 - `StackIterator` 구현체를 논스태틱 중첩 클래스로 정의한다. 

- `Stack` 클래스
  - `StackIterator` 구현체를 *논스태틱 중첩 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.Stack 클래스 변경


### 3단계 - `QueueIterator` 구현체를 논스태틱 중첩 클래스로 정의한다. 

- `Queue` 클래스
  - `QueueIterator` 구현체를 *논스태틱 중첩 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.Queue 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/AbstractList.java 변경
- src/main/java/com/eomcs/util/Stack.java 변경
- src/main/java/com/eomcs/util/Queue.java 변경
  