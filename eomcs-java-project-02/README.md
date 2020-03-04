# 02 - 프로젝트를 `이클립스 IDE`로 임포트하기

## 학습 목표

- `그레이들`을 이용하여 `이클립스 IDE`용 프로젝트로 전환할 수 있다.
- `이클립스` 워크스페이스로 프로젝트를 가져올 수 있다.
- `이클립스`에서 빌드하고 실행할 수 있다.

## 구현 목표

- 프로젝트를 이클립스 IDE에서 임포트할 수 있게 설정하라.
- 이클립스 IDE에서 애플리케이션을 실행하라.
  
## 구현 결과 및 소스 파일

- Eclipse IDE의 Console 뷰에서 실행 결과를 확인한다.

## 실습

### 실습1 - 그레이들 빌드 스크립트에 이클립스 플러그인을 추가한다

build.gradle 파일에 'eclipse' 플러그인을 추가한다.

```groovy
plugins {
    id 'java'
    id 'application'
    id 'eclipse'
}
```

### 실습2 - 이클립스 IDE 용 설정 파일을 생성한다

```console
[~/git/java-study/java-project]$ gradle eclipse
```

### 실습3 - 이클립스 IDE의 워크스페이스로 프로젝트를 가져온다

- Eclise IDE 메뉴 > `File > Import...` 클릭
- Import 대화창 > `General 노드 > Existing Projects into Workspace 선택` > Next 클릭
- `java-project` 프로젝트 폴더 선택 > Finish 클릭

### 실습4 - 이클립스 IDE에서 프로젝트를 실행한다

- `src/main/java/com/eomcs/pms/App.java` 소스 파일을 열기
- Eclipse IDE 메뉴 > `Run > Run` 클릭
- `Console 뷰`에 출력된 결과 확인
