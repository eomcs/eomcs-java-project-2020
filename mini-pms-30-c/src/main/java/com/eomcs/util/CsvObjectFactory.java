package com.eomcs.util;

//이 인터페이스를 구현하는 클래스는
//CSV 형식의 문자열을 객체로 변환하는 기능을 갖춘다.
//
public interface CsvObjectFactory<T> {
  T create(String csv);
}

