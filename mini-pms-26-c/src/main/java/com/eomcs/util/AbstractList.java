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
    // local class에는 로컬 변수처럼 접근 제어 키워드(private, protected, public)를 붙일 수 없다. 
    class ListIterator implements Iterator<E> {
      int cursor;

      @Override
      public boolean hasNext() {
        // 로컬 클래스에서도 마찬가지로 바깥 클래스의 인스턴스 주소를 사용하고 싶다면 
        // 다음과 같이 바깥 클래스 이름으로 사용하라.
        return cursor < AbstractList.this.size();
      }

      @Override
      public E next() {
        // 물론 바깥 클래스의 인스턴스를 가리키는 AbstractList.this 를 생략할 수 있다.
        if (cursor ==  /*AbstractList.this.*/size())
          throw new NoSuchElementException();

        return get(cursor++);
      }
    }
    return new ListIterator();
  }
}
