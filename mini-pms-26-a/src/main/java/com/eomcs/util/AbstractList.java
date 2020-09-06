package com.eomcs.util;

import java.util.NoSuchElementException;

public abstract class AbstractList<E> implements List<E>{

  protected int size;

  @Override
  public int size() {
    return size;
  }

  // 인터페이스 새로 추가된 규칙, `Iterator` 구현체를 리턴하는 메서드를 정의한다.
  @Override
  public Iterator<E> iterator() {
    return new ListIterator<E>(this);
  }

  // 스태틱 중첩 클래스로 정의한 Iterator 구현체 
  // => 이 클래스 자체는 외부에 공개하지 않기 때문에 private 으로 접근을 막는다.
  // => 외부에서는 iterator()가 리턴한 객체가 ListIterator 인스턴스인지 알 필요가 없다.
  // => 그냥 리턴 받은 객체를 Iterator 규칙에 따라 사용하면 되는 것이다.
  private static class ListIterator<E> implements Iterator<E> {
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
}
