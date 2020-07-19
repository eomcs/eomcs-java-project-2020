package com.eomcs.util;

import java.util.Arrays;

public class ArrayList<E> extends AbstractList<E> {
  
  private static final int DEFAULT_CAPACITY = 2;
  
  Object[] elementData;
  
  public ArrayList() {
    this.elementData = new Object[DEFAULT_CAPACITY];
  }
  
  public ArrayList(int initialCapacity) {
    if (initialCapacity < DEFAULT_CAPACITY) {
      this.elementData = new Object[DEFAULT_CAPACITY];
    } else {
      this.elementData = new Object[initialCapacity];
    }
  }
  
  // 추상 메서드를 구현할 때, 
  // 다음과 같이 @Override 애노테이션을 붙이지 않아도 문법 검사가 이루어지기 때문에 편하다.
  //@Override
  public void add(E e) {
    if (this.size == this.elementData.length) {
      grow();
    }
    this.elementData[this.size++] = e;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public E get(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }
    return (E) this.elementData[index];
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public E set(int index, E e) {
    if (index < 0 || index >= this.size) {
      return null;
    }
    E oldValue = (E) this.elementData[index];
    this.elementData[index] = e;
    return oldValue;
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public E remove(int index) {
    if (index < 0 || index >= this.size) {
      return null;
    }
    
    E oldValue = (E) this.elementData[index];
    System.arraycopy(this.elementData, index + 1, 
        this.elementData, index, this.size - (index + 1) );
    /*
    for (int i = index + 1; i < this.size; i++) {
      this.elementData[i - 1] = this.elementData[i];
    }
    */
    this.size--;
    return oldValue;
  }
  
  @Override
  public Object[] toArray() {
    return Arrays.copyOf(this.elementData, this.size);
    /*
    Object[] arr = new Object[this.size];
    for (int i = 0; i < this.size; i++) {
      arr[i] = this.elementData[i];
    }
    return arr;
    */
  }
  
  @Override
  @SuppressWarnings("unchecked")
  public E[] toArray(E[] arr) {
    if (arr.length < this.size) {
      // 파라미터로 받은 배열이 작을 때는 새 배열을 만들어 리턴.
      return (E[]) Arrays.copyOf(this.elementData, this.size, arr.getClass());
    }
    System.arraycopy(this.elementData, 0, arr, 0, this.size);
    return arr; // 넉넉할 때는 파라미터로 받은 배열을 그대로 리턴. 
  }
  
  @Override
  public void add(int index, E value) {
    if (index < 0 || index >= this.size)
      return;

    if (this.size == this.elementData.length) {
      grow();
    }

    for (int i = size - 1; i >= index; i--)
      this.elementData[i + 1] = this.elementData[i];

    this.elementData[index] = value;
    this.size++;
  }
  
  private Object[] grow() {
    return this.elementData = Arrays.copyOf(this.elementData, 
        newCapacity());
  }
  
  private int newCapacity() {
    int oldSize = this.elementData.length;
    return oldSize + (oldSize >> 1);
  }
}





