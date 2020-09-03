package com.eomcs.util;

import java.lang.reflect.Array;

// List 를 상속 받기 때문에 
// - size 필드는 제거한다.
// - size() 메서드는 제거한다.
// - 상속 받은 메서드를 구현한다.
//
public class LinkedList<E> extends AbstractList<E> {

  private Node<E> first;

  private Node<E> last;

  static class Node<E> {
    E value;
    Node<E> next;

    public Node() {}

    public Node(E value) {
      this.value = value;
    }
  }

  @Override
  public boolean add(E e) {
    Node<E> node = new Node<>();
    node.value = e;

    if (first == null) {
      first = node;
    } else {
      last.next = node;
    }
    last = node;

    size++;

    return true;
  }

  @Override
  public E get(int index) {
    if (index < 0 || index >= this.size) {
      throw new IndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }

    Node<E> cursor = this.first;
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }
    return cursor.value;
  }

  @Override
  public void add(int index, E element) {
    if (index < 0 || index > this.size) {
      throw new IndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }

    Node<E> node = new Node<>(element);

    size++;

    if (index == 0) {
      node.next = first;
      first = node;
      return;
    }

    Node<E> cursor = this.first;
    for (int i = 1; i <= index - 1; i++) {
      cursor = cursor.next;
    }

    node.next = cursor.next;
    cursor.next = node;

    if (node.next == null) {
      last = node;
    }
  }

  @Override
  public E remove(int index) {
    if (index < 0 || index >= this.size) {
      throw new IndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }

    size--;

    if (index == 0) {
      Node<E> old = first;
      first = old.next;
      old.next = null; // 가비지가 다른 인스턴스를 가리키지 않게 한다.
      return old.value;
    }

    Node<E> cursor = this.first;
    for (int i = 1; i <= index - 1; i++) {
      cursor = cursor.next;
    }

    Node<E> old = cursor.next;
    cursor.next = old.next;
    old.next = null; // 가비지가 다른 인스턴스를 가리키지 않게 한다.

    if (cursor.next == null) {
      last = cursor;
    }

    return old.value;
  }

  @Override
  public E set(int index, E element) {
    if (index < 0 || index >= this.size) {
      throw new IndexOutOfBoundsException("인덱스가 유효하지 않습니다.");
    }

    Node<E> cursor = this.first;
    for (int i = 1; i <= index; i++) {
      cursor = cursor.next;
    }

    E old = cursor.value;
    cursor.value = element;

    return old;
  }

  @Override
  public Object[] toArray() {
    Object[] arr = new Object[this.size];

    int i = 0;
    Node<E> cursor = first;

    while (cursor != null) {
      arr[i++] = cursor.value;
      cursor = cursor.next;
    }

    return arr;
  }

  @Override
  @SuppressWarnings("unchecked")
  public E[] toArray(E[] arr) {

    if (arr.length < size) {
      arr = (E[]) Array.newInstance(arr.getClass().getComponentType(), size);
    }

    Node<E> cursor = first;
    for (int i = 0; i < size; i++) {
      arr[i] = cursor.value;
      cursor = cursor.next;
    }

    return arr;
  }

  // Object.clone()을 오버라이딩 할 때 'deep copy' 이용하여 스택 객체 복사하기
  // => 새 연결 리스트를 만들어 원본에 보관된 값을 복사한다.
  // => 따라서 복사본의 Node 객체는 원본의 Node 객체와 다르다. 
  //    복사본의 상태 변경에 원본은 영향 받지 않는다.
  //
  @SuppressWarnings("unchecked")
  @Override
  public LinkedList<E> clone() throws CloneNotSupportedException {
    LinkedList<E> newList = new LinkedList<>();
    Object[] values = this.toArray();
    for (Object value : values) {
      newList.add((E) value);
    }
    return newList;
  }
}





