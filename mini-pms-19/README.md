# 19 - 배열 대신 연결 리스트 자료구조 사용하기

이번 훈련에서는 **연결 리스트(linked list)** 방식으로 데이터를 저장하는 자료 구조를 만들어보자.

**연결 리스트** 는 
- *노드(node)* 를 이용해 데이터와 데이터를 연결하는 방식으로 데이터 목록을 관리한다.
- 각각의 노드는 데이터와 다음 노드의 주소를 갖고 있다.
- 배열과 달리 데이터를 추가할 때 마다 노드를 늘리는 방식이기 때문에 메모리를 효율적으로 사용한다.
- 노드와 노드를 연결하는 방식이기 때문에 데이터의 삽입, 삭제가 빠르다.
- 배열의 비해 데이터 조회 속도는 느리다. 
  배열의 경우 인덱스를 통해 바로 데이터를 찾을 수 있지만, 
  연결 리스트에서는 노드의 연결 고리를 따라가야 하기 때문에 조회 속도가 느리다.
- 데이터의 삽입, 삭제가 잦고 데이터가 지속적으로 추가되는 경우 
  배열 방식 보다는 연결 리스트 방식이 낫다.
  
## 훈련 목표

- 연결 리스트 구현을 통해 연결 리스트 자료 구조의 구동 원리를 이해한다.
- 배열 방식과 연결 리스트 방식의 장단점을 이해한다.
- 또한 레퍼런스를 이용하여 객체를 다루는 것을 연습한다.
- 중첩 클래스의 활용법을 연습한다.
- 자바에서 제공하는 `java.util.LinkedList` 클래스의 이해도를 높인다.

## 훈련 내용

- `java.util.LinkedList` 를 모방하여 `LinkedList` 를 구현한다. 
- 기존의 XxxHandler 클래스에서 사용하는 `ArrayList` 를 `LinkeList` 로 교체한다.
  
## 실습

### 1단계 - `java.util.LinkedList` 를 모방하여 `LinkedList` 클래스를 구현한다. 

**연결 리스트(linked list)** 자료 구조를 직접 구현해본다.

- `LinkedList` 클래스를 작성한다.

#### 작업 파일

- com.eomcs.util.LinkedList 클래스 생성


### 2단계 - `ArrayList` 를 사용하는 부분을 `LinkedList` 를 사용하도록 변경한다.

- XxxHandler 에서 `ArrayList` 대신 `LinkedList` 를 사용하여 데이터를 관리한다.  

#### 작업 파일

- com.eomcs.pms.handler.BoardHandler 클래스 변경
- com.eomcs.pms.handler.MemberHandler 클래스 변경
- com.eomcs.pms.handler.ProjectHandler 클래스 변경
- com.eomcs.pms.handler.TaskHandler 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/util/LinkedList.java 추가
- src/main/java/com/eomcs/pms/handler/BoardHandler.java 변경
- src/main/java/com/eomcs/pms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
