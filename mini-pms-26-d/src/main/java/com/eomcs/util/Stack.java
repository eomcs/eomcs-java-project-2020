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

    // 익명 클래스는 이름이 없기 때문에 
    // 클래스를 정의하자마자 바로 인스턴스를 생성해야 한다.
    return new Iterator<E>() {
      Stack<E> stack;

      { // 인스턴스 초기화 블록
        // 익명 클래스는 이름이 없기 때문에 생성자를 만들 수 없다.
        // => 이런 경우에 인스턴스 초기화 블록을 사용할 수 있다.
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
    };
  }
}
