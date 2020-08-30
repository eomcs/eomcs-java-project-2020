# 20 - 스택 자료구조 구현과 활용

이번 훈련에서는 **스택(stack)** 방식으로 데이터를 저장하는 자료 구조를 만들어보자.

**스택(stack)** 은 
- LIFO(Last In First Out) 방식으로 데이터를 넣고 꺼낸다.
- 데이터를 넣는 것을 `push`라고 하고, 데이터를 꺼내는 것을 `pop`이라 한다.
- 보통 입력한 역순으로 데이터를 꺼내야 하는 상황에서 이 자료구조를 사용한다.
- 예)
  - JVM 스택 메모리 영역에서 메서드 호출을 관리할 때 
  - 웹 브라우저에서 이전 페이지로 따라 올라 갈 때
  - 자바스크립트에서 이벤트를 처리할 때 버블링 단계를 수행(부모 엘리먼트를 따라 올라가면서 처리하는 것)


## 훈련 목표

- 스택(stack) 자료구조를 구현하고 구동 원리를 이해한다.
- Object.clone() 메서드의 용도와 인스턴스를 복제하는 방법을 배운다.
- 얕은 복제(shallow copy)와 깊은 복제(deep copy)의 차이점을 이해한다.

## 훈련 내용

- `java.util.Stack` 을 모방하여 `Stack` 클래스를 구현한다. 
- 스택을 이용하여 사용자가 입력한 명령을 보관한다.
- 사용자가 입력한 명령을 최신순으로 출력하는 `history` 명령을 추가한다.
  
## 실습

### 1단계 - `java.util.Stack` 를 모방하여 `Stack` 클래스를 구현한다. 

**스택(stack)** 자료 구조를 직접 구현해본다.

- `Stack` 클래스를 작성한다.

#### 작업 파일

- com.eomcs.util.Stack 클래스 생성


### 2단계 - 사용자가 입력한 명령을 스택에 보관한다. 

- `Stack` 객체를 준비하여 사용자가 명령어를 입력할 때 마다 저장한다.

#### 작업 파일

- com.eomcs.pms.App 클래스 변경


### 3단계 - 사용자가 입력한 명령을 최신순으로 출력하는 `history` 명령을 추가한다. 

- 사용자가 입력한 명령을 최신순으로 출력하는 `printCommandHistory()` 메서드를 정의한다.
- `history` 명령을 처리하는 분기문을 추가한다.

```
명령> history
history
/board/detail
/member/list
/lesson/add
/lesson/list
:  <== 키보드에서 ‘q’가 아닌 다른 문자키를 누른다.
/board/add
/member/list
/member/list
/board/add
/board/add
:q  <== 키보드에서 ‘q’ 키를 누른다.
명령>

```

#### 작업 파일

- com.eomcs.pms.App 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/Stack.java 추가
- src/main/java/com/eomcs/pms/App.java 변경
