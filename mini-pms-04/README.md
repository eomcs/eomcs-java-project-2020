# 04 - 리터럴과 콘솔 출력 다루기

## 학습 목표

- 다양한 유형의 값을 콘솔로 출력할 수 있다.

## 구현 목표

- 콘솔로 한 명의 회원 정보를 출력하라.
- 콘솔로 한 개의 프로젝트 정보를 출력하라.
- 콘솔로 한 개의 작업 정보를 출력하라.
  
## 구현 결과 및 소스 파일

- src/main/java/com/eomcs/pms/App.java 변경
- src/main/java/com/eomcs/pms/App2.java 추가
- src/main/java/com/eomcs/pms/App3.java 추가
- src/test/java/com/eomcs/pms/AppTest.java 변경

## 실습

### 실습1 - 한 명의 회원 정보를 출력한다

```console
[회원]
번호: 101
이름: 홍길동
이메일: hong@test.com
암호: 1111
사진: hong.png
전화: 1111-2222
가입일: 2020-01-01
```

- com.eomcs.pms.App  클래스 변경
- src/test/java/com/eomcs/pms/AppTest.java 변경
  - App 클래스 변경에 맞춰 테스트 코드를 제거한다.

### 실습2 - 한 개의 프로젝트 정보를 출력한다

```console
[프로젝트]
번호: 1201
프로젝트명: 미니 프로젝트 관리 시스템 개발
내용: 소규모 팀을 위한 프로젝트 관리 시스템을 개발한다.
시작일: 2020-01-01
종료일: 2020-12-31
생성자 번호: 101
```

- com.eomcs.pms.App2  클래스 추가
  
### 실습3 - 한 개의 작업 정보를 출력한다

```console
[작업]
번호: 1
내용: 요구사항 수집
완료일: 2020-01-20
프로젝트 번호: 1201
상태: 진행중
```

- com.eomcs.pms.App3  클래스 추가
