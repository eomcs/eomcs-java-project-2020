# 40-c. 커맨드 실행 전/후에 기능 추가하기: init() 와 destroy()의 필요성

이번 훈련에서는,
- **Chain of Responsibility 디자인 패턴** 을 응용하는 방법을 배울 것이다.

## 훈련 목표
-

## 훈련 내용
-

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
