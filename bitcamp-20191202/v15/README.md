# 15 - 인스턴스 연산자와 메서드

## 학습 목표

- 메서드를 활용하여 인스턴스 값을 다루는 연산자를 정의할 수 있다.
- 캡슐화의 의미를 이해하고, 셋터/겟터를 만들 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/domain/Lesson.java 변경
- src/main/java/com/eomcs/lms/domain/Member.java 변경
- src/main/java/com/eomcs/lms/domain/Board.java 변경
- src/main/java/com/eomcs/lms/handler/LessonHandler.java 변경
- src/main/java/com/eomcs/lms/handler/MemberHandler.java 변경
- src/main/java/com/eomcs/lms/handler/BoardHandler.java 변경

## 실습

### 작업1) Lesson 인스턴스의 값을 다룰 연산자를 정의하라.

- Lesson.java
    - 인스턴스 변수(필드)를 비공개(private)로 전환한다.
    - 값을 설정하고 리턴해주는 세터/게터를 정의한다.
- LessonHandler.java
    - Lesson 인스턴스에 값을 넣고 꺼낼 때 세터/겟터를 사용한다.

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
