# 21 - 큐 자료구조 구현과 활용

이번 훈련에서는 **큐(queue)** 방식으로 데이터를 저장하는 자료 구조를 만들어보자.

**큐(queue)** 는

- FIFO(First In First Out) 방식으로 데이터를 넣고 꺼낸다.
- 데이터를 넣는 것을 `offer`라고 하고 목록의 맨 끝에 추가한다.
- 데이터를 꺼내는 것을 `poll`이라 하고 목록의 맨 앞의 값을 꺼낸다.
- 보통 입력한 순으로 데이터를 꺼내야 하는 상황에서 이 자료구조를 사용한다.
- 예)
  - 등록된 예약을 처리할 때 
  - 네트워킹에서 연결된 순서대로 소켓을 승인하고 처리할 때


## 훈련 목표

- 큐(queue) 자료구조를 구현하고 구동 원리를 이해한다.
- Object.clone() 메서드의 용도와 인스턴스를 복제하는 방법을 배운다.
- 얕은 복제(shallow copy)와 깊은 복제(deep copy)의 차이점을 이해한다.

## 훈련 내용

- `java.util.Queue` 인터페이스의 메서드를 모방하여 `Queue` 클래스를 구현한다. 
- 큐를 이용하여 사용자가 입력한 명령을 보관한다.
- 사용자가 입력한 명령을 순서대로 출력하는 `history2` 명령을 추가한다.
  
## 실습

### 1단계 - `java.util.Queue` 인터페이스의 메서드를 모방하여 `Queue` 클래스를 구현한다. 

**큐(queue)** 자료 구조를 직접 구현해본다.

- `Queue` 클래스를 작성한다.

#### 작업 파일

- com.eomcs.util.Queue 클래스 생성


### 2단계 - 사용자가 입력한 명령을 스택에 보관한다. 

- `Queue` 객체를 준비하여 사용자가 명령어를 입력할 때 마다 저장한다.

#### 작업 파일

- com.eomcs.pms.App 클래스 변경


### 3단계 - 사용자가 입력한 명령을 최신순으로 출력하는 `history2` 명령을 추가한다. 

- 사용자가 입력한 명령을 최신순으로 출력하는 `printCommandHistory2()` 메서드를 정의한다.
- `history2` 명령을 처리하는 분기문을 추가한다.

```
명령> history2
/board/add
/board/add
/board/add
/board/list
/board/detail
:
/member/add
/member/add
/member/add
/member/list
/member/detail
:
/member/detail
/project/add
/project/list
history
history2
:q

명령> 

```

#### 작업 파일

- com.eomcs.pms.App 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/Queue.java 추가
- src/main/java/com/eomcs/pms/App.java 변경
