# 32. 바이너리 형식으로 데이터를 읽고 쓰기

이번 훈련에서는,
- **바이너리(binary)** 형식으로 입출력하는 것을 연습할 것이다.
- **파일 입출력 API** 에 적용되어 있는 **데코레이터(decorator) 패턴** 을 활용하여
  객체에 기능을 덧붙여 사용하는 방법을 연습할 것이다.

**바이너리** 파일 포맷,
- 해당 파일 포맷을 다루는 전용 프로그램을 사용해야만 편집할 수 있다.
  - 예) 포토샵, 파워포인트, 워드, 엑셀, 동영상 편집기 등
  - 예) .class, .hwp, .doc, .xls, .ppt, .pdf, .gif, .jpg, .avi, .wav, .mp3 등
- 메모장 등 텍스트 편집기에서 직접 파일을 편집하지 못한다.
- 만약 텍스트 편집기로 편집한 후에 저장하면 파일 형식이 깨져서 무효한 파일이 된다.
- 같은 데이터를 저장하더라도 텍스트 포맷 보다는 파일의 크기가 작다.

**텍스트** 파일 포맷,
- 전용 프로그램의 도움 없이 텍스트 편집기로 직접 편집할 수 있다.
  - 예) .txt, .csv, .html, .css, .js, .java, .xml, .rtf 등

**데코레이터 패턴** 은,
- 객체에 동적으로 새로운 서비스를 추가할 수 있게 해주는 설계 기법이다.
- 상속은 정적으로 기능을 확장하는 방식으로 서브 클래스에서 기능을 한 번 추가하면,
  그 자손 클래스들은 임의로 그 기능을 뺄 수 없다.
- 그에 반해 데코레이터 패턴으로 클래스를 설계하면, 플러그인 처럼 필요한 시점에 임의로 기능을 추가하고
  뺄 수 있어 프로그래밍이 편하다.
- *데코레이터 패턴* 이 적용된 대표적인 예가 **파일 입출력 API** 이다.   


## 훈련 목표
- 바이너리 입출력 스트림 클래스를 사용하여 객체의 필드 값을 바이너리 형식으로 읽고 쓰는 방법을 배운다.
- DataInputStream/DataOutputStream 의 사용법을 배운다.

## 훈련 내용
- 게시글, 회원, 프로젝트, 작업 데이터를 바이너리 형식을 저장하고 읽는다.


## 실습

`mini-pms-30-b` 프로젝트 소스를 가지고 작업한다.

### 1단계 - 게시글, 회원, 프로젝트, 작업 데이터 각각에 대해 바이너리 형식으로 저장한다.

- App 변경
  - saveBoards(), saveMembers(), saveProjects(), saveTasks() 메서드를 변경한다.
  - DataOutputStream 클래스를 사용하여 도메인 객체의 각 필드 값을 바이너리로 출력한다.

#### 작업 파일
- com.eomcs.pms.App 변경


### 2단계 - 바이너리 형식으로 저장된 게시글, 회원, 프로젝트, 작업 데이터 파일을 읽어 로딩한다.

- App 변경
  - loadBoards(), loadMembers(), loadProjects(), loadTasks() 메서드를 변경한다.
  - DataInputStream 클래스를 사용하여 바이너리 데이터를 읽어 도메인 객체에 저장한다.

#### 작업 파일
- com.eomcs.pms.App 변경


## 실습 결과
- src/main/java/com/eomcs/pms/App.java 변경