# 32_1 - 자바 서버 프로젝트 만들기

## 학습목표

- gradle을 이용하여 자바 프로젝트를 만들 수 있다.
- eclipse로 임포트 할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/ServerApp.java 추가

## 실습  

### 훈련 1: 프로젝트 폴더를 생성하라.

- 'bitcamp-study/bitcamp-project-server' 디렉토리를 생성한다.

### 훈련 2: 프로젝트 폴더에 자바 프로젝트로 만들라.

- '$ gradle init' 실행

### 훈련 3: 이클립스 IDE로 임포트 하라.

- 'build.gradle' 변경
  - 'eclipse' gradle 플러그인을 추가한다.
  - 'javaCompile'을 설정한다.
- '$ gradle eclipse' 실행
  - gradle을 실행하여 이클립스 설정 파일을 생성한다.
- 이클립스에서 프로젝트 폴더를 임포트한다.

### 훈련 4: 프로젝트 시작 클래스를 변경하라.

- 'ServerApp.java' 생성
  - 기존의 'App.java'의 클래스 이름을 변경한다.
  - 소스 코드를 정리한다.
  - '서버 수업 관리 시스템입니다' 문구를 출력한다.
- 'src/test/java/ServerAppTest.java' 생성
  - 기존의 'AppTest.java'의 클래스 이름을 변경한다.
  - 소스 코드를 정리한다.
- ServerApp.java 를 실행하여 결과를 확인한다.    

