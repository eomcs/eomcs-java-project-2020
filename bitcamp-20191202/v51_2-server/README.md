# 51_2 - 관리하기 쉽게 Spring IoC 설정 파일(Java Config)을 분리하기

## 학습목표

- Spring Java Config 파일을 다룰 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/AppConfig.java 변경
- src/main/java/com/eomcs/lms/DatabaseConfig.java 추가
- src/main/java/com/eomcs/lms/MybatisConfig.java 추가
- src/main/java/com/eomcs/lms/ContextLoaderListener.java 변경

## 실습  

### 훈련1: 데이터베이스 관련 설정을 분리하기

- com.eomcs.lms.DatabaseConfig 추가
  - AppConfig에서 데이터베이스 관련 객체 생성 코드를 가져온다.
- com.eomcs.lms.AppConfig 변경
  
### 훈련2: Mybatis 관련 설정을 분리하기

- com.eomcs.lms.MybatisConfig 추가
  - AppConfig에서 Mybatis 관련 객체 생성 코드를 가져온다.
- com.eomcs.lms.AppConfig 변경

### 훈련3: Spring IoC 컨테이너를 생성할 때 Java Config를 모두 지정한다.

- com.eomcs.lms.ContextLoaderListener 변경
  - Spring IoC 컨테이너 생성 코드를 변경한다.
  
### 훈련4: @Configuration 애노테이션을 사용하여 Java Config 를 설정한다.

- com.eomcs.lms.DatabaseConfig 변경
  - @Configuration 애노테이션을 붙인다.
- com.eomcs.lms.MybatisConfig 변경
  - @Configuration 애노테이션을 붙인다.
- com.eomcs.lms.ContextLoaderListener 변경
  - Spring IoC 컨테이너를 생성할 때 Java Config로 AppConfig 만 지정한다.