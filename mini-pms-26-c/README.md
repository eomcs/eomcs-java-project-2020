# 26-c. 중첩 클래스 : 로컬 클래스(local class)

이번 훈련에서는 **로컬 클래스** 문법을 사용하여 `Iterator` 구현체를 만들어 볼 것이다.

**로컬 클래스(local class)** 는, 

- 메서드의 로컬 변수와 사용법이 유사하다.  
- 특정 메서드에서만 사용할 클래스라면 그 메서드 내부에 정의하는 것이 
  코드를 읽기 쉽고 유지보수 하기 쉽게 만든다.
- 로컬 클래스도 바깥 클래스의 멤버에 직접 접근할 수 있다. 
- 인스턴스 메서드는 this 라는 내장 변수에 인스턴스 주소를 담고 있다.
- 인스턴스 메서드에 정의된 로컬 클래스에서도 이 this 변수를 사용할 수 있다.

## 훈련 목표

- **로컬 클래스** 를 만들고 사용하는 방법을 배운다.
- **로컬 클래스** 의 용도와 이점을 이해한다.


## 훈련 내용

- `ListIterator` 구현체를 로컬 클래스로 정의한다. 
- `StackIterator` 구현체를 로컬 클래스로 정의한다.
- `QueueIterator` 구현체를 로컬 클래스로 정의한다.


## 실습

### 1단계 - `ListIterator` 구현체를 iterator() 메서드 안에 정의한다. 

- `AbstractList` 클래스
  - `ListIterator` 구현체를 iterator()의 *로컬 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.AbsractList 클래스 변경


### 2단계 - `StackIterator` 구현체를 iterator() 메서드 안에 정의한다. 

- `Stack` 클래스
  - `StackIterator` 구현체를 *로컬 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.Stack 클래스 변경


### 3단계 - `QueueIterator` 구현체를 iterator() 메서드 안에 정의한다. 

- `Queue` 클래스
  - `QueueIterator` 구현체를 *로컬 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.Queue 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/AbstractList.java 변경
- src/main/java/com/eomcs/util/Stack.java 변경
- src/main/java/com/eomcs/util/Queue.java 변경
  