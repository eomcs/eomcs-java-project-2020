package com.eomcs.util;

// Generalization 1단계:
// => ArrayList와 LinkedList 사이의 공통 분모를 추출하여 수퍼 클래스를 정의한다.
// => ArrayList와 LinkedList는 이렇게 정의한 수퍼 클래스를 상속 받는다.
//
public class List<E> {
  protected int size;
  
  public int size() {
    return size;
  }
  
  public void add(E e) {
    //ArrayList나 LinkedList는 동작 방법이 다르기 때문에 
    //여기서 구현할 필요가 없다.
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






