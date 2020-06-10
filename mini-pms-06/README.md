# 06 - 기본 문법의 활용

## 훈련 목표

- 변수, 상수, 연산자, 조건문, 반복문, 블록, 배열 등 자바 기본 문법의 사용법을 배운다.

## 훈련 내용

- 사용자로부터 명령을 입력 받아 처리한다.
- 조건문을 사용하여 명령에 따라 분기하여 처리한다.
  
## 실습

### 1단계 - 사용자에게 입력을 안내하는 명령 프롬프트를 출력한다.

- com.eomcs.pms.App 클래스 변경
  - 백업: App.java.01

### 2단계 - 사용자로부터 명령어를 입력 받아 출력한다.

- com.eomcs.pms.App 클래스 변경
  - 백업: App.java.02

### 3단계 - 사용자로부터 반복해서 명령어를 입력 받는다.

사용자가로부터 반복적으로 명령어를 입력 받게 한다. 명령어가 'exit'나 'quit'이면 입력을 종료한다.

- com.eomcs.pms.App 클래스 변경
  - 백업: App.java.03

### 4단계 - `/member/add`, `/member/list` 명령을 구분한다.

회원 등록 명령('/member/add') 이나 회원 목록 조회 명령('/member/list')를 구분한다. 그 외 명령은 오류 메시지를 띄운다.

- com.eomcs.pms.App 클래스 변경
  - 백업: App.java.04

### 5단계 - `/member/add` 명령을 처리한다.

'회원 등록' 명령을 처리한다.

- com.eomcs.pms.App 클래스 변경
  - 백업: App.java.05

### 6단계 - `/member/list` 명령을 처리한다.

'회원 목록 조회' 명령을 처리한다.

- com.eomcs.pms.App 클래스 변경
  - 백업: App.java.06

### 7단계 - `/project/add` 명령을 처리한다.

'프로젝트 등록' 명령을 처리한다.

- com.eomcs.pms.App 클래스 변경
  - App2.java 에서 프로젝트 등록과 관련된 코드를 가져온다.
  - 백업: App.java.07

### 8단계 - `/project/list` 명령을 처리한다.

'프로젝트 목록 조회' 명령을 처리한다.

- com.eomcs.pms.App 클래스 변경
  - App2.java 에서 프로젝트 목록 조회와 관련된 코드를 가져온다.
  - 백업: App.java.08

### 9단계 - `/task/add` 명령을 처리한다.

'작업 등록' 명령을 처리한다.

- com.eomcs.pms.App 클래스 변경
  - App3.java 에서 작업 등록과 관련된 코드를 가져온다.
  - 백업: App.java.09

### 10단계 - `/task/list` 명령을 처리한다.

'작업 목록 조회' 명령을 처리한다.

- com.eomcs.pms.App 클래스 변경
  - App3.java 에서 작업 목록 조회와 관련된 코드를 가져온다.
  - 백업: App.java.10


## 실습 결과

- src/main/java/com/eomcs/pms/App.java 변경
- src/main/java/com/eomcs/pms/App2.java 삭제
- src/main/java/com/eomcs/pms/App3.java 삭제