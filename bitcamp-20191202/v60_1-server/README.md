# 60_1 - 뷰 컴포넌트에 Tiles 기술 적용하기

## 학습목표

- Tiles를 JSP와 결합하여 뷰 컴포넌트를 구성할 수 있다.
- Tiles 템플릿의 레이아웃을 구성할 수 있다.

## 실습 소스 및 결과

- build.gradle 변경
- src/main/java/com/eomcs/lms/web/AppWebConfig.java 변경
- src/main/webapp/WEB-INF/defs/tiles.xml 생성
- src/main/webapp/WEB-INF/tiles/template.jsp 생성
- src/main/webapp/WEB-INF/tiles/header.jsp 생성
- src/main/webapp/WEB-INF/tiles/footer.jsp 생성
- src/main/webapp/WEB-INF/views/**/*.jsp 생성
- src/main/webapp/css/common.css 생성
- src/main/webapp/css/auth.css 생성
- src/main/webapp/css/board.css 생성
- src/main/webapp/css/member.css 생성
- src/main/webapp/css/lesson.css 생성
- src/main/webapp/css/photoboard.css 생성
  
## 실습  

### 훈련1: Tiles 라이브러리를 프로젝트에 추가하기

- build.gradle 변경
  - 'tiles-jsp' 라이브러리를 의존 라이브러리에 추가한다.
  - 'gradle eclipse'를 실행하여 이클립스 설정 파일을 갱신한다.
  - 이클립스 IDE에서 프로젝트를 리프래시 한다.
 
### 훈련2: Spring WebMVC에 Tiles 뷰를 사용할 뷰리졸버를 추가하기

- com.eomcs.lms.web.AppWebConfig 변경
  - TilesView 템플릿 엔진을 추가한다.

### 훈련3: Tiles 템플릿 설정하기

- com.eomcs.lms.web.AppWebConfig 변경
  - Tiles의 템플릿을 설정하는 TilesConfigurer 를 추가한다.
  

### 훈련4: TilesView 템플릿 엔진이 사용할 설정 파일 준비

- /webapp/WEB-INF/defs/tiles.xml 생성
  - 템플릿의 레이아웃을 정의한다.
  
### 훈련5: TilesView 템플릿 엔진이 사용할 템플릿 JSP 파일을 준비

- /webapp/WEB-INF/tiles/template.jsp 생성
- /webapp/WEB-INF/tiles/header.jsp 생성
- /webapp/WEB-INF/tiles/footer.jsp 생성

### 훈련6: body 에 삽입될 JSP를 준비

- /webapp/WEB-INF/jsp 폴더를 복사하여 /WEB-INF/views 이름으로 저장한다.
  - 템플릿 사용에 맞춰서 JSP 파일을 편집한다. 

  