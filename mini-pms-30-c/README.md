# 30-c. 파일 입출력 API : 버퍼 사용하기(BufferedInputStream/BufferedOutputStream)

이번 훈련에서는 **버퍼** 를 활용하여 일정 크기의 데이터를 모았다가 한 번에 출력하는 방식으로 입출력 속도를 개선할 것이다.

**BufferedInputStream** / **BufferedOutputStream** 은,

- 내부에 바이트 배열을 사용하여 입출력 데이터를 보관한다.
- 버퍼가 꽉차면 연결된 출력 스트림으로 내보낸다.
- 데이터를 읽을 때도 일단 버퍼로 왕창 읽어들인 다음에 1바이트씩 리턴한다.

## 훈련 목표

- **BufferedInputStream/BufferedOutputStream**의 역할을 이해한다.
- 버퍼를 이용해 입출력 속도를 개선한다.


## 훈련 내용

- FileInputStream/FileOutputStream 에 BufferedInputStream/BufferedOutputStream을 연결한다.
- DataInputStream/DataOutputStream은 BufferedInputStream/BufferedOutputStream에 연결한다.


## 실습


### 1단계 - FileInputStream/FileOutputStream과 DataInputStream/DataOutputStream 사이에 BufferedInputStream/BufferedOutputStream 연결한다.

- App 클래스
  - 기존의 `saveXxx()` 와 `loadXxx()`를 변경한다.

#### 작업 파일

- com.eomcs.pms.App 변경

## 실습 결과

- src/main/java/com/eomcs/pms/App.java 변경
