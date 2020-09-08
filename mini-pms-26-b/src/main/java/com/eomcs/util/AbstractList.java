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
    return new ListIterator();
  }

  // 논스태틱 중첩 클래스로 정의한 Iterator 구현체 
  // => 바깥 클래스의 인스턴스 멤버를 직접 접근할 수 있다.
  // => 생성자에서 바깥 클래스의 인스턴스를 받을 필요가 없다.
  // => 바깥 클래스의 타입 파라미터를 그대로 사용하면 된다.
  private class ListIterator implements Iterator<E> {
    int cursor;

    @Override
    public boolean hasNext() {
      return cursor < size();
    }

    @Override
    public E next() {
      if (cursor ==  size())
      throw new NoSuchElementException();
      return get(cursor++);
    }
  }
}
