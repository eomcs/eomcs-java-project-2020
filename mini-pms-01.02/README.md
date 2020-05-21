# 01.02 - `github.com`에 프로젝트 공유하기

## 학습 목표

- `.gitignore` 파일의 용도를 이해하고 작성할 수 있다.
- `github.com`의 저장소에 프로젝트를 공유할 수 있다.

## 구현 목표

- 프로젝트를 로컬 저장소에 등록하라.
- 로컬 저장소를 서버에 업로드하라.
  
## 구현 결과 및 소스 파일

- `github.com` 저장소에 업로드된 것을 확인한다.

## 실습

### 실습1 - git 저장소에 보관하지 않을 항목을 지정한다

- `.gitignore` 파일 변경
  - `~/git/java-study/.gitignore` 파일 편집
  - 저장에서 제외시킬 대상을 등록

### 실습2 - 로컬 저장소에 보관할 파일(디렉토리 포함) 정보를 git 스테이지에 등록한다

```console
[~/git/java-study]$ git add .
```

### 실습3 - git 스테이지에 등록된 정보에 따라 파일을 로컬 저장소에 보관한다

```console
[~/git/java-study]$ git commit -m "작업내용"
```

### 실습4 - 로컬 저장소를 원격 저장소(github.com)에 업로드한다.

```console
[~/git/java-study]$ git push
```
