# 32. JSON 형식으로 객체를 읽고 쓰기 : Gson 라이브러리 활용

이번 훈련에서는,
- **JSON(JavaScript Object Notation)** 형식으로 입출력하는 것을 연습할 것이다.
- *JSON* 형식을 다루기 위해 **Gson** 외부 라이브러리를 활용할 것이다.

**JSON** 은,
- 속성-값 또는 키-값 으로 된 데이터 객체를 텍스트로 표현하는 개방형 표준 데이터 포맷이다.
```
{속성:값, 속성:값, ...}
예) {"no":1,"name":"1","email":"1","password":"1","photo":"1","tel":"1"}
```
- 텍스트 형식이기 때문에 프로그래밍 언어나 운영체제에 영향을 받지 않는다.
- 바이너리 방식에 비해 데이터 커지는 문제가 있지만,
   모든 프로그래밍 언어에서 다룰 수 있다는 장점이 있다.
- 인터넷 상에서 애플리케이션 간에 데이터를 주고 받을 때 주로 사용한다.
- 특히 이기종 플랫폼(OS, 프로그래밍 언어 등) 간에 데이터를 교환할 때 유용하다.
- JSON 공식 홈인 https://www.json.org 사이트에 자세한 내용이 있다.

**JSON 라이브러리**,
- JSON 데이터 포맷을 다루는 라이브러리다.
- JSON 홈페이지에 다양한 프로그래밍 언어에서 사용할 수 있는 라이브러리를 소개한다.

**Gson**,
- 구글에서 제공하는 JSON 자바 라이브러리다.
- 자바 객체를 JSON 형식의 텍스트로 변환하는 기능을 제공한다.
- JSON 형식의 텍스트를 자바 객체로 변환하는 기능을 제공한다.  


## 훈련 목표
- *Gson* 라이브러리를 사용하여 자바 객체를 *JSON* 데이터 포맷으로 출력하는 것을 연습한다.
- 꺼꾸로 *JSON* 형식의 텍스트를 읽어 들여 자바 객체로 변환하는 것을 연습한다.


## 훈련 내용
- 프로젝트에 *Gson* 라이브러리를 추가한다.
- 게시글, 회원, 프로젝트, 작업 목록을 JSON 형식의 텍스트로 변환하여 파일로 출력한다.
- 파일로부터 JSON 형식의 텍스트를 읽어 List 구현체로 변환한다.


## 실습

### 1단계 - *Gson* 라이브러리를 프로젝트에 추가한다.

- `build.gradle` 빌드 스크립트 파일 변경
  - Gson 라이브러리 정보를 dependecies {} 블록에 추가한다.
    - https://search.maven.org/ 사이트에 방문한다.
    - `gson` 검색어로 라이브러리를 찾는다.
    - `com.google.code.gson` 라이브러리를 선택한다.
    - 검색 결과에서 최신 버전을 선택한다.
    - Gradle Groovy DSL 코드를 복사하여 빌드 스크립트에 붙여 넣는다.
  - `$ gradle eclipse` 를 실행하여 라이브러리를 다운로드하여 프로젝트에 등록한다.
    - 명령을 실행한 후 eclipse IDE 에서 해당 프로젝트를 refresh 해야 한다.
    - 'Referenced Libraries' 노드에서 gson 라이브러리 파일이 추가된 것을 확인한다.

#### 작업 파일
- build.gradle 변경


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
- com.eomcs.util.CsvObject 삭제
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
- com.eomcs.util.ObjectFactory 삭제


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
- src/main/java/com/eomcs/pms/domain/Board.java 변경
- src/main/java/com/eomcs/pms/domain/Member.java 변경
- src/main/java/com/eomcs/pms/domain/Project.java 변경
- src/main/java/com/eomcs/pms/domain/Task.java 변경
- src/main/java/com/eomcs/pms/App.java 변경
- src/main/java/com/eomcs/util/CsvObject.java 삭제
- src/main/java/com/eomcs/util/ObjectFactory.java 삭제