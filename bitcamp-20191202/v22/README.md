# 22 - 큐 자료구조 구현과 활용

## 학습 목표

- 큐(queue) 자료구조를 구현할 수 있다.
- 큐의 구동원리를 이해하고 활용할 수 있다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/util/Queue.java 추가
- src/main/java/com/eomcs/lms/App.java 변경

## 실습

### 훈련1. 큐 자료구조를 구현하라.

- Queue.java
    - 제네릭을 적용한다.
    - 객체 복제가 가능하도록 Cloneable 인터페이스를 구현한다.


### 훈련2. 사용자가 입력한 명령어를 큐에 보관하라.

- App.java
    - 사용자가 입력한 명령어를 큐에 보관한다.


### 훈련3. 사용자가 입력한 명령을 입력한 순서로 출력하는 `history2` 명령을 추가하라.

- App.java
    - 명령어 내역을 출력하는 printCommandHistory2()를 정의한다.
    - `history2` 명령을 처리하는 분기문을 추가한다.

#### 실행 결과

```
명령> history2
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
