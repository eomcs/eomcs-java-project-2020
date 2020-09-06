package com.eomcs.util;

import java.util.NoSuchElementException;

// List 구현체의 목록 조회 기능을 담당한다.
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
    if (cursor == list.size())
      throw new NoSuchElementException();

    return list.get(cursor++);
  }
}
