package com.eomcs.util;

import java.util.EmptyStackException;

public class Stack<E> extends LinkedList<E> {

  public E push(E item) {
    add(item);
    return item;
  }

  public E pop() {
    if (size() == 0) {
      throw new EmptyStackException();
    }
    return remove(size() - 1);
  }

  public E peek() {
    if (size() == 0) {
      throw new EmptyStackException();
    }
    return get(size() - 1);
  }

  public boolean empty() {
    return size() == 0;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Stack<E> clone() throws CloneNotSupportedException {
    Stack<E> newStack = new Stack<>();
    Object[] values = this.toArray();
    for (Object value : values) {
      newStack.push((E) value);
    }
    return newStack;
  }

  // AbstractList 에서 구현한 iterator()를 
  // Stack 자료 구조에 맞춰 오버라이딩 한다.
  @Override
  public Iterator<E> iterator() {
    try {
      // 스택은 한 번 pop() 하면 데이터가 제거된다.
      // 따라서 복제본을 만들어 사용한다.
      return new StackIterator<E>(this.clone());
    } catch (Exception e) {
      // 스택을 복제할 때 오류가 발생한다면,
      // 이 메서드를 호출한 쪽에 실행 오류를 던진다.
      throw new RuntimeException("스택 복제하는 중에 오류 발생!");
    }
  }
}
