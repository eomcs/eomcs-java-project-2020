package com.eomcs.util;

// List 객체에서 Iterator 규칙에 따라 값을 꺼내주는 클래스를 정의
public class ListIterator<E> implements Iterator<E> {
  
  List<E> list;
  int cursor;
  
  public ListIterator(List<E> list) {
    this.list = list;
  }
  
  @Override
  public boolean hasNext() {
    return cursor < list.size();
  }
  
  @Override
  public E next() {
    return list.get(cursor++);
  }
}
