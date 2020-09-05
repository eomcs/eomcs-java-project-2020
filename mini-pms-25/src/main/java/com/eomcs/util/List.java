package com.eomcs.util;

public interface List<E> {

  boolean add(E e);

  void add(int index, E element);

  E get(int index);

  E set(int index, E element);

  E remove(int index);

  Object[] toArray();

  E[] toArray(E[] arr);

  int size();

  // 컬렉션의 반복자를 리턴해주는 규칙을 추가한다.
  // => 컬렉션이 목록을 다루는 방식이 다르기 때문에 
  //    서브 클래스가 반드시 구현해야만 하는 추상 메서드이다.
  Iterator<E> iterator();
}




