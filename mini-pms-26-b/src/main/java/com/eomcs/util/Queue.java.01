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
    // QueueIterator 생성자에서 clone()에서 발생한 예외를 처리하도록 프로그래밍 했기 때문에
    // 여기서는 할 필요가 없다.
    return new QueueIterator<E>();
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
  private class QueueIterator<T> implements Iterator<T> {
    Queue<T> queue;

    @SuppressWarnings("unchecked")
    public QueueIterator() {
      try {
        // 큐는 한 번 poll() 하면 데이터가 제거된다.
        // 따라서 복제본을 만들어 사용한다.
        this.queue = (Queue<T>) Queue.this.clone();
      } catch (Exception e) {
        // 큐를 복제할 때 오류가 발생한다면,
        // 이 메서드를 호출한 쪽에 실행 오류를 던진다.
        throw new RuntimeException("큐를 복제하는 중에 오류 발생!");
      }
    }

    @Override
    public boolean hasNext() {
      return queue.size() > 0;
    }

    @Override
    public T next() {
      if (queue.size() == 0)
        throw new NoSuchElementException();

      // queue 는 중첩 클래스 내부에서 선언한 변수다. 
      // 이미 생성자에서 제네릭 타입에 대한 형변환을 수행했기 때문에 
      // 여기에서 따로 형변환 할 필요가 없다.
      return queue.poll();
    }
  }
}
