# eomcs-java-project-2020

이 저장소는 '[프로젝트로 배우는] 자바 실전 프로그래밍'의 강의 예제 소스를 보관하는 곳이다.

이 강의의 핵심 목표는 개발 입문자에게 `자바 프로그래밍의 실전 기법`과 `개발 경험`을 전달하는 것이다.
이를 위해 우리는 간단한 프로젝트를 진행할 것이다.
이 프로젝트를 통해 자바 문법이 어떻게 실전에서 활용되는지 배울 수 있고,
과거에서 최근까지 약 20여년에 걸쳐 애플리케이션 아키텍처가 진화해 온 과정을 압축하여 경험할 수 있다.

예제로 진행할 프로젝트는 소규모 팀을 위한 '프로젝트 관리 시스템'을 만드는 것이다.
콘솔 입출력에서 시작하여 웹 애플리케이션, 모바일 웹까지 단계적으로 진화하도록 구성하였다.
각 단계마다 구현 목표가 있으며, 구현에 필요한 자바 문법과 기법들을 소개하였다.

프로젝트의 각 단계를 따라가다 보면,
`자료구조`에 따라 데이터를 다루는 방법이나
`리팩터링`을 통해 코드를 유지보수 하기 좋게 만드는 방법,
`GoF의 디자인 패턴`으로 기능 변경이나 확장이 용이한 구조로 애플리케이션을 설계하는 방법 등을 배울 수 있다.

특히 단계적으로 기술이 진화해 가는 과정을 체험함으로써,
단순한 프로그래밍 역량 강화를 넘어서 더 빠르게 성장할 수 있는 토대를 마련할 것이다.
당장 시스템 유지보수에 투입될 예정인 신입 개발자라면,
앞으로 마주하게 될 다양한 구조의 시스템에 대한 대응력을 더 높이는 계기가 될 것이다.

## 대상자

- 자바 기본 문법을 공부중인 분
- 서블릿/JSP를 학습하였거나 학습하려는 분
- C/C++, Python 등 다른 프로그래밍 언어를 알고 있는 데, 자바 프로그래밍을 빠르게 배우고 싶은 분
- 자바 기본 문법을 공부하였는데 어떻게 응용해야 할 지 모르겠는 분
- 다양한 오픈 소스를 자바 애플리케이션에 개발에 적용하는 방법을 배우고 싶은 분
- 스프링 프레임워크 기반 프로젝트에 참여중이거나 참여할 예정인 분
- 자바 웹 애플리케이션 프로젝트의 유지보수를 맡고 있거나 맡을 예정인 분
- 웹 애플리케이션의 아키텍처나 스프링 프레임워크의 내부 구조가 궁금한 분

## 학습 목표

이 교육과정을 통해 다음을 배울 수 있다.  

- 자바 언어에서 제공하는 각종 문법의 목적을 이해하고 활용하는 방법
- 기본적인 자료구조를 구현하고 활용하는 방법
- 리팩터링과 디자인 패턴을 적용하는 방법
- 스프링 프레임워크, 마이바티스 등 오픈 소스 프레임워크를 프로젝트에 적용하는 방법
- 애플리케이션 아키텍처의 발전 과정을 이해하고 구현하는 방법

## 프로젝트 버전 및 학습 목표

### 개발 도구 준비하기

- GraalVM(OpenJDK 11 포함) 설치 및 환경 설정
- Eclipse 설치 및 환경 설정
- Visual Studio Code 설치 및 환경 설정
- Scoop(Win)/Homebrew(macOS) 패키지 관리자 설치
- Gradle 빌드 도구 설치
- Git 형상관리 도구 설치
- MariaDB 데이터베이스 설치

### 강의 자료 가져오기

- `github.com` 사이트에 가입하기
- `github.com/eomcs/eomcs-java-project-2020` 저장소를 로컬(개발 PC)로 복제하기

### 개인 저장소 만들기

