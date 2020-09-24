# 31-d. 파일 입출력 API를 활용하여 데이터를 읽고 쓰기 II : 리팩터링 II

이번 훈련에서는,
- **인터페이스** 를 활용하여 코드를 통합하는 것을 연습할 것이다.
- **제네릭** 을 활용하여 범용 메서드를 만드는 방법을 연습할 것이다.
- **메서드 레퍼런스** 를 활용하여 인터페이스를 구현하는 방법을 연습할 것이다.
- **생성자 레퍼런스** 를 활용하여 인터페이스를 구현하는 방법을 연습할 것이다.

**인터페이스(interface)** 는,
- 객체의 사용 규칙을 정의하는 문법이다.
- 즉 객체에 대해 메서드 호출 규칙을 정의한다.

**인터페이스** 의 이점은,
- 객체를 사용하는 측(client)의 코드 작성과 피사용측 코드 작성을 분리할 수 있다.
- 즉 코드를 작성하는 개발자들이 서로 영향을 끼치지 않고 프로그래밍을 할 수 있다.  
- 특정 클래스에 종속되지 않기 때문에 구현이 더 자유롭고 객체를 대체하기가 더 쉽다.


## 훈련 목표
- *인터페이스* 의 활용을 연습한다.
- *제네릭* 을 메서드에 적용하는 것을 연습한다.
- *메서드 레퍼런스* 를 이용하여 인터페이스 구현체를 파라미터로 전달하고 사용하는 방법을 연습한다.
- *생성자 레퍼런스* 를 이용하여 인터페이스 구현체를 파라미터로 전달하고 사용하는 방법을 연습한다.

## 훈련 내용
- CSV 문자열을 리턴하는 메서드를 인터페이스 규칙으로 정의한다.
- 인터페이스를 활용하여 파일 출력 코드를 통합한다.
- 파라미터 구현체로서 *메서드 레퍼런스* 또는 *생성자 레퍼런스* 를 전달한다.
- 이를 이용하여 파일 입력 코드를 통합한다.


## 실습

### 1단계 - 객체에서 CSV 형식의 문자열 추출을 인터페이스를 이용하여 규칙으로 정의한다.

CSV 문자열 추출을 규칙으로 정의해두면 규칙에 따라 일관성있게 객체를 다룰 수 있다.

- com.eomcs.util.CsvObject 인터페이스 생성
  - toCsvString() 메서드를 규칙으로 정의한다.

#### 작업 파일
- com.eomcs.util.CsvObject 생성


### 2단계 - 도메인 클래스에 CsvObject 인터페이스를 적용한다.

- Board, Member, Project, Task 변경
  - CsvObject 인터페이스의 구현체로 선언한다.

#### 작업 파일
- com.eomcs.pms.domain.Board 변경
- com.eomcs.pms.domain.Member 변경
- com.eomcs.pms.domain.Project 변경
- com.eomcs.pms.domain.Task 변경

### 3단계 - Board, Member, Project, Task 의 파일 저장 메서드를 통합한다.
 
- App 변경
  - saveBoards(), saveMembers(), saveProjects(), saveTasks() 메서드를
    saveObjects() 메서드로 통합한다.

#### 작업 파일
- com.eomcs.pms.App 변경

### 4단계 - CSV 형식의 문자열을 객체로 변환하는 것을 인터페이스를 이용해 규칙으로 정의한다.

이렇게 규칙으로 정의해두면,
CSV 문자열을 객체로 바꿀 때 일관성 있게 코딩할 수 있다.

- com.eomcs.util.ObjectFactory 인터페이스 생성
  - create() 메서드를 규칙으로 정의한다.

#### 작업 파일
- com.eomcs.util.ObjectFactory 생성

### 5단계 - 파일의 데이터를 로딩하는 메서드를 통합한다.

- App 변경
  - loadBoards(), loadMembers(), loadProjects(), loadTasks() 메서드를
    loadObjects() 메서드로 통합한다.

#### 작업 파일
- com.eomcs.pms.App 변경
  - 백업: App01.java

### 6단계 - ObjectFactory 구현체로서 생성자를 사용한다.

- Board, Member, Project, Task 변경
  - CSV 문자열을 받아 인스턴스를 초기화시키는 생성자를 추가한다.
- App 변경
  - loadObjects()를 호출할 때 valueOfCsv() 대신에 도메인 클래스의 생성자를 전달한다.

#### 작업 파일
- com.eomcs.pms.App 변경


## 실습 결과
- src/main/java/com/eomcs/util/CsvObject.java 생성
- src/main/java/com/eomcs/util/CsvObjectFactory.java 생성
- src/main/java/com/eomcs/pms/domain/Board.java 변경
- src/main/java/com/eomcs/pms/domain/Member.java 변경
- src/main/java/com/eomcs/pms/domain/Project.java 변경
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
