# 34-a. 네트워크 API를 활용한 C/S 아키텍처 : 클라이언트/서버 프로젝트 준비

이번 훈련에서는,
- **자바 네트워크 API** 를 사용하여 클라이언트/서버 통신 애플리케이션을 만든다.

## 훈련 목표
- Gradle 빌드 도구를 이용하여 자바 애플리케이션 프로젝트를 만드는 것을 연습한다.
- Gradle 빌드 도구를 이용하여 Eclipse IDE 용 프로젝트로 전환하는 것을 연습한다.

## 훈련 내용
- 자바 프로젝트로 사용할 폴더를 만들고 Gradle 빌드 도구로 초기화시킨다.
- Gradle eclipse 플러그인을 사용하여 Eclipse IDE 용 설정 파일을 준비한다.


## 실습

### 1단계 - 클라이언트 프로젝트 폴더를 생성한다.

- 'bitcamp-workspace/bitcamp-project-client' 디렉토리를 생성한다.

### 2단계 - 클라이언트 프로젝트 폴더를 Maven 기본 자바 디렉토리 구조로 초기화한다.

- '$ gradle init' 실행

### 3단계 - 이클립스 IDE로 임포트 한다.

- 'build.gradle' 변경
  - 'eclipse' gradle 플러그인을 추가한다.
  - 'javaCompile'을 설정한다.
- '$ gradle eclipse' 실행
  - gradle을 실행하여 이클립스 설정 파일을 생성한다.
- 이클립스에서 프로젝트 폴더를 임포트한다.

### 4단계 - 애플리케이션 메인 클래스를 변경한다.

- 'ClientApp.java' 생성
  - 기존의 'App.java'의 클래스 이름을 변경한다.
- 'src/test/java/ClientAppTest.java' 생성
  - 기존의 'AppTest.java'의 클래스 이름을 변경한다.
  - 소스 코드를 정리한다.
- ClientApp.java 를 실행하여 결과를 확인한다.    


## 실습 결과
- src/main/java/com/eomcs/pms/ClientApp.java 이름 변경
