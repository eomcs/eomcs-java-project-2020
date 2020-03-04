# 01 - Gradle 빌드 도구를 활용하여 자바 애플리케이션 프로젝트 만들기

## 학습 목표

- `그레이들`을 이용하여 프로젝트 디렉토리를 구성할 수 있다.
- `그레이들`로 프로젝트를 빌드하고 실행할 수 있다.
- `아파치 메이븐` 프로젝트의 디렉토리 구조를 이해한다.

## 구현 목표

- 자바 애플리케이션 프로젝트를 생성하라.
- 아파치 메이븐(Apache Maven)과 호환되는 구조로 디렉토리를 구성하라.
  
## 구현 결과 및 소스 파일

자바 애플리케이션 프로젝트 폴더

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

## 실습

### 실습1 - 프로젝트 디렉토리를 준비한다

로컬 Git 저장소에 자바 프로젝트 폴더를 생성한다.

```console
[~/git/java-study]$ mkdir java-project
[~/git/java-study]$ cd java-project
```

프로젝트 폴더를 자바 애플리케이션 프로젝트로 초기화시킨다.

```console
[~/git/java-study/java-project]$ gradle init
Starting a Gradle Daemon (subsequent builds will be faster)

Select type of project to generate:
  1: basic
  2: application  <== 애플리케이션 개발 프로젝트
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2

Select implementation language:
  1: C++
  2: Groovy
  3: Java  <== 자바 프로젝트
  4: Kotlin
  5: Swift
Enter selection (default: Java) [1..5] 3

Select build script DSL:
  1: Groovy  <== 빌드 스크립트로 사용할 언어 선정
  2: Kotlin
Enter selection (default: Groovy) [1..2] 1

Select test framework:
  1: JUnit 4  <== 단위 테스트로 사용할 프레임워크 선정
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit 4) [1..4] 1

Project name (default: java-project): <== 그냥 엔터치면 디렉토리명을 프로젝트 이름으로 사용
Source package (default: eomcs.java.project): com.eomcs.pms  <== 자바 기본 소스 패키지

```

### 실습2 - 생성한 프로젝트의 디렉토리 구조를 확인한다

```console
java-project
├── README.md
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

### 실습3 - 자바 애플리케이션을 실행한다

#### Gradle 빌드 도구를 사용하여 자바 애플리케이션 실행하기

```console
[~/git/java-study/java-project]$ gradle run
```

#### 프로젝트를 빌드한 후 자바 애플리케이션을 실행하기

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
