# 01 - Gradle 빌드 도구를 활용하여 자바 애플리케이션 프로젝트 만들기

## 훈련 목표

- `그레이들` 빌드 도구를 이용하여 자바 프로젝트의 디렉토리를 구성하는 기술을 확보한다. 
- 자바 프로젝트의 디렉토리 구조와 용도를 이해한다.
- `그레이들`로 프로젝트를 빌드하고 실행하는 방법을 익힌다.

## 훈련 내용

- `그레이들` 빌드 도구로 자바 애플리케이션 프로젝트를 생성한다.
- 생성된 자바 애플리케이션 프로젝트의 폴더 구조를 확인한다.
- 프로젝트를 빌드하고 실행한다.
  
## 실습

### 1단계 - 프로젝트로 사용할 디렉토리 준비

사용자 홈 폴더(예: /Users/eomjinyoung)에 자바 프로젝트로 사용할 폴더를 생성한다.

```console
[~]$ mkdir mini-pms
[~]$ cd mini-pms
```

### 2단계 - 디렉토리를 자바 애플리케이션 프로젝트로 전환

자바 애플리케이션 프로젝트에 필요한 설정 파일과 폴더를 준비한다.

```console
[~/mini-pms]$ gradle init
Starting a Gradle Daemon (subsequent builds will be faster)

Select type of project to generate:
  1: basic
  2: application  <== 프로젝트 유형을 '일반 애플리케이션 개발'로 선정한다. 
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2

Select implementation language:
  1: C++
  2: Groovy
  3: Java  <== 프로그래밍에 사용할 언어를 '자바'로 선정한다.
  4: Kotlin
  5: Swift
Enter selection (default: Java) [1..5] 3

Select build script DSL:
  1: Groovy  <== 빌드 스크립트 파일을 작성할 때 사용할 언어를 'Groovy'로 선정한다.
  2: Kotlin
Enter selection (default: Groovy) [1..2] 1

Select test framework:
  1: JUnit 4  <== 단위 테스트로 사용할 프레임워크를 'JUnit'으로 선정한다.
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit 4) [1..4] 1

Project name (default: java-project): <== 그냥 엔터치면 디렉토리명을 프로젝트 이름으로 사용한다.
Source package (default: eomcs.java.project): com.eomcs.pms  <== 기본 자바 패키지를 설정한다.
```

### 실습3 - 프로젝트의 디렉토리 구조 확인

Gradle로 생성한 프로젝트 설정 파일과 폴더를 확인한다.

```console
[~/mini-pms]$ tree   <== tree 명령을 실행하면 현재 폴더의 구조를 보여준다.
├── build.gradle
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── eomcs
    │   │           └── pms
    │   │               └── App.java
    │   └── resources
    └── test
        ├── java
        │   └── com
        │       └── eomcs
        │           └── pms
        │               └── AppTest.java
        └── resources
```

### 실습4 - 자바 애플리케이션 실행

Gradle을 사용하여 자바 애플리케이션을 실행한다.

```console
[~/git/java-study/java-project]$ gradle run
```

### 실습5 - 프로젝트 빌드 및 배포

```console
1) 프로젝트를 빌드한다
[~/git/java-study/java-project]$ gradle build

2) 배포 파일의 압출을 푼다
- zip 파일의 압축을 풀어도 된다.
[~/git/java-study/java-project/build/distributions]$ tar -xvf java-project.tar

3) 쉘스크립트 실행
- Windows OS에서는 java-project.bat 파일을 실행한다.
[~/git/java-study/java-project/build/distributions/java-project/bin]$ ./java-project
```

## 실습 결과 및 소스 파일

```console
src/
  main/
    java/
    resources/
  test/
    java/
    resources/
gradle/
build.gradle
settings.gradle
gradlew
gradlew.bat
```