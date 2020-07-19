# 63_1 - Tiles 템플릿을 관리자 모드와 일반 모드로 구분하기

## 학습목표

- Tiles 템플릿의 설정을 다룰 수 있다.

## 실습 소스 및 결과

- Web Config 변경 
  - src/main/java/com/eomcs/lms/web/AppWebConfig.java 변경
  - src/main/java/com/eomcs/lms/admin/AdminWebConfig.java 변경
- Tiles 레이아웃 정의
  - src/main/webapp/WEB-INF/defs/tiles.xml 변경
  - src/main/webapp/WEB-INF/defs/admin-tiles.xml 추가
- 템플릿 JSP
  - src/main/webapp/WEB-INF/tiles/app/*.jsp 변경
  - src/main/webapp/WEB-INF/tiles/admin/*.jsp 변경
- JSP
  - src/main/webapp/WEB-INF/views/app/**/*.jsp 이동
  - src/main/webapp/WEB-INF/views/admin/**/*.jsp 추가

 