- github.com 에 개인 저장소 만들기
- github.com 의 개인 저장소를 로컬로 복제하기

### 01-a. Gradle 빌드 도구를 활용하여 자바 애플리케이션 프로젝트 만들기
### 01-b. 프로젝트를 `이클립스 IDE`로 임포트하기
### 01-c. 프로젝트에 버전 관리 시스템 Git을 적용하기

### 02. 리터럴과 콘솔 출력 다루기

### 03. 변수와 키보드 입력 다루기

### 04. 배열과 흐름 제어문 활용하기

### 05. 프로그램의 시작점(entry point), `main()`

### 06. 메서드의 존재 이유

### 07. 클래스를 이용하여 새 데이터 타입 정의하기

### 08. 클래스로 메서드를 분류하기

### 09. 패키지로 클래스를 분류하기

### 10. 다른 클래스와 관계 맺기 : 의존 관계

### 11. 클래스 필드와 클래스 메서드의 한계

### 12. 인스턴스 필드와 인스턴스 메서드가 필요한 이유

### 13. 생성자가 필요한 이유 : 의존 객체 주입

### 14. 데이터를 처리하는 코드를 별도의 클래스로 분리하기 : 캡슐화

### 15. 캡슐화와 접근 제어 : 세터(setter) / 게터(getter)

### 16. 다형성과 형변환 응용

### 17. 제네릭이 필요한 이유와 사용법

### 18. CRUD

### 19. 배열 대신 연결 리스트 자료구조 사용하기

### 20. 스택 자료구조 구현과 활용

### 21. 큐 자료구조 구현과 활용

### 22. 상속 : 일반화(generalization)를 이용한 공통점 분리

### 23. 추상 클래스와 추상 메서드

### 24. 인터페이스를 활용한 객체 사용 규칙 정의

### 25. `Iterator` 디자인 패턴

### 26-a. 중첩 클래스 : 스태틱 중첩 클래스(static nested class)
### 26-b. 중첩 클래스 : 논스태틱 중첩 클래스(inner class)
### 26-c. 중첩 클래스 : 로컬 클래스(local class)
### 26-d. 중첩 클래스 : 익명 클래스(anonymous class)

### 27. 자바 컬렉션 API 사용하기

### 28-a. 'Command' 디자인 패턴을 적용하기 : 메서드를 객체로 분리하기
### 28-b. 'Command' 디자인 패턴을 적용하기 : Map 을 이용한 커맨드 객체 관리

### 29. 예외가 발생했을 때 시스템을 멈추지 않게 하는 방법

### 30-a. 파일 입출력 API : 바이너리 형식으로 데이터를 읽고 쓰기(FileInputStream/FileOutputStream)
### 30-b. 파일 입출력 API : 데코레이터 객체 활용하기(DataInputStream/DataOutputStream)
### 30-c. 파일 입출력 API : 버퍼 사용하기(BufferedInputStream/BufferedOutputStream)
### 30-d. 파일 입출력 API : 객체 읽고 쓰기(ObjectInputStream/ObjectOutputStream)
### 30-e. 파일 입출력 API : 리팩터링 I

### 31-a. 파일 입출력 API : 텍스트 형식(CSV 파일 포맷)으로 데이터를 읽고 쓰기(FileReader/FileWriter)
### 31-b. 파일 입출력 API : 버퍼 사용하기(BufferedReader/BufferedWriter)
### 31-c. 파일 입출력 API : 리팩터링 I
### 31-d. 파일 입출력 API : 리팩터링 II

### 32. JSON 형식으로 객체를 읽고 쓰기 : Gson 라이브러리 활용

### 33-a. `Observer` 디자인 패턴 : 프로젝트에 적용하기
### 33-b. `Observer` 디자인 패턴 : `Observer` 객체를 통해 파일 다루기

