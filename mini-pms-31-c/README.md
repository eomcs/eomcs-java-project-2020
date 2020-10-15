# 31-c. 파일 입출력 API : 리팩터링 I

이번 훈련에서는,
- 좀 더 유지보수하기 좋은 구조로 소스 코드를 **리팩터링** 해보자.
- 객체 생성에 **팩토리 메서드(factory method) 패턴** 을 응용해보자.
- 객체지향 설계 기법 중에서 **GRASP 패턴** 의 하나인 **Information Expert** 를 적용해본다.

**리팩터링(refactoring)** 은, (마틴 파울러의 '리팩터링'에서 발췌)
- 소프트웨어를 보다 쉽게 이해할 수 있고, 적은 비용으로 수정할 수 있도록
  겉으로 보이는 동작의 변화 없이 내부 구조를 변경하는 것.
- 중복된 코드를 제거하여 설계(design)를 개선시킨다.
- 코드를 더 이해하기 쉽게 만든다.
- 버그를 찾기 쉽게 해준다.
- 프로그램을 빨리 작성하도록 도와준다.

**리팩터링** 을 해야할 때,
- 비슷한 코드를 중복해서 작성할 때
- 기능을 추가할 때
- 버그를 수정할 때
- 코드 리뷰(code review)를 수행할 때

**팩토리 메서드(factory method) 패턴** 은,
- new 명령을 사용하여 객체를 생성하기 보다는, 메서드를 통해 객체를 리턴 받는다.
- 인터페이스로 객체 생성 규칙을 정의하여 프로그래밍의 일관성을 제공한다.
- 또한 객체 생성의 책임을 인터페이스 구현체에게 떠넘긴다.

**GRASP 패턴**
- General Responsibility Assignment Software Patterns 의 약자이다.
- 객체지향 설계를 수행할 때 일반적으로 적용할 수 있는 설계 원칙이다.
- 객체에 책임(또는 역할)을 부여하는 9가지 원칙을 제안하고 있다.

**Information Expert 패턴**
- GRASP 설계 기법 중 하나이다.
- 데이터를 가지고 있는 객체에게 책임을 부여하는 설계 방식이다.
- 보통 데이터는 비공개로 감추고, 메서드를 통해 데이터를 노출하는 방식을 취한다.

## 훈련 목표

- *메서드 추출(extract method)* 리팩토링 기법을 연습한다.
- *메서드 이동(move method)* 리팩토링 기법을 연습한다.
- *팩토리 메서드(factory method) 패턴* 의 적용 방법을 연습한다.
- *Information Expert 패턴* 의 적용 방법을 연습한다.

## 훈련 내용

- **Information Expert 원칙**에 따라
  CSV 형식의 문자열을 다루는 코드를 valueOfCsv(), toCsvString() 메서드로 추출하여,
  그 값을 다루는 도메인 클래스에 둔다.
- CSV 데이터를 다룰 때는 도메인 클래스에 정의된 valueOfCsv()와 toCsvString() 메서드를 사용한다.

## 실습


### 1단계 - 코드에서 데이터를 CSV 형식의 문자열로 다루는 부분을 메서드로 추출하여 도메인 클래스에 정의한다.

- Board 클래스
  - CSV 문자열을 가지고 Board 객체를 생성하는 valueOfCsv() 를 추가한다.
  - Board 객체의 필드 값을 CSV 문자열을 리턴하는 toCsvString() 를 추가한다.
- Member 클래스
  - CSV 문자열을 가지고 Member 객체를 생성하는 valueOfCsv() 를 추가한다.
  - Member 객체의 필드 값을 CSV 문자열을 리턴하는 toCsvString() 를 추가한다.
- Project 클래스
  - CSV 문자열을 가지고 Project 객체를 생성하는 valueOfCsv() 를 추가한다.
  - Project 객체의 필드 값을 CSV 문자열을 리턴하는 toCsvString() 를 추가한다.
- Task 클래스
  - CSV 문자열을 가지고 Task 객체를 생성하는 valueOfCsv() 를 추가한다.
  - Task 객체의 필드 값을 CSV 문자열을 리턴하는 toCsvString() 를 추가한다.

#### 작업 파일

- com.eomcs.pms.domain.Board 변경
- com.eomcs.pms.domain.Member 변경
- com.eomcs.pms.domain.Project 변경
- com.eomcs.pms.domain.Task 변경


### 2단계 - 도메인 클래스에 정의한 메서드를 사용하여 CSV 데이터를 다룬다.

- App 클래스
  - loadBoards() 를 변경한다.
  - saveBoards() 를 변경한다.
  - loadMembers() 를 변경한다.
  - saveMembers() 를 변경한다.
  - loadProjects() 를 변경한다.
  - saveProjects() 를 변경한다.
  - loadTasks() 를 변경한다.
  - saveTasks() 를 변경한다.

#### 작업 파일

- com.eomcs.pms.App 변경


## 실습 결과
- src/main/java/com/eomcs/pms/domain/Board.java 변경
- src/main/java/com/eomcs/pms/domain/Member.java 변경
- src/main/java/com/eomcs/pms/domain/Project.java 변경
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
