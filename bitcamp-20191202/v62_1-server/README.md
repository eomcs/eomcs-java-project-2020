# 62_1 - Facebook SNS 로그인 적용 + sidebar 레이아웃 템플릿 적용

## 학습목표

- Facebook 로그인을 적용할 수 있다.
- Tiles 템플릿을 다룰 수 있다.

## 실습 소스 및 결과

- src/main/webapp/WEB-INF/views/auth/form.jsp 변경
- src/main/webapp/WEB-INF/views/member/list.jsp 변경
- src/main/java/com/eomcs/lms/web/AuthController.java 변경
- src/main/java/com/eomcs/lms/service/MemberService.java 변경
- src/main/java/com/eomcs/lms/service/impl/MemberServiceImpl.java 변경
- src/main/java/com/eomcs/lms/dao/MemberDao.java 변경
- src/main/resources/com/eomcs/lms/mapper/MemberMapper.xml 변경
- src/main/webapp/WEB-INF/defs/tiles.xml 변경
- src/main/webapp/WEB-INF/tiles/template2.jsp 추가
- src/main/webapp/WEB-INF/views/auth/side.jsp 추가
- src/main/webapp/WEB-INF/views/board/side.jsp 추가
- src/main/webapp/WEB-INF/views/lesson/side.jsp 추가
- src/main/webapp/WEB-INF/views/member/side.jsp 추가
- src/main/webapp/WEB-INF/views/photoboard/side.jsp 추가
- src/main/webapp/css/common.css 변경 

## 실습  

### 훈련1: 로그인 폼에 페이스북 로그인 버튼을 추가

- /WEB-INF/views/auth/form.jsp 변경
  - 페이지북 로그인 버튼 태그 추가
  - 페이스북 API 로딩 자바스크립트 코드 추가

### 훈련2: 페이스북 로그인 요청 처리 추가

- com.eomcs.lms.web.AuthController 변경
  - `/auth/facebookLogin` 요청 핸들러 추가
- com.eomcs.lms.service.MemberService 변경
  - get(String email) 메서드 선언 추가 
- com.eomcs.lms.service.impl.MemberServiceImpl 변경
  - get(String email) 메서드 구현
- com.eomcs.lms.dao.MemberDao 변경
  - findByEmail(String email) 메서드 선언 추가
- src/main/resources/com/eomcs/lms/mapper/MemberMapper 변경
  - findByEmail SQL 문 추가

### 훈련3: 회원 목록에서 회원 사진에 링크 걸기

- /WEB-INF/views/member/list.jsp 변경
  - 회원 이름 뿐만아니라 회원 사진도 링크에 포함한다.

### 훈련4: 화면에서 사이드바 추가하기
