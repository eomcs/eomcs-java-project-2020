package com.eomcs.util;

// 데이터 목록 조회 기능을 캡슐화하여 그 사용 규칙을 정의한다.
// => 목록으로 다루려는 데이터의 타입을 제네릭으로 처리한다.
public interface Iterator<E> {
  boolean hasNext();
  E next();
}
