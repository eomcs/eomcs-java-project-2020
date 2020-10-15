# 30-b. 파일 입출력 API : 데코레이터 객체 활용하기(DataInputStream/DataOutputStream)

이번 훈련에서는,
- **파일 입출력 API** 에 적용되어 있는 **데코레이터(decorator) 패턴** 을 활용하여
  객체에 기능을 덧붙여 사용하는 방법을 연습할 것이다.

**데코레이터 패턴** 은,
- 객체에 동적으로 새로운 서비스를 추가할 수 있게 해주는 설계 기법이다.
- 상속은 정적으로 기능을 확장하는 방식으로 서브 클래스에서 기능을 한 번 추가하면,
  그 자손 클래스들은 임의로 그 기능을 뺄 수 없다.
- 그에 반해 데코레이터 패턴으로 클래스를 설계하면, 플러그인 처럼 필요한 시점에 임의로 기능을 추가하고
  뺄 수 있어 프로그래밍이 편하다.
- *데코레이터 패턴* 이 적용된 대표적인 예가 **파일 입출력 API** 이다.   

**Data Processing Stream Class** 는,
- **Decorator 패턴** 에서 데코레이터 역할을 수행하는 클래스다.
- **Data Sink Stream Class**에 연결하거나 다른 데코레이터에 연결하여 중간에서 데이터를 가공하는 일을 한다.

**DataInputStream** / **DataOutputStream** 은,

- **Decorator** 역할을 하는 **Data Processing Stream Class** 이다.
- 문자열이나 자바 원시 타입의 값들 입출력하는 메서드를 구비하고 있다.
- 이 클래스를 입출력 스트림에 연결하면, 데이터를 읽고 쓰기 편하다.

## 훈련 목표

- **Data Processing Stream Class**의 역할을 이해한다.
- 데코레이터 패턴에서 각 객체의 역할을 이해한다.


## 훈련 내용

- FileInputStream/FileOutputStream 에 DataInputStream/DataOutputStream을 연결하여 데이터를 저장하고 읽는다.


## 실습


### 1단계 - 파일에 데이터를 쓰거나 파일에서 데이터를 읽을 때 DataOutputStream과 DataInputStream을 사용하여 처리한다.

- App 클래스
  - 기존의 `saveXxx()` 와 `loadXxx()`를 변경한다.

#### 작업 파일

- com.eomcs.pms.App 변경

## 실습 결과

- src/main/java/com/eomcs/pms/App.java 변경
