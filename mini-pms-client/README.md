# 40-c. 커맨드 실행 전/후에 기능 추가하기: init() 와 destroy()의 필요성

이번 훈련에서는,
- **Chain of Responsibility 디자인 패턴** 을 응용하는 방법을 배울 것이다.

**Chain of Responsibility 패턴** 은,
- 작업 요청을 받은 객체(sender)가 실제 작업자(receiver)에게 그 책임을 위임하는 구조에서 사용하는 설계 기법이다.
- 작업자 간에 연결 고리를 구축하여 작업을 나누어 처리할 수 있다.
- 체인 방식이기 때문에 작업에 참여하는 모든 객체가 서로 알 필요가 없다.
- 오직 자신과 연결된 다음 작업자만 알면 되기 때문에 객체 간에 결합도를 낮추는 효과가 있다.
- 런타임에서 연결 고리를 변경하거나 추가할 수 있어, 상황에 따라 실시간으로 기능을 추가하거나 삭제할 수 있다.
- 보통 필터링을 구현할 때 이 설계 기법을 많이 사용한다.

## 훈련 목표
- 객체를 사용하기 전/후에 자원을 준비시키고 해제시키는 방법을 배운다.

## 훈련 내용
- **CommandFilter** 를 사용하기 전에 자원을 준비시키는 방법을 제공한다.
- **CommandFilter** 를 사용 완료 후 자원을 해제시키는 방법을 제공한다.

## 실습

### 1단계 - `CommandFilter` 인터페이스 에 초기화와 마무리에 관련된 규칙을 추가한다.

- com.eomcs.pms.filter.CommandFilter 변경
  - init() 규칙 추가
    - 필터가 사용되기 전에 호출된다.
    - 필터가 사용할 자원을 준비시키는 코드를 둔다.
  - destroy() 규칙 추가
    - 필터 사용을 마친 후 종료되기 전에 호출된다.
    - 필터가 준비한 자원을 해제시키는 코드를 둔다.


### 2단계 - `LogCommandFilter` 에서 시스템이 종료될 때 파일을 닫는다.

- com.eomcs.pms.filter.LogCommandFilter 변경
  - destroy() 메서드를 구현한다.
  - 열려있는 파일을 닫는다.

### 3단계 - 필터 관리자가 보유한 각 필터에 대해 초기화시키고 마무리시키는 기능을 추가한다.

- com.eomcs.pms.filter.CommandFilterManager 변경
  - init() 추가 : 각 필터에 대해 init() 호출
  - destory() 추가 : 각 필터에 대해 destroy() 호출
- com.eomcs.pms.App 변경
  - 필터를 사용하기 전에 필터 관리자의 init()를 호출하여 필터를 초기화시킨다.
  - 필터 사용을 끝낸 후에는 필터 관리자의 destroy()를 호출하여 필터를 마무리시킨다.



## 실습 결과
- src/main/java/com/eomcs/pms/filter/CommandFilter.java 변경
- src/main/java/com/eomcs/pms/filter/LogCommandFilter.java 변경
- src/main/java/com/eomcs/pms/filter/CommandFilterManager.java 변경
- src/main/java/com/eomcs/pms/App.java 변경