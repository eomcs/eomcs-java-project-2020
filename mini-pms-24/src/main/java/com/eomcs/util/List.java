package com.eomcs.util;

// 데이터 목록을 다루는 객체의 사용 규칙을 따로 정의
// => 문법
//    interface 규칙명 {...}
//
public interface List<E> {

  // 사용 규칙(호출할 메서드의 시그너처)이기 때문에 모든 메서드는 추상 메서드이다.
  // 또한 규칙은 공개되어야 하기 때문에 모든 메서드는 public 이다.
  // => 문법
  //      public abstract 리턴타입 메서드명(파라미터,...);

  // => public을 생략할 수 있다.
  //      abstract 리턴타입 메서드명(파라미터,...);

  // => abstract를 생략할 수 있다.
  //      리턴타입 메서드명(파라미터,...);
  //
  public abstract boolean add(E e);

  public /*abstract*/ void add(int index, E element);

  /*public*/ abstract E get(int index);

  E set(int index, E element);

  E remove(int index);

  Object[] toArray();

  E[] toArray(E[] arr);

  int size();
}




