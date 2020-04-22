# 01 - Gradle 빌드 도구를 활용하여 자바 애플리케이션 프로젝트 만들기

## 훈련 목표

- `그레이들` 빌드 도구를 이용하여 자바 프로젝트의 디렉토리를 구성하는 방법을 배운다. 
- 자바 프로젝트의 디렉토리 구조와 용도를 이해한다.
- `그레이들`로 프로젝트를 빌드하고 실행하는 방법을 배운다.

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

### 3단계 - 프로젝트의 디렉토리 구조 확인

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

### 4단계 - 자바 애플리케이션 실행

Gradle을 사용하여 자바 애플리케이션을 실행한다.

```console
[~/mini-pms]$ gradle run
Starting a Gradle Daemon (subsequent builds will be faster)

> Task :run
Hello world.     <== 실행 결과

BUILD SUCCESSFUL in 16s
3 actionable tasks: 3 executed
```

### 5단계 - 프로젝트 빌드

Gradle 빌드 도구를 사용하여 일반 사용자에게 배포할 파일을 만든다.

```console
[~/mini-pms]$ gradle build
```

#### build 절차

- 소스 파일을 컴파일한다.
- 단위 테스트를 수행한다.
- .jar 아카이브 파일을 만든다.
- 실행과 관련된 파일을 생성한다.
- .tar, .zip 배포 파일을 생성한다.

빌드를 통해 생성된 파일과 폴더는 build 디렉토리 아래에 놓인다.

```console
[~/mini-pms]$ tree  <== 윈도우에서는 파일 탐색기로 확인하면 된다.
.
├── build
│   ├── classes
│   │   └── java
│   │       ├── main
│   │       │   └── com
│   │       │       └── eomcs
│   │       │           └── pms
│   │       │               └── App.class
│   │       └── test
│   │           └── com
│   │               └── eomcs
│   │                   └── pms
│   │                       └── AppTest.class
│   ├── distributions
│   │   ├── mini-pms.tar    <== 이 파일을 Unix/Linux 사용자에게 배포한다.
│   │   └── mini-pms.zip    <== 이 파일을 Windows 사용자에게 배포한다.
│   ├── generated
│   │   └── sources
│   │       ├── annotationProcessor
│   │       │   └── java
│   │       │       ├── main
│   │       │       └── test
│   │       └── headers
│   │           └── java
│   │               ├── main
│   │               └── test
│   ├── libs
│   │   └── mini-pms.jar
│   ├── reports
│   │   └── tests
│   │       └── test
│   │           ├── classes
│   │           │   └── com.eomcs.pms.AppTest.html
│   │           ├── css
│   │           │   ├── base-style.css
│   │           │   └── style.css
│   │           ├── index.html
│   │           ├── js
│   │           │   └── report.js
│   │           └── packages
│   │               └── com.eomcs.pms.html
│   ├── scripts
│   │   ├── mini-pms
│   │   └── mini-pms.bat
│   ├── test-results
│   │   └── test
│   │       ├── TEST-com.eomcs.pms.AppTest.xml
│   │       └── binary
│   │           ├── output.bin
│   │           ├── output.bin.idx
│   │           └── results.bin
│   └── tmp
│       ├── compileJava
│       ├── compileTestJava
│       └── jar
│           └── MANIFEST.MF
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

53 directories, 26 files
```

### 6단계 - 배포 파일 실행

배포 파일을 받은 일반 사용자는 보통 다음의 절차에 따라 애플리케이션을 실행한다.

적당한 폴더에 배포 파일(.tar 또는 .zip)의 압축을 푼다. 배포 파일을 `Downloads` 폴더에 다운로드 받았다고 가정하자.  

```console
[~/Downloads]$ tar -xvf mini-pms.tar
[~/Downloads]$ tree  <== tree 가 없으면 따로 설치해야 한다.
.
├── mini-pms
│   ├── bin
│   │   ├── mini-pms   <== Unix/Linux 에서는 이 파일을 실행한다.
│   │   └── mini-pms.bat   <== Windows 에서는 이 파일을 실행한다.
│   └── lib
│       ├── checker-qual-2.10.0.jar
│       ├── error_prone_annotations-2.3.4.jar
│       ├── failureaccess-1.0.1.jar
│       ├── guava-28.2-jre.jar
│       ├── j2objc-annotations-1.3.jar
│       ├── jsr305-3.0.2.jar
│       ├── listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar
│       └── mini-pms.jar
├── mini-pms.tar
└── mini-pms.zip
```

배포 파일에 들어 있는 스크립트 파일을 실행한다.

Windows OS:

```console
[~/Downloads]$ ./mini-pms/bin/mini-pms.bat
Hello world.
```

macOS/Linux/Unix OS:

```console
[~/Downloads]$ ./mini-pms/bin/mini-pms
Hello world.
```