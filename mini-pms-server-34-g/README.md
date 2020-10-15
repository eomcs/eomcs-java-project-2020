# 34-g. 네트워크 API를 활용한 C/S 아키텍처 : PMS 코드를 C/S로 분리

이번 훈련에서는,
- **자바 네트워크 API** 를 사용하여 클라이언트/서버 통신 애플리케이션을 만든다. 

## 훈련 목표
- Gradle 빌드 도구를 이용하여 자바 애플리케이션 프로젝트를 만드는 것을 연습한다.
- Gradle 빌드 도구를 이용하여 Eclipse IDE 용 프로젝트로 전환하는 것을 연습한다.

## 훈련 내용
- 자바 프로젝트로 사용할 폴더를 만들고 Gradle 빌드 도구로 초기화시킨다.
- Gradle eclipse 플러그인을 사용하여 Eclipse IDE 용 설정 파일을 준비한다.


## 실습

### 1단계 - JSON 데이터 포맷을 다룰 Gson 라이브러리를 추가한다.

- **build.gradle** 파일에 gson 라이브러리 정보를 추가한다.
- `$ gradle eclipse` 를 실행하여 라이브러리를 프로젝트에 추가한다.
- 이클립스 IDE에서 프로젝트를 `refresh` 한다.

### 2단계 - 기존 애플리케이션에서 관련된 패키지 및 클래스를 가져온다.

- com.eomcs.pms.domain 패키지 및 그 하위 클래스를 가져온다.
- com.eomcs.pms.handler 패키지 및 그 하위 클래스를 가져온다.
- com.eomcs.context 패키지 및 그 하위 클래스를 가져온다.
- com.eomcs.pms.listener 패키지 및 그 하위 클래스를 가져온다.
- com.eomcs.pms.App 클래스에서 옵저버 패턴과 관련된 코드를 가져온다.

#### 작업 파일
- com.eomcs.pms.domain.* 추가
- com.eomcs.pms.handler.* 추가
- com.eomcs.context.* 추가
- com.eomcs.pms.listener.* 추가
- com.eomcs.pms.ServerApp 변경


### 3단계 - 클라이언트의 "stop" 명령을 처리한다.

- `ServerApp` 변경
  - stop 변수 추가
  - 클라이언트와 연결할 때 "stop"의 상태가 true 이면 서버를 멈춘다.

#### 작업 파일
- com.eomcs.pms.ServerApp 변경

### 4단계 - 파일에서 JSON 데이터를 로딩하고 파일로 저장하는 옵저버를 등록한다.

- `ServerApp` 변경
  - AppInitListener 를 등록한다.
  - DataHandlerListener 를 등록한다.

#### 작업 파일
- com.eomcs.pms.ServerApp 변경


### 5단계 - 클라이언트의 요청을 처리하는 Command 객체를 준비한다.

- `RequestMappingListener` 생성
  - `DataHandlerListener` 가 준비한 데이터를 가지고 Command 객체를 생성한다.
- `ServerApp` 변경
  - `RequestMappingListener` 를 등록한다.

#### 작업 파일
- com.eomcs.pms.listener.RequestMappingListener 생성
- com.eomcs.pms.ServerApp 변경

### 6단계 - 클라이언트 명령이 들어오면 커맨드 객체를 찾아 실행한다.

- `Command` 인터페이스 변경
  - 커맨드 객체가 클라이언트에게 응답할 수 있도록 출력 스트림 객체를 넘겨준다.
  - `execute()` 를 `execute(PrintWriter)` 로 변경한다. 
- XxxListCommand 구현체를 변경
  - `Command` 인터페이스 변경에 따라 execute() 메서드의 코드를 수정한다.
- `ServerApp` 변경
  - 커맨드 객체의 execute()를 호출할 때 클라이언트 출력 스트림을 제공한다.
  - `sendResponse()` 메서드는 제거한다.

#### 작업 파일
- com.eomcs.pms.handler.Command 변경
- com.eomcs.pms.handler.XxxListCommand 변경
- com.eomcs.pms.ServerApp 변경

