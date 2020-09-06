package com.eomcs.util;

import java.util.NoSuchElementException;

// Stack 객체의 목록 조회 기능을 담당한다.
public class StackIterator<E> implements Iterator<E> {
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
