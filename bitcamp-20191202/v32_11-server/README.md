# 32_11 - 프록시 패턴을 적용하여 서버에 요청하는 부분을 간결하기 만들기 

## 학습목표

- 프록시 패턴의 구조와 실행 원리를 이해한다.
- 프록시 패턴의 사용 목적을 이해한다.
- 프록시 패턴을 적용할 수 있고 사용할 수 있다.

### 프록시

- 실제 일을 하는 객체와 동일하게 인터페이스를 구현한다.
- 프록시 객체의 역할은 실제 작업 객체의 사용을 간결하게 만드는 것이다.
- 따라서 프록시 객체의 메서드를 호출하면 프록시 객체는 실제 작업을 하는 객체에 위임한다.
- 프록시 객체는 작업 객체를 제공하는 쪽에서 정의해야 한다.
- 작업 객체가 필요한 쪽에서는 프록시 객체를 통해 작업을 수행한다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/proxy 패키지 생성
- src/main/java/com/eomcs/lms/dao/proxy/BoardDaoProxy.java 추가
- src/main/java/com/eomcs/lms/dao/proxy/LessonDaoProxy.java 추가
- src/main/java/com/eomcs/lms/dao/proxy/MemberDaoProxy.java 추가

## 실습  

### 훈련 1: BoardDao의 사용법을 캡슐화하라.

- com.eomcs.lms.dao.proxy 패키지를 생성한다.
- com.eomcs.lms.dao.proxy.BoardDaoProxy 클래스를 정의한다.
 
### 훈련 2: LessonDao의 사용법을 캡슐화하라.

- com.eomcs.lms.dao.proxy.LessonDaoProxy 클래스를 정의한다.

### 훈련 3: MemberDao의 사용법을 캡슐화하라.

- com.eomcs.lms.dao.proxy.MemberDaoProxy 클래스를 정의한다.
  
### 훈련 4: 프록시 객체를 Client 프로젝트에 가져간 후 실행을 테스트하라.