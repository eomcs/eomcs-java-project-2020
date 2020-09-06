package com.eomcs.util;

import java.util.NoSuchElementException;

// Queue 객체의 목록 조회 기능을 담당한다.
public class QueueIterator<E> implements Iterator<E> {
  Queue<E> queue;

  public QueueIterator(Queue<E> queue) {
    this.queue = queue;
  }

  @Override
  public boolean hasNext() {
    return queue.size() > 0;
  }

  @Override
  public E next() {
    if (queue.size() == 0)
      throw new NoSuchElementException();

    return queue.poll();
  }
}
