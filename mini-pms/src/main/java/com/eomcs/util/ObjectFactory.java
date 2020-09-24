package com.eomcs.util;

// CSV 문자열 받아서 분석하여 객체를 생성해주는 공장에 대한 규칙이다.
// 
public interface ObjectFactory<T> {
  T create(String csv);
}
