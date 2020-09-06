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

  // 스태틱 중첩 클래스로 정의한 Iterator 구현체 
  // => 이 클래스 자체는 외부에 공개하지 않기 때문에 private 으로 접근을 막는다.
  // => 외부에서는 iterator()가 리턴한 객체가 ListIterator 인스턴스인지 알 필요가 없다.
  // => 그냥 리턴 받은 객체를 Iterator 규칙에 따라 사용하면 되는 것이다.
  private static class StackIterator<E> implements Iterator<E> {
    Stack<E> stack;

    public StackIterator(Stack<E> stack) {
      this.stack = stack;
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
}
