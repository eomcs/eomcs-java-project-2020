# 45_2 - Mybatis를 이용하여 DAO 구현체 자동 생성하기

## 학습목표

- Mybatis의 SqlSession을 이용하여 DAO 구현체를 자동으로 생성할 수 있다.
- Mybatis의 DAO 자동 생성기를 경험해 본다.

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/service/impl/BoardServiceImpl2.java 추가
- src/main/java/com/eomcs/lms/DataLoaderListener.java 변경

## 실습  

### 훈련1: BoardServiceImpl 에 대해서 Mybatis DAO 자동 생성기를 적용한다.

- com.eomcs.lms.service.impl.BoardServiceImpl2 추가
  - BoardDao 구현체를 직접 주입하는 대신에 SqlSessionFactory를 주입한다.
  - BoardDao를 사용할 때 마다 SqlSession 객체를 통해 만들어 쓴다. 
- com.eomcs.lms.DataLoaderListener 변경
  - BoardService 구현체를 BoardServiceImpl2로 교체한다.
