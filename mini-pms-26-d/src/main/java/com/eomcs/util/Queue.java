package com.eomcs.util;

import java.util.NoSuchElementException;

public class Queue<E> extends LinkedList<E> {

  public boolean offer(E e) {
    return add(e);
  }

  public E poll() {
    if (size() == 0) {
      return null;
    }
    return remove(0);
  }

  public E peek() {
    if (size() == 0) {
      return null;
    }
    return get(0);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Queue<E> clone() throws CloneNotSupportedException {
    // => MyQueue는 MyLinkedList를 상속 받았고,
    //    MyLinkedList의 경우 노드와 노드 사이를 연결해야 하기 때문에 
    //    단순히 'shallow copy'를 수행해서는 안된다.
    // => 다음과 같이 새 Queue를 만들고, 
    //    기존 Queue에 저장된 값을 꺼내서 새 Queue에 저장해야 한다.
    Queue<E> newQueue = new Queue<>();
    Object[] values = this.toArray();
    for (Object value : values) {
      newQueue.offer((E) value);
    }
    return newQueue;
  }

  // AbstractList 에서 구현한 iterator()를 
  // Queue 자료 구조에 맞춰 오버라이딩 한다.
  @Override
  public Iterator<E> iterator() {

    // 익명 클래스는 이름이 없기 때문에 
    // 클래스를 정의하자마자 바로 인스턴스를 생성해야 한다.
    return new Iterator<E>() {
      Queue<E> queue;

      { // 인스턴스 초기화 블록
        // 익명 클래스는 이름이 없기 때문에 생성자를 만들 수 없다.
        // => 이런 경우에 인스턴스 초기화 블록을 사용할 수 있다.
        try {
          this.queue = Queue.this.clone();
        } catch (Exception e) {
          throw new RuntimeException("큐를 복제하는 중에 오류 발생!");
        }
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
    };
  }
}