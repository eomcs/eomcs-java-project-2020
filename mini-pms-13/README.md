# 13. 생성자가 필요한 이유 : 의존 객체 주입(Dependency Injection; DI)

**생성자(constructor)** 는 인스턴스를 사용하는데 문제가 없도록,

- 인스턴스 필드를 **유효한 값** 으로 초기화시키는 일을 한다.
- 필요하다면 **의존 객체** 를 주입하는 일을 한다.


**의존 객체(dependency object)** 란,

- 작업을 수행하기 위해 사용하는 객체다.
- 즉 작업할 때 의존하는 객체라고 해서 **의존 객체** 라 부른다.

이번 훈련에서는 *생성자* 를 사용하지 않는 경우의 문제점을 확인하고,
*생성자* 를 적용해야 하는 상황을 경험해보자.

## 훈련 목표

- 인스턴스의 필드를 유효한 값으로 초기화시키지 않을 때 문제점을 확인한다.
- 생성자의 용도를 이해한다.
- 생성자를 이용하여 인스턴스를 사용하기 전에 필요한 값들을 준비한다.

## 훈련 내용

- 생성자를 통해 의존 객체를 강제로 주입하게 만든다.
- `ProjectHandler` 와 `TaskHandler` 에서 사용할 `MemberHandler` 를 강제로 주입하게 한다.


## 실습

### 1단계 - 의존 객체를 제대로 주입하지 않았을 때 발생되는 문제를 확인한다.

- `App` 클래스에서 `ProjectHandler` 객체를 생성할 때,
  의존 객체인 `MemberHandler` 를 설정하는 코드를 주석으로 처리한다.
- `App` 클래스에서 `TaskHandler` 객체를 생성할 때,
  의존 객체인 `MemberHandler` 를 설정하는 코드를 주석으로 처리한다.
- *프로젝트 등록* 과 *작업 등록* 을 테스트하여 문제점을 확인한다.   

#### 작업 파일

- com.eomcs.pms.App 클래스 변경
  - 백업: App_a.java


### 2단계 - 생성자를 이용하여 의존 객체를 강제로 주입하게 한다.

- `ProjectHandler` 의 인스턴스를 생성할 때 의존 객체를 반드시 주입하도록
   기본 생성자 대신에 의존 객체를 파라미터로 받는 생성자를 추가한다.
- `TaskHandler` 의 인스턴스를 생성할 때 의존 객체를 반드시 주입하도록
   기본 생성자 대신에 의존 객체를 파라미터로 받는 생성자를 추가한다.   
- `App` 클래스에서 `ProjectHandler` 객체를 생성할 때,
  생성자 파라미터에 의존 객체를 넘겨준다.
- `App` 클래스에서 `TaskHandler` 객체를 생성할 때,
  생성자 파라미터에 의존 객체를 넘겨준다.

#### 작업 파일

- com.eomcs.pms.handler.ProjectHandler 클래스 변경
- com.eomcs.pms.handler.TaskHandler 클래스 변경
- com.eomcs.pms.App 클래스 변경


## 실습 결과

- src/main/java/com/eomcs/pms/handler/ProjectHandler.java 변경
- src/main/java/com/eomcs/pms/handler/TaskHandler.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
