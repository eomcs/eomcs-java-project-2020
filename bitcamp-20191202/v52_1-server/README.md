# 52_1 - 애노테이션을 이용하여 트랜잭션 제어하기

## 학습목표

- Spring 에서 제공하는 애노테이션을 이용하여 선언하는 방식으로 트랜잭션을 제어할 수 있다.
- @Transactional, @EnableTransactionManagement 애노테이션을 사용했을 때 
  Spring 프레임워크가 트랜잭션을 다루는 기법을 이해한다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/DatabaseConfig.java 변경
- src/main/java/com/eomcs/lms/service/impl/PhotoBoardServiceImpl.java 변경

## 실습  

### 훈련1: 애노테이션으로 트랜잭션을 제어할 수 있도록 해당 기능을 활성화시킨다.

- com.eomcs.lms.DatabaseConfig 변경
  - @Transactional 애노테이션 기능을 활성화시키기 위해서 
    @EnableTransactionManagement 애노테이션을 추가로 선언한다.

#### @EnableTransactionManagement 

메서드에 @Transactional 이 붙어 있으면 
해당 메서드를 트랜잭션으로 묶기 위해  
프록시 객체를 자동 생성한다.
  

### 훈련2: @Transactional 애노테이션으로 트랜잭션을 제어하라.

- com.eomcs.lms.service.impl.PhotoBoardServiceImpl 변경
  - TransactionTemplate을 사용하는 대신에 @Transactional 애노테이션을 붙인다.
  - add(), update(), delete()을 변경한다.
  
