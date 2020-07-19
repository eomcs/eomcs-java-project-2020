# 45_1 - Java Proxy를 이용하여 DAO 구현체 자동 생성하기

## 학습목표

- Java Proxy의 구동 원리를 이해한다.
- Java Proxy를 이용하여 인터페이스 구현체를 자동으로 생성할 수 있다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao 에서 인터페이스 구현 클래스를 모두 삭제
- src/main/resources/com/eomcs/lms/mapper/XxxMapper.xml 변경
- src/main/java/com/eomcs/lms/dao/MemberDao.java 변경
- src/main/java/com/eomcs/lms/service/impl/MemberServiceImpl.java 변경
- src/main/java/com/eomcs/sql/MybatisDaoFactory.java 추가
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경

## 실습  

### 훈련1: InvocationHandler에서 SQL을 찾기 쉽도록 DAO 인터페이스 메서드명과 SQL ID를 일치시킨다.

- src/main/resources/com/eomcs/lms/mapper/XxxMapper.xml 변경
  - namespace 값을 인터페이스 전체 이름(fully-qualified name)과 일치시킨다.
  - 메서드에서 사용할 SQL은 메서드 이름과 일치시킨다.
- com.eomcs.lms.dao.MemberDao 변경
  - findByEmailAndPassword()의 파라미터를 Map 타입으로 변경한다.
- com.eomcs.lms.service.impl.MemberServiceImpl 변경
  - findByEmailAndPassword()를 호출할 때 파라미터를 Map에 담아 넘긴다. 

### 훈련2: 복잡한 DAO 생성을 단순화시키는 팩토리 클래스를 정의한다.

- com.eomcs.sql.MybatisDaoFactory 클래스 추가
  - DAO 프록시 객체를 생성하는 팩토리 메서드 createDao()를 정의한다.
  - 인터페이스에 따라 리턴 타입을 다르도록 제네릭을 적용한다.
  - InvocationHandler 구현체를 람다 문법을 사용하여 로컬 클래스로 정의한다. 

### 훈련3: DAO 객체 생성에 프록시 생성기를 적용한다.

- com.eomcs.lms.dao.* 에서 DAO 구현체 모두 제거
- com.eomcs.lms.DataLoaderListener 변경
  - MybatisDaoFactory를 이용하여 DAO 구현 객체 생성한다.


