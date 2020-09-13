# 30-c. 파일 입출력 API를 활용하여 데이터를 읽고 쓰기 : 리팩터링 II

이번 훈련에서는,
- **인터페이스** 를 활용하여 코드를 통합하는 것을 연습할 것이다.
- **제네릭** 을 활용하여 범용 메서드를 만드는 방법을 연습할 것이다.
- **리플랙션 API** 를 활용하여 클래스의 특정 메서드를 찾아내어 호출하는 것을 연습할 것이다.

**인터페이스(interface)** 는,
- 객체의 사용 규칙을 정의하는 문법이다.
- 즉 객체에 대해 메서드 호출 규칙을 정의한다.

**인터페이스** 의 이점은,
- 객체를 사용하는 측(client)의 코드 작성과 피사용측 코드 작성을 분리할 수 있다.
- 즉 코드를 작성하는 개발자들이 서로 영향을 끼치지 않고 프로그래밍을 할 수 있다.  
- 특정 클래스에 종속되지 않기 때문에 구현이 더 자유롭고 객체를 대체하기가 더 쉽다.

**리플랙션(reflection) API** 는
- 클래스나 인터페이스의 내부 멤버를 살펴 볼 때 사용한다.
- 수퍼 클래스나 구현한 인터페이스의 정보도 알아 낼 수 있다.
- 클래스 정보를 가지고 인스턴스를 생성하거나 메서드를 호출할 수 있다.
- 프레임워크를 만들 때 많이 사용한다.


## 훈련 목표
- *인터페이스* 의 활용을 연습한다.
- *제네릭* 을 메서드에 적용하는 것을 연습한다.
- *리플랙션 API* 를 사용하여 메서드를 찾아서 호출하는 방법을 연습한다.


## 훈련 내용
- CSV 문자열을 리턴하는 메서드를 인터페이스의 규칙을 정의한다.
- 인터페이스를 활용하여 파일 출력 코드를 통합한다.
- 리플랫션 API를 활용하여 파일의 데이터를 로딩하는 메서드를 통합한다.


## 실습

### 1단계 - 객체에서 CSV 형식의 문자열을 뽑는 메서드를 규칙으로 정의한다.

- com.eomcs.util.CsvData 인터페이스 생성
  - toCsvString() 메서드를 규칙을 정의한다.

#### 작업 파일
- com.eomcs.util.CsvData 생성


### 2단계 - 도메인 클래스는 CsvData 인터페이스를 구현한다.

- Board, Member, Project, Task 변경
  - CsvData 인터페이스의 구현체로 선언한다.

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


### 4단계 - 파일의 데이터를 로딩하는 메서드를 통합한다.

- App 변경
  - loadBoards(), loadMembers(), loadProjects(), loadTasks() 메서드를
    loadObjects() 메서드로 통합한다.

#### 작업 파일
- com.eomcs.pms.App 변경


## 실습 결과
- src/main/java/com/eomcs/util/CsvData.java 생성
- src/main/java/com/eomcs/pms/domain/Board.java 변경
- src/main/java/com/eomcs/pms/domain/Member.java 변경
- src/main/java/com/eomcs/pms/domain/Project.java 변경
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
