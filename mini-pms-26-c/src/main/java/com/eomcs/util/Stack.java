package com.eomcs.util;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;

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

    // local class에는 로컬 변수처럼 접근 제어 키워드(private, protected, public)를 붙일 수 없다. 
    class StackIterator implements Iterator<E> {
      Stack<E> stack;

      public StackIterator() {
        try {
          this.stack = Stack.this.clone();
        } catch (Exception e) {
          throw new RuntimeException("큐를 복제하는 중에 오류 발생!");
        }
      }

      @Override
      public boolean hasNext() {
        return !stack.empty();
      }

      @Override
      public E next() {
        if (stack.empty())
          throw new NoSuchElementException();
        return stack.pop();
      }
    }

    return new StackIterator();
  }
}
