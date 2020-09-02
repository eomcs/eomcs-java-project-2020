package com.eomcs.util;

import java.util.Arrays;

// List 를 상속 받기 때문에 
// - size 필드는 제거한다.
// - size() 메서드는 제거한다.
// - 상속 받은 메서드를 구현한다.
//
public class ArrayList<E> extends AbstractList<E> {

  static final int DEFAULT_CAPACITY = 3;
  Object[] elementData;

  public ArrayList() {
    elementData = new Object[DEFAULT_CAPACITY];
  }

  public ArrayList(int initialCapacity) {
    if (initialCapacity <= DEFAULT_CAPACITY) {
      elementData = new Object[DEFAULT_CAPACITY];
    } else {
      elementData = new Object[initialCapacity];
    }
  }

  @Override
  public boolean add(E e) {
    if (size == elementData.length) {
      grow();
    }
    elementData[size++] = e;
    return true;
  }

  private void grow() {
    int newCapacity = elementData.length + (elementData.length >> 1);
    elementData = Arrays.copyOf(elementData, newCapacity);
  }

  @Override
  public void add(int index, E element) {
    if (size == elementData.length) {
      grow();
    }
    if (index < 0 || index > size) {
      throw new ArrayIndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }
    for (int i = size; i > index ; i--) {
      elementData[i] = elementData[i - 1];
    }
    elementData[index] = element;
    size++;
  }

  @Override
  @SuppressWarnings("unchecked")
  public E get(int index) {
    if (index < 0 || index >= size) {
      throw new ArrayIndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }
    return (E) elementData[index];
  }

  @Override
  @SuppressWarnings("unchecked")
  public E set(int index, E element) {
    if (index < 0 || index >= size) {
      throw new ArrayIndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }
    Object old = elementData[index];
    elementData[index] = element;
    return (E) old;
  }

  @Override
  @SuppressWarnings("unchecked")
  public E remove(int index) {
    Object old = elementData[index];

    System.arraycopy(
        elementData, // 복사 대상
        index + 1, // 복사할 항목의 시작 인덱스
        elementData, // 목적지
        index, // 복사 목적지 인덱스
        this.size - (index + 1) // 복사할 항목의 개수
        );

    size--;
    elementData[size] = null;
    return (E) old;
  }

  @Override
  public Object[] toArray() {
    Object[] arr = Arrays.copyOf(elementData, this.size);
    return arr;
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
}




