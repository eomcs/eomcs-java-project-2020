package com.eomcs.util;

// 컬렉션에서 값을 꺼내는 방법(사용 규칙)을 정의
public interface Iterator<E> {
  boolean hasNext();
  E next();
}
