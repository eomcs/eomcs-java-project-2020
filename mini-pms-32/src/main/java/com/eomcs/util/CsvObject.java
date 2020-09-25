package com.eomcs.util;

// 객체에서 CSV 문자열을 뽑는 규칙을 정의한다.
// 이 규칙을 준수하는 객체인 경우
// 인터페이스에 선언된 메서드 규칙에 따라 호출하면
// CSV 문자열을 뽑을 수 있다.
public interface CsvObject {
  String toCsvString();
}
