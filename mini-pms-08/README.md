# 08 - 클래스로 메서드를 분류하기

클래스는 여러 개의 메서드를 한 단위로 묶을 때 사용하는 문법이다. 
서로 관련된 기능을 한 곳에 묶어 놓으면 관리가 쉬워진다.

## 훈련 목표

- 클래스를 이용하여 관련 메서드를 한 단위로 묶는 방법을 배운다.
- 리팩토링 기법 중에서 '클래스 추출(Extract Class)'을 연습한다.

## 훈련 내용

- 프롬프트 관련 메서드를 별도의 클래스로 분리한다.
- 회원 관련 메서드를 별도의 클래스로 분리한다.
- 프로젝트 관련 메서드를 별도의 클래스로 분리한다.
- 작업 관련 메서드를 별도의 클래스로 분리한다.

## 실습

### 1단계 - 사용자 입력을 받는 프롬프트 메서드를 클래스로 분리한다

#### 작업 파일

- com.eomcs.pms.Prompt 클래스 생성
    - `App.java` 에 있는 프롬프트 관련 필드 및 메서드를 이 클래스로 옮긴다.
    - 메서드 이름을 적절하게 변경한다.
- com.eomcs.pms.App 변경
    - 프롬프트 메서드를 호출하는 코드를 변경한다.
    - 백업: App.java.01

### 2단계 - 회원 데이터 처리와 관련된 메서드를 별도의 클래스로 분리한다

#### 작업 파일

- com.eomcs.pms.MemberHandler 클래스 생성
    - `App.java` 에 있는 회원 관리와 관련된 필드와 메서드를 이 클래스로 옮긴다.
    - 필드와 메서드를 적절한 이름으로 변경한다.
- com.eomcs.pms.App 변경
    - 회원 관리 명령을 처리할 때 `MemberHandler` 클래스 사용한다.
    - 백업: App.java.02

### 3단계 - 프로젝트 데이터 처리와 관련된 메서드를 별도의 클래스로 분리한다

#### 작업 파일

- com.eomcs.pms.ProjectHandler 클래스 생성
    - `App.java` 에 있는 프로젝트 관리와 관련된 필드와 메서드를 이 클래스로 옮긴다.
    - 필드와 메서드를 적절한 이름으로 변경한다.
- com.eomcs.pms.App 변경
    - 프로젝트 관리 명령을 처리할 때 `ProjectHandler` 클래스 사용한다.
    - 백업: App.java.03

### 4단계 - 작업 데이터 처리와 관련된 메서드를 별도의 클래스로 분리한다

#### 작업 파일

- com.eomcs.pms.TaskHandler 클래스 생성
    - `App.java` 에 있는 작업 관리와 관련된 필드와 메서드를 이 클래스로 옮긴다.
    - 필드와 메서드를 적절한 이름으로 변경한다.
- com.eomcs.pms.App 변경
    - 작업 관리 명령을 처리할 때 `TaskHandler` 클래스 사용한다.
    - 백업: App.java.04


## 실습 결과

- src/main/java/com/eomcs/pms/App.java 변경
- src/main/java/com/eomcs/pms/MemberHandler.java 추가
- src/main/java/com/eomcs/pms/ProjectHandler.java 추가
- src/main/java/com/eomcs/pms/TaskHandler.java 추가