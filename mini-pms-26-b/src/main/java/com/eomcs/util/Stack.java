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
    // StackIterator의 생성자에서 clone()에서 발생한 예외를 처리하도록 프로그래밍 했기 때문에
    // 여기서는 할 필요가 없다.
    return new StackIterator<E>();
  }

  // 논스태틱 중첩 클래스로 정의한 Iterator 구현체 
  // => 바깥 클래스의 인스턴스 멤버를 직접 접근할 수 있다.
  // => 생성자에서 바깥 클래스의 인스턴스를 받을 필요가 없다.
  // => 중첩 클래스가 인스턴스 멤버가 되는 순간 
  //    바깥 클래스의 영향을 받기 때문에 
  //    바깥 클래스의 타입 파라미터와 중첩 클래스의 타입 파라미터와 이름이 같으면 
  //    충돌이 발생하게 된다.
  //    따라서 중첩 클래스의 타입 파라미터 이름을 변경하여 
  //    바깥 클래스의 타입 파라미터와 구분하도록 한다.
  private class StackIterator<T> implements Iterator<T> {
    Stack<T> stack;

    @SuppressWarnings("unchecked")
    public StackIterator() {
      try {
        // 스택은 한 번 pop() 하면 데이터가 제거된다.
        // 따라서 복제본을 만들어 사용한다.
        this.stack = (Stack<T>) Stack.this.clone();
      } catch (Exception e) {
        // 스택을 복제할 때 오류가 발생한다면,
        // 이 메서드를 호출한 쪽에 실행 오류를 던진다.
        throw new RuntimeException("큐를 복제하는 중에 오류 발생!");
      }
    }

    @Override
    public boolean hasNext() {
      return !stack.empty();
    }

    @Override
    public T next() {
      if (stack.empty())
        throw new NoSuchElementException();

      // stack 은 중첩 클래스 내부에서 선언한 변수다. 
      // 이미 생성자에서 제네릭 타입에 대한 형변환을 수행했기 때문에 
      // 여기에서 따로 형변환 할 필요가 없다.
      return stack.pop();
    }
  }
}
