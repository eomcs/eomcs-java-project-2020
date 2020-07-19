# 55_2 - 이클립스 웹 프로젝트로 전환하기

## 학습목표

- gradle 설정을 통해 이클립스 웹 프로젝트로 전환할 수 있다.

## 실습 소스 및 결과

- build.gradle 변경
- src/main/webapp/index.html 추가


## 실습  

### 훈련1: 이클립스에 톰캣 서버 환경을 추가한다.

- Window 메뉴/Preferences/Server/Runtime Environment 설정 추가

### 훈련2: 웹애플리케이션 테스트 서버 구축 

- Servers 뷰/New Server

### 훈련3: 웹프로젝트로 전환

- build.gradle 변경
  - 'eclipse' 플러그인 대신에 'eclipse-wtp' 플러그인 추가
  - 'war' 플러그인 추가 
- 'gradle eclipse' 실행
  - .settings/ 폴더에 웹프로젝트 관련 설정파일이 추가된다.
  - .project 파일에 웹프로젝트 관련 설정 정보가 추가된다.
- 프로젝트 refresh
  - 프로젝트 아이콘에 지구본 모양이 추가된다.
  
### 훈련4: 테스트 서버에 웹 프로젝트를 등록한다.

- 'Servers 뷰/테스트서버' 에 대해 컨텍스트 메뉴 출력
- 'Add and Remove...' 메뉴 선택
- 웹프로젝트 추가

### 훈련5: 테스트 서버 시작

- '테스트서버/컨텍스트 메뉴/Publish' 선택
  - 테스트 서버의 배치 폴더에 웹애플리케이션 배치한다.
  - '**.server.core/tmp0/wtpwebapps/' 폴더를 확인하라.
- '테스트서버/컨텍스트 메뉴/Start' 선택
  - 서버를 실행한다.
   
### 훈련6: 웹애플리케이션 테스트

- http://localhost:9999/
  - 테스트 서버에는 루트 웹 애플리케이션이 배치되지 않았기 때문에 오류가 발생한다.  
- http://localhost:9999/bitcamp-project-server/board/list

### 훈련7: 정적자원 폴더 추가하기

- src/main/webapp/ 폴더를 생성한다.
- eclipse 프로젝트 설정 파일을 생성한 후에 이 디렉토리를 만들면,
  설정파일에 이 디렉토리 정보가 포함되지 않는다.
- 해결책? 다시 'gradle eclipse'를 실행하여 설정파일을 만들라.

### 훈련8: 웹애플리케이션 기본 HTML 파일 생성

- src/main/webapp/index.html 파일을 생성한다.