### 34-a. 네트워크 API를 활용한 C/S 아키텍처 : 클라이언트/서버 프로젝트 준비
### 34-b. 네트워크 API를 활용한 C/S 아키텍처 : 간단한 메시지 송수신
### 34-c. 네트워크 API를 활용한 C/S 아키텍처 : 사용자가 입력한 명령처리
### 34-d. 네트워크 API를 활용한 C/S 아키텍처 : 응답 프로토콜 변경
### 34-e. 네트워크 API를 활용한 C/S 아키텍처 : 다중 클라이언트의 요청 처리
### 34-f. 네트워크 API를 활용한 C/S 아키텍처 : 다중 클라이언트의 동시 접속 처리
### 34-g. 네트워크 API를 활용한 C/S 아키텍처 : PMS 코드를 C/S로 분리

### 35. 동일한 자원으로 더 많은 클라이언트 요청을 처리하는 방법 : Stateful을 Stateless로 전환하기

### 36-a. 스레드풀을 이용하여 스레드를 재사용하기 : 스레드풀 구현하기
### 36-b. 스레드풀을 이용하여 스레드를 재사용하기 : 자바에서 제공하는 스레드풀 사용하기

### 37-a. 데이터 관리를 DBMS에게 맡기기 : JDBC API 사용하기
### 37-b. 데이터 관리를 DBMS에게 맡기기 : SQL 삽입 공격과 자바 시큐어 코딩
### 37-c. 데이터 관리를 DBMS에게 맡기기 : 무결성 제약 조건 다루기
### 37-d. 데이터 관리를 DBMS에게 맡기기 : 무결성 제약 조건 다루기 II

### 38-a. 데이터 처리 코드를 별도의 클래스로 분리하기 : DAO 클래스 도입
### 38-b. 데이터 처리 코드를 별도의 클래스로 분리하기 : DAO 인터페이스 도입
### 38-c. 데이터 처리 코드를 별도의 클래스로 분리하기 : 의존 객체 주입과 DB 커넥션 객체 공유하기
### 38-d. 데이터 처리 코드를 별도의 클래스로 분리하기 : 트랜잭션이 필용한 이유

### 39. 로그인/로그아웃 구현하기 : 사용자 인증(authentication)하기

### 40-a. 커맨드 실행 전/후에 기능 추가하기: 디자인 패턴 적용 전
### 40-b. 커맨드 실행 전/후에 기능 추가하기: Chain of Responsibility 패턴 적용
### 40-c. 커맨드 실행 전/후에 기능 추가하기: init() 와 destroy()의 필요성

### 41-a. DB 프로그래밍 더 쉽고 간단히 하는 방법 : Mybatis 퍼시스턴스 프레임워크 도입
### 41-b. DB 프로그래밍 더 쉽고 간단히 하는 방법 : Mybatis 기타 기능 활용하기

### 42-a. 트랜잭션 다루기 : 트랜잭션 관리자가 필요한 이유



### 43. DAO 구현체를 자동으로 만들기


### 37 - 애플리케이션 서버가 등장한 이유!

### 38 - 트랜잭션이 필요한 이유!

### 39 - 여러 스레드가 동시에 같은 커넥션을 사용했을 때 발생되는 문제와 해결책

### 40 - 스레드 로컬 변수를 활용하여 작업 간에 DB 커넥션 공유하기

### 41 - 커넥션 재사용을 위해 커넥션풀 적용하기




### 46 - 객체 생성을 자동화하는 IoC 컨테이너 만들기

### 47 - IoC 컨테이너 개선 : 애노테이션을 이용한 객체 관리

### 48 - 인터페이스 대신 애노테이션으로 메서드 구분하기

### 49 - CRUD 메서드를 묶어 한 클래스로 분류하기

### 50 - Spring IoC 컨테이너 도입하기

### 51 - Spring IoC 컨테이너와 MyBatis 연동하기

### 52 - 애노테이션을 이용하여 트랜잭션 제어하기

### 53 - Log4j를 사용하여 애플리케이션 로그 처리하기

### 54 - Web 기술 도입하기
