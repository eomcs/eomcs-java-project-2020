# 50_1 - Spring IoC 컨테이너 도입하기

## 학습목표

- Spring Frameowork를 프로젝트에 설정할 수 있다.
- Spring IoC 컨테이너를 사용하여 객체를 관리할 수 있다.

## 실습 소스 및 결과

- build.gradle 변경
- src/main/java/com/eomcs/util/ApplicationContext.java 삭제
- src/main/java/com/eomcs/lms/AppConfig.java 추가
- src/main/java/com/eomcs/lms/ContextLoaderListener.java 변경
- src/main/java/com/eomcs/lms/ServerApp.java 변경
- src/main/java/com/eomcs/util/Component.java 삭제
- src/main/java/com/eomcs/lms/servlet/XxxServlet.java 변경
- src/main/java/com/eomcs/lms/service/impl/XxxServiceImpl.java 변경

## 실습  

### 훈련1: Spring IoC 컨테이너 라이브러리를 가져온다.

- Spring IoC 컨테이너의 라이브러리 정보 찾기
  - `mvnrepository.com` 또는 `search.maven.org`에서 `spring-context` 이름으로 
    라이브러리를 검색한다.
- `build.gradle` 변경
  - 빌드 설정 파일에 의존 라이브러리 정보 추가하기
  - 예) `implementation group: 'org.springframework', name: 'spring-context', version: '5.1.2.RELEASE'`
- Gradle 빌드 도구를 사용하여 라이브러리 파일을 프로젝트로 가져오기
  - `$ gradle eclipse` 명령을 실행하면, 의존 라이브러리를 가져온다. 
  - 또한 Eclipse의 CLASSPATH 정보를 갱신한다.
  - 명령어를 실행한 후 이클립스에서 프로젝트를 갱신해야 한다.

### 훈련2: Spring IoC 컨테이너의 설정 정보를 제공하는 클래스 만든다.

- com.eomcs.lms.AppConfig 추가
  - Spring IoC 컨테이너가 객체를 생성하기 위해 탐색할 패키지를 설정한다.
  
  
### 훈련3: Spring IoC 컨테이너를 생성한다.

- com.eomcs.util.ApplicationContext 삭제
  - Spring IoC 컨테이너로 대체한다.
- com.eomcs.lms.ContextLoaderListener 변경 
  - Spring 프레임워크에서 제공하는 클래스를 사용하여 IoC 컨테이너를 생성한다.
  - Spring IoC 컨테이너에 들어 있는 객체를 출력해본다.
    - printBeans()를 추가한다.
- com.eomcs.lms.ServerApp 변경
  - Spring IoC 컨테이너의 ApplicationContext로 교체한다.
- 서버를 실행하여 Spring IoC 컨테이너에 기본적으로 들어 있는 객체를 확인한다.
  - 서블릿이나 서비스 객체가 생성되지 않았다.
  - 이유? Spring IoC 컨테이너는 Spring에서 제공한 @Component 애노테이션이 붙은
    클래스를 찾는다.
  - 아직 서블릿과 서비스 클래스에 붙인 @Component를 Spring 것으로 교체하지 않았다.

### 훈련4: @Component 애노테이션을 Spring 것으로 교체한다.

- com.eomcs.util.Component 제거
- com.eomcs.servlet.* 변경
  - Spring의 @Component로 교체한다.
- com.eomcs.service.* 변경
  - Spring의 @Component로 교체한다.
- com.eomcs.lms.ContextLoaderListener 변경
  - Spring의 @Component로 교체한다.
- 서버를 실행하면 Spring IoC 컨테이너를 생성하는 중에 오류가 발생한다.
  - 서비스 객체를 생성하는 중에 의존 객체인 DAO가 없기 때문이다.
  - 해결책? DAO를 먼저 준비해야 한다.
  
### 훈련5: Spring IoC 컨테이너가 자동으로 생성할 수 없는 경우 설정 클래스에서 생성한다.

- com.eomcs.lms.AppConfig 변경
  - DAO 객체를 생성하는 메서드를 추가한다.  
- com.eomcs.lms.ContextLoaderListener 변경
  - IoC 컨테이너에 저장할 객체 생성 코드를 제거한다.
- 서버를 실행하면 정상적으로 동작한다.

  