### 7단계 - 클라이언트에게 입력 값을 요구할 수 있도록 프로토콜을 변경한다.

```
[클라이언트]              [서버]
명령         -----------> 작업 수행
예) "/board/detail"
출력         <----------- 출력 문자열
                          예) "번호"
사용자 입력  <----------- 입력을 요구하는 명령
                          예) "!{}!" 
입력값       -----------> 작업 수행
예) "1"
출력         <----------- 출력 문자열
                          예) "제목: 하하하" 
출력         <----------- 출력 문자열
                          예) "내용: ㅋㅋㅋ" 
출력         <----------- 출력 문자열
                          예) "작성자: 홍길동" 
완료         <----------- 빈 문자열
                          예) "" 
```

- `Command` 인터페이스 변경
  - 클라이언트 입력 값을 읽을 수 있도록 파라미터에 입력 스트림을 추가한다.
  - `execute(PrintWriter)` 를 `execute(PrintWriter out, BufferedReader in)` 로 바꾼다.
- XxxListCommand 구현체를 변경
  - `Command` 인터페이스 변경에 따라 execute() 메서드의 코드를 수정한다.
- `Prompt` 클래스 변경
  - 파라미터로 받은 출력 스트림으로 프롬프트 제목을 출력하고,
    파라미터로 받은 입력 스트림에서 값을 읽어 리턴하는 메서드를 추가한다. 
- `Xxx[Add|Detail|Update|Delete]Command` 구현체 변경
  - `Command` 인터페이스 변경에 따라 execute() 메서드의 코드를 수정한다.
  - `execute(PrintWriter)` 를 `execute(PrintWriter, BufferedReader)` 로 변경한다. 
- `ServerApp` 변경
  - 커맨드 객체의 execute()를 호출할 때,
    클라이언트 출력 스트림과 입력 스트림을 제공한다.


#### 작업 파일
- com.eomcs.pms.handler.Command 변경
- com.eomcs.pms.handler.XxxListCommand 변경
- com.eomcs.pms.ServerApp 변경

### 8단계 - 디자인 패턴와 C/S 애플리케이션 아키텍처의 유용성을 확인한다.

- 계산기 기능을 수행하는 새 명령을 추가해보자.
  - `CalculatorCommand` 클래스를 추가한다.
  - 커맨드 패턴의 장점을 확인한다.
  - 명령 당 한 개의 메서드를 만드는 상황에서 적절한 설계 기법이다.
  - 새 기능을 추가할 때 기존 코드를 손대지 않고 새 클래스를 추가하는 방식으로 
    유지보수하기가 좋다. 
- 기존 소스 코드를 최소한으로 손대는 것을 확인한다.
  - 새로 만든 기능을 삽입할 때 `RequestMappingListener` 클래스를 변경한다.
  - 최소한의 코드로 새 기능을 추가하는 것을 확인한다.
  - 또한 Observer 패턴의 이점도 확인한다.

#### 작업 파일
- com.eomcs.pms.handler.CalculatorCommand 추가
- com.eomcs.pms.listener.RequestMappingListener 변경


## 실습 결과
- build.gradle 변경
- src/main/java/com/eomcs/domain/*.java 추가
- src/main/java/com/eomcs/util/*.java 추가
- src/main/java/com/eomcs/handler/*.java 추가
- src/main/java/com/context/*.java 추가
- src/main/java/com/eomcs/listener/*.java 추가
- src/main/java/com/eomcs/listener/RequestMappingListener.java 추가
- src/main/java/com/eomcs/pms/handler/Command.java 변경
- src/main/java/com/eomcs/util/Prompt.java 변경
- src/main/java/com/eomcs/pms/handler/XxxCommand.java 변경
- src/main/java/com/eomcs/pms/ServerApp.java 변경
- src/main/java/com/eomcs/pms/handler/CalculatorCommand.java 추가
- src/main/java/com/eomcs/pms/listener/RequestMappingListener.java 변경