package com.eomcs.util;

public class List<E> {

  protected int size;

  public int size() {
    return size;
  }

  public boolean add(E e) {
    //ArrayList나 LinkedList는 동작 방법이 다르기 때문에 
    //여기서 구현할 필요가 없다.
    return false;
  }

  public void add(int index, E value) {
    //ArrayList나 LinkedList는 동작 방법이 다르기 때문에 
    //여기서 구현할 필요가 없다.
  }

  public E get(int index) {
    //ArrayList나 LinkedList는 동작 방법이 다르기 때문에 
    //여기서 구현할 필요가 없다.
    return null;
  }

  public E set(int index, E e) {
    //ArrayList나 LinkedList는 동작 방법이 다르기 때문에 
    //여기서 구현할 필요가 없다.
    return null;
  }

  public E remove(int index) {
    //ArrayList나 LinkedList는 동작 방법이 다르기 때문에 
    //여기서 구현할 필요가 없다.
    return null;
  }

  public Object[] toArray() {
    //ArrayList나 LinkedList는 동작 방법이 다르기 때문에 
    //여기서 구현할 필요가 없다.
    return null;
  }

  public E[] toArray(E[] arr) {
    //ArrayList나 LinkedList는 동작 방법이 다르기 때문에 
    //여기서 구현할 필요가 없다.
    return null;
  }
}
