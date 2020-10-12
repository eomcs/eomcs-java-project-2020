# 33. `Observer` 디자인 패턴을 적용하여 클래스 구조를 변경

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

- App 클래스 변경
  - 스태틱 필드와 스태틱 메서드를 인스턴스 필드와 인스턴스 메서드로 전환한다.
  - 보통 실무에서는 클래스의 일반적인 구조로 인스턴스 필드와 메서드를 사용한다.

#### 작업 파일
- com.eomcs.pms.App 변경
  - 백업: App01.java





### 2단계 - 데이터를 파일에 저장할 때 JSON 형식으로 출력한다.

- App 클래스 변경
  - CSV 형식 대신에 JSON 형식으로 출력하도록 saveObjects() 메서드를 변경한다.
- 도메인 클래스 변경
  - 도메인 클래스는 더이상 CsvObject 인터페이스를 구현할 필요가 없다.
  - toCsvString() 메서드를 제거한다.
  - 또한 CSV 문자열로 객체를 생성하는 valueOfCsv() 메서드도 제거한다.
  - CSV 문자열을 파라미터로 받아 인스턴스 필드를 초기화시키는 생성자도 제거한다.
- CsvObject 인터페이스 삭제
  - 객체를 JSON 문자열로 변환하여 저장하기 때문에 이 인터페이스를 삭제한다.

#### 작업 파일
- com.eomcs.pms.util.CsvObject 삭제
- com.eomcs.pms.domain.Board 변경
- com.eomcs.pms.domain.Member 변경
- com.eomcs.pms.domain.Project 변경
- com.eomcs.pms.domain.Task 변경
- com.eomcs.pms.App 변경


### 3단계 - 파일로부터 JSON 형식의 텍스트를 읽어서 객체로 변환하여 `List` 객체에 저장한다.

- App 변경
  - CSV 형식 대신에 JSON 형식으로 파일에 저장된 데이터를 읽어서 객체로 변환한 다음
    `List` 객체에 저장하도록 saveObjects() 메서드를 변경한다.
- 도메인 클래스 변경
  - CSV 문자열로 객체를 생성하는 valueOfCsv() 메서드를 제거한다.
  - CSV 문자열을 파라미터로 받아 인스턴스 필드를 초기화시키는 생성자도 제거한다.
- ObjectFactory 삭제
  - Gson 객체를 이용하여 JSON 문자열을 객체로 벼환할 것이기 때문에 더이상 이 인터페이스는 필요 없다. 

#### 작업 파일
- com.eomcs.pms.domain.Board 변경
- com.eomcs.pms.domain.Member 변경
- com.eomcs.pms.domain.Project 변경
- com.eomcs.pms.domain.Task 변경
- com.eomcs.pms.App 변경
- com.eomcs.pms.util.ObjectFactory 삭제


### 4단계 - `Arrays.asList()` 를 사용하여 배열을 데이터 목록에 바로 추가한다.

- App 변경
  - loadObjects() 메서드를 변경한다.
  - `Arrays.asList()` 를 사용하면 배열을 `List` 구현체로 만들 수 있다.
  - `List.addAll()` 을 이용하면 `List` 객체를 통째로 추가할 수 있다.
  - 반복문을 사용하는 것 보다 간결하다.

#### 작업 파일
- com.eomcs.pms.App 변경


## 실습 결과
- build.gradle 변경
- src/main/java/com/eomcs/pms/App.java 변경