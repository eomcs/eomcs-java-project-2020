package com.eomcs.util;

public abstract class AbstractList<E> implements List<E> {
  
  protected int size;
  
  public int size() {
    return size;
  }
  
  @Override
  public Iterator<E> iterator() {
    // this = 인스턴스주소;
    // List 객체에서 값을 꺼내주는 일을 할 Iterator 구현체를 준비하여 리턴한다.
    return this.new ListIterator<E>();
  }
  
  // non-static nested class = inner class
  class ListIterator<T> implements Iterator<T> {
    
    List<T> list;
    int cursor;
    
    @SuppressWarnings("unchecked")
    public ListIterator() {
      this.list = (List<T>) AbstractList.this;
    }
    
    @Override
    public boolean hasNext() {
      return cursor < list.size();
    }
    
    @Override
    public T next() {
      return list.get(cursor++);
    }
  }
}






