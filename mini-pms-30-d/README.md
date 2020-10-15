# 30-d. 파일 입출력 API : 객체 읽고 쓰기(ObjectInputStream/ObjectOutputStream)

이번 훈련에서는 **ObjectInputStream/ObjectOutputStream** 을 활용하여 객체를 직렬화하고 복원하는 것을 연습해 볼 것이다.

**직렬화(serialize; marshaling)** 은,

- 인스턴스의 필드 값을 바이트 배열로 변환하여 출력하는 것이다.
- 클래스 정보 및 필드의 타입 정보도 포함한다.

**복원(deserialize; unmarshaling)** 은,

- 직렬화를 통해 저장된 바이트 배열을 자바 객체로 변환하는 것이다.
- 클래스 정보와 필드 정보를 바탕으로 인스턴스를 자동 생성한다.
- 생성자 호출 없이 인스턴스의 필드 값을 설정한다.

## 훈련 목표

- **ObjectInputStream/ObjectOutputStream**의 동작 원리를 이해한다.
- 직렬화를 수행하기 위해 필요한 조건을 이해한다.


## 훈련 내용

- DataInputStream/DataOutputStream 대신에 ObjectInputStream/ObjectOutputStream을 사용하여 객체를 저장한다.


## 실습


### 1단계 - 자바 도메인 클래스를 직렬화 할 수 있도록 설정한다.

- Board, Member, Project, Task 클래스
  - `java.io.Serializable` 인터페이스를 구현한다.
  - `private static final int serialVersionUID` 값을 설정한다.

#### 작업 파일

- com.eomcs.pms.domain.Board 변경
- com.eomcs.pms.domain.Member 변경
- com.eomcs.pms.domain.Project 변경
- com.eomcs.pms.domain.Task 변경

### 2단계 - DataInputStream/DataOutputStream 을 ObjectInputStream/ObjectOutputStream 으로 교체하여 객체를 저장하고 읽는다.

- App 클래스
  - `saveXxx()` 와 `loadXxx()` 메서드를 변경한다.
  - `DataInputStream` / `DataOutputStream` 을 사용하는 대신에 `ObjectInputStream` / `ObjectOutputStream` 을 사용하여 객체를 저장하고 읽는다.

#### 작업 파일

- com.eomcs.pms.App 변경

## 실습 결과

- src/main/java/com/eomcs/pms/domain/Board.java 변경
- src/main/java/com/eomcs/pms/domain/Member.java 변경
- src/main/java/com/eomcs/pms/domain/Project.java 변경
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
