# 26-d. 중첩 클래스 : 익명 클래스(anonymous class)

이번 훈련에서는 **익명 클래스** 문법을 사용하여 `Iterator` 구현체를 만들어 볼 것이다.

**익명 클래스(local class)** 는, 

- 이름이 없는 클래스다.  
- 이름이 없기에 클래스를 정의하자 마자 바로 인스턴스를 생성해야 한다.
- 일반적으로 인스턴스를 딱 한 개만 만들 때 익명 클래스로 정의한다.

## 훈련 목표

- **익명 클래스** 를 만들고 사용하는 방법을 배운다.
- **익명 클래스** 의 용도와 이점을 이해한다.


## 훈련 내용

- `ListIterator` 구현체를 익명 클래스로 정의한다. 
- `StackIterator` 구현체를 익명 클래스로 정의한다.
- `QueueIterator` 구현체를 익명 클래스로 정의한다.


## 실습

### 1단계 - `ListIterator` 구현체를 iterator() 메서드에서 익명 클래스로 정의한다

- `AbstractList` 클래스
  - `ListIterator` 구현체를 iterator()의 *익명 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.AbsractList 클래스 변경


### 2단계 - `StackIterator` 구현체를 iterator() 메서드에서 익명 클래스로 정의한다

- `Stack` 클래스
  - `StackIterator` 구현체를 iterator()의 *익명 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.Stack 클래스 변경


### 3단계 - `QueueIterator` 구현체를 iterator() 메서드에서 익명 클래스로 정의한다

- `Queue` 클래스
  - `QueueIterator` 구현체를 iterator()의 *익명 클래스* 로 정의한다. 

#### 작업 파일

- com.eomcs.util.Queue 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/AbstractList.java 변경
- src/main/java/com/eomcs/util/Stack.java 변경
- src/main/java/com/eomcs/util/Queue.java 변경
  