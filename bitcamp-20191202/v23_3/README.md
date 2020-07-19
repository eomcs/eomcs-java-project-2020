# 23_3 - 인터페이스를 활용하여 객체 사용 규칙 정의하기

## 학습 목표

- 인터페이스의 용도와 이점을 이해한다.
- 객체 간의 사용 규칙을 정의할 때 인터페이스를 활용할 수 있다.


## 실습 소스 및 결과

- src/main/java/com/eomcs/util/List.java 추가
- src/main/java/com/eomcs/util/AbstractList.java 변경

## 실습

### 훈련1. 추상 클래스에서 추상 메서드를 추출하여 인터페이스를 정의하라.

- List.java
    - AbstractList 추상 클래스에 있는 추상 메서드를 추출하여 따로 메서드 사용 규칙을 정의한다.
- AbstractList.java
    - 추상 메서드를 List 인터페이스로 옮긴다.
    - List 규칙을 따른다.
