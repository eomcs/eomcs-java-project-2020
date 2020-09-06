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
    try {
      // 큐는 한 번 poll() 하면 데이터가 제거된다.
      // 따라서 복제본을 만들어 사용한다.
      return new QueueIterator<E>(this.clone());
    } catch (Exception e) {
      // 큐를 복제할 때 오류가 발생한다면,
      // 이 메서드를 호출한 쪽에 실행 오류를 던진다.
      throw new RuntimeException("큐를 복제하는 중에 오류 발생!");
    }
  }

  // 스태틱 중첩 클래스로 정의한 Iterator 구현체 
  // => 이 클래스 자체는 외부에 공개하지 않기 때문에 private 으로 접근을 막는다.
  // => 외부에서는 iterator()가 리턴한 객체가 ListIterator 인스턴스인지 알 필요가 없다.
  // => 그냥 리턴 받은 객체를 Iterator 규칙에 따라 사용하면 되는 것이다.
  private static class QueueIterator<E> implements Iterator<E> {
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
}
