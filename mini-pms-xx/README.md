# 14 - 인스턴스 연산자와 메서드, 그리고 세터(setter)/게터(getter)

**인스턴스 메서드** 는 인스턴스의 필드를 다루는 일을 한다.
즉 인스턴스 값을 다루는 **연산자(operator)** 역할을 수행한다.
그래서 OOA/D(Object-Oriented Analysis/Design)에서는 메서드를 오퍼레이터라고 부른다.

이번 훈련에서는 인스턴스 메서드를 **연산자** 관점에서 바라보고 이해해보자.
인스턴스 필드의 값을 외부에서 직접 접근하지 못하게 막고,
대신 세터(setter)/게터(getter) 메서드를 통해 값을 조회하고 변경하자.

## 훈련 목표

- 변수와 연산자 관점에서 인스턴스와 메서드를 이해한다.
- 메서드를 활용하여 인스턴스 값을 다루는 연산자를 정의한다.
- 캡슐화의 의미를 이해하고, 셋터/겟터를 만든다.

## 훈련 내용

- 게시글, 회원 정보, 프로젝트 정보, 작업 정보를 다루는 세터/게터를 정의한다.
- 세터/게터를 사용하여 인스턴스 필드 값을 조회하고 변경한다.  


## 실습

### 1단계 - MemberHandler의 인스턴스 필드를 비공개로 전환한다.

- MemberHandler를 의존 객체로 사용하는 클래스(ProjectHandler, TaskHandler)에서
  MemberHandler의 필드 값을 임의로 조작할 수 없도록 필드를 비공개(private)로 전환한다. 

#### 작업 파일

- com.eomcs.pms.MemberHandler 클래스 변경

### 2단계 - Board 인스턴스의 값을 다룰 연산자를 정의한다.

- Board 클래스의 인스턴스 필드를 패키지 공개(default) 모드에서 
  비공개(private) 모드로 전환한다.
- 대신 세터/게터를 정의하여 필드 값을 다룰 수 있도록 한다.

#### 작업 파일

- com.eomcs.pms.handler.BoardHandler$Board 클래스 변경
- com.eomcs.pms.handler.BoardHandler 클래스 변경




### 작업2) Member 인스턴스의 값을 다룰 연산자를 정의하라.

- Member.java
    - 인스턴스 변수(필드)를 비공개(private)로 전환한다.
    - 값을 설정하고 리턴해주는 세터/게터를 정의한다.
- MemberHandler.java
    - Member 인스턴스에 값을 넣고 꺼낼 때 세터/겟터를 사용한다.

### 작업3) Board 인스턴스의 값을 다룰 연산자를 정의하라.

- Board.java
    - 인스턴스 변수(필드)를 비공개(private)로 전환한다.
    - 값을 설정하고 리턴해주는 세터/게터를 정의한다.
- BoardHandler.java
    - Board 인스턴스에 값을 넣고 꺼낼 때 세터/겟터를 사용한다.

## 실습 결과

- src/main/java/com/eomcs/lms/domain/Lesson.java 변경
- src/main/java/com/eomcs/lms/domain/Member.java 변경
- src/main/java/com/eomcs/lms/domain/Board.java 변경
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경