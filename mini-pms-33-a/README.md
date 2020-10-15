# 33-a. `Observer` 디자인 패턴 : 프로젝트에 적용하기

이번 훈련에서는,
- **Observer 디자인 패턴** 을 프로젝트에 적용하는 것을 연습할 것이다.

**Observer 디자인 패턴**은,
- 특정 객체의 상태 변화에 따라 수행해야 하는 작업이 있을 경우,
  기존 코드를 손대지 않고 손쉽게 기능을 추가하거나 제거할 수 있는 설계 기법이다.
- **발행(publish)/구독(subscribe) 모델** 이라고 부르기도 한다.
- 발행 측(publisher)에서는 구독 객체(subscriber)의 목록을 유지할 컬렉션을 가지고 있다.
- 또한 구독 객체를 등록하거나 제거하는 메서드가 있다.
- 구독 객체를 **리스너(listener)** 또는 **관찰자(observer)** 라 부르기도 한다.

## 훈련 목표
- `Observer` 디자인 패턴의 용도와 이점을 이해한다.
- Observer 디자인 패턴으로 구조를 바꾸는 것을 연습한다.

## 훈련 내용
- 인터페이스를 활용하여 옵저버 호출 규칙을 정의한다.
- 옵저버 구현체를 등록하고 제거하는 메서드와 컬렉션을 추가한다.
- 특정 상태가 되면 옵저버에게 통지하게 한다.


## 실습

### 1단계 - App 클래스의 스태틱 멤버(필드와 메서드)를 인스턴스 멤버로 전환한다.

- `App` 클래스 변경
  - 스태틱 필드와 스태틱 메서드를 인스턴스 필드와 인스턴스 메서드로 전환한다.
  - 보통 실무에서는 클래스의 일반적인 구조로 인스턴스 필드와 메서드를 사용한다.

#### 작업 파일
- com.eomcs.pms.App 변경
  - 백업: App01.java


### 2단계 - 애플리케이션을 시작하거나 종료할 때 실행할 옵저버의 메서드 호출 규칙을 정의한다.

- `ApplicationContextListener` 인터페이스 생성
  - Observer가 갖춰야 할 규칙을 정의한다.
  - 애플리케이션이 시작할 때 자동으로 호출할 메서드의 규칙을 정의한다.
  - 애플리케이션을 종료하기 전에 자동으로 호출할 메서드의 규칙을 정의한다.

#### 작업 파일
- com.eomcs.context.ApplicationContextListener 생성



### 3단계 - 옵저버를 저장할 컬렉션 객체와 옵저버를 추가하고 제거하는 메서드를 추가한다.

- `App` 클래스 변경
  - 옵저버를 보관할 컬렉션 객체를 추가한다.
  - 옵저버를 등록하는 메서드(`addApplicationContextListener()`)를 추가한다.
  - 옵저버를 제거하는 메서드(`removeApplicationContextListener()`)를 추가한다.

#### 작업 파일
- com.eomcs.pms.App 변경
  - 백업: App02.java

### 4단계 - 애플리케이션의 service() 실행 전/후에 옵저버에게 통지하는 코드를 추가한다.

- `App` 클래스 변경
  - 옵저버를 호출하는 메서드를 정의한다.
    - notifyApplicationContextListenerOnServiceStarted()
    - notifyApplicationContextListenerOnServiceStopped()
  - service() 메서드의 시작/종료 부분에 옵저버를 호출하는 메서드를 실행한다.

#### 작업 파일
- com.eomcs.pms.App 변경
  - 백업: App03.java


### 5단계 - 애플리케이션을 시작하고 종료할 때 간단한 안내 메시지를 출력하는 옵저버를 추가한다.

이번 단계에서는 옵저버 디자인 패턴을 적용한 후 그 사용법을 간단히 실험한다.

- AppInitListener 생성
  - ApplicationContextListener를 구현한 옵저버를 만든다.
  - 애플리케이션을 시작할 때 다음과 같이 간단한 안내 메시지를 출력한다.
    - "프로젝트 관리 시스템(PMS)에 오신 걸 환영합니다!"
  - 애플리케이션을 종료할 때 다음과 같이 간단한 안내 메시지를 출력한다.
    - "프로젝트 관리 시스템(PMS)을 종료합니다!"
- App 변경
  - service() 호출 전에 옵저버를 등록한다.

#### 작업 파일
- com.eomcs.pms.listener.AppInitListener 클래스 생성
- com.eomcs.pms.App 변경
  - 백업: App04.java

## 실습 결과
- src/main/java/com/eomcs/context/ApplicationContextListener.java 생성
- src/main/java/com/eomcs/pms/listener/AppInitListener.java 생성
- src/main/java/com/eomcs/pms/App.java 변경
