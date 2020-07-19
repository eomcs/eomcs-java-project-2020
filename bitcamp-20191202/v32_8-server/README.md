# 32_8 - DAO 클래스의 공통점을 뽑아 수퍼 클래스로 정의하기(generalization 수행하기)

## 학습목표

- 상속의 기법 중 generalization을 이해한다.
- generalization을 구현할 수 있다.

### 상속

- specialization
  - 수퍼 클래스를 상속 받아 특별한 기능을 수행하는 서브 클래스 만들기.
- generalization
  - 클래스들의 공통점을 뽑아 수퍼 클래스로 만든 후에 상속 관계를 맺기.
 

## 실습 소스 및 결과

- src/main/java/com/eomcs/lms/dao/AbstractObjectFileDao.java 추가
- src/main/java/com/eomcs/lms/dao/BoardObjectFileDao.java 변경
- src/main/java/com/eomcs/lms/dao/LessonObjectFileDao.java 변경
- src/main/java/com/eomcs/lms/dao/MemberObjectFileDao.java 변경

## 실습  

### 훈련 1: DAO의 공통점을 뽑아 수퍼 클래스로 정의하라.

- com.eomcs.lms.dao.AbstractObjectFileDao 클래스를 생성한다.

### 훈련 2: BoardObjectFileDao가 위에서 정의한 클래스를 상속 받도록 변경하라.

- com.eomcs.lms.dao.BoardObjectFileDao 변경한다.
  - 상속 받은 필드와 메서드를 사용한다.
  - indexOf()를 오버라이딩 한다.

### 훈련 3: LessonObjectFileDao가 위에서 정의한 클래스를 상속 받도록 변경하라.

- com.eomcs.lms.dao.LessonObjectFileDao 변경한다.
  - 상속 받은 필드와 메서드를 사용한다.
  - indexOf()를 오버라이딩 한다.

### 훈련 4: MemberObjectFileDao가 위에서 정의한 클래스를 상속 받도록 변경하라.

- com.eomcs.lms.dao.MemberObjectFileDao 변경한다.
  - 상속 받은 필드와 메서드를 사용한다.
  - indexOf()를 오버라이딩 한다.





  
  