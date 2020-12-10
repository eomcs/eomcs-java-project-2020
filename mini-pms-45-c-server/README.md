# 45-c. MVC 아키텍처로 전환하기 : 프론트 컨트롤러 도입하기

이번 훈련에서는,
-

## 훈련 목표
-

## 훈련 내용
-

## 실습


### 1단계 - 웹 애플리케이션 서버를 설치한다.

- 톰캣 서버 설치
  - tomcat.apache.org 사이트에서 다운로드 한다.
  - `~/server/` 디렉토리에 압축을 푼다.
- 톰캣 서버 실행 확인
  - $CATALINA_HOME/bin/startup.bat 파일 실행(Windows)
  - $CATALINA_HOME/bin/startup.sh 파일 실행(macOS/Linux/Unix)
  - 웹 브라우저에서 주소 창에 `http://localhost:8080` 입력하여 페이지 확인한다.

### 2단계 - 톰캣 서버의 위치를 이클립스에 등록한다.

- 이클립스 메뉴 > Window >  Preferences...
  - Server > Runtime Environments > add 버튼 클릭
  - 톰캣 서버의 디렉토를 등록한다.

### 3단계 - 개발할 때 사용할 톰캣 서버 실행 환경을 구축한다.

- 이클립스 > Servers 뷰 > 새 서버 실행 환경 등록


### 4단계 - 기존 프로젝트를 이클립스 용 웹 프로젝트로 전환한다.

- build.gradle 변경
  - 그래이들 플러그인 추가
    - 기존의 `eclipse` 플러그인 대신에 `eclipse-wtp` 을 사용한다.
    - `war` 플러그인 추가
    - 이 두 개의 플러그인이 있어야만 웹 프로젝트에 관련된 설정 파일을 생성할 수 있다.
  - 웹 프로젝트에 필요한 라이브러리 추가
    - http://search.maven.org 사이트에 간다.
    - `javax.servlet-api` 키워드로 검색한다.
    - `4.0.1` 버전의 라이브러리 정보를 가져온다.
  - `$ gradle eclipse` 실행한다.
    - 이클립스 웹 프로젝트와 관련된 설정 파일을 생성한다.
    - 의존 라이브러리도 가져온다.
  - 이클립스 IDE 에서 프로젝트를 refresh 한다.


### 5단계 - 웹 프로젝트를 톰캣 실행 환경에 등록한다.

- 이클립스 > Servers 뷰 > 서버 실행 환경 > add
  - 프로젝트를 추가한다.


## 실습 결과

- src/main/java/com/eomcs/pms/ServerApp.java 변경
